package com.shop.api.common.advice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.shop.api.common.CustomLocalDateTimeSerializer;
import com.shop.api.common.response.dto.ErrorResDTO;
import com.shop.api.common.response.message.ResponseEnum;
import com.shop.api.common.response.message.TokenResponseEnum;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

@Component
@Getter
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        String errMsg = (String) request.getAttribute("errMsg");

        String errorCode;
        HttpStatus httpStatus;
        String errorMessage;

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        if(errMsg == null){
            errorCode = TokenResponseEnum.NOT_FOUND_REFRESH_TOKEN.getCode();
            httpStatus = TokenResponseEnum.NOT_FOUND_REFRESH_TOKEN.getStatus();
            errorMessage = TokenResponseEnum.NOT_FOUND_REFRESH_TOKEN.getMessage();
        }  else {
            switch (errMsg){
                case "TE100":
                    errorCode = TokenResponseEnum.UNSUPPORTED_REFRESH_TOKEN.getCode();
                    httpStatus = TokenResponseEnum.UNSUPPORTED_REFRESH_TOKEN.getStatus();
                    errorMessage = TokenResponseEnum.UNSUPPORTED_REFRESH_TOKEN.getMessage();
                    break;
                case "TE101":
                    errorCode = TokenResponseEnum.MALFORMED_REFRESH_TOKEN.getCode();
                    httpStatus = TokenResponseEnum.MALFORMED_REFRESH_TOKEN.getStatus();
                    errorMessage = TokenResponseEnum.MALFORMED_REFRESH_TOKEN.getMessage();
                    break;
                case "TE102":
                    errorCode = TokenResponseEnum.EXPIRED_REFRESH_TOKEN.getCode();
                    httpStatus = TokenResponseEnum.EXPIRED_REFRESH_TOKEN.getStatus();
                    errorMessage = TokenResponseEnum.EXPIRED_REFRESH_TOKEN.getMessage();
                    break;
                case "TE103":
                    errorCode = TokenResponseEnum.SIGNATURE_REFRESH_TOKEN.getCode();
                    httpStatus = TokenResponseEnum.SIGNATURE_REFRESH_TOKEN.getStatus();
                    errorMessage = TokenResponseEnum.SIGNATURE_REFRESH_TOKEN.getMessage();
                    break;
                case "TE104":
                    errorCode = TokenResponseEnum.ILLEGAL_ARGUMENT_REFRESH_TOKEN.getCode();
                    httpStatus = TokenResponseEnum.ILLEGAL_ARGUMENT_REFRESH_TOKEN.getStatus();
                    errorMessage = TokenResponseEnum.ILLEGAL_ARGUMENT_REFRESH_TOKEN.getMessage();
                    break;
                case "TE105":
                    errorCode = TokenResponseEnum.NOT_FOUND_REFRESH_TOKEN.getCode();
                    httpStatus = TokenResponseEnum.NOT_FOUND_REFRESH_TOKEN.getStatus();
                    errorMessage = TokenResponseEnum.NOT_FOUND_REFRESH_TOKEN.getMessage();
                    break;
                default:
                    errorCode = ResponseEnum.INTERNAL_SERVER_ERROR.getCode();
                    httpStatus = ResponseEnum.INTERNAL_SERVER_ERROR.getStatus();
                    errorMessage = ResponseEnum.INTERNAL_SERVER_ERROR.getMessage();
            }
        }

        ErrorResDTO errorResDTO = ErrorResDTO.builder()
                .errorCode(errorCode)
                .httpStatus(httpStatus)
                .errorMessage(errorMessage)
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
