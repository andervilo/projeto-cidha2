package br.com.cidha.service;

import br.com.cidha.domain.Conflito;
import br.com.cidha.repository.ConflitoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Conflito}.
 */
@Service
@Transactional
public class ConflitoService {

    private final Logger log = LoggerFactory.getLogger(ConflitoService.class);

    private final ConflitoRepository conflitoRepository;

    public ConflitoService(ConflitoRepository conflitoRepository) {
        this.conflitoRepository = conflitoRepository;
    }

    /**
     * Save a conflito.
     *
     * @param conflito the entity to save.
     * @return the persisted entity.
     */
    public Conflito save(Conflito conflito) {
        log.debug("Request to save Conflito : {}", conflito);
        return conflitoRepository.save(conflito);
    }

    /**
     * Get all the conflitos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Conflito> findAll(Pageable pageable) {
        log.debug("Request to get all Conflitos");
        return conflitoRepository.findAll(pageable);
    }


    /**
     * Get one conflito by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Conflito> findOne(Long id) {
        log.debug("Request to get Conflito : {}", id);
        return conflitoRepository.findById(id);
    }

    /**
     * Delete the conflito by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Conflito : {}", id);
        conflitoRepository.deleteById(id);
    }
}
