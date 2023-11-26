package com.projeto.watchflix.controller;

import com.projeto.watchflix.dto.CommentDao;
import com.projeto.watchflix.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/videos")
@RequiredArgsConstructor
public class VideoController {

	private final VideoService videoService;

	@PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
	@ResponseStatus(HttpStatus.CREATED)
	public void  uploadVideo (@RequestParam(value="thumbnailFile", required = true) MultipartFile thumbnailFile,
							  @RequestParam(value="videoUrl", required = true) String videoUrl,
							  @RequestParam(value="title", required = true) String title,
							  @RequestParam(value="description", required = true)String description,
							  @RequestParam(value="videoStatus", required = true)int videoStatus
							  ) throws IOException {
		videoService.uploadVideo(thumbnailFile,videoUrl,title,description,videoStatus);
	}


	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<Map<String, Object>> getAllVideos(){
		return videoService.getAllVideos();


	}


	@GetMapping("/id")
	@ResponseStatus(HttpStatus.OK)
	public Object getVideoById(@RequestParam(value = "id", required = true) long videoId) {

		return videoService.getVideoById(videoId);
	}
	@PostMapping("/{videoId}/likeOrDislikeVideo")
	@ResponseStatus(HttpStatus.OK)
	public Object likeOrDislikeVideo(@PathVariable long videoId, @RequestParam boolean likeOrDislike){
		return videoService.likeOrDislikeVideo(videoId, likeOrDislike);
	}


	@PostMapping("/{commentId}/likeOrDislikeComment")
	@ResponseStatus(HttpStatus.OK)
	public Object likeOrDislikeComment(@PathVariable long commentId, @RequestParam boolean likeOrDislike){
		return videoService.likeOrDislikeComment(commentId, likeOrDislike);
	}

	@PostMapping("/{videoId}/comment")
	@ResponseStatus(HttpStatus.CREATED)
	public void comment(@PathVariable long videoId, @RequestBody CommentDao commentDao){
			 videoService.commentVideo(videoId, commentDao);
	}

	@DeleteMapping("/{commentId}/deletecomment")
	@ResponseStatus(HttpStatus.OK)
	public void deleteComment(@PathVariable long commentId) {
		videoService.deleteComment(commentId);
	}


	@GetMapping("/videobyview")
	@ResponseStatus(HttpStatus.OK)
	public List<Map<String, Object>> videosByView(@RequestParam(value = "groupViewCount", required = true) int groupViewCount) {
		return videoService.videosByViewCount(groupViewCount);
	}


	@DeleteMapping("/{id}/deleteVideo")
	@ResponseStatus(HttpStatus.OK)
	public void deleteVideo(@PathVariable(value="id", required= true) long id){
		videoService.deleteVideo(id);
	}

}
