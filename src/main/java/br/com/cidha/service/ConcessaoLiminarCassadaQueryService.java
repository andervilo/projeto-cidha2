package br.com.cidha.service;

import br.com.cidha.domain.*; // for static metamodels
import br.com.cidha.domain.ConcessaoLiminarCassada;
import br.com.cidha.repository.ConcessaoLiminarCassadaRepository;
import br.com.cidha.service.criteria.ConcessaoLiminarCassadaCriteria;
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
 * Service for executing complex queries for {@link ConcessaoLiminarCassada} entities in the database.
 * The main input is a {@link ConcessaoLiminarCassadaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ConcessaoLiminarCassada} or a {@link Page} of {@link ConcessaoLiminarCassada} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ConcessaoLiminarCassadaQueryService extends QueryService<ConcessaoLiminarCassada> {

    private final Logger log = LoggerFactory.getLogger(ConcessaoLiminarCassadaQueryService.class);

    private final ConcessaoLiminarCassadaRepository concessaoLiminarCassadaRepository;

    public ConcessaoLiminarCassadaQueryService(ConcessaoLiminarCassadaRepository concessaoLiminarCassadaRepository) {
        this.concessaoLiminarCassadaRepository = concessaoLiminarCassadaRepository;
    }

    /**
     * Return a {@link List} of {@link ConcessaoLiminarCassada} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ConcessaoLiminarCassada> findByCriteria(ConcessaoLiminarCassadaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ConcessaoLiminarCassada> specification = createSpecification(criteria);
        return concessaoLiminarCassadaRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link ConcessaoLiminarCassada} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ConcessaoLiminarCassada> findByCriteria(ConcessaoLiminarCassadaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ConcessaoLiminarCassada> specification = createSpecification(criteria);
        return concessaoLiminarCassadaRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ConcessaoLiminarCassadaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ConcessaoLiminarCassada> specification = createSpecification(criteria);
        return concessaoLiminarCassadaRepository.count(specification);
    }

    /**
     * Function to convert {@link ConcessaoLiminarCassadaCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ConcessaoLiminarCassada> createSpecification(ConcessaoLiminarCassadaCriteria criteria) {
        Specification<ConcessaoLiminarCassada> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ConcessaoLiminarCassada_.id));
            }
            if (criteria.getDescricao() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescricao(), ConcessaoLiminarCassada_.descricao));
            }
            if (criteria.getProcessoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getProcessoId(),
                            root -> root.join(ConcessaoLiminarCassada_.processo, JoinType.LEFT).get(Processo_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
