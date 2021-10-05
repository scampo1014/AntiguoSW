import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AnuncioListarComponent } from './Anuncio-listar/anuncio-listar.component';

@NgModule({
  imports: [
    CommonModule
  ],
  declarations: [AnuncioListarComponent],
  exports: [AnuncioListarComponent],
})
export class AnuncioModule { }
