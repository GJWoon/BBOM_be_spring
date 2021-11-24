package com.laonstory.bbom.domain.content.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ContentSearchModel {

    private String size;
    private String height;
    private String weight;
    private String type;

}
