package br.com.cidha.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.cidha.IntegrationTest;
import br.com.cidha.domain.Processo;
import br.com.cidha.domain.UnidadeConservacao;
import br.com.cidha.repository.UnidadeConservacaoRepository;
import br.com.cidha.service.criteria.UnidadeConservacaoCriteria;
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
 * Integration tests for the {@link UnidadeConservacaoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class UnidadeConservacaoResourceIT {

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/unidade-conservacaos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private UnidadeConservacaoRepository unidadeConservacaoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUnidadeConservacaoMockMvc;

    private UnidadeConservacao unidadeConservacao;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UnidadeConservacao createEntity(EntityManager em) {
        UnidadeConservacao unidadeConservacao = new UnidadeConservacao().descricao(DEFAULT_DESCRICAO);
        return unidadeConservacao;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UnidadeConservacao createUpdatedEntity(EntityManager em) {
        UnidadeConservacao unidadeConservacao = new UnidadeConservacao().descricao(UPDATED_DESCRICAO);
        return unidadeConservacao;
    }

    @BeforeEach
    public void initTest() {
        unidadeConservacao = createEntity(em);
    }

    @Test
    @Transactional
    void createUnidadeConservacao() throws Exception {
        int databaseSizeBeforeCreate = unidadeConservacaoRepository.findAll().size();
        // Create the UnidadeConservacao
        restUnidadeConservacaoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(unidadeConservacao))
            )
            .andExpect(status().isCreated());

        // Validate the UnidadeConservacao in the database
        List<UnidadeConservacao> unidadeConservacaoList = unidadeConservacaoRepository.findAll();
        assertThat(unidadeConservacaoList).hasSize(databaseSizeBeforeCreate + 1);
        UnidadeConservacao testUnidadeConservacao = unidadeConservacaoList.get(unidadeConservacaoList.size() - 1);
        assertThat(testUnidadeConservacao.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
    }

    @Test
    @Transactional
    void createUnidadeConservacaoWithExistingId() throws Exception {
        // Create the UnidadeConservacao with an existing ID
        unidadeConservacao.setId(1L);

        int databaseSizeBeforeCreate = unidadeConservacaoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restUnidadeConservacaoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(unidadeConservacao))
            )
            .andExpect(status().isBadRequest());

        // Validate the UnidadeConservacao in the database
        List<UnidadeConservacao> unidadeConservacaoList = unidadeConservacaoRepository.findAll();
        assertThat(unidadeConservacaoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllUnidadeConservacaos() throws Exception {
        // Initialize the database
        unidadeConservacaoRepository.saveAndFlush(unidadeConservacao);

        // Get all the unidadeConservacaoList
        restUnidadeConservacaoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(unidadeConservacao.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)));
    }

    @Test
    @Transactional
    void getUnidadeConservacao() throws Exception {
        // Initialize the database
        unidadeConservacaoRepository.saveAndFlush(unidadeConservacao);

        // Get the unidadeConservacao
        restUnidadeConservacaoMockMvc
            .perform(get(ENTITY_API_URL_ID, unidadeConservacao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(unidadeConservacao.getId().intValue()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO));
    }

    @Test
    @Transactional
    void getUnidadeConservacaosByIdFiltering() throws Exception {
        // Initialize the database
        unidadeConservacaoRepository.saveAndFlush(unidadeConservacao);

        Long id = unidadeConservacao.getId();

        defaultUnidadeConservacaoShouldBeFound("id.equals=" + id);
        defaultUnidadeConservacaoShouldNotBeFound("id.notEquals=" + id);

        defaultUnidadeConservacaoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultUnidadeConservacaoShouldNotBeFound("id.greaterThan=" + id);

        defaultUnidadeConservacaoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultUnidadeConservacaoShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllUnidadeConservacaosByDescricaoIsEqualToSomething() throws Exception {
        // Initialize the database
        unidadeConservacaoRepository.saveAndFlush(unidadeConservacao);

        // Get all the unidadeConservacaoList where descricao equals to DEFAULT_DESCRICAO
        defaultUnidadeConservacaoShouldBeFound("descricao.equals=" + DEFAULT_DESCRICAO);

        // Get all the unidadeConservacaoList where descricao equals to UPDATED_DESCRICAO
        defaultUnidadeConservacaoShouldNotBeFound("descricao.equals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllUnidadeConservacaosByDescricaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        unidadeConservacaoRepository.saveAndFlush(unidadeConservacao);

        // Get all the unidadeConservacaoList where descricao not equals to DEFAULT_DESCRICAO
        defaultUnidadeConservacaoShouldNotBeFound("descricao.notEquals=" + DEFAULT_DESCRICAO);

        // Get all the unidadeConservacaoList where descricao not equals to UPDATED_DESCRICAO
        defaultUnidadeConservacaoShouldBeFound("descricao.notEquals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllUnidadeConservacaosByDescricaoIsInShouldWork() throws Exception {
        // Initialize the database
        unidadeConservacaoRepository.saveAndFlush(unidadeConservacao);

        // Get all the unidadeConservacaoList where descricao in DEFAULT_DESCRICAO or UPDATED_DESCRICAO
        defaultUnidadeConservacaoShouldBeFound("descricao.in=" + DEFAULT_DESCRICAO + "," + UPDATED_DESCRICAO);

        // Get all the unidadeConservacaoList where descricao equals to UPDATED_DESCRICAO
        defaultUnidadeConservacaoShouldNotBeFound("descricao.in=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllUnidadeConservacaosByDescricaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        unidadeConservacaoRepository.saveAndFlush(unidadeConservacao);

        // Get all the unidadeConservacaoList where descricao is not null
        defaultUnidadeConservacaoShouldBeFound("descricao.specified=true");

        // Get all the unidadeConservacaoList where descricao is null
        defaultUnidadeConservacaoShouldNotBeFound("descricao.specified=false");
    }

    @Test
    @Transactional
    void getAllUnidadeConservacaosByDescricaoContainsSomething() throws Exception {
        // Initialize the database
        unidadeConservacaoRepository.saveAndFlush(unidadeConservacao);

        // Get all the unidadeConservacaoList where descricao contains DEFAULT_DESCRICAO
        defaultUnidadeConservacaoShouldBeFound("descricao.contains=" + DEFAULT_DESCRICAO);

        // Get all the unidadeConservacaoList where descricao contains UPDATED_DESCRICAO
        defaultUnidadeConservacaoShouldNotBeFound("descricao.contains=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllUnidadeConservacaosByDescricaoNotContainsSomething() throws Exception {
        // Initialize the database
        unidadeConservacaoRepository.saveAndFlush(unidadeConservacao);

        // Get all the unidadeConservacaoList where descricao does not contain DEFAULT_DESCRICAO
        defaultUnidadeConservacaoShouldNotBeFound("descricao.doesNotContain=" + DEFAULT_DESCRICAO);

        // Get all the unidadeConservacaoList where descricao does not contain UPDATED_DESCRICAO
        defaultUnidadeConservacaoShouldBeFound("descricao.doesNotContain=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllUnidadeConservacaosByProcessoIsEqualToSomething() throws Exception {
        // Initialize the database
        unidadeConservacaoRepository.saveAndFlush(unidadeConservacao);
        Processo processo = ProcessoResourceIT.createEntity(em);
        em.persist(processo);
        em.flush();
        unidadeConservacao.addProcesso(processo);
        unidadeConservacaoRepository.saveAndFlush(unidadeConservacao);
        Long processoId = processo.getId();

        // Get all the unidadeConservacaoList where processo equals to processoId
        defaultUnidadeConservacaoShouldBeFound("processoId.equals=" + processoId);

        // Get all the unidadeConservacaoList where processo equals to (processoId + 1)
        defaultUnidadeConservacaoShouldNotBeFound("processoId.equals=" + (processoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultUnidadeConservacaoShouldBeFound(String filter) throws Exception {
        restUnidadeConservacaoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(unidadeConservacao.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)));

        // Check, that the count call also returns 1
        restUnidadeConservacaoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultUnidadeConservacaoShouldNotBeFound(String filter) throws Exception {
        restUnidadeConservacaoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restUnidadeConservacaoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingUnidadeConservacao() throws Exception {
        // Get the unidadeConservacao
        restUnidadeConservacaoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewUnidadeConservacao() throws Exception {
        // Initialize the database
        unidadeConservacaoRepository.saveAndFlush(unidadeConservacao);

        int databaseSizeBeforeUpdate = unidadeConservacaoRepository.findAll().size();

        // Update the unidadeConservacao
        UnidadeConservacao updatedUnidadeConservacao = unidadeConservacaoRepository.findById(unidadeConservacao.getId()).get();
        // Disconnect from session so that the updates on updatedUnidadeConservacao are not directly saved in db
        em.detach(updatedUnidadeConservacao);
        updatedUnidadeConservacao.descricao(UPDATED_DESCRICAO);

        restUnidadeConservacaoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedUnidadeConservacao.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedUnidadeConservacao))
            )
            .andExpect(status().isOk());

        // Validate the UnidadeConservacao in the database
        List<UnidadeConservacao> unidadeConservacaoList = unidadeConservacaoRepository.findAll();
        assertThat(unidadeConservacaoList).hasSize(databaseSizeBeforeUpdate);
        UnidadeConservacao testUnidadeConservacao = unidadeConservacaoList.get(unidadeConservacaoList.size() - 1);
        assertThat(testUnidadeConservacao.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void putNonExistingUnidadeConservacao() throws Exception {
        int databaseSizeBeforeUpdate = unidadeConservacaoRepository.findAll().size();
        unidadeConservacao.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUnidadeConservacaoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, unidadeConservacao.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(unidadeConservacao))
            )
            .andExpect(status().isBadRequest());

        // Validate the UnidadeConservacao in the database
        List<UnidadeConservacao> unidadeConservacaoList = unidadeConservacaoRepository.findAll();
        assertThat(unidadeConservacaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchUnidadeConservacao() throws Exception {
        int databaseSizeBeforeUpdate = unidadeConservacaoRepository.findAll().size();
        unidadeConservacao.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUnidadeConservacaoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(unidadeConservacao))
            )
            .andExpect(status().isBadRequest());

        // Validate the UnidadeConservacao in the database
        List<UnidadeConservacao> unidadeConservacaoList = unidadeConservacaoRepository.findAll();
        assertThat(unidadeConservacaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamUnidadeConservacao() throws Exception {
        int databaseSizeBeforeUpdate = unidadeConservacaoRepository.findAll().size();
        unidadeConservacao.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUnidadeConservacaoMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(unidadeConservacao))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the UnidadeConservacao in the database
        List<UnidadeConservacao> unidadeConservacaoList = unidadeConservacaoRepository.findAll();
        assertThat(unidadeConservacaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateUnidadeConservacaoWithPatch() throws Exception {
        // Initialize the database
        unidadeConservacaoRepository.saveAndFlush(unidadeConservacao);

        int databaseSizeBeforeUpdate = unidadeConservacaoRepository.findAll().size();

        // Update the unidadeConservacao using partial update
        UnidadeConservacao partialUpdatedUnidadeConservacao = new UnidadeConservacao();
        partialUpdatedUnidadeConservacao.setId(unidadeConservacao.getId());

        partialUpdatedUnidadeConservacao.descricao(UPDATED_DESCRICAO);

        restUnidadeConservacaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUnidadeConservacao.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUnidadeConservacao))
            )
            .andExpect(status().isOk());

        // Validate the UnidadeConservacao in the database
        List<UnidadeConservacao> unidadeConservacaoList = unidadeConservacaoRepository.findAll();
        assertThat(unidadeConservacaoList).hasSize(databaseSizeBeforeUpdate);
        UnidadeConservacao testUnidadeConservacao = unidadeConservacaoList.get(unidadeConservacaoList.size() - 1);
        assertThat(testUnidadeConservacao.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void fullUpdateUnidadeConservacaoWithPatch() throws Exception {
        // Initialize the database
        unidadeConservacaoRepository.saveAndFlush(unidadeConservacao);

        int databaseSizeBeforeUpdate = unidadeConservacaoRepository.findAll().size();

        // Update the unidadeConservacao using partial update
        UnidadeConservacao partialUpdatedUnidadeConservacao = new UnidadeConservacao();
        partialUpdatedUnidadeConservacao.setId(unidadeConservacao.getId());

        partialUpdatedUnidadeConservacao.descricao(UPDATED_DESCRICAO);

        restUnidadeConservacaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUnidadeConservacao.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUnidadeConservacao))
            )
            .andExpect(status().isOk());

        // Validate the UnidadeConservacao in the database
        List<UnidadeConservacao> unidadeConservacaoList = unidadeConservacaoRepository.findAll();
        assertThat(unidadeConservacaoList).hasSize(databaseSizeBeforeUpdate);
        UnidadeConservacao testUnidadeConservacao = unidadeConservacaoList.get(unidadeConservacaoList.size() - 1);
        assertThat(testUnidadeConservacao.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void patchNonExistingUnidadeConservacao() throws Exception {
        int databaseSizeBeforeUpdate = unidadeConservacaoRepository.findAll().size();
        unidadeConservacao.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUnidadeConservacaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, unidadeConservacao.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(unidadeConservacao))
            )
            .andExpect(status().isBadRequest());

        // Validate the UnidadeConservacao in the database
        List<UnidadeConservacao> unidadeConservacaoList = unidadeConservacaoRepository.findAll();
        assertThat(unidadeConservacaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchUnidadeConservacao() throws Exception {
        int databaseSizeBeforeUpdate = unidadeConservacaoRepository.findAll().size();
        unidadeConservacao.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUnidadeConservacaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(unidadeConservacao))
            )
            .andExpect(status().isBadRequest());

        // Validate the UnidadeConservacao in the database
        List<UnidadeConservacao> unidadeConservacaoList = unidadeConservacaoRepository.findAll();
        assertThat(unidadeConservacaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamUnidadeConservacao() throws Exception {
        int databaseSizeBeforeUpdate = unidadeConservacaoRepository.findAll().size();
        unidadeConservacao.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUnidadeConservacaoMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(unidadeConservacao))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the UnidadeConservacao in the database
        List<UnidadeConservacao> unidadeConservacaoList = unidadeConservacaoRepository.findAll();
        assertThat(unidadeConservacaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteUnidadeConservacao() throws Exception {
        // Initialize the database
        unidadeConservacaoRepository.saveAndFlush(unidadeConservacao);

        int databaseSizeBeforeDelete = unidadeConservacaoRepository.findAll().size();

        // Delete the unidadeConservacao
        restUnidadeConservacaoMockMvc
            .perform(delete(ENTITY_API_URL_ID, unidadeConservacao.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UnidadeConservacao> unidadeConservacaoList = unidadeConservacaoRepository.findAll();
        assertThat(unidadeConservacaoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
