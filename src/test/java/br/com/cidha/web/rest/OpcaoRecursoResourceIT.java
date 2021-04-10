package br.com.cidha.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.cidha.IntegrationTest;
import br.com.cidha.domain.OpcaoRecurso;
import br.com.cidha.repository.OpcaoRecursoRepository;
import br.com.cidha.service.criteria.OpcaoRecursoCriteria;
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
 * Integration tests for the {@link OpcaoRecursoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OpcaoRecursoResourceIT {

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/opcao-recursos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private OpcaoRecursoRepository opcaoRecursoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOpcaoRecursoMockMvc;

    private OpcaoRecurso opcaoRecurso;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OpcaoRecurso createEntity(EntityManager em) {
        OpcaoRecurso opcaoRecurso = new OpcaoRecurso().descricao(DEFAULT_DESCRICAO);
        return opcaoRecurso;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OpcaoRecurso createUpdatedEntity(EntityManager em) {
        OpcaoRecurso opcaoRecurso = new OpcaoRecurso().descricao(UPDATED_DESCRICAO);
        return opcaoRecurso;
    }

    @BeforeEach
    public void initTest() {
        opcaoRecurso = createEntity(em);
    }

    @Test
    @Transactional
    void createOpcaoRecurso() throws Exception {
        int databaseSizeBeforeCreate = opcaoRecursoRepository.findAll().size();
        // Create the OpcaoRecurso
        restOpcaoRecursoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(opcaoRecurso)))
            .andExpect(status().isCreated());

        // Validate the OpcaoRecurso in the database
        List<OpcaoRecurso> opcaoRecursoList = opcaoRecursoRepository.findAll();
        assertThat(opcaoRecursoList).hasSize(databaseSizeBeforeCreate + 1);
        OpcaoRecurso testOpcaoRecurso = opcaoRecursoList.get(opcaoRecursoList.size() - 1);
        assertThat(testOpcaoRecurso.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
    }

    @Test
    @Transactional
    void createOpcaoRecursoWithExistingId() throws Exception {
        // Create the OpcaoRecurso with an existing ID
        opcaoRecurso.setId(1L);

        int databaseSizeBeforeCreate = opcaoRecursoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOpcaoRecursoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(opcaoRecurso)))
            .andExpect(status().isBadRequest());

        // Validate the OpcaoRecurso in the database
        List<OpcaoRecurso> opcaoRecursoList = opcaoRecursoRepository.findAll();
        assertThat(opcaoRecursoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllOpcaoRecursos() throws Exception {
        // Initialize the database
        opcaoRecursoRepository.saveAndFlush(opcaoRecurso);

        // Get all the opcaoRecursoList
        restOpcaoRecursoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(opcaoRecurso.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)));
    }

    @Test
    @Transactional
    void getOpcaoRecurso() throws Exception {
        // Initialize the database
        opcaoRecursoRepository.saveAndFlush(opcaoRecurso);

        // Get the opcaoRecurso
        restOpcaoRecursoMockMvc
            .perform(get(ENTITY_API_URL_ID, opcaoRecurso.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(opcaoRecurso.getId().intValue()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO));
    }

    @Test
    @Transactional
    void getOpcaoRecursosByIdFiltering() throws Exception {
        // Initialize the database
        opcaoRecursoRepository.saveAndFlush(opcaoRecurso);

        Long id = opcaoRecurso.getId();

        defaultOpcaoRecursoShouldBeFound("id.equals=" + id);
        defaultOpcaoRecursoShouldNotBeFound("id.notEquals=" + id);

        defaultOpcaoRecursoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultOpcaoRecursoShouldNotBeFound("id.greaterThan=" + id);

        defaultOpcaoRecursoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultOpcaoRecursoShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllOpcaoRecursosByDescricaoIsEqualToSomething() throws Exception {
        // Initialize the database
        opcaoRecursoRepository.saveAndFlush(opcaoRecurso);

        // Get all the opcaoRecursoList where descricao equals to DEFAULT_DESCRICAO
        defaultOpcaoRecursoShouldBeFound("descricao.equals=" + DEFAULT_DESCRICAO);

        // Get all the opcaoRecursoList where descricao equals to UPDATED_DESCRICAO
        defaultOpcaoRecursoShouldNotBeFound("descricao.equals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllOpcaoRecursosByDescricaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        opcaoRecursoRepository.saveAndFlush(opcaoRecurso);

        // Get all the opcaoRecursoList where descricao not equals to DEFAULT_DESCRICAO
        defaultOpcaoRecursoShouldNotBeFound("descricao.notEquals=" + DEFAULT_DESCRICAO);

        // Get all the opcaoRecursoList where descricao not equals to UPDATED_DESCRICAO
        defaultOpcaoRecursoShouldBeFound("descricao.notEquals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllOpcaoRecursosByDescricaoIsInShouldWork() throws Exception {
        // Initialize the database
        opcaoRecursoRepository.saveAndFlush(opcaoRecurso);

        // Get all the opcaoRecursoList where descricao in DEFAULT_DESCRICAO or UPDATED_DESCRICAO
        defaultOpcaoRecursoShouldBeFound("descricao.in=" + DEFAULT_DESCRICAO + "," + UPDATED_DESCRICAO);

        // Get all the opcaoRecursoList where descricao equals to UPDATED_DESCRICAO
        defaultOpcaoRecursoShouldNotBeFound("descricao.in=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllOpcaoRecursosByDescricaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        opcaoRecursoRepository.saveAndFlush(opcaoRecurso);

        // Get all the opcaoRecursoList where descricao is not null
        defaultOpcaoRecursoShouldBeFound("descricao.specified=true");

        // Get all the opcaoRecursoList where descricao is null
        defaultOpcaoRecursoShouldNotBeFound("descricao.specified=false");
    }

    @Test
    @Transactional
    void getAllOpcaoRecursosByDescricaoContainsSomething() throws Exception {
        // Initialize the database
        opcaoRecursoRepository.saveAndFlush(opcaoRecurso);

        // Get all the opcaoRecursoList where descricao contains DEFAULT_DESCRICAO
        defaultOpcaoRecursoShouldBeFound("descricao.contains=" + DEFAULT_DESCRICAO);

        // Get all the opcaoRecursoList where descricao contains UPDATED_DESCRICAO
        defaultOpcaoRecursoShouldNotBeFound("descricao.contains=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllOpcaoRecursosByDescricaoNotContainsSomething() throws Exception {
        // Initialize the database
        opcaoRecursoRepository.saveAndFlush(opcaoRecurso);

        // Get all the opcaoRecursoList where descricao does not contain DEFAULT_DESCRICAO
        defaultOpcaoRecursoShouldNotBeFound("descricao.doesNotContain=" + DEFAULT_DESCRICAO);

        // Get all the opcaoRecursoList where descricao does not contain UPDATED_DESCRICAO
        defaultOpcaoRecursoShouldBeFound("descricao.doesNotContain=" + UPDATED_DESCRICAO);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultOpcaoRecursoShouldBeFound(String filter) throws Exception {
        restOpcaoRecursoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(opcaoRecurso.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)));

        // Check, that the count call also returns 1
        restOpcaoRecursoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultOpcaoRecursoShouldNotBeFound(String filter) throws Exception {
        restOpcaoRecursoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restOpcaoRecursoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingOpcaoRecurso() throws Exception {
        // Get the opcaoRecurso
        restOpcaoRecursoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewOpcaoRecurso() throws Exception {
        // Initialize the database
        opcaoRecursoRepository.saveAndFlush(opcaoRecurso);

        int databaseSizeBeforeUpdate = opcaoRecursoRepository.findAll().size();

        // Update the opcaoRecurso
        OpcaoRecurso updatedOpcaoRecurso = opcaoRecursoRepository.findById(opcaoRecurso.getId()).get();
        // Disconnect from session so that the updates on updatedOpcaoRecurso are not directly saved in db
        em.detach(updatedOpcaoRecurso);
        updatedOpcaoRecurso.descricao(UPDATED_DESCRICAO);

        restOpcaoRecursoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedOpcaoRecurso.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedOpcaoRecurso))
            )
            .andExpect(status().isOk());

        // Validate the OpcaoRecurso in the database
        List<OpcaoRecurso> opcaoRecursoList = opcaoRecursoRepository.findAll();
        assertThat(opcaoRecursoList).hasSize(databaseSizeBeforeUpdate);
        OpcaoRecurso testOpcaoRecurso = opcaoRecursoList.get(opcaoRecursoList.size() - 1);
        assertThat(testOpcaoRecurso.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void putNonExistingOpcaoRecurso() throws Exception {
        int databaseSizeBeforeUpdate = opcaoRecursoRepository.findAll().size();
        opcaoRecurso.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOpcaoRecursoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, opcaoRecurso.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(opcaoRecurso))
            )
            .andExpect(status().isBadRequest());

        // Validate the OpcaoRecurso in the database
        List<OpcaoRecurso> opcaoRecursoList = opcaoRecursoRepository.findAll();
        assertThat(opcaoRecursoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOpcaoRecurso() throws Exception {
        int databaseSizeBeforeUpdate = opcaoRecursoRepository.findAll().size();
        opcaoRecurso.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOpcaoRecursoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(opcaoRecurso))
            )
            .andExpect(status().isBadRequest());

        // Validate the OpcaoRecurso in the database
        List<OpcaoRecurso> opcaoRecursoList = opcaoRecursoRepository.findAll();
        assertThat(opcaoRecursoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOpcaoRecurso() throws Exception {
        int databaseSizeBeforeUpdate = opcaoRecursoRepository.findAll().size();
        opcaoRecurso.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOpcaoRecursoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(opcaoRecurso)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the OpcaoRecurso in the database
        List<OpcaoRecurso> opcaoRecursoList = opcaoRecursoRepository.findAll();
        assertThat(opcaoRecursoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOpcaoRecursoWithPatch() throws Exception {
        // Initialize the database
        opcaoRecursoRepository.saveAndFlush(opcaoRecurso);

        int databaseSizeBeforeUpdate = opcaoRecursoRepository.findAll().size();

        // Update the opcaoRecurso using partial update
        OpcaoRecurso partialUpdatedOpcaoRecurso = new OpcaoRecurso();
        partialUpdatedOpcaoRecurso.setId(opcaoRecurso.getId());

        restOpcaoRecursoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOpcaoRecurso.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOpcaoRecurso))
            )
            .andExpect(status().isOk());

        // Validate the OpcaoRecurso in the database
        List<OpcaoRecurso> opcaoRecursoList = opcaoRecursoRepository.findAll();
        assertThat(opcaoRecursoList).hasSize(databaseSizeBeforeUpdate);
        OpcaoRecurso testOpcaoRecurso = opcaoRecursoList.get(opcaoRecursoList.size() - 1);
        assertThat(testOpcaoRecurso.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
    }

    @Test
    @Transactional
    void fullUpdateOpcaoRecursoWithPatch() throws Exception {
        // Initialize the database
        opcaoRecursoRepository.saveAndFlush(opcaoRecurso);

        int databaseSizeBeforeUpdate = opcaoRecursoRepository.findAll().size();

        // Update the opcaoRecurso using partial update
        OpcaoRecurso partialUpdatedOpcaoRecurso = new OpcaoRecurso();
        partialUpdatedOpcaoRecurso.setId(opcaoRecurso.getId());

        partialUpdatedOpcaoRecurso.descricao(UPDATED_DESCRICAO);

        restOpcaoRecursoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOpcaoRecurso.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOpcaoRecurso))
            )
            .andExpect(status().isOk());

        // Validate the OpcaoRecurso in the database
        List<OpcaoRecurso> opcaoRecursoList = opcaoRecursoRepository.findAll();
        assertThat(opcaoRecursoList).hasSize(databaseSizeBeforeUpdate);
        OpcaoRecurso testOpcaoRecurso = opcaoRecursoList.get(opcaoRecursoList.size() - 1);
        assertThat(testOpcaoRecurso.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void patchNonExistingOpcaoRecurso() throws Exception {
        int databaseSizeBeforeUpdate = opcaoRecursoRepository.findAll().size();
        opcaoRecurso.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOpcaoRecursoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, opcaoRecurso.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(opcaoRecurso))
            )
            .andExpect(status().isBadRequest());

        // Validate the OpcaoRecurso in the database
        List<OpcaoRecurso> opcaoRecursoList = opcaoRecursoRepository.findAll();
        assertThat(opcaoRecursoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOpcaoRecurso() throws Exception {
        int databaseSizeBeforeUpdate = opcaoRecursoRepository.findAll().size();
        opcaoRecurso.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOpcaoRecursoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(opcaoRecurso))
            )
            .andExpect(status().isBadRequest());

        // Validate the OpcaoRecurso in the database
        List<OpcaoRecurso> opcaoRecursoList = opcaoRecursoRepository.findAll();
        assertThat(opcaoRecursoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOpcaoRecurso() throws Exception {
        int databaseSizeBeforeUpdate = opcaoRecursoRepository.findAll().size();
        opcaoRecurso.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOpcaoRecursoMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(opcaoRecurso))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OpcaoRecurso in the database
        List<OpcaoRecurso> opcaoRecursoList = opcaoRecursoRepository.findAll();
        assertThat(opcaoRecursoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOpcaoRecurso() throws Exception {
        // Initialize the database
        opcaoRecursoRepository.saveAndFlush(opcaoRecurso);

        int databaseSizeBeforeDelete = opcaoRecursoRepository.findAll().size();

        // Delete the opcaoRecurso
        restOpcaoRecursoMockMvc
            .perform(delete(ENTITY_API_URL_ID, opcaoRecurso.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OpcaoRecurso> opcaoRecursoList = opcaoRecursoRepository.findAll();
        assertThat(opcaoRecursoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
