import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable} from 'rxjs';
import { Foto } from './foto';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class FotoService {
  private apiUrl = environment.baseUrl + 'fotos';
  constructor(private http: HttpClient) { }
  getFotos(): Observable<Foto[]> {
    return this.http.get<Foto[]>(this.apiUrl);
  }

}
