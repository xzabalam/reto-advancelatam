import { Observable } from 'rxjs';
import { HttpService } from './../http/http.service';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class TipoAutoService {
  constructor(private httpService: HttpService) {}

  obtenerTipoAutos(): Observable<any> {
    return this.httpService.get('/tipo-auto');
  }
}
