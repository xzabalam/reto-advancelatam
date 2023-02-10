import { MyErrorStateMatcher } from './../../../../domain/util/error-state-matcher';
import { HorarioDto } from './../../../../domain/model/mapper/dto/horarios/horario_dto';
import { HorariosService } from './../../../../domain/services/horarios/horarios.service';
import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-horarios',
  templateUrl: './horarios.component.html',
  styleUrls: ['./horarios.component.css'],
})
export class HorariosComponent {
  isLoading: boolean = false;
  listadoHorarios: HorarioDto[] = [];
  horariosForm: FormGroup;
  matcher = new MyErrorStateMatcher();
  errorMsg: string = '';
  mostrarRegistro: boolean = false;
  displayedColumns: string[] = [
    'diaSemana',
    'horaInicio',
    'horaFin',
    'resticcionUltimoDigito',
  ];
  listadoDiasSemana: String[] = [
    'LUNES',
    'MARTES',
    'MIERCOLES',
    'JUEVES',
    'VIERNES',
    'SABADO',
    'DOMINGO',
  ];
  listadoPlacas: Number[] = [0, 1, 2, 3, 4, 5, 6, 7, 8, 9];
  horas: string[] = [];

  constructor(
    private horarioService: HorariosService,
    private formBuilder: FormBuilder
  ) {
    this.obtenerTodosLosHorariosConfigurados();
    this.horariosForm = this.formBuilder.group({
      diaSemana: ['', [Validators.required]],
      horaInicio: ['', [Validators.required]],
      horaFin: ['', [Validators.required]],
      resticcionUltimoDigito: ['', [Validators.required]],
    });
    for (let i = 0; i < 24; i++) {
      let hour = i.toString().padStart(2, '0') + ':00';
      this.horas.push(hour);
    }
  }

  get registerFormControl() {
    return this.horariosForm.controls;
  }

  private obtenerTodosLosHorariosConfigurados() {
    this.horarioService.obtenerHorariosConfigurados().subscribe({
      next: (data) => {
        this.listadoHorarios = data;

        if (data.length == 0) {
          this.mostrarRegistro = true;
        }
      },
      error: (error) => {
        this.errorMsg = error.message;
        console.log(error);
      },
    });
  }

  public onClickNuevaConfiguracion() {
    this.mostrarRegistro = true;
  }

  public onClickCancelarRegistro() {
    this.mostrarRegistro = false;
  }

  public onClickGuardarConfiguracion() {
    this.isLoading = true;
    this.mostrarRegistro = true;
    if (this.horariosForm.valid) {
      this.horarioService
        .guardarConfiguracionHorarios(this.horariosForm.value)
        .subscribe({
          next: (data) => {
            this.obtenerTodosLosHorariosConfigurados();
            this.mostrarRegistro = false;
            this.isLoading = false;
            this.horariosForm.reset();
          },
          error: (error) => {
            this.errorMsg = error.message;
            console.log(error);
            this.isLoading = false;
          },
        });
    }
  }
}
