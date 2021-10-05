import { Receta } from "../receta/receta";
import { Usuario } from "../usuario/usuario";
import { Proveedor } from "../Proveedor/proveedor";

export class Comentario {
  id: number;
  aprobado: boolean;
  comentario: string;
  positivo: boolean;
  receta: Receta;
  usuario: Usuario;
  proveedor:Proveedor;

  constructor(
    id?: number,
    aprobado?: boolean,
    comentario?: string,
    positivo?: boolean,
    receta?: Receta,
    usuario?: Usuario,
    proveedor?:Proveedor,
  ) {
    this.id = id;
    this.aprobado = aprobado;
    this.comentario = comentario;
    this.positivo = positivo;
    this.receta = receta;
    this.usuario = usuario;
    this.proveedor=proveedor;
  }
}
