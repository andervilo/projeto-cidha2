package br.com.cidha.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.cidha.IntegrationTest;
import br.com.cidha.domain.ConcessaoLiminar;
import br.com.cidha.domain.Processo;
import br.com.cidha.repository.ConcessaoLiminarRepository;
import br.com.cidha.service.criteria.ConcessaoLiminarCriteria;
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
 * Integration tests for the {@link ConcessaoLiminarResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ConcessaoLiminarResourceIT {

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/concessao-liminars";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ConcessaoLiminarRepository concessaoLiminarRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restConcessaoLiminarMockMvc;

    private ConcessaoLiminar concessaoLiminar;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ConcessaoLiminar createEntity(EntityManager em) {
        ConcessaoLiminar concessaoLiminar = new ConcessaoLiminar().descricao(DEFAULT_DESCRICAO);
        return concessaoLiminar;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ConcessaoLiminar createUpdatedEntity(EntityManager em) {
        ConcessaoLiminar concessaoLiminar = new ConcessaoLiminar().descricao(UPDATED_DESCRICAO);
        return concessaoLiminar;
    }

    @BeforeEach
    public void initTest() {
        concessaoLiminar = createEntity(em);
    }

    @Test
    @Transactional
    void createConcessaoLiminar() throws Exception {
        int databaseSizeBeforeCreate = concessaoLiminarRepository.findAll().size();
        // Create the ConcessaoLiminar
        restConcessaoLiminarMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(concessaoLiminar))
            )
            .andExpect(status().isCreated());

        // Validate the ConcessaoLiminar in the database
        List<ConcessaoLiminar> concessaoLiminarList = concessaoLiminarRepository.findAll();
        assertThat(concessaoLiminarList).hasSize(databaseSizeBeforeCreate + 1);
        ConcessaoLiminar testConcessaoLiminar = concessaoLiminarList.get(concessaoLiminarList.size() - 1);
        assertThat(testConcessaoLiminar.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
    }

    @Test
    @Transactional
    void createConcessaoLiminarWithExistingId() throws Exception {
        // Create the ConcessaoLiminar with an existing ID
        concessaoLiminar.setId(1L);

        int databaseSizeBeforeCreate = concessaoLiminarRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restConcessaoLiminarMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(concessaoLiminar))
            )
            .andExpect(status().isBadRequest());

        // Validate the ConcessaoLiminar in the database
        List<ConcessaoLiminar> concessaoLiminarList = concessaoLiminarRepository.findAll();
        assertThat(concessaoLiminarList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllConcessaoLiminars() throws Exception {
        // Initialize the database
        concessaoLiminarRepository.saveAndFlush(concessaoLiminar);

        // Get all the concessaoLiminarList
        restConcessaoLiminarMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(concessaoLiminar.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)));
    }

    @Test
    @Transactional
    void getConcessaoLiminar() throws Exception {
        // Initialize the database
        concessaoLiminarRepository.saveAndFlush(concessaoLiminar);

        // Get the concessaoLiminar
        restConcessaoLiminarMockMvc
            .perform(get(ENTITY_API_URL_ID, concessaoLiminar.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(concessaoLiminar.getId().intValue()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO));
    }

    @Test
    @Transactional
    void getConcessaoLiminarsByIdFiltering() throws Exception {
        // Initialize the database
        concessaoLiminarRepository.saveAndFlush(concessaoLiminar);

        Long id = concessaoLiminar.getId();

        defaultConcessaoLiminarShouldBeFound("id.equals=" + id);
        defaultConcessaoLiminarShouldNotBeFound("id.notEquals=" + id);

        defaultConcessaoLiminarShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultConcessaoLiminarShouldNotBeFound("id.greaterThan=" + id);

        defaultConcessaoLiminarShouldBeFound("id.lessThanOrEqual=" + id);
        defaultConcessaoLiminarShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllConcessaoLiminarsByDescricaoIsEqualToSomething() throws Exception {
        // Initialize the database
        concessaoLiminarRepository.saveAndFlush(concessaoLiminar);

        // Get all the concessaoLiminarList where descricao equals to DEFAULT_DESCRICAO
        defaultConcessaoLiminarShouldBeFound("descricao.equals=" + DEFAULT_DESCRICAO);

        // Get all the concessaoLiminarList where descricao equals to UPDATED_DESCRICAO
        defaultConcessaoLiminarShouldNotBeFound("descricao.equals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllConcessaoLiminarsByDescricaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        concessaoLiminarRepository.saveAndFlush(concessaoLiminar);

        // Get all the concessaoLiminarList where descricao not equals to DEFAULT_DESCRICAO
        defaultConcessaoLiminarShouldNotBeFound("descricao.notEquals=" + DEFAULT_DESCRICAO);

        // Get all the concessaoLiminarList where descricao not equals to UPDATED_DESCRICAO
        defaultConcessaoLiminarShouldBeFound("descricao.notEquals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllConcessaoLiminarsByDescricaoIsInShouldWork() throws Exception {
        // Initialize the database
        concessaoLiminarRepository.saveAndFlush(concessaoLiminar);

        // Get all the concessaoLiminarList where descricao in DEFAULT_DESCRICAO or UPDATED_DESCRICAO
        defaultConcessaoLiminarShouldBeFound("descricao.in=" + DEFAULT_DESCRICAO + "," + UPDATED_DESCRICAO);

        // Get all the concessaoLiminarList where descricao equals to UPDATED_DESCRICAO
        defaultConcessaoLiminarShouldNotBeFound("descricao.in=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllConcessaoLiminarsByDescricaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        concessaoLiminarRepository.saveAndFlush(concessaoLiminar);

        // Get all the concessaoLiminarList where descricao is not null
        defaultConcessaoLiminarShouldBeFound("descricao.specified=true");

        // Get all the concessaoLiminarList where descricao is null
        defaultConcessaoLiminarShouldNotBeFound("descricao.specified=false");
    }

    @Test
    @Transactional
    void getAllConcessaoLiminarsByDescricaoContainsSomething() throws Exception {
        // Initialize the database
        concessaoLiminarRepository.saveAndFlush(concessaoLiminar);

        // Get all the concessaoLiminarList where descricao contains DEFAULT_DESCRICAO
        defaultConcessaoLiminarShouldBeFound("descricao.contains=" + DEFAULT_DESCRICAO);

        // Get all the concessaoLiminarList where descricao contains UPDATED_DESCRICAO
        defaultConcessaoLiminarShouldNotBeFound("descricao.contains=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllConcessaoLiminarsByDescricaoNotContainsSomething() throws Exception {
        // Initialize the database
        concessaoLiminarRepository.saveAndFlush(concessaoLiminar);

        // Get all the concessaoLiminarList where descricao does not contain DEFAULT_DESCRICAO
        defaultConcessaoLiminarShouldNotBeFound("descricao.doesNotContain=" + DEFAULT_DESCRICAO);

        // Get all the concessaoLiminarList where descricao does not contain UPDATED_DESCRICAO
        defaultConcessaoLiminarShouldBeFound("descricao.doesNotContain=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllConcessaoLiminarsByProcessoIsEqualToSomething() throws Exception {
        // Initialize the database
        concessaoLiminarRepository.saveAndFlush(concessaoLiminar);
        Processo processo = ProcessoResourceIT.createEntity(em);
        em.persist(processo);
        em.flush();
        concessaoLiminar.setProcesso(processo);
        concessaoLiminarRepository.saveAndFlush(concessaoLiminar);
        Long processoId = processo.getId();

        // Get all the concessaoLiminarList where processo equals to processoId
        defaultConcessaoLiminarShouldBeFound("processoId.equals=" + processoId);

        // Get all the concessaoLiminarList where processo equals to (processoId + 1)
        defaultConcessaoLiminarShouldNotBeFound("processoId.equals=" + (processoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultConcessaoLiminarShouldBeFound(String filter) throws Exception {
        restConcessaoLiminarMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(concessaoLiminar.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)));

        // Check, that the count call also returns 1
        restConcessaoLiminarMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultConcessaoLiminarShouldNotBeFound(String filter) throws Exception {
        restConcessaoLiminarMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restConcessaoLiminarMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingConcessaoLiminar() throws Exception {
        // Get the concessaoLiminar
        restConcessaoLiminarMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewConcessaoLiminar() throws Exception {
        // Initialize the database
        concessaoLiminarRepository.saveAndFlush(concessaoLiminar);

        int databaseSizeBeforeUpdate = concessaoLiminarRepository.findAll().size();

        // Update the concessaoLiminar
        ConcessaoLiminar updatedConcessaoLiminar = concessaoLiminarRepository.findById(concessaoLiminar.getId()).get();
        // Disconnect from session so that the updates on updatedConcessaoLiminar are not directly saved in db
        em.detach(updatedConcessaoLiminar);
        updatedConcessaoLiminar.descricao(UPDATED_DESCRICAO);

        restConcessaoLiminarMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedConcessaoLiminar.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedConcessaoLiminar))
            )
            .andExpect(status().isOk());

        // Validate the ConcessaoLiminar in the database
        List<ConcessaoLiminar> concessaoLiminarList = concessaoLiminarRepository.findAll();
        assertThat(concessaoLiminarList).hasSize(databaseSizeBeforeUpdate);
        ConcessaoLiminar testConcessaoLiminar = concessaoLiminarList.get(concessaoLiminarList.size() - 1);
        assertThat(testConcessaoLiminar.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void putNonExistingConcessaoLiminar() throws Exception {
        int databaseSizeBeforeUpdate = concessaoLiminarRepository.findAll().size();
        concessaoLiminar.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConcessaoLiminarMockMvc
            .perform(
                put(ENTITY_API_URL_ID, concessaoLiminar.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(concessaoLiminar))
            )
            .andExpect(status().isBadRequest());

        // Validate the ConcessaoLiminar in the database
        List<ConcessaoLiminar> concessaoLiminarList = concessaoLiminarRepository.findAll();
        assertThat(concessaoLiminarList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchConcessaoLiminar() throws Exception {
        int databaseSizeBeforeUpdate = concessaoLiminarRepository.findAll().size();
        concessaoLiminar.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConcessaoLiminarMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(concessaoLiminar))
            )
            .andExpect(status().isBadRequest());

        // Validate the ConcessaoLiminar in the database
        List<ConcessaoLiminar> concessaoLiminarList = concessaoLiminarRepository.findAll();
        assertThat(concessaoLiminarList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamConcessaoLiminar() throws Exception {
        int databaseSizeBeforeUpdate = concessaoLiminarRepository.findAll().size();
        concessaoLiminar.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConcessaoLiminarMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(concessaoLiminar))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ConcessaoLiminar in the database
        List<ConcessaoLiminar> concessaoLiminarList = concessaoLiminarRepository.findAll();
        assertThat(concessaoLiminarList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateConcessaoLiminarWithPatch() throws Exception {
        // Initialize the database
        concessaoLiminarRepository.saveAndFlush(concessaoLiminar);

        int databaseSizeBeforeUpdate = concessaoLiminarRepository.findAll().size();

        // Update the concessaoLiminar using partial update
        ConcessaoLiminar partialUpdatedConcessaoLiminar = new ConcessaoLiminar();
        partialUpdatedConcessaoLiminar.setId(concessaoLiminar.getId());

        partialUpdatedConcessaoLiminar.descricao(UPDATED_DESCRICAO);

        restConcessaoLiminarMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedConcessaoLiminar.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedConcessaoLiminar))
            )
            .andExpect(status().isOk());

        // Validate the ConcessaoLiminar in the database
        List<ConcessaoLiminar> concessaoLiminarList = concessaoLiminarRepository.findAll();
        assertThat(concessaoLiminarList).hasSize(databaseSizeBeforeUpdate);
        ConcessaoLiminar testConcessaoLiminar = concessaoLiminarList.get(concessaoLiminarList.size() - 1);
        assertThat(testConcessaoLiminar.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void fullUpdateConcessaoLiminarWithPatch() throws Exception {
        // Initialize the database
        concessaoLiminarRepository.saveAndFlush(concessaoLiminar);

        int databaseSizeBeforeUpdate = concessaoLiminarRepository.findAll().size();

        // Update the concessaoLiminar using partial update
        ConcessaoLiminar partialUpdatedConcessaoLiminar = new ConcessaoLiminar();
        partialUpdatedConcessaoLiminar.setId(concessaoLiminar.getId());

        partialUpdatedConcessaoLiminar.descricao(UPDATED_DESCRICAO);

        restConcessaoLiminarMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedConcessaoLiminar.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedConcessaoLiminar))
            )
            .andExpect(status().isOk());

        // Validate the ConcessaoLiminar in the database
        List<ConcessaoLiminar> concessaoLiminarList = concessaoLiminarRepository.findAll();
        assertThat(concessaoLiminarList).hasSize(databaseSizeBeforeUpdate);
        ConcessaoLiminar testConcessaoLiminar = concessaoLiminarList.get(concessaoLiminarList.size() - 1);
        assertThat(testConcessaoLiminar.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void patchNonExistingConcessaoLiminar() throws Exception {
        int databaseSizeBeforeUpdate = concessaoLiminarRepository.findAll().size();
        concessaoLiminar.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConcessaoLiminarMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, concessaoLiminar.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(concessaoLiminar))
            )
            .andExpect(status().isBadRequest());

        // Validate the ConcessaoLiminar in the database
        List<ConcessaoLiminar> concessaoLiminarList = concessaoLiminarRepository.findAll();
        assertThat(concessaoLiminarList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchConcessaoLiminar() throws Exception {
        int databaseSizeBeforeUpdate = concessaoLiminarRepository.findAll().size();
        concessaoLiminar.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConcessaoLiminarMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(concessaoLiminar))
            )
            .andExpect(status().isBadRequest());

        // Validate the ConcessaoLiminar in the database
        List<ConcessaoLiminar> concessaoLiminarList = concessaoLiminarRepository.findAll();
        assertThat(concessaoLiminarList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamConcessaoLiminar() throws Exception {
        int databaseSizeBeforeUpdate = concessaoLiminarRepository.findAll().size();
        concessaoLiminar.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConcessaoLiminarMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(concessaoLiminar))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ConcessaoLiminar in the database
        List<ConcessaoLiminar> concessaoLiminarList = concessaoLiminarRepository.findAll();
        assertThat(concessaoLiminarList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteConcessaoLiminar() throws Exception {
        // Initialize the database
        concessaoLiminarRepository.saveAndFlush(concessaoLiminar);

        int databaseSizeBeforeDelete = concessaoLiminarRepository.findAll().size();

        // Delete the concessaoLiminar
        restConcessaoLiminarMockMvc
            .perform(delete(ENTITY_API_URL_ID, concessaoLiminar.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ConcessaoLiminar> concessaoLiminarList = concessaoLiminarRepository.findAll();
        assertThat(concessaoLiminarList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
