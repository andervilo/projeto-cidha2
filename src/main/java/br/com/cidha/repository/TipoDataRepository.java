package br.com.cidha.repository;

import br.com.cidha.domain.TipoData;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TipoData entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TipoDataRepository extends JpaRepository<TipoData, Long>, JpaSpecificationExecutor<TipoData> {
}
