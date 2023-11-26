package com.projeto.watchflix.designpatterns.video;

import com.projeto.watchflix.repository.VideoRepository;

public interface LikeOrDislikeMethods {
    public Object sendLikeOrDislike(long videoId, VideoRepository videoRepository);

}
