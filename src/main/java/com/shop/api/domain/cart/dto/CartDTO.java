package com.shop.api.domain.cart.dto;

import com.shop.api.domain.product.dto.ProductDTO;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CartDTO {

    private long cartId;
    private String memberId;
    @NotNull(message = "CART_E100")
    private  long productId;
    @NotNull(message = "CART_E101")
    @Min(value = 1, message = "CART_E102")
    private int amount;
    private int price;
    private String cartCreateTime;
    private ProductDTO productInfo;
}
