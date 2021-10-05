import { Component, Input, OnInit } from '@angular/core';
import { Comentario } from '../../comentario/comentario';

@Component({
  selector: 'app-proveedor-comentarios',
  templateUrl: './proveedor-comentarios.component.html',
  styleUrls: ['./proveedor-comentarios.component.scss']
})
export class ProveedorComentariosComponent implements OnInit {
  public isCollapsed = true;

  @Input() proveedorComentarios: Comentario[];

  constructor() { }

  ngOnInit() {
  }

  positivoNegativo(b: boolean): string {
    if (b) return "Positivo"
    else return "Negativo"
  }
}
