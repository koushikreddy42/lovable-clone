package com.koushik.projects.lovable_clone.service.impl;

import com.koushik.projects.lovable_clone.dto.chat.StreamResponse;
import com.koushik.projects.lovable_clone.llm.PromptUtils;
import com.koushik.projects.lovable_clone.security.AuthUtil;
import com.koushik.projects.lovable_clone.service.AiGenerationService;
import com.koushik.projects.lovable_clone.service.ProjectFileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.metadata.Usage;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@Slf4j
public class AiGenerationServiceImpl implements AiGenerationService {

    private final ChatClient chatClient;
    private final AuthUtil authUtil;
    private final ProjectFileService projectFileService;

    private static final Pattern FILE_TAG_PATTERN = Pattern.compile("<file path=\"([^\"]+)\">(.*?)</file>", Pattern.DOTALL);

    @Override
    @PreAuthorize("@security.canEditProject(#projectId)")
    public Flux<StreamResponse> streamResponse(String userMessage, Long projectId) {
        Long userId = authUtil.getCurrentUserId();
        createChatSessionIfNotExists(projectId, userId);

        Map<String, Object> advisorParams = Map.of(
                "userId", userId,
                "projectId", projectId
        );

        AtomicReference<Long> endTime = new AtomicReference<>(0L);
        AtomicReference<Usage> usageRef = new AtomicReference<>();

        StringBuilder fullResponseBuffer = new StringBuilder();

        return chatClient.prompt()
                .user(userMessage)
                .system(PromptUtils.CODE_GENERATION_SYSTEM_PROMPT)
                .advisors(advisorSpec -> {
                    advisorSpec.params(advisorParams);
                })
                .stream()
                .chatResponse()
                .doOnNext(response -> {
                    if (response.getResults() != null && !response.getResults().isEmpty()) {
                        String content = response.getResult().getOutput().getText();

                        if(content != null && !content.isEmpty() && endTime.get() == 0) { // first non-empty chunk received
                            endTime.set(System.currentTimeMillis());
                        }
                        if(response.getMetadata().getUsage() != null) {
                            usageRef.set(response.getMetadata().getUsage());
                        }
                        fullResponseBuffer.append(content);
                    }

                })
                .doOnComplete(()->{
                    Schedulers.boundedElastic().schedule(() -> {
                        parseAndSaveFiles(fullResponseBuffer.toString(), projectId);
                    });
                })
                .doOnError(error -> log.error("Error during streaming for projectId: {}", projectId))
                .map(response -> {
                    if (response.getResults() != null && !response.getResults().isEmpty()) {
                        String text = response.getResult().getOutput().getText();
                        return new StreamResponse(text != null ? text : "");
                    }
                    return new StreamResponse("");
                });
    }

    private void parseAndSaveFiles(String fullResponse, Long projectId) {
//        String fullResponseBuffer = """
//                <message>I'm going to read the files and generate the code </message>
//                <file path="src/App.jsx">
//                    import App from './App.jsx'
//                    .....
//                </file>
//                <message>I'm going to read the files and generate the code </message>
//                <file path="src/App.jsx">
//                    import App from './App.jsx'
//                    .....
//                </file>
//                """;
        Matcher matcher = FILE_TAG_PATTERN.matcher(fullResponse);
        while(matcher.find()){
            String filePath = matcher.group(1);
            String fileContent = matcher.group(2).trim();
            projectFileService.saveFile(projectId, filePath, fileContent);
        }
    }

    private void createChatSessionIfNotExists(Long projectId, Long userId) {
    }
}
