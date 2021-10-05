import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { ToastrService } from "ngx-toastr";
import { Calificacion } from 'src/app/calificacion/calificacion';
import { Usuario } from 'src/app/usuario/usuario';
import { UsuarioService } from 'src/app/usuario/usuario.service';

@Component({
  selector: 'app-calificacion-usuario-create',
  templateUrl: './calificacion-usuario-create.component.html',
  styleUrls: ['./calificacion-usuario-create.component.css']
})
export class CalificacionUsuarioCreateComponent implements OnInit {

  public isCollapsed =true;
  calificacionForm: FormGroup;

  @Input() usuario: Usuario;

  constructor(
    private usuarioService:UsuarioService,
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
  newCalificacion.usuario = this.usuario;
  this.usuarioService.createCalificacion(this.usuario.id, newCalificacion)
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
