package com.shop.api.domain.category.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CategoryDTO {

    private long catId;
    private String catCode;
    private String catName;
    private String upCode;
    private List<CategoryDTO> children;


}
