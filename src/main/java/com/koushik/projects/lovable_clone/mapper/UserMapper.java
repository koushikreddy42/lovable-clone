package com.koushik.projects.lovable_clone.mapper;

import com.koushik.projects.lovable_clone.dto.auth.SignUpRequest;
import com.koushik.projects.lovable_clone.dto.auth.UserProfileResponse;
import com.koushik.projects.lovable_clone.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(SignUpRequest signUpRequest);

    UserProfileResponse toUserProfileResponse(User user);
}
