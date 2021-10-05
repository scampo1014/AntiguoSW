import { NgModule } from '@angular/core';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { CommonModule } from '@angular/common';
import { ComentarioListarComponent } from './comentario-listar/comentario-listar.component';
import { ComentarioDetailComponent } from './comentario-detail/comentario-detail.component';

@NgModule({
  imports: [
    NgbModule,
    CommonModule
  ],
  declarations: [ComentarioListarComponent, ComentarioDetailComponent],
  exports: [ComentarioListarComponent],
})
export class ComentarioModule { }
