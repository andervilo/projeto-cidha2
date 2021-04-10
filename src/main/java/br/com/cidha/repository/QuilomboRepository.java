package br.com.cidha.repository;

import br.com.cidha.domain.Quilombo;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Quilombo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface QuilomboRepository extends JpaRepository<Quilombo, Long>, JpaSpecificationExecutor<Quilombo> {
}
