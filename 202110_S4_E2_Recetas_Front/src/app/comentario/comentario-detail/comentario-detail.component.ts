import { Component, Input, OnInit } from '@angular/core';
import { ComentarioDetail } from '../comentarioDetail';

@Component({
  selector: 'app-comentario-detail',
  templateUrl: './comentario-detail.component.html',
  styleUrls: ['./comentario-detail.component.css']
})
export class ComentarioDetailComponent implements OnInit {
  @Input() comentarioDetail: ComentarioDetail;
  constructor() { }

  ngOnInit() {
    console.log(this.comentarioDetail?.id);
  }

}
