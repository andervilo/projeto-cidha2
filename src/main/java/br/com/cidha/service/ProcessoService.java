package br.com.cidha.service;

import br.com.cidha.domain.Processo;
import br.com.cidha.repository.ProcessoRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Processo}.
 */
@Service
@Transactional
public class ProcessoService {

    private final Logger log = LoggerFactory.getLogger(ProcessoService.class);

    private final ProcessoRepository processoRepository;

    public ProcessoService(ProcessoRepository processoRepository) {
        this.processoRepository = processoRepository;
    }

    /**
     * Save a processo.
     *
     * @param processo the entity to save.
     * @return the persisted entity.
     */
    public Processo save(Processo processo) {
        log.debug("Request to save Processo : {}", processo);
        return processoRepository.save(processo);
    }

    /**
     * Partially update a processo.
     *
     * @param processo the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Processo> partialUpdate(Processo processo) {
        log.debug("Request to partially update Processo : {}", processo);

        return processoRepository
            .findById(processo.getId())
            .map(
                existingProcesso -> {
                    if (processo.getOficio() != null) {
                        existingProcesso.setOficio(processo.getOficio());
                    }
                    if (processo.getAssunto() != null) {
                        existingProcesso.setAssunto(processo.getAssunto());
                    }
                    if (processo.getLinkUnico() != null) {
                        existingProcesso.setLinkUnico(processo.getLinkUnico());
                    }
                    if (processo.getLinkTrf() != null) {
                        existingProcesso.setLinkTrf(processo.getLinkTrf());
                    }
                    if (processo.getSubsecaoJudiciaria() != null) {
                        existingProcesso.setSubsecaoJudiciaria(processo.getSubsecaoJudiciaria());
                    }
                    if (processo.getTurmaTrf1() != null) {
                        existingProcesso.setTurmaTrf1(processo.getTurmaTrf1());
                    }
                    if (processo.getNumeroProcessoAdministrativo() != null) {
                        existingProcesso.setNumeroProcessoAdministrativo(processo.getNumeroProcessoAdministrativo());
                    }
                    if (processo.getNumeroProcessoJudicialPrimeiraInstancia() != null) {
                        existingProcesso.setNumeroProcessoJudicialPrimeiraInstancia(processo.getNumeroProcessoJudicialPrimeiraInstancia());
                    }
                    if (processo.getNumeroProcessoJudicialPrimeiraInstanciaLink() != null) {
                        existingProcesso.setNumeroProcessoJudicialPrimeiraInstanciaLink(
                            processo.getNumeroProcessoJudicialPrimeiraInstanciaLink()
                        );
                    }
                    if (processo.getNumeroProcessoJudicialPrimeiraInstanciaObservacoes() != null) {
                        existingProcesso.setNumeroProcessoJudicialPrimeiraInstanciaObservacoes(
                            processo.getNumeroProcessoJudicialPrimeiraInstanciaObservacoes()
                        );
                    }
                    if (processo.getParecer() != null) {
                        existingProcesso.setParecer(processo.getParecer());
                    }
                    if (processo.getApelacao() != null) {
                        existingProcesso.setApelacao(processo.getApelacao());
                    }

                    return existingProcesso;
                }
            )
            .map(processoRepository::save);
    }

    /**
     * Get all the processos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Processo> findAll(Pageable pageable) {
        log.debug("Request to get all Processos");
        return processoRepository.findAll(pageable);
    }

    /**
     * Get all the processos with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<Processo> findAllWithEagerRelationships(Pageable pageable) {
        return processoRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one processo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Processo> findOne(Long id) {
        log.debug("Request to get Processo : {}", id);
        return processoRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the processo by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Processo : {}", id);
        processoRepository.deleteById(id);
    }
}
