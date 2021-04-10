package br.com.cidha.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.cidha.IntegrationTest;
import br.com.cidha.domain.TipoDecisao;
import br.com.cidha.repository.TipoDecisaoRepository;
import br.com.cidha.service.criteria.TipoDecisaoCriteria;
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
 * Integration tests for the {@link TipoDecisaoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TipoDecisaoResourceIT {

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/tipo-decisaos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TipoDecisaoRepository tipoDecisaoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTipoDecisaoMockMvc;

    private TipoDecisao tipoDecisao;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoDecisao createEntity(EntityManager em) {
        TipoDecisao tipoDecisao = new TipoDecisao().descricao(DEFAULT_DESCRICAO);
        return tipoDecisao;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoDecisao createUpdatedEntity(EntityManager em) {
        TipoDecisao tipoDecisao = new TipoDecisao().descricao(UPDATED_DESCRICAO);
        return tipoDecisao;
    }

    @BeforeEach
    public void initTest() {
        tipoDecisao = createEntity(em);
    }

    @Test
    @Transactional
    void createTipoDecisao() throws Exception {
        int databaseSizeBeforeCreate = tipoDecisaoRepository.findAll().size();
        // Create the TipoDecisao
        restTipoDecisaoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tipoDecisao)))
            .andExpect(status().isCreated());

        // Validate the TipoDecisao in the database
        List<TipoDecisao> tipoDecisaoList = tipoDecisaoRepository.findAll();
        assertThat(tipoDecisaoList).hasSize(databaseSizeBeforeCreate + 1);
        TipoDecisao testTipoDecisao = tipoDecisaoList.get(tipoDecisaoList.size() - 1);
        assertThat(testTipoDecisao.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
    }

    @Test
    @Transactional
    void createTipoDecisaoWithExistingId() throws Exception {
        // Create the TipoDecisao with an existing ID
        tipoDecisao.setId(1L);

        int databaseSizeBeforeCreate = tipoDecisaoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTipoDecisaoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tipoDecisao)))
            .andExpect(status().isBadRequest());

        // Validate the TipoDecisao in the database
        List<TipoDecisao> tipoDecisaoList = tipoDecisaoRepository.findAll();
        assertThat(tipoDecisaoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTipoDecisaos() throws Exception {
        // Initialize the database
        tipoDecisaoRepository.saveAndFlush(tipoDecisao);

        // Get all the tipoDecisaoList
        restTipoDecisaoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoDecisao.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)));
    }

    @Test
    @Transactional
    void getTipoDecisao() throws Exception {
        // Initialize the database
        tipoDecisaoRepository.saveAndFlush(tipoDecisao);

        // Get the tipoDecisao
        restTipoDecisaoMockMvc
            .perform(get(ENTITY_API_URL_ID, tipoDecisao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tipoDecisao.getId().intValue()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO));
    }

    @Test
    @Transactional
    void getTipoDecisaosByIdFiltering() throws Exception {
        // Initialize the database
        tipoDecisaoRepository.saveAndFlush(tipoDecisao);

        Long id = tipoDecisao.getId();

        defaultTipoDecisaoShouldBeFound("id.equals=" + id);
        defaultTipoDecisaoShouldNotBeFound("id.notEquals=" + id);

        defaultTipoDecisaoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTipoDecisaoShouldNotBeFound("id.greaterThan=" + id);

        defaultTipoDecisaoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTipoDecisaoShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllTipoDecisaosByDescricaoIsEqualToSomething() throws Exception {
        // Initialize the database
        tipoDecisaoRepository.saveAndFlush(tipoDecisao);

        // Get all the tipoDecisaoList where descricao equals to DEFAULT_DESCRICAO
        defaultTipoDecisaoShouldBeFound("descricao.equals=" + DEFAULT_DESCRICAO);

        // Get all the tipoDecisaoList where descricao equals to UPDATED_DESCRICAO
        defaultTipoDecisaoShouldNotBeFound("descricao.equals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllTipoDecisaosByDescricaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tipoDecisaoRepository.saveAndFlush(tipoDecisao);

        // Get all the tipoDecisaoList where descricao not equals to DEFAULT_DESCRICAO
        defaultTipoDecisaoShouldNotBeFound("descricao.notEquals=" + DEFAULT_DESCRICAO);

        // Get all the tipoDecisaoList where descricao not equals to UPDATED_DESCRICAO
        defaultTipoDecisaoShouldBeFound("descricao.notEquals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllTipoDecisaosByDescricaoIsInShouldWork() throws Exception {
        // Initialize the database
        tipoDecisaoRepository.saveAndFlush(tipoDecisao);

        // Get all the tipoDecisaoList where descricao in DEFAULT_DESCRICAO or UPDATED_DESCRICAO
        defaultTipoDecisaoShouldBeFound("descricao.in=" + DEFAULT_DESCRICAO + "," + UPDATED_DESCRICAO);

        // Get all the tipoDecisaoList where descricao equals to UPDATED_DESCRICAO
        defaultTipoDecisaoShouldNotBeFound("descricao.in=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllTipoDecisaosByDescricaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        tipoDecisaoRepository.saveAndFlush(tipoDecisao);

        // Get all the tipoDecisaoList where descricao is not null
        defaultTipoDecisaoShouldBeFound("descricao.specified=true");

        // Get all the tipoDecisaoList where descricao is null
        defaultTipoDecisaoShouldNotBeFound("descricao.specified=false");
    }

    @Test
    @Transactional
    void getAllTipoDecisaosByDescricaoContainsSomething() throws Exception {
        // Initialize the database
        tipoDecisaoRepository.saveAndFlush(tipoDecisao);

        // Get all the tipoDecisaoList where descricao contains DEFAULT_DESCRICAO
        defaultTipoDecisaoShouldBeFound("descricao.contains=" + DEFAULT_DESCRICAO);

        // Get all the tipoDecisaoList where descricao contains UPDATED_DESCRICAO
        defaultTipoDecisaoShouldNotBeFound("descricao.contains=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllTipoDecisaosByDescricaoNotContainsSomething() throws Exception {
        // Initialize the database
        tipoDecisaoRepository.saveAndFlush(tipoDecisao);

        // Get all the tipoDecisaoList where descricao does not contain DEFAULT_DESCRICAO
        defaultTipoDecisaoShouldNotBeFound("descricao.doesNotContain=" + DEFAULT_DESCRICAO);

        // Get all the tipoDecisaoList where descricao does not contain UPDATED_DESCRICAO
        defaultTipoDecisaoShouldBeFound("descricao.doesNotContain=" + UPDATED_DESCRICAO);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTipoDecisaoShouldBeFound(String filter) throws Exception {
        restTipoDecisaoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoDecisao.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)));

        // Check, that the count call also returns 1
        restTipoDecisaoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTipoDecisaoShouldNotBeFound(String filter) throws Exception {
        restTipoDecisaoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTipoDecisaoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingTipoDecisao() throws Exception {
        // Get the tipoDecisao
        restTipoDecisaoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTipoDecisao() throws Exception {
        // Initialize the database
        tipoDecisaoRepository.saveAndFlush(tipoDecisao);

        int databaseSizeBeforeUpdate = tipoDecisaoRepository.findAll().size();

        // Update the tipoDecisao
        TipoDecisao updatedTipoDecisao = tipoDecisaoRepository.findById(tipoDecisao.getId()).get();
        // Disconnect from session so that the updates on updatedTipoDecisao are not directly saved in db
        em.detach(updatedTipoDecisao);
        updatedTipoDecisao.descricao(UPDATED_DESCRICAO);

        restTipoDecisaoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTipoDecisao.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedTipoDecisao))
            )
            .andExpect(status().isOk());

        // Validate the TipoDecisao in the database
        List<TipoDecisao> tipoDecisaoList = tipoDecisaoRepository.findAll();
        assertThat(tipoDecisaoList).hasSize(databaseSizeBeforeUpdate);
        TipoDecisao testTipoDecisao = tipoDecisaoList.get(tipoDecisaoList.size() - 1);
        assertThat(testTipoDecisao.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void putNonExistingTipoDecisao() throws Exception {
        int databaseSizeBeforeUpdate = tipoDecisaoRepository.findAll().size();
        tipoDecisao.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTipoDecisaoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tipoDecisao.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tipoDecisao))
            )
            .andExpect(status().isBadRequest());

        // Validate the TipoDecisao in the database
        List<TipoDecisao> tipoDecisaoList = tipoDecisaoRepository.findAll();
        assertThat(tipoDecisaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTipoDecisao() throws Exception {
        int databaseSizeBeforeUpdate = tipoDecisaoRepository.findAll().size();
        tipoDecisao.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTipoDecisaoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tipoDecisao))
            )
            .andExpect(status().isBadRequest());

        // Validate the TipoDecisao in the database
        List<TipoDecisao> tipoDecisaoList = tipoDecisaoRepository.findAll();
        assertThat(tipoDecisaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTipoDecisao() throws Exception {
        int databaseSizeBeforeUpdate = tipoDecisaoRepository.findAll().size();
        tipoDecisao.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTipoDecisaoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tipoDecisao)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TipoDecisao in the database
        List<TipoDecisao> tipoDecisaoList = tipoDecisaoRepository.findAll();
        assertThat(tipoDecisaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTipoDecisaoWithPatch() throws Exception {
        // Initialize the database
        tipoDecisaoRepository.saveAndFlush(tipoDecisao);

        int databaseSizeBeforeUpdate = tipoDecisaoRepository.findAll().size();

        // Update the tipoDecisao using partial update
        TipoDecisao partialUpdatedTipoDecisao = new TipoDecisao();
        partialUpdatedTipoDecisao.setId(tipoDecisao.getId());

        restTipoDecisaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTipoDecisao.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTipoDecisao))
            )
            .andExpect(status().isOk());

        // Validate the TipoDecisao in the database
        List<TipoDecisao> tipoDecisaoList = tipoDecisaoRepository.findAll();
        assertThat(tipoDecisaoList).hasSize(databaseSizeBeforeUpdate);
        TipoDecisao testTipoDecisao = tipoDecisaoList.get(tipoDecisaoList.size() - 1);
        assertThat(testTipoDecisao.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
    }

    @Test
    @Transactional
    void fullUpdateTipoDecisaoWithPatch() throws Exception {
        // Initialize the database
        tipoDecisaoRepository.saveAndFlush(tipoDecisao);

        int databaseSizeBeforeUpdate = tipoDecisaoRepository.findAll().size();

        // Update the tipoDecisao using partial update
        TipoDecisao partialUpdatedTipoDecisao = new TipoDecisao();
        partialUpdatedTipoDecisao.setId(tipoDecisao.getId());

        partialUpdatedTipoDecisao.descricao(UPDATED_DESCRICAO);

        restTipoDecisaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTipoDecisao.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTipoDecisao))
            )
            .andExpect(status().isOk());

        // Validate the TipoDecisao in the database
        List<TipoDecisao> tipoDecisaoList = tipoDecisaoRepository.findAll();
        assertThat(tipoDecisaoList).hasSize(databaseSizeBeforeUpdate);
        TipoDecisao testTipoDecisao = tipoDecisaoList.get(tipoDecisaoList.size() - 1);
        assertThat(testTipoDecisao.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void patchNonExistingTipoDecisao() throws Exception {
        int databaseSizeBeforeUpdate = tipoDecisaoRepository.findAll().size();
        tipoDecisao.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTipoDecisaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, tipoDecisao.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tipoDecisao))
            )
            .andExpect(status().isBadRequest());

        // Validate the TipoDecisao in the database
        List<TipoDecisao> tipoDecisaoList = tipoDecisaoRepository.findAll();
        assertThat(tipoDecisaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTipoDecisao() throws Exception {
        int databaseSizeBeforeUpdate = tipoDecisaoRepository.findAll().size();
        tipoDecisao.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTipoDecisaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tipoDecisao))
            )
            .andExpect(status().isBadRequest());

        // Validate the TipoDecisao in the database
        List<TipoDecisao> tipoDecisaoList = tipoDecisaoRepository.findAll();
        assertThat(tipoDecisaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTipoDecisao() throws Exception {
        int databaseSizeBeforeUpdate = tipoDecisaoRepository.findAll().size();
        tipoDecisao.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTipoDecisaoMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(tipoDecisao))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TipoDecisao in the database
        List<TipoDecisao> tipoDecisaoList = tipoDecisaoRepository.findAll();
        assertThat(tipoDecisaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTipoDecisao() throws Exception {
        // Initialize the database
        tipoDecisaoRepository.saveAndFlush(tipoDecisao);

        int databaseSizeBeforeDelete = tipoDecisaoRepository.findAll().size();

        // Delete the tipoDecisao
        restTipoDecisaoMockMvc
            .perform(delete(ENTITY_API_URL_ID, tipoDecisao.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TipoDecisao> tipoDecisaoList = tipoDecisaoRepository.findAll();
        assertThat(tipoDecisaoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
