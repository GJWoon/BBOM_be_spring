package com.laonstory.bbom.domain.content.dto;

import com.laonstory.bbom.domain.content.domain.ClothesInfo;
import com.laonstory.bbom.global.dto.response.ImageResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ClotheInfoSearchResponse {
    private Long id;

    private String comment;

    private Long contentId;

    private String size;

    private long price;

    private String brandName;

    private String name;

    private String path;

    private String url;

    public ClotheInfoSearchResponse(ClothesInfo info, String flag){
        this.id = info.getContent().getId();
        this.comment = info.getComment();
        this.size = info.getSize();
        this.price = info.getPrice();
        this.brandName = info.getBrandName();
        this.contentId = info.getContent().getId();
        this.name = info.getName();
        this.path = info.getClothesInfoImages() != null ? info.getClothesInfoImages().getPath() : null;
        this.url = info.getUrl();
    }
}
