import { Receta } from '../receta/receta';

export class Ingrediente {

  id: number;
  nombre: string;
  precio: number;
  cantidad: number;
  receta: Receta;

  constructor(id: number, nombre: string, precio: number, cantidad: number, receta: Receta)
  {
    this.id = id;
    this.nombre = nombre;
    this.precio = precio;
    this.cantidad = cantidad;
    this.receta= receta;
  }
}
