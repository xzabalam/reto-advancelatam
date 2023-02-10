import { UsersService } from '../users/users.service';
import { Router } from '@angular/router';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class AuthGuardService {
  constructor(public auth: UsersService, public router: Router) {}

  canActivate(): boolean {
    var roles: string[] =
      this.auth.getDatosUsuario() == null
        ? []
        : this.auth.getDatosUsuario().roles;

    if (
      !this.auth.isAuthenticated() &&
      !(roles.includes('ROLE_ADMINISTRADOR') || roles.includes('ROLE_USUARIO'))
    ) {
      this.auth.logout();
      this.router.navigate(['login']);
      return false;
    }
    return true;
  }
}
