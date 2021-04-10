package br.com.cidha.service;

import br.com.cidha.domain.*; // for static metamodels
import br.com.cidha.domain.TerraIndigena;
import br.com.cidha.repository.TerraIndigenaRepository;
import br.com.cidha.service.criteria.TerraIndigenaCriteria;
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
 * Service for executing complex queries for {@link TerraIndigena} entities in the database.
 * The main input is a {@link TerraIndigenaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TerraIndigena} or a {@link Page} of {@link TerraIndigena} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TerraIndigenaQueryService extends QueryService<TerraIndigena> {

    private final Logger log = LoggerFactory.getLogger(TerraIndigenaQueryService.class);

    private final TerraIndigenaRepository terraIndigenaRepository;

    public TerraIndigenaQueryService(TerraIndigenaRepository terraIndigenaRepository) {
        this.terraIndigenaRepository = terraIndigenaRepository;
    }

    /**
     * Return a {@link List} of {@link TerraIndigena} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TerraIndigena> findByCriteria(TerraIndigenaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<TerraIndigena> specification = createSpecification(criteria);
        return terraIndigenaRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link TerraIndigena} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TerraIndigena> findByCriteria(TerraIndigenaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<TerraIndigena> specification = createSpecification(criteria);
        return terraIndigenaRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TerraIndigenaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<TerraIndigena> specification = createSpecification(criteria);
        return terraIndigenaRepository.count(specification);
    }

    /**
     * Function to convert {@link TerraIndigenaCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<TerraIndigena> createSpecification(TerraIndigenaCriteria criteria) {
        Specification<TerraIndigena> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), TerraIndigena_.id));
            }
            if (criteria.getEtniaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEtniaId(),
                            root -> root.join(TerraIndigena_.etnias, JoinType.LEFT).get(EtniaIndigena_.id)
                        )
                    );
            }
            if (criteria.getProcessoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getProcessoId(),
                            root -> root.join(TerraIndigena_.processos, JoinType.LEFT).get(Processo_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
