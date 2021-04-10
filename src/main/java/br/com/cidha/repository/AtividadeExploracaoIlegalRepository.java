package br.com.cidha.repository;

import br.com.cidha.domain.AtividadeExploracaoIlegal;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the AtividadeExploracaoIlegal entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AtividadeExploracaoIlegalRepository
    extends JpaRepository<AtividadeExploracaoIlegal, Long>, JpaSpecificationExecutor<AtividadeExploracaoIlegal> {}
