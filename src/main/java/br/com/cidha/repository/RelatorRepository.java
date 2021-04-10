package br.com.cidha.repository;

import br.com.cidha.domain.Relator;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Relator entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RelatorRepository extends JpaRepository<Relator, Long>, JpaSpecificationExecutor<Relator> {
}
