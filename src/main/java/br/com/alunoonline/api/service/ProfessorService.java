package br.com.alunoonline.api.service;

import br.com.alunoonline.api.dto.professor.ProfessorRequestDTO;
import br.com.alunoonline.api.dto.professor.ProfessorResponseDTO;
import br.com.alunoonline.api.dto.professor.ProfessorDetailsDTO;
import br.com.alunoonline.api.model.Professor;
import br.com.alunoonline.api.repository.ProfessorRepository;
import br.com.alunoonline.api.util.CpfUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProfessorService {

    // Repositorio faz acesso ao banco para a entidade Professor.
    private final ProfessorRepository professorRepository;

    // ModelMapper reduz codigo manual de conversao entre entidade e DTO.
    private final ModelMapper modelMapper;

    // Cria professor no banco e devolve DTO de resposta.
    public ProfessorResponseDTO criarProfessor(ProfessorRequestDTO professorRequestDTO) {
        log.info("Persistindo novo professor. Nome: {} Email: {} CPF: {}",
                professorRequestDTO.getNomeCompleto(), professorRequestDTO.getEmail(),
                CpfUtils.formatCpf(professorRequestDTO.getCpf()));

        // Converte o DTO recebido para entidade antes de persistir.
        Professor professor = modelMapper.map(professorRequestDTO, Professor.class);
        Professor professorSalvo = professorRepository.save(professor);

        log.info("Professor persistido com sucesso. ID: {} Email: {}",
                professorSalvo.getId(), professorSalvo.getEmail());

        return modelMapper.map(professorSalvo, ProfessorResponseDTO.class);
    }

    // Lista professores com paginacao nativa do Spring Data.
    public Page<ProfessorResponseDTO> listarProfessores(Pageable pageable) {
        log.info("Buscando professores com paginação. Página: {} Tamanho: {} Ordenação: {}",
                pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort());

        // Mapeia cada entidade da pagina para DTO de resposta.
        Page<ProfessorResponseDTO> professores = professorRepository.findAll(pageable)
                .map(professor -> modelMapper.map(professor, ProfessorResponseDTO.class));

        log.info("Consulta paginada de professores finalizada. Página atual: {} Total de elementos: {} Total de páginas: {} Elementos retornados: {}",
                professores.getNumber(), professores.getTotalElements(), professores.getTotalPages(),
                professores.getNumberOfElements());

        return professores;
    }

    // Busca um professor por id e retorna DTO de detalhe.
    public ProfessorDetailsDTO buscarProfessorPorId(Long id) {
        log.info("Buscando professor por ID: {}", id);

        // Quando nao encontra no banco, devolve 404 com mensagem de negocio.
        Professor professor = professorRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Professor não encontrado para o ID: {}", id);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Professor não encontrado no banco de dados");
                });

        // Monta DTO de detalhe e aplica mascara no CPF para nao expor o valor completo.
        ProfessorDetailsDTO professorDetailsDTO = modelMapper.map(professor, ProfessorDetailsDTO.class);
        professorDetailsDTO.setCpfMascarado(CpfUtils.formatCpf(professor.getCpf()));

        log.info("Professor localizado por ID. ID: {} Email: {} CPF: {}",
                professor.getId(), professor.getEmail(), professorDetailsDTO.getCpfMascarado());

        return professorDetailsDTO;
    }
}
