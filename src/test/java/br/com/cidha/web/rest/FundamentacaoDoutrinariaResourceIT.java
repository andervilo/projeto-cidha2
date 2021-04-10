package br.com.cidha.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.cidha.IntegrationTest;
import br.com.cidha.domain.FundamentacaoDoutrinaria;
import br.com.cidha.domain.ProblemaJuridico;
import br.com.cidha.repository.FundamentacaoDoutrinariaRepository;
import br.com.cidha.service.criteria.FundamentacaoDoutrinariaCriteria;
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
 * Integration tests for the {@link FundamentacaoDoutrinariaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FundamentacaoDoutrinariaResourceIT {

    private static final String DEFAULT_FUNDAMENTACAO_DOUTRINARIA_CITADA = "AAAAAAAAAA";
    private static final String UPDATED_FUNDAMENTACAO_DOUTRINARIA_CITADA = "BBBBBBBBBB";

    private static final String DEFAULT_FOLHAS_FUNDAMENTACAO_DOUTRINARIA = "AAAAAAAAAA";
    private static final String UPDATED_FOLHAS_FUNDAMENTACAO_DOUTRINARIA = "BBBBBBBBBB";

    private static final String DEFAULT_FUNDAMENTACAO_DOUTRINARIA_SUGERIDA = "AAAAAAAAAA";
    private static final String UPDATED_FUNDAMENTACAO_DOUTRINARIA_SUGERIDA = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/fundamentacao-doutrinarias";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FundamentacaoDoutrinariaRepository fundamentacaoDoutrinariaRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFundamentacaoDoutrinariaMockMvc;

    private FundamentacaoDoutrinaria fundamentacaoDoutrinaria;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FundamentacaoDoutrinaria createEntity(EntityManager em) {
        FundamentacaoDoutrinaria fundamentacaoDoutrinaria = new FundamentacaoDoutrinaria()
            .fundamentacaoDoutrinariaCitada(DEFAULT_FUNDAMENTACAO_DOUTRINARIA_CITADA)
            .folhasFundamentacaoDoutrinaria(DEFAULT_FOLHAS_FUNDAMENTACAO_DOUTRINARIA)
            .fundamentacaoDoutrinariaSugerida(DEFAULT_FUNDAMENTACAO_DOUTRINARIA_SUGERIDA);
        return fundamentacaoDoutrinaria;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FundamentacaoDoutrinaria createUpdatedEntity(EntityManager em) {
        FundamentacaoDoutrinaria fundamentacaoDoutrinaria = new FundamentacaoDoutrinaria()
            .fundamentacaoDoutrinariaCitada(UPDATED_FUNDAMENTACAO_DOUTRINARIA_CITADA)
            .folhasFundamentacaoDoutrinaria(UPDATED_FOLHAS_FUNDAMENTACAO_DOUTRINARIA)
            .fundamentacaoDoutrinariaSugerida(UPDATED_FUNDAMENTACAO_DOUTRINARIA_SUGERIDA);
        return fundamentacaoDoutrinaria;
    }

    @BeforeEach
    public void initTest() {
        fundamentacaoDoutrinaria = createEntity(em);
    }

    @Test
    @Transactional
    void createFundamentacaoDoutrinaria() throws Exception {
        int databaseSizeBeforeCreate = fundamentacaoDoutrinariaRepository.findAll().size();
        // Create the FundamentacaoDoutrinaria
        restFundamentacaoDoutrinariaMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fundamentacaoDoutrinaria))
            )
            .andExpect(status().isCreated());

        // Validate the FundamentacaoDoutrinaria in the database
        List<FundamentacaoDoutrinaria> fundamentacaoDoutrinariaList = fundamentacaoDoutrinariaRepository.findAll();
        assertThat(fundamentacaoDoutrinariaList).hasSize(databaseSizeBeforeCreate + 1);
        FundamentacaoDoutrinaria testFundamentacaoDoutrinaria = fundamentacaoDoutrinariaList.get(fundamentacaoDoutrinariaList.size() - 1);
        assertThat(testFundamentacaoDoutrinaria.getFundamentacaoDoutrinariaCitada()).isEqualTo(DEFAULT_FUNDAMENTACAO_DOUTRINARIA_CITADA);
        assertThat(testFundamentacaoDoutrinaria.getFolhasFundamentacaoDoutrinaria()).isEqualTo(DEFAULT_FOLHAS_FUNDAMENTACAO_DOUTRINARIA);
        assertThat(testFundamentacaoDoutrinaria.getFundamentacaoDoutrinariaSugerida())
            .isEqualTo(DEFAULT_FUNDAMENTACAO_DOUTRINARIA_SUGERIDA);
    }

    @Test
    @Transactional
    void createFundamentacaoDoutrinariaWithExistingId() throws Exception {
        // Create the FundamentacaoDoutrinaria with an existing ID
        fundamentacaoDoutrinaria.setId(1L);

        int databaseSizeBeforeCreate = fundamentacaoDoutrinariaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFundamentacaoDoutrinariaMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fundamentacaoDoutrinaria))
            )
            .andExpect(status().isBadRequest());

        // Validate the FundamentacaoDoutrinaria in the database
        List<FundamentacaoDoutrinaria> fundamentacaoDoutrinariaList = fundamentacaoDoutrinariaRepository.findAll();
        assertThat(fundamentacaoDoutrinariaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFundamentacaoDoutrinarias() throws Exception {
        // Initialize the database
        fundamentacaoDoutrinariaRepository.saveAndFlush(fundamentacaoDoutrinaria);

        // Get all the fundamentacaoDoutrinariaList
        restFundamentacaoDoutrinariaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fundamentacaoDoutrinaria.getId().intValue())))
            .andExpect(jsonPath("$.[*].fundamentacaoDoutrinariaCitada").value(hasItem(DEFAULT_FUNDAMENTACAO_DOUTRINARIA_CITADA.toString())))
            .andExpect(jsonPath("$.[*].folhasFundamentacaoDoutrinaria").value(hasItem(DEFAULT_FOLHAS_FUNDAMENTACAO_DOUTRINARIA)))
            .andExpect(
                jsonPath("$.[*].fundamentacaoDoutrinariaSugerida").value(hasItem(DEFAULT_FUNDAMENTACAO_DOUTRINARIA_SUGERIDA.toString()))
            );
    }

    @Test
    @Transactional
    void getFundamentacaoDoutrinaria() throws Exception {
        // Initialize the database
        fundamentacaoDoutrinariaRepository.saveAndFlush(fundamentacaoDoutrinaria);

        // Get the fundamentacaoDoutrinaria
        restFundamentacaoDoutrinariaMockMvc
            .perform(get(ENTITY_API_URL_ID, fundamentacaoDoutrinaria.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(fundamentacaoDoutrinaria.getId().intValue()))
            .andExpect(jsonPath("$.fundamentacaoDoutrinariaCitada").value(DEFAULT_FUNDAMENTACAO_DOUTRINARIA_CITADA.toString()))
            .andExpect(jsonPath("$.folhasFundamentacaoDoutrinaria").value(DEFAULT_FOLHAS_FUNDAMENTACAO_DOUTRINARIA))
            .andExpect(jsonPath("$.fundamentacaoDoutrinariaSugerida").value(DEFAULT_FUNDAMENTACAO_DOUTRINARIA_SUGERIDA.toString()));
    }

    @Test
    @Transactional
    void getFundamentacaoDoutrinariasByIdFiltering() throws Exception {
        // Initialize the database
        fundamentacaoDoutrinariaRepository.saveAndFlush(fundamentacaoDoutrinaria);

        Long id = fundamentacaoDoutrinaria.getId();

        defaultFundamentacaoDoutrinariaShouldBeFound("id.equals=" + id);
        defaultFundamentacaoDoutrinariaShouldNotBeFound("id.notEquals=" + id);

        defaultFundamentacaoDoutrinariaShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultFundamentacaoDoutrinariaShouldNotBeFound("id.greaterThan=" + id);

        defaultFundamentacaoDoutrinariaShouldBeFound("id.lessThanOrEqual=" + id);
        defaultFundamentacaoDoutrinariaShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllFundamentacaoDoutrinariasByFolhasFundamentacaoDoutrinariaIsEqualToSomething() throws Exception {
        // Initialize the database
        fundamentacaoDoutrinariaRepository.saveAndFlush(fundamentacaoDoutrinaria);

        // Get all the fundamentacaoDoutrinariaList where folhasFundamentacaoDoutrinaria equals to DEFAULT_FOLHAS_FUNDAMENTACAO_DOUTRINARIA
        defaultFundamentacaoDoutrinariaShouldBeFound("folhasFundamentacaoDoutrinaria.equals=" + DEFAULT_FOLHAS_FUNDAMENTACAO_DOUTRINARIA);

        // Get all the fundamentacaoDoutrinariaList where folhasFundamentacaoDoutrinaria equals to UPDATED_FOLHAS_FUNDAMENTACAO_DOUTRINARIA
        defaultFundamentacaoDoutrinariaShouldNotBeFound(
            "folhasFundamentacaoDoutrinaria.equals=" + UPDATED_FOLHAS_FUNDAMENTACAO_DOUTRINARIA
        );
    }

    @Test
    @Transactional
    void getAllFundamentacaoDoutrinariasByFolhasFundamentacaoDoutrinariaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fundamentacaoDoutrinariaRepository.saveAndFlush(fundamentacaoDoutrinaria);

        // Get all the fundamentacaoDoutrinariaList where folhasFundamentacaoDoutrinaria not equals to DEFAULT_FOLHAS_FUNDAMENTACAO_DOUTRINARIA
        defaultFundamentacaoDoutrinariaShouldNotBeFound(
            "folhasFundamentacaoDoutrinaria.notEquals=" + DEFAULT_FOLHAS_FUNDAMENTACAO_DOUTRINARIA
        );

        // Get all the fundamentacaoDoutrinariaList where folhasFundamentacaoDoutrinaria not equals to UPDATED_FOLHAS_FUNDAMENTACAO_DOUTRINARIA
        defaultFundamentacaoDoutrinariaShouldBeFound(
            "folhasFundamentacaoDoutrinaria.notEquals=" + UPDATED_FOLHAS_FUNDAMENTACAO_DOUTRINARIA
        );
    }

    @Test
    @Transactional
    void getAllFundamentacaoDoutrinariasByFolhasFundamentacaoDoutrinariaIsInShouldWork() throws Exception {
        // Initialize the database
        fundamentacaoDoutrinariaRepository.saveAndFlush(fundamentacaoDoutrinaria);

        // Get all the fundamentacaoDoutrinariaList where folhasFundamentacaoDoutrinaria in DEFAULT_FOLHAS_FUNDAMENTACAO_DOUTRINARIA or UPDATED_FOLHAS_FUNDAMENTACAO_DOUTRINARIA
        defaultFundamentacaoDoutrinariaShouldBeFound(
            "folhasFundamentacaoDoutrinaria.in=" + DEFAULT_FOLHAS_FUNDAMENTACAO_DOUTRINARIA + "," + UPDATED_FOLHAS_FUNDAMENTACAO_DOUTRINARIA
        );

        // Get all the fundamentacaoDoutrinariaList where folhasFundamentacaoDoutrinaria equals to UPDATED_FOLHAS_FUNDAMENTACAO_DOUTRINARIA
        defaultFundamentacaoDoutrinariaShouldNotBeFound("folhasFundamentacaoDoutrinaria.in=" + UPDATED_FOLHAS_FUNDAMENTACAO_DOUTRINARIA);
    }

    @Test
    @Transactional
    void getAllFundamentacaoDoutrinariasByFolhasFundamentacaoDoutrinariaIsNullOrNotNull() throws Exception {
        // Initialize the database
        fundamentacaoDoutrinariaRepository.saveAndFlush(fundamentacaoDoutrinaria);

        // Get all the fundamentacaoDoutrinariaList where folhasFundamentacaoDoutrinaria is not null
        defaultFundamentacaoDoutrinariaShouldBeFound("folhasFundamentacaoDoutrinaria.specified=true");

        // Get all the fundamentacaoDoutrinariaList where folhasFundamentacaoDoutrinaria is null
        defaultFundamentacaoDoutrinariaShouldNotBeFound("folhasFundamentacaoDoutrinaria.specified=false");
    }

    @Test
    @Transactional
    void getAllFundamentacaoDoutrinariasByFolhasFundamentacaoDoutrinariaContainsSomething() throws Exception {
        // Initialize the database
        fundamentacaoDoutrinariaRepository.saveAndFlush(fundamentacaoDoutrinaria);

        // Get all the fundamentacaoDoutrinariaList where folhasFundamentacaoDoutrinaria contains DEFAULT_FOLHAS_FUNDAMENTACAO_DOUTRINARIA
        defaultFundamentacaoDoutrinariaShouldBeFound("folhasFundamentacaoDoutrinaria.contains=" + DEFAULT_FOLHAS_FUNDAMENTACAO_DOUTRINARIA);

        // Get all the fundamentacaoDoutrinariaList where folhasFundamentacaoDoutrinaria contains UPDATED_FOLHAS_FUNDAMENTACAO_DOUTRINARIA
        defaultFundamentacaoDoutrinariaShouldNotBeFound(
            "folhasFundamentacaoDoutrinaria.contains=" + UPDATED_FOLHAS_FUNDAMENTACAO_DOUTRINARIA
        );
    }

    @Test
    @Transactional
    void getAllFundamentacaoDoutrinariasByFolhasFundamentacaoDoutrinariaNotContainsSomething() throws Exception {
        // Initialize the database
        fundamentacaoDoutrinariaRepository.saveAndFlush(fundamentacaoDoutrinaria);

        // Get all the fundamentacaoDoutrinariaList where folhasFundamentacaoDoutrinaria does not contain DEFAULT_FOLHAS_FUNDAMENTACAO_DOUTRINARIA
        defaultFundamentacaoDoutrinariaShouldNotBeFound(
            "folhasFundamentacaoDoutrinaria.doesNotContain=" + DEFAULT_FOLHAS_FUNDAMENTACAO_DOUTRINARIA
        );

        // Get all the fundamentacaoDoutrinariaList where folhasFundamentacaoDoutrinaria does not contain UPDATED_FOLHAS_FUNDAMENTACAO_DOUTRINARIA
        defaultFundamentacaoDoutrinariaShouldBeFound(
            "folhasFundamentacaoDoutrinaria.doesNotContain=" + UPDATED_FOLHAS_FUNDAMENTACAO_DOUTRINARIA
        );
    }

    @Test
    @Transactional
    void getAllFundamentacaoDoutrinariasByProblemaJuridicoIsEqualToSomething() throws Exception {
        // Initialize the database
        fundamentacaoDoutrinariaRepository.saveAndFlush(fundamentacaoDoutrinaria);
        ProblemaJuridico problemaJuridico = ProblemaJuridicoResourceIT.createEntity(em);
        em.persist(problemaJuridico);
        em.flush();
        fundamentacaoDoutrinaria.addProblemaJuridico(problemaJuridico);
        fundamentacaoDoutrinariaRepository.saveAndFlush(fundamentacaoDoutrinaria);
        Long problemaJuridicoId = problemaJuridico.getId();

        // Get all the fundamentacaoDoutrinariaList where problemaJuridico equals to problemaJuridicoId
        defaultFundamentacaoDoutrinariaShouldBeFound("problemaJuridicoId.equals=" + problemaJuridicoId);

        // Get all the fundamentacaoDoutrinariaList where problemaJuridico equals to (problemaJuridicoId + 1)
        defaultFundamentacaoDoutrinariaShouldNotBeFound("problemaJuridicoId.equals=" + (problemaJuridicoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultFundamentacaoDoutrinariaShouldBeFound(String filter) throws Exception {
        restFundamentacaoDoutrinariaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fundamentacaoDoutrinaria.getId().intValue())))
            .andExpect(jsonPath("$.[*].fundamentacaoDoutrinariaCitada").value(hasItem(DEFAULT_FUNDAMENTACAO_DOUTRINARIA_CITADA.toString())))
            .andExpect(jsonPath("$.[*].folhasFundamentacaoDoutrinaria").value(hasItem(DEFAULT_FOLHAS_FUNDAMENTACAO_DOUTRINARIA)))
            .andExpect(
                jsonPath("$.[*].fundamentacaoDoutrinariaSugerida").value(hasItem(DEFAULT_FUNDAMENTACAO_DOUTRINARIA_SUGERIDA.toString()))
            );

        // Check, that the count call also returns 1
        restFundamentacaoDoutrinariaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultFundamentacaoDoutrinariaShouldNotBeFound(String filter) throws Exception {
        restFundamentacaoDoutrinariaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restFundamentacaoDoutrinariaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingFundamentacaoDoutrinaria() throws Exception {
        // Get the fundamentacaoDoutrinaria
        restFundamentacaoDoutrinariaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewFundamentacaoDoutrinaria() throws Exception {
        // Initialize the database
        fundamentacaoDoutrinariaRepository.saveAndFlush(fundamentacaoDoutrinaria);

        int databaseSizeBeforeUpdate = fundamentacaoDoutrinariaRepository.findAll().size();

        // Update the fundamentacaoDoutrinaria
        FundamentacaoDoutrinaria updatedFundamentacaoDoutrinaria = fundamentacaoDoutrinariaRepository
            .findById(fundamentacaoDoutrinaria.getId())
            .get();
        // Disconnect from session so that the updates on updatedFundamentacaoDoutrinaria are not directly saved in db
        em.detach(updatedFundamentacaoDoutrinaria);
        updatedFundamentacaoDoutrinaria
            .fundamentacaoDoutrinariaCitada(UPDATED_FUNDAMENTACAO_DOUTRINARIA_CITADA)
            .folhasFundamentacaoDoutrinaria(UPDATED_FOLHAS_FUNDAMENTACAO_DOUTRINARIA)
            .fundamentacaoDoutrinariaSugerida(UPDATED_FUNDAMENTACAO_DOUTRINARIA_SUGERIDA);

        restFundamentacaoDoutrinariaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedFundamentacaoDoutrinaria.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedFundamentacaoDoutrinaria))
            )
            .andExpect(status().isOk());

        // Validate the FundamentacaoDoutrinaria in the database
        List<FundamentacaoDoutrinaria> fundamentacaoDoutrinariaList = fundamentacaoDoutrinariaRepository.findAll();
        assertThat(fundamentacaoDoutrinariaList).hasSize(databaseSizeBeforeUpdate);
        FundamentacaoDoutrinaria testFundamentacaoDoutrinaria = fundamentacaoDoutrinariaList.get(fundamentacaoDoutrinariaList.size() - 1);
        assertThat(testFundamentacaoDoutrinaria.getFundamentacaoDoutrinariaCitada()).isEqualTo(UPDATED_FUNDAMENTACAO_DOUTRINARIA_CITADA);
        assertThat(testFundamentacaoDoutrinaria.getFolhasFundamentacaoDoutrinaria()).isEqualTo(UPDATED_FOLHAS_FUNDAMENTACAO_DOUTRINARIA);
        assertThat(testFundamentacaoDoutrinaria.getFundamentacaoDoutrinariaSugerida())
            .isEqualTo(UPDATED_FUNDAMENTACAO_DOUTRINARIA_SUGERIDA);
    }

    @Test
    @Transactional
    void putNonExistingFundamentacaoDoutrinaria() throws Exception {
        int databaseSizeBeforeUpdate = fundamentacaoDoutrinariaRepository.findAll().size();
        fundamentacaoDoutrinaria.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFundamentacaoDoutrinariaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fundamentacaoDoutrinaria.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fundamentacaoDoutrinaria))
            )
            .andExpect(status().isBadRequest());

        // Validate the FundamentacaoDoutrinaria in the database
        List<FundamentacaoDoutrinaria> fundamentacaoDoutrinariaList = fundamentacaoDoutrinariaRepository.findAll();
        assertThat(fundamentacaoDoutrinariaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFundamentacaoDoutrinaria() throws Exception {
        int databaseSizeBeforeUpdate = fundamentacaoDoutrinariaRepository.findAll().size();
        fundamentacaoDoutrinaria.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFundamentacaoDoutrinariaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fundamentacaoDoutrinaria))
            )
            .andExpect(status().isBadRequest());

        // Validate the FundamentacaoDoutrinaria in the database
        List<FundamentacaoDoutrinaria> fundamentacaoDoutrinariaList = fundamentacaoDoutrinariaRepository.findAll();
        assertThat(fundamentacaoDoutrinariaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFundamentacaoDoutrinaria() throws Exception {
        int databaseSizeBeforeUpdate = fundamentacaoDoutrinariaRepository.findAll().size();
        fundamentacaoDoutrinaria.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFundamentacaoDoutrinariaMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fundamentacaoDoutrinaria))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FundamentacaoDoutrinaria in the database
        List<FundamentacaoDoutrinaria> fundamentacaoDoutrinariaList = fundamentacaoDoutrinariaRepository.findAll();
        assertThat(fundamentacaoDoutrinariaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFundamentacaoDoutrinariaWithPatch() throws Exception {
        // Initialize the database
        fundamentacaoDoutrinariaRepository.saveAndFlush(fundamentacaoDoutrinaria);

        int databaseSizeBeforeUpdate = fundamentacaoDoutrinariaRepository.findAll().size();

        // Update the fundamentacaoDoutrinaria using partial update
        FundamentacaoDoutrinaria partialUpdatedFundamentacaoDoutrinaria = new FundamentacaoDoutrinaria();
        partialUpdatedFundamentacaoDoutrinaria.setId(fundamentacaoDoutrinaria.getId());

        partialUpdatedFundamentacaoDoutrinaria
            .fundamentacaoDoutrinariaCitada(UPDATED_FUNDAMENTACAO_DOUTRINARIA_CITADA)
            .folhasFundamentacaoDoutrinaria(UPDATED_FOLHAS_FUNDAMENTACAO_DOUTRINARIA)
            .fundamentacaoDoutrinariaSugerida(UPDATED_FUNDAMENTACAO_DOUTRINARIA_SUGERIDA);

        restFundamentacaoDoutrinariaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFundamentacaoDoutrinaria.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFundamentacaoDoutrinaria))
            )
            .andExpect(status().isOk());

        // Validate the FundamentacaoDoutrinaria in the database
        List<FundamentacaoDoutrinaria> fundamentacaoDoutrinariaList = fundamentacaoDoutrinariaRepository.findAll();
        assertThat(fundamentacaoDoutrinariaList).hasSize(databaseSizeBeforeUpdate);
        FundamentacaoDoutrinaria testFundamentacaoDoutrinaria = fundamentacaoDoutrinariaList.get(fundamentacaoDoutrinariaList.size() - 1);
        assertThat(testFundamentacaoDoutrinaria.getFundamentacaoDoutrinariaCitada()).isEqualTo(UPDATED_FUNDAMENTACAO_DOUTRINARIA_CITADA);
        assertThat(testFundamentacaoDoutrinaria.getFolhasFundamentacaoDoutrinaria()).isEqualTo(UPDATED_FOLHAS_FUNDAMENTACAO_DOUTRINARIA);
        assertThat(testFundamentacaoDoutrinaria.getFundamentacaoDoutrinariaSugerida())
            .isEqualTo(UPDATED_FUNDAMENTACAO_DOUTRINARIA_SUGERIDA);
    }

    @Test
    @Transactional
    void fullUpdateFundamentacaoDoutrinariaWithPatch() throws Exception {
        // Initialize the database
        fundamentacaoDoutrinariaRepository.saveAndFlush(fundamentacaoDoutrinaria);

        int databaseSizeBeforeUpdate = fundamentacaoDoutrinariaRepository.findAll().size();

        // Update the fundamentacaoDoutrinaria using partial update
        FundamentacaoDoutrinaria partialUpdatedFundamentacaoDoutrinaria = new FundamentacaoDoutrinaria();
        partialUpdatedFundamentacaoDoutrinaria.setId(fundamentacaoDoutrinaria.getId());

        partialUpdatedFundamentacaoDoutrinaria
            .fundamentacaoDoutrinariaCitada(UPDATED_FUNDAMENTACAO_DOUTRINARIA_CITADA)
            .folhasFundamentacaoDoutrinaria(UPDATED_FOLHAS_FUNDAMENTACAO_DOUTRINARIA)
            .fundamentacaoDoutrinariaSugerida(UPDATED_FUNDAMENTACAO_DOUTRINARIA_SUGERIDA);

        restFundamentacaoDoutrinariaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFundamentacaoDoutrinaria.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFundamentacaoDoutrinaria))
            )
            .andExpect(status().isOk());

        // Validate the FundamentacaoDoutrinaria in the database
        List<FundamentacaoDoutrinaria> fundamentacaoDoutrinariaList = fundamentacaoDoutrinariaRepository.findAll();
        assertThat(fundamentacaoDoutrinariaList).hasSize(databaseSizeBeforeUpdate);
        FundamentacaoDoutrinaria testFundamentacaoDoutrinaria = fundamentacaoDoutrinariaList.get(fundamentacaoDoutrinariaList.size() - 1);
        assertThat(testFundamentacaoDoutrinaria.getFundamentacaoDoutrinariaCitada()).isEqualTo(UPDATED_FUNDAMENTACAO_DOUTRINARIA_CITADA);
        assertThat(testFundamentacaoDoutrinaria.getFolhasFundamentacaoDoutrinaria()).isEqualTo(UPDATED_FOLHAS_FUNDAMENTACAO_DOUTRINARIA);
        assertThat(testFundamentacaoDoutrinaria.getFundamentacaoDoutrinariaSugerida())
            .isEqualTo(UPDATED_FUNDAMENTACAO_DOUTRINARIA_SUGERIDA);
    }

    @Test
    @Transactional
    void patchNonExistingFundamentacaoDoutrinaria() throws Exception {
        int databaseSizeBeforeUpdate = fundamentacaoDoutrinariaRepository.findAll().size();
        fundamentacaoDoutrinaria.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFundamentacaoDoutrinariaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, fundamentacaoDoutrinaria.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fundamentacaoDoutrinaria))
            )
            .andExpect(status().isBadRequest());

        // Validate the FundamentacaoDoutrinaria in the database
        List<FundamentacaoDoutrinaria> fundamentacaoDoutrinariaList = fundamentacaoDoutrinariaRepository.findAll();
        assertThat(fundamentacaoDoutrinariaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFundamentacaoDoutrinaria() throws Exception {
        int databaseSizeBeforeUpdate = fundamentacaoDoutrinariaRepository.findAll().size();
        fundamentacaoDoutrinaria.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFundamentacaoDoutrinariaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fundamentacaoDoutrinaria))
            )
            .andExpect(status().isBadRequest());

        // Validate the FundamentacaoDoutrinaria in the database
        List<FundamentacaoDoutrinaria> fundamentacaoDoutrinariaList = fundamentacaoDoutrinariaRepository.findAll();
        assertThat(fundamentacaoDoutrinariaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFundamentacaoDoutrinaria() throws Exception {
        int databaseSizeBeforeUpdate = fundamentacaoDoutrinariaRepository.findAll().size();
        fundamentacaoDoutrinaria.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFundamentacaoDoutrinariaMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fundamentacaoDoutrinaria))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FundamentacaoDoutrinaria in the database
        List<FundamentacaoDoutrinaria> fundamentacaoDoutrinariaList = fundamentacaoDoutrinariaRepository.findAll();
        assertThat(fundamentacaoDoutrinariaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFundamentacaoDoutrinaria() throws Exception {
        // Initialize the database
        fundamentacaoDoutrinariaRepository.saveAndFlush(fundamentacaoDoutrinaria);

        int databaseSizeBeforeDelete = fundamentacaoDoutrinariaRepository.findAll().size();

        // Delete the fundamentacaoDoutrinaria
        restFundamentacaoDoutrinariaMockMvc
            .perform(delete(ENTITY_API_URL_ID, fundamentacaoDoutrinaria.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FundamentacaoDoutrinaria> fundamentacaoDoutrinariaList = fundamentacaoDoutrinariaRepository.findAll();
        assertThat(fundamentacaoDoutrinariaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
