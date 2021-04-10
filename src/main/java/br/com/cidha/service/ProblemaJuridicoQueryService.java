package br.com.cidha.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import br.com.cidha.domain.ProblemaJuridico;
import br.com.cidha.domain.*; // for static metamodels
import br.com.cidha.repository.ProblemaJuridicoRepository;
import br.com.cidha.service.dto.ProblemaJuridicoCriteria;

/**
 * Service for executing complex queries for {@link ProblemaJuridico} entities in the database.
 * The main input is a {@link ProblemaJuridicoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ProblemaJuridico} or a {@link Page} of {@link ProblemaJuridico} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProblemaJuridicoQueryService extends QueryService<ProblemaJuridico> {

    private final Logger log = LoggerFactory.getLogger(ProblemaJuridicoQueryService.class);

    private final ProblemaJuridicoRepository problemaJuridicoRepository;

    public ProblemaJuridicoQueryService(ProblemaJuridicoRepository problemaJuridicoRepository) {
        this.problemaJuridicoRepository = problemaJuridicoRepository;
    }

    /**
     * Return a {@link List} of {@link ProblemaJuridico} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ProblemaJuridico> findByCriteria(ProblemaJuridicoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ProblemaJuridico> specification = createSpecification(criteria);
        return problemaJuridicoRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link ProblemaJuridico} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProblemaJuridico> findByCriteria(ProblemaJuridicoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ProblemaJuridico> specification = createSpecification(criteria);
        return problemaJuridicoRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProblemaJuridicoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ProblemaJuridico> specification = createSpecification(criteria);
        return problemaJuridicoRepository.count(specification);
    }

    /**
     * Function to convert {@link ProblemaJuridicoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ProblemaJuridico> createSpecification(ProblemaJuridicoCriteria criteria) {
        Specification<ProblemaJuridico> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ProblemaJuridico_.id));
            }
            if (criteria.getFolhasProblemaJuridico() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFolhasProblemaJuridico(), ProblemaJuridico_.folhasProblemaJuridico));
            }
            if (criteria.getFundamentacaoDoutrinariaId() != null) {
                specification = specification.and(buildSpecification(criteria.getFundamentacaoDoutrinariaId(),
                    root -> root.join(ProblemaJuridico_.fundamentacaoDoutrinarias, JoinType.LEFT).get(FundamentacaoDoutrinaria_.id)));
            }
            if (criteria.getJurisprudenciaId() != null) {
                specification = specification.and(buildSpecification(criteria.getJurisprudenciaId(),
                    root -> root.join(ProblemaJuridico_.jurisprudencias, JoinType.LEFT).get(Jurisprudencia_.id)));
            }
            if (criteria.getFundamentacaoLegalId() != null) {
                specification = specification.and(buildSpecification(criteria.getFundamentacaoLegalId(),
                    root -> root.join(ProblemaJuridico_.fundamentacaoLegals, JoinType.LEFT).get(FundamentacaoLegal_.id)));
            }
            if (criteria.getInstrumentoInternacionalId() != null) {
                specification = specification.and(buildSpecification(criteria.getInstrumentoInternacionalId(),
                    root -> root.join(ProblemaJuridico_.instrumentoInternacionals, JoinType.LEFT).get(InstrumentoInternacional_.id)));
            }
            if (criteria.getProcessoId() != null) {
                specification = specification.and(buildSpecification(criteria.getProcessoId(),
                    root -> root.join(ProblemaJuridico_.processos, JoinType.LEFT).get(Processo_.id)));
            }
        }
        return specification;
    }
}
