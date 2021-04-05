package br.com.cidha.web.rest;

import br.com.cidha.CidhaApp;
import br.com.cidha.domain.Conflito;
import br.com.cidha.domain.ProcessoConflito;
import br.com.cidha.repository.ConflitoRepository;
import br.com.cidha.service.ConflitoService;
import br.com.cidha.service.dto.ConflitoCriteria;
import br.com.cidha.service.ConflitoQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ConflitoResource} REST controller.
 */
@SpringBootTest(classes = CidhaApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ConflitoResourceIT {

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    @Autowired
    private ConflitoRepository conflitoRepository;

    @Autowired
    private ConflitoService conflitoService;

    @Autowired
    private ConflitoQueryService conflitoQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restConflitoMockMvc;

    private Conflito conflito;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Conflito createEntity(EntityManager em) {
        Conflito conflito = new Conflito()
            .descricao(DEFAULT_DESCRICAO);
        return conflito;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Conflito createUpdatedEntity(EntityManager em) {
        Conflito conflito = new Conflito()
            .descricao(UPDATED_DESCRICAO);
        return conflito;
    }

    @BeforeEach
    public void initTest() {
        conflito = createEntity(em);
    }

    @Test
    @Transactional
    public void createConflito() throws Exception {
        int databaseSizeBeforeCreate = conflitoRepository.findAll().size();
        // Create the Conflito
        restConflitoMockMvc.perform(post("/api/conflitos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(conflito)))
            .andExpect(status().isCreated());

        // Validate the Conflito in the database
        List<Conflito> conflitoList = conflitoRepository.findAll();
        assertThat(conflitoList).hasSize(databaseSizeBeforeCreate + 1);
        Conflito testConflito = conflitoList.get(conflitoList.size() - 1);
        assertThat(testConflito.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
    }

    @Test
    @Transactional
    public void createConflitoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = conflitoRepository.findAll().size();

        // Create the Conflito with an existing ID
        conflito.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restConflitoMockMvc.perform(post("/api/conflitos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(conflito)))
            .andExpect(status().isBadRequest());

        // Validate the Conflito in the database
        List<Conflito> conflitoList = conflitoRepository.findAll();
        assertThat(conflitoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllConflitos() throws Exception {
        // Initialize the database
        conflitoRepository.saveAndFlush(conflito);

        // Get all the conflitoList
        restConflitoMockMvc.perform(get("/api/conflitos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(conflito.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())));
    }
    
    @Test
    @Transactional
    public void getConflito() throws Exception {
        // Initialize the database
        conflitoRepository.saveAndFlush(conflito);

        // Get the conflito
        restConflitoMockMvc.perform(get("/api/conflitos/{id}", conflito.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(conflito.getId().intValue()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()));
    }


    @Test
    @Transactional
    public void getConflitosByIdFiltering() throws Exception {
        // Initialize the database
        conflitoRepository.saveAndFlush(conflito);

        Long id = conflito.getId();

        defaultConflitoShouldBeFound("id.equals=" + id);
        defaultConflitoShouldNotBeFound("id.notEquals=" + id);

        defaultConflitoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultConflitoShouldNotBeFound("id.greaterThan=" + id);

        defaultConflitoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultConflitoShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllConflitosByProcessoConflitoIsEqualToSomething() throws Exception {
        // Initialize the database
        conflitoRepository.saveAndFlush(conflito);
        ProcessoConflito processoConflito = ProcessoConflitoResourceIT.createEntity(em);
        em.persist(processoConflito);
        em.flush();
        conflito.setProcessoConflito(processoConflito);
        conflitoRepository.saveAndFlush(conflito);
        Long processoConflitoId = processoConflito.getId();

        // Get all the conflitoList where processoConflito equals to processoConflitoId
        defaultConflitoShouldBeFound("processoConflitoId.equals=" + processoConflitoId);

        // Get all the conflitoList where processoConflito equals to processoConflitoId + 1
        defaultConflitoShouldNotBeFound("processoConflitoId.equals=" + (processoConflitoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultConflitoShouldBeFound(String filter) throws Exception {
        restConflitoMockMvc.perform(get("/api/conflitos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(conflito.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())));

        // Check, that the count call also returns 1
        restConflitoMockMvc.perform(get("/api/conflitos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultConflitoShouldNotBeFound(String filter) throws Exception {
        restConflitoMockMvc.perform(get("/api/conflitos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restConflitoMockMvc.perform(get("/api/conflitos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingConflito() throws Exception {
        // Get the conflito
        restConflitoMockMvc.perform(get("/api/conflitos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateConflito() throws Exception {
        // Initialize the database
        conflitoService.save(conflito);

        int databaseSizeBeforeUpdate = conflitoRepository.findAll().size();

        // Update the conflito
        Conflito updatedConflito = conflitoRepository.findById(conflito.getId()).get();
        // Disconnect from session so that the updates on updatedConflito are not directly saved in db
        em.detach(updatedConflito);
        updatedConflito
            .descricao(UPDATED_DESCRICAO);

        restConflitoMockMvc.perform(put("/api/conflitos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedConflito)))
            .andExpect(status().isOk());

        // Validate the Conflito in the database
        List<Conflito> conflitoList = conflitoRepository.findAll();
        assertThat(conflitoList).hasSize(databaseSizeBeforeUpdate);
        Conflito testConflito = conflitoList.get(conflitoList.size() - 1);
        assertThat(testConflito.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void updateNonExistingConflito() throws Exception {
        int databaseSizeBeforeUpdate = conflitoRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConflitoMockMvc.perform(put("/api/conflitos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(conflito)))
            .andExpect(status().isBadRequest());

        // Validate the Conflito in the database
        List<Conflito> conflitoList = conflitoRepository.findAll();
        assertThat(conflitoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteConflito() throws Exception {
        // Initialize the database
        conflitoService.save(conflito);

        int databaseSizeBeforeDelete = conflitoRepository.findAll().size();

        // Delete the conflito
        restConflitoMockMvc.perform(delete("/api/conflitos/{id}", conflito.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Conflito> conflitoList = conflitoRepository.findAll();
        assertThat(conflitoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
