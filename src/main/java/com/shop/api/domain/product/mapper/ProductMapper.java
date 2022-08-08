package com.shop.api.domain.product.mapper;

import com.shop.api.domain.product.dto.ProductDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ProductMapper {

    void saveProduct(ProductDTO productDTO);
}
