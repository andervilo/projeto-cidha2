package br.com.cidha.service;

import br.com.cidha.domain.ConcessaoLiminar;
import br.com.cidha.repository.ConcessaoLiminarRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ConcessaoLiminar}.
 */
@Service
@Transactional
public class ConcessaoLiminarService {

    private final Logger log = LoggerFactory.getLogger(ConcessaoLiminarService.class);

    private final ConcessaoLiminarRepository concessaoLiminarRepository;

    public ConcessaoLiminarService(ConcessaoLiminarRepository concessaoLiminarRepository) {
        this.concessaoLiminarRepository = concessaoLiminarRepository;
    }

    /**
     * Save a concessaoLiminar.
     *
     * @param concessaoLiminar the entity to save.
     * @return the persisted entity.
     */
    public ConcessaoLiminar save(ConcessaoLiminar concessaoLiminar) {
        log.debug("Request to save ConcessaoLiminar : {}", concessaoLiminar);
        return concessaoLiminarRepository.save(concessaoLiminar);
    }

    /**
     * Partially update a concessaoLiminar.
     *
     * @param concessaoLiminar the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ConcessaoLiminar> partialUpdate(ConcessaoLiminar concessaoLiminar) {
        log.debug("Request to partially update ConcessaoLiminar : {}", concessaoLiminar);

        return concessaoLiminarRepository
            .findById(concessaoLiminar.getId())
            .map(
                existingConcessaoLiminar -> {
                    if (concessaoLiminar.getDescricao() != null) {
                        existingConcessaoLiminar.setDescricao(concessaoLiminar.getDescricao());
                    }

                    return existingConcessaoLiminar;
                }
            )
            .map(concessaoLiminarRepository::save);
    }

    /**
     * Get all the concessaoLiminars.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ConcessaoLiminar> findAll(Pageable pageable) {
        log.debug("Request to get all ConcessaoLiminars");
        return concessaoLiminarRepository.findAll(pageable);
    }

    /**
     * Get one concessaoLiminar by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ConcessaoLiminar> findOne(Long id) {
        log.debug("Request to get ConcessaoLiminar : {}", id);
        return concessaoLiminarRepository.findById(id);
    }

    /**
     * Delete the concessaoLiminar by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ConcessaoLiminar : {}", id);
        concessaoLiminarRepository.deleteById(id);
    }
}
