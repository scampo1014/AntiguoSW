import {Receta} from './receta';
import {Usuario} from '../usuario/usuario';
import {Ingrediente} from '../ingrediente/ingrediente';
import {Utensilio} from '../utensilio/utensilio';
import {Foto} from '../foto/foto';
import {Video} from '../video/video';
import {Comentario} from '../comentario/comentario';
import {Calificacion} from '../calificacion/calificacion';


export class RecetaDetail extends Receta{
    ingredientes: Array<Ingrediente>;
    utensilios: Array<Utensilio>;
    fotos?: Array<Foto>;
    videos: Array<Video>;
    comentarios: Array<Comentario>;
    calificaciones: Array<Calificacion>;

    // constructor(
    //     ingredientes: Ingrediente[],
    //     utensilios: Utensilio[],
    //     fotos: Foto[],
    //     videos: Video[],
    //     comentarios: Comentario[],
    //     calificaciones: Calificacion[]){

    //     super();
    //     this.ingredientes= ingredientes;
    //     this.utensilios=utensilios;
    //     this.fotos=fotos;
    //     this.videos=videos;
    //     this.calificaciones=calificaciones;
    //     this.comentarios=comentarios;
    //     }
}
