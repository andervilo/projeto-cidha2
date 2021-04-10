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

import br.com.cidha.domain.Comarca;
import br.com.cidha.domain.*; // for static metamodels
import br.com.cidha.repository.ComarcaRepository;
import br.com.cidha.service.dto.ComarcaCriteria;

/**
 * Service for executing complex queries for {@link Comarca} entities in the database.
 * The main input is a {@link ComarcaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Comarca} or a {@link Page} of {@link Comarca} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ComarcaQueryService extends QueryService<Comarca> {

    private final Logger log = LoggerFactory.getLogger(ComarcaQueryService.class);

    private final ComarcaRepository comarcaRepository;

    public ComarcaQueryService(ComarcaRepository comarcaRepository) {
        this.comarcaRepository = comarcaRepository;
    }

    /**
     * Return a {@link List} of {@link Comarca} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Comarca> findByCriteria(ComarcaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Comarca> specification = createSpecification(criteria);
        return comarcaRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Comarca} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Comarca> findByCriteria(ComarcaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Comarca> specification = createSpecification(criteria);
        return comarcaRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ComarcaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Comarca> specification = createSpecification(criteria);
        return comarcaRepository.count(specification);
    }

    /**
     * Function to convert {@link ComarcaCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Comarca> createSpecification(ComarcaCriteria criteria) {
        Specification<Comarca> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Comarca_.id));
            }
            if (criteria.getNome() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNome(), Comarca_.nome));
            }
            if (criteria.getCodigoCnj() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCodigoCnj(), Comarca_.codigoCnj));
            }
            if (criteria.getProcessoId() != null) {
                specification = specification.and(buildSpecification(criteria.getProcessoId(),
                    root -> root.join(Comarca_.processos, JoinType.LEFT).get(Processo_.id)));
            }
        }
        return specification;
    }
}
