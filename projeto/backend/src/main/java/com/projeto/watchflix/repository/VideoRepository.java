package com.projeto.watchflix.repository;

import com.projeto.watchflix.dto.CommentDao;
import com.projeto.watchflix.model.Channel;
import com.projeto.watchflix.model.Comment;
import com.projeto.watchflix.model.Video;
import com.projeto.watchflix.model.VideoStatus;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Repository
public class VideoRepository {

    @PersistenceContext
    EntityManager em;

    @Transactional(readOnly = true, propagation = Propagation.NEVER)
    public List<Map<String, Object>> findAllVideos() {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
        Root<Video> root = criteriaQuery.from(Video.class);

        criteriaQuery.multiselect(
                root.get("videoId"),
                root.get("thumbnailUrl"),
                root.get("title"),
                root.get("description"),
                root.get("channel").get("fullName"),
                root.get("videoStatus").get("statusName"),
                root.get("viewCount")
        );

        Predicate isActivePredicate = criteriaBuilder.equal(root.get("active"), 1);
        criteriaQuery.where(isActivePredicate);

        List<Object[]> videoData = em.createQuery(criteriaQuery).getResultList();

        List<Map<String, Object>> result = new ArrayList<>();
        for (Object[] row : videoData) {
            Map<String, Object> videoMap = new HashMap<>();
            videoMap.put("id", row[0]);
            videoMap.put("thumbnailUrl", row[1]);
            videoMap.put("title", row[2]);
            videoMap.put("description", row[3]);
            videoMap.put("channelName", row[4]);
            videoMap.put("videoStatus", row[5]);
            videoMap.put("viewCount", row[6]);

            result.add(videoMap);
        }

        return result;
    }


    @Transactional(propagation = Propagation.REQUIRED)
    public Map<String, Object> findVideoById(long videoId) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
        Root<Video> root = criteriaQuery.from(Video.class);

        Join<Video, Comment> commentsJoin = root.join("comments", JoinType.LEFT);

        criteriaQuery.multiselect(
                root.get("videoId"),
                root.get("thumbnailUrl"),
                root.get("videoUrl"),
                root.get("title"),
                root.get("description"),
                root.get("channel").get("fullName"),
                root.get("videoStatus").get("statusName"),
                root.get("viewCount"),
                root.get("likes"),
                root.get("disLikes"),
                commentsJoin.get("commentId"),
                commentsJoin.get("text"),
                commentsJoin.get("video").get("videoId"),
                commentsJoin.get("likeCount"),
                commentsJoin.get("dislikeCount")
        );

        Predicate isActivePredicate = criteriaBuilder.equal(root.get("active"), 1);
        Predicate videoIdPredicate = criteriaBuilder.equal(root.get("videoId"), videoId);
        criteriaQuery.where(criteriaBuilder.and(isActivePredicate, videoIdPredicate));

        List<Object[]> resultList = em.createQuery(criteriaQuery).getResultList();

        Map<String, Object> videoWithCommentsMap = new HashMap<>();

        if (resultList.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No results found for the id.");
        }

        for (Object[] result : resultList) {
            Long videoIdResult = (Long) result[0];

            if (!videoWithCommentsMap.containsKey("videoId")) {
                videoWithCommentsMap.put("videoId", videoIdResult);
                videoWithCommentsMap.put("thumbnailUrl", result[1]);
                videoWithCommentsMap.put("videoUrl", result[2]);
                videoWithCommentsMap.put("title", result[3]);
                videoWithCommentsMap.put("description", result[4]);
                videoWithCommentsMap.put("fullName", result[5]);
                videoWithCommentsMap.put("statusName", result[6]);

                int currentViewCount = (int) result[7];
                int updatedViewCount = currentViewCount + 1;
                videoWithCommentsMap.put("viewCount", updatedViewCount);

                videoWithCommentsMap.put("likes", result[8]);
                videoWithCommentsMap.put("disLikes", result[9]);
                videoWithCommentsMap.put("comments", new ArrayList<>());
            }
            if(result[9] != null){
            Map<String, Object> commentDetails = new HashMap<>();
            commentDetails.put("commentId", result[10]);
            commentDetails.put("text", result[11]);
            commentDetails.put("commentedVideoId", result[12]);
            commentDetails.put("likeCount", result[13]);
            commentDetails.put("dislikeCount", result[14]);
            ((List<Map<String, Object>>) videoWithCommentsMap.get("comments")).add(commentDetails);
            }

            Video videoToUpdate = em.find(Video.class, videoId);
            videoToUpdate.setViewCount((int) videoWithCommentsMap.get("viewCount"));
        }

