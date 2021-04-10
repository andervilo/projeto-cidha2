package br.com.cidha.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.cidha.IntegrationTest;
import br.com.cidha.domain.Processo;
import br.com.cidha.domain.Territorio;
import br.com.cidha.repository.TerritorioRepository;
import br.com.cidha.service.criteria.TerritorioCriteria;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link TerritorioResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TerritorioResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/territorios";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TerritorioRepository territorioRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTerritorioMockMvc;

    private Territorio territorio;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Territorio createEntity(EntityManager em) {
        Territorio territorio = new Territorio().nome(DEFAULT_NOME);
        return territorio;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Territorio createUpdatedEntity(EntityManager em) {
        Territorio territorio = new Territorio().nome(UPDATED_NOME);
        return territorio;
    }

    @BeforeEach
    public void initTest() {
        territorio = createEntity(em);
    }

    @Test
    @Transactional
    void createTerritorio() throws Exception {
        int databaseSizeBeforeCreate = territorioRepository.findAll().size();
        // Create the Territorio
        restTerritorioMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(territorio)))
            .andExpect(status().isCreated());

        // Validate the Territorio in the database
        List<Territorio> territorioList = territorioRepository.findAll();
        assertThat(territorioList).hasSize(databaseSizeBeforeCreate + 1);
        Territorio testTerritorio = territorioList.get(territorioList.size() - 1);
        assertThat(testTerritorio.getNome()).isEqualTo(DEFAULT_NOME);
    }

    @Test
    @Transactional
    void createTerritorioWithExistingId() throws Exception {
        // Create the Territorio with an existing ID
        territorio.setId(1L);

        int databaseSizeBeforeCreate = territorioRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTerritorioMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(territorio)))
            .andExpect(status().isBadRequest());

        // Validate the Territorio in the database
        List<Territorio> territorioList = territorioRepository.findAll();
        assertThat(territorioList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTerritorios() throws Exception {
        // Initialize the database
        territorioRepository.saveAndFlush(territorio);

        // Get all the territorioList
        restTerritorioMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(territorio.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)));
    }

    @Test
    @Transactional
    void getTerritorio() throws Exception {
        // Initialize the database
        territorioRepository.saveAndFlush(territorio);

        // Get the territorio
        restTerritorioMockMvc
            .perform(get(ENTITY_API_URL_ID, territorio.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(territorio.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME));
    }

    @Test
    @Transactional
    void getTerritoriosByIdFiltering() throws Exception {
        // Initialize the database
        territorioRepository.saveAndFlush(territorio);

        Long id = territorio.getId();

        defaultTerritorioShouldBeFound("id.equals=" + id);
        defaultTerritorioShouldNotBeFound("id.notEquals=" + id);

        defaultTerritorioShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTerritorioShouldNotBeFound("id.greaterThan=" + id);

        defaultTerritorioShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTerritorioShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllTerritoriosByNomeIsEqualToSomething() throws Exception {
        // Initialize the database
        territorioRepository.saveAndFlush(territorio);

        // Get all the territorioList where nome equals to DEFAULT_NOME
        defaultTerritorioShouldBeFound("nome.equals=" + DEFAULT_NOME);

        // Get all the territorioList where nome equals to UPDATED_NOME
        defaultTerritorioShouldNotBeFound("nome.equals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllTerritoriosByNomeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        territorioRepository.saveAndFlush(territorio);

        // Get all the territorioList where nome not equals to DEFAULT_NOME
        defaultTerritorioShouldNotBeFound("nome.notEquals=" + DEFAULT_NOME);

        // Get all the territorioList where nome not equals to UPDATED_NOME
        defaultTerritorioShouldBeFound("nome.notEquals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllTerritoriosByNomeIsInShouldWork() throws Exception {
        // Initialize the database
        territorioRepository.saveAndFlush(territorio);

        // Get all the territorioList where nome in DEFAULT_NOME or UPDATED_NOME
        defaultTerritorioShouldBeFound("nome.in=" + DEFAULT_NOME + "," + UPDATED_NOME);

        // Get all the territorioList where nome equals to UPDATED_NOME
        defaultTerritorioShouldNotBeFound("nome.in=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllTerritoriosByNomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        territorioRepository.saveAndFlush(territorio);

        // Get all the territorioList where nome is not null
        defaultTerritorioShouldBeFound("nome.specified=true");

        // Get all the territorioList where nome is null
        defaultTerritorioShouldNotBeFound("nome.specified=false");
    }

    @Test
    @Transactional
    void getAllTerritoriosByNomeContainsSomething() throws Exception {
        // Initialize the database
        territorioRepository.saveAndFlush(territorio);

        // Get all the territorioList where nome contains DEFAULT_NOME
        defaultTerritorioShouldBeFound("nome.contains=" + DEFAULT_NOME);

        // Get all the territorioList where nome contains UPDATED_NOME
        defaultTerritorioShouldNotBeFound("nome.contains=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllTerritoriosByNomeNotContainsSomething() throws Exception {
        // Initialize the database
        territorioRepository.saveAndFlush(territorio);

        // Get all the territorioList where nome does not contain DEFAULT_NOME
        defaultTerritorioShouldNotBeFound("nome.doesNotContain=" + DEFAULT_NOME);

        // Get all the territorioList where nome does not contain UPDATED_NOME
        defaultTerritorioShouldBeFound("nome.doesNotContain=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllTerritoriosByProcessoIsEqualToSomething() throws Exception {
        // Initialize the database
        territorioRepository.saveAndFlush(territorio);
        Processo processo = ProcessoResourceIT.createEntity(em);
        em.persist(processo);
        em.flush();
        territorio.addProcesso(processo);
        territorioRepository.saveAndFlush(territorio);
        Long processoId = processo.getId();

        // Get all the territorioList where processo equals to processoId
        defaultTerritorioShouldBeFound("processoId.equals=" + processoId);

        // Get all the territorioList where processo equals to (processoId + 1)
        defaultTerritorioShouldNotBeFound("processoId.equals=" + (processoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTerritorioShouldBeFound(String filter) throws Exception {
        restTerritorioMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(territorio.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)));

        // Check, that the count call also returns 1
        restTerritorioMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTerritorioShouldNotBeFound(String filter) throws Exception {
        restTerritorioMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTerritorioMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingTerritorio() throws Exception {
        // Get the territorio
        restTerritorioMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTerritorio() throws Exception {
        // Initialize the database
        territorioRepository.saveAndFlush(territorio);

        int databaseSizeBeforeUpdate = territorioRepository.findAll().size();

        // Update the territorio
        Territorio updatedTerritorio = territorioRepository.findById(territorio.getId()).get();
        // Disconnect from session so that the updates on updatedTerritorio are not directly saved in db
        em.detach(updatedTerritorio);
        updatedTerritorio.nome(UPDATED_NOME);

        restTerritorioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTerritorio.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedTerritorio))
            )
            .andExpect(status().isOk());

        // Validate the Territorio in the database
        List<Territorio> territorioList = territorioRepository.findAll();
        assertThat(territorioList).hasSize(databaseSizeBeforeUpdate);
        Territorio testTerritorio = territorioList.get(territorioList.size() - 1);
        assertThat(testTerritorio.getNome()).isEqualTo(UPDATED_NOME);
    }

    @Test
    @Transactional
    void putNonExistingTerritorio() throws Exception {
        int databaseSizeBeforeUpdate = territorioRepository.findAll().size();
        territorio.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTerritorioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, territorio.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(territorio))
            )
            .andExpect(status().isBadRequest());

        // Validate the Territorio in the database
        List<Territorio> territorioList = territorioRepository.findAll();
        assertThat(territorioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTerritorio() throws Exception {
        int databaseSizeBeforeUpdate = territorioRepository.findAll().size();
        territorio.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTerritorioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(territorio))
            )
            .andExpect(status().isBadRequest());

        // Validate the Territorio in the database
        List<Territorio> territorioList = territorioRepository.findAll();
        assertThat(territorioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTerritorio() throws Exception {
        int databaseSizeBeforeUpdate = territorioRepository.findAll().size();
        territorio.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTerritorioMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(territorio)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Territorio in the database
        List<Territorio> territorioList = territorioRepository.findAll();
        assertThat(territorioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTerritorioWithPatch() throws Exception {
        // Initialize the database
        territorioRepository.saveAndFlush(territorio);

        int databaseSizeBeforeUpdate = territorioRepository.findAll().size();

        // Update the territorio using partial update
        Territorio partialUpdatedTerritorio = new Territorio();
        partialUpdatedTerritorio.setId(territorio.getId());

        restTerritorioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTerritorio.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTerritorio))
            )
            .andExpect(status().isOk());

        // Validate the Territorio in the database
        List<Territorio> territorioList = territorioRepository.findAll();
        assertThat(territorioList).hasSize(databaseSizeBeforeUpdate);
        Territorio testTerritorio = territorioList.get(territorioList.size() - 1);
        assertThat(testTerritorio.getNome()).isEqualTo(DEFAULT_NOME);
    }

    @Test
    @Transactional
    void fullUpdateTerritorioWithPatch() throws Exception {
        // Initialize the database
        territorioRepository.saveAndFlush(territorio);

        int databaseSizeBeforeUpdate = territorioRepository.findAll().size();

        // Update the territorio using partial update
        Territorio partialUpdatedTerritorio = new Territorio();
        partialUpdatedTerritorio.setId(territorio.getId());

        partialUpdatedTerritorio.nome(UPDATED_NOME);

        restTerritorioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTerritorio.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTerritorio))
            )
            .andExpect(status().isOk());

        // Validate the Territorio in the database
        List<Territorio> territorioList = territorioRepository.findAll();
        assertThat(territorioList).hasSize(databaseSizeBeforeUpdate);
        Territorio testTerritorio = territorioList.get(territorioList.size() - 1);
        assertThat(testTerritorio.getNome()).isEqualTo(UPDATED_NOME);
    }

    @Test
    @Transactional
    void patchNonExistingTerritorio() throws Exception {
        int databaseSizeBeforeUpdate = territorioRepository.findAll().size();
        territorio.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTerritorioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, territorio.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(territorio))
            )
            .andExpect(status().isBadRequest());

        // Validate the Territorio in the database
        List<Territorio> territorioList = territorioRepository.findAll();
        assertThat(territorioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTerritorio() throws Exception {
        int databaseSizeBeforeUpdate = territorioRepository.findAll().size();
        territorio.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTerritorioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(territorio))
            )
            .andExpect(status().isBadRequest());

        // Validate the Territorio in the database
        List<Territorio> territorioList = territorioRepository.findAll();
        assertThat(territorioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTerritorio() throws Exception {
        int databaseSizeBeforeUpdate = territorioRepository.findAll().size();
        territorio.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTerritorioMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(territorio))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Territorio in the database
        List<Territorio> territorioList = territorioRepository.findAll();
        assertThat(territorioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTerritorio() throws Exception {
        // Initialize the database
        territorioRepository.saveAndFlush(territorio);

        int databaseSizeBeforeDelete = territorioRepository.findAll().size();

        // Delete the territorio
        restTerritorioMockMvc
            .perform(delete(ENTITY_API_URL_ID, territorio.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Territorio> territorioList = territorioRepository.findAll();
        assertThat(territorioList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
