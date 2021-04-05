package br.com.cidha.service;

import br.com.cidha.domain.EmbargoDeclaracao;
import br.com.cidha.repository.EmbargoDeclaracaoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link EmbargoDeclaracao}.
 */
@Service
@Transactional
public class EmbargoDeclaracaoService {

    private final Logger log = LoggerFactory.getLogger(EmbargoDeclaracaoService.class);

    private final EmbargoDeclaracaoRepository embargoDeclaracaoRepository;

    public EmbargoDeclaracaoService(EmbargoDeclaracaoRepository embargoDeclaracaoRepository) {
        this.embargoDeclaracaoRepository = embargoDeclaracaoRepository;
    }

    /**
     * Save a embargoDeclaracao.
     *
     * @param embargoDeclaracao the entity to save.
     * @return the persisted entity.
     */
    public EmbargoDeclaracao save(EmbargoDeclaracao embargoDeclaracao) {
        log.debug("Request to save EmbargoDeclaracao : {}", embargoDeclaracao);
        return embargoDeclaracaoRepository.save(embargoDeclaracao);
    }

    /**
     * Get all the embargoDeclaracaos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<EmbargoDeclaracao> findAll(Pageable pageable) {
        log.debug("Request to get all EmbargoDeclaracaos");
        return embargoDeclaracaoRepository.findAll(pageable);
    }


    /**
     * Get one embargoDeclaracao by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EmbargoDeclaracao> findOne(Long id) {
        log.debug("Request to get EmbargoDeclaracao : {}", id);
        return embargoDeclaracaoRepository.findById(id);
    }

    /**
     * Delete the embargoDeclaracao by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete EmbargoDeclaracao : {}", id);
        embargoDeclaracaoRepository.deleteById(id);
    }
}
