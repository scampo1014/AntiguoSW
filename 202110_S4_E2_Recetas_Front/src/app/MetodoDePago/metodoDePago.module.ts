import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MetodoDePagoListarComponent } from './metodoDePago-listar/metodoDePago-listar.component';

@NgModule({
  imports: [
    CommonModule
  ],
  declarations: [MetodoDePagoListarComponent],
  exports: [MetodoDePagoListarComponent],
})
export class MetodoDePagoModule { }
