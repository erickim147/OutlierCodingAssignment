package com.shop.api.common.response.message;

import lombok.Getter;
import org.springframework.http.HttpStatus;
@Getter
public enum OrderResponseEnum {


    ORDER_SUCCESS(HttpStatus.OK, "ORDER_000", "구매 완료"),

    NOT_FOUNT_CART(HttpStatus.OK, "ORDER_E100", "장바구니 정보를 찾을 수 없습니다.");






    private HttpStatus status;
    private String code;
    private String message;

    OrderResponseEnum(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
