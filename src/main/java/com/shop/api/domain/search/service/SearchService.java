package com.shop.api.domain.search.service;

import com.shop.api.domain.category.dto.CategoryDTO;
import com.shop.api.domain.category.mapper.CategoryLocalRepository;
import com.shop.api.domain.product.dto.ProductDTO;
import com.shop.api.domain.search.dto.SearchDTO;
import com.shop.api.domain.search.mapper.SearchMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class SearchService {

    @Autowired
    SearchMapper searchMapper;
    @Autowired
    CategoryLocalRepository categoryLocalRepository;


    public List<ProductDTO> selectProduct(SearchDTO searchDTO){

        String searchParam = "";

        if(searchDTO.isCatSearch()){
            System.out.println("1");
            if(!searchDTO.getSearchLargeCat().equals("") && searchDTO.getSearchLargeCat() != null) {
                categoryValidation("largeCategory", searchDTO);
                searchParam = searchDTO.getSearchLargeCat();
                return searchByCat(searchParam);
            } else if (!searchDTO.getSearchMiddleCat().equals("") && searchDTO.getSearchMiddleCat() != null) {
                categoryValidation("middleCategory", searchDTO);
                searchParam = searchDTO.getSearchMiddleCat();
                return searchByCat(searchParam);
            } else if (!searchDTO.getSearchSubCat().equals("") && searchDTO.getSearchSubCat() != null) {
                categoryValidation("subCategory", searchDTO);
                searchParam = searchDTO.getSearchSubCat();
                return searchByCat(searchParam);
            } else {
                throw new IllegalArgumentException("SEARCH_E1000");
            }

        } else if (searchDTO.getSearchTitle().equals("") || searchDTO.getSearchTitle() == null){
            return selectAll();
        } else {
            searchParam = searchDTO.getSearchTitle();
            return searchByTitle(searchParam);
        }
    }

    private List<ProductDTO> selectAll(){
        return searchMapper.selectAll();
    }

    private List<ProductDTO> searchByTitle(String searchParam){
        return searchMapper.searchByTitle(searchParam);
    }

    private List<ProductDTO> searchByCat(String searchParam){
        return searchMapper.searchByCat(searchParam);
    }

    private void categoryValidation(String value, SearchDTO searchDTO){

        Map<String, List<CategoryDTO>> map = categoryLocalRepository.getCategoryLocalRepository();
        List<CategoryDTO> equalsList = new ArrayList<>();
        String equalsStr = "";
        String errorCode = "";

        if(value.equals("largeCategory")){
            equalsStr = searchDTO.getSearchLargeCat();
            equalsList = map.get(value);
            errorCode = "PCE111";
        } else if (value.equals("middleCategory")) {
            equalsStr = searchDTO.getSearchMiddleCat();
            equalsList = map.get(value);
            errorCode = "PCE112";
        } else if (value.equals("subCategory")) {
            equalsStr = searchDTO.getSearchSubCat();
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
