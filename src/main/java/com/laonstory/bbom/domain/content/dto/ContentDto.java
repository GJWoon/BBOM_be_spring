package com.laonstory.bbom.domain.content.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ContentDto {

    private String content;

    private Boolean isShow;

    private List<String> tagList;

    private List<ClothesInfoDto> infoList;

}
