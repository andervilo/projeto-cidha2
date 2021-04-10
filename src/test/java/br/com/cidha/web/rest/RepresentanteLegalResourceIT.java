package br.com.cidha.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.cidha.IntegrationTest;
import br.com.cidha.domain.ParteInteresssada;
import br.com.cidha.domain.RepresentanteLegal;
import br.com.cidha.domain.TipoRepresentante;
import br.com.cidha.repository.RepresentanteLegalRepository;
import br.com.cidha.service.criteria.RepresentanteLegalCriteria;
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
 * Integration tests for the {@link RepresentanteLegalResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RepresentanteLegalResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/representante-legals";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RepresentanteLegalRepository representanteLegalRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRepresentanteLegalMockMvc;

    private RepresentanteLegal representanteLegal;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RepresentanteLegal createEntity(EntityManager em) {
        RepresentanteLegal representanteLegal = new RepresentanteLegal().nome(DEFAULT_NOME);
        return representanteLegal;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RepresentanteLegal createUpdatedEntity(EntityManager em) {
        RepresentanteLegal representanteLegal = new RepresentanteLegal().nome(UPDATED_NOME);
        return representanteLegal;
    }

    @BeforeEach
    public void initTest() {
        representanteLegal = createEntity(em);
    }

    @Test
    @Transactional
    void createRepresentanteLegal() throws Exception {
        int databaseSizeBeforeCreate = representanteLegalRepository.findAll().size();
        // Create the RepresentanteLegal
        restRepresentanteLegalMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(representanteLegal))
            )
            .andExpect(status().isCreated());

        // Validate the RepresentanteLegal in the database
        List<RepresentanteLegal> representanteLegalList = representanteLegalRepository.findAll();
        assertThat(representanteLegalList).hasSize(databaseSizeBeforeCreate + 1);
        RepresentanteLegal testRepresentanteLegal = representanteLegalList.get(representanteLegalList.size() - 1);
        assertThat(testRepresentanteLegal.getNome()).isEqualTo(DEFAULT_NOME);
    }

    @Test
    @Transactional
    void createRepresentanteLegalWithExistingId() throws Exception {
        // Create the RepresentanteLegal with an existing ID
        representanteLegal.setId(1L);

        int databaseSizeBeforeCreate = representanteLegalRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRepresentanteLegalMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(representanteLegal))
            )
            .andExpect(status().isBadRequest());

        // Validate the RepresentanteLegal in the database
        List<RepresentanteLegal> representanteLegalList = representanteLegalRepository.findAll();
        assertThat(representanteLegalList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllRepresentanteLegals() throws Exception {
        // Initialize the database
        representanteLegalRepository.saveAndFlush(representanteLegal);

        // Get all the representanteLegalList
        restRepresentanteLegalMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(representanteLegal.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)));
    }

    @Test
    @Transactional
    void getRepresentanteLegal() throws Exception {
        // Initialize the database
        representanteLegalRepository.saveAndFlush(representanteLegal);

        // Get the representanteLegal
        restRepresentanteLegalMockMvc
            .perform(get(ENTITY_API_URL_ID, representanteLegal.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(representanteLegal.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME));
    }

    @Test
    @Transactional
    void getRepresentanteLegalsByIdFiltering() throws Exception {
        // Initialize the database
        representanteLegalRepository.saveAndFlush(representanteLegal);

        Long id = representanteLegal.getId();

        defaultRepresentanteLegalShouldBeFound("id.equals=" + id);
        defaultRepresentanteLegalShouldNotBeFound("id.notEquals=" + id);

        defaultRepresentanteLegalShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultRepresentanteLegalShouldNotBeFound("id.greaterThan=" + id);

        defaultRepresentanteLegalShouldBeFound("id.lessThanOrEqual=" + id);
        defaultRepresentanteLegalShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllRepresentanteLegalsByNomeIsEqualToSomething() throws Exception {
        // Initialize the database
        representanteLegalRepository.saveAndFlush(representanteLegal);

        // Get all the representanteLegalList where nome equals to DEFAULT_NOME
        defaultRepresentanteLegalShouldBeFound("nome.equals=" + DEFAULT_NOME);

        // Get all the representanteLegalList where nome equals to UPDATED_NOME
        defaultRepresentanteLegalShouldNotBeFound("nome.equals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllRepresentanteLegalsByNomeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        representanteLegalRepository.saveAndFlush(representanteLegal);

        // Get all the representanteLegalList where nome not equals to DEFAULT_NOME
        defaultRepresentanteLegalShouldNotBeFound("nome.notEquals=" + DEFAULT_NOME);

        // Get all the representanteLegalList where nome not equals to UPDATED_NOME
        defaultRepresentanteLegalShouldBeFound("nome.notEquals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllRepresentanteLegalsByNomeIsInShouldWork() throws Exception {
        // Initialize the database
        representanteLegalRepository.saveAndFlush(representanteLegal);

        // Get all the representanteLegalList where nome in DEFAULT_NOME or UPDATED_NOME
        defaultRepresentanteLegalShouldBeFound("nome.in=" + DEFAULT_NOME + "," + UPDATED_NOME);

        // Get all the representanteLegalList where nome equals to UPDATED_NOME
        defaultRepresentanteLegalShouldNotBeFound("nome.in=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllRepresentanteLegalsByNomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        representanteLegalRepository.saveAndFlush(representanteLegal);

        // Get all the representanteLegalList where nome is not null
        defaultRepresentanteLegalShouldBeFound("nome.specified=true");

        // Get all the representanteLegalList where nome is null
        defaultRepresentanteLegalShouldNotBeFound("nome.specified=false");
    }

    @Test
    @Transactional
    void getAllRepresentanteLegalsByNomeContainsSomething() throws Exception {
        // Initialize the database
        representanteLegalRepository.saveAndFlush(representanteLegal);

        // Get all the representanteLegalList where nome contains DEFAULT_NOME
        defaultRepresentanteLegalShouldBeFound("nome.contains=" + DEFAULT_NOME);

        // Get all the representanteLegalList where nome contains UPDATED_NOME
        defaultRepresentanteLegalShouldNotBeFound("nome.contains=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllRepresentanteLegalsByNomeNotContainsSomething() throws Exception {
        // Initialize the database
        representanteLegalRepository.saveAndFlush(representanteLegal);

        // Get all the representanteLegalList where nome does not contain DEFAULT_NOME
        defaultRepresentanteLegalShouldNotBeFound("nome.doesNotContain=" + DEFAULT_NOME);

        // Get all the representanteLegalList where nome does not contain UPDATED_NOME
        defaultRepresentanteLegalShouldBeFound("nome.doesNotContain=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllRepresentanteLegalsByTipoRepresentanteIsEqualToSomething() throws Exception {
        // Initialize the database
        representanteLegalRepository.saveAndFlush(representanteLegal);
        TipoRepresentante tipoRepresentante = TipoRepresentanteResourceIT.createEntity(em);
        em.persist(tipoRepresentante);
        em.flush();
        representanteLegal.setTipoRepresentante(tipoRepresentante);
        representanteLegalRepository.saveAndFlush(representanteLegal);
        Long tipoRepresentanteId = tipoRepresentante.getId();

        // Get all the representanteLegalList where tipoRepresentante equals to tipoRepresentanteId
        defaultRepresentanteLegalShouldBeFound("tipoRepresentanteId.equals=" + tipoRepresentanteId);

        // Get all the representanteLegalList where tipoRepresentante equals to (tipoRepresentanteId + 1)
        defaultRepresentanteLegalShouldNotBeFound("tipoRepresentanteId.equals=" + (tipoRepresentanteId + 1));
    }

    @Test
    @Transactional
    void getAllRepresentanteLegalsByProcessoConflitoIsEqualToSomething() throws Exception {
        // Initialize the database
        representanteLegalRepository.saveAndFlush(representanteLegal);
        ParteInteresssada processoConflito = ParteInteresssadaResourceIT.createEntity(em);
        em.persist(processoConflito);
        em.flush();
        representanteLegal.addProcessoConflito(processoConflito);
        representanteLegalRepository.saveAndFlush(representanteLegal);
        Long processoConflitoId = processoConflito.getId();

        // Get all the representanteLegalList where processoConflito equals to processoConflitoId
        defaultRepresentanteLegalShouldBeFound("processoConflitoId.equals=" + processoConflitoId);

        // Get all the representanteLegalList where processoConflito equals to (processoConflitoId + 1)
        defaultRepresentanteLegalShouldNotBeFound("processoConflitoId.equals=" + (processoConflitoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultRepresentanteLegalShouldBeFound(String filter) throws Exception {
        restRepresentanteLegalMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(representanteLegal.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)));

        // Check, that the count call also returns 1
        restRepresentanteLegalMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultRepresentanteLegalShouldNotBeFound(String filter) throws Exception {
        restRepresentanteLegalMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restRepresentanteLegalMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingRepresentanteLegal() throws Exception {
        // Get the representanteLegal
        restRepresentanteLegalMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewRepresentanteLegal() throws Exception {
        // Initialize the database
        representanteLegalRepository.saveAndFlush(representanteLegal);

        int databaseSizeBeforeUpdate = representanteLegalRepository.findAll().size();

        // Update the representanteLegal
        RepresentanteLegal updatedRepresentanteLegal = representanteLegalRepository.findById(representanteLegal.getId()).get();
        // Disconnect from session so that the updates on updatedRepresentanteLegal are not directly saved in db
        em.detach(updatedRepresentanteLegal);
        updatedRepresentanteLegal.nome(UPDATED_NOME);

        restRepresentanteLegalMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedRepresentanteLegal.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedRepresentanteLegal))
            )
            .andExpect(status().isOk());

        // Validate the RepresentanteLegal in the database
        List<RepresentanteLegal> representanteLegalList = representanteLegalRepository.findAll();
        assertThat(representanteLegalList).hasSize(databaseSizeBeforeUpdate);
        RepresentanteLegal testRepresentanteLegal = representanteLegalList.get(representanteLegalList.size() - 1);
        assertThat(testRepresentanteLegal.getNome()).isEqualTo(UPDATED_NOME);
    }

    @Test
    @Transactional
    void putNonExistingRepresentanteLegal() throws Exception {
        int databaseSizeBeforeUpdate = representanteLegalRepository.findAll().size();
        representanteLegal.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRepresentanteLegalMockMvc
            .perform(
                put(ENTITY_API_URL_ID, representanteLegal.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(representanteLegal))
            )
            .andExpect(status().isBadRequest());

        // Validate the RepresentanteLegal in the database
        List<RepresentanteLegal> representanteLegalList = representanteLegalRepository.findAll();
        assertThat(representanteLegalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRepresentanteLegal() throws Exception {
        int databaseSizeBeforeUpdate = representanteLegalRepository.findAll().size();
        representanteLegal.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRepresentanteLegalMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(representanteLegal))
            )
            .andExpect(status().isBadRequest());

        // Validate the RepresentanteLegal in the database
        List<RepresentanteLegal> representanteLegalList = representanteLegalRepository.findAll();
        assertThat(representanteLegalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRepresentanteLegal() throws Exception {
        int databaseSizeBeforeUpdate = representanteLegalRepository.findAll().size();
        representanteLegal.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRepresentanteLegalMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(representanteLegal))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RepresentanteLegal in the database
        List<RepresentanteLegal> representanteLegalList = representanteLegalRepository.findAll();
        assertThat(representanteLegalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRepresentanteLegalWithPatch() throws Exception {
        // Initialize the database
        representanteLegalRepository.saveAndFlush(representanteLegal);

        int databaseSizeBeforeUpdate = representanteLegalRepository.findAll().size();

        // Update the representanteLegal using partial update
        RepresentanteLegal partialUpdatedRepresentanteLegal = new RepresentanteLegal();
        partialUpdatedRepresentanteLegal.setId(representanteLegal.getId());

        restRepresentanteLegalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRepresentanteLegal.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRepresentanteLegal))
            )
            .andExpect(status().isOk());

        // Validate the RepresentanteLegal in the database
        List<RepresentanteLegal> representanteLegalList = representanteLegalRepository.findAll();
        assertThat(representanteLegalList).hasSize(databaseSizeBeforeUpdate);
        RepresentanteLegal testRepresentanteLegal = representanteLegalList.get(representanteLegalList.size() - 1);
        assertThat(testRepresentanteLegal.getNome()).isEqualTo(DEFAULT_NOME);
    }

    @Test
    @Transactional
    void fullUpdateRepresentanteLegalWithPatch() throws Exception {
        // Initialize the database
        representanteLegalRepository.saveAndFlush(representanteLegal);

        int databaseSizeBeforeUpdate = representanteLegalRepository.findAll().size();

        // Update the representanteLegal using partial update
        RepresentanteLegal partialUpdatedRepresentanteLegal = new RepresentanteLegal();
        partialUpdatedRepresentanteLegal.setId(representanteLegal.getId());

        partialUpdatedRepresentanteLegal.nome(UPDATED_NOME);

        restRepresentanteLegalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRepresentanteLegal.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRepresentanteLegal))
            )
            .andExpect(status().isOk());

        // Validate the RepresentanteLegal in the database
        List<RepresentanteLegal> representanteLegalList = representanteLegalRepository.findAll();
        assertThat(representanteLegalList).hasSize(databaseSizeBeforeUpdate);
        RepresentanteLegal testRepresentanteLegal = representanteLegalList.get(representanteLegalList.size() - 1);
        assertThat(testRepresentanteLegal.getNome()).isEqualTo(UPDATED_NOME);
    }

    @Test
    @Transactional
    void patchNonExistingRepresentanteLegal() throws Exception {
        int databaseSizeBeforeUpdate = representanteLegalRepository.findAll().size();
        representanteLegal.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRepresentanteLegalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, representanteLegal.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(representanteLegal))
            )
            .andExpect(status().isBadRequest());

        // Validate the RepresentanteLegal in the database
        List<RepresentanteLegal> representanteLegalList = representanteLegalRepository.findAll();
        assertThat(representanteLegalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRepresentanteLegal() throws Exception {
        int databaseSizeBeforeUpdate = representanteLegalRepository.findAll().size();
        representanteLegal.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRepresentanteLegalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(representanteLegal))
            )
            .andExpect(status().isBadRequest());

        // Validate the RepresentanteLegal in the database
        List<RepresentanteLegal> representanteLegalList = representanteLegalRepository.findAll();
        assertThat(representanteLegalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRepresentanteLegal() throws Exception {
        int databaseSizeBeforeUpdate = representanteLegalRepository.findAll().size();
        representanteLegal.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRepresentanteLegalMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(representanteLegal))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RepresentanteLegal in the database
        List<RepresentanteLegal> representanteLegalList = representanteLegalRepository.findAll();
        assertThat(representanteLegalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRepresentanteLegal() throws Exception {
        // Initialize the database
        representanteLegalRepository.saveAndFlush(representanteLegal);

        int databaseSizeBeforeDelete = representanteLegalRepository.findAll().size();

        // Delete the representanteLegal
        restRepresentanteLegalMockMvc
            .perform(delete(ENTITY_API_URL_ID, representanteLegal.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RepresentanteLegal> representanteLegalList = representanteLegalRepository.findAll();
        assertThat(representanteLegalList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
