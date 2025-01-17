package com.devteria.project1.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults ( level = AccessLevel.PRIVATE)
@JsonInclude ( value = JsonInclude.Include.NON_NULL )
public class ApiResponse <T>{
    private int code;
    private String message;
    private T result;

}
