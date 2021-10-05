import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { VideoListarComponent} from './video-listar/video-listar.component';

@NgModule({
  imports: [
    CommonModule
  ],
  declarations: [VideoListarComponent],
  exports: [VideoListarComponent],
})
export class VideoModule { }
