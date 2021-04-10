package br.com.cidha.service;

import br.com.cidha.domain.Direito;
import br.com.cidha.repository.DireitoRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Direito}.
 */
@Service
@Transactional
public class DireitoService {

    private final Logger log = LoggerFactory.getLogger(DireitoService.class);

    private final DireitoRepository direitoRepository;

    public DireitoService(DireitoRepository direitoRepository) {
        this.direitoRepository = direitoRepository;
    }

    /**
     * Save a direito.
     *
     * @param direito the entity to save.
     * @return the persisted entity.
     */
    public Direito save(Direito direito) {
        log.debug("Request to save Direito : {}", direito);
        return direitoRepository.save(direito);
    }

    /**
     * Partially update a direito.
     *
     * @param direito the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Direito> partialUpdate(Direito direito) {
        log.debug("Request to partially update Direito : {}", direito);

        return direitoRepository
            .findById(direito.getId())
            .map(
                existingDireito -> {
                    if (direito.getDescricao() != null) {
                        existingDireito.setDescricao(direito.getDescricao());
                    }

                    return existingDireito;
                }
            )
            .map(direitoRepository::save);
    }

    /**
     * Get all the direitos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Direito> findAll(Pageable pageable) {
        log.debug("Request to get all Direitos");
        return direitoRepository.findAll(pageable);
    }

    /**
     * Get one direito by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Direito> findOne(Long id) {
        log.debug("Request to get Direito : {}", id);
        return direitoRepository.findById(id);
    }

    /**
     * Delete the direito by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Direito : {}", id);
        direitoRepository.deleteById(id);
    }
}
