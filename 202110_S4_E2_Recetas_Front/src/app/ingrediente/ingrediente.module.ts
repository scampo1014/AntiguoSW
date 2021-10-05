import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { IngredienteListarComponent } from './ingrediente-listar/ingrediente-listar.component';

@NgModule({
  imports: [
    CommonModule
  ],
  declarations: [IngredienteListarComponent],
  exports: [IngredienteListarComponent,]
})
export class IngredienteModule { }
