package br.com.cidha.web.rest;

import br.com.cidha.CidhaApp;
import br.com.cidha.domain.ProblemaJuridico;
import br.com.cidha.domain.FundamentacaoDoutrinaria;
import br.com.cidha.domain.Jurisprudencia;
import br.com.cidha.domain.FundamentacaoLegal;
import br.com.cidha.domain.InstrumentoInternacional;
import br.com.cidha.domain.Processo;
import br.com.cidha.repository.ProblemaJuridicoRepository;
import br.com.cidha.service.ProblemaJuridicoService;
import br.com.cidha.service.dto.ProblemaJuridicoCriteria;
import br.com.cidha.service.ProblemaJuridicoQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ProblemaJuridicoResource} REST controller.
 */
@SpringBootTest(classes = CidhaApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class ProblemaJuridicoResourceIT {

    private static final String DEFAULT_PROLEMA_JURIDICO_RESPONDIDO = "AAAAAAAAAA";
    private static final String UPDATED_PROLEMA_JURIDICO_RESPONDIDO = "BBBBBBBBBB";

    private static final String DEFAULT_FOLHAS_PROBLEMA_JURIDICO = "AAAAAAAAAA";
    private static final String UPDATED_FOLHAS_PROBLEMA_JURIDICO = "BBBBBBBBBB";

    @Autowired
    private ProblemaJuridicoRepository problemaJuridicoRepository;

    @Mock
    private ProblemaJuridicoRepository problemaJuridicoRepositoryMock;

    @Mock
    private ProblemaJuridicoService problemaJuridicoServiceMock;

    @Autowired
    private ProblemaJuridicoService problemaJuridicoService;

    @Autowired
    private ProblemaJuridicoQueryService problemaJuridicoQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProblemaJuridicoMockMvc;

    private ProblemaJuridico problemaJuridico;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProblemaJuridico createEntity(EntityManager em) {
        ProblemaJuridico problemaJuridico = new ProblemaJuridico()
            .prolemaJuridicoRespondido(DEFAULT_PROLEMA_JURIDICO_RESPONDIDO)
            .folhasProblemaJuridico(DEFAULT_FOLHAS_PROBLEMA_JURIDICO);
        return problemaJuridico;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProblemaJuridico createUpdatedEntity(EntityManager em) {
        ProblemaJuridico problemaJuridico = new ProblemaJuridico()
            .prolemaJuridicoRespondido(UPDATED_PROLEMA_JURIDICO_RESPONDIDO)
            .folhasProblemaJuridico(UPDATED_FOLHAS_PROBLEMA_JURIDICO);
        return problemaJuridico;
    }

    @BeforeEach
    public void initTest() {
        problemaJuridico = createEntity(em);
    }

    @Test
    @Transactional
    public void createProblemaJuridico() throws Exception {
        int databaseSizeBeforeCreate = problemaJuridicoRepository.findAll().size();
        // Create the ProblemaJuridico
        restProblemaJuridicoMockMvc.perform(post("/api/problema-juridicos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(problemaJuridico)))
            .andExpect(status().isCreated());

        // Validate the ProblemaJuridico in the database
        List<ProblemaJuridico> problemaJuridicoList = problemaJuridicoRepository.findAll();
        assertThat(problemaJuridicoList).hasSize(databaseSizeBeforeCreate + 1);
        ProblemaJuridico testProblemaJuridico = problemaJuridicoList.get(problemaJuridicoList.size() - 1);
        assertThat(testProblemaJuridico.getProlemaJuridicoRespondido()).isEqualTo(DEFAULT_PROLEMA_JURIDICO_RESPONDIDO);
        assertThat(testProblemaJuridico.getFolhasProblemaJuridico()).isEqualTo(DEFAULT_FOLHAS_PROBLEMA_JURIDICO);
    }

    @Test
    @Transactional
    public void createProblemaJuridicoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = problemaJuridicoRepository.findAll().size();

        // Create the ProblemaJuridico with an existing ID
        problemaJuridico.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProblemaJuridicoMockMvc.perform(post("/api/problema-juridicos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(problemaJuridico)))
            .andExpect(status().isBadRequest());

        // Validate the ProblemaJuridico in the database
        List<ProblemaJuridico> problemaJuridicoList = problemaJuridicoRepository.findAll();
        assertThat(problemaJuridicoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllProblemaJuridicos() throws Exception {
        // Initialize the database
        problemaJuridicoRepository.saveAndFlush(problemaJuridico);

        // Get all the problemaJuridicoList
        restProblemaJuridicoMockMvc.perform(get("/api/problema-juridicos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(problemaJuridico.getId().intValue())))
            .andExpect(jsonPath("$.[*].prolemaJuridicoRespondido").value(hasItem(DEFAULT_PROLEMA_JURIDICO_RESPONDIDO.toString())))
            .andExpect(jsonPath("$.[*].folhasProblemaJuridico").value(hasItem(DEFAULT_FOLHAS_PROBLEMA_JURIDICO)));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllProblemaJuridicosWithEagerRelationshipsIsEnabled() throws Exception {
        when(problemaJuridicoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restProblemaJuridicoMockMvc.perform(get("/api/problema-juridicos?eagerload=true"))
            .andExpect(status().isOk());

        verify(problemaJuridicoServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllProblemaJuridicosWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(problemaJuridicoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restProblemaJuridicoMockMvc.perform(get("/api/problema-juridicos?eagerload=true"))
            .andExpect(status().isOk());

        verify(problemaJuridicoServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getProblemaJuridico() throws Exception {
        // Initialize the database
        problemaJuridicoRepository.saveAndFlush(problemaJuridico);

        // Get the problemaJuridico
        restProblemaJuridicoMockMvc.perform(get("/api/problema-juridicos/{id}", problemaJuridico.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(problemaJuridico.getId().intValue()))
            .andExpect(jsonPath("$.prolemaJuridicoRespondido").value(DEFAULT_PROLEMA_JURIDICO_RESPONDIDO.toString()))
            .andExpect(jsonPath("$.folhasProblemaJuridico").value(DEFAULT_FOLHAS_PROBLEMA_JURIDICO));
    }


    @Test
    @Transactional
    public void getProblemaJuridicosByIdFiltering() throws Exception {
        // Initialize the database
        problemaJuridicoRepository.saveAndFlush(problemaJuridico);

        Long id = problemaJuridico.getId();

        defaultProblemaJuridicoShouldBeFound("id.equals=" + id);
        defaultProblemaJuridicoShouldNotBeFound("id.notEquals=" + id);

        defaultProblemaJuridicoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultProblemaJuridicoShouldNotBeFound("id.greaterThan=" + id);

        defaultProblemaJuridicoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultProblemaJuridicoShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllProblemaJuridicosByFolhasProblemaJuridicoIsEqualToSomething() throws Exception {
        // Initialize the database
        problemaJuridicoRepository.saveAndFlush(problemaJuridico);

        // Get all the problemaJuridicoList where folhasProblemaJuridico equals to DEFAULT_FOLHAS_PROBLEMA_JURIDICO
        defaultProblemaJuridicoShouldBeFound("folhasProblemaJuridico.equals=" + DEFAULT_FOLHAS_PROBLEMA_JURIDICO);

        // Get all the problemaJuridicoList where folhasProblemaJuridico equals to UPDATED_FOLHAS_PROBLEMA_JURIDICO
        defaultProblemaJuridicoShouldNotBeFound("folhasProblemaJuridico.equals=" + UPDATED_FOLHAS_PROBLEMA_JURIDICO);
    }

    @Test
    @Transactional
    public void getAllProblemaJuridicosByFolhasProblemaJuridicoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        problemaJuridicoRepository.saveAndFlush(problemaJuridico);

        // Get all the problemaJuridicoList where folhasProblemaJuridico not equals to DEFAULT_FOLHAS_PROBLEMA_JURIDICO
        defaultProblemaJuridicoShouldNotBeFound("folhasProblemaJuridico.notEquals=" + DEFAULT_FOLHAS_PROBLEMA_JURIDICO);

        // Get all the problemaJuridicoList where folhasProblemaJuridico not equals to UPDATED_FOLHAS_PROBLEMA_JURIDICO
        defaultProblemaJuridicoShouldBeFound("folhasProblemaJuridico.notEquals=" + UPDATED_FOLHAS_PROBLEMA_JURIDICO);
    }

    @Test
    @Transactional
    public void getAllProblemaJuridicosByFolhasProblemaJuridicoIsInShouldWork() throws Exception {
        // Initialize the database
        problemaJuridicoRepository.saveAndFlush(problemaJuridico);

        // Get all the problemaJuridicoList where folhasProblemaJuridico in DEFAULT_FOLHAS_PROBLEMA_JURIDICO or UPDATED_FOLHAS_PROBLEMA_JURIDICO
        defaultProblemaJuridicoShouldBeFound("folhasProblemaJuridico.in=" + DEFAULT_FOLHAS_PROBLEMA_JURIDICO + "," + UPDATED_FOLHAS_PROBLEMA_JURIDICO);

        // Get all the problemaJuridicoList where folhasProblemaJuridico equals to UPDATED_FOLHAS_PROBLEMA_JURIDICO
        defaultProblemaJuridicoShouldNotBeFound("folhasProblemaJuridico.in=" + UPDATED_FOLHAS_PROBLEMA_JURIDICO);
    }

    @Test
    @Transactional
    public void getAllProblemaJuridicosByFolhasProblemaJuridicoIsNullOrNotNull() throws Exception {
        // Initialize the database
        problemaJuridicoRepository.saveAndFlush(problemaJuridico);

        // Get all the problemaJuridicoList where folhasProblemaJuridico is not null
        defaultProblemaJuridicoShouldBeFound("folhasProblemaJuridico.specified=true");

        // Get all the problemaJuridicoList where folhasProblemaJuridico is null
        defaultProblemaJuridicoShouldNotBeFound("folhasProblemaJuridico.specified=false");
    }
                @Test
    @Transactional
    public void getAllProblemaJuridicosByFolhasProblemaJuridicoContainsSomething() throws Exception {
        // Initialize the database
        problemaJuridicoRepository.saveAndFlush(problemaJuridico);

        // Get all the problemaJuridicoList where folhasProblemaJuridico contains DEFAULT_FOLHAS_PROBLEMA_JURIDICO
        defaultProblemaJuridicoShouldBeFound("folhasProblemaJuridico.contains=" + DEFAULT_FOLHAS_PROBLEMA_JURIDICO);

        // Get all the problemaJuridicoList where folhasProblemaJuridico contains UPDATED_FOLHAS_PROBLEMA_JURIDICO
        defaultProblemaJuridicoShouldNotBeFound("folhasProblemaJuridico.contains=" + UPDATED_FOLHAS_PROBLEMA_JURIDICO);
    }

    @Test
    @Transactional
    public void getAllProblemaJuridicosByFolhasProblemaJuridicoNotContainsSomething() throws Exception {
        // Initialize the database
        problemaJuridicoRepository.saveAndFlush(problemaJuridico);

        // Get all the problemaJuridicoList where folhasProblemaJuridico does not contain DEFAULT_FOLHAS_PROBLEMA_JURIDICO
        defaultProblemaJuridicoShouldNotBeFound("folhasProblemaJuridico.doesNotContain=" + DEFAULT_FOLHAS_PROBLEMA_JURIDICO);

        // Get all the problemaJuridicoList where folhasProblemaJuridico does not contain UPDATED_FOLHAS_PROBLEMA_JURIDICO
        defaultProblemaJuridicoShouldBeFound("folhasProblemaJuridico.doesNotContain=" + UPDATED_FOLHAS_PROBLEMA_JURIDICO);
    }


    @Test
    @Transactional
    public void getAllProblemaJuridicosByFundamentacaoDoutrinariaIsEqualToSomething() throws Exception {
        // Initialize the database
        problemaJuridicoRepository.saveAndFlush(problemaJuridico);
        FundamentacaoDoutrinaria fundamentacaoDoutrinaria = FundamentacaoDoutrinariaResourceIT.createEntity(em);
        em.persist(fundamentacaoDoutrinaria);
        em.flush();
        problemaJuridico.addFundamentacaoDoutrinaria(fundamentacaoDoutrinaria);
        problemaJuridicoRepository.saveAndFlush(problemaJuridico);
        Long fundamentacaoDoutrinariaId = fundamentacaoDoutrinaria.getId();

        // Get all the problemaJuridicoList where fundamentacaoDoutrinaria equals to fundamentacaoDoutrinariaId
        defaultProblemaJuridicoShouldBeFound("fundamentacaoDoutrinariaId.equals=" + fundamentacaoDoutrinariaId);

        // Get all the problemaJuridicoList where fundamentacaoDoutrinaria equals to fundamentacaoDoutrinariaId + 1
        defaultProblemaJuridicoShouldNotBeFound("fundamentacaoDoutrinariaId.equals=" + (fundamentacaoDoutrinariaId + 1));
    }


    @Test
    @Transactional
    public void getAllProblemaJuridicosByJurisprudenciaIsEqualToSomething() throws Exception {
        // Initialize the database
        problemaJuridicoRepository.saveAndFlush(problemaJuridico);
        Jurisprudencia jurisprudencia = JurisprudenciaResourceIT.createEntity(em);
        em.persist(jurisprudencia);
        em.flush();
        problemaJuridico.addJurisprudencia(jurisprudencia);
        problemaJuridicoRepository.saveAndFlush(problemaJuridico);
        Long jurisprudenciaId = jurisprudencia.getId();

        // Get all the problemaJuridicoList where jurisprudencia equals to jurisprudenciaId
        defaultProblemaJuridicoShouldBeFound("jurisprudenciaId.equals=" + jurisprudenciaId);

        // Get all the problemaJuridicoList where jurisprudencia equals to jurisprudenciaId + 1
        defaultProblemaJuridicoShouldNotBeFound("jurisprudenciaId.equals=" + (jurisprudenciaId + 1));
    }


    @Test
    @Transactional
    public void getAllProblemaJuridicosByFundamentacaoLegalIsEqualToSomething() throws Exception {
        // Initialize the database
        problemaJuridicoRepository.saveAndFlush(problemaJuridico);
        FundamentacaoLegal fundamentacaoLegal = FundamentacaoLegalResourceIT.createEntity(em);
        em.persist(fundamentacaoLegal);
        em.flush();
        problemaJuridico.addFundamentacaoLegal(fundamentacaoLegal);
        problemaJuridicoRepository.saveAndFlush(problemaJuridico);
        Long fundamentacaoLegalId = fundamentacaoLegal.getId();

        // Get all the problemaJuridicoList where fundamentacaoLegal equals to fundamentacaoLegalId
        defaultProblemaJuridicoShouldBeFound("fundamentacaoLegalId.equals=" + fundamentacaoLegalId);

        // Get all the problemaJuridicoList where fundamentacaoLegal equals to fundamentacaoLegalId + 1
        defaultProblemaJuridicoShouldNotBeFound("fundamentacaoLegalId.equals=" + (fundamentacaoLegalId + 1));
    }


    @Test
    @Transactional
    public void getAllProblemaJuridicosByInstrumentoInternacionalIsEqualToSomething() throws Exception {
        // Initialize the database
        problemaJuridicoRepository.saveAndFlush(problemaJuridico);
        InstrumentoInternacional instrumentoInternacional = InstrumentoInternacionalResourceIT.createEntity(em);
        em.persist(instrumentoInternacional);
        em.flush();
        problemaJuridico.addInstrumentoInternacional(instrumentoInternacional);
        problemaJuridicoRepository.saveAndFlush(problemaJuridico);
        Long instrumentoInternacionalId = instrumentoInternacional.getId();

        // Get all the problemaJuridicoList where instrumentoInternacional equals to instrumentoInternacionalId
        defaultProblemaJuridicoShouldBeFound("instrumentoInternacionalId.equals=" + instrumentoInternacionalId);

        // Get all the problemaJuridicoList where instrumentoInternacional equals to instrumentoInternacionalId + 1
        defaultProblemaJuridicoShouldNotBeFound("instrumentoInternacionalId.equals=" + (instrumentoInternacionalId + 1));
    }


    @Test
    @Transactional
    public void getAllProblemaJuridicosByProcessoIsEqualToSomething() throws Exception {
        // Initialize the database
        problemaJuridicoRepository.saveAndFlush(problemaJuridico);
        Processo processo = ProcessoResourceIT.createEntity(em);
        em.persist(processo);
        em.flush();
        problemaJuridico.addProcesso(processo);
        problemaJuridicoRepository.saveAndFlush(problemaJuridico);
        Long processoId = processo.getId();

        // Get all the problemaJuridicoList where processo equals to processoId
        defaultProblemaJuridicoShouldBeFound("processoId.equals=" + processoId);

        // Get all the problemaJuridicoList where processo equals to processoId + 1
        defaultProblemaJuridicoShouldNotBeFound("processoId.equals=" + (processoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProblemaJuridicoShouldBeFound(String filter) throws Exception {
        restProblemaJuridicoMockMvc.perform(get("/api/problema-juridicos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(problemaJuridico.getId().intValue())))
            .andExpect(jsonPath("$.[*].prolemaJuridicoRespondido").value(hasItem(DEFAULT_PROLEMA_JURIDICO_RESPONDIDO.toString())))
            .andExpect(jsonPath("$.[*].folhasProblemaJuridico").value(hasItem(DEFAULT_FOLHAS_PROBLEMA_JURIDICO)));

        // Check, that the count call also returns 1
        restProblemaJuridicoMockMvc.perform(get("/api/problema-juridicos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProblemaJuridicoShouldNotBeFound(String filter) throws Exception {
        restProblemaJuridicoMockMvc.perform(get("/api/problema-juridicos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProblemaJuridicoMockMvc.perform(get("/api/problema-juridicos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingProblemaJuridico() throws Exception {
        // Get the problemaJuridico
        restProblemaJuridicoMockMvc.perform(get("/api/problema-juridicos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProblemaJuridico() throws Exception {
        // Initialize the database
        problemaJuridicoService.save(problemaJuridico);

        int databaseSizeBeforeUpdate = problemaJuridicoRepository.findAll().size();

        // Update the problemaJuridico
        ProblemaJuridico updatedProblemaJuridico = problemaJuridicoRepository.findById(problemaJuridico.getId()).get();
        // Disconnect from session so that the updates on updatedProblemaJuridico are not directly saved in db
        em.detach(updatedProblemaJuridico);
        updatedProblemaJuridico
            .prolemaJuridicoRespondido(UPDATED_PROLEMA_JURIDICO_RESPONDIDO)
            .folhasProblemaJuridico(UPDATED_FOLHAS_PROBLEMA_JURIDICO);

        restProblemaJuridicoMockMvc.perform(put("/api/problema-juridicos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedProblemaJuridico)))
            .andExpect(status().isOk());

        // Validate the ProblemaJuridico in the database
        List<ProblemaJuridico> problemaJuridicoList = problemaJuridicoRepository.findAll();
        assertThat(problemaJuridicoList).hasSize(databaseSizeBeforeUpdate);
        ProblemaJuridico testProblemaJuridico = problemaJuridicoList.get(problemaJuridicoList.size() - 1);
        assertThat(testProblemaJuridico.getProlemaJuridicoRespondido()).isEqualTo(UPDATED_PROLEMA_JURIDICO_RESPONDIDO);
        assertThat(testProblemaJuridico.getFolhasProblemaJuridico()).isEqualTo(UPDATED_FOLHAS_PROBLEMA_JURIDICO);
    }

    @Test
    @Transactional
    public void updateNonExistingProblemaJuridico() throws Exception {
        int databaseSizeBeforeUpdate = problemaJuridicoRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProblemaJuridicoMockMvc.perform(put("/api/problema-juridicos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(problemaJuridico)))
            .andExpect(status().isBadRequest());

        // Validate the ProblemaJuridico in the database
        List<ProblemaJuridico> problemaJuridicoList = problemaJuridicoRepository.findAll();
        assertThat(problemaJuridicoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProblemaJuridico() throws Exception {
        // Initialize the database
        problemaJuridicoService.save(problemaJuridico);

        int databaseSizeBeforeDelete = problemaJuridicoRepository.findAll().size();

        // Delete the problemaJuridico
        restProblemaJuridicoMockMvc.perform(delete("/api/problema-juridicos/{id}", problemaJuridico.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProblemaJuridico> problemaJuridicoList = problemaJuridicoRepository.findAll();
        assertThat(problemaJuridicoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
