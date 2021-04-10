package br.com.cidha.service;

import br.com.cidha.domain.EmbargoDeclaracao;
import br.com.cidha.repository.EmbargoDeclaracaoRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
     * Partially update a embargoDeclaracao.
     *
     * @param embargoDeclaracao the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<EmbargoDeclaracao> partialUpdate(EmbargoDeclaracao embargoDeclaracao) {
        log.debug("Request to partially update EmbargoDeclaracao : {}", embargoDeclaracao);

        return embargoDeclaracaoRepository
            .findById(embargoDeclaracao.getId())
            .map(
                existingEmbargoDeclaracao -> {
                    if (embargoDeclaracao.getDescricao() != null) {
                        existingEmbargoDeclaracao.setDescricao(embargoDeclaracao.getDescricao());
                    }

                    return existingEmbargoDeclaracao;
                }
            )
            .map(embargoDeclaracaoRepository::save);
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
