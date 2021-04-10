package br.com.cidha.repository;

import br.com.cidha.domain.ConcessaoLiminar;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ConcessaoLiminar entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConcessaoLiminarRepository extends JpaRepository<ConcessaoLiminar, Long>, JpaSpecificationExecutor<ConcessaoLiminar> {
}
