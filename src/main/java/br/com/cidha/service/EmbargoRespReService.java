package br.com.cidha.service;

import br.com.cidha.domain.EmbargoRespRe;
import br.com.cidha.repository.EmbargoRespReRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link EmbargoRespRe}.
 */
@Service
@Transactional
public class EmbargoRespReService {

    private final Logger log = LoggerFactory.getLogger(EmbargoRespReService.class);

    private final EmbargoRespReRepository embargoRespReRepository;

    public EmbargoRespReService(EmbargoRespReRepository embargoRespReRepository) {
        this.embargoRespReRepository = embargoRespReRepository;
    }

    /**
     * Save a embargoRespRe.
     *
     * @param embargoRespRe the entity to save.
     * @return the persisted entity.
     */
    public EmbargoRespRe save(EmbargoRespRe embargoRespRe) {
        log.debug("Request to save EmbargoRespRe : {}", embargoRespRe);
        return embargoRespReRepository.save(embargoRespRe);
    }

    /**
     * Get all the embargoRespRes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<EmbargoRespRe> findAll(Pageable pageable) {
        log.debug("Request to get all EmbargoRespRes");
        return embargoRespReRepository.findAll(pageable);
    }


    /**
     * Get one embargoRespRe by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EmbargoRespRe> findOne(Long id) {
        log.debug("Request to get EmbargoRespRe : {}", id);
        return embargoRespReRepository.findById(id);
    }

    /**
     * Delete the embargoRespRe by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete EmbargoRespRe : {}", id);
        embargoRespReRepository.deleteById(id);
    }
}
