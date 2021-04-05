package br.com.cidha.service;

import br.com.cidha.domain.UnidadeConservacao;
import br.com.cidha.repository.UnidadeConservacaoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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
