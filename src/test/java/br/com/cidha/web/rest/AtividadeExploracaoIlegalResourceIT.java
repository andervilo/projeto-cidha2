package br.com.cidha.web.rest;

import br.com.cidha.CidhaApp;
import br.com.cidha.domain.AtividadeExploracaoIlegal;
import br.com.cidha.domain.Processo;
import br.com.cidha.repository.AtividadeExploracaoIlegalRepository;
import br.com.cidha.service.AtividadeExploracaoIlegalService;
import br.com.cidha.service.dto.AtividadeExploracaoIlegalCriteria;
import br.com.cidha.service.AtividadeExploracaoIlegalQueryService;

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
 * Integration tests for the {@link AtividadeExploracaoIlegalResource} REST controller.
 */
@SpringBootTest(classes = CidhaApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class AtividadeExploracaoIlegalResourceIT {

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    @Autowired
    private AtividadeExploracaoIlegalRepository atividadeExploracaoIlegalRepository;

    @Autowired
    private AtividadeExploracaoIlegalService atividadeExploracaoIlegalService;

    @Autowired
    private AtividadeExploracaoIlegalQueryService atividadeExploracaoIlegalQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAtividadeExploracaoIlegalMockMvc;

    private AtividadeExploracaoIlegal atividadeExploracaoIlegal;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AtividadeExploracaoIlegal createEntity(EntityManager em) {
        AtividadeExploracaoIlegal atividadeExploracaoIlegal = new AtividadeExploracaoIlegal()
            .descricao(DEFAULT_DESCRICAO);
        return atividadeExploracaoIlegal;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AtividadeExploracaoIlegal createUpdatedEntity(EntityManager em) {
        AtividadeExploracaoIlegal atividadeExploracaoIlegal = new AtividadeExploracaoIlegal()
            .descricao(UPDATED_DESCRICAO);
        return atividadeExploracaoIlegal;
    }

    @BeforeEach
    public void initTest() {
        atividadeExploracaoIlegal = createEntity(em);
    }

    @Test
    @Transactional
    public void createAtividadeExploracaoIlegal() throws Exception {
        int databaseSizeBeforeCreate = atividadeExploracaoIlegalRepository.findAll().size();
        // Create the AtividadeExploracaoIlegal
        restAtividadeExploracaoIlegalMockMvc.perform(post("/api/atividade-exploracao-ilegals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(atividadeExploracaoIlegal)))
            .andExpect(status().isCreated());

        // Validate the AtividadeExploracaoIlegal in the database
        List<AtividadeExploracaoIlegal> atividadeExploracaoIlegalList = atividadeExploracaoIlegalRepository.findAll();
        assertThat(atividadeExploracaoIlegalList).hasSize(databaseSizeBeforeCreate + 1);
        AtividadeExploracaoIlegal testAtividadeExploracaoIlegal = atividadeExploracaoIlegalList.get(atividadeExploracaoIlegalList.size() - 1);
        assertThat(testAtividadeExploracaoIlegal.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
    }

    @Test
    @Transactional
    public void createAtividadeExploracaoIlegalWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = atividadeExploracaoIlegalRepository.findAll().size();

        // Create the AtividadeExploracaoIlegal with an existing ID
        atividadeExploracaoIlegal.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAtividadeExploracaoIlegalMockMvc.perform(post("/api/atividade-exploracao-ilegals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(atividadeExploracaoIlegal)))
            .andExpect(status().isBadRequest());

        // Validate the AtividadeExploracaoIlegal in the database
        List<AtividadeExploracaoIlegal> atividadeExploracaoIlegalList = atividadeExploracaoIlegalRepository.findAll();
        assertThat(atividadeExploracaoIlegalList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllAtividadeExploracaoIlegals() throws Exception {
        // Initialize the database
        atividadeExploracaoIlegalRepository.saveAndFlush(atividadeExploracaoIlegal);

        // Get all the atividadeExploracaoIlegalList
        restAtividadeExploracaoIlegalMockMvc.perform(get("/api/atividade-exploracao-ilegals?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(atividadeExploracaoIlegal.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)));
    }
    
    @Test
    @Transactional
    public void getAtividadeExploracaoIlegal() throws Exception {
        // Initialize the database
        atividadeExploracaoIlegalRepository.saveAndFlush(atividadeExploracaoIlegal);

        // Get the atividadeExploracaoIlegal
        restAtividadeExploracaoIlegalMockMvc.perform(get("/api/atividade-exploracao-ilegals/{id}", atividadeExploracaoIlegal.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(atividadeExploracaoIlegal.getId().intValue()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO));
    }


    @Test
    @Transactional
    public void getAtividadeExploracaoIlegalsByIdFiltering() throws Exception {
        // Initialize the database
        atividadeExploracaoIlegalRepository.saveAndFlush(atividadeExploracaoIlegal);

        Long id = atividadeExploracaoIlegal.getId();

        defaultAtividadeExploracaoIlegalShouldBeFound("id.equals=" + id);
        defaultAtividadeExploracaoIlegalShouldNotBeFound("id.notEquals=" + id);

        defaultAtividadeExploracaoIlegalShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAtividadeExploracaoIlegalShouldNotBeFound("id.greaterThan=" + id);

        defaultAtividadeExploracaoIlegalShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAtividadeExploracaoIlegalShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllAtividadeExploracaoIlegalsByDescricaoIsEqualToSomething() throws Exception {
        // Initialize the database
        atividadeExploracaoIlegalRepository.saveAndFlush(atividadeExploracaoIlegal);

        // Get all the atividadeExploracaoIlegalList where descricao equals to DEFAULT_DESCRICAO
        defaultAtividadeExploracaoIlegalShouldBeFound("descricao.equals=" + DEFAULT_DESCRICAO);

        // Get all the atividadeExploracaoIlegalList where descricao equals to UPDATED_DESCRICAO
        defaultAtividadeExploracaoIlegalShouldNotBeFound("descricao.equals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllAtividadeExploracaoIlegalsByDescricaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        atividadeExploracaoIlegalRepository.saveAndFlush(atividadeExploracaoIlegal);

        // Get all the atividadeExploracaoIlegalList where descricao not equals to DEFAULT_DESCRICAO
        defaultAtividadeExploracaoIlegalShouldNotBeFound("descricao.notEquals=" + DEFAULT_DESCRICAO);

        // Get all the atividadeExploracaoIlegalList where descricao not equals to UPDATED_DESCRICAO
        defaultAtividadeExploracaoIlegalShouldBeFound("descricao.notEquals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllAtividadeExploracaoIlegalsByDescricaoIsInShouldWork() throws Exception {
        // Initialize the database
        atividadeExploracaoIlegalRepository.saveAndFlush(atividadeExploracaoIlegal);

        // Get all the atividadeExploracaoIlegalList where descricao in DEFAULT_DESCRICAO or UPDATED_DESCRICAO
        defaultAtividadeExploracaoIlegalShouldBeFound("descricao.in=" + DEFAULT_DESCRICAO + "," + UPDATED_DESCRICAO);

        // Get all the atividadeExploracaoIlegalList where descricao equals to UPDATED_DESCRICAO
        defaultAtividadeExploracaoIlegalShouldNotBeFound("descricao.in=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllAtividadeExploracaoIlegalsByDescricaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        atividadeExploracaoIlegalRepository.saveAndFlush(atividadeExploracaoIlegal);

        // Get all the atividadeExploracaoIlegalList where descricao is not null
        defaultAtividadeExploracaoIlegalShouldBeFound("descricao.specified=true");

        // Get all the atividadeExploracaoIlegalList where descricao is null
        defaultAtividadeExploracaoIlegalShouldNotBeFound("descricao.specified=false");
    }
                @Test
    @Transactional
    public void getAllAtividadeExploracaoIlegalsByDescricaoContainsSomething() throws Exception {
        // Initialize the database
        atividadeExploracaoIlegalRepository.saveAndFlush(atividadeExploracaoIlegal);

        // Get all the atividadeExploracaoIlegalList where descricao contains DEFAULT_DESCRICAO
        defaultAtividadeExploracaoIlegalShouldBeFound("descricao.contains=" + DEFAULT_DESCRICAO);

        // Get all the atividadeExploracaoIlegalList where descricao contains UPDATED_DESCRICAO
        defaultAtividadeExploracaoIlegalShouldNotBeFound("descricao.contains=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllAtividadeExploracaoIlegalsByDescricaoNotContainsSomething() throws Exception {
        // Initialize the database
        atividadeExploracaoIlegalRepository.saveAndFlush(atividadeExploracaoIlegal);

        // Get all the atividadeExploracaoIlegalList where descricao does not contain DEFAULT_DESCRICAO
        defaultAtividadeExploracaoIlegalShouldNotBeFound("descricao.doesNotContain=" + DEFAULT_DESCRICAO);

        // Get all the atividadeExploracaoIlegalList where descricao does not contain UPDATED_DESCRICAO
        defaultAtividadeExploracaoIlegalShouldBeFound("descricao.doesNotContain=" + UPDATED_DESCRICAO);
    }


    @Test
    @Transactional
    public void getAllAtividadeExploracaoIlegalsByProcessoIsEqualToSomething() throws Exception {
        // Initialize the database
        atividadeExploracaoIlegalRepository.saveAndFlush(atividadeExploracaoIlegal);
        Processo processo = ProcessoResourceIT.createEntity(em);
        em.persist(processo);
        em.flush();
        atividadeExploracaoIlegal.addProcesso(processo);
        atividadeExploracaoIlegalRepository.saveAndFlush(atividadeExploracaoIlegal);
        Long processoId = processo.getId();

        // Get all the atividadeExploracaoIlegalList where processo equals to processoId
        defaultAtividadeExploracaoIlegalShouldBeFound("processoId.equals=" + processoId);

        // Get all the atividadeExploracaoIlegalList where processo equals to processoId + 1
        defaultAtividadeExploracaoIlegalShouldNotBeFound("processoId.equals=" + (processoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAtividadeExploracaoIlegalShouldBeFound(String filter) throws Exception {
        restAtividadeExploracaoIlegalMockMvc.perform(get("/api/atividade-exploracao-ilegals?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(atividadeExploracaoIlegal.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)));

        // Check, that the count call also returns 1
        restAtividadeExploracaoIlegalMockMvc.perform(get("/api/atividade-exploracao-ilegals/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAtividadeExploracaoIlegalShouldNotBeFound(String filter) throws Exception {
        restAtividadeExploracaoIlegalMockMvc.perform(get("/api/atividade-exploracao-ilegals?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAtividadeExploracaoIlegalMockMvc.perform(get("/api/atividade-exploracao-ilegals/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingAtividadeExploracaoIlegal() throws Exception {
        // Get the atividadeExploracaoIlegal
        restAtividadeExploracaoIlegalMockMvc.perform(get("/api/atividade-exploracao-ilegals/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAtividadeExploracaoIlegal() throws Exception {
        // Initialize the database
        atividadeExploracaoIlegalService.save(atividadeExploracaoIlegal);

        int databaseSizeBeforeUpdate = atividadeExploracaoIlegalRepository.findAll().size();

        // Update the atividadeExploracaoIlegal
        AtividadeExploracaoIlegal updatedAtividadeExploracaoIlegal = atividadeExploracaoIlegalRepository.findById(atividadeExploracaoIlegal.getId()).get();
        // Disconnect from session so that the updates on updatedAtividadeExploracaoIlegal are not directly saved in db
        em.detach(updatedAtividadeExploracaoIlegal);
        updatedAtividadeExploracaoIlegal
            .descricao(UPDATED_DESCRICAO);

        restAtividadeExploracaoIlegalMockMvc.perform(put("/api/atividade-exploracao-ilegals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedAtividadeExploracaoIlegal)))
            .andExpect(status().isOk());

        // Validate the AtividadeExploracaoIlegal in the database
        List<AtividadeExploracaoIlegal> atividadeExploracaoIlegalList = atividadeExploracaoIlegalRepository.findAll();
        assertThat(atividadeExploracaoIlegalList).hasSize(databaseSizeBeforeUpdate);
        AtividadeExploracaoIlegal testAtividadeExploracaoIlegal = atividadeExploracaoIlegalList.get(atividadeExploracaoIlegalList.size() - 1);
        assertThat(testAtividadeExploracaoIlegal.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void updateNonExistingAtividadeExploracaoIlegal() throws Exception {
        int databaseSizeBeforeUpdate = atividadeExploracaoIlegalRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAtividadeExploracaoIlegalMockMvc.perform(put("/api/atividade-exploracao-ilegals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(atividadeExploracaoIlegal)))
            .andExpect(status().isBadRequest());

        // Validate the AtividadeExploracaoIlegal in the database
        List<AtividadeExploracaoIlegal> atividadeExploracaoIlegalList = atividadeExploracaoIlegalRepository.findAll();
        assertThat(atividadeExploracaoIlegalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAtividadeExploracaoIlegal() throws Exception {
        // Initialize the database
        atividadeExploracaoIlegalService.save(atividadeExploracaoIlegal);

        int databaseSizeBeforeDelete = atividadeExploracaoIlegalRepository.findAll().size();

        // Delete the atividadeExploracaoIlegal
        restAtividadeExploracaoIlegalMockMvc.perform(delete("/api/atividade-exploracao-ilegals/{id}", atividadeExploracaoIlegal.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AtividadeExploracaoIlegal> atividadeExploracaoIlegalList = atividadeExploracaoIlegalRepository.findAll();
        assertThat(atividadeExploracaoIlegalList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
