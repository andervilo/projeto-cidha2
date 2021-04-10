package br.com.cidha.web.rest;

import br.com.cidha.CidhaApp;
import br.com.cidha.domain.InstrumentoInternacional;
import br.com.cidha.domain.ProblemaJuridico;
import br.com.cidha.repository.InstrumentoInternacionalRepository;
import br.com.cidha.service.InstrumentoInternacionalService;
import br.com.cidha.service.dto.InstrumentoInternacionalCriteria;
import br.com.cidha.service.InstrumentoInternacionalQueryService;

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
 * Integration tests for the {@link InstrumentoInternacionalResource} REST controller.
 */
@SpringBootTest(classes = CidhaApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class InstrumentoInternacionalResourceIT {

    private static final String DEFAULT_INSTRUMENTO_INTERNACIONAL_CITADO_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_INSTRUMENTO_INTERNACIONAL_CITADO_DESCRICAO = "BBBBBBBBBB";

    private static final String DEFAULT_FOLHAS_INSTRUMENTO_INTERNACIONAL = "AAAAAAAAAA";
    private static final String UPDATED_FOLHAS_INSTRUMENTO_INTERNACIONAL = "BBBBBBBBBB";

    private static final String DEFAULT_INSTRUMENTO_INTERNACIONAL_SUGERIDO = "AAAAAAAAAA";
    private static final String UPDATED_INSTRUMENTO_INTERNACIONAL_SUGERIDO = "BBBBBBBBBB";

    @Autowired
    private InstrumentoInternacionalRepository instrumentoInternacionalRepository;

    @Autowired
    private InstrumentoInternacionalService instrumentoInternacionalService;

    @Autowired
    private InstrumentoInternacionalQueryService instrumentoInternacionalQueryService;

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
    public void createInstrumentoInternacional() throws Exception {
        int databaseSizeBeforeCreate = instrumentoInternacionalRepository.findAll().size();
        // Create the InstrumentoInternacional
        restInstrumentoInternacionalMockMvc.perform(post("/api/instrumento-internacionals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(instrumentoInternacional)))
            .andExpect(status().isCreated());

        // Validate the InstrumentoInternacional in the database
        List<InstrumentoInternacional> instrumentoInternacionalList = instrumentoInternacionalRepository.findAll();
        assertThat(instrumentoInternacionalList).hasSize(databaseSizeBeforeCreate + 1);
        InstrumentoInternacional testInstrumentoInternacional = instrumentoInternacionalList.get(instrumentoInternacionalList.size() - 1);
        assertThat(testInstrumentoInternacional.getInstrumentoInternacionalCitadoDescricao()).isEqualTo(DEFAULT_INSTRUMENTO_INTERNACIONAL_CITADO_DESCRICAO);
        assertThat(testInstrumentoInternacional.getFolhasInstrumentoInternacional()).isEqualTo(DEFAULT_FOLHAS_INSTRUMENTO_INTERNACIONAL);
        assertThat(testInstrumentoInternacional.getInstrumentoInternacionalSugerido()).isEqualTo(DEFAULT_INSTRUMENTO_INTERNACIONAL_SUGERIDO);
    }

    @Test
    @Transactional
    public void createInstrumentoInternacionalWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = instrumentoInternacionalRepository.findAll().size();

        // Create the InstrumentoInternacional with an existing ID
        instrumentoInternacional.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInstrumentoInternacionalMockMvc.perform(post("/api/instrumento-internacionals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(instrumentoInternacional)))
            .andExpect(status().isBadRequest());

        // Validate the InstrumentoInternacional in the database
        List<InstrumentoInternacional> instrumentoInternacionalList = instrumentoInternacionalRepository.findAll();
        assertThat(instrumentoInternacionalList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllInstrumentoInternacionals() throws Exception {
        // Initialize the database
        instrumentoInternacionalRepository.saveAndFlush(instrumentoInternacional);

        // Get all the instrumentoInternacionalList
        restInstrumentoInternacionalMockMvc.perform(get("/api/instrumento-internacionals?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(instrumentoInternacional.getId().intValue())))
            .andExpect(jsonPath("$.[*].instrumentoInternacionalCitadoDescricao").value(hasItem(DEFAULT_INSTRUMENTO_INTERNACIONAL_CITADO_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].folhasInstrumentoInternacional").value(hasItem(DEFAULT_FOLHAS_INSTRUMENTO_INTERNACIONAL)))
            .andExpect(jsonPath("$.[*].instrumentoInternacionalSugerido").value(hasItem(DEFAULT_INSTRUMENTO_INTERNACIONAL_SUGERIDO.toString())));
    }
    
    @Test
    @Transactional
    public void getInstrumentoInternacional() throws Exception {
        // Initialize the database
        instrumentoInternacionalRepository.saveAndFlush(instrumentoInternacional);

        // Get the instrumentoInternacional
        restInstrumentoInternacionalMockMvc.perform(get("/api/instrumento-internacionals/{id}", instrumentoInternacional.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(instrumentoInternacional.getId().intValue()))
            .andExpect(jsonPath("$.instrumentoInternacionalCitadoDescricao").value(DEFAULT_INSTRUMENTO_INTERNACIONAL_CITADO_DESCRICAO.toString()))
            .andExpect(jsonPath("$.folhasInstrumentoInternacional").value(DEFAULT_FOLHAS_INSTRUMENTO_INTERNACIONAL))
            .andExpect(jsonPath("$.instrumentoInternacionalSugerido").value(DEFAULT_INSTRUMENTO_INTERNACIONAL_SUGERIDO.toString()));
    }


    @Test
    @Transactional
    public void getInstrumentoInternacionalsByIdFiltering() throws Exception {
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
    public void getAllInstrumentoInternacionalsByFolhasInstrumentoInternacionalIsEqualToSomething() throws Exception {
        // Initialize the database
        instrumentoInternacionalRepository.saveAndFlush(instrumentoInternacional);

        // Get all the instrumentoInternacionalList where folhasInstrumentoInternacional equals to DEFAULT_FOLHAS_INSTRUMENTO_INTERNACIONAL
        defaultInstrumentoInternacionalShouldBeFound("folhasInstrumentoInternacional.equals=" + DEFAULT_FOLHAS_INSTRUMENTO_INTERNACIONAL);

        // Get all the instrumentoInternacionalList where folhasInstrumentoInternacional equals to UPDATED_FOLHAS_INSTRUMENTO_INTERNACIONAL
        defaultInstrumentoInternacionalShouldNotBeFound("folhasInstrumentoInternacional.equals=" + UPDATED_FOLHAS_INSTRUMENTO_INTERNACIONAL);
    }

    @Test
    @Transactional
    public void getAllInstrumentoInternacionalsByFolhasInstrumentoInternacionalIsNotEqualToSomething() throws Exception {
        // Initialize the database
        instrumentoInternacionalRepository.saveAndFlush(instrumentoInternacional);

        // Get all the instrumentoInternacionalList where folhasInstrumentoInternacional not equals to DEFAULT_FOLHAS_INSTRUMENTO_INTERNACIONAL
        defaultInstrumentoInternacionalShouldNotBeFound("folhasInstrumentoInternacional.notEquals=" + DEFAULT_FOLHAS_INSTRUMENTO_INTERNACIONAL);

        // Get all the instrumentoInternacionalList where folhasInstrumentoInternacional not equals to UPDATED_FOLHAS_INSTRUMENTO_INTERNACIONAL
        defaultInstrumentoInternacionalShouldBeFound("folhasInstrumentoInternacional.notEquals=" + UPDATED_FOLHAS_INSTRUMENTO_INTERNACIONAL);
    }

    @Test
    @Transactional
    public void getAllInstrumentoInternacionalsByFolhasInstrumentoInternacionalIsInShouldWork() throws Exception {
        // Initialize the database
        instrumentoInternacionalRepository.saveAndFlush(instrumentoInternacional);

        // Get all the instrumentoInternacionalList where folhasInstrumentoInternacional in DEFAULT_FOLHAS_INSTRUMENTO_INTERNACIONAL or UPDATED_FOLHAS_INSTRUMENTO_INTERNACIONAL
        defaultInstrumentoInternacionalShouldBeFound("folhasInstrumentoInternacional.in=" + DEFAULT_FOLHAS_INSTRUMENTO_INTERNACIONAL + "," + UPDATED_FOLHAS_INSTRUMENTO_INTERNACIONAL);

        // Get all the instrumentoInternacionalList where folhasInstrumentoInternacional equals to UPDATED_FOLHAS_INSTRUMENTO_INTERNACIONAL
        defaultInstrumentoInternacionalShouldNotBeFound("folhasInstrumentoInternacional.in=" + UPDATED_FOLHAS_INSTRUMENTO_INTERNACIONAL);
    }

    @Test
    @Transactional
    public void getAllInstrumentoInternacionalsByFolhasInstrumentoInternacionalIsNullOrNotNull() throws Exception {
        // Initialize the database
        instrumentoInternacionalRepository.saveAndFlush(instrumentoInternacional);

        // Get all the instrumentoInternacionalList where folhasInstrumentoInternacional is not null
        defaultInstrumentoInternacionalShouldBeFound("folhasInstrumentoInternacional.specified=true");

        // Get all the instrumentoInternacionalList where folhasInstrumentoInternacional is null
        defaultInstrumentoInternacionalShouldNotBeFound("folhasInstrumentoInternacional.specified=false");
    }
                @Test
    @Transactional
    public void getAllInstrumentoInternacionalsByFolhasInstrumentoInternacionalContainsSomething() throws Exception {
        // Initialize the database
        instrumentoInternacionalRepository.saveAndFlush(instrumentoInternacional);

        // Get all the instrumentoInternacionalList where folhasInstrumentoInternacional contains DEFAULT_FOLHAS_INSTRUMENTO_INTERNACIONAL
        defaultInstrumentoInternacionalShouldBeFound("folhasInstrumentoInternacional.contains=" + DEFAULT_FOLHAS_INSTRUMENTO_INTERNACIONAL);

        // Get all the instrumentoInternacionalList where folhasInstrumentoInternacional contains UPDATED_FOLHAS_INSTRUMENTO_INTERNACIONAL
        defaultInstrumentoInternacionalShouldNotBeFound("folhasInstrumentoInternacional.contains=" + UPDATED_FOLHAS_INSTRUMENTO_INTERNACIONAL);
    }

    @Test
    @Transactional
    public void getAllInstrumentoInternacionalsByFolhasInstrumentoInternacionalNotContainsSomething() throws Exception {
        // Initialize the database
        instrumentoInternacionalRepository.saveAndFlush(instrumentoInternacional);

        // Get all the instrumentoInternacionalList where folhasInstrumentoInternacional does not contain DEFAULT_FOLHAS_INSTRUMENTO_INTERNACIONAL
        defaultInstrumentoInternacionalShouldNotBeFound("folhasInstrumentoInternacional.doesNotContain=" + DEFAULT_FOLHAS_INSTRUMENTO_INTERNACIONAL);

        // Get all the instrumentoInternacionalList where folhasInstrumentoInternacional does not contain UPDATED_FOLHAS_INSTRUMENTO_INTERNACIONAL
        defaultInstrumentoInternacionalShouldBeFound("folhasInstrumentoInternacional.doesNotContain=" + UPDATED_FOLHAS_INSTRUMENTO_INTERNACIONAL);
    }


    @Test
    @Transactional
    public void getAllInstrumentoInternacionalsByProblemaJuridicoIsEqualToSomething() throws Exception {
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

        // Get all the instrumentoInternacionalList where problemaJuridico equals to problemaJuridicoId + 1
        defaultInstrumentoInternacionalShouldNotBeFound("problemaJuridicoId.equals=" + (problemaJuridicoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultInstrumentoInternacionalShouldBeFound(String filter) throws Exception {
        restInstrumentoInternacionalMockMvc.perform(get("/api/instrumento-internacionals?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(instrumentoInternacional.getId().intValue())))
            .andExpect(jsonPath("$.[*].instrumentoInternacionalCitadoDescricao").value(hasItem(DEFAULT_INSTRUMENTO_INTERNACIONAL_CITADO_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].folhasInstrumentoInternacional").value(hasItem(DEFAULT_FOLHAS_INSTRUMENTO_INTERNACIONAL)))
            .andExpect(jsonPath("$.[*].instrumentoInternacionalSugerido").value(hasItem(DEFAULT_INSTRUMENTO_INTERNACIONAL_SUGERIDO.toString())));

        // Check, that the count call also returns 1
        restInstrumentoInternacionalMockMvc.perform(get("/api/instrumento-internacionals/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultInstrumentoInternacionalShouldNotBeFound(String filter) throws Exception {
        restInstrumentoInternacionalMockMvc.perform(get("/api/instrumento-internacionals?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restInstrumentoInternacionalMockMvc.perform(get("/api/instrumento-internacionals/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingInstrumentoInternacional() throws Exception {
        // Get the instrumentoInternacional
        restInstrumentoInternacionalMockMvc.perform(get("/api/instrumento-internacionals/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInstrumentoInternacional() throws Exception {
        // Initialize the database
        instrumentoInternacionalService.save(instrumentoInternacional);

        int databaseSizeBeforeUpdate = instrumentoInternacionalRepository.findAll().size();

        // Update the instrumentoInternacional
        InstrumentoInternacional updatedInstrumentoInternacional = instrumentoInternacionalRepository.findById(instrumentoInternacional.getId()).get();
        // Disconnect from session so that the updates on updatedInstrumentoInternacional are not directly saved in db
        em.detach(updatedInstrumentoInternacional);
        updatedInstrumentoInternacional
            .instrumentoInternacionalCitadoDescricao(UPDATED_INSTRUMENTO_INTERNACIONAL_CITADO_DESCRICAO)
            .folhasInstrumentoInternacional(UPDATED_FOLHAS_INSTRUMENTO_INTERNACIONAL)
            .instrumentoInternacionalSugerido(UPDATED_INSTRUMENTO_INTERNACIONAL_SUGERIDO);

        restInstrumentoInternacionalMockMvc.perform(put("/api/instrumento-internacionals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedInstrumentoInternacional)))
            .andExpect(status().isOk());

        // Validate the InstrumentoInternacional in the database
        List<InstrumentoInternacional> instrumentoInternacionalList = instrumentoInternacionalRepository.findAll();
        assertThat(instrumentoInternacionalList).hasSize(databaseSizeBeforeUpdate);
        InstrumentoInternacional testInstrumentoInternacional = instrumentoInternacionalList.get(instrumentoInternacionalList.size() - 1);
        assertThat(testInstrumentoInternacional.getInstrumentoInternacionalCitadoDescricao()).isEqualTo(UPDATED_INSTRUMENTO_INTERNACIONAL_CITADO_DESCRICAO);
        assertThat(testInstrumentoInternacional.getFolhasInstrumentoInternacional()).isEqualTo(UPDATED_FOLHAS_INSTRUMENTO_INTERNACIONAL);
        assertThat(testInstrumentoInternacional.getInstrumentoInternacionalSugerido()).isEqualTo(UPDATED_INSTRUMENTO_INTERNACIONAL_SUGERIDO);
    }

    @Test
    @Transactional
    public void updateNonExistingInstrumentoInternacional() throws Exception {
        int databaseSizeBeforeUpdate = instrumentoInternacionalRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInstrumentoInternacionalMockMvc.perform(put("/api/instrumento-internacionals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(instrumentoInternacional)))
            .andExpect(status().isBadRequest());

        // Validate the InstrumentoInternacional in the database
        List<InstrumentoInternacional> instrumentoInternacionalList = instrumentoInternacionalRepository.findAll();
        assertThat(instrumentoInternacionalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteInstrumentoInternacional() throws Exception {
        // Initialize the database
        instrumentoInternacionalService.save(instrumentoInternacional);

        int databaseSizeBeforeDelete = instrumentoInternacionalRepository.findAll().size();

        // Delete the instrumentoInternacional
        restInstrumentoInternacionalMockMvc.perform(delete("/api/instrumento-internacionals/{id}", instrumentoInternacional.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<InstrumentoInternacional> instrumentoInternacionalList = instrumentoInternacionalRepository.findAll();
        assertThat(instrumentoInternacionalList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
