package com.shop.api.common.advice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.shop.api.common.CustomLocalDateTimeSerializer;
import com.shop.api.common.response.dto.ErrorResDTO;
import com.shop.api.common.response.message.ResponseEnum;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

@Component
@Getter
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        ErrorResDTO errorResDTO = ErrorResDTO.builder()
                .errorCode(ResponseEnum.ACCESS_DENIED.getCode())
                .httpStatus(ResponseEnum.ACCESS_DENIED.getStatus())
                .errorMessage(ResponseEnum.ACCESS_DENIED.getMessage())
                .build();
        response.getWriter().print(convertObjectToJson(errorResDTO));
    }

    public String convertObjectToJson(Object object) throws JsonProcessingException {
        if (object == null) {
            return null;
        }
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(LocalDateTime.class, new CustomLocalDateTimeSerializer());

        mapper.registerModule(simpleModule);
        return mapper.writeValueAsString(object);
    }
}
