package br.com.cidha.web.rest;

import br.com.cidha.CidhaApp;
import br.com.cidha.domain.Jurisprudencia;
import br.com.cidha.domain.ProblemaJuridico;
import br.com.cidha.repository.JurisprudenciaRepository;
import br.com.cidha.service.JurisprudenciaService;
import br.com.cidha.service.dto.JurisprudenciaCriteria;
import br.com.cidha.service.JurisprudenciaQueryService;

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
 * Integration tests for the {@link JurisprudenciaResource} REST controller.
 */
@SpringBootTest(classes = CidhaApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class JurisprudenciaResourceIT {

    private static final String DEFAULT_JURISPRUDENCIA_CITADA_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_JURISPRUDENCIA_CITADA_DESCRICAO = "BBBBBBBBBB";

    private static final String DEFAULT_FOLHAS_JURISPRUDENCIA_CITADA = "AAAAAAAAAA";
    private static final String UPDATED_FOLHAS_JURISPRUDENCIA_CITADA = "BBBBBBBBBB";

    private static final String DEFAULT_JURISPRUDENCIA_SUGERIDA = "AAAAAAAAAA";
    private static final String UPDATED_JURISPRUDENCIA_SUGERIDA = "BBBBBBBBBB";

    @Autowired
    private JurisprudenciaRepository jurisprudenciaRepository;

    @Autowired
    private JurisprudenciaService jurisprudenciaService;

    @Autowired
    private JurisprudenciaQueryService jurisprudenciaQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restJurisprudenciaMockMvc;

    private Jurisprudencia jurisprudencia;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Jurisprudencia createEntity(EntityManager em) {
        Jurisprudencia jurisprudencia = new Jurisprudencia()
            .jurisprudenciaCitadaDescricao(DEFAULT_JURISPRUDENCIA_CITADA_DESCRICAO)
            .folhasJurisprudenciaCitada(DEFAULT_FOLHAS_JURISPRUDENCIA_CITADA)
            .jurisprudenciaSugerida(DEFAULT_JURISPRUDENCIA_SUGERIDA);
        return jurisprudencia;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Jurisprudencia createUpdatedEntity(EntityManager em) {
        Jurisprudencia jurisprudencia = new Jurisprudencia()
            .jurisprudenciaCitadaDescricao(UPDATED_JURISPRUDENCIA_CITADA_DESCRICAO)
            .folhasJurisprudenciaCitada(UPDATED_FOLHAS_JURISPRUDENCIA_CITADA)
            .jurisprudenciaSugerida(UPDATED_JURISPRUDENCIA_SUGERIDA);
        return jurisprudencia;
    }

    @BeforeEach
    public void initTest() {
        jurisprudencia = createEntity(em);
    }

    @Test
    @Transactional
    public void createJurisprudencia() throws Exception {
        int databaseSizeBeforeCreate = jurisprudenciaRepository.findAll().size();
        // Create the Jurisprudencia
        restJurisprudenciaMockMvc.perform(post("/api/jurisprudencias")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(jurisprudencia)))
            .andExpect(status().isCreated());

        // Validate the Jurisprudencia in the database
        List<Jurisprudencia> jurisprudenciaList = jurisprudenciaRepository.findAll();
        assertThat(jurisprudenciaList).hasSize(databaseSizeBeforeCreate + 1);
        Jurisprudencia testJurisprudencia = jurisprudenciaList.get(jurisprudenciaList.size() - 1);
        assertThat(testJurisprudencia.getJurisprudenciaCitadaDescricao()).isEqualTo(DEFAULT_JURISPRUDENCIA_CITADA_DESCRICAO);
        assertThat(testJurisprudencia.getFolhasJurisprudenciaCitada()).isEqualTo(DEFAULT_FOLHAS_JURISPRUDENCIA_CITADA);
        assertThat(testJurisprudencia.getJurisprudenciaSugerida()).isEqualTo(DEFAULT_JURISPRUDENCIA_SUGERIDA);
    }

    @Test
    @Transactional
    public void createJurisprudenciaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = jurisprudenciaRepository.findAll().size();

        // Create the Jurisprudencia with an existing ID
        jurisprudencia.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restJurisprudenciaMockMvc.perform(post("/api/jurisprudencias")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(jurisprudencia)))
            .andExpect(status().isBadRequest());

        // Validate the Jurisprudencia in the database
        List<Jurisprudencia> jurisprudenciaList = jurisprudenciaRepository.findAll();
        assertThat(jurisprudenciaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllJurisprudencias() throws Exception {
        // Initialize the database
        jurisprudenciaRepository.saveAndFlush(jurisprudencia);

        // Get all the jurisprudenciaList
        restJurisprudenciaMockMvc.perform(get("/api/jurisprudencias?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(jurisprudencia.getId().intValue())))
            .andExpect(jsonPath("$.[*].jurisprudenciaCitadaDescricao").value(hasItem(DEFAULT_JURISPRUDENCIA_CITADA_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].folhasJurisprudenciaCitada").value(hasItem(DEFAULT_FOLHAS_JURISPRUDENCIA_CITADA)))
            .andExpect(jsonPath("$.[*].jurisprudenciaSugerida").value(hasItem(DEFAULT_JURISPRUDENCIA_SUGERIDA.toString())));
    }
    
    @Test
    @Transactional
    public void getJurisprudencia() throws Exception {
        // Initialize the database
        jurisprudenciaRepository.saveAndFlush(jurisprudencia);

        // Get the jurisprudencia
        restJurisprudenciaMockMvc.perform(get("/api/jurisprudencias/{id}", jurisprudencia.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(jurisprudencia.getId().intValue()))
            .andExpect(jsonPath("$.jurisprudenciaCitadaDescricao").value(DEFAULT_JURISPRUDENCIA_CITADA_DESCRICAO.toString()))
            .andExpect(jsonPath("$.folhasJurisprudenciaCitada").value(DEFAULT_FOLHAS_JURISPRUDENCIA_CITADA))
            .andExpect(jsonPath("$.jurisprudenciaSugerida").value(DEFAULT_JURISPRUDENCIA_SUGERIDA.toString()));
    }


    @Test
    @Transactional
    public void getJurisprudenciasByIdFiltering() throws Exception {
        // Initialize the database
        jurisprudenciaRepository.saveAndFlush(jurisprudencia);

        Long id = jurisprudencia.getId();

        defaultJurisprudenciaShouldBeFound("id.equals=" + id);
        defaultJurisprudenciaShouldNotBeFound("id.notEquals=" + id);

        defaultJurisprudenciaShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultJurisprudenciaShouldNotBeFound("id.greaterThan=" + id);

        defaultJurisprudenciaShouldBeFound("id.lessThanOrEqual=" + id);
        defaultJurisprudenciaShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllJurisprudenciasByFolhasJurisprudenciaCitadaIsEqualToSomething() throws Exception {
        // Initialize the database
        jurisprudenciaRepository.saveAndFlush(jurisprudencia);

        // Get all the jurisprudenciaList where folhasJurisprudenciaCitada equals to DEFAULT_FOLHAS_JURISPRUDENCIA_CITADA
        defaultJurisprudenciaShouldBeFound("folhasJurisprudenciaCitada.equals=" + DEFAULT_FOLHAS_JURISPRUDENCIA_CITADA);

        // Get all the jurisprudenciaList where folhasJurisprudenciaCitada equals to UPDATED_FOLHAS_JURISPRUDENCIA_CITADA
        defaultJurisprudenciaShouldNotBeFound("folhasJurisprudenciaCitada.equals=" + UPDATED_FOLHAS_JURISPRUDENCIA_CITADA);
    }

    @Test
    @Transactional
    public void getAllJurisprudenciasByFolhasJurisprudenciaCitadaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        jurisprudenciaRepository.saveAndFlush(jurisprudencia);

        // Get all the jurisprudenciaList where folhasJurisprudenciaCitada not equals to DEFAULT_FOLHAS_JURISPRUDENCIA_CITADA
        defaultJurisprudenciaShouldNotBeFound("folhasJurisprudenciaCitada.notEquals=" + DEFAULT_FOLHAS_JURISPRUDENCIA_CITADA);

        // Get all the jurisprudenciaList where folhasJurisprudenciaCitada not equals to UPDATED_FOLHAS_JURISPRUDENCIA_CITADA
        defaultJurisprudenciaShouldBeFound("folhasJurisprudenciaCitada.notEquals=" + UPDATED_FOLHAS_JURISPRUDENCIA_CITADA);
    }

    @Test
    @Transactional
    public void getAllJurisprudenciasByFolhasJurisprudenciaCitadaIsInShouldWork() throws Exception {
        // Initialize the database
        jurisprudenciaRepository.saveAndFlush(jurisprudencia);

        // Get all the jurisprudenciaList where folhasJurisprudenciaCitada in DEFAULT_FOLHAS_JURISPRUDENCIA_CITADA or UPDATED_FOLHAS_JURISPRUDENCIA_CITADA
        defaultJurisprudenciaShouldBeFound("folhasJurisprudenciaCitada.in=" + DEFAULT_FOLHAS_JURISPRUDENCIA_CITADA + "," + UPDATED_FOLHAS_JURISPRUDENCIA_CITADA);

        // Get all the jurisprudenciaList where folhasJurisprudenciaCitada equals to UPDATED_FOLHAS_JURISPRUDENCIA_CITADA
        defaultJurisprudenciaShouldNotBeFound("folhasJurisprudenciaCitada.in=" + UPDATED_FOLHAS_JURISPRUDENCIA_CITADA);
    }

    @Test
    @Transactional
    public void getAllJurisprudenciasByFolhasJurisprudenciaCitadaIsNullOrNotNull() throws Exception {
        // Initialize the database
        jurisprudenciaRepository.saveAndFlush(jurisprudencia);

        // Get all the jurisprudenciaList where folhasJurisprudenciaCitada is not null
        defaultJurisprudenciaShouldBeFound("folhasJurisprudenciaCitada.specified=true");

        // Get all the jurisprudenciaList where folhasJurisprudenciaCitada is null
        defaultJurisprudenciaShouldNotBeFound("folhasJurisprudenciaCitada.specified=false");
    }
                @Test
    @Transactional
    public void getAllJurisprudenciasByFolhasJurisprudenciaCitadaContainsSomething() throws Exception {
        // Initialize the database
        jurisprudenciaRepository.saveAndFlush(jurisprudencia);

        // Get all the jurisprudenciaList where folhasJurisprudenciaCitada contains DEFAULT_FOLHAS_JURISPRUDENCIA_CITADA
        defaultJurisprudenciaShouldBeFound("folhasJurisprudenciaCitada.contains=" + DEFAULT_FOLHAS_JURISPRUDENCIA_CITADA);

        // Get all the jurisprudenciaList where folhasJurisprudenciaCitada contains UPDATED_FOLHAS_JURISPRUDENCIA_CITADA
        defaultJurisprudenciaShouldNotBeFound("folhasJurisprudenciaCitada.contains=" + UPDATED_FOLHAS_JURISPRUDENCIA_CITADA);
    }

    @Test
    @Transactional
    public void getAllJurisprudenciasByFolhasJurisprudenciaCitadaNotContainsSomething() throws Exception {
        // Initialize the database
        jurisprudenciaRepository.saveAndFlush(jurisprudencia);

        // Get all the jurisprudenciaList where folhasJurisprudenciaCitada does not contain DEFAULT_FOLHAS_JURISPRUDENCIA_CITADA
        defaultJurisprudenciaShouldNotBeFound("folhasJurisprudenciaCitada.doesNotContain=" + DEFAULT_FOLHAS_JURISPRUDENCIA_CITADA);

        // Get all the jurisprudenciaList where folhasJurisprudenciaCitada does not contain UPDATED_FOLHAS_JURISPRUDENCIA_CITADA
        defaultJurisprudenciaShouldBeFound("folhasJurisprudenciaCitada.doesNotContain=" + UPDATED_FOLHAS_JURISPRUDENCIA_CITADA);
    }


    @Test
    @Transactional
    public void getAllJurisprudenciasByProblemaJuridicoIsEqualToSomething() throws Exception {
        // Initialize the database
        jurisprudenciaRepository.saveAndFlush(jurisprudencia);
        ProblemaJuridico problemaJuridico = ProblemaJuridicoResourceIT.createEntity(em);
        em.persist(problemaJuridico);
        em.flush();
        jurisprudencia.addProblemaJuridico(problemaJuridico);
        jurisprudenciaRepository.saveAndFlush(jurisprudencia);
        Long problemaJuridicoId = problemaJuridico.getId();

        // Get all the jurisprudenciaList where problemaJuridico equals to problemaJuridicoId
        defaultJurisprudenciaShouldBeFound("problemaJuridicoId.equals=" + problemaJuridicoId);

        // Get all the jurisprudenciaList where problemaJuridico equals to problemaJuridicoId + 1
        defaultJurisprudenciaShouldNotBeFound("problemaJuridicoId.equals=" + (problemaJuridicoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultJurisprudenciaShouldBeFound(String filter) throws Exception {
        restJurisprudenciaMockMvc.perform(get("/api/jurisprudencias?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(jurisprudencia.getId().intValue())))
            .andExpect(jsonPath("$.[*].jurisprudenciaCitadaDescricao").value(hasItem(DEFAULT_JURISPRUDENCIA_CITADA_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].folhasJurisprudenciaCitada").value(hasItem(DEFAULT_FOLHAS_JURISPRUDENCIA_CITADA)))
            .andExpect(jsonPath("$.[*].jurisprudenciaSugerida").value(hasItem(DEFAULT_JURISPRUDENCIA_SUGERIDA.toString())));

        // Check, that the count call also returns 1
        restJurisprudenciaMockMvc.perform(get("/api/jurisprudencias/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultJurisprudenciaShouldNotBeFound(String filter) throws Exception {
        restJurisprudenciaMockMvc.perform(get("/api/jurisprudencias?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restJurisprudenciaMockMvc.perform(get("/api/jurisprudencias/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingJurisprudencia() throws Exception {
        // Get the jurisprudencia
        restJurisprudenciaMockMvc.perform(get("/api/jurisprudencias/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateJurisprudencia() throws Exception {
        // Initialize the database
        jurisprudenciaService.save(jurisprudencia);

        int databaseSizeBeforeUpdate = jurisprudenciaRepository.findAll().size();

        // Update the jurisprudencia
        Jurisprudencia updatedJurisprudencia = jurisprudenciaRepository.findById(jurisprudencia.getId()).get();
        // Disconnect from session so that the updates on updatedJurisprudencia are not directly saved in db
        em.detach(updatedJurisprudencia);
        updatedJurisprudencia
            .jurisprudenciaCitadaDescricao(UPDATED_JURISPRUDENCIA_CITADA_DESCRICAO)
            .folhasJurisprudenciaCitada(UPDATED_FOLHAS_JURISPRUDENCIA_CITADA)
            .jurisprudenciaSugerida(UPDATED_JURISPRUDENCIA_SUGERIDA);

        restJurisprudenciaMockMvc.perform(put("/api/jurisprudencias")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedJurisprudencia)))
            .andExpect(status().isOk());

        // Validate the Jurisprudencia in the database
        List<Jurisprudencia> jurisprudenciaList = jurisprudenciaRepository.findAll();
        assertThat(jurisprudenciaList).hasSize(databaseSizeBeforeUpdate);
        Jurisprudencia testJurisprudencia = jurisprudenciaList.get(jurisprudenciaList.size() - 1);
        assertThat(testJurisprudencia.getJurisprudenciaCitadaDescricao()).isEqualTo(UPDATED_JURISPRUDENCIA_CITADA_DESCRICAO);
        assertThat(testJurisprudencia.getFolhasJurisprudenciaCitada()).isEqualTo(UPDATED_FOLHAS_JURISPRUDENCIA_CITADA);
        assertThat(testJurisprudencia.getJurisprudenciaSugerida()).isEqualTo(UPDATED_JURISPRUDENCIA_SUGERIDA);
    }

    @Test
    @Transactional
    public void updateNonExistingJurisprudencia() throws Exception {
        int databaseSizeBeforeUpdate = jurisprudenciaRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restJurisprudenciaMockMvc.perform(put("/api/jurisprudencias")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(jurisprudencia)))
            .andExpect(status().isBadRequest());

        // Validate the Jurisprudencia in the database
        List<Jurisprudencia> jurisprudenciaList = jurisprudenciaRepository.findAll();
        assertThat(jurisprudenciaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteJurisprudencia() throws Exception {
        // Initialize the database
        jurisprudenciaService.save(jurisprudencia);

        int databaseSizeBeforeDelete = jurisprudenciaRepository.findAll().size();

        // Delete the jurisprudencia
        restJurisprudenciaMockMvc.perform(delete("/api/jurisprudencias/{id}", jurisprudencia.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Jurisprudencia> jurisprudenciaList = jurisprudenciaRepository.findAll();
        assertThat(jurisprudenciaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
