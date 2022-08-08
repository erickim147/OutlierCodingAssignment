package com.shop.api.domain.category.mapper;

import com.shop.api.domain.category.dto.CategoryDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CategoryMapper {

    List<CategoryDTO> selectHierarchy();
    List<CategoryDTO> selectAll();

}
