//import { Receta } from "../receta/receta";

export class Usuario {
  id: number;
  nombre: string;
  login: string;
  correo: string;
  contrasenia: string;
  foto: string;

  constructor(id?: number, nombre?:string, login?: string, correo?: string, contrasenia?: string, foto?: string)
  {
    this.id = id;
    this.nombre = nombre;
    this.login = login;
    this.correo = correo;
    this.contrasenia = contrasenia;
    this.foto = foto;
  }

}
