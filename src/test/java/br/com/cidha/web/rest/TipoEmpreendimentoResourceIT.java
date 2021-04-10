package br.com.cidha.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.cidha.IntegrationTest;
import br.com.cidha.domain.TipoEmpreendimento;
import br.com.cidha.repository.TipoEmpreendimentoRepository;
import br.com.cidha.service.criteria.TipoEmpreendimentoCriteria;
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
 * Integration tests for the {@link TipoEmpreendimentoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TipoEmpreendimentoResourceIT {

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/tipo-empreendimentos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TipoEmpreendimentoRepository tipoEmpreendimentoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTipoEmpreendimentoMockMvc;

    private TipoEmpreendimento tipoEmpreendimento;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoEmpreendimento createEntity(EntityManager em) {
        TipoEmpreendimento tipoEmpreendimento = new TipoEmpreendimento().descricao(DEFAULT_DESCRICAO);
        return tipoEmpreendimento;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoEmpreendimento createUpdatedEntity(EntityManager em) {
        TipoEmpreendimento tipoEmpreendimento = new TipoEmpreendimento().descricao(UPDATED_DESCRICAO);
        return tipoEmpreendimento;
    }

    @BeforeEach
    public void initTest() {
        tipoEmpreendimento = createEntity(em);
    }

    @Test
    @Transactional
    void createTipoEmpreendimento() throws Exception {
        int databaseSizeBeforeCreate = tipoEmpreendimentoRepository.findAll().size();
        // Create the TipoEmpreendimento
        restTipoEmpreendimentoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tipoEmpreendimento))
            )
            .andExpect(status().isCreated());

        // Validate the TipoEmpreendimento in the database
        List<TipoEmpreendimento> tipoEmpreendimentoList = tipoEmpreendimentoRepository.findAll();
        assertThat(tipoEmpreendimentoList).hasSize(databaseSizeBeforeCreate + 1);
        TipoEmpreendimento testTipoEmpreendimento = tipoEmpreendimentoList.get(tipoEmpreendimentoList.size() - 1);
        assertThat(testTipoEmpreendimento.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
    }

    @Test
    @Transactional
    void createTipoEmpreendimentoWithExistingId() throws Exception {
        // Create the TipoEmpreendimento with an existing ID
        tipoEmpreendimento.setId(1L);

        int databaseSizeBeforeCreate = tipoEmpreendimentoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTipoEmpreendimentoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tipoEmpreendimento))
            )
            .andExpect(status().isBadRequest());

        // Validate the TipoEmpreendimento in the database
        List<TipoEmpreendimento> tipoEmpreendimentoList = tipoEmpreendimentoRepository.findAll();
        assertThat(tipoEmpreendimentoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTipoEmpreendimentos() throws Exception {
        // Initialize the database
        tipoEmpreendimentoRepository.saveAndFlush(tipoEmpreendimento);

        // Get all the tipoEmpreendimentoList
        restTipoEmpreendimentoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoEmpreendimento.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)));
    }

    @Test
    @Transactional
    void getTipoEmpreendimento() throws Exception {
        // Initialize the database
        tipoEmpreendimentoRepository.saveAndFlush(tipoEmpreendimento);

        // Get the tipoEmpreendimento
        restTipoEmpreendimentoMockMvc
            .perform(get(ENTITY_API_URL_ID, tipoEmpreendimento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tipoEmpreendimento.getId().intValue()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO));
    }

    @Test
    @Transactional
    void getTipoEmpreendimentosByIdFiltering() throws Exception {
        // Initialize the database
        tipoEmpreendimentoRepository.saveAndFlush(tipoEmpreendimento);

        Long id = tipoEmpreendimento.getId();

        defaultTipoEmpreendimentoShouldBeFound("id.equals=" + id);
        defaultTipoEmpreendimentoShouldNotBeFound("id.notEquals=" + id);

        defaultTipoEmpreendimentoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTipoEmpreendimentoShouldNotBeFound("id.greaterThan=" + id);

        defaultTipoEmpreendimentoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTipoEmpreendimentoShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllTipoEmpreendimentosByDescricaoIsEqualToSomething() throws Exception {
        // Initialize the database
        tipoEmpreendimentoRepository.saveAndFlush(tipoEmpreendimento);

        // Get all the tipoEmpreendimentoList where descricao equals to DEFAULT_DESCRICAO
        defaultTipoEmpreendimentoShouldBeFound("descricao.equals=" + DEFAULT_DESCRICAO);

        // Get all the tipoEmpreendimentoList where descricao equals to UPDATED_DESCRICAO
        defaultTipoEmpreendimentoShouldNotBeFound("descricao.equals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllTipoEmpreendimentosByDescricaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tipoEmpreendimentoRepository.saveAndFlush(tipoEmpreendimento);

        // Get all the tipoEmpreendimentoList where descricao not equals to DEFAULT_DESCRICAO
        defaultTipoEmpreendimentoShouldNotBeFound("descricao.notEquals=" + DEFAULT_DESCRICAO);

        // Get all the tipoEmpreendimentoList where descricao not equals to UPDATED_DESCRICAO
        defaultTipoEmpreendimentoShouldBeFound("descricao.notEquals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllTipoEmpreendimentosByDescricaoIsInShouldWork() throws Exception {
        // Initialize the database
        tipoEmpreendimentoRepository.saveAndFlush(tipoEmpreendimento);

        // Get all the tipoEmpreendimentoList where descricao in DEFAULT_DESCRICAO or UPDATED_DESCRICAO
        defaultTipoEmpreendimentoShouldBeFound("descricao.in=" + DEFAULT_DESCRICAO + "," + UPDATED_DESCRICAO);

        // Get all the tipoEmpreendimentoList where descricao equals to UPDATED_DESCRICAO
        defaultTipoEmpreendimentoShouldNotBeFound("descricao.in=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllTipoEmpreendimentosByDescricaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        tipoEmpreendimentoRepository.saveAndFlush(tipoEmpreendimento);

        // Get all the tipoEmpreendimentoList where descricao is not null
        defaultTipoEmpreendimentoShouldBeFound("descricao.specified=true");

        // Get all the tipoEmpreendimentoList where descricao is null
        defaultTipoEmpreendimentoShouldNotBeFound("descricao.specified=false");
    }

    @Test
    @Transactional
    void getAllTipoEmpreendimentosByDescricaoContainsSomething() throws Exception {
        // Initialize the database
        tipoEmpreendimentoRepository.saveAndFlush(tipoEmpreendimento);

        // Get all the tipoEmpreendimentoList where descricao contains DEFAULT_DESCRICAO
        defaultTipoEmpreendimentoShouldBeFound("descricao.contains=" + DEFAULT_DESCRICAO);

        // Get all the tipoEmpreendimentoList where descricao contains UPDATED_DESCRICAO
        defaultTipoEmpreendimentoShouldNotBeFound("descricao.contains=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllTipoEmpreendimentosByDescricaoNotContainsSomething() throws Exception {
        // Initialize the database
        tipoEmpreendimentoRepository.saveAndFlush(tipoEmpreendimento);

        // Get all the tipoEmpreendimentoList where descricao does not contain DEFAULT_DESCRICAO
        defaultTipoEmpreendimentoShouldNotBeFound("descricao.doesNotContain=" + DEFAULT_DESCRICAO);

        // Get all the tipoEmpreendimentoList where descricao does not contain UPDATED_DESCRICAO
        defaultTipoEmpreendimentoShouldBeFound("descricao.doesNotContain=" + UPDATED_DESCRICAO);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTipoEmpreendimentoShouldBeFound(String filter) throws Exception {
        restTipoEmpreendimentoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoEmpreendimento.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)));

        // Check, that the count call also returns 1
        restTipoEmpreendimentoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTipoEmpreendimentoShouldNotBeFound(String filter) throws Exception {
        restTipoEmpreendimentoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTipoEmpreendimentoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingTipoEmpreendimento() throws Exception {
        // Get the tipoEmpreendimento
        restTipoEmpreendimentoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTipoEmpreendimento() throws Exception {
        // Initialize the database
        tipoEmpreendimentoRepository.saveAndFlush(tipoEmpreendimento);

        int databaseSizeBeforeUpdate = tipoEmpreendimentoRepository.findAll().size();

        // Update the tipoEmpreendimento
        TipoEmpreendimento updatedTipoEmpreendimento = tipoEmpreendimentoRepository.findById(tipoEmpreendimento.getId()).get();
        // Disconnect from session so that the updates on updatedTipoEmpreendimento are not directly saved in db
        em.detach(updatedTipoEmpreendimento);
        updatedTipoEmpreendimento.descricao(UPDATED_DESCRICAO);

        restTipoEmpreendimentoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTipoEmpreendimento.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedTipoEmpreendimento))
            )
            .andExpect(status().isOk());

        // Validate the TipoEmpreendimento in the database
        List<TipoEmpreendimento> tipoEmpreendimentoList = tipoEmpreendimentoRepository.findAll();
        assertThat(tipoEmpreendimentoList).hasSize(databaseSizeBeforeUpdate);
        TipoEmpreendimento testTipoEmpreendimento = tipoEmpreendimentoList.get(tipoEmpreendimentoList.size() - 1);
        assertThat(testTipoEmpreendimento.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void putNonExistingTipoEmpreendimento() throws Exception {
        int databaseSizeBeforeUpdate = tipoEmpreendimentoRepository.findAll().size();
        tipoEmpreendimento.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTipoEmpreendimentoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tipoEmpreendimento.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tipoEmpreendimento))
            )
            .andExpect(status().isBadRequest());

        // Validate the TipoEmpreendimento in the database
        List<TipoEmpreendimento> tipoEmpreendimentoList = tipoEmpreendimentoRepository.findAll();
        assertThat(tipoEmpreendimentoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTipoEmpreendimento() throws Exception {
        int databaseSizeBeforeUpdate = tipoEmpreendimentoRepository.findAll().size();
        tipoEmpreendimento.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTipoEmpreendimentoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tipoEmpreendimento))
            )
            .andExpect(status().isBadRequest());

        // Validate the TipoEmpreendimento in the database
        List<TipoEmpreendimento> tipoEmpreendimentoList = tipoEmpreendimentoRepository.findAll();
        assertThat(tipoEmpreendimentoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTipoEmpreendimento() throws Exception {
        int databaseSizeBeforeUpdate = tipoEmpreendimentoRepository.findAll().size();
        tipoEmpreendimento.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTipoEmpreendimentoMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tipoEmpreendimento))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TipoEmpreendimento in the database
        List<TipoEmpreendimento> tipoEmpreendimentoList = tipoEmpreendimentoRepository.findAll();
        assertThat(tipoEmpreendimentoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTipoEmpreendimentoWithPatch() throws Exception {
        // Initialize the database
        tipoEmpreendimentoRepository.saveAndFlush(tipoEmpreendimento);

        int databaseSizeBeforeUpdate = tipoEmpreendimentoRepository.findAll().size();

        // Update the tipoEmpreendimento using partial update
        TipoEmpreendimento partialUpdatedTipoEmpreendimento = new TipoEmpreendimento();
        partialUpdatedTipoEmpreendimento.setId(tipoEmpreendimento.getId());

        restTipoEmpreendimentoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTipoEmpreendimento.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTipoEmpreendimento))
            )
            .andExpect(status().isOk());

        // Validate the TipoEmpreendimento in the database
        List<TipoEmpreendimento> tipoEmpreendimentoList = tipoEmpreendimentoRepository.findAll();
        assertThat(tipoEmpreendimentoList).hasSize(databaseSizeBeforeUpdate);
        TipoEmpreendimento testTipoEmpreendimento = tipoEmpreendimentoList.get(tipoEmpreendimentoList.size() - 1);
        assertThat(testTipoEmpreendimento.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
    }

    @Test
    @Transactional
    void fullUpdateTipoEmpreendimentoWithPatch() throws Exception {
        // Initialize the database
        tipoEmpreendimentoRepository.saveAndFlush(tipoEmpreendimento);

        int databaseSizeBeforeUpdate = tipoEmpreendimentoRepository.findAll().size();

        // Update the tipoEmpreendimento using partial update
        TipoEmpreendimento partialUpdatedTipoEmpreendimento = new TipoEmpreendimento();
        partialUpdatedTipoEmpreendimento.setId(tipoEmpreendimento.getId());

        partialUpdatedTipoEmpreendimento.descricao(UPDATED_DESCRICAO);

        restTipoEmpreendimentoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTipoEmpreendimento.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTipoEmpreendimento))
            )
            .andExpect(status().isOk());

        // Validate the TipoEmpreendimento in the database
        List<TipoEmpreendimento> tipoEmpreendimentoList = tipoEmpreendimentoRepository.findAll();
        assertThat(tipoEmpreendimentoList).hasSize(databaseSizeBeforeUpdate);
        TipoEmpreendimento testTipoEmpreendimento = tipoEmpreendimentoList.get(tipoEmpreendimentoList.size() - 1);
        assertThat(testTipoEmpreendimento.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void patchNonExistingTipoEmpreendimento() throws Exception {
        int databaseSizeBeforeUpdate = tipoEmpreendimentoRepository.findAll().size();
        tipoEmpreendimento.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTipoEmpreendimentoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, tipoEmpreendimento.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tipoEmpreendimento))
            )
            .andExpect(status().isBadRequest());

        // Validate the TipoEmpreendimento in the database
        List<TipoEmpreendimento> tipoEmpreendimentoList = tipoEmpreendimentoRepository.findAll();
        assertThat(tipoEmpreendimentoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTipoEmpreendimento() throws Exception {
        int databaseSizeBeforeUpdate = tipoEmpreendimentoRepository.findAll().size();
        tipoEmpreendimento.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTipoEmpreendimentoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tipoEmpreendimento))
            )
            .andExpect(status().isBadRequest());

        // Validate the TipoEmpreendimento in the database
        List<TipoEmpreendimento> tipoEmpreendimentoList = tipoEmpreendimentoRepository.findAll();
        assertThat(tipoEmpreendimentoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTipoEmpreendimento() throws Exception {
        int databaseSizeBeforeUpdate = tipoEmpreendimentoRepository.findAll().size();
        tipoEmpreendimento.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTipoEmpreendimentoMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tipoEmpreendimento))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TipoEmpreendimento in the database
        List<TipoEmpreendimento> tipoEmpreendimentoList = tipoEmpreendimentoRepository.findAll();
        assertThat(tipoEmpreendimentoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTipoEmpreendimento() throws Exception {
        // Initialize the database
        tipoEmpreendimentoRepository.saveAndFlush(tipoEmpreendimento);

        int databaseSizeBeforeDelete = tipoEmpreendimentoRepository.findAll().size();

        // Delete the tipoEmpreendimento
        restTipoEmpreendimentoMockMvc
            .perform(delete(ENTITY_API_URL_ID, tipoEmpreendimento.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TipoEmpreendimento> tipoEmpreendimentoList = tipoEmpreendimentoRepository.findAll();
        assertThat(tipoEmpreendimentoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
