package br.com.cidha.service;

import br.com.cidha.domain.*; // for static metamodels
import br.com.cidha.domain.ParteInteresssada;
import br.com.cidha.repository.ParteInteresssadaRepository;
import br.com.cidha.service.criteria.ParteInteresssadaCriteria;
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
 * Service for executing complex queries for {@link ParteInteresssada} entities in the database.
 * The main input is a {@link ParteInteresssadaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ParteInteresssada} or a {@link Page} of {@link ParteInteresssada} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ParteInteresssadaQueryService extends QueryService<ParteInteresssada> {

    private final Logger log = LoggerFactory.getLogger(ParteInteresssadaQueryService.class);

    private final ParteInteresssadaRepository parteInteresssadaRepository;

    public ParteInteresssadaQueryService(ParteInteresssadaRepository parteInteresssadaRepository) {
        this.parteInteresssadaRepository = parteInteresssadaRepository;
    }

    /**
     * Return a {@link List} of {@link ParteInteresssada} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ParteInteresssada> findByCriteria(ParteInteresssadaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ParteInteresssada> specification = createSpecification(criteria);
        return parteInteresssadaRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link ParteInteresssada} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ParteInteresssada> findByCriteria(ParteInteresssadaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ParteInteresssada> specification = createSpecification(criteria);
        return parteInteresssadaRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ParteInteresssadaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ParteInteresssada> specification = createSpecification(criteria);
        return parteInteresssadaRepository.count(specification);
    }

    /**
     * Function to convert {@link ParteInteresssadaCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ParteInteresssada> createSpecification(ParteInteresssadaCriteria criteria) {
        Specification<ParteInteresssada> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ParteInteresssada_.id));
            }
            if (criteria.getNome() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNome(), ParteInteresssada_.nome));
            }
            if (criteria.getClassificacao() != null) {
                specification = specification.and(buildStringSpecification(criteria.getClassificacao(), ParteInteresssada_.classificacao));
            }
            if (criteria.getRepresentanteLegalId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getRepresentanteLegalId(),
                            root -> root.join(ParteInteresssada_.representanteLegals, JoinType.LEFT).get(RepresentanteLegal_.id)
                        )
                    );
            }
            if (criteria.getProcessoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getProcessoId(),
                            root -> root.join(ParteInteresssada_.processos, JoinType.LEFT).get(Processo_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
