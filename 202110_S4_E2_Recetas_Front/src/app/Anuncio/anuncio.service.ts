import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Anuncio } from './anuncio';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AnuncioService {
  private apiUrl = environment.baseUrl + 'proveedores/100/anuncios';

  constructor(private http: HttpClient) { }

  getAnuncios(): Observable<Anuncio[]>
  {
    return this.http.get<Anuncio[]>(this.apiUrl);
  }

}
