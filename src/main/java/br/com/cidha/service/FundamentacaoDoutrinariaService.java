package br.com.cidha.service;

import br.com.cidha.domain.FundamentacaoDoutrinaria;
import br.com.cidha.repository.FundamentacaoDoutrinariaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link FundamentacaoDoutrinaria}.
 */
@Service
@Transactional
public class FundamentacaoDoutrinariaService {

    private final Logger log = LoggerFactory.getLogger(FundamentacaoDoutrinariaService.class);

    private final FundamentacaoDoutrinariaRepository fundamentacaoDoutrinariaRepository;

    public FundamentacaoDoutrinariaService(FundamentacaoDoutrinariaRepository fundamentacaoDoutrinariaRepository) {
        this.fundamentacaoDoutrinariaRepository = fundamentacaoDoutrinariaRepository;
    }

    /**
     * Save a fundamentacaoDoutrinaria.
     *
     * @param fundamentacaoDoutrinaria the entity to save.
     * @return the persisted entity.
     */
    public FundamentacaoDoutrinaria save(FundamentacaoDoutrinaria fundamentacaoDoutrinaria) {
        log.debug("Request to save FundamentacaoDoutrinaria : {}", fundamentacaoDoutrinaria);
        return fundamentacaoDoutrinariaRepository.save(fundamentacaoDoutrinaria);
    }

    /**
     * Get all the fundamentacaoDoutrinarias.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<FundamentacaoDoutrinaria> findAll(Pageable pageable) {
        log.debug("Request to get all FundamentacaoDoutrinarias");
        return fundamentacaoDoutrinariaRepository.findAll(pageable);
    }


    /**
     * Get one fundamentacaoDoutrinaria by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<FundamentacaoDoutrinaria> findOne(Long id) {
        log.debug("Request to get FundamentacaoDoutrinaria : {}", id);
        return fundamentacaoDoutrinariaRepository.findById(id);
    }

    /**
     * Delete the fundamentacaoDoutrinaria by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete FundamentacaoDoutrinaria : {}", id);
        fundamentacaoDoutrinariaRepository.deleteById(id);
    }
}
