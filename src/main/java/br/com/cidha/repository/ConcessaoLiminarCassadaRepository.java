package br.com.cidha.repository;

import br.com.cidha.domain.ConcessaoLiminarCassada;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ConcessaoLiminarCassada entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConcessaoLiminarCassadaRepository
    extends JpaRepository<ConcessaoLiminarCassada, Long>, JpaSpecificationExecutor<ConcessaoLiminarCassada> {}
