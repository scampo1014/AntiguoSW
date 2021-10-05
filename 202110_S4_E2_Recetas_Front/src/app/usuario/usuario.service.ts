import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Usuario } from './usuario';
import { environment } from 'src/environments/environment';
import { UsuarioDetail } from './usuarioDetail';
import { Calificacion } from '../calificacion/calificacion';
import { Comentario } from '../comentario/comentario';
import { Receta } from '../receta/receta';

const calificaciones = '/calificaciones';
const comentarios = '/comentarios';
const recetas = '/recetas';

@Injectable({
  providedIn: 'root'
})
export class UsuarioService {
  private apiUrl = environment.baseUrl + 'usuarios';

  constructor(private http: HttpClient) { }

  getUsuarios(): Observable<Array<UsuarioDetail>>
  {
    return this.http.get<Array<UsuarioDetail>>(this.apiUrl);
  }

  getUsuarioDetail(usuarioId: number): Observable<UsuarioDetail> {
    return this.http.get<UsuarioDetail>(this.apiUrl + '/' + usuarioId);
  }

  createUsuario(usuario: Usuario): Observable<Usuario> {
    return this.http.post<Usuario>(this.apiUrl, usuario);
  }

  createCalificacion(usuarioId: number, calificacion: Calificacion): Observable<Calificacion> {
    return this.http.post<Calificacion>(this.apiUrl + '/' + usuarioId +  calificaciones, calificacion);
  }

  createComentario(usuarioId: number, comentario: Comentario): Observable<Comentario> {
    return this.http.post<Comentario>(this.apiUrl + '/' + usuarioId + comentarios, comentario);
  }
  createReceta(usuarioId: number, receta: Receta): Observable<Receta> {
    return this.http.post<Receta>(this.apiUrl + '/' + usuarioId +  recetas, receta);
  }
}
