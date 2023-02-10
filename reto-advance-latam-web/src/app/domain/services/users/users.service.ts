import { HttpErrorService } from './../http/http-error.service';
import { environment } from './../../../../environments/environment';
import { HttpHeaders, HttpClient } from '@angular/common/http';
import { HttpService } from './../http/http.service';
import { UsuarioMapper } from './../../../domain/model/mapper/usuario_mapper';
import { Router } from '@angular/router';
import { Injectable } from '@angular/core';
import { Observable, catchError, throwError } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class UsersService {
  constructor(
    private httpService: HttpService,
    private router: Router,
    private http: HttpClient,
    public httpError: HttpErrorService
  ) {}

  public login(user: any): Observable<any> {
    var token = window.btoa(`${user.username}:${user.password}`);
    localStorage.setItem('token', token);

    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        Authorization: `Basic ${token ?? localStorage.getItem('token')}`,
      }),
    };

    return this.http.get(environment.urlApi + '/auth', httpOptions).pipe(
      catchError((error) => {
        let errorMsg: string;
        if (error.error instanceof ErrorEvent) {
          errorMsg = `Error: ${error.error.message}`;
        } else {
          errorMsg = this.httpError.getServerErrorMessage(error);
        }

        return throwError(() => new Error(errorMsg));
      })
    );
  }

  public isAuthenticated(): boolean {
    const token = localStorage.getItem('token');
    const datosUsuario = localStorage.getItem('datosUsuario');

    return (
      token != null &&
      token != undefined &&
      datosUsuario != null &&
      datosUsuario != undefined
    );
  }

  public authenticate(datosUsuario: string) {
    localStorage.setItem('datosUsuario', datosUsuario);
  }

  public logout() {
    localStorage.clear();
    this.router.navigate(['login']);
  }

  public getToken() {
    return localStorage.getItem('token');
  }

  public getDatosUsuario(): any {
    var jsonDatosUsuario = localStorage.getItem('datosUsuario');
    return jsonDatosUsuario == null
      ? null
      : UsuarioMapper.toUsuarioDto(jsonDatosUsuario ?? '');
  }
}
