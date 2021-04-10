package br.com.cidha.service;

import br.com.cidha.domain.*; // for static metamodels
import br.com.cidha.domain.Data;
import br.com.cidha.repository.DataRepository;
import br.com.cidha.service.criteria.DataCriteria;
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
 * Service for executing complex queries for {@link Data} entities in the database.
 * The main input is a {@link DataCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Data} or a {@link Page} of {@link Data} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DataQueryService extends QueryService<Data> {

    private final Logger log = LoggerFactory.getLogger(DataQueryService.class);

    private final DataRepository dataRepository;

    public DataQueryService(DataRepository dataRepository) {
        this.dataRepository = dataRepository;
    }

    /**
     * Return a {@link List} of {@link Data} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Data> findByCriteria(DataCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Data> specification = createSpecification(criteria);
        return dataRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Data} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Data> findByCriteria(DataCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Data> specification = createSpecification(criteria);
        return dataRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DataCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Data> specification = createSpecification(criteria);
        return dataRepository.count(specification);
    }

    /**
     * Function to convert {@link DataCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Data> createSpecification(DataCriteria criteria) {
        Specification<Data> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Data_.id));
            }
            if (criteria.getData() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getData(), Data_.data));
            }
            if (criteria.getTipoDataId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getTipoDataId(), root -> root.join(Data_.tipoData, JoinType.LEFT).get(TipoData_.id))
                    );
            }
            if (criteria.getProcessoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getProcessoId(), root -> root.join(Data_.processo, JoinType.LEFT).get(Processo_.id))
                    );
            }
        }
        return specification;
    }
}
