package br.com.cidha.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.cidha.IntegrationTest;
import br.com.cidha.domain.EmbargoRespRe;
import br.com.cidha.domain.Processo;
import br.com.cidha.repository.EmbargoRespReRepository;
import br.com.cidha.service.criteria.EmbargoRespReCriteria;
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
 * Integration tests for the {@link EmbargoRespReResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EmbargoRespReResourceIT {

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/embargo-resp-res";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EmbargoRespReRepository embargoRespReRepository;

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
        EmbargoRespRe embargoRespRe = new EmbargoRespRe().descricao(DEFAULT_DESCRICAO);
        return embargoRespRe;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmbargoRespRe createUpdatedEntity(EntityManager em) {
        EmbargoRespRe embargoRespRe = new EmbargoRespRe().descricao(UPDATED_DESCRICAO);
        return embargoRespRe;
    }

    @BeforeEach
    public void initTest() {
        embargoRespRe = createEntity(em);
    }

    @Test
    @Transactional
    void createEmbargoRespRe() throws Exception {
        int databaseSizeBeforeCreate = embargoRespReRepository.findAll().size();
        // Create the EmbargoRespRe
        restEmbargoRespReMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(embargoRespRe)))
            .andExpect(status().isCreated());

        // Validate the EmbargoRespRe in the database
        List<EmbargoRespRe> embargoRespReList = embargoRespReRepository.findAll();
        assertThat(embargoRespReList).hasSize(databaseSizeBeforeCreate + 1);
        EmbargoRespRe testEmbargoRespRe = embargoRespReList.get(embargoRespReList.size() - 1);
        assertThat(testEmbargoRespRe.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
    }

    @Test
    @Transactional
    void createEmbargoRespReWithExistingId() throws Exception {
        // Create the EmbargoRespRe with an existing ID
        embargoRespRe.setId(1L);

        int databaseSizeBeforeCreate = embargoRespReRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmbargoRespReMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(embargoRespRe)))
            .andExpect(status().isBadRequest());

        // Validate the EmbargoRespRe in the database
        List<EmbargoRespRe> embargoRespReList = embargoRespReRepository.findAll();
        assertThat(embargoRespReList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEmbargoRespRes() throws Exception {
        // Initialize the database
        embargoRespReRepository.saveAndFlush(embargoRespRe);

        // Get all the embargoRespReList
        restEmbargoRespReMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(embargoRespRe.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)));
    }

    @Test
    @Transactional
    void getEmbargoRespRe() throws Exception {
        // Initialize the database
        embargoRespReRepository.saveAndFlush(embargoRespRe);

        // Get the embargoRespRe
        restEmbargoRespReMockMvc
            .perform(get(ENTITY_API_URL_ID, embargoRespRe.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(embargoRespRe.getId().intValue()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO));
    }

    @Test
    @Transactional
    void getEmbargoRespResByIdFiltering() throws Exception {
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
    void getAllEmbargoRespResByDescricaoIsEqualToSomething() throws Exception {
        // Initialize the database
        embargoRespReRepository.saveAndFlush(embargoRespRe);

        // Get all the embargoRespReList where descricao equals to DEFAULT_DESCRICAO
        defaultEmbargoRespReShouldBeFound("descricao.equals=" + DEFAULT_DESCRICAO);

        // Get all the embargoRespReList where descricao equals to UPDATED_DESCRICAO
        defaultEmbargoRespReShouldNotBeFound("descricao.equals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllEmbargoRespResByDescricaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        embargoRespReRepository.saveAndFlush(embargoRespRe);

        // Get all the embargoRespReList where descricao not equals to DEFAULT_DESCRICAO
        defaultEmbargoRespReShouldNotBeFound("descricao.notEquals=" + DEFAULT_DESCRICAO);

        // Get all the embargoRespReList where descricao not equals to UPDATED_DESCRICAO
        defaultEmbargoRespReShouldBeFound("descricao.notEquals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllEmbargoRespResByDescricaoIsInShouldWork() throws Exception {
        // Initialize the database
        embargoRespReRepository.saveAndFlush(embargoRespRe);

        // Get all the embargoRespReList where descricao in DEFAULT_DESCRICAO or UPDATED_DESCRICAO
        defaultEmbargoRespReShouldBeFound("descricao.in=" + DEFAULT_DESCRICAO + "," + UPDATED_DESCRICAO);

        // Get all the embargoRespReList where descricao equals to UPDATED_DESCRICAO
        defaultEmbargoRespReShouldNotBeFound("descricao.in=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllEmbargoRespResByDescricaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        embargoRespReRepository.saveAndFlush(embargoRespRe);

        // Get all the embargoRespReList where descricao is not null
        defaultEmbargoRespReShouldBeFound("descricao.specified=true");

        // Get all the embargoRespReList where descricao is null
        defaultEmbargoRespReShouldNotBeFound("descricao.specified=false");
    }

    @Test
    @Transactional
    void getAllEmbargoRespResByDescricaoContainsSomething() throws Exception {
        // Initialize the database
        embargoRespReRepository.saveAndFlush(embargoRespRe);

        // Get all the embargoRespReList where descricao contains DEFAULT_DESCRICAO
        defaultEmbargoRespReShouldBeFound("descricao.contains=" + DEFAULT_DESCRICAO);

        // Get all the embargoRespReList where descricao contains UPDATED_DESCRICAO
        defaultEmbargoRespReShouldNotBeFound("descricao.contains=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllEmbargoRespResByDescricaoNotContainsSomething() throws Exception {
        // Initialize the database
        embargoRespReRepository.saveAndFlush(embargoRespRe);

        // Get all the embargoRespReList where descricao does not contain DEFAULT_DESCRICAO
        defaultEmbargoRespReShouldNotBeFound("descricao.doesNotContain=" + DEFAULT_DESCRICAO);

        // Get all the embargoRespReList where descricao does not contain UPDATED_DESCRICAO
        defaultEmbargoRespReShouldBeFound("descricao.doesNotContain=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllEmbargoRespResByProcessoIsEqualToSomething() throws Exception {
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

        // Get all the embargoRespReList where processo equals to (processoId + 1)
        defaultEmbargoRespReShouldNotBeFound("processoId.equals=" + (processoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEmbargoRespReShouldBeFound(String filter) throws Exception {
        restEmbargoRespReMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(embargoRespRe.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)));

        // Check, that the count call also returns 1
        restEmbargoRespReMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEmbargoRespReShouldNotBeFound(String filter) throws Exception {
        restEmbargoRespReMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEmbargoRespReMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingEmbargoRespRe() throws Exception {
        // Get the embargoRespRe
        restEmbargoRespReMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewEmbargoRespRe() throws Exception {
        // Initialize the database
        embargoRespReRepository.saveAndFlush(embargoRespRe);

        int databaseSizeBeforeUpdate = embargoRespReRepository.findAll().size();

        // Update the embargoRespRe
        EmbargoRespRe updatedEmbargoRespRe = embargoRespReRepository.findById(embargoRespRe.getId()).get();
        // Disconnect from session so that the updates on updatedEmbargoRespRe are not directly saved in db
        em.detach(updatedEmbargoRespRe);
        updatedEmbargoRespRe.descricao(UPDATED_DESCRICAO);

        restEmbargoRespReMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedEmbargoRespRe.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedEmbargoRespRe))
            )
            .andExpect(status().isOk());

        // Validate the EmbargoRespRe in the database
        List<EmbargoRespRe> embargoRespReList = embargoRespReRepository.findAll();
        assertThat(embargoRespReList).hasSize(databaseSizeBeforeUpdate);
        EmbargoRespRe testEmbargoRespRe = embargoRespReList.get(embargoRespReList.size() - 1);
        assertThat(testEmbargoRespRe.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void putNonExistingEmbargoRespRe() throws Exception {
        int databaseSizeBeforeUpdate = embargoRespReRepository.findAll().size();
        embargoRespRe.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmbargoRespReMockMvc
            .perform(
                put(ENTITY_API_URL_ID, embargoRespRe.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(embargoRespRe))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmbargoRespRe in the database
        List<EmbargoRespRe> embargoRespReList = embargoRespReRepository.findAll();
        assertThat(embargoRespReList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEmbargoRespRe() throws Exception {
        int databaseSizeBeforeUpdate = embargoRespReRepository.findAll().size();
        embargoRespRe.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmbargoRespReMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(embargoRespRe))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmbargoRespRe in the database
        List<EmbargoRespRe> embargoRespReList = embargoRespReRepository.findAll();
        assertThat(embargoRespReList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEmbargoRespRe() throws Exception {
        int databaseSizeBeforeUpdate = embargoRespReRepository.findAll().size();
        embargoRespRe.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmbargoRespReMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(embargoRespRe)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the EmbargoRespRe in the database
        List<EmbargoRespRe> embargoRespReList = embargoRespReRepository.findAll();
        assertThat(embargoRespReList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEmbargoRespReWithPatch() throws Exception {
        // Initialize the database
        embargoRespReRepository.saveAndFlush(embargoRespRe);

        int databaseSizeBeforeUpdate = embargoRespReRepository.findAll().size();

        // Update the embargoRespRe using partial update
        EmbargoRespRe partialUpdatedEmbargoRespRe = new EmbargoRespRe();
        partialUpdatedEmbargoRespRe.setId(embargoRespRe.getId());

        partialUpdatedEmbargoRespRe.descricao(UPDATED_DESCRICAO);

        restEmbargoRespReMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmbargoRespRe.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEmbargoRespRe))
            )
            .andExpect(status().isOk());

        // Validate the EmbargoRespRe in the database
        List<EmbargoRespRe> embargoRespReList = embargoRespReRepository.findAll();
        assertThat(embargoRespReList).hasSize(databaseSizeBeforeUpdate);
        EmbargoRespRe testEmbargoRespRe = embargoRespReList.get(embargoRespReList.size() - 1);
        assertThat(testEmbargoRespRe.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void fullUpdateEmbargoRespReWithPatch() throws Exception {
        // Initialize the database
        embargoRespReRepository.saveAndFlush(embargoRespRe);

        int databaseSizeBeforeUpdate = embargoRespReRepository.findAll().size();

        // Update the embargoRespRe using partial update
        EmbargoRespRe partialUpdatedEmbargoRespRe = new EmbargoRespRe();
        partialUpdatedEmbargoRespRe.setId(embargoRespRe.getId());

        partialUpdatedEmbargoRespRe.descricao(UPDATED_DESCRICAO);

        restEmbargoRespReMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmbargoRespRe.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEmbargoRespRe))
            )
            .andExpect(status().isOk());

        // Validate the EmbargoRespRe in the database
        List<EmbargoRespRe> embargoRespReList = embargoRespReRepository.findAll();
        assertThat(embargoRespReList).hasSize(databaseSizeBeforeUpdate);
        EmbargoRespRe testEmbargoRespRe = embargoRespReList.get(embargoRespReList.size() - 1);
        assertThat(testEmbargoRespRe.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void patchNonExistingEmbargoRespRe() throws Exception {
        int databaseSizeBeforeUpdate = embargoRespReRepository.findAll().size();
        embargoRespRe.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmbargoRespReMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, embargoRespRe.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(embargoRespRe))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmbargoRespRe in the database
        List<EmbargoRespRe> embargoRespReList = embargoRespReRepository.findAll();
        assertThat(embargoRespReList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEmbargoRespRe() throws Exception {
        int databaseSizeBeforeUpdate = embargoRespReRepository.findAll().size();
        embargoRespRe.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmbargoRespReMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(embargoRespRe))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmbargoRespRe in the database
        List<EmbargoRespRe> embargoRespReList = embargoRespReRepository.findAll();
        assertThat(embargoRespReList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEmbargoRespRe() throws Exception {
        int databaseSizeBeforeUpdate = embargoRespReRepository.findAll().size();
        embargoRespRe.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmbargoRespReMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(embargoRespRe))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EmbargoRespRe in the database
        List<EmbargoRespRe> embargoRespReList = embargoRespReRepository.findAll();
        assertThat(embargoRespReList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEmbargoRespRe() throws Exception {
        // Initialize the database
        embargoRespReRepository.saveAndFlush(embargoRespRe);

        int databaseSizeBeforeDelete = embargoRespReRepository.findAll().size();

        // Delete the embargoRespRe
        restEmbargoRespReMockMvc
            .perform(delete(ENTITY_API_URL_ID, embargoRespRe.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EmbargoRespRe> embargoRespReList = embargoRespReRepository.findAll();
        assertThat(embargoRespReList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
