package br.com.cidha.service;

import br.com.cidha.domain.*; // for static metamodels
import br.com.cidha.domain.Municipio;
import br.com.cidha.repository.MunicipioRepository;
import br.com.cidha.service.criteria.MunicipioCriteria;
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
 * Service for executing complex queries for {@link Municipio} entities in the database.
 * The main input is a {@link MunicipioCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Municipio} or a {@link Page} of {@link Municipio} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MunicipioQueryService extends QueryService<Municipio> {

    private final Logger log = LoggerFactory.getLogger(MunicipioQueryService.class);

    private final MunicipioRepository municipioRepository;

    public MunicipioQueryService(MunicipioRepository municipioRepository) {
        this.municipioRepository = municipioRepository;
    }

    /**
     * Return a {@link List} of {@link Municipio} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Municipio> findByCriteria(MunicipioCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Municipio> specification = createSpecification(criteria);
        return municipioRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Municipio} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Municipio> findByCriteria(MunicipioCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Municipio> specification = createSpecification(criteria);
        return municipioRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MunicipioCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Municipio> specification = createSpecification(criteria);
        return municipioRepository.count(specification);
    }

    /**
     * Function to convert {@link MunicipioCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Municipio> createSpecification(MunicipioCriteria criteria) {
        Specification<Municipio> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Municipio_.id));
            }
            if (criteria.getAmazoniaLegal() != null) {
                specification = specification.and(buildSpecification(criteria.getAmazoniaLegal(), Municipio_.amazoniaLegal));
            }
            if (criteria.getCodigoIbge() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCodigoIbge(), Municipio_.codigoIbge));
            }
            if (criteria.getEstado() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEstado(), Municipio_.estado));
            }
            if (criteria.getNome() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNome(), Municipio_.nome));
            }
            if (criteria.getProcessoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getProcessoId(),
                            root -> root.join(Municipio_.processos, JoinType.LEFT).get(Processo_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
