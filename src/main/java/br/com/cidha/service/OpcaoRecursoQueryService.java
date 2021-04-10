package br.com.cidha.service;

import br.com.cidha.domain.*; // for static metamodels
import br.com.cidha.domain.OpcaoRecurso;
import br.com.cidha.repository.OpcaoRecursoRepository;
import br.com.cidha.service.criteria.OpcaoRecursoCriteria;
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
 * Service for executing complex queries for {@link OpcaoRecurso} entities in the database.
 * The main input is a {@link OpcaoRecursoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link OpcaoRecurso} or a {@link Page} of {@link OpcaoRecurso} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class OpcaoRecursoQueryService extends QueryService<OpcaoRecurso> {

    private final Logger log = LoggerFactory.getLogger(OpcaoRecursoQueryService.class);

    private final OpcaoRecursoRepository opcaoRecursoRepository;

    public OpcaoRecursoQueryService(OpcaoRecursoRepository opcaoRecursoRepository) {
        this.opcaoRecursoRepository = opcaoRecursoRepository;
    }

    /**
     * Return a {@link List} of {@link OpcaoRecurso} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<OpcaoRecurso> findByCriteria(OpcaoRecursoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<OpcaoRecurso> specification = createSpecification(criteria);
        return opcaoRecursoRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link OpcaoRecurso} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<OpcaoRecurso> findByCriteria(OpcaoRecursoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<OpcaoRecurso> specification = createSpecification(criteria);
        return opcaoRecursoRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(OpcaoRecursoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<OpcaoRecurso> specification = createSpecification(criteria);
        return opcaoRecursoRepository.count(specification);
    }

    /**
     * Function to convert {@link OpcaoRecursoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<OpcaoRecurso> createSpecification(OpcaoRecursoCriteria criteria) {
        Specification<OpcaoRecurso> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), OpcaoRecurso_.id));
            }
            if (criteria.getDescricao() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescricao(), OpcaoRecurso_.descricao));
            }
        }
        return specification;
    }
}
