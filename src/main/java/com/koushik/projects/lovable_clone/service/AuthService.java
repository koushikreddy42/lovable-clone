package com.koushik.projects.lovable_clone.service;

import com.koushik.projects.lovable_clone.dto.auth.AuthResponse;
import com.koushik.projects.lovable_clone.dto.auth.LoginRequest;
import com.koushik.projects.lovable_clone.dto.auth.SignUpRequest;

public interface AuthService {
    AuthResponse signUp(SignUpRequest request);

    AuthResponse login(LoginRequest request);
}
