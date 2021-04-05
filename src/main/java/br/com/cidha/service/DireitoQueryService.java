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

import br.com.cidha.domain.Direito;
import br.com.cidha.domain.*; // for static metamodels
import br.com.cidha.repository.DireitoRepository;
import br.com.cidha.service.dto.DireitoCriteria;

/**
 * Service for executing complex queries for {@link Direito} entities in the database.
 * The main input is a {@link DireitoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Direito} or a {@link Page} of {@link Direito} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DireitoQueryService extends QueryService<Direito> {

    private final Logger log = LoggerFactory.getLogger(DireitoQueryService.class);

    private final DireitoRepository direitoRepository;

    public DireitoQueryService(DireitoRepository direitoRepository) {
        this.direitoRepository = direitoRepository;
    }

    /**
     * Return a {@link List} of {@link Direito} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Direito> findByCriteria(DireitoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Direito> specification = createSpecification(criteria);
        return direitoRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Direito} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Direito> findByCriteria(DireitoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Direito> specification = createSpecification(criteria);
        return direitoRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DireitoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Direito> specification = createSpecification(criteria);
        return direitoRepository.count(specification);
    }

    /**
     * Function to convert {@link DireitoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Direito> createSpecification(DireitoCriteria criteria) {
        Specification<Direito> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Direito_.id));
            }
            if (criteria.getProcessoConflitoId() != null) {
                specification = specification.and(buildSpecification(criteria.getProcessoConflitoId(),
                    root -> root.join(Direito_.processoConflitos, JoinType.LEFT).get(ProcessoConflito_.id)));
            }
        }
        return specification;
    }
}
