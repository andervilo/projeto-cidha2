package br.com.cidha.web.rest;

import br.com.cidha.CidhaApp;
import br.com.cidha.domain.EtniaIndigena;
import br.com.cidha.domain.TerraIndigena;
import br.com.cidha.repository.EtniaIndigenaRepository;
import br.com.cidha.service.EtniaIndigenaService;
import br.com.cidha.service.dto.EtniaIndigenaCriteria;
import br.com.cidha.service.EtniaIndigenaQueryService;

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
 * Integration tests for the {@link EtniaIndigenaResource} REST controller.
 */
@SpringBootTest(classes = CidhaApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class EtniaIndigenaResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    @Autowired
    private EtniaIndigenaRepository etniaIndigenaRepository;

    @Autowired
    private EtniaIndigenaService etniaIndigenaService;

    @Autowired
    private EtniaIndigenaQueryService etniaIndigenaQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEtniaIndigenaMockMvc;

    private EtniaIndigena etniaIndigena;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EtniaIndigena createEntity(EntityManager em) {
        EtniaIndigena etniaIndigena = new EtniaIndigena()
            .nome(DEFAULT_NOME);
        return etniaIndigena;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EtniaIndigena createUpdatedEntity(EntityManager em) {
        EtniaIndigena etniaIndigena = new EtniaIndigena()
            .nome(UPDATED_NOME);
        return etniaIndigena;
    }

    @BeforeEach
    public void initTest() {
        etniaIndigena = createEntity(em);
    }

    @Test
    @Transactional
    public void createEtniaIndigena() throws Exception {
        int databaseSizeBeforeCreate = etniaIndigenaRepository.findAll().size();
        // Create the EtniaIndigena
        restEtniaIndigenaMockMvc.perform(post("/api/etnia-indigenas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(etniaIndigena)))
            .andExpect(status().isCreated());

        // Validate the EtniaIndigena in the database
        List<EtniaIndigena> etniaIndigenaList = etniaIndigenaRepository.findAll();
        assertThat(etniaIndigenaList).hasSize(databaseSizeBeforeCreate + 1);
        EtniaIndigena testEtniaIndigena = etniaIndigenaList.get(etniaIndigenaList.size() - 1);
        assertThat(testEtniaIndigena.getNome()).isEqualTo(DEFAULT_NOME);
    }

    @Test
    @Transactional
    public void createEtniaIndigenaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = etniaIndigenaRepository.findAll().size();

        // Create the EtniaIndigena with an existing ID
        etniaIndigena.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEtniaIndigenaMockMvc.perform(post("/api/etnia-indigenas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(etniaIndigena)))
            .andExpect(status().isBadRequest());

        // Validate the EtniaIndigena in the database
        List<EtniaIndigena> etniaIndigenaList = etniaIndigenaRepository.findAll();
        assertThat(etniaIndigenaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllEtniaIndigenas() throws Exception {
        // Initialize the database
        etniaIndigenaRepository.saveAndFlush(etniaIndigena);

        // Get all the etniaIndigenaList
        restEtniaIndigenaMockMvc.perform(get("/api/etnia-indigenas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(etniaIndigena.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)));
    }
    
    @Test
    @Transactional
    public void getEtniaIndigena() throws Exception {
        // Initialize the database
        etniaIndigenaRepository.saveAndFlush(etniaIndigena);

        // Get the etniaIndigena
        restEtniaIndigenaMockMvc.perform(get("/api/etnia-indigenas/{id}", etniaIndigena.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(etniaIndigena.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME));
    }


    @Test
    @Transactional
    public void getEtniaIndigenasByIdFiltering() throws Exception {
        // Initialize the database
        etniaIndigenaRepository.saveAndFlush(etniaIndigena);

        Long id = etniaIndigena.getId();

        defaultEtniaIndigenaShouldBeFound("id.equals=" + id);
        defaultEtniaIndigenaShouldNotBeFound("id.notEquals=" + id);

        defaultEtniaIndigenaShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultEtniaIndigenaShouldNotBeFound("id.greaterThan=" + id);

        defaultEtniaIndigenaShouldBeFound("id.lessThanOrEqual=" + id);
        defaultEtniaIndigenaShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllEtniaIndigenasByNomeIsEqualToSomething() throws Exception {
        // Initialize the database
        etniaIndigenaRepository.saveAndFlush(etniaIndigena);

        // Get all the etniaIndigenaList where nome equals to DEFAULT_NOME
        defaultEtniaIndigenaShouldBeFound("nome.equals=" + DEFAULT_NOME);

        // Get all the etniaIndigenaList where nome equals to UPDATED_NOME
        defaultEtniaIndigenaShouldNotBeFound("nome.equals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllEtniaIndigenasByNomeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        etniaIndigenaRepository.saveAndFlush(etniaIndigena);

        // Get all the etniaIndigenaList where nome not equals to DEFAULT_NOME
        defaultEtniaIndigenaShouldNotBeFound("nome.notEquals=" + DEFAULT_NOME);

        // Get all the etniaIndigenaList where nome not equals to UPDATED_NOME
        defaultEtniaIndigenaShouldBeFound("nome.notEquals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllEtniaIndigenasByNomeIsInShouldWork() throws Exception {
        // Initialize the database
        etniaIndigenaRepository.saveAndFlush(etniaIndigena);

        // Get all the etniaIndigenaList where nome in DEFAULT_NOME or UPDATED_NOME
        defaultEtniaIndigenaShouldBeFound("nome.in=" + DEFAULT_NOME + "," + UPDATED_NOME);

        // Get all the etniaIndigenaList where nome equals to UPDATED_NOME
        defaultEtniaIndigenaShouldNotBeFound("nome.in=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllEtniaIndigenasByNomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        etniaIndigenaRepository.saveAndFlush(etniaIndigena);

        // Get all the etniaIndigenaList where nome is not null
        defaultEtniaIndigenaShouldBeFound("nome.specified=true");

        // Get all the etniaIndigenaList where nome is null
        defaultEtniaIndigenaShouldNotBeFound("nome.specified=false");
    }
                @Test
    @Transactional
    public void getAllEtniaIndigenasByNomeContainsSomething() throws Exception {
        // Initialize the database
        etniaIndigenaRepository.saveAndFlush(etniaIndigena);

        // Get all the etniaIndigenaList where nome contains DEFAULT_NOME
        defaultEtniaIndigenaShouldBeFound("nome.contains=" + DEFAULT_NOME);

        // Get all the etniaIndigenaList where nome contains UPDATED_NOME
        defaultEtniaIndigenaShouldNotBeFound("nome.contains=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllEtniaIndigenasByNomeNotContainsSomething() throws Exception {
        // Initialize the database
        etniaIndigenaRepository.saveAndFlush(etniaIndigena);

        // Get all the etniaIndigenaList where nome does not contain DEFAULT_NOME
        defaultEtniaIndigenaShouldNotBeFound("nome.doesNotContain=" + DEFAULT_NOME);

        // Get all the etniaIndigenaList where nome does not contain UPDATED_NOME
        defaultEtniaIndigenaShouldBeFound("nome.doesNotContain=" + UPDATED_NOME);
    }


    @Test
    @Transactional
    public void getAllEtniaIndigenasByTerraIndigenaIsEqualToSomething() throws Exception {
        // Initialize the database
        etniaIndigenaRepository.saveAndFlush(etniaIndigena);
        TerraIndigena terraIndigena = TerraIndigenaResourceIT.createEntity(em);
        em.persist(terraIndigena);
        em.flush();
        etniaIndigena.addTerraIndigena(terraIndigena);
        etniaIndigenaRepository.saveAndFlush(etniaIndigena);
        Long terraIndigenaId = terraIndigena.getId();

        // Get all the etniaIndigenaList where terraIndigena equals to terraIndigenaId
        defaultEtniaIndigenaShouldBeFound("terraIndigenaId.equals=" + terraIndigenaId);

        // Get all the etniaIndigenaList where terraIndigena equals to terraIndigenaId + 1
        defaultEtniaIndigenaShouldNotBeFound("terraIndigenaId.equals=" + (terraIndigenaId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEtniaIndigenaShouldBeFound(String filter) throws Exception {
        restEtniaIndigenaMockMvc.perform(get("/api/etnia-indigenas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(etniaIndigena.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)));

        // Check, that the count call also returns 1
        restEtniaIndigenaMockMvc.perform(get("/api/etnia-indigenas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEtniaIndigenaShouldNotBeFound(String filter) throws Exception {
        restEtniaIndigenaMockMvc.perform(get("/api/etnia-indigenas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEtniaIndigenaMockMvc.perform(get("/api/etnia-indigenas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingEtniaIndigena() throws Exception {
        // Get the etniaIndigena
        restEtniaIndigenaMockMvc.perform(get("/api/etnia-indigenas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEtniaIndigena() throws Exception {
        // Initialize the database
        etniaIndigenaService.save(etniaIndigena);

        int databaseSizeBeforeUpdate = etniaIndigenaRepository.findAll().size();

        // Update the etniaIndigena
        EtniaIndigena updatedEtniaIndigena = etniaIndigenaRepository.findById(etniaIndigena.getId()).get();
        // Disconnect from session so that the updates on updatedEtniaIndigena are not directly saved in db
        em.detach(updatedEtniaIndigena);
        updatedEtniaIndigena
            .nome(UPDATED_NOME);

        restEtniaIndigenaMockMvc.perform(put("/api/etnia-indigenas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedEtniaIndigena)))
            .andExpect(status().isOk());

        // Validate the EtniaIndigena in the database
        List<EtniaIndigena> etniaIndigenaList = etniaIndigenaRepository.findAll();
        assertThat(etniaIndigenaList).hasSize(databaseSizeBeforeUpdate);
        EtniaIndigena testEtniaIndigena = etniaIndigenaList.get(etniaIndigenaList.size() - 1);
        assertThat(testEtniaIndigena.getNome()).isEqualTo(UPDATED_NOME);
    }

    @Test
    @Transactional
    public void updateNonExistingEtniaIndigena() throws Exception {
        int databaseSizeBeforeUpdate = etniaIndigenaRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEtniaIndigenaMockMvc.perform(put("/api/etnia-indigenas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(etniaIndigena)))
            .andExpect(status().isBadRequest());

        // Validate the EtniaIndigena in the database
        List<EtniaIndigena> etniaIndigenaList = etniaIndigenaRepository.findAll();
        assertThat(etniaIndigenaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEtniaIndigena() throws Exception {
        // Initialize the database
        etniaIndigenaService.save(etniaIndigena);

        int databaseSizeBeforeDelete = etniaIndigenaRepository.findAll().size();

        // Delete the etniaIndigena
        restEtniaIndigenaMockMvc.perform(delete("/api/etnia-indigenas/{id}", etniaIndigena.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EtniaIndigena> etniaIndigenaList = etniaIndigenaRepository.findAll();
        assertThat(etniaIndigenaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
