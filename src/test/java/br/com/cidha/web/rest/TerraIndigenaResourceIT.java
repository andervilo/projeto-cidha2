package br.com.cidha.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.cidha.IntegrationTest;
import br.com.cidha.domain.EtniaIndigena;
import br.com.cidha.domain.Processo;
import br.com.cidha.domain.TerraIndigena;
import br.com.cidha.repository.TerraIndigenaRepository;
import br.com.cidha.service.TerraIndigenaService;
import br.com.cidha.service.criteria.TerraIndigenaCriteria;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link TerraIndigenaResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class TerraIndigenaResourceIT {

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/terra-indigenas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TerraIndigenaRepository terraIndigenaRepository;

    @Mock
    private TerraIndigenaRepository terraIndigenaRepositoryMock;

    @Mock
    private TerraIndigenaService terraIndigenaServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTerraIndigenaMockMvc;

    private TerraIndigena terraIndigena;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TerraIndigena createEntity(EntityManager em) {
        TerraIndigena terraIndigena = new TerraIndigena().descricao(DEFAULT_DESCRICAO);
        return terraIndigena;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TerraIndigena createUpdatedEntity(EntityManager em) {
        TerraIndigena terraIndigena = new TerraIndigena().descricao(UPDATED_DESCRICAO);
        return terraIndigena;
    }

    @BeforeEach
    public void initTest() {
        terraIndigena = createEntity(em);
    }

    @Test
    @Transactional
    void createTerraIndigena() throws Exception {
        int databaseSizeBeforeCreate = terraIndigenaRepository.findAll().size();
        // Create the TerraIndigena
        restTerraIndigenaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(terraIndigena)))
            .andExpect(status().isCreated());

        // Validate the TerraIndigena in the database
        List<TerraIndigena> terraIndigenaList = terraIndigenaRepository.findAll();
        assertThat(terraIndigenaList).hasSize(databaseSizeBeforeCreate + 1);
        TerraIndigena testTerraIndigena = terraIndigenaList.get(terraIndigenaList.size() - 1);
        assertThat(testTerraIndigena.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
    }

    @Test
    @Transactional
    void createTerraIndigenaWithExistingId() throws Exception {
        // Create the TerraIndigena with an existing ID
        terraIndigena.setId(1L);

        int databaseSizeBeforeCreate = terraIndigenaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTerraIndigenaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(terraIndigena)))
            .andExpect(status().isBadRequest());

        // Validate the TerraIndigena in the database
        List<TerraIndigena> terraIndigenaList = terraIndigenaRepository.findAll();
        assertThat(terraIndigenaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTerraIndigenas() throws Exception {
        // Initialize the database
        terraIndigenaRepository.saveAndFlush(terraIndigena);

        // Get all the terraIndigenaList
        restTerraIndigenaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(terraIndigena.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllTerraIndigenasWithEagerRelationshipsIsEnabled() throws Exception {
        when(terraIndigenaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restTerraIndigenaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(terraIndigenaServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllTerraIndigenasWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(terraIndigenaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restTerraIndigenaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(terraIndigenaServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getTerraIndigena() throws Exception {
        // Initialize the database
        terraIndigenaRepository.saveAndFlush(terraIndigena);

        // Get the terraIndigena
        restTerraIndigenaMockMvc
            .perform(get(ENTITY_API_URL_ID, terraIndigena.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(terraIndigena.getId().intValue()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()));
    }

    @Test
    @Transactional
    void getTerraIndigenasByIdFiltering() throws Exception {
        // Initialize the database
        terraIndigenaRepository.saveAndFlush(terraIndigena);

        Long id = terraIndigena.getId();

        defaultTerraIndigenaShouldBeFound("id.equals=" + id);
        defaultTerraIndigenaShouldNotBeFound("id.notEquals=" + id);

        defaultTerraIndigenaShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTerraIndigenaShouldNotBeFound("id.greaterThan=" + id);

        defaultTerraIndigenaShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTerraIndigenaShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllTerraIndigenasByEtniaIsEqualToSomething() throws Exception {
        // Initialize the database
        terraIndigenaRepository.saveAndFlush(terraIndigena);
        EtniaIndigena etnia = EtniaIndigenaResourceIT.createEntity(em);
        em.persist(etnia);
        em.flush();
        terraIndigena.addEtnia(etnia);
        terraIndigenaRepository.saveAndFlush(terraIndigena);
        Long etniaId = etnia.getId();

        // Get all the terraIndigenaList where etnia equals to etniaId
        defaultTerraIndigenaShouldBeFound("etniaId.equals=" + etniaId);

        // Get all the terraIndigenaList where etnia equals to (etniaId + 1)
        defaultTerraIndigenaShouldNotBeFound("etniaId.equals=" + (etniaId + 1));
    }

    @Test
    @Transactional
    void getAllTerraIndigenasByProcessoIsEqualToSomething() throws Exception {
        // Initialize the database
        terraIndigenaRepository.saveAndFlush(terraIndigena);
        Processo processo = ProcessoResourceIT.createEntity(em);
        em.persist(processo);
        em.flush();
        terraIndigena.addProcesso(processo);
        terraIndigenaRepository.saveAndFlush(terraIndigena);
        Long processoId = processo.getId();

        // Get all the terraIndigenaList where processo equals to processoId
        defaultTerraIndigenaShouldBeFound("processoId.equals=" + processoId);

        // Get all the terraIndigenaList where processo equals to (processoId + 1)
        defaultTerraIndigenaShouldNotBeFound("processoId.equals=" + (processoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTerraIndigenaShouldBeFound(String filter) throws Exception {
        restTerraIndigenaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(terraIndigena.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())));

        // Check, that the count call also returns 1
        restTerraIndigenaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTerraIndigenaShouldNotBeFound(String filter) throws Exception {
        restTerraIndigenaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTerraIndigenaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingTerraIndigena() throws Exception {
        // Get the terraIndigena
        restTerraIndigenaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTerraIndigena() throws Exception {
        // Initialize the database
        terraIndigenaRepository.saveAndFlush(terraIndigena);

        int databaseSizeBeforeUpdate = terraIndigenaRepository.findAll().size();

        // Update the terraIndigena
        TerraIndigena updatedTerraIndigena = terraIndigenaRepository.findById(terraIndigena.getId()).get();
        // Disconnect from session so that the updates on updatedTerraIndigena are not directly saved in db
        em.detach(updatedTerraIndigena);
        updatedTerraIndigena.descricao(UPDATED_DESCRICAO);

        restTerraIndigenaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTerraIndigena.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedTerraIndigena))
            )
            .andExpect(status().isOk());

        // Validate the TerraIndigena in the database
        List<TerraIndigena> terraIndigenaList = terraIndigenaRepository.findAll();
        assertThat(terraIndigenaList).hasSize(databaseSizeBeforeUpdate);
        TerraIndigena testTerraIndigena = terraIndigenaList.get(terraIndigenaList.size() - 1);
        assertThat(testTerraIndigena.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void putNonExistingTerraIndigena() throws Exception {
        int databaseSizeBeforeUpdate = terraIndigenaRepository.findAll().size();
        terraIndigena.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTerraIndigenaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, terraIndigena.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(terraIndigena))
            )
            .andExpect(status().isBadRequest());

        // Validate the TerraIndigena in the database
        List<TerraIndigena> terraIndigenaList = terraIndigenaRepository.findAll();
        assertThat(terraIndigenaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTerraIndigena() throws Exception {
        int databaseSizeBeforeUpdate = terraIndigenaRepository.findAll().size();
        terraIndigena.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTerraIndigenaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(terraIndigena))
            )
            .andExpect(status().isBadRequest());

        // Validate the TerraIndigena in the database
        List<TerraIndigena> terraIndigenaList = terraIndigenaRepository.findAll();
        assertThat(terraIndigenaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTerraIndigena() throws Exception {
        int databaseSizeBeforeUpdate = terraIndigenaRepository.findAll().size();
        terraIndigena.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTerraIndigenaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(terraIndigena)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TerraIndigena in the database
        List<TerraIndigena> terraIndigenaList = terraIndigenaRepository.findAll();
        assertThat(terraIndigenaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTerraIndigenaWithPatch() throws Exception {
        // Initialize the database
        terraIndigenaRepository.saveAndFlush(terraIndigena);

        int databaseSizeBeforeUpdate = terraIndigenaRepository.findAll().size();

        // Update the terraIndigena using partial update
        TerraIndigena partialUpdatedTerraIndigena = new TerraIndigena();
        partialUpdatedTerraIndigena.setId(terraIndigena.getId());

        partialUpdatedTerraIndigena.descricao(UPDATED_DESCRICAO);

        restTerraIndigenaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTerraIndigena.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTerraIndigena))
            )
            .andExpect(status().isOk());

        // Validate the TerraIndigena in the database
        List<TerraIndigena> terraIndigenaList = terraIndigenaRepository.findAll();
        assertThat(terraIndigenaList).hasSize(databaseSizeBeforeUpdate);
        TerraIndigena testTerraIndigena = terraIndigenaList.get(terraIndigenaList.size() - 1);
        assertThat(testTerraIndigena.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void fullUpdateTerraIndigenaWithPatch() throws Exception {
        // Initialize the database
        terraIndigenaRepository.saveAndFlush(terraIndigena);

        int databaseSizeBeforeUpdate = terraIndigenaRepository.findAll().size();

        // Update the terraIndigena using partial update
        TerraIndigena partialUpdatedTerraIndigena = new TerraIndigena();
        partialUpdatedTerraIndigena.setId(terraIndigena.getId());

        partialUpdatedTerraIndigena.descricao(UPDATED_DESCRICAO);

        restTerraIndigenaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTerraIndigena.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTerraIndigena))
            )
            .andExpect(status().isOk());

        // Validate the TerraIndigena in the database
        List<TerraIndigena> terraIndigenaList = terraIndigenaRepository.findAll();
        assertThat(terraIndigenaList).hasSize(databaseSizeBeforeUpdate);
        TerraIndigena testTerraIndigena = terraIndigenaList.get(terraIndigenaList.size() - 1);
        assertThat(testTerraIndigena.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void patchNonExistingTerraIndigena() throws Exception {
        int databaseSizeBeforeUpdate = terraIndigenaRepository.findAll().size();
        terraIndigena.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTerraIndigenaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, terraIndigena.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(terraIndigena))
            )
            .andExpect(status().isBadRequest());

        // Validate the TerraIndigena in the database
        List<TerraIndigena> terraIndigenaList = terraIndigenaRepository.findAll();
        assertThat(terraIndigenaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTerraIndigena() throws Exception {
        int databaseSizeBeforeUpdate = terraIndigenaRepository.findAll().size();
        terraIndigena.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTerraIndigenaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(terraIndigena))
            )
            .andExpect(status().isBadRequest());

        // Validate the TerraIndigena in the database
        List<TerraIndigena> terraIndigenaList = terraIndigenaRepository.findAll();
        assertThat(terraIndigenaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTerraIndigena() throws Exception {
        int databaseSizeBeforeUpdate = terraIndigenaRepository.findAll().size();
        terraIndigena.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTerraIndigenaMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(terraIndigena))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TerraIndigena in the database
        List<TerraIndigena> terraIndigenaList = terraIndigenaRepository.findAll();
        assertThat(terraIndigenaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTerraIndigena() throws Exception {
        // Initialize the database
        terraIndigenaRepository.saveAndFlush(terraIndigena);

        int databaseSizeBeforeDelete = terraIndigenaRepository.findAll().size();

        // Delete the terraIndigena
        restTerraIndigenaMockMvc
            .perform(delete(ENTITY_API_URL_ID, terraIndigena.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TerraIndigena> terraIndigenaList = terraIndigenaRepository.findAll();
        assertThat(terraIndigenaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
