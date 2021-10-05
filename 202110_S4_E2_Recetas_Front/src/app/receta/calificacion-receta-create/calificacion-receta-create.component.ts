import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { ToastrService } from "ngx-toastr";
import { Calificacion } from 'src/app/calificacion/calificacion';
import { Receta } from 'src/app/receta/receta';
import { RecetaService } from 'src/app/receta/receta.service';

@Component({
  selector: 'app-calificacion-receta-create',
  templateUrl: './calificacion-receta-create.component.html',
  styleUrls: ['./calificacion-receta-create.component.css']
})
export class CalificacionRecetaCreateComponent implements OnInit {

  public isCollapsed =true;
  calificacionForm: FormGroup;

  @Input() receta: Receta;

  constructor(
    private recetaService:RecetaService,
    private toastrService: ToastrService,
    private formBuilder: FormBuilder
  ) { }

  ngOnInit() {
    this.calificacionForm = this.formBuilder.group({
      calificador: ['', [Validators.required, Validators.minLength(2)]],
      puntos: ["", [Validators.required, Validators.min(1),Validators.max(5)]]
  })
}
createCalificacion(newCalificacion: Calificacion) {
  newCalificacion.receta = this.receta;
  this.recetaService.createCalificacion(this.receta.id, newCalificacion)
    .subscribe(r => {
      this.toastrService.success('La calificacion fue creada');
      this.calificacionForm.reset();
    },err => {
      this.toastrService.error(err, 'Error');
    })
  // Process checkout data here
  console.warn("La calificacion fue creada", newCalificacion);
}

cancelCreation() {
  console.log("Cancelando ...");
  this.toastrService.warning('La calificacion no fue creada', 'Crear calificacion');
  this.calificacionForm.reset();
}

}
