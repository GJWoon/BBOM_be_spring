package com.laonstory.bbom.domain.content.dto;

import com.laonstory.bbom.domain.content.domain.Content;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ContentMyPageResponse {


    private Long id;

    private String imagePath;


    public ContentMyPageResponse(Content content){

        this.id = content.getId();
        this.imagePath = content.getContentImageList() != null && content.getContentImageList().size() != 0 ?
                content.getContentImageList().get(0).getPath() : null;
    }
}
