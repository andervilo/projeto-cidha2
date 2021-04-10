package br.com.cidha.repository;

import br.com.cidha.domain.ProcessoConflito;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ProcessoConflito entity.
 */
@Repository
public interface ProcessoConflitoRepository extends JpaRepository<ProcessoConflito, Long>, JpaSpecificationExecutor<ProcessoConflito> {
    @Query(
        value = "select distinct processoConflito from ProcessoConflito processoConflito left join fetch processoConflito.direitos",
        countQuery = "select count(distinct processoConflito) from ProcessoConflito processoConflito"
    )
    Page<ProcessoConflito> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct processoConflito from ProcessoConflito processoConflito left join fetch processoConflito.direitos")
    List<ProcessoConflito> findAllWithEagerRelationships();

    @Query(
        "select processoConflito from ProcessoConflito processoConflito left join fetch processoConflito.direitos where processoConflito.id =:id"
    )
    Optional<ProcessoConflito> findOneWithEagerRelationships(@Param("id") Long id);
}
