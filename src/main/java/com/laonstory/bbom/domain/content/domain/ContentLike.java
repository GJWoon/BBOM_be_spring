package com.laonstory.bbom.domain.content.domain;


import com.laonstory.bbom.domain.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Table(name = "t_content_like")
@Entity()
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class ContentLike {

    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Content content;

    //TODO
    // User 만들기

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;


}
