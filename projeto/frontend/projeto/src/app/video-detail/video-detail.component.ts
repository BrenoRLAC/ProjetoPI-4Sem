import { Component } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {VideoService} from "../video.service";
import {FormControl, FormGroup} from "@angular/forms";

@Component({
  selector: 'app-video-detail',
  templateUrl: './video-detail.component.html',
  styleUrls: ['./video-detail.component.css']
})
export class VideoDetailComponent {

  id!: number;
  videoUrl!: string;
  videoTitle!: string;
  videoDescription!: string;
  tags: Array<string> = [];
  comments: { like: number; dislike: number; commentId: number; commentText: string }[] = [];
  videoAvailable: boolean = false;
  likeCount: number = 0;
  dislikeCount: number = 0;
  viewCount!: number;
  commentsForm: FormGroup;
  videoId!: number;

  constructor(private activatedRoute: ActivatedRoute,
              private videoService: VideoService,
              private router: Router
  ) {

    this.id = this.activatedRoute.snapshot.params['id'];

    this.commentsForm = new FormGroup({
      comment: new FormControl(''),
    });

    this.videoService.getVideo(this.id).subscribe(data => {
      console.log(data)
      this.videoUrl = data.videoUrl;
      this.videoTitle = data.title;
      this.videoDescription = data.description;
      this.videoAvailable = true;
      this.likeCount = data.likes;
      this.dislikeCount = data.disLikes;
      this.viewCount = data.viewCount;
      this.videoId = data.id;
      this.comments = data.comments.map(comment => ({
        commentId: comment.commentId,
        commentText: comment.text,
        like: comment.likeCount,
        dislike: comment.dislikeCount
      }));

    });


  }

  likeOrDislikeVideo(b: boolean) {
    this.videoService.likeOrDislikeVideo(this.id, b).subscribe(data => {
      this.likeCount = data.likes;
      this.dislikeCount = data.disLikes;

    });
  }

  likeOrDislikeComment(commentId: number, isLikeOrDislike: boolean) {

    this.videoService.likeOrDislikeComment(commentId, isLikeOrDislike).subscribe(data => {

      const updatedComment = this.comments.find(comment => comment.commentId === data.commentId);

      if (updatedComment) {
        updatedComment.like = data.likeCount;
        updatedComment.dislike = data.dislikeCount;

      }
    });

  }


  postComment() {
    const comments = this.commentsForm.get('comment')?.value;

    // @ts-ignore
    const comment = {
      "commentText": comments,
      videoId: this.videoId,
      "like": 0,
      "dislike": 0
    };

    this.videoService.postComment(comment, this.id).subscribe(() => {
      this.commentsForm.get('comment')?.reset();

      this.videoService.getVideo(this.id).subscribe(data => {
        this.comments = data.comments.map(comment => ({
          commentId: comment.commentId,
          commentText: comment.text,
          like: comment.likeCount,
          dislike: comment.dislikeCount
        }));
      });
    });

  }

  deleteComment(commentId: number) {
    this.videoService.deleteComment(commentId).subscribe(() => {

      this.commentsForm.get('comment')?.reset();
      this.videoService.getVideo(this.id).subscribe(data => {
        this.comments = data.comments.map(comment => ({
          commentId: comment.commentId,
          commentText: comment.text,
          like: comment.likeCount,
          dislike: comment.dislikeCount
        }));
      });
    });

  }

  deleteVideo(id: number) {
    this.videoService.deleteVideo(id).subscribe(() => {

      this.router.navigate(['/featured']);

    })


  }
}
