import { HttpService } from './../http/http.service';
import { Injectable } from '@angular/core';
import { Content } from '../../model/mapper/dto/auto/autos_dto';

@Injectable({
  providedIn: 'root',
})
export class AutoService {
  constructor(private httpService: HttpService) {}

  public obtenerListadoAutos(pagina: number, tamanoPagina: number) {
    return this.httpService.getPublic(
      `/auto/page?page=${pagina ?? 0}&size=${tamanoPagina ?? 100}`
    );
  }

  public obtenerListadoTiposAuto() {
    return this.httpService.getPublic('/tipo-auto');
  }

  public guardarAuto(data: Content) {
    return this.httpService.post('/auto', data);
  }
}
