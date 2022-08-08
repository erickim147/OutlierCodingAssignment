package com.shop.api.domain.product.service;

import com.shop.api.domain.category.dto.CategoryDTO;
import com.shop.api.domain.category.mapper.CategoryLocalRepository;
import com.shop.api.domain.product.dto.ProductDTO;
import com.shop.api.domain.product.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ProductService {

    @Autowired
    ProductMapper productMapper;
    @Autowired
    CategoryLocalRepository categoryLocalRepository;

    public void saveProduct(ProductDTO productDTO) {

        categoryValidation("largeCategory", productDTO);
        categoryValidation("middleCategory", productDTO);
        categoryValidation("subCategory", productDTO);

        productMapper.saveProduct(productDTO);
    }

    private void categoryValidation(String value, ProductDTO productDTO){

        Map<String, List<CategoryDTO>> map = categoryLocalRepository.getCategoryLocalRepository();
        List<CategoryDTO> equalsList = new ArrayList<>();
        String equalsStr = "";
        String errorCode = "";

        if(value.equals("largeCategory")){
            equalsStr = productDTO.getPdLargeCat();
            equalsList = map.get(value);
            errorCode = "PCE111";
        } else if (value.equals("middleCategory")) {
            equalsStr = productDTO.getPdMiddleCat();
            equalsList = map.get(value);
            errorCode = "PCE112";
        } else if (value.equals("subCategory")) {
            equalsStr = productDTO.getPdSubCat();
            equalsList = map.get(value);
            errorCode = "PCE113";
        }

        for (int i = 0; i < equalsList.size(); i++){
            if(equalsStr.equals(equalsList.get(i).getCatCode())){
                break;
            }
            if (i+1 == equalsList.size()){
                throw new IllegalArgumentException(errorCode);
            }
        }
    }

}
