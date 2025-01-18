package com.devteria.project1.controller;


import com.devteria.project1.dto.request.ApiResponse;
import com.devteria.project1.dto.request.UserCreationRequest;
import com.devteria.project1.dto.request.UserUpdateRequest;
import com.devteria.project1.dto.response.UserResponse;
import com.devteria.project1.entity.User;
import com.devteria.project1.service.UserService;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/user")//đẩy API lên
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)

public class UserController {
     UserService userService;
     // Tạo lên AIP
    @PostMapping
    ApiResponse<User> createUser(@RequestBody @Valid UserCreationRequest request) {

        ApiResponse<User> apiRequest = new ApiResponse<>();
        apiRequest.setCode(1000);
        apiRequest.setResult(userService.createUesr(request));
        return apiRequest;
    }
    //Gọi API ALL
    @GetMapping
    List<User> getAllUsers() {
       var authenticatiom = SecurityContextHolder.getContext().getAuthentication();
       log.info("Username :{}", authenticatiom.getName());
       authenticatiom.getAuthorities().forEach(grantedAuthority -> log.info(grantedAuthority.getAuthority()));
    return userService.getAllUsers();
    }
    //Gọi từng API theo ID
    @GetMapping("/{userId}")
    UserResponse getUser(@PathVariable("userId") String userId) {
    return userService.getUser(userId);
    }
    //SỬa API theo ID
    @PutMapping("/{userId}")
    UserResponse updateUser(@RequestBody UserUpdateRequest request, @PathVariable("userId") String userId) {
     return userService.updateUser(userId, request);
    }
    //Xóa API theo ID
    @DeleteMapping("/{userId}")
    public ApiResponse<String> deleteUser(@PathVariable("userId") String userId) {
        userService.deleteUser(userId); // Gọi phương thức trong service
        return ApiResponse.<String>builder().result("User has been deleted").build();
    }

}

