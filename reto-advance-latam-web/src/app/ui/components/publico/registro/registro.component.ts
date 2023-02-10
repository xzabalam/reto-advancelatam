import { TipoAutoDto } from './../../../../domain/model/mapper/dto/auto/tipo_auto_dto';
import { MyErrorStateMatcher } from './../../../../domain/util/error-state-matcher';
import { AutoService } from './../../../../domain/services/auto/auto.service';
import {
  AutosDto,
  Content,
} from './../../../../domain/model/mapper/dto/auto/autos_dto';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AfterViewInit, Component, ViewChild } from '@angular/core';
import { finalize } from 'rxjs';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';

@Component({
  selector: 'app-registro',
  templateUrl: './registro.component.html',
  styleUrls: ['./registro.component.css'],
})
export class RegistroComponent {
  isLoading: boolean = false;
  registroForm: FormGroup;
  matcher = new MyErrorStateMatcher();
  errorMsg: string = '';
  paginaAuto: AutosDto | undefined;
  autos: Content[] = [];
  tipoAuto: TipoAutoDto[] = [];
  mostrarRegistro: boolean = false;
  displayedColumns: string[] = [
    'id',
    'placa',
    'modelo',
    'chasis',
    'color',
    'idTipoAuto',
  ];

  constructor(
    private formBuilder: FormBuilder,
    private autoService: AutoService
  ) {
    this.registroForm = this.formBuilder.group({
      placa: ['', [Validators.required]],
      modelo: ['', [Validators.required]],
      chasis: ['', [Validators.required]],
      color: ['', [Validators.required]],
      idTipoAuto: ['', [Validators.required]],
    });

    this.obtenerListadoTiposAutos();
  }

  get registerFormControl() {
    return this.registroForm.controls;
  }

  public onClickNuevoAuto() {
    this.mostrarRegistro = true;
  }

  public onClickCancelarRegistro() {
    this.mostrarRegistro = false;
  }

  public getNombreTipoAuto(idTipoAuto: number) {
    const item = this.tipoAuto.find((auto) => auto.id === idTipoAuto);
    return item ? item.tipo : 'No se encontró un item con ese id';
  }

  public onClickRegistrarAuto() {
    this.isLoading = true;
    this.mostrarRegistro = true;

    if (this.registroForm.valid) {
      this.autoService
        .guardarAuto(this.registroForm.value)
        .pipe(
          finalize(() => {
            this.obtenerListadoAutos(0, 100);
          })
        )
        .subscribe({
          next: (data) => {
            this.mostrarRegistro = false;
            this.isLoading = false;
            this.registroForm.reset();
          },
          error: (error) => {
            this.errorMsg = error.message;
            this.isLoading = false;
          },
        });
    }
  }

  private obtenerListadoTiposAutos() {
    this.autoService
      .obtenerListadoTiposAuto()
      .pipe(
        finalize(() => {
          this.obtenerListadoAutos(0, 100);
        })
      )
      .subscribe({
        next: (data) => {
          this.tipoAuto = data;
          this.isLoading = false;
        },
        error: (error) => {
          this.errorMsg = error.message;
          this.isLoading = false;
        },
      });
  }

  private obtenerListadoAutos(page: number, size: number) {
    this.autoService.obtenerListadoAutos(page, size).subscribe({
      next: (data) => {
        this.paginaAuto = data;
        if (this.paginaAuto != undefined) {
          if (this.paginaAuto.content.length == 0) {
            this.mostrarRegistro = true;
          } else {
            this.autos = this.paginaAuto.content;
          }
        }
        this.isLoading = false;
      },
      error: (error) => {
        this.errorMsg = error.message;
        this.isLoading = false;
      },
    });
  }
}
