package br.com.cidha.web.rest;

import br.com.cidha.CidhaApp;
import br.com.cidha.domain.FundamentacaoLegal;
import br.com.cidha.domain.ProblemaJuridico;
import br.com.cidha.repository.FundamentacaoLegalRepository;
import br.com.cidha.service.FundamentacaoLegalService;
import br.com.cidha.service.dto.FundamentacaoLegalCriteria;
import br.com.cidha.service.FundamentacaoLegalQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link FundamentacaoLegalResource} REST controller.
 */
@SpringBootTest(classes = CidhaApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class FundamentacaoLegalResourceIT {

    private static final String DEFAULT_FUNDAMENTACAO_LEGAL = "AAAAAAAAAA";
    private static final String UPDATED_FUNDAMENTACAO_LEGAL = "BBBBBBBBBB";

    private static final String DEFAULT_FOLHAS_FUNDAMENTACAO_LEGAL = "AAAAAAAAAA";
    private static final String UPDATED_FOLHAS_FUNDAMENTACAO_LEGAL = "BBBBBBBBBB";

    private static final String DEFAULT_FUNDAMENTACAO_LEGAL_SUGERIDA = "AAAAAAAAAA";
    private static final String UPDATED_FUNDAMENTACAO_LEGAL_SUGERIDA = "BBBBBBBBBB";

    @Autowired
    private FundamentacaoLegalRepository fundamentacaoLegalRepository;

    @Autowired
    private FundamentacaoLegalService fundamentacaoLegalService;

    @Autowired
    private FundamentacaoLegalQueryService fundamentacaoLegalQueryService;

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
    public void createFundamentacaoLegal() throws Exception {
        int databaseSizeBeforeCreate = fundamentacaoLegalRepository.findAll().size();
        // Create the FundamentacaoLegal
        restFundamentacaoLegalMockMvc.perform(post("/api/fundamentacao-legals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(fundamentacaoLegal)))
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
    public void createFundamentacaoLegalWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = fundamentacaoLegalRepository.findAll().size();

        // Create the FundamentacaoLegal with an existing ID
        fundamentacaoLegal.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFundamentacaoLegalMockMvc.perform(post("/api/fundamentacao-legals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(fundamentacaoLegal)))
            .andExpect(status().isBadRequest());

        // Validate the FundamentacaoLegal in the database
        List<FundamentacaoLegal> fundamentacaoLegalList = fundamentacaoLegalRepository.findAll();
        assertThat(fundamentacaoLegalList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllFundamentacaoLegals() throws Exception {
        // Initialize the database
        fundamentacaoLegalRepository.saveAndFlush(fundamentacaoLegal);

        // Get all the fundamentacaoLegalList
        restFundamentacaoLegalMockMvc.perform(get("/api/fundamentacao-legals?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fundamentacaoLegal.getId().intValue())))
            .andExpect(jsonPath("$.[*].fundamentacaoLegal").value(hasItem(DEFAULT_FUNDAMENTACAO_LEGAL.toString())))
            .andExpect(jsonPath("$.[*].folhasFundamentacaoLegal").value(hasItem(DEFAULT_FOLHAS_FUNDAMENTACAO_LEGAL)))
            .andExpect(jsonPath("$.[*].fundamentacaoLegalSugerida").value(hasItem(DEFAULT_FUNDAMENTACAO_LEGAL_SUGERIDA.toString())));
    }
    
    @Test
    @Transactional
    public void getFundamentacaoLegal() throws Exception {
        // Initialize the database
        fundamentacaoLegalRepository.saveAndFlush(fundamentacaoLegal);

        // Get the fundamentacaoLegal
        restFundamentacaoLegalMockMvc.perform(get("/api/fundamentacao-legals/{id}", fundamentacaoLegal.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(fundamentacaoLegal.getId().intValue()))
            .andExpect(jsonPath("$.fundamentacaoLegal").value(DEFAULT_FUNDAMENTACAO_LEGAL.toString()))
            .andExpect(jsonPath("$.folhasFundamentacaoLegal").value(DEFAULT_FOLHAS_FUNDAMENTACAO_LEGAL))
            .andExpect(jsonPath("$.fundamentacaoLegalSugerida").value(DEFAULT_FUNDAMENTACAO_LEGAL_SUGERIDA.toString()));
    }


    @Test
    @Transactional
    public void getFundamentacaoLegalsByIdFiltering() throws Exception {
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
    public void getAllFundamentacaoLegalsByFolhasFundamentacaoLegalIsEqualToSomething() throws Exception {
        // Initialize the database
        fundamentacaoLegalRepository.saveAndFlush(fundamentacaoLegal);

        // Get all the fundamentacaoLegalList where folhasFundamentacaoLegal equals to DEFAULT_FOLHAS_FUNDAMENTACAO_LEGAL
        defaultFundamentacaoLegalShouldBeFound("folhasFundamentacaoLegal.equals=" + DEFAULT_FOLHAS_FUNDAMENTACAO_LEGAL);

        // Get all the fundamentacaoLegalList where folhasFundamentacaoLegal equals to UPDATED_FOLHAS_FUNDAMENTACAO_LEGAL
        defaultFundamentacaoLegalShouldNotBeFound("folhasFundamentacaoLegal.equals=" + UPDATED_FOLHAS_FUNDAMENTACAO_LEGAL);
    }

    @Test
    @Transactional
    public void getAllFundamentacaoLegalsByFolhasFundamentacaoLegalIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fundamentacaoLegalRepository.saveAndFlush(fundamentacaoLegal);

        // Get all the fundamentacaoLegalList where folhasFundamentacaoLegal not equals to DEFAULT_FOLHAS_FUNDAMENTACAO_LEGAL
        defaultFundamentacaoLegalShouldNotBeFound("folhasFundamentacaoLegal.notEquals=" + DEFAULT_FOLHAS_FUNDAMENTACAO_LEGAL);

        // Get all the fundamentacaoLegalList where folhasFundamentacaoLegal not equals to UPDATED_FOLHAS_FUNDAMENTACAO_LEGAL
        defaultFundamentacaoLegalShouldBeFound("folhasFundamentacaoLegal.notEquals=" + UPDATED_FOLHAS_FUNDAMENTACAO_LEGAL);
    }

    @Test
    @Transactional
    public void getAllFundamentacaoLegalsByFolhasFundamentacaoLegalIsInShouldWork() throws Exception {
        // Initialize the database
        fundamentacaoLegalRepository.saveAndFlush(fundamentacaoLegal);

        // Get all the fundamentacaoLegalList where folhasFundamentacaoLegal in DEFAULT_FOLHAS_FUNDAMENTACAO_LEGAL or UPDATED_FOLHAS_FUNDAMENTACAO_LEGAL
        defaultFundamentacaoLegalShouldBeFound("folhasFundamentacaoLegal.in=" + DEFAULT_FOLHAS_FUNDAMENTACAO_LEGAL + "," + UPDATED_FOLHAS_FUNDAMENTACAO_LEGAL);

        // Get all the fundamentacaoLegalList where folhasFundamentacaoLegal equals to UPDATED_FOLHAS_FUNDAMENTACAO_LEGAL
        defaultFundamentacaoLegalShouldNotBeFound("folhasFundamentacaoLegal.in=" + UPDATED_FOLHAS_FUNDAMENTACAO_LEGAL);
    }

    @Test
    @Transactional
    public void getAllFundamentacaoLegalsByFolhasFundamentacaoLegalIsNullOrNotNull() throws Exception {
        // Initialize the database
        fundamentacaoLegalRepository.saveAndFlush(fundamentacaoLegal);

        // Get all the fundamentacaoLegalList where folhasFundamentacaoLegal is not null
        defaultFundamentacaoLegalShouldBeFound("folhasFundamentacaoLegal.specified=true");

        // Get all the fundamentacaoLegalList where folhasFundamentacaoLegal is null
        defaultFundamentacaoLegalShouldNotBeFound("folhasFundamentacaoLegal.specified=false");
    }
                @Test
    @Transactional
    public void getAllFundamentacaoLegalsByFolhasFundamentacaoLegalContainsSomething() throws Exception {
        // Initialize the database
        fundamentacaoLegalRepository.saveAndFlush(fundamentacaoLegal);

        // Get all the fundamentacaoLegalList where folhasFundamentacaoLegal contains DEFAULT_FOLHAS_FUNDAMENTACAO_LEGAL
        defaultFundamentacaoLegalShouldBeFound("folhasFundamentacaoLegal.contains=" + DEFAULT_FOLHAS_FUNDAMENTACAO_LEGAL);

        // Get all the fundamentacaoLegalList where folhasFundamentacaoLegal contains UPDATED_FOLHAS_FUNDAMENTACAO_LEGAL
        defaultFundamentacaoLegalShouldNotBeFound("folhasFundamentacaoLegal.contains=" + UPDATED_FOLHAS_FUNDAMENTACAO_LEGAL);
    }

    @Test
    @Transactional
    public void getAllFundamentacaoLegalsByFolhasFundamentacaoLegalNotContainsSomething() throws Exception {
        // Initialize the database
        fundamentacaoLegalRepository.saveAndFlush(fundamentacaoLegal);

        // Get all the fundamentacaoLegalList where folhasFundamentacaoLegal does not contain DEFAULT_FOLHAS_FUNDAMENTACAO_LEGAL
        defaultFundamentacaoLegalShouldNotBeFound("folhasFundamentacaoLegal.doesNotContain=" + DEFAULT_FOLHAS_FUNDAMENTACAO_LEGAL);

        // Get all the fundamentacaoLegalList where folhasFundamentacaoLegal does not contain UPDATED_FOLHAS_FUNDAMENTACAO_LEGAL
        defaultFundamentacaoLegalShouldBeFound("folhasFundamentacaoLegal.doesNotContain=" + UPDATED_FOLHAS_FUNDAMENTACAO_LEGAL);
    }


    @Test
    @Transactional
    public void getAllFundamentacaoLegalsByProblemaJuridicoIsEqualToSomething() throws Exception {
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

        // Get all the fundamentacaoLegalList where problemaJuridico equals to problemaJuridicoId + 1
        defaultFundamentacaoLegalShouldNotBeFound("problemaJuridicoId.equals=" + (problemaJuridicoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultFundamentacaoLegalShouldBeFound(String filter) throws Exception {
        restFundamentacaoLegalMockMvc.perform(get("/api/fundamentacao-legals?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fundamentacaoLegal.getId().intValue())))
            .andExpect(jsonPath("$.[*].fundamentacaoLegal").value(hasItem(DEFAULT_FUNDAMENTACAO_LEGAL.toString())))
            .andExpect(jsonPath("$.[*].folhasFundamentacaoLegal").value(hasItem(DEFAULT_FOLHAS_FUNDAMENTACAO_LEGAL)))
            .andExpect(jsonPath("$.[*].fundamentacaoLegalSugerida").value(hasItem(DEFAULT_FUNDAMENTACAO_LEGAL_SUGERIDA.toString())));

        // Check, that the count call also returns 1
        restFundamentacaoLegalMockMvc.perform(get("/api/fundamentacao-legals/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultFundamentacaoLegalShouldNotBeFound(String filter) throws Exception {
        restFundamentacaoLegalMockMvc.perform(get("/api/fundamentacao-legals?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restFundamentacaoLegalMockMvc.perform(get("/api/fundamentacao-legals/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingFundamentacaoLegal() throws Exception {
        // Get the fundamentacaoLegal
        restFundamentacaoLegalMockMvc.perform(get("/api/fundamentacao-legals/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFundamentacaoLegal() throws Exception {
        // Initialize the database
        fundamentacaoLegalService.save(fundamentacaoLegal);

        int databaseSizeBeforeUpdate = fundamentacaoLegalRepository.findAll().size();

        // Update the fundamentacaoLegal
        FundamentacaoLegal updatedFundamentacaoLegal = fundamentacaoLegalRepository.findById(fundamentacaoLegal.getId()).get();
        // Disconnect from session so that the updates on updatedFundamentacaoLegal are not directly saved in db
        em.detach(updatedFundamentacaoLegal);
        updatedFundamentacaoLegal
            .fundamentacaoLegal(UPDATED_FUNDAMENTACAO_LEGAL)
            .folhasFundamentacaoLegal(UPDATED_FOLHAS_FUNDAMENTACAO_LEGAL)
            .fundamentacaoLegalSugerida(UPDATED_FUNDAMENTACAO_LEGAL_SUGERIDA);

        restFundamentacaoLegalMockMvc.perform(put("/api/fundamentacao-legals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedFundamentacaoLegal)))
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
    public void updateNonExistingFundamentacaoLegal() throws Exception {
        int databaseSizeBeforeUpdate = fundamentacaoLegalRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFundamentacaoLegalMockMvc.perform(put("/api/fundamentacao-legals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(fundamentacaoLegal)))
            .andExpect(status().isBadRequest());

        // Validate the FundamentacaoLegal in the database
        List<FundamentacaoLegal> fundamentacaoLegalList = fundamentacaoLegalRepository.findAll();
        assertThat(fundamentacaoLegalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFundamentacaoLegal() throws Exception {
        // Initialize the database
        fundamentacaoLegalService.save(fundamentacaoLegal);

        int databaseSizeBeforeDelete = fundamentacaoLegalRepository.findAll().size();

        // Delete the fundamentacaoLegal
        restFundamentacaoLegalMockMvc.perform(delete("/api/fundamentacao-legals/{id}", fundamentacaoLegal.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FundamentacaoLegal> fundamentacaoLegalList = fundamentacaoLegalRepository.findAll();
        assertThat(fundamentacaoLegalList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
