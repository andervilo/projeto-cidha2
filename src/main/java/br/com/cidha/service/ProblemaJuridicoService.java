package br.com.cidha.service;

import br.com.cidha.domain.ProblemaJuridico;
import br.com.cidha.repository.ProblemaJuridicoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ProblemaJuridico}.
 */
@Service
@Transactional
public class ProblemaJuridicoService {

    private final Logger log = LoggerFactory.getLogger(ProblemaJuridicoService.class);

    private final ProblemaJuridicoRepository problemaJuridicoRepository;

    public ProblemaJuridicoService(ProblemaJuridicoRepository problemaJuridicoRepository) {
        this.problemaJuridicoRepository = problemaJuridicoRepository;
    }

    /**
     * Save a problemaJuridico.
     *
     * @param problemaJuridico the entity to save.
     * @return the persisted entity.
     */
    public ProblemaJuridico save(ProblemaJuridico problemaJuridico) {
        log.debug("Request to save ProblemaJuridico : {}", problemaJuridico);
        return problemaJuridicoRepository.save(problemaJuridico);
    }

    /**
     * Get all the problemaJuridicos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ProblemaJuridico> findAll(Pageable pageable) {
        log.debug("Request to get all ProblemaJuridicos");
        return problemaJuridicoRepository.findAll(pageable);
    }


    /**
     * Get all the problemaJuridicos with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<ProblemaJuridico> findAllWithEagerRelationships(Pageable pageable) {
        return problemaJuridicoRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one problemaJuridico by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProblemaJuridico> findOne(Long id) {
        log.debug("Request to get ProblemaJuridico : {}", id);
        return problemaJuridicoRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the problemaJuridico by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ProblemaJuridico : {}", id);
        problemaJuridicoRepository.deleteById(id);
    }
}
