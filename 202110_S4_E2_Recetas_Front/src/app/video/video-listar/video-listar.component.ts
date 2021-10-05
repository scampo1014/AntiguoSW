import { Component, OnInit } from '@angular/core';
import { Video } from '../video';
import { VideoService } from '../video.service';

@Component({
  selector: 'app-video-listar',
  templateUrl: './video-listar.component.html',
  styleUrls: ['./video-listar.component.css']
})
export class VideoListarComponent implements OnInit {

  constructor(private videoService: VideoService) { }
  videos: Array<Video>;

  getVideos(): void {
    this.videoService.getVideos()
      .subscribe(videos => {
        this.videos = videos;
      });
  }

  ngOnInit() {
    this.getVideos();
  }

}
