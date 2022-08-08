package com.shop.api.domain.cart.service;

import com.shop.api.domain.cart.dto.CartDTO;
import com.shop.api.domain.cart.mapper.CartMapper;
import com.shop.api.domain.product.dto.ProductDTO;
import com.shop.api.domain.product.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CartService {

    @Autowired
    CartMapper cartMapper;
    @Autowired
    ProductMapper productMapper;

    @Transactional
    public void saveCart(CartDTO cartDTO){
        if(cartMapper.selectProductCount(cartDTO.getProductId()) == 0){
            throw new IllegalArgumentException("CART_E103");
        } else if (cartMapper.selectCartCount(cartDTO.getProductId()) == 1) {
            throw new IllegalArgumentException("CART_E105");
        } else {
            int price = cartMapper.selectProductPrice(cartDTO.getProductId());
            cartDTO.setPrice(price * cartDTO.getAmount());
            cartMapper.saveCart(cartDTO);
        }
    }

    @Transactional
    public List<CartDTO> selectAll(String memberId) {
        List<String> resultList = cartMapper.selectProductId(memberId);
        if(resultList.size() == 0){
            throw new IllegalArgumentException("CART_E104");
        } else {
            List<ProductDTO> productDTORes = cartMapper.selectAllProduct(resultList);
            List<CartDTO> cartDTORes = cartMapper.selectAllCart();
            for(int i=0; i<cartDTORes.size(); i++){
                cartDTORes.get(i).setProductInfo(productDTORes.get(i));
            }
            return cartDTORes;
        }
    }

    @Transactional
    public void deleteCart(long productId, String memberId) {
        if(cartMapper.selectCartCount(productId) == 0){
            throw new IllegalArgumentException("CART_E103");
        } else {
            cartMapper.deleteCart(productId);
        }
    }
    @Transactional
    public void deleteAll(String memberId) {
        if(cartMapper.selectCartMemberIdCnt(memberId) == 0){
            throw new IllegalArgumentException("CART_E104");
        } else {
            cartMapper.deleteAll(memberId);
        }
    }

}
