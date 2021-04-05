package br.com.cidha.repository;

import br.com.cidha.domain.EmbargoDeclaracao;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the EmbargoDeclaracao entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmbargoDeclaracaoRepository extends JpaRepository<EmbargoDeclaracao, Long>, JpaSpecificationExecutor<EmbargoDeclaracao> {
}
