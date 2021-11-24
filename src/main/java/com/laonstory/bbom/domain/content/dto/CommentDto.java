package com.laonstory.bbom.domain.content.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {


    private Long contentId;

    private String content;

    private Long parentId;
}
