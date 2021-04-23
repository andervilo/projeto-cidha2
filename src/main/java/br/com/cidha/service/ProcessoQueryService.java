package br.com.cidha.service;

import br.com.cidha.domain.*; // for static metamodels
import br.com.cidha.domain.Processo;
import br.com.cidha.repository.ProcessoRepository;
import br.com.cidha.service.criteria.ProcessoCriteria;
import java.util.List;
import javax.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Processo} entities in the database.
 * The main input is a {@link ProcessoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Processo} or a {@link Page} of {@link Processo} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProcessoQueryService extends QueryService<Processo> {

    private final Logger log = LoggerFactory.getLogger(ProcessoQueryService.class);

    private final ProcessoRepository processoRepository;

    public ProcessoQueryService(ProcessoRepository processoRepository) {
        this.processoRepository = processoRepository;
    }

    /**
     * Return a {@link List} of {@link Processo} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Processo> findByCriteria(ProcessoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Processo> specification = createSpecification(criteria);
        return processoRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Processo} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Processo> findByCriteria(ProcessoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Processo> specification = createSpecification(criteria);
        return processoRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProcessoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Processo> specification = createSpecification(criteria);
        return processoRepository.count(specification);
    }

    /**
     * Function to convert {@link ProcessoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Processo> createSpecification(ProcessoCriteria criteria) {
        Specification<Processo> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Processo_.id));
            }
            if (criteria.getNumeroProcesso() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNumeroProcesso(), Processo_.numeroProcesso));
            }
            if (criteria.getOficio() != null) {
                specification = specification.and(buildStringSpecification(criteria.getOficio(), Processo_.oficio));
            }
            if (criteria.getLinkUnico() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLinkUnico(), Processo_.linkUnico));
            }
            if (criteria.getLinkTrf() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLinkTrf(), Processo_.linkTrf));
            }
            if (criteria.getSecaoJudiciaria() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSecaoJudiciaria(), Processo_.secaoJudiciaria));
            }
            if (criteria.getSubsecaoJudiciaria() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSubsecaoJudiciaria(), Processo_.subsecaoJudiciaria));
            }
            if (criteria.getTurmaTrf1() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTurmaTrf1(), Processo_.turmaTrf1));
            }
            if (criteria.getNumeroProcessoAdministrativo() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(criteria.getNumeroProcessoAdministrativo(), Processo_.numeroProcessoAdministrativo)
                    );
            }
            if (criteria.getNumeroProcessoJudicialPrimeiraInstancia() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(
                            criteria.getNumeroProcessoJudicialPrimeiraInstancia(),
                            Processo_.numeroProcessoJudicialPrimeiraInstancia
                        )
                    );
            }
            if (criteria.getNumeroProcessoJudicialPrimeiraInstanciaLink() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(
                            criteria.getNumeroProcessoJudicialPrimeiraInstanciaLink(),
                            Processo_.numeroProcessoJudicialPrimeiraInstanciaLink
                        )
                    );
            }
            if (criteria.getParecer() != null) {
                specification = specification.and(buildSpecification(criteria.getParecer(), Processo_.parecer));
            }
            if (criteria.getFolhasProcessoConcessaoLiminar() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(criteria.getFolhasProcessoConcessaoLiminar(), Processo_.folhasProcessoConcessaoLiminar)
                    );
            }
            if (criteria.getFolhasProcessoCassacao() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getFolhasProcessoCassacao(), Processo_.folhasProcessoCassacao));
            }
            if (criteria.getFolhasParecer() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFolhasParecer(), Processo_.folhasParecer));
            }
            if (criteria.getFolhasEmbargo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFolhasEmbargo(), Processo_.folhasEmbargo));
            }
            if (criteria.getFolhasCienciaJulgEmbargos() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(criteria.getFolhasCienciaJulgEmbargos(), Processo_.folhasCienciaJulgEmbargos)
                    );
            }
            if (criteria.getApelacao() != null) {
                specification = specification.and(buildStringSpecification(criteria.getApelacao(), Processo_.apelacao));
            }
            if (criteria.getFolhasApelacao() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFolhasApelacao(), Processo_.folhasApelacao));
            }
            if (criteria.getFolhasCienciaJulgApelacao() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(criteria.getFolhasCienciaJulgApelacao(), Processo_.folhasCienciaJulgApelacao)
                    );
            }
            if (criteria.getEmbargoDeclaracao() != null) {
                specification = specification.and(buildSpecification(criteria.getEmbargoDeclaracao(), Processo_.embargoDeclaracao));
            }
            if (criteria.getFolhasRecursoEspecial() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getFolhasRecursoEspecial(), Processo_.folhasRecursoEspecial));
            }
            if (criteria.getFolhasCienciaJulgamentoRecursoEspecial() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(
                            criteria.getFolhasCienciaJulgamentoRecursoEspecial(),
                            Processo_.folhasCienciaJulgamentoRecursoEspecial
                        )
                    );
            }
            if (criteria.getEmbargoRecursoEspecial() != null) {
                specification =
                    specification.and(buildSpecification(criteria.getEmbargoRecursoEspecial(), Processo_.embargoRecursoEspecial));
            }
            if (criteria.getFolhasCiencia() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFolhasCiencia(), Processo_.folhasCiencia));
            }
            if (criteria.getAgravoRespRe() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAgravoRespRe(), Processo_.agravoRespRe));
            }
            if (criteria.getFolhasRespRe() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFolhasRespRe(), Processo_.folhasRespRe));
            }
            if (criteria.getFolhasCienciaJulgamentoAgravoRespRe() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(
                            criteria.getFolhasCienciaJulgamentoAgravoRespRe(),
                            Processo_.folhasCienciaJulgamentoAgravoRespRe
                        )
                    );
            }
            if (criteria.getEmbargoRespRe() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmbargoRespRe(), Processo_.embargoRespRe));
            }
            if (criteria.getAgravoInterno() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAgravoInterno(), Processo_.agravoInterno));
            }
            if (criteria.getFolhasAgravoInterno() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getFolhasAgravoInterno(), Processo_.folhasAgravoInterno));
            }
            if (criteria.getEmbargoRecursoAgravo() != null) {
                specification = specification.and(buildSpecification(criteria.getEmbargoRecursoAgravo(), Processo_.embargoRecursoAgravo));
            }
            if (criteria.getRecursoSTJ() != null) {
                specification = specification.and(buildSpecification(criteria.getRecursoSTJ(), Processo_.recursoSTJ));
            }
            if (criteria.getLinkRecursoSTJ() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLinkRecursoSTJ(), Processo_.linkRecursoSTJ));
            }
            if (criteria.getFolhasRecursoSTJ() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFolhasRecursoSTJ(), Processo_.folhasRecursoSTJ));
            }
            if (criteria.getRecursoSTF() != null) {
                specification = specification.and(buildSpecification(criteria.getRecursoSTF(), Processo_.recursoSTF));
            }
            if (criteria.getLinkRecursoSTF() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLinkRecursoSTF(), Processo_.linkRecursoSTF));
            }
            if (criteria.getFolhasRecursoSTF() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFolhasRecursoSTF(), Processo_.folhasRecursoSTF));
            }
            if (criteria.getFolhasMemorialMPF() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFolhasMemorialMPF(), Processo_.folhasMemorialMPF));
            }
            if (criteria.getExecusaoProvisoria() != null) {
                specification = specification.and(buildSpecification(criteria.getExecusaoProvisoria(), Processo_.execusaoProvisoria));
            }
            if (criteria.getNumeracaoExecusaoProvisoria() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(criteria.getNumeracaoExecusaoProvisoria(), Processo_.numeracaoExecusaoProvisoria)
                    );
            }
            if (criteria.getEnvolveEmpreendimento() != null) {
                specification = specification.and(buildSpecification(criteria.getEnvolveEmpreendimento(), Processo_.envolveEmpreendimento));
            }
            if (criteria.getEnvolveExploracaoIlegal() != null) {
                specification =
                    specification.and(buildSpecification(criteria.getEnvolveExploracaoIlegal(), Processo_.envolveExploracaoIlegal));
            }
            if (criteria.getEnvolveTerraQuilombola() != null) {
                specification =
                    specification.and(buildSpecification(criteria.getEnvolveTerraQuilombola(), Processo_.envolveTerraQuilombola));
            }
            if (criteria.getEnvolveTerraComunidadeTradicional() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getEnvolveTerraComunidadeTradicional(), Processo_.envolveTerraComunidadeTradicional)
                    );
            }
            if (criteria.getEnvolveTerraIndigena() != null) {
                specification = specification.and(buildSpecification(criteria.getEnvolveTerraIndigena(), Processo_.envolveTerraIndigena));
            }
            if (criteria.getTamanhoArea() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTamanhoArea(), Processo_.tamanhoArea));
            }
            if (criteria.getValorArea() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValorArea(), Processo_.valorArea));
            }
            if (criteria.getDadosGeograficosLitigioConflito() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getDadosGeograficosLitigioConflito(), Processo_.dadosGeograficosLitigioConflito)
                    );
            }
            if (criteria.getLatitude() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLatitude(), Processo_.latitude));
            }
            if (criteria.getLongitude() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLongitude(), Processo_.longitude));
            }
            if (criteria.getNumeroProcessoMPF() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNumeroProcessoMPF(), Processo_.numeroProcessoMPF));
            }
            if (criteria.getNumeroEmbargo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNumeroEmbargo(), Processo_.numeroEmbargo));
            }
            if (criteria.getNumeroRecursoEspecial() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getNumeroRecursoEspecial(), Processo_.numeroRecursoEspecial));
            }
            if (criteria.getEnvolveGrandeProjeto() != null) {
                specification = specification.and(buildSpecification(criteria.getEnvolveGrandeProjeto(), Processo_.envolveGrandeProjeto));
            }
            if (criteria.getEnvolveUnidadeConservacao() != null) {
                specification =
                    specification.and(buildSpecification(criteria.getEnvolveUnidadeConservacao(), Processo_.envolveUnidadeConservacao));
            }
            if (criteria.getConcessaoLiminarId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getConcessaoLiminarId(),
                            root -> root.join(Processo_.concessaoLiminars, JoinType.LEFT).get(ConcessaoLiminar_.id)
                        )
                    );
            }
            if (criteria.getConcessaoLiminarCassadaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getConcessaoLiminarCassadaId(),
                            root -> root.join(Processo_.concessaoLiminarCassadas, JoinType.LEFT).get(ConcessaoLiminarCassada_.id)
                        )
                    );
            }
            if (criteria.getEmbargoDeclaracaoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEmbargoDeclaracaoId(),
                            root -> root.join(Processo_.embargoDeclaracaos, JoinType.LEFT).get(EmbargoDeclaracao_.id)
                        )
                    );
            }
            if (criteria.getEmbargoDeclaracaoAgravoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEmbargoDeclaracaoAgravoId(),
                            root -> root.join(Processo_.embargoDeclaracaoAgravos, JoinType.LEFT).get(EmbargoDeclaracaoAgravo_.id)
                        )
                    );
            }
            if (criteria.getEmbargoRecursoEspecialId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEmbargoRecursoEspecialId(),
                            root -> root.join(Processo_.embargoRecursoEspecials, JoinType.LEFT).get(EmbargoRecursoEspecial_.id)
                        )
                    );
            }
            if (criteria.getEmbargoRespReId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEmbargoRespReId(),
                            root -> root.join(Processo_.embargoRespRes, JoinType.LEFT).get(EmbargoRespRe_.id)
                        )
                    );
            }
            if (criteria.getTipoDecisaoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getTipoDecisaoId(),
                            root -> root.join(Processo_.tipoDecisao, JoinType.LEFT).get(TipoDecisao_.id)
                        )
                    );
            }
            if (criteria.getTipoEmpreendimentoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getTipoEmpreendimentoId(),
                            root -> root.join(Processo_.tipoEmpreendimento, JoinType.LEFT).get(TipoEmpreendimento_.id)
                        )
                    );
            }
            if (criteria.getComarcaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getComarcaId(), root -> root.join(Processo_.comarcas, JoinType.LEFT).get(Comarca_.id))
                    );
            }
            if (criteria.getQuilomboId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getQuilomboId(),
                            root -> root.join(Processo_.quilombos, JoinType.LEFT).get(Quilombo_.id)
                        )
                    );
            }
            if (criteria.getMunicipioId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getMunicipioId(),
                            root -> root.join(Processo_.municipios, JoinType.LEFT).get(Municipio_.id)
                        )
                    );
            }
            if (criteria.getTerritorioId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getTerritorioId(),
                            root -> root.join(Processo_.territorios, JoinType.LEFT).get(Territorio_.id)
                        )
                    );
            }
            if (criteria.getAtividadeExploracaoIlegalId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getAtividadeExploracaoIlegalId(),
                            root -> root.join(Processo_.atividadeExploracaoIlegals, JoinType.LEFT).get(AtividadeExploracaoIlegal_.id)
                        )
                    );
            }
            if (criteria.getUnidadeConservacaoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getUnidadeConservacaoId(),
                            root -> root.join(Processo_.unidadeConservacaos, JoinType.LEFT).get(UnidadeConservacao_.id)
                        )
                    );
            }
            if (criteria.getEnvolvidosConflitoLitigioId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEnvolvidosConflitoLitigioId(),
                            root -> root.join(Processo_.envolvidosConflitoLitigios, JoinType.LEFT).get(EnvolvidosConflitoLitigio_.id)
                        )
                    );
            }
            if (criteria.getTerraIndigenaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getTerraIndigenaId(),
                            root -> root.join(Processo_.terraIndigenas, JoinType.LEFT).get(TerraIndigena_.id)
                        )
                    );
            }
            if (criteria.getProcessoConflitoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getProcessoConflitoId(),
                            root -> root.join(Processo_.processoConflitos, JoinType.LEFT).get(ProcessoConflito_.id)
                        )
                    );
            }
            if (criteria.getParteInteresssadaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getParteInteresssadaId(),
                            root -> root.join(Processo_.parteInteresssadas, JoinType.LEFT).get(ParteInteresssada_.id)
                        )
                    );
            }
            if (criteria.getRelatorId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getRelatorId(), root -> root.join(Processo_.relators, JoinType.LEFT).get(Relator_.id))
                    );
            }
            if (criteria.getProblemaJuridicoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getProblemaJuridicoId(),
                            root -> root.join(Processo_.problemaJuridicos, JoinType.LEFT).get(ProblemaJuridico_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
