package com.laonstory.bbom.domain.content.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ContentUpdateDto {

        private Long id;
        private String content;
        private List<String> tagList;
        private List<ClothesInfoDto> infoList;
        private List<ClothesInfoUpdateDto> infoUpdateList;
        private List<Long> infoDeleteId;
}
