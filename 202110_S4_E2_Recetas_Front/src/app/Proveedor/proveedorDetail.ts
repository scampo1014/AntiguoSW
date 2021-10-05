import { Anuncio } from "../Anuncio/anuncio";
import { Proveedor } from "./proveedor";
import { Comentario } from '../comentario/comentario';
import { Calificacion } from '../calificacion/calificacion';

export class ProveedorDetail extends Proveedor {

  anuncios:Array<Anuncio>;
  comentarios: Array<Comentario>;
  calificaciones: Array<Calificacion>;
}
