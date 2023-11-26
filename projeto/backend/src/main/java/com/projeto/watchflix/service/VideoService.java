package com.projeto.watchflix.service;

import com.projeto.watchflix.designpatterns.comment.LikeOrDislikeComment;
import com.projeto.watchflix.designpatterns.groupbylikes.GroupVideosByLikes;
import com.projeto.watchflix.designpatterns.video.LikeOrDislike;
import com.projeto.watchflix.dto.CommentDao;
import com.projeto.watchflix.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class VideoService {
    private final VideoRepository videoRepository;


    public void uploadVideo(MultipartFile thumbnailFile, String videoUrl, String title, String description, int videoStatus) throws IOException {

        if(!( videoStatus > 0 && videoStatus < 4 && videoUrl.length() == 11)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "VideoStatus should be between 1 and 3, got: " +  videoStatus + " videoUrl should be of length of 11 got:" + videoUrl);
       }
        videoRepository.insertVideo(thumbnailFile,videoUrl,title,description,videoStatus);

    }

    public List<Map<String, Object>> getAllVideos() {
        return videoRepository.findAllVideos();
    }


    public Object getVideoById(long videoId) {
        return videoRepository.findVideoById(videoId);
    }


    public Object likeOrDislikeVideo(long videoId, boolean likeOrDislike) {

        if (likeOrDislike) {
            return LikeOrDislike.LIKE.sendLikeOrDislike(videoId, videoRepository);
        }

        return LikeOrDislike.DISLIKE.sendLikeOrDislike(videoId, videoRepository);

    }

    public Object likeOrDislikeComment(long commentId, boolean likeOrDislike) {

        if (likeOrDislike) {
            return LikeOrDislikeComment.LIKE.sendLikeOrDislike(commentId, videoRepository);
        }

        return LikeOrDislikeComment.DISLIKE.sendLikeOrDislike(commentId, videoRepository);
    }

    public void commentVideo(long videoId, CommentDao comment) {
        videoRepository.commentVideo(videoId, comment);
    }

    public void deleteComment(long commentId) {
        videoRepository.deleteComment(commentId);

    }

    private final Map<Integer, GroupVideosByLikes> viewCountMap = Map.of(
            5, GroupVideosByLikes.VIEWCOUNT5,
            10, GroupVideosByLikes.VIEWCOUNT10,
            15, GroupVideosByLikes.VIEWCOUNT15,
            20, GroupVideosByLikes.VIEWCOUNT20,
            25, GroupVideosByLikes.VIEWCOUNT25,
            30, GroupVideosByLikes.VIEWCOUNT30
    );

    public List<Map<String, Object>> videosByViewCount(int groupViewCount) {
        if (!viewCountMap.containsKey(groupViewCount)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "The groupViewCount is out of range, choose between 5, 10, 15, 20, 25, 30");
        }

        List<Map<String, Object>> allVideos = videoRepository.findAllVideos();

        return viewCountMap.get(groupViewCount).calculateViewCount(allVideos);
    }

    public void deleteVideo(Long id) {
        videoRepository.deleteVideo(id);
    }


}