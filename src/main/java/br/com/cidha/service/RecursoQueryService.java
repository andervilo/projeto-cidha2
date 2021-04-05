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

import br.com.cidha.domain.Recurso;
import br.com.cidha.domain.*; // for static metamodels
import br.com.cidha.repository.RecursoRepository;
import br.com.cidha.service.dto.RecursoCriteria;

/**
 * Service for executing complex queries for {@link Recurso} entities in the database.
 * The main input is a {@link RecursoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Recurso} or a {@link Page} of {@link Recurso} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class RecursoQueryService extends QueryService<Recurso> {

    private final Logger log = LoggerFactory.getLogger(RecursoQueryService.class);

    private final RecursoRepository recursoRepository;

    public RecursoQueryService(RecursoRepository recursoRepository) {
        this.recursoRepository = recursoRepository;
    }

    /**
     * Return a {@link List} of {@link Recurso} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Recurso> findByCriteria(RecursoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Recurso> specification = createSpecification(criteria);
        return recursoRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Recurso} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Recurso> findByCriteria(RecursoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Recurso> specification = createSpecification(criteria);
        return recursoRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(RecursoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Recurso> specification = createSpecification(criteria);
        return recursoRepository.count(specification);
    }

    /**
     * Function to convert {@link RecursoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Recurso> createSpecification(RecursoCriteria criteria) {
        Specification<Recurso> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Recurso_.id));
            }
            if (criteria.getTipoRecursoId() != null) {
                specification = specification.and(buildSpecification(criteria.getTipoRecursoId(),
                    root -> root.join(Recurso_.tipoRecurso, JoinType.LEFT).get(TipoRecurso_.id)));
            }
            if (criteria.getOpcaoRecursoId() != null) {
                specification = specification.and(buildSpecification(criteria.getOpcaoRecursoId(),
                    root -> root.join(Recurso_.opcaoRecurso, JoinType.LEFT).get(OpcaoRecurso_.id)));
            }
            if (criteria.getProcessoId() != null) {
                specification = specification.and(buildSpecification(criteria.getProcessoId(),
                    root -> root.join(Recurso_.processo, JoinType.LEFT).get(Processo_.id)));
            }
        }
        return specification;
    }
}
