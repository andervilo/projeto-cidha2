package br.com.cidha.service;

import br.com.cidha.domain.AtividadeExploracaoIlegal;
import br.com.cidha.repository.AtividadeExploracaoIlegalRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link AtividadeExploracaoIlegal}.
 */
@Service
@Transactional
public class AtividadeExploracaoIlegalService {

    private final Logger log = LoggerFactory.getLogger(AtividadeExploracaoIlegalService.class);

    private final AtividadeExploracaoIlegalRepository atividadeExploracaoIlegalRepository;

    public AtividadeExploracaoIlegalService(AtividadeExploracaoIlegalRepository atividadeExploracaoIlegalRepository) {
        this.atividadeExploracaoIlegalRepository = atividadeExploracaoIlegalRepository;
    }

    /**
     * Save a atividadeExploracaoIlegal.
     *
     * @param atividadeExploracaoIlegal the entity to save.
     * @return the persisted entity.
     */
    public AtividadeExploracaoIlegal save(AtividadeExploracaoIlegal atividadeExploracaoIlegal) {
        log.debug("Request to save AtividadeExploracaoIlegal : {}", atividadeExploracaoIlegal);
        return atividadeExploracaoIlegalRepository.save(atividadeExploracaoIlegal);
    }

    /**
     * Partially update a atividadeExploracaoIlegal.
     *
     * @param atividadeExploracaoIlegal the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AtividadeExploracaoIlegal> partialUpdate(AtividadeExploracaoIlegal atividadeExploracaoIlegal) {
        log.debug("Request to partially update AtividadeExploracaoIlegal : {}", atividadeExploracaoIlegal);

        return atividadeExploracaoIlegalRepository
            .findById(atividadeExploracaoIlegal.getId())
            .map(
                existingAtividadeExploracaoIlegal -> {
                    if (atividadeExploracaoIlegal.getDescricao() != null) {
                        existingAtividadeExploracaoIlegal.setDescricao(atividadeExploracaoIlegal.getDescricao());
                    }

                    return existingAtividadeExploracaoIlegal;
                }
            )
            .map(atividadeExploracaoIlegalRepository::save);
    }

    /**
     * Get all the atividadeExploracaoIlegals.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AtividadeExploracaoIlegal> findAll(Pageable pageable) {
        log.debug("Request to get all AtividadeExploracaoIlegals");
        return atividadeExploracaoIlegalRepository.findAll(pageable);
    }

    /**
     * Get one atividadeExploracaoIlegal by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AtividadeExploracaoIlegal> findOne(Long id) {
        log.debug("Request to get AtividadeExploracaoIlegal : {}", id);
        return atividadeExploracaoIlegalRepository.findById(id);
    }

    /**
     * Delete the atividadeExploracaoIlegal by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AtividadeExploracaoIlegal : {}", id);
        atividadeExploracaoIlegalRepository.deleteById(id);
    }
}
