package com.shop.api.domain.category.controller;

import com.shop.api.domain.category.dto.CategoryDTO;
import com.shop.api.domain.category.service.CategoryService;
import com.shop.api.common.response.dto.ResultResDTO;
import com.shop.api.common.response.message.ResponseEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @GetMapping("/category")
    public ResponseEntity<?> selectCategory(){
        List<CategoryDTO> list = categoryService.selectHierarchy();
        List<CategoryDTO> resultList = new ArrayList<>();

        for(CategoryDTO e : list){
            if(e.getUpCode() == null){
                resultList.add(e);
            }
        }

        ResultResDTO resultResDTO = ResultResDTO.builder()
                .code(ResponseEnum.READ_OK.getCode())
                .httpStatus(ResponseEnum.READ_OK.getStatus())
                .message(ResponseEnum.READ_OK.getMessage())
                .count(resultList.size())
                .data(resultList)
                .build();
        return new ResponseEntity<>(resultResDTO, resultResDTO.getHttpStatus());
    }
}
