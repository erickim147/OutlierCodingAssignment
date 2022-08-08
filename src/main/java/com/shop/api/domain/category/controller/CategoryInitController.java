package com.shop.api.domain.category.controller;

import com.shop.api.domain.category.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class CategoryInitController  {
    @Autowired
    CategoryService categoryService;

    @PostConstruct
    public void init() {
        categoryService.selectAll();
    }
}
