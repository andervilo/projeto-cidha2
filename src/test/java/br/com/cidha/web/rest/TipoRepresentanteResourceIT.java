package br.com.cidha.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.cidha.IntegrationTest;
import br.com.cidha.domain.TipoRepresentante;
import br.com.cidha.repository.TipoRepresentanteRepository;
import br.com.cidha.service.criteria.TipoRepresentanteCriteria;
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
 * Integration tests for the {@link TipoRepresentanteResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TipoRepresentanteResourceIT {

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/tipo-representantes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TipoRepresentanteRepository tipoRepresentanteRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTipoRepresentanteMockMvc;

    private TipoRepresentante tipoRepresentante;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoRepresentante createEntity(EntityManager em) {
        TipoRepresentante tipoRepresentante = new TipoRepresentante().descricao(DEFAULT_DESCRICAO);
        return tipoRepresentante;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoRepresentante createUpdatedEntity(EntityManager em) {
        TipoRepresentante tipoRepresentante = new TipoRepresentante().descricao(UPDATED_DESCRICAO);
        return tipoRepresentante;
    }

    @BeforeEach
    public void initTest() {
        tipoRepresentante = createEntity(em);
    }

    @Test
    @Transactional
    void createTipoRepresentante() throws Exception {
        int databaseSizeBeforeCreate = tipoRepresentanteRepository.findAll().size();
        // Create the TipoRepresentante
        restTipoRepresentanteMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tipoRepresentante))
            )
            .andExpect(status().isCreated());

        // Validate the TipoRepresentante in the database
        List<TipoRepresentante> tipoRepresentanteList = tipoRepresentanteRepository.findAll();
        assertThat(tipoRepresentanteList).hasSize(databaseSizeBeforeCreate + 1);
        TipoRepresentante testTipoRepresentante = tipoRepresentanteList.get(tipoRepresentanteList.size() - 1);
        assertThat(testTipoRepresentante.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
    }

    @Test
    @Transactional
    void createTipoRepresentanteWithExistingId() throws Exception {
        // Create the TipoRepresentante with an existing ID
        tipoRepresentante.setId(1L);

        int databaseSizeBeforeCreate = tipoRepresentanteRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTipoRepresentanteMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tipoRepresentante))
            )
            .andExpect(status().isBadRequest());

        // Validate the TipoRepresentante in the database
        List<TipoRepresentante> tipoRepresentanteList = tipoRepresentanteRepository.findAll();
        assertThat(tipoRepresentanteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTipoRepresentantes() throws Exception {
        // Initialize the database
        tipoRepresentanteRepository.saveAndFlush(tipoRepresentante);

        // Get all the tipoRepresentanteList
        restTipoRepresentanteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoRepresentante.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)));
    }

    @Test
    @Transactional
    void getTipoRepresentante() throws Exception {
        // Initialize the database
        tipoRepresentanteRepository.saveAndFlush(tipoRepresentante);

        // Get the tipoRepresentante
        restTipoRepresentanteMockMvc
            .perform(get(ENTITY_API_URL_ID, tipoRepresentante.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tipoRepresentante.getId().intValue()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO));
    }

    @Test
    @Transactional
    void getTipoRepresentantesByIdFiltering() throws Exception {
        // Initialize the database
        tipoRepresentanteRepository.saveAndFlush(tipoRepresentante);

        Long id = tipoRepresentante.getId();

        defaultTipoRepresentanteShouldBeFound("id.equals=" + id);
        defaultTipoRepresentanteShouldNotBeFound("id.notEquals=" + id);

        defaultTipoRepresentanteShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTipoRepresentanteShouldNotBeFound("id.greaterThan=" + id);

        defaultTipoRepresentanteShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTipoRepresentanteShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllTipoRepresentantesByDescricaoIsEqualToSomething() throws Exception {
        // Initialize the database
        tipoRepresentanteRepository.saveAndFlush(tipoRepresentante);

        // Get all the tipoRepresentanteList where descricao equals to DEFAULT_DESCRICAO
        defaultTipoRepresentanteShouldBeFound("descricao.equals=" + DEFAULT_DESCRICAO);

        // Get all the tipoRepresentanteList where descricao equals to UPDATED_DESCRICAO
        defaultTipoRepresentanteShouldNotBeFound("descricao.equals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllTipoRepresentantesByDescricaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tipoRepresentanteRepository.saveAndFlush(tipoRepresentante);

        // Get all the tipoRepresentanteList where descricao not equals to DEFAULT_DESCRICAO
        defaultTipoRepresentanteShouldNotBeFound("descricao.notEquals=" + DEFAULT_DESCRICAO);

        // Get all the tipoRepresentanteList where descricao not equals to UPDATED_DESCRICAO
        defaultTipoRepresentanteShouldBeFound("descricao.notEquals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllTipoRepresentantesByDescricaoIsInShouldWork() throws Exception {
        // Initialize the database
        tipoRepresentanteRepository.saveAndFlush(tipoRepresentante);

        // Get all the tipoRepresentanteList where descricao in DEFAULT_DESCRICAO or UPDATED_DESCRICAO
        defaultTipoRepresentanteShouldBeFound("descricao.in=" + DEFAULT_DESCRICAO + "," + UPDATED_DESCRICAO);

        // Get all the tipoRepresentanteList where descricao equals to UPDATED_DESCRICAO
        defaultTipoRepresentanteShouldNotBeFound("descricao.in=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllTipoRepresentantesByDescricaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        tipoRepresentanteRepository.saveAndFlush(tipoRepresentante);

        // Get all the tipoRepresentanteList where descricao is not null
        defaultTipoRepresentanteShouldBeFound("descricao.specified=true");

        // Get all the tipoRepresentanteList where descricao is null
        defaultTipoRepresentanteShouldNotBeFound("descricao.specified=false");
    }

    @Test
    @Transactional
    void getAllTipoRepresentantesByDescricaoContainsSomething() throws Exception {
        // Initialize the database
        tipoRepresentanteRepository.saveAndFlush(tipoRepresentante);

        // Get all the tipoRepresentanteList where descricao contains DEFAULT_DESCRICAO
        defaultTipoRepresentanteShouldBeFound("descricao.contains=" + DEFAULT_DESCRICAO);

        // Get all the tipoRepresentanteList where descricao contains UPDATED_DESCRICAO
        defaultTipoRepresentanteShouldNotBeFound("descricao.contains=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllTipoRepresentantesByDescricaoNotContainsSomething() throws Exception {
        // Initialize the database
        tipoRepresentanteRepository.saveAndFlush(tipoRepresentante);

        // Get all the tipoRepresentanteList where descricao does not contain DEFAULT_DESCRICAO
        defaultTipoRepresentanteShouldNotBeFound("descricao.doesNotContain=" + DEFAULT_DESCRICAO);

        // Get all the tipoRepresentanteList where descricao does not contain UPDATED_DESCRICAO
        defaultTipoRepresentanteShouldBeFound("descricao.doesNotContain=" + UPDATED_DESCRICAO);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTipoRepresentanteShouldBeFound(String filter) throws Exception {
        restTipoRepresentanteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoRepresentante.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)));

        // Check, that the count call also returns 1
        restTipoRepresentanteMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTipoRepresentanteShouldNotBeFound(String filter) throws Exception {
        restTipoRepresentanteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTipoRepresentanteMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingTipoRepresentante() throws Exception {
        // Get the tipoRepresentante
        restTipoRepresentanteMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTipoRepresentante() throws Exception {
        // Initialize the database
        tipoRepresentanteRepository.saveAndFlush(tipoRepresentante);

        int databaseSizeBeforeUpdate = tipoRepresentanteRepository.findAll().size();

        // Update the tipoRepresentante
        TipoRepresentante updatedTipoRepresentante = tipoRepresentanteRepository.findById(tipoRepresentante.getId()).get();
        // Disconnect from session so that the updates on updatedTipoRepresentante are not directly saved in db
        em.detach(updatedTipoRepresentante);
        updatedTipoRepresentante.descricao(UPDATED_DESCRICAO);

        restTipoRepresentanteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTipoRepresentante.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedTipoRepresentante))
            )
            .andExpect(status().isOk());

        // Validate the TipoRepresentante in the database
        List<TipoRepresentante> tipoRepresentanteList = tipoRepresentanteRepository.findAll();
        assertThat(tipoRepresentanteList).hasSize(databaseSizeBeforeUpdate);
        TipoRepresentante testTipoRepresentante = tipoRepresentanteList.get(tipoRepresentanteList.size() - 1);
        assertThat(testTipoRepresentante.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void putNonExistingTipoRepresentante() throws Exception {
        int databaseSizeBeforeUpdate = tipoRepresentanteRepository.findAll().size();
        tipoRepresentante.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTipoRepresentanteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tipoRepresentante.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tipoRepresentante))
            )
            .andExpect(status().isBadRequest());

        // Validate the TipoRepresentante in the database
        List<TipoRepresentante> tipoRepresentanteList = tipoRepresentanteRepository.findAll();
        assertThat(tipoRepresentanteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTipoRepresentante() throws Exception {
        int databaseSizeBeforeUpdate = tipoRepresentanteRepository.findAll().size();
        tipoRepresentante.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTipoRepresentanteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tipoRepresentante))
            )
            .andExpect(status().isBadRequest());

        // Validate the TipoRepresentante in the database
        List<TipoRepresentante> tipoRepresentanteList = tipoRepresentanteRepository.findAll();
        assertThat(tipoRepresentanteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTipoRepresentante() throws Exception {
        int databaseSizeBeforeUpdate = tipoRepresentanteRepository.findAll().size();
        tipoRepresentante.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTipoRepresentanteMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tipoRepresentante))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TipoRepresentante in the database
        List<TipoRepresentante> tipoRepresentanteList = tipoRepresentanteRepository.findAll();
        assertThat(tipoRepresentanteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTipoRepresentanteWithPatch() throws Exception {
        // Initialize the database
        tipoRepresentanteRepository.saveAndFlush(tipoRepresentante);

        int databaseSizeBeforeUpdate = tipoRepresentanteRepository.findAll().size();

        // Update the tipoRepresentante using partial update
        TipoRepresentante partialUpdatedTipoRepresentante = new TipoRepresentante();
        partialUpdatedTipoRepresentante.setId(tipoRepresentante.getId());

        restTipoRepresentanteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTipoRepresentante.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTipoRepresentante))
            )
            .andExpect(status().isOk());

        // Validate the TipoRepresentante in the database
        List<TipoRepresentante> tipoRepresentanteList = tipoRepresentanteRepository.findAll();
        assertThat(tipoRepresentanteList).hasSize(databaseSizeBeforeUpdate);
        TipoRepresentante testTipoRepresentante = tipoRepresentanteList.get(tipoRepresentanteList.size() - 1);
        assertThat(testTipoRepresentante.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
    }

    @Test
    @Transactional
    void fullUpdateTipoRepresentanteWithPatch() throws Exception {
        // Initialize the database
        tipoRepresentanteRepository.saveAndFlush(tipoRepresentante);

        int databaseSizeBeforeUpdate = tipoRepresentanteRepository.findAll().size();

        // Update the tipoRepresentante using partial update
        TipoRepresentante partialUpdatedTipoRepresentante = new TipoRepresentante();
        partialUpdatedTipoRepresentante.setId(tipoRepresentante.getId());

        partialUpdatedTipoRepresentante.descricao(UPDATED_DESCRICAO);

        restTipoRepresentanteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTipoRepresentante.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTipoRepresentante))
            )
            .andExpect(status().isOk());

        // Validate the TipoRepresentante in the database
        List<TipoRepresentante> tipoRepresentanteList = tipoRepresentanteRepository.findAll();
        assertThat(tipoRepresentanteList).hasSize(databaseSizeBeforeUpdate);
        TipoRepresentante testTipoRepresentante = tipoRepresentanteList.get(tipoRepresentanteList.size() - 1);
        assertThat(testTipoRepresentante.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void patchNonExistingTipoRepresentante() throws Exception {
        int databaseSizeBeforeUpdate = tipoRepresentanteRepository.findAll().size();
        tipoRepresentante.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTipoRepresentanteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, tipoRepresentante.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tipoRepresentante))
            )
            .andExpect(status().isBadRequest());

        // Validate the TipoRepresentante in the database
        List<TipoRepresentante> tipoRepresentanteList = tipoRepresentanteRepository.findAll();
        assertThat(tipoRepresentanteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTipoRepresentante() throws Exception {
        int databaseSizeBeforeUpdate = tipoRepresentanteRepository.findAll().size();
        tipoRepresentante.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTipoRepresentanteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tipoRepresentante))
            )
            .andExpect(status().isBadRequest());

        // Validate the TipoRepresentante in the database
        List<TipoRepresentante> tipoRepresentanteList = tipoRepresentanteRepository.findAll();
        assertThat(tipoRepresentanteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTipoRepresentante() throws Exception {
        int databaseSizeBeforeUpdate = tipoRepresentanteRepository.findAll().size();
        tipoRepresentante.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTipoRepresentanteMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tipoRepresentante))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TipoRepresentante in the database
        List<TipoRepresentante> tipoRepresentanteList = tipoRepresentanteRepository.findAll();
        assertThat(tipoRepresentanteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTipoRepresentante() throws Exception {
        // Initialize the database
        tipoRepresentanteRepository.saveAndFlush(tipoRepresentante);

        int databaseSizeBeforeDelete = tipoRepresentanteRepository.findAll().size();

        // Delete the tipoRepresentante
        restTipoRepresentanteMockMvc
            .perform(delete(ENTITY_API_URL_ID, tipoRepresentante.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TipoRepresentante> tipoRepresentanteList = tipoRepresentanteRepository.findAll();
        assertThat(tipoRepresentanteList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
