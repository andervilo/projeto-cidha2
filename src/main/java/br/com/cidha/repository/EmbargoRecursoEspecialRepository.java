package br.com.cidha.repository;

import br.com.cidha.domain.EmbargoRecursoEspecial;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the EmbargoRecursoEspecial entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmbargoRecursoEspecialRepository extends JpaRepository<EmbargoRecursoEspecial, Long>, JpaSpecificationExecutor<EmbargoRecursoEspecial> {
}
