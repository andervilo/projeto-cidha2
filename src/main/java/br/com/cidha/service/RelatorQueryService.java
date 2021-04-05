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

import br.com.cidha.domain.Relator;
import br.com.cidha.domain.*; // for static metamodels
import br.com.cidha.repository.RelatorRepository;
import br.com.cidha.service.dto.RelatorCriteria;

/**
 * Service for executing complex queries for {@link Relator} entities in the database.
 * The main input is a {@link RelatorCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Relator} or a {@link Page} of {@link Relator} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class RelatorQueryService extends QueryService<Relator> {

    private final Logger log = LoggerFactory.getLogger(RelatorQueryService.class);

    private final RelatorRepository relatorRepository;

    public RelatorQueryService(RelatorRepository relatorRepository) {
        this.relatorRepository = relatorRepository;
    }

    /**
     * Return a {@link List} of {@link Relator} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Relator> findByCriteria(RelatorCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Relator> specification = createSpecification(criteria);
        return relatorRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Relator} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Relator> findByCriteria(RelatorCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Relator> specification = createSpecification(criteria);
        return relatorRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(RelatorCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Relator> specification = createSpecification(criteria);
        return relatorRepository.count(specification);
    }

    /**
     * Function to convert {@link RelatorCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Relator> createSpecification(RelatorCriteria criteria) {
        Specification<Relator> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Relator_.id));
            }
            if (criteria.getNome() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNome(), Relator_.nome));
            }
            if (criteria.getProcessoId() != null) {
                specification = specification.and(buildSpecification(criteria.getProcessoId(),
                    root -> root.join(Relator_.processos, JoinType.LEFT).get(Processo_.id)));
            }
        }
        return specification;
    }
}
