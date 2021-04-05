package br.com.cidha.repository;

import br.com.cidha.domain.FundamentacaoDoutrinaria;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the FundamentacaoDoutrinaria entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FundamentacaoDoutrinariaRepository extends JpaRepository<FundamentacaoDoutrinaria, Long>, JpaSpecificationExecutor<FundamentacaoDoutrinaria> {
}
