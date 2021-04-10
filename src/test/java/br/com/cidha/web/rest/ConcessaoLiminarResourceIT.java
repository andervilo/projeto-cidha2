package br.com.cidha.web.rest;

import br.com.cidha.CidhaApp;
import br.com.cidha.domain.ConcessaoLiminar;
import br.com.cidha.domain.Processo;
import br.com.cidha.repository.ConcessaoLiminarRepository;
import br.com.cidha.service.ConcessaoLiminarService;
import br.com.cidha.service.dto.ConcessaoLiminarCriteria;
import br.com.cidha.service.ConcessaoLiminarQueryService;

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
 * Integration tests for the {@link ConcessaoLiminarResource} REST controller.
 */
@SpringBootTest(classes = CidhaApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ConcessaoLiminarResourceIT {

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    @Autowired
    private ConcessaoLiminarRepository concessaoLiminarRepository;

    @Autowired
    private ConcessaoLiminarService concessaoLiminarService;

    @Autowired
    private ConcessaoLiminarQueryService concessaoLiminarQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restConcessaoLiminarMockMvc;

    private ConcessaoLiminar concessaoLiminar;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ConcessaoLiminar createEntity(EntityManager em) {
        ConcessaoLiminar concessaoLiminar = new ConcessaoLiminar()
            .descricao(DEFAULT_DESCRICAO);
        return concessaoLiminar;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ConcessaoLiminar createUpdatedEntity(EntityManager em) {
        ConcessaoLiminar concessaoLiminar = new ConcessaoLiminar()
            .descricao(UPDATED_DESCRICAO);
        return concessaoLiminar;
    }

    @BeforeEach
    public void initTest() {
        concessaoLiminar = createEntity(em);
    }

    @Test
    @Transactional
    public void createConcessaoLiminar() throws Exception {
        int databaseSizeBeforeCreate = concessaoLiminarRepository.findAll().size();
        // Create the ConcessaoLiminar
        restConcessaoLiminarMockMvc.perform(post("/api/concessao-liminars")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(concessaoLiminar)))
            .andExpect(status().isCreated());

        // Validate the ConcessaoLiminar in the database
        List<ConcessaoLiminar> concessaoLiminarList = concessaoLiminarRepository.findAll();
        assertThat(concessaoLiminarList).hasSize(databaseSizeBeforeCreate + 1);
        ConcessaoLiminar testConcessaoLiminar = concessaoLiminarList.get(concessaoLiminarList.size() - 1);
        assertThat(testConcessaoLiminar.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
    }

    @Test
    @Transactional
    public void createConcessaoLiminarWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = concessaoLiminarRepository.findAll().size();

        // Create the ConcessaoLiminar with an existing ID
        concessaoLiminar.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restConcessaoLiminarMockMvc.perform(post("/api/concessao-liminars")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(concessaoLiminar)))
            .andExpect(status().isBadRequest());

        // Validate the ConcessaoLiminar in the database
        List<ConcessaoLiminar> concessaoLiminarList = concessaoLiminarRepository.findAll();
        assertThat(concessaoLiminarList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllConcessaoLiminars() throws Exception {
        // Initialize the database
        concessaoLiminarRepository.saveAndFlush(concessaoLiminar);

        // Get all the concessaoLiminarList
        restConcessaoLiminarMockMvc.perform(get("/api/concessao-liminars?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(concessaoLiminar.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)));
    }
    
    @Test
    @Transactional
    public void getConcessaoLiminar() throws Exception {
        // Initialize the database
        concessaoLiminarRepository.saveAndFlush(concessaoLiminar);

        // Get the concessaoLiminar
        restConcessaoLiminarMockMvc.perform(get("/api/concessao-liminars/{id}", concessaoLiminar.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(concessaoLiminar.getId().intValue()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO));
    }


    @Test
    @Transactional
    public void getConcessaoLiminarsByIdFiltering() throws Exception {
        // Initialize the database
        concessaoLiminarRepository.saveAndFlush(concessaoLiminar);

        Long id = concessaoLiminar.getId();

        defaultConcessaoLiminarShouldBeFound("id.equals=" + id);
        defaultConcessaoLiminarShouldNotBeFound("id.notEquals=" + id);

        defaultConcessaoLiminarShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultConcessaoLiminarShouldNotBeFound("id.greaterThan=" + id);

        defaultConcessaoLiminarShouldBeFound("id.lessThanOrEqual=" + id);
        defaultConcessaoLiminarShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllConcessaoLiminarsByDescricaoIsEqualToSomething() throws Exception {
        // Initialize the database
        concessaoLiminarRepository.saveAndFlush(concessaoLiminar);

        // Get all the concessaoLiminarList where descricao equals to DEFAULT_DESCRICAO
        defaultConcessaoLiminarShouldBeFound("descricao.equals=" + DEFAULT_DESCRICAO);

        // Get all the concessaoLiminarList where descricao equals to UPDATED_DESCRICAO
        defaultConcessaoLiminarShouldNotBeFound("descricao.equals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllConcessaoLiminarsByDescricaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        concessaoLiminarRepository.saveAndFlush(concessaoLiminar);

        // Get all the concessaoLiminarList where descricao not equals to DEFAULT_DESCRICAO
        defaultConcessaoLiminarShouldNotBeFound("descricao.notEquals=" + DEFAULT_DESCRICAO);

        // Get all the concessaoLiminarList where descricao not equals to UPDATED_DESCRICAO
        defaultConcessaoLiminarShouldBeFound("descricao.notEquals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllConcessaoLiminarsByDescricaoIsInShouldWork() throws Exception {
        // Initialize the database
        concessaoLiminarRepository.saveAndFlush(concessaoLiminar);

        // Get all the concessaoLiminarList where descricao in DEFAULT_DESCRICAO or UPDATED_DESCRICAO
        defaultConcessaoLiminarShouldBeFound("descricao.in=" + DEFAULT_DESCRICAO + "," + UPDATED_DESCRICAO);

        // Get all the concessaoLiminarList where descricao equals to UPDATED_DESCRICAO
        defaultConcessaoLiminarShouldNotBeFound("descricao.in=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllConcessaoLiminarsByDescricaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        concessaoLiminarRepository.saveAndFlush(concessaoLiminar);

        // Get all the concessaoLiminarList where descricao is not null
        defaultConcessaoLiminarShouldBeFound("descricao.specified=true");

        // Get all the concessaoLiminarList where descricao is null
        defaultConcessaoLiminarShouldNotBeFound("descricao.specified=false");
    }
                @Test
    @Transactional
    public void getAllConcessaoLiminarsByDescricaoContainsSomething() throws Exception {
        // Initialize the database
        concessaoLiminarRepository.saveAndFlush(concessaoLiminar);

        // Get all the concessaoLiminarList where descricao contains DEFAULT_DESCRICAO
        defaultConcessaoLiminarShouldBeFound("descricao.contains=" + DEFAULT_DESCRICAO);

        // Get all the concessaoLiminarList where descricao contains UPDATED_DESCRICAO
        defaultConcessaoLiminarShouldNotBeFound("descricao.contains=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllConcessaoLiminarsByDescricaoNotContainsSomething() throws Exception {
        // Initialize the database
        concessaoLiminarRepository.saveAndFlush(concessaoLiminar);

        // Get all the concessaoLiminarList where descricao does not contain DEFAULT_DESCRICAO
        defaultConcessaoLiminarShouldNotBeFound("descricao.doesNotContain=" + DEFAULT_DESCRICAO);

        // Get all the concessaoLiminarList where descricao does not contain UPDATED_DESCRICAO
        defaultConcessaoLiminarShouldBeFound("descricao.doesNotContain=" + UPDATED_DESCRICAO);
    }


    @Test
    @Transactional
    public void getAllConcessaoLiminarsByProcessoIsEqualToSomething() throws Exception {
        // Initialize the database
        concessaoLiminarRepository.saveAndFlush(concessaoLiminar);
        Processo processo = ProcessoResourceIT.createEntity(em);
        em.persist(processo);
        em.flush();
        concessaoLiminar.setProcesso(processo);
        concessaoLiminarRepository.saveAndFlush(concessaoLiminar);
        Long processoId = processo.getId();

        // Get all the concessaoLiminarList where processo equals to processoId
        defaultConcessaoLiminarShouldBeFound("processoId.equals=" + processoId);

        // Get all the concessaoLiminarList where processo equals to processoId + 1
        defaultConcessaoLiminarShouldNotBeFound("processoId.equals=" + (processoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultConcessaoLiminarShouldBeFound(String filter) throws Exception {
        restConcessaoLiminarMockMvc.perform(get("/api/concessao-liminars?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(concessaoLiminar.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)));

        // Check, that the count call also returns 1
        restConcessaoLiminarMockMvc.perform(get("/api/concessao-liminars/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultConcessaoLiminarShouldNotBeFound(String filter) throws Exception {
        restConcessaoLiminarMockMvc.perform(get("/api/concessao-liminars?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restConcessaoLiminarMockMvc.perform(get("/api/concessao-liminars/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingConcessaoLiminar() throws Exception {
        // Get the concessaoLiminar
        restConcessaoLiminarMockMvc.perform(get("/api/concessao-liminars/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateConcessaoLiminar() throws Exception {
        // Initialize the database
        concessaoLiminarService.save(concessaoLiminar);

        int databaseSizeBeforeUpdate = concessaoLiminarRepository.findAll().size();

        // Update the concessaoLiminar
        ConcessaoLiminar updatedConcessaoLiminar = concessaoLiminarRepository.findById(concessaoLiminar.getId()).get();
        // Disconnect from session so that the updates on updatedConcessaoLiminar are not directly saved in db
        em.detach(updatedConcessaoLiminar);
        updatedConcessaoLiminar
            .descricao(UPDATED_DESCRICAO);

        restConcessaoLiminarMockMvc.perform(put("/api/concessao-liminars")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedConcessaoLiminar)))
            .andExpect(status().isOk());

        // Validate the ConcessaoLiminar in the database
        List<ConcessaoLiminar> concessaoLiminarList = concessaoLiminarRepository.findAll();
        assertThat(concessaoLiminarList).hasSize(databaseSizeBeforeUpdate);
        ConcessaoLiminar testConcessaoLiminar = concessaoLiminarList.get(concessaoLiminarList.size() - 1);
        assertThat(testConcessaoLiminar.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void updateNonExistingConcessaoLiminar() throws Exception {
        int databaseSizeBeforeUpdate = concessaoLiminarRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConcessaoLiminarMockMvc.perform(put("/api/concessao-liminars")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(concessaoLiminar)))
            .andExpect(status().isBadRequest());

        // Validate the ConcessaoLiminar in the database
        List<ConcessaoLiminar> concessaoLiminarList = concessaoLiminarRepository.findAll();
        assertThat(concessaoLiminarList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteConcessaoLiminar() throws Exception {
        // Initialize the database
        concessaoLiminarService.save(concessaoLiminar);

        int databaseSizeBeforeDelete = concessaoLiminarRepository.findAll().size();

        // Delete the concessaoLiminar
        restConcessaoLiminarMockMvc.perform(delete("/api/concessao-liminars/{id}", concessaoLiminar.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ConcessaoLiminar> concessaoLiminarList = concessaoLiminarRepository.findAll();
        assertThat(concessaoLiminarList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
