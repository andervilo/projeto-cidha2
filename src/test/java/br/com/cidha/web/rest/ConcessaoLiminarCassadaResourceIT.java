package br.com.cidha.web.rest;

import br.com.cidha.CidhaApp;
import br.com.cidha.domain.ConcessaoLiminarCassada;
import br.com.cidha.domain.Processo;
import br.com.cidha.repository.ConcessaoLiminarCassadaRepository;
import br.com.cidha.service.ConcessaoLiminarCassadaService;
import br.com.cidha.service.dto.ConcessaoLiminarCassadaCriteria;
import br.com.cidha.service.ConcessaoLiminarCassadaQueryService;

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
 * Integration tests for the {@link ConcessaoLiminarCassadaResource} REST controller.
 */
@SpringBootTest(classes = CidhaApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ConcessaoLiminarCassadaResourceIT {

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    @Autowired
    private ConcessaoLiminarCassadaRepository concessaoLiminarCassadaRepository;

    @Autowired
    private ConcessaoLiminarCassadaService concessaoLiminarCassadaService;

    @Autowired
    private ConcessaoLiminarCassadaQueryService concessaoLiminarCassadaQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restConcessaoLiminarCassadaMockMvc;

    private ConcessaoLiminarCassada concessaoLiminarCassada;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ConcessaoLiminarCassada createEntity(EntityManager em) {
        ConcessaoLiminarCassada concessaoLiminarCassada = new ConcessaoLiminarCassada()
            .descricao(DEFAULT_DESCRICAO);
        return concessaoLiminarCassada;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ConcessaoLiminarCassada createUpdatedEntity(EntityManager em) {
        ConcessaoLiminarCassada concessaoLiminarCassada = new ConcessaoLiminarCassada()
            .descricao(UPDATED_DESCRICAO);
        return concessaoLiminarCassada;
    }

    @BeforeEach
    public void initTest() {
        concessaoLiminarCassada = createEntity(em);
    }

    @Test
    @Transactional
    public void createConcessaoLiminarCassada() throws Exception {
        int databaseSizeBeforeCreate = concessaoLiminarCassadaRepository.findAll().size();
        // Create the ConcessaoLiminarCassada
        restConcessaoLiminarCassadaMockMvc.perform(post("/api/concessao-liminar-cassadas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(concessaoLiminarCassada)))
            .andExpect(status().isCreated());

        // Validate the ConcessaoLiminarCassada in the database
        List<ConcessaoLiminarCassada> concessaoLiminarCassadaList = concessaoLiminarCassadaRepository.findAll();
        assertThat(concessaoLiminarCassadaList).hasSize(databaseSizeBeforeCreate + 1);
        ConcessaoLiminarCassada testConcessaoLiminarCassada = concessaoLiminarCassadaList.get(concessaoLiminarCassadaList.size() - 1);
        assertThat(testConcessaoLiminarCassada.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
    }

    @Test
    @Transactional
    public void createConcessaoLiminarCassadaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = concessaoLiminarCassadaRepository.findAll().size();

        // Create the ConcessaoLiminarCassada with an existing ID
        concessaoLiminarCassada.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restConcessaoLiminarCassadaMockMvc.perform(post("/api/concessao-liminar-cassadas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(concessaoLiminarCassada)))
            .andExpect(status().isBadRequest());

        // Validate the ConcessaoLiminarCassada in the database
        List<ConcessaoLiminarCassada> concessaoLiminarCassadaList = concessaoLiminarCassadaRepository.findAll();
        assertThat(concessaoLiminarCassadaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllConcessaoLiminarCassadas() throws Exception {
        // Initialize the database
        concessaoLiminarCassadaRepository.saveAndFlush(concessaoLiminarCassada);

        // Get all the concessaoLiminarCassadaList
        restConcessaoLiminarCassadaMockMvc.perform(get("/api/concessao-liminar-cassadas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(concessaoLiminarCassada.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)));
    }
    
    @Test
    @Transactional
    public void getConcessaoLiminarCassada() throws Exception {
        // Initialize the database
        concessaoLiminarCassadaRepository.saveAndFlush(concessaoLiminarCassada);

        // Get the concessaoLiminarCassada
        restConcessaoLiminarCassadaMockMvc.perform(get("/api/concessao-liminar-cassadas/{id}", concessaoLiminarCassada.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(concessaoLiminarCassada.getId().intValue()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO));
    }


    @Test
    @Transactional
    public void getConcessaoLiminarCassadasByIdFiltering() throws Exception {
        // Initialize the database
        concessaoLiminarCassadaRepository.saveAndFlush(concessaoLiminarCassada);

        Long id = concessaoLiminarCassada.getId();

        defaultConcessaoLiminarCassadaShouldBeFound("id.equals=" + id);
        defaultConcessaoLiminarCassadaShouldNotBeFound("id.notEquals=" + id);

        defaultConcessaoLiminarCassadaShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultConcessaoLiminarCassadaShouldNotBeFound("id.greaterThan=" + id);

        defaultConcessaoLiminarCassadaShouldBeFound("id.lessThanOrEqual=" + id);
        defaultConcessaoLiminarCassadaShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllConcessaoLiminarCassadasByDescricaoIsEqualToSomething() throws Exception {
        // Initialize the database
        concessaoLiminarCassadaRepository.saveAndFlush(concessaoLiminarCassada);

        // Get all the concessaoLiminarCassadaList where descricao equals to DEFAULT_DESCRICAO
        defaultConcessaoLiminarCassadaShouldBeFound("descricao.equals=" + DEFAULT_DESCRICAO);

        // Get all the concessaoLiminarCassadaList where descricao equals to UPDATED_DESCRICAO
        defaultConcessaoLiminarCassadaShouldNotBeFound("descricao.equals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllConcessaoLiminarCassadasByDescricaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        concessaoLiminarCassadaRepository.saveAndFlush(concessaoLiminarCassada);

        // Get all the concessaoLiminarCassadaList where descricao not equals to DEFAULT_DESCRICAO
        defaultConcessaoLiminarCassadaShouldNotBeFound("descricao.notEquals=" + DEFAULT_DESCRICAO);

        // Get all the concessaoLiminarCassadaList where descricao not equals to UPDATED_DESCRICAO
        defaultConcessaoLiminarCassadaShouldBeFound("descricao.notEquals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllConcessaoLiminarCassadasByDescricaoIsInShouldWork() throws Exception {
        // Initialize the database
        concessaoLiminarCassadaRepository.saveAndFlush(concessaoLiminarCassada);

        // Get all the concessaoLiminarCassadaList where descricao in DEFAULT_DESCRICAO or UPDATED_DESCRICAO
        defaultConcessaoLiminarCassadaShouldBeFound("descricao.in=" + DEFAULT_DESCRICAO + "," + UPDATED_DESCRICAO);

        // Get all the concessaoLiminarCassadaList where descricao equals to UPDATED_DESCRICAO
        defaultConcessaoLiminarCassadaShouldNotBeFound("descricao.in=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllConcessaoLiminarCassadasByDescricaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        concessaoLiminarCassadaRepository.saveAndFlush(concessaoLiminarCassada);

        // Get all the concessaoLiminarCassadaList where descricao is not null
        defaultConcessaoLiminarCassadaShouldBeFound("descricao.specified=true");

        // Get all the concessaoLiminarCassadaList where descricao is null
        defaultConcessaoLiminarCassadaShouldNotBeFound("descricao.specified=false");
    }
                @Test
    @Transactional
    public void getAllConcessaoLiminarCassadasByDescricaoContainsSomething() throws Exception {
        // Initialize the database
        concessaoLiminarCassadaRepository.saveAndFlush(concessaoLiminarCassada);

        // Get all the concessaoLiminarCassadaList where descricao contains DEFAULT_DESCRICAO
        defaultConcessaoLiminarCassadaShouldBeFound("descricao.contains=" + DEFAULT_DESCRICAO);

        // Get all the concessaoLiminarCassadaList where descricao contains UPDATED_DESCRICAO
        defaultConcessaoLiminarCassadaShouldNotBeFound("descricao.contains=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllConcessaoLiminarCassadasByDescricaoNotContainsSomething() throws Exception {
        // Initialize the database
        concessaoLiminarCassadaRepository.saveAndFlush(concessaoLiminarCassada);

        // Get all the concessaoLiminarCassadaList where descricao does not contain DEFAULT_DESCRICAO
        defaultConcessaoLiminarCassadaShouldNotBeFound("descricao.doesNotContain=" + DEFAULT_DESCRICAO);

        // Get all the concessaoLiminarCassadaList where descricao does not contain UPDATED_DESCRICAO
        defaultConcessaoLiminarCassadaShouldBeFound("descricao.doesNotContain=" + UPDATED_DESCRICAO);
    }


    @Test
    @Transactional
    public void getAllConcessaoLiminarCassadasByProcessoIsEqualToSomething() throws Exception {
        // Initialize the database
        concessaoLiminarCassadaRepository.saveAndFlush(concessaoLiminarCassada);
        Processo processo = ProcessoResourceIT.createEntity(em);
        em.persist(processo);
        em.flush();
        concessaoLiminarCassada.setProcesso(processo);
        concessaoLiminarCassadaRepository.saveAndFlush(concessaoLiminarCassada);
        Long processoId = processo.getId();

        // Get all the concessaoLiminarCassadaList where processo equals to processoId
        defaultConcessaoLiminarCassadaShouldBeFound("processoId.equals=" + processoId);

        // Get all the concessaoLiminarCassadaList where processo equals to processoId + 1
        defaultConcessaoLiminarCassadaShouldNotBeFound("processoId.equals=" + (processoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultConcessaoLiminarCassadaShouldBeFound(String filter) throws Exception {
        restConcessaoLiminarCassadaMockMvc.perform(get("/api/concessao-liminar-cassadas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(concessaoLiminarCassada.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)));

        // Check, that the count call also returns 1
        restConcessaoLiminarCassadaMockMvc.perform(get("/api/concessao-liminar-cassadas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultConcessaoLiminarCassadaShouldNotBeFound(String filter) throws Exception {
        restConcessaoLiminarCassadaMockMvc.perform(get("/api/concessao-liminar-cassadas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restConcessaoLiminarCassadaMockMvc.perform(get("/api/concessao-liminar-cassadas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingConcessaoLiminarCassada() throws Exception {
        // Get the concessaoLiminarCassada
        restConcessaoLiminarCassadaMockMvc.perform(get("/api/concessao-liminar-cassadas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateConcessaoLiminarCassada() throws Exception {
        // Initialize the database
        concessaoLiminarCassadaService.save(concessaoLiminarCassada);

        int databaseSizeBeforeUpdate = concessaoLiminarCassadaRepository.findAll().size();

        // Update the concessaoLiminarCassada
        ConcessaoLiminarCassada updatedConcessaoLiminarCassada = concessaoLiminarCassadaRepository.findById(concessaoLiminarCassada.getId()).get();
        // Disconnect from session so that the updates on updatedConcessaoLiminarCassada are not directly saved in db
        em.detach(updatedConcessaoLiminarCassada);
        updatedConcessaoLiminarCassada
            .descricao(UPDATED_DESCRICAO);

        restConcessaoLiminarCassadaMockMvc.perform(put("/api/concessao-liminar-cassadas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedConcessaoLiminarCassada)))
            .andExpect(status().isOk());

        // Validate the ConcessaoLiminarCassada in the database
        List<ConcessaoLiminarCassada> concessaoLiminarCassadaList = concessaoLiminarCassadaRepository.findAll();
        assertThat(concessaoLiminarCassadaList).hasSize(databaseSizeBeforeUpdate);
        ConcessaoLiminarCassada testConcessaoLiminarCassada = concessaoLiminarCassadaList.get(concessaoLiminarCassadaList.size() - 1);
        assertThat(testConcessaoLiminarCassada.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void updateNonExistingConcessaoLiminarCassada() throws Exception {
        int databaseSizeBeforeUpdate = concessaoLiminarCassadaRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConcessaoLiminarCassadaMockMvc.perform(put("/api/concessao-liminar-cassadas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(concessaoLiminarCassada)))
            .andExpect(status().isBadRequest());

        // Validate the ConcessaoLiminarCassada in the database
        List<ConcessaoLiminarCassada> concessaoLiminarCassadaList = concessaoLiminarCassadaRepository.findAll();
        assertThat(concessaoLiminarCassadaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteConcessaoLiminarCassada() throws Exception {
        // Initialize the database
        concessaoLiminarCassadaService.save(concessaoLiminarCassada);

        int databaseSizeBeforeDelete = concessaoLiminarCassadaRepository.findAll().size();

        // Delete the concessaoLiminarCassada
        restConcessaoLiminarCassadaMockMvc.perform(delete("/api/concessao-liminar-cassadas/{id}", concessaoLiminarCassada.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ConcessaoLiminarCassada> concessaoLiminarCassadaList = concessaoLiminarCassadaRepository.findAll();
        assertThat(concessaoLiminarCassadaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
