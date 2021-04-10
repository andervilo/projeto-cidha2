package br.com.cidha.repository;

import br.com.cidha.domain.Processo;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Processo entity.
 */
@Repository
public interface ProcessoRepository extends JpaRepository<Processo, Long>, JpaSpecificationExecutor<Processo> {
    @Query(
        value = "select distinct processo from Processo processo left join fetch processo.comarcas left join fetch processo.quilombos left join fetch processo.municipios left join fetch processo.territorios left join fetch processo.atividadeExploracaoIlegals left join fetch processo.unidadeConservacaos left join fetch processo.envolvidosConflitoLitigios left join fetch processo.terraIndigenas left join fetch processo.processoConflitos left join fetch processo.parteInteresssadas left join fetch processo.relators",
        countQuery = "select count(distinct processo) from Processo processo"
    )
    Page<Processo> findAllWithEagerRelationships(Pageable pageable);

    @Query(
        "select distinct processo from Processo processo left join fetch processo.comarcas left join fetch processo.quilombos left join fetch processo.municipios left join fetch processo.territorios left join fetch processo.atividadeExploracaoIlegals left join fetch processo.unidadeConservacaos left join fetch processo.envolvidosConflitoLitigios left join fetch processo.terraIndigenas left join fetch processo.processoConflitos left join fetch processo.parteInteresssadas left join fetch processo.relators"
    )
    List<Processo> findAllWithEagerRelationships();

    @Query(
        "select processo from Processo processo left join fetch processo.comarcas left join fetch processo.quilombos left join fetch processo.municipios left join fetch processo.territorios left join fetch processo.atividadeExploracaoIlegals left join fetch processo.unidadeConservacaos left join fetch processo.envolvidosConflitoLitigios left join fetch processo.terraIndigenas left join fetch processo.processoConflitos left join fetch processo.parteInteresssadas left join fetch processo.relators where processo.id =:id"
    )
    Optional<Processo> findOneWithEagerRelationships(@Param("id") Long id);
}
