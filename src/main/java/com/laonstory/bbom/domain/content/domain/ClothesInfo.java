package com.laonstory.bbom.domain.content.domain;


import com.laonstory.bbom.domain.content.dto.ClothesInfoDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity()
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "t_clothe_info")
@Getter
@Builder
public class ClothesInfo {


    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String brandName;

    private String name;

    private long price;

    private String size;

    private String comment;

    @ManyToOne(fetch = FetchType.LAZY)
    private Content content;

    @ManyToOne(fetch = FetchType.LAZY)
    private ClothesInfoImages clothesInfoImages;

    public static ClothesInfo create (ClothesInfoDto dto,Content content){

        return ClothesInfo.builder()
                .brandName(dto.getBrandName())
                .content(content)
                .size(dto.getSize())
                .comment(dto.getComment())
                .price(dto.getPrice())
                .name(dto.getName())
                .build();
    }

    public void addImage(ClothesInfoImages image){
        this.clothesInfoImages = image;

    }

}
