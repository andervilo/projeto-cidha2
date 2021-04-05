package br.com.cidha.web.rest;

import br.com.cidha.CidhaApp;
import br.com.cidha.domain.Quilombo;
import br.com.cidha.domain.Processo;
import br.com.cidha.repository.QuilomboRepository;
import br.com.cidha.service.QuilomboService;
import br.com.cidha.service.dto.QuilomboCriteria;
import br.com.cidha.service.QuilomboQueryService;

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
 * Integration tests for the {@link QuilomboResource} REST controller.
 */
@SpringBootTest(classes = CidhaApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class QuilomboResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    @Autowired
    private QuilomboRepository quilomboRepository;

    @Autowired
    private QuilomboService quilomboService;

    @Autowired
    private QuilomboQueryService quilomboQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restQuilomboMockMvc;

    private Quilombo quilombo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Quilombo createEntity(EntityManager em) {
        Quilombo quilombo = new Quilombo()
            .nome(DEFAULT_NOME);
        return quilombo;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Quilombo createUpdatedEntity(EntityManager em) {
        Quilombo quilombo = new Quilombo()
            .nome(UPDATED_NOME);
        return quilombo;
    }

    @BeforeEach
    public void initTest() {
        quilombo = createEntity(em);
    }

    @Test
    @Transactional
    public void createQuilombo() throws Exception {
        int databaseSizeBeforeCreate = quilomboRepository.findAll().size();
        // Create the Quilombo
        restQuilomboMockMvc.perform(post("/api/quilombos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(quilombo)))
            .andExpect(status().isCreated());

        // Validate the Quilombo in the database
        List<Quilombo> quilomboList = quilomboRepository.findAll();
        assertThat(quilomboList).hasSize(databaseSizeBeforeCreate + 1);
        Quilombo testQuilombo = quilomboList.get(quilomboList.size() - 1);
        assertThat(testQuilombo.getNome()).isEqualTo(DEFAULT_NOME);
    }

    @Test
    @Transactional
    public void createQuilomboWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = quilomboRepository.findAll().size();

        // Create the Quilombo with an existing ID
        quilombo.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restQuilomboMockMvc.perform(post("/api/quilombos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(quilombo)))
            .andExpect(status().isBadRequest());

        // Validate the Quilombo in the database
        List<Quilombo> quilomboList = quilomboRepository.findAll();
        assertThat(quilomboList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllQuilombos() throws Exception {
        // Initialize the database
        quilomboRepository.saveAndFlush(quilombo);

        // Get all the quilomboList
        restQuilomboMockMvc.perform(get("/api/quilombos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(quilombo.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)));
    }
    
    @Test
    @Transactional
    public void getQuilombo() throws Exception {
        // Initialize the database
        quilomboRepository.saveAndFlush(quilombo);

        // Get the quilombo
        restQuilomboMockMvc.perform(get("/api/quilombos/{id}", quilombo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(quilombo.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME));
    }


    @Test
    @Transactional
    public void getQuilombosByIdFiltering() throws Exception {
        // Initialize the database
        quilomboRepository.saveAndFlush(quilombo);

        Long id = quilombo.getId();

        defaultQuilomboShouldBeFound("id.equals=" + id);
        defaultQuilomboShouldNotBeFound("id.notEquals=" + id);

        defaultQuilomboShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultQuilomboShouldNotBeFound("id.greaterThan=" + id);

        defaultQuilomboShouldBeFound("id.lessThanOrEqual=" + id);
        defaultQuilomboShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllQuilombosByNomeIsEqualToSomething() throws Exception {
        // Initialize the database
        quilomboRepository.saveAndFlush(quilombo);

        // Get all the quilomboList where nome equals to DEFAULT_NOME
        defaultQuilomboShouldBeFound("nome.equals=" + DEFAULT_NOME);

        // Get all the quilomboList where nome equals to UPDATED_NOME
        defaultQuilomboShouldNotBeFound("nome.equals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllQuilombosByNomeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        quilomboRepository.saveAndFlush(quilombo);

        // Get all the quilomboList where nome not equals to DEFAULT_NOME
        defaultQuilomboShouldNotBeFound("nome.notEquals=" + DEFAULT_NOME);

        // Get all the quilomboList where nome not equals to UPDATED_NOME
        defaultQuilomboShouldBeFound("nome.notEquals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllQuilombosByNomeIsInShouldWork() throws Exception {
        // Initialize the database
        quilomboRepository.saveAndFlush(quilombo);

        // Get all the quilomboList where nome in DEFAULT_NOME or UPDATED_NOME
        defaultQuilomboShouldBeFound("nome.in=" + DEFAULT_NOME + "," + UPDATED_NOME);

        // Get all the quilomboList where nome equals to UPDATED_NOME
        defaultQuilomboShouldNotBeFound("nome.in=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllQuilombosByNomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        quilomboRepository.saveAndFlush(quilombo);

        // Get all the quilomboList where nome is not null
        defaultQuilomboShouldBeFound("nome.specified=true");

        // Get all the quilomboList where nome is null
        defaultQuilomboShouldNotBeFound("nome.specified=false");
    }
                @Test
    @Transactional
    public void getAllQuilombosByNomeContainsSomething() throws Exception {
        // Initialize the database
        quilomboRepository.saveAndFlush(quilombo);

        // Get all the quilomboList where nome contains DEFAULT_NOME
        defaultQuilomboShouldBeFound("nome.contains=" + DEFAULT_NOME);

        // Get all the quilomboList where nome contains UPDATED_NOME
        defaultQuilomboShouldNotBeFound("nome.contains=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllQuilombosByNomeNotContainsSomething() throws Exception {
        // Initialize the database
        quilomboRepository.saveAndFlush(quilombo);

        // Get all the quilomboList where nome does not contain DEFAULT_NOME
        defaultQuilomboShouldNotBeFound("nome.doesNotContain=" + DEFAULT_NOME);

        // Get all the quilomboList where nome does not contain UPDATED_NOME
        defaultQuilomboShouldBeFound("nome.doesNotContain=" + UPDATED_NOME);
    }


    @Test
    @Transactional
    public void getAllQuilombosByProcessoIsEqualToSomething() throws Exception {
        // Initialize the database
        quilomboRepository.saveAndFlush(quilombo);
        Processo processo = ProcessoResourceIT.createEntity(em);
        em.persist(processo);
        em.flush();
        quilombo.addProcesso(processo);
        quilomboRepository.saveAndFlush(quilombo);
        Long processoId = processo.getId();

        // Get all the quilomboList where processo equals to processoId
        defaultQuilomboShouldBeFound("processoId.equals=" + processoId);

        // Get all the quilomboList where processo equals to processoId + 1
        defaultQuilomboShouldNotBeFound("processoId.equals=" + (processoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultQuilomboShouldBeFound(String filter) throws Exception {
        restQuilomboMockMvc.perform(get("/api/quilombos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(quilombo.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)));

        // Check, that the count call also returns 1
        restQuilomboMockMvc.perform(get("/api/quilombos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultQuilomboShouldNotBeFound(String filter) throws Exception {
        restQuilomboMockMvc.perform(get("/api/quilombos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restQuilomboMockMvc.perform(get("/api/quilombos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingQuilombo() throws Exception {
        // Get the quilombo
        restQuilomboMockMvc.perform(get("/api/quilombos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateQuilombo() throws Exception {
        // Initialize the database
        quilomboService.save(quilombo);

        int databaseSizeBeforeUpdate = quilomboRepository.findAll().size();

        // Update the quilombo
        Quilombo updatedQuilombo = quilomboRepository.findById(quilombo.getId()).get();
        // Disconnect from session so that the updates on updatedQuilombo are not directly saved in db
        em.detach(updatedQuilombo);
        updatedQuilombo
            .nome(UPDATED_NOME);

        restQuilomboMockMvc.perform(put("/api/quilombos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedQuilombo)))
            .andExpect(status().isOk());

        // Validate the Quilombo in the database
        List<Quilombo> quilomboList = quilomboRepository.findAll();
        assertThat(quilomboList).hasSize(databaseSizeBeforeUpdate);
        Quilombo testQuilombo = quilomboList.get(quilomboList.size() - 1);
        assertThat(testQuilombo.getNome()).isEqualTo(UPDATED_NOME);
    }

    @Test
    @Transactional
    public void updateNonExistingQuilombo() throws Exception {
        int databaseSizeBeforeUpdate = quilomboRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQuilomboMockMvc.perform(put("/api/quilombos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(quilombo)))
            .andExpect(status().isBadRequest());

        // Validate the Quilombo in the database
        List<Quilombo> quilomboList = quilomboRepository.findAll();
        assertThat(quilomboList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteQuilombo() throws Exception {
        // Initialize the database
        quilomboService.save(quilombo);

        int databaseSizeBeforeDelete = quilomboRepository.findAll().size();

        // Delete the quilombo
        restQuilomboMockMvc.perform(delete("/api/quilombos/{id}", quilombo.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Quilombo> quilomboList = quilomboRepository.findAll();
        assertThat(quilomboList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
