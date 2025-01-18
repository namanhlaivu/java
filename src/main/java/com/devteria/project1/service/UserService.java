package com.devteria.project1.service;

import com.devteria.project1.dto.request.UserCreationRequest;
import com.devteria.project1.dto.request.UserUpdateRequest;
import com.devteria.project1.dto.response.UserResponse;
import com.devteria.project1.entity.User;
import com.devteria.project1.enums.Role;
import com.devteria.project1.exception.AppExcaption;
import com.devteria.project1.exception.ErrorCode;
import com.devteria.project1.mapper.UserMapper;
import com.devteria.project1.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {
     UserRepository userRepository;
     UserMapper userMapper;
     PasswordEncoder passwordEncoder;


    public User createUesr(UserCreationRequest request) {

        if(userRepository.existsByUsername(request.getUsername()))
            throw new AppExcaption(ErrorCode.USER_EXISTED);

        User user = userMapper.toUser(request);

        user.setPassword( passwordEncoder.encode(request.getPassword()));

        HashSet<String> roles = new HashSet<>();
        roles.add(Role.USER.name());
        user.setRoles(roles);


        return userRepository.save(user);

    }
    public  UserResponse updateUser(String userId, UserUpdateRequest request) {
        User user= userRepository.findById(userId).
                orElseThrow(()-> new RuntimeException("k thay ID"));
        
        userMapper.updateUser(user,request);
        return userMapper.toUserResponse(userRepository.save(user));
    }

    public void deleteUser(String userId) {
        if (!userRepository.existsById(userId)) {
            throw new AppExcaption(ErrorCode.USER_DELETED);
        }
        userRepository.deleteById(userId);
    }


public List <User> getAllUsers() {
        return userRepository.findAll();
}

// get ID
    public UserResponse getUser(String id){
        return userMapper.toUserResponse( userRepository.findById(id).
                orElseThrow(()-> new RuntimeException("k thay ID")));
    }
}
