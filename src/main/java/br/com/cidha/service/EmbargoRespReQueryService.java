package br.com.cidha.service;

import br.com.cidha.domain.*; // for static metamodels
import br.com.cidha.domain.EmbargoRespRe;
import br.com.cidha.repository.EmbargoRespReRepository;
import br.com.cidha.service.criteria.EmbargoRespReCriteria;
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
 * Service for executing complex queries for {@link EmbargoRespRe} entities in the database.
 * The main input is a {@link EmbargoRespReCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EmbargoRespRe} or a {@link Page} of {@link EmbargoRespRe} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EmbargoRespReQueryService extends QueryService<EmbargoRespRe> {

    private final Logger log = LoggerFactory.getLogger(EmbargoRespReQueryService.class);

    private final EmbargoRespReRepository embargoRespReRepository;

    public EmbargoRespReQueryService(EmbargoRespReRepository embargoRespReRepository) {
        this.embargoRespReRepository = embargoRespReRepository;
    }

    /**
     * Return a {@link List} of {@link EmbargoRespRe} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EmbargoRespRe> findByCriteria(EmbargoRespReCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<EmbargoRespRe> specification = createSpecification(criteria);
        return embargoRespReRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link EmbargoRespRe} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EmbargoRespRe> findByCriteria(EmbargoRespReCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<EmbargoRespRe> specification = createSpecification(criteria);
        return embargoRespReRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EmbargoRespReCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<EmbargoRespRe> specification = createSpecification(criteria);
        return embargoRespReRepository.count(specification);
    }

    /**
     * Function to convert {@link EmbargoRespReCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<EmbargoRespRe> createSpecification(EmbargoRespReCriteria criteria) {
        Specification<EmbargoRespRe> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), EmbargoRespRe_.id));
            }
            if (criteria.getDescricao() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescricao(), EmbargoRespRe_.descricao));
            }
            if (criteria.getProcessoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getProcessoId(),
                            root -> root.join(EmbargoRespRe_.processo, JoinType.LEFT).get(Processo_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
