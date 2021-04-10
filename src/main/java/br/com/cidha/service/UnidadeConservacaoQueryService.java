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

import br.com.cidha.domain.UnidadeConservacao;
import br.com.cidha.domain.*; // for static metamodels
import br.com.cidha.repository.UnidadeConservacaoRepository;
import br.com.cidha.service.dto.UnidadeConservacaoCriteria;

/**
 * Service for executing complex queries for {@link UnidadeConservacao} entities in the database.
 * The main input is a {@link UnidadeConservacaoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link UnidadeConservacao} or a {@link Page} of {@link UnidadeConservacao} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class UnidadeConservacaoQueryService extends QueryService<UnidadeConservacao> {

    private final Logger log = LoggerFactory.getLogger(UnidadeConservacaoQueryService.class);

    private final UnidadeConservacaoRepository unidadeConservacaoRepository;

    public UnidadeConservacaoQueryService(UnidadeConservacaoRepository unidadeConservacaoRepository) {
        this.unidadeConservacaoRepository = unidadeConservacaoRepository;
    }

    /**
     * Return a {@link List} of {@link UnidadeConservacao} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<UnidadeConservacao> findByCriteria(UnidadeConservacaoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<UnidadeConservacao> specification = createSpecification(criteria);
        return unidadeConservacaoRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link UnidadeConservacao} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<UnidadeConservacao> findByCriteria(UnidadeConservacaoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<UnidadeConservacao> specification = createSpecification(criteria);
        return unidadeConservacaoRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(UnidadeConservacaoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<UnidadeConservacao> specification = createSpecification(criteria);
        return unidadeConservacaoRepository.count(specification);
    }

    /**
     * Function to convert {@link UnidadeConservacaoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<UnidadeConservacao> createSpecification(UnidadeConservacaoCriteria criteria) {
        Specification<UnidadeConservacao> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), UnidadeConservacao_.id));
            }
            if (criteria.getDescricao() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescricao(), UnidadeConservacao_.descricao));
            }
            if (criteria.getProcessoId() != null) {
                specification = specification.and(buildSpecification(criteria.getProcessoId(),
                    root -> root.join(UnidadeConservacao_.processos, JoinType.LEFT).get(Processo_.id)));
            }
        }
        return specification;
    }
}
