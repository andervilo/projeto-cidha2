package br.com.cidha.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.cidha.IntegrationTest;
import br.com.cidha.domain.EmbargoDeclaracaoAgravo;
import br.com.cidha.domain.Processo;
import br.com.cidha.repository.EmbargoDeclaracaoAgravoRepository;
import br.com.cidha.service.criteria.EmbargoDeclaracaoAgravoCriteria;
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
 * Integration tests for the {@link EmbargoDeclaracaoAgravoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EmbargoDeclaracaoAgravoResourceIT {

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/embargo-declaracao-agravos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EmbargoDeclaracaoAgravoRepository embargoDeclaracaoAgravoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEmbargoDeclaracaoAgravoMockMvc;

    private EmbargoDeclaracaoAgravo embargoDeclaracaoAgravo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmbargoDeclaracaoAgravo createEntity(EntityManager em) {
        EmbargoDeclaracaoAgravo embargoDeclaracaoAgravo = new EmbargoDeclaracaoAgravo().descricao(DEFAULT_DESCRICAO);
        return embargoDeclaracaoAgravo;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmbargoDeclaracaoAgravo createUpdatedEntity(EntityManager em) {
        EmbargoDeclaracaoAgravo embargoDeclaracaoAgravo = new EmbargoDeclaracaoAgravo().descricao(UPDATED_DESCRICAO);
        return embargoDeclaracaoAgravo;
    }

    @BeforeEach
    public void initTest() {
        embargoDeclaracaoAgravo = createEntity(em);
    }

    @Test
    @Transactional
    void createEmbargoDeclaracaoAgravo() throws Exception {
        int databaseSizeBeforeCreate = embargoDeclaracaoAgravoRepository.findAll().size();
        // Create the EmbargoDeclaracaoAgravo
        restEmbargoDeclaracaoAgravoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(embargoDeclaracaoAgravo))
            )
            .andExpect(status().isCreated());

        // Validate the EmbargoDeclaracaoAgravo in the database
        List<EmbargoDeclaracaoAgravo> embargoDeclaracaoAgravoList = embargoDeclaracaoAgravoRepository.findAll();
        assertThat(embargoDeclaracaoAgravoList).hasSize(databaseSizeBeforeCreate + 1);
        EmbargoDeclaracaoAgravo testEmbargoDeclaracaoAgravo = embargoDeclaracaoAgravoList.get(embargoDeclaracaoAgravoList.size() - 1);
        assertThat(testEmbargoDeclaracaoAgravo.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
    }

    @Test
    @Transactional
    void createEmbargoDeclaracaoAgravoWithExistingId() throws Exception {
        // Create the EmbargoDeclaracaoAgravo with an existing ID
        embargoDeclaracaoAgravo.setId(1L);

        int databaseSizeBeforeCreate = embargoDeclaracaoAgravoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmbargoDeclaracaoAgravoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(embargoDeclaracaoAgravo))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmbargoDeclaracaoAgravo in the database
        List<EmbargoDeclaracaoAgravo> embargoDeclaracaoAgravoList = embargoDeclaracaoAgravoRepository.findAll();
        assertThat(embargoDeclaracaoAgravoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEmbargoDeclaracaoAgravos() throws Exception {
        // Initialize the database
        embargoDeclaracaoAgravoRepository.saveAndFlush(embargoDeclaracaoAgravo);

        // Get all the embargoDeclaracaoAgravoList
        restEmbargoDeclaracaoAgravoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(embargoDeclaracaoAgravo.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)));
    }

    @Test
    @Transactional
    void getEmbargoDeclaracaoAgravo() throws Exception {
        // Initialize the database
        embargoDeclaracaoAgravoRepository.saveAndFlush(embargoDeclaracaoAgravo);

        // Get the embargoDeclaracaoAgravo
        restEmbargoDeclaracaoAgravoMockMvc
            .perform(get(ENTITY_API_URL_ID, embargoDeclaracaoAgravo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(embargoDeclaracaoAgravo.getId().intValue()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO));
    }

    @Test
    @Transactional
    void getEmbargoDeclaracaoAgravosByIdFiltering() throws Exception {
        // Initialize the database
        embargoDeclaracaoAgravoRepository.saveAndFlush(embargoDeclaracaoAgravo);

        Long id = embargoDeclaracaoAgravo.getId();

        defaultEmbargoDeclaracaoAgravoShouldBeFound("id.equals=" + id);
        defaultEmbargoDeclaracaoAgravoShouldNotBeFound("id.notEquals=" + id);

        defaultEmbargoDeclaracaoAgravoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultEmbargoDeclaracaoAgravoShouldNotBeFound("id.greaterThan=" + id);

        defaultEmbargoDeclaracaoAgravoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultEmbargoDeclaracaoAgravoShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllEmbargoDeclaracaoAgravosByDescricaoIsEqualToSomething() throws Exception {
        // Initialize the database
        embargoDeclaracaoAgravoRepository.saveAndFlush(embargoDeclaracaoAgravo);

        // Get all the embargoDeclaracaoAgravoList where descricao equals to DEFAULT_DESCRICAO
        defaultEmbargoDeclaracaoAgravoShouldBeFound("descricao.equals=" + DEFAULT_DESCRICAO);

        // Get all the embargoDeclaracaoAgravoList where descricao equals to UPDATED_DESCRICAO
        defaultEmbargoDeclaracaoAgravoShouldNotBeFound("descricao.equals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllEmbargoDeclaracaoAgravosByDescricaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        embargoDeclaracaoAgravoRepository.saveAndFlush(embargoDeclaracaoAgravo);

        // Get all the embargoDeclaracaoAgravoList where descricao not equals to DEFAULT_DESCRICAO
        defaultEmbargoDeclaracaoAgravoShouldNotBeFound("descricao.notEquals=" + DEFAULT_DESCRICAO);

        // Get all the embargoDeclaracaoAgravoList where descricao not equals to UPDATED_DESCRICAO
        defaultEmbargoDeclaracaoAgravoShouldBeFound("descricao.notEquals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllEmbargoDeclaracaoAgravosByDescricaoIsInShouldWork() throws Exception {
        // Initialize the database
        embargoDeclaracaoAgravoRepository.saveAndFlush(embargoDeclaracaoAgravo);

        // Get all the embargoDeclaracaoAgravoList where descricao in DEFAULT_DESCRICAO or UPDATED_DESCRICAO
        defaultEmbargoDeclaracaoAgravoShouldBeFound("descricao.in=" + DEFAULT_DESCRICAO + "," + UPDATED_DESCRICAO);

        // Get all the embargoDeclaracaoAgravoList where descricao equals to UPDATED_DESCRICAO
        defaultEmbargoDeclaracaoAgravoShouldNotBeFound("descricao.in=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllEmbargoDeclaracaoAgravosByDescricaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        embargoDeclaracaoAgravoRepository.saveAndFlush(embargoDeclaracaoAgravo);

        // Get all the embargoDeclaracaoAgravoList where descricao is not null
        defaultEmbargoDeclaracaoAgravoShouldBeFound("descricao.specified=true");

        // Get all the embargoDeclaracaoAgravoList where descricao is null
        defaultEmbargoDeclaracaoAgravoShouldNotBeFound("descricao.specified=false");
    }

    @Test
    @Transactional
    void getAllEmbargoDeclaracaoAgravosByDescricaoContainsSomething() throws Exception {
        // Initialize the database
        embargoDeclaracaoAgravoRepository.saveAndFlush(embargoDeclaracaoAgravo);

        // Get all the embargoDeclaracaoAgravoList where descricao contains DEFAULT_DESCRICAO
        defaultEmbargoDeclaracaoAgravoShouldBeFound("descricao.contains=" + DEFAULT_DESCRICAO);

        // Get all the embargoDeclaracaoAgravoList where descricao contains UPDATED_DESCRICAO
        defaultEmbargoDeclaracaoAgravoShouldNotBeFound("descricao.contains=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllEmbargoDeclaracaoAgravosByDescricaoNotContainsSomething() throws Exception {
        // Initialize the database
        embargoDeclaracaoAgravoRepository.saveAndFlush(embargoDeclaracaoAgravo);

        // Get all the embargoDeclaracaoAgravoList where descricao does not contain DEFAULT_DESCRICAO
        defaultEmbargoDeclaracaoAgravoShouldNotBeFound("descricao.doesNotContain=" + DEFAULT_DESCRICAO);

        // Get all the embargoDeclaracaoAgravoList where descricao does not contain UPDATED_DESCRICAO
        defaultEmbargoDeclaracaoAgravoShouldBeFound("descricao.doesNotContain=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllEmbargoDeclaracaoAgravosByProcessoIsEqualToSomething() throws Exception {
        // Initialize the database
        embargoDeclaracaoAgravoRepository.saveAndFlush(embargoDeclaracaoAgravo);
        Processo processo = ProcessoResourceIT.createEntity(em);
        em.persist(processo);
        em.flush();
        embargoDeclaracaoAgravo.setProcesso(processo);
        embargoDeclaracaoAgravoRepository.saveAndFlush(embargoDeclaracaoAgravo);
        Long processoId = processo.getId();

        // Get all the embargoDeclaracaoAgravoList where processo equals to processoId
        defaultEmbargoDeclaracaoAgravoShouldBeFound("processoId.equals=" + processoId);

        // Get all the embargoDeclaracaoAgravoList where processo equals to (processoId + 1)
        defaultEmbargoDeclaracaoAgravoShouldNotBeFound("processoId.equals=" + (processoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEmbargoDeclaracaoAgravoShouldBeFound(String filter) throws Exception {
        restEmbargoDeclaracaoAgravoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(embargoDeclaracaoAgravo.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)));

        // Check, that the count call also returns 1
        restEmbargoDeclaracaoAgravoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEmbargoDeclaracaoAgravoShouldNotBeFound(String filter) throws Exception {
        restEmbargoDeclaracaoAgravoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEmbargoDeclaracaoAgravoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingEmbargoDeclaracaoAgravo() throws Exception {
        // Get the embargoDeclaracaoAgravo
        restEmbargoDeclaracaoAgravoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewEmbargoDeclaracaoAgravo() throws Exception {
        // Initialize the database
        embargoDeclaracaoAgravoRepository.saveAndFlush(embargoDeclaracaoAgravo);

        int databaseSizeBeforeUpdate = embargoDeclaracaoAgravoRepository.findAll().size();

        // Update the embargoDeclaracaoAgravo
        EmbargoDeclaracaoAgravo updatedEmbargoDeclaracaoAgravo = embargoDeclaracaoAgravoRepository
            .findById(embargoDeclaracaoAgravo.getId())
            .get();
        // Disconnect from session so that the updates on updatedEmbargoDeclaracaoAgravo are not directly saved in db
        em.detach(updatedEmbargoDeclaracaoAgravo);
        updatedEmbargoDeclaracaoAgravo.descricao(UPDATED_DESCRICAO);

        restEmbargoDeclaracaoAgravoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedEmbargoDeclaracaoAgravo.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedEmbargoDeclaracaoAgravo))
            )
            .andExpect(status().isOk());

        // Validate the EmbargoDeclaracaoAgravo in the database
        List<EmbargoDeclaracaoAgravo> embargoDeclaracaoAgravoList = embargoDeclaracaoAgravoRepository.findAll();
        assertThat(embargoDeclaracaoAgravoList).hasSize(databaseSizeBeforeUpdate);
        EmbargoDeclaracaoAgravo testEmbargoDeclaracaoAgravo = embargoDeclaracaoAgravoList.get(embargoDeclaracaoAgravoList.size() - 1);
        assertThat(testEmbargoDeclaracaoAgravo.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void putNonExistingEmbargoDeclaracaoAgravo() throws Exception {
        int databaseSizeBeforeUpdate = embargoDeclaracaoAgravoRepository.findAll().size();
        embargoDeclaracaoAgravo.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmbargoDeclaracaoAgravoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, embargoDeclaracaoAgravo.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(embargoDeclaracaoAgravo))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmbargoDeclaracaoAgravo in the database
        List<EmbargoDeclaracaoAgravo> embargoDeclaracaoAgravoList = embargoDeclaracaoAgravoRepository.findAll();
        assertThat(embargoDeclaracaoAgravoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEmbargoDeclaracaoAgravo() throws Exception {
        int databaseSizeBeforeUpdate = embargoDeclaracaoAgravoRepository.findAll().size();
        embargoDeclaracaoAgravo.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmbargoDeclaracaoAgravoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(embargoDeclaracaoAgravo))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmbargoDeclaracaoAgravo in the database
        List<EmbargoDeclaracaoAgravo> embargoDeclaracaoAgravoList = embargoDeclaracaoAgravoRepository.findAll();
        assertThat(embargoDeclaracaoAgravoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEmbargoDeclaracaoAgravo() throws Exception {
        int databaseSizeBeforeUpdate = embargoDeclaracaoAgravoRepository.findAll().size();
        embargoDeclaracaoAgravo.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmbargoDeclaracaoAgravoMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(embargoDeclaracaoAgravo))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EmbargoDeclaracaoAgravo in the database
        List<EmbargoDeclaracaoAgravo> embargoDeclaracaoAgravoList = embargoDeclaracaoAgravoRepository.findAll();
        assertThat(embargoDeclaracaoAgravoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEmbargoDeclaracaoAgravoWithPatch() throws Exception {
        // Initialize the database
        embargoDeclaracaoAgravoRepository.saveAndFlush(embargoDeclaracaoAgravo);

        int databaseSizeBeforeUpdate = embargoDeclaracaoAgravoRepository.findAll().size();

        // Update the embargoDeclaracaoAgravo using partial update
        EmbargoDeclaracaoAgravo partialUpdatedEmbargoDeclaracaoAgravo = new EmbargoDeclaracaoAgravo();
        partialUpdatedEmbargoDeclaracaoAgravo.setId(embargoDeclaracaoAgravo.getId());

        restEmbargoDeclaracaoAgravoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmbargoDeclaracaoAgravo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEmbargoDeclaracaoAgravo))
            )
            .andExpect(status().isOk());

        // Validate the EmbargoDeclaracaoAgravo in the database
        List<EmbargoDeclaracaoAgravo> embargoDeclaracaoAgravoList = embargoDeclaracaoAgravoRepository.findAll();
        assertThat(embargoDeclaracaoAgravoList).hasSize(databaseSizeBeforeUpdate);
        EmbargoDeclaracaoAgravo testEmbargoDeclaracaoAgravo = embargoDeclaracaoAgravoList.get(embargoDeclaracaoAgravoList.size() - 1);
        assertThat(testEmbargoDeclaracaoAgravo.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
    }

    @Test
    @Transactional
    void fullUpdateEmbargoDeclaracaoAgravoWithPatch() throws Exception {
        // Initialize the database
        embargoDeclaracaoAgravoRepository.saveAndFlush(embargoDeclaracaoAgravo);

        int databaseSizeBeforeUpdate = embargoDeclaracaoAgravoRepository.findAll().size();

        // Update the embargoDeclaracaoAgravo using partial update
        EmbargoDeclaracaoAgravo partialUpdatedEmbargoDeclaracaoAgravo = new EmbargoDeclaracaoAgravo();
        partialUpdatedEmbargoDeclaracaoAgravo.setId(embargoDeclaracaoAgravo.getId());

        partialUpdatedEmbargoDeclaracaoAgravo.descricao(UPDATED_DESCRICAO);

        restEmbargoDeclaracaoAgravoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmbargoDeclaracaoAgravo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEmbargoDeclaracaoAgravo))
            )
            .andExpect(status().isOk());

        // Validate the EmbargoDeclaracaoAgravo in the database
        List<EmbargoDeclaracaoAgravo> embargoDeclaracaoAgravoList = embargoDeclaracaoAgravoRepository.findAll();
        assertThat(embargoDeclaracaoAgravoList).hasSize(databaseSizeBeforeUpdate);
        EmbargoDeclaracaoAgravo testEmbargoDeclaracaoAgravo = embargoDeclaracaoAgravoList.get(embargoDeclaracaoAgravoList.size() - 1);
        assertThat(testEmbargoDeclaracaoAgravo.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void patchNonExistingEmbargoDeclaracaoAgravo() throws Exception {
        int databaseSizeBeforeUpdate = embargoDeclaracaoAgravoRepository.findAll().size();
        embargoDeclaracaoAgravo.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmbargoDeclaracaoAgravoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, embargoDeclaracaoAgravo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(embargoDeclaracaoAgravo))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmbargoDeclaracaoAgravo in the database
        List<EmbargoDeclaracaoAgravo> embargoDeclaracaoAgravoList = embargoDeclaracaoAgravoRepository.findAll();
        assertThat(embargoDeclaracaoAgravoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEmbargoDeclaracaoAgravo() throws Exception {
        int databaseSizeBeforeUpdate = embargoDeclaracaoAgravoRepository.findAll().size();
        embargoDeclaracaoAgravo.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmbargoDeclaracaoAgravoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(embargoDeclaracaoAgravo))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmbargoDeclaracaoAgravo in the database
        List<EmbargoDeclaracaoAgravo> embargoDeclaracaoAgravoList = embargoDeclaracaoAgravoRepository.findAll();
        assertThat(embargoDeclaracaoAgravoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEmbargoDeclaracaoAgravo() throws Exception {
        int databaseSizeBeforeUpdate = embargoDeclaracaoAgravoRepository.findAll().size();
        embargoDeclaracaoAgravo.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmbargoDeclaracaoAgravoMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(embargoDeclaracaoAgravo))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EmbargoDeclaracaoAgravo in the database
        List<EmbargoDeclaracaoAgravo> embargoDeclaracaoAgravoList = embargoDeclaracaoAgravoRepository.findAll();
        assertThat(embargoDeclaracaoAgravoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEmbargoDeclaracaoAgravo() throws Exception {
        // Initialize the database
        embargoDeclaracaoAgravoRepository.saveAndFlush(embargoDeclaracaoAgravo);

        int databaseSizeBeforeDelete = embargoDeclaracaoAgravoRepository.findAll().size();

        // Delete the embargoDeclaracaoAgravo
        restEmbargoDeclaracaoAgravoMockMvc
            .perform(delete(ENTITY_API_URL_ID, embargoDeclaracaoAgravo.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EmbargoDeclaracaoAgravo> embargoDeclaracaoAgravoList = embargoDeclaracaoAgravoRepository.findAll();
        assertThat(embargoDeclaracaoAgravoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
