package com.projeto.watchflix.designpatterns.comment;

import com.projeto.watchflix.repository.VideoRepository;

public interface LikeOrDislikeMethods {
    public Object sendLikeOrDislike(long videoId, VideoRepository videoRepository);

}
