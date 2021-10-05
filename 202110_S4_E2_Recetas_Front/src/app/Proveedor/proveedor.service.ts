import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Proveedor } from './proveedor';
import { ProveedorDetail } from './proveedorDetail';
import { environment } from 'src/environments/environment';
import { Calificacion } from '../calificacion/calificacion';
import { Comentario } from '../comentario/comentario';

const calificaciones = '/calificaciones';


const comentarios = '/comentarios';

@Injectable({
  providedIn: 'root'
})
export class ProveedorService {
  private apiUrl = environment.baseUrl + 'proveedores';

  constructor(private http: HttpClient) { }

  getProveedores(): Observable<ProveedorDetail[]>
  {
    return this.http.get<ProveedorDetail[]>(this.apiUrl);
  }

  getProveedorDetail(proveedorId): Observable<ProveedorDetail> {
    return this.http.get<ProveedorDetail>(this.apiUrl + '/' + proveedorId)
  }

  createComentario(proveedorId: number, comentario: Comentario): Observable<Comentario> {
    return this.http.post<Comentario>(this.apiUrl + '/' + proveedorId + comentarios, comentario);
  }

  createCalificacion(proveedorId: number, calificacion: Calificacion): Observable<Calificacion> {
    return this.http.post<Calificacion>(this.apiUrl + '/' + proveedorId +  calificaciones, calificacion);
  }

}
