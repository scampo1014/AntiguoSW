import {Comentario} from './comentario';
import {Calificacion} from '../calificacion/calificacion';

export class ComentarioDetail extends Comentario {

  calificaciones: Array<Calificacion>;

  constructor(
    calificaciones: Calificacion[]
  ){
    super();
    this.calificaciones = calificaciones;
  }
}
