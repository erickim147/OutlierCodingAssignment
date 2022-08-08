package com.shop.api.domain.product.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class ProductDTO {

    private long pdIdx;

    private String pdSellerId;
    @NotBlank(message = "PCE100")
    private String pdLargeCat;
    @NotBlank(message = "PCE101")
    private String pdMiddleCat;
    @NotBlank(message = "PCE102")
    private String pdSubCat;
    @NotBlank(message = "PCE103")
    private String pdName;
    @NotNull(message = "PCE104")
    private int pdPrice;
    @NotNull(message = "PCE105")
    private int pdDiscount;
    @NotNull(message = "PCE106")
    @JsonProperty("setSalePeriod")
    private boolean setSalePeriod;
    private String pdSalePeriod;
    @NotNull(message = "PCE108")
    private int pdStock;
    @NotBlank(message = "PCE109")
    private String pdDesc;
    @NotNull(message = "PCE110")
    private int pdDeliveryCharge;
}
