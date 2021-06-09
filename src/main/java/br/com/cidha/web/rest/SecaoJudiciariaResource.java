package br.com.cidha.web.rest;

import br.com.cidha.domain.SecaoJudiciaria;
import br.com.cidha.repository.SecaoJudiciariaRepository;
import br.com.cidha.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link br.com.cidha.domain.SecaoJudiciaria}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class SecaoJudiciariaResource {

    private final Logger log = LoggerFactory.getLogger(SecaoJudiciariaResource.class);

    private static final String ENTITY_NAME = "secaoJudiciaria";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SecaoJudiciariaRepository secaoJudiciariaRepository;

    public SecaoJudiciariaResource(SecaoJudiciariaRepository secaoJudiciariaRepository) {
        this.secaoJudiciariaRepository = secaoJudiciariaRepository;
    }

    /**
     * {@code POST  /secao-judiciarias} : Create a new secaoJudiciaria.
     *
     * @param secaoJudiciaria the secaoJudiciaria to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new secaoJudiciaria, or with status {@code 400 (Bad Request)} if the secaoJudiciaria has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/secao-judiciarias")
    public ResponseEntity<SecaoJudiciaria> createSecaoJudiciaria(@RequestBody SecaoJudiciaria secaoJudiciaria) throws URISyntaxException {
        log.debug("REST request to save SecaoJudiciaria : {}", secaoJudiciaria);
        if (secaoJudiciaria.getId() != null) {
            throw new BadRequestAlertException("A new secaoJudiciaria cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SecaoJudiciaria result = secaoJudiciariaRepository.save(secaoJudiciaria);
        return ResponseEntity
            .created(new URI("/api/secao-judiciarias/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /secao-judiciarias/:id} : Updates an existing secaoJudiciaria.
     *
     * @param id the id of the secaoJudiciaria to save.
     * @param secaoJudiciaria the secaoJudiciaria to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated secaoJudiciaria,
     * or with status {@code 400 (Bad Request)} if the secaoJudiciaria is not valid,
     * or with status {@code 500 (Internal Server Error)} if the secaoJudiciaria couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/secao-judiciarias/{id}")
    public ResponseEntity<SecaoJudiciaria> updateSecaoJudiciaria(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SecaoJudiciaria secaoJudiciaria
    ) throws URISyntaxException {
        log.debug("REST request to update SecaoJudiciaria : {}, {}", id, secaoJudiciaria);
        if (secaoJudiciaria.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, secaoJudiciaria.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!secaoJudiciariaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SecaoJudiciaria result = secaoJudiciariaRepository.save(secaoJudiciaria);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, secaoJudiciaria.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /secao-judiciarias/:id} : Partial updates given fields of an existing secaoJudiciaria, field will ignore if it is null
     *
     * @param id the id of the secaoJudiciaria to save.
     * @param secaoJudiciaria the secaoJudiciaria to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated secaoJudiciaria,
     * or with status {@code 400 (Bad Request)} if the secaoJudiciaria is not valid,
     * or with status {@code 404 (Not Found)} if the secaoJudiciaria is not found,
     * or with status {@code 500 (Internal Server Error)} if the secaoJudiciaria couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/secao-judiciarias/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<SecaoJudiciaria> partialUpdateSecaoJudiciaria(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SecaoJudiciaria secaoJudiciaria
    ) throws URISyntaxException {
        log.debug("REST request to partial update SecaoJudiciaria partially : {}, {}", id, secaoJudiciaria);
        if (secaoJudiciaria.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, secaoJudiciaria.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!secaoJudiciariaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SecaoJudiciaria> result = secaoJudiciariaRepository
            .findById(secaoJudiciaria.getId())
            .map(
                existingSecaoJudiciaria -> {
                    if (secaoJudiciaria.getSigla() != null) {
                        existingSecaoJudiciaria.setSigla(secaoJudiciaria.getSigla());
                    }
                    if (secaoJudiciaria.getNome() != null) {
                        existingSecaoJudiciaria.setNome(secaoJudiciaria.getNome());
                    }

                    return existingSecaoJudiciaria;
                }
            )
            .map(secaoJudiciariaRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, secaoJudiciaria.getId().toString())
        );
    }

    /**
     * {@code GET  /secao-judiciarias} : get all the secaoJudiciarias.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of secaoJudiciarias in body.
     */
    @GetMapping("/secao-judiciarias")
    public List<SecaoJudiciaria> getAllSecaoJudiciarias() {
        log.debug("REST request to get all SecaoJudiciarias");
        return secaoJudiciariaRepository.findAll();
    }

    /**
     * {@code GET  /secao-judiciarias/:id} : get the "id" secaoJudiciaria.
     *
     * @param id the id of the secaoJudiciaria to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the secaoJudiciaria, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/secao-judiciarias/{id}")
    public ResponseEntity<SecaoJudiciaria> getSecaoJudiciaria(@PathVariable Long id) {
        log.debug("REST request to get SecaoJudiciaria : {}", id);
        Optional<SecaoJudiciaria> secaoJudiciaria = secaoJudiciariaRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(secaoJudiciaria);
    }

    /**
     * {@code DELETE  /secao-judiciarias/:id} : delete the "id" secaoJudiciaria.
     *
     * @param id the id of the secaoJudiciaria to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/secao-judiciarias/{id}")
    public ResponseEntity<Void> deleteSecaoJudiciaria(@PathVariable Long id) {
        log.debug("REST request to delete SecaoJudiciaria : {}", id);
        secaoJudiciariaRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
