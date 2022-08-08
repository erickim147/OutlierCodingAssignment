package com.shop.api.common.response.message;

import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@ToString
public enum TokenResponseEnum {

    CREATE_REFRESH_TOKEN(HttpStatus.OK, "TC000", "REFRESH TOKEN으로 신규 ACCESS TOKEN이 생성 되었습니다."), //OK

    UNSUPPORTED_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "TE100", "지원하지 않는 ACCESS TOKEN 입니다."),
    MALFORMED_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "TE101", "유효하지 않은 ACCESS TOKEN 입니다."), //OK
    EXPIRED_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "TE102", "만료된 ACCESS TOKEN 입니다."), //OK
    SIGNATURE_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "TE103", "잘못된 서명의 ACCESS TOKEN 입니다."), //OK
    ILLEGAL_ARGUMENT_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "TE104", "지원하지 않는 ACCESS TOKEN 입니다."),
    NOT_FOUND_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "TE105", "ACCESS TOKEN을 찾을 수 없습니다."), //OK

    REFRESH_TOKEN_EXCEPTION(HttpStatus.UNAUTHORIZED, "TE106", "REFRESH TOKEN이 유효하지 않습니다. 다시 로그인 해주세요."); //OK

    private HttpStatus status;
    private String code;
    private String message;

    TokenResponseEnum(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
