package com.koushik.projects.lovable_clone.mapper;

import com.koushik.projects.lovable_clone.dto.chat.ChatResponse;
import com.koushik.projects.lovable_clone.entity.ChatMessage;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ChatMapper {

    List<ChatResponse> fromListOfChatMessage(List<ChatMessage> chatMessageList);
}
