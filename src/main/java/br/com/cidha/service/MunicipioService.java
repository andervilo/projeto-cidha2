package br.com.cidha.service;

import br.com.cidha.domain.Municipio;
import br.com.cidha.repository.MunicipioRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Municipio}.
 */
@Service
@Transactional
public class MunicipioService {

    private final Logger log = LoggerFactory.getLogger(MunicipioService.class);

    private final MunicipioRepository municipioRepository;

    public MunicipioService(MunicipioRepository municipioRepository) {
        this.municipioRepository = municipioRepository;
    }

    /**
     * Save a municipio.
     *
     * @param municipio the entity to save.
     * @return the persisted entity.
     */
    public Municipio save(Municipio municipio) {
        log.debug("Request to save Municipio : {}", municipio);
        return municipioRepository.save(municipio);
    }

    /**
     * Partially update a municipio.
     *
     * @param municipio the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Municipio> partialUpdate(Municipio municipio) {
        log.debug("Request to partially update Municipio : {}", municipio);

        return municipioRepository
            .findById(municipio.getId())
            .map(
                existingMunicipio -> {
                    if (municipio.getAmazoniaLegal() != null) {
                        existingMunicipio.setAmazoniaLegal(municipio.getAmazoniaLegal());
                    }
                    if (municipio.getCodigoIbge() != null) {
                        existingMunicipio.setCodigoIbge(municipio.getCodigoIbge());
                    }
                    if (municipio.getEstado() != null) {
                        existingMunicipio.setEstado(municipio.getEstado());
                    }
                    if (municipio.getNome() != null) {
                        existingMunicipio.setNome(municipio.getNome());
                    }

                    return existingMunicipio;
                }
            )
            .map(municipioRepository::save);
    }

    /**
     * Get all the municipios.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Municipio> findAll(Pageable pageable) {
        log.debug("Request to get all Municipios");
        return municipioRepository.findAll(pageable);
    }

    /**
     * Get one municipio by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Municipio> findOne(Long id) {
        log.debug("Request to get Municipio : {}", id);
        return municipioRepository.findById(id);
    }

    /**
     * Delete the municipio by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Municipio : {}", id);
        municipioRepository.deleteById(id);
    }
}
