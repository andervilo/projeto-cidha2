package br.com.cidha.service;

import br.com.cidha.domain.InstrumentoInternacional;
import br.com.cidha.repository.InstrumentoInternacionalRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link InstrumentoInternacional}.
 */
@Service
@Transactional
public class InstrumentoInternacionalService {

    private final Logger log = LoggerFactory.getLogger(InstrumentoInternacionalService.class);

    private final InstrumentoInternacionalRepository instrumentoInternacionalRepository;

    public InstrumentoInternacionalService(InstrumentoInternacionalRepository instrumentoInternacionalRepository) {
        this.instrumentoInternacionalRepository = instrumentoInternacionalRepository;
    }

    /**
     * Save a instrumentoInternacional.
     *
     * @param instrumentoInternacional the entity to save.
     * @return the persisted entity.
     */
    public InstrumentoInternacional save(InstrumentoInternacional instrumentoInternacional) {
        log.debug("Request to save InstrumentoInternacional : {}", instrumentoInternacional);
        return instrumentoInternacionalRepository.save(instrumentoInternacional);
    }

    /**
     * Partially update a instrumentoInternacional.
     *
     * @param instrumentoInternacional the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<InstrumentoInternacional> partialUpdate(InstrumentoInternacional instrumentoInternacional) {
        log.debug("Request to partially update InstrumentoInternacional : {}", instrumentoInternacional);

        return instrumentoInternacionalRepository
            .findById(instrumentoInternacional.getId())
            .map(
                existingInstrumentoInternacional -> {
                    if (instrumentoInternacional.getInstrumentoInternacionalCitadoDescricao() != null) {
                        existingInstrumentoInternacional.setInstrumentoInternacionalCitadoDescricao(
                            instrumentoInternacional.getInstrumentoInternacionalCitadoDescricao()
                        );
                    }
                    if (instrumentoInternacional.getFolhasInstrumentoInternacional() != null) {
                        existingInstrumentoInternacional.setFolhasInstrumentoInternacional(
                            instrumentoInternacional.getFolhasInstrumentoInternacional()
                        );
                    }
                    if (instrumentoInternacional.getInstrumentoInternacionalSugerido() != null) {
                        existingInstrumentoInternacional.setInstrumentoInternacionalSugerido(
                            instrumentoInternacional.getInstrumentoInternacionalSugerido()
                        );
                    }

                    return existingInstrumentoInternacional;
                }
            )
            .map(instrumentoInternacionalRepository::save);
    }

    /**
     * Get all the instrumentoInternacionals.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<InstrumentoInternacional> findAll(Pageable pageable) {
        log.debug("Request to get all InstrumentoInternacionals");
        return instrumentoInternacionalRepository.findAll(pageable);
    }

    /**
     * Get one instrumentoInternacional by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<InstrumentoInternacional> findOne(Long id) {
        log.debug("Request to get InstrumentoInternacional : {}", id);
        return instrumentoInternacionalRepository.findById(id);
    }

    /**
     * Delete the instrumentoInternacional by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete InstrumentoInternacional : {}", id);
        instrumentoInternacionalRepository.deleteById(id);
    }
}
