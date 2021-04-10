package br.com.cidha.service;

import br.com.cidha.domain.*; // for static metamodels
import br.com.cidha.domain.ProcessoConflito;
import br.com.cidha.repository.ProcessoConflitoRepository;
import br.com.cidha.service.criteria.ProcessoConflitoCriteria;
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
 * Service for executing complex queries for {@link ProcessoConflito} entities in the database.
 * The main input is a {@link ProcessoConflitoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ProcessoConflito} or a {@link Page} of {@link ProcessoConflito} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProcessoConflitoQueryService extends QueryService<ProcessoConflito> {

    private final Logger log = LoggerFactory.getLogger(ProcessoConflitoQueryService.class);

    private final ProcessoConflitoRepository processoConflitoRepository;

    public ProcessoConflitoQueryService(ProcessoConflitoRepository processoConflitoRepository) {
        this.processoConflitoRepository = processoConflitoRepository;
    }

    /**
     * Return a {@link List} of {@link ProcessoConflito} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ProcessoConflito> findByCriteria(ProcessoConflitoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ProcessoConflito> specification = createSpecification(criteria);
        return processoConflitoRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link ProcessoConflito} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProcessoConflito> findByCriteria(ProcessoConflitoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ProcessoConflito> specification = createSpecification(criteria);
        return processoConflitoRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProcessoConflitoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ProcessoConflito> specification = createSpecification(criteria);
        return processoConflitoRepository.count(specification);
    }

    /**
     * Function to convert {@link ProcessoConflitoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ProcessoConflito> createSpecification(ProcessoConflitoCriteria criteria) {
        Specification<ProcessoConflito> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ProcessoConflito_.id));
            }
            if (criteria.getNomeCasoComuidade() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getNomeCasoComuidade(), ProcessoConflito_.nomeCasoComuidade));
            }
            if (criteria.getConsultaPrevia() != null) {
                specification = specification.and(buildSpecification(criteria.getConsultaPrevia(), ProcessoConflito_.consultaPrevia));
            }
            if (criteria.getConflitoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getConflitoId(),
                            root -> root.join(ProcessoConflito_.conflitos, JoinType.LEFT).get(Conflito_.id)
                        )
                    );
            }
            if (criteria.getDireitoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getDireitoId(),
                            root -> root.join(ProcessoConflito_.direitos, JoinType.LEFT).get(Direito_.id)
                        )
                    );
            }
            if (criteria.getProcessoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getProcessoId(),
                            root -> root.join(ProcessoConflito_.processos, JoinType.LEFT).get(Processo_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
