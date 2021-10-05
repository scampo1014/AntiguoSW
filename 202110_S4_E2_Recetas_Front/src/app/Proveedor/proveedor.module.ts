import { NgModule } from '@angular/core';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { CommonModule } from '@angular/common';
import { ProveedorListarComponent } from './proveedor-listar/proveedor-listar.component';
import { ProveedorDetailComponent } from './proveedor-detail/proveedor-detail.component';
import { ProveedorComentariosComponent } from './proveedor-comentarios/proveedor-comentarios.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ProveedorComentarioCreateComponent } from './proveedor-comentario-create/proveedor-comentario-create.component';
import { CalificacionProveedorCreateComponent } from './calificacion-proveedor-create/calificacion-proveedor-create.component';

@NgModule({
  imports: [
    NgbModule,
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
  ],
  declarations: [ProveedorListarComponent, ProveedorDetailComponent, ProveedorComentarioCreateComponent, ProveedorComentariosComponent,CalificacionProveedorCreateComponent],
  exports: [ProveedorListarComponent, ProveedorComentarioCreateComponent,CalificacionProveedorCreateComponent],
})
export class ProveedorModule { }
