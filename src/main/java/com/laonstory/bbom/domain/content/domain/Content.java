package com.laonstory.bbom.domain.content.domain;


import com.laonstory.bbom.domain.content.dto.ContentDto;
import com.laonstory.bbom.domain.user.domain.User;
import com.laonstory.bbom.global.domain.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Table(name = "t_content")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Content extends BaseTimeEntity {

    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    private LocalDateTime createdDate;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user ;

    private Boolean isShow;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "content",cascade = CascadeType.ALL)
    private final List<ContentImage> contentImageList= new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "content",cascade = CascadeType.ALL)
    private final List<ClothesInfo> clothesInfos = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "content",cascade = CascadeType.ALL)
    private final List<ContentTag> tagList = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "content",cascade = CascadeType.ALL)
    private final List<ContentLike> like = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "content",cascade = CascadeType.ALL)
    private final List<Comment> commentList = new ArrayList<>();

    public static Content create(ContentDto dto,User user ){
        return Content.builder()
                .user(user)
                .content(dto.getContent())
                .createdDate(LocalDateTime.now())
                .isShow(dto.getIsShow())
                .build();

    }
}
