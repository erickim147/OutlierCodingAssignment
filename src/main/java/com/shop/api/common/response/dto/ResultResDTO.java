package com.shop.api.common.response.dto;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Builder
public class ResultResDTO {
    private final LocalDateTime timestamp = LocalDateTime.now();
    private String code;
    private HttpStatus httpStatus;
    private String message;
    private Integer count;
    private Object data;

}
