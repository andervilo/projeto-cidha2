package br.com.cidha.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.cidha.IntegrationTest;
import br.com.cidha.domain.EmbargoDeclaracao;
import br.com.cidha.domain.Processo;
import br.com.cidha.repository.EmbargoDeclaracaoRepository;
import br.com.cidha.service.criteria.EmbargoDeclaracaoCriteria;
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
 * Integration tests for the {@link EmbargoDeclaracaoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EmbargoDeclaracaoResourceIT {

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/embargo-declaracaos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EmbargoDeclaracaoRepository embargoDeclaracaoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEmbargoDeclaracaoMockMvc;

    private EmbargoDeclaracao embargoDeclaracao;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmbargoDeclaracao createEntity(EntityManager em) {
        EmbargoDeclaracao embargoDeclaracao = new EmbargoDeclaracao().descricao(DEFAULT_DESCRICAO);
        return embargoDeclaracao;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmbargoDeclaracao createUpdatedEntity(EntityManager em) {
        EmbargoDeclaracao embargoDeclaracao = new EmbargoDeclaracao().descricao(UPDATED_DESCRICAO);
        return embargoDeclaracao;
    }

    @BeforeEach
    public void initTest() {
        embargoDeclaracao = createEntity(em);
    }

    @Test
    @Transactional
    void createEmbargoDeclaracao() throws Exception {
        int databaseSizeBeforeCreate = embargoDeclaracaoRepository.findAll().size();
        // Create the EmbargoDeclaracao
        restEmbargoDeclaracaoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(embargoDeclaracao))
            )
            .andExpect(status().isCreated());

        // Validate the EmbargoDeclaracao in the database
        List<EmbargoDeclaracao> embargoDeclaracaoList = embargoDeclaracaoRepository.findAll();
        assertThat(embargoDeclaracaoList).hasSize(databaseSizeBeforeCreate + 1);
        EmbargoDeclaracao testEmbargoDeclaracao = embargoDeclaracaoList.get(embargoDeclaracaoList.size() - 1);
        assertThat(testEmbargoDeclaracao.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
    }

    @Test
    @Transactional
    void createEmbargoDeclaracaoWithExistingId() throws Exception {
        // Create the EmbargoDeclaracao with an existing ID
        embargoDeclaracao.setId(1L);

        int databaseSizeBeforeCreate = embargoDeclaracaoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmbargoDeclaracaoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(embargoDeclaracao))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmbargoDeclaracao in the database
        List<EmbargoDeclaracao> embargoDeclaracaoList = embargoDeclaracaoRepository.findAll();
        assertThat(embargoDeclaracaoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEmbargoDeclaracaos() throws Exception {
        // Initialize the database
        embargoDeclaracaoRepository.saveAndFlush(embargoDeclaracao);

        // Get all the embargoDeclaracaoList
        restEmbargoDeclaracaoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(embargoDeclaracao.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)));
    }

    @Test
    @Transactional
    void getEmbargoDeclaracao() throws Exception {
        // Initialize the database
        embargoDeclaracaoRepository.saveAndFlush(embargoDeclaracao);

        // Get the embargoDeclaracao
        restEmbargoDeclaracaoMockMvc
            .perform(get(ENTITY_API_URL_ID, embargoDeclaracao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(embargoDeclaracao.getId().intValue()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO));
    }

    @Test
    @Transactional
    void getEmbargoDeclaracaosByIdFiltering() throws Exception {
        // Initialize the database
        embargoDeclaracaoRepository.saveAndFlush(embargoDeclaracao);

        Long id = embargoDeclaracao.getId();

        defaultEmbargoDeclaracaoShouldBeFound("id.equals=" + id);
        defaultEmbargoDeclaracaoShouldNotBeFound("id.notEquals=" + id);

        defaultEmbargoDeclaracaoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultEmbargoDeclaracaoShouldNotBeFound("id.greaterThan=" + id);

        defaultEmbargoDeclaracaoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultEmbargoDeclaracaoShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllEmbargoDeclaracaosByDescricaoIsEqualToSomething() throws Exception {
        // Initialize the database
        embargoDeclaracaoRepository.saveAndFlush(embargoDeclaracao);

        // Get all the embargoDeclaracaoList where descricao equals to DEFAULT_DESCRICAO
        defaultEmbargoDeclaracaoShouldBeFound("descricao.equals=" + DEFAULT_DESCRICAO);

        // Get all the embargoDeclaracaoList where descricao equals to UPDATED_DESCRICAO
        defaultEmbargoDeclaracaoShouldNotBeFound("descricao.equals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllEmbargoDeclaracaosByDescricaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        embargoDeclaracaoRepository.saveAndFlush(embargoDeclaracao);

        // Get all the embargoDeclaracaoList where descricao not equals to DEFAULT_DESCRICAO
        defaultEmbargoDeclaracaoShouldNotBeFound("descricao.notEquals=" + DEFAULT_DESCRICAO);

        // Get all the embargoDeclaracaoList where descricao not equals to UPDATED_DESCRICAO
        defaultEmbargoDeclaracaoShouldBeFound("descricao.notEquals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllEmbargoDeclaracaosByDescricaoIsInShouldWork() throws Exception {
        // Initialize the database
        embargoDeclaracaoRepository.saveAndFlush(embargoDeclaracao);

        // Get all the embargoDeclaracaoList where descricao in DEFAULT_DESCRICAO or UPDATED_DESCRICAO
        defaultEmbargoDeclaracaoShouldBeFound("descricao.in=" + DEFAULT_DESCRICAO + "," + UPDATED_DESCRICAO);

        // Get all the embargoDeclaracaoList where descricao equals to UPDATED_DESCRICAO
        defaultEmbargoDeclaracaoShouldNotBeFound("descricao.in=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllEmbargoDeclaracaosByDescricaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        embargoDeclaracaoRepository.saveAndFlush(embargoDeclaracao);

        // Get all the embargoDeclaracaoList where descricao is not null
        defaultEmbargoDeclaracaoShouldBeFound("descricao.specified=true");

        // Get all the embargoDeclaracaoList where descricao is null
        defaultEmbargoDeclaracaoShouldNotBeFound("descricao.specified=false");
    }

    @Test
    @Transactional
    void getAllEmbargoDeclaracaosByDescricaoContainsSomething() throws Exception {
        // Initialize the database
        embargoDeclaracaoRepository.saveAndFlush(embargoDeclaracao);

        // Get all the embargoDeclaracaoList where descricao contains DEFAULT_DESCRICAO
        defaultEmbargoDeclaracaoShouldBeFound("descricao.contains=" + DEFAULT_DESCRICAO);

        // Get all the embargoDeclaracaoList where descricao contains UPDATED_DESCRICAO
        defaultEmbargoDeclaracaoShouldNotBeFound("descricao.contains=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllEmbargoDeclaracaosByDescricaoNotContainsSomething() throws Exception {
        // Initialize the database
        embargoDeclaracaoRepository.saveAndFlush(embargoDeclaracao);

        // Get all the embargoDeclaracaoList where descricao does not contain DEFAULT_DESCRICAO
        defaultEmbargoDeclaracaoShouldNotBeFound("descricao.doesNotContain=" + DEFAULT_DESCRICAO);

        // Get all the embargoDeclaracaoList where descricao does not contain UPDATED_DESCRICAO
        defaultEmbargoDeclaracaoShouldBeFound("descricao.doesNotContain=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllEmbargoDeclaracaosByProcessoIsEqualToSomething() throws Exception {
        // Initialize the database
        embargoDeclaracaoRepository.saveAndFlush(embargoDeclaracao);
        Processo processo = ProcessoResourceIT.createEntity(em);
        em.persist(processo);
        em.flush();
        embargoDeclaracao.setProcesso(processo);
        embargoDeclaracaoRepository.saveAndFlush(embargoDeclaracao);
        Long processoId = processo.getId();

        // Get all the embargoDeclaracaoList where processo equals to processoId
        defaultEmbargoDeclaracaoShouldBeFound("processoId.equals=" + processoId);

        // Get all the embargoDeclaracaoList where processo equals to (processoId + 1)
        defaultEmbargoDeclaracaoShouldNotBeFound("processoId.equals=" + (processoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEmbargoDeclaracaoShouldBeFound(String filter) throws Exception {
        restEmbargoDeclaracaoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(embargoDeclaracao.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)));

        // Check, that the count call also returns 1
        restEmbargoDeclaracaoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEmbargoDeclaracaoShouldNotBeFound(String filter) throws Exception {
        restEmbargoDeclaracaoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEmbargoDeclaracaoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingEmbargoDeclaracao() throws Exception {
        // Get the embargoDeclaracao
        restEmbargoDeclaracaoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewEmbargoDeclaracao() throws Exception {
        // Initialize the database
        embargoDeclaracaoRepository.saveAndFlush(embargoDeclaracao);

        int databaseSizeBeforeUpdate = embargoDeclaracaoRepository.findAll().size();

        // Update the embargoDeclaracao
        EmbargoDeclaracao updatedEmbargoDeclaracao = embargoDeclaracaoRepository.findById(embargoDeclaracao.getId()).get();
        // Disconnect from session so that the updates on updatedEmbargoDeclaracao are not directly saved in db
        em.detach(updatedEmbargoDeclaracao);
        updatedEmbargoDeclaracao.descricao(UPDATED_DESCRICAO);

        restEmbargoDeclaracaoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedEmbargoDeclaracao.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedEmbargoDeclaracao))
            )
            .andExpect(status().isOk());

        // Validate the EmbargoDeclaracao in the database
        List<EmbargoDeclaracao> embargoDeclaracaoList = embargoDeclaracaoRepository.findAll();
        assertThat(embargoDeclaracaoList).hasSize(databaseSizeBeforeUpdate);
        EmbargoDeclaracao testEmbargoDeclaracao = embargoDeclaracaoList.get(embargoDeclaracaoList.size() - 1);
        assertThat(testEmbargoDeclaracao.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void putNonExistingEmbargoDeclaracao() throws Exception {
        int databaseSizeBeforeUpdate = embargoDeclaracaoRepository.findAll().size();
        embargoDeclaracao.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmbargoDeclaracaoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, embargoDeclaracao.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(embargoDeclaracao))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmbargoDeclaracao in the database
        List<EmbargoDeclaracao> embargoDeclaracaoList = embargoDeclaracaoRepository.findAll();
        assertThat(embargoDeclaracaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEmbargoDeclaracao() throws Exception {
        int databaseSizeBeforeUpdate = embargoDeclaracaoRepository.findAll().size();
        embargoDeclaracao.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmbargoDeclaracaoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(embargoDeclaracao))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmbargoDeclaracao in the database
        List<EmbargoDeclaracao> embargoDeclaracaoList = embargoDeclaracaoRepository.findAll();
        assertThat(embargoDeclaracaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEmbargoDeclaracao() throws Exception {
        int databaseSizeBeforeUpdate = embargoDeclaracaoRepository.findAll().size();
        embargoDeclaracao.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmbargoDeclaracaoMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(embargoDeclaracao))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EmbargoDeclaracao in the database
        List<EmbargoDeclaracao> embargoDeclaracaoList = embargoDeclaracaoRepository.findAll();
        assertThat(embargoDeclaracaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEmbargoDeclaracaoWithPatch() throws Exception {
        // Initialize the database
        embargoDeclaracaoRepository.saveAndFlush(embargoDeclaracao);

        int databaseSizeBeforeUpdate = embargoDeclaracaoRepository.findAll().size();

        // Update the embargoDeclaracao using partial update
        EmbargoDeclaracao partialUpdatedEmbargoDeclaracao = new EmbargoDeclaracao();
        partialUpdatedEmbargoDeclaracao.setId(embargoDeclaracao.getId());

        partialUpdatedEmbargoDeclaracao.descricao(UPDATED_DESCRICAO);

        restEmbargoDeclaracaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmbargoDeclaracao.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEmbargoDeclaracao))
            )
            .andExpect(status().isOk());

        // Validate the EmbargoDeclaracao in the database
        List<EmbargoDeclaracao> embargoDeclaracaoList = embargoDeclaracaoRepository.findAll();
        assertThat(embargoDeclaracaoList).hasSize(databaseSizeBeforeUpdate);
        EmbargoDeclaracao testEmbargoDeclaracao = embargoDeclaracaoList.get(embargoDeclaracaoList.size() - 1);
        assertThat(testEmbargoDeclaracao.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void fullUpdateEmbargoDeclaracaoWithPatch() throws Exception {
        // Initialize the database
        embargoDeclaracaoRepository.saveAndFlush(embargoDeclaracao);

        int databaseSizeBeforeUpdate = embargoDeclaracaoRepository.findAll().size();

        // Update the embargoDeclaracao using partial update
        EmbargoDeclaracao partialUpdatedEmbargoDeclaracao = new EmbargoDeclaracao();
        partialUpdatedEmbargoDeclaracao.setId(embargoDeclaracao.getId());

        partialUpdatedEmbargoDeclaracao.descricao(UPDATED_DESCRICAO);

        restEmbargoDeclaracaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmbargoDeclaracao.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEmbargoDeclaracao))
            )
            .andExpect(status().isOk());

        // Validate the EmbargoDeclaracao in the database
        List<EmbargoDeclaracao> embargoDeclaracaoList = embargoDeclaracaoRepository.findAll();
        assertThat(embargoDeclaracaoList).hasSize(databaseSizeBeforeUpdate);
        EmbargoDeclaracao testEmbargoDeclaracao = embargoDeclaracaoList.get(embargoDeclaracaoList.size() - 1);
        assertThat(testEmbargoDeclaracao.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void patchNonExistingEmbargoDeclaracao() throws Exception {
        int databaseSizeBeforeUpdate = embargoDeclaracaoRepository.findAll().size();
        embargoDeclaracao.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmbargoDeclaracaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, embargoDeclaracao.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(embargoDeclaracao))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmbargoDeclaracao in the database
        List<EmbargoDeclaracao> embargoDeclaracaoList = embargoDeclaracaoRepository.findAll();
        assertThat(embargoDeclaracaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEmbargoDeclaracao() throws Exception {
        int databaseSizeBeforeUpdate = embargoDeclaracaoRepository.findAll().size();
        embargoDeclaracao.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmbargoDeclaracaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(embargoDeclaracao))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmbargoDeclaracao in the database
        List<EmbargoDeclaracao> embargoDeclaracaoList = embargoDeclaracaoRepository.findAll();
        assertThat(embargoDeclaracaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEmbargoDeclaracao() throws Exception {
        int databaseSizeBeforeUpdate = embargoDeclaracaoRepository.findAll().size();
        embargoDeclaracao.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmbargoDeclaracaoMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(embargoDeclaracao))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EmbargoDeclaracao in the database
        List<EmbargoDeclaracao> embargoDeclaracaoList = embargoDeclaracaoRepository.findAll();
        assertThat(embargoDeclaracaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEmbargoDeclaracao() throws Exception {
        // Initialize the database
        embargoDeclaracaoRepository.saveAndFlush(embargoDeclaracao);

        int databaseSizeBeforeDelete = embargoDeclaracaoRepository.findAll().size();

        // Delete the embargoDeclaracao
        restEmbargoDeclaracaoMockMvc
            .perform(delete(ENTITY_API_URL_ID, embargoDeclaracao.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EmbargoDeclaracao> embargoDeclaracaoList = embargoDeclaracaoRepository.findAll();
        assertThat(embargoDeclaracaoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
