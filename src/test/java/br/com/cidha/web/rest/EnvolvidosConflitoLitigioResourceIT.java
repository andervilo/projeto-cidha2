package br.com.cidha.web.rest;

import br.com.cidha.CidhaApp;
import br.com.cidha.domain.EnvolvidosConflitoLitigio;
import br.com.cidha.domain.Processo;
import br.com.cidha.repository.EnvolvidosConflitoLitigioRepository;
import br.com.cidha.service.EnvolvidosConflitoLitigioService;
import br.com.cidha.service.dto.EnvolvidosConflitoLitigioCriteria;
import br.com.cidha.service.EnvolvidosConflitoLitigioQueryService;

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
 * Integration tests for the {@link EnvolvidosConflitoLitigioResource} REST controller.
 */
@SpringBootTest(classes = CidhaApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class EnvolvidosConflitoLitigioResourceIT {

    private static final Integer DEFAULT_NUMERO_INDIVIDUOS = 1;
    private static final Integer UPDATED_NUMERO_INDIVIDUOS = 2;
    private static final Integer SMALLER_NUMERO_INDIVIDUOS = 1 - 1;

    private static final String DEFAULT_FONTE_INFORMACAO_QTDE = "AAAAAAAAAA";
    private static final String UPDATED_FONTE_INFORMACAO_QTDE = "BBBBBBBBBB";

    private static final String DEFAULT_OBSERVACOES = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVACOES = "BBBBBBBBBB";

    @Autowired
    private EnvolvidosConflitoLitigioRepository envolvidosConflitoLitigioRepository;

    @Autowired
    private EnvolvidosConflitoLitigioService envolvidosConflitoLitigioService;

    @Autowired
    private EnvolvidosConflitoLitigioQueryService envolvidosConflitoLitigioQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEnvolvidosConflitoLitigioMockMvc;

    private EnvolvidosConflitoLitigio envolvidosConflitoLitigio;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EnvolvidosConflitoLitigio createEntity(EntityManager em) {
        EnvolvidosConflitoLitigio envolvidosConflitoLitigio = new EnvolvidosConflitoLitigio()
            .numeroIndividuos(DEFAULT_NUMERO_INDIVIDUOS)
            .fonteInformacaoQtde(DEFAULT_FONTE_INFORMACAO_QTDE)
            .observacoes(DEFAULT_OBSERVACOES);
        return envolvidosConflitoLitigio;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EnvolvidosConflitoLitigio createUpdatedEntity(EntityManager em) {
        EnvolvidosConflitoLitigio envolvidosConflitoLitigio = new EnvolvidosConflitoLitigio()
            .numeroIndividuos(UPDATED_NUMERO_INDIVIDUOS)
            .fonteInformacaoQtde(UPDATED_FONTE_INFORMACAO_QTDE)
            .observacoes(UPDATED_OBSERVACOES);
        return envolvidosConflitoLitigio;
    }

    @BeforeEach
    public void initTest() {
        envolvidosConflitoLitigio = createEntity(em);
    }

    @Test
    @Transactional
    public void createEnvolvidosConflitoLitigio() throws Exception {
        int databaseSizeBeforeCreate = envolvidosConflitoLitigioRepository.findAll().size();
        // Create the EnvolvidosConflitoLitigio
        restEnvolvidosConflitoLitigioMockMvc.perform(post("/api/envolvidos-conflito-litigios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(envolvidosConflitoLitigio)))
            .andExpect(status().isCreated());

        // Validate the EnvolvidosConflitoLitigio in the database
        List<EnvolvidosConflitoLitigio> envolvidosConflitoLitigioList = envolvidosConflitoLitigioRepository.findAll();
        assertThat(envolvidosConflitoLitigioList).hasSize(databaseSizeBeforeCreate + 1);
        EnvolvidosConflitoLitigio testEnvolvidosConflitoLitigio = envolvidosConflitoLitigioList.get(envolvidosConflitoLitigioList.size() - 1);
        assertThat(testEnvolvidosConflitoLitigio.getNumeroIndividuos()).isEqualTo(DEFAULT_NUMERO_INDIVIDUOS);
        assertThat(testEnvolvidosConflitoLitigio.getFonteInformacaoQtde()).isEqualTo(DEFAULT_FONTE_INFORMACAO_QTDE);
        assertThat(testEnvolvidosConflitoLitigio.getObservacoes()).isEqualTo(DEFAULT_OBSERVACOES);
    }

    @Test
    @Transactional
    public void createEnvolvidosConflitoLitigioWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = envolvidosConflitoLitigioRepository.findAll().size();

        // Create the EnvolvidosConflitoLitigio with an existing ID
        envolvidosConflitoLitigio.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEnvolvidosConflitoLitigioMockMvc.perform(post("/api/envolvidos-conflito-litigios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(envolvidosConflitoLitigio)))
            .andExpect(status().isBadRequest());

        // Validate the EnvolvidosConflitoLitigio in the database
        List<EnvolvidosConflitoLitigio> envolvidosConflitoLitigioList = envolvidosConflitoLitigioRepository.findAll();
        assertThat(envolvidosConflitoLitigioList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllEnvolvidosConflitoLitigios() throws Exception {
        // Initialize the database
        envolvidosConflitoLitigioRepository.saveAndFlush(envolvidosConflitoLitigio);

        // Get all the envolvidosConflitoLitigioList
        restEnvolvidosConflitoLitigioMockMvc.perform(get("/api/envolvidos-conflito-litigios?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(envolvidosConflitoLitigio.getId().intValue())))
            .andExpect(jsonPath("$.[*].numeroIndividuos").value(hasItem(DEFAULT_NUMERO_INDIVIDUOS)))
            .andExpect(jsonPath("$.[*].fonteInformacaoQtde").value(hasItem(DEFAULT_FONTE_INFORMACAO_QTDE.toString())))
            .andExpect(jsonPath("$.[*].observacoes").value(hasItem(DEFAULT_OBSERVACOES.toString())));
    }
    
    @Test
    @Transactional
    public void getEnvolvidosConflitoLitigio() throws Exception {
        // Initialize the database
        envolvidosConflitoLitigioRepository.saveAndFlush(envolvidosConflitoLitigio);

        // Get the envolvidosConflitoLitigio
        restEnvolvidosConflitoLitigioMockMvc.perform(get("/api/envolvidos-conflito-litigios/{id}", envolvidosConflitoLitigio.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(envolvidosConflitoLitigio.getId().intValue()))
            .andExpect(jsonPath("$.numeroIndividuos").value(DEFAULT_NUMERO_INDIVIDUOS))
            .andExpect(jsonPath("$.fonteInformacaoQtde").value(DEFAULT_FONTE_INFORMACAO_QTDE.toString()))
            .andExpect(jsonPath("$.observacoes").value(DEFAULT_OBSERVACOES.toString()));
    }


    @Test
    @Transactional
    public void getEnvolvidosConflitoLitigiosByIdFiltering() throws Exception {
        // Initialize the database
        envolvidosConflitoLitigioRepository.saveAndFlush(envolvidosConflitoLitigio);

        Long id = envolvidosConflitoLitigio.getId();

        defaultEnvolvidosConflitoLitigioShouldBeFound("id.equals=" + id);
        defaultEnvolvidosConflitoLitigioShouldNotBeFound("id.notEquals=" + id);

        defaultEnvolvidosConflitoLitigioShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultEnvolvidosConflitoLitigioShouldNotBeFound("id.greaterThan=" + id);

        defaultEnvolvidosConflitoLitigioShouldBeFound("id.lessThanOrEqual=" + id);
        defaultEnvolvidosConflitoLitigioShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllEnvolvidosConflitoLitigiosByNumeroIndividuosIsEqualToSomething() throws Exception {
        // Initialize the database
        envolvidosConflitoLitigioRepository.saveAndFlush(envolvidosConflitoLitigio);

        // Get all the envolvidosConflitoLitigioList where numeroIndividuos equals to DEFAULT_NUMERO_INDIVIDUOS
        defaultEnvolvidosConflitoLitigioShouldBeFound("numeroIndividuos.equals=" + DEFAULT_NUMERO_INDIVIDUOS);

        // Get all the envolvidosConflitoLitigioList where numeroIndividuos equals to UPDATED_NUMERO_INDIVIDUOS
        defaultEnvolvidosConflitoLitigioShouldNotBeFound("numeroIndividuos.equals=" + UPDATED_NUMERO_INDIVIDUOS);
    }

    @Test
    @Transactional
    public void getAllEnvolvidosConflitoLitigiosByNumeroIndividuosIsNotEqualToSomething() throws Exception {
        // Initialize the database
        envolvidosConflitoLitigioRepository.saveAndFlush(envolvidosConflitoLitigio);

        // Get all the envolvidosConflitoLitigioList where numeroIndividuos not equals to DEFAULT_NUMERO_INDIVIDUOS
        defaultEnvolvidosConflitoLitigioShouldNotBeFound("numeroIndividuos.notEquals=" + DEFAULT_NUMERO_INDIVIDUOS);

        // Get all the envolvidosConflitoLitigioList where numeroIndividuos not equals to UPDATED_NUMERO_INDIVIDUOS
        defaultEnvolvidosConflitoLitigioShouldBeFound("numeroIndividuos.notEquals=" + UPDATED_NUMERO_INDIVIDUOS);
    }

    @Test
    @Transactional
    public void getAllEnvolvidosConflitoLitigiosByNumeroIndividuosIsInShouldWork() throws Exception {
        // Initialize the database
        envolvidosConflitoLitigioRepository.saveAndFlush(envolvidosConflitoLitigio);

        // Get all the envolvidosConflitoLitigioList where numeroIndividuos in DEFAULT_NUMERO_INDIVIDUOS or UPDATED_NUMERO_INDIVIDUOS
        defaultEnvolvidosConflitoLitigioShouldBeFound("numeroIndividuos.in=" + DEFAULT_NUMERO_INDIVIDUOS + "," + UPDATED_NUMERO_INDIVIDUOS);

        // Get all the envolvidosConflitoLitigioList where numeroIndividuos equals to UPDATED_NUMERO_INDIVIDUOS
        defaultEnvolvidosConflitoLitigioShouldNotBeFound("numeroIndividuos.in=" + UPDATED_NUMERO_INDIVIDUOS);
    }

    @Test
    @Transactional
    public void getAllEnvolvidosConflitoLitigiosByNumeroIndividuosIsNullOrNotNull() throws Exception {
        // Initialize the database
        envolvidosConflitoLitigioRepository.saveAndFlush(envolvidosConflitoLitigio);

        // Get all the envolvidosConflitoLitigioList where numeroIndividuos is not null
        defaultEnvolvidosConflitoLitigioShouldBeFound("numeroIndividuos.specified=true");

        // Get all the envolvidosConflitoLitigioList where numeroIndividuos is null
        defaultEnvolvidosConflitoLitigioShouldNotBeFound("numeroIndividuos.specified=false");
    }

    @Test
    @Transactional
    public void getAllEnvolvidosConflitoLitigiosByNumeroIndividuosIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        envolvidosConflitoLitigioRepository.saveAndFlush(envolvidosConflitoLitigio);

        // Get all the envolvidosConflitoLitigioList where numeroIndividuos is greater than or equal to DEFAULT_NUMERO_INDIVIDUOS
        defaultEnvolvidosConflitoLitigioShouldBeFound("numeroIndividuos.greaterThanOrEqual=" + DEFAULT_NUMERO_INDIVIDUOS);

        // Get all the envolvidosConflitoLitigioList where numeroIndividuos is greater than or equal to UPDATED_NUMERO_INDIVIDUOS
        defaultEnvolvidosConflitoLitigioShouldNotBeFound("numeroIndividuos.greaterThanOrEqual=" + UPDATED_NUMERO_INDIVIDUOS);
    }

    @Test
    @Transactional
    public void getAllEnvolvidosConflitoLitigiosByNumeroIndividuosIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        envolvidosConflitoLitigioRepository.saveAndFlush(envolvidosConflitoLitigio);

        // Get all the envolvidosConflitoLitigioList where numeroIndividuos is less than or equal to DEFAULT_NUMERO_INDIVIDUOS
        defaultEnvolvidosConflitoLitigioShouldBeFound("numeroIndividuos.lessThanOrEqual=" + DEFAULT_NUMERO_INDIVIDUOS);

        // Get all the envolvidosConflitoLitigioList where numeroIndividuos is less than or equal to SMALLER_NUMERO_INDIVIDUOS
        defaultEnvolvidosConflitoLitigioShouldNotBeFound("numeroIndividuos.lessThanOrEqual=" + SMALLER_NUMERO_INDIVIDUOS);
    }

    @Test
    @Transactional
    public void getAllEnvolvidosConflitoLitigiosByNumeroIndividuosIsLessThanSomething() throws Exception {
        // Initialize the database
        envolvidosConflitoLitigioRepository.saveAndFlush(envolvidosConflitoLitigio);

        // Get all the envolvidosConflitoLitigioList where numeroIndividuos is less than DEFAULT_NUMERO_INDIVIDUOS
        defaultEnvolvidosConflitoLitigioShouldNotBeFound("numeroIndividuos.lessThan=" + DEFAULT_NUMERO_INDIVIDUOS);

        // Get all the envolvidosConflitoLitigioList where numeroIndividuos is less than UPDATED_NUMERO_INDIVIDUOS
        defaultEnvolvidosConflitoLitigioShouldBeFound("numeroIndividuos.lessThan=" + UPDATED_NUMERO_INDIVIDUOS);
    }

    @Test
    @Transactional
    public void getAllEnvolvidosConflitoLitigiosByNumeroIndividuosIsGreaterThanSomething() throws Exception {
        // Initialize the database
        envolvidosConflitoLitigioRepository.saveAndFlush(envolvidosConflitoLitigio);

        // Get all the envolvidosConflitoLitigioList where numeroIndividuos is greater than DEFAULT_NUMERO_INDIVIDUOS
        defaultEnvolvidosConflitoLitigioShouldNotBeFound("numeroIndividuos.greaterThan=" + DEFAULT_NUMERO_INDIVIDUOS);

        // Get all the envolvidosConflitoLitigioList where numeroIndividuos is greater than SMALLER_NUMERO_INDIVIDUOS
        defaultEnvolvidosConflitoLitigioShouldBeFound("numeroIndividuos.greaterThan=" + SMALLER_NUMERO_INDIVIDUOS);
    }


    @Test
    @Transactional
    public void getAllEnvolvidosConflitoLitigiosByProcessoIsEqualToSomething() throws Exception {
        // Initialize the database
        envolvidosConflitoLitigioRepository.saveAndFlush(envolvidosConflitoLitigio);
        Processo processo = ProcessoResourceIT.createEntity(em);
        em.persist(processo);
        em.flush();
        envolvidosConflitoLitigio.addProcesso(processo);
        envolvidosConflitoLitigioRepository.saveAndFlush(envolvidosConflitoLitigio);
        Long processoId = processo.getId();

        // Get all the envolvidosConflitoLitigioList where processo equals to processoId
        defaultEnvolvidosConflitoLitigioShouldBeFound("processoId.equals=" + processoId);

        // Get all the envolvidosConflitoLitigioList where processo equals to processoId + 1
        defaultEnvolvidosConflitoLitigioShouldNotBeFound("processoId.equals=" + (processoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEnvolvidosConflitoLitigioShouldBeFound(String filter) throws Exception {
        restEnvolvidosConflitoLitigioMockMvc.perform(get("/api/envolvidos-conflito-litigios?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(envolvidosConflitoLitigio.getId().intValue())))
            .andExpect(jsonPath("$.[*].numeroIndividuos").value(hasItem(DEFAULT_NUMERO_INDIVIDUOS)))
            .andExpect(jsonPath("$.[*].fonteInformacaoQtde").value(hasItem(DEFAULT_FONTE_INFORMACAO_QTDE.toString())))
            .andExpect(jsonPath("$.[*].observacoes").value(hasItem(DEFAULT_OBSERVACOES.toString())));

        // Check, that the count call also returns 1
        restEnvolvidosConflitoLitigioMockMvc.perform(get("/api/envolvidos-conflito-litigios/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEnvolvidosConflitoLitigioShouldNotBeFound(String filter) throws Exception {
        restEnvolvidosConflitoLitigioMockMvc.perform(get("/api/envolvidos-conflito-litigios?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEnvolvidosConflitoLitigioMockMvc.perform(get("/api/envolvidos-conflito-litigios/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingEnvolvidosConflitoLitigio() throws Exception {
        // Get the envolvidosConflitoLitigio
        restEnvolvidosConflitoLitigioMockMvc.perform(get("/api/envolvidos-conflito-litigios/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEnvolvidosConflitoLitigio() throws Exception {
        // Initialize the database
        envolvidosConflitoLitigioService.save(envolvidosConflitoLitigio);

        int databaseSizeBeforeUpdate = envolvidosConflitoLitigioRepository.findAll().size();

        // Update the envolvidosConflitoLitigio
        EnvolvidosConflitoLitigio updatedEnvolvidosConflitoLitigio = envolvidosConflitoLitigioRepository.findById(envolvidosConflitoLitigio.getId()).get();
        // Disconnect from session so that the updates on updatedEnvolvidosConflitoLitigio are not directly saved in db
        em.detach(updatedEnvolvidosConflitoLitigio);
        updatedEnvolvidosConflitoLitigio
            .numeroIndividuos(UPDATED_NUMERO_INDIVIDUOS)
            .fonteInformacaoQtde(UPDATED_FONTE_INFORMACAO_QTDE)
            .observacoes(UPDATED_OBSERVACOES);

        restEnvolvidosConflitoLitigioMockMvc.perform(put("/api/envolvidos-conflito-litigios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedEnvolvidosConflitoLitigio)))
            .andExpect(status().isOk());

        // Validate the EnvolvidosConflitoLitigio in the database
        List<EnvolvidosConflitoLitigio> envolvidosConflitoLitigioList = envolvidosConflitoLitigioRepository.findAll();
        assertThat(envolvidosConflitoLitigioList).hasSize(databaseSizeBeforeUpdate);
        EnvolvidosConflitoLitigio testEnvolvidosConflitoLitigio = envolvidosConflitoLitigioList.get(envolvidosConflitoLitigioList.size() - 1);
        assertThat(testEnvolvidosConflitoLitigio.getNumeroIndividuos()).isEqualTo(UPDATED_NUMERO_INDIVIDUOS);
        assertThat(testEnvolvidosConflitoLitigio.getFonteInformacaoQtde()).isEqualTo(UPDATED_FONTE_INFORMACAO_QTDE);
        assertThat(testEnvolvidosConflitoLitigio.getObservacoes()).isEqualTo(UPDATED_OBSERVACOES);
    }

    @Test
    @Transactional
    public void updateNonExistingEnvolvidosConflitoLitigio() throws Exception {
        int databaseSizeBeforeUpdate = envolvidosConflitoLitigioRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEnvolvidosConflitoLitigioMockMvc.perform(put("/api/envolvidos-conflito-litigios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(envolvidosConflitoLitigio)))
            .andExpect(status().isBadRequest());

        // Validate the EnvolvidosConflitoLitigio in the database
        List<EnvolvidosConflitoLitigio> envolvidosConflitoLitigioList = envolvidosConflitoLitigioRepository.findAll();
        assertThat(envolvidosConflitoLitigioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEnvolvidosConflitoLitigio() throws Exception {
        // Initialize the database
        envolvidosConflitoLitigioService.save(envolvidosConflitoLitigio);

        int databaseSizeBeforeDelete = envolvidosConflitoLitigioRepository.findAll().size();

        // Delete the envolvidosConflitoLitigio
        restEnvolvidosConflitoLitigioMockMvc.perform(delete("/api/envolvidos-conflito-litigios/{id}", envolvidosConflitoLitigio.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EnvolvidosConflitoLitigio> envolvidosConflitoLitigioList = envolvidosConflitoLitigioRepository.findAll();
        assertThat(envolvidosConflitoLitigioList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
