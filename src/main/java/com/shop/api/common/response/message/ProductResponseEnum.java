package com.shop.api.common.response.message;

import lombok.Getter;
import org.springframework.http.HttpStatus;
@Getter
public enum ProductResponseEnum {

    CREATE_PRODUCT(HttpStatus.OK, "PC000", "상품 등록 완료"), // OK
    FIND_PRODUCT_SUCCESS(HttpStatus.OK, "PC001","상품 조회 완료"), // OK

    NOT_BLANK_LARGE_CAT(HttpStatus.BAD_REQUEST, "PCE100", "1차 카테고리 값이 없습니다."), // OK
    NOT_BLANK_MIDDLE_CAT(HttpStatus.BAD_REQUEST, "PCE101", "2차 카테고리 값이 없습니다."),// OK
    NOT_BLANK_SUB_CAT(HttpStatus.BAD_REQUEST, "PCE102", "3차 카테고리 값이 없습니다."),// OK
    NOT_BLANK_PD_NAME(HttpStatus.BAD_REQUEST, "PCE103", "상품 제목 값이 없습니다."),// OK
    NOT_BLANK_PD_PRICE(HttpStatus.BAD_REQUEST, "PCE104", "상품 가격 값이 없습니다."),// OK
    NOT_BLANK_PD_DISCOUNT(HttpStatus.BAD_REQUEST, "PCE105", "상품 할인가 값이 없습니다."),// OK
    NOT_BLANK_SET_SALE_PERIOD(HttpStatus.BAD_REQUEST, "PCE106", "상품 판매기간 설정 여부 값이 없습니다."),// OK
    NOT_BLANK_PD_SALE_PERIOD(HttpStatus.BAD_REQUEST, "PCE107", "상품 판매기간 값이 없습니다."),// OK
    NOT_BLANK_PD_STOCK(HttpStatus.BAD_REQUEST, "PCE108", "상품 재고 값이 없습니다."),// OK
    NOT_BLANK_PD_DESC(HttpStatus.BAD_REQUEST, "PCE109", "상품 설명 값이 없습니다."),// OK
    NOT_BLANK_PD_DELIVERY_CHARGE(HttpStatus.BAD_REQUEST, "PCE110", "상품 배송비 값이 없습니다."),// OK

    NOT_MATCH_LARGE_CATEGORY_NAME(HttpStatus.BAD_REQUEST, "PCE111", "1차 카테고리 값에 일치하는 값이 없습니다."),// OK
    NOT_MATCH_MIDDLE_CATEGORY_NAME(HttpStatus.BAD_REQUEST, "PCE112", "2차 카테고리 값에 일치하는 값이 없습니다."),// OK
    NOT_MATCH_SUB_CATEGORY_NAME(HttpStatus.BAD_REQUEST, "PCE113", "3차 카테고리 값에 일치하는 값이 없습니다.");// OK

    //NOT_FOUND_SEARCH_CATEGORY(HttpStatus.BAD_REQUEST, "PCE114", "검색 카테고리가 없습니다.");

    private HttpStatus status;
    private String code;
    private String message;

    ProductResponseEnum(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
