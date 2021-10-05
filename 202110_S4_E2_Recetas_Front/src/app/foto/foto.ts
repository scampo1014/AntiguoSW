//import { Usuario } from "../usuario/usuario";
import { Receta } from "../receta/receta";


export class Foto {
  id: number;
  formato: string;
  direccion: string;
  tamanio: number;
  receta: Receta;

  constructor(
    id: number,
    formato: string,
    direccion: string,
    tamanio: number,
    receta: Receta
  ) {
    this.id = id;
    this.formato = formato;
    this.direccion = direccion;
    this.tamanio = tamanio;
    this.receta= receta;
  }
}
