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

import br.com.cidha.domain.AtividadeExploracaoIlegal;
import br.com.cidha.domain.*; // for static metamodels
import br.com.cidha.repository.AtividadeExploracaoIlegalRepository;
import br.com.cidha.service.dto.AtividadeExploracaoIlegalCriteria;

/**
 * Service for executing complex queries for {@link AtividadeExploracaoIlegal} entities in the database.
 * The main input is a {@link AtividadeExploracaoIlegalCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AtividadeExploracaoIlegal} or a {@link Page} of {@link AtividadeExploracaoIlegal} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AtividadeExploracaoIlegalQueryService extends QueryService<AtividadeExploracaoIlegal> {

    private final Logger log = LoggerFactory.getLogger(AtividadeExploracaoIlegalQueryService.class);

    private final AtividadeExploracaoIlegalRepository atividadeExploracaoIlegalRepository;

    public AtividadeExploracaoIlegalQueryService(AtividadeExploracaoIlegalRepository atividadeExploracaoIlegalRepository) {
        this.atividadeExploracaoIlegalRepository = atividadeExploracaoIlegalRepository;
    }

    /**
     * Return a {@link List} of {@link AtividadeExploracaoIlegal} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AtividadeExploracaoIlegal> findByCriteria(AtividadeExploracaoIlegalCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<AtividadeExploracaoIlegal> specification = createSpecification(criteria);
        return atividadeExploracaoIlegalRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link AtividadeExploracaoIlegal} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AtividadeExploracaoIlegal> findByCriteria(AtividadeExploracaoIlegalCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<AtividadeExploracaoIlegal> specification = createSpecification(criteria);
        return atividadeExploracaoIlegalRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AtividadeExploracaoIlegalCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<AtividadeExploracaoIlegal> specification = createSpecification(criteria);
        return atividadeExploracaoIlegalRepository.count(specification);
    }

    /**
     * Function to convert {@link AtividadeExploracaoIlegalCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<AtividadeExploracaoIlegal> createSpecification(AtividadeExploracaoIlegalCriteria criteria) {
        Specification<AtividadeExploracaoIlegal> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), AtividadeExploracaoIlegal_.id));
            }
            if (criteria.getDescricao() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescricao(), AtividadeExploracaoIlegal_.descricao));
            }
            if (criteria.getProcessoId() != null) {
                specification = specification.and(buildSpecification(criteria.getProcessoId(),
                    root -> root.join(AtividadeExploracaoIlegal_.processos, JoinType.LEFT).get(Processo_.id)));
            }
        }
        return specification;
    }
}
