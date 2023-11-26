package com.projeto.watchflix.designpatterns.comment;


import com.projeto.watchflix.repository.VideoRepository;

public enum LikeOrDislikeComment implements LikeOrDislikeMethods {

    LIKE {
        @Override
        public Object sendLikeOrDislike(long commentId, VideoRepository videoRepository) {
            return videoRepository.likeComment(commentId);
        }
    },
    DISLIKE {
        @Override
        public Object sendLikeOrDislike(long commentId, VideoRepository videoRepository) {
            return videoRepository.disLikeComment(commentId);
        }
    };
}
