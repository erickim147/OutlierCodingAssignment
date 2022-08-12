package com.shop.api.common.response.message;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum SellerResponseEnum {

    CREATE_SELLER(HttpStatus.OK, "SC000", "판매자 등록 완료"), // OK

    NOT_BLANK_SHOP_NAME(HttpStatus.BAD_REQUEST, "SCE100", "SHOP 이름값이 없습니다."), // OK
    NOT_BLANK_SHOP_ADDRESS(HttpStatus.BAD_REQUEST, "SCE101", "SHOP 주소값이 없습니다."), // OK
    NOT_BLANK_SHOP_TELNUM(HttpStatus.BAD_REQUEST, "SCE102", "SHOP 전화번호 값이 없습니다."), // OK
    ALREADY_SELLER(HttpStatus.BAD_REQUEST, "SCE102", "이미 등록된 판매자 입니다."); // OK

    private HttpStatus status;
    private String code;
    private String message;

    SellerResponseEnum(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
