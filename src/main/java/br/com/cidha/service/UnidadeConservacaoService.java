package br.com.cidha.service;

import br.com.cidha.domain.UnidadeConservacao;
import br.com.cidha.repository.UnidadeConservacaoRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link UnidadeConservacao}.
 */
@Service
@Transactional
public class UnidadeConservacaoService {

    private final Logger log = LoggerFactory.getLogger(UnidadeConservacaoService.class);

    private final UnidadeConservacaoRepository unidadeConservacaoRepository;

    public UnidadeConservacaoService(UnidadeConservacaoRepository unidadeConservacaoRepository) {
        this.unidadeConservacaoRepository = unidadeConservacaoRepository;
    }

    /**
     * Save a unidadeConservacao.
     *
     * @param unidadeConservacao the entity to save.
     * @return the persisted entity.
     */
    public UnidadeConservacao save(UnidadeConservacao unidadeConservacao) {
        log.debug("Request to save UnidadeConservacao : {}", unidadeConservacao);
        return unidadeConservacaoRepository.save(unidadeConservacao);
    }

    /**
     * Partially update a unidadeConservacao.
     *
     * @param unidadeConservacao the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<UnidadeConservacao> partialUpdate(UnidadeConservacao unidadeConservacao) {
        log.debug("Request to partially update UnidadeConservacao : {}", unidadeConservacao);

        return unidadeConservacaoRepository
            .findById(unidadeConservacao.getId())
            .map(
                existingUnidadeConservacao -> {
                    if (unidadeConservacao.getDescricao() != null) {
                        existingUnidadeConservacao.setDescricao(unidadeConservacao.getDescricao());
                    }

                    return existingUnidadeConservacao;
                }
            )
            .map(unidadeConservacaoRepository::save);
    }

    /**
     * Get all the unidadeConservacaos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<UnidadeConservacao> findAll(Pageable pageable) {
        log.debug("Request to get all UnidadeConservacaos");
        return unidadeConservacaoRepository.findAll(pageable);
    }

    /**
     * Get one unidadeConservacao by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<UnidadeConservacao> findOne(Long id) {
        log.debug("Request to get UnidadeConservacao : {}", id);
        return unidadeConservacaoRepository.findById(id);
    }

    /**
     * Delete the unidadeConservacao by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete UnidadeConservacao : {}", id);
        unidadeConservacaoRepository.deleteById(id);
    }
}
