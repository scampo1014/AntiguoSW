
export class Proveedor {
  id: number;
  nombre: string;
  login: string;
  correo: string;
  contrasenia: string;

  constructor(
    id?: number,
  nombre?:string,
  login?: string,
  correo?: string,
  contrasenia?: string
  )
  {
    this.id=id;
    this.nombre = nombre;
    this.login = login;
    this.correo = correo;
    this.contrasenia = contrasenia;
  }
}
