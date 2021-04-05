package br.com.cidha.service;

import br.com.cidha.domain.Jurisprudencia;
import br.com.cidha.repository.JurisprudenciaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Jurisprudencia}.
 */
@Service
@Transactional
public class JurisprudenciaService {

    private final Logger log = LoggerFactory.getLogger(JurisprudenciaService.class);

    private final JurisprudenciaRepository jurisprudenciaRepository;

    public JurisprudenciaService(JurisprudenciaRepository jurisprudenciaRepository) {
        this.jurisprudenciaRepository = jurisprudenciaRepository;
    }

    /**
     * Save a jurisprudencia.
     *
     * @param jurisprudencia the entity to save.
     * @return the persisted entity.
     */
    public Jurisprudencia save(Jurisprudencia jurisprudencia) {
        log.debug("Request to save Jurisprudencia : {}", jurisprudencia);
        return jurisprudenciaRepository.save(jurisprudencia);
    }

    /**
     * Get all the jurisprudencias.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Jurisprudencia> findAll(Pageable pageable) {
        log.debug("Request to get all Jurisprudencias");
        return jurisprudenciaRepository.findAll(pageable);
    }


    /**
     * Get one jurisprudencia by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Jurisprudencia> findOne(Long id) {
        log.debug("Request to get Jurisprudencia : {}", id);
        return jurisprudenciaRepository.findById(id);
    }

    /**
     * Delete the jurisprudencia by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Jurisprudencia : {}", id);
        jurisprudenciaRepository.deleteById(id);
    }
}
