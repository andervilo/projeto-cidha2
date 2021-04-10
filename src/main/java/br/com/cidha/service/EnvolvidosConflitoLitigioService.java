package br.com.cidha.service;

import br.com.cidha.domain.EnvolvidosConflitoLitigio;
import br.com.cidha.repository.EnvolvidosConflitoLitigioRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link EnvolvidosConflitoLitigio}.
 */
@Service
@Transactional
public class EnvolvidosConflitoLitigioService {

    private final Logger log = LoggerFactory.getLogger(EnvolvidosConflitoLitigioService.class);

    private final EnvolvidosConflitoLitigioRepository envolvidosConflitoLitigioRepository;

    public EnvolvidosConflitoLitigioService(EnvolvidosConflitoLitigioRepository envolvidosConflitoLitigioRepository) {
        this.envolvidosConflitoLitigioRepository = envolvidosConflitoLitigioRepository;
    }

    /**
     * Save a envolvidosConflitoLitigio.
     *
     * @param envolvidosConflitoLitigio the entity to save.
     * @return the persisted entity.
     */
    public EnvolvidosConflitoLitigio save(EnvolvidosConflitoLitigio envolvidosConflitoLitigio) {
        log.debug("Request to save EnvolvidosConflitoLitigio : {}", envolvidosConflitoLitigio);
        return envolvidosConflitoLitigioRepository.save(envolvidosConflitoLitigio);
    }

    /**
     * Partially update a envolvidosConflitoLitigio.
     *
     * @param envolvidosConflitoLitigio the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<EnvolvidosConflitoLitigio> partialUpdate(EnvolvidosConflitoLitigio envolvidosConflitoLitigio) {
        log.debug("Request to partially update EnvolvidosConflitoLitigio : {}", envolvidosConflitoLitigio);

        return envolvidosConflitoLitigioRepository
            .findById(envolvidosConflitoLitigio.getId())
            .map(
                existingEnvolvidosConflitoLitigio -> {
                    if (envolvidosConflitoLitigio.getNumeroIndividuos() != null) {
                        existingEnvolvidosConflitoLitigio.setNumeroIndividuos(envolvidosConflitoLitigio.getNumeroIndividuos());
                    }
                    if (envolvidosConflitoLitigio.getFonteInformacaoQtde() != null) {
                        existingEnvolvidosConflitoLitigio.setFonteInformacaoQtde(envolvidosConflitoLitigio.getFonteInformacaoQtde());
                    }
                    if (envolvidosConflitoLitigio.getObservacoes() != null) {
                        existingEnvolvidosConflitoLitigio.setObservacoes(envolvidosConflitoLitigio.getObservacoes());
                    }

                    return existingEnvolvidosConflitoLitigio;
                }
            )
            .map(envolvidosConflitoLitigioRepository::save);
    }

    /**
     * Get all the envolvidosConflitoLitigios.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<EnvolvidosConflitoLitigio> findAll(Pageable pageable) {
        log.debug("Request to get all EnvolvidosConflitoLitigios");
        return envolvidosConflitoLitigioRepository.findAll(pageable);
    }

    /**
     * Get one envolvidosConflitoLitigio by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EnvolvidosConflitoLitigio> findOne(Long id) {
        log.debug("Request to get EnvolvidosConflitoLitigio : {}", id);
        return envolvidosConflitoLitigioRepository.findById(id);
    }

    /**
     * Delete the envolvidosConflitoLitigio by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete EnvolvidosConflitoLitigio : {}", id);
        envolvidosConflitoLitigioRepository.deleteById(id);
    }
}
