import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { ToastrService } from "ngx-toastr";
import { Calificacion } from 'src/app/calificacion/calificacion';
import { Proveedor } from '../proveedor';
import { ProveedorService } from '../proveedor.service';

@Component({
  selector: 'app-calificacion-proveedor-create',
  templateUrl: './calificacion-proveedor-create.component.html',
  styleUrls: ['./calificacion-proveedor-create.component.css']
})
export class CalificacionProveedorCreateComponent implements OnInit {

  public isCollapsed =true;
  calificacionForm: FormGroup;

  @Input() proveedor: Proveedor;

  constructor(
    private proveedorService:ProveedorService,
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
  newCalificacion.proveedor = this.proveedor;
  this.proveedorService.createCalificacion(this.proveedor.id, newCalificacion)
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
