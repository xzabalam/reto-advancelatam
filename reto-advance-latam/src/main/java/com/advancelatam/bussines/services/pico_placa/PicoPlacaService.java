package com.advancelatam.bussines.services.pico_placa;

import com.advancelatam.bussines.services.security.UsuarioService;
import com.advancelatam.common.dto.CirculacionTo;
import com.advancelatam.common.dto.PicoPlacaTo;
import com.advancelatam.common.enumeration.EstadoEnum;
import com.advancelatam.common.exception.pico_placa.PicoPlacaNotFoundException;
import com.advancelatam.common.exception.pico_placa.PicoPlacaValidationException;
import com.advancelatam.common.util.Roles;
import com.advancelatam.data.entities.pico_placa.PicoPlaca;
import com.advancelatam.data.entities.security.Usuario;
import com.advancelatam.data.enums.DiasSemanaEnum;
import com.advancelatam.data.repositories.pico_placa.PicoPlacaRepository;
import com.advancelatam.web.mapper.PicoPlacaMapper;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class PicoPlacaService {
    private final PicoPlacaRepository picoPlacaRepository;
    private final UsuarioService usuarioService;
    private final MessageSource messageSource;
    private Boolean ultimoDigitoTieneRestriccion;

    public PicoPlacaService(PicoPlacaRepository picoPlacaRepository, UsuarioService usuarioService, MessageSource messageSource) {
        this.picoPlacaRepository = picoPlacaRepository;
        this.usuarioService = usuarioService;
        this.messageSource = messageSource;
    }

    @Transactional
    @Secured({Roles.ROLE_ADMIN, Roles.ROLE_USER})
    public void deletePicoPlaca(Long idPicoPlaca) {
        PicoPlaca picoPlaca = picoPlacaRepository.findById(idPicoPlaca).orElseThrow(() -> new PicoPlacaNotFoundException(
                "No existe una configuracion de pico y placa con id " + idPicoPlaca));
        picoPlacaRepository.deleteById(picoPlaca.getId());
    }

    @Transactional
    @Secured({Roles.ROLE_ADMIN, Roles.ROLE_USER})
    public void deletePicoPlacaPorDia(DiasSemanaEnum diasSemanaEnum) {
        List<PicoPlaca> picoPlacas =
                picoPlacaRepository.findByDiaSemanaAndEstado(diasSemanaEnum, EstadoEnum.ACTIVO.getEstado());
        picoPlacaRepository.deleteAllInBatch(picoPlacas);
    }

    @Transactional
    @Secured({Roles.ROLE_ADMIN, Roles.ROLE_USER})
    public PicoPlaca updatePicoPlaca(PicoPlacaTo picoPlacaTo, Long idPicoPlaca, String usernameModifica) {
        Usuario usuario = usuarioService.getUserByUsername(usernameModifica);
        PicoPlaca picoPlaca = PicoPlacaMapper.toEntity(picoPlacaTo, usuario.getId());

        validatePicoPlaca(picoPlaca);

        PicoPlaca picoPlacaBuscado =
                picoPlacaRepository.findById(idPicoPlaca).orElseThrow(() -> new PicoPlacaNotFoundException(
                        "No existe una configuracion de pico y placa con id " + idPicoPlaca));

        picoPlacaBuscado.setDiaSemana(picoPlacaTo.getDiaSemana());
        picoPlacaBuscado.setHoraInicio(picoPlacaTo.getHoraInicio());
        picoPlacaBuscado.setHoraFin(picoPlacaTo.getHoraFin());
        picoPlacaBuscado.setResticcionUltimoDigito(picoPlacaTo.getResticcionUltimoDigito());
        picoPlacaBuscado.setFechaModifica(new Date());
        picoPlacaBuscado.setUsuarioModifica(usuario.getId());
        picoPlacaBuscado.setEstado(picoPlaca.getEstado());

        return picoPlacaRepository.save(picoPlacaBuscado);
    }

    @Transactional
    @Secured({Roles.ROLE_ADMIN, Roles.ROLE_USER})
    public PicoPlaca createPicoPlaca(PicoPlacaTo picoPlacaTo, String usernameModifica) {
        Usuario usuario = usuarioService.getUserByUsername(usernameModifica);
        PicoPlaca picoPlaca = PicoPlacaMapper.toEntity(picoPlacaTo, usuario.getId());
        validatePicoPlaca(picoPlaca);
        return picoPlacaRepository.save(picoPlaca);
    }

    @Secured({Roles.ROLE_ADMIN, Roles.ROLE_USER})
    public PicoPlaca getById(Long idPicoPlaca) {
        return picoPlacaRepository.findById(idPicoPlaca)
                .orElseThrow(() -> new PicoPlacaNotFoundException("No existe una configuracion de pico y placa con id " + idPicoPlaca));
    }

    @Secured({Roles.ROLE_ADMIN, Roles.ROLE_USER})
    public List<PicoPlaca> getByDiaSemana(DiasSemanaEnum diasSemanaEnum) {
        return picoPlacaRepository.findByDiaSemanaAndEstado(diasSemanaEnum, EstadoEnum.ACTIVO.getEstado());
    }

    @Secured({Roles.ROLE_ADMIN, Roles.ROLE_USER})
    public List<PicoPlacaTo> getAll() {
        return picoPlacaRepository.findAllByEstadoOrderByDiaSemanaAscHoraInicioAsc(EstadoEnum.ACTIVO.getEstado())
                .stream().map(picoPlaca -> PicoPlacaMapper.toTo(picoPlaca)).collect(Collectors.toList());
    }

    @Secured({Roles.ROLE_ADMIN, Roles.ROLE_USER})
    public Page<PicoPlacaTo> getAllWithPage(int page, int size) {
        return picoPlacaRepository
                .findAllByEstado(PageRequest.of(page, size), EstadoEnum.ACTIVO.getEstado())
                .map(picoPlaca -> PicoPlacaMapper.toTo(picoPlaca));
    }

    public CirculacionTo verificarCirculacion(String placa, LocalDateTime fecha) {
        String dia = fecha.getDayOfWeek().getDisplayName(TextStyle.FULL, LocaleContextHolder.getLocale());
        List<PicoPlaca> picoPlaca = getByDiaSemana(DiasSemanaEnum.getDiaSemana(dia));

        Boolean ultimoDigitoTieneRestriccion = verificarSiUltimoDigitoTieneRestriccion(placa, dia, picoPlaca);
        Boolean tieneRestriccionEnHora = verificarSiTieneRestriccionEnHoras(fecha, picoPlaca);

        return CirculacionTo.builder()
                .configuracion(picoPlaca.stream().map(configuracion -> PicoPlacaMapper.toTo(configuracion)).collect(Collectors.toList()))
                .puedeCircular(!(ultimoDigitoTieneRestriccion && tieneRestriccionEnHora))
                .build();
    }

    private Boolean verificarSiTieneRestriccionEnHoras(LocalDateTime fecha, List<PicoPlaca> picoPlaca) {
        AtomicInteger contadorCoincidencias = new AtomicInteger(0);
        int hora = fecha.getHour();
        picoPlaca.forEach(configuracion -> {
            int horaInicio = configuracion.getHoraInicio().getHour();
            int horaFin = configuracion.getHoraFin().getHour();
            if (hora >= horaInicio && hora <= horaFin) {
                contadorCoincidencias.getAndIncrement();
            }
        });
        return (contadorCoincidencias.get() > 0);
    }

    private Boolean verificarSiUltimoDigitoTieneRestriccion(String placa, String dia, List<PicoPlaca> picoPlaca) {
        Set<Byte> ultimoDigitoConRestriccion =
                picoPlaca.stream().flatMap(configuracion -> configuracion.getResticcionUltimoDigito().stream()).collect(Collectors.toSet());

        char ultimoCaracterPlaca = placa.charAt(placa.length() - 1);
        Byte ultimoDigitoPlaca = Byte.valueOf((byte) (ultimoCaracterPlaca - '0'));
        return ultimoDigitoConRestriccion.contains(ultimoDigitoPlaca);
    }

    private void validatePicoPlaca(PicoPlaca picoPlaca) {
        Optional.ofNullable(picoPlaca.getDiaSemana())
                .orElseThrow(() -> new PicoPlacaValidationException(getMessage("pico.placa.picoPlaca.placa.notnull")));

        Optional.ofNullable(picoPlaca.getHoraInicio())
                .orElseThrow(() -> new PicoPlacaValidationException(getMessage("pico.placa.picoPlaca.modelo.notnull")));

        Optional.ofNullable(picoPlaca.getHoraFin())
                .orElseThrow(() -> new PicoPlacaValidationException(getMessage("pico.placa.picoPlaca.chasis.notnull")));

        Optional.ofNullable(picoPlaca.getResticcionUltimoDigito())
                .filter(s -> !s.isEmpty())
                .orElseThrow(() -> new PicoPlacaValidationException(getMessage("pico.placa.picoPlaca.color.notnull")));
    }

    private String getMessage(String messageKey) {
        return messageSource.getMessage(messageKey, null, LocaleContextHolder.getLocale());
    }
}
