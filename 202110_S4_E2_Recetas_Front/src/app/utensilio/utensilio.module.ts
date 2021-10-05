import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UtensilioListarComponent } from './utensilio-listar/utensilio-listar.component';

@NgModule({
  imports: [
    CommonModule
  ],
  declarations: [UtensilioListarComponent],
  exports: [UtensilioListarComponent],
})
export class UtensilioModule { }
