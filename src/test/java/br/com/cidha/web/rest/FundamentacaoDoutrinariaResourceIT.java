package br.com.cidha.web.rest;

import br.com.cidha.CidhaApp;
import br.com.cidha.domain.FundamentacaoDoutrinaria;
import br.com.cidha.domain.ProblemaJuridico;
import br.com.cidha.repository.FundamentacaoDoutrinariaRepository;
import br.com.cidha.service.FundamentacaoDoutrinariaService;
import br.com.cidha.service.dto.FundamentacaoDoutrinariaCriteria;
import br.com.cidha.service.FundamentacaoDoutrinariaQueryService;

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
 * Integration tests for the {@link FundamentacaoDoutrinariaResource} REST controller.
 */
@SpringBootTest(classes = CidhaApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class FundamentacaoDoutrinariaResourceIT {

    private static final String DEFAULT_FUNDAMENTACAO_DOUTRINARIA_CITADA = "AAAAAAAAAA";
    private static final String UPDATED_FUNDAMENTACAO_DOUTRINARIA_CITADA = "BBBBBBBBBB";

    private static final String DEFAULT_FOLHAS_FUNDAMENTACAO_DOUTRINARIA = "AAAAAAAAAA";
    private static final String UPDATED_FOLHAS_FUNDAMENTACAO_DOUTRINARIA = "BBBBBBBBBB";

    private static final String DEFAULT_FUNDAMENTACAO_DOUTRINARIA_SUGERIDA = "AAAAAAAAAA";
    private static final String UPDATED_FUNDAMENTACAO_DOUTRINARIA_SUGERIDA = "BBBBBBBBBB";

    @Autowired
    private FundamentacaoDoutrinariaRepository fundamentacaoDoutrinariaRepository;

    @Autowired
    private FundamentacaoDoutrinariaService fundamentacaoDoutrinariaService;

    @Autowired
    private FundamentacaoDoutrinariaQueryService fundamentacaoDoutrinariaQueryService;

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
    public void createFundamentacaoDoutrinaria() throws Exception {
        int databaseSizeBeforeCreate = fundamentacaoDoutrinariaRepository.findAll().size();
        // Create the FundamentacaoDoutrinaria
        restFundamentacaoDoutrinariaMockMvc.perform(post("/api/fundamentacao-doutrinarias")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(fundamentacaoDoutrinaria)))
            .andExpect(status().isCreated());

        // Validate the FundamentacaoDoutrinaria in the database
        List<FundamentacaoDoutrinaria> fundamentacaoDoutrinariaList = fundamentacaoDoutrinariaRepository.findAll();
        assertThat(fundamentacaoDoutrinariaList).hasSize(databaseSizeBeforeCreate + 1);
        FundamentacaoDoutrinaria testFundamentacaoDoutrinaria = fundamentacaoDoutrinariaList.get(fundamentacaoDoutrinariaList.size() - 1);
        assertThat(testFundamentacaoDoutrinaria.getFundamentacaoDoutrinariaCitada()).isEqualTo(DEFAULT_FUNDAMENTACAO_DOUTRINARIA_CITADA);
        assertThat(testFundamentacaoDoutrinaria.getFolhasFundamentacaoDoutrinaria()).isEqualTo(DEFAULT_FOLHAS_FUNDAMENTACAO_DOUTRINARIA);
        assertThat(testFundamentacaoDoutrinaria.getFundamentacaoDoutrinariaSugerida()).isEqualTo(DEFAULT_FUNDAMENTACAO_DOUTRINARIA_SUGERIDA);
    }

    @Test
    @Transactional
    public void createFundamentacaoDoutrinariaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = fundamentacaoDoutrinariaRepository.findAll().size();

        // Create the FundamentacaoDoutrinaria with an existing ID
        fundamentacaoDoutrinaria.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFundamentacaoDoutrinariaMockMvc.perform(post("/api/fundamentacao-doutrinarias")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(fundamentacaoDoutrinaria)))
            .andExpect(status().isBadRequest());

        // Validate the FundamentacaoDoutrinaria in the database
        List<FundamentacaoDoutrinaria> fundamentacaoDoutrinariaList = fundamentacaoDoutrinariaRepository.findAll();
        assertThat(fundamentacaoDoutrinariaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllFundamentacaoDoutrinarias() throws Exception {
        // Initialize the database
        fundamentacaoDoutrinariaRepository.saveAndFlush(fundamentacaoDoutrinaria);

        // Get all the fundamentacaoDoutrinariaList
        restFundamentacaoDoutrinariaMockMvc.perform(get("/api/fundamentacao-doutrinarias?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fundamentacaoDoutrinaria.getId().intValue())))
            .andExpect(jsonPath("$.[*].fundamentacaoDoutrinariaCitada").value(hasItem(DEFAULT_FUNDAMENTACAO_DOUTRINARIA_CITADA.toString())))
            .andExpect(jsonPath("$.[*].folhasFundamentacaoDoutrinaria").value(hasItem(DEFAULT_FOLHAS_FUNDAMENTACAO_DOUTRINARIA)))
            .andExpect(jsonPath("$.[*].fundamentacaoDoutrinariaSugerida").value(hasItem(DEFAULT_FUNDAMENTACAO_DOUTRINARIA_SUGERIDA.toString())));
    }
    
    @Test
    @Transactional
    public void getFundamentacaoDoutrinaria() throws Exception {
        // Initialize the database
        fundamentacaoDoutrinariaRepository.saveAndFlush(fundamentacaoDoutrinaria);

        // Get the fundamentacaoDoutrinaria
        restFundamentacaoDoutrinariaMockMvc.perform(get("/api/fundamentacao-doutrinarias/{id}", fundamentacaoDoutrinaria.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(fundamentacaoDoutrinaria.getId().intValue()))
            .andExpect(jsonPath("$.fundamentacaoDoutrinariaCitada").value(DEFAULT_FUNDAMENTACAO_DOUTRINARIA_CITADA.toString()))
            .andExpect(jsonPath("$.folhasFundamentacaoDoutrinaria").value(DEFAULT_FOLHAS_FUNDAMENTACAO_DOUTRINARIA))
            .andExpect(jsonPath("$.fundamentacaoDoutrinariaSugerida").value(DEFAULT_FUNDAMENTACAO_DOUTRINARIA_SUGERIDA.toString()));
    }


    @Test
    @Transactional
    public void getFundamentacaoDoutrinariasByIdFiltering() throws Exception {
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
    public void getAllFundamentacaoDoutrinariasByFolhasFundamentacaoDoutrinariaIsEqualToSomething() throws Exception {
        // Initialize the database
        fundamentacaoDoutrinariaRepository.saveAndFlush(fundamentacaoDoutrinaria);

        // Get all the fundamentacaoDoutrinariaList where folhasFundamentacaoDoutrinaria equals to DEFAULT_FOLHAS_FUNDAMENTACAO_DOUTRINARIA
        defaultFundamentacaoDoutrinariaShouldBeFound("folhasFundamentacaoDoutrinaria.equals=" + DEFAULT_FOLHAS_FUNDAMENTACAO_DOUTRINARIA);

        // Get all the fundamentacaoDoutrinariaList where folhasFundamentacaoDoutrinaria equals to UPDATED_FOLHAS_FUNDAMENTACAO_DOUTRINARIA
        defaultFundamentacaoDoutrinariaShouldNotBeFound("folhasFundamentacaoDoutrinaria.equals=" + UPDATED_FOLHAS_FUNDAMENTACAO_DOUTRINARIA);
    }

    @Test
    @Transactional
    public void getAllFundamentacaoDoutrinariasByFolhasFundamentacaoDoutrinariaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fundamentacaoDoutrinariaRepository.saveAndFlush(fundamentacaoDoutrinaria);

        // Get all the fundamentacaoDoutrinariaList where folhasFundamentacaoDoutrinaria not equals to DEFAULT_FOLHAS_FUNDAMENTACAO_DOUTRINARIA
        defaultFundamentacaoDoutrinariaShouldNotBeFound("folhasFundamentacaoDoutrinaria.notEquals=" + DEFAULT_FOLHAS_FUNDAMENTACAO_DOUTRINARIA);

        // Get all the fundamentacaoDoutrinariaList where folhasFundamentacaoDoutrinaria not equals to UPDATED_FOLHAS_FUNDAMENTACAO_DOUTRINARIA
        defaultFundamentacaoDoutrinariaShouldBeFound("folhasFundamentacaoDoutrinaria.notEquals=" + UPDATED_FOLHAS_FUNDAMENTACAO_DOUTRINARIA);
    }

    @Test
    @Transactional
    public void getAllFundamentacaoDoutrinariasByFolhasFundamentacaoDoutrinariaIsInShouldWork() throws Exception {
        // Initialize the database
        fundamentacaoDoutrinariaRepository.saveAndFlush(fundamentacaoDoutrinaria);

        // Get all the fundamentacaoDoutrinariaList where folhasFundamentacaoDoutrinaria in DEFAULT_FOLHAS_FUNDAMENTACAO_DOUTRINARIA or UPDATED_FOLHAS_FUNDAMENTACAO_DOUTRINARIA
        defaultFundamentacaoDoutrinariaShouldBeFound("folhasFundamentacaoDoutrinaria.in=" + DEFAULT_FOLHAS_FUNDAMENTACAO_DOUTRINARIA + "," + UPDATED_FOLHAS_FUNDAMENTACAO_DOUTRINARIA);

        // Get all the fundamentacaoDoutrinariaList where folhasFundamentacaoDoutrinaria equals to UPDATED_FOLHAS_FUNDAMENTACAO_DOUTRINARIA
        defaultFundamentacaoDoutrinariaShouldNotBeFound("folhasFundamentacaoDoutrinaria.in=" + UPDATED_FOLHAS_FUNDAMENTACAO_DOUTRINARIA);
    }

    @Test
    @Transactional
    public void getAllFundamentacaoDoutrinariasByFolhasFundamentacaoDoutrinariaIsNullOrNotNull() throws Exception {
        // Initialize the database
        fundamentacaoDoutrinariaRepository.saveAndFlush(fundamentacaoDoutrinaria);

        // Get all the fundamentacaoDoutrinariaList where folhasFundamentacaoDoutrinaria is not null
        defaultFundamentacaoDoutrinariaShouldBeFound("folhasFundamentacaoDoutrinaria.specified=true");

        // Get all the fundamentacaoDoutrinariaList where folhasFundamentacaoDoutrinaria is null
        defaultFundamentacaoDoutrinariaShouldNotBeFound("folhasFundamentacaoDoutrinaria.specified=false");
    }
                @Test
    @Transactional
    public void getAllFundamentacaoDoutrinariasByFolhasFundamentacaoDoutrinariaContainsSomething() throws Exception {
        // Initialize the database
        fundamentacaoDoutrinariaRepository.saveAndFlush(fundamentacaoDoutrinaria);

        // Get all the fundamentacaoDoutrinariaList where folhasFundamentacaoDoutrinaria contains DEFAULT_FOLHAS_FUNDAMENTACAO_DOUTRINARIA
        defaultFundamentacaoDoutrinariaShouldBeFound("folhasFundamentacaoDoutrinaria.contains=" + DEFAULT_FOLHAS_FUNDAMENTACAO_DOUTRINARIA);

        // Get all the fundamentacaoDoutrinariaList where folhasFundamentacaoDoutrinaria contains UPDATED_FOLHAS_FUNDAMENTACAO_DOUTRINARIA
        defaultFundamentacaoDoutrinariaShouldNotBeFound("folhasFundamentacaoDoutrinaria.contains=" + UPDATED_FOLHAS_FUNDAMENTACAO_DOUTRINARIA);
    }

    @Test
    @Transactional
    public void getAllFundamentacaoDoutrinariasByFolhasFundamentacaoDoutrinariaNotContainsSomething() throws Exception {
        // Initialize the database
        fundamentacaoDoutrinariaRepository.saveAndFlush(fundamentacaoDoutrinaria);

        // Get all the fundamentacaoDoutrinariaList where folhasFundamentacaoDoutrinaria does not contain DEFAULT_FOLHAS_FUNDAMENTACAO_DOUTRINARIA
        defaultFundamentacaoDoutrinariaShouldNotBeFound("folhasFundamentacaoDoutrinaria.doesNotContain=" + DEFAULT_FOLHAS_FUNDAMENTACAO_DOUTRINARIA);

        // Get all the fundamentacaoDoutrinariaList where folhasFundamentacaoDoutrinaria does not contain UPDATED_FOLHAS_FUNDAMENTACAO_DOUTRINARIA
        defaultFundamentacaoDoutrinariaShouldBeFound("folhasFundamentacaoDoutrinaria.doesNotContain=" + UPDATED_FOLHAS_FUNDAMENTACAO_DOUTRINARIA);
    }


    @Test
    @Transactional
    public void getAllFundamentacaoDoutrinariasByProblemaJuridicoIsEqualToSomething() throws Exception {
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

        // Get all the fundamentacaoDoutrinariaList where problemaJuridico equals to problemaJuridicoId + 1
        defaultFundamentacaoDoutrinariaShouldNotBeFound("problemaJuridicoId.equals=" + (problemaJuridicoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultFundamentacaoDoutrinariaShouldBeFound(String filter) throws Exception {
        restFundamentacaoDoutrinariaMockMvc.perform(get("/api/fundamentacao-doutrinarias?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fundamentacaoDoutrinaria.getId().intValue())))
            .andExpect(jsonPath("$.[*].fundamentacaoDoutrinariaCitada").value(hasItem(DEFAULT_FUNDAMENTACAO_DOUTRINARIA_CITADA.toString())))
            .andExpect(jsonPath("$.[*].folhasFundamentacaoDoutrinaria").value(hasItem(DEFAULT_FOLHAS_FUNDAMENTACAO_DOUTRINARIA)))
            .andExpect(jsonPath("$.[*].fundamentacaoDoutrinariaSugerida").value(hasItem(DEFAULT_FUNDAMENTACAO_DOUTRINARIA_SUGERIDA.toString())));

        // Check, that the count call also returns 1
        restFundamentacaoDoutrinariaMockMvc.perform(get("/api/fundamentacao-doutrinarias/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultFundamentacaoDoutrinariaShouldNotBeFound(String filter) throws Exception {
        restFundamentacaoDoutrinariaMockMvc.perform(get("/api/fundamentacao-doutrinarias?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restFundamentacaoDoutrinariaMockMvc.perform(get("/api/fundamentacao-doutrinarias/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingFundamentacaoDoutrinaria() throws Exception {
        // Get the fundamentacaoDoutrinaria
        restFundamentacaoDoutrinariaMockMvc.perform(get("/api/fundamentacao-doutrinarias/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFundamentacaoDoutrinaria() throws Exception {
        // Initialize the database
        fundamentacaoDoutrinariaService.save(fundamentacaoDoutrinaria);

        int databaseSizeBeforeUpdate = fundamentacaoDoutrinariaRepository.findAll().size();

        // Update the fundamentacaoDoutrinaria
        FundamentacaoDoutrinaria updatedFundamentacaoDoutrinaria = fundamentacaoDoutrinariaRepository.findById(fundamentacaoDoutrinaria.getId()).get();
        // Disconnect from session so that the updates on updatedFundamentacaoDoutrinaria are not directly saved in db
        em.detach(updatedFundamentacaoDoutrinaria);
        updatedFundamentacaoDoutrinaria
            .fundamentacaoDoutrinariaCitada(UPDATED_FUNDAMENTACAO_DOUTRINARIA_CITADA)
            .folhasFundamentacaoDoutrinaria(UPDATED_FOLHAS_FUNDAMENTACAO_DOUTRINARIA)
            .fundamentacaoDoutrinariaSugerida(UPDATED_FUNDAMENTACAO_DOUTRINARIA_SUGERIDA);

        restFundamentacaoDoutrinariaMockMvc.perform(put("/api/fundamentacao-doutrinarias")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedFundamentacaoDoutrinaria)))
            .andExpect(status().isOk());

        // Validate the FundamentacaoDoutrinaria in the database
        List<FundamentacaoDoutrinaria> fundamentacaoDoutrinariaList = fundamentacaoDoutrinariaRepository.findAll();
        assertThat(fundamentacaoDoutrinariaList).hasSize(databaseSizeBeforeUpdate);
        FundamentacaoDoutrinaria testFundamentacaoDoutrinaria = fundamentacaoDoutrinariaList.get(fundamentacaoDoutrinariaList.size() - 1);
        assertThat(testFundamentacaoDoutrinaria.getFundamentacaoDoutrinariaCitada()).isEqualTo(UPDATED_FUNDAMENTACAO_DOUTRINARIA_CITADA);
        assertThat(testFundamentacaoDoutrinaria.getFolhasFundamentacaoDoutrinaria()).isEqualTo(UPDATED_FOLHAS_FUNDAMENTACAO_DOUTRINARIA);
        assertThat(testFundamentacaoDoutrinaria.getFundamentacaoDoutrinariaSugerida()).isEqualTo(UPDATED_FUNDAMENTACAO_DOUTRINARIA_SUGERIDA);
    }

    @Test
    @Transactional
    public void updateNonExistingFundamentacaoDoutrinaria() throws Exception {
        int databaseSizeBeforeUpdate = fundamentacaoDoutrinariaRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFundamentacaoDoutrinariaMockMvc.perform(put("/api/fundamentacao-doutrinarias")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(fundamentacaoDoutrinaria)))
            .andExpect(status().isBadRequest());

        // Validate the FundamentacaoDoutrinaria in the database
        List<FundamentacaoDoutrinaria> fundamentacaoDoutrinariaList = fundamentacaoDoutrinariaRepository.findAll();
        assertThat(fundamentacaoDoutrinariaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFundamentacaoDoutrinaria() throws Exception {
        // Initialize the database
        fundamentacaoDoutrinariaService.save(fundamentacaoDoutrinaria);

        int databaseSizeBeforeDelete = fundamentacaoDoutrinariaRepository.findAll().size();

        // Delete the fundamentacaoDoutrinaria
        restFundamentacaoDoutrinariaMockMvc.perform(delete("/api/fundamentacao-doutrinarias/{id}", fundamentacaoDoutrinaria.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FundamentacaoDoutrinaria> fundamentacaoDoutrinariaList = fundamentacaoDoutrinariaRepository.findAll();
        assertThat(fundamentacaoDoutrinariaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
