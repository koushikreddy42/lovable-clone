package com.koushik.projects.lovable_clone.service;

import com.koushik.projects.lovable_clone.dto.member.InviteMemberRequest;
import com.koushik.projects.lovable_clone.dto.member.MemberResponse;

import java.util.List;

public interface ProjectMemberService {
    List<MemberResponse> getProjectMembers(Long projectId, Long userId);

    MemberResponse inviteMember(Long projectId, InviteMemberRequest request, Long userId);

    MemberResponse updateMemberRole(Long projectId, Long memberId, InviteMemberRequest request, Long userId);

    MemberResponse deleteMemberRole(Long projectId, Long memberId, Long userId);
}
