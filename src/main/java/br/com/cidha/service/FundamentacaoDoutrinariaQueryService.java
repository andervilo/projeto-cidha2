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

import br.com.cidha.domain.FundamentacaoDoutrinaria;
import br.com.cidha.domain.*; // for static metamodels
import br.com.cidha.repository.FundamentacaoDoutrinariaRepository;
import br.com.cidha.service.dto.FundamentacaoDoutrinariaCriteria;

/**
 * Service for executing complex queries for {@link FundamentacaoDoutrinaria} entities in the database.
 * The main input is a {@link FundamentacaoDoutrinariaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link FundamentacaoDoutrinaria} or a {@link Page} of {@link FundamentacaoDoutrinaria} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class FundamentacaoDoutrinariaQueryService extends QueryService<FundamentacaoDoutrinaria> {

    private final Logger log = LoggerFactory.getLogger(FundamentacaoDoutrinariaQueryService.class);

    private final FundamentacaoDoutrinariaRepository fundamentacaoDoutrinariaRepository;

    public FundamentacaoDoutrinariaQueryService(FundamentacaoDoutrinariaRepository fundamentacaoDoutrinariaRepository) {
        this.fundamentacaoDoutrinariaRepository = fundamentacaoDoutrinariaRepository;
    }

    /**
     * Return a {@link List} of {@link FundamentacaoDoutrinaria} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<FundamentacaoDoutrinaria> findByCriteria(FundamentacaoDoutrinariaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<FundamentacaoDoutrinaria> specification = createSpecification(criteria);
        return fundamentacaoDoutrinariaRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link FundamentacaoDoutrinaria} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<FundamentacaoDoutrinaria> findByCriteria(FundamentacaoDoutrinariaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<FundamentacaoDoutrinaria> specification = createSpecification(criteria);
        return fundamentacaoDoutrinariaRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(FundamentacaoDoutrinariaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<FundamentacaoDoutrinaria> specification = createSpecification(criteria);
        return fundamentacaoDoutrinariaRepository.count(specification);
    }

    /**
     * Function to convert {@link FundamentacaoDoutrinariaCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<FundamentacaoDoutrinaria> createSpecification(FundamentacaoDoutrinariaCriteria criteria) {
        Specification<FundamentacaoDoutrinaria> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), FundamentacaoDoutrinaria_.id));
            }
            if (criteria.getFolhasFundamentacaoDoutrinaria() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFolhasFundamentacaoDoutrinaria(), FundamentacaoDoutrinaria_.folhasFundamentacaoDoutrinaria));
            }
            if (criteria.getProblemaJuridicoId() != null) {
                specification = specification.and(buildSpecification(criteria.getProblemaJuridicoId(),
                    root -> root.join(FundamentacaoDoutrinaria_.problemaJuridicos, JoinType.LEFT).get(ProblemaJuridico_.id)));
            }
        }
        return specification;
    }
}
