package br.com.cidha.service;

import br.com.cidha.domain.TipoData;
import br.com.cidha.repository.TipoDataRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link TipoData}.
 */
@Service
@Transactional
public class TipoDataService {

    private final Logger log = LoggerFactory.getLogger(TipoDataService.class);

    private final TipoDataRepository tipoDataRepository;

    public TipoDataService(TipoDataRepository tipoDataRepository) {
        this.tipoDataRepository = tipoDataRepository;
    }

    /**
     * Save a tipoData.
     *
     * @param tipoData the entity to save.
     * @return the persisted entity.
     */
    public TipoData save(TipoData tipoData) {
        log.debug("Request to save TipoData : {}", tipoData);
        return tipoDataRepository.save(tipoData);
    }

    /**
     * Partially update a tipoData.
     *
     * @param tipoData the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<TipoData> partialUpdate(TipoData tipoData) {
        log.debug("Request to partially update TipoData : {}", tipoData);

        return tipoDataRepository
            .findById(tipoData.getId())
            .map(
                existingTipoData -> {
                    if (tipoData.getDescricao() != null) {
                        existingTipoData.setDescricao(tipoData.getDescricao());
                    }

                    return existingTipoData;
                }
            )
            .map(tipoDataRepository::save);
    }

    /**
     * Get all the tipoData.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TipoData> findAll(Pageable pageable) {
        log.debug("Request to get all TipoData");
        return tipoDataRepository.findAll(pageable);
    }

    /**
     * Get one tipoData by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TipoData> findOne(Long id) {
        log.debug("Request to get TipoData : {}", id);
        return tipoDataRepository.findById(id);
    }

    /**
     * Delete the tipoData by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TipoData : {}", id);
        tipoDataRepository.deleteById(id);
    }
}
