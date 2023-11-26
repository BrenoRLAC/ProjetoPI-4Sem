package com.projeto.watchflix.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentDao {

    private long commentId;
    private String commentText;

    private int like;
    private int dislike;
}
