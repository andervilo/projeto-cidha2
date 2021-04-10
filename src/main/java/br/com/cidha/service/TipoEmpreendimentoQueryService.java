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

import br.com.cidha.domain.TipoEmpreendimento;
import br.com.cidha.domain.*; // for static metamodels
import br.com.cidha.repository.TipoEmpreendimentoRepository;
import br.com.cidha.service.dto.TipoEmpreendimentoCriteria;

/**
 * Service for executing complex queries for {@link TipoEmpreendimento} entities in the database.
 * The main input is a {@link TipoEmpreendimentoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TipoEmpreendimento} or a {@link Page} of {@link TipoEmpreendimento} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TipoEmpreendimentoQueryService extends QueryService<TipoEmpreendimento> {

    private final Logger log = LoggerFactory.getLogger(TipoEmpreendimentoQueryService.class);

    private final TipoEmpreendimentoRepository tipoEmpreendimentoRepository;

    public TipoEmpreendimentoQueryService(TipoEmpreendimentoRepository tipoEmpreendimentoRepository) {
        this.tipoEmpreendimentoRepository = tipoEmpreendimentoRepository;
    }

    /**
     * Return a {@link List} of {@link TipoEmpreendimento} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TipoEmpreendimento> findByCriteria(TipoEmpreendimentoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<TipoEmpreendimento> specification = createSpecification(criteria);
        return tipoEmpreendimentoRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link TipoEmpreendimento} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TipoEmpreendimento> findByCriteria(TipoEmpreendimentoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<TipoEmpreendimento> specification = createSpecification(criteria);
        return tipoEmpreendimentoRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TipoEmpreendimentoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<TipoEmpreendimento> specification = createSpecification(criteria);
        return tipoEmpreendimentoRepository.count(specification);
    }

    /**
     * Function to convert {@link TipoEmpreendimentoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<TipoEmpreendimento> createSpecification(TipoEmpreendimentoCriteria criteria) {
        Specification<TipoEmpreendimento> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), TipoEmpreendimento_.id));
            }
            if (criteria.getDescricao() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescricao(), TipoEmpreendimento_.descricao));
            }
        }
        return specification;
    }
}
