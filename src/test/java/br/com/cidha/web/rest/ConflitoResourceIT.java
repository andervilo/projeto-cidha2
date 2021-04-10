package br.com.cidha.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.cidha.IntegrationTest;
import br.com.cidha.domain.Conflito;
import br.com.cidha.domain.ProcessoConflito;
import br.com.cidha.repository.ConflitoRepository;
import br.com.cidha.service.criteria.ConflitoCriteria;
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
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link ConflitoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ConflitoResourceIT {

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/conflitos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ConflitoRepository conflitoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restConflitoMockMvc;

    private Conflito conflito;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Conflito createEntity(EntityManager em) {
        Conflito conflito = new Conflito().descricao(DEFAULT_DESCRICAO);
        return conflito;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Conflito createUpdatedEntity(EntityManager em) {
        Conflito conflito = new Conflito().descricao(UPDATED_DESCRICAO);
        return conflito;
    }

    @BeforeEach
    public void initTest() {
        conflito = createEntity(em);
    }

    @Test
    @Transactional
    void createConflito() throws Exception {
        int databaseSizeBeforeCreate = conflitoRepository.findAll().size();
        // Create the Conflito
        restConflitoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(conflito)))
            .andExpect(status().isCreated());

        // Validate the Conflito in the database
        List<Conflito> conflitoList = conflitoRepository.findAll();
        assertThat(conflitoList).hasSize(databaseSizeBeforeCreate + 1);
        Conflito testConflito = conflitoList.get(conflitoList.size() - 1);
        assertThat(testConflito.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
    }

    @Test
    @Transactional
    void createConflitoWithExistingId() throws Exception {
        // Create the Conflito with an existing ID
        conflito.setId(1L);

        int databaseSizeBeforeCreate = conflitoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restConflitoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(conflito)))
            .andExpect(status().isBadRequest());

        // Validate the Conflito in the database
        List<Conflito> conflitoList = conflitoRepository.findAll();
        assertThat(conflitoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllConflitos() throws Exception {
        // Initialize the database
        conflitoRepository.saveAndFlush(conflito);

        // Get all the conflitoList
        restConflitoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(conflito.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())));
    }

    @Test
    @Transactional
    void getConflito() throws Exception {
        // Initialize the database
        conflitoRepository.saveAndFlush(conflito);

        // Get the conflito
        restConflitoMockMvc
            .perform(get(ENTITY_API_URL_ID, conflito.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(conflito.getId().intValue()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()));
    }

    @Test
    @Transactional
    void getConflitosByIdFiltering() throws Exception {
        // Initialize the database
        conflitoRepository.saveAndFlush(conflito);

        Long id = conflito.getId();

        defaultConflitoShouldBeFound("id.equals=" + id);
        defaultConflitoShouldNotBeFound("id.notEquals=" + id);

        defaultConflitoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultConflitoShouldNotBeFound("id.greaterThan=" + id);

        defaultConflitoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultConflitoShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllConflitosByProcessoConflitoIsEqualToSomething() throws Exception {
        // Initialize the database
        conflitoRepository.saveAndFlush(conflito);
        ProcessoConflito processoConflito = ProcessoConflitoResourceIT.createEntity(em);
        em.persist(processoConflito);
        em.flush();
        conflito.setProcessoConflito(processoConflito);
        conflitoRepository.saveAndFlush(conflito);
        Long processoConflitoId = processoConflito.getId();

        // Get all the conflitoList where processoConflito equals to processoConflitoId
        defaultConflitoShouldBeFound("processoConflitoId.equals=" + processoConflitoId);

        // Get all the conflitoList where processoConflito equals to (processoConflitoId + 1)
        defaultConflitoShouldNotBeFound("processoConflitoId.equals=" + (processoConflitoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultConflitoShouldBeFound(String filter) throws Exception {
        restConflitoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(conflito.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())));

        // Check, that the count call also returns 1
        restConflitoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultConflitoShouldNotBeFound(String filter) throws Exception {
        restConflitoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restConflitoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingConflito() throws Exception {
        // Get the conflito
        restConflitoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewConflito() throws Exception {
        // Initialize the database
        conflitoRepository.saveAndFlush(conflito);

        int databaseSizeBeforeUpdate = conflitoRepository.findAll().size();

        // Update the conflito
        Conflito updatedConflito = conflitoRepository.findById(conflito.getId()).get();
        // Disconnect from session so that the updates on updatedConflito are not directly saved in db
        em.detach(updatedConflito);
        updatedConflito.descricao(UPDATED_DESCRICAO);

        restConflitoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedConflito.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedConflito))
            )
            .andExpect(status().isOk());

        // Validate the Conflito in the database
        List<Conflito> conflitoList = conflitoRepository.findAll();
        assertThat(conflitoList).hasSize(databaseSizeBeforeUpdate);
        Conflito testConflito = conflitoList.get(conflitoList.size() - 1);
        assertThat(testConflito.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void putNonExistingConflito() throws Exception {
        int databaseSizeBeforeUpdate = conflitoRepository.findAll().size();
        conflito.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConflitoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, conflito.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(conflito))
            )
            .andExpect(status().isBadRequest());

        // Validate the Conflito in the database
        List<Conflito> conflitoList = conflitoRepository.findAll();
        assertThat(conflitoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchConflito() throws Exception {
        int databaseSizeBeforeUpdate = conflitoRepository.findAll().size();
        conflito.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConflitoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(conflito))
            )
            .andExpect(status().isBadRequest());

        // Validate the Conflito in the database
        List<Conflito> conflitoList = conflitoRepository.findAll();
        assertThat(conflitoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamConflito() throws Exception {
        int databaseSizeBeforeUpdate = conflitoRepository.findAll().size();
        conflito.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConflitoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(conflito)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Conflito in the database
        List<Conflito> conflitoList = conflitoRepository.findAll();
        assertThat(conflitoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateConflitoWithPatch() throws Exception {
        // Initialize the database
        conflitoRepository.saveAndFlush(conflito);

        int databaseSizeBeforeUpdate = conflitoRepository.findAll().size();

        // Update the conflito using partial update
        Conflito partialUpdatedConflito = new Conflito();
        partialUpdatedConflito.setId(conflito.getId());

        partialUpdatedConflito.descricao(UPDATED_DESCRICAO);

        restConflitoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedConflito.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedConflito))
            )
            .andExpect(status().isOk());

        // Validate the Conflito in the database
        List<Conflito> conflitoList = conflitoRepository.findAll();
        assertThat(conflitoList).hasSize(databaseSizeBeforeUpdate);
        Conflito testConflito = conflitoList.get(conflitoList.size() - 1);
        assertThat(testConflito.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void fullUpdateConflitoWithPatch() throws Exception {
        // Initialize the database
        conflitoRepository.saveAndFlush(conflito);

        int databaseSizeBeforeUpdate = conflitoRepository.findAll().size();

        // Update the conflito using partial update
        Conflito partialUpdatedConflito = new Conflito();
        partialUpdatedConflito.setId(conflito.getId());

        partialUpdatedConflito.descricao(UPDATED_DESCRICAO);

        restConflitoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedConflito.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedConflito))
            )
            .andExpect(status().isOk());

        // Validate the Conflito in the database
        List<Conflito> conflitoList = conflitoRepository.findAll();
        assertThat(conflitoList).hasSize(databaseSizeBeforeUpdate);
        Conflito testConflito = conflitoList.get(conflitoList.size() - 1);
        assertThat(testConflito.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void patchNonExistingConflito() throws Exception {
        int databaseSizeBeforeUpdate = conflitoRepository.findAll().size();
        conflito.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConflitoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, conflito.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(conflito))
            )
            .andExpect(status().isBadRequest());

        // Validate the Conflito in the database
        List<Conflito> conflitoList = conflitoRepository.findAll();
        assertThat(conflitoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchConflito() throws Exception {
        int databaseSizeBeforeUpdate = conflitoRepository.findAll().size();
        conflito.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConflitoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(conflito))
            )
            .andExpect(status().isBadRequest());

        // Validate the Conflito in the database
        List<Conflito> conflitoList = conflitoRepository.findAll();
        assertThat(conflitoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamConflito() throws Exception {
        int databaseSizeBeforeUpdate = conflitoRepository.findAll().size();
        conflito.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConflitoMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(conflito)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Conflito in the database
        List<Conflito> conflitoList = conflitoRepository.findAll();
        assertThat(conflitoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteConflito() throws Exception {
        // Initialize the database
        conflitoRepository.saveAndFlush(conflito);

        int databaseSizeBeforeDelete = conflitoRepository.findAll().size();

        // Delete the conflito
        restConflitoMockMvc
            .perform(delete(ENTITY_API_URL_ID, conflito.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Conflito> conflitoList = conflitoRepository.findAll();
        assertThat(conflitoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
