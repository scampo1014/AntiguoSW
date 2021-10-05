import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ComentarioDetail } from './comentarioDetail';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ComentarioService {
  private apiUrl = environment.baseUrl + 'comentarios';
  constructor(private http: HttpClient) { }
  getComentarios(): Observable<ComentarioDetail[]> {
    return this.http.get<ComentarioDetail[]>(this.apiUrl);
  }

}
