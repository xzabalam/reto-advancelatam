import { UsersService } from './../../../domain/services/users/users.service';
import { UsuarioDto } from './../../../domain/model/mapper/dto/usuario/usuario_dto';
import { Component } from '@angular/core';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css'],
})
export class HomeComponent {
  // Datos del usuario para la barra de herramientas.
  datosUsuarioDto: UsuarioDto;

  constructor(private userService: UsersService) {
    this.datosUsuarioDto = userService.getDatosUsuario();
  }

  logout() {
    this.userService.logout();
  }
}
