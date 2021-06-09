package br.com.cidha.web.rest;

import br.com.cidha.domain.SubsecaoJudiciaria;
import br.com.cidha.repository.SubsecaoJudiciariaRepository;
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
 * REST controller for managing {@link br.com.cidha.domain.SubsecaoJudiciaria}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class SubsecaoJudiciariaResource {

    private final Logger log = LoggerFactory.getLogger(SubsecaoJudiciariaResource.class);

    private static final String ENTITY_NAME = "subsecaoJudiciaria";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SubsecaoJudiciariaRepository subsecaoJudiciariaRepository;

    public SubsecaoJudiciariaResource(SubsecaoJudiciariaRepository subsecaoJudiciariaRepository) {
        this.subsecaoJudiciariaRepository = subsecaoJudiciariaRepository;
    }

    /**
     * {@code POST  /subsecao-judiciarias} : Create a new subsecaoJudiciaria.
     *
     * @param subsecaoJudiciaria the subsecaoJudiciaria to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new subsecaoJudiciaria, or with status {@code 400 (Bad Request)} if the subsecaoJudiciaria has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/subsecao-judiciarias")
    public ResponseEntity<SubsecaoJudiciaria> createSubsecaoJudiciaria(@RequestBody SubsecaoJudiciaria subsecaoJudiciaria)
        throws URISyntaxException {
        log.debug("REST request to save SubsecaoJudiciaria : {}", subsecaoJudiciaria);
        if (subsecaoJudiciaria.getId() != null) {
            throw new BadRequestAlertException("A new subsecaoJudiciaria cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SubsecaoJudiciaria result = subsecaoJudiciariaRepository.save(subsecaoJudiciaria);
        return ResponseEntity
            .created(new URI("/api/subsecao-judiciarias/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /subsecao-judiciarias/:id} : Updates an existing subsecaoJudiciaria.
     *
     * @param id the id of the subsecaoJudiciaria to save.
     * @param subsecaoJudiciaria the subsecaoJudiciaria to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated subsecaoJudiciaria,
     * or with status {@code 400 (Bad Request)} if the subsecaoJudiciaria is not valid,
     * or with status {@code 500 (Internal Server Error)} if the subsecaoJudiciaria couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/subsecao-judiciarias/{id}")
    public ResponseEntity<SubsecaoJudiciaria> updateSubsecaoJudiciaria(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SubsecaoJudiciaria subsecaoJudiciaria
    ) throws URISyntaxException {
        log.debug("REST request to update SubsecaoJudiciaria : {}, {}", id, subsecaoJudiciaria);
        if (subsecaoJudiciaria.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, subsecaoJudiciaria.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!subsecaoJudiciariaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SubsecaoJudiciaria result = subsecaoJudiciariaRepository.save(subsecaoJudiciaria);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, subsecaoJudiciaria.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /subsecao-judiciarias/:id} : Partial updates given fields of an existing subsecaoJudiciaria, field will ignore if it is null
     *
     * @param id the id of the subsecaoJudiciaria to save.
     * @param subsecaoJudiciaria the subsecaoJudiciaria to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated subsecaoJudiciaria,
     * or with status {@code 400 (Bad Request)} if the subsecaoJudiciaria is not valid,
     * or with status {@code 404 (Not Found)} if the subsecaoJudiciaria is not found,
     * or with status {@code 500 (Internal Server Error)} if the subsecaoJudiciaria couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/subsecao-judiciarias/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<SubsecaoJudiciaria> partialUpdateSubsecaoJudiciaria(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SubsecaoJudiciaria subsecaoJudiciaria
    ) throws URISyntaxException {
        log.debug("REST request to partial update SubsecaoJudiciaria partially : {}, {}", id, subsecaoJudiciaria);
        if (subsecaoJudiciaria.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, subsecaoJudiciaria.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!subsecaoJudiciariaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SubsecaoJudiciaria> result = subsecaoJudiciariaRepository
            .findById(subsecaoJudiciaria.getId())
            .map(
                existingSubsecaoJudiciaria -> {
                    if (subsecaoJudiciaria.getSigla() != null) {
                        existingSubsecaoJudiciaria.setSigla(subsecaoJudiciaria.getSigla());
                    }
                    if (subsecaoJudiciaria.getNome() != null) {
                        existingSubsecaoJudiciaria.setNome(subsecaoJudiciaria.getNome());
                    }

                    return existingSubsecaoJudiciaria;
                }
            )
            .map(subsecaoJudiciariaRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, subsecaoJudiciaria.getId().toString())
        );
    }

    /**
     * {@code GET  /subsecao-judiciarias} : get all the subsecaoJudiciarias.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of subsecaoJudiciarias in body.
     */
    @GetMapping("/subsecao-judiciarias")
    public List<SubsecaoJudiciaria> getAllSubsecaoJudiciarias() {
        log.debug("REST request to get all SubsecaoJudiciarias");
        return subsecaoJudiciariaRepository.findAll();
    }

    /**
     * {@code GET  /subsecao-judiciarias/:id} : get the "id" subsecaoJudiciaria.
     *
     * @param id the id of the subsecaoJudiciaria to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the subsecaoJudiciaria, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/subsecao-judiciarias/{id}")
    public ResponseEntity<SubsecaoJudiciaria> getSubsecaoJudiciaria(@PathVariable Long id) {
        log.debug("REST request to get SubsecaoJudiciaria : {}", id);
        Optional<SubsecaoJudiciaria> subsecaoJudiciaria = subsecaoJudiciariaRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(subsecaoJudiciaria);
    }

    /**
     * {@code DELETE  /subsecao-judiciarias/:id} : delete the "id" subsecaoJudiciaria.
     *
     * @param id the id of the subsecaoJudiciaria to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/subsecao-judiciarias/{id}")
    public ResponseEntity<Void> deleteSubsecaoJudiciaria(@PathVariable Long id) {
        log.debug("REST request to delete SubsecaoJudiciaria : {}", id);
        subsecaoJudiciariaRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
