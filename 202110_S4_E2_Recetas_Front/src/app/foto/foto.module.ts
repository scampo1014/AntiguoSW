import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FotoListarComponent } from './foto-listar/foto-listar.component';

@NgModule({
  imports: [
    CommonModule
  ],
  declarations: [FotoListarComponent],
  exports: [FotoListarComponent]
})
export class FotoModule { }
