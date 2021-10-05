import { Usuario } from "../usuario/usuario";
/*import { Ingrediente } from '../ingrediente/ingrediente';
import { Utensilio } from '../utensilio/utensilio';
import { Foto } from '../foto/foto';
import { Video } from '../video/video';
import { Comentario } from '../comentario/comentario';
import { Calificacion } from '../calificacion/calificacion';*/

export class Receta {
  id: number;
  nombre: string;
  cantIngredientes: number;
  descripcion: string;
  calorias: number;
  tiempoPrep: string;
  dificultad: string;
  popular: boolean;
  usuario: Usuario;

  constructor(id?: number,
    nombre?: string,
    cantIngredientes?: number,
    descripcion?: string,
    calorias?: number,
    tiempoPrep?: string,
    dificultad?: string,
    popular?: boolean,
    usuario?: Usuario,
    ){

    this.id= id;
    this.nombre=nombre;
    this.cantIngredientes=cantIngredientes;
    this.descripcion=descripcion;
    this.calorias= calorias;
    this.tiempoPrep=tiempoPrep;
    this.dificultad=dificultad;
    this.popular=popular;
    this.usuario=usuario;
    }
}
