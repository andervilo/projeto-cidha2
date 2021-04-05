package br.com.cidha.web.rest;

import br.com.cidha.CidhaApp;
import br.com.cidha.domain.TerraIndigena;
import br.com.cidha.domain.EtniaIndigena;
import br.com.cidha.domain.Processo;
import br.com.cidha.repository.TerraIndigenaRepository;
import br.com.cidha.service.TerraIndigenaService;
import br.com.cidha.service.dto.TerraIndigenaCriteria;
import br.com.cidha.service.TerraIndigenaQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link TerraIndigenaResource} REST controller.
 */
@SpringBootTest(classes = CidhaApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class TerraIndigenaResourceIT {

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    @Autowired
    private TerraIndigenaRepository terraIndigenaRepository;

    @Mock
    private TerraIndigenaRepository terraIndigenaRepositoryMock;

    @Mock
    private TerraIndigenaService terraIndigenaServiceMock;

    @Autowired
    private TerraIndigenaService terraIndigenaService;

    @Autowired
    private TerraIndigenaQueryService terraIndigenaQueryService;

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
        TerraIndigena terraIndigena = new TerraIndigena()
            .descricao(DEFAULT_DESCRICAO);
        return terraIndigena;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TerraIndigena createUpdatedEntity(EntityManager em) {
        TerraIndigena terraIndigena = new TerraIndigena()
            .descricao(UPDATED_DESCRICAO);
        return terraIndigena;
    }

    @BeforeEach
    public void initTest() {
        terraIndigena = createEntity(em);
    }

    @Test
    @Transactional
    public void createTerraIndigena() throws Exception {
        int databaseSizeBeforeCreate = terraIndigenaRepository.findAll().size();
        // Create the TerraIndigena
        restTerraIndigenaMockMvc.perform(post("/api/terra-indigenas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(terraIndigena)))
            .andExpect(status().isCreated());

        // Validate the TerraIndigena in the database
        List<TerraIndigena> terraIndigenaList = terraIndigenaRepository.findAll();
        assertThat(terraIndigenaList).hasSize(databaseSizeBeforeCreate + 1);
        TerraIndigena testTerraIndigena = terraIndigenaList.get(terraIndigenaList.size() - 1);
        assertThat(testTerraIndigena.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
    }

    @Test
    @Transactional
    public void createTerraIndigenaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = terraIndigenaRepository.findAll().size();

        // Create the TerraIndigena with an existing ID
        terraIndigena.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTerraIndigenaMockMvc.perform(post("/api/terra-indigenas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(terraIndigena)))
            .andExpect(status().isBadRequest());

        // Validate the TerraIndigena in the database
        List<TerraIndigena> terraIndigenaList = terraIndigenaRepository.findAll();
        assertThat(terraIndigenaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTerraIndigenas() throws Exception {
        // Initialize the database
        terraIndigenaRepository.saveAndFlush(terraIndigena);

        // Get all the terraIndigenaList
        restTerraIndigenaMockMvc.perform(get("/api/terra-indigenas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(terraIndigena.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllTerraIndigenasWithEagerRelationshipsIsEnabled() throws Exception {
        when(terraIndigenaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restTerraIndigenaMockMvc.perform(get("/api/terra-indigenas?eagerload=true"))
            .andExpect(status().isOk());

        verify(terraIndigenaServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllTerraIndigenasWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(terraIndigenaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restTerraIndigenaMockMvc.perform(get("/api/terra-indigenas?eagerload=true"))
            .andExpect(status().isOk());

        verify(terraIndigenaServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getTerraIndigena() throws Exception {
        // Initialize the database
        terraIndigenaRepository.saveAndFlush(terraIndigena);

        // Get the terraIndigena
        restTerraIndigenaMockMvc.perform(get("/api/terra-indigenas/{id}", terraIndigena.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(terraIndigena.getId().intValue()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()));
    }


    @Test
    @Transactional
    public void getTerraIndigenasByIdFiltering() throws Exception {
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
    public void getAllTerraIndigenasByEtniaIsEqualToSomething() throws Exception {
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

        // Get all the terraIndigenaList where etnia equals to etniaId + 1
        defaultTerraIndigenaShouldNotBeFound("etniaId.equals=" + (etniaId + 1));
    }


    @Test
    @Transactional
    public void getAllTerraIndigenasByProcessoIsEqualToSomething() throws Exception {
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

        // Get all the terraIndigenaList where processo equals to processoId + 1
        defaultTerraIndigenaShouldNotBeFound("processoId.equals=" + (processoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTerraIndigenaShouldBeFound(String filter) throws Exception {
        restTerraIndigenaMockMvc.perform(get("/api/terra-indigenas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(terraIndigena.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())));

        // Check, that the count call also returns 1
        restTerraIndigenaMockMvc.perform(get("/api/terra-indigenas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTerraIndigenaShouldNotBeFound(String filter) throws Exception {
        restTerraIndigenaMockMvc.perform(get("/api/terra-indigenas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTerraIndigenaMockMvc.perform(get("/api/terra-indigenas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingTerraIndigena() throws Exception {
        // Get the terraIndigena
        restTerraIndigenaMockMvc.perform(get("/api/terra-indigenas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTerraIndigena() throws Exception {
        // Initialize the database
        terraIndigenaService.save(terraIndigena);

        int databaseSizeBeforeUpdate = terraIndigenaRepository.findAll().size();

        // Update the terraIndigena
        TerraIndigena updatedTerraIndigena = terraIndigenaRepository.findById(terraIndigena.getId()).get();
        // Disconnect from session so that the updates on updatedTerraIndigena are not directly saved in db
        em.detach(updatedTerraIndigena);
        updatedTerraIndigena
            .descricao(UPDATED_DESCRICAO);

        restTerraIndigenaMockMvc.perform(put("/api/terra-indigenas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTerraIndigena)))
            .andExpect(status().isOk());

        // Validate the TerraIndigena in the database
        List<TerraIndigena> terraIndigenaList = terraIndigenaRepository.findAll();
        assertThat(terraIndigenaList).hasSize(databaseSizeBeforeUpdate);
        TerraIndigena testTerraIndigena = terraIndigenaList.get(terraIndigenaList.size() - 1);
        assertThat(testTerraIndigena.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void updateNonExistingTerraIndigena() throws Exception {
        int databaseSizeBeforeUpdate = terraIndigenaRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTerraIndigenaMockMvc.perform(put("/api/terra-indigenas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(terraIndigena)))
            .andExpect(status().isBadRequest());

        // Validate the TerraIndigena in the database
        List<TerraIndigena> terraIndigenaList = terraIndigenaRepository.findAll();
        assertThat(terraIndigenaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTerraIndigena() throws Exception {
        // Initialize the database
        terraIndigenaService.save(terraIndigena);

        int databaseSizeBeforeDelete = terraIndigenaRepository.findAll().size();

        // Delete the terraIndigena
        restTerraIndigenaMockMvc.perform(delete("/api/terra-indigenas/{id}", terraIndigena.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TerraIndigena> terraIndigenaList = terraIndigenaRepository.findAll();
        assertThat(terraIndigenaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
