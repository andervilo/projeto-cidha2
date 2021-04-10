package br.com.cidha.service;

import br.com.cidha.domain.Territorio;
import br.com.cidha.repository.TerritorioRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Territorio}.
 */
@Service
@Transactional
public class TerritorioService {

    private final Logger log = LoggerFactory.getLogger(TerritorioService.class);

    private final TerritorioRepository territorioRepository;

    public TerritorioService(TerritorioRepository territorioRepository) {
        this.territorioRepository = territorioRepository;
    }

    /**
     * Save a territorio.
     *
     * @param territorio the entity to save.
     * @return the persisted entity.
     */
    public Territorio save(Territorio territorio) {
        log.debug("Request to save Territorio : {}", territorio);
        return territorioRepository.save(territorio);
    }

    /**
     * Partially update a territorio.
     *
     * @param territorio the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Territorio> partialUpdate(Territorio territorio) {
        log.debug("Request to partially update Territorio : {}", territorio);

        return territorioRepository
            .findById(territorio.getId())
            .map(
                existingTerritorio -> {
                    if (territorio.getNome() != null) {
                        existingTerritorio.setNome(territorio.getNome());
                    }

                    return existingTerritorio;
                }
            )
            .map(territorioRepository::save);
    }

    /**
     * Get all the territorios.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Territorio> findAll(Pageable pageable) {
        log.debug("Request to get all Territorios");
        return territorioRepository.findAll(pageable);
    }

    /**
     * Get one territorio by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Territorio> findOne(Long id) {
        log.debug("Request to get Territorio : {}", id);
        return territorioRepository.findById(id);
    }

    /**
     * Delete the territorio by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Territorio : {}", id);
        territorioRepository.deleteById(id);
    }
}
