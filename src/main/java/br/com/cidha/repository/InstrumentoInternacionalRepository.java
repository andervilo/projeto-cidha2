package br.com.cidha.repository;

import br.com.cidha.domain.InstrumentoInternacional;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the InstrumentoInternacional entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InstrumentoInternacionalRepository
    extends JpaRepository<InstrumentoInternacional, Long>, JpaSpecificationExecutor<InstrumentoInternacional> {}
