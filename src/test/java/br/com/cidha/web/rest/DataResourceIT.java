package br.com.cidha.web.rest;

import br.com.cidha.CidhaApp;
import br.com.cidha.domain.Data;
import br.com.cidha.domain.TipoData;
import br.com.cidha.domain.Processo;
import br.com.cidha.repository.DataRepository;
import br.com.cidha.service.DataService;
import br.com.cidha.service.dto.DataCriteria;
import br.com.cidha.service.DataQueryService;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link DataResource} REST controller.
 */
@SpringBootTest(classes = CidhaApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class DataResourceIT {

    private static final LocalDate DEFAULT_DATA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATA = LocalDate.ofEpochDay(-1L);

    @Autowired
    private DataRepository dataRepository;

    @Autowired
    private DataService dataService;

    @Autowired
    private DataQueryService dataQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDataMockMvc;

    private Data data;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Data createEntity(EntityManager em) {
        Data data = new Data()
            .data(DEFAULT_DATA);
        return data;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Data createUpdatedEntity(EntityManager em) {
        Data data = new Data()
            .data(UPDATED_DATA);
        return data;
    }

    @BeforeEach
    public void initTest() {
        data = createEntity(em);
    }

    @Test
    @Transactional
    public void createData() throws Exception {
        int databaseSizeBeforeCreate = dataRepository.findAll().size();
        // Create the Data
        restDataMockMvc.perform(post("/api/data")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(data)))
            .andExpect(status().isCreated());

        // Validate the Data in the database
        List<Data> dataList = dataRepository.findAll();
        assertThat(dataList).hasSize(databaseSizeBeforeCreate + 1);
        Data testData = dataList.get(dataList.size() - 1);
        assertThat(testData.getData()).isEqualTo(DEFAULT_DATA);
    }

    @Test
    @Transactional
    public void createDataWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dataRepository.findAll().size();

        // Create the Data with an existing ID
        data.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDataMockMvc.perform(post("/api/data")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(data)))
            .andExpect(status().isBadRequest());

        // Validate the Data in the database
        List<Data> dataList = dataRepository.findAll();
        assertThat(dataList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllData() throws Exception {
        // Initialize the database
        dataRepository.saveAndFlush(data);

        // Get all the dataList
        restDataMockMvc.perform(get("/api/data?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(data.getId().intValue())))
            .andExpect(jsonPath("$.[*].data").value(hasItem(DEFAULT_DATA.toString())));
    }
    
    @Test
    @Transactional
    public void getData() throws Exception {
        // Initialize the database
        dataRepository.saveAndFlush(data);

        // Get the data
        restDataMockMvc.perform(get("/api/data/{id}", data.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(data.getId().intValue()))
            .andExpect(jsonPath("$.data").value(DEFAULT_DATA.toString()));
    }


    @Test
    @Transactional
    public void getDataByIdFiltering() throws Exception {
        // Initialize the database
        dataRepository.saveAndFlush(data);

        Long id = data.getId();

        defaultDataShouldBeFound("id.equals=" + id);
        defaultDataShouldNotBeFound("id.notEquals=" + id);

        defaultDataShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDataShouldNotBeFound("id.greaterThan=" + id);

        defaultDataShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDataShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllDataByDataIsEqualToSomething() throws Exception {
        // Initialize the database
        dataRepository.saveAndFlush(data);

        // Get all the dataList where data equals to DEFAULT_DATA
        defaultDataShouldBeFound("data.equals=" + DEFAULT_DATA);

        // Get all the dataList where data equals to UPDATED_DATA
        defaultDataShouldNotBeFound("data.equals=" + UPDATED_DATA);
    }

    @Test
    @Transactional
    public void getAllDataByDataIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dataRepository.saveAndFlush(data);

        // Get all the dataList where data not equals to DEFAULT_DATA
        defaultDataShouldNotBeFound("data.notEquals=" + DEFAULT_DATA);

        // Get all the dataList where data not equals to UPDATED_DATA
        defaultDataShouldBeFound("data.notEquals=" + UPDATED_DATA);
    }

    @Test
    @Transactional
    public void getAllDataByDataIsInShouldWork() throws Exception {
        // Initialize the database
        dataRepository.saveAndFlush(data);

        // Get all the dataList where data in DEFAULT_DATA or UPDATED_DATA
        defaultDataShouldBeFound("data.in=" + DEFAULT_DATA + "," + UPDATED_DATA);

        // Get all the dataList where data equals to UPDATED_DATA
        defaultDataShouldNotBeFound("data.in=" + UPDATED_DATA);
    }

    @Test
    @Transactional
    public void getAllDataByDataIsNullOrNotNull() throws Exception {
        // Initialize the database
        dataRepository.saveAndFlush(data);

        // Get all the dataList where data is not null
        defaultDataShouldBeFound("data.specified=true");

        // Get all the dataList where data is null
        defaultDataShouldNotBeFound("data.specified=false");
    }

    @Test
    @Transactional
    public void getAllDataByDataIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dataRepository.saveAndFlush(data);

        // Get all the dataList where data is greater than or equal to DEFAULT_DATA
        defaultDataShouldBeFound("data.greaterThanOrEqual=" + DEFAULT_DATA);

        // Get all the dataList where data is greater than or equal to UPDATED_DATA
        defaultDataShouldNotBeFound("data.greaterThanOrEqual=" + UPDATED_DATA);
    }

    @Test
    @Transactional
    public void getAllDataByDataIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dataRepository.saveAndFlush(data);

        // Get all the dataList where data is less than or equal to DEFAULT_DATA
        defaultDataShouldBeFound("data.lessThanOrEqual=" + DEFAULT_DATA);

        // Get all the dataList where data is less than or equal to SMALLER_DATA
        defaultDataShouldNotBeFound("data.lessThanOrEqual=" + SMALLER_DATA);
    }

    @Test
    @Transactional
    public void getAllDataByDataIsLessThanSomething() throws Exception {
        // Initialize the database
        dataRepository.saveAndFlush(data);

        // Get all the dataList where data is less than DEFAULT_DATA
        defaultDataShouldNotBeFound("data.lessThan=" + DEFAULT_DATA);

        // Get all the dataList where data is less than UPDATED_DATA
        defaultDataShouldBeFound("data.lessThan=" + UPDATED_DATA);
    }

    @Test
    @Transactional
    public void getAllDataByDataIsGreaterThanSomething() throws Exception {
        // Initialize the database
        dataRepository.saveAndFlush(data);

        // Get all the dataList where data is greater than DEFAULT_DATA
        defaultDataShouldNotBeFound("data.greaterThan=" + DEFAULT_DATA);

        // Get all the dataList where data is greater than SMALLER_DATA
        defaultDataShouldBeFound("data.greaterThan=" + SMALLER_DATA);
    }


    @Test
    @Transactional
    public void getAllDataByTipoDataIsEqualToSomething() throws Exception {
        // Initialize the database
        dataRepository.saveAndFlush(data);
        TipoData tipoData = TipoDataResourceIT.createEntity(em);
        em.persist(tipoData);
        em.flush();
        data.setTipoData(tipoData);
        dataRepository.saveAndFlush(data);
        Long tipoDataId = tipoData.getId();

        // Get all the dataList where tipoData equals to tipoDataId
        defaultDataShouldBeFound("tipoDataId.equals=" + tipoDataId);

        // Get all the dataList where tipoData equals to tipoDataId + 1
        defaultDataShouldNotBeFound("tipoDataId.equals=" + (tipoDataId + 1));
    }


    @Test
    @Transactional
    public void getAllDataByProcessoIsEqualToSomething() throws Exception {
        // Initialize the database
        dataRepository.saveAndFlush(data);
        Processo processo = ProcessoResourceIT.createEntity(em);
        em.persist(processo);
        em.flush();
        data.setProcesso(processo);
        dataRepository.saveAndFlush(data);
        Long processoId = processo.getId();

        // Get all the dataList where processo equals to processoId
        defaultDataShouldBeFound("processoId.equals=" + processoId);

        // Get all the dataList where processo equals to processoId + 1
        defaultDataShouldNotBeFound("processoId.equals=" + (processoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDataShouldBeFound(String filter) throws Exception {
        restDataMockMvc.perform(get("/api/data?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(data.getId().intValue())))
            .andExpect(jsonPath("$.[*].data").value(hasItem(DEFAULT_DATA.toString())));

        // Check, that the count call also returns 1
        restDataMockMvc.perform(get("/api/data/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDataShouldNotBeFound(String filter) throws Exception {
        restDataMockMvc.perform(get("/api/data?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDataMockMvc.perform(get("/api/data/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingData() throws Exception {
        // Get the data
        restDataMockMvc.perform(get("/api/data/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateData() throws Exception {
        // Initialize the database
        dataService.save(data);

        int databaseSizeBeforeUpdate = dataRepository.findAll().size();

        // Update the data
        Data updatedData = dataRepository.findById(data.getId()).get();
        // Disconnect from session so that the updates on updatedData are not directly saved in db
        em.detach(updatedData);
        updatedData
            .data(UPDATED_DATA);

        restDataMockMvc.perform(put("/api/data")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedData)))
            .andExpect(status().isOk());

        // Validate the Data in the database
        List<Data> dataList = dataRepository.findAll();
        assertThat(dataList).hasSize(databaseSizeBeforeUpdate);
        Data testData = dataList.get(dataList.size() - 1);
        assertThat(testData.getData()).isEqualTo(UPDATED_DATA);
    }

    @Test
    @Transactional
    public void updateNonExistingData() throws Exception {
        int databaseSizeBeforeUpdate = dataRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDataMockMvc.perform(put("/api/data")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(data)))
            .andExpect(status().isBadRequest());

        // Validate the Data in the database
        List<Data> dataList = dataRepository.findAll();
        assertThat(dataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteData() throws Exception {
        // Initialize the database
        dataService.save(data);

        int databaseSizeBeforeDelete = dataRepository.findAll().size();

        // Delete the data
        restDataMockMvc.perform(delete("/api/data/{id}", data.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Data> dataList = dataRepository.findAll();
        assertThat(dataList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
