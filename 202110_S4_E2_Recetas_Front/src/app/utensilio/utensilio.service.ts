import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Utensilio } from './utensilio';

@Injectable({
  providedIn: 'root'
})
export class UtensilioService {

  private apiUrl = environment.baseUrl + 'utensilios';
  constructor(private http: HttpClient) { }
  getUtensilios(): Observable<Utensilio[]> {
    return this.http.get<Utensilio[]>(this.apiUrl);
  }

}
