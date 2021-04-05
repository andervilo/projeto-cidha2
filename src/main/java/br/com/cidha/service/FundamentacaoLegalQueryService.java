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

import br.com.cidha.domain.FundamentacaoLegal;
import br.com.cidha.domain.*; // for static metamodels
import br.com.cidha.repository.FundamentacaoLegalRepository;
import br.com.cidha.service.dto.FundamentacaoLegalCriteria;

/**
 * Service for executing complex queries for {@link FundamentacaoLegal} entities in the database.
 * The main input is a {@link FundamentacaoLegalCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link FundamentacaoLegal} or a {@link Page} of {@link FundamentacaoLegal} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class FundamentacaoLegalQueryService extends QueryService<FundamentacaoLegal> {

    private final Logger log = LoggerFactory.getLogger(FundamentacaoLegalQueryService.class);

    private final FundamentacaoLegalRepository fundamentacaoLegalRepository;

    public FundamentacaoLegalQueryService(FundamentacaoLegalRepository fundamentacaoLegalRepository) {
        this.fundamentacaoLegalRepository = fundamentacaoLegalRepository;
    }

    /**
     * Return a {@link List} of {@link FundamentacaoLegal} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<FundamentacaoLegal> findByCriteria(FundamentacaoLegalCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<FundamentacaoLegal> specification = createSpecification(criteria);
        return fundamentacaoLegalRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link FundamentacaoLegal} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<FundamentacaoLegal> findByCriteria(FundamentacaoLegalCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<FundamentacaoLegal> specification = createSpecification(criteria);
        return fundamentacaoLegalRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(FundamentacaoLegalCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<FundamentacaoLegal> specification = createSpecification(criteria);
        return fundamentacaoLegalRepository.count(specification);
    }

    /**
     * Function to convert {@link FundamentacaoLegalCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<FundamentacaoLegal> createSpecification(FundamentacaoLegalCriteria criteria) {
        Specification<FundamentacaoLegal> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), FundamentacaoLegal_.id));
            }
            if (criteria.getFolhasFundamentacaoLegal() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFolhasFundamentacaoLegal(), FundamentacaoLegal_.folhasFundamentacaoLegal));
            }
            if (criteria.getProblemaJuridicoId() != null) {
                specification = specification.and(buildSpecification(criteria.getProblemaJuridicoId(),
                    root -> root.join(FundamentacaoLegal_.problemaJuridicos, JoinType.LEFT).get(ProblemaJuridico_.id)));
            }
        }
        return specification;
    }
}
