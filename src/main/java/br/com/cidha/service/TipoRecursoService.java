package br.com.cidha.service;

import br.com.cidha.domain.TipoRecurso;
import br.com.cidha.repository.TipoRecursoRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link TipoRecurso}.
 */
@Service
@Transactional
public class TipoRecursoService {

    private final Logger log = LoggerFactory.getLogger(TipoRecursoService.class);

    private final TipoRecursoRepository tipoRecursoRepository;

    public TipoRecursoService(TipoRecursoRepository tipoRecursoRepository) {
        this.tipoRecursoRepository = tipoRecursoRepository;
    }

    /**
     * Save a tipoRecurso.
     *
     * @param tipoRecurso the entity to save.
     * @return the persisted entity.
     */
    public TipoRecurso save(TipoRecurso tipoRecurso) {
        log.debug("Request to save TipoRecurso : {}", tipoRecurso);
        return tipoRecursoRepository.save(tipoRecurso);
    }

    /**
     * Partially update a tipoRecurso.
     *
     * @param tipoRecurso the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<TipoRecurso> partialUpdate(TipoRecurso tipoRecurso) {
        log.debug("Request to partially update TipoRecurso : {}", tipoRecurso);

        return tipoRecursoRepository
            .findById(tipoRecurso.getId())
            .map(
                existingTipoRecurso -> {
                    if (tipoRecurso.getDescricao() != null) {
                        existingTipoRecurso.setDescricao(tipoRecurso.getDescricao());
                    }

                    return existingTipoRecurso;
                }
            )
            .map(tipoRecursoRepository::save);
    }

    /**
     * Get all the tipoRecursos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TipoRecurso> findAll(Pageable pageable) {
        log.debug("Request to get all TipoRecursos");
        return tipoRecursoRepository.findAll(pageable);
    }

    /**
     * Get one tipoRecurso by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TipoRecurso> findOne(Long id) {
        log.debug("Request to get TipoRecurso : {}", id);
        return tipoRecursoRepository.findById(id);
    }

    /**
     * Delete the tipoRecurso by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TipoRecurso : {}", id);
        tipoRecursoRepository.deleteById(id);
    }
}
