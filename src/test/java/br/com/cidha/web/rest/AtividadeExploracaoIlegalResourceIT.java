package br.com.cidha.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.cidha.IntegrationTest;
import br.com.cidha.domain.AtividadeExploracaoIlegal;
import br.com.cidha.domain.Processo;
import br.com.cidha.repository.AtividadeExploracaoIlegalRepository;
import br.com.cidha.service.criteria.AtividadeExploracaoIlegalCriteria;
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
 * Integration tests for the {@link AtividadeExploracaoIlegalResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AtividadeExploracaoIlegalResourceIT {

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/atividade-exploracao-ilegals";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AtividadeExploracaoIlegalRepository atividadeExploracaoIlegalRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAtividadeExploracaoIlegalMockMvc;

    private AtividadeExploracaoIlegal atividadeExploracaoIlegal;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AtividadeExploracaoIlegal createEntity(EntityManager em) {
        AtividadeExploracaoIlegal atividadeExploracaoIlegal = new AtividadeExploracaoIlegal().descricao(DEFAULT_DESCRICAO);
        return atividadeExploracaoIlegal;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AtividadeExploracaoIlegal createUpdatedEntity(EntityManager em) {
        AtividadeExploracaoIlegal atividadeExploracaoIlegal = new AtividadeExploracaoIlegal().descricao(UPDATED_DESCRICAO);
        return atividadeExploracaoIlegal;
    }

    @BeforeEach
    public void initTest() {
        atividadeExploracaoIlegal = createEntity(em);
    }

    @Test
    @Transactional
    void createAtividadeExploracaoIlegal() throws Exception {
        int databaseSizeBeforeCreate = atividadeExploracaoIlegalRepository.findAll().size();
        // Create the AtividadeExploracaoIlegal
        restAtividadeExploracaoIlegalMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(atividadeExploracaoIlegal))
            )
            .andExpect(status().isCreated());

        // Validate the AtividadeExploracaoIlegal in the database
        List<AtividadeExploracaoIlegal> atividadeExploracaoIlegalList = atividadeExploracaoIlegalRepository.findAll();
        assertThat(atividadeExploracaoIlegalList).hasSize(databaseSizeBeforeCreate + 1);
        AtividadeExploracaoIlegal testAtividadeExploracaoIlegal = atividadeExploracaoIlegalList.get(
            atividadeExploracaoIlegalList.size() - 1
        );
        assertThat(testAtividadeExploracaoIlegal.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
    }

    @Test
    @Transactional
    void createAtividadeExploracaoIlegalWithExistingId() throws Exception {
        // Create the AtividadeExploracaoIlegal with an existing ID
        atividadeExploracaoIlegal.setId(1L);

        int databaseSizeBeforeCreate = atividadeExploracaoIlegalRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAtividadeExploracaoIlegalMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(atividadeExploracaoIlegal))
            )
            .andExpect(status().isBadRequest());

        // Validate the AtividadeExploracaoIlegal in the database
        List<AtividadeExploracaoIlegal> atividadeExploracaoIlegalList = atividadeExploracaoIlegalRepository.findAll();
        assertThat(atividadeExploracaoIlegalList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAtividadeExploracaoIlegals() throws Exception {
        // Initialize the database
        atividadeExploracaoIlegalRepository.saveAndFlush(atividadeExploracaoIlegal);

        // Get all the atividadeExploracaoIlegalList
        restAtividadeExploracaoIlegalMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(atividadeExploracaoIlegal.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)));
    }

    @Test
    @Transactional
    void getAtividadeExploracaoIlegal() throws Exception {
        // Initialize the database
        atividadeExploracaoIlegalRepository.saveAndFlush(atividadeExploracaoIlegal);

        // Get the atividadeExploracaoIlegal
        restAtividadeExploracaoIlegalMockMvc
            .perform(get(ENTITY_API_URL_ID, atividadeExploracaoIlegal.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(atividadeExploracaoIlegal.getId().intValue()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO));
    }

    @Test
    @Transactional
    void getAtividadeExploracaoIlegalsByIdFiltering() throws Exception {
        // Initialize the database
        atividadeExploracaoIlegalRepository.saveAndFlush(atividadeExploracaoIlegal);

        Long id = atividadeExploracaoIlegal.getId();

        defaultAtividadeExploracaoIlegalShouldBeFound("id.equals=" + id);
        defaultAtividadeExploracaoIlegalShouldNotBeFound("id.notEquals=" + id);

        defaultAtividadeExploracaoIlegalShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAtividadeExploracaoIlegalShouldNotBeFound("id.greaterThan=" + id);

        defaultAtividadeExploracaoIlegalShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAtividadeExploracaoIlegalShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllAtividadeExploracaoIlegalsByDescricaoIsEqualToSomething() throws Exception {
        // Initialize the database
        atividadeExploracaoIlegalRepository.saveAndFlush(atividadeExploracaoIlegal);

        // Get all the atividadeExploracaoIlegalList where descricao equals to DEFAULT_DESCRICAO
        defaultAtividadeExploracaoIlegalShouldBeFound("descricao.equals=" + DEFAULT_DESCRICAO);

        // Get all the atividadeExploracaoIlegalList where descricao equals to UPDATED_DESCRICAO
        defaultAtividadeExploracaoIlegalShouldNotBeFound("descricao.equals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllAtividadeExploracaoIlegalsByDescricaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        atividadeExploracaoIlegalRepository.saveAndFlush(atividadeExploracaoIlegal);

        // Get all the atividadeExploracaoIlegalList where descricao not equals to DEFAULT_DESCRICAO
        defaultAtividadeExploracaoIlegalShouldNotBeFound("descricao.notEquals=" + DEFAULT_DESCRICAO);

        // Get all the atividadeExploracaoIlegalList where descricao not equals to UPDATED_DESCRICAO
        defaultAtividadeExploracaoIlegalShouldBeFound("descricao.notEquals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllAtividadeExploracaoIlegalsByDescricaoIsInShouldWork() throws Exception {
        // Initialize the database
        atividadeExploracaoIlegalRepository.saveAndFlush(atividadeExploracaoIlegal);

        // Get all the atividadeExploracaoIlegalList where descricao in DEFAULT_DESCRICAO or UPDATED_DESCRICAO
        defaultAtividadeExploracaoIlegalShouldBeFound("descricao.in=" + DEFAULT_DESCRICAO + "," + UPDATED_DESCRICAO);

        // Get all the atividadeExploracaoIlegalList where descricao equals to UPDATED_DESCRICAO
        defaultAtividadeExploracaoIlegalShouldNotBeFound("descricao.in=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllAtividadeExploracaoIlegalsByDescricaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        atividadeExploracaoIlegalRepository.saveAndFlush(atividadeExploracaoIlegal);

        // Get all the atividadeExploracaoIlegalList where descricao is not null
        defaultAtividadeExploracaoIlegalShouldBeFound("descricao.specified=true");

        // Get all the atividadeExploracaoIlegalList where descricao is null
        defaultAtividadeExploracaoIlegalShouldNotBeFound("descricao.specified=false");
    }

    @Test
    @Transactional
    void getAllAtividadeExploracaoIlegalsByDescricaoContainsSomething() throws Exception {
        // Initialize the database
        atividadeExploracaoIlegalRepository.saveAndFlush(atividadeExploracaoIlegal);

        // Get all the atividadeExploracaoIlegalList where descricao contains DEFAULT_DESCRICAO
        defaultAtividadeExploracaoIlegalShouldBeFound("descricao.contains=" + DEFAULT_DESCRICAO);

        // Get all the atividadeExploracaoIlegalList where descricao contains UPDATED_DESCRICAO
        defaultAtividadeExploracaoIlegalShouldNotBeFound("descricao.contains=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllAtividadeExploracaoIlegalsByDescricaoNotContainsSomething() throws Exception {
        // Initialize the database
        atividadeExploracaoIlegalRepository.saveAndFlush(atividadeExploracaoIlegal);

        // Get all the atividadeExploracaoIlegalList where descricao does not contain DEFAULT_DESCRICAO
        defaultAtividadeExploracaoIlegalShouldNotBeFound("descricao.doesNotContain=" + DEFAULT_DESCRICAO);

        // Get all the atividadeExploracaoIlegalList where descricao does not contain UPDATED_DESCRICAO
        defaultAtividadeExploracaoIlegalShouldBeFound("descricao.doesNotContain=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllAtividadeExploracaoIlegalsByProcessoIsEqualToSomething() throws Exception {
        // Initialize the database
        atividadeExploracaoIlegalRepository.saveAndFlush(atividadeExploracaoIlegal);
        Processo processo = ProcessoResourceIT.createEntity(em);
        em.persist(processo);
        em.flush();
        atividadeExploracaoIlegal.addProcesso(processo);
        atividadeExploracaoIlegalRepository.saveAndFlush(atividadeExploracaoIlegal);
        Long processoId = processo.getId();

        // Get all the atividadeExploracaoIlegalList where processo equals to processoId
        defaultAtividadeExploracaoIlegalShouldBeFound("processoId.equals=" + processoId);

        // Get all the atividadeExploracaoIlegalList where processo equals to (processoId + 1)
        defaultAtividadeExploracaoIlegalShouldNotBeFound("processoId.equals=" + (processoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAtividadeExploracaoIlegalShouldBeFound(String filter) throws Exception {
        restAtividadeExploracaoIlegalMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(atividadeExploracaoIlegal.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)));

        // Check, that the count call also returns 1
        restAtividadeExploracaoIlegalMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAtividadeExploracaoIlegalShouldNotBeFound(String filter) throws Exception {
        restAtividadeExploracaoIlegalMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAtividadeExploracaoIlegalMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingAtividadeExploracaoIlegal() throws Exception {
        // Get the atividadeExploracaoIlegal
        restAtividadeExploracaoIlegalMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewAtividadeExploracaoIlegal() throws Exception {
        // Initialize the database
        atividadeExploracaoIlegalRepository.saveAndFlush(atividadeExploracaoIlegal);

        int databaseSizeBeforeUpdate = atividadeExploracaoIlegalRepository.findAll().size();

        // Update the atividadeExploracaoIlegal
        AtividadeExploracaoIlegal updatedAtividadeExploracaoIlegal = atividadeExploracaoIlegalRepository
            .findById(atividadeExploracaoIlegal.getId())
            .get();
        // Disconnect from session so that the updates on updatedAtividadeExploracaoIlegal are not directly saved in db
        em.detach(updatedAtividadeExploracaoIlegal);
        updatedAtividadeExploracaoIlegal.descricao(UPDATED_DESCRICAO);

        restAtividadeExploracaoIlegalMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAtividadeExploracaoIlegal.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedAtividadeExploracaoIlegal))
            )
            .andExpect(status().isOk());

        // Validate the AtividadeExploracaoIlegal in the database
        List<AtividadeExploracaoIlegal> atividadeExploracaoIlegalList = atividadeExploracaoIlegalRepository.findAll();
        assertThat(atividadeExploracaoIlegalList).hasSize(databaseSizeBeforeUpdate);
        AtividadeExploracaoIlegal testAtividadeExploracaoIlegal = atividadeExploracaoIlegalList.get(
            atividadeExploracaoIlegalList.size() - 1
        );
        assertThat(testAtividadeExploracaoIlegal.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void putNonExistingAtividadeExploracaoIlegal() throws Exception {
        int databaseSizeBeforeUpdate = atividadeExploracaoIlegalRepository.findAll().size();
        atividadeExploracaoIlegal.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAtividadeExploracaoIlegalMockMvc
            .perform(
                put(ENTITY_API_URL_ID, atividadeExploracaoIlegal.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(atividadeExploracaoIlegal))
            )
            .andExpect(status().isBadRequest());

        // Validate the AtividadeExploracaoIlegal in the database
        List<AtividadeExploracaoIlegal> atividadeExploracaoIlegalList = atividadeExploracaoIlegalRepository.findAll();
        assertThat(atividadeExploracaoIlegalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAtividadeExploracaoIlegal() throws Exception {
        int databaseSizeBeforeUpdate = atividadeExploracaoIlegalRepository.findAll().size();
        atividadeExploracaoIlegal.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAtividadeExploracaoIlegalMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(atividadeExploracaoIlegal))
            )
            .andExpect(status().isBadRequest());

        // Validate the AtividadeExploracaoIlegal in the database
        List<AtividadeExploracaoIlegal> atividadeExploracaoIlegalList = atividadeExploracaoIlegalRepository.findAll();
        assertThat(atividadeExploracaoIlegalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAtividadeExploracaoIlegal() throws Exception {
        int databaseSizeBeforeUpdate = atividadeExploracaoIlegalRepository.findAll().size();
        atividadeExploracaoIlegal.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAtividadeExploracaoIlegalMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(atividadeExploracaoIlegal))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AtividadeExploracaoIlegal in the database
        List<AtividadeExploracaoIlegal> atividadeExploracaoIlegalList = atividadeExploracaoIlegalRepository.findAll();
        assertThat(atividadeExploracaoIlegalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAtividadeExploracaoIlegalWithPatch() throws Exception {
        // Initialize the database
        atividadeExploracaoIlegalRepository.saveAndFlush(atividadeExploracaoIlegal);

        int databaseSizeBeforeUpdate = atividadeExploracaoIlegalRepository.findAll().size();

        // Update the atividadeExploracaoIlegal using partial update
        AtividadeExploracaoIlegal partialUpdatedAtividadeExploracaoIlegal = new AtividadeExploracaoIlegal();
        partialUpdatedAtividadeExploracaoIlegal.setId(atividadeExploracaoIlegal.getId());

        restAtividadeExploracaoIlegalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAtividadeExploracaoIlegal.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAtividadeExploracaoIlegal))
            )
            .andExpect(status().isOk());

        // Validate the AtividadeExploracaoIlegal in the database
        List<AtividadeExploracaoIlegal> atividadeExploracaoIlegalList = atividadeExploracaoIlegalRepository.findAll();
        assertThat(atividadeExploracaoIlegalList).hasSize(databaseSizeBeforeUpdate);
        AtividadeExploracaoIlegal testAtividadeExploracaoIlegal = atividadeExploracaoIlegalList.get(
            atividadeExploracaoIlegalList.size() - 1
        );
        assertThat(testAtividadeExploracaoIlegal.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
    }

    @Test
    @Transactional
    void fullUpdateAtividadeExploracaoIlegalWithPatch() throws Exception {
        // Initialize the database
        atividadeExploracaoIlegalRepository.saveAndFlush(atividadeExploracaoIlegal);

        int databaseSizeBeforeUpdate = atividadeExploracaoIlegalRepository.findAll().size();

        // Update the atividadeExploracaoIlegal using partial update
        AtividadeExploracaoIlegal partialUpdatedAtividadeExploracaoIlegal = new AtividadeExploracaoIlegal();
        partialUpdatedAtividadeExploracaoIlegal.setId(atividadeExploracaoIlegal.getId());

        partialUpdatedAtividadeExploracaoIlegal.descricao(UPDATED_DESCRICAO);

        restAtividadeExploracaoIlegalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAtividadeExploracaoIlegal.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAtividadeExploracaoIlegal))
            )
            .andExpect(status().isOk());

        // Validate the AtividadeExploracaoIlegal in the database
        List<AtividadeExploracaoIlegal> atividadeExploracaoIlegalList = atividadeExploracaoIlegalRepository.findAll();
        assertThat(atividadeExploracaoIlegalList).hasSize(databaseSizeBeforeUpdate);
        AtividadeExploracaoIlegal testAtividadeExploracaoIlegal = atividadeExploracaoIlegalList.get(
            atividadeExploracaoIlegalList.size() - 1
        );
        assertThat(testAtividadeExploracaoIlegal.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void patchNonExistingAtividadeExploracaoIlegal() throws Exception {
        int databaseSizeBeforeUpdate = atividadeExploracaoIlegalRepository.findAll().size();
        atividadeExploracaoIlegal.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAtividadeExploracaoIlegalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, atividadeExploracaoIlegal.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(atividadeExploracaoIlegal))
            )
            .andExpect(status().isBadRequest());

        // Validate the AtividadeExploracaoIlegal in the database
        List<AtividadeExploracaoIlegal> atividadeExploracaoIlegalList = atividadeExploracaoIlegalRepository.findAll();
        assertThat(atividadeExploracaoIlegalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAtividadeExploracaoIlegal() throws Exception {
        int databaseSizeBeforeUpdate = atividadeExploracaoIlegalRepository.findAll().size();
        atividadeExploracaoIlegal.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAtividadeExploracaoIlegalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(atividadeExploracaoIlegal))
            )
            .andExpect(status().isBadRequest());

        // Validate the AtividadeExploracaoIlegal in the database
        List<AtividadeExploracaoIlegal> atividadeExploracaoIlegalList = atividadeExploracaoIlegalRepository.findAll();
        assertThat(atividadeExploracaoIlegalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAtividadeExploracaoIlegal() throws Exception {
        int databaseSizeBeforeUpdate = atividadeExploracaoIlegalRepository.findAll().size();
        atividadeExploracaoIlegal.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAtividadeExploracaoIlegalMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(atividadeExploracaoIlegal))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AtividadeExploracaoIlegal in the database
        List<AtividadeExploracaoIlegal> atividadeExploracaoIlegalList = atividadeExploracaoIlegalRepository.findAll();
        assertThat(atividadeExploracaoIlegalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAtividadeExploracaoIlegal() throws Exception {
        // Initialize the database
        atividadeExploracaoIlegalRepository.saveAndFlush(atividadeExploracaoIlegal);

        int databaseSizeBeforeDelete = atividadeExploracaoIlegalRepository.findAll().size();

        // Delete the atividadeExploracaoIlegal
        restAtividadeExploracaoIlegalMockMvc
            .perform(delete(ENTITY_API_URL_ID, atividadeExploracaoIlegal.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AtividadeExploracaoIlegal> atividadeExploracaoIlegalList = atividadeExploracaoIlegalRepository.findAll();
        assertThat(atividadeExploracaoIlegalList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
