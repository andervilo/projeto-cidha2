package br.com.cidha.repository;

import br.com.cidha.domain.EnvolvidosConflitoLitigio;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the EnvolvidosConflitoLitigio entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EnvolvidosConflitoLitigioRepository
    extends JpaRepository<EnvolvidosConflitoLitigio, Long>, JpaSpecificationExecutor<EnvolvidosConflitoLitigio> {}
