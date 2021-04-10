package br.com.cidha.service;

import br.com.cidha.domain.OpcaoRecurso;
import br.com.cidha.repository.OpcaoRecursoRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link OpcaoRecurso}.
 */
@Service
@Transactional
public class OpcaoRecursoService {

    private final Logger log = LoggerFactory.getLogger(OpcaoRecursoService.class);

    private final OpcaoRecursoRepository opcaoRecursoRepository;

    public OpcaoRecursoService(OpcaoRecursoRepository opcaoRecursoRepository) {
        this.opcaoRecursoRepository = opcaoRecursoRepository;
    }

    /**
     * Save a opcaoRecurso.
     *
     * @param opcaoRecurso the entity to save.
     * @return the persisted entity.
     */
    public OpcaoRecurso save(OpcaoRecurso opcaoRecurso) {
        log.debug("Request to save OpcaoRecurso : {}", opcaoRecurso);
        return opcaoRecursoRepository.save(opcaoRecurso);
    }

    /**
     * Partially update a opcaoRecurso.
     *
     * @param opcaoRecurso the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<OpcaoRecurso> partialUpdate(OpcaoRecurso opcaoRecurso) {
        log.debug("Request to partially update OpcaoRecurso : {}", opcaoRecurso);

        return opcaoRecursoRepository
            .findById(opcaoRecurso.getId())
            .map(
                existingOpcaoRecurso -> {
                    if (opcaoRecurso.getDescricao() != null) {
                        existingOpcaoRecurso.setDescricao(opcaoRecurso.getDescricao());
                    }

                    return existingOpcaoRecurso;
                }
            )
            .map(opcaoRecursoRepository::save);
    }

    /**
     * Get all the opcaoRecursos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<OpcaoRecurso> findAll(Pageable pageable) {
        log.debug("Request to get all OpcaoRecursos");
        return opcaoRecursoRepository.findAll(pageable);
    }

    /**
     * Get one opcaoRecurso by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<OpcaoRecurso> findOne(Long id) {
        log.debug("Request to get OpcaoRecurso : {}", id);
        return opcaoRecursoRepository.findById(id);
    }

    /**
     * Delete the opcaoRecurso by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete OpcaoRecurso : {}", id);
        opcaoRecursoRepository.deleteById(id);
    }
}
