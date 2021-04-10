package br.com.cidha.service;

import br.com.cidha.domain.*; // for static metamodels
import br.com.cidha.domain.EnvolvidosConflitoLitigio;
import br.com.cidha.repository.EnvolvidosConflitoLitigioRepository;
import br.com.cidha.service.criteria.EnvolvidosConflitoLitigioCriteria;
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
 * Service for executing complex queries for {@link EnvolvidosConflitoLitigio} entities in the database.
 * The main input is a {@link EnvolvidosConflitoLitigioCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EnvolvidosConflitoLitigio} or a {@link Page} of {@link EnvolvidosConflitoLitigio} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EnvolvidosConflitoLitigioQueryService extends QueryService<EnvolvidosConflitoLitigio> {

    private final Logger log = LoggerFactory.getLogger(EnvolvidosConflitoLitigioQueryService.class);

    private final EnvolvidosConflitoLitigioRepository envolvidosConflitoLitigioRepository;

    public EnvolvidosConflitoLitigioQueryService(EnvolvidosConflitoLitigioRepository envolvidosConflitoLitigioRepository) {
        this.envolvidosConflitoLitigioRepository = envolvidosConflitoLitigioRepository;
    }

    /**
     * Return a {@link List} of {@link EnvolvidosConflitoLitigio} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EnvolvidosConflitoLitigio> findByCriteria(EnvolvidosConflitoLitigioCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<EnvolvidosConflitoLitigio> specification = createSpecification(criteria);
        return envolvidosConflitoLitigioRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link EnvolvidosConflitoLitigio} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EnvolvidosConflitoLitigio> findByCriteria(EnvolvidosConflitoLitigioCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<EnvolvidosConflitoLitigio> specification = createSpecification(criteria);
        return envolvidosConflitoLitigioRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EnvolvidosConflitoLitigioCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<EnvolvidosConflitoLitigio> specification = createSpecification(criteria);
        return envolvidosConflitoLitigioRepository.count(specification);
    }

    /**
     * Function to convert {@link EnvolvidosConflitoLitigioCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<EnvolvidosConflitoLitigio> createSpecification(EnvolvidosConflitoLitigioCriteria criteria) {
        Specification<EnvolvidosConflitoLitigio> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), EnvolvidosConflitoLitigio_.id));
            }
            if (criteria.getNumeroIndividuos() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getNumeroIndividuos(), EnvolvidosConflitoLitigio_.numeroIndividuos));
            }
            if (criteria.getProcessoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getProcessoId(),
                            root -> root.join(EnvolvidosConflitoLitigio_.processos, JoinType.LEFT).get(Processo_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
