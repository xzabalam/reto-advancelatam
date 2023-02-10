package com.advancelatam.web.controllers.auto;

import com.advancelatam.bussines.services.auto.TipoAutoService;
import com.advancelatam.data.entities.auto.TipoAuto;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tipo-auto")
public class TipoAutoController {
    @Autowired
    private TipoAutoService tipoAutoService;

    @GetMapping
    @Operation(summary = "Permite obtener el listado de los tipos de autos")
    public ResponseEntity<List<TipoAuto>> finadAll() {
        return new ResponseEntity<>(tipoAutoService.findAll(), HttpStatus.OK);
    }
}
