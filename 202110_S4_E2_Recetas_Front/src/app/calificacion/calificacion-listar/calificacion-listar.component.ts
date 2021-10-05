import { Component, OnInit } from '@angular/core';
import {Calificacion} from '../calificacion';
import {CalificacionService} from '../calificacion.service';
@Component({
  selector: 'app-calificacion-listar',
  templateUrl: './calificacion-listar.component.html',
  styleUrls: ['./calificacion-listar.component.css']
})
export class CalificacionListarComponent implements OnInit {
  calificaciones: Array<Calificacion>;

  constructor(private calificacionService: CalificacionService) { }
  getCalificaciones(): void
  {
    this.calificacionService.getCalificaciones().subscribe(calificaciones=>{this.calificaciones= calificaciones;})
  }
  ngOnInit() {
    this.getCalificaciones();
  }

}
