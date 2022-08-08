package com.shop.api.domain.search.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SearchDTO {

    private String searchTitle;
    @JsonProperty("isCatSearch")
    private boolean isCatSearch;
    private String searchLargeCat;
    private String searchMiddleCat;
    private String searchSubCat;

}
