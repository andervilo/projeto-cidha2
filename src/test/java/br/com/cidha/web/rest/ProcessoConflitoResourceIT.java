package br.com.cidha.web.rest;

import br.com.cidha.CidhaApp;
import br.com.cidha.domain.ProcessoConflito;
import br.com.cidha.domain.Conflito;
import br.com.cidha.domain.Direito;
import br.com.cidha.domain.Processo;
import br.com.cidha.repository.ProcessoConflitoRepository;
import br.com.cidha.service.ProcessoConflitoService;
import br.com.cidha.service.dto.ProcessoConflitoCriteria;
import br.com.cidha.service.ProcessoConflitoQueryService;

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
 * Integration tests for the {@link ProcessoConflitoResource} REST controller.
 */
@SpringBootTest(classes = CidhaApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class ProcessoConflitoResourceIT {

    private static final String DEFAULT_INICIO_CONFLITO_OBSERVACOES = "AAAAAAAAAA";
    private static final String UPDATED_INICIO_CONFLITO_OBSERVACOES = "BBBBBBBBBB";

    private static final String DEFAULT_HISTORICO_CONLITO = "AAAAAAAAAA";
    private static final String UPDATED_HISTORICO_CONLITO = "BBBBBBBBBB";

    private static final String DEFAULT_NOME_CASO_COMUIDADE = "AAAAAAAAAA";
    private static final String UPDATED_NOME_CASO_COMUIDADE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_CONSULTA_PREVIA = false;
    private static final Boolean UPDATED_CONSULTA_PREVIA = true;

    @Autowired
    private ProcessoConflitoRepository processoConflitoRepository;

    @Mock
    private ProcessoConflitoRepository processoConflitoRepositoryMock;

    @Mock
    private ProcessoConflitoService processoConflitoServiceMock;

    @Autowired
    private ProcessoConflitoService processoConflitoService;

    @Autowired
    private ProcessoConflitoQueryService processoConflitoQueryService;

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
    public void createProcessoConflito() throws Exception {
        int databaseSizeBeforeCreate = processoConflitoRepository.findAll().size();
        // Create the ProcessoConflito
        restProcessoConflitoMockMvc.perform(post("/api/processo-conflitos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(processoConflito)))
            .andExpect(status().isCreated());

        // Validate the ProcessoConflito in the database
        List<ProcessoConflito> processoConflitoList = processoConflitoRepository.findAll();
        assertThat(processoConflitoList).hasSize(databaseSizeBeforeCreate + 1);
        ProcessoConflito testProcessoConflito = processoConflitoList.get(processoConflitoList.size() - 1);
        assertThat(testProcessoConflito.getInicioConflitoObservacoes()).isEqualTo(DEFAULT_INICIO_CONFLITO_OBSERVACOES);
        assertThat(testProcessoConflito.getHistoricoConlito()).isEqualTo(DEFAULT_HISTORICO_CONLITO);
        assertThat(testProcessoConflito.getNomeCasoComuidade()).isEqualTo(DEFAULT_NOME_CASO_COMUIDADE);
        assertThat(testProcessoConflito.isConsultaPrevia()).isEqualTo(DEFAULT_CONSULTA_PREVIA);
    }

    @Test
    @Transactional
    public void createProcessoConflitoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = processoConflitoRepository.findAll().size();

        // Create the ProcessoConflito with an existing ID
        processoConflito.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProcessoConflitoMockMvc.perform(post("/api/processo-conflitos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(processoConflito)))
            .andExpect(status().isBadRequest());

        // Validate the ProcessoConflito in the database
        List<ProcessoConflito> processoConflitoList = processoConflitoRepository.findAll();
        assertThat(processoConflitoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllProcessoConflitos() throws Exception {
        // Initialize the database
        processoConflitoRepository.saveAndFlush(processoConflito);

        // Get all the processoConflitoList
        restProcessoConflitoMockMvc.perform(get("/api/processo-conflitos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(processoConflito.getId().intValue())))
            .andExpect(jsonPath("$.[*].inicioConflitoObservacoes").value(hasItem(DEFAULT_INICIO_CONFLITO_OBSERVACOES.toString())))
            .andExpect(jsonPath("$.[*].historicoConlito").value(hasItem(DEFAULT_HISTORICO_CONLITO.toString())))
            .andExpect(jsonPath("$.[*].nomeCasoComuidade").value(hasItem(DEFAULT_NOME_CASO_COMUIDADE)))
            .andExpect(jsonPath("$.[*].consultaPrevia").value(hasItem(DEFAULT_CONSULTA_PREVIA.booleanValue())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllProcessoConflitosWithEagerRelationshipsIsEnabled() throws Exception {
        when(processoConflitoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restProcessoConflitoMockMvc.perform(get("/api/processo-conflitos?eagerload=true"))
            .andExpect(status().isOk());

        verify(processoConflitoServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllProcessoConflitosWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(processoConflitoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restProcessoConflitoMockMvc.perform(get("/api/processo-conflitos?eagerload=true"))
            .andExpect(status().isOk());

        verify(processoConflitoServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getProcessoConflito() throws Exception {
        // Initialize the database
        processoConflitoRepository.saveAndFlush(processoConflito);

        // Get the processoConflito
        restProcessoConflitoMockMvc.perform(get("/api/processo-conflitos/{id}", processoConflito.getId()))
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
    public void getProcessoConflitosByIdFiltering() throws Exception {
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
    public void getAllProcessoConflitosByNomeCasoComuidadeIsEqualToSomething() throws Exception {
        // Initialize the database
        processoConflitoRepository.saveAndFlush(processoConflito);

        // Get all the processoConflitoList where nomeCasoComuidade equals to DEFAULT_NOME_CASO_COMUIDADE
        defaultProcessoConflitoShouldBeFound("nomeCasoComuidade.equals=" + DEFAULT_NOME_CASO_COMUIDADE);

        // Get all the processoConflitoList where nomeCasoComuidade equals to UPDATED_NOME_CASO_COMUIDADE
        defaultProcessoConflitoShouldNotBeFound("nomeCasoComuidade.equals=" + UPDATED_NOME_CASO_COMUIDADE);
    }

    @Test
    @Transactional
    public void getAllProcessoConflitosByNomeCasoComuidadeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        processoConflitoRepository.saveAndFlush(processoConflito);

        // Get all the processoConflitoList where nomeCasoComuidade not equals to DEFAULT_NOME_CASO_COMUIDADE
        defaultProcessoConflitoShouldNotBeFound("nomeCasoComuidade.notEquals=" + DEFAULT_NOME_CASO_COMUIDADE);

        // Get all the processoConflitoList where nomeCasoComuidade not equals to UPDATED_NOME_CASO_COMUIDADE
        defaultProcessoConflitoShouldBeFound("nomeCasoComuidade.notEquals=" + UPDATED_NOME_CASO_COMUIDADE);
    }

    @Test
    @Transactional
    public void getAllProcessoConflitosByNomeCasoComuidadeIsInShouldWork() throws Exception {
        // Initialize the database
        processoConflitoRepository.saveAndFlush(processoConflito);

        // Get all the processoConflitoList where nomeCasoComuidade in DEFAULT_NOME_CASO_COMUIDADE or UPDATED_NOME_CASO_COMUIDADE
        defaultProcessoConflitoShouldBeFound("nomeCasoComuidade.in=" + DEFAULT_NOME_CASO_COMUIDADE + "," + UPDATED_NOME_CASO_COMUIDADE);

        // Get all the processoConflitoList where nomeCasoComuidade equals to UPDATED_NOME_CASO_COMUIDADE
        defaultProcessoConflitoShouldNotBeFound("nomeCasoComuidade.in=" + UPDATED_NOME_CASO_COMUIDADE);
    }

    @Test
    @Transactional
    public void getAllProcessoConflitosByNomeCasoComuidadeIsNullOrNotNull() throws Exception {
        // Initialize the database
        processoConflitoRepository.saveAndFlush(processoConflito);

        // Get all the processoConflitoList where nomeCasoComuidade is not null
        defaultProcessoConflitoShouldBeFound("nomeCasoComuidade.specified=true");

        // Get all the processoConflitoList where nomeCasoComuidade is null
        defaultProcessoConflitoShouldNotBeFound("nomeCasoComuidade.specified=false");
    }
                @Test
    @Transactional
    public void getAllProcessoConflitosByNomeCasoComuidadeContainsSomething() throws Exception {
        // Initialize the database
        processoConflitoRepository.saveAndFlush(processoConflito);

        // Get all the processoConflitoList where nomeCasoComuidade contains DEFAULT_NOME_CASO_COMUIDADE
        defaultProcessoConflitoShouldBeFound("nomeCasoComuidade.contains=" + DEFAULT_NOME_CASO_COMUIDADE);

        // Get all the processoConflitoList where nomeCasoComuidade contains UPDATED_NOME_CASO_COMUIDADE
        defaultProcessoConflitoShouldNotBeFound("nomeCasoComuidade.contains=" + UPDATED_NOME_CASO_COMUIDADE);
    }

    @Test
    @Transactional
    public void getAllProcessoConflitosByNomeCasoComuidadeNotContainsSomething() throws Exception {
        // Initialize the database
        processoConflitoRepository.saveAndFlush(processoConflito);

        // Get all the processoConflitoList where nomeCasoComuidade does not contain DEFAULT_NOME_CASO_COMUIDADE
        defaultProcessoConflitoShouldNotBeFound("nomeCasoComuidade.doesNotContain=" + DEFAULT_NOME_CASO_COMUIDADE);

        // Get all the processoConflitoList where nomeCasoComuidade does not contain UPDATED_NOME_CASO_COMUIDADE
        defaultProcessoConflitoShouldBeFound("nomeCasoComuidade.doesNotContain=" + UPDATED_NOME_CASO_COMUIDADE);
    }


    @Test
    @Transactional
    public void getAllProcessoConflitosByConsultaPreviaIsEqualToSomething() throws Exception {
        // Initialize the database
        processoConflitoRepository.saveAndFlush(processoConflito);

        // Get all the processoConflitoList where consultaPrevia equals to DEFAULT_CONSULTA_PREVIA
        defaultProcessoConflitoShouldBeFound("consultaPrevia.equals=" + DEFAULT_CONSULTA_PREVIA);

        // Get all the processoConflitoList where consultaPrevia equals to UPDATED_CONSULTA_PREVIA
        defaultProcessoConflitoShouldNotBeFound("consultaPrevia.equals=" + UPDATED_CONSULTA_PREVIA);
    }

    @Test
    @Transactional
    public void getAllProcessoConflitosByConsultaPreviaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        processoConflitoRepository.saveAndFlush(processoConflito);

        // Get all the processoConflitoList where consultaPrevia not equals to DEFAULT_CONSULTA_PREVIA
        defaultProcessoConflitoShouldNotBeFound("consultaPrevia.notEquals=" + DEFAULT_CONSULTA_PREVIA);

        // Get all the processoConflitoList where consultaPrevia not equals to UPDATED_CONSULTA_PREVIA
        defaultProcessoConflitoShouldBeFound("consultaPrevia.notEquals=" + UPDATED_CONSULTA_PREVIA);
    }

    @Test
    @Transactional
    public void getAllProcessoConflitosByConsultaPreviaIsInShouldWork() throws Exception {
        // Initialize the database
        processoConflitoRepository.saveAndFlush(processoConflito);

        // Get all the processoConflitoList where consultaPrevia in DEFAULT_CONSULTA_PREVIA or UPDATED_CONSULTA_PREVIA
        defaultProcessoConflitoShouldBeFound("consultaPrevia.in=" + DEFAULT_CONSULTA_PREVIA + "," + UPDATED_CONSULTA_PREVIA);

        // Get all the processoConflitoList where consultaPrevia equals to UPDATED_CONSULTA_PREVIA
        defaultProcessoConflitoShouldNotBeFound("consultaPrevia.in=" + UPDATED_CONSULTA_PREVIA);
    }

    @Test
    @Transactional
    public void getAllProcessoConflitosByConsultaPreviaIsNullOrNotNull() throws Exception {
        // Initialize the database
        processoConflitoRepository.saveAndFlush(processoConflito);

        // Get all the processoConflitoList where consultaPrevia is not null
        defaultProcessoConflitoShouldBeFound("consultaPrevia.specified=true");

        // Get all the processoConflitoList where consultaPrevia is null
        defaultProcessoConflitoShouldNotBeFound("consultaPrevia.specified=false");
    }

    @Test
    @Transactional
    public void getAllProcessoConflitosByConflitoIsEqualToSomething() throws Exception {
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

        // Get all the processoConflitoList where conflito equals to conflitoId + 1
        defaultProcessoConflitoShouldNotBeFound("conflitoId.equals=" + (conflitoId + 1));
    }


    @Test
    @Transactional
    public void getAllProcessoConflitosByDireitoIsEqualToSomething() throws Exception {
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

        // Get all the processoConflitoList where direito equals to direitoId + 1
        defaultProcessoConflitoShouldNotBeFound("direitoId.equals=" + (direitoId + 1));
    }


    @Test
    @Transactional
    public void getAllProcessoConflitosByProcessoIsEqualToSomething() throws Exception {
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

        // Get all the processoConflitoList where processo equals to processoId + 1
        defaultProcessoConflitoShouldNotBeFound("processoId.equals=" + (processoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProcessoConflitoShouldBeFound(String filter) throws Exception {
        restProcessoConflitoMockMvc.perform(get("/api/processo-conflitos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(processoConflito.getId().intValue())))
            .andExpect(jsonPath("$.[*].inicioConflitoObservacoes").value(hasItem(DEFAULT_INICIO_CONFLITO_OBSERVACOES.toString())))
            .andExpect(jsonPath("$.[*].historicoConlito").value(hasItem(DEFAULT_HISTORICO_CONLITO.toString())))
            .andExpect(jsonPath("$.[*].nomeCasoComuidade").value(hasItem(DEFAULT_NOME_CASO_COMUIDADE)))
            .andExpect(jsonPath("$.[*].consultaPrevia").value(hasItem(DEFAULT_CONSULTA_PREVIA.booleanValue())));

        // Check, that the count call also returns 1
        restProcessoConflitoMockMvc.perform(get("/api/processo-conflitos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProcessoConflitoShouldNotBeFound(String filter) throws Exception {
        restProcessoConflitoMockMvc.perform(get("/api/processo-conflitos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProcessoConflitoMockMvc.perform(get("/api/processo-conflitos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingProcessoConflito() throws Exception {
        // Get the processoConflito
        restProcessoConflitoMockMvc.perform(get("/api/processo-conflitos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProcessoConflito() throws Exception {
        // Initialize the database
        processoConflitoService.save(processoConflito);

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

        restProcessoConflitoMockMvc.perform(put("/api/processo-conflitos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedProcessoConflito)))
            .andExpect(status().isOk());

        // Validate the ProcessoConflito in the database
        List<ProcessoConflito> processoConflitoList = processoConflitoRepository.findAll();
        assertThat(processoConflitoList).hasSize(databaseSizeBeforeUpdate);
        ProcessoConflito testProcessoConflito = processoConflitoList.get(processoConflitoList.size() - 1);
        assertThat(testProcessoConflito.getInicioConflitoObservacoes()).isEqualTo(UPDATED_INICIO_CONFLITO_OBSERVACOES);
        assertThat(testProcessoConflito.getHistoricoConlito()).isEqualTo(UPDATED_HISTORICO_CONLITO);
        assertThat(testProcessoConflito.getNomeCasoComuidade()).isEqualTo(UPDATED_NOME_CASO_COMUIDADE);
        assertThat(testProcessoConflito.isConsultaPrevia()).isEqualTo(UPDATED_CONSULTA_PREVIA);
    }

    @Test
    @Transactional
    public void updateNonExistingProcessoConflito() throws Exception {
        int databaseSizeBeforeUpdate = processoConflitoRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProcessoConflitoMockMvc.perform(put("/api/processo-conflitos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(processoConflito)))
            .andExpect(status().isBadRequest());

        // Validate the ProcessoConflito in the database
        List<ProcessoConflito> processoConflitoList = processoConflitoRepository.findAll();
        assertThat(processoConflitoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProcessoConflito() throws Exception {
        // Initialize the database
        processoConflitoService.save(processoConflito);

        int databaseSizeBeforeDelete = processoConflitoRepository.findAll().size();

        // Delete the processoConflito
        restProcessoConflitoMockMvc.perform(delete("/api/processo-conflitos/{id}", processoConflito.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProcessoConflito> processoConflitoList = processoConflitoRepository.findAll();
        assertThat(processoConflitoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
