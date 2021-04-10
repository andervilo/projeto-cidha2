package br.com.cidha.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.cidha.IntegrationTest;
import br.com.cidha.domain.TipoRecurso;
import br.com.cidha.repository.TipoRecursoRepository;
import br.com.cidha.service.criteria.TipoRecursoCriteria;
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
 * Integration tests for the {@link TipoRecursoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TipoRecursoResourceIT {

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/tipo-recursos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TipoRecursoRepository tipoRecursoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTipoRecursoMockMvc;

    private TipoRecurso tipoRecurso;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoRecurso createEntity(EntityManager em) {
        TipoRecurso tipoRecurso = new TipoRecurso().descricao(DEFAULT_DESCRICAO);
        return tipoRecurso;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoRecurso createUpdatedEntity(EntityManager em) {
        TipoRecurso tipoRecurso = new TipoRecurso().descricao(UPDATED_DESCRICAO);
        return tipoRecurso;
    }

    @BeforeEach
    public void initTest() {
        tipoRecurso = createEntity(em);
    }

    @Test
    @Transactional
    void createTipoRecurso() throws Exception {
        int databaseSizeBeforeCreate = tipoRecursoRepository.findAll().size();
        // Create the TipoRecurso
        restTipoRecursoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tipoRecurso)))
            .andExpect(status().isCreated());

        // Validate the TipoRecurso in the database
        List<TipoRecurso> tipoRecursoList = tipoRecursoRepository.findAll();
        assertThat(tipoRecursoList).hasSize(databaseSizeBeforeCreate + 1);
        TipoRecurso testTipoRecurso = tipoRecursoList.get(tipoRecursoList.size() - 1);
        assertThat(testTipoRecurso.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
    }

    @Test
    @Transactional
    void createTipoRecursoWithExistingId() throws Exception {
        // Create the TipoRecurso with an existing ID
        tipoRecurso.setId(1L);

        int databaseSizeBeforeCreate = tipoRecursoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTipoRecursoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tipoRecurso)))
            .andExpect(status().isBadRequest());

        // Validate the TipoRecurso in the database
        List<TipoRecurso> tipoRecursoList = tipoRecursoRepository.findAll();
        assertThat(tipoRecursoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTipoRecursos() throws Exception {
        // Initialize the database
        tipoRecursoRepository.saveAndFlush(tipoRecurso);

        // Get all the tipoRecursoList
        restTipoRecursoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoRecurso.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)));
    }

    @Test
    @Transactional
    void getTipoRecurso() throws Exception {
        // Initialize the database
        tipoRecursoRepository.saveAndFlush(tipoRecurso);

        // Get the tipoRecurso
        restTipoRecursoMockMvc
            .perform(get(ENTITY_API_URL_ID, tipoRecurso.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tipoRecurso.getId().intValue()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO));
    }

    @Test
    @Transactional
    void getTipoRecursosByIdFiltering() throws Exception {
        // Initialize the database
        tipoRecursoRepository.saveAndFlush(tipoRecurso);

        Long id = tipoRecurso.getId();

        defaultTipoRecursoShouldBeFound("id.equals=" + id);
        defaultTipoRecursoShouldNotBeFound("id.notEquals=" + id);

        defaultTipoRecursoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTipoRecursoShouldNotBeFound("id.greaterThan=" + id);

        defaultTipoRecursoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTipoRecursoShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllTipoRecursosByDescricaoIsEqualToSomething() throws Exception {
        // Initialize the database
        tipoRecursoRepository.saveAndFlush(tipoRecurso);

        // Get all the tipoRecursoList where descricao equals to DEFAULT_DESCRICAO
        defaultTipoRecursoShouldBeFound("descricao.equals=" + DEFAULT_DESCRICAO);

        // Get all the tipoRecursoList where descricao equals to UPDATED_DESCRICAO
        defaultTipoRecursoShouldNotBeFound("descricao.equals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllTipoRecursosByDescricaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tipoRecursoRepository.saveAndFlush(tipoRecurso);

        // Get all the tipoRecursoList where descricao not equals to DEFAULT_DESCRICAO
        defaultTipoRecursoShouldNotBeFound("descricao.notEquals=" + DEFAULT_DESCRICAO);

        // Get all the tipoRecursoList where descricao not equals to UPDATED_DESCRICAO
        defaultTipoRecursoShouldBeFound("descricao.notEquals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllTipoRecursosByDescricaoIsInShouldWork() throws Exception {
        // Initialize the database
        tipoRecursoRepository.saveAndFlush(tipoRecurso);

        // Get all the tipoRecursoList where descricao in DEFAULT_DESCRICAO or UPDATED_DESCRICAO
        defaultTipoRecursoShouldBeFound("descricao.in=" + DEFAULT_DESCRICAO + "," + UPDATED_DESCRICAO);

        // Get all the tipoRecursoList where descricao equals to UPDATED_DESCRICAO
        defaultTipoRecursoShouldNotBeFound("descricao.in=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllTipoRecursosByDescricaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        tipoRecursoRepository.saveAndFlush(tipoRecurso);

        // Get all the tipoRecursoList where descricao is not null
        defaultTipoRecursoShouldBeFound("descricao.specified=true");

        // Get all the tipoRecursoList where descricao is null
        defaultTipoRecursoShouldNotBeFound("descricao.specified=false");
    }

    @Test
    @Transactional
    void getAllTipoRecursosByDescricaoContainsSomething() throws Exception {
        // Initialize the database
        tipoRecursoRepository.saveAndFlush(tipoRecurso);

        // Get all the tipoRecursoList where descricao contains DEFAULT_DESCRICAO
        defaultTipoRecursoShouldBeFound("descricao.contains=" + DEFAULT_DESCRICAO);

        // Get all the tipoRecursoList where descricao contains UPDATED_DESCRICAO
        defaultTipoRecursoShouldNotBeFound("descricao.contains=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllTipoRecursosByDescricaoNotContainsSomething() throws Exception {
        // Initialize the database
        tipoRecursoRepository.saveAndFlush(tipoRecurso);

        // Get all the tipoRecursoList where descricao does not contain DEFAULT_DESCRICAO
        defaultTipoRecursoShouldNotBeFound("descricao.doesNotContain=" + DEFAULT_DESCRICAO);

        // Get all the tipoRecursoList where descricao does not contain UPDATED_DESCRICAO
        defaultTipoRecursoShouldBeFound("descricao.doesNotContain=" + UPDATED_DESCRICAO);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTipoRecursoShouldBeFound(String filter) throws Exception {
        restTipoRecursoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoRecurso.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)));

        // Check, that the count call also returns 1
        restTipoRecursoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTipoRecursoShouldNotBeFound(String filter) throws Exception {
        restTipoRecursoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTipoRecursoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingTipoRecurso() throws Exception {
        // Get the tipoRecurso
        restTipoRecursoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTipoRecurso() throws Exception {
        // Initialize the database
        tipoRecursoRepository.saveAndFlush(tipoRecurso);

        int databaseSizeBeforeUpdate = tipoRecursoRepository.findAll().size();

        // Update the tipoRecurso
        TipoRecurso updatedTipoRecurso = tipoRecursoRepository.findById(tipoRecurso.getId()).get();
        // Disconnect from session so that the updates on updatedTipoRecurso are not directly saved in db
        em.detach(updatedTipoRecurso);
        updatedTipoRecurso.descricao(UPDATED_DESCRICAO);

        restTipoRecursoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTipoRecurso.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedTipoRecurso))
            )
            .andExpect(status().isOk());

        // Validate the TipoRecurso in the database
        List<TipoRecurso> tipoRecursoList = tipoRecursoRepository.findAll();
        assertThat(tipoRecursoList).hasSize(databaseSizeBeforeUpdate);
        TipoRecurso testTipoRecurso = tipoRecursoList.get(tipoRecursoList.size() - 1);
        assertThat(testTipoRecurso.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void putNonExistingTipoRecurso() throws Exception {
        int databaseSizeBeforeUpdate = tipoRecursoRepository.findAll().size();
        tipoRecurso.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTipoRecursoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tipoRecurso.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tipoRecurso))
            )
            .andExpect(status().isBadRequest());

        // Validate the TipoRecurso in the database
        List<TipoRecurso> tipoRecursoList = tipoRecursoRepository.findAll();
        assertThat(tipoRecursoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTipoRecurso() throws Exception {
        int databaseSizeBeforeUpdate = tipoRecursoRepository.findAll().size();
        tipoRecurso.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTipoRecursoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tipoRecurso))
            )
            .andExpect(status().isBadRequest());

        // Validate the TipoRecurso in the database
        List<TipoRecurso> tipoRecursoList = tipoRecursoRepository.findAll();
        assertThat(tipoRecursoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTipoRecurso() throws Exception {
        int databaseSizeBeforeUpdate = tipoRecursoRepository.findAll().size();
        tipoRecurso.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTipoRecursoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tipoRecurso)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TipoRecurso in the database
        List<TipoRecurso> tipoRecursoList = tipoRecursoRepository.findAll();
        assertThat(tipoRecursoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTipoRecursoWithPatch() throws Exception {
        // Initialize the database
        tipoRecursoRepository.saveAndFlush(tipoRecurso);

        int databaseSizeBeforeUpdate = tipoRecursoRepository.findAll().size();

        // Update the tipoRecurso using partial update
        TipoRecurso partialUpdatedTipoRecurso = new TipoRecurso();
        partialUpdatedTipoRecurso.setId(tipoRecurso.getId());

        partialUpdatedTipoRecurso.descricao(UPDATED_DESCRICAO);

        restTipoRecursoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTipoRecurso.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTipoRecurso))
            )
            .andExpect(status().isOk());

        // Validate the TipoRecurso in the database
        List<TipoRecurso> tipoRecursoList = tipoRecursoRepository.findAll();
        assertThat(tipoRecursoList).hasSize(databaseSizeBeforeUpdate);
        TipoRecurso testTipoRecurso = tipoRecursoList.get(tipoRecursoList.size() - 1);
        assertThat(testTipoRecurso.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void fullUpdateTipoRecursoWithPatch() throws Exception {
        // Initialize the database
        tipoRecursoRepository.saveAndFlush(tipoRecurso);

        int databaseSizeBeforeUpdate = tipoRecursoRepository.findAll().size();

        // Update the tipoRecurso using partial update
        TipoRecurso partialUpdatedTipoRecurso = new TipoRecurso();
        partialUpdatedTipoRecurso.setId(tipoRecurso.getId());

        partialUpdatedTipoRecurso.descricao(UPDATED_DESCRICAO);

        restTipoRecursoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTipoRecurso.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTipoRecurso))
            )
            .andExpect(status().isOk());

        // Validate the TipoRecurso in the database
        List<TipoRecurso> tipoRecursoList = tipoRecursoRepository.findAll();
        assertThat(tipoRecursoList).hasSize(databaseSizeBeforeUpdate);
        TipoRecurso testTipoRecurso = tipoRecursoList.get(tipoRecursoList.size() - 1);
        assertThat(testTipoRecurso.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void patchNonExistingTipoRecurso() throws Exception {
        int databaseSizeBeforeUpdate = tipoRecursoRepository.findAll().size();
        tipoRecurso.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTipoRecursoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, tipoRecurso.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tipoRecurso))
            )
            .andExpect(status().isBadRequest());

        // Validate the TipoRecurso in the database
        List<TipoRecurso> tipoRecursoList = tipoRecursoRepository.findAll();
        assertThat(tipoRecursoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTipoRecurso() throws Exception {
        int databaseSizeBeforeUpdate = tipoRecursoRepository.findAll().size();
        tipoRecurso.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTipoRecursoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tipoRecurso))
            )
            .andExpect(status().isBadRequest());

        // Validate the TipoRecurso in the database
        List<TipoRecurso> tipoRecursoList = tipoRecursoRepository.findAll();
        assertThat(tipoRecursoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTipoRecurso() throws Exception {
        int databaseSizeBeforeUpdate = tipoRecursoRepository.findAll().size();
        tipoRecurso.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTipoRecursoMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(tipoRecurso))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TipoRecurso in the database
        List<TipoRecurso> tipoRecursoList = tipoRecursoRepository.findAll();
        assertThat(tipoRecursoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTipoRecurso() throws Exception {
        // Initialize the database
        tipoRecursoRepository.saveAndFlush(tipoRecurso);

        int databaseSizeBeforeDelete = tipoRecursoRepository.findAll().size();

        // Delete the tipoRecurso
        restTipoRecursoMockMvc
            .perform(delete(ENTITY_API_URL_ID, tipoRecurso.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TipoRecurso> tipoRecursoList = tipoRecursoRepository.findAll();
        assertThat(tipoRecursoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
