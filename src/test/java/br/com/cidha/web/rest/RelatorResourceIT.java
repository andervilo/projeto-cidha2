package br.com.cidha.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.cidha.IntegrationTest;
import br.com.cidha.domain.Processo;
import br.com.cidha.domain.Relator;
import br.com.cidha.repository.RelatorRepository;
import br.com.cidha.service.criteria.RelatorCriteria;
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
 * Integration tests for the {@link RelatorResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RelatorResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/relators";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RelatorRepository relatorRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRelatorMockMvc;

    private Relator relator;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Relator createEntity(EntityManager em) {
        Relator relator = new Relator().nome(DEFAULT_NOME);
        return relator;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Relator createUpdatedEntity(EntityManager em) {
        Relator relator = new Relator().nome(UPDATED_NOME);
        return relator;
    }

    @BeforeEach
    public void initTest() {
        relator = createEntity(em);
    }

    @Test
    @Transactional
    void createRelator() throws Exception {
        int databaseSizeBeforeCreate = relatorRepository.findAll().size();
        // Create the Relator
        restRelatorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(relator)))
            .andExpect(status().isCreated());

        // Validate the Relator in the database
        List<Relator> relatorList = relatorRepository.findAll();
        assertThat(relatorList).hasSize(databaseSizeBeforeCreate + 1);
        Relator testRelator = relatorList.get(relatorList.size() - 1);
        assertThat(testRelator.getNome()).isEqualTo(DEFAULT_NOME);
    }

    @Test
    @Transactional
    void createRelatorWithExistingId() throws Exception {
        // Create the Relator with an existing ID
        relator.setId(1L);

        int databaseSizeBeforeCreate = relatorRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRelatorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(relator)))
            .andExpect(status().isBadRequest());

        // Validate the Relator in the database
        List<Relator> relatorList = relatorRepository.findAll();
        assertThat(relatorList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllRelators() throws Exception {
        // Initialize the database
        relatorRepository.saveAndFlush(relator);

        // Get all the relatorList
        restRelatorMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(relator.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)));
    }

    @Test
    @Transactional
    void getRelator() throws Exception {
        // Initialize the database
        relatorRepository.saveAndFlush(relator);

        // Get the relator
        restRelatorMockMvc
            .perform(get(ENTITY_API_URL_ID, relator.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(relator.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME));
    }

    @Test
    @Transactional
    void getRelatorsByIdFiltering() throws Exception {
        // Initialize the database
        relatorRepository.saveAndFlush(relator);

        Long id = relator.getId();

        defaultRelatorShouldBeFound("id.equals=" + id);
        defaultRelatorShouldNotBeFound("id.notEquals=" + id);

        defaultRelatorShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultRelatorShouldNotBeFound("id.greaterThan=" + id);

        defaultRelatorShouldBeFound("id.lessThanOrEqual=" + id);
        defaultRelatorShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllRelatorsByNomeIsEqualToSomething() throws Exception {
        // Initialize the database
        relatorRepository.saveAndFlush(relator);

        // Get all the relatorList where nome equals to DEFAULT_NOME
        defaultRelatorShouldBeFound("nome.equals=" + DEFAULT_NOME);

        // Get all the relatorList where nome equals to UPDATED_NOME
        defaultRelatorShouldNotBeFound("nome.equals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllRelatorsByNomeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        relatorRepository.saveAndFlush(relator);

        // Get all the relatorList where nome not equals to DEFAULT_NOME
        defaultRelatorShouldNotBeFound("nome.notEquals=" + DEFAULT_NOME);

        // Get all the relatorList where nome not equals to UPDATED_NOME
        defaultRelatorShouldBeFound("nome.notEquals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllRelatorsByNomeIsInShouldWork() throws Exception {
        // Initialize the database
        relatorRepository.saveAndFlush(relator);

        // Get all the relatorList where nome in DEFAULT_NOME or UPDATED_NOME
        defaultRelatorShouldBeFound("nome.in=" + DEFAULT_NOME + "," + UPDATED_NOME);

        // Get all the relatorList where nome equals to UPDATED_NOME
        defaultRelatorShouldNotBeFound("nome.in=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllRelatorsByNomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        relatorRepository.saveAndFlush(relator);

        // Get all the relatorList where nome is not null
        defaultRelatorShouldBeFound("nome.specified=true");

        // Get all the relatorList where nome is null
        defaultRelatorShouldNotBeFound("nome.specified=false");
    }

    @Test
    @Transactional
    void getAllRelatorsByNomeContainsSomething() throws Exception {
        // Initialize the database
        relatorRepository.saveAndFlush(relator);

        // Get all the relatorList where nome contains DEFAULT_NOME
        defaultRelatorShouldBeFound("nome.contains=" + DEFAULT_NOME);

        // Get all the relatorList where nome contains UPDATED_NOME
        defaultRelatorShouldNotBeFound("nome.contains=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllRelatorsByNomeNotContainsSomething() throws Exception {
        // Initialize the database
        relatorRepository.saveAndFlush(relator);

        // Get all the relatorList where nome does not contain DEFAULT_NOME
        defaultRelatorShouldNotBeFound("nome.doesNotContain=" + DEFAULT_NOME);

        // Get all the relatorList where nome does not contain UPDATED_NOME
        defaultRelatorShouldBeFound("nome.doesNotContain=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllRelatorsByProcessoIsEqualToSomething() throws Exception {
        // Initialize the database
        relatorRepository.saveAndFlush(relator);
        Processo processo = ProcessoResourceIT.createEntity(em);
        em.persist(processo);
        em.flush();
        relator.addProcesso(processo);
        relatorRepository.saveAndFlush(relator);
        Long processoId = processo.getId();

        // Get all the relatorList where processo equals to processoId
        defaultRelatorShouldBeFound("processoId.equals=" + processoId);

        // Get all the relatorList where processo equals to (processoId + 1)
        defaultRelatorShouldNotBeFound("processoId.equals=" + (processoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultRelatorShouldBeFound(String filter) throws Exception {
        restRelatorMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(relator.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)));

        // Check, that the count call also returns 1
        restRelatorMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultRelatorShouldNotBeFound(String filter) throws Exception {
        restRelatorMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restRelatorMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingRelator() throws Exception {
        // Get the relator
        restRelatorMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewRelator() throws Exception {
        // Initialize the database
        relatorRepository.saveAndFlush(relator);

        int databaseSizeBeforeUpdate = relatorRepository.findAll().size();

        // Update the relator
        Relator updatedRelator = relatorRepository.findById(relator.getId()).get();
        // Disconnect from session so that the updates on updatedRelator are not directly saved in db
        em.detach(updatedRelator);
        updatedRelator.nome(UPDATED_NOME);

        restRelatorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedRelator.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedRelator))
            )
            .andExpect(status().isOk());

        // Validate the Relator in the database
        List<Relator> relatorList = relatorRepository.findAll();
        assertThat(relatorList).hasSize(databaseSizeBeforeUpdate);
        Relator testRelator = relatorList.get(relatorList.size() - 1);
        assertThat(testRelator.getNome()).isEqualTo(UPDATED_NOME);
    }

    @Test
    @Transactional
    void putNonExistingRelator() throws Exception {
        int databaseSizeBeforeUpdate = relatorRepository.findAll().size();
        relator.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRelatorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, relator.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(relator))
            )
            .andExpect(status().isBadRequest());

        // Validate the Relator in the database
        List<Relator> relatorList = relatorRepository.findAll();
        assertThat(relatorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRelator() throws Exception {
        int databaseSizeBeforeUpdate = relatorRepository.findAll().size();
        relator.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRelatorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(relator))
            )
            .andExpect(status().isBadRequest());

        // Validate the Relator in the database
        List<Relator> relatorList = relatorRepository.findAll();
        assertThat(relatorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRelator() throws Exception {
        int databaseSizeBeforeUpdate = relatorRepository.findAll().size();
        relator.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRelatorMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(relator)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Relator in the database
        List<Relator> relatorList = relatorRepository.findAll();
        assertThat(relatorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRelatorWithPatch() throws Exception {
        // Initialize the database
        relatorRepository.saveAndFlush(relator);

        int databaseSizeBeforeUpdate = relatorRepository.findAll().size();

        // Update the relator using partial update
        Relator partialUpdatedRelator = new Relator();
        partialUpdatedRelator.setId(relator.getId());

        restRelatorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRelator.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRelator))
            )
            .andExpect(status().isOk());

        // Validate the Relator in the database
        List<Relator> relatorList = relatorRepository.findAll();
        assertThat(relatorList).hasSize(databaseSizeBeforeUpdate);
        Relator testRelator = relatorList.get(relatorList.size() - 1);
        assertThat(testRelator.getNome()).isEqualTo(DEFAULT_NOME);
    }

    @Test
    @Transactional
    void fullUpdateRelatorWithPatch() throws Exception {
        // Initialize the database
        relatorRepository.saveAndFlush(relator);

        int databaseSizeBeforeUpdate = relatorRepository.findAll().size();

        // Update the relator using partial update
        Relator partialUpdatedRelator = new Relator();
        partialUpdatedRelator.setId(relator.getId());

        partialUpdatedRelator.nome(UPDATED_NOME);

        restRelatorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRelator.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRelator))
            )
            .andExpect(status().isOk());

        // Validate the Relator in the database
        List<Relator> relatorList = relatorRepository.findAll();
        assertThat(relatorList).hasSize(databaseSizeBeforeUpdate);
        Relator testRelator = relatorList.get(relatorList.size() - 1);
        assertThat(testRelator.getNome()).isEqualTo(UPDATED_NOME);
    }

    @Test
    @Transactional
    void patchNonExistingRelator() throws Exception {
        int databaseSizeBeforeUpdate = relatorRepository.findAll().size();
        relator.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRelatorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, relator.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(relator))
            )
            .andExpect(status().isBadRequest());

        // Validate the Relator in the database
        List<Relator> relatorList = relatorRepository.findAll();
        assertThat(relatorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRelator() throws Exception {
        int databaseSizeBeforeUpdate = relatorRepository.findAll().size();
        relator.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRelatorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(relator))
            )
            .andExpect(status().isBadRequest());

        // Validate the Relator in the database
        List<Relator> relatorList = relatorRepository.findAll();
        assertThat(relatorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRelator() throws Exception {
        int databaseSizeBeforeUpdate = relatorRepository.findAll().size();
        relator.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRelatorMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(relator)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Relator in the database
        List<Relator> relatorList = relatorRepository.findAll();
        assertThat(relatorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRelator() throws Exception {
        // Initialize the database
        relatorRepository.saveAndFlush(relator);

        int databaseSizeBeforeDelete = relatorRepository.findAll().size();

        // Delete the relator
        restRelatorMockMvc
            .perform(delete(ENTITY_API_URL_ID, relator.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Relator> relatorList = relatorRepository.findAll();
        assertThat(relatorList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
