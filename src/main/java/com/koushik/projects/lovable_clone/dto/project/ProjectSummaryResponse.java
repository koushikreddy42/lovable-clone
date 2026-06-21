package com.koushik.projects.lovable_clone.dto.project;

import com.koushik.projects.lovable_clone.enums.ProjectRole;

import java.time.Instant;

public record ProjectSummaryResponse(
        Long id,
        String name,
        ProjectRole role,
        Instant createdAt,
        Instant updatedAt
) {
}
