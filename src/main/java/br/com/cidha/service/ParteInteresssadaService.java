package br.com.cidha.service;

import br.com.cidha.domain.ParteInteresssada;
import br.com.cidha.repository.ParteInteresssadaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ParteInteresssada}.
 */
@Service
@Transactional
public class ParteInteresssadaService {

    private final Logger log = LoggerFactory.getLogger(ParteInteresssadaService.class);

    private final ParteInteresssadaRepository parteInteresssadaRepository;

    public ParteInteresssadaService(ParteInteresssadaRepository parteInteresssadaRepository) {
        this.parteInteresssadaRepository = parteInteresssadaRepository;
    }

    /**
     * Save a parteInteresssada.
     *
     * @param parteInteresssada the entity to save.
     * @return the persisted entity.
     */
    public ParteInteresssada save(ParteInteresssada parteInteresssada) {
        log.debug("Request to save ParteInteresssada : {}", parteInteresssada);
        return parteInteresssadaRepository.save(parteInteresssada);
    }

    /**
     * Get all the parteInteresssadas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ParteInteresssada> findAll(Pageable pageable) {
        log.debug("Request to get all ParteInteresssadas");
        return parteInteresssadaRepository.findAll(pageable);
    }


    /**
     * Get all the parteInteresssadas with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<ParteInteresssada> findAllWithEagerRelationships(Pageable pageable) {
        return parteInteresssadaRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one parteInteresssada by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ParteInteresssada> findOne(Long id) {
        log.debug("Request to get ParteInteresssada : {}", id);
        return parteInteresssadaRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the parteInteresssada by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ParteInteresssada : {}", id);
        parteInteresssadaRepository.deleteById(id);
    }
}
