package com.shop.api.common.advice;

import com.shop.api.common.response.dto.ErrorResDTO;
import com.shop.api.common.response.message.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionAdvice {
    String errorCode;
    HttpStatus httpStatus;
    String errorMessage;

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> illegalArgumentException(IllegalArgumentException e) {
        switch (e.getMessage()){
            case "NOT_MATCH_PASSWORD":
                errorCode = ResponseEnum.NOT_MATCH_PASSWORD.getCode();
                httpStatus = ResponseEnum.NOT_MATCH_PASSWORD.getStatus();
                errorMessage = ResponseEnum.NOT_MATCH_PASSWORD.getMessage();
                break;
            case "AE100":
                errorCode = ResponseEnum.ACCESS_DENIED.getCode();
                httpStatus = ResponseEnum.ACCESS_DENIED.getStatus();
                errorMessage = ResponseEnum.ACCESS_DENIED.getMessage();
                break;
            case "SCE103":
                errorCode = SellerResponseEnum.ALREADY_SELLER.getCode();
                httpStatus = SellerResponseEnum.ALREADY_SELLER.getStatus();
                errorMessage = SellerResponseEnum.ALREADY_SELLER.getMessage();
                break;
            case "PCE111":
                errorCode = ProductResponseEnum.NOT_MATCH_LARGE_CATEGORY_NAME.getCode();
                httpStatus = ProductResponseEnum.NOT_MATCH_LARGE_CATEGORY_NAME.getStatus();
                errorMessage = ProductResponseEnum.NOT_MATCH_LARGE_CATEGORY_NAME.getMessage();
                break;
            case "PCE112":
                errorCode = ProductResponseEnum.NOT_MATCH_MIDDLE_CATEGORY_NAME.getCode();
                httpStatus = ProductResponseEnum.NOT_MATCH_MIDDLE_CATEGORY_NAME.getStatus();
                errorMessage = ProductResponseEnum.NOT_MATCH_MIDDLE_CATEGORY_NAME.getMessage();
                break;
            case "PCE113":
                errorCode = ProductResponseEnum.NOT_MATCH_SUB_CATEGORY_NAME.getCode();
                httpStatus = ProductResponseEnum.NOT_MATCH_SUB_CATEGORY_NAME.getStatus();
                errorMessage = ProductResponseEnum.NOT_MATCH_SUB_CATEGORY_NAME.getMessage();
                break;
            case "CART_E103":
                errorCode = CartResponseEnum.NOT_FOUND_PRODUCT_ID.getCode();
                httpStatus = CartResponseEnum.NOT_FOUND_PRODUCT_ID.getStatus();
                errorMessage = CartResponseEnum.NOT_FOUND_PRODUCT_ID.getMessage();
                break;
            case "CART_E104":
                errorCode = CartResponseEnum.NOT_FOUND_MEMBER_ID.getCode();
                httpStatus = CartResponseEnum.NOT_FOUND_MEMBER_ID.getStatus();
                errorMessage = CartResponseEnum.NOT_FOUND_MEMBER_ID.getMessage();
                break;
            case "CART_E105":
                errorCode = CartResponseEnum.ALREADY_CART.getCode();
                httpStatus = CartResponseEnum.ALREADY_CART.getStatus();
                errorMessage = CartResponseEnum.ALREADY_CART.getMessage();
                break;
            case "ORDER_E100":
                errorCode = OrderResponseEnum.NOT_FOUNT_CART.getCode();
                httpStatus = OrderResponseEnum.NOT_FOUNT_CART.getStatus();
                errorMessage = OrderResponseEnum.NOT_FOUNT_CART.getMessage();
                break;
            case "SEARCH_E1000":
                errorCode = SearchResponseEnum.NOT_FOUNT_CAT_SEARCH_PARAM.getCode();
                httpStatus = SearchResponseEnum.NOT_FOUNT_CAT_SEARCH_PARAM.getStatus();
                errorMessage = SearchResponseEnum.NOT_FOUNT_CAT_SEARCH_PARAM.getMessage();
                break;
            case "ORDER_E101":
                errorCode = OrderResponseEnum.NOT_FOUNT_ORDER.getCode();
                httpStatus = OrderResponseEnum.NOT_FOUNT_ORDER.getStatus();
                errorMessage = OrderResponseEnum.NOT_FOUNT_ORDER.getMessage();
                break;
            default:
                errorCode = ResponseEnum.INTERNAL_SERVER_ERROR.getCode();
                httpStatus = ResponseEnum.INTERNAL_SERVER_ERROR.getStatus();
                errorMessage = ResponseEnum.INTERNAL_SERVER_ERROR.getMessage();
        }
        ErrorResDTO errorResDTO = ErrorResDTO.builder()
                .errorCode(errorCode)
                .httpStatus(httpStatus)
                .errorMessage(errorMessage)
                .build();
        return new ResponseEntity<>(errorResDTO, errorResDTO.getHttpStatus());
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<?> nullPointerException(NullPointerException e){
        switch (e.getMessage()){
            case "NOT_FOUND_USER_ID":
                errorCode = ResponseEnum.NOT_FOUND_USER_ID.getCode();
                httpStatus = ResponseEnum.NOT_FOUND_USER_ID.getStatus();
                errorMessage = ResponseEnum.NOT_FOUND_USER_ID.getMessage();
                break;
            default:
                errorCode = ResponseEnum.INTERNAL_SERVER_ERROR.getCode();
                httpStatus = ResponseEnum.INTERNAL_SERVER_ERROR.getStatus();
                errorMessage = ResponseEnum.INTERNAL_SERVER_ERROR.getMessage();
        }
        ErrorResDTO errorResDTO = ErrorResDTO.builder()
                .errorCode(errorCode)
                .httpStatus(httpStatus)
                .errorMessage(errorMessage)
                .build();
        return new ResponseEntity<>(errorResDTO, errorResDTO.getHttpStatus());
    }

    // Validation Error
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> methodArgumentNotValidException(MethodArgumentNotValidException e){

        switch (e.getBindingResult().getAllErrors().get(0).getDefaultMessage()){
            //Member Create Validation Error
            case "UE102":
                errorCode = ResponseEnum.NOT_BLANK_ID.getCode();
                httpStatus = ResponseEnum.NOT_BLANK_ID.getStatus();
                errorMessage = ResponseEnum.NOT_BLANK_ID.getMessage();
                break;
            case "UE103":
                errorCode = ResponseEnum.NOT_BLANK_PASSWORD.getCode();
                httpStatus = ResponseEnum.NOT_BLANK_PASSWORD.getStatus();
                errorMessage = ResponseEnum.NOT_BLANK_PASSWORD.getMessage();
                break;
            case "UE104":
                errorCode = ResponseEnum.NOT_CORRECT_PASSWORD_FORMAT.getCode();
                httpStatus = ResponseEnum.NOT_CORRECT_PASSWORD_FORMAT.getStatus();
                errorMessage = ResponseEnum.NOT_CORRECT_PASSWORD_FORMAT.getMessage();
                break;
            case "UE105":
                errorCode = ResponseEnum.NOT_BLANK_NAME.getCode();
                httpStatus = ResponseEnum.NOT_BLANK_NAME.getStatus();
                errorMessage = ResponseEnum.NOT_BLANK_NAME.getMessage();
                break;
            case "UE106":
                errorCode = ResponseEnum.NOT_BLANK_PHONENUM.getCode();
                httpStatus = ResponseEnum.NOT_BLANK_PHONENUM.getStatus();
                errorMessage = ResponseEnum.NOT_BLANK_PHONENUM.getMessage();
                break;
            case "UE107":
                errorCode = ResponseEnum.NOT_BLANK_EAIL.getCode();
                httpStatus = ResponseEnum.NOT_BLANK_EAIL.getStatus();
                errorMessage = ResponseEnum.NOT_BLANK_EAIL.getMessage();
                break;
            case "UE108":
                errorCode = ResponseEnum.NOT_CORRECT_EMAIL_FORMAT.getCode();
                httpStatus = ResponseEnum.NOT_CORRECT_EMAIL_FORMAT.getStatus();
                errorMessage = ResponseEnum.NOT_CORRECT_EMAIL_FORMAT.getMessage();
                break;
            case "UE109":
                errorCode = ResponseEnum.NOT_BLANK_ADDRESS.getCode();
                httpStatus = ResponseEnum.NOT_BLANK_ADDRESS.getStatus();
                errorMessage = ResponseEnum.NOT_BLANK_ADDRESS.getMessage();
                break;
            case "UE110":
                errorCode = ResponseEnum.NOT_BLANK_BIRTH.getCode();
                httpStatus = ResponseEnum.NOT_BLANK_BIRTH.getStatus();
                errorMessage = ResponseEnum.NOT_BLANK_BIRTH.getMessage();
                break;
            //Seller Create Validation Error
            case "SCE100":
                errorCode = SellerResponseEnum.NOT_BLANK_SHOP_NAME.getCode();
                httpStatus = SellerResponseEnum.NOT_BLANK_SHOP_NAME.getStatus();
                errorMessage = SellerResponseEnum.NOT_BLANK_SHOP_NAME.getMessage();
                break;
            case "SCE101":
                errorCode = SellerResponseEnum.NOT_BLANK_SHOP_ADDRESS.getCode();
                httpStatus = SellerResponseEnum.NOT_BLANK_SHOP_ADDRESS.getStatus();
                errorMessage = SellerResponseEnum.NOT_BLANK_SHOP_ADDRESS.getMessage();
                break;
            case "SCE102":
                errorCode = SellerResponseEnum.NOT_BLANK_SHOP_TELNUM.getCode();
                httpStatus = SellerResponseEnum.NOT_BLANK_SHOP_TELNUM.getStatus();
                errorMessage = SellerResponseEnum.NOT_BLANK_SHOP_TELNUM.getMessage();
                break;
            //Product Create Validation Error
            case "PCE100":
                errorCode = ProductResponseEnum.NOT_BLANK_LARGE_CAT.getCode();
                httpStatus = ProductResponseEnum.NOT_BLANK_LARGE_CAT.getStatus();
                errorMessage = ProductResponseEnum.NOT_BLANK_LARGE_CAT.getMessage();
                break;
            case "PCE101":
                errorCode = ProductResponseEnum.NOT_BLANK_MIDDLE_CAT.getCode();
                httpStatus = ProductResponseEnum.NOT_BLANK_MIDDLE_CAT.getStatus();
                errorMessage = ProductResponseEnum.NOT_BLANK_MIDDLE_CAT.getMessage();
                break;
            case "PCE102":
                errorCode = ProductResponseEnum.NOT_BLANK_SUB_CAT.getCode();
                httpStatus = ProductResponseEnum.NOT_BLANK_SUB_CAT.getStatus();
                errorMessage = ProductResponseEnum.NOT_BLANK_SUB_CAT.getMessage();
                break;
            case "PCE103":
                errorCode = ProductResponseEnum.NOT_BLANK_PD_NAME.getCode();
                httpStatus = ProductResponseEnum.NOT_BLANK_PD_NAME.getStatus();
                errorMessage = ProductResponseEnum.NOT_BLANK_PD_NAME.getMessage();
                break;
            case "PCE104":
                errorCode = ProductResponseEnum.NOT_BLANK_PD_PRICE.getCode();
                httpStatus = ProductResponseEnum.NOT_BLANK_PD_PRICE.getStatus();
                errorMessage = ProductResponseEnum.NOT_BLANK_PD_PRICE.getMessage();
                break;
            case "PCE105":
                errorCode = ProductResponseEnum.NOT_BLANK_PD_DISCOUNT.getCode();
                httpStatus = ProductResponseEnum.NOT_BLANK_PD_DISCOUNT.getStatus();
                errorMessage = ProductResponseEnum.NOT_BLANK_PD_DISCOUNT.getMessage();
                break;
            case "PCE106":
                errorCode = ProductResponseEnum.NOT_BLANK_SET_SALE_PERIOD.getCode();
                httpStatus = ProductResponseEnum.NOT_BLANK_SET_SALE_PERIOD.getStatus();
                errorMessage = ProductResponseEnum.NOT_BLANK_SET_SALE_PERIOD.getMessage();
                break;
            case "PCE107":
                errorCode = ProductResponseEnum.NOT_BLANK_PD_SALE_PERIOD.getCode();
                httpStatus = ProductResponseEnum.NOT_BLANK_PD_SALE_PERIOD.getStatus();
                errorMessage = ProductResponseEnum.NOT_BLANK_PD_SALE_PERIOD.getMessage();
                break;
            case "PCE108":
                errorCode = ProductResponseEnum.NOT_BLANK_PD_STOCK.getCode();
                httpStatus = ProductResponseEnum.NOT_BLANK_PD_STOCK.getStatus();
                errorMessage = ProductResponseEnum.NOT_BLANK_PD_STOCK.getMessage();
                break;
            case "PCE109":
                errorCode = ProductResponseEnum.NOT_BLANK_PD_DESC.getCode();
                httpStatus = ProductResponseEnum.NOT_BLANK_PD_DESC.getStatus();
                errorMessage = ProductResponseEnum.NOT_BLANK_PD_DESC.getMessage();
                break;
            case "PCE110":
                errorCode = ProductResponseEnum.NOT_BLANK_PD_DELIVERY_CHARGE.getCode();
                httpStatus = ProductResponseEnum.NOT_BLANK_PD_DELIVERY_CHARGE.getStatus();
                errorMessage = ProductResponseEnum.NOT_BLANK_PD_DELIVERY_CHARGE.getMessage();
                break;
            //Cart Create Validation Error
            case "CART_E100":
                errorCode = CartResponseEnum.NOT_NULL_PRODUCT_ID.getCode();
                httpStatus = CartResponseEnum.NOT_NULL_PRODUCT_ID.getStatus();
                errorMessage = CartResponseEnum.NOT_NULL_PRODUCT_ID.getMessage();
                break;
            case "CART_E101":
                errorCode = CartResponseEnum.NOT_NULL_AMOUNT.getCode();
                httpStatus = CartResponseEnum.NOT_NULL_AMOUNT.getStatus();
                errorMessage = CartResponseEnum.NOT_NULL_AMOUNT.getMessage();
                break;
            case "CART_E102":
                errorCode = CartResponseEnum.AMOUNT_NOT_ZERO.getCode();
                httpStatus = CartResponseEnum.AMOUNT_NOT_ZERO.getStatus();
                errorMessage = CartResponseEnum.AMOUNT_NOT_ZERO.getMessage();
                break;
            default:
                errorCode = ResponseEnum.INTERNAL_SERVER_ERROR.getCode();
                httpStatus = ResponseEnum.INTERNAL_SERVER_ERROR.getStatus();
                errorMessage = ResponseEnum.INTERNAL_SERVER_ERROR.getMessage();
        }
        ErrorResDTO errorResDTO = ErrorResDTO.builder()
                .errorCode(errorCode)
                .httpStatus(httpStatus)
                .errorMessage(errorMessage)
                .build();
        return new ResponseEntity<>(errorResDTO, errorResDTO.getHttpStatus());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> dataIntegrityViolationException(DataIntegrityViolationException e){

        switch (e.getMessage()){
            case "UE101":
                errorCode = ResponseEnum.SAME_USER_ID.getCode();
                httpStatus = ResponseEnum.SAME_USER_ID.getStatus();
                errorMessage = ResponseEnum.SAME_USER_ID.getMessage();
                break;
            default:
                errorCode = ResponseEnum.INTERNAL_SERVER_ERROR.getCode();
                httpStatus = ResponseEnum.INTERNAL_SERVER_ERROR.getStatus();
                errorMessage = ResponseEnum.INTERNAL_SERVER_ERROR.getMessage();
        }
        ErrorResDTO errorResDTO = ErrorResDTO.builder()
                .errorCode(errorCode)
                .httpStatus(httpStatus)
                .errorMessage(errorMessage)
                .build();
        return new ResponseEntity<>(errorResDTO, errorResDTO.getHttpStatus());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> httpMessageNotReadableException(HttpMessageNotReadableException e){

        ErrorResDTO errorResDTO = ErrorResDTO.builder()
                .errorCode(ResponseEnum.REQUEST_PARAM_ERROR.getCode())
                .httpStatus(ResponseEnum.REQUEST_PARAM_ERROR.getStatus())
                .errorMessage(ResponseEnum.REQUEST_PARAM_ERROR.getMessage())
                .build();
        return new ResponseEntity<>(errorResDTO, errorResDTO.getHttpStatus());
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<?> missingServletRequestParameterException(MissingServletRequestParameterException e){

        ErrorResDTO errorResDTO = ErrorResDTO.builder()
                .errorCode(ResponseEnum.REQUEST_MISSING_ERROR.getCode())
                .httpStatus(ResponseEnum.REQUEST_MISSING_ERROR.getStatus())
                .errorMessage(ResponseEnum.REQUEST_MISSING_ERROR.getMessage())
                .build();
        return new ResponseEntity<>(errorResDTO, errorResDTO.getHttpStatus());
    }

}
