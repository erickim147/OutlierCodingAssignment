package com.shop.api.domain.search.controller;

import com.shop.api.common.response.dto.ResultResDTO;
import com.shop.api.common.response.message.ProductResponseEnum;
import com.shop.api.common.response.message.SearchResponseEnum;
import com.shop.api.domain.product.dto.ProductDTO;
import com.shop.api.domain.search.dto.SearchDTO;
import com.shop.api.domain.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v4/search")
public class SearchController {

    @Autowired
    SearchService searchService;

    @GetMapping()
    public ResponseEntity<?> searchProduct(@RequestBody SearchDTO searchDTO) {

        List<ProductDTO> resultProduct = searchService.selectProduct(searchDTO);

        ResultResDTO resultResDTO = ResultResDTO.builder()
                .code(SearchResponseEnum.SEARCH_SUCCESS.getCode())
                .httpStatus(SearchResponseEnum.SEARCH_SUCCESS.getStatus())
                .message(SearchResponseEnum.SEARCH_SUCCESS.getMessage())
                .count(resultProduct.size())
                .data(resultProduct)
                .build();
        return new ResponseEntity<>(resultResDTO, resultResDTO.getHttpStatus());
    }

}
