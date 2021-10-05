import { Component, Input, OnInit } from '@angular/core';
import { Comentario } from "../../comentario/comentario";

@Component({
  selector: 'app-usuario-comentarios',
  templateUrl: './usuario-comentarios.component.html',
  styleUrls: ['./usuario-comentarios.component.scss']
})
export class UsuarioComentariosComponent implements OnInit {
  public isCollapsed = true;
  @Input() usuarioComentarios: Comentario[];

  constructor() { }

  ngOnInit() {
  }

  positivoNegativo(b: boolean): string {
    if (b) return "Positivo"
    else return "Negativo"
  }

}
