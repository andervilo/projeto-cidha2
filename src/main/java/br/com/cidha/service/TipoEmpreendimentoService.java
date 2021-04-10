package br.com.cidha.service;

import br.com.cidha.domain.TipoEmpreendimento;
import br.com.cidha.repository.TipoEmpreendimentoRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link TipoEmpreendimento}.
 */
@Service
@Transactional
public class TipoEmpreendimentoService {

    private final Logger log = LoggerFactory.getLogger(TipoEmpreendimentoService.class);

    private final TipoEmpreendimentoRepository tipoEmpreendimentoRepository;

    public TipoEmpreendimentoService(TipoEmpreendimentoRepository tipoEmpreendimentoRepository) {
        this.tipoEmpreendimentoRepository = tipoEmpreendimentoRepository;
    }

    /**
     * Save a tipoEmpreendimento.
     *
     * @param tipoEmpreendimento the entity to save.
     * @return the persisted entity.
     */
    public TipoEmpreendimento save(TipoEmpreendimento tipoEmpreendimento) {
        log.debug("Request to save TipoEmpreendimento : {}", tipoEmpreendimento);
        return tipoEmpreendimentoRepository.save(tipoEmpreendimento);
    }

    /**
     * Partially update a tipoEmpreendimento.
     *
     * @param tipoEmpreendimento the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<TipoEmpreendimento> partialUpdate(TipoEmpreendimento tipoEmpreendimento) {
        log.debug("Request to partially update TipoEmpreendimento : {}", tipoEmpreendimento);

        return tipoEmpreendimentoRepository
            .findById(tipoEmpreendimento.getId())
            .map(
                existingTipoEmpreendimento -> {
                    if (tipoEmpreendimento.getDescricao() != null) {
                        existingTipoEmpreendimento.setDescricao(tipoEmpreendimento.getDescricao());
                    }

                    return existingTipoEmpreendimento;
                }
            )
            .map(tipoEmpreendimentoRepository::save);
    }

    /**
     * Get all the tipoEmpreendimentos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TipoEmpreendimento> findAll(Pageable pageable) {
        log.debug("Request to get all TipoEmpreendimentos");
        return tipoEmpreendimentoRepository.findAll(pageable);
    }

    /**
     * Get one tipoEmpreendimento by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TipoEmpreendimento> findOne(Long id) {
        log.debug("Request to get TipoEmpreendimento : {}", id);
        return tipoEmpreendimentoRepository.findById(id);
    }

    /**
     * Delete the tipoEmpreendimento by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TipoEmpreendimento : {}", id);
        tipoEmpreendimentoRepository.deleteById(id);
    }
}
