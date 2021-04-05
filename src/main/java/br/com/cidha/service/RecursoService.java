package br.com.cidha.service;

import br.com.cidha.domain.Recurso;
import br.com.cidha.repository.RecursoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Recurso}.
 */
@Service
@Transactional
public class RecursoService {

    private final Logger log = LoggerFactory.getLogger(RecursoService.class);

    private final RecursoRepository recursoRepository;

    public RecursoService(RecursoRepository recursoRepository) {
        this.recursoRepository = recursoRepository;
    }

    /**
     * Save a recurso.
     *
     * @param recurso the entity to save.
     * @return the persisted entity.
     */
    public Recurso save(Recurso recurso) {
        log.debug("Request to save Recurso : {}", recurso);
        return recursoRepository.save(recurso);
    }

    /**
     * Get all the recursos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Recurso> findAll(Pageable pageable) {
        log.debug("Request to get all Recursos");
        return recursoRepository.findAll(pageable);
    }


    /**
     * Get one recurso by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Recurso> findOne(Long id) {
        log.debug("Request to get Recurso : {}", id);
        return recursoRepository.findById(id);
    }

    /**
     * Delete the recurso by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Recurso : {}", id);
        recursoRepository.deleteById(id);
    }
}
