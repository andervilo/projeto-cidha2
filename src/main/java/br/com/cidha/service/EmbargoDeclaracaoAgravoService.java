package br.com.cidha.service;

import br.com.cidha.domain.EmbargoDeclaracaoAgravo;
import br.com.cidha.repository.EmbargoDeclaracaoAgravoRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link EmbargoDeclaracaoAgravo}.
 */
@Service
@Transactional
public class EmbargoDeclaracaoAgravoService {

    private final Logger log = LoggerFactory.getLogger(EmbargoDeclaracaoAgravoService.class);

    private final EmbargoDeclaracaoAgravoRepository embargoDeclaracaoAgravoRepository;

    public EmbargoDeclaracaoAgravoService(EmbargoDeclaracaoAgravoRepository embargoDeclaracaoAgravoRepository) {
        this.embargoDeclaracaoAgravoRepository = embargoDeclaracaoAgravoRepository;
    }

    /**
     * Save a embargoDeclaracaoAgravo.
     *
     * @param embargoDeclaracaoAgravo the entity to save.
     * @return the persisted entity.
     */
    public EmbargoDeclaracaoAgravo save(EmbargoDeclaracaoAgravo embargoDeclaracaoAgravo) {
        log.debug("Request to save EmbargoDeclaracaoAgravo : {}", embargoDeclaracaoAgravo);
        return embargoDeclaracaoAgravoRepository.save(embargoDeclaracaoAgravo);
    }

    /**
     * Partially update a embargoDeclaracaoAgravo.
     *
     * @param embargoDeclaracaoAgravo the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<EmbargoDeclaracaoAgravo> partialUpdate(EmbargoDeclaracaoAgravo embargoDeclaracaoAgravo) {
        log.debug("Request to partially update EmbargoDeclaracaoAgravo : {}", embargoDeclaracaoAgravo);

        return embargoDeclaracaoAgravoRepository
            .findById(embargoDeclaracaoAgravo.getId())
            .map(
                existingEmbargoDeclaracaoAgravo -> {
                    if (embargoDeclaracaoAgravo.getDescricao() != null) {
                        existingEmbargoDeclaracaoAgravo.setDescricao(embargoDeclaracaoAgravo.getDescricao());
                    }

                    return existingEmbargoDeclaracaoAgravo;
                }
            )
            .map(embargoDeclaracaoAgravoRepository::save);
    }

    /**
     * Get all the embargoDeclaracaoAgravos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<EmbargoDeclaracaoAgravo> findAll(Pageable pageable) {
        log.debug("Request to get all EmbargoDeclaracaoAgravos");
        return embargoDeclaracaoAgravoRepository.findAll(pageable);
    }

    /**
     * Get one embargoDeclaracaoAgravo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EmbargoDeclaracaoAgravo> findOne(Long id) {
        log.debug("Request to get EmbargoDeclaracaoAgravo : {}", id);
        return embargoDeclaracaoAgravoRepository.findById(id);
    }

    /**
     * Delete the embargoDeclaracaoAgravo by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete EmbargoDeclaracaoAgravo : {}", id);
        embargoDeclaracaoAgravoRepository.deleteById(id);
    }
}
