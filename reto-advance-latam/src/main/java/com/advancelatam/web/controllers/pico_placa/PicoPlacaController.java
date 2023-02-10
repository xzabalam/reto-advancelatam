package com.advancelatam.web.controllers.pico_placa;

import com.advancelatam.bussines.services.auto.AutoService;
import com.advancelatam.bussines.services.pico_placa.PicoPlacaService;
import com.advancelatam.common.dto.AutoTo;
import com.advancelatam.common.dto.CirculacionTo;
import com.advancelatam.common.dto.PicoPlacaTo;
import com.advancelatam.common.exception.auto.AutoException;
import com.advancelatam.common.util.Roles;
import com.advancelatam.data.entities.pico_placa.PicoPlaca;
import com.advancelatam.data.enums.DiasSemanaEnum;
import com.advancelatam.web.mapper.AutoMapper;
import com.advancelatam.web.mapper.PicoPlacaMapper;
import com.advancelatam.web.util.RestPreconditions;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/pico-placa")
public class PicoPlacaController {

    private final PicoPlacaService picoPlacaService;
    private final AutoService autoService;

    public PicoPlacaController(PicoPlacaService picoPlacaService, AutoService autoService) {
        this.picoPlacaService = picoPlacaService;
        this.autoService = autoService;
    }

    @PreAuthorize("hasRole('" + Roles.ROLE_ADMIN + "')")
    @DeleteMapping("/id/{idPicoPlaca}")
    @Operation(summary = "Eliminar una configuracion del pico y placa por id")
    public ResponseEntity<Void> deletePicoPlaca(@Valid @NotNull @PathVariable("idPicoPlaca") Long idPicoPlaca) {
        RestPreconditions.checkNull(idPicoPlaca);
        picoPlacaService.deletePicoPlaca(idPicoPlaca);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('" + Roles.ROLE_ADMIN + "')")
    @DeleteMapping("/{dia}")
    @Operation(summary = "Eliminar una configuracion del pico y placa por dia")
    public ResponseEntity<Void> deletePicoPlacaPorDia(@Valid @NotNull @PathVariable("dia") DiasSemanaEnum dia) {
        RestPreconditions.checkNull(dia);
        picoPlacaService.deletePicoPlacaPorDia(dia);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('" + Roles.ROLE_ADMIN + "')")
    @PostMapping
    @Operation(summary = "Permite crear una nueva configuración para pico y placa.")
    public ResponseEntity<PicoPlacaTo> createPicoPlaca(
            @Valid @NotNull @RequestBody PicoPlacaTo picoPlacaTo,
            Authentication authentication) {
        RestPreconditions.checkNull(picoPlacaTo);

        PicoPlaca picoPlacaCreado = picoPlacaService.createPicoPlaca(picoPlacaTo, authentication.getName());

        return new ResponseEntity<>(PicoPlacaMapper.toTo(picoPlacaCreado), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('" + Roles.ROLE_ADMIN + "')")
    @GetMapping("/id/{idPicoPlaca}")
    @Operation(summary = "Permite buscar una configuracion de pico y placa por su id")
    public ResponseEntity<PicoPlacaTo> getAllById(@PathVariable("idPicoPlaca") Long idPicoPlaca) {
        RestPreconditions.checkNull(idPicoPlaca);
        PicoPlaca picoPlaca = picoPlacaService.getById(idPicoPlaca);
        return new ResponseEntity<>(PicoPlacaMapper.toTo(picoPlaca), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('" + Roles.ROLE_ADMIN + "')")
    @GetMapping("/{dia}")
    @Operation(summary = "Permite buscar una configuracion de pico y placa por su id")
    public ResponseEntity<List<PicoPlacaTo>> getAllByDia(@PathVariable("dia") DiasSemanaEnum dia) {
        RestPreconditions.checkNull(dia);
        List<PicoPlaca> picoPlaca = picoPlacaService.getByDiaSemana(dia);
        return new ResponseEntity<>(picoPlaca.stream().map(configuracion -> PicoPlacaMapper.toTo(configuracion)).collect(Collectors.toList()),
                HttpStatus.OK);

    }

    @PreAuthorize("hasRole('" + Roles.ROLE_ADMIN + "') or hasRole('" + Roles.ROLE_USER + "')")
    @GetMapping
    @Operation(summary = "Permite buscar todas las configuracion de pico y placa")
    public ResponseEntity<List<PicoPlacaTo>> getAll() {
        List<PicoPlacaTo> picoPlaca = picoPlacaService.getAll();
        return new ResponseEntity<>(picoPlaca, HttpStatus.OK);
    }

    @GetMapping("/verificar-circulacion/{placa}/{fecha}")
    @Operation(summary = "Verifica si una placa puede circular en una fecha indicada.")
    public ResponseEntity<CirculacionTo> verificarCirculacion(
            @PathVariable("placa") String placa,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            @PathVariable("fecha") LocalDateTime fecha) {
        RestPreconditions.checkNull(placa);
        RestPreconditions.checkNull(fecha);
        String placaMayuscula = placa.trim().toUpperCase();
        CirculacionTo circulacionTo = picoPlacaService.verificarCirculacion(placaMayuscula, fecha);
        circulacionTo.setAutoTo(generarAuto(placaMayuscula));
        return new ResponseEntity<>(circulacionTo, HttpStatus.OK);
    }

    /**
     * Debido a que se pide la informaci[on del auto, se debe verificar si existe registrado o no
     * Si no est[a registrado, se devuelve una instancia nueva del carro,
     * Se captura la excepcion AutoExcepcion cuando el auto no est[a registrado en la base de datos
     *
     * @param placa
     * @return @link(AutoTo) Una nueva instancia de AutoTo
     */
    private AutoTo generarAuto(String placa) {
        AutoTo autoTo;

        try {
            autoTo = AutoMapper.toTo(autoService.getByPlaca(placa));
        } catch (AutoException e) {
            autoTo = AutoTo.builder().build();
        }

        return autoTo;
    }
}
