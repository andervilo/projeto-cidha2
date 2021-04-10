package br.com.cidha.web.rest;

import br.com.cidha.CidhaApp;
import br.com.cidha.domain.TipoRepresentante;
import br.com.cidha.repository.TipoRepresentanteRepository;
import br.com.cidha.service.TipoRepresentanteService;
import br.com.cidha.service.dto.TipoRepresentanteCriteria;
import br.com.cidha.service.TipoRepresentanteQueryService;

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
 * Integration tests for the {@link TipoRepresentanteResource} REST controller.
 */
@SpringBootTest(classes = CidhaApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class TipoRepresentanteResourceIT {

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    @Autowired
    private TipoRepresentanteRepository tipoRepresentanteRepository;

    @Autowired
    private TipoRepresentanteService tipoRepresentanteService;

    @Autowired
    private TipoRepresentanteQueryService tipoRepresentanteQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTipoRepresentanteMockMvc;

    private TipoRepresentante tipoRepresentante;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoRepresentante createEntity(EntityManager em) {
        TipoRepresentante tipoRepresentante = new TipoRepresentante()
            .descricao(DEFAULT_DESCRICAO);
        return tipoRepresentante;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoRepresentante createUpdatedEntity(EntityManager em) {
        TipoRepresentante tipoRepresentante = new TipoRepresentante()
            .descricao(UPDATED_DESCRICAO);
        return tipoRepresentante;
    }

    @BeforeEach
    public void initTest() {
        tipoRepresentante = createEntity(em);
    }

    @Test
    @Transactional
    public void createTipoRepresentante() throws Exception {
        int databaseSizeBeforeCreate = tipoRepresentanteRepository.findAll().size();
        // Create the TipoRepresentante
        restTipoRepresentanteMockMvc.perform(post("/api/tipo-representantes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tipoRepresentante)))
            .andExpect(status().isCreated());

        // Validate the TipoRepresentante in the database
        List<TipoRepresentante> tipoRepresentanteList = tipoRepresentanteRepository.findAll();
        assertThat(tipoRepresentanteList).hasSize(databaseSizeBeforeCreate + 1);
        TipoRepresentante testTipoRepresentante = tipoRepresentanteList.get(tipoRepresentanteList.size() - 1);
        assertThat(testTipoRepresentante.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
    }

    @Test
    @Transactional
    public void createTipoRepresentanteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tipoRepresentanteRepository.findAll().size();

        // Create the TipoRepresentante with an existing ID
        tipoRepresentante.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTipoRepresentanteMockMvc.perform(post("/api/tipo-representantes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tipoRepresentante)))
            .andExpect(status().isBadRequest());

        // Validate the TipoRepresentante in the database
        List<TipoRepresentante> tipoRepresentanteList = tipoRepresentanteRepository.findAll();
        assertThat(tipoRepresentanteList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTipoRepresentantes() throws Exception {
        // Initialize the database
        tipoRepresentanteRepository.saveAndFlush(tipoRepresentante);

        // Get all the tipoRepresentanteList
        restTipoRepresentanteMockMvc.perform(get("/api/tipo-representantes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoRepresentante.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)));
    }
    
    @Test
    @Transactional
    public void getTipoRepresentante() throws Exception {
        // Initialize the database
        tipoRepresentanteRepository.saveAndFlush(tipoRepresentante);

        // Get the tipoRepresentante
        restTipoRepresentanteMockMvc.perform(get("/api/tipo-representantes/{id}", tipoRepresentante.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tipoRepresentante.getId().intValue()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO));
    }


    @Test
    @Transactional
    public void getTipoRepresentantesByIdFiltering() throws Exception {
        // Initialize the database
        tipoRepresentanteRepository.saveAndFlush(tipoRepresentante);

        Long id = tipoRepresentante.getId();

        defaultTipoRepresentanteShouldBeFound("id.equals=" + id);
        defaultTipoRepresentanteShouldNotBeFound("id.notEquals=" + id);

        defaultTipoRepresentanteShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTipoRepresentanteShouldNotBeFound("id.greaterThan=" + id);

        defaultTipoRepresentanteShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTipoRepresentanteShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllTipoRepresentantesByDescricaoIsEqualToSomething() throws Exception {
        // Initialize the database
        tipoRepresentanteRepository.saveAndFlush(tipoRepresentante);

        // Get all the tipoRepresentanteList where descricao equals to DEFAULT_DESCRICAO
        defaultTipoRepresentanteShouldBeFound("descricao.equals=" + DEFAULT_DESCRICAO);

        // Get all the tipoRepresentanteList where descricao equals to UPDATED_DESCRICAO
        defaultTipoRepresentanteShouldNotBeFound("descricao.equals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllTipoRepresentantesByDescricaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tipoRepresentanteRepository.saveAndFlush(tipoRepresentante);

        // Get all the tipoRepresentanteList where descricao not equals to DEFAULT_DESCRICAO
        defaultTipoRepresentanteShouldNotBeFound("descricao.notEquals=" + DEFAULT_DESCRICAO);

        // Get all the tipoRepresentanteList where descricao not equals to UPDATED_DESCRICAO
        defaultTipoRepresentanteShouldBeFound("descricao.notEquals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllTipoRepresentantesByDescricaoIsInShouldWork() throws Exception {
        // Initialize the database
        tipoRepresentanteRepository.saveAndFlush(tipoRepresentante);

        // Get all the tipoRepresentanteList where descricao in DEFAULT_DESCRICAO or UPDATED_DESCRICAO
        defaultTipoRepresentanteShouldBeFound("descricao.in=" + DEFAULT_DESCRICAO + "," + UPDATED_DESCRICAO);

        // Get all the tipoRepresentanteList where descricao equals to UPDATED_DESCRICAO
        defaultTipoRepresentanteShouldNotBeFound("descricao.in=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllTipoRepresentantesByDescricaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        tipoRepresentanteRepository.saveAndFlush(tipoRepresentante);

        // Get all the tipoRepresentanteList where descricao is not null
        defaultTipoRepresentanteShouldBeFound("descricao.specified=true");

        // Get all the tipoRepresentanteList where descricao is null
        defaultTipoRepresentanteShouldNotBeFound("descricao.specified=false");
    }
                @Test
    @Transactional
    public void getAllTipoRepresentantesByDescricaoContainsSomething() throws Exception {
        // Initialize the database
        tipoRepresentanteRepository.saveAndFlush(tipoRepresentante);

        // Get all the tipoRepresentanteList where descricao contains DEFAULT_DESCRICAO
        defaultTipoRepresentanteShouldBeFound("descricao.contains=" + DEFAULT_DESCRICAO);

        // Get all the tipoRepresentanteList where descricao contains UPDATED_DESCRICAO
        defaultTipoRepresentanteShouldNotBeFound("descricao.contains=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllTipoRepresentantesByDescricaoNotContainsSomething() throws Exception {
        // Initialize the database
        tipoRepresentanteRepository.saveAndFlush(tipoRepresentante);

        // Get all the tipoRepresentanteList where descricao does not contain DEFAULT_DESCRICAO
        defaultTipoRepresentanteShouldNotBeFound("descricao.doesNotContain=" + DEFAULT_DESCRICAO);

        // Get all the tipoRepresentanteList where descricao does not contain UPDATED_DESCRICAO
        defaultTipoRepresentanteShouldBeFound("descricao.doesNotContain=" + UPDATED_DESCRICAO);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTipoRepresentanteShouldBeFound(String filter) throws Exception {
        restTipoRepresentanteMockMvc.perform(get("/api/tipo-representantes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoRepresentante.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)));

        // Check, that the count call also returns 1
        restTipoRepresentanteMockMvc.perform(get("/api/tipo-representantes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTipoRepresentanteShouldNotBeFound(String filter) throws Exception {
        restTipoRepresentanteMockMvc.perform(get("/api/tipo-representantes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTipoRepresentanteMockMvc.perform(get("/api/tipo-representantes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingTipoRepresentante() throws Exception {
        // Get the tipoRepresentante
        restTipoRepresentanteMockMvc.perform(get("/api/tipo-representantes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTipoRepresentante() throws Exception {
        // Initialize the database
        tipoRepresentanteService.save(tipoRepresentante);

        int databaseSizeBeforeUpdate = tipoRepresentanteRepository.findAll().size();

        // Update the tipoRepresentante
        TipoRepresentante updatedTipoRepresentante = tipoRepresentanteRepository.findById(tipoRepresentante.getId()).get();
        // Disconnect from session so that the updates on updatedTipoRepresentante are not directly saved in db
        em.detach(updatedTipoRepresentante);
        updatedTipoRepresentante
            .descricao(UPDATED_DESCRICAO);

        restTipoRepresentanteMockMvc.perform(put("/api/tipo-representantes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTipoRepresentante)))
            .andExpect(status().isOk());

        // Validate the TipoRepresentante in the database
        List<TipoRepresentante> tipoRepresentanteList = tipoRepresentanteRepository.findAll();
        assertThat(tipoRepresentanteList).hasSize(databaseSizeBeforeUpdate);
        TipoRepresentante testTipoRepresentante = tipoRepresentanteList.get(tipoRepresentanteList.size() - 1);
        assertThat(testTipoRepresentante.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void updateNonExistingTipoRepresentante() throws Exception {
        int databaseSizeBeforeUpdate = tipoRepresentanteRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTipoRepresentanteMockMvc.perform(put("/api/tipo-representantes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tipoRepresentante)))
            .andExpect(status().isBadRequest());

        // Validate the TipoRepresentante in the database
        List<TipoRepresentante> tipoRepresentanteList = tipoRepresentanteRepository.findAll();
        assertThat(tipoRepresentanteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTipoRepresentante() throws Exception {
        // Initialize the database
        tipoRepresentanteService.save(tipoRepresentante);

        int databaseSizeBeforeDelete = tipoRepresentanteRepository.findAll().size();

        // Delete the tipoRepresentante
        restTipoRepresentanteMockMvc.perform(delete("/api/tipo-representantes/{id}", tipoRepresentante.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TipoRepresentante> tipoRepresentanteList = tipoRepresentanteRepository.findAll();
        assertThat(tipoRepresentanteList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
