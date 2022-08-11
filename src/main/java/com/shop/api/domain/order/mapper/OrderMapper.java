package com.shop.api.domain.order.mapper;

import com.shop.api.domain.cart.dto.CartDTO;
import com.shop.api.domain.order.dto.OrderDTO;
import com.shop.api.domain.product.dto.ProductDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface OrderMapper {

    void saveOrder(OrderDTO orderDTO);
    int selectCartCount(long cartId);
    CartDTO selectCart(long cartId);
    void updateProductAmount(OrderDTO orderDTO);

    List<String> selectProductId(String memberId);

    List<ProductDTO> selectAllProduct(List<String> productList);
    List<OrderDTO> selectAllOrder(String memberId);
}
