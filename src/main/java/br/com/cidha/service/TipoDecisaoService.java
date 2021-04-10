package br.com.cidha.service;

import br.com.cidha.domain.TipoDecisao;
import br.com.cidha.repository.TipoDecisaoRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link TipoDecisao}.
 */
@Service
@Transactional
public class TipoDecisaoService {

    private final Logger log = LoggerFactory.getLogger(TipoDecisaoService.class);

    private final TipoDecisaoRepository tipoDecisaoRepository;

    public TipoDecisaoService(TipoDecisaoRepository tipoDecisaoRepository) {
        this.tipoDecisaoRepository = tipoDecisaoRepository;
    }

    /**
     * Save a tipoDecisao.
     *
     * @param tipoDecisao the entity to save.
     * @return the persisted entity.
     */
    public TipoDecisao save(TipoDecisao tipoDecisao) {
        log.debug("Request to save TipoDecisao : {}", tipoDecisao);
        return tipoDecisaoRepository.save(tipoDecisao);
    }

    /**
     * Partially update a tipoDecisao.
     *
     * @param tipoDecisao the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<TipoDecisao> partialUpdate(TipoDecisao tipoDecisao) {
        log.debug("Request to partially update TipoDecisao : {}", tipoDecisao);

        return tipoDecisaoRepository
            .findById(tipoDecisao.getId())
            .map(
                existingTipoDecisao -> {
                    if (tipoDecisao.getDescricao() != null) {
                        existingTipoDecisao.setDescricao(tipoDecisao.getDescricao());
                    }

                    return existingTipoDecisao;
                }
            )
            .map(tipoDecisaoRepository::save);
    }

    /**
     * Get all the tipoDecisaos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TipoDecisao> findAll(Pageable pageable) {
        log.debug("Request to get all TipoDecisaos");
        return tipoDecisaoRepository.findAll(pageable);
    }

    /**
     * Get one tipoDecisao by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TipoDecisao> findOne(Long id) {
        log.debug("Request to get TipoDecisao : {}", id);
        return tipoDecisaoRepository.findById(id);
    }

    /**
     * Delete the tipoDecisao by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TipoDecisao : {}", id);
        tipoDecisaoRepository.deleteById(id);
    }
}
