package br.com.cidha.web.rest;

import br.com.cidha.CidhaApp;
import br.com.cidha.domain.ParteInteresssada;
import br.com.cidha.domain.RepresentanteLegal;
import br.com.cidha.domain.Processo;
import br.com.cidha.repository.ParteInteresssadaRepository;
import br.com.cidha.service.ParteInteresssadaService;
import br.com.cidha.service.dto.ParteInteresssadaCriteria;
import br.com.cidha.service.ParteInteresssadaQueryService;

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
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ParteInteresssadaResource} REST controller.
 */
@SpringBootTest(classes = CidhaApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class ParteInteresssadaResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_CLASSIFICACAO = "AAAAAAAAAA";
    private static final String UPDATED_CLASSIFICACAO = "BBBBBBBBBB";

    @Autowired
    private ParteInteresssadaRepository parteInteresssadaRepository;

    @Mock
    private ParteInteresssadaRepository parteInteresssadaRepositoryMock;

    @Mock
    private ParteInteresssadaService parteInteresssadaServiceMock;

    @Autowired
    private ParteInteresssadaService parteInteresssadaService;

    @Autowired
    private ParteInteresssadaQueryService parteInteresssadaQueryService;

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
        ParteInteresssada parteInteresssada = new ParteInteresssada()
            .nome(DEFAULT_NOME)
            .classificacao(DEFAULT_CLASSIFICACAO);
        return parteInteresssada;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ParteInteresssada createUpdatedEntity(EntityManager em) {
        ParteInteresssada parteInteresssada = new ParteInteresssada()
            .nome(UPDATED_NOME)
            .classificacao(UPDATED_CLASSIFICACAO);
        return parteInteresssada;
    }

    @BeforeEach
    public void initTest() {
        parteInteresssada = createEntity(em);
    }

    @Test
    @Transactional
    public void createParteInteresssada() throws Exception {
        int databaseSizeBeforeCreate = parteInteresssadaRepository.findAll().size();
        // Create the ParteInteresssada
        restParteInteresssadaMockMvc.perform(post("/api/parte-interesssadas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(parteInteresssada)))
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
    public void createParteInteresssadaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = parteInteresssadaRepository.findAll().size();

        // Create the ParteInteresssada with an existing ID
        parteInteresssada.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restParteInteresssadaMockMvc.perform(post("/api/parte-interesssadas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(parteInteresssada)))
            .andExpect(status().isBadRequest());

        // Validate the ParteInteresssada in the database
        List<ParteInteresssada> parteInteresssadaList = parteInteresssadaRepository.findAll();
        assertThat(parteInteresssadaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllParteInteresssadas() throws Exception {
        // Initialize the database
        parteInteresssadaRepository.saveAndFlush(parteInteresssada);

        // Get all the parteInteresssadaList
        restParteInteresssadaMockMvc.perform(get("/api/parte-interesssadas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(parteInteresssada.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].classificacao").value(hasItem(DEFAULT_CLASSIFICACAO)));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllParteInteresssadasWithEagerRelationshipsIsEnabled() throws Exception {
        when(parteInteresssadaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restParteInteresssadaMockMvc.perform(get("/api/parte-interesssadas?eagerload=true"))
            .andExpect(status().isOk());

        verify(parteInteresssadaServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllParteInteresssadasWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(parteInteresssadaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restParteInteresssadaMockMvc.perform(get("/api/parte-interesssadas?eagerload=true"))
            .andExpect(status().isOk());

        verify(parteInteresssadaServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getParteInteresssada() throws Exception {
        // Initialize the database
        parteInteresssadaRepository.saveAndFlush(parteInteresssada);

        // Get the parteInteresssada
        restParteInteresssadaMockMvc.perform(get("/api/parte-interesssadas/{id}", parteInteresssada.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(parteInteresssada.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.classificacao").value(DEFAULT_CLASSIFICACAO));
    }


    @Test
    @Transactional
    public void getParteInteresssadasByIdFiltering() throws Exception {
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
    public void getAllParteInteresssadasByNomeIsEqualToSomething() throws Exception {
        // Initialize the database
        parteInteresssadaRepository.saveAndFlush(parteInteresssada);

        // Get all the parteInteresssadaList where nome equals to DEFAULT_NOME
        defaultParteInteresssadaShouldBeFound("nome.equals=" + DEFAULT_NOME);

        // Get all the parteInteresssadaList where nome equals to UPDATED_NOME
        defaultParteInteresssadaShouldNotBeFound("nome.equals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllParteInteresssadasByNomeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        parteInteresssadaRepository.saveAndFlush(parteInteresssada);

        // Get all the parteInteresssadaList where nome not equals to DEFAULT_NOME
        defaultParteInteresssadaShouldNotBeFound("nome.notEquals=" + DEFAULT_NOME);

        // Get all the parteInteresssadaList where nome not equals to UPDATED_NOME
        defaultParteInteresssadaShouldBeFound("nome.notEquals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllParteInteresssadasByNomeIsInShouldWork() throws Exception {
        // Initialize the database
        parteInteresssadaRepository.saveAndFlush(parteInteresssada);

        // Get all the parteInteresssadaList where nome in DEFAULT_NOME or UPDATED_NOME
        defaultParteInteresssadaShouldBeFound("nome.in=" + DEFAULT_NOME + "," + UPDATED_NOME);

        // Get all the parteInteresssadaList where nome equals to UPDATED_NOME
        defaultParteInteresssadaShouldNotBeFound("nome.in=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllParteInteresssadasByNomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        parteInteresssadaRepository.saveAndFlush(parteInteresssada);

        // Get all the parteInteresssadaList where nome is not null
        defaultParteInteresssadaShouldBeFound("nome.specified=true");

        // Get all the parteInteresssadaList where nome is null
        defaultParteInteresssadaShouldNotBeFound("nome.specified=false");
    }
                @Test
    @Transactional
    public void getAllParteInteresssadasByNomeContainsSomething() throws Exception {
        // Initialize the database
        parteInteresssadaRepository.saveAndFlush(parteInteresssada);

        // Get all the parteInteresssadaList where nome contains DEFAULT_NOME
        defaultParteInteresssadaShouldBeFound("nome.contains=" + DEFAULT_NOME);

        // Get all the parteInteresssadaList where nome contains UPDATED_NOME
        defaultParteInteresssadaShouldNotBeFound("nome.contains=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllParteInteresssadasByNomeNotContainsSomething() throws Exception {
        // Initialize the database
        parteInteresssadaRepository.saveAndFlush(parteInteresssada);

        // Get all the parteInteresssadaList where nome does not contain DEFAULT_NOME
        defaultParteInteresssadaShouldNotBeFound("nome.doesNotContain=" + DEFAULT_NOME);

        // Get all the parteInteresssadaList where nome does not contain UPDATED_NOME
        defaultParteInteresssadaShouldBeFound("nome.doesNotContain=" + UPDATED_NOME);
    }


    @Test
    @Transactional
    public void getAllParteInteresssadasByClassificacaoIsEqualToSomething() throws Exception {
        // Initialize the database
        parteInteresssadaRepository.saveAndFlush(parteInteresssada);

        // Get all the parteInteresssadaList where classificacao equals to DEFAULT_CLASSIFICACAO
        defaultParteInteresssadaShouldBeFound("classificacao.equals=" + DEFAULT_CLASSIFICACAO);

        // Get all the parteInteresssadaList where classificacao equals to UPDATED_CLASSIFICACAO
        defaultParteInteresssadaShouldNotBeFound("classificacao.equals=" + UPDATED_CLASSIFICACAO);
    }

    @Test
    @Transactional
    public void getAllParteInteresssadasByClassificacaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        parteInteresssadaRepository.saveAndFlush(parteInteresssada);

        // Get all the parteInteresssadaList where classificacao not equals to DEFAULT_CLASSIFICACAO
        defaultParteInteresssadaShouldNotBeFound("classificacao.notEquals=" + DEFAULT_CLASSIFICACAO);

        // Get all the parteInteresssadaList where classificacao not equals to UPDATED_CLASSIFICACAO
        defaultParteInteresssadaShouldBeFound("classificacao.notEquals=" + UPDATED_CLASSIFICACAO);
    }

    @Test
    @Transactional
    public void getAllParteInteresssadasByClassificacaoIsInShouldWork() throws Exception {
        // Initialize the database
        parteInteresssadaRepository.saveAndFlush(parteInteresssada);

        // Get all the parteInteresssadaList where classificacao in DEFAULT_CLASSIFICACAO or UPDATED_CLASSIFICACAO
        defaultParteInteresssadaShouldBeFound("classificacao.in=" + DEFAULT_CLASSIFICACAO + "," + UPDATED_CLASSIFICACAO);

        // Get all the parteInteresssadaList where classificacao equals to UPDATED_CLASSIFICACAO
        defaultParteInteresssadaShouldNotBeFound("classificacao.in=" + UPDATED_CLASSIFICACAO);
    }

    @Test
    @Transactional
    public void getAllParteInteresssadasByClassificacaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        parteInteresssadaRepository.saveAndFlush(parteInteresssada);

        // Get all the parteInteresssadaList where classificacao is not null
        defaultParteInteresssadaShouldBeFound("classificacao.specified=true");

        // Get all the parteInteresssadaList where classificacao is null
        defaultParteInteresssadaShouldNotBeFound("classificacao.specified=false");
    }
                @Test
    @Transactional
    public void getAllParteInteresssadasByClassificacaoContainsSomething() throws Exception {
        // Initialize the database
        parteInteresssadaRepository.saveAndFlush(parteInteresssada);

        // Get all the parteInteresssadaList where classificacao contains DEFAULT_CLASSIFICACAO
        defaultParteInteresssadaShouldBeFound("classificacao.contains=" + DEFAULT_CLASSIFICACAO);

        // Get all the parteInteresssadaList where classificacao contains UPDATED_CLASSIFICACAO
        defaultParteInteresssadaShouldNotBeFound("classificacao.contains=" + UPDATED_CLASSIFICACAO);
    }

    @Test
    @Transactional
    public void getAllParteInteresssadasByClassificacaoNotContainsSomething() throws Exception {
        // Initialize the database
        parteInteresssadaRepository.saveAndFlush(parteInteresssada);

        // Get all the parteInteresssadaList where classificacao does not contain DEFAULT_CLASSIFICACAO
        defaultParteInteresssadaShouldNotBeFound("classificacao.doesNotContain=" + DEFAULT_CLASSIFICACAO);

        // Get all the parteInteresssadaList where classificacao does not contain UPDATED_CLASSIFICACAO
        defaultParteInteresssadaShouldBeFound("classificacao.doesNotContain=" + UPDATED_CLASSIFICACAO);
    }


    @Test
    @Transactional
    public void getAllParteInteresssadasByRepresentanteLegalIsEqualToSomething() throws Exception {
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

        // Get all the parteInteresssadaList where representanteLegal equals to representanteLegalId + 1
        defaultParteInteresssadaShouldNotBeFound("representanteLegalId.equals=" + (representanteLegalId + 1));
    }


    @Test
    @Transactional
    public void getAllParteInteresssadasByProcessoIsEqualToSomething() throws Exception {
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

        // Get all the parteInteresssadaList where processo equals to processoId + 1
        defaultParteInteresssadaShouldNotBeFound("processoId.equals=" + (processoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultParteInteresssadaShouldBeFound(String filter) throws Exception {
        restParteInteresssadaMockMvc.perform(get("/api/parte-interesssadas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(parteInteresssada.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].classificacao").value(hasItem(DEFAULT_CLASSIFICACAO)));

        // Check, that the count call also returns 1
        restParteInteresssadaMockMvc.perform(get("/api/parte-interesssadas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultParteInteresssadaShouldNotBeFound(String filter) throws Exception {
        restParteInteresssadaMockMvc.perform(get("/api/parte-interesssadas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restParteInteresssadaMockMvc.perform(get("/api/parte-interesssadas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingParteInteresssada() throws Exception {
        // Get the parteInteresssada
        restParteInteresssadaMockMvc.perform(get("/api/parte-interesssadas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateParteInteresssada() throws Exception {
        // Initialize the database
        parteInteresssadaService.save(parteInteresssada);

        int databaseSizeBeforeUpdate = parteInteresssadaRepository.findAll().size();

        // Update the parteInteresssada
        ParteInteresssada updatedParteInteresssada = parteInteresssadaRepository.findById(parteInteresssada.getId()).get();
        // Disconnect from session so that the updates on updatedParteInteresssada are not directly saved in db
        em.detach(updatedParteInteresssada);
        updatedParteInteresssada
            .nome(UPDATED_NOME)
            .classificacao(UPDATED_CLASSIFICACAO);

        restParteInteresssadaMockMvc.perform(put("/api/parte-interesssadas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedParteInteresssada)))
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
    public void updateNonExistingParteInteresssada() throws Exception {
        int databaseSizeBeforeUpdate = parteInteresssadaRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restParteInteresssadaMockMvc.perform(put("/api/parte-interesssadas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(parteInteresssada)))
            .andExpect(status().isBadRequest());

        // Validate the ParteInteresssada in the database
        List<ParteInteresssada> parteInteresssadaList = parteInteresssadaRepository.findAll();
        assertThat(parteInteresssadaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteParteInteresssada() throws Exception {
        // Initialize the database
        parteInteresssadaService.save(parteInteresssada);

        int databaseSizeBeforeDelete = parteInteresssadaRepository.findAll().size();

        // Delete the parteInteresssada
        restParteInteresssadaMockMvc.perform(delete("/api/parte-interesssadas/{id}", parteInteresssada.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ParteInteresssada> parteInteresssadaList = parteInteresssadaRepository.findAll();
        assertThat(parteInteresssadaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
