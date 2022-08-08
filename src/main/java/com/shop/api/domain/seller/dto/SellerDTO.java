package com.shop.api.domain.seller.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
public class SellerDTO {


    private String sellerMemberId;
    @NotBlank(message = "SCE100")
    private String shopName;
    @NotBlank(message = "SCE101")
    private String shopAddr;
    @NotBlank(message = "SCE102")
    private String shopTelNum;
    private String sellerCreateTime;
}
