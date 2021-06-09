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
                    if (processo.getNumeroProcesso() != null) {
                        existingProcesso.setNumeroProcesso(processo.getNumeroProcesso());
                    }
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
                    if (processo.getFolhasProcessoConcessaoLiminar() != null) {
                        existingProcesso.setFolhasProcessoConcessaoLiminar(processo.getFolhasProcessoConcessaoLiminar());
                    }
                    if (processo.getConcessaoLiminarObservacoes() != null) {
                        existingProcesso.setConcessaoLiminarObservacoes(processo.getConcessaoLiminarObservacoes());
                    }
                    if (processo.getFolhasProcessoCassacao() != null) {
                        existingProcesso.setFolhasProcessoCassacao(processo.getFolhasProcessoCassacao());
                    }
                    if (processo.getFolhasParecer() != null) {
                        existingProcesso.setFolhasParecer(processo.getFolhasParecer());
                    }
                    if (processo.getFolhasEmbargo() != null) {
                        existingProcesso.setFolhasEmbargo(processo.getFolhasEmbargo());
                    }
                    if (processo.getAcordaoEmbargo() != null) {
                        existingProcesso.setAcordaoEmbargo(processo.getAcordaoEmbargo());
                    }
                    if (processo.getFolhasCienciaJulgEmbargos() != null) {
                        existingProcesso.setFolhasCienciaJulgEmbargos(processo.getFolhasCienciaJulgEmbargos());
                    }
                    if (processo.getApelacao() != null) {
                        existingProcesso.setApelacao(processo.getApelacao());
                    }
                    if (processo.getFolhasApelacao() != null) {
                        existingProcesso.setFolhasApelacao(processo.getFolhasApelacao());
                    }
                    if (processo.getAcordaoApelacao() != null) {
                        existingProcesso.setAcordaoApelacao(processo.getAcordaoApelacao());
                    }
                    if (processo.getFolhasCienciaJulgApelacao() != null) {
                        existingProcesso.setFolhasCienciaJulgApelacao(processo.getFolhasCienciaJulgApelacao());
                    }
                    if (processo.getEmbargoDeclaracao() != null) {
                        existingProcesso.setEmbargoDeclaracao(processo.getEmbargoDeclaracao());
                    }
                    if (processo.getEmbargoRecursoExtraordinario() != null) {
                        existingProcesso.setEmbargoRecursoExtraordinario(processo.getEmbargoRecursoExtraordinario());
                    }
                    if (processo.getFolhasRecursoEspecial() != null) {
                        existingProcesso.setFolhasRecursoEspecial(processo.getFolhasRecursoEspecial());
                    }
                    if (processo.getAcordaoRecursoEspecial() != null) {
                        existingProcesso.setAcordaoRecursoEspecial(processo.getAcordaoRecursoEspecial());
                    }
                    if (processo.getFolhasCienciaJulgamentoRecursoEspecial() != null) {
                        existingProcesso.setFolhasCienciaJulgamentoRecursoEspecial(processo.getFolhasCienciaJulgamentoRecursoEspecial());
                    }
                    if (processo.getEmbargoRecursoEspecial() != null) {
                        existingProcesso.setEmbargoRecursoEspecial(processo.getEmbargoRecursoEspecial());
                    }
                    if (processo.getFolhasCiencia() != null) {
                        existingProcesso.setFolhasCiencia(processo.getFolhasCiencia());
                    }
                    if (processo.getAgravoRespRe() != null) {
                        existingProcesso.setAgravoRespRe(processo.getAgravoRespRe());
                    }
                    if (processo.getFolhasRespRe() != null) {
                        existingProcesso.setFolhasRespRe(processo.getFolhasRespRe());
                    }
                    if (processo.getAcordaoAgravoRespRe() != null) {
                        existingProcesso.setAcordaoAgravoRespRe(processo.getAcordaoAgravoRespRe());
                    }
                    if (processo.getFolhasCienciaJulgamentoAgravoRespRe() != null) {
                        existingProcesso.setFolhasCienciaJulgamentoAgravoRespRe(processo.getFolhasCienciaJulgamentoAgravoRespRe());
                    }
                    if (processo.getEmbargoRespRe() != null) {
                        existingProcesso.setEmbargoRespRe(processo.getEmbargoRespRe());
                    }
                    if (processo.getAgravoInterno() != null) {
                        existingProcesso.setAgravoInterno(processo.getAgravoInterno());
                    }
                    if (processo.getFolhasAgravoInterno() != null) {
                        existingProcesso.setFolhasAgravoInterno(processo.getFolhasAgravoInterno());
                    }
                    if (processo.getEmbargoRecursoAgravo() != null) {
                        existingProcesso.setEmbargoRecursoAgravo(processo.getEmbargoRecursoAgravo());
                    }
                    if (processo.getObservacoes() != null) {
                        existingProcesso.setObservacoes(processo.getObservacoes());
                    }
                    if (processo.getRecursoSTJ() != null) {
                        existingProcesso.setRecursoSTJ(processo.getRecursoSTJ());
                    }
                    if (processo.getLinkRecursoSTJ() != null) {
                        existingProcesso.setLinkRecursoSTJ(processo.getLinkRecursoSTJ());
                    }
                    if (processo.getFolhasRecursoSTJ() != null) {
                        existingProcesso.setFolhasRecursoSTJ(processo.getFolhasRecursoSTJ());
                    }
                    if (processo.getRecursoSTF() != null) {
                        existingProcesso.setRecursoSTF(processo.getRecursoSTF());
                    }
                    if (processo.getLinkRecursoSTF() != null) {
                        existingProcesso.setLinkRecursoSTF(processo.getLinkRecursoSTF());
                    }
                    if (processo.getFolhasRecursoSTF() != null) {
                        existingProcesso.setFolhasRecursoSTF(processo.getFolhasRecursoSTF());
                    }
                    if (processo.getFolhasMemorialMPF() != null) {
                        existingProcesso.setFolhasMemorialMPF(processo.getFolhasMemorialMPF());
                    }
                    if (processo.getExecusaoProvisoria() != null) {
                        existingProcesso.setExecusaoProvisoria(processo.getExecusaoProvisoria());
                    }
                    if (processo.getNumeracaoExecusaoProvisoria() != null) {
                        existingProcesso.setNumeracaoExecusaoProvisoria(processo.getNumeracaoExecusaoProvisoria());
                    }
                    if (processo.getRecuperacaoEfetivaCumprimentoSentenca() != null) {
                        existingProcesso.setRecuperacaoEfetivaCumprimentoSentenca(processo.getRecuperacaoEfetivaCumprimentoSentenca());
                    }
                    if (processo.getRecuperacaoEfetivaCumprimentoSentencaObservacoes() != null) {
                        existingProcesso.setRecuperacaoEfetivaCumprimentoSentencaObservacoes(
                            processo.getRecuperacaoEfetivaCumprimentoSentencaObservacoes()
                        );
                    }
                    if (processo.getEnvolveEmpreendimento() != null) {
                        existingProcesso.setEnvolveEmpreendimento(processo.getEnvolveEmpreendimento());
                    }
                    if (processo.getEnvolveExploracaoIlegal() != null) {
                        existingProcesso.setEnvolveExploracaoIlegal(processo.getEnvolveExploracaoIlegal());
                    }
                    if (processo.getEnvolveTerraQuilombola() != null) {
                        existingProcesso.setEnvolveTerraQuilombola(processo.getEnvolveTerraQuilombola());
                    }
                    if (processo.getEnvolveTerraComunidadeTradicional() != null) {
                        existingProcesso.setEnvolveTerraComunidadeTradicional(processo.getEnvolveTerraComunidadeTradicional());
                    }
                    if (processo.getEnvolveTerraIndigena() != null) {
                        existingProcesso.setEnvolveTerraIndigena(processo.getEnvolveTerraIndigena());
                    }
                    if (processo.getResumoFatos() != null) {
                        existingProcesso.setResumoFatos(processo.getResumoFatos());
                    }
                    if (processo.getTamanhoArea() != null) {
                        existingProcesso.setTamanhoArea(processo.getTamanhoArea());
                    }
                    if (processo.getValorArea() != null) {
                        existingProcesso.setValorArea(processo.getValorArea());
                    }
                    if (processo.getTamanhoAreaObservacao() != null) {
                        existingProcesso.setTamanhoAreaObservacao(processo.getTamanhoAreaObservacao());
                    }
                    if (processo.getDadosGeograficosLitigioConflito() != null) {
                        existingProcesso.setDadosGeograficosLitigioConflito(processo.getDadosGeograficosLitigioConflito());
                    }
                    if (processo.getLatitude() != null) {
                        existingProcesso.setLatitude(processo.getLatitude());
                    }
                    if (processo.getLongitude() != null) {
                        existingProcesso.setLongitude(processo.getLongitude());
                    }
                    if (processo.getNumeroProcessoMPF() != null) {
                        existingProcesso.setNumeroProcessoMPF(processo.getNumeroProcessoMPF());
                    }
                    if (processo.getNumeroEmbargo() != null) {
                        existingProcesso.setNumeroEmbargo(processo.getNumeroEmbargo());
                    }
                    if (processo.getPautaApelacao() != null) {
                        existingProcesso.setPautaApelacao(processo.getPautaApelacao());
                    }
                    if (processo.getNumeroRecursoEspecial() != null) {
                        existingProcesso.setNumeroRecursoEspecial(processo.getNumeroRecursoEspecial());
                    }
                    if (processo.getAdmissiblidade() != null) {
                        existingProcesso.setAdmissiblidade(processo.getAdmissiblidade());
                    }
                    if (processo.getEnvolveGrandeProjeto() != null) {
                        existingProcesso.setEnvolveGrandeProjeto(processo.getEnvolveGrandeProjeto());
                    }
                    if (processo.getEnvolveUnidadeConservacao() != null) {
                        existingProcesso.setEnvolveUnidadeConservacao(processo.getEnvolveUnidadeConservacao());
                    }
                    if (processo.getLinkReferencia() != null) {
                        existingProcesso.setLinkReferencia(processo.getLinkReferencia());
                    }
                    if (processo.getStatusProcesso() != null) {
                        existingProcesso.setStatusProcesso(processo.getStatusProcesso());
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
