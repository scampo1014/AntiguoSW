import { Receta } from "../receta/receta";
import { Usuario } from "./usuario";
import { Comentario } from '../comentario/comentario';
import { Calificacion } from '../calificacion/calificacion';

export class UsuarioDetail extends Usuario {

  recetas: Array<Receta>;
  comentarios: Array<Comentario>;
  calificaciones: Array<Calificacion>;
}