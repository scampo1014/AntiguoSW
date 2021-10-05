import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, FormArray, FormControl } from "@angular/forms";
import { Usuario } from '../usuario';
import { UsuarioService } from '../usuario.service';
import { ToastrService } from 'ngx-toastr';
import { Receta } from 'src/app/receta/receta';
import { Ingrediente } from 'src/app/ingrediente/ingrediente';
import { Utensilio } from 'src/app/utensilio/utensilio';
import { UtensilioService } from 'src/app/utensilio/utensilio.service';
import { IngredienteService } from 'src/app/ingrediente/ingrediente.service';
import { RecetaDetail } from 'src/app/receta/recetaDetail';
import { FotoService } from 'src/app/foto/foto.service';
import { VideoService } from 'src/app/video/video.service';
import { Foto } from 'src/app/foto/foto';
import { Video } from 'src/app/video/video';

@Component({
  selector: 'app-receta-usuario-create',
  templateUrl: './receta-usuario-create.component.html',
  styleUrls: ['./receta-usuario-create.component.css']
})
export class RecetaUsuarioCreateComponent implements OnInit {

  public isCollapsed =true;
  recetaForm: FormGroup;

  @Input() usuario: Usuario;

  constructor(
    private usuarioService:UsuarioService,
    private toastrService: ToastrService,
    private formBuilder: FormBuilder
  ) { }

  ngOnInit() {
    this.recetaForm = this.formBuilder.group({
      nombre: ['', [Validators.required, Validators.minLength(5)]],
      cantIngredientes: ["", [Validators.required, Validators.min(1)]],
      descripcion: ['', [Validators.required, Validators.minLength(10)]],
      calorias: ['', [Validators.required, Validators.min(1)]],
      tiempoPrep: ['', [Validators.required, Validators.minLength(3)]],
      dificultad: ['', [Validators.required, Validators.minLength(5)]],
      //popular: ['', [Validators.required, Validators.minLength(2)]]
  })
}

createReceta(newReceta: RecetaDetail) {
  newReceta.usuario = this.usuario;
  this.usuarioService.createReceta(this.usuario.id, newReceta)
    .subscribe(r => {
      this.toastrService.success('La receta fue creada');
      this.recetaForm.reset();
    },err => {
      this.toastrService.error(err, 'Error');
    })
  // Process checkout data here
  console.warn("La receta fue creada", newReceta);
}

cancelCreation() {
  console.log("Cancelando ...");
  this.toastrService.warning('La receta no fue creada', 'Receta creation');
  this.recetaForm.reset();
}

}
