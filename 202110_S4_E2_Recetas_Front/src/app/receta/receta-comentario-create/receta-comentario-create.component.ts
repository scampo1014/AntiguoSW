import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { ToastrService } from "ngx-toastr";
import { Comentario } from '../../comentario/comentario';
import { Receta } from '../receta';
import { RecetaService } from '../receta.service';

@Component({
  selector: 'app-receta-comentario-create',
  templateUrl: './receta-comentario-create.component.html',
  styleUrls: ['./receta-comentario-create.component.css']
})
export class RecetaComentarioCreateComponent implements OnInit {

  public isCollapsed = true;
  comentarioForm: FormGroup;

  @Input() receta: Receta;

  constructor(
    private recetaService: RecetaService,
    private toastrService: ToastrService,
    private formBuilder: FormBuilder
  ) { }

  ngOnInit() {
    this.comentarioForm = this.formBuilder.group({
      aprobado: [false, [Validators.required, Validators.requiredTrue]],
      positivo: [false, [Validators.required]],
      comentario: ['', [Validators.required, Validators.minLength(2)]]
    })
  }

  createComentario(newComentario: Comentario) {
    newComentario.receta = this.receta;
    this.recetaService.createComentario(this.receta.id, newComentario)
      .subscribe(r => {
        this.toastrService.success('El comentario fue creado');
        this.comentarioForm.reset();
      }, err => {
        this.toastrService.error(err, 'Error');
      })

      console.warn("El comentario fue creado", newComentario);
  }

  cancelCreation() {
    console.log("Cancelando ...");
    this.toastrService.warning('El comentario no fue creado', 'comentario creation');
    this.comentarioForm.reset();
  }

}
