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

import br.com.cidha.domain.EmbargoDeclaracaoAgravo;
import br.com.cidha.domain.*; // for static metamodels
import br.com.cidha.repository.EmbargoDeclaracaoAgravoRepository;
import br.com.cidha.service.dto.EmbargoDeclaracaoAgravoCriteria;

/**
 * Service for executing complex queries for {@link EmbargoDeclaracaoAgravo} entities in the database.
 * The main input is a {@link EmbargoDeclaracaoAgravoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EmbargoDeclaracaoAgravo} or a {@link Page} of {@link EmbargoDeclaracaoAgravo} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EmbargoDeclaracaoAgravoQueryService extends QueryService<EmbargoDeclaracaoAgravo> {

    private final Logger log = LoggerFactory.getLogger(EmbargoDeclaracaoAgravoQueryService.class);

    private final EmbargoDeclaracaoAgravoRepository embargoDeclaracaoAgravoRepository;

    public EmbargoDeclaracaoAgravoQueryService(EmbargoDeclaracaoAgravoRepository embargoDeclaracaoAgravoRepository) {
        this.embargoDeclaracaoAgravoRepository = embargoDeclaracaoAgravoRepository;
    }

    /**
     * Return a {@link List} of {@link EmbargoDeclaracaoAgravo} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EmbargoDeclaracaoAgravo> findByCriteria(EmbargoDeclaracaoAgravoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<EmbargoDeclaracaoAgravo> specification = createSpecification(criteria);
        return embargoDeclaracaoAgravoRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link EmbargoDeclaracaoAgravo} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EmbargoDeclaracaoAgravo> findByCriteria(EmbargoDeclaracaoAgravoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<EmbargoDeclaracaoAgravo> specification = createSpecification(criteria);
        return embargoDeclaracaoAgravoRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EmbargoDeclaracaoAgravoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<EmbargoDeclaracaoAgravo> specification = createSpecification(criteria);
        return embargoDeclaracaoAgravoRepository.count(specification);
    }

    /**
     * Function to convert {@link EmbargoDeclaracaoAgravoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<EmbargoDeclaracaoAgravo> createSpecification(EmbargoDeclaracaoAgravoCriteria criteria) {
        Specification<EmbargoDeclaracaoAgravo> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), EmbargoDeclaracaoAgravo_.id));
            }
            if (criteria.getDescricao() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescricao(), EmbargoDeclaracaoAgravo_.descricao));
            }
            if (criteria.getProcessoId() != null) {
                specification = specification.and(buildSpecification(criteria.getProcessoId(),
                    root -> root.join(EmbargoDeclaracaoAgravo_.processo, JoinType.LEFT).get(Processo_.id)));
            }
        }
        return specification;
    }
}
