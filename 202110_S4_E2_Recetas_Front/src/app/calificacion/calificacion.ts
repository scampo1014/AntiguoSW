import { Receta } from "../receta/receta";
import { Usuario } from '../usuario/usuario';
import { Comentario } from '../comentario/comentario';
import { Proveedor } from '../Proveedor/proveedor';

export class Calificacion{
  id: number;
  calificador: string;
  puntos: number;
  receta: Receta;
  usuario: Usuario;
  comentario: Comentario;
  proveedor: Proveedor;

  constructor(
  id: number,
  calificador: string,
  puntos: number,
  receta?: Receta,
  usuario?: Usuario,
  comentario?: Comentario,
  proveedor?: Proveedor
  )
  {
    this.id= id;
    this.calificador= calificador;
    this.puntos=puntos;
    this.receta=receta;
    this.usuario=usuario;
    this.comentario=comentario;
    this.proveedor= proveedor;
  }
}

