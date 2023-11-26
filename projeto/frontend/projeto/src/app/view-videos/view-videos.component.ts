import { Component } from '@angular/core';
import {Video} from "../video";
import {VideoService} from "../video.service";

@Component({
  selector: 'app-view-videos',
  templateUrl: './view-videos.component.html',
  styleUrls: ['./view-videos.component.css']
})
export class ViewVideosComponent {

  featuredVideos: Array<Video> = [];

  featuredVideos2: Array<Video> = [];
  featuredVideos3: Array<Video> = [];

  featuredVideos4: Array<Video> = [];
  featuredVideos5: Array<Video> = [];
  featuredVideos6: Array<Video> = [];
  constructor(private videoService: VideoService) {

  }

  ngOnInit() {
    this.videoService.getViewCount(5).subscribe(response => {
      this.featuredVideos = response;
    });

    this.videoService.getViewCount(10).subscribe(response => {
      this.featuredVideos2 = response;
    });

    this.videoService.getViewCount(15).subscribe(response => {
      this.featuredVideos3 = response;
    });

    this.videoService.getViewCount(20).subscribe(response => {
      this.featuredVideos4 = response;
    });

    this.videoService.getViewCount(25).subscribe(response => {
      this.featuredVideos5 = response;
    });

    this.videoService.getViewCount(30).subscribe(response => {
      this.featuredVideos6 = response;
    });
  }
}
