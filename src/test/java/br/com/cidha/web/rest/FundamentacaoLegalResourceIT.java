package br.com.cidha.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.cidha.IntegrationTest;
import br.com.cidha.domain.FundamentacaoLegal;
import br.com.cidha.domain.ProblemaJuridico;
import br.com.cidha.repository.FundamentacaoLegalRepository;
import br.com.cidha.service.criteria.FundamentacaoLegalCriteria;
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
 * Integration tests for the {@link FundamentacaoLegalResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FundamentacaoLegalResourceIT {

    private static final String DEFAULT_FUNDAMENTACAO_LEGAL = "AAAAAAAAAA";
    private static final String UPDATED_FUNDAMENTACAO_LEGAL = "BBBBBBBBBB";

    private static final String DEFAULT_FOLHAS_FUNDAMENTACAO_LEGAL = "AAAAAAAAAA";
    private static final String UPDATED_FOLHAS_FUNDAMENTACAO_LEGAL = "BBBBBBBBBB";

    private static final String DEFAULT_FUNDAMENTACAO_LEGAL_SUGERIDA = "AAAAAAAAAA";
    private static final String UPDATED_FUNDAMENTACAO_LEGAL_SUGERIDA = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/fundamentacao-legals";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FundamentacaoLegalRepository fundamentacaoLegalRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFundamentacaoLegalMockMvc;

    private FundamentacaoLegal fundamentacaoLegal;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FundamentacaoLegal createEntity(EntityManager em) {
        FundamentacaoLegal fundamentacaoLegal = new FundamentacaoLegal()
            .fundamentacaoLegal(DEFAULT_FUNDAMENTACAO_LEGAL)
            .folhasFundamentacaoLegal(DEFAULT_FOLHAS_FUNDAMENTACAO_LEGAL)
            .fundamentacaoLegalSugerida(DEFAULT_FUNDAMENTACAO_LEGAL_SUGERIDA);
        return fundamentacaoLegal;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FundamentacaoLegal createUpdatedEntity(EntityManager em) {
        FundamentacaoLegal fundamentacaoLegal = new FundamentacaoLegal()
            .fundamentacaoLegal(UPDATED_FUNDAMENTACAO_LEGAL)
            .folhasFundamentacaoLegal(UPDATED_FOLHAS_FUNDAMENTACAO_LEGAL)
            .fundamentacaoLegalSugerida(UPDATED_FUNDAMENTACAO_LEGAL_SUGERIDA);
        return fundamentacaoLegal;
    }

    @BeforeEach
    public void initTest() {
        fundamentacaoLegal = createEntity(em);
    }

    @Test
    @Transactional
    void createFundamentacaoLegal() throws Exception {
        int databaseSizeBeforeCreate = fundamentacaoLegalRepository.findAll().size();
        // Create the FundamentacaoLegal
        restFundamentacaoLegalMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fundamentacaoLegal))
            )
            .andExpect(status().isCreated());

        // Validate the FundamentacaoLegal in the database
        List<FundamentacaoLegal> fundamentacaoLegalList = fundamentacaoLegalRepository.findAll();
        assertThat(fundamentacaoLegalList).hasSize(databaseSizeBeforeCreate + 1);
        FundamentacaoLegal testFundamentacaoLegal = fundamentacaoLegalList.get(fundamentacaoLegalList.size() - 1);
        assertThat(testFundamentacaoLegal.getFundamentacaoLegal()).isEqualTo(DEFAULT_FUNDAMENTACAO_LEGAL);
        assertThat(testFundamentacaoLegal.getFolhasFundamentacaoLegal()).isEqualTo(DEFAULT_FOLHAS_FUNDAMENTACAO_LEGAL);
        assertThat(testFundamentacaoLegal.getFundamentacaoLegalSugerida()).isEqualTo(DEFAULT_FUNDAMENTACAO_LEGAL_SUGERIDA);
    }

    @Test
    @Transactional
    void createFundamentacaoLegalWithExistingId() throws Exception {
        // Create the FundamentacaoLegal with an existing ID
        fundamentacaoLegal.setId(1L);

        int databaseSizeBeforeCreate = fundamentacaoLegalRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFundamentacaoLegalMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fundamentacaoLegal))
            )
            .andExpect(status().isBadRequest());

        // Validate the FundamentacaoLegal in the database
        List<FundamentacaoLegal> fundamentacaoLegalList = fundamentacaoLegalRepository.findAll();
        assertThat(fundamentacaoLegalList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFundamentacaoLegals() throws Exception {
        // Initialize the database
        fundamentacaoLegalRepository.saveAndFlush(fundamentacaoLegal);

        // Get all the fundamentacaoLegalList
        restFundamentacaoLegalMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fundamentacaoLegal.getId().intValue())))
            .andExpect(jsonPath("$.[*].fundamentacaoLegal").value(hasItem(DEFAULT_FUNDAMENTACAO_LEGAL.toString())))
            .andExpect(jsonPath("$.[*].folhasFundamentacaoLegal").value(hasItem(DEFAULT_FOLHAS_FUNDAMENTACAO_LEGAL)))
            .andExpect(jsonPath("$.[*].fundamentacaoLegalSugerida").value(hasItem(DEFAULT_FUNDAMENTACAO_LEGAL_SUGERIDA.toString())));
    }

    @Test
    @Transactional
    void getFundamentacaoLegal() throws Exception {
        // Initialize the database
        fundamentacaoLegalRepository.saveAndFlush(fundamentacaoLegal);

        // Get the fundamentacaoLegal
        restFundamentacaoLegalMockMvc
            .perform(get(ENTITY_API_URL_ID, fundamentacaoLegal.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(fundamentacaoLegal.getId().intValue()))
            .andExpect(jsonPath("$.fundamentacaoLegal").value(DEFAULT_FUNDAMENTACAO_LEGAL.toString()))
            .andExpect(jsonPath("$.folhasFundamentacaoLegal").value(DEFAULT_FOLHAS_FUNDAMENTACAO_LEGAL))
            .andExpect(jsonPath("$.fundamentacaoLegalSugerida").value(DEFAULT_FUNDAMENTACAO_LEGAL_SUGERIDA.toString()));
    }

    @Test
    @Transactional
    void getFundamentacaoLegalsByIdFiltering() throws Exception {
        // Initialize the database
        fundamentacaoLegalRepository.saveAndFlush(fundamentacaoLegal);

        Long id = fundamentacaoLegal.getId();

        defaultFundamentacaoLegalShouldBeFound("id.equals=" + id);
        defaultFundamentacaoLegalShouldNotBeFound("id.notEquals=" + id);

        defaultFundamentacaoLegalShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultFundamentacaoLegalShouldNotBeFound("id.greaterThan=" + id);

        defaultFundamentacaoLegalShouldBeFound("id.lessThanOrEqual=" + id);
        defaultFundamentacaoLegalShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllFundamentacaoLegalsByFolhasFundamentacaoLegalIsEqualToSomething() throws Exception {
        // Initialize the database
        fundamentacaoLegalRepository.saveAndFlush(fundamentacaoLegal);

        // Get all the fundamentacaoLegalList where folhasFundamentacaoLegal equals to DEFAULT_FOLHAS_FUNDAMENTACAO_LEGAL
        defaultFundamentacaoLegalShouldBeFound("folhasFundamentacaoLegal.equals=" + DEFAULT_FOLHAS_FUNDAMENTACAO_LEGAL);

        // Get all the fundamentacaoLegalList where folhasFundamentacaoLegal equals to UPDATED_FOLHAS_FUNDAMENTACAO_LEGAL
        defaultFundamentacaoLegalShouldNotBeFound("folhasFundamentacaoLegal.equals=" + UPDATED_FOLHAS_FUNDAMENTACAO_LEGAL);
    }

    @Test
    @Transactional
    void getAllFundamentacaoLegalsByFolhasFundamentacaoLegalIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fundamentacaoLegalRepository.saveAndFlush(fundamentacaoLegal);

        // Get all the fundamentacaoLegalList where folhasFundamentacaoLegal not equals to DEFAULT_FOLHAS_FUNDAMENTACAO_LEGAL
        defaultFundamentacaoLegalShouldNotBeFound("folhasFundamentacaoLegal.notEquals=" + DEFAULT_FOLHAS_FUNDAMENTACAO_LEGAL);

        // Get all the fundamentacaoLegalList where folhasFundamentacaoLegal not equals to UPDATED_FOLHAS_FUNDAMENTACAO_LEGAL
        defaultFundamentacaoLegalShouldBeFound("folhasFundamentacaoLegal.notEquals=" + UPDATED_FOLHAS_FUNDAMENTACAO_LEGAL);
    }

    @Test
    @Transactional
    void getAllFundamentacaoLegalsByFolhasFundamentacaoLegalIsInShouldWork() throws Exception {
        // Initialize the database
        fundamentacaoLegalRepository.saveAndFlush(fundamentacaoLegal);

        // Get all the fundamentacaoLegalList where folhasFundamentacaoLegal in DEFAULT_FOLHAS_FUNDAMENTACAO_LEGAL or UPDATED_FOLHAS_FUNDAMENTACAO_LEGAL
        defaultFundamentacaoLegalShouldBeFound(
            "folhasFundamentacaoLegal.in=" + DEFAULT_FOLHAS_FUNDAMENTACAO_LEGAL + "," + UPDATED_FOLHAS_FUNDAMENTACAO_LEGAL
        );

        // Get all the fundamentacaoLegalList where folhasFundamentacaoLegal equals to UPDATED_FOLHAS_FUNDAMENTACAO_LEGAL
        defaultFundamentacaoLegalShouldNotBeFound("folhasFundamentacaoLegal.in=" + UPDATED_FOLHAS_FUNDAMENTACAO_LEGAL);
    }

    @Test
    @Transactional
    void getAllFundamentacaoLegalsByFolhasFundamentacaoLegalIsNullOrNotNull() throws Exception {
        // Initialize the database
        fundamentacaoLegalRepository.saveAndFlush(fundamentacaoLegal);

        // Get all the fundamentacaoLegalList where folhasFundamentacaoLegal is not null
        defaultFundamentacaoLegalShouldBeFound("folhasFundamentacaoLegal.specified=true");

        // Get all the fundamentacaoLegalList where folhasFundamentacaoLegal is null
        defaultFundamentacaoLegalShouldNotBeFound("folhasFundamentacaoLegal.specified=false");
    }

    @Test
    @Transactional
    void getAllFundamentacaoLegalsByFolhasFundamentacaoLegalContainsSomething() throws Exception {
        // Initialize the database
        fundamentacaoLegalRepository.saveAndFlush(fundamentacaoLegal);

        // Get all the fundamentacaoLegalList where folhasFundamentacaoLegal contains DEFAULT_FOLHAS_FUNDAMENTACAO_LEGAL
        defaultFundamentacaoLegalShouldBeFound("folhasFundamentacaoLegal.contains=" + DEFAULT_FOLHAS_FUNDAMENTACAO_LEGAL);

        // Get all the fundamentacaoLegalList where folhasFundamentacaoLegal contains UPDATED_FOLHAS_FUNDAMENTACAO_LEGAL
        defaultFundamentacaoLegalShouldNotBeFound("folhasFundamentacaoLegal.contains=" + UPDATED_FOLHAS_FUNDAMENTACAO_LEGAL);
    }

    @Test
    @Transactional
    void getAllFundamentacaoLegalsByFolhasFundamentacaoLegalNotContainsSomething() throws Exception {
        // Initialize the database
        fundamentacaoLegalRepository.saveAndFlush(fundamentacaoLegal);

        // Get all the fundamentacaoLegalList where folhasFundamentacaoLegal does not contain DEFAULT_FOLHAS_FUNDAMENTACAO_LEGAL
        defaultFundamentacaoLegalShouldNotBeFound("folhasFundamentacaoLegal.doesNotContain=" + DEFAULT_FOLHAS_FUNDAMENTACAO_LEGAL);

        // Get all the fundamentacaoLegalList where folhasFundamentacaoLegal does not contain UPDATED_FOLHAS_FUNDAMENTACAO_LEGAL
        defaultFundamentacaoLegalShouldBeFound("folhasFundamentacaoLegal.doesNotContain=" + UPDATED_FOLHAS_FUNDAMENTACAO_LEGAL);
    }

    @Test
    @Transactional
    void getAllFundamentacaoLegalsByProblemaJuridicoIsEqualToSomething() throws Exception {
        // Initialize the database
        fundamentacaoLegalRepository.saveAndFlush(fundamentacaoLegal);
        ProblemaJuridico problemaJuridico = ProblemaJuridicoResourceIT.createEntity(em);
        em.persist(problemaJuridico);
        em.flush();
        fundamentacaoLegal.addProblemaJuridico(problemaJuridico);
        fundamentacaoLegalRepository.saveAndFlush(fundamentacaoLegal);
        Long problemaJuridicoId = problemaJuridico.getId();

        // Get all the fundamentacaoLegalList where problemaJuridico equals to problemaJuridicoId
        defaultFundamentacaoLegalShouldBeFound("problemaJuridicoId.equals=" + problemaJuridicoId);

        // Get all the fundamentacaoLegalList where problemaJuridico equals to (problemaJuridicoId + 1)
        defaultFundamentacaoLegalShouldNotBeFound("problemaJuridicoId.equals=" + (problemaJuridicoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultFundamentacaoLegalShouldBeFound(String filter) throws Exception {
        restFundamentacaoLegalMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fundamentacaoLegal.getId().intValue())))
            .andExpect(jsonPath("$.[*].fundamentacaoLegal").value(hasItem(DEFAULT_FUNDAMENTACAO_LEGAL.toString())))
            .andExpect(jsonPath("$.[*].folhasFundamentacaoLegal").value(hasItem(DEFAULT_FOLHAS_FUNDAMENTACAO_LEGAL)))
            .andExpect(jsonPath("$.[*].fundamentacaoLegalSugerida").value(hasItem(DEFAULT_FUNDAMENTACAO_LEGAL_SUGERIDA.toString())));

        // Check, that the count call also returns 1
        restFundamentacaoLegalMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultFundamentacaoLegalShouldNotBeFound(String filter) throws Exception {
        restFundamentacaoLegalMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restFundamentacaoLegalMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingFundamentacaoLegal() throws Exception {
        // Get the fundamentacaoLegal
        restFundamentacaoLegalMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewFundamentacaoLegal() throws Exception {
        // Initialize the database
        fundamentacaoLegalRepository.saveAndFlush(fundamentacaoLegal);

        int databaseSizeBeforeUpdate = fundamentacaoLegalRepository.findAll().size();

        // Update the fundamentacaoLegal
        FundamentacaoLegal updatedFundamentacaoLegal = fundamentacaoLegalRepository.findById(fundamentacaoLegal.getId()).get();
        // Disconnect from session so that the updates on updatedFundamentacaoLegal are not directly saved in db
        em.detach(updatedFundamentacaoLegal);
        updatedFundamentacaoLegal
            .fundamentacaoLegal(UPDATED_FUNDAMENTACAO_LEGAL)
            .folhasFundamentacaoLegal(UPDATED_FOLHAS_FUNDAMENTACAO_LEGAL)
            .fundamentacaoLegalSugerida(UPDATED_FUNDAMENTACAO_LEGAL_SUGERIDA);

        restFundamentacaoLegalMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedFundamentacaoLegal.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedFundamentacaoLegal))
            )
            .andExpect(status().isOk());

        // Validate the FundamentacaoLegal in the database
        List<FundamentacaoLegal> fundamentacaoLegalList = fundamentacaoLegalRepository.findAll();
        assertThat(fundamentacaoLegalList).hasSize(databaseSizeBeforeUpdate);
        FundamentacaoLegal testFundamentacaoLegal = fundamentacaoLegalList.get(fundamentacaoLegalList.size() - 1);
        assertThat(testFundamentacaoLegal.getFundamentacaoLegal()).isEqualTo(UPDATED_FUNDAMENTACAO_LEGAL);
        assertThat(testFundamentacaoLegal.getFolhasFundamentacaoLegal()).isEqualTo(UPDATED_FOLHAS_FUNDAMENTACAO_LEGAL);
        assertThat(testFundamentacaoLegal.getFundamentacaoLegalSugerida()).isEqualTo(UPDATED_FUNDAMENTACAO_LEGAL_SUGERIDA);
    }

    @Test
    @Transactional
    void putNonExistingFundamentacaoLegal() throws Exception {
        int databaseSizeBeforeUpdate = fundamentacaoLegalRepository.findAll().size();
        fundamentacaoLegal.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFundamentacaoLegalMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fundamentacaoLegal.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fundamentacaoLegal))
            )
            .andExpect(status().isBadRequest());

        // Validate the FundamentacaoLegal in the database
        List<FundamentacaoLegal> fundamentacaoLegalList = fundamentacaoLegalRepository.findAll();
        assertThat(fundamentacaoLegalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFundamentacaoLegal() throws Exception {
        int databaseSizeBeforeUpdate = fundamentacaoLegalRepository.findAll().size();
        fundamentacaoLegal.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFundamentacaoLegalMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fundamentacaoLegal))
            )
            .andExpect(status().isBadRequest());

        // Validate the FundamentacaoLegal in the database
        List<FundamentacaoLegal> fundamentacaoLegalList = fundamentacaoLegalRepository.findAll();
        assertThat(fundamentacaoLegalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFundamentacaoLegal() throws Exception {
        int databaseSizeBeforeUpdate = fundamentacaoLegalRepository.findAll().size();
        fundamentacaoLegal.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFundamentacaoLegalMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fundamentacaoLegal))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FundamentacaoLegal in the database
        List<FundamentacaoLegal> fundamentacaoLegalList = fundamentacaoLegalRepository.findAll();
        assertThat(fundamentacaoLegalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFundamentacaoLegalWithPatch() throws Exception {
        // Initialize the database
        fundamentacaoLegalRepository.saveAndFlush(fundamentacaoLegal);

        int databaseSizeBeforeUpdate = fundamentacaoLegalRepository.findAll().size();

        // Update the fundamentacaoLegal using partial update
        FundamentacaoLegal partialUpdatedFundamentacaoLegal = new FundamentacaoLegal();
        partialUpdatedFundamentacaoLegal.setId(fundamentacaoLegal.getId());

        restFundamentacaoLegalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFundamentacaoLegal.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFundamentacaoLegal))
            )
            .andExpect(status().isOk());

        // Validate the FundamentacaoLegal in the database
        List<FundamentacaoLegal> fundamentacaoLegalList = fundamentacaoLegalRepository.findAll();
        assertThat(fundamentacaoLegalList).hasSize(databaseSizeBeforeUpdate);
        FundamentacaoLegal testFundamentacaoLegal = fundamentacaoLegalList.get(fundamentacaoLegalList.size() - 1);
        assertThat(testFundamentacaoLegal.getFundamentacaoLegal()).isEqualTo(DEFAULT_FUNDAMENTACAO_LEGAL);
        assertThat(testFundamentacaoLegal.getFolhasFundamentacaoLegal()).isEqualTo(DEFAULT_FOLHAS_FUNDAMENTACAO_LEGAL);
        assertThat(testFundamentacaoLegal.getFundamentacaoLegalSugerida()).isEqualTo(DEFAULT_FUNDAMENTACAO_LEGAL_SUGERIDA);
    }

    @Test
    @Transactional
    void fullUpdateFundamentacaoLegalWithPatch() throws Exception {
        // Initialize the database
        fundamentacaoLegalRepository.saveAndFlush(fundamentacaoLegal);

        int databaseSizeBeforeUpdate = fundamentacaoLegalRepository.findAll().size();

        // Update the fundamentacaoLegal using partial update
        FundamentacaoLegal partialUpdatedFundamentacaoLegal = new FundamentacaoLegal();
        partialUpdatedFundamentacaoLegal.setId(fundamentacaoLegal.getId());

        partialUpdatedFundamentacaoLegal
            .fundamentacaoLegal(UPDATED_FUNDAMENTACAO_LEGAL)
            .folhasFundamentacaoLegal(UPDATED_FOLHAS_FUNDAMENTACAO_LEGAL)
            .fundamentacaoLegalSugerida(UPDATED_FUNDAMENTACAO_LEGAL_SUGERIDA);

        restFundamentacaoLegalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFundamentacaoLegal.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFundamentacaoLegal))
            )
            .andExpect(status().isOk());

        // Validate the FundamentacaoLegal in the database
        List<FundamentacaoLegal> fundamentacaoLegalList = fundamentacaoLegalRepository.findAll();
        assertThat(fundamentacaoLegalList).hasSize(databaseSizeBeforeUpdate);
        FundamentacaoLegal testFundamentacaoLegal = fundamentacaoLegalList.get(fundamentacaoLegalList.size() - 1);
        assertThat(testFundamentacaoLegal.getFundamentacaoLegal()).isEqualTo(UPDATED_FUNDAMENTACAO_LEGAL);
        assertThat(testFundamentacaoLegal.getFolhasFundamentacaoLegal()).isEqualTo(UPDATED_FOLHAS_FUNDAMENTACAO_LEGAL);
        assertThat(testFundamentacaoLegal.getFundamentacaoLegalSugerida()).isEqualTo(UPDATED_FUNDAMENTACAO_LEGAL_SUGERIDA);
    }

    @Test
    @Transactional
    void patchNonExistingFundamentacaoLegal() throws Exception {
        int databaseSizeBeforeUpdate = fundamentacaoLegalRepository.findAll().size();
        fundamentacaoLegal.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFundamentacaoLegalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, fundamentacaoLegal.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fundamentacaoLegal))
            )
            .andExpect(status().isBadRequest());

        // Validate the FundamentacaoLegal in the database
        List<FundamentacaoLegal> fundamentacaoLegalList = fundamentacaoLegalRepository.findAll();
        assertThat(fundamentacaoLegalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFundamentacaoLegal() throws Exception {
        int databaseSizeBeforeUpdate = fundamentacaoLegalRepository.findAll().size();
        fundamentacaoLegal.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFundamentacaoLegalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fundamentacaoLegal))
            )
            .andExpect(status().isBadRequest());

        // Validate the FundamentacaoLegal in the database
        List<FundamentacaoLegal> fundamentacaoLegalList = fundamentacaoLegalRepository.findAll();
        assertThat(fundamentacaoLegalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFundamentacaoLegal() throws Exception {
        int databaseSizeBeforeUpdate = fundamentacaoLegalRepository.findAll().size();
        fundamentacaoLegal.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFundamentacaoLegalMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fundamentacaoLegal))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FundamentacaoLegal in the database
        List<FundamentacaoLegal> fundamentacaoLegalList = fundamentacaoLegalRepository.findAll();
        assertThat(fundamentacaoLegalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFundamentacaoLegal() throws Exception {
        // Initialize the database
        fundamentacaoLegalRepository.saveAndFlush(fundamentacaoLegal);

        int databaseSizeBeforeDelete = fundamentacaoLegalRepository.findAll().size();

        // Delete the fundamentacaoLegal
        restFundamentacaoLegalMockMvc
            .perform(delete(ENTITY_API_URL_ID, fundamentacaoLegal.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FundamentacaoLegal> fundamentacaoLegalList = fundamentacaoLegalRepository.findAll();
        assertThat(fundamentacaoLegalList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
