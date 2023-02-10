package com.advancelatam.bussines.services.auto;

import com.advancelatam.bussines.services.security.UsuarioService;
import com.advancelatam.common.dto.AutoTo;
import com.advancelatam.common.enumeration.EstadoEnum;
import com.advancelatam.common.exception.auto.AutoNotFoundException;
import com.advancelatam.common.exception.auto.AutoValidationException;
import com.advancelatam.data.entities.auto.Auto;
import com.advancelatam.data.entities.auto.TipoAuto;
import com.advancelatam.data.entities.security.Usuario;
import com.advancelatam.data.repositories.auto.AutoRepository;
import com.advancelatam.web.mapper.AutoMapper;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.locks.StampedLock;
import java.util.stream.Collectors;

@Service
public class AutoService {
    private final AutoRepository autoRepository;
    private final UsuarioService usuarioService;
    private final TipoAutoService tipoAutoService;
    private final MessageSource messageSource;

    public AutoService(AutoRepository autoRepository, UsuarioService usuarioService, TipoAutoService tipoAutoService, MessageSource messageSource) {
        this.autoRepository = autoRepository;
        this.usuarioService = usuarioService;
        this.tipoAutoService = tipoAutoService;
        this.messageSource = messageSource;
    }

    @Transactional
    public void deleteAuto(String placa) {
        Auto auto = autoRepository.findByPlaca(placa).orElseThrow(() -> new AutoNotFoundException("No existe un auto con la placa " + placa));
        autoRepository.deleteById(auto.getId());
    }


    @Transactional
    public Auto updateAuto(AutoTo autoTo, String placa, String usernameModifica) {
        Usuario usuario = usuarioService.getUserByUsername(usernameModifica);
        TipoAuto tipoAuto = tipoAutoService.findById(autoTo.getIdTipoAuto());
        Auto auto = AutoMapper.toEntity(autoTo, tipoAuto, usuario.getId());

        validateAuto(auto);

        Auto autoBuscado = autoRepository.findByPlaca(placa)
                .orElseThrow(() -> new AutoNotFoundException("No existe un auto con la placa " + placa));

        StampedLock lock = auto.getStampedLock();
        long stamp = lock.writeLock();
        try {
            autoBuscado.setPlaca(auto.getPlaca());
            autoBuscado.setModelo(auto.getModelo());
            autoBuscado.setChasis(auto.getChasis());
            autoBuscado.setColor(auto.getColor());
            autoBuscado.setTipoAuto(auto.getTipoAuto());
            autoBuscado.setFechaModifica(new Date());
            autoBuscado.setUsuarioModifica(usuario.getId());
            autoBuscado.setEstado(auto.getEstado());
            return autoRepository.save(autoBuscado);
        } finally {
            lock.unlockWrite(stamp);
        }
    }

    @Transactional
    public Auto createAuto(AutoTo autoTo, String usernameModifica) {
        Usuario usuario = usuarioService.getUserByUsername(usernameModifica);
        TipoAuto tipoAuto = tipoAutoService.findById(autoTo.getIdTipoAuto());
        Auto auto = AutoMapper.toEntity(autoTo, tipoAuto, usuario.getId());

        Optional<Auto> autoBuscado = autoRepository.findByPlaca(auto.getPlaca());
        if (autoBuscado.isPresent()) {
            throw new AutoValidationException("El auto con placa " + autoTo.getPlaca() + " ya está registrado.");
        }

        validateAuto(auto);
        return autoRepository.save(auto);
    }

    public Auto getByPlaca(String placa) {
        return autoRepository.findByPlaca(placa).orElseThrow(() -> new AutoNotFoundException("No existe un " +
                "auto con la placa " + placa));
    }

    public List<AutoTo> getAll() {
        return autoRepository.findAllByEstado(EstadoEnum.ACTIVO.getEstado()).stream().map(auto -> AutoMapper.toTo(auto)).collect(Collectors.toList());
    }

    public Page<AutoTo> getAllWithPage(int page, int size) {
        return autoRepository
                .findAllByEstado(PageRequest.of(page, size), EstadoEnum.ACTIVO.getEstado())
                .map(auto -> AutoMapper.toTo(auto));
    }

    private void validateAuto(Auto auto) {
        Optional.ofNullable(auto.getPlaca())
                .filter(s -> !s.isBlank())
                .orElseThrow(() -> new AutoValidationException(getMessage("pico.placa.auto.placa.notnull")));

        Optional.ofNullable(auto.getModelo())
                .filter(s -> !s.isBlank())
                .orElseThrow(() -> new AutoValidationException(getMessage("pico.placa.auto.modelo.notnull")));

        Optional.ofNullable(auto.getChasis())
                .filter(s -> !s.isBlank())
                .orElseThrow(() -> new AutoValidationException(getMessage("pico.placa.auto.chasis.notnull")));

        Optional.ofNullable(auto.getColor())
                .filter(s -> !s.isBlank())
                .orElseThrow(() -> new AutoValidationException(getMessage("pico.placa.auto.color.notnull")));
    }

    private String getMessage(String messageKey) {
        return messageSource.getMessage(messageKey, null, LocaleContextHolder.getLocale());
    }
}
