package br.com.cidha.repository;

import br.com.cidha.domain.SecaoJudiciaria;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the SecaoJudiciaria entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SecaoJudiciariaRepository extends JpaRepository<SecaoJudiciaria, Long> {}
