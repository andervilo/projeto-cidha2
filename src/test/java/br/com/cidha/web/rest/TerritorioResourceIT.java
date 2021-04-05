package br.com.cidha.web.rest;

import br.com.cidha.CidhaApp;
import br.com.cidha.domain.Territorio;
import br.com.cidha.domain.Processo;
import br.com.cidha.repository.TerritorioRepository;
import br.com.cidha.service.TerritorioService;
import br.com.cidha.service.dto.TerritorioCriteria;
import br.com.cidha.service.TerritorioQueryService;

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
 * Integration tests for the {@link TerritorioResource} REST controller.
 */
@SpringBootTest(classes = CidhaApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class TerritorioResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    @Autowired
    private TerritorioRepository territorioRepository;

    @Autowired
    private TerritorioService territorioService;

    @Autowired
    private TerritorioQueryService territorioQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTerritorioMockMvc;

    private Territorio territorio;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Territorio createEntity(EntityManager em) {
        Territorio territorio = new Territorio()
            .nome(DEFAULT_NOME);
        return territorio;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Territorio createUpdatedEntity(EntityManager em) {
        Territorio territorio = new Territorio()
            .nome(UPDATED_NOME);
        return territorio;
    }

    @BeforeEach
    public void initTest() {
        territorio = createEntity(em);
    }

    @Test
    @Transactional
    public void createTerritorio() throws Exception {
        int databaseSizeBeforeCreate = territorioRepository.findAll().size();
        // Create the Territorio
        restTerritorioMockMvc.perform(post("/api/territorios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(territorio)))
            .andExpect(status().isCreated());

        // Validate the Territorio in the database
        List<Territorio> territorioList = territorioRepository.findAll();
        assertThat(territorioList).hasSize(databaseSizeBeforeCreate + 1);
        Territorio testTerritorio = territorioList.get(territorioList.size() - 1);
        assertThat(testTerritorio.getNome()).isEqualTo(DEFAULT_NOME);
    }

    @Test
    @Transactional
    public void createTerritorioWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = territorioRepository.findAll().size();

        // Create the Territorio with an existing ID
        territorio.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTerritorioMockMvc.perform(post("/api/territorios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(territorio)))
            .andExpect(status().isBadRequest());

        // Validate the Territorio in the database
        List<Territorio> territorioList = territorioRepository.findAll();
        assertThat(territorioList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTerritorios() throws Exception {
        // Initialize the database
        territorioRepository.saveAndFlush(territorio);

        // Get all the territorioList
        restTerritorioMockMvc.perform(get("/api/territorios?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(territorio.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)));
    }
    
    @Test
    @Transactional
    public void getTerritorio() throws Exception {
        // Initialize the database
        territorioRepository.saveAndFlush(territorio);

        // Get the territorio
        restTerritorioMockMvc.perform(get("/api/territorios/{id}", territorio.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(territorio.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME));
    }


    @Test
    @Transactional
    public void getTerritoriosByIdFiltering() throws Exception {
        // Initialize the database
        territorioRepository.saveAndFlush(territorio);

        Long id = territorio.getId();

        defaultTerritorioShouldBeFound("id.equals=" + id);
        defaultTerritorioShouldNotBeFound("id.notEquals=" + id);

        defaultTerritorioShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTerritorioShouldNotBeFound("id.greaterThan=" + id);

        defaultTerritorioShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTerritorioShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllTerritoriosByNomeIsEqualToSomething() throws Exception {
        // Initialize the database
        territorioRepository.saveAndFlush(territorio);

        // Get all the territorioList where nome equals to DEFAULT_NOME
        defaultTerritorioShouldBeFound("nome.equals=" + DEFAULT_NOME);

        // Get all the territorioList where nome equals to UPDATED_NOME
        defaultTerritorioShouldNotBeFound("nome.equals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllTerritoriosByNomeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        territorioRepository.saveAndFlush(territorio);

        // Get all the territorioList where nome not equals to DEFAULT_NOME
        defaultTerritorioShouldNotBeFound("nome.notEquals=" + DEFAULT_NOME);

        // Get all the territorioList where nome not equals to UPDATED_NOME
        defaultTerritorioShouldBeFound("nome.notEquals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllTerritoriosByNomeIsInShouldWork() throws Exception {
        // Initialize the database
        territorioRepository.saveAndFlush(territorio);

        // Get all the territorioList where nome in DEFAULT_NOME or UPDATED_NOME
        defaultTerritorioShouldBeFound("nome.in=" + DEFAULT_NOME + "," + UPDATED_NOME);

        // Get all the territorioList where nome equals to UPDATED_NOME
        defaultTerritorioShouldNotBeFound("nome.in=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllTerritoriosByNomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        territorioRepository.saveAndFlush(territorio);

        // Get all the territorioList where nome is not null
        defaultTerritorioShouldBeFound("nome.specified=true");

        // Get all the territorioList where nome is null
        defaultTerritorioShouldNotBeFound("nome.specified=false");
    }
                @Test
    @Transactional
    public void getAllTerritoriosByNomeContainsSomething() throws Exception {
        // Initialize the database
        territorioRepository.saveAndFlush(territorio);

        // Get all the territorioList where nome contains DEFAULT_NOME
        defaultTerritorioShouldBeFound("nome.contains=" + DEFAULT_NOME);

        // Get all the territorioList where nome contains UPDATED_NOME
        defaultTerritorioShouldNotBeFound("nome.contains=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllTerritoriosByNomeNotContainsSomething() throws Exception {
        // Initialize the database
        territorioRepository.saveAndFlush(territorio);

        // Get all the territorioList where nome does not contain DEFAULT_NOME
        defaultTerritorioShouldNotBeFound("nome.doesNotContain=" + DEFAULT_NOME);

        // Get all the territorioList where nome does not contain UPDATED_NOME
        defaultTerritorioShouldBeFound("nome.doesNotContain=" + UPDATED_NOME);
    }


    @Test
    @Transactional
    public void getAllTerritoriosByProcessoIsEqualToSomething() throws Exception {
        // Initialize the database
        territorioRepository.saveAndFlush(territorio);
        Processo processo = ProcessoResourceIT.createEntity(em);
        em.persist(processo);
        em.flush();
        territorio.addProcesso(processo);
        territorioRepository.saveAndFlush(territorio);
        Long processoId = processo.getId();

        // Get all the territorioList where processo equals to processoId
        defaultTerritorioShouldBeFound("processoId.equals=" + processoId);

        // Get all the territorioList where processo equals to processoId + 1
        defaultTerritorioShouldNotBeFound("processoId.equals=" + (processoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTerritorioShouldBeFound(String filter) throws Exception {
        restTerritorioMockMvc.perform(get("/api/territorios?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(territorio.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)));

        // Check, that the count call also returns 1
        restTerritorioMockMvc.perform(get("/api/territorios/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTerritorioShouldNotBeFound(String filter) throws Exception {
        restTerritorioMockMvc.perform(get("/api/territorios?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTerritorioMockMvc.perform(get("/api/territorios/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingTerritorio() throws Exception {
        // Get the territorio
        restTerritorioMockMvc.perform(get("/api/territorios/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTerritorio() throws Exception {
        // Initialize the database
        territorioService.save(territorio);

        int databaseSizeBeforeUpdate = territorioRepository.findAll().size();

        // Update the territorio
        Territorio updatedTerritorio = territorioRepository.findById(territorio.getId()).get();
        // Disconnect from session so that the updates on updatedTerritorio are not directly saved in db
        em.detach(updatedTerritorio);
        updatedTerritorio
            .nome(UPDATED_NOME);

        restTerritorioMockMvc.perform(put("/api/territorios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTerritorio)))
            .andExpect(status().isOk());

        // Validate the Territorio in the database
        List<Territorio> territorioList = territorioRepository.findAll();
        assertThat(territorioList).hasSize(databaseSizeBeforeUpdate);
        Territorio testTerritorio = territorioList.get(territorioList.size() - 1);
        assertThat(testTerritorio.getNome()).isEqualTo(UPDATED_NOME);
    }

    @Test
    @Transactional
    public void updateNonExistingTerritorio() throws Exception {
        int databaseSizeBeforeUpdate = territorioRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTerritorioMockMvc.perform(put("/api/territorios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(territorio)))
            .andExpect(status().isBadRequest());

        // Validate the Territorio in the database
        List<Territorio> territorioList = territorioRepository.findAll();
        assertThat(territorioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTerritorio() throws Exception {
        // Initialize the database
        territorioService.save(territorio);

        int databaseSizeBeforeDelete = territorioRepository.findAll().size();

        // Delete the territorio
        restTerritorioMockMvc.perform(delete("/api/territorios/{id}", territorio.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Territorio> territorioList = territorioRepository.findAll();
        assertThat(territorioList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
