package br.com.cidha.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.cidha.IntegrationTest;
import br.com.cidha.domain.InstrumentoInternacional;
import br.com.cidha.domain.ProblemaJuridico;
import br.com.cidha.repository.InstrumentoInternacionalRepository;
import br.com.cidha.service.criteria.InstrumentoInternacionalCriteria;
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
 * Integration tests for the {@link InstrumentoInternacionalResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class InstrumentoInternacionalResourceIT {

    private static final String DEFAULT_INSTRUMENTO_INTERNACIONAL_CITADO_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_INSTRUMENTO_INTERNACIONAL_CITADO_DESCRICAO = "BBBBBBBBBB";

    private static final String DEFAULT_FOLHAS_INSTRUMENTO_INTERNACIONAL = "AAAAAAAAAA";
    private static final String UPDATED_FOLHAS_INSTRUMENTO_INTERNACIONAL = "BBBBBBBBBB";

    private static final String DEFAULT_INSTRUMENTO_INTERNACIONAL_SUGERIDO = "AAAAAAAAAA";
    private static final String UPDATED_INSTRUMENTO_INTERNACIONAL_SUGERIDO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/instrumento-internacionals";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private InstrumentoInternacionalRepository instrumentoInternacionalRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restInstrumentoInternacionalMockMvc;

    private InstrumentoInternacional instrumentoInternacional;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InstrumentoInternacional createEntity(EntityManager em) {
        InstrumentoInternacional instrumentoInternacional = new InstrumentoInternacional()
            .instrumentoInternacionalCitadoDescricao(DEFAULT_INSTRUMENTO_INTERNACIONAL_CITADO_DESCRICAO)
            .folhasInstrumentoInternacional(DEFAULT_FOLHAS_INSTRUMENTO_INTERNACIONAL)
            .instrumentoInternacionalSugerido(DEFAULT_INSTRUMENTO_INTERNACIONAL_SUGERIDO);
        return instrumentoInternacional;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InstrumentoInternacional createUpdatedEntity(EntityManager em) {
        InstrumentoInternacional instrumentoInternacional = new InstrumentoInternacional()
            .instrumentoInternacionalCitadoDescricao(UPDATED_INSTRUMENTO_INTERNACIONAL_CITADO_DESCRICAO)
            .folhasInstrumentoInternacional(UPDATED_FOLHAS_INSTRUMENTO_INTERNACIONAL)
            .instrumentoInternacionalSugerido(UPDATED_INSTRUMENTO_INTERNACIONAL_SUGERIDO);
        return instrumentoInternacional;
    }

    @BeforeEach
    public void initTest() {
        instrumentoInternacional = createEntity(em);
    }

    @Test
    @Transactional
    void createInstrumentoInternacional() throws Exception {
        int databaseSizeBeforeCreate = instrumentoInternacionalRepository.findAll().size();
        // Create the InstrumentoInternacional
        restInstrumentoInternacionalMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(instrumentoInternacional))
            )
            .andExpect(status().isCreated());

        // Validate the InstrumentoInternacional in the database
        List<InstrumentoInternacional> instrumentoInternacionalList = instrumentoInternacionalRepository.findAll();
        assertThat(instrumentoInternacionalList).hasSize(databaseSizeBeforeCreate + 1);
        InstrumentoInternacional testInstrumentoInternacional = instrumentoInternacionalList.get(instrumentoInternacionalList.size() - 1);
        assertThat(testInstrumentoInternacional.getInstrumentoInternacionalCitadoDescricao())
            .isEqualTo(DEFAULT_INSTRUMENTO_INTERNACIONAL_CITADO_DESCRICAO);
        assertThat(testInstrumentoInternacional.getFolhasInstrumentoInternacional()).isEqualTo(DEFAULT_FOLHAS_INSTRUMENTO_INTERNACIONAL);
        assertThat(testInstrumentoInternacional.getInstrumentoInternacionalSugerido())
            .isEqualTo(DEFAULT_INSTRUMENTO_INTERNACIONAL_SUGERIDO);
    }

    @Test
    @Transactional
    void createInstrumentoInternacionalWithExistingId() throws Exception {
        // Create the InstrumentoInternacional with an existing ID
        instrumentoInternacional.setId(1L);

        int databaseSizeBeforeCreate = instrumentoInternacionalRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restInstrumentoInternacionalMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(instrumentoInternacional))
            )
            .andExpect(status().isBadRequest());

        // Validate the InstrumentoInternacional in the database
        List<InstrumentoInternacional> instrumentoInternacionalList = instrumentoInternacionalRepository.findAll();
        assertThat(instrumentoInternacionalList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllInstrumentoInternacionals() throws Exception {
        // Initialize the database
        instrumentoInternacionalRepository.saveAndFlush(instrumentoInternacional);

        // Get all the instrumentoInternacionalList
        restInstrumentoInternacionalMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(instrumentoInternacional.getId().intValue())))
            .andExpect(
                jsonPath("$.[*].instrumentoInternacionalCitadoDescricao")
                    .value(hasItem(DEFAULT_INSTRUMENTO_INTERNACIONAL_CITADO_DESCRICAO.toString()))
            )
            .andExpect(jsonPath("$.[*].folhasInstrumentoInternacional").value(hasItem(DEFAULT_FOLHAS_INSTRUMENTO_INTERNACIONAL)))
            .andExpect(
                jsonPath("$.[*].instrumentoInternacionalSugerido").value(hasItem(DEFAULT_INSTRUMENTO_INTERNACIONAL_SUGERIDO.toString()))
            );
    }

    @Test
    @Transactional
    void getInstrumentoInternacional() throws Exception {
        // Initialize the database
        instrumentoInternacionalRepository.saveAndFlush(instrumentoInternacional);

        // Get the instrumentoInternacional
        restInstrumentoInternacionalMockMvc
            .perform(get(ENTITY_API_URL_ID, instrumentoInternacional.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(instrumentoInternacional.getId().intValue()))
            .andExpect(
                jsonPath("$.instrumentoInternacionalCitadoDescricao").value(DEFAULT_INSTRUMENTO_INTERNACIONAL_CITADO_DESCRICAO.toString())
            )
            .andExpect(jsonPath("$.folhasInstrumentoInternacional").value(DEFAULT_FOLHAS_INSTRUMENTO_INTERNACIONAL))
            .andExpect(jsonPath("$.instrumentoInternacionalSugerido").value(DEFAULT_INSTRUMENTO_INTERNACIONAL_SUGERIDO.toString()));
    }

    @Test
    @Transactional
    void getInstrumentoInternacionalsByIdFiltering() throws Exception {
        // Initialize the database
        instrumentoInternacionalRepository.saveAndFlush(instrumentoInternacional);

        Long id = instrumentoInternacional.getId();

        defaultInstrumentoInternacionalShouldBeFound("id.equals=" + id);
        defaultInstrumentoInternacionalShouldNotBeFound("id.notEquals=" + id);

        defaultInstrumentoInternacionalShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultInstrumentoInternacionalShouldNotBeFound("id.greaterThan=" + id);

        defaultInstrumentoInternacionalShouldBeFound("id.lessThanOrEqual=" + id);
        defaultInstrumentoInternacionalShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllInstrumentoInternacionalsByFolhasInstrumentoInternacionalIsEqualToSomething() throws Exception {
        // Initialize the database
        instrumentoInternacionalRepository.saveAndFlush(instrumentoInternacional);

        // Get all the instrumentoInternacionalList where folhasInstrumentoInternacional equals to DEFAULT_FOLHAS_INSTRUMENTO_INTERNACIONAL
        defaultInstrumentoInternacionalShouldBeFound("folhasInstrumentoInternacional.equals=" + DEFAULT_FOLHAS_INSTRUMENTO_INTERNACIONAL);

        // Get all the instrumentoInternacionalList where folhasInstrumentoInternacional equals to UPDATED_FOLHAS_INSTRUMENTO_INTERNACIONAL
        defaultInstrumentoInternacionalShouldNotBeFound(
            "folhasInstrumentoInternacional.equals=" + UPDATED_FOLHAS_INSTRUMENTO_INTERNACIONAL
        );
    }

    @Test
    @Transactional
    void getAllInstrumentoInternacionalsByFolhasInstrumentoInternacionalIsNotEqualToSomething() throws Exception {
        // Initialize the database
        instrumentoInternacionalRepository.saveAndFlush(instrumentoInternacional);

        // Get all the instrumentoInternacionalList where folhasInstrumentoInternacional not equals to DEFAULT_FOLHAS_INSTRUMENTO_INTERNACIONAL
        defaultInstrumentoInternacionalShouldNotBeFound(
            "folhasInstrumentoInternacional.notEquals=" + DEFAULT_FOLHAS_INSTRUMENTO_INTERNACIONAL
        );

        // Get all the instrumentoInternacionalList where folhasInstrumentoInternacional not equals to UPDATED_FOLHAS_INSTRUMENTO_INTERNACIONAL
        defaultInstrumentoInternacionalShouldBeFound(
            "folhasInstrumentoInternacional.notEquals=" + UPDATED_FOLHAS_INSTRUMENTO_INTERNACIONAL
        );
    }

    @Test
    @Transactional
    void getAllInstrumentoInternacionalsByFolhasInstrumentoInternacionalIsInShouldWork() throws Exception {
        // Initialize the database
        instrumentoInternacionalRepository.saveAndFlush(instrumentoInternacional);

        // Get all the instrumentoInternacionalList where folhasInstrumentoInternacional in DEFAULT_FOLHAS_INSTRUMENTO_INTERNACIONAL or UPDATED_FOLHAS_INSTRUMENTO_INTERNACIONAL
        defaultInstrumentoInternacionalShouldBeFound(
            "folhasInstrumentoInternacional.in=" + DEFAULT_FOLHAS_INSTRUMENTO_INTERNACIONAL + "," + UPDATED_FOLHAS_INSTRUMENTO_INTERNACIONAL
        );

        // Get all the instrumentoInternacionalList where folhasInstrumentoInternacional equals to UPDATED_FOLHAS_INSTRUMENTO_INTERNACIONAL
        defaultInstrumentoInternacionalShouldNotBeFound("folhasInstrumentoInternacional.in=" + UPDATED_FOLHAS_INSTRUMENTO_INTERNACIONAL);
    }

    @Test
    @Transactional
    void getAllInstrumentoInternacionalsByFolhasInstrumentoInternacionalIsNullOrNotNull() throws Exception {
        // Initialize the database
        instrumentoInternacionalRepository.saveAndFlush(instrumentoInternacional);

        // Get all the instrumentoInternacionalList where folhasInstrumentoInternacional is not null
        defaultInstrumentoInternacionalShouldBeFound("folhasInstrumentoInternacional.specified=true");

        // Get all the instrumentoInternacionalList where folhasInstrumentoInternacional is null
        defaultInstrumentoInternacionalShouldNotBeFound("folhasInstrumentoInternacional.specified=false");
    }

    @Test
    @Transactional
    void getAllInstrumentoInternacionalsByFolhasInstrumentoInternacionalContainsSomething() throws Exception {
        // Initialize the database
        instrumentoInternacionalRepository.saveAndFlush(instrumentoInternacional);

        // Get all the instrumentoInternacionalList where folhasInstrumentoInternacional contains DEFAULT_FOLHAS_INSTRUMENTO_INTERNACIONAL
        defaultInstrumentoInternacionalShouldBeFound("folhasInstrumentoInternacional.contains=" + DEFAULT_FOLHAS_INSTRUMENTO_INTERNACIONAL);

        // Get all the instrumentoInternacionalList where folhasInstrumentoInternacional contains UPDATED_FOLHAS_INSTRUMENTO_INTERNACIONAL
        defaultInstrumentoInternacionalShouldNotBeFound(
            "folhasInstrumentoInternacional.contains=" + UPDATED_FOLHAS_INSTRUMENTO_INTERNACIONAL
        );
    }

    @Test
    @Transactional
    void getAllInstrumentoInternacionalsByFolhasInstrumentoInternacionalNotContainsSomething() throws Exception {
        // Initialize the database
        instrumentoInternacionalRepository.saveAndFlush(instrumentoInternacional);

        // Get all the instrumentoInternacionalList where folhasInstrumentoInternacional does not contain DEFAULT_FOLHAS_INSTRUMENTO_INTERNACIONAL
        defaultInstrumentoInternacionalShouldNotBeFound(
            "folhasInstrumentoInternacional.doesNotContain=" + DEFAULT_FOLHAS_INSTRUMENTO_INTERNACIONAL
        );

        // Get all the instrumentoInternacionalList where folhasInstrumentoInternacional does not contain UPDATED_FOLHAS_INSTRUMENTO_INTERNACIONAL
        defaultInstrumentoInternacionalShouldBeFound(
            "folhasInstrumentoInternacional.doesNotContain=" + UPDATED_FOLHAS_INSTRUMENTO_INTERNACIONAL
        );
    }

    @Test
    @Transactional
    void getAllInstrumentoInternacionalsByProblemaJuridicoIsEqualToSomething() throws Exception {
        // Initialize the database
        instrumentoInternacionalRepository.saveAndFlush(instrumentoInternacional);
        ProblemaJuridico problemaJuridico = ProblemaJuridicoResourceIT.createEntity(em);
        em.persist(problemaJuridico);
        em.flush();
        instrumentoInternacional.addProblemaJuridico(problemaJuridico);
        instrumentoInternacionalRepository.saveAndFlush(instrumentoInternacional);
        Long problemaJuridicoId = problemaJuridico.getId();

        // Get all the instrumentoInternacionalList where problemaJuridico equals to problemaJuridicoId
        defaultInstrumentoInternacionalShouldBeFound("problemaJuridicoId.equals=" + problemaJuridicoId);

        // Get all the instrumentoInternacionalList where problemaJuridico equals to (problemaJuridicoId + 1)
        defaultInstrumentoInternacionalShouldNotBeFound("problemaJuridicoId.equals=" + (problemaJuridicoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultInstrumentoInternacionalShouldBeFound(String filter) throws Exception {
        restInstrumentoInternacionalMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(instrumentoInternacional.getId().intValue())))
            .andExpect(
                jsonPath("$.[*].instrumentoInternacionalCitadoDescricao")
                    .value(hasItem(DEFAULT_INSTRUMENTO_INTERNACIONAL_CITADO_DESCRICAO.toString()))
            )
            .andExpect(jsonPath("$.[*].folhasInstrumentoInternacional").value(hasItem(DEFAULT_FOLHAS_INSTRUMENTO_INTERNACIONAL)))
            .andExpect(
                jsonPath("$.[*].instrumentoInternacionalSugerido").value(hasItem(DEFAULT_INSTRUMENTO_INTERNACIONAL_SUGERIDO.toString()))
            );

        // Check, that the count call also returns 1
        restInstrumentoInternacionalMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultInstrumentoInternacionalShouldNotBeFound(String filter) throws Exception {
        restInstrumentoInternacionalMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restInstrumentoInternacionalMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingInstrumentoInternacional() throws Exception {
        // Get the instrumentoInternacional
        restInstrumentoInternacionalMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewInstrumentoInternacional() throws Exception {
        // Initialize the database
        instrumentoInternacionalRepository.saveAndFlush(instrumentoInternacional);

        int databaseSizeBeforeUpdate = instrumentoInternacionalRepository.findAll().size();

        // Update the instrumentoInternacional
        InstrumentoInternacional updatedInstrumentoInternacional = instrumentoInternacionalRepository
            .findById(instrumentoInternacional.getId())
            .get();
        // Disconnect from session so that the updates on updatedInstrumentoInternacional are not directly saved in db
        em.detach(updatedInstrumentoInternacional);
        updatedInstrumentoInternacional
            .instrumentoInternacionalCitadoDescricao(UPDATED_INSTRUMENTO_INTERNACIONAL_CITADO_DESCRICAO)
            .folhasInstrumentoInternacional(UPDATED_FOLHAS_INSTRUMENTO_INTERNACIONAL)
            .instrumentoInternacionalSugerido(UPDATED_INSTRUMENTO_INTERNACIONAL_SUGERIDO);

        restInstrumentoInternacionalMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedInstrumentoInternacional.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedInstrumentoInternacional))
            )
            .andExpect(status().isOk());

        // Validate the InstrumentoInternacional in the database
        List<InstrumentoInternacional> instrumentoInternacionalList = instrumentoInternacionalRepository.findAll();
        assertThat(instrumentoInternacionalList).hasSize(databaseSizeBeforeUpdate);
        InstrumentoInternacional testInstrumentoInternacional = instrumentoInternacionalList.get(instrumentoInternacionalList.size() - 1);
        assertThat(testInstrumentoInternacional.getInstrumentoInternacionalCitadoDescricao())
            .isEqualTo(UPDATED_INSTRUMENTO_INTERNACIONAL_CITADO_DESCRICAO);
        assertThat(testInstrumentoInternacional.getFolhasInstrumentoInternacional()).isEqualTo(UPDATED_FOLHAS_INSTRUMENTO_INTERNACIONAL);
        assertThat(testInstrumentoInternacional.getInstrumentoInternacionalSugerido())
            .isEqualTo(UPDATED_INSTRUMENTO_INTERNACIONAL_SUGERIDO);
    }

    @Test
    @Transactional
    void putNonExistingInstrumentoInternacional() throws Exception {
        int databaseSizeBeforeUpdate = instrumentoInternacionalRepository.findAll().size();
        instrumentoInternacional.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInstrumentoInternacionalMockMvc
            .perform(
                put(ENTITY_API_URL_ID, instrumentoInternacional.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(instrumentoInternacional))
            )
            .andExpect(status().isBadRequest());

        // Validate the InstrumentoInternacional in the database
        List<InstrumentoInternacional> instrumentoInternacionalList = instrumentoInternacionalRepository.findAll();
        assertThat(instrumentoInternacionalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchInstrumentoInternacional() throws Exception {
        int databaseSizeBeforeUpdate = instrumentoInternacionalRepository.findAll().size();
        instrumentoInternacional.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInstrumentoInternacionalMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(instrumentoInternacional))
            )
            .andExpect(status().isBadRequest());

        // Validate the InstrumentoInternacional in the database
        List<InstrumentoInternacional> instrumentoInternacionalList = instrumentoInternacionalRepository.findAll();
        assertThat(instrumentoInternacionalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamInstrumentoInternacional() throws Exception {
        int databaseSizeBeforeUpdate = instrumentoInternacionalRepository.findAll().size();
        instrumentoInternacional.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInstrumentoInternacionalMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(instrumentoInternacional))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the InstrumentoInternacional in the database
        List<InstrumentoInternacional> instrumentoInternacionalList = instrumentoInternacionalRepository.findAll();
        assertThat(instrumentoInternacionalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateInstrumentoInternacionalWithPatch() throws Exception {
        // Initialize the database
        instrumentoInternacionalRepository.saveAndFlush(instrumentoInternacional);

        int databaseSizeBeforeUpdate = instrumentoInternacionalRepository.findAll().size();

        // Update the instrumentoInternacional using partial update
        InstrumentoInternacional partialUpdatedInstrumentoInternacional = new InstrumentoInternacional();
        partialUpdatedInstrumentoInternacional.setId(instrumentoInternacional.getId());

        partialUpdatedInstrumentoInternacional
            .instrumentoInternacionalCitadoDescricao(UPDATED_INSTRUMENTO_INTERNACIONAL_CITADO_DESCRICAO)
            .instrumentoInternacionalSugerido(UPDATED_INSTRUMENTO_INTERNACIONAL_SUGERIDO);

        restInstrumentoInternacionalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInstrumentoInternacional.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedInstrumentoInternacional))
            )
            .andExpect(status().isOk());

        // Validate the InstrumentoInternacional in the database
        List<InstrumentoInternacional> instrumentoInternacionalList = instrumentoInternacionalRepository.findAll();
        assertThat(instrumentoInternacionalList).hasSize(databaseSizeBeforeUpdate);
        InstrumentoInternacional testInstrumentoInternacional = instrumentoInternacionalList.get(instrumentoInternacionalList.size() - 1);
        assertThat(testInstrumentoInternacional.getInstrumentoInternacionalCitadoDescricao())
            .isEqualTo(UPDATED_INSTRUMENTO_INTERNACIONAL_CITADO_DESCRICAO);
        assertThat(testInstrumentoInternacional.getFolhasInstrumentoInternacional()).isEqualTo(DEFAULT_FOLHAS_INSTRUMENTO_INTERNACIONAL);
        assertThat(testInstrumentoInternacional.getInstrumentoInternacionalSugerido())
            .isEqualTo(UPDATED_INSTRUMENTO_INTERNACIONAL_SUGERIDO);
    }

    @Test
    @Transactional
    void fullUpdateInstrumentoInternacionalWithPatch() throws Exception {
        // Initialize the database
        instrumentoInternacionalRepository.saveAndFlush(instrumentoInternacional);

        int databaseSizeBeforeUpdate = instrumentoInternacionalRepository.findAll().size();

        // Update the instrumentoInternacional using partial update
        InstrumentoInternacional partialUpdatedInstrumentoInternacional = new InstrumentoInternacional();
        partialUpdatedInstrumentoInternacional.setId(instrumentoInternacional.getId());

        partialUpdatedInstrumentoInternacional
            .instrumentoInternacionalCitadoDescricao(UPDATED_INSTRUMENTO_INTERNACIONAL_CITADO_DESCRICAO)
            .folhasInstrumentoInternacional(UPDATED_FOLHAS_INSTRUMENTO_INTERNACIONAL)
            .instrumentoInternacionalSugerido(UPDATED_INSTRUMENTO_INTERNACIONAL_SUGERIDO);

        restInstrumentoInternacionalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInstrumentoInternacional.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedInstrumentoInternacional))
            )
            .andExpect(status().isOk());

        // Validate the InstrumentoInternacional in the database
        List<InstrumentoInternacional> instrumentoInternacionalList = instrumentoInternacionalRepository.findAll();
        assertThat(instrumentoInternacionalList).hasSize(databaseSizeBeforeUpdate);
        InstrumentoInternacional testInstrumentoInternacional = instrumentoInternacionalList.get(instrumentoInternacionalList.size() - 1);
        assertThat(testInstrumentoInternacional.getInstrumentoInternacionalCitadoDescricao())
            .isEqualTo(UPDATED_INSTRUMENTO_INTERNACIONAL_CITADO_DESCRICAO);
        assertThat(testInstrumentoInternacional.getFolhasInstrumentoInternacional()).isEqualTo(UPDATED_FOLHAS_INSTRUMENTO_INTERNACIONAL);
        assertThat(testInstrumentoInternacional.getInstrumentoInternacionalSugerido())
            .isEqualTo(UPDATED_INSTRUMENTO_INTERNACIONAL_SUGERIDO);
    }

    @Test
    @Transactional
    void patchNonExistingInstrumentoInternacional() throws Exception {
        int databaseSizeBeforeUpdate = instrumentoInternacionalRepository.findAll().size();
        instrumentoInternacional.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInstrumentoInternacionalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, instrumentoInternacional.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(instrumentoInternacional))
            )
            .andExpect(status().isBadRequest());

        // Validate the InstrumentoInternacional in the database
        List<InstrumentoInternacional> instrumentoInternacionalList = instrumentoInternacionalRepository.findAll();
        assertThat(instrumentoInternacionalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchInstrumentoInternacional() throws Exception {
        int databaseSizeBeforeUpdate = instrumentoInternacionalRepository.findAll().size();
        instrumentoInternacional.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInstrumentoInternacionalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(instrumentoInternacional))
            )
            .andExpect(status().isBadRequest());

        // Validate the InstrumentoInternacional in the database
        List<InstrumentoInternacional> instrumentoInternacionalList = instrumentoInternacionalRepository.findAll();
        assertThat(instrumentoInternacionalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamInstrumentoInternacional() throws Exception {
        int databaseSizeBeforeUpdate = instrumentoInternacionalRepository.findAll().size();
        instrumentoInternacional.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInstrumentoInternacionalMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(instrumentoInternacional))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the InstrumentoInternacional in the database
        List<InstrumentoInternacional> instrumentoInternacionalList = instrumentoInternacionalRepository.findAll();
        assertThat(instrumentoInternacionalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteInstrumentoInternacional() throws Exception {
        // Initialize the database
        instrumentoInternacionalRepository.saveAndFlush(instrumentoInternacional);

        int databaseSizeBeforeDelete = instrumentoInternacionalRepository.findAll().size();

        // Delete the instrumentoInternacional
        restInstrumentoInternacionalMockMvc
            .perform(delete(ENTITY_API_URL_ID, instrumentoInternacional.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<InstrumentoInternacional> instrumentoInternacionalList = instrumentoInternacionalRepository.findAll();
        assertThat(instrumentoInternacionalList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
