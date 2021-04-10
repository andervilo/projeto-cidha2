package br.com.cidha.repository;

import br.com.cidha.domain.FundamentacaoLegal;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the FundamentacaoLegal entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FundamentacaoLegalRepository extends JpaRepository<FundamentacaoLegal, Long>, JpaSpecificationExecutor<FundamentacaoLegal> {
}
