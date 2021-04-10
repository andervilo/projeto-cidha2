package br.com.cidha.web.rest;

import br.com.cidha.CidhaApp;
import br.com.cidha.domain.TipoData;
import br.com.cidha.repository.TipoDataRepository;
import br.com.cidha.service.TipoDataService;
import br.com.cidha.service.dto.TipoDataCriteria;
import br.com.cidha.service.TipoDataQueryService;

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
 * Integration tests for the {@link TipoDataResource} REST controller.
 */
@SpringBootTest(classes = CidhaApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class TipoDataResourceIT {

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    @Autowired
    private TipoDataRepository tipoDataRepository;

    @Autowired
    private TipoDataService tipoDataService;

    @Autowired
    private TipoDataQueryService tipoDataQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTipoDataMockMvc;

    private TipoData tipoData;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoData createEntity(EntityManager em) {
        TipoData tipoData = new TipoData()
            .descricao(DEFAULT_DESCRICAO);
        return tipoData;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoData createUpdatedEntity(EntityManager em) {
        TipoData tipoData = new TipoData()
            .descricao(UPDATED_DESCRICAO);
        return tipoData;
    }

    @BeforeEach
    public void initTest() {
        tipoData = createEntity(em);
    }

    @Test
    @Transactional
    public void createTipoData() throws Exception {
        int databaseSizeBeforeCreate = tipoDataRepository.findAll().size();
        // Create the TipoData
        restTipoDataMockMvc.perform(post("/api/tipo-data")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tipoData)))
            .andExpect(status().isCreated());

        // Validate the TipoData in the database
        List<TipoData> tipoDataList = tipoDataRepository.findAll();
        assertThat(tipoDataList).hasSize(databaseSizeBeforeCreate + 1);
        TipoData testTipoData = tipoDataList.get(tipoDataList.size() - 1);
        assertThat(testTipoData.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
    }

    @Test
    @Transactional
    public void createTipoDataWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tipoDataRepository.findAll().size();

        // Create the TipoData with an existing ID
        tipoData.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTipoDataMockMvc.perform(post("/api/tipo-data")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tipoData)))
            .andExpect(status().isBadRequest());

        // Validate the TipoData in the database
        List<TipoData> tipoDataList = tipoDataRepository.findAll();
        assertThat(tipoDataList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTipoData() throws Exception {
        // Initialize the database
        tipoDataRepository.saveAndFlush(tipoData);

        // Get all the tipoDataList
        restTipoDataMockMvc.perform(get("/api/tipo-data?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoData.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)));
    }
    
    @Test
    @Transactional
    public void getTipoData() throws Exception {
        // Initialize the database
        tipoDataRepository.saveAndFlush(tipoData);

        // Get the tipoData
        restTipoDataMockMvc.perform(get("/api/tipo-data/{id}", tipoData.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tipoData.getId().intValue()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO));
    }


    @Test
    @Transactional
    public void getTipoDataByIdFiltering() throws Exception {
        // Initialize the database
        tipoDataRepository.saveAndFlush(tipoData);

        Long id = tipoData.getId();

        defaultTipoDataShouldBeFound("id.equals=" + id);
        defaultTipoDataShouldNotBeFound("id.notEquals=" + id);

        defaultTipoDataShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTipoDataShouldNotBeFound("id.greaterThan=" + id);

        defaultTipoDataShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTipoDataShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllTipoDataByDescricaoIsEqualToSomething() throws Exception {
        // Initialize the database
        tipoDataRepository.saveAndFlush(tipoData);

        // Get all the tipoDataList where descricao equals to DEFAULT_DESCRICAO
        defaultTipoDataShouldBeFound("descricao.equals=" + DEFAULT_DESCRICAO);

        // Get all the tipoDataList where descricao equals to UPDATED_DESCRICAO
        defaultTipoDataShouldNotBeFound("descricao.equals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllTipoDataByDescricaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tipoDataRepository.saveAndFlush(tipoData);

        // Get all the tipoDataList where descricao not equals to DEFAULT_DESCRICAO
        defaultTipoDataShouldNotBeFound("descricao.notEquals=" + DEFAULT_DESCRICAO);

        // Get all the tipoDataList where descricao not equals to UPDATED_DESCRICAO
        defaultTipoDataShouldBeFound("descricao.notEquals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllTipoDataByDescricaoIsInShouldWork() throws Exception {
        // Initialize the database
        tipoDataRepository.saveAndFlush(tipoData);

        // Get all the tipoDataList where descricao in DEFAULT_DESCRICAO or UPDATED_DESCRICAO
        defaultTipoDataShouldBeFound("descricao.in=" + DEFAULT_DESCRICAO + "," + UPDATED_DESCRICAO);

        // Get all the tipoDataList where descricao equals to UPDATED_DESCRICAO
        defaultTipoDataShouldNotBeFound("descricao.in=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllTipoDataByDescricaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        tipoDataRepository.saveAndFlush(tipoData);

        // Get all the tipoDataList where descricao is not null
        defaultTipoDataShouldBeFound("descricao.specified=true");

        // Get all the tipoDataList where descricao is null
        defaultTipoDataShouldNotBeFound("descricao.specified=false");
    }
                @Test
    @Transactional
    public void getAllTipoDataByDescricaoContainsSomething() throws Exception {
        // Initialize the database
        tipoDataRepository.saveAndFlush(tipoData);

        // Get all the tipoDataList where descricao contains DEFAULT_DESCRICAO
        defaultTipoDataShouldBeFound("descricao.contains=" + DEFAULT_DESCRICAO);

        // Get all the tipoDataList where descricao contains UPDATED_DESCRICAO
        defaultTipoDataShouldNotBeFound("descricao.contains=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllTipoDataByDescricaoNotContainsSomething() throws Exception {
        // Initialize the database
        tipoDataRepository.saveAndFlush(tipoData);

        // Get all the tipoDataList where descricao does not contain DEFAULT_DESCRICAO
        defaultTipoDataShouldNotBeFound("descricao.doesNotContain=" + DEFAULT_DESCRICAO);

        // Get all the tipoDataList where descricao does not contain UPDATED_DESCRICAO
        defaultTipoDataShouldBeFound("descricao.doesNotContain=" + UPDATED_DESCRICAO);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTipoDataShouldBeFound(String filter) throws Exception {
        restTipoDataMockMvc.perform(get("/api/tipo-data?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoData.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)));

        // Check, that the count call also returns 1
        restTipoDataMockMvc.perform(get("/api/tipo-data/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTipoDataShouldNotBeFound(String filter) throws Exception {
        restTipoDataMockMvc.perform(get("/api/tipo-data?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTipoDataMockMvc.perform(get("/api/tipo-data/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingTipoData() throws Exception {
        // Get the tipoData
        restTipoDataMockMvc.perform(get("/api/tipo-data/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTipoData() throws Exception {
        // Initialize the database
        tipoDataService.save(tipoData);

        int databaseSizeBeforeUpdate = tipoDataRepository.findAll().size();

        // Update the tipoData
        TipoData updatedTipoData = tipoDataRepository.findById(tipoData.getId()).get();
        // Disconnect from session so that the updates on updatedTipoData are not directly saved in db
        em.detach(updatedTipoData);
        updatedTipoData
            .descricao(UPDATED_DESCRICAO);

        restTipoDataMockMvc.perform(put("/api/tipo-data")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTipoData)))
            .andExpect(status().isOk());

        // Validate the TipoData in the database
        List<TipoData> tipoDataList = tipoDataRepository.findAll();
        assertThat(tipoDataList).hasSize(databaseSizeBeforeUpdate);
        TipoData testTipoData = tipoDataList.get(tipoDataList.size() - 1);
        assertThat(testTipoData.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void updateNonExistingTipoData() throws Exception {
        int databaseSizeBeforeUpdate = tipoDataRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTipoDataMockMvc.perform(put("/api/tipo-data")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tipoData)))
            .andExpect(status().isBadRequest());

        // Validate the TipoData in the database
        List<TipoData> tipoDataList = tipoDataRepository.findAll();
        assertThat(tipoDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTipoData() throws Exception {
        // Initialize the database
        tipoDataService.save(tipoData);

        int databaseSizeBeforeDelete = tipoDataRepository.findAll().size();

        // Delete the tipoData
        restTipoDataMockMvc.perform(delete("/api/tipo-data/{id}", tipoData.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TipoData> tipoDataList = tipoDataRepository.findAll();
        assertThat(tipoDataList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
