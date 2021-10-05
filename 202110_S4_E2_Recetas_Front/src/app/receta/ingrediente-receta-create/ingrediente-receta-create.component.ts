import { Component, OnInit, Input } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Receta } from '../receta';
import { RecetaService } from '../receta.service';
import { ToastrService } from 'ngx-toastr';
import { Ingrediente } from 'src/app/ingrediente/ingrediente';

@Component({
  selector: 'app-ingrediente-receta-create',
  templateUrl: './ingrediente-receta-create.component.html',
  styleUrls: ['./ingrediente-receta-create.component.css']
})
export class IngredienteRecetaCreateComponent implements OnInit {

  public isCollapsed = true;
  ingredienteForm: FormGroup;

  @Input() receta: Receta;

  constructor(
    private recetaService: RecetaService,
    private toastrService: ToastrService,
    private formBuilder: FormBuilder
  ) { }

  ngOnInit() {
    this.ingredienteForm = this.formBuilder.group({
      nombre: ['', [Validators.required, Validators.minLength(2)]],
      cantidad: ['', [Validators.required, Validators.min(1)]],
    })
  }

  createIngrediente(newIngrediente: Ingrediente) {
    newIngrediente.receta = this.receta;
    this.recetaService.createIngrediente(this.receta.id, newIngrediente)
      .subscribe(r => {
        this.toastrService.success('El ingrediente fue creado');
        this.ingredienteForm.reset();
      }, err => {
        this.toastrService.error(err, 'Error');
      })

      console.warn("El comentario fue creado", newIngrediente);
  }

  cancelCreation() {
    console.log("Cancelando ...");
    this.toastrService.warning('El comentario no fue creado', 'comentario creation');
    this.ingredienteForm.reset();
  }

}
