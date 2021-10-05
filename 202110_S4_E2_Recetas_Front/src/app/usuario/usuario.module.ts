import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UsuarioComentariosComponent } from './usuario-comentarios/usuario-comentarios.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { UsuarioListarComponent } from './usuario-listar/usuario-listar.component';
import { UsuarioDetailComponent } from './usuario-detail/usuario-detail.component';
import { CalificacionUsuarioCreateComponent } from './calificacion-usuario-create/calificacion-usuario-create.component';
import { UsuarioCreateComponent } from './usuario-create/usuario-create.component';
import { RouterModule } from '@angular/router';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { UsuarioComentarioCreateComponent } from './usuario-comentario-create/usuario-comentario-create.component';
import { RecetaUsuarioCreateComponent } from './receta-usuario-create/receta-usuario-create.component';

@NgModule({
  imports: [
    CommonModule, ReactiveFormsModule, RouterModule, NgbModule, FormsModule
  ],
  declarations: [UsuarioListarComponent, UsuarioDetailComponent,CalificacionUsuarioCreateComponent, UsuarioCreateComponent, UsuarioComentariosComponent, UsuarioComentarioCreateComponent, RecetaUsuarioCreateComponent],
  exports: [UsuarioListarComponent,CalificacionUsuarioCreateComponent,UsuarioCreateComponent, UsuarioComentarioCreateComponent, RecetaUsuarioCreateComponent],
})
export class UsuarioModule { }
