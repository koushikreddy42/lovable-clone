package com.koushik.projects.lovable_clone.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Set;

import static com.koushik.projects.lovable_clone.enums.ProjectPermission.*;

@RequiredArgsConstructor
@Getter
public enum ProjectRole {

    EDITOR(VIEW, EDIT, DELETE, VIEW_MEMBERS), // we can do this since we wrote custom constructor which converts to set
    VIEWER(Set.of(VIEW, VIEW_MEMBERS)), // or we can directly pass set here as well
    OWNER(Set.of(VIEW, EDIT, DELETE, MANAGE_MEMBERS, VIEW_MEMBERS));

    ProjectRole(ProjectPermission... permissions){
        this.permissions = Set.of(permissions);
    }

    private final Set<ProjectPermission> permissions;
}
