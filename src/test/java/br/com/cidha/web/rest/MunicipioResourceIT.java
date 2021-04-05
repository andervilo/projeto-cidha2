package br.com.cidha.web.rest;

import br.com.cidha.CidhaApp;
import br.com.cidha.domain.Municipio;
import br.com.cidha.domain.Processo;
import br.com.cidha.repository.MunicipioRepository;
import br.com.cidha.service.MunicipioService;
import br.com.cidha.service.dto.MunicipioCriteria;
import br.com.cidha.service.MunicipioQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link MunicipioResource} REST controller.
 */
@SpringBootTest(classes = CidhaApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class MunicipioResourceIT {

    private static final Boolean DEFAULT_AMAZONIA_LEGAL = false;
    private static final Boolean UPDATED_AMAZONIA_LEGAL = true;

    private static final Integer DEFAULT_CODIGO_IBGE = 1;
    private static final Integer UPDATED_CODIGO_IBGE = 2;
    private static final Integer SMALLER_CODIGO_IBGE = 1 - 1;

    private static final String DEFAULT_ESTADO = "AAAAAAAAAA";
    private static final String UPDATED_ESTADO = "BBBBBBBBBB";

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    @Autowired
    private MunicipioRepository municipioRepository;

    @Autowired
    private MunicipioService municipioService;

    @Autowired
    private MunicipioQueryService municipioQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMunicipioMockMvc;

    private Municipio municipio;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Municipio createEntity(EntityManager em) {
        Municipio municipio = new Municipio()
            .amazoniaLegal(DEFAULT_AMAZONIA_LEGAL)
            .codigoIbge(DEFAULT_CODIGO_IBGE)
            .estado(DEFAULT_ESTADO)
            .nome(DEFAULT_NOME);
        return municipio;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Municipio createUpdatedEntity(EntityManager em) {
        Municipio municipio = new Municipio()
            .amazoniaLegal(UPDATED_AMAZONIA_LEGAL)
            .codigoIbge(UPDATED_CODIGO_IBGE)
            .estado(UPDATED_ESTADO)
            .nome(UPDATED_NOME);
        return municipio;
    }

    @BeforeEach
    public void initTest() {
        municipio = createEntity(em);
    }

    @Test
    @Transactional
    public void createMunicipio() throws Exception {
        int databaseSizeBeforeCreate = municipioRepository.findAll().size();
        // Create the Municipio
        restMunicipioMockMvc.perform(post("/api/municipios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(municipio)))
            .andExpect(status().isCreated());

        // Validate the Municipio in the database
        List<Municipio> municipioList = municipioRepository.findAll();
        assertThat(municipioList).hasSize(databaseSizeBeforeCreate + 1);
        Municipio testMunicipio = municipioList.get(municipioList.size() - 1);
        assertThat(testMunicipio.isAmazoniaLegal()).isEqualTo(DEFAULT_AMAZONIA_LEGAL);
        assertThat(testMunicipio.getCodigoIbge()).isEqualTo(DEFAULT_CODIGO_IBGE);
        assertThat(testMunicipio.getEstado()).isEqualTo(DEFAULT_ESTADO);
        assertThat(testMunicipio.getNome()).isEqualTo(DEFAULT_NOME);
    }

    @Test
    @Transactional
    public void createMunicipioWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = municipioRepository.findAll().size();

        // Create the Municipio with an existing ID
        municipio.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMunicipioMockMvc.perform(post("/api/municipios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(municipio)))
            .andExpect(status().isBadRequest());

        // Validate the Municipio in the database
        List<Municipio> municipioList = municipioRepository.findAll();
        assertThat(municipioList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllMunicipios() throws Exception {
        // Initialize the database
        municipioRepository.saveAndFlush(municipio);

        // Get all the municipioList
        restMunicipioMockMvc.perform(get("/api/municipios?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(municipio.getId().intValue())))
            .andExpect(jsonPath("$.[*].amazoniaLegal").value(hasItem(DEFAULT_AMAZONIA_LEGAL.booleanValue())))
            .andExpect(jsonPath("$.[*].codigoIbge").value(hasItem(DEFAULT_CODIGO_IBGE)))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO)))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)));
    }
    
    @Test
    @Transactional
    public void getMunicipio() throws Exception {
        // Initialize the database
        municipioRepository.saveAndFlush(municipio);

        // Get the municipio
        restMunicipioMockMvc.perform(get("/api/municipios/{id}", municipio.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(municipio.getId().intValue()))
            .andExpect(jsonPath("$.amazoniaLegal").value(DEFAULT_AMAZONIA_LEGAL.booleanValue()))
            .andExpect(jsonPath("$.codigoIbge").value(DEFAULT_CODIGO_IBGE))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME));
    }


    @Test
    @Transactional
    public void getMunicipiosByIdFiltering() throws Exception {
        // Initialize the database
        municipioRepository.saveAndFlush(municipio);

        Long id = municipio.getId();

        defaultMunicipioShouldBeFound("id.equals=" + id);
        defaultMunicipioShouldNotBeFound("id.notEquals=" + id);

        defaultMunicipioShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMunicipioShouldNotBeFound("id.greaterThan=" + id);

        defaultMunicipioShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMunicipioShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllMunicipiosByAmazoniaLegalIsEqualToSomething() throws Exception {
        // Initialize the database
        municipioRepository.saveAndFlush(municipio);

        // Get all the municipioList where amazoniaLegal equals to DEFAULT_AMAZONIA_LEGAL
        defaultMunicipioShouldBeFound("amazoniaLegal.equals=" + DEFAULT_AMAZONIA_LEGAL);

        // Get all the municipioList where amazoniaLegal equals to UPDATED_AMAZONIA_LEGAL
        defaultMunicipioShouldNotBeFound("amazoniaLegal.equals=" + UPDATED_AMAZONIA_LEGAL);
    }

    @Test
    @Transactional
    public void getAllMunicipiosByAmazoniaLegalIsNotEqualToSomething() throws Exception {
        // Initialize the database
        municipioRepository.saveAndFlush(municipio);

        // Get all the municipioList where amazoniaLegal not equals to DEFAULT_AMAZONIA_LEGAL
        defaultMunicipioShouldNotBeFound("amazoniaLegal.notEquals=" + DEFAULT_AMAZONIA_LEGAL);

        // Get all the municipioList where amazoniaLegal not equals to UPDATED_AMAZONIA_LEGAL
        defaultMunicipioShouldBeFound("amazoniaLegal.notEquals=" + UPDATED_AMAZONIA_LEGAL);
    }

    @Test
    @Transactional
    public void getAllMunicipiosByAmazoniaLegalIsInShouldWork() throws Exception {
        // Initialize the database
        municipioRepository.saveAndFlush(municipio);

        // Get all the municipioList where amazoniaLegal in DEFAULT_AMAZONIA_LEGAL or UPDATED_AMAZONIA_LEGAL
        defaultMunicipioShouldBeFound("amazoniaLegal.in=" + DEFAULT_AMAZONIA_LEGAL + "," + UPDATED_AMAZONIA_LEGAL);

        // Get all the municipioList where amazoniaLegal equals to UPDATED_AMAZONIA_LEGAL
        defaultMunicipioShouldNotBeFound("amazoniaLegal.in=" + UPDATED_AMAZONIA_LEGAL);
    }

    @Test
    @Transactional
    public void getAllMunicipiosByAmazoniaLegalIsNullOrNotNull() throws Exception {
        // Initialize the database
        municipioRepository.saveAndFlush(municipio);

        // Get all the municipioList where amazoniaLegal is not null
        defaultMunicipioShouldBeFound("amazoniaLegal.specified=true");

        // Get all the municipioList where amazoniaLegal is null
        defaultMunicipioShouldNotBeFound("amazoniaLegal.specified=false");
    }

    @Test
    @Transactional
    public void getAllMunicipiosByCodigoIbgeIsEqualToSomething() throws Exception {
        // Initialize the database
        municipioRepository.saveAndFlush(municipio);

        // Get all the municipioList where codigoIbge equals to DEFAULT_CODIGO_IBGE
        defaultMunicipioShouldBeFound("codigoIbge.equals=" + DEFAULT_CODIGO_IBGE);

        // Get all the municipioList where codigoIbge equals to UPDATED_CODIGO_IBGE
        defaultMunicipioShouldNotBeFound("codigoIbge.equals=" + UPDATED_CODIGO_IBGE);
    }

    @Test
    @Transactional
    public void getAllMunicipiosByCodigoIbgeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        municipioRepository.saveAndFlush(municipio);

        // Get all the municipioList where codigoIbge not equals to DEFAULT_CODIGO_IBGE
        defaultMunicipioShouldNotBeFound("codigoIbge.notEquals=" + DEFAULT_CODIGO_IBGE);

        // Get all the municipioList where codigoIbge not equals to UPDATED_CODIGO_IBGE
        defaultMunicipioShouldBeFound("codigoIbge.notEquals=" + UPDATED_CODIGO_IBGE);
    }

    @Test
    @Transactional
    public void getAllMunicipiosByCodigoIbgeIsInShouldWork() throws Exception {
        // Initialize the database
        municipioRepository.saveAndFlush(municipio);

        // Get all the municipioList where codigoIbge in DEFAULT_CODIGO_IBGE or UPDATED_CODIGO_IBGE
        defaultMunicipioShouldBeFound("codigoIbge.in=" + DEFAULT_CODIGO_IBGE + "," + UPDATED_CODIGO_IBGE);

        // Get all the municipioList where codigoIbge equals to UPDATED_CODIGO_IBGE
        defaultMunicipioShouldNotBeFound("codigoIbge.in=" + UPDATED_CODIGO_IBGE);
    }

    @Test
    @Transactional
    public void getAllMunicipiosByCodigoIbgeIsNullOrNotNull() throws Exception {
        // Initialize the database
        municipioRepository.saveAndFlush(municipio);

        // Get all the municipioList where codigoIbge is not null
        defaultMunicipioShouldBeFound("codigoIbge.specified=true");

        // Get all the municipioList where codigoIbge is null
        defaultMunicipioShouldNotBeFound("codigoIbge.specified=false");
    }

    @Test
    @Transactional
    public void getAllMunicipiosByCodigoIbgeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        municipioRepository.saveAndFlush(municipio);

        // Get all the municipioList where codigoIbge is greater than or equal to DEFAULT_CODIGO_IBGE
        defaultMunicipioShouldBeFound("codigoIbge.greaterThanOrEqual=" + DEFAULT_CODIGO_IBGE);

        // Get all the municipioList where codigoIbge is greater than or equal to UPDATED_CODIGO_IBGE
        defaultMunicipioShouldNotBeFound("codigoIbge.greaterThanOrEqual=" + UPDATED_CODIGO_IBGE);
    }

    @Test
    @Transactional
    public void getAllMunicipiosByCodigoIbgeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        municipioRepository.saveAndFlush(municipio);

        // Get all the municipioList where codigoIbge is less than or equal to DEFAULT_CODIGO_IBGE
        defaultMunicipioShouldBeFound("codigoIbge.lessThanOrEqual=" + DEFAULT_CODIGO_IBGE);

        // Get all the municipioList where codigoIbge is less than or equal to SMALLER_CODIGO_IBGE
        defaultMunicipioShouldNotBeFound("codigoIbge.lessThanOrEqual=" + SMALLER_CODIGO_IBGE);
    }

    @Test
    @Transactional
    public void getAllMunicipiosByCodigoIbgeIsLessThanSomething() throws Exception {
        // Initialize the database
        municipioRepository.saveAndFlush(municipio);

        // Get all the municipioList where codigoIbge is less than DEFAULT_CODIGO_IBGE
        defaultMunicipioShouldNotBeFound("codigoIbge.lessThan=" + DEFAULT_CODIGO_IBGE);

        // Get all the municipioList where codigoIbge is less than UPDATED_CODIGO_IBGE
        defaultMunicipioShouldBeFound("codigoIbge.lessThan=" + UPDATED_CODIGO_IBGE);
    }

    @Test
    @Transactional
    public void getAllMunicipiosByCodigoIbgeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        municipioRepository.saveAndFlush(municipio);

        // Get all the municipioList where codigoIbge is greater than DEFAULT_CODIGO_IBGE
        defaultMunicipioShouldNotBeFound("codigoIbge.greaterThan=" + DEFAULT_CODIGO_IBGE);

        // Get all the municipioList where codigoIbge is greater than SMALLER_CODIGO_IBGE
        defaultMunicipioShouldBeFound("codigoIbge.greaterThan=" + SMALLER_CODIGO_IBGE);
    }


    @Test
    @Transactional
    public void getAllMunicipiosByEstadoIsEqualToSomething() throws Exception {
        // Initialize the database
        municipioRepository.saveAndFlush(municipio);

        // Get all the municipioList where estado equals to DEFAULT_ESTADO
        defaultMunicipioShouldBeFound("estado.equals=" + DEFAULT_ESTADO);

        // Get all the municipioList where estado equals to UPDATED_ESTADO
        defaultMunicipioShouldNotBeFound("estado.equals=" + UPDATED_ESTADO);
    }

    @Test
    @Transactional
    public void getAllMunicipiosByEstadoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        municipioRepository.saveAndFlush(municipio);

        // Get all the municipioList where estado not equals to DEFAULT_ESTADO
        defaultMunicipioShouldNotBeFound("estado.notEquals=" + DEFAULT_ESTADO);

        // Get all the municipioList where estado not equals to UPDATED_ESTADO
        defaultMunicipioShouldBeFound("estado.notEquals=" + UPDATED_ESTADO);
    }

    @Test
    @Transactional
    public void getAllMunicipiosByEstadoIsInShouldWork() throws Exception {
        // Initialize the database
        municipioRepository.saveAndFlush(municipio);

        // Get all the municipioList where estado in DEFAULT_ESTADO or UPDATED_ESTADO
        defaultMunicipioShouldBeFound("estado.in=" + DEFAULT_ESTADO + "," + UPDATED_ESTADO);

        // Get all the municipioList where estado equals to UPDATED_ESTADO
        defaultMunicipioShouldNotBeFound("estado.in=" + UPDATED_ESTADO);
    }

    @Test
    @Transactional
    public void getAllMunicipiosByEstadoIsNullOrNotNull() throws Exception {
        // Initialize the database
        municipioRepository.saveAndFlush(municipio);

        // Get all the municipioList where estado is not null
        defaultMunicipioShouldBeFound("estado.specified=true");

        // Get all the municipioList where estado is null
        defaultMunicipioShouldNotBeFound("estado.specified=false");
    }
                @Test
    @Transactional
    public void getAllMunicipiosByEstadoContainsSomething() throws Exception {
        // Initialize the database
        municipioRepository.saveAndFlush(municipio);

        // Get all the municipioList where estado contains DEFAULT_ESTADO
        defaultMunicipioShouldBeFound("estado.contains=" + DEFAULT_ESTADO);

        // Get all the municipioList where estado contains UPDATED_ESTADO
        defaultMunicipioShouldNotBeFound("estado.contains=" + UPDATED_ESTADO);
    }

    @Test
    @Transactional
    public void getAllMunicipiosByEstadoNotContainsSomething() throws Exception {
        // Initialize the database
        municipioRepository.saveAndFlush(municipio);

        // Get all the municipioList where estado does not contain DEFAULT_ESTADO
        defaultMunicipioShouldNotBeFound("estado.doesNotContain=" + DEFAULT_ESTADO);

        // Get all the municipioList where estado does not contain UPDATED_ESTADO
        defaultMunicipioShouldBeFound("estado.doesNotContain=" + UPDATED_ESTADO);
    }


    @Test
    @Transactional
    public void getAllMunicipiosByNomeIsEqualToSomething() throws Exception {
        // Initialize the database
        municipioRepository.saveAndFlush(municipio);

        // Get all the municipioList where nome equals to DEFAULT_NOME
        defaultMunicipioShouldBeFound("nome.equals=" + DEFAULT_NOME);

        // Get all the municipioList where nome equals to UPDATED_NOME
        defaultMunicipioShouldNotBeFound("nome.equals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllMunicipiosByNomeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        municipioRepository.saveAndFlush(municipio);

        // Get all the municipioList where nome not equals to DEFAULT_NOME
        defaultMunicipioShouldNotBeFound("nome.notEquals=" + DEFAULT_NOME);

        // Get all the municipioList where nome not equals to UPDATED_NOME
        defaultMunicipioShouldBeFound("nome.notEquals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllMunicipiosByNomeIsInShouldWork() throws Exception {
        // Initialize the database
        municipioRepository.saveAndFlush(municipio);

        // Get all the municipioList where nome in DEFAULT_NOME or UPDATED_NOME
        defaultMunicipioShouldBeFound("nome.in=" + DEFAULT_NOME + "," + UPDATED_NOME);

        // Get all the municipioList where nome equals to UPDATED_NOME
        defaultMunicipioShouldNotBeFound("nome.in=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllMunicipiosByNomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        municipioRepository.saveAndFlush(municipio);

        // Get all the municipioList where nome is not null
        defaultMunicipioShouldBeFound("nome.specified=true");

        // Get all the municipioList where nome is null
        defaultMunicipioShouldNotBeFound("nome.specified=false");
    }
                @Test
    @Transactional
    public void getAllMunicipiosByNomeContainsSomething() throws Exception {
        // Initialize the database
        municipioRepository.saveAndFlush(municipio);

        // Get all the municipioList where nome contains DEFAULT_NOME
        defaultMunicipioShouldBeFound("nome.contains=" + DEFAULT_NOME);

        // Get all the municipioList where nome contains UPDATED_NOME
        defaultMunicipioShouldNotBeFound("nome.contains=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllMunicipiosByNomeNotContainsSomething() throws Exception {
        // Initialize the database
        municipioRepository.saveAndFlush(municipio);

        // Get all the municipioList where nome does not contain DEFAULT_NOME
        defaultMunicipioShouldNotBeFound("nome.doesNotContain=" + DEFAULT_NOME);

        // Get all the municipioList where nome does not contain UPDATED_NOME
        defaultMunicipioShouldBeFound("nome.doesNotContain=" + UPDATED_NOME);
    }


    @Test
    @Transactional
    public void getAllMunicipiosByProcessoIsEqualToSomething() throws Exception {
        // Initialize the database
        municipioRepository.saveAndFlush(municipio);
        Processo processo = ProcessoResourceIT.createEntity(em);
        em.persist(processo);
        em.flush();
        municipio.addProcesso(processo);
        municipioRepository.saveAndFlush(municipio);
        Long processoId = processo.getId();

        // Get all the municipioList where processo equals to processoId
        defaultMunicipioShouldBeFound("processoId.equals=" + processoId);

        // Get all the municipioList where processo equals to processoId + 1
        defaultMunicipioShouldNotBeFound("processoId.equals=" + (processoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMunicipioShouldBeFound(String filter) throws Exception {
        restMunicipioMockMvc.perform(get("/api/municipios?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(municipio.getId().intValue())))
            .andExpect(jsonPath("$.[*].amazoniaLegal").value(hasItem(DEFAULT_AMAZONIA_LEGAL.booleanValue())))
            .andExpect(jsonPath("$.[*].codigoIbge").value(hasItem(DEFAULT_CODIGO_IBGE)))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO)))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)));

        // Check, that the count call also returns 1
        restMunicipioMockMvc.perform(get("/api/municipios/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMunicipioShouldNotBeFound(String filter) throws Exception {
        restMunicipioMockMvc.perform(get("/api/municipios?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMunicipioMockMvc.perform(get("/api/municipios/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingMunicipio() throws Exception {
        // Get the municipio
        restMunicipioMockMvc.perform(get("/api/municipios/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMunicipio() throws Exception {
        // Initialize the database
        municipioService.save(municipio);

        int databaseSizeBeforeUpdate = municipioRepository.findAll().size();

        // Update the municipio
        Municipio updatedMunicipio = municipioRepository.findById(municipio.getId()).get();
        // Disconnect from session so that the updates on updatedMunicipio are not directly saved in db
        em.detach(updatedMunicipio);
        updatedMunicipio
            .amazoniaLegal(UPDATED_AMAZONIA_LEGAL)
            .codigoIbge(UPDATED_CODIGO_IBGE)
            .estado(UPDATED_ESTADO)
            .nome(UPDATED_NOME);

        restMunicipioMockMvc.perform(put("/api/municipios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedMunicipio)))
            .andExpect(status().isOk());

        // Validate the Municipio in the database
        List<Municipio> municipioList = municipioRepository.findAll();
        assertThat(municipioList).hasSize(databaseSizeBeforeUpdate);
        Municipio testMunicipio = municipioList.get(municipioList.size() - 1);
        assertThat(testMunicipio.isAmazoniaLegal()).isEqualTo(UPDATED_AMAZONIA_LEGAL);
        assertThat(testMunicipio.getCodigoIbge()).isEqualTo(UPDATED_CODIGO_IBGE);
        assertThat(testMunicipio.getEstado()).isEqualTo(UPDATED_ESTADO);
        assertThat(testMunicipio.getNome()).isEqualTo(UPDATED_NOME);
    }

    @Test
    @Transactional
    public void updateNonExistingMunicipio() throws Exception {
        int databaseSizeBeforeUpdate = municipioRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMunicipioMockMvc.perform(put("/api/municipios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(municipio)))
            .andExpect(status().isBadRequest());

        // Validate the Municipio in the database
        List<Municipio> municipioList = municipioRepository.findAll();
        assertThat(municipioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMunicipio() throws Exception {
        // Initialize the database
        municipioService.save(municipio);

        int databaseSizeBeforeDelete = municipioRepository.findAll().size();

        // Delete the municipio
        restMunicipioMockMvc.perform(delete("/api/municipios/{id}", municipio.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Municipio> municipioList = municipioRepository.findAll();
        assertThat(municipioList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
