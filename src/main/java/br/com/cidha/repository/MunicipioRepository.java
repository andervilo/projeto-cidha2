package br.com.cidha.repository;

import br.com.cidha.domain.Municipio;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Municipio entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MunicipioRepository extends JpaRepository<Municipio, Long>, JpaSpecificationExecutor<Municipio> {
}
