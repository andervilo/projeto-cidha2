package br.com.cidha.service;

import br.com.cidha.domain.ConcessaoLiminarCassada;
import br.com.cidha.repository.ConcessaoLiminarCassadaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ConcessaoLiminarCassada}.
 */
@Service
@Transactional
public class ConcessaoLiminarCassadaService {

    private final Logger log = LoggerFactory.getLogger(ConcessaoLiminarCassadaService.class);

    private final ConcessaoLiminarCassadaRepository concessaoLiminarCassadaRepository;

    public ConcessaoLiminarCassadaService(ConcessaoLiminarCassadaRepository concessaoLiminarCassadaRepository) {
        this.concessaoLiminarCassadaRepository = concessaoLiminarCassadaRepository;
    }

    /**
     * Save a concessaoLiminarCassada.
     *
     * @param concessaoLiminarCassada the entity to save.
     * @return the persisted entity.
     */
    public ConcessaoLiminarCassada save(ConcessaoLiminarCassada concessaoLiminarCassada) {
        log.debug("Request to save ConcessaoLiminarCassada : {}", concessaoLiminarCassada);
        return concessaoLiminarCassadaRepository.save(concessaoLiminarCassada);
    }

    /**
     * Get all the concessaoLiminarCassadas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ConcessaoLiminarCassada> findAll(Pageable pageable) {
        log.debug("Request to get all ConcessaoLiminarCassadas");
        return concessaoLiminarCassadaRepository.findAll(pageable);
    }


    /**
     * Get one concessaoLiminarCassada by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ConcessaoLiminarCassada> findOne(Long id) {
        log.debug("Request to get ConcessaoLiminarCassada : {}", id);
        return concessaoLiminarCassadaRepository.findById(id);
    }

    /**
     * Delete the concessaoLiminarCassada by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ConcessaoLiminarCassada : {}", id);
        concessaoLiminarCassadaRepository.deleteById(id);
    }
}
