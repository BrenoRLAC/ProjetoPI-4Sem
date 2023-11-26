import {Component, OnInit} from '@angular/core';
import {VideoService} from "../video.service";
import {Video} from "../video";

@Component({
  selector: 'app-featured',
  templateUrl: './featured.component.html',
  styleUrls: ['./featured.component.css']
})
export class FeaturedComponent implements OnInit {

  featuredVideos: Array<Video> = [];
  constructor(private videoService: VideoService) {

  }

  trackByFn(index: number, video: Video): number {
    return video.id;
  }
  ngOnInit() {
    this.videoService.getAllVideos().subscribe(response => {
      this.featuredVideos = response;
    });
  }
}
