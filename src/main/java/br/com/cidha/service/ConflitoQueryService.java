package br.com.cidha.service;

import br.com.cidha.domain.*; // for static metamodels
import br.com.cidha.domain.Conflito;
import br.com.cidha.repository.ConflitoRepository;
import br.com.cidha.service.criteria.ConflitoCriteria;
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
 * Service for executing complex queries for {@link Conflito} entities in the database.
 * The main input is a {@link ConflitoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Conflito} or a {@link Page} of {@link Conflito} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ConflitoQueryService extends QueryService<Conflito> {

    private final Logger log = LoggerFactory.getLogger(ConflitoQueryService.class);

    private final ConflitoRepository conflitoRepository;

    public ConflitoQueryService(ConflitoRepository conflitoRepository) {
        this.conflitoRepository = conflitoRepository;
    }

    /**
     * Return a {@link List} of {@link Conflito} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Conflito> findByCriteria(ConflitoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Conflito> specification = createSpecification(criteria);
        return conflitoRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Conflito} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Conflito> findByCriteria(ConflitoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Conflito> specification = createSpecification(criteria);
        return conflitoRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ConflitoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Conflito> specification = createSpecification(criteria);
        return conflitoRepository.count(specification);
    }

    /**
     * Function to convert {@link ConflitoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Conflito> createSpecification(ConflitoCriteria criteria) {
        Specification<Conflito> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Conflito_.id));
            }
            if (criteria.getProcessoConflitoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getProcessoConflitoId(),
                            root -> root.join(Conflito_.processoConflito, JoinType.LEFT).get(ProcessoConflito_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
