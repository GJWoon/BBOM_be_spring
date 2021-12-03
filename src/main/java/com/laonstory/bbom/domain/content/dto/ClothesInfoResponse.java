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
public class ClothesInfoResponse {

    private Long id;

    private String comment;

    private Long contentId;

    private String size;

    private long price;

    private String brandName;

    private String name;

    private ImageResponse infoImageList;

    private String url;

    public ClothesInfoResponse(ClothesInfo info){
        this.id = info.getId();
        this.comment = info.getComment();
        this.size = info.getSize();
        this.price = info.getPrice();
        this.brandName = info.getBrandName();
        this.contentId = info.getContent().getId();
        this.name = info.getName();
        this.infoImageList = info.getClothesInfoImages() != null ? new ImageResponse(info.getClothesInfoImages().getId(),info.getClothesInfoImages().getPath()) : null;
        this.url = info.getUrl();
    }
    public ClothesInfoResponse(ClothesInfo info,String flag){
        this.id = info.getContent().getId();
        this.comment = info.getComment();
        this.size = info.getSize();
        this.price = info.getPrice();
        this.brandName = info.getBrandName();
        this.contentId = info.getContent().getId();
        this.name = info.getName();
        this.infoImageList = info.getClothesInfoImages() != null ? new ImageResponse(info.getClothesInfoImages().getId(),info.getClothesInfoImages().getPath()) : null;
        this.url = info.getUrl();
    }
}
