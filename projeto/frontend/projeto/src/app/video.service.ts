import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";

import { Observable } from 'rxjs';
import {Video} from "./video";
import {Comment} from "./comment";


@Injectable({
  providedIn: 'root'
})
export class VideoService {

  constructor(private httpClient: HttpClient,) { }


  getVideo(id: number): Observable<Video>{

    const params = new HttpParams().set('?id', id);

   return this.httpClient.get<Video>("http://localhost:8080/api/videos/id/" + params);
  }

  getAllVideos(): Observable<Array<Video>>{
    return this.httpClient.get<Array<Video>>("http://localhost:8080/api/videos/")
  }


  likeOrDislikeVideo(id: number, likeOrDislike: boolean): Observable<Video> {

    const likeOrDislikeValue = likeOrDislike ? 1 : 0;

    return this.httpClient.post<Video>(`http://localhost:8080/api/videos/${id}/likeOrDislikeVideo?likeOrDislike=${likeOrDislikeValue}`, null);
  }

  likeOrDislikeComment(commentId: number, isLikeOrDislike: boolean) {

    const likeOrDislikeValue = isLikeOrDislike ? 1 : 0;

    return this.httpClient.post<Comment>(`http://localhost:8080/api/videos/${commentId}/likeOrDislikeComment?likeOrDislike=${likeOrDislikeValue}`, null);
  }

  postComment(comment: any, id: number): Observable<any>{

    return this.httpClient.post("http://localhost:8080/api/videos/" + id + "/comment", comment);
  }

  deleteComment(commentId: number) {
    return this.httpClient.delete("http://localhost:8080/api/videos/" + commentId + "/deletecomment");
  }


  getViewCount (groupViewCount: number): Observable<Array<Video>>{
    const params = new HttpParams().set('?groupViewCount', groupViewCount);
    return this.httpClient.get<Array<Video>>("http://localhost:8080/api/videos/videobyview" + params)
  }



  registerVideo(thumbnailFile: File, videoUrl: string, title: string, description: string, videoStatus: number) {

    const formData = new FormData();
    formData.append('thumbnailFile', thumbnailFile);
    formData.append('videoUrl', videoUrl);
    formData.append('title', title);
    formData.append('description', description);
    formData.append('videoStatus', videoStatus.toString());

    return this.httpClient.post<any>("http://localhost:8080/api/videos", formData)
  }


    deleteVideo(id: number) {
      return this.httpClient.delete(`http://localhost:8080/api/videos/${id}/deleteVideo`);
    }
}
