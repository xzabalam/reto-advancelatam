package com.advancelatam.web.controllers.auto;

import com.advancelatam.bussines.services.auto.AutoService;
import com.advancelatam.common.util.Roles;
import com.advancelatam.data.entities.auto.Auto;
import com.advancelatam.common.dto.AutoTo;
import com.advancelatam.web.mapper.AutoMapper;
import com.advancelatam.web.util.RestPreconditions;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/api/auto")
public class AutoController {

    @Autowired
    private AutoService autoService;

    @DeleteMapping("/{placa}")
    @Operation(summary = "Eliminar un auto por placa")
    public ResponseEntity<Void> deleteAuto(@Valid @NotNull @PathVariable("placa") String placa) {
        RestPreconditions.checkNull(placa);
        RestPreconditions.checkEmptyString(placa);

        autoService.deleteAuto(placa);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{placa}")
    @Operation(summary = "Actualizar un auto")
    public ResponseEntity<AutoTo> updateAuto(
            @Valid @NotNull @RequestBody AutoTo autoTo,
            @Valid @NotNull @PathVariable("placa") String placa,
            Authentication authentication) {
        RestPreconditions.checkNull(autoTo);
        RestPreconditions.checkNull(placa);
        RestPreconditions.checkEmptyString(placa);

        Auto auto = autoService.updateAuto(autoTo, placa, authentication.getName());
        return new ResponseEntity<>(AutoMapper.toTo(auto), HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Crear un auto")
    public ResponseEntity<AutoTo> createAuto(@Valid @NotNull @RequestBody AutoTo autoTo, Authentication authentication) {
        RestPreconditions.checkNull(autoTo);

        Auto autoCreado = autoService.createAuto(autoTo, authentication.getName());
        return new ResponseEntity<>(AutoMapper.toTo(autoCreado), HttpStatus.CREATED);
    }

    @GetMapping("/{placa}")
    @Operation(summary = "Obtener un auto por placa")
    public ResponseEntity<AutoTo> getByPlaca(
            @Valid @NotNull @PathVariable("placa") String placa) {
        RestPreconditions.checkNull(placa);
        RestPreconditions.checkEmptyString(placa);

        Auto auto = autoService.getByPlaca(placa);
        return ResponseEntity.ok(AutoMapper.toTo(auto));
    }

    @GetMapping
    @Operation(summary = "Obtener todos los autos")
    public ResponseEntity<List<AutoTo>> getAll() {
        return ResponseEntity.ok(autoService.getAll());
    }

    @GetMapping("/page")
    @Operation(summary = "Obtener todos los autos con paginación")
    public ResponseEntity<Page<AutoTo>> getAllWithPage(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        return ResponseEntity.ok(autoService.getAllWithPage(page, size));
    }
}
