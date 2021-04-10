package br.com.cidha.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.cidha.IntegrationTest;
import br.com.cidha.domain.ParteInteresssada;
import br.com.cidha.domain.Processo;
import br.com.cidha.domain.RepresentanteLegal;
import br.com.cidha.repository.ParteInteresssadaRepository;
import br.com.cidha.service.ParteInteresssadaService;
import br.com.cidha.service.criteria.ParteInteresssadaCriteria;
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

/**
 * Integration tests for the {@link ParteInteresssadaResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ParteInteresssadaResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_CLASSIFICACAO = "AAAAAAAAAA";
    private static final String UPDATED_CLASSIFICACAO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/parte-interesssadas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ParteInteresssadaRepository parteInteresssadaRepository;

    @Mock
    private ParteInteresssadaRepository parteInteresssadaRepositoryMock;

    @Mock
    private ParteInteresssadaService parteInteresssadaServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restParteInteresssadaMockMvc;

    private ParteInteresssada parteInteresssada;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ParteInteresssada createEntity(EntityManager em) {
        ParteInteresssada parteInteresssada = new ParteInteresssada().nome(DEFAULT_NOME).classificacao(DEFAULT_CLASSIFICACAO);
        return parteInteresssada;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ParteInteresssada createUpdatedEntity(EntityManager em) {
        ParteInteresssada parteInteresssada = new ParteInteresssada().nome(UPDATED_NOME).classificacao(UPDATED_CLASSIFICACAO);
        return parteInteresssada;
    }

    @BeforeEach
    public void initTest() {
        parteInteresssada = createEntity(em);
    }

    @Test
    @Transactional
    void createParteInteresssada() throws Exception {
        int databaseSizeBeforeCreate = parteInteresssadaRepository.findAll().size();
        // Create the ParteInteresssada
        restParteInteresssadaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(parteInteresssada))
            )
            .andExpect(status().isCreated());

        // Validate the ParteInteresssada in the database
        List<ParteInteresssada> parteInteresssadaList = parteInteresssadaRepository.findAll();
        assertThat(parteInteresssadaList).hasSize(databaseSizeBeforeCreate + 1);
        ParteInteresssada testParteInteresssada = parteInteresssadaList.get(parteInteresssadaList.size() - 1);
        assertThat(testParteInteresssada.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testParteInteresssada.getClassificacao()).isEqualTo(DEFAULT_CLASSIFICACAO);
    }

    @Test
    @Transactional
    void createParteInteresssadaWithExistingId() throws Exception {
        // Create the ParteInteresssada with an existing ID
        parteInteresssada.setId(1L);

        int databaseSizeBeforeCreate = parteInteresssadaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restParteInteresssadaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(parteInteresssada))
            )
            .andExpect(status().isBadRequest());

        // Validate the ParteInteresssada in the database
        List<ParteInteresssada> parteInteresssadaList = parteInteresssadaRepository.findAll();
        assertThat(parteInteresssadaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllParteInteresssadas() throws Exception {
        // Initialize the database
        parteInteresssadaRepository.saveAndFlush(parteInteresssada);

        // Get all the parteInteresssadaList
        restParteInteresssadaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(parteInteresssada.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].classificacao").value(hasItem(DEFAULT_CLASSIFICACAO)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllParteInteresssadasWithEagerRelationshipsIsEnabled() throws Exception {
        when(parteInteresssadaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restParteInteresssadaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(parteInteresssadaServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllParteInteresssadasWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(parteInteresssadaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restParteInteresssadaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(parteInteresssadaServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getParteInteresssada() throws Exception {
        // Initialize the database
        parteInteresssadaRepository.saveAndFlush(parteInteresssada);

        // Get the parteInteresssada
        restParteInteresssadaMockMvc
            .perform(get(ENTITY_API_URL_ID, parteInteresssada.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(parteInteresssada.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.classificacao").value(DEFAULT_CLASSIFICACAO));
    }

    @Test
    @Transactional
    void getParteInteresssadasByIdFiltering() throws Exception {
        // Initialize the database
        parteInteresssadaRepository.saveAndFlush(parteInteresssada);

        Long id = parteInteresssada.getId();

        defaultParteInteresssadaShouldBeFound("id.equals=" + id);
        defaultParteInteresssadaShouldNotBeFound("id.notEquals=" + id);

        defaultParteInteresssadaShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultParteInteresssadaShouldNotBeFound("id.greaterThan=" + id);

        defaultParteInteresssadaShouldBeFound("id.lessThanOrEqual=" + id);
        defaultParteInteresssadaShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllParteInteresssadasByNomeIsEqualToSomething() throws Exception {
        // Initialize the database
        parteInteresssadaRepository.saveAndFlush(parteInteresssada);

        // Get all the parteInteresssadaList where nome equals to DEFAULT_NOME
        defaultParteInteresssadaShouldBeFound("nome.equals=" + DEFAULT_NOME);

        // Get all the parteInteresssadaList where nome equals to UPDATED_NOME
        defaultParteInteresssadaShouldNotBeFound("nome.equals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllParteInteresssadasByNomeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        parteInteresssadaRepository.saveAndFlush(parteInteresssada);

        // Get all the parteInteresssadaList where nome not equals to DEFAULT_NOME
        defaultParteInteresssadaShouldNotBeFound("nome.notEquals=" + DEFAULT_NOME);

        // Get all the parteInteresssadaList where nome not equals to UPDATED_NOME
        defaultParteInteresssadaShouldBeFound("nome.notEquals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllParteInteresssadasByNomeIsInShouldWork() throws Exception {
        // Initialize the database
        parteInteresssadaRepository.saveAndFlush(parteInteresssada);

        // Get all the parteInteresssadaList where nome in DEFAULT_NOME or UPDATED_NOME
        defaultParteInteresssadaShouldBeFound("nome.in=" + DEFAULT_NOME + "," + UPDATED_NOME);

        // Get all the parteInteresssadaList where nome equals to UPDATED_NOME
        defaultParteInteresssadaShouldNotBeFound("nome.in=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllParteInteresssadasByNomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        parteInteresssadaRepository.saveAndFlush(parteInteresssada);

        // Get all the parteInteresssadaList where nome is not null
        defaultParteInteresssadaShouldBeFound("nome.specified=true");

        // Get all the parteInteresssadaList where nome is null
        defaultParteInteresssadaShouldNotBeFound("nome.specified=false");
    }

    @Test
    @Transactional
    void getAllParteInteresssadasByNomeContainsSomething() throws Exception {
        // Initialize the database
        parteInteresssadaRepository.saveAndFlush(parteInteresssada);

        // Get all the parteInteresssadaList where nome contains DEFAULT_NOME
        defaultParteInteresssadaShouldBeFound("nome.contains=" + DEFAULT_NOME);

        // Get all the parteInteresssadaList where nome contains UPDATED_NOME
        defaultParteInteresssadaShouldNotBeFound("nome.contains=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllParteInteresssadasByNomeNotContainsSomething() throws Exception {
        // Initialize the database
        parteInteresssadaRepository.saveAndFlush(parteInteresssada);

        // Get all the parteInteresssadaList where nome does not contain DEFAULT_NOME
        defaultParteInteresssadaShouldNotBeFound("nome.doesNotContain=" + DEFAULT_NOME);

        // Get all the parteInteresssadaList where nome does not contain UPDATED_NOME
        defaultParteInteresssadaShouldBeFound("nome.doesNotContain=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllParteInteresssadasByClassificacaoIsEqualToSomething() throws Exception {
        // Initialize the database
        parteInteresssadaRepository.saveAndFlush(parteInteresssada);

        // Get all the parteInteresssadaList where classificacao equals to DEFAULT_CLASSIFICACAO
        defaultParteInteresssadaShouldBeFound("classificacao.equals=" + DEFAULT_CLASSIFICACAO);

        // Get all the parteInteresssadaList where classificacao equals to UPDATED_CLASSIFICACAO
        defaultParteInteresssadaShouldNotBeFound("classificacao.equals=" + UPDATED_CLASSIFICACAO);
    }

    @Test
    @Transactional
    void getAllParteInteresssadasByClassificacaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        parteInteresssadaRepository.saveAndFlush(parteInteresssada);

        // Get all the parteInteresssadaList where classificacao not equals to DEFAULT_CLASSIFICACAO
        defaultParteInteresssadaShouldNotBeFound("classificacao.notEquals=" + DEFAULT_CLASSIFICACAO);

        // Get all the parteInteresssadaList where classificacao not equals to UPDATED_CLASSIFICACAO
        defaultParteInteresssadaShouldBeFound("classificacao.notEquals=" + UPDATED_CLASSIFICACAO);
    }

    @Test
    @Transactional
    void getAllParteInteresssadasByClassificacaoIsInShouldWork() throws Exception {
        // Initialize the database
        parteInteresssadaRepository.saveAndFlush(parteInteresssada);

        // Get all the parteInteresssadaList where classificacao in DEFAULT_CLASSIFICACAO or UPDATED_CLASSIFICACAO
        defaultParteInteresssadaShouldBeFound("classificacao.in=" + DEFAULT_CLASSIFICACAO + "," + UPDATED_CLASSIFICACAO);

        // Get all the parteInteresssadaList where classificacao equals to UPDATED_CLASSIFICACAO
        defaultParteInteresssadaShouldNotBeFound("classificacao.in=" + UPDATED_CLASSIFICACAO);
    }

    @Test
    @Transactional
    void getAllParteInteresssadasByClassificacaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        parteInteresssadaRepository.saveAndFlush(parteInteresssada);

        // Get all the parteInteresssadaList where classificacao is not null
        defaultParteInteresssadaShouldBeFound("classificacao.specified=true");

        // Get all the parteInteresssadaList where classificacao is null
        defaultParteInteresssadaShouldNotBeFound("classificacao.specified=false");
    }

    @Test
    @Transactional
    void getAllParteInteresssadasByClassificacaoContainsSomething() throws Exception {
        // Initialize the database
        parteInteresssadaRepository.saveAndFlush(parteInteresssada);

        // Get all the parteInteresssadaList where classificacao contains DEFAULT_CLASSIFICACAO
        defaultParteInteresssadaShouldBeFound("classificacao.contains=" + DEFAULT_CLASSIFICACAO);

        // Get all the parteInteresssadaList where classificacao contains UPDATED_CLASSIFICACAO
        defaultParteInteresssadaShouldNotBeFound("classificacao.contains=" + UPDATED_CLASSIFICACAO);
    }

    @Test
    @Transactional
    void getAllParteInteresssadasByClassificacaoNotContainsSomething() throws Exception {
        // Initialize the database
        parteInteresssadaRepository.saveAndFlush(parteInteresssada);

        // Get all the parteInteresssadaList where classificacao does not contain DEFAULT_CLASSIFICACAO
        defaultParteInteresssadaShouldNotBeFound("classificacao.doesNotContain=" + DEFAULT_CLASSIFICACAO);

        // Get all the parteInteresssadaList where classificacao does not contain UPDATED_CLASSIFICACAO
        defaultParteInteresssadaShouldBeFound("classificacao.doesNotContain=" + UPDATED_CLASSIFICACAO);
    }

    @Test
    @Transactional
    void getAllParteInteresssadasByRepresentanteLegalIsEqualToSomething() throws Exception {
        // Initialize the database
        parteInteresssadaRepository.saveAndFlush(parteInteresssada);
        RepresentanteLegal representanteLegal = RepresentanteLegalResourceIT.createEntity(em);
        em.persist(representanteLegal);
        em.flush();
        parteInteresssada.addRepresentanteLegal(representanteLegal);
        parteInteresssadaRepository.saveAndFlush(parteInteresssada);
        Long representanteLegalId = representanteLegal.getId();

        // Get all the parteInteresssadaList where representanteLegal equals to representanteLegalId
        defaultParteInteresssadaShouldBeFound("representanteLegalId.equals=" + representanteLegalId);

        // Get all the parteInteresssadaList where representanteLegal equals to (representanteLegalId + 1)
        defaultParteInteresssadaShouldNotBeFound("representanteLegalId.equals=" + (representanteLegalId + 1));
    }

    @Test
    @Transactional
    void getAllParteInteresssadasByProcessoIsEqualToSomething() throws Exception {
        // Initialize the database
        parteInteresssadaRepository.saveAndFlush(parteInteresssada);
        Processo processo = ProcessoResourceIT.createEntity(em);
        em.persist(processo);
        em.flush();
        parteInteresssada.addProcesso(processo);
        parteInteresssadaRepository.saveAndFlush(parteInteresssada);
        Long processoId = processo.getId();

        // Get all the parteInteresssadaList where processo equals to processoId
        defaultParteInteresssadaShouldBeFound("processoId.equals=" + processoId);

        // Get all the parteInteresssadaList where processo equals to (processoId + 1)
        defaultParteInteresssadaShouldNotBeFound("processoId.equals=" + (processoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultParteInteresssadaShouldBeFound(String filter) throws Exception {
        restParteInteresssadaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(parteInteresssada.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].classificacao").value(hasItem(DEFAULT_CLASSIFICACAO)));

        // Check, that the count call also returns 1
        restParteInteresssadaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultParteInteresssadaShouldNotBeFound(String filter) throws Exception {
        restParteInteresssadaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restParteInteresssadaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingParteInteresssada() throws Exception {
        // Get the parteInteresssada
        restParteInteresssadaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewParteInteresssada() throws Exception {
        // Initialize the database
        parteInteresssadaRepository.saveAndFlush(parteInteresssada);

        int databaseSizeBeforeUpdate = parteInteresssadaRepository.findAll().size();

        // Update the parteInteresssada
        ParteInteresssada updatedParteInteresssada = parteInteresssadaRepository.findById(parteInteresssada.getId()).get();
        // Disconnect from session so that the updates on updatedParteInteresssada are not directly saved in db
        em.detach(updatedParteInteresssada);
        updatedParteInteresssada.nome(UPDATED_NOME).classificacao(UPDATED_CLASSIFICACAO);

        restParteInteresssadaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedParteInteresssada.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedParteInteresssada))
            )
            .andExpect(status().isOk());

        // Validate the ParteInteresssada in the database
        List<ParteInteresssada> parteInteresssadaList = parteInteresssadaRepository.findAll();
        assertThat(parteInteresssadaList).hasSize(databaseSizeBeforeUpdate);
        ParteInteresssada testParteInteresssada = parteInteresssadaList.get(parteInteresssadaList.size() - 1);
        assertThat(testParteInteresssada.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testParteInteresssada.getClassificacao()).isEqualTo(UPDATED_CLASSIFICACAO);
    }

    @Test
    @Transactional
    void putNonExistingParteInteresssada() throws Exception {
        int databaseSizeBeforeUpdate = parteInteresssadaRepository.findAll().size();
        parteInteresssada.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restParteInteresssadaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, parteInteresssada.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(parteInteresssada))
            )
            .andExpect(status().isBadRequest());

        // Validate the ParteInteresssada in the database
        List<ParteInteresssada> parteInteresssadaList = parteInteresssadaRepository.findAll();
        assertThat(parteInteresssadaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchParteInteresssada() throws Exception {
        int databaseSizeBeforeUpdate = parteInteresssadaRepository.findAll().size();
        parteInteresssada.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restParteInteresssadaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(parteInteresssada))
            )
            .andExpect(status().isBadRequest());

        // Validate the ParteInteresssada in the database
        List<ParteInteresssada> parteInteresssadaList = parteInteresssadaRepository.findAll();
        assertThat(parteInteresssadaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamParteInteresssada() throws Exception {
        int databaseSizeBeforeUpdate = parteInteresssadaRepository.findAll().size();
        parteInteresssada.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restParteInteresssadaMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(parteInteresssada))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ParteInteresssada in the database
        List<ParteInteresssada> parteInteresssadaList = parteInteresssadaRepository.findAll();
        assertThat(parteInteresssadaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateParteInteresssadaWithPatch() throws Exception {
        // Initialize the database
        parteInteresssadaRepository.saveAndFlush(parteInteresssada);

        int databaseSizeBeforeUpdate = parteInteresssadaRepository.findAll().size();

        // Update the parteInteresssada using partial update
        ParteInteresssada partialUpdatedParteInteresssada = new ParteInteresssada();
        partialUpdatedParteInteresssada.setId(parteInteresssada.getId());

        partialUpdatedParteInteresssada.nome(UPDATED_NOME);

        restParteInteresssadaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedParteInteresssada.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedParteInteresssada))
            )
            .andExpect(status().isOk());

        // Validate the ParteInteresssada in the database
        List<ParteInteresssada> parteInteresssadaList = parteInteresssadaRepository.findAll();
        assertThat(parteInteresssadaList).hasSize(databaseSizeBeforeUpdate);
        ParteInteresssada testParteInteresssada = parteInteresssadaList.get(parteInteresssadaList.size() - 1);
        assertThat(testParteInteresssada.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testParteInteresssada.getClassificacao()).isEqualTo(DEFAULT_CLASSIFICACAO);
    }

    @Test
    @Transactional
    void fullUpdateParteInteresssadaWithPatch() throws Exception {
        // Initialize the database
        parteInteresssadaRepository.saveAndFlush(parteInteresssada);

        int databaseSizeBeforeUpdate = parteInteresssadaRepository.findAll().size();

        // Update the parteInteresssada using partial update
        ParteInteresssada partialUpdatedParteInteresssada = new ParteInteresssada();
        partialUpdatedParteInteresssada.setId(parteInteresssada.getId());

        partialUpdatedParteInteresssada.nome(UPDATED_NOME).classificacao(UPDATED_CLASSIFICACAO);

        restParteInteresssadaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedParteInteresssada.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedParteInteresssada))
            )
            .andExpect(status().isOk());

        // Validate the ParteInteresssada in the database
        List<ParteInteresssada> parteInteresssadaList = parteInteresssadaRepository.findAll();
        assertThat(parteInteresssadaList).hasSize(databaseSizeBeforeUpdate);
        ParteInteresssada testParteInteresssada = parteInteresssadaList.get(parteInteresssadaList.size() - 1);
        assertThat(testParteInteresssada.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testParteInteresssada.getClassificacao()).isEqualTo(UPDATED_CLASSIFICACAO);
    }

    @Test
    @Transactional
    void patchNonExistingParteInteresssada() throws Exception {
        int databaseSizeBeforeUpdate = parteInteresssadaRepository.findAll().size();
        parteInteresssada.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restParteInteresssadaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, parteInteresssada.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(parteInteresssada))
            )
            .andExpect(status().isBadRequest());

        // Validate the ParteInteresssada in the database
        List<ParteInteresssada> parteInteresssadaList = parteInteresssadaRepository.findAll();
        assertThat(parteInteresssadaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchParteInteresssada() throws Exception {
        int databaseSizeBeforeUpdate = parteInteresssadaRepository.findAll().size();
        parteInteresssada.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restParteInteresssadaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(parteInteresssada))
            )
            .andExpect(status().isBadRequest());

        // Validate the ParteInteresssada in the database
        List<ParteInteresssada> parteInteresssadaList = parteInteresssadaRepository.findAll();
        assertThat(parteInteresssadaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamParteInteresssada() throws Exception {
        int databaseSizeBeforeUpdate = parteInteresssadaRepository.findAll().size();
        parteInteresssada.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restParteInteresssadaMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(parteInteresssada))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ParteInteresssada in the database
        List<ParteInteresssada> parteInteresssadaList = parteInteresssadaRepository.findAll();
        assertThat(parteInteresssadaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteParteInteresssada() throws Exception {
        // Initialize the database
        parteInteresssadaRepository.saveAndFlush(parteInteresssada);

        int databaseSizeBeforeDelete = parteInteresssadaRepository.findAll().size();

        // Delete the parteInteresssada
        restParteInteresssadaMockMvc
            .perform(delete(ENTITY_API_URL_ID, parteInteresssada.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ParteInteresssada> parteInteresssadaList = parteInteresssadaRepository.findAll();
        assertThat(parteInteresssadaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
