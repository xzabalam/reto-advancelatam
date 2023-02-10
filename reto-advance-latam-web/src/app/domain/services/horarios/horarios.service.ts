import { HorarioDto } from './../../model/mapper/dto/horarios/horario_dto';
import { Observable } from 'rxjs';
import { HttpService } from './../http/http.service';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class HorariosService {
  urlPicoPlaca: string = '/pico-placa';

  constructor(private httpService: HttpService) {}

  public obtenerHorariosConfigurados(): Observable<any> {
    return this.httpService.get(this.urlPicoPlaca);
  }

  public guardarConfiguracionHorarios(data: HorarioDto) {
    return this.httpService.post(this.urlPicoPlaca, data);
  }

  public consultarCirculacionPlacaFecha(placa: string, fecha: string) {
    return this.httpService.getPublic(
      `${this.urlPicoPlaca}/verificar-circulacion/${placa}/${fecha}`
    );
  }
}
