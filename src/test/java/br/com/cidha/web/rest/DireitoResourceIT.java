package br.com.cidha.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.cidha.IntegrationTest;
import br.com.cidha.domain.Direito;
import br.com.cidha.domain.ProcessoConflito;
import br.com.cidha.repository.DireitoRepository;
import br.com.cidha.service.criteria.DireitoCriteria;
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
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link DireitoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DireitoResourceIT {

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/direitos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DireitoRepository direitoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDireitoMockMvc;

    private Direito direito;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Direito createEntity(EntityManager em) {
        Direito direito = new Direito().descricao(DEFAULT_DESCRICAO);
        return direito;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Direito createUpdatedEntity(EntityManager em) {
        Direito direito = new Direito().descricao(UPDATED_DESCRICAO);
        return direito;
    }

    @BeforeEach
    public void initTest() {
        direito = createEntity(em);
    }

    @Test
    @Transactional
    void createDireito() throws Exception {
        int databaseSizeBeforeCreate = direitoRepository.findAll().size();
        // Create the Direito
        restDireitoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(direito)))
            .andExpect(status().isCreated());

        // Validate the Direito in the database
        List<Direito> direitoList = direitoRepository.findAll();
        assertThat(direitoList).hasSize(databaseSizeBeforeCreate + 1);
        Direito testDireito = direitoList.get(direitoList.size() - 1);
        assertThat(testDireito.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
    }

    @Test
    @Transactional
    void createDireitoWithExistingId() throws Exception {
        // Create the Direito with an existing ID
        direito.setId(1L);

        int databaseSizeBeforeCreate = direitoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDireitoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(direito)))
            .andExpect(status().isBadRequest());

        // Validate the Direito in the database
        List<Direito> direitoList = direitoRepository.findAll();
        assertThat(direitoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDireitos() throws Exception {
        // Initialize the database
        direitoRepository.saveAndFlush(direito);

        // Get all the direitoList
        restDireitoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(direito.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())));
    }

    @Test
    @Transactional
    void getDireito() throws Exception {
        // Initialize the database
        direitoRepository.saveAndFlush(direito);

        // Get the direito
        restDireitoMockMvc
            .perform(get(ENTITY_API_URL_ID, direito.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(direito.getId().intValue()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()));
    }

    @Test
    @Transactional
    void getDireitosByIdFiltering() throws Exception {
        // Initialize the database
        direitoRepository.saveAndFlush(direito);

        Long id = direito.getId();

        defaultDireitoShouldBeFound("id.equals=" + id);
        defaultDireitoShouldNotBeFound("id.notEquals=" + id);

        defaultDireitoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDireitoShouldNotBeFound("id.greaterThan=" + id);

        defaultDireitoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDireitoShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllDireitosByProcessoConflitoIsEqualToSomething() throws Exception {
        // Initialize the database
        direitoRepository.saveAndFlush(direito);
        ProcessoConflito processoConflito = ProcessoConflitoResourceIT.createEntity(em);
        em.persist(processoConflito);
        em.flush();
        direito.addProcessoConflito(processoConflito);
        direitoRepository.saveAndFlush(direito);
        Long processoConflitoId = processoConflito.getId();

        // Get all the direitoList where processoConflito equals to processoConflitoId
        defaultDireitoShouldBeFound("processoConflitoId.equals=" + processoConflitoId);

        // Get all the direitoList where processoConflito equals to (processoConflitoId + 1)
        defaultDireitoShouldNotBeFound("processoConflitoId.equals=" + (processoConflitoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDireitoShouldBeFound(String filter) throws Exception {
        restDireitoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(direito.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())));

        // Check, that the count call also returns 1
        restDireitoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDireitoShouldNotBeFound(String filter) throws Exception {
        restDireitoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDireitoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingDireito() throws Exception {
        // Get the direito
        restDireitoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewDireito() throws Exception {
        // Initialize the database
        direitoRepository.saveAndFlush(direito);

        int databaseSizeBeforeUpdate = direitoRepository.findAll().size();

        // Update the direito
        Direito updatedDireito = direitoRepository.findById(direito.getId()).get();
        // Disconnect from session so that the updates on updatedDireito are not directly saved in db
        em.detach(updatedDireito);
        updatedDireito.descricao(UPDATED_DESCRICAO);

        restDireitoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDireito.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedDireito))
            )
            .andExpect(status().isOk());

        // Validate the Direito in the database
        List<Direito> direitoList = direitoRepository.findAll();
        assertThat(direitoList).hasSize(databaseSizeBeforeUpdate);
        Direito testDireito = direitoList.get(direitoList.size() - 1);
        assertThat(testDireito.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void putNonExistingDireito() throws Exception {
        int databaseSizeBeforeUpdate = direitoRepository.findAll().size();
        direito.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDireitoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, direito.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(direito))
            )
            .andExpect(status().isBadRequest());

        // Validate the Direito in the database
        List<Direito> direitoList = direitoRepository.findAll();
        assertThat(direitoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDireito() throws Exception {
        int databaseSizeBeforeUpdate = direitoRepository.findAll().size();
        direito.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDireitoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(direito))
            )
            .andExpect(status().isBadRequest());

        // Validate the Direito in the database
        List<Direito> direitoList = direitoRepository.findAll();
        assertThat(direitoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDireito() throws Exception {
        int databaseSizeBeforeUpdate = direitoRepository.findAll().size();
        direito.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDireitoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(direito)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Direito in the database
        List<Direito> direitoList = direitoRepository.findAll();
        assertThat(direitoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDireitoWithPatch() throws Exception {
        // Initialize the database
        direitoRepository.saveAndFlush(direito);

        int databaseSizeBeforeUpdate = direitoRepository.findAll().size();

        // Update the direito using partial update
        Direito partialUpdatedDireito = new Direito();
        partialUpdatedDireito.setId(direito.getId());

        partialUpdatedDireito.descricao(UPDATED_DESCRICAO);

        restDireitoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDireito.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDireito))
            )
            .andExpect(status().isOk());

        // Validate the Direito in the database
        List<Direito> direitoList = direitoRepository.findAll();
        assertThat(direitoList).hasSize(databaseSizeBeforeUpdate);
        Direito testDireito = direitoList.get(direitoList.size() - 1);
        assertThat(testDireito.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void fullUpdateDireitoWithPatch() throws Exception {
        // Initialize the database
        direitoRepository.saveAndFlush(direito);

        int databaseSizeBeforeUpdate = direitoRepository.findAll().size();

        // Update the direito using partial update
        Direito partialUpdatedDireito = new Direito();
        partialUpdatedDireito.setId(direito.getId());

        partialUpdatedDireito.descricao(UPDATED_DESCRICAO);

        restDireitoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDireito.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDireito))
            )
            .andExpect(status().isOk());

        // Validate the Direito in the database
        List<Direito> direitoList = direitoRepository.findAll();
        assertThat(direitoList).hasSize(databaseSizeBeforeUpdate);
        Direito testDireito = direitoList.get(direitoList.size() - 1);
        assertThat(testDireito.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void patchNonExistingDireito() throws Exception {
        int databaseSizeBeforeUpdate = direitoRepository.findAll().size();
        direito.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDireitoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, direito.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(direito))
            )
            .andExpect(status().isBadRequest());

        // Validate the Direito in the database
        List<Direito> direitoList = direitoRepository.findAll();
        assertThat(direitoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDireito() throws Exception {
        int databaseSizeBeforeUpdate = direitoRepository.findAll().size();
        direito.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDireitoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(direito))
            )
            .andExpect(status().isBadRequest());

        // Validate the Direito in the database
        List<Direito> direitoList = direitoRepository.findAll();
        assertThat(direitoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDireito() throws Exception {
        int databaseSizeBeforeUpdate = direitoRepository.findAll().size();
        direito.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDireitoMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(direito)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Direito in the database
        List<Direito> direitoList = direitoRepository.findAll();
        assertThat(direitoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDireito() throws Exception {
        // Initialize the database
        direitoRepository.saveAndFlush(direito);

        int databaseSizeBeforeDelete = direitoRepository.findAll().size();

        // Delete the direito
        restDireitoMockMvc
            .perform(delete(ENTITY_API_URL_ID, direito.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Direito> direitoList = direitoRepository.findAll();
        assertThat(direitoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
