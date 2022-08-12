package com.shop.api.common.response.message;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum SearchResponseEnum {


    SEARCH_SUCCESS(HttpStatus.OK, "SEARCH_000", "상품 검색 완료"), // OK

    NOT_FOUNT_CAT_SEARCH_PARAM(HttpStatus.BAD_REQUEST, "SEARCH_E1000", "카테고리 검색의 요청 파라미터가 없습니다."); // OK







    private HttpStatus status;
    private String code;
    private String message;

    SearchResponseEnum(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
