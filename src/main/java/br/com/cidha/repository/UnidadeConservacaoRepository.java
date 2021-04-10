package br.com.cidha.repository;

import br.com.cidha.domain.UnidadeConservacao;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the UnidadeConservacao entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UnidadeConservacaoRepository extends JpaRepository<UnidadeConservacao, Long>, JpaSpecificationExecutor<UnidadeConservacao> {
}
