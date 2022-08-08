package com.shop.api.domain.category.service;

import com.shop.api.domain.category.dto.CategoryDTO;
import com.shop.api.domain.category.mapper.CategoryLocalRepository;
import com.shop.api.domain.category.mapper.CategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {

    @Autowired
    CategoryMapper categoryMapper;

    @Autowired
    CategoryLocalRepository categoryLocalRepository;
    //private Map<String, List<CategoryDTO>> categoryLocalRepository = new HashMap<>();

    public List<CategoryDTO> selectHierarchy() {
        return categoryMapper.selectHierarchy();
    }

    public void selectAll() {
        List<CategoryDTO> categoryDTOList = categoryMapper.selectAll();
        List<CategoryDTO> largeCategory = new ArrayList<>();
        List<CategoryDTO> middleCategory = new ArrayList<>();
        List<CategoryDTO> subCategory = new ArrayList<>();
        for (CategoryDTO e : categoryDTOList){
            if(e.getCatCode().startsWith("A")){
                largeCategory.add(e);
            } else if (e.getCatCode().startsWith("B")) {
                middleCategory.add(e);
            } else if (e.getCatCode().startsWith("C")) {
                subCategory.add(e);
            }
        }
        categoryLocalRepository.getCategoryLocalRepository().put("largeCategory", largeCategory);
        categoryLocalRepository.getCategoryLocalRepository().put("middleCategory", middleCategory);
        categoryLocalRepository.getCategoryLocalRepository().put("subCategory", subCategory);
    }

}
