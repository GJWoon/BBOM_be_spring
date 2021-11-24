package com.laonstory.bbom.domain.content.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "t_content_tag")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class ContentTag {
     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

     private String tag;

     @ManyToOne(fetch = FetchType.LAZY)
    private Content content;

}
