package com.shop.api.common.response.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Builder
public class ErrorResDTO {
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private final LocalDateTime localDateTime = LocalDateTime.now();
    private String errorCode;
    private HttpStatus httpStatus;
    private String errorMessage;
}
