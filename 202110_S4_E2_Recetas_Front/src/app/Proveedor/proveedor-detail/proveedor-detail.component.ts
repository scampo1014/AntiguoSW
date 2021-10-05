import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { Proveedor } from '../proveedor';
import { ProveedorDetail } from '../proveedorDetail';
import { CalificacionProveedorCreateComponent } from '../calificacion-proveedor-create/calificacion-proveedor-create.component';
import { ProveedorService } from '../proveedor.service';
import { ActivatedRoute } from '@angular/router';
import { Calificacion } from 'src/app/calificacion/calificacion';
import { ProveedorComentariosComponent } from '../proveedor-comentarios/proveedor-comentarios.component';

@Component({
  selector: 'app-proveedor-detail',
  templateUrl: './proveedor-detail.component.html',
  styleUrls: ['./proveedor-detail.component.css']
})
export class ProveedorDetailComponent implements OnInit {

  @Input() proveedorDetail: ProveedorDetail;
  proveedorId: number;
  @ViewChild(CalificacionProveedorCreateComponent) calificacionCreateComponent: CalificacionProveedorCreateComponent;

  constructor(private proveedorService :ProveedorService,  private route: ActivatedRoute,) { }

  getProveedorDetail(): void {
    this.proveedorService.getProveedorDetail(this.proveedorId)
      .subscribe(proveedorDetail => {
        this.proveedorDetail = proveedorDetail;
      });
  }

  getPromedioCalificaciones(calificaciones: Calificacion[]): string{
    let total: number = 0;
    calificaciones.forEach((calificacion) => {total = total + calificacion.puntos});
    return (total/calificaciones.length).toFixed(2);
  }

  @ViewChild(ProveedorComentariosComponent) comentarioListComponent: ProveedorComentariosComponent;

  ngOnInit() {
    console.log(this.proveedorDetail?.id);
    this.proveedorDetail = new ProveedorDetail();
    this.proveedorDetail.calificaciones = []
    this.proveedorId = +this.route.snapshot.paramMap.get('id');
    this.getProveedorDetail();
  }

}
