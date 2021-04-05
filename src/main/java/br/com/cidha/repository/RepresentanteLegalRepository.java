package br.com.cidha.repository;

import br.com.cidha.domain.RepresentanteLegal;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the RepresentanteLegal entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RepresentanteLegalRepository extends JpaRepository<RepresentanteLegal, Long>, JpaSpecificationExecutor<RepresentanteLegal> {
}
