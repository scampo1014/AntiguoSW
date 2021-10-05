import { NgModule } from '@angular/core';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RecetaListarComponent } from './receta-listar/receta-listar.component';
import { RecetaDetailComponent } from './receta-detail/receta-detail.component';
import { RecetaComentariosComponent} from './receta-comentarios/receta-comentarios.component';
import { CalificacionRecetaCreateComponent } from './calificacion-receta-create/calificacion-receta-create.component';
import { RecetaComentarioCreateComponent } from './receta-comentario-create/receta-comentario-create.component';
import { IngredienteRecetaCreateComponent } from './ingrediente-receta-create/ingrediente-receta-create.component';
import { UtensilioRecetaCreateComponent } from './utensilio-receta-create/utensilio-receta-create.component';
import { RecetaFotoCreateComponent } from './receta-foto-create/receta-foto-create.component';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    NgbModule,
    CommonModule,
    RouterModule,
    FormsModule,
    ReactiveFormsModule
  ],
  declarations: [RecetaListarComponent, RecetaDetailComponent, RecetaComentariosComponent,CalificacionRecetaCreateComponent, RecetaComentarioCreateComponent, IngredienteRecetaCreateComponent, UtensilioRecetaCreateComponent, RecetaFotoCreateComponent],
  exports: [RecetaListarComponent, RecetaDetailComponent, CalificacionRecetaCreateComponent, RecetaComentarioCreateComponent, IngredienteRecetaCreateComponent, UtensilioRecetaCreateComponent, RecetaFotoCreateComponent]
})
export class RecetaModule { }
