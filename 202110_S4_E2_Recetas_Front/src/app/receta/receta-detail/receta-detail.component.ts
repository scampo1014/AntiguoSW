import { Component, OnInit, Input, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { RecetaComentariosComponent } from '../receta-comentarios/receta-comentarios.component';
import {RecetaService} from '../receta.service';
import { RecetaDetail } from '../recetaDetail';
import { Calificacion } from 'src/app/calificacion/calificacion';
import { CalificacionRecetaCreateComponent } from '../calificacion-receta-create/calificacion-receta-create.component';
import { RecetaComentarioCreateComponent } from '../receta-comentario-create/receta-comentario-create.component';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';

@Component({
  selector: 'app-receta-detail',
  templateUrl: './receta-detail.component.html',
  styleUrls: ['./receta-detail.component.css']
})
export class RecetaDetailComponent implements OnInit {

  @Input() recetaDetail: RecetaDetail;
  constructor(
    private recetaService: RecetaService,
    private route: ActivatedRoute,
    private _sanitizer: DomSanitizer
  ) { }

  receta_id: number;
  @ViewChild(RecetaComentariosComponent) comentarioListComponent: RecetaComentariosComponent;
  @ViewChild(CalificacionRecetaCreateComponent) calificacionCreateComponent: CalificacionRecetaCreateComponent;
  @ViewChild(RecetaComentarioCreateComponent) comentarioCreateComponent: RecetaComentarioCreateComponent;

  ngOnInit() {
    this.receta_id = +this.route.snapshot.paramMap.get('id');
    if(this.receta_id) {
      this.recetaDetail = new RecetaDetail()
      this.getRecetaDetail();
    }
  }

  getRecetaDetail(): void {
    this.recetaService.getRecetaDetail(this.receta_id)
      .subscribe(recetaDetail => {
        this.recetaDetail = recetaDetail
      })
  }

  getPromedioCalificaciones(calificaciones: Calificacion[]): string{
    let total: number = 0;
    calificaciones.forEach((calificacion) => {total = total + calificacion.puntos});
    return (total/calificaciones.length).toFixed(2);
  }

  getUrl(videoURL: string):SafeResourceUrl
  {
    return this._sanitizer.bypassSecurityTrustResourceUrl(videoURL);
  }

}
