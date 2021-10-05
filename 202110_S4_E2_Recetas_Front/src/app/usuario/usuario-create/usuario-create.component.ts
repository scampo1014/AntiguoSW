import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { UsuarioService } from '../usuario.service';
import { ToastrService } from 'ngx-toastr';
import { Router } from '@angular/router';
import { UsuarioDetail } from '../usuarioDetail';
import { Usuario } from '../usuario';

@Component({
  selector: 'app-usuario-create',
  templateUrl: './usuario-create.component.html',
  styleUrls: ['./usuario-create.component.css']
})
export class UsuarioCreateComponent implements OnInit {

  usuarioForm: FormGroup;

  constructor(
    private usuarioService: UsuarioService,
    private toastrService: ToastrService,
    private router: Router,
    private formBuilder: FormBuilder) { }

    createUsuario(usuario: Usuario) {
        this.usuarioService.createUsuario(usuario)
        .subscribe(u => {
          this.toastrService.success('El usuario fue creado exitosamente');
          this.toastrService.success('Bienvenido a recetas');
          this.usuarioForm.reset();
        }, err => {
          this.toastrService.error(err, 'Error');
        });
    }

    cancelCreation(): void {
      this.toastrService.warning('El usuario no se creó', 'Crear usuario');
      this.router.navigate(['/usuarios/listar']);
      this.usuarioForm.reset();
    }

  ngOnInit() {
    this.usuarioForm = this.formBuilder.group({
      nombre: ['', [Validators.required, Validators.minLength(2)]],
      login: ['', [Validators.required,, Validators.minLength(2)]],
      correo: ['', [Validators.required,, Validators.email]],
      contrasenia: ['', [Validators.required, Validators.minLength(8)]],
      foto: ['https://image.freepik.com/vector-gratis/chef-mujer-trabajadora-avatar_18591-58459.jpg', []]
    })
  }



}
