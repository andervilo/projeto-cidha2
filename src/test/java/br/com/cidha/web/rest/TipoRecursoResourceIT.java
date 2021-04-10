package br.com.cidha.web.rest;

import br.com.cidha.CidhaApp;
import br.com.cidha.domain.TipoRecurso;
import br.com.cidha.repository.TipoRecursoRepository;
import br.com.cidha.service.TipoRecursoService;
import br.com.cidha.service.dto.TipoRecursoCriteria;
import br.com.cidha.service.TipoRecursoQueryService;

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
 * Integration tests for the {@link TipoRecursoResource} REST controller.
 */
@SpringBootTest(classes = CidhaApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class TipoRecursoResourceIT {

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    @Autowired
    private TipoRecursoRepository tipoRecursoRepository;

    @Autowired
    private TipoRecursoService tipoRecursoService;

    @Autowired
    private TipoRecursoQueryService tipoRecursoQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTipoRecursoMockMvc;

    private TipoRecurso tipoRecurso;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoRecurso createEntity(EntityManager em) {
        TipoRecurso tipoRecurso = new TipoRecurso()
            .descricao(DEFAULT_DESCRICAO);
        return tipoRecurso;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoRecurso createUpdatedEntity(EntityManager em) {
        TipoRecurso tipoRecurso = new TipoRecurso()
            .descricao(UPDATED_DESCRICAO);
        return tipoRecurso;
    }

    @BeforeEach
    public void initTest() {
        tipoRecurso = createEntity(em);
    }

    @Test
    @Transactional
    public void createTipoRecurso() throws Exception {
        int databaseSizeBeforeCreate = tipoRecursoRepository.findAll().size();
        // Create the TipoRecurso
        restTipoRecursoMockMvc.perform(post("/api/tipo-recursos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tipoRecurso)))
            .andExpect(status().isCreated());

        // Validate the TipoRecurso in the database
        List<TipoRecurso> tipoRecursoList = tipoRecursoRepository.findAll();
        assertThat(tipoRecursoList).hasSize(databaseSizeBeforeCreate + 1);
        TipoRecurso testTipoRecurso = tipoRecursoList.get(tipoRecursoList.size() - 1);
        assertThat(testTipoRecurso.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
    }

    @Test
    @Transactional
    public void createTipoRecursoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tipoRecursoRepository.findAll().size();

        // Create the TipoRecurso with an existing ID
        tipoRecurso.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTipoRecursoMockMvc.perform(post("/api/tipo-recursos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tipoRecurso)))
            .andExpect(status().isBadRequest());

        // Validate the TipoRecurso in the database
        List<TipoRecurso> tipoRecursoList = tipoRecursoRepository.findAll();
        assertThat(tipoRecursoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTipoRecursos() throws Exception {
        // Initialize the database
        tipoRecursoRepository.saveAndFlush(tipoRecurso);

        // Get all the tipoRecursoList
        restTipoRecursoMockMvc.perform(get("/api/tipo-recursos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoRecurso.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)));
    }
    
    @Test
    @Transactional
    public void getTipoRecurso() throws Exception {
        // Initialize the database
        tipoRecursoRepository.saveAndFlush(tipoRecurso);

        // Get the tipoRecurso
        restTipoRecursoMockMvc.perform(get("/api/tipo-recursos/{id}", tipoRecurso.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tipoRecurso.getId().intValue()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO));
    }


    @Test
    @Transactional
    public void getTipoRecursosByIdFiltering() throws Exception {
        // Initialize the database
        tipoRecursoRepository.saveAndFlush(tipoRecurso);

        Long id = tipoRecurso.getId();

        defaultTipoRecursoShouldBeFound("id.equals=" + id);
        defaultTipoRecursoShouldNotBeFound("id.notEquals=" + id);

        defaultTipoRecursoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTipoRecursoShouldNotBeFound("id.greaterThan=" + id);

        defaultTipoRecursoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTipoRecursoShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllTipoRecursosByDescricaoIsEqualToSomething() throws Exception {
        // Initialize the database
        tipoRecursoRepository.saveAndFlush(tipoRecurso);

        // Get all the tipoRecursoList where descricao equals to DEFAULT_DESCRICAO
        defaultTipoRecursoShouldBeFound("descricao.equals=" + DEFAULT_DESCRICAO);

        // Get all the tipoRecursoList where descricao equals to UPDATED_DESCRICAO
        defaultTipoRecursoShouldNotBeFound("descricao.equals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllTipoRecursosByDescricaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tipoRecursoRepository.saveAndFlush(tipoRecurso);

        // Get all the tipoRecursoList where descricao not equals to DEFAULT_DESCRICAO
        defaultTipoRecursoShouldNotBeFound("descricao.notEquals=" + DEFAULT_DESCRICAO);

        // Get all the tipoRecursoList where descricao not equals to UPDATED_DESCRICAO
        defaultTipoRecursoShouldBeFound("descricao.notEquals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllTipoRecursosByDescricaoIsInShouldWork() throws Exception {
        // Initialize the database
        tipoRecursoRepository.saveAndFlush(tipoRecurso);

        // Get all the tipoRecursoList where descricao in DEFAULT_DESCRICAO or UPDATED_DESCRICAO
        defaultTipoRecursoShouldBeFound("descricao.in=" + DEFAULT_DESCRICAO + "," + UPDATED_DESCRICAO);

        // Get all the tipoRecursoList where descricao equals to UPDATED_DESCRICAO
        defaultTipoRecursoShouldNotBeFound("descricao.in=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllTipoRecursosByDescricaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        tipoRecursoRepository.saveAndFlush(tipoRecurso);

        // Get all the tipoRecursoList where descricao is not null
        defaultTipoRecursoShouldBeFound("descricao.specified=true");

        // Get all the tipoRecursoList where descricao is null
        defaultTipoRecursoShouldNotBeFound("descricao.specified=false");
    }
                @Test
    @Transactional
    public void getAllTipoRecursosByDescricaoContainsSomething() throws Exception {
        // Initialize the database
        tipoRecursoRepository.saveAndFlush(tipoRecurso);

        // Get all the tipoRecursoList where descricao contains DEFAULT_DESCRICAO
        defaultTipoRecursoShouldBeFound("descricao.contains=" + DEFAULT_DESCRICAO);

        // Get all the tipoRecursoList where descricao contains UPDATED_DESCRICAO
        defaultTipoRecursoShouldNotBeFound("descricao.contains=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllTipoRecursosByDescricaoNotContainsSomething() throws Exception {
        // Initialize the database
        tipoRecursoRepository.saveAndFlush(tipoRecurso);

        // Get all the tipoRecursoList where descricao does not contain DEFAULT_DESCRICAO
        defaultTipoRecursoShouldNotBeFound("descricao.doesNotContain=" + DEFAULT_DESCRICAO);

        // Get all the tipoRecursoList where descricao does not contain UPDATED_DESCRICAO
        defaultTipoRecursoShouldBeFound("descricao.doesNotContain=" + UPDATED_DESCRICAO);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTipoRecursoShouldBeFound(String filter) throws Exception {
        restTipoRecursoMockMvc.perform(get("/api/tipo-recursos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoRecurso.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)));

        // Check, that the count call also returns 1
        restTipoRecursoMockMvc.perform(get("/api/tipo-recursos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTipoRecursoShouldNotBeFound(String filter) throws Exception {
        restTipoRecursoMockMvc.perform(get("/api/tipo-recursos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTipoRecursoMockMvc.perform(get("/api/tipo-recursos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingTipoRecurso() throws Exception {
        // Get the tipoRecurso
        restTipoRecursoMockMvc.perform(get("/api/tipo-recursos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTipoRecurso() throws Exception {
        // Initialize the database
        tipoRecursoService.save(tipoRecurso);

        int databaseSizeBeforeUpdate = tipoRecursoRepository.findAll().size();

        // Update the tipoRecurso
        TipoRecurso updatedTipoRecurso = tipoRecursoRepository.findById(tipoRecurso.getId()).get();
        // Disconnect from session so that the updates on updatedTipoRecurso are not directly saved in db
        em.detach(updatedTipoRecurso);
        updatedTipoRecurso
            .descricao(UPDATED_DESCRICAO);

        restTipoRecursoMockMvc.perform(put("/api/tipo-recursos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTipoRecurso)))
            .andExpect(status().isOk());

        // Validate the TipoRecurso in the database
        List<TipoRecurso> tipoRecursoList = tipoRecursoRepository.findAll();
        assertThat(tipoRecursoList).hasSize(databaseSizeBeforeUpdate);
        TipoRecurso testTipoRecurso = tipoRecursoList.get(tipoRecursoList.size() - 1);
        assertThat(testTipoRecurso.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void updateNonExistingTipoRecurso() throws Exception {
        int databaseSizeBeforeUpdate = tipoRecursoRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTipoRecursoMockMvc.perform(put("/api/tipo-recursos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tipoRecurso)))
            .andExpect(status().isBadRequest());

        // Validate the TipoRecurso in the database
        List<TipoRecurso> tipoRecursoList = tipoRecursoRepository.findAll();
        assertThat(tipoRecursoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTipoRecurso() throws Exception {
        // Initialize the database
        tipoRecursoService.save(tipoRecurso);

        int databaseSizeBeforeDelete = tipoRecursoRepository.findAll().size();

        // Delete the tipoRecurso
        restTipoRecursoMockMvc.perform(delete("/api/tipo-recursos/{id}", tipoRecurso.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TipoRecurso> tipoRecursoList = tipoRecursoRepository.findAll();
        assertThat(tipoRecursoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
