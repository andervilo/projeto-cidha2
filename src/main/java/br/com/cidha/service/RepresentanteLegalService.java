package br.com.cidha.service;

import br.com.cidha.domain.RepresentanteLegal;
import br.com.cidha.repository.RepresentanteLegalRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link RepresentanteLegal}.
 */
@Service
@Transactional
public class RepresentanteLegalService {

    private final Logger log = LoggerFactory.getLogger(RepresentanteLegalService.class);

    private final RepresentanteLegalRepository representanteLegalRepository;

    public RepresentanteLegalService(RepresentanteLegalRepository representanteLegalRepository) {
        this.representanteLegalRepository = representanteLegalRepository;
    }

    /**
     * Save a representanteLegal.
     *
     * @param representanteLegal the entity to save.
     * @return the persisted entity.
     */
    public RepresentanteLegal save(RepresentanteLegal representanteLegal) {
        log.debug("Request to save RepresentanteLegal : {}", representanteLegal);
        return representanteLegalRepository.save(representanteLegal);
    }

    /**
     * Get all the representanteLegals.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<RepresentanteLegal> findAll(Pageable pageable) {
        log.debug("Request to get all RepresentanteLegals");
        return representanteLegalRepository.findAll(pageable);
    }


    /**
     * Get one representanteLegal by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<RepresentanteLegal> findOne(Long id) {
        log.debug("Request to get RepresentanteLegal : {}", id);
        return representanteLegalRepository.findById(id);
    }

    /**
     * Delete the representanteLegal by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete RepresentanteLegal : {}", id);
        representanteLegalRepository.deleteById(id);
    }
}
