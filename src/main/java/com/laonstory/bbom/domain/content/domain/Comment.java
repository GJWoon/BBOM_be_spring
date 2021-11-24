package com.laonstory.bbom.domain.content.domain;


import com.laonstory.bbom.domain.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "t_comment")
@Builder
public class Comment {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String commentContent;

    @ManyToOne(fetch = FetchType.LAZY)
    private Content content;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Comment parent;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "parent")
    private  final List<Comment> childComment = new ArrayList<>();

    public void addParentComment(Comment comment){
        this.parent = comment;
        comment.childComment.add(this);
    }
}
