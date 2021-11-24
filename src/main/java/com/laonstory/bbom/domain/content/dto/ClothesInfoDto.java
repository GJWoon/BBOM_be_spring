package com.laonstory.bbom.domain.content.dto;


import com.laonstory.bbom.domain.content.domain.ClothesInfo;
import com.laonstory.bbom.global.dto.response.ImageResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ClothesInfoDto {

    private String comment;

    private String size;

    private long price;

    private String brandName;

    private String name;

    private List<ImageResponse> infoImageList;

    public ClothesInfoDto(ClothesInfo info){
        this.comment = info.getComment();
        this.size = info.getSize();
        this.price = info.getPrice();
        this.brandName = info.getBrandName();
        this.name = info.getName();
    }
}
