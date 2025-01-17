package com.devteria.project1.mapper;

import com.devteria.project1.dto.request.UserCreationRequest;
import com.devteria.project1.dto.request.UserUpdateRequest;
import com.devteria.project1.dto.response.UserResponse;
import com.devteria.project1.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;


@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreationRequest request);
    
    @Mapping(target = "lastName")
    UserResponse toUserResponse(User user);
    void updateUser(@MappingTarget User user, UserUpdateRequest request);

}
