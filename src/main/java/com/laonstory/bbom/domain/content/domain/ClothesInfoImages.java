package com.laonstory.bbom.domain.content.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name ="t_clothes_info_image")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ClothesInfoImages {

    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String path;


    public ClothesInfoImages(String path){
        this.path = path;
    }
}
