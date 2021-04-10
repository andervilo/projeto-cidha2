package br.com.cidha.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.cidha.IntegrationTest;
import br.com.cidha.domain.Conflito;
import br.com.cidha.domain.Direito;
import br.com.cidha.domain.Processo;
import br.com.cidha.domain.ProcessoConflito;
import br.com.cidha.repository.ProcessoConflitoRepository;
import br.com.cidha.service.ProcessoConflitoService;
import br.com.cidha.service.criteria.ProcessoConflitoCriteria;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link ProcessoConflitoResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ProcessoConflitoResourceIT {

    private static final String DEFAULT_INICIO_CONFLITO_OBSERVACOES = "AAAAAAAAAA";
    private static final String UPDATED_INICIO_CONFLITO_OBSERVACOES = "BBBBBBBBBB";

    private static final String DEFAULT_HISTORICO_CONLITO = "AAAAAAAAAA";
    private static final String UPDATED_HISTORICO_CONLITO = "BBBBBBBBBB";

    private static final String DEFAULT_NOME_CASO_COMUIDADE = "AAAAAAAAAA";
    private static final String UPDATED_NOME_CASO_COMUIDADE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_CONSULTA_PREVIA = false;
    private static final Boolean UPDATED_CONSULTA_PREVIA = true;

    private static final String ENTITY_API_URL = "/api/processo-conflitos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProcessoConflitoRepository processoConflitoRepository;

    @Mock
    private ProcessoConflitoRepository processoConflitoRepositoryMock;

    @Mock
    private ProcessoConflitoService processoConflitoServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProcessoConflitoMockMvc;

    private ProcessoConflito processoConflito;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProcessoConflito createEntity(EntityManager em) {
        ProcessoConflito processoConflito = new ProcessoConflito()
            .inicioConflitoObservacoes(DEFAULT_INICIO_CONFLITO_OBSERVACOES)
            .historicoConlito(DEFAULT_HISTORICO_CONLITO)
            .nomeCasoComuidade(DEFAULT_NOME_CASO_COMUIDADE)
            .consultaPrevia(DEFAULT_CONSULTA_PREVIA);
        return processoConflito;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProcessoConflito createUpdatedEntity(EntityManager em) {
        ProcessoConflito processoConflito = new ProcessoConflito()
            .inicioConflitoObservacoes(UPDATED_INICIO_CONFLITO_OBSERVACOES)
            .historicoConlito(UPDATED_HISTORICO_CONLITO)
            .nomeCasoComuidade(UPDATED_NOME_CASO_COMUIDADE)
            .consultaPrevia(UPDATED_CONSULTA_PREVIA);
        return processoConflito;
    }

    @BeforeEach
    public void initTest() {
        processoConflito = createEntity(em);
    }

    @Test
    @Transactional
    void createProcessoConflito() throws Exception {
        int databaseSizeBeforeCreate = processoConflitoRepository.findAll().size();
        // Create the ProcessoConflito
        restProcessoConflitoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(processoConflito))
            )
            .andExpect(status().isCreated());

        // Validate the ProcessoConflito in the database
        List<ProcessoConflito> processoConflitoList = processoConflitoRepository.findAll();
        assertThat(processoConflitoList).hasSize(databaseSizeBeforeCreate + 1);
        ProcessoConflito testProcessoConflito = processoConflitoList.get(processoConflitoList.size() - 1);
        assertThat(testProcessoConflito.getInicioConflitoObservacoes()).isEqualTo(DEFAULT_INICIO_CONFLITO_OBSERVACOES);
        assertThat(testProcessoConflito.getHistoricoConlito()).isEqualTo(DEFAULT_HISTORICO_CONLITO);
        assertThat(testProcessoConflito.getNomeCasoComuidade()).isEqualTo(DEFAULT_NOME_CASO_COMUIDADE);
        assertThat(testProcessoConflito.getConsultaPrevia()).isEqualTo(DEFAULT_CONSULTA_PREVIA);
    }

    @Test
    @Transactional
    void createProcessoConflitoWithExistingId() throws Exception {
        // Create the ProcessoConflito with an existing ID
        processoConflito.setId(1L);

        int databaseSizeBeforeCreate = processoConflitoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProcessoConflitoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(processoConflito))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProcessoConflito in the database
        List<ProcessoConflito> processoConflitoList = processoConflitoRepository.findAll();
        assertThat(processoConflitoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllProcessoConflitos() throws Exception {
        // Initialize the database
        processoConflitoRepository.saveAndFlush(processoConflito);

        // Get all the processoConflitoList
        restProcessoConflitoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(processoConflito.getId().intValue())))
            .andExpect(jsonPath("$.[*].inicioConflitoObservacoes").value(hasItem(DEFAULT_INICIO_CONFLITO_OBSERVACOES.toString())))
            .andExpect(jsonPath("$.[*].historicoConlito").value(hasItem(DEFAULT_HISTORICO_CONLITO.toString())))
            .andExpect(jsonPath("$.[*].nomeCasoComuidade").value(hasItem(DEFAULT_NOME_CASO_COMUIDADE)))
            .andExpect(jsonPath("$.[*].consultaPrevia").value(hasItem(DEFAULT_CONSULTA_PREVIA.booleanValue())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllProcessoConflitosWithEagerRelationshipsIsEnabled() throws Exception {
        when(processoConflitoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restProcessoConflitoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(processoConflitoServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllProcessoConflitosWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(processoConflitoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restProcessoConflitoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(processoConflitoServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getProcessoConflito() throws Exception {
        // Initialize the database
        processoConflitoRepository.saveAndFlush(processoConflito);

        // Get the processoConflito
        restProcessoConflitoMockMvc
            .perform(get(ENTITY_API_URL_ID, processoConflito.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(processoConflito.getId().intValue()))
            .andExpect(jsonPath("$.inicioConflitoObservacoes").value(DEFAULT_INICIO_CONFLITO_OBSERVACOES.toString()))
            .andExpect(jsonPath("$.historicoConlito").value(DEFAULT_HISTORICO_CONLITO.toString()))
            .andExpect(jsonPath("$.nomeCasoComuidade").value(DEFAULT_NOME_CASO_COMUIDADE))
            .andExpect(jsonPath("$.consultaPrevia").value(DEFAULT_CONSULTA_PREVIA.booleanValue()));
    }

    @Test
    @Transactional
    void getProcessoConflitosByIdFiltering() throws Exception {
        // Initialize the database
        processoConflitoRepository.saveAndFlush(processoConflito);

        Long id = processoConflito.getId();

        defaultProcessoConflitoShouldBeFound("id.equals=" + id);
        defaultProcessoConflitoShouldNotBeFound("id.notEquals=" + id);

        defaultProcessoConflitoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultProcessoConflitoShouldNotBeFound("id.greaterThan=" + id);

        defaultProcessoConflitoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultProcessoConflitoShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllProcessoConflitosByNomeCasoComuidadeIsEqualToSomething() throws Exception {
        // Initialize the database
        processoConflitoRepository.saveAndFlush(processoConflito);

        // Get all the processoConflitoList where nomeCasoComuidade equals to DEFAULT_NOME_CASO_COMUIDADE
        defaultProcessoConflitoShouldBeFound("nomeCasoComuidade.equals=" + DEFAULT_NOME_CASO_COMUIDADE);

        // Get all the processoConflitoList where nomeCasoComuidade equals to UPDATED_NOME_CASO_COMUIDADE
        defaultProcessoConflitoShouldNotBeFound("nomeCasoComuidade.equals=" + UPDATED_NOME_CASO_COMUIDADE);
    }

    @Test
    @Transactional
    void getAllProcessoConflitosByNomeCasoComuidadeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        processoConflitoRepository.saveAndFlush(processoConflito);

        // Get all the processoConflitoList where nomeCasoComuidade not equals to DEFAULT_NOME_CASO_COMUIDADE
        defaultProcessoConflitoShouldNotBeFound("nomeCasoComuidade.notEquals=" + DEFAULT_NOME_CASO_COMUIDADE);

        // Get all the processoConflitoList where nomeCasoComuidade not equals to UPDATED_NOME_CASO_COMUIDADE
        defaultProcessoConflitoShouldBeFound("nomeCasoComuidade.notEquals=" + UPDATED_NOME_CASO_COMUIDADE);
    }

    @Test
    @Transactional
    void getAllProcessoConflitosByNomeCasoComuidadeIsInShouldWork() throws Exception {
        // Initialize the database
        processoConflitoRepository.saveAndFlush(processoConflito);

        // Get all the processoConflitoList where nomeCasoComuidade in DEFAULT_NOME_CASO_COMUIDADE or UPDATED_NOME_CASO_COMUIDADE
        defaultProcessoConflitoShouldBeFound("nomeCasoComuidade.in=" + DEFAULT_NOME_CASO_COMUIDADE + "," + UPDATED_NOME_CASO_COMUIDADE);

        // Get all the processoConflitoList where nomeCasoComuidade equals to UPDATED_NOME_CASO_COMUIDADE
        defaultProcessoConflitoShouldNotBeFound("nomeCasoComuidade.in=" + UPDATED_NOME_CASO_COMUIDADE);
    }

    @Test
    @Transactional
    void getAllProcessoConflitosByNomeCasoComuidadeIsNullOrNotNull() throws Exception {
        // Initialize the database
        processoConflitoRepository.saveAndFlush(processoConflito);

        // Get all the processoConflitoList where nomeCasoComuidade is not null
        defaultProcessoConflitoShouldBeFound("nomeCasoComuidade.specified=true");

        // Get all the processoConflitoList where nomeCasoComuidade is null
        defaultProcessoConflitoShouldNotBeFound("nomeCasoComuidade.specified=false");
    }

    @Test
    @Transactional
    void getAllProcessoConflitosByNomeCasoComuidadeContainsSomething() throws Exception {
        // Initialize the database
        processoConflitoRepository.saveAndFlush(processoConflito);

        // Get all the processoConflitoList where nomeCasoComuidade contains DEFAULT_NOME_CASO_COMUIDADE
        defaultProcessoConflitoShouldBeFound("nomeCasoComuidade.contains=" + DEFAULT_NOME_CASO_COMUIDADE);

        // Get all the processoConflitoList where nomeCasoComuidade contains UPDATED_NOME_CASO_COMUIDADE
        defaultProcessoConflitoShouldNotBeFound("nomeCasoComuidade.contains=" + UPDATED_NOME_CASO_COMUIDADE);
    }

    @Test
    @Transactional
    void getAllProcessoConflitosByNomeCasoComuidadeNotContainsSomething() throws Exception {
        // Initialize the database
        processoConflitoRepository.saveAndFlush(processoConflito);

        // Get all the processoConflitoList where nomeCasoComuidade does not contain DEFAULT_NOME_CASO_COMUIDADE
        defaultProcessoConflitoShouldNotBeFound("nomeCasoComuidade.doesNotContain=" + DEFAULT_NOME_CASO_COMUIDADE);

        // Get all the processoConflitoList where nomeCasoComuidade does not contain UPDATED_NOME_CASO_COMUIDADE
        defaultProcessoConflitoShouldBeFound("nomeCasoComuidade.doesNotContain=" + UPDATED_NOME_CASO_COMUIDADE);
    }

    @Test
    @Transactional
    void getAllProcessoConflitosByConsultaPreviaIsEqualToSomething() throws Exception {
        // Initialize the database
        processoConflitoRepository.saveAndFlush(processoConflito);

        // Get all the processoConflitoList where consultaPrevia equals to DEFAULT_CONSULTA_PREVIA
        defaultProcessoConflitoShouldBeFound("consultaPrevia.equals=" + DEFAULT_CONSULTA_PREVIA);

        // Get all the processoConflitoList where consultaPrevia equals to UPDATED_CONSULTA_PREVIA
        defaultProcessoConflitoShouldNotBeFound("consultaPrevia.equals=" + UPDATED_CONSULTA_PREVIA);
    }

    @Test
    @Transactional
    void getAllProcessoConflitosByConsultaPreviaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        processoConflitoRepository.saveAndFlush(processoConflito);

        // Get all the processoConflitoList where consultaPrevia not equals to DEFAULT_CONSULTA_PREVIA
        defaultProcessoConflitoShouldNotBeFound("consultaPrevia.notEquals=" + DEFAULT_CONSULTA_PREVIA);

        // Get all the processoConflitoList where consultaPrevia not equals to UPDATED_CONSULTA_PREVIA
        defaultProcessoConflitoShouldBeFound("consultaPrevia.notEquals=" + UPDATED_CONSULTA_PREVIA);
    }

    @Test
    @Transactional
    void getAllProcessoConflitosByConsultaPreviaIsInShouldWork() throws Exception {
        // Initialize the database
        processoConflitoRepository.saveAndFlush(processoConflito);

        // Get all the processoConflitoList where consultaPrevia in DEFAULT_CONSULTA_PREVIA or UPDATED_CONSULTA_PREVIA
        defaultProcessoConflitoShouldBeFound("consultaPrevia.in=" + DEFAULT_CONSULTA_PREVIA + "," + UPDATED_CONSULTA_PREVIA);

        // Get all the processoConflitoList where consultaPrevia equals to UPDATED_CONSULTA_PREVIA
        defaultProcessoConflitoShouldNotBeFound("consultaPrevia.in=" + UPDATED_CONSULTA_PREVIA);
    }

    @Test
    @Transactional
    void getAllProcessoConflitosByConsultaPreviaIsNullOrNotNull() throws Exception {
        // Initialize the database
        processoConflitoRepository.saveAndFlush(processoConflito);

        // Get all the processoConflitoList where consultaPrevia is not null
        defaultProcessoConflitoShouldBeFound("consultaPrevia.specified=true");

        // Get all the processoConflitoList where consultaPrevia is null
        defaultProcessoConflitoShouldNotBeFound("consultaPrevia.specified=false");
    }

    @Test
    @Transactional
    void getAllProcessoConflitosByConflitoIsEqualToSomething() throws Exception {
        // Initialize the database
        processoConflitoRepository.saveAndFlush(processoConflito);
        Conflito conflito = ConflitoResourceIT.createEntity(em);
        em.persist(conflito);
        em.flush();
        processoConflito.addConflito(conflito);
        processoConflitoRepository.saveAndFlush(processoConflito);
        Long conflitoId = conflito.getId();

        // Get all the processoConflitoList where conflito equals to conflitoId
        defaultProcessoConflitoShouldBeFound("conflitoId.equals=" + conflitoId);

        // Get all the processoConflitoList where conflito equals to (conflitoId + 1)
        defaultProcessoConflitoShouldNotBeFound("conflitoId.equals=" + (conflitoId + 1));
    }

    @Test
    @Transactional
    void getAllProcessoConflitosByDireitoIsEqualToSomething() throws Exception {
        // Initialize the database
        processoConflitoRepository.saveAndFlush(processoConflito);
        Direito direito = DireitoResourceIT.createEntity(em);
        em.persist(direito);
        em.flush();
        processoConflito.addDireito(direito);
        processoConflitoRepository.saveAndFlush(processoConflito);
        Long direitoId = direito.getId();

        // Get all the processoConflitoList where direito equals to direitoId
        defaultProcessoConflitoShouldBeFound("direitoId.equals=" + direitoId);

        // Get all the processoConflitoList where direito equals to (direitoId + 1)
        defaultProcessoConflitoShouldNotBeFound("direitoId.equals=" + (direitoId + 1));
    }

    @Test
    @Transactional
    void getAllProcessoConflitosByProcessoIsEqualToSomething() throws Exception {
        // Initialize the database
        processoConflitoRepository.saveAndFlush(processoConflito);
        Processo processo = ProcessoResourceIT.createEntity(em);
        em.persist(processo);
        em.flush();
        processoConflito.addProcesso(processo);
        processoConflitoRepository.saveAndFlush(processoConflito);
        Long processoId = processo.getId();

        // Get all the processoConflitoList where processo equals to processoId
        defaultProcessoConflitoShouldBeFound("processoId.equals=" + processoId);

        // Get all the processoConflitoList where processo equals to (processoId + 1)
        defaultProcessoConflitoShouldNotBeFound("processoId.equals=" + (processoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProcessoConflitoShouldBeFound(String filter) throws Exception {
        restProcessoConflitoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(processoConflito.getId().intValue())))
            .andExpect(jsonPath("$.[*].inicioConflitoObservacoes").value(hasItem(DEFAULT_INICIO_CONFLITO_OBSERVACOES.toString())))
            .andExpect(jsonPath("$.[*].historicoConlito").value(hasItem(DEFAULT_HISTORICO_CONLITO.toString())))
            .andExpect(jsonPath("$.[*].nomeCasoComuidade").value(hasItem(DEFAULT_NOME_CASO_COMUIDADE)))
            .andExpect(jsonPath("$.[*].consultaPrevia").value(hasItem(DEFAULT_CONSULTA_PREVIA.booleanValue())));

        // Check, that the count call also returns 1
        restProcessoConflitoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProcessoConflitoShouldNotBeFound(String filter) throws Exception {
        restProcessoConflitoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProcessoConflitoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingProcessoConflito() throws Exception {
        // Get the processoConflito
        restProcessoConflitoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewProcessoConflito() throws Exception {
        // Initialize the database
        processoConflitoRepository.saveAndFlush(processoConflito);

        int databaseSizeBeforeUpdate = processoConflitoRepository.findAll().size();

        // Update the processoConflito
        ProcessoConflito updatedProcessoConflito = processoConflitoRepository.findById(processoConflito.getId()).get();
        // Disconnect from session so that the updates on updatedProcessoConflito are not directly saved in db
        em.detach(updatedProcessoConflito);
        updatedProcessoConflito
            .inicioConflitoObservacoes(UPDATED_INICIO_CONFLITO_OBSERVACOES)
            .historicoConlito(UPDATED_HISTORICO_CONLITO)
            .nomeCasoComuidade(UPDATED_NOME_CASO_COMUIDADE)
            .consultaPrevia(UPDATED_CONSULTA_PREVIA);

        restProcessoConflitoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedProcessoConflito.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedProcessoConflito))
            )
            .andExpect(status().isOk());

        // Validate the ProcessoConflito in the database
        List<ProcessoConflito> processoConflitoList = processoConflitoRepository.findAll();
        assertThat(processoConflitoList).hasSize(databaseSizeBeforeUpdate);
        ProcessoConflito testProcessoConflito = processoConflitoList.get(processoConflitoList.size() - 1);
        assertThat(testProcessoConflito.getInicioConflitoObservacoes()).isEqualTo(UPDATED_INICIO_CONFLITO_OBSERVACOES);
        assertThat(testProcessoConflito.getHistoricoConlito()).isEqualTo(UPDATED_HISTORICO_CONLITO);
        assertThat(testProcessoConflito.getNomeCasoComuidade()).isEqualTo(UPDATED_NOME_CASO_COMUIDADE);
        assertThat(testProcessoConflito.getConsultaPrevia()).isEqualTo(UPDATED_CONSULTA_PREVIA);
    }

    @Test
    @Transactional
    void putNonExistingProcessoConflito() throws Exception {
        int databaseSizeBeforeUpdate = processoConflitoRepository.findAll().size();
        processoConflito.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProcessoConflitoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, processoConflito.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(processoConflito))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProcessoConflito in the database
        List<ProcessoConflito> processoConflitoList = processoConflitoRepository.findAll();
        assertThat(processoConflitoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProcessoConflito() throws Exception {
        int databaseSizeBeforeUpdate = processoConflitoRepository.findAll().size();
        processoConflito.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProcessoConflitoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(processoConflito))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProcessoConflito in the database
        List<ProcessoConflito> processoConflitoList = processoConflitoRepository.findAll();
        assertThat(processoConflitoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProcessoConflito() throws Exception {
        int databaseSizeBeforeUpdate = processoConflitoRepository.findAll().size();
        processoConflito.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProcessoConflitoMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(processoConflito))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProcessoConflito in the database
        List<ProcessoConflito> processoConflitoList = processoConflitoRepository.findAll();
        assertThat(processoConflitoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProcessoConflitoWithPatch() throws Exception {
        // Initialize the database
        processoConflitoRepository.saveAndFlush(processoConflito);

        int databaseSizeBeforeUpdate = processoConflitoRepository.findAll().size();

        // Update the processoConflito using partial update
        ProcessoConflito partialUpdatedProcessoConflito = new ProcessoConflito();
        partialUpdatedProcessoConflito.setId(processoConflito.getId());

        partialUpdatedProcessoConflito.nomeCasoComuidade(UPDATED_NOME_CASO_COMUIDADE);

        restProcessoConflitoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProcessoConflito.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProcessoConflito))
            )
            .andExpect(status().isOk());

        // Validate the ProcessoConflito in the database
        List<ProcessoConflito> processoConflitoList = processoConflitoRepository.findAll();
        assertThat(processoConflitoList).hasSize(databaseSizeBeforeUpdate);
        ProcessoConflito testProcessoConflito = processoConflitoList.get(processoConflitoList.size() - 1);
        assertThat(testProcessoConflito.getInicioConflitoObservacoes()).isEqualTo(DEFAULT_INICIO_CONFLITO_OBSERVACOES);
        assertThat(testProcessoConflito.getHistoricoConlito()).isEqualTo(DEFAULT_HISTORICO_CONLITO);
        assertThat(testProcessoConflito.getNomeCasoComuidade()).isEqualTo(UPDATED_NOME_CASO_COMUIDADE);
        assertThat(testProcessoConflito.getConsultaPrevia()).isEqualTo(DEFAULT_CONSULTA_PREVIA);
    }

    @Test
    @Transactional
    void fullUpdateProcessoConflitoWithPatch() throws Exception {
        // Initialize the database
        processoConflitoRepository.saveAndFlush(processoConflito);

        int databaseSizeBeforeUpdate = processoConflitoRepository.findAll().size();

        // Update the processoConflito using partial update
        ProcessoConflito partialUpdatedProcessoConflito = new ProcessoConflito();
        partialUpdatedProcessoConflito.setId(processoConflito.getId());

        partialUpdatedProcessoConflito
            .inicioConflitoObservacoes(UPDATED_INICIO_CONFLITO_OBSERVACOES)
            .historicoConlito(UPDATED_HISTORICO_CONLITO)
            .nomeCasoComuidade(UPDATED_NOME_CASO_COMUIDADE)
            .consultaPrevia(UPDATED_CONSULTA_PREVIA);

        restProcessoConflitoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProcessoConflito.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProcessoConflito))
            )
            .andExpect(status().isOk());

        // Validate the ProcessoConflito in the database
        List<ProcessoConflito> processoConflitoList = processoConflitoRepository.findAll();
        assertThat(processoConflitoList).hasSize(databaseSizeBeforeUpdate);
        ProcessoConflito testProcessoConflito = processoConflitoList.get(processoConflitoList.size() - 1);
        assertThat(testProcessoConflito.getInicioConflitoObservacoes()).isEqualTo(UPDATED_INICIO_CONFLITO_OBSERVACOES);
        assertThat(testProcessoConflito.getHistoricoConlito()).isEqualTo(UPDATED_HISTORICO_CONLITO);
        assertThat(testProcessoConflito.getNomeCasoComuidade()).isEqualTo(UPDATED_NOME_CASO_COMUIDADE);
        assertThat(testProcessoConflito.getConsultaPrevia()).isEqualTo(UPDATED_CONSULTA_PREVIA);
    }

    @Test
    @Transactional
    void patchNonExistingProcessoConflito() throws Exception {
        int databaseSizeBeforeUpdate = processoConflitoRepository.findAll().size();
        processoConflito.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProcessoConflitoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, processoConflito.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(processoConflito))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProcessoConflito in the database
        List<ProcessoConflito> processoConflitoList = processoConflitoRepository.findAll();
        assertThat(processoConflitoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProcessoConflito() throws Exception {
        int databaseSizeBeforeUpdate = processoConflitoRepository.findAll().size();
        processoConflito.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProcessoConflitoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(processoConflito))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProcessoConflito in the database
        List<ProcessoConflito> processoConflitoList = processoConflitoRepository.findAll();
        assertThat(processoConflitoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProcessoConflito() throws Exception {
        int databaseSizeBeforeUpdate = processoConflitoRepository.findAll().size();
        processoConflito.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProcessoConflitoMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(processoConflito))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProcessoConflito in the database
        List<ProcessoConflito> processoConflitoList = processoConflitoRepository.findAll();
        assertThat(processoConflitoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProcessoConflito() throws Exception {
        // Initialize the database
        processoConflitoRepository.saveAndFlush(processoConflito);

        int databaseSizeBeforeDelete = processoConflitoRepository.findAll().size();

        // Delete the processoConflito
        restProcessoConflitoMockMvc
            .perform(delete(ENTITY_API_URL_ID, processoConflito.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProcessoConflito> processoConflitoList = processoConflitoRepository.findAll();
        assertThat(processoConflitoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