        return videoWithCommentsMap;
    }


    @Transactional(propagation = Propagation.REQUIRED)
    public Object likeVideo(long videoId) {
        try{
            em.createNativeQuery("UPDATE Video SET likes = likes + 1, dislikes = CASE WHEN dislikes > 0 THEN dislikes - 1 ELSE 0 END WHERE id = :videoId")
                    .setParameter("videoId", videoId)
                    .executeUpdate();

            return mapLikesAndDislikesVideo(videoId);
        }
            catch (Exception e){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No results found for the id.");
            }

    }
    @Transactional(propagation = Propagation.REQUIRED)
    public Object disLikeVideo(long videoId) {
        try{
        em.createNativeQuery("UPDATE Video SET dislikes = dislikes + 1, likes = CASE WHEN likes > 0 THEN likes - 1 ELSE 0 END WHERE id = :videoId")
                .setParameter("videoId", videoId)
                .executeUpdate();

        return mapLikesAndDislikesVideo(videoId);
        }
        catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No results found for the id.");
        }
    }

    @NotNull
    private Map<String, Object> mapLikesAndDislikesVideo(long videoId) {
        Object[] result = (Object[]) em.createNativeQuery("SELECT likes, dislikes FROM Video WHERE id = :videoId")
                .setParameter("videoId", videoId)
                .getSingleResult();

        Map<String, Object> updatedValues = new HashMap<>();
        updatedValues.put("likes", result[0]);
        updatedValues.put("disLikes", result[1]);

        return updatedValues;
    }


    @Transactional(propagation = Propagation.REQUIRED)
    public Object likeComment(long commentId) {
    try {
        em.createNativeQuery("UPDATE Comment SET LIKE_COUNT = LIKE_COUNT + 1, DISLIKE_COUNT = CASE WHEN DISLIKE_COUNT > 0 THEN DISLIKE_COUNT - 1 ELSE 0 END WHERE id = :commentId")
                .setParameter("commentId", commentId)
                .executeUpdate();

        return getCommentMap(commentId);
    }
    catch (Exception e){
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No results found for the id.");
    }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Object disLikeComment(long commentId) {
        try {

            em.createNativeQuery("UPDATE Comment SET DISLIKE_COUNT = DISLIKE_COUNT + 1, LIKE_COUNT = CASE WHEN LIKE_COUNT > 0 THEN LIKE_COUNT - 1 ELSE 0 END WHERE id = :commentId")
                    .setParameter("commentId", commentId)
                    .executeUpdate();

            return getCommentMap(commentId);
        }
        catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No results found for the id.");
        }
    }

    @NotNull
    private Map<String, Object> getCommentMap(long commentId) {
        Object[] result = (Object[]) em.createNativeQuery("SELECT ID, LIKE_COUNT, DISLIKE_COUNT FROM Comment WHERE id = :commentId")
                .setParameter("commentId", commentId)
                .getSingleResult();

        Map<String, Object> updatedValues = new HashMap<>();
        updatedValues.put("commentId", result[0]);
        updatedValues.put("likeCount", result[1]);
        updatedValues.put("dislikeCount", result[2]);

        return updatedValues;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void commentVideo(long videoId, CommentDao commentDao) {
        Video video = em.find(Video.class, videoId);

        if (video == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No results found for the video.");
        }

        Comment comment = new Comment();
        comment.setVideo(video);
        comment.setText(commentDao.getCommentText());
        comment.setLikeCount(commentDao.getLike());
        comment.setDislikeCount(commentDao.getDislike());

        video.getComments().add(comment);

        em.persist(comment);
        em.merge(video);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteComment(long commentId) {

        Comment comment = em.find(Comment.class, commentId);

        if(comment == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No results found for the commentId.");
        }

        if (comment != null) {
            Video video = comment.getVideo();
            video.getComments().remove(comment);
            em.remove(comment);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteVideo(long id) {

        Video v = em.find(Video.class, id);

        if(v == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No results found for the id.");
        }

        if (v != null) {
            em.remove(v);
        }

    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void insertVideo(MultipartFile thumbnailFile, String videoUrl, String title, String description, int videoStatus) throws IOException {
        Video video = new Video();


        Channel c = em.find(Channel.class, 1L);
        VideoStatus vs = em.find(VideoStatus.class, (long) videoStatus);

        byte[] imageBytes = thumbnailFile.getBytes();

        video.setActive(true);
        video.setThumbnailUrl(imageBytes);
        video.setVideoUrl(videoUrl);
        video.setTitle(title);
        video.setDescription(description);
        video.setChannel(c);
        video.setVideoStatus(vs);

        em.persist(video);

    }
}
