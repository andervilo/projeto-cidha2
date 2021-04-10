package br.com.cidha.service;

import br.com.cidha.domain.EmbargoRecursoEspecial;
import br.com.cidha.repository.EmbargoRecursoEspecialRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link EmbargoRecursoEspecial}.
 */
@Service
@Transactional
public class EmbargoRecursoEspecialService {

    private final Logger log = LoggerFactory.getLogger(EmbargoRecursoEspecialService.class);

    private final EmbargoRecursoEspecialRepository embargoRecursoEspecialRepository;

    public EmbargoRecursoEspecialService(EmbargoRecursoEspecialRepository embargoRecursoEspecialRepository) {
        this.embargoRecursoEspecialRepository = embargoRecursoEspecialRepository;
    }

    /**
     * Save a embargoRecursoEspecial.
     *
     * @param embargoRecursoEspecial the entity to save.
     * @return the persisted entity.
     */
    public EmbargoRecursoEspecial save(EmbargoRecursoEspecial embargoRecursoEspecial) {
        log.debug("Request to save EmbargoRecursoEspecial : {}", embargoRecursoEspecial);
        return embargoRecursoEspecialRepository.save(embargoRecursoEspecial);
    }

    /**
     * Partially update a embargoRecursoEspecial.
     *
     * @param embargoRecursoEspecial the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<EmbargoRecursoEspecial> partialUpdate(EmbargoRecursoEspecial embargoRecursoEspecial) {
        log.debug("Request to partially update EmbargoRecursoEspecial : {}", embargoRecursoEspecial);

        return embargoRecursoEspecialRepository
            .findById(embargoRecursoEspecial.getId())
            .map(
                existingEmbargoRecursoEspecial -> {
                    if (embargoRecursoEspecial.getDescricao() != null) {
                        existingEmbargoRecursoEspecial.setDescricao(embargoRecursoEspecial.getDescricao());
                    }

                    return existingEmbargoRecursoEspecial;
                }
            )
            .map(embargoRecursoEspecialRepository::save);
    }

    /**
     * Get all the embargoRecursoEspecials.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<EmbargoRecursoEspecial> findAll(Pageable pageable) {
        log.debug("Request to get all EmbargoRecursoEspecials");
        return embargoRecursoEspecialRepository.findAll(pageable);
    }

    /**
     * Get one embargoRecursoEspecial by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EmbargoRecursoEspecial> findOne(Long id) {
        log.debug("Request to get EmbargoRecursoEspecial : {}", id);
        return embargoRecursoEspecialRepository.findById(id);
    }

    /**
     * Delete the embargoRecursoEspecial by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete EmbargoRecursoEspecial : {}", id);
        embargoRecursoEspecialRepository.deleteById(id);
    }
}
