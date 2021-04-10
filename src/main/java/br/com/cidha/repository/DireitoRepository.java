package br.com.cidha.repository;

import br.com.cidha.domain.Direito;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Direito entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DireitoRepository extends JpaRepository<Direito, Long>, JpaSpecificationExecutor<Direito> {}
