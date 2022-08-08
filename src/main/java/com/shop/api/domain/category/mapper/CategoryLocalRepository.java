package com.shop.api.domain.category.mapper;

import com.shop.api.domain.category.dto.CategoryDTO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@Service
public class CategoryLocalRepository {

    private Map<String, List<CategoryDTO>> categoryLocalRepository = new HashMap<>();
}
