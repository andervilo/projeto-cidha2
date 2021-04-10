package br.com.cidha.repository;

import br.com.cidha.domain.Jurisprudencia;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Jurisprudencia entity.
 */
@SuppressWarnings("unused")
@Repository
public interface JurisprudenciaRepository extends JpaRepository<Jurisprudencia, Long>, JpaSpecificationExecutor<Jurisprudencia> {}
