import { environment } from './../../../../environments/environment';
import { UsuarioDto } from './../../model/mapper/dto/usuario/usuario_dto';
import { UsuarioMapper } from './../../model/mapper/usuario_mapper';
import { HttpErrorService } from './http-error.service';
import { Observable, catchError, throwError } from 'rxjs';
import { Router } from '@angular/router';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class HttpService {
  usuario: UsuarioDto | undefined;
  constructor(
    private http: HttpClient,
    public router: Router,
    public httpError: HttpErrorService
  ) {
    this.obtenerUsuario();
  }

  obtenerUsuario() {
    var jsonDatosUsuario = localStorage.getItem('datosUsuario');
    this.usuario =
      jsonDatosUsuario == null
        ? undefined
        : UsuarioMapper.toUsuarioDto(jsonDatosUsuario ?? '');
  }

  get(urlServicio: string): Observable<any>;

  get(urlServicio: string, token: string): Observable<any>;

  get(urlServicio: string, token?: string): Observable<any> {
    this.obtenerUsuario();

    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        Authorization: `Basic ${localStorage.getItem('token')}`,
      }),
    };

    return this.http
      .get(environment.urlApi + urlServicio, httpOptions)
      .pipe(catchError((error) => this.manageError(error)));
  }

  getPublic(urlServicio: string): Observable<any> {
    return this.http
      .get(environment.urlApi + urlServicio)
      .pipe(catchError((error) => this.manageError(error)));
  }

  post(urlServicio: string, datos: any) {
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        Authorization: `Basic ${localStorage.getItem('token')}`,
      }),
    };

    return this.http
      .post(environment.urlApi + urlServicio, datos, httpOptions)
      .pipe(catchError((error) => this.manageError(error)));
  }

  private manageError(error: any) {
    let errorMsg: string;
    if (error.error instanceof ErrorEvent) {
      errorMsg = `Error: ${error.error.message}`;
    } else if (
      error.error.message != undefined ||
      error.error.message != null
    ) {
      errorMsg = `Error: ${error.error.message}`;
    } else {
      errorMsg = this.httpError.getServerErrorMessage(error);
    }

    return throwError(() => new Error(errorMsg));
  }
}
