package br.com.cidha.repository;

import br.com.cidha.domain.TipoDecisao;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TipoDecisao entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TipoDecisaoRepository extends JpaRepository<TipoDecisao, Long>, JpaSpecificationExecutor<TipoDecisao> {
}
