package br.com.cidha.service;

import br.com.cidha.domain.Relator;
import br.com.cidha.repository.RelatorRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Relator}.
 */
@Service
@Transactional
public class RelatorService {

    private final Logger log = LoggerFactory.getLogger(RelatorService.class);

    private final RelatorRepository relatorRepository;

    public RelatorService(RelatorRepository relatorRepository) {
        this.relatorRepository = relatorRepository;
    }

    /**
     * Save a relator.
     *
     * @param relator the entity to save.
     * @return the persisted entity.
     */
    public Relator save(Relator relator) {
        log.debug("Request to save Relator : {}", relator);
        return relatorRepository.save(relator);
    }

    /**
     * Partially update a relator.
     *
     * @param relator the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Relator> partialUpdate(Relator relator) {
        log.debug("Request to partially update Relator : {}", relator);

        return relatorRepository
            .findById(relator.getId())
            .map(
                existingRelator -> {
                    if (relator.getNome() != null) {
                        existingRelator.setNome(relator.getNome());
                    }

                    return existingRelator;
                }
            )
            .map(relatorRepository::save);
    }

    /**
     * Get all the relators.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Relator> findAll(Pageable pageable) {
        log.debug("Request to get all Relators");
        return relatorRepository.findAll(pageable);
    }

    /**
     * Get one relator by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Relator> findOne(Long id) {
        log.debug("Request to get Relator : {}", id);
        return relatorRepository.findById(id);
    }

    /**
     * Delete the relator by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Relator : {}", id);
        relatorRepository.deleteById(id);
    }
}
