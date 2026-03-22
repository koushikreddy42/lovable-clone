package com.koushik.projects.lovable_clone.dto.member;

import com.koushik.projects.lovable_clone.enums.ProjectRole;

public record InviteMemberRequest(
        String email,
        ProjectRole role
) {
}
