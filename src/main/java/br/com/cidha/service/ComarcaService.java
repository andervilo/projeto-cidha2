package br.com.cidha.service;

import br.com.cidha.domain.Comarca;
import br.com.cidha.repository.ComarcaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Comarca}.
 */
@Service
@Transactional
public class ComarcaService {

    private final Logger log = LoggerFactory.getLogger(ComarcaService.class);

    private final ComarcaRepository comarcaRepository;

    public ComarcaService(ComarcaRepository comarcaRepository) {
        this.comarcaRepository = comarcaRepository;
    }

    /**
     * Save a comarca.
     *
     * @param comarca the entity to save.
     * @return the persisted entity.
     */
    public Comarca save(Comarca comarca) {
        log.debug("Request to save Comarca : {}", comarca);
        return comarcaRepository.save(comarca);
    }

    /**
     * Get all the comarcas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Comarca> findAll(Pageable pageable) {
        log.debug("Request to get all Comarcas");
        return comarcaRepository.findAll(pageable);
    }


    /**
     * Get one comarca by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Comarca> findOne(Long id) {
        log.debug("Request to get Comarca : {}", id);
        return comarcaRepository.findById(id);
    }

    /**
     * Delete the comarca by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Comarca : {}", id);
        comarcaRepository.deleteById(id);
    }
}
