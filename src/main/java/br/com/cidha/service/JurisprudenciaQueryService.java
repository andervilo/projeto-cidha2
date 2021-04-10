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

import br.com.cidha.domain.Jurisprudencia;
import br.com.cidha.domain.*; // for static metamodels
import br.com.cidha.repository.JurisprudenciaRepository;
import br.com.cidha.service.dto.JurisprudenciaCriteria;

/**
 * Service for executing complex queries for {@link Jurisprudencia} entities in the database.
 * The main input is a {@link JurisprudenciaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Jurisprudencia} or a {@link Page} of {@link Jurisprudencia} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class JurisprudenciaQueryService extends QueryService<Jurisprudencia> {

    private final Logger log = LoggerFactory.getLogger(JurisprudenciaQueryService.class);

    private final JurisprudenciaRepository jurisprudenciaRepository;

    public JurisprudenciaQueryService(JurisprudenciaRepository jurisprudenciaRepository) {
        this.jurisprudenciaRepository = jurisprudenciaRepository;
    }

    /**
     * Return a {@link List} of {@link Jurisprudencia} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Jurisprudencia> findByCriteria(JurisprudenciaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Jurisprudencia> specification = createSpecification(criteria);
        return jurisprudenciaRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Jurisprudencia} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Jurisprudencia> findByCriteria(JurisprudenciaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Jurisprudencia> specification = createSpecification(criteria);
        return jurisprudenciaRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(JurisprudenciaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Jurisprudencia> specification = createSpecification(criteria);
        return jurisprudenciaRepository.count(specification);
    }

    /**
     * Function to convert {@link JurisprudenciaCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Jurisprudencia> createSpecification(JurisprudenciaCriteria criteria) {
        Specification<Jurisprudencia> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Jurisprudencia_.id));
            }
            if (criteria.getFolhasJurisprudenciaCitada() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFolhasJurisprudenciaCitada(), Jurisprudencia_.folhasJurisprudenciaCitada));
            }
            if (criteria.getProblemaJuridicoId() != null) {
                specification = specification.and(buildSpecification(criteria.getProblemaJuridicoId(),
                    root -> root.join(Jurisprudencia_.problemaJuridicos, JoinType.LEFT).get(ProblemaJuridico_.id)));
            }
        }
        return specification;
    }
}
