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

import br.com.cidha.domain.InstrumentoInternacional;
import br.com.cidha.domain.*; // for static metamodels
import br.com.cidha.repository.InstrumentoInternacionalRepository;
import br.com.cidha.service.dto.InstrumentoInternacionalCriteria;

/**
 * Service for executing complex queries for {@link InstrumentoInternacional} entities in the database.
 * The main input is a {@link InstrumentoInternacionalCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link InstrumentoInternacional} or a {@link Page} of {@link InstrumentoInternacional} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class InstrumentoInternacionalQueryService extends QueryService<InstrumentoInternacional> {

    private final Logger log = LoggerFactory.getLogger(InstrumentoInternacionalQueryService.class);

    private final InstrumentoInternacionalRepository instrumentoInternacionalRepository;

    public InstrumentoInternacionalQueryService(InstrumentoInternacionalRepository instrumentoInternacionalRepository) {
        this.instrumentoInternacionalRepository = instrumentoInternacionalRepository;
    }

    /**
     * Return a {@link List} of {@link InstrumentoInternacional} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<InstrumentoInternacional> findByCriteria(InstrumentoInternacionalCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<InstrumentoInternacional> specification = createSpecification(criteria);
        return instrumentoInternacionalRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link InstrumentoInternacional} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<InstrumentoInternacional> findByCriteria(InstrumentoInternacionalCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<InstrumentoInternacional> specification = createSpecification(criteria);
        return instrumentoInternacionalRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(InstrumentoInternacionalCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<InstrumentoInternacional> specification = createSpecification(criteria);
        return instrumentoInternacionalRepository.count(specification);
    }

    /**
     * Function to convert {@link InstrumentoInternacionalCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<InstrumentoInternacional> createSpecification(InstrumentoInternacionalCriteria criteria) {
        Specification<InstrumentoInternacional> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), InstrumentoInternacional_.id));
            }
            if (criteria.getFolhasInstrumentoInternacional() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFolhasInstrumentoInternacional(), InstrumentoInternacional_.folhasInstrumentoInternacional));
            }
            if (criteria.getProblemaJuridicoId() != null) {
                specification = specification.and(buildSpecification(criteria.getProblemaJuridicoId(),
                    root -> root.join(InstrumentoInternacional_.problemaJuridicos, JoinType.LEFT).get(ProblemaJuridico_.id)));
            }
        }
        return specification;
    }
}
