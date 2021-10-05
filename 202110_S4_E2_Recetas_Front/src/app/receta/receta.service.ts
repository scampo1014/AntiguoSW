import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { RecetaDetail } from './recetaDetail';
import { environment } from '../../environments/environment';
import { Calificacion } from '../calificacion/calificacion';
import { Comentario } from '../comentario/comentario';
import { Ingrediente } from '../ingrediente/ingrediente';
import { Utensilio } from '../utensilio/utensilio';
import { Foto } from '../foto/foto';

const calificaciones = '/calificaciones';
const comentarios = '/comentarios';
const ingredientes = '/ingredientes';
const utensilios = '/utensilios';
const fotos = '/fotos';

@Injectable({
  providedIn: 'root'
})
export class RecetaService {

  private apiUrl= environment.baseUrl+'recetas';

  constructor(private http: HttpClient) { }
  getRecetas(): Observable<Array<RecetaDetail>>
  {
    return this.http.get<Array<RecetaDetail>>(this.apiUrl);
  }

  getRecetaDetail(recetaId): Observable<RecetaDetail> {
    return this.http.get<RecetaDetail>(this.apiUrl + '/' + recetaId)
  }

  createCalificacion(recetaId: number, calificacion: Calificacion): Observable<Calificacion> {
    return this.http.post<Calificacion>(this.apiUrl + '/' + recetaId +  calificaciones, calificacion);
  }

  createComentario(recetaId: number, comentario: Comentario): Observable<Comentario> {
    return this.http.post<Comentario>(this.apiUrl + '/' + recetaId + comentarios, comentario);
  }
  createIngrediente(recetaId: number, ingrediente: Ingrediente): Observable<Ingrediente> {
    return this.http.post<Ingrediente>(this.apiUrl + '/' + recetaId + ingredientes, ingrediente);
  }
  createUtensilio(recetaId: number, utensilio: Utensilio): Observable<Utensilio> {
    return this.http.post<Utensilio>(this.apiUrl + '/' + recetaId + utensilios, utensilio);
  }
  createFoto(recetaId: number, foto: Foto): Observable<Foto> {
    return this.http.post<Foto>(this.apiUrl + '/' + recetaId + fotos, foto);
  }
}

