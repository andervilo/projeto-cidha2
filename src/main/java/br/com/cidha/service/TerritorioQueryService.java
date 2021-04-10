package br.com.cidha.service;

import br.com.cidha.domain.*; // for static metamodels
import br.com.cidha.domain.Territorio;
import br.com.cidha.repository.TerritorioRepository;
import br.com.cidha.service.criteria.TerritorioCriteria;
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
 * Service for executing complex queries for {@link Territorio} entities in the database.
 * The main input is a {@link TerritorioCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Territorio} or a {@link Page} of {@link Territorio} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TerritorioQueryService extends QueryService<Territorio> {

    private final Logger log = LoggerFactory.getLogger(TerritorioQueryService.class);

    private final TerritorioRepository territorioRepository;

    public TerritorioQueryService(TerritorioRepository territorioRepository) {
        this.territorioRepository = territorioRepository;
    }

    /**
     * Return a {@link List} of {@link Territorio} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Territorio> findByCriteria(TerritorioCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Territorio> specification = createSpecification(criteria);
        return territorioRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Territorio} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Territorio> findByCriteria(TerritorioCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Territorio> specification = createSpecification(criteria);
        return territorioRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TerritorioCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Territorio> specification = createSpecification(criteria);
        return territorioRepository.count(specification);
    }

    /**
     * Function to convert {@link TerritorioCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Territorio> createSpecification(TerritorioCriteria criteria) {
        Specification<Territorio> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Territorio_.id));
            }
            if (criteria.getNome() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNome(), Territorio_.nome));
            }
            if (criteria.getProcessoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getProcessoId(),
                            root -> root.join(Territorio_.processos, JoinType.LEFT).get(Processo_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
