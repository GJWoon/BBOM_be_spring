package com.laonstory.bbom.domain.content.domain;


import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name="t_content_image")
@Entity()
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class ContentImage {


    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String path;

    @ManyToOne(fetch = FetchType.LAZY)
    private Content content;


}
