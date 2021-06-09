package br.com.cidha.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.cidha.IntegrationTest;
import br.com.cidha.domain.Processo;
import br.com.cidha.domain.Quilombo;
import br.com.cidha.domain.enumeration.TipoQuilombo;
import br.com.cidha.repository.QuilomboRepository;
import br.com.cidha.service.criteria.QuilomboCriteria;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link QuilomboResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class QuilomboResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final TipoQuilombo DEFAULT_TIPO_QUILOMBO = TipoQuilombo.COMUNIDADE;
    private static final TipoQuilombo UPDATED_TIPO_QUILOMBO = TipoQuilombo.TERRITORIO;

    private static final String ENTITY_API_URL = "/api/quilombos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private QuilomboRepository quilomboRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restQuilomboMockMvc;

    private Quilombo quilombo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Quilombo createEntity(EntityManager em) {
        Quilombo quilombo = new Quilombo().nome(DEFAULT_NOME).tipoQuilombo(DEFAULT_TIPO_QUILOMBO);
        return quilombo;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Quilombo createUpdatedEntity(EntityManager em) {
        Quilombo quilombo = new Quilombo().nome(UPDATED_NOME).tipoQuilombo(UPDATED_TIPO_QUILOMBO);
        return quilombo;
    }

    @BeforeEach
    public void initTest() {
        quilombo = createEntity(em);
    }

    @Test
    @Transactional
    void createQuilombo() throws Exception {
        int databaseSizeBeforeCreate = quilomboRepository.findAll().size();
        // Create the Quilombo
        restQuilomboMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(quilombo)))
            .andExpect(status().isCreated());

        // Validate the Quilombo in the database
        List<Quilombo> quilomboList = quilomboRepository.findAll();
        assertThat(quilomboList).hasSize(databaseSizeBeforeCreate + 1);
        Quilombo testQuilombo = quilomboList.get(quilomboList.size() - 1);
        assertThat(testQuilombo.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testQuilombo.getTipoQuilombo()).isEqualTo(DEFAULT_TIPO_QUILOMBO);
    }

    @Test
    @Transactional
    void createQuilomboWithExistingId() throws Exception {
        // Create the Quilombo with an existing ID
        quilombo.setId(1L);

        int databaseSizeBeforeCreate = quilomboRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restQuilomboMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(quilombo)))
            .andExpect(status().isBadRequest());

        // Validate the Quilombo in the database
        List<Quilombo> quilomboList = quilomboRepository.findAll();
        assertThat(quilomboList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllQuilombos() throws Exception {
        // Initialize the database
        quilomboRepository.saveAndFlush(quilombo);

        // Get all the quilomboList
        restQuilomboMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(quilombo.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].tipoQuilombo").value(hasItem(DEFAULT_TIPO_QUILOMBO.toString())));
    }

    @Test
    @Transactional
    void getQuilombo() throws Exception {
        // Initialize the database
        quilomboRepository.saveAndFlush(quilombo);

        // Get the quilombo
        restQuilomboMockMvc
            .perform(get(ENTITY_API_URL_ID, quilombo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(quilombo.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.tipoQuilombo").value(DEFAULT_TIPO_QUILOMBO.toString()));
    }

    @Test
    @Transactional
    void getQuilombosByIdFiltering() throws Exception {
        // Initialize the database
        quilomboRepository.saveAndFlush(quilombo);

        Long id = quilombo.getId();

        defaultQuilomboShouldBeFound("id.equals=" + id);
        defaultQuilomboShouldNotBeFound("id.notEquals=" + id);

        defaultQuilomboShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultQuilomboShouldNotBeFound("id.greaterThan=" + id);

        defaultQuilomboShouldBeFound("id.lessThanOrEqual=" + id);
        defaultQuilomboShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllQuilombosByNomeIsEqualToSomething() throws Exception {
        // Initialize the database
        quilomboRepository.saveAndFlush(quilombo);

        // Get all the quilomboList where nome equals to DEFAULT_NOME
        defaultQuilomboShouldBeFound("nome.equals=" + DEFAULT_NOME);

        // Get all the quilomboList where nome equals to UPDATED_NOME
        defaultQuilomboShouldNotBeFound("nome.equals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllQuilombosByNomeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        quilomboRepository.saveAndFlush(quilombo);

        // Get all the quilomboList where nome not equals to DEFAULT_NOME
        defaultQuilomboShouldNotBeFound("nome.notEquals=" + DEFAULT_NOME);

        // Get all the quilomboList where nome not equals to UPDATED_NOME
        defaultQuilomboShouldBeFound("nome.notEquals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllQuilombosByNomeIsInShouldWork() throws Exception {
        // Initialize the database
        quilomboRepository.saveAndFlush(quilombo);

        // Get all the quilomboList where nome in DEFAULT_NOME or UPDATED_NOME
        defaultQuilomboShouldBeFound("nome.in=" + DEFAULT_NOME + "," + UPDATED_NOME);

        // Get all the quilomboList where nome equals to UPDATED_NOME
        defaultQuilomboShouldNotBeFound("nome.in=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllQuilombosByNomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        quilomboRepository.saveAndFlush(quilombo);

        // Get all the quilomboList where nome is not null
        defaultQuilomboShouldBeFound("nome.specified=true");

        // Get all the quilomboList where nome is null
        defaultQuilomboShouldNotBeFound("nome.specified=false");
    }

    @Test
    @Transactional
    void getAllQuilombosByNomeContainsSomething() throws Exception {
        // Initialize the database
        quilomboRepository.saveAndFlush(quilombo);

        // Get all the quilomboList where nome contains DEFAULT_NOME
        defaultQuilomboShouldBeFound("nome.contains=" + DEFAULT_NOME);

        // Get all the quilomboList where nome contains UPDATED_NOME
        defaultQuilomboShouldNotBeFound("nome.contains=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllQuilombosByNomeNotContainsSomething() throws Exception {
        // Initialize the database
        quilomboRepository.saveAndFlush(quilombo);

        // Get all the quilomboList where nome does not contain DEFAULT_NOME
        defaultQuilomboShouldNotBeFound("nome.doesNotContain=" + DEFAULT_NOME);

        // Get all the quilomboList where nome does not contain UPDATED_NOME
        defaultQuilomboShouldBeFound("nome.doesNotContain=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllQuilombosByTipoQuilomboIsEqualToSomething() throws Exception {
        // Initialize the database
        quilomboRepository.saveAndFlush(quilombo);

        // Get all the quilomboList where tipoQuilombo equals to DEFAULT_TIPO_QUILOMBO
        defaultQuilomboShouldBeFound("tipoQuilombo.equals=" + DEFAULT_TIPO_QUILOMBO);

        // Get all the quilomboList where tipoQuilombo equals to UPDATED_TIPO_QUILOMBO
        defaultQuilomboShouldNotBeFound("tipoQuilombo.equals=" + UPDATED_TIPO_QUILOMBO);
    }

    @Test
    @Transactional
    void getAllQuilombosByTipoQuilomboIsNotEqualToSomething() throws Exception {
        // Initialize the database
        quilomboRepository.saveAndFlush(quilombo);

        // Get all the quilomboList where tipoQuilombo not equals to DEFAULT_TIPO_QUILOMBO
        defaultQuilomboShouldNotBeFound("tipoQuilombo.notEquals=" + DEFAULT_TIPO_QUILOMBO);

        // Get all the quilomboList where tipoQuilombo not equals to UPDATED_TIPO_QUILOMBO
        defaultQuilomboShouldBeFound("tipoQuilombo.notEquals=" + UPDATED_TIPO_QUILOMBO);
    }

    @Test
    @Transactional
    void getAllQuilombosByTipoQuilomboIsInShouldWork() throws Exception {
        // Initialize the database
        quilomboRepository.saveAndFlush(quilombo);

        // Get all the quilomboList where tipoQuilombo in DEFAULT_TIPO_QUILOMBO or UPDATED_TIPO_QUILOMBO
        defaultQuilomboShouldBeFound("tipoQuilombo.in=" + DEFAULT_TIPO_QUILOMBO + "," + UPDATED_TIPO_QUILOMBO);

        // Get all the quilomboList where tipoQuilombo equals to UPDATED_TIPO_QUILOMBO
        defaultQuilomboShouldNotBeFound("tipoQuilombo.in=" + UPDATED_TIPO_QUILOMBO);
    }

    @Test
    @Transactional
    void getAllQuilombosByTipoQuilomboIsNullOrNotNull() throws Exception {
        // Initialize the database
        quilomboRepository.saveAndFlush(quilombo);

        // Get all the quilomboList where tipoQuilombo is not null
        defaultQuilomboShouldBeFound("tipoQuilombo.specified=true");

        // Get all the quilomboList where tipoQuilombo is null
        defaultQuilomboShouldNotBeFound("tipoQuilombo.specified=false");
    }

    @Test
    @Transactional
    void getAllQuilombosByProcessoIsEqualToSomething() throws Exception {
        // Initialize the database
        quilomboRepository.saveAndFlush(quilombo);
        Processo processo = ProcessoResourceIT.createEntity(em);
        em.persist(processo);
        em.flush();
        quilombo.addProcesso(processo);
        quilomboRepository.saveAndFlush(quilombo);
        Long processoId = processo.getId();

        // Get all the quilomboList where processo equals to processoId
        defaultQuilomboShouldBeFound("processoId.equals=" + processoId);

        // Get all the quilomboList where processo equals to (processoId + 1)
        defaultQuilomboShouldNotBeFound("processoId.equals=" + (processoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultQuilomboShouldBeFound(String filter) throws Exception {
        restQuilomboMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(quilombo.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].tipoQuilombo").value(hasItem(DEFAULT_TIPO_QUILOMBO.toString())));

        // Check, that the count call also returns 1
        restQuilomboMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultQuilomboShouldNotBeFound(String filter) throws Exception {
        restQuilomboMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restQuilomboMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingQuilombo() throws Exception {
        // Get the quilombo
        restQuilomboMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewQuilombo() throws Exception {
        // Initialize the database
        quilomboRepository.saveAndFlush(quilombo);

        int databaseSizeBeforeUpdate = quilomboRepository.findAll().size();

        // Update the quilombo
        Quilombo updatedQuilombo = quilomboRepository.findById(quilombo.getId()).get();
        // Disconnect from session so that the updates on updatedQuilombo are not directly saved in db
        em.detach(updatedQuilombo);
        updatedQuilombo.nome(UPDATED_NOME).tipoQuilombo(UPDATED_TIPO_QUILOMBO);

        restQuilomboMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedQuilombo.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedQuilombo))
            )
            .andExpect(status().isOk());

        // Validate the Quilombo in the database
        List<Quilombo> quilomboList = quilomboRepository.findAll();
        assertThat(quilomboList).hasSize(databaseSizeBeforeUpdate);
        Quilombo testQuilombo = quilomboList.get(quilomboList.size() - 1);
        assertThat(testQuilombo.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testQuilombo.getTipoQuilombo()).isEqualTo(UPDATED_TIPO_QUILOMBO);
    }

    @Test
    @Transactional
    void putNonExistingQuilombo() throws Exception {
        int databaseSizeBeforeUpdate = quilomboRepository.findAll().size();
        quilombo.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQuilomboMockMvc
            .perform(
                put(ENTITY_API_URL_ID, quilombo.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(quilombo))
            )
            .andExpect(status().isBadRequest());

        // Validate the Quilombo in the database
        List<Quilombo> quilomboList = quilomboRepository.findAll();
        assertThat(quilomboList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchQuilombo() throws Exception {
        int databaseSizeBeforeUpdate = quilomboRepository.findAll().size();
        quilombo.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQuilomboMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(quilombo))
            )
            .andExpect(status().isBadRequest());

        // Validate the Quilombo in the database
        List<Quilombo> quilomboList = quilomboRepository.findAll();
        assertThat(quilomboList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamQuilombo() throws Exception {
        int databaseSizeBeforeUpdate = quilomboRepository.findAll().size();
        quilombo.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQuilomboMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(quilombo)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Quilombo in the database
        List<Quilombo> quilomboList = quilomboRepository.findAll();
        assertThat(quilomboList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateQuilomboWithPatch() throws Exception {
        // Initialize the database
        quilomboRepository.saveAndFlush(quilombo);

        int databaseSizeBeforeUpdate = quilomboRepository.findAll().size();

        // Update the quilombo using partial update
        Quilombo partialUpdatedQuilombo = new Quilombo();
        partialUpdatedQuilombo.setId(quilombo.getId());

        partialUpdatedQuilombo.tipoQuilombo(UPDATED_TIPO_QUILOMBO);

        restQuilomboMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedQuilombo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedQuilombo))
            )
            .andExpect(status().isOk());

        // Validate the Quilombo in the database
        List<Quilombo> quilomboList = quilomboRepository.findAll();
        assertThat(quilomboList).hasSize(databaseSizeBeforeUpdate);
        Quilombo testQuilombo = quilomboList.get(quilomboList.size() - 1);
        assertThat(testQuilombo.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testQuilombo.getTipoQuilombo()).isEqualTo(UPDATED_TIPO_QUILOMBO);
    }

    @Test
    @Transactional
    void fullUpdateQuilomboWithPatch() throws Exception {
        // Initialize the database
        quilomboRepository.saveAndFlush(quilombo);

        int databaseSizeBeforeUpdate = quilomboRepository.findAll().size();

        // Update the quilombo using partial update
        Quilombo partialUpdatedQuilombo = new Quilombo();
        partialUpdatedQuilombo.setId(quilombo.getId());

        partialUpdatedQuilombo.nome(UPDATED_NOME).tipoQuilombo(UPDATED_TIPO_QUILOMBO);

        restQuilomboMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedQuilombo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedQuilombo))
            )
            .andExpect(status().isOk());

        // Validate the Quilombo in the database
        List<Quilombo> quilomboList = quilomboRepository.findAll();
        assertThat(quilomboList).hasSize(databaseSizeBeforeUpdate);
        Quilombo testQuilombo = quilomboList.get(quilomboList.size() - 1);
        assertThat(testQuilombo.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testQuilombo.getTipoQuilombo()).isEqualTo(UPDATED_TIPO_QUILOMBO);
    }

    @Test
    @Transactional
    void patchNonExistingQuilombo() throws Exception {
        int databaseSizeBeforeUpdate = quilomboRepository.findAll().size();
        quilombo.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQuilomboMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, quilombo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(quilombo))
            )
            .andExpect(status().isBadRequest());

        // Validate the Quilombo in the database
        List<Quilombo> quilomboList = quilomboRepository.findAll();
        assertThat(quilomboList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchQuilombo() throws Exception {
        int databaseSizeBeforeUpdate = quilomboRepository.findAll().size();
        quilombo.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQuilomboMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(quilombo))
            )
            .andExpect(status().isBadRequest());

        // Validate the Quilombo in the database
        List<Quilombo> quilomboList = quilomboRepository.findAll();
        assertThat(quilomboList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamQuilombo() throws Exception {
        int databaseSizeBeforeUpdate = quilomboRepository.findAll().size();
        quilombo.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQuilomboMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(quilombo)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Quilombo in the database
        List<Quilombo> quilomboList = quilomboRepository.findAll();
        assertThat(quilomboList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteQuilombo() throws Exception {
        // Initialize the database
        quilomboRepository.saveAndFlush(quilombo);

        int databaseSizeBeforeDelete = quilomboRepository.findAll().size();

        // Delete the quilombo
        restQuilomboMockMvc
            .perform(delete(ENTITY_API_URL_ID, quilombo.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Quilombo> quilomboList = quilomboRepository.findAll();
        assertThat(quilomboList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
