package br.com.cidha.web.rest;

import br.com.cidha.CidhaApp;
import br.com.cidha.domain.EmbargoRespRe;
import br.com.cidha.domain.Processo;
import br.com.cidha.repository.EmbargoRespReRepository;
import br.com.cidha.service.EmbargoRespReService;
import br.com.cidha.service.dto.EmbargoRespReCriteria;
import br.com.cidha.service.EmbargoRespReQueryService;

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
 * Integration tests for the {@link EmbargoRespReResource} REST controller.
 */
@SpringBootTest(classes = CidhaApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class EmbargoRespReResourceIT {

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    @Autowired
    private EmbargoRespReRepository embargoRespReRepository;

    @Autowired
    private EmbargoRespReService embargoRespReService;

    @Autowired
    private EmbargoRespReQueryService embargoRespReQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEmbargoRespReMockMvc;

    private EmbargoRespRe embargoRespRe;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmbargoRespRe createEntity(EntityManager em) {
        EmbargoRespRe embargoRespRe = new EmbargoRespRe()
            .descricao(DEFAULT_DESCRICAO);
        return embargoRespRe;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmbargoRespRe createUpdatedEntity(EntityManager em) {
        EmbargoRespRe embargoRespRe = new EmbargoRespRe()
            .descricao(UPDATED_DESCRICAO);
        return embargoRespRe;
    }

    @BeforeEach
    public void initTest() {
        embargoRespRe = createEntity(em);
    }

    @Test
    @Transactional
    public void createEmbargoRespRe() throws Exception {
        int databaseSizeBeforeCreate = embargoRespReRepository.findAll().size();
        // Create the EmbargoRespRe
        restEmbargoRespReMockMvc.perform(post("/api/embargo-resp-res")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(embargoRespRe)))
            .andExpect(status().isCreated());

        // Validate the EmbargoRespRe in the database
        List<EmbargoRespRe> embargoRespReList = embargoRespReRepository.findAll();
        assertThat(embargoRespReList).hasSize(databaseSizeBeforeCreate + 1);
        EmbargoRespRe testEmbargoRespRe = embargoRespReList.get(embargoRespReList.size() - 1);
        assertThat(testEmbargoRespRe.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
    }

    @Test
    @Transactional
    public void createEmbargoRespReWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = embargoRespReRepository.findAll().size();

        // Create the EmbargoRespRe with an existing ID
        embargoRespRe.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmbargoRespReMockMvc.perform(post("/api/embargo-resp-res")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(embargoRespRe)))
            .andExpect(status().isBadRequest());

        // Validate the EmbargoRespRe in the database
        List<EmbargoRespRe> embargoRespReList = embargoRespReRepository.findAll();
        assertThat(embargoRespReList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllEmbargoRespRes() throws Exception {
        // Initialize the database
        embargoRespReRepository.saveAndFlush(embargoRespRe);

        // Get all the embargoRespReList
        restEmbargoRespReMockMvc.perform(get("/api/embargo-resp-res?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(embargoRespRe.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)));
    }
    
    @Test
    @Transactional
    public void getEmbargoRespRe() throws Exception {
        // Initialize the database
        embargoRespReRepository.saveAndFlush(embargoRespRe);

        // Get the embargoRespRe
        restEmbargoRespReMockMvc.perform(get("/api/embargo-resp-res/{id}", embargoRespRe.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(embargoRespRe.getId().intValue()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO));
    }


    @Test
    @Transactional
    public void getEmbargoRespResByIdFiltering() throws Exception {
        // Initialize the database
        embargoRespReRepository.saveAndFlush(embargoRespRe);

        Long id = embargoRespRe.getId();

        defaultEmbargoRespReShouldBeFound("id.equals=" + id);
        defaultEmbargoRespReShouldNotBeFound("id.notEquals=" + id);

        defaultEmbargoRespReShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultEmbargoRespReShouldNotBeFound("id.greaterThan=" + id);

        defaultEmbargoRespReShouldBeFound("id.lessThanOrEqual=" + id);
        defaultEmbargoRespReShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllEmbargoRespResByDescricaoIsEqualToSomething() throws Exception {
        // Initialize the database
        embargoRespReRepository.saveAndFlush(embargoRespRe);

        // Get all the embargoRespReList where descricao equals to DEFAULT_DESCRICAO
        defaultEmbargoRespReShouldBeFound("descricao.equals=" + DEFAULT_DESCRICAO);

        // Get all the embargoRespReList where descricao equals to UPDATED_DESCRICAO
        defaultEmbargoRespReShouldNotBeFound("descricao.equals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllEmbargoRespResByDescricaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        embargoRespReRepository.saveAndFlush(embargoRespRe);

        // Get all the embargoRespReList where descricao not equals to DEFAULT_DESCRICAO
        defaultEmbargoRespReShouldNotBeFound("descricao.notEquals=" + DEFAULT_DESCRICAO);

        // Get all the embargoRespReList where descricao not equals to UPDATED_DESCRICAO
        defaultEmbargoRespReShouldBeFound("descricao.notEquals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllEmbargoRespResByDescricaoIsInShouldWork() throws Exception {
        // Initialize the database
        embargoRespReRepository.saveAndFlush(embargoRespRe);

        // Get all the embargoRespReList where descricao in DEFAULT_DESCRICAO or UPDATED_DESCRICAO
        defaultEmbargoRespReShouldBeFound("descricao.in=" + DEFAULT_DESCRICAO + "," + UPDATED_DESCRICAO);

        // Get all the embargoRespReList where descricao equals to UPDATED_DESCRICAO
        defaultEmbargoRespReShouldNotBeFound("descricao.in=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllEmbargoRespResByDescricaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        embargoRespReRepository.saveAndFlush(embargoRespRe);

        // Get all the embargoRespReList where descricao is not null
        defaultEmbargoRespReShouldBeFound("descricao.specified=true");

        // Get all the embargoRespReList where descricao is null
        defaultEmbargoRespReShouldNotBeFound("descricao.specified=false");
    }
                @Test
    @Transactional
    public void getAllEmbargoRespResByDescricaoContainsSomething() throws Exception {
        // Initialize the database
        embargoRespReRepository.saveAndFlush(embargoRespRe);

        // Get all the embargoRespReList where descricao contains DEFAULT_DESCRICAO
        defaultEmbargoRespReShouldBeFound("descricao.contains=" + DEFAULT_DESCRICAO);

        // Get all the embargoRespReList where descricao contains UPDATED_DESCRICAO
        defaultEmbargoRespReShouldNotBeFound("descricao.contains=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllEmbargoRespResByDescricaoNotContainsSomething() throws Exception {
        // Initialize the database
        embargoRespReRepository.saveAndFlush(embargoRespRe);

        // Get all the embargoRespReList where descricao does not contain DEFAULT_DESCRICAO
        defaultEmbargoRespReShouldNotBeFound("descricao.doesNotContain=" + DEFAULT_DESCRICAO);

        // Get all the embargoRespReList where descricao does not contain UPDATED_DESCRICAO
        defaultEmbargoRespReShouldBeFound("descricao.doesNotContain=" + UPDATED_DESCRICAO);
    }


    @Test
    @Transactional
    public void getAllEmbargoRespResByProcessoIsEqualToSomething() throws Exception {
        // Initialize the database
        embargoRespReRepository.saveAndFlush(embargoRespRe);
        Processo processo = ProcessoResourceIT.createEntity(em);
        em.persist(processo);
        em.flush();
        embargoRespRe.setProcesso(processo);
        embargoRespReRepository.saveAndFlush(embargoRespRe);
        Long processoId = processo.getId();

        // Get all the embargoRespReList where processo equals to processoId
        defaultEmbargoRespReShouldBeFound("processoId.equals=" + processoId);

        // Get all the embargoRespReList where processo equals to processoId + 1
        defaultEmbargoRespReShouldNotBeFound("processoId.equals=" + (processoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEmbargoRespReShouldBeFound(String filter) throws Exception {
        restEmbargoRespReMockMvc.perform(get("/api/embargo-resp-res?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(embargoRespRe.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)));

        // Check, that the count call also returns 1
        restEmbargoRespReMockMvc.perform(get("/api/embargo-resp-res/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEmbargoRespReShouldNotBeFound(String filter) throws Exception {
        restEmbargoRespReMockMvc.perform(get("/api/embargo-resp-res?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEmbargoRespReMockMvc.perform(get("/api/embargo-resp-res/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingEmbargoRespRe() throws Exception {
        // Get the embargoRespRe
        restEmbargoRespReMockMvc.perform(get("/api/embargo-resp-res/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmbargoRespRe() throws Exception {
        // Initialize the database
        embargoRespReService.save(embargoRespRe);

        int databaseSizeBeforeUpdate = embargoRespReRepository.findAll().size();

        // Update the embargoRespRe
        EmbargoRespRe updatedEmbargoRespRe = embargoRespReRepository.findById(embargoRespRe.getId()).get();
        // Disconnect from session so that the updates on updatedEmbargoRespRe are not directly saved in db
        em.detach(updatedEmbargoRespRe);
        updatedEmbargoRespRe
            .descricao(UPDATED_DESCRICAO);

        restEmbargoRespReMockMvc.perform(put("/api/embargo-resp-res")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedEmbargoRespRe)))
            .andExpect(status().isOk());

        // Validate the EmbargoRespRe in the database
        List<EmbargoRespRe> embargoRespReList = embargoRespReRepository.findAll();
        assertThat(embargoRespReList).hasSize(databaseSizeBeforeUpdate);
        EmbargoRespRe testEmbargoRespRe = embargoRespReList.get(embargoRespReList.size() - 1);
        assertThat(testEmbargoRespRe.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void updateNonExistingEmbargoRespRe() throws Exception {
        int databaseSizeBeforeUpdate = embargoRespReRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmbargoRespReMockMvc.perform(put("/api/embargo-resp-res")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(embargoRespRe)))
            .andExpect(status().isBadRequest());

        // Validate the EmbargoRespRe in the database
        List<EmbargoRespRe> embargoRespReList = embargoRespReRepository.findAll();
        assertThat(embargoRespReList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEmbargoRespRe() throws Exception {
        // Initialize the database
        embargoRespReService.save(embargoRespRe);

        int databaseSizeBeforeDelete = embargoRespReRepository.findAll().size();

        // Delete the embargoRespRe
        restEmbargoRespReMockMvc.perform(delete("/api/embargo-resp-res/{id}", embargoRespRe.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EmbargoRespRe> embargoRespReList = embargoRespReRepository.findAll();
        assertThat(embargoRespReList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
