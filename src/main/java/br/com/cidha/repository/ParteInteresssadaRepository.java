package br.com.cidha.repository;

import br.com.cidha.domain.ParteInteresssada;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ParteInteresssada entity.
 */
@Repository
public interface ParteInteresssadaRepository extends JpaRepository<ParteInteresssada, Long>, JpaSpecificationExecutor<ParteInteresssada> {
    @Query(
        value = "select distinct parteInteresssada from ParteInteresssada parteInteresssada left join fetch parteInteresssada.representanteLegals",
        countQuery = "select count(distinct parteInteresssada) from ParteInteresssada parteInteresssada"
    )
    Page<ParteInteresssada> findAllWithEagerRelationships(Pageable pageable);

    @Query(
        "select distinct parteInteresssada from ParteInteresssada parteInteresssada left join fetch parteInteresssada.representanteLegals"
    )
    List<ParteInteresssada> findAllWithEagerRelationships();

    @Query(
        "select parteInteresssada from ParteInteresssada parteInteresssada left join fetch parteInteresssada.representanteLegals where parteInteresssada.id =:id"
    )
    Optional<ParteInteresssada> findOneWithEagerRelationships(@Param("id") Long id);
}
