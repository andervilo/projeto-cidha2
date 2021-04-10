package br.com.cidha.service;

import br.com.cidha.domain.*; // for static metamodels
import br.com.cidha.domain.RepresentanteLegal;
import br.com.cidha.repository.RepresentanteLegalRepository;
import br.com.cidha.service.criteria.RepresentanteLegalCriteria;
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
 * Service for executing complex queries for {@link RepresentanteLegal} entities in the database.
 * The main input is a {@link RepresentanteLegalCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link RepresentanteLegal} or a {@link Page} of {@link RepresentanteLegal} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class RepresentanteLegalQueryService extends QueryService<RepresentanteLegal> {

    private final Logger log = LoggerFactory.getLogger(RepresentanteLegalQueryService.class);

    private final RepresentanteLegalRepository representanteLegalRepository;

    public RepresentanteLegalQueryService(RepresentanteLegalRepository representanteLegalRepository) {
        this.representanteLegalRepository = representanteLegalRepository;
    }

    /**
     * Return a {@link List} of {@link RepresentanteLegal} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<RepresentanteLegal> findByCriteria(RepresentanteLegalCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<RepresentanteLegal> specification = createSpecification(criteria);
        return representanteLegalRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link RepresentanteLegal} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<RepresentanteLegal> findByCriteria(RepresentanteLegalCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<RepresentanteLegal> specification = createSpecification(criteria);
        return representanteLegalRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(RepresentanteLegalCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<RepresentanteLegal> specification = createSpecification(criteria);
        return representanteLegalRepository.count(specification);
    }

    /**
     * Function to convert {@link RepresentanteLegalCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<RepresentanteLegal> createSpecification(RepresentanteLegalCriteria criteria) {
        Specification<RepresentanteLegal> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), RepresentanteLegal_.id));
            }
            if (criteria.getNome() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNome(), RepresentanteLegal_.nome));
            }
            if (criteria.getTipoRepresentanteId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getTipoRepresentanteId(),
                            root -> root.join(RepresentanteLegal_.tipoRepresentante, JoinType.LEFT).get(TipoRepresentante_.id)
                        )
                    );
            }
            if (criteria.getProcessoConflitoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getProcessoConflitoId(),
                            root -> root.join(RepresentanteLegal_.processoConflitos, JoinType.LEFT).get(ParteInteresssada_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
