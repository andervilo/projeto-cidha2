package br.com.cidha.service;

import br.com.cidha.domain.TipoRepresentante;
import br.com.cidha.repository.TipoRepresentanteRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link TipoRepresentante}.
 */
@Service
@Transactional
public class TipoRepresentanteService {

    private final Logger log = LoggerFactory.getLogger(TipoRepresentanteService.class);

    private final TipoRepresentanteRepository tipoRepresentanteRepository;

    public TipoRepresentanteService(TipoRepresentanteRepository tipoRepresentanteRepository) {
        this.tipoRepresentanteRepository = tipoRepresentanteRepository;
    }

    /**
     * Save a tipoRepresentante.
     *
     * @param tipoRepresentante the entity to save.
     * @return the persisted entity.
     */
    public TipoRepresentante save(TipoRepresentante tipoRepresentante) {
        log.debug("Request to save TipoRepresentante : {}", tipoRepresentante);
        return tipoRepresentanteRepository.save(tipoRepresentante);
    }

    /**
     * Partially update a tipoRepresentante.
     *
     * @param tipoRepresentante the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<TipoRepresentante> partialUpdate(TipoRepresentante tipoRepresentante) {
        log.debug("Request to partially update TipoRepresentante : {}", tipoRepresentante);

        return tipoRepresentanteRepository
            .findById(tipoRepresentante.getId())
            .map(
                existingTipoRepresentante -> {
                    if (tipoRepresentante.getDescricao() != null) {
                        existingTipoRepresentante.setDescricao(tipoRepresentante.getDescricao());
                    }

                    return existingTipoRepresentante;
                }
            )
            .map(tipoRepresentanteRepository::save);
    }

    /**
     * Get all the tipoRepresentantes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TipoRepresentante> findAll(Pageable pageable) {
        log.debug("Request to get all TipoRepresentantes");
        return tipoRepresentanteRepository.findAll(pageable);
    }

    /**
     * Get one tipoRepresentante by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TipoRepresentante> findOne(Long id) {
        log.debug("Request to get TipoRepresentante : {}", id);
        return tipoRepresentanteRepository.findById(id);
    }

    /**
     * Delete the tipoRepresentante by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TipoRepresentante : {}", id);
        tipoRepresentanteRepository.deleteById(id);
    }
}
