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

import br.com.cidha.domain.EtniaIndigena;
import br.com.cidha.domain.*; // for static metamodels
import br.com.cidha.repository.EtniaIndigenaRepository;
import br.com.cidha.service.dto.EtniaIndigenaCriteria;

/**
 * Service for executing complex queries for {@link EtniaIndigena} entities in the database.
 * The main input is a {@link EtniaIndigenaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EtniaIndigena} or a {@link Page} of {@link EtniaIndigena} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EtniaIndigenaQueryService extends QueryService<EtniaIndigena> {

    private final Logger log = LoggerFactory.getLogger(EtniaIndigenaQueryService.class);

    private final EtniaIndigenaRepository etniaIndigenaRepository;

    public EtniaIndigenaQueryService(EtniaIndigenaRepository etniaIndigenaRepository) {
        this.etniaIndigenaRepository = etniaIndigenaRepository;
    }

    /**
     * Return a {@link List} of {@link EtniaIndigena} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EtniaIndigena> findByCriteria(EtniaIndigenaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<EtniaIndigena> specification = createSpecification(criteria);
        return etniaIndigenaRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link EtniaIndigena} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EtniaIndigena> findByCriteria(EtniaIndigenaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<EtniaIndigena> specification = createSpecification(criteria);
        return etniaIndigenaRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EtniaIndigenaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<EtniaIndigena> specification = createSpecification(criteria);
        return etniaIndigenaRepository.count(specification);
    }

    /**
     * Function to convert {@link EtniaIndigenaCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<EtniaIndigena> createSpecification(EtniaIndigenaCriteria criteria) {
        Specification<EtniaIndigena> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), EtniaIndigena_.id));
            }
            if (criteria.getNome() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNome(), EtniaIndigena_.nome));
            }
            if (criteria.getTerraIndigenaId() != null) {
                specification = specification.and(buildSpecification(criteria.getTerraIndigenaId(),
                    root -> root.join(EtniaIndigena_.terraIndigenas, JoinType.LEFT).get(TerraIndigena_.id)));
            }
        }
        return specification;
    }
}
