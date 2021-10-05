import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { MetodoDePago } from './metodoDePago';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class MetodoDePagoService {
  private apiUrl = environment.baseUrl + 'proveedores/100/metodosPago';

  constructor(private http: HttpClient) { }

  getMetodosDePago(): Observable<MetodoDePago[]>
  {
    return this.http.get<MetodoDePago[]>(this.apiUrl);
  }

}
