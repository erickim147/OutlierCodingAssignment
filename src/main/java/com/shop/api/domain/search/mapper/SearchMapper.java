package com.shop.api.domain.search.mapper;

import com.shop.api.domain.product.dto.ProductDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface SearchMapper {

    List<ProductDTO> selectAll();

    List<ProductDTO> searchByTitle(String searchParam);

    List<ProductDTO> searchByCat(String searchParam);

}
