package com.devteria.project1.exception;



import com.devteria.project1.dto.request.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    private com.devteria.project1.dto.request.ApiResponse ApiRequest;

    @ExceptionHandler(value = RuntimeException.class)
    ResponseEntity<ApiResponse> handlingRuntimeException(RuntimeException exception) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setCode(ErrorCode.UNCATEGORIZED_EXCEPTION.getCode()); // Đặt mã lỗi
        apiResponse.setMessage(ErrorCode.UNCATEGORIZED_EXCEPTION.getMessage()); // Gán thông báo lỗi
        return ResponseEntity.badRequest().body(apiResponse);
    }

    @ExceptionHandler(value = AppExcaption.class)
    ResponseEntity<ApiResponse> handlingAppException(AppExcaption exception) {

        ErrorCode errorCode = exception.getErrorCode();
        ApiResponse apiResponse = new ApiResponse();

        apiResponse.setCode(errorCode.getCode()); // Đặt mã lỗi
        apiResponse.setMessage(errorCode.getMessage()); // Gán thông báo lỗi
        return ResponseEntity.badRequest().body(apiResponse);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<ApiResponse> handlingValidation(MethodArgumentNotValidException exception) {
            String enumKey = exception.getFieldError().getDefaultMessage();
            ErrorCode errorCode = ErrorCode.INVALID_KEY;
            try {
            errorCode = ErrorCode.valueOf(enumKey);
            }catch (IllegalArgumentException e) {

            }
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setCode(errorCode.getCode()); // Đặt mã lỗi
        apiResponse.setMessage(errorCode.getMessage()); // Gán thông báo lỗi
        return ResponseEntity.badRequest().body(apiResponse);
    }
}
