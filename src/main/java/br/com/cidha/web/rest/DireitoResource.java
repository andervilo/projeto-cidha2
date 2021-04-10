package br.com.cidha.web.rest;

import br.com.cidha.domain.Direito;
import br.com.cidha.repository.DireitoRepository;
import br.com.cidha.service.DireitoQueryService;
import br.com.cidha.service.DireitoService;
import br.com.cidha.service.criteria.DireitoCriteria;
import br.com.cidha.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link br.com.cidha.domain.Direito}.
 */
@RestController
@RequestMapping("/api")
public class DireitoResource {

    private final Logger log = LoggerFactory.getLogger(DireitoResource.class);

    private static final String ENTITY_NAME = "direito";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DireitoService direitoService;

    private final DireitoRepository direitoRepository;

    private final DireitoQueryService direitoQueryService;

    public DireitoResource(DireitoService direitoService, DireitoRepository direitoRepository, DireitoQueryService direitoQueryService) {
        this.direitoService = direitoService;
        this.direitoRepository = direitoRepository;
        this.direitoQueryService = direitoQueryService;
    }

    /**
     * {@code POST  /direitos} : Create a new direito.
     *
     * @param direito the direito to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new direito, or with status {@code 400 (Bad Request)} if the direito has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/direitos")
    public ResponseEntity<Direito> createDireito(@RequestBody Direito direito) throws URISyntaxException {
        log.debug("REST request to save Direito : {}", direito);
        if (direito.getId() != null) {
            throw new BadRequestAlertException("A new direito cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Direito result = direitoService.save(direito);
        return ResponseEntity
            .created(new URI("/api/direitos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /direitos/:id} : Updates an existing direito.
     *
     * @param id the id of the direito to save.
     * @param direito the direito to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated direito,
     * or with status {@code 400 (Bad Request)} if the direito is not valid,
     * or with status {@code 500 (Internal Server Error)} if the direito couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/direitos/{id}")
    public ResponseEntity<Direito> updateDireito(@PathVariable(value = "id", required = false) final Long id, @RequestBody Direito direito)
        throws URISyntaxException {
        log.debug("REST request to update Direito : {}, {}", id, direito);
        if (direito.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, direito.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!direitoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Direito result = direitoService.save(direito);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, direito.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /direitos/:id} : Partial updates given fields of an existing direito, field will ignore if it is null
     *
     * @param id the id of the direito to save.
     * @param direito the direito to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated direito,
     * or with status {@code 400 (Bad Request)} if the direito is not valid,
     * or with status {@code 404 (Not Found)} if the direito is not found,
     * or with status {@code 500 (Internal Server Error)} if the direito couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/direitos/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<Direito> partialUpdateDireito(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Direito direito
    ) throws URISyntaxException {
        log.debug("REST request to partial update Direito partially : {}, {}", id, direito);
        if (direito.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, direito.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!direitoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Direito> result = direitoService.partialUpdate(direito);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, direito.getId().toString())
        );
    }

    /**
     * {@code GET  /direitos} : get all the direitos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of direitos in body.
     */
    @GetMapping("/direitos")
    public ResponseEntity<List<Direito>> getAllDireitos(DireitoCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Direitos by criteria: {}", criteria);
        Page<Direito> page = direitoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /direitos/count} : count all the direitos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/direitos/count")
    public ResponseEntity<Long> countDireitos(DireitoCriteria criteria) {
        log.debug("REST request to count Direitos by criteria: {}", criteria);
        return ResponseEntity.ok().body(direitoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /direitos/:id} : get the "id" direito.
     *
     * @param id the id of the direito to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the direito, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/direitos/{id}")
    public ResponseEntity<Direito> getDireito(@PathVariable Long id) {
        log.debug("REST request to get Direito : {}", id);
        Optional<Direito> direito = direitoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(direito);
    }

    /**
     * {@code DELETE  /direitos/:id} : delete the "id" direito.
     *
     * @param id the id of the direito to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/direitos/{id}")
    public ResponseEntity<Void> deleteDireito(@PathVariable Long id) {
        log.debug("REST request to delete Direito : {}", id);
        direitoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
