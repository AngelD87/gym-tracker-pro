package com.dwes.ProyectoConMySQL.service;

import com.dwes.ProyectoConMySQL.dto.*;
import com.dwes.ProyectoConMySQL.model.Entrenamiento;
import com.dwes.ProyectoConMySQL.model.EntrenamientoEjercicio;
import com.dwes.ProyectoConMySQL.model.Usuario;
import com.dwes.ProyectoConMySQL.repository.EntrenamientoEjercicioRepository;
import com.dwes.ProyectoConMySQL.repository.EntrenamientoRepository;
import com.dwes.ProyectoConMySQL.repository.SerieRepository;
import com.dwes.ProyectoConMySQL.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EntrenamientoService {

    private final EntrenamientoRepository entrenamientoRepository;
    private final UsuarioRepository usuarioRepository;
    private final SerieRepository serieRepository;
    private final EntrenamientoEjercicioRepository entrenamientoEjercicioRepository;

    public EntrenamientoService(
            EntrenamientoRepository entrenamientoRepository,
            UsuarioRepository usuarioRepository,
            SerieRepository serieRepository,
            EntrenamientoEjercicioRepository entrenamientoEjercicioRepository) {
        this.entrenamientoRepository = entrenamientoRepository;
        this.usuarioRepository = usuarioRepository;
        this.serieRepository = serieRepository;
        this.entrenamientoEjercicioRepository = entrenamientoEjercicioRepository;
    }

    private EntrenamientoResponseDTO toResponseDTO(Entrenamiento e) {
        return EntrenamientoResponseDTO.builder()
                .idEntrenamiento(e.getIdEntrenamiento())
                .idUsuario(e.getUsuario().getIdUsuario())
                .nombre(e.getNombre())
                .inicio(e.getInicio())
                .fin(e.getFin())
                .valoracion(e.getValoracion())
                .comentario(e.getComentario())
                .build();
    }

    //CREAR ENTRENAMIENTO (DTO)
    public EntrenamientoResponseDTO crearEntrenamientoDTO(EntrenamientoCreateDTO dto) {
        Usuario usuario = usuarioRepository.findById(dto.getIdUsuario())
                .orElseThrow(() ->
                        new IllegalArgumentException("El usuario no existe"));

        if(dto.getNombre() == null || dto.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del entrenamiento es obligatorio");
        }

        String nombreLimpio = dto.getNombre().trim();

        if (entrenamientoRepository.existsByUsuarioIdUsuarioAndNombreIgnoreCase(dto.getIdUsuario(), nombreLimpio)) {
            throw new IllegalArgumentException("Ya tienes un entrenamiento con ese nombre");
        }

        Entrenamiento entrenamiento = new Entrenamiento();
        entrenamiento.setUsuario(usuario);
        entrenamiento.setNombre(nombreLimpio);
        entrenamiento.setInicio(LocalDateTime.now());

        Entrenamiento guardado = entrenamientoRepository.save(entrenamiento);

        return toResponseDTO(guardado);
    }

    //LISTAR ENTRENAMIENTOS (DTO)
    public List<EntrenamientoResponseDTO> listarDTO() {
        return entrenamientoRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    //LISTAR ENTRENAMIENTOS DE UN USUARIO (DTO)
    public List<EntrenamientoResponseDTO> listarPorUsuarioDTO(Long idUsuario) {
        return entrenamientoRepository.findByUsuarioIdUsuario(idUsuario)
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    //BUSCAR POR ID (DTO)
    public EntrenamientoResponseDTO buscarPorIdDTO(Long id) {
        Entrenamiento entrenamiento = entrenamientoRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("El entrenamiento no existe"));

        return toResponseDTO(entrenamiento);
    }

    //CERRAR ENTRENAMIENTO (DTO)
    public EntrenamientoResponseDTO cerrarEntrenamientoDesdeDTO(Long idEntrenamiento, EntrenamientoCerrarDTO dto) {
        Entrenamiento entrenamiento = entrenamientoRepository.findById(idEntrenamiento)
                .orElseThrow(() ->
                        new IllegalArgumentException("El entrenamiento no existe"));

        if (entrenamiento.getFin() != null) {
            throw new IllegalStateException("El entrenamiento ya está cerrado");
        }

        //VALIDACIÓN: No cerrar sin ejercicios
        List<EntrenamientoEjercicio> ejercicios = entrenamientoEjercicioRepository
                .findByEntrenamientoIdEntrenamientoOrderByOrden(idEntrenamiento);

        if(ejercicios.isEmpty()) {
            throw new IllegalStateException("No se puede cerrar un entrenamiento sin ejercicios");
        }

        entrenamiento.setFin(LocalDateTime.now());
        entrenamiento.setValoracion(dto.getValoracion());
        entrenamiento.setComentario(dto.getComentario());

        Entrenamiento actualizado = entrenamientoRepository.save(entrenamiento);

        return toResponseDTO(actualizado);
    }

    //ENTRENAMIENTO COMPLETO
    public EntrenamientoCompletoDTO obtenerEntrenamientoCompleto(Long idEntrenamiento) {

        //BUSCAR EL ENTRENAMIENTO
        Entrenamiento entrenamiento = entrenamientoRepository.findById(idEntrenamiento)
                .orElseThrow(() ->
                        new IllegalArgumentException("El entrenamiento no existe"));

        //BUSCAR LOS EJERCICIOS DEL ENTRENAMIENTO
        List<EntrenamientoEjercicio> ejercicios =
                entrenamientoEjercicioRepository
                        .findByEntrenamientoIdEntrenamientoOrderByOrden(idEntrenamiento);

        //CONVERTIR EJERCICIOS A DTO CON SUS SERIES
        List<EntrenamientoEjercicioCompletoDTO> ejerciciosDTO = ejercicios.stream()
                .map(ee -> EntrenamientoEjercicioCompletoDTO.builder()
                        .idEntrenamientoEjercicio(ee.getIdEntrenamientoEjercicio())
                        .idEjercicio(ee.getEjercicio().getIdEjercicio())
                        .nombreEjercicio(ee.getEjercicio().getNombre())
                        .orden(ee.getOrden())
                        .notas(ee.getNotas())
                        .series(
                                serieRepository
                                        .findByEntrenamientoEjercicioIdEntrenamientoEjercicioOrderByNumeroSerie(
                                                ee.getIdEntrenamientoEjercicio())
                                        .stream()
                                        .map(s -> SerieSimpleDTO.builder()
                                                .idSerie(s.getIdSerie())
                                                .numeroSerie(s.getNumeroSerie())
                                                .repeticiones(s.getRepeticiones())
                                                .peso(s.getPeso())
                                                .rir(s.getRir())
                                                .build())
                                        .toList()
                        )
                        .build())
                .toList();

        return EntrenamientoCompletoDTO.builder()
                .idEntrenamiento(entrenamiento.getIdEntrenamiento())
                .nombre(entrenamiento.getNombre())
                .inicio(entrenamiento.getInicio())
                .fin(entrenamiento.getFin())
                .valoracion(entrenamiento.getValoracion())
                .comentario(entrenamiento.getComentario())
                .ejercicios(ejerciciosDTO)
                .build();
    }

    //ELIMINAR
    public void eliminar(Long id) {
        Entrenamiento entrenamiento = entrenamientoRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("El entrenamiento no existe"));

        if (entrenamiento.getFin() != null) {
            throw new IllegalStateException("No se puede eliminar un entrenamiento cerrado");
        }

        entrenamientoRepository.delete(entrenamiento);
    }
}