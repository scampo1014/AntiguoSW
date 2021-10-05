export class Video {
  id: number;
  formato: string;
  direccion: string;
  tamanio: number;
  duracion: number;

  constructor(
    id: number,
    formato: string,
    direccion: string,
    tamanio: number,
    duracion: number,
  ) {
    this.id = id;
    this.formato = formato;
    this.direccion = direccion;
    this.tamanio = tamanio;
    this.duracion = duracion;
  }
}
