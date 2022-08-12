package com.shop.api.domain.order.service;

import com.shop.api.domain.cart.dto.CartDTO;
import com.shop.api.domain.cart.mapper.CartMapper;
import com.shop.api.domain.order.dto.OrderDTO;
import com.shop.api.domain.order.mapper.OrderMapper;
import com.shop.api.domain.product.dto.ProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    OrderMapper orderMapper;
    @Autowired
    CartMapper cartMapper;
    @Transactional
    public void saveOrder(OrderDTO orderDTO){
        if(orderMapper.selectCartCount(orderDTO.getCartId()) == 0){
            throw new IllegalArgumentException("ORDER_E100");
        } else {
            CartDTO cartDTO = orderMapper.selectCart(orderDTO.getCartId());
            orderDTO.setMemberId(cartDTO.getMemberId());
            orderDTO.setProductId(cartDTO.getProductId());
            orderDTO.setTotalAmount(cartDTO.getAmount());
            orderDTO.setCartId(cartDTO.getCartId());
            orderDTO.setTotalPrice(cartDTO.getPrice());
            orderMapper.saveOrder(orderDTO);
            orderMapper.updateProductAmount(orderDTO);
            cartMapper.deleteCart(orderDTO.getProductId());
        }
    }

//    @Transactional
//    public List<OrderDTO> selectAll(String memberId){
//        System.out.println(memberId);
//        List<Integer> list = orderMapper.selectProductId(memberId);
//        for(Integer e : list){
//            System.out.println(e);
//        }
//        return orderMapper.selectAll(list);
//    }

    @Transactional
    public List<OrderDTO> selectAll(String memberId) {
        List<String> resultList = orderMapper.selectProductId(memberId);
        if(resultList.size() == 0){
            throw new IllegalArgumentException("ORDER_E101");
        } else {
            List<ProductDTO> productDTORes = orderMapper.selectAllProduct(resultList);
            List<OrderDTO> orderDTORes = orderMapper.selectAllOrder(memberId);
            for(int i=0; i<orderDTORes.size(); i++){
                orderDTORes.get(i).setProductInfo(productDTORes.get(i));
            }
            return orderDTORes;
        }
    }

}
