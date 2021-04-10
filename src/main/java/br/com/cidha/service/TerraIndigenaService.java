package br.com.cidha.service;

import br.com.cidha.domain.TerraIndigena;
import br.com.cidha.repository.TerraIndigenaRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link TerraIndigena}.
 */
@Service
@Transactional
public class TerraIndigenaService {

    private final Logger log = LoggerFactory.getLogger(TerraIndigenaService.class);

    private final TerraIndigenaRepository terraIndigenaRepository;

    public TerraIndigenaService(TerraIndigenaRepository terraIndigenaRepository) {
        this.terraIndigenaRepository = terraIndigenaRepository;
    }

    /**
     * Save a terraIndigena.
     *
     * @param terraIndigena the entity to save.
     * @return the persisted entity.
     */
    public TerraIndigena save(TerraIndigena terraIndigena) {
        log.debug("Request to save TerraIndigena : {}", terraIndigena);
        return terraIndigenaRepository.save(terraIndigena);
    }

    /**
     * Partially update a terraIndigena.
     *
     * @param terraIndigena the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<TerraIndigena> partialUpdate(TerraIndigena terraIndigena) {
        log.debug("Request to partially update TerraIndigena : {}", terraIndigena);

        return terraIndigenaRepository
            .findById(terraIndigena.getId())
            .map(
                existingTerraIndigena -> {
                    if (terraIndigena.getDescricao() != null) {
                        existingTerraIndigena.setDescricao(terraIndigena.getDescricao());
                    }

                    return existingTerraIndigena;
                }
            )
            .map(terraIndigenaRepository::save);
    }

    /**
     * Get all the terraIndigenas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TerraIndigena> findAll(Pageable pageable) {
        log.debug("Request to get all TerraIndigenas");
        return terraIndigenaRepository.findAll(pageable);
    }

    /**
     * Get all the terraIndigenas with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<TerraIndigena> findAllWithEagerRelationships(Pageable pageable) {
        return terraIndigenaRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one terraIndigena by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TerraIndigena> findOne(Long id) {
        log.debug("Request to get TerraIndigena : {}", id);
        return terraIndigenaRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the terraIndigena by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TerraIndigena : {}", id);
        terraIndigenaRepository.deleteById(id);
    }
}
