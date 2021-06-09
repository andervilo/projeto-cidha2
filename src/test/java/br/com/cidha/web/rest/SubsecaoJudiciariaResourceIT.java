package br.com.cidha.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.cidha.IntegrationTest;
import br.com.cidha.domain.SubsecaoJudiciaria;
import br.com.cidha.repository.SubsecaoJudiciariaRepository;
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
 * Integration tests for the {@link SubsecaoJudiciariaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SubsecaoJudiciariaResourceIT {

    private static final String DEFAULT_SIGLA = "AAAAAAAAAA";
    private static final String UPDATED_SIGLA = "BBBBBBBBBB";

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/subsecao-judiciarias";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SubsecaoJudiciariaRepository subsecaoJudiciariaRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSubsecaoJudiciariaMockMvc;

    private SubsecaoJudiciaria subsecaoJudiciaria;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SubsecaoJudiciaria createEntity(EntityManager em) {
        SubsecaoJudiciaria subsecaoJudiciaria = new SubsecaoJudiciaria().sigla(DEFAULT_SIGLA).nome(DEFAULT_NOME);
        return subsecaoJudiciaria;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SubsecaoJudiciaria createUpdatedEntity(EntityManager em) {
        SubsecaoJudiciaria subsecaoJudiciaria = new SubsecaoJudiciaria().sigla(UPDATED_SIGLA).nome(UPDATED_NOME);
        return subsecaoJudiciaria;
    }

    @BeforeEach
    public void initTest() {
        subsecaoJudiciaria = createEntity(em);
    }

    @Test
    @Transactional
    void createSubsecaoJudiciaria() throws Exception {
        int databaseSizeBeforeCreate = subsecaoJudiciariaRepository.findAll().size();
        // Create the SubsecaoJudiciaria
        restSubsecaoJudiciariaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(subsecaoJudiciaria))
            )
            .andExpect(status().isCreated());

        // Validate the SubsecaoJudiciaria in the database
        List<SubsecaoJudiciaria> subsecaoJudiciariaList = subsecaoJudiciariaRepository.findAll();
        assertThat(subsecaoJudiciariaList).hasSize(databaseSizeBeforeCreate + 1);
        SubsecaoJudiciaria testSubsecaoJudiciaria = subsecaoJudiciariaList.get(subsecaoJudiciariaList.size() - 1);
        assertThat(testSubsecaoJudiciaria.getSigla()).isEqualTo(DEFAULT_SIGLA);
        assertThat(testSubsecaoJudiciaria.getNome()).isEqualTo(DEFAULT_NOME);
    }

    @Test
    @Transactional
    void createSubsecaoJudiciariaWithExistingId() throws Exception {
        // Create the SubsecaoJudiciaria with an existing ID
        subsecaoJudiciaria.setId(1L);

        int databaseSizeBeforeCreate = subsecaoJudiciariaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSubsecaoJudiciariaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(subsecaoJudiciaria))
            )
            .andExpect(status().isBadRequest());

        // Validate the SubsecaoJudiciaria in the database
        List<SubsecaoJudiciaria> subsecaoJudiciariaList = subsecaoJudiciariaRepository.findAll();
        assertThat(subsecaoJudiciariaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSubsecaoJudiciarias() throws Exception {
        // Initialize the database
        subsecaoJudiciariaRepository.saveAndFlush(subsecaoJudiciaria);

        // Get all the subsecaoJudiciariaList
        restSubsecaoJudiciariaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(subsecaoJudiciaria.getId().intValue())))
            .andExpect(jsonPath("$.[*].sigla").value(hasItem(DEFAULT_SIGLA)))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)));
    }

    @Test
    @Transactional
    void getSubsecaoJudiciaria() throws Exception {
        // Initialize the database
        subsecaoJudiciariaRepository.saveAndFlush(subsecaoJudiciaria);

        // Get the subsecaoJudiciaria
        restSubsecaoJudiciariaMockMvc
            .perform(get(ENTITY_API_URL_ID, subsecaoJudiciaria.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(subsecaoJudiciaria.getId().intValue()))
            .andExpect(jsonPath("$.sigla").value(DEFAULT_SIGLA))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME));
    }

    @Test
    @Transactional
    void getNonExistingSubsecaoJudiciaria() throws Exception {
        // Get the subsecaoJudiciaria
        restSubsecaoJudiciariaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewSubsecaoJudiciaria() throws Exception {
        // Initialize the database
        subsecaoJudiciariaRepository.saveAndFlush(subsecaoJudiciaria);

        int databaseSizeBeforeUpdate = subsecaoJudiciariaRepository.findAll().size();

        // Update the subsecaoJudiciaria
        SubsecaoJudiciaria updatedSubsecaoJudiciaria = subsecaoJudiciariaRepository.findById(subsecaoJudiciaria.getId()).get();
        // Disconnect from session so that the updates on updatedSubsecaoJudiciaria are not directly saved in db
        em.detach(updatedSubsecaoJudiciaria);
        updatedSubsecaoJudiciaria.sigla(UPDATED_SIGLA).nome(UPDATED_NOME);

        restSubsecaoJudiciariaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSubsecaoJudiciaria.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedSubsecaoJudiciaria))
            )
            .andExpect(status().isOk());

        // Validate the SubsecaoJudiciaria in the database
        List<SubsecaoJudiciaria> subsecaoJudiciariaList = subsecaoJudiciariaRepository.findAll();
        assertThat(subsecaoJudiciariaList).hasSize(databaseSizeBeforeUpdate);
        SubsecaoJudiciaria testSubsecaoJudiciaria = subsecaoJudiciariaList.get(subsecaoJudiciariaList.size() - 1);
        assertThat(testSubsecaoJudiciaria.getSigla()).isEqualTo(UPDATED_SIGLA);
        assertThat(testSubsecaoJudiciaria.getNome()).isEqualTo(UPDATED_NOME);
    }

    @Test
    @Transactional
    void putNonExistingSubsecaoJudiciaria() throws Exception {
        int databaseSizeBeforeUpdate = subsecaoJudiciariaRepository.findAll().size();
        subsecaoJudiciaria.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSubsecaoJudiciariaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, subsecaoJudiciaria.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(subsecaoJudiciaria))
            )
            .andExpect(status().isBadRequest());

        // Validate the SubsecaoJudiciaria in the database
        List<SubsecaoJudiciaria> subsecaoJudiciariaList = subsecaoJudiciariaRepository.findAll();
        assertThat(subsecaoJudiciariaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSubsecaoJudiciaria() throws Exception {
        int databaseSizeBeforeUpdate = subsecaoJudiciariaRepository.findAll().size();
        subsecaoJudiciaria.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubsecaoJudiciariaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(subsecaoJudiciaria))
            )
            .andExpect(status().isBadRequest());

        // Validate the SubsecaoJudiciaria in the database
        List<SubsecaoJudiciaria> subsecaoJudiciariaList = subsecaoJudiciariaRepository.findAll();
        assertThat(subsecaoJudiciariaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSubsecaoJudiciaria() throws Exception {
        int databaseSizeBeforeUpdate = subsecaoJudiciariaRepository.findAll().size();
        subsecaoJudiciaria.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubsecaoJudiciariaMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(subsecaoJudiciaria))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SubsecaoJudiciaria in the database
        List<SubsecaoJudiciaria> subsecaoJudiciariaList = subsecaoJudiciariaRepository.findAll();
        assertThat(subsecaoJudiciariaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSubsecaoJudiciariaWithPatch() throws Exception {
        // Initialize the database
        subsecaoJudiciariaRepository.saveAndFlush(subsecaoJudiciaria);

        int databaseSizeBeforeUpdate = subsecaoJudiciariaRepository.findAll().size();

        // Update the subsecaoJudiciaria using partial update
        SubsecaoJudiciaria partialUpdatedSubsecaoJudiciaria = new SubsecaoJudiciaria();
        partialUpdatedSubsecaoJudiciaria.setId(subsecaoJudiciaria.getId());

        restSubsecaoJudiciariaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSubsecaoJudiciaria.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSubsecaoJudiciaria))
            )
            .andExpect(status().isOk());

        // Validate the SubsecaoJudiciaria in the database
        List<SubsecaoJudiciaria> subsecaoJudiciariaList = subsecaoJudiciariaRepository.findAll();
        assertThat(subsecaoJudiciariaList).hasSize(databaseSizeBeforeUpdate);
        SubsecaoJudiciaria testSubsecaoJudiciaria = subsecaoJudiciariaList.get(subsecaoJudiciariaList.size() - 1);
        assertThat(testSubsecaoJudiciaria.getSigla()).isEqualTo(DEFAULT_SIGLA);
        assertThat(testSubsecaoJudiciaria.getNome()).isEqualTo(DEFAULT_NOME);
    }

    @Test
    @Transactional
    void fullUpdateSubsecaoJudiciariaWithPatch() throws Exception {
        // Initialize the database
        subsecaoJudiciariaRepository.saveAndFlush(subsecaoJudiciaria);

        int databaseSizeBeforeUpdate = subsecaoJudiciariaRepository.findAll().size();

        // Update the subsecaoJudiciaria using partial update
        SubsecaoJudiciaria partialUpdatedSubsecaoJudiciaria = new SubsecaoJudiciaria();
        partialUpdatedSubsecaoJudiciaria.setId(subsecaoJudiciaria.getId());

        partialUpdatedSubsecaoJudiciaria.sigla(UPDATED_SIGLA).nome(UPDATED_NOME);

        restSubsecaoJudiciariaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSubsecaoJudiciaria.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSubsecaoJudiciaria))
            )
            .andExpect(status().isOk());

        // Validate the SubsecaoJudiciaria in the database
        List<SubsecaoJudiciaria> subsecaoJudiciariaList = subsecaoJudiciariaRepository.findAll();
        assertThat(subsecaoJudiciariaList).hasSize(databaseSizeBeforeUpdate);
        SubsecaoJudiciaria testSubsecaoJudiciaria = subsecaoJudiciariaList.get(subsecaoJudiciariaList.size() - 1);
        assertThat(testSubsecaoJudiciaria.getSigla()).isEqualTo(UPDATED_SIGLA);
        assertThat(testSubsecaoJudiciaria.getNome()).isEqualTo(UPDATED_NOME);
    }

    @Test
    @Transactional
    void patchNonExistingSubsecaoJudiciaria() throws Exception {
        int databaseSizeBeforeUpdate = subsecaoJudiciariaRepository.findAll().size();
        subsecaoJudiciaria.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSubsecaoJudiciariaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, subsecaoJudiciaria.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(subsecaoJudiciaria))
            )
            .andExpect(status().isBadRequest());

        // Validate the SubsecaoJudiciaria in the database
        List<SubsecaoJudiciaria> subsecaoJudiciariaList = subsecaoJudiciariaRepository.findAll();
        assertThat(subsecaoJudiciariaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSubsecaoJudiciaria() throws Exception {
        int databaseSizeBeforeUpdate = subsecaoJudiciariaRepository.findAll().size();
        subsecaoJudiciaria.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubsecaoJudiciariaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(subsecaoJudiciaria))
            )
            .andExpect(status().isBadRequest());

        // Validate the SubsecaoJudiciaria in the database
        List<SubsecaoJudiciaria> subsecaoJudiciariaList = subsecaoJudiciariaRepository.findAll();
        assertThat(subsecaoJudiciariaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSubsecaoJudiciaria() throws Exception {
        int databaseSizeBeforeUpdate = subsecaoJudiciariaRepository.findAll().size();
        subsecaoJudiciaria.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubsecaoJudiciariaMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(subsecaoJudiciaria))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SubsecaoJudiciaria in the database
        List<SubsecaoJudiciaria> subsecaoJudiciariaList = subsecaoJudiciariaRepository.findAll();
        assertThat(subsecaoJudiciariaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSubsecaoJudiciaria() throws Exception {
        // Initialize the database
        subsecaoJudiciariaRepository.saveAndFlush(subsecaoJudiciaria);

        int databaseSizeBeforeDelete = subsecaoJudiciariaRepository.findAll().size();

        // Delete the subsecaoJudiciaria
        restSubsecaoJudiciariaMockMvc
            .perform(delete(ENTITY_API_URL_ID, subsecaoJudiciaria.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SubsecaoJudiciaria> subsecaoJudiciariaList = subsecaoJudiciariaRepository.findAll();
        assertThat(subsecaoJudiciariaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
