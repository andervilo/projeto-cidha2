package br.com.cidha.repository;

import br.com.cidha.domain.ProblemaJuridico;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ProblemaJuridico entity.
 */
@Repository
public interface ProblemaJuridicoRepository extends JpaRepository<ProblemaJuridico, Long>, JpaSpecificationExecutor<ProblemaJuridico> {
    @Query(
        value = "select distinct problemaJuridico from ProblemaJuridico problemaJuridico left join fetch problemaJuridico.fundamentacaoDoutrinarias left join fetch problemaJuridico.jurisprudencias left join fetch problemaJuridico.fundamentacaoLegals left join fetch problemaJuridico.instrumentoInternacionals left join fetch problemaJuridico.processos",
        countQuery = "select count(distinct problemaJuridico) from ProblemaJuridico problemaJuridico"
    )
    Page<ProblemaJuridico> findAllWithEagerRelationships(Pageable pageable);

    @Query(
        "select distinct problemaJuridico from ProblemaJuridico problemaJuridico left join fetch problemaJuridico.fundamentacaoDoutrinarias left join fetch problemaJuridico.jurisprudencias left join fetch problemaJuridico.fundamentacaoLegals left join fetch problemaJuridico.instrumentoInternacionals left join fetch problemaJuridico.processos"
    )
    List<ProblemaJuridico> findAllWithEagerRelationships();

    @Query(
        "select problemaJuridico from ProblemaJuridico problemaJuridico left join fetch problemaJuridico.fundamentacaoDoutrinarias left join fetch problemaJuridico.jurisprudencias left join fetch problemaJuridico.fundamentacaoLegals left join fetch problemaJuridico.instrumentoInternacionals left join fetch problemaJuridico.processos where problemaJuridico.id =:id"
    )
    Optional<ProblemaJuridico> findOneWithEagerRelationships(@Param("id") Long id);
}
