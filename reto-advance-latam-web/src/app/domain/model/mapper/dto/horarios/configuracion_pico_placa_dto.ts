export interface ConfiguracionesPicoPlacaDto {
  puedeCircular: boolean;
  autoTo: AutoTo;
  configuracion: Configuracion[];
}

export interface AutoTo {
  id: number;
  placa: string;
  modelo: string;
  chasis: string;
  color: string;
  idTipoAuto: number;
}

export interface Configuracion {
  id: null;
  diaSemana: string;
  horaInicio: string;
  horaFin: string;
  resticcionUltimoDigito: number[];
}
