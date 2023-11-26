package com.projeto.watchflix.designpatterns.video;


import com.projeto.watchflix.repository.VideoRepository;

public enum LikeOrDislike implements LikeOrDislikeMethods {

    LIKE {
        @Override
        public Object sendLikeOrDislike(long videoId, VideoRepository videoRepository) {
            return videoRepository.likeVideo(videoId);
        }
    },
    DISLIKE {
        @Override
        public Object sendLikeOrDislike(long videoId, VideoRepository videoRepository) {
            return videoRepository.disLikeVideo(videoId);
        }
    };
}
