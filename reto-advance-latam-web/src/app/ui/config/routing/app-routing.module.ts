import { CirculacionComponent } from '../../components/publico/circulacion/circulacion.component';
import { RegistroComponent } from '../../components/publico/registro/registro.component';
import { HorariosComponent } from './../../components/admin/horarios/horarios.component';
import { UsuariosComponent } from './../../components/admin/usuarios/usuarios.component';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HomeComponent } from './../../components/home/home.component';
import { LoginComponent } from '../../components/publico/login/login.component';
import { AuthGuardService } from '../../../domain/services/auth/auth-guard.service';

const routes: Routes = [
  {
    path: 'home',
    component: HomeComponent,
    pathMatch: 'full',
  },
  { path: 'login', component: LoginComponent, pathMatch: 'full' },
  {
    path: 'usuarios',
    component: UsuariosComponent,
    pathMatch: 'full',
    canActivate: [AuthGuardService],
  },
  {
    path: 'horarios',
    component: HorariosComponent,
    pathMatch: 'full',
    canActivate: [AuthGuardService],
  },
  { path: 'registro', component: RegistroComponent, pathMatch: 'full' },
  { path: 'circulacion', component: CirculacionComponent, pathMatch: 'full' },
  { path: '**', redirectTo: 'circulacion', pathMatch: 'full' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
