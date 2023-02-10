import { ConfiguracionesPicoPlacaDto } from './../../../../domain/model/mapper/dto/horarios/configuracion_pico_placa_dto';
import { HorariosService } from './../../../../domain/services/horarios/horarios.service';
import { MyErrorStateMatcher } from './../../../../domain/util/error-state-matcher';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Component } from '@angular/core';
import { NgxMatDatetimePicker } from '@angular-material-components/datetime-picker/public-api';

@Component({
  selector: 'app-circulacion',
  templateUrl: './circulacion.component.html',
  styleUrls: ['./circulacion.component.css'],
})
export class CirculacionComponent {
  isLoading: boolean = false;
  configuracion: ConfiguracionesPicoPlacaDto | undefined;
  circulacionForm: FormGroup;
  matcher = new MyErrorStateMatcher();
  errorMsg: string = '';
  fechaMinima = new Date();
  picker!: NgxMatDatetimePicker<any>;

  constructor(
    private formBuilder: FormBuilder,
    private horarioService: HorariosService
  ) {
    this.circulacionForm = this.formBuilder.group({
      placa: ['', [Validators.required]],
      fecha: ['', [Validators.required]],
    });
  }

  get registerFormControl() {
    return this.circulacionForm.controls;
  }

  public onClickConsultarCirculacion() {
    this.isLoading = true;

    this.configuracion = undefined;
    this.horarioService
      .consultarCirculacionPlacaFecha(
        this.circulacionForm.value.placa,
        this.circulacionForm.value.fecha.toISOString()
      )
      .subscribe({
        next: (data) => {
          console.log(data);
          this.configuracion = data;
          this.isLoading = false;
        },
        error: (error) => {
          this.errorMsg = error.message;
          this.isLoading = false;
        },
      });
  }
}
