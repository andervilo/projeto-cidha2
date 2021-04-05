package br.com.cidha.web.rest;

import br.com.cidha.CidhaApp;
import br.com.cidha.domain.EmbargoRecursoEspecial;
import br.com.cidha.domain.Processo;
import br.com.cidha.repository.EmbargoRecursoEspecialRepository;
import br.com.cidha.service.EmbargoRecursoEspecialService;
import br.com.cidha.service.dto.EmbargoRecursoEspecialCriteria;
import br.com.cidha.service.EmbargoRecursoEspecialQueryService;

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
 * Integration tests for the {@link EmbargoRecursoEspecialResource} REST controller.
 */
@SpringBootTest(classes = CidhaApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class EmbargoRecursoEspecialResourceIT {

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    @Autowired
    private EmbargoRecursoEspecialRepository embargoRecursoEspecialRepository;

    @Autowired
    private EmbargoRecursoEspecialService embargoRecursoEspecialService;

    @Autowired
    private EmbargoRecursoEspecialQueryService embargoRecursoEspecialQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEmbargoRecursoEspecialMockMvc;

    private EmbargoRecursoEspecial embargoRecursoEspecial;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmbargoRecursoEspecial createEntity(EntityManager em) {
        EmbargoRecursoEspecial embargoRecursoEspecial = new EmbargoRecursoEspecial()
            .descricao(DEFAULT_DESCRICAO);
        return embargoRecursoEspecial;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmbargoRecursoEspecial createUpdatedEntity(EntityManager em) {
        EmbargoRecursoEspecial embargoRecursoEspecial = new EmbargoRecursoEspecial()
            .descricao(UPDATED_DESCRICAO);
        return embargoRecursoEspecial;
    }

    @BeforeEach
    public void initTest() {
        embargoRecursoEspecial = createEntity(em);
    }

    @Test
    @Transactional
    public void createEmbargoRecursoEspecial() throws Exception {
        int databaseSizeBeforeCreate = embargoRecursoEspecialRepository.findAll().size();
        // Create the EmbargoRecursoEspecial
        restEmbargoRecursoEspecialMockMvc.perform(post("/api/embargo-recurso-especials")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(embargoRecursoEspecial)))
            .andExpect(status().isCreated());

        // Validate the EmbargoRecursoEspecial in the database
        List<EmbargoRecursoEspecial> embargoRecursoEspecialList = embargoRecursoEspecialRepository.findAll();
        assertThat(embargoRecursoEspecialList).hasSize(databaseSizeBeforeCreate + 1);
        EmbargoRecursoEspecial testEmbargoRecursoEspecial = embargoRecursoEspecialList.get(embargoRecursoEspecialList.size() - 1);
        assertThat(testEmbargoRecursoEspecial.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
    }

    @Test
    @Transactional
    public void createEmbargoRecursoEspecialWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = embargoRecursoEspecialRepository.findAll().size();

        // Create the EmbargoRecursoEspecial with an existing ID
        embargoRecursoEspecial.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmbargoRecursoEspecialMockMvc.perform(post("/api/embargo-recurso-especials")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(embargoRecursoEspecial)))
            .andExpect(status().isBadRequest());

        // Validate the EmbargoRecursoEspecial in the database
        List<EmbargoRecursoEspecial> embargoRecursoEspecialList = embargoRecursoEspecialRepository.findAll();
        assertThat(embargoRecursoEspecialList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllEmbargoRecursoEspecials() throws Exception {
        // Initialize the database
        embargoRecursoEspecialRepository.saveAndFlush(embargoRecursoEspecial);

        // Get all the embargoRecursoEspecialList
        restEmbargoRecursoEspecialMockMvc.perform(get("/api/embargo-recurso-especials?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(embargoRecursoEspecial.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)));
    }
    
    @Test
    @Transactional
    public void getEmbargoRecursoEspecial() throws Exception {
        // Initialize the database
        embargoRecursoEspecialRepository.saveAndFlush(embargoRecursoEspecial);

        // Get the embargoRecursoEspecial
        restEmbargoRecursoEspecialMockMvc.perform(get("/api/embargo-recurso-especials/{id}", embargoRecursoEspecial.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(embargoRecursoEspecial.getId().intValue()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO));
    }


    @Test
    @Transactional
    public void getEmbargoRecursoEspecialsByIdFiltering() throws Exception {
        // Initialize the database
        embargoRecursoEspecialRepository.saveAndFlush(embargoRecursoEspecial);

        Long id = embargoRecursoEspecial.getId();

        defaultEmbargoRecursoEspecialShouldBeFound("id.equals=" + id);
        defaultEmbargoRecursoEspecialShouldNotBeFound("id.notEquals=" + id);

        defaultEmbargoRecursoEspecialShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultEmbargoRecursoEspecialShouldNotBeFound("id.greaterThan=" + id);

        defaultEmbargoRecursoEspecialShouldBeFound("id.lessThanOrEqual=" + id);
        defaultEmbargoRecursoEspecialShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllEmbargoRecursoEspecialsByDescricaoIsEqualToSomething() throws Exception {
        // Initialize the database
        embargoRecursoEspecialRepository.saveAndFlush(embargoRecursoEspecial);

        // Get all the embargoRecursoEspecialList where descricao equals to DEFAULT_DESCRICAO
        defaultEmbargoRecursoEspecialShouldBeFound("descricao.equals=" + DEFAULT_DESCRICAO);

        // Get all the embargoRecursoEspecialList where descricao equals to UPDATED_DESCRICAO
        defaultEmbargoRecursoEspecialShouldNotBeFound("descricao.equals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllEmbargoRecursoEspecialsByDescricaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        embargoRecursoEspecialRepository.saveAndFlush(embargoRecursoEspecial);

        // Get all the embargoRecursoEspecialList where descricao not equals to DEFAULT_DESCRICAO
        defaultEmbargoRecursoEspecialShouldNotBeFound("descricao.notEquals=" + DEFAULT_DESCRICAO);

        // Get all the embargoRecursoEspecialList where descricao not equals to UPDATED_DESCRICAO
        defaultEmbargoRecursoEspecialShouldBeFound("descricao.notEquals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllEmbargoRecursoEspecialsByDescricaoIsInShouldWork() throws Exception {
        // Initialize the database
        embargoRecursoEspecialRepository.saveAndFlush(embargoRecursoEspecial);

        // Get all the embargoRecursoEspecialList where descricao in DEFAULT_DESCRICAO or UPDATED_DESCRICAO
        defaultEmbargoRecursoEspecialShouldBeFound("descricao.in=" + DEFAULT_DESCRICAO + "," + UPDATED_DESCRICAO);

        // Get all the embargoRecursoEspecialList where descricao equals to UPDATED_DESCRICAO
        defaultEmbargoRecursoEspecialShouldNotBeFound("descricao.in=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllEmbargoRecursoEspecialsByDescricaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        embargoRecursoEspecialRepository.saveAndFlush(embargoRecursoEspecial);

        // Get all the embargoRecursoEspecialList where descricao is not null
        defaultEmbargoRecursoEspecialShouldBeFound("descricao.specified=true");

        // Get all the embargoRecursoEspecialList where descricao is null
        defaultEmbargoRecursoEspecialShouldNotBeFound("descricao.specified=false");
    }
                @Test
    @Transactional
    public void getAllEmbargoRecursoEspecialsByDescricaoContainsSomething() throws Exception {
        // Initialize the database
        embargoRecursoEspecialRepository.saveAndFlush(embargoRecursoEspecial);

        // Get all the embargoRecursoEspecialList where descricao contains DEFAULT_DESCRICAO
        defaultEmbargoRecursoEspecialShouldBeFound("descricao.contains=" + DEFAULT_DESCRICAO);

        // Get all the embargoRecursoEspecialList where descricao contains UPDATED_DESCRICAO
        defaultEmbargoRecursoEspecialShouldNotBeFound("descricao.contains=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllEmbargoRecursoEspecialsByDescricaoNotContainsSomething() throws Exception {
        // Initialize the database
        embargoRecursoEspecialRepository.saveAndFlush(embargoRecursoEspecial);

        // Get all the embargoRecursoEspecialList where descricao does not contain DEFAULT_DESCRICAO
        defaultEmbargoRecursoEspecialShouldNotBeFound("descricao.doesNotContain=" + DEFAULT_DESCRICAO);

        // Get all the embargoRecursoEspecialList where descricao does not contain UPDATED_DESCRICAO
        defaultEmbargoRecursoEspecialShouldBeFound("descricao.doesNotContain=" + UPDATED_DESCRICAO);
    }


    @Test
    @Transactional
    public void getAllEmbargoRecursoEspecialsByProcessoIsEqualToSomething() throws Exception {
        // Initialize the database
        embargoRecursoEspecialRepository.saveAndFlush(embargoRecursoEspecial);
        Processo processo = ProcessoResourceIT.createEntity(em);
        em.persist(processo);
        em.flush();
        embargoRecursoEspecial.setProcesso(processo);
        embargoRecursoEspecialRepository.saveAndFlush(embargoRecursoEspecial);
        Long processoId = processo.getId();

        // Get all the embargoRecursoEspecialList where processo equals to processoId
        defaultEmbargoRecursoEspecialShouldBeFound("processoId.equals=" + processoId);

        // Get all the embargoRecursoEspecialList where processo equals to processoId + 1
        defaultEmbargoRecursoEspecialShouldNotBeFound("processoId.equals=" + (processoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEmbargoRecursoEspecialShouldBeFound(String filter) throws Exception {
        restEmbargoRecursoEspecialMockMvc.perform(get("/api/embargo-recurso-especials?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(embargoRecursoEspecial.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)));

        // Check, that the count call also returns 1
        restEmbargoRecursoEspecialMockMvc.perform(get("/api/embargo-recurso-especials/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEmbargoRecursoEspecialShouldNotBeFound(String filter) throws Exception {
        restEmbargoRecursoEspecialMockMvc.perform(get("/api/embargo-recurso-especials?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEmbargoRecursoEspecialMockMvc.perform(get("/api/embargo-recurso-especials/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingEmbargoRecursoEspecial() throws Exception {
        // Get the embargoRecursoEspecial
        restEmbargoRecursoEspecialMockMvc.perform(get("/api/embargo-recurso-especials/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmbargoRecursoEspecial() throws Exception {
        // Initialize the database
        embargoRecursoEspecialService.save(embargoRecursoEspecial);

        int databaseSizeBeforeUpdate = embargoRecursoEspecialRepository.findAll().size();

        // Update the embargoRecursoEspecial
        EmbargoRecursoEspecial updatedEmbargoRecursoEspecial = embargoRecursoEspecialRepository.findById(embargoRecursoEspecial.getId()).get();
        // Disconnect from session so that the updates on updatedEmbargoRecursoEspecial are not directly saved in db
        em.detach(updatedEmbargoRecursoEspecial);
        updatedEmbargoRecursoEspecial
            .descricao(UPDATED_DESCRICAO);

        restEmbargoRecursoEspecialMockMvc.perform(put("/api/embargo-recurso-especials")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedEmbargoRecursoEspecial)))
            .andExpect(status().isOk());

        // Validate the EmbargoRecursoEspecial in the database
        List<EmbargoRecursoEspecial> embargoRecursoEspecialList = embargoRecursoEspecialRepository.findAll();
        assertThat(embargoRecursoEspecialList).hasSize(databaseSizeBeforeUpdate);
        EmbargoRecursoEspecial testEmbargoRecursoEspecial = embargoRecursoEspecialList.get(embargoRecursoEspecialList.size() - 1);
        assertThat(testEmbargoRecursoEspecial.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void updateNonExistingEmbargoRecursoEspecial() throws Exception {
        int databaseSizeBeforeUpdate = embargoRecursoEspecialRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmbargoRecursoEspecialMockMvc.perform(put("/api/embargo-recurso-especials")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(embargoRecursoEspecial)))
            .andExpect(status().isBadRequest());

        // Validate the EmbargoRecursoEspecial in the database
        List<EmbargoRecursoEspecial> embargoRecursoEspecialList = embargoRecursoEspecialRepository.findAll();
        assertThat(embargoRecursoEspecialList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEmbargoRecursoEspecial() throws Exception {
        // Initialize the database
        embargoRecursoEspecialService.save(embargoRecursoEspecial);

        int databaseSizeBeforeDelete = embargoRecursoEspecialRepository.findAll().size();

        // Delete the embargoRecursoEspecial
        restEmbargoRecursoEspecialMockMvc.perform(delete("/api/embargo-recurso-especials/{id}", embargoRecursoEspecial.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EmbargoRecursoEspecial> embargoRecursoEspecialList = embargoRecursoEspecialRepository.findAll();
        assertThat(embargoRecursoEspecialList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
