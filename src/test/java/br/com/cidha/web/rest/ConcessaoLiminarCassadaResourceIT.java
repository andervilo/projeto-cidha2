package br.com.cidha.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.cidha.IntegrationTest;
import br.com.cidha.domain.ConcessaoLiminarCassada;
import br.com.cidha.domain.Processo;
import br.com.cidha.repository.ConcessaoLiminarCassadaRepository;
import br.com.cidha.service.criteria.ConcessaoLiminarCassadaCriteria;
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
 * Integration tests for the {@link ConcessaoLiminarCassadaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ConcessaoLiminarCassadaResourceIT {

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/concessao-liminar-cassadas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ConcessaoLiminarCassadaRepository concessaoLiminarCassadaRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restConcessaoLiminarCassadaMockMvc;

    private ConcessaoLiminarCassada concessaoLiminarCassada;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ConcessaoLiminarCassada createEntity(EntityManager em) {
        ConcessaoLiminarCassada concessaoLiminarCassada = new ConcessaoLiminarCassada().descricao(DEFAULT_DESCRICAO);
        return concessaoLiminarCassada;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ConcessaoLiminarCassada createUpdatedEntity(EntityManager em) {
        ConcessaoLiminarCassada concessaoLiminarCassada = new ConcessaoLiminarCassada().descricao(UPDATED_DESCRICAO);
        return concessaoLiminarCassada;
    }

    @BeforeEach
    public void initTest() {
        concessaoLiminarCassada = createEntity(em);
    }

    @Test
    @Transactional
    void createConcessaoLiminarCassada() throws Exception {
        int databaseSizeBeforeCreate = concessaoLiminarCassadaRepository.findAll().size();
        // Create the ConcessaoLiminarCassada
        restConcessaoLiminarCassadaMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(concessaoLiminarCassada))
            )
            .andExpect(status().isCreated());

        // Validate the ConcessaoLiminarCassada in the database
        List<ConcessaoLiminarCassada> concessaoLiminarCassadaList = concessaoLiminarCassadaRepository.findAll();
        assertThat(concessaoLiminarCassadaList).hasSize(databaseSizeBeforeCreate + 1);
        ConcessaoLiminarCassada testConcessaoLiminarCassada = concessaoLiminarCassadaList.get(concessaoLiminarCassadaList.size() - 1);
        assertThat(testConcessaoLiminarCassada.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
    }

    @Test
    @Transactional
    void createConcessaoLiminarCassadaWithExistingId() throws Exception {
        // Create the ConcessaoLiminarCassada with an existing ID
        concessaoLiminarCassada.setId(1L);

        int databaseSizeBeforeCreate = concessaoLiminarCassadaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restConcessaoLiminarCassadaMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(concessaoLiminarCassada))
            )
            .andExpect(status().isBadRequest());

        // Validate the ConcessaoLiminarCassada in the database
        List<ConcessaoLiminarCassada> concessaoLiminarCassadaList = concessaoLiminarCassadaRepository.findAll();
        assertThat(concessaoLiminarCassadaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllConcessaoLiminarCassadas() throws Exception {
        // Initialize the database
        concessaoLiminarCassadaRepository.saveAndFlush(concessaoLiminarCassada);

        // Get all the concessaoLiminarCassadaList
        restConcessaoLiminarCassadaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(concessaoLiminarCassada.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)));
    }

    @Test
    @Transactional
    void getConcessaoLiminarCassada() throws Exception {
        // Initialize the database
        concessaoLiminarCassadaRepository.saveAndFlush(concessaoLiminarCassada);

        // Get the concessaoLiminarCassada
        restConcessaoLiminarCassadaMockMvc
            .perform(get(ENTITY_API_URL_ID, concessaoLiminarCassada.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(concessaoLiminarCassada.getId().intValue()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO));
    }

    @Test
    @Transactional
    void getConcessaoLiminarCassadasByIdFiltering() throws Exception {
        // Initialize the database
        concessaoLiminarCassadaRepository.saveAndFlush(concessaoLiminarCassada);

        Long id = concessaoLiminarCassada.getId();

        defaultConcessaoLiminarCassadaShouldBeFound("id.equals=" + id);
        defaultConcessaoLiminarCassadaShouldNotBeFound("id.notEquals=" + id);

        defaultConcessaoLiminarCassadaShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultConcessaoLiminarCassadaShouldNotBeFound("id.greaterThan=" + id);

        defaultConcessaoLiminarCassadaShouldBeFound("id.lessThanOrEqual=" + id);
        defaultConcessaoLiminarCassadaShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllConcessaoLiminarCassadasByDescricaoIsEqualToSomething() throws Exception {
        // Initialize the database
        concessaoLiminarCassadaRepository.saveAndFlush(concessaoLiminarCassada);

        // Get all the concessaoLiminarCassadaList where descricao equals to DEFAULT_DESCRICAO
        defaultConcessaoLiminarCassadaShouldBeFound("descricao.equals=" + DEFAULT_DESCRICAO);

        // Get all the concessaoLiminarCassadaList where descricao equals to UPDATED_DESCRICAO
        defaultConcessaoLiminarCassadaShouldNotBeFound("descricao.equals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllConcessaoLiminarCassadasByDescricaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        concessaoLiminarCassadaRepository.saveAndFlush(concessaoLiminarCassada);

        // Get all the concessaoLiminarCassadaList where descricao not equals to DEFAULT_DESCRICAO
        defaultConcessaoLiminarCassadaShouldNotBeFound("descricao.notEquals=" + DEFAULT_DESCRICAO);

        // Get all the concessaoLiminarCassadaList where descricao not equals to UPDATED_DESCRICAO
        defaultConcessaoLiminarCassadaShouldBeFound("descricao.notEquals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllConcessaoLiminarCassadasByDescricaoIsInShouldWork() throws Exception {
        // Initialize the database
        concessaoLiminarCassadaRepository.saveAndFlush(concessaoLiminarCassada);

        // Get all the concessaoLiminarCassadaList where descricao in DEFAULT_DESCRICAO or UPDATED_DESCRICAO
        defaultConcessaoLiminarCassadaShouldBeFound("descricao.in=" + DEFAULT_DESCRICAO + "," + UPDATED_DESCRICAO);

        // Get all the concessaoLiminarCassadaList where descricao equals to UPDATED_DESCRICAO
        defaultConcessaoLiminarCassadaShouldNotBeFound("descricao.in=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllConcessaoLiminarCassadasByDescricaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        concessaoLiminarCassadaRepository.saveAndFlush(concessaoLiminarCassada);

        // Get all the concessaoLiminarCassadaList where descricao is not null
        defaultConcessaoLiminarCassadaShouldBeFound("descricao.specified=true");

        // Get all the concessaoLiminarCassadaList where descricao is null
        defaultConcessaoLiminarCassadaShouldNotBeFound("descricao.specified=false");
    }

    @Test
    @Transactional
    void getAllConcessaoLiminarCassadasByDescricaoContainsSomething() throws Exception {
        // Initialize the database
        concessaoLiminarCassadaRepository.saveAndFlush(concessaoLiminarCassada);

        // Get all the concessaoLiminarCassadaList where descricao contains DEFAULT_DESCRICAO
        defaultConcessaoLiminarCassadaShouldBeFound("descricao.contains=" + DEFAULT_DESCRICAO);

        // Get all the concessaoLiminarCassadaList where descricao contains UPDATED_DESCRICAO
        defaultConcessaoLiminarCassadaShouldNotBeFound("descricao.contains=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllConcessaoLiminarCassadasByDescricaoNotContainsSomething() throws Exception {
        // Initialize the database
        concessaoLiminarCassadaRepository.saveAndFlush(concessaoLiminarCassada);

        // Get all the concessaoLiminarCassadaList where descricao does not contain DEFAULT_DESCRICAO
        defaultConcessaoLiminarCassadaShouldNotBeFound("descricao.doesNotContain=" + DEFAULT_DESCRICAO);

        // Get all the concessaoLiminarCassadaList where descricao does not contain UPDATED_DESCRICAO
        defaultConcessaoLiminarCassadaShouldBeFound("descricao.doesNotContain=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllConcessaoLiminarCassadasByProcessoIsEqualToSomething() throws Exception {
        // Initialize the database
        concessaoLiminarCassadaRepository.saveAndFlush(concessaoLiminarCassada);
        Processo processo = ProcessoResourceIT.createEntity(em);
        em.persist(processo);
        em.flush();
        concessaoLiminarCassada.setProcesso(processo);
        concessaoLiminarCassadaRepository.saveAndFlush(concessaoLiminarCassada);
        Long processoId = processo.getId();

        // Get all the concessaoLiminarCassadaList where processo equals to processoId
        defaultConcessaoLiminarCassadaShouldBeFound("processoId.equals=" + processoId);

        // Get all the concessaoLiminarCassadaList where processo equals to (processoId + 1)
        defaultConcessaoLiminarCassadaShouldNotBeFound("processoId.equals=" + (processoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultConcessaoLiminarCassadaShouldBeFound(String filter) throws Exception {
        restConcessaoLiminarCassadaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(concessaoLiminarCassada.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)));

        // Check, that the count call also returns 1
        restConcessaoLiminarCassadaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultConcessaoLiminarCassadaShouldNotBeFound(String filter) throws Exception {
        restConcessaoLiminarCassadaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restConcessaoLiminarCassadaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingConcessaoLiminarCassada() throws Exception {
        // Get the concessaoLiminarCassada
        restConcessaoLiminarCassadaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewConcessaoLiminarCassada() throws Exception {
        // Initialize the database
        concessaoLiminarCassadaRepository.saveAndFlush(concessaoLiminarCassada);

        int databaseSizeBeforeUpdate = concessaoLiminarCassadaRepository.findAll().size();

        // Update the concessaoLiminarCassada
        ConcessaoLiminarCassada updatedConcessaoLiminarCassada = concessaoLiminarCassadaRepository
            .findById(concessaoLiminarCassada.getId())
            .get();
        // Disconnect from session so that the updates on updatedConcessaoLiminarCassada are not directly saved in db
        em.detach(updatedConcessaoLiminarCassada);
        updatedConcessaoLiminarCassada.descricao(UPDATED_DESCRICAO);

        restConcessaoLiminarCassadaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedConcessaoLiminarCassada.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedConcessaoLiminarCassada))
            )
            .andExpect(status().isOk());

        // Validate the ConcessaoLiminarCassada in the database
        List<ConcessaoLiminarCassada> concessaoLiminarCassadaList = concessaoLiminarCassadaRepository.findAll();
        assertThat(concessaoLiminarCassadaList).hasSize(databaseSizeBeforeUpdate);
        ConcessaoLiminarCassada testConcessaoLiminarCassada = concessaoLiminarCassadaList.get(concessaoLiminarCassadaList.size() - 1);
        assertThat(testConcessaoLiminarCassada.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void putNonExistingConcessaoLiminarCassada() throws Exception {
        int databaseSizeBeforeUpdate = concessaoLiminarCassadaRepository.findAll().size();
        concessaoLiminarCassada.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConcessaoLiminarCassadaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, concessaoLiminarCassada.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(concessaoLiminarCassada))
            )
            .andExpect(status().isBadRequest());

        // Validate the ConcessaoLiminarCassada in the database
        List<ConcessaoLiminarCassada> concessaoLiminarCassadaList = concessaoLiminarCassadaRepository.findAll();
        assertThat(concessaoLiminarCassadaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchConcessaoLiminarCassada() throws Exception {
        int databaseSizeBeforeUpdate = concessaoLiminarCassadaRepository.findAll().size();
        concessaoLiminarCassada.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConcessaoLiminarCassadaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(concessaoLiminarCassada))
            )
            .andExpect(status().isBadRequest());

        // Validate the ConcessaoLiminarCassada in the database
        List<ConcessaoLiminarCassada> concessaoLiminarCassadaList = concessaoLiminarCassadaRepository.findAll();
        assertThat(concessaoLiminarCassadaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamConcessaoLiminarCassada() throws Exception {
        int databaseSizeBeforeUpdate = concessaoLiminarCassadaRepository.findAll().size();
        concessaoLiminarCassada.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConcessaoLiminarCassadaMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(concessaoLiminarCassada))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ConcessaoLiminarCassada in the database
        List<ConcessaoLiminarCassada> concessaoLiminarCassadaList = concessaoLiminarCassadaRepository.findAll();
        assertThat(concessaoLiminarCassadaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateConcessaoLiminarCassadaWithPatch() throws Exception {
        // Initialize the database
        concessaoLiminarCassadaRepository.saveAndFlush(concessaoLiminarCassada);

        int databaseSizeBeforeUpdate = concessaoLiminarCassadaRepository.findAll().size();

        // Update the concessaoLiminarCassada using partial update
        ConcessaoLiminarCassada partialUpdatedConcessaoLiminarCassada = new ConcessaoLiminarCassada();
        partialUpdatedConcessaoLiminarCassada.setId(concessaoLiminarCassada.getId());

        partialUpdatedConcessaoLiminarCassada.descricao(UPDATED_DESCRICAO);

        restConcessaoLiminarCassadaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedConcessaoLiminarCassada.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedConcessaoLiminarCassada))
            )
            .andExpect(status().isOk());

        // Validate the ConcessaoLiminarCassada in the database
        List<ConcessaoLiminarCassada> concessaoLiminarCassadaList = concessaoLiminarCassadaRepository.findAll();
        assertThat(concessaoLiminarCassadaList).hasSize(databaseSizeBeforeUpdate);
        ConcessaoLiminarCassada testConcessaoLiminarCassada = concessaoLiminarCassadaList.get(concessaoLiminarCassadaList.size() - 1);
        assertThat(testConcessaoLiminarCassada.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void fullUpdateConcessaoLiminarCassadaWithPatch() throws Exception {
        // Initialize the database
        concessaoLiminarCassadaRepository.saveAndFlush(concessaoLiminarCassada);

        int databaseSizeBeforeUpdate = concessaoLiminarCassadaRepository.findAll().size();

        // Update the concessaoLiminarCassada using partial update
        ConcessaoLiminarCassada partialUpdatedConcessaoLiminarCassada = new ConcessaoLiminarCassada();
        partialUpdatedConcessaoLiminarCassada.setId(concessaoLiminarCassada.getId());

        partialUpdatedConcessaoLiminarCassada.descricao(UPDATED_DESCRICAO);

        restConcessaoLiminarCassadaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedConcessaoLiminarCassada.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedConcessaoLiminarCassada))
            )
            .andExpect(status().isOk());

        // Validate the ConcessaoLiminarCassada in the database
        List<ConcessaoLiminarCassada> concessaoLiminarCassadaList = concessaoLiminarCassadaRepository.findAll();
        assertThat(concessaoLiminarCassadaList).hasSize(databaseSizeBeforeUpdate);
        ConcessaoLiminarCassada testConcessaoLiminarCassada = concessaoLiminarCassadaList.get(concessaoLiminarCassadaList.size() - 1);
        assertThat(testConcessaoLiminarCassada.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void patchNonExistingConcessaoLiminarCassada() throws Exception {
        int databaseSizeBeforeUpdate = concessaoLiminarCassadaRepository.findAll().size();
        concessaoLiminarCassada.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConcessaoLiminarCassadaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, concessaoLiminarCassada.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(concessaoLiminarCassada))
            )
            .andExpect(status().isBadRequest());

        // Validate the ConcessaoLiminarCassada in the database
        List<ConcessaoLiminarCassada> concessaoLiminarCassadaList = concessaoLiminarCassadaRepository.findAll();
        assertThat(concessaoLiminarCassadaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchConcessaoLiminarCassada() throws Exception {
        int databaseSizeBeforeUpdate = concessaoLiminarCassadaRepository.findAll().size();
        concessaoLiminarCassada.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConcessaoLiminarCassadaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(concessaoLiminarCassada))
            )
            .andExpect(status().isBadRequest());

        // Validate the ConcessaoLiminarCassada in the database
        List<ConcessaoLiminarCassada> concessaoLiminarCassadaList = concessaoLiminarCassadaRepository.findAll();
        assertThat(concessaoLiminarCassadaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamConcessaoLiminarCassada() throws Exception {
        int databaseSizeBeforeUpdate = concessaoLiminarCassadaRepository.findAll().size();
        concessaoLiminarCassada.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConcessaoLiminarCassadaMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(concessaoLiminarCassada))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ConcessaoLiminarCassada in the database
        List<ConcessaoLiminarCassada> concessaoLiminarCassadaList = concessaoLiminarCassadaRepository.findAll();
        assertThat(concessaoLiminarCassadaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteConcessaoLiminarCassada() throws Exception {
        // Initialize the database
        concessaoLiminarCassadaRepository.saveAndFlush(concessaoLiminarCassada);

        int databaseSizeBeforeDelete = concessaoLiminarCassadaRepository.findAll().size();

        // Delete the concessaoLiminarCassada
        restConcessaoLiminarCassadaMockMvc
            .perform(delete(ENTITY_API_URL_ID, concessaoLiminarCassada.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ConcessaoLiminarCassada> concessaoLiminarCassadaList = concessaoLiminarCassadaRepository.findAll();
        assertThat(concessaoLiminarCassadaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
