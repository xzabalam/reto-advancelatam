import { HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class HttpErrorService {
  constructor() {}

  getServerErrorMessage(error: HttpErrorResponse): string {
    switch (error.status) {
      case 401: {
        return 'Usuario no autorizado. Es posible que las credenciales no sean las correctas.';
      }
      case 404: {
        return 'No existe el recurso';
      }
      case 403: {
        return 'Acceso denegado';
      }
      case 500: {
        return 'Error en el servidor';
      }
      default: {
        return 'Error desconocido';
      }
    }
  }
}
