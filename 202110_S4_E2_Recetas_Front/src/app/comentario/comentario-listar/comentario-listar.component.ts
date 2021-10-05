import { Component, OnInit } from '@angular/core';
import { Comentario } from '../comentario';
import { ComentarioDetail} from '../comentarioDetail';
import { ComentarioService } from '../comentario.service';

@Component({
  selector: 'app-comentario-listar',
  templateUrl: './comentario-listar.component.html',
  styleUrls: ['./comentario-listar.component.css']
})
export class ComentarioListarComponent implements OnInit {

  constructor(private comentarioService: ComentarioService) { }
  comentarios: Array<Comentario>=[];
  selectedComentario!: Comentario;
  selected = false;

  getComentarios(): void {
    this.comentarioService.getComentarios()
      .subscribe(comentarios => {
        this.comentarios = comentarios;
      });
  }

  ngOnInit() {
    this.getComentarios();
  }

  onSelected(c: Comentario):void {
    this.selected=true;
    this.selectedComentario= c;
  }

}
