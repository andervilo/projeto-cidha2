package br.com.cidha.web.rest;

import br.com.cidha.CidhaApp;
import br.com.cidha.domain.Recurso;
import br.com.cidha.domain.TipoRecurso;
import br.com.cidha.domain.OpcaoRecurso;
import br.com.cidha.domain.Processo;
import br.com.cidha.repository.RecursoRepository;
import br.com.cidha.service.RecursoService;
import br.com.cidha.service.dto.RecursoCriteria;
import br.com.cidha.service.RecursoQueryService;

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
 * Integration tests for the {@link RecursoResource} REST controller.
 */
@SpringBootTest(classes = CidhaApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class RecursoResourceIT {

    private static final String DEFAULT_OBSERVACOES = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVACOES = "BBBBBBBBBB";

    @Autowired
    private RecursoRepository recursoRepository;

    @Autowired
    private RecursoService recursoService;

    @Autowired
    private RecursoQueryService recursoQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRecursoMockMvc;

    private Recurso recurso;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Recurso createEntity(EntityManager em) {
        Recurso recurso = new Recurso()
            .observacoes(DEFAULT_OBSERVACOES);
        return recurso;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Recurso createUpdatedEntity(EntityManager em) {
        Recurso recurso = new Recurso()
            .observacoes(UPDATED_OBSERVACOES);
        return recurso;
    }

    @BeforeEach
    public void initTest() {
        recurso = createEntity(em);
    }

    @Test
    @Transactional
    public void createRecurso() throws Exception {
        int databaseSizeBeforeCreate = recursoRepository.findAll().size();
        // Create the Recurso
        restRecursoMockMvc.perform(post("/api/recursos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(recurso)))
            .andExpect(status().isCreated());

        // Validate the Recurso in the database
        List<Recurso> recursoList = recursoRepository.findAll();
        assertThat(recursoList).hasSize(databaseSizeBeforeCreate + 1);
        Recurso testRecurso = recursoList.get(recursoList.size() - 1);
        assertThat(testRecurso.getObservacoes()).isEqualTo(DEFAULT_OBSERVACOES);
    }

    @Test
    @Transactional
    public void createRecursoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = recursoRepository.findAll().size();

        // Create the Recurso with an existing ID
        recurso.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRecursoMockMvc.perform(post("/api/recursos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(recurso)))
            .andExpect(status().isBadRequest());

        // Validate the Recurso in the database
        List<Recurso> recursoList = recursoRepository.findAll();
        assertThat(recursoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllRecursos() throws Exception {
        // Initialize the database
        recursoRepository.saveAndFlush(recurso);

        // Get all the recursoList
        restRecursoMockMvc.perform(get("/api/recursos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(recurso.getId().intValue())))
            .andExpect(jsonPath("$.[*].observacoes").value(hasItem(DEFAULT_OBSERVACOES.toString())));
    }
    
    @Test
    @Transactional
    public void getRecurso() throws Exception {
        // Initialize the database
        recursoRepository.saveAndFlush(recurso);

        // Get the recurso
        restRecursoMockMvc.perform(get("/api/recursos/{id}", recurso.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(recurso.getId().intValue()))
            .andExpect(jsonPath("$.observacoes").value(DEFAULT_OBSERVACOES.toString()));
    }


    @Test
    @Transactional
    public void getRecursosByIdFiltering() throws Exception {
        // Initialize the database
        recursoRepository.saveAndFlush(recurso);

        Long id = recurso.getId();

        defaultRecursoShouldBeFound("id.equals=" + id);
        defaultRecursoShouldNotBeFound("id.notEquals=" + id);

        defaultRecursoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultRecursoShouldNotBeFound("id.greaterThan=" + id);

        defaultRecursoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultRecursoShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllRecursosByTipoRecursoIsEqualToSomething() throws Exception {
        // Initialize the database
        recursoRepository.saveAndFlush(recurso);
        TipoRecurso tipoRecurso = TipoRecursoResourceIT.createEntity(em);
        em.persist(tipoRecurso);
        em.flush();
        recurso.setTipoRecurso(tipoRecurso);
        recursoRepository.saveAndFlush(recurso);
        Long tipoRecursoId = tipoRecurso.getId();

        // Get all the recursoList where tipoRecurso equals to tipoRecursoId
        defaultRecursoShouldBeFound("tipoRecursoId.equals=" + tipoRecursoId);

        // Get all the recursoList where tipoRecurso equals to tipoRecursoId + 1
        defaultRecursoShouldNotBeFound("tipoRecursoId.equals=" + (tipoRecursoId + 1));
    }


    @Test
    @Transactional
    public void getAllRecursosByOpcaoRecursoIsEqualToSomething() throws Exception {
        // Initialize the database
        recursoRepository.saveAndFlush(recurso);
        OpcaoRecurso opcaoRecurso = OpcaoRecursoResourceIT.createEntity(em);
        em.persist(opcaoRecurso);
        em.flush();
        recurso.setOpcaoRecurso(opcaoRecurso);
        recursoRepository.saveAndFlush(recurso);
        Long opcaoRecursoId = opcaoRecurso.getId();

        // Get all the recursoList where opcaoRecurso equals to opcaoRecursoId
        defaultRecursoShouldBeFound("opcaoRecursoId.equals=" + opcaoRecursoId);

        // Get all the recursoList where opcaoRecurso equals to opcaoRecursoId + 1
        defaultRecursoShouldNotBeFound("opcaoRecursoId.equals=" + (opcaoRecursoId + 1));
    }


    @Test
    @Transactional
    public void getAllRecursosByProcessoIsEqualToSomething() throws Exception {
        // Initialize the database
        recursoRepository.saveAndFlush(recurso);
        Processo processo = ProcessoResourceIT.createEntity(em);
        em.persist(processo);
        em.flush();
        recurso.setProcesso(processo);
        recursoRepository.saveAndFlush(recurso);
        Long processoId = processo.getId();

        // Get all the recursoList where processo equals to processoId
        defaultRecursoShouldBeFound("processoId.equals=" + processoId);

        // Get all the recursoList where processo equals to processoId + 1
        defaultRecursoShouldNotBeFound("processoId.equals=" + (processoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultRecursoShouldBeFound(String filter) throws Exception {
        restRecursoMockMvc.perform(get("/api/recursos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(recurso.getId().intValue())))
            .andExpect(jsonPath("$.[*].observacoes").value(hasItem(DEFAULT_OBSERVACOES.toString())));

        // Check, that the count call also returns 1
        restRecursoMockMvc.perform(get("/api/recursos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultRecursoShouldNotBeFound(String filter) throws Exception {
        restRecursoMockMvc.perform(get("/api/recursos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restRecursoMockMvc.perform(get("/api/recursos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingRecurso() throws Exception {
        // Get the recurso
        restRecursoMockMvc.perform(get("/api/recursos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRecurso() throws Exception {
        // Initialize the database
        recursoService.save(recurso);

        int databaseSizeBeforeUpdate = recursoRepository.findAll().size();

        // Update the recurso
        Recurso updatedRecurso = recursoRepository.findById(recurso.getId()).get();
        // Disconnect from session so that the updates on updatedRecurso are not directly saved in db
        em.detach(updatedRecurso);
        updatedRecurso
            .observacoes(UPDATED_OBSERVACOES);

        restRecursoMockMvc.perform(put("/api/recursos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedRecurso)))
            .andExpect(status().isOk());

        // Validate the Recurso in the database
        List<Recurso> recursoList = recursoRepository.findAll();
        assertThat(recursoList).hasSize(databaseSizeBeforeUpdate);
        Recurso testRecurso = recursoList.get(recursoList.size() - 1);
        assertThat(testRecurso.getObservacoes()).isEqualTo(UPDATED_OBSERVACOES);
    }

    @Test
    @Transactional
    public void updateNonExistingRecurso() throws Exception {
        int databaseSizeBeforeUpdate = recursoRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRecursoMockMvc.perform(put("/api/recursos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(recurso)))
            .andExpect(status().isBadRequest());

        // Validate the Recurso in the database
        List<Recurso> recursoList = recursoRepository.findAll();
        assertThat(recursoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRecurso() throws Exception {
        // Initialize the database
        recursoService.save(recurso);

        int databaseSizeBeforeDelete = recursoRepository.findAll().size();

        // Delete the recurso
        restRecursoMockMvc.perform(delete("/api/recursos/{id}", recurso.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Recurso> recursoList = recursoRepository.findAll();
        assertThat(recursoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
