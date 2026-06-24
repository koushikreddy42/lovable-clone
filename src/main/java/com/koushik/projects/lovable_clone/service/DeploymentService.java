package com.koushik.projects.lovable_clone.service;

import com.koushik.projects.lovable_clone.dto.deploy.DeployResponse;

public interface DeploymentService {

    DeployResponse deploy(Long projectId);
}
