package com.devteria.project1.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, " EXCEPTION ERROR (loi)"),
    INVALID_KEY(1001, "Uncategorized error"),
    USER_EXISTED(1002, " USER ERROR (loi)"),
    USERNAME_INVALID(1003, "Username must be at least {min} characters"),
    INVALID_PASSWORD(1004, "Password must be at least {min} characters"),
    USER_DELETED(1005, "User deleted"),
    USER_NOT_EXISTED(1005, "User not existed"),
    UNAUTHENTICATED(1006, "Unauthenticated")
    ;

    private final int code;
    private final String message;


    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
