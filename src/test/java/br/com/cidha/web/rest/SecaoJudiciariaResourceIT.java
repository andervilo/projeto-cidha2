package br.com.cidha.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.cidha.IntegrationTest;
import br.com.cidha.domain.SecaoJudiciaria;
import br.com.cidha.repository.SecaoJudiciariaRepository;
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
 * Integration tests for the {@link SecaoJudiciariaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SecaoJudiciariaResourceIT {

    private static final String DEFAULT_SIGLA = "AAAAAAAAAA";
    private static final String UPDATED_SIGLA = "BBBBBBBBBB";

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/secao-judiciarias";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SecaoJudiciariaRepository secaoJudiciariaRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSecaoJudiciariaMockMvc;

    private SecaoJudiciaria secaoJudiciaria;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SecaoJudiciaria createEntity(EntityManager em) {
        SecaoJudiciaria secaoJudiciaria = new SecaoJudiciaria().sigla(DEFAULT_SIGLA).nome(DEFAULT_NOME);
        return secaoJudiciaria;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SecaoJudiciaria createUpdatedEntity(EntityManager em) {
        SecaoJudiciaria secaoJudiciaria = new SecaoJudiciaria().sigla(UPDATED_SIGLA).nome(UPDATED_NOME);
        return secaoJudiciaria;
    }

    @BeforeEach
    public void initTest() {
        secaoJudiciaria = createEntity(em);
    }

    @Test
    @Transactional
    void createSecaoJudiciaria() throws Exception {
        int databaseSizeBeforeCreate = secaoJudiciariaRepository.findAll().size();
        // Create the SecaoJudiciaria
        restSecaoJudiciariaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(secaoJudiciaria))
            )
            .andExpect(status().isCreated());

        // Validate the SecaoJudiciaria in the database
        List<SecaoJudiciaria> secaoJudiciariaList = secaoJudiciariaRepository.findAll();
        assertThat(secaoJudiciariaList).hasSize(databaseSizeBeforeCreate + 1);
        SecaoJudiciaria testSecaoJudiciaria = secaoJudiciariaList.get(secaoJudiciariaList.size() - 1);
        assertThat(testSecaoJudiciaria.getSigla()).isEqualTo(DEFAULT_SIGLA);
        assertThat(testSecaoJudiciaria.getNome()).isEqualTo(DEFAULT_NOME);
    }

    @Test
    @Transactional
    void createSecaoJudiciariaWithExistingId() throws Exception {
        // Create the SecaoJudiciaria with an existing ID
        secaoJudiciaria.setId(1L);

        int databaseSizeBeforeCreate = secaoJudiciariaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSecaoJudiciariaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(secaoJudiciaria))
            )
            .andExpect(status().isBadRequest());

        // Validate the SecaoJudiciaria in the database
        List<SecaoJudiciaria> secaoJudiciariaList = secaoJudiciariaRepository.findAll();
        assertThat(secaoJudiciariaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSecaoJudiciarias() throws Exception {
        // Initialize the database
        secaoJudiciariaRepository.saveAndFlush(secaoJudiciaria);

        // Get all the secaoJudiciariaList
        restSecaoJudiciariaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(secaoJudiciaria.getId().intValue())))
            .andExpect(jsonPath("$.[*].sigla").value(hasItem(DEFAULT_SIGLA)))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)));
    }

    @Test
    @Transactional
    void getSecaoJudiciaria() throws Exception {
        // Initialize the database
        secaoJudiciariaRepository.saveAndFlush(secaoJudiciaria);

        // Get the secaoJudiciaria
        restSecaoJudiciariaMockMvc
            .perform(get(ENTITY_API_URL_ID, secaoJudiciaria.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(secaoJudiciaria.getId().intValue()))
            .andExpect(jsonPath("$.sigla").value(DEFAULT_SIGLA))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME));
    }

    @Test
    @Transactional
    void getNonExistingSecaoJudiciaria() throws Exception {
        // Get the secaoJudiciaria
        restSecaoJudiciariaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewSecaoJudiciaria() throws Exception {
        // Initialize the database
        secaoJudiciariaRepository.saveAndFlush(secaoJudiciaria);

        int databaseSizeBeforeUpdate = secaoJudiciariaRepository.findAll().size();

        // Update the secaoJudiciaria
        SecaoJudiciaria updatedSecaoJudiciaria = secaoJudiciariaRepository.findById(secaoJudiciaria.getId()).get();
        // Disconnect from session so that the updates on updatedSecaoJudiciaria are not directly saved in db
        em.detach(updatedSecaoJudiciaria);
        updatedSecaoJudiciaria.sigla(UPDATED_SIGLA).nome(UPDATED_NOME);

        restSecaoJudiciariaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSecaoJudiciaria.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedSecaoJudiciaria))
            )
            .andExpect(status().isOk());

        // Validate the SecaoJudiciaria in the database
        List<SecaoJudiciaria> secaoJudiciariaList = secaoJudiciariaRepository.findAll();
        assertThat(secaoJudiciariaList).hasSize(databaseSizeBeforeUpdate);
        SecaoJudiciaria testSecaoJudiciaria = secaoJudiciariaList.get(secaoJudiciariaList.size() - 1);
        assertThat(testSecaoJudiciaria.getSigla()).isEqualTo(UPDATED_SIGLA);
        assertThat(testSecaoJudiciaria.getNome()).isEqualTo(UPDATED_NOME);
    }

    @Test
    @Transactional
    void putNonExistingSecaoJudiciaria() throws Exception {
        int databaseSizeBeforeUpdate = secaoJudiciariaRepository.findAll().size();
        secaoJudiciaria.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSecaoJudiciariaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, secaoJudiciaria.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(secaoJudiciaria))
            )
            .andExpect(status().isBadRequest());

        // Validate the SecaoJudiciaria in the database
        List<SecaoJudiciaria> secaoJudiciariaList = secaoJudiciariaRepository.findAll();
        assertThat(secaoJudiciariaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSecaoJudiciaria() throws Exception {
        int databaseSizeBeforeUpdate = secaoJudiciariaRepository.findAll().size();
        secaoJudiciaria.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSecaoJudiciariaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(secaoJudiciaria))
            )
            .andExpect(status().isBadRequest());

        // Validate the SecaoJudiciaria in the database
        List<SecaoJudiciaria> secaoJudiciariaList = secaoJudiciariaRepository.findAll();
        assertThat(secaoJudiciariaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSecaoJudiciaria() throws Exception {
        int databaseSizeBeforeUpdate = secaoJudiciariaRepository.findAll().size();
        secaoJudiciaria.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSecaoJudiciariaMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(secaoJudiciaria))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SecaoJudiciaria in the database
        List<SecaoJudiciaria> secaoJudiciariaList = secaoJudiciariaRepository.findAll();
        assertThat(secaoJudiciariaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSecaoJudiciariaWithPatch() throws Exception {
        // Initialize the database
        secaoJudiciariaRepository.saveAndFlush(secaoJudiciaria);

        int databaseSizeBeforeUpdate = secaoJudiciariaRepository.findAll().size();

        // Update the secaoJudiciaria using partial update
        SecaoJudiciaria partialUpdatedSecaoJudiciaria = new SecaoJudiciaria();
        partialUpdatedSecaoJudiciaria.setId(secaoJudiciaria.getId());

        restSecaoJudiciariaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSecaoJudiciaria.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSecaoJudiciaria))
            )
            .andExpect(status().isOk());

        // Validate the SecaoJudiciaria in the database
        List<SecaoJudiciaria> secaoJudiciariaList = secaoJudiciariaRepository.findAll();
        assertThat(secaoJudiciariaList).hasSize(databaseSizeBeforeUpdate);
        SecaoJudiciaria testSecaoJudiciaria = secaoJudiciariaList.get(secaoJudiciariaList.size() - 1);
        assertThat(testSecaoJudiciaria.getSigla()).isEqualTo(DEFAULT_SIGLA);
        assertThat(testSecaoJudiciaria.getNome()).isEqualTo(DEFAULT_NOME);
    }

    @Test
    @Transactional
    void fullUpdateSecaoJudiciariaWithPatch() throws Exception {
        // Initialize the database
        secaoJudiciariaRepository.saveAndFlush(secaoJudiciaria);

        int databaseSizeBeforeUpdate = secaoJudiciariaRepository.findAll().size();

        // Update the secaoJudiciaria using partial update
        SecaoJudiciaria partialUpdatedSecaoJudiciaria = new SecaoJudiciaria();
        partialUpdatedSecaoJudiciaria.setId(secaoJudiciaria.getId());

        partialUpdatedSecaoJudiciaria.sigla(UPDATED_SIGLA).nome(UPDATED_NOME);

        restSecaoJudiciariaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSecaoJudiciaria.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSecaoJudiciaria))
            )
            .andExpect(status().isOk());

        // Validate the SecaoJudiciaria in the database
        List<SecaoJudiciaria> secaoJudiciariaList = secaoJudiciariaRepository.findAll();
        assertThat(secaoJudiciariaList).hasSize(databaseSizeBeforeUpdate);
        SecaoJudiciaria testSecaoJudiciaria = secaoJudiciariaList.get(secaoJudiciariaList.size() - 1);
        assertThat(testSecaoJudiciaria.getSigla()).isEqualTo(UPDATED_SIGLA);
        assertThat(testSecaoJudiciaria.getNome()).isEqualTo(UPDATED_NOME);
    }

    @Test
    @Transactional
    void patchNonExistingSecaoJudiciaria() throws Exception {
        int databaseSizeBeforeUpdate = secaoJudiciariaRepository.findAll().size();
        secaoJudiciaria.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSecaoJudiciariaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, secaoJudiciaria.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(secaoJudiciaria))
            )
            .andExpect(status().isBadRequest());

        // Validate the SecaoJudiciaria in the database
        List<SecaoJudiciaria> secaoJudiciariaList = secaoJudiciariaRepository.findAll();
        assertThat(secaoJudiciariaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSecaoJudiciaria() throws Exception {
        int databaseSizeBeforeUpdate = secaoJudiciariaRepository.findAll().size();
        secaoJudiciaria.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSecaoJudiciariaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(secaoJudiciaria))
            )
            .andExpect(status().isBadRequest());

        // Validate the SecaoJudiciaria in the database
        List<SecaoJudiciaria> secaoJudiciariaList = secaoJudiciariaRepository.findAll();
        assertThat(secaoJudiciariaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSecaoJudiciaria() throws Exception {
        int databaseSizeBeforeUpdate = secaoJudiciariaRepository.findAll().size();
        secaoJudiciaria.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSecaoJudiciariaMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(secaoJudiciaria))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SecaoJudiciaria in the database
        List<SecaoJudiciaria> secaoJudiciariaList = secaoJudiciariaRepository.findAll();
        assertThat(secaoJudiciariaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSecaoJudiciaria() throws Exception {
        // Initialize the database
        secaoJudiciariaRepository.saveAndFlush(secaoJudiciaria);

        int databaseSizeBeforeDelete = secaoJudiciariaRepository.findAll().size();

        // Delete the secaoJudiciaria
        restSecaoJudiciariaMockMvc
            .perform(delete(ENTITY_API_URL_ID, secaoJudiciaria.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SecaoJudiciaria> secaoJudiciariaList = secaoJudiciariaRepository.findAll();
        assertThat(secaoJudiciariaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
