package com.devteria.project1.controller;

import com.devteria.project1.dto.request.ApiResponse;
import com.devteria.project1.dto.request.AuthenticationRequest;
import com.devteria.project1.dto.request.IntrospectRequest;
import com.devteria.project1.dto.response.AuthenticationResponse;
import com.devteria.project1.dto.response.IntrospectResponse;
import com.devteria.project1.service.AuthenticationService;
import com.nimbusds.jose.JOSEException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PACKAGE, makeFinal = true)

public class AuthenticationController {

    AuthenticationService authenticationService;

    @PostMapping("/token")
    ApiResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest repuest) {
        var result=   authenticationService.authenticate(repuest);
        return ApiResponse.<AuthenticationResponse>builder()
                .result(result)
                .build();   
    }
    @PostMapping("/introspect")
    ApiResponse<IntrospectResponse> authenticate(@RequestBody IntrospectRequest repuest)
            throws ParseException, JOSEException {
        var result=   authenticationService.introspect(repuest);
        return ApiResponse.<IntrospectResponse>builder()
                .result(result)
                .build();
    }
}
