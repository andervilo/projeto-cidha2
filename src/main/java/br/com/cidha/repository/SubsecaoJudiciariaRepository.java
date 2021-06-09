package br.com.cidha.repository;

import br.com.cidha.domain.SubsecaoJudiciaria;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the SubsecaoJudiciaria entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SubsecaoJudiciariaRepository extends JpaRepository<SubsecaoJudiciaria, Long> {}
