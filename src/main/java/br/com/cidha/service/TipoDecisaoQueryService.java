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

import br.com.cidha.domain.TipoDecisao;
import br.com.cidha.domain.*; // for static metamodels
import br.com.cidha.repository.TipoDecisaoRepository;
import br.com.cidha.service.dto.TipoDecisaoCriteria;

/**
 * Service for executing complex queries for {@link TipoDecisao} entities in the database.
 * The main input is a {@link TipoDecisaoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TipoDecisao} or a {@link Page} of {@link TipoDecisao} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TipoDecisaoQueryService extends QueryService<TipoDecisao> {

    private final Logger log = LoggerFactory.getLogger(TipoDecisaoQueryService.class);

    private final TipoDecisaoRepository tipoDecisaoRepository;

    public TipoDecisaoQueryService(TipoDecisaoRepository tipoDecisaoRepository) {
        this.tipoDecisaoRepository = tipoDecisaoRepository;
    }

    /**
     * Return a {@link List} of {@link TipoDecisao} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TipoDecisao> findByCriteria(TipoDecisaoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<TipoDecisao> specification = createSpecification(criteria);
        return tipoDecisaoRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link TipoDecisao} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TipoDecisao> findByCriteria(TipoDecisaoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<TipoDecisao> specification = createSpecification(criteria);
        return tipoDecisaoRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TipoDecisaoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<TipoDecisao> specification = createSpecification(criteria);
        return tipoDecisaoRepository.count(specification);
    }

    /**
     * Function to convert {@link TipoDecisaoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<TipoDecisao> createSpecification(TipoDecisaoCriteria criteria) {
        Specification<TipoDecisao> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), TipoDecisao_.id));
            }
            if (criteria.getDescricao() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescricao(), TipoDecisao_.descricao));
            }
        }
        return specification;
    }
}
