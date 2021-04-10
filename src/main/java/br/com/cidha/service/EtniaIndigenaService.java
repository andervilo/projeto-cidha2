package br.com.cidha.service;

import br.com.cidha.domain.EtniaIndigena;
import br.com.cidha.repository.EtniaIndigenaRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link EtniaIndigena}.
 */
@Service
@Transactional
public class EtniaIndigenaService {

    private final Logger log = LoggerFactory.getLogger(EtniaIndigenaService.class);

    private final EtniaIndigenaRepository etniaIndigenaRepository;

    public EtniaIndigenaService(EtniaIndigenaRepository etniaIndigenaRepository) {
        this.etniaIndigenaRepository = etniaIndigenaRepository;
    }

    /**
     * Save a etniaIndigena.
     *
     * @param etniaIndigena the entity to save.
     * @return the persisted entity.
     */
    public EtniaIndigena save(EtniaIndigena etniaIndigena) {
        log.debug("Request to save EtniaIndigena : {}", etniaIndigena);
        return etniaIndigenaRepository.save(etniaIndigena);
    }

    /**
     * Partially update a etniaIndigena.
     *
     * @param etniaIndigena the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<EtniaIndigena> partialUpdate(EtniaIndigena etniaIndigena) {
        log.debug("Request to partially update EtniaIndigena : {}", etniaIndigena);

        return etniaIndigenaRepository
            .findById(etniaIndigena.getId())
            .map(
                existingEtniaIndigena -> {
                    if (etniaIndigena.getNome() != null) {
                        existingEtniaIndigena.setNome(etniaIndigena.getNome());
                    }

                    return existingEtniaIndigena;
                }
            )
            .map(etniaIndigenaRepository::save);
    }

    /**
     * Get all the etniaIndigenas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<EtniaIndigena> findAll(Pageable pageable) {
        log.debug("Request to get all EtniaIndigenas");
        return etniaIndigenaRepository.findAll(pageable);
    }

    /**
     * Get one etniaIndigena by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EtniaIndigena> findOne(Long id) {
        log.debug("Request to get EtniaIndigena : {}", id);
        return etniaIndigenaRepository.findById(id);
    }

    /**
     * Delete the etniaIndigena by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete EtniaIndigena : {}", id);
        etniaIndigenaRepository.deleteById(id);
    }
}
