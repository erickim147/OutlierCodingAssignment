package com.shop.api.common.response.message;

import lombok.Getter;
import org.springframework.http.HttpStatus;
@Getter
public enum OrderResponseEnum {


    ORDER_SUCCESS(HttpStatus.OK, "ORDER_000", "구매 완료"), // OK
    SUCCESS_READ_ORDER(HttpStatus.OK, "ORDER_001", "구매 상품 조회완료"), // OK

    NOT_FOUNT_CART(HttpStatus.BAD_REQUEST, "ORDER_E100", "장바구니 정보를 찾을 수 없습니다."), // OK

    NOT_FOUNT_ORDER(HttpStatus.BAD_REQUEST, "ORDER_E101", "구매 이력이 없습니다."); // OK






    private HttpStatus status;
    private String code;
    private String message;

    OrderResponseEnum(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
