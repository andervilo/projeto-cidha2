package br.com.cidha.service;

import br.com.cidha.domain.*; // for static metamodels
import br.com.cidha.domain.ConcessaoLiminar;
import br.com.cidha.repository.ConcessaoLiminarRepository;
import br.com.cidha.service.criteria.ConcessaoLiminarCriteria;
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
 * Service for executing complex queries for {@link ConcessaoLiminar} entities in the database.
 * The main input is a {@link ConcessaoLiminarCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ConcessaoLiminar} or a {@link Page} of {@link ConcessaoLiminar} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ConcessaoLiminarQueryService extends QueryService<ConcessaoLiminar> {

    private final Logger log = LoggerFactory.getLogger(ConcessaoLiminarQueryService.class);

    private final ConcessaoLiminarRepository concessaoLiminarRepository;

    public ConcessaoLiminarQueryService(ConcessaoLiminarRepository concessaoLiminarRepository) {
        this.concessaoLiminarRepository = concessaoLiminarRepository;
    }

    /**
     * Return a {@link List} of {@link ConcessaoLiminar} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ConcessaoLiminar> findByCriteria(ConcessaoLiminarCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ConcessaoLiminar> specification = createSpecification(criteria);
        return concessaoLiminarRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link ConcessaoLiminar} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ConcessaoLiminar> findByCriteria(ConcessaoLiminarCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ConcessaoLiminar> specification = createSpecification(criteria);
        return concessaoLiminarRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ConcessaoLiminarCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ConcessaoLiminar> specification = createSpecification(criteria);
        return concessaoLiminarRepository.count(specification);
    }

    /**
     * Function to convert {@link ConcessaoLiminarCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ConcessaoLiminar> createSpecification(ConcessaoLiminarCriteria criteria) {
        Specification<ConcessaoLiminar> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ConcessaoLiminar_.id));
            }
            if (criteria.getDescricao() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescricao(), ConcessaoLiminar_.descricao));
            }
            if (criteria.getProcessoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getProcessoId(),
                            root -> root.join(ConcessaoLiminar_.processo, JoinType.LEFT).get(Processo_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
