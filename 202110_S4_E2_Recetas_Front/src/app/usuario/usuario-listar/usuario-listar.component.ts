import { Component, Input, OnInit } from '@angular/core';
import { Usuario } from '../usuario';
import { UsuarioDetail } from '../usuarioDetail';
import { UsuarioService } from '../usuario.service';
import { ActivatedRoute } from '@angular/router';

import { filter } from 'rxjs/operators';
import { ConstantPool } from '@angular/compiler';

@Component({
  selector: 'app-usuario-listar',
  templateUrl: './usuario-listar.component.html',
  styleUrls: ['./usuario-listar.component.css']
})

export class UsuarioListarComponent implements OnInit {

  @Input() usuarios: Array<UsuarioDetail> = [];

  allUsuarios:string;

  constructor(private usuarioService: UsuarioService, private route: ActivatedRoute) { }

  //selectedUsuario!: Usuario;
  //selected = false;

  getUsuarios():void{
    this.usuarioService.getUsuarios().subscribe(usuarios => {this.usuarios = usuarios;});
  }

/*   onSelected(u: Usuario): void {
    this.selected = true;
    this.selectedUsuario = u;
  } */

  ngOnInit() {

/*      this.route.queryParams.pipe(
      filter(params => params.allUsuarios)
    ).subscribe(params => {
      this.allUsuarios = params.allUsuarios;
    });

    if(this.allUsuarios === "true"){
      this.getUsuarios;
    } */
    this.getUsuarios();
  }

}
