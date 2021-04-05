package br.com.cidha.web.rest;

import br.com.cidha.CidhaApp;
import br.com.cidha.domain.RepresentanteLegal;
import br.com.cidha.domain.TipoRepresentante;
import br.com.cidha.domain.ParteInteresssada;
import br.com.cidha.repository.RepresentanteLegalRepository;
import br.com.cidha.service.RepresentanteLegalService;
import br.com.cidha.service.dto.RepresentanteLegalCriteria;
import br.com.cidha.service.RepresentanteLegalQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link RepresentanteLegalResource} REST controller.
 */
@SpringBootTest(classes = CidhaApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class RepresentanteLegalResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    @Autowired
    private RepresentanteLegalRepository representanteLegalRepository;

    @Autowired
    private RepresentanteLegalService representanteLegalService;

    @Autowired
    private RepresentanteLegalQueryService representanteLegalQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRepresentanteLegalMockMvc;

    private RepresentanteLegal representanteLegal;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RepresentanteLegal createEntity(EntityManager em) {
        RepresentanteLegal representanteLegal = new RepresentanteLegal()
            .nome(DEFAULT_NOME);
        return representanteLegal;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RepresentanteLegal createUpdatedEntity(EntityManager em) {
        RepresentanteLegal representanteLegal = new RepresentanteLegal()
            .nome(UPDATED_NOME);
        return representanteLegal;
    }

    @BeforeEach
    public void initTest() {
        representanteLegal = createEntity(em);
    }

    @Test
    @Transactional
    public void createRepresentanteLegal() throws Exception {
        int databaseSizeBeforeCreate = representanteLegalRepository.findAll().size();
        // Create the RepresentanteLegal
        restRepresentanteLegalMockMvc.perform(post("/api/representante-legals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(representanteLegal)))
            .andExpect(status().isCreated());

        // Validate the RepresentanteLegal in the database
        List<RepresentanteLegal> representanteLegalList = representanteLegalRepository.findAll();
        assertThat(representanteLegalList).hasSize(databaseSizeBeforeCreate + 1);
        RepresentanteLegal testRepresentanteLegal = representanteLegalList.get(representanteLegalList.size() - 1);
        assertThat(testRepresentanteLegal.getNome()).isEqualTo(DEFAULT_NOME);
    }

    @Test
    @Transactional
    public void createRepresentanteLegalWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = representanteLegalRepository.findAll().size();

        // Create the RepresentanteLegal with an existing ID
        representanteLegal.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRepresentanteLegalMockMvc.perform(post("/api/representante-legals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(representanteLegal)))
            .andExpect(status().isBadRequest());

        // Validate the RepresentanteLegal in the database
        List<RepresentanteLegal> representanteLegalList = representanteLegalRepository.findAll();
        assertThat(representanteLegalList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllRepresentanteLegals() throws Exception {
        // Initialize the database
        representanteLegalRepository.saveAndFlush(representanteLegal);

        // Get all the representanteLegalList
        restRepresentanteLegalMockMvc.perform(get("/api/representante-legals?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(representanteLegal.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)));
    }
    
    @Test
    @Transactional
    public void getRepresentanteLegal() throws Exception {
        // Initialize the database
        representanteLegalRepository.saveAndFlush(representanteLegal);

        // Get the representanteLegal
        restRepresentanteLegalMockMvc.perform(get("/api/representante-legals/{id}", representanteLegal.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(representanteLegal.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME));
    }


    @Test
    @Transactional
    public void getRepresentanteLegalsByIdFiltering() throws Exception {
        // Initialize the database
        representanteLegalRepository.saveAndFlush(representanteLegal);

        Long id = representanteLegal.getId();

        defaultRepresentanteLegalShouldBeFound("id.equals=" + id);
        defaultRepresentanteLegalShouldNotBeFound("id.notEquals=" + id);

        defaultRepresentanteLegalShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultRepresentanteLegalShouldNotBeFound("id.greaterThan=" + id);

        defaultRepresentanteLegalShouldBeFound("id.lessThanOrEqual=" + id);
        defaultRepresentanteLegalShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllRepresentanteLegalsByNomeIsEqualToSomething() throws Exception {
        // Initialize the database
        representanteLegalRepository.saveAndFlush(representanteLegal);

        // Get all the representanteLegalList where nome equals to DEFAULT_NOME
        defaultRepresentanteLegalShouldBeFound("nome.equals=" + DEFAULT_NOME);

        // Get all the representanteLegalList where nome equals to UPDATED_NOME
        defaultRepresentanteLegalShouldNotBeFound("nome.equals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllRepresentanteLegalsByNomeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        representanteLegalRepository.saveAndFlush(representanteLegal);

        // Get all the representanteLegalList where nome not equals to DEFAULT_NOME
        defaultRepresentanteLegalShouldNotBeFound("nome.notEquals=" + DEFAULT_NOME);

        // Get all the representanteLegalList where nome not equals to UPDATED_NOME
        defaultRepresentanteLegalShouldBeFound("nome.notEquals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllRepresentanteLegalsByNomeIsInShouldWork() throws Exception {
        // Initialize the database
        representanteLegalRepository.saveAndFlush(representanteLegal);

        // Get all the representanteLegalList where nome in DEFAULT_NOME or UPDATED_NOME
        defaultRepresentanteLegalShouldBeFound("nome.in=" + DEFAULT_NOME + "," + UPDATED_NOME);

        // Get all the representanteLegalList where nome equals to UPDATED_NOME
        defaultRepresentanteLegalShouldNotBeFound("nome.in=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllRepresentanteLegalsByNomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        representanteLegalRepository.saveAndFlush(representanteLegal);

        // Get all the representanteLegalList where nome is not null
        defaultRepresentanteLegalShouldBeFound("nome.specified=true");

        // Get all the representanteLegalList where nome is null
        defaultRepresentanteLegalShouldNotBeFound("nome.specified=false");
    }
                @Test
    @Transactional
    public void getAllRepresentanteLegalsByNomeContainsSomething() throws Exception {
        // Initialize the database
        representanteLegalRepository.saveAndFlush(representanteLegal);

        // Get all the representanteLegalList where nome contains DEFAULT_NOME
        defaultRepresentanteLegalShouldBeFound("nome.contains=" + DEFAULT_NOME);

        // Get all the representanteLegalList where nome contains UPDATED_NOME
        defaultRepresentanteLegalShouldNotBeFound("nome.contains=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllRepresentanteLegalsByNomeNotContainsSomething() throws Exception {
        // Initialize the database
        representanteLegalRepository.saveAndFlush(representanteLegal);

        // Get all the representanteLegalList where nome does not contain DEFAULT_NOME
        defaultRepresentanteLegalShouldNotBeFound("nome.doesNotContain=" + DEFAULT_NOME);

        // Get all the representanteLegalList where nome does not contain UPDATED_NOME
        defaultRepresentanteLegalShouldBeFound("nome.doesNotContain=" + UPDATED_NOME);
    }


    @Test
    @Transactional
    public void getAllRepresentanteLegalsByTipoRepresentanteIsEqualToSomething() throws Exception {
        // Initialize the database
        representanteLegalRepository.saveAndFlush(representanteLegal);
        TipoRepresentante tipoRepresentante = TipoRepresentanteResourceIT.createEntity(em);
        em.persist(tipoRepresentante);
        em.flush();
        representanteLegal.setTipoRepresentante(tipoRepresentante);
        representanteLegalRepository.saveAndFlush(representanteLegal);
        Long tipoRepresentanteId = tipoRepresentante.getId();

        // Get all the representanteLegalList where tipoRepresentante equals to tipoRepresentanteId
        defaultRepresentanteLegalShouldBeFound("tipoRepresentanteId.equals=" + tipoRepresentanteId);

        // Get all the representanteLegalList where tipoRepresentante equals to tipoRepresentanteId + 1
        defaultRepresentanteLegalShouldNotBeFound("tipoRepresentanteId.equals=" + (tipoRepresentanteId + 1));
    }


    @Test
    @Transactional
    public void getAllRepresentanteLegalsByProcessoConflitoIsEqualToSomething() throws Exception {
        // Initialize the database
        representanteLegalRepository.saveAndFlush(representanteLegal);
        ParteInteresssada processoConflito = ParteInteresssadaResourceIT.createEntity(em);
        em.persist(processoConflito);
        em.flush();
        representanteLegal.addProcessoConflito(processoConflito);
        representanteLegalRepository.saveAndFlush(representanteLegal);
        Long processoConflitoId = processoConflito.getId();

        // Get all the representanteLegalList where processoConflito equals to processoConflitoId
        defaultRepresentanteLegalShouldBeFound("processoConflitoId.equals=" + processoConflitoId);

        // Get all the representanteLegalList where processoConflito equals to processoConflitoId + 1
        defaultRepresentanteLegalShouldNotBeFound("processoConflitoId.equals=" + (processoConflitoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultRepresentanteLegalShouldBeFound(String filter) throws Exception {
        restRepresentanteLegalMockMvc.perform(get("/api/representante-legals?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(representanteLegal.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)));

        // Check, that the count call also returns 1
        restRepresentanteLegalMockMvc.perform(get("/api/representante-legals/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultRepresentanteLegalShouldNotBeFound(String filter) throws Exception {
        restRepresentanteLegalMockMvc.perform(get("/api/representante-legals?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restRepresentanteLegalMockMvc.perform(get("/api/representante-legals/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingRepresentanteLegal() throws Exception {
        // Get the representanteLegal
        restRepresentanteLegalMockMvc.perform(get("/api/representante-legals/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRepresentanteLegal() throws Exception {
        // Initialize the database
        representanteLegalService.save(representanteLegal);

        int databaseSizeBeforeUpdate = representanteLegalRepository.findAll().size();

        // Update the representanteLegal
        RepresentanteLegal updatedRepresentanteLegal = representanteLegalRepository.findById(representanteLegal.getId()).get();
        // Disconnect from session so that the updates on updatedRepresentanteLegal are not directly saved in db
        em.detach(updatedRepresentanteLegal);
        updatedRepresentanteLegal
            .nome(UPDATED_NOME);

        restRepresentanteLegalMockMvc.perform(put("/api/representante-legals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedRepresentanteLegal)))
            .andExpect(status().isOk());

        // Validate the RepresentanteLegal in the database
        List<RepresentanteLegal> representanteLegalList = representanteLegalRepository.findAll();
        assertThat(representanteLegalList).hasSize(databaseSizeBeforeUpdate);
        RepresentanteLegal testRepresentanteLegal = representanteLegalList.get(representanteLegalList.size() - 1);
        assertThat(testRepresentanteLegal.getNome()).isEqualTo(UPDATED_NOME);
    }

    @Test
    @Transactional
    public void updateNonExistingRepresentanteLegal() throws Exception {
        int databaseSizeBeforeUpdate = representanteLegalRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRepresentanteLegalMockMvc.perform(put("/api/representante-legals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(representanteLegal)))
            .andExpect(status().isBadRequest());

        // Validate the RepresentanteLegal in the database
        List<RepresentanteLegal> representanteLegalList = representanteLegalRepository.findAll();
        assertThat(representanteLegalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRepresentanteLegal() throws Exception {
        // Initialize the database
        representanteLegalService.save(representanteLegal);

        int databaseSizeBeforeDelete = representanteLegalRepository.findAll().size();

        // Delete the representanteLegal
        restRepresentanteLegalMockMvc.perform(delete("/api/representante-legals/{id}", representanteLegal.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RepresentanteLegal> representanteLegalList = representanteLegalRepository.findAll();
        assertThat(representanteLegalList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
