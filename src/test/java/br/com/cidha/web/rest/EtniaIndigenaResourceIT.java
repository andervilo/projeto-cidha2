package br.com.cidha.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.cidha.IntegrationTest;
import br.com.cidha.domain.EtniaIndigena;
import br.com.cidha.domain.TerraIndigena;
import br.com.cidha.repository.EtniaIndigenaRepository;
import br.com.cidha.service.criteria.EtniaIndigenaCriteria;
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
 * Integration tests for the {@link EtniaIndigenaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EtniaIndigenaResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/etnia-indigenas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EtniaIndigenaRepository etniaIndigenaRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEtniaIndigenaMockMvc;

    private EtniaIndigena etniaIndigena;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EtniaIndigena createEntity(EntityManager em) {
        EtniaIndigena etniaIndigena = new EtniaIndigena().nome(DEFAULT_NOME);
        return etniaIndigena;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EtniaIndigena createUpdatedEntity(EntityManager em) {
        EtniaIndigena etniaIndigena = new EtniaIndigena().nome(UPDATED_NOME);
        return etniaIndigena;
    }

    @BeforeEach
    public void initTest() {
        etniaIndigena = createEntity(em);
    }

    @Test
    @Transactional
    void createEtniaIndigena() throws Exception {
        int databaseSizeBeforeCreate = etniaIndigenaRepository.findAll().size();
        // Create the EtniaIndigena
        restEtniaIndigenaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(etniaIndigena)))
            .andExpect(status().isCreated());

        // Validate the EtniaIndigena in the database
        List<EtniaIndigena> etniaIndigenaList = etniaIndigenaRepository.findAll();
        assertThat(etniaIndigenaList).hasSize(databaseSizeBeforeCreate + 1);
        EtniaIndigena testEtniaIndigena = etniaIndigenaList.get(etniaIndigenaList.size() - 1);
        assertThat(testEtniaIndigena.getNome()).isEqualTo(DEFAULT_NOME);
    }

    @Test
    @Transactional
    void createEtniaIndigenaWithExistingId() throws Exception {
        // Create the EtniaIndigena with an existing ID
        etniaIndigena.setId(1L);

        int databaseSizeBeforeCreate = etniaIndigenaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEtniaIndigenaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(etniaIndigena)))
            .andExpect(status().isBadRequest());

        // Validate the EtniaIndigena in the database
        List<EtniaIndigena> etniaIndigenaList = etniaIndigenaRepository.findAll();
        assertThat(etniaIndigenaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEtniaIndigenas() throws Exception {
        // Initialize the database
        etniaIndigenaRepository.saveAndFlush(etniaIndigena);

        // Get all the etniaIndigenaList
        restEtniaIndigenaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(etniaIndigena.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)));
    }

    @Test
    @Transactional
    void getEtniaIndigena() throws Exception {
        // Initialize the database
        etniaIndigenaRepository.saveAndFlush(etniaIndigena);

        // Get the etniaIndigena
        restEtniaIndigenaMockMvc
            .perform(get(ENTITY_API_URL_ID, etniaIndigena.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(etniaIndigena.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME));
    }

    @Test
    @Transactional
    void getEtniaIndigenasByIdFiltering() throws Exception {
        // Initialize the database
        etniaIndigenaRepository.saveAndFlush(etniaIndigena);

        Long id = etniaIndigena.getId();

        defaultEtniaIndigenaShouldBeFound("id.equals=" + id);
        defaultEtniaIndigenaShouldNotBeFound("id.notEquals=" + id);

        defaultEtniaIndigenaShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultEtniaIndigenaShouldNotBeFound("id.greaterThan=" + id);

        defaultEtniaIndigenaShouldBeFound("id.lessThanOrEqual=" + id);
        defaultEtniaIndigenaShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllEtniaIndigenasByNomeIsEqualToSomething() throws Exception {
        // Initialize the database
        etniaIndigenaRepository.saveAndFlush(etniaIndigena);

        // Get all the etniaIndigenaList where nome equals to DEFAULT_NOME
        defaultEtniaIndigenaShouldBeFound("nome.equals=" + DEFAULT_NOME);

        // Get all the etniaIndigenaList where nome equals to UPDATED_NOME
        defaultEtniaIndigenaShouldNotBeFound("nome.equals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllEtniaIndigenasByNomeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        etniaIndigenaRepository.saveAndFlush(etniaIndigena);

        // Get all the etniaIndigenaList where nome not equals to DEFAULT_NOME
        defaultEtniaIndigenaShouldNotBeFound("nome.notEquals=" + DEFAULT_NOME);

        // Get all the etniaIndigenaList where nome not equals to UPDATED_NOME
        defaultEtniaIndigenaShouldBeFound("nome.notEquals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllEtniaIndigenasByNomeIsInShouldWork() throws Exception {
        // Initialize the database
        etniaIndigenaRepository.saveAndFlush(etniaIndigena);

        // Get all the etniaIndigenaList where nome in DEFAULT_NOME or UPDATED_NOME
        defaultEtniaIndigenaShouldBeFound("nome.in=" + DEFAULT_NOME + "," + UPDATED_NOME);

        // Get all the etniaIndigenaList where nome equals to UPDATED_NOME
        defaultEtniaIndigenaShouldNotBeFound("nome.in=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllEtniaIndigenasByNomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        etniaIndigenaRepository.saveAndFlush(etniaIndigena);

        // Get all the etniaIndigenaList where nome is not null
        defaultEtniaIndigenaShouldBeFound("nome.specified=true");

        // Get all the etniaIndigenaList where nome is null
        defaultEtniaIndigenaShouldNotBeFound("nome.specified=false");
    }

    @Test
    @Transactional
    void getAllEtniaIndigenasByNomeContainsSomething() throws Exception {
        // Initialize the database
        etniaIndigenaRepository.saveAndFlush(etniaIndigena);

        // Get all the etniaIndigenaList where nome contains DEFAULT_NOME
        defaultEtniaIndigenaShouldBeFound("nome.contains=" + DEFAULT_NOME);

        // Get all the etniaIndigenaList where nome contains UPDATED_NOME
        defaultEtniaIndigenaShouldNotBeFound("nome.contains=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllEtniaIndigenasByNomeNotContainsSomething() throws Exception {
        // Initialize the database
        etniaIndigenaRepository.saveAndFlush(etniaIndigena);

        // Get all the etniaIndigenaList where nome does not contain DEFAULT_NOME
        defaultEtniaIndigenaShouldNotBeFound("nome.doesNotContain=" + DEFAULT_NOME);

        // Get all the etniaIndigenaList where nome does not contain UPDATED_NOME
        defaultEtniaIndigenaShouldBeFound("nome.doesNotContain=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllEtniaIndigenasByTerraIndigenaIsEqualToSomething() throws Exception {
        // Initialize the database
        etniaIndigenaRepository.saveAndFlush(etniaIndigena);
        TerraIndigena terraIndigena = TerraIndigenaResourceIT.createEntity(em);
        em.persist(terraIndigena);
        em.flush();
        etniaIndigena.addTerraIndigena(terraIndigena);
        etniaIndigenaRepository.saveAndFlush(etniaIndigena);
        Long terraIndigenaId = terraIndigena.getId();

        // Get all the etniaIndigenaList where terraIndigena equals to terraIndigenaId
        defaultEtniaIndigenaShouldBeFound("terraIndigenaId.equals=" + terraIndigenaId);

        // Get all the etniaIndigenaList where terraIndigena equals to (terraIndigenaId + 1)
        defaultEtniaIndigenaShouldNotBeFound("terraIndigenaId.equals=" + (terraIndigenaId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEtniaIndigenaShouldBeFound(String filter) throws Exception {
        restEtniaIndigenaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(etniaIndigena.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)));

        // Check, that the count call also returns 1
        restEtniaIndigenaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEtniaIndigenaShouldNotBeFound(String filter) throws Exception {
        restEtniaIndigenaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEtniaIndigenaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingEtniaIndigena() throws Exception {
        // Get the etniaIndigena
        restEtniaIndigenaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewEtniaIndigena() throws Exception {
        // Initialize the database
        etniaIndigenaRepository.saveAndFlush(etniaIndigena);

        int databaseSizeBeforeUpdate = etniaIndigenaRepository.findAll().size();

        // Update the etniaIndigena
        EtniaIndigena updatedEtniaIndigena = etniaIndigenaRepository.findById(etniaIndigena.getId()).get();
        // Disconnect from session so that the updates on updatedEtniaIndigena are not directly saved in db
        em.detach(updatedEtniaIndigena);
        updatedEtniaIndigena.nome(UPDATED_NOME);

        restEtniaIndigenaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedEtniaIndigena.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedEtniaIndigena))
            )
            .andExpect(status().isOk());

        // Validate the EtniaIndigena in the database
        List<EtniaIndigena> etniaIndigenaList = etniaIndigenaRepository.findAll();
        assertThat(etniaIndigenaList).hasSize(databaseSizeBeforeUpdate);
        EtniaIndigena testEtniaIndigena = etniaIndigenaList.get(etniaIndigenaList.size() - 1);
        assertThat(testEtniaIndigena.getNome()).isEqualTo(UPDATED_NOME);
    }

    @Test
    @Transactional
    void putNonExistingEtniaIndigena() throws Exception {
        int databaseSizeBeforeUpdate = etniaIndigenaRepository.findAll().size();
        etniaIndigena.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEtniaIndigenaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, etniaIndigena.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(etniaIndigena))
            )
            .andExpect(status().isBadRequest());

        // Validate the EtniaIndigena in the database
        List<EtniaIndigena> etniaIndigenaList = etniaIndigenaRepository.findAll();
        assertThat(etniaIndigenaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEtniaIndigena() throws Exception {
        int databaseSizeBeforeUpdate = etniaIndigenaRepository.findAll().size();
        etniaIndigena.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEtniaIndigenaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(etniaIndigena))
            )
            .andExpect(status().isBadRequest());

        // Validate the EtniaIndigena in the database
        List<EtniaIndigena> etniaIndigenaList = etniaIndigenaRepository.findAll();
        assertThat(etniaIndigenaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEtniaIndigena() throws Exception {
        int databaseSizeBeforeUpdate = etniaIndigenaRepository.findAll().size();
        etniaIndigena.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEtniaIndigenaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(etniaIndigena)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the EtniaIndigena in the database
        List<EtniaIndigena> etniaIndigenaList = etniaIndigenaRepository.findAll();
        assertThat(etniaIndigenaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEtniaIndigenaWithPatch() throws Exception {
        // Initialize the database
        etniaIndigenaRepository.saveAndFlush(etniaIndigena);

        int databaseSizeBeforeUpdate = etniaIndigenaRepository.findAll().size();

        // Update the etniaIndigena using partial update
        EtniaIndigena partialUpdatedEtniaIndigena = new EtniaIndigena();
        partialUpdatedEtniaIndigena.setId(etniaIndigena.getId());

        restEtniaIndigenaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEtniaIndigena.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEtniaIndigena))
            )
            .andExpect(status().isOk());

        // Validate the EtniaIndigena in the database
        List<EtniaIndigena> etniaIndigenaList = etniaIndigenaRepository.findAll();
        assertThat(etniaIndigenaList).hasSize(databaseSizeBeforeUpdate);
        EtniaIndigena testEtniaIndigena = etniaIndigenaList.get(etniaIndigenaList.size() - 1);
        assertThat(testEtniaIndigena.getNome()).isEqualTo(DEFAULT_NOME);
    }

    @Test
    @Transactional
    void fullUpdateEtniaIndigenaWithPatch() throws Exception {
        // Initialize the database
        etniaIndigenaRepository.saveAndFlush(etniaIndigena);

        int databaseSizeBeforeUpdate = etniaIndigenaRepository.findAll().size();

        // Update the etniaIndigena using partial update
        EtniaIndigena partialUpdatedEtniaIndigena = new EtniaIndigena();
        partialUpdatedEtniaIndigena.setId(etniaIndigena.getId());

        partialUpdatedEtniaIndigena.nome(UPDATED_NOME);

        restEtniaIndigenaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEtniaIndigena.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEtniaIndigena))
            )
            .andExpect(status().isOk());

        // Validate the EtniaIndigena in the database
        List<EtniaIndigena> etniaIndigenaList = etniaIndigenaRepository.findAll();
        assertThat(etniaIndigenaList).hasSize(databaseSizeBeforeUpdate);
        EtniaIndigena testEtniaIndigena = etniaIndigenaList.get(etniaIndigenaList.size() - 1);
        assertThat(testEtniaIndigena.getNome()).isEqualTo(UPDATED_NOME);
    }

    @Test
    @Transactional
    void patchNonExistingEtniaIndigena() throws Exception {
        int databaseSizeBeforeUpdate = etniaIndigenaRepository.findAll().size();
        etniaIndigena.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEtniaIndigenaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, etniaIndigena.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(etniaIndigena))
            )
            .andExpect(status().isBadRequest());

        // Validate the EtniaIndigena in the database
        List<EtniaIndigena> etniaIndigenaList = etniaIndigenaRepository.findAll();
        assertThat(etniaIndigenaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEtniaIndigena() throws Exception {
        int databaseSizeBeforeUpdate = etniaIndigenaRepository.findAll().size();
        etniaIndigena.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEtniaIndigenaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(etniaIndigena))
            )
            .andExpect(status().isBadRequest());

        // Validate the EtniaIndigena in the database
        List<EtniaIndigena> etniaIndigenaList = etniaIndigenaRepository.findAll();
        assertThat(etniaIndigenaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEtniaIndigena() throws Exception {
        int databaseSizeBeforeUpdate = etniaIndigenaRepository.findAll().size();
        etniaIndigena.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEtniaIndigenaMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(etniaIndigena))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EtniaIndigena in the database
        List<EtniaIndigena> etniaIndigenaList = etniaIndigenaRepository.findAll();
        assertThat(etniaIndigenaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEtniaIndigena() throws Exception {
        // Initialize the database
        etniaIndigenaRepository.saveAndFlush(etniaIndigena);

        int databaseSizeBeforeDelete = etniaIndigenaRepository.findAll().size();

        // Delete the etniaIndigena
        restEtniaIndigenaMockMvc
            .perform(delete(ENTITY_API_URL_ID, etniaIndigena.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EtniaIndigena> etniaIndigenaList = etniaIndigenaRepository.findAll();
        assertThat(etniaIndigenaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
