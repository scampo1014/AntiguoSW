import { Receta } from '../receta/receta';

export class Utensilio {

  id: number;
  nombre: string;
  precio: number;
  descripcion: string;
  receta: Receta;

  constructor(id: number, nombre: string, precio: number, descripcion: string, receta: Receta)
  {
    this.id = id;
    this.nombre = nombre;
    this.precio = precio;
    this.descripcion = descripcion;
    this.receta= receta;

  }
}
