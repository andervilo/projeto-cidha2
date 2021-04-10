package br.com.cidha.repository;

import br.com.cidha.domain.TerraIndigena;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the TerraIndigena entity.
 */
@Repository
public interface TerraIndigenaRepository extends JpaRepository<TerraIndigena, Long>, JpaSpecificationExecutor<TerraIndigena> {

    @Query(value = "select distinct terraIndigena from TerraIndigena terraIndigena left join fetch terraIndigena.etnias",
        countQuery = "select count(distinct terraIndigena) from TerraIndigena terraIndigena")
    Page<TerraIndigena> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct terraIndigena from TerraIndigena terraIndigena left join fetch terraIndigena.etnias")
    List<TerraIndigena> findAllWithEagerRelationships();

    @Query("select terraIndigena from TerraIndigena terraIndigena left join fetch terraIndigena.etnias where terraIndigena.id =:id")
    Optional<TerraIndigena> findOneWithEagerRelationships(@Param("id") Long id);
}
