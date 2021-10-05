import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { ToastrService } from "ngx-toastr";
import { Comentario } from '../../comentario/comentario';
import { Usuario } from '../usuario';
import { UsuarioService } from '../usuario.service';

@Component({
  selector: 'app-usuario-comentario-create',
  templateUrl: './usuario-comentario-create.component.html',
  styleUrls: ['./usuario-comentario-create.component.scss']
})
export class UsuarioComentarioCreateComponent implements OnInit {
  public isCollapsed = true;
  comentarioForm: FormGroup;

  @Input() usuario: Usuario;

  constructor(
    private usuarioService: UsuarioService,
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
    newComentario.usuario = this.usuario;
    this.usuarioService.createComentario(this.usuario.id, newComentario)
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
