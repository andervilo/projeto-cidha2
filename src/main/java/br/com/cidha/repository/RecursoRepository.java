package br.com.cidha.repository;

import br.com.cidha.domain.Recurso;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Recurso entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RecursoRepository extends JpaRepository<Recurso, Long>, JpaSpecificationExecutor<Recurso> {
}
