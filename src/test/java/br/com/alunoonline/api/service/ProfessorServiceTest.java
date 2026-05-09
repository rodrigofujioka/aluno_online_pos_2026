package br.com.alunoonline.api.service;

import br.com.alunoonline.api.dto.professor.ProfessorRequestDTO;
import br.com.alunoonline.api.dto.professor.ProfessorResponseDTO;
import br.com.alunoonline.api.dto.professor.ProfessorDetailsDTO;
import br.com.alunoonline.api.model.Professor;
import br.com.alunoonline.api.repository.ProfessorRepository;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ProfessorServiceTest {

    @Test
    void deveCriarProfessorUsandoModelMapper() {
        ProfessorRepository professorRepository = mock(ProfessorRepository.class);
        ProfessorService professorService = new ProfessorService(professorRepository, new ModelMapper());

        ProfessorRequestDTO requestDTO = new ProfessorRequestDTO(
                "Marcos Silva",
                "marcos@escola.com",
                "123.456.789-09",
                "Rua das Acacias, 100"
        );

        Professor professorSalvo = new Professor(
                1L,
                "Marcos Silva",
                "marcos@escola.com",
                "123.456.789-09",
                "Rua das Acacias, 100"
        );

        when(professorRepository.save(any(Professor.class))).thenReturn(professorSalvo);

        ProfessorResponseDTO responseDTO = professorService.criarProfessor(requestDTO);

        assertEquals(1L, responseDTO.getId());
        assertEquals("Marcos Silva", responseDTO.getNomeCompleto());
        assertEquals("marcos@escola.com", responseDTO.getEmail());
        assertEquals("Rua das Acacias, 100", responseDTO.getEndereco());
        verify(professorRepository).save(any(Professor.class));
    }

    @Test
    void deveListarProfessoresDeFormaPaginada() {
        ProfessorRepository professorRepository = mock(ProfessorRepository.class);
        ProfessorService professorService = new ProfessorService(professorRepository, new ModelMapper());

        Pageable pageable = PageRequest.of(0, 2);
        Professor professor = new Professor(1L, "Ana Lima", "ana@escola.com", "987.654.321-00", "Rua A, 123");
        Page<Professor> pagina = new PageImpl<>(List.of(professor), pageable, 1);

        when(professorRepository.findAll(pageable)).thenReturn(pagina);

        Page<ProfessorResponseDTO> resultado = professorService.listarProfessores(pageable);

        assertEquals(1, resultado.getTotalElements());
        assertEquals(1, resultado.getContent().size());
        assertEquals("Ana Lima", resultado.getContent().getFirst().getNomeCompleto());
        assertEquals("ana@escola.com", resultado.getContent().getFirst().getEmail());
        assertEquals("Rua A, 123", resultado.getContent().getFirst().getEndereco());
        verify(professorRepository).findAll(pageable);
    }

    @Test
    void deveBuscarProfessorPorIdComSucesso() {
        ProfessorRepository professorRepository = mock(ProfessorRepository.class);
        ProfessorService professorService = new ProfessorService(professorRepository, new ModelMapper());

        Professor professor = new Professor(10L, "Fernanda Costa", "fernanda@escola.com", "123.456.789-00", "Av Central, 500");
        when(professorRepository.findById(10L)).thenReturn(Optional.of(professor));

        ProfessorDetailsDTO resultado = professorService.buscarProfessorPorId(10L);

        assertEquals(10L, resultado.getId());
        assertEquals("Fernanda Costa", resultado.getNomeCompleto());
        assertEquals("fernanda@escola.com", resultado.getEmail());
        assertEquals("Av Central, 500", resultado.getEndereco());
        assertEquals("***8900", resultado.getCpfMascarado());
        verify(professorRepository).findById(10L);
    }

    @Test
    void deveLancarNotFoundAoBuscarProfessorPorIdInexistente() {
        ProfessorRepository professorRepository = mock(ProfessorRepository.class);
        ProfessorService professorService = new ProfessorService(professorRepository, new ModelMapper());

        when(professorRepository.findById(99L)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> professorService.buscarProfessorPorId(99L)
        );

        assertEquals(HttpStatus.NOT_FOUND.value(), exception.getStatusCode().value());
        assertEquals("Professor não encontrado no banco de dados", exception.getReason());
        verify(professorRepository).findById(99L);
    }
}

