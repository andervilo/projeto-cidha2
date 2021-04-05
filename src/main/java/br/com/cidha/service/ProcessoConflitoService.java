package br.com.cidha.service;

import br.com.cidha.domain.ProcessoConflito;
import br.com.cidha.repository.ProcessoConflitoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ProcessoConflito}.
 */
@Service
@Transactional
public class ProcessoConflitoService {

    private final Logger log = LoggerFactory.getLogger(ProcessoConflitoService.class);

    private final ProcessoConflitoRepository processoConflitoRepository;

    public ProcessoConflitoService(ProcessoConflitoRepository processoConflitoRepository) {
        this.processoConflitoRepository = processoConflitoRepository;
    }

    /**
     * Save a processoConflito.
     *
     * @param processoConflito the entity to save.
     * @return the persisted entity.
     */
    public ProcessoConflito save(ProcessoConflito processoConflito) {
        log.debug("Request to save ProcessoConflito : {}", processoConflito);
        return processoConflitoRepository.save(processoConflito);
    }

    /**
     * Get all the processoConflitos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ProcessoConflito> findAll(Pageable pageable) {
        log.debug("Request to get all ProcessoConflitos");
        return processoConflitoRepository.findAll(pageable);
    }


    /**
     * Get all the processoConflitos with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<ProcessoConflito> findAllWithEagerRelationships(Pageable pageable) {
        return processoConflitoRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one processoConflito by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProcessoConflito> findOne(Long id) {
        log.debug("Request to get ProcessoConflito : {}", id);
        return processoConflitoRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the processoConflito by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ProcessoConflito : {}", id);
        processoConflitoRepository.deleteById(id);
    }
}
