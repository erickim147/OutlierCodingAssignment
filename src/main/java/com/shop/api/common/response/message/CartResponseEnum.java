package com.shop.api.common.response.message;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
public enum CartResponseEnum {

    CREATE_CART(HttpStatus.OK, "CART_000", "장바구니 등록 완료"), // OK
    READ_ALL_CART(HttpStatus.OK, "CART_001", "장바구니 전체 조회 완료"), // OK
    DELETE_CART_SUCCESS(HttpStatus.OK, "CART_002", "장바구니 상품 삭제 완료"), // OK
    DELETE_ALL_CART_SUCCESS(HttpStatus.OK, "CART_003", "장바구니 상품 전체 삭제 완료"), // OK

    NOT_NULL_PRODUCT_ID(HttpStatus.BAD_REQUEST, "CART_E100", "상품 ID값이 없습니다."), // OK
    NOT_NULL_AMOUNT(HttpStatus.BAD_REQUEST, "CART_E101", "상품 수량 값이 없습니다."), // OK
    AMOUNT_NOT_ZERO(HttpStatus.BAD_REQUEST, "CART_E102", "상품 수량 값이 없습니다."), // OK
    NOT_FOUND_PRODUCT_ID(HttpStatus.BAD_REQUEST, "CART_E103", "상품을 찾을 수 없습니다."), // OK
    NOT_FOUND_MEMBER_ID(HttpStatus.BAD_REQUEST, "CART_E104", "장바구니가 비었습니다."), // OK
    ALREADY_CART(HttpStatus.BAD_REQUEST, "CART_E105", "해당 상품이 이미 장바구니에 있습니다."); // OK


    private HttpStatus status;
    private String code;
    private String message;

    CartResponseEnum(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
