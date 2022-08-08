package com.shop.api.domain.cart.mapper;

import com.shop.api.domain.cart.dto.CartDTO;
import com.shop.api.domain.product.dto.ProductDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CartMapper {

    void saveCart(CartDTO cartDTO);
    List<String> selectProductId(String memberId);
    List<ProductDTO> selectAllProduct(List<String> productList);
    List<CartDTO> selectAllCart();
    int selectCartCount(long productId);
    int selectCartMemberIdCnt(String memberId);
    void deleteCart(long productId);
    void deleteAll(String memberId);
    int selectProductPrice(long productId);
    int selectProductCount(long productId);
}
