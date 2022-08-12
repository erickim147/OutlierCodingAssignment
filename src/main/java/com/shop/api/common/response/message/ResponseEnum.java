package com.shop.api.common.response.message;

import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
public enum ResponseEnum {

    // 로그인
    LOGIN_SUCCESS(HttpStatus.OK, "L0000","로그인 성공"), // OK
    NOT_FOUND_USER_ID(HttpStatus.BAD_REQUEST, "LE100", "아이디를 찾을 수 없습니다."), // OK
    NOT_MATCH_PASSWORD(HttpStatus.BAD_REQUEST, "LE101", "비밀번호가 일치 하지 않습니다."), // OK

    // 회원가입
    CREATED_USER(HttpStatus.CREATED, "UC000","회원 가입 성공"),   // OK
    //NOT_EMPTY_VALUE(HttpStatus.BAD_REQUEST, "UE100", "요청 파라미터에 빈 값이 있습니다."),
    SAME_USER_ID(HttpStatus.BAD_REQUEST, "UE101", "이미 가입된 ID 입니다."), // OK
    NOT_BLANK_ID(HttpStatus.BAD_REQUEST, "UE102", "ID를 입력 해주세요."), // OK
    NOT_BLANK_PASSWORD(HttpStatus.BAD_REQUEST, "UE103","비밀번호를 입력 해주세요."), // OK
    NOT_CORRECT_PASSWORD_FORMAT(HttpStatus.BAD_REQUEST, "UE104", "비밀번호는 영문 대,소문자와 숫자, 특수기호가 적어도 1개 이상씩 포함된 8자 ~ 20자의 비밀번호여야 합니다."), // OK
    NOT_BLANK_NAME(HttpStatus.BAD_REQUEST, "UE105", "이름을 입력 해주세요."), // OK
    NOT_BLANK_PHONENUM(HttpStatus.BAD_REQUEST, "UE106", "휴대폰 번호를 입력 해주세요."), // OK
    NOT_BLANK_EAIL(HttpStatus.BAD_REQUEST, "UE107", "이메일을 입력 해주세요."), // OK
    NOT_CORRECT_EMAIL_FORMAT(HttpStatus.BAD_REQUEST, "UE108", "이메일 형식에 맞지 않습니다."), // OK
    NOT_BLANK_ADDRESS(HttpStatus.BAD_REQUEST, "UE109", "주소를 입력 해주세요."), // OK
    NOT_BLANK_BIRTH(HttpStatus.BAD_REQUEST, "UE110","생년월일을 입력 해주세요."), // OK

    // 회원조회 및 수정 삭제
    READ_USER(HttpStatus.OK, "UR000", "회원 조회 성공"),  // OK
    READ_ALL_USER(HttpStatus.OK, "UR001","회원 전체 조회 성공"), // OK
    UPDATE_USER(HttpStatus.OK, "UU000","회원 정보 수정 성공"),
    DELETE_USER(HttpStatus.OK, "UD000","회원 탈퇴 성공"),

    ACCESS_DENIED(HttpStatus.FORBIDDEN, "AE100", "접근 권한이 없습니다."), // OK
    REQUEST_PARAM_ERROR(HttpStatus.BAD_REQUEST, "AE101","요청 파라미터 형식이 잘못 되었습니다."), // OK
    REQUEST_MISSING_ERROR(HttpStatus.BAD_REQUEST, "AE102","요청 파라미터가 누락 되었습니다."), //OK

    // 서버 Error
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "E0003", "서버 내부 Error로 관리자에게 문의 바랍니다."), //OK

    // Common
    READ_OK(HttpStatus.OK, "S0000", "조회 완료");

    private HttpStatus status;
    private String code;
    private String message;


    ResponseEnum(String message) {
        this.message = message;
    }

    ResponseEnum(HttpStatus status, String code) {
        this.status = status;
        this.code = code;
    }

    ResponseEnum(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
