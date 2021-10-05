import { Component, Input, OnInit } from '@angular/core';
import { Comentario } from "../../comentario/comentario";

@Component({
  selector: 'app-receta-comentarios',
  templateUrl: './receta-comentarios.component.html',
  styleUrls: ['./receta-comentarios.component.css']
})
export class RecetaComentariosComponent implements OnInit {
  public isCollapsed = true;
  @Input() recetaComentarios: Comentario[];

  constructor() { }

  ngOnInit() {
  }

  positivoNegativo(b: boolean): string {
    if (b) return "Positivo"
    else return "Negativo"
  }

}
