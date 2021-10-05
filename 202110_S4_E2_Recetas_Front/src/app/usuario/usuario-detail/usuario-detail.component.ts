import { Component, OnInit, ViewChild, Input } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Usuario } from '../usuario';
import { UsuarioDetail } from '../usuarioDetail';
import { UsuarioService } from '../usuario.service';
import { CalificacionUsuarioCreateComponent } from '../calificacion-usuario-create/calificacion-usuario-create.component';
import { Calificacion } from 'src/app/calificacion/calificacion';
import { UsuarioComentariosComponent } from '../usuario-comentarios/usuario-comentarios.component';
import { UsuarioComentarioCreateComponent } from '../usuario-comentario-create/usuario-comentario-create.component';

@Component({
  selector: 'app-usuario-detail',
  templateUrl: './usuario-detail.component.html',
  styleUrls: ['./usuario-detail.component.css']
})
export class UsuarioDetailComponent implements OnInit {

  @Input() usuarioDetail: UsuarioDetail;
  usuarioId: number;
  @ViewChild(CalificacionUsuarioCreateComponent) calificacionCreateComponent: CalificacionUsuarioCreateComponent;
  @ViewChild(UsuarioComentariosComponent) comentarioListComponent: UsuarioComentariosComponent;
  @ViewChild(UsuarioComentarioCreateComponent) comentarioCreateComponent: UsuarioComentarioCreateComponent;

  constructor(private usuarioService :UsuarioService,  private route: ActivatedRoute) { }

  getUsuarioDetail(): void {
    this.usuarioService.getUsuarioDetail(this.usuarioId)
      .subscribe(usuarioDetail => {
        this.usuarioDetail = usuarioDetail;
      });
  }

  getPromedioCalificaciones(calificaciones: Calificacion[]): string{
    let total: number = 0;
    calificaciones.forEach((calificacion) => {total = total + calificacion.puntos});
    return (total/calificaciones.length).toFixed(2);
  }

  ngOnInit() {
    console.log(this.usuarioDetail?.id);
    this.usuarioDetail = new UsuarioDetail();
    this.usuarioDetail.calificaciones = []
    this.usuarioId = +this.route.snapshot.paramMap.get('id');
    this.getUsuarioDetail();
  }

}
