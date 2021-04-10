package br.com.cidha.repository;

import br.com.cidha.domain.TipoRecurso;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TipoRecurso entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TipoRecursoRepository extends JpaRepository<TipoRecurso, Long>, JpaSpecificationExecutor<TipoRecurso> {
}
