package br.com.cidha.web.rest;

import br.com.cidha.CidhaApp;
import br.com.cidha.domain.TipoEmpreendimento;
import br.com.cidha.repository.TipoEmpreendimentoRepository;
import br.com.cidha.service.TipoEmpreendimentoService;
import br.com.cidha.service.dto.TipoEmpreendimentoCriteria;
import br.com.cidha.service.TipoEmpreendimentoQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link TipoEmpreendimentoResource} REST controller.
 */
@SpringBootTest(classes = CidhaApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class TipoEmpreendimentoResourceIT {

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    @Autowired
    private TipoEmpreendimentoRepository tipoEmpreendimentoRepository;

    @Autowired
    private TipoEmpreendimentoService tipoEmpreendimentoService;

    @Autowired
    private TipoEmpreendimentoQueryService tipoEmpreendimentoQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTipoEmpreendimentoMockMvc;

    private TipoEmpreendimento tipoEmpreendimento;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoEmpreendimento createEntity(EntityManager em) {
        TipoEmpreendimento tipoEmpreendimento = new TipoEmpreendimento()
            .descricao(DEFAULT_DESCRICAO);
        return tipoEmpreendimento;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoEmpreendimento createUpdatedEntity(EntityManager em) {
        TipoEmpreendimento tipoEmpreendimento = new TipoEmpreendimento()
            .descricao(UPDATED_DESCRICAO);
        return tipoEmpreendimento;
    }

    @BeforeEach
    public void initTest() {
        tipoEmpreendimento = createEntity(em);
    }

    @Test
    @Transactional
    public void createTipoEmpreendimento() throws Exception {
        int databaseSizeBeforeCreate = tipoEmpreendimentoRepository.findAll().size();
        // Create the TipoEmpreendimento
        restTipoEmpreendimentoMockMvc.perform(post("/api/tipo-empreendimentos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tipoEmpreendimento)))
            .andExpect(status().isCreated());

        // Validate the TipoEmpreendimento in the database
        List<TipoEmpreendimento> tipoEmpreendimentoList = tipoEmpreendimentoRepository.findAll();
        assertThat(tipoEmpreendimentoList).hasSize(databaseSizeBeforeCreate + 1);
        TipoEmpreendimento testTipoEmpreendimento = tipoEmpreendimentoList.get(tipoEmpreendimentoList.size() - 1);
        assertThat(testTipoEmpreendimento.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
    }

    @Test
    @Transactional
    public void createTipoEmpreendimentoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tipoEmpreendimentoRepository.findAll().size();

        // Create the TipoEmpreendimento with an existing ID
        tipoEmpreendimento.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTipoEmpreendimentoMockMvc.perform(post("/api/tipo-empreendimentos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tipoEmpreendimento)))
            .andExpect(status().isBadRequest());

        // Validate the TipoEmpreendimento in the database
        List<TipoEmpreendimento> tipoEmpreendimentoList = tipoEmpreendimentoRepository.findAll();
        assertThat(tipoEmpreendimentoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTipoEmpreendimentos() throws Exception {
        // Initialize the database
        tipoEmpreendimentoRepository.saveAndFlush(tipoEmpreendimento);

        // Get all the tipoEmpreendimentoList
        restTipoEmpreendimentoMockMvc.perform(get("/api/tipo-empreendimentos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoEmpreendimento.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)));
    }
    
    @Test
    @Transactional
    public void getTipoEmpreendimento() throws Exception {
        // Initialize the database
        tipoEmpreendimentoRepository.saveAndFlush(tipoEmpreendimento);

        // Get the tipoEmpreendimento
        restTipoEmpreendimentoMockMvc.perform(get("/api/tipo-empreendimentos/{id}", tipoEmpreendimento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tipoEmpreendimento.getId().intValue()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO));
    }


    @Test
    @Transactional
    public void getTipoEmpreendimentosByIdFiltering() throws Exception {
        // Initialize the database
        tipoEmpreendimentoRepository.saveAndFlush(tipoEmpreendimento);

        Long id = tipoEmpreendimento.getId();

        defaultTipoEmpreendimentoShouldBeFound("id.equals=" + id);
        defaultTipoEmpreendimentoShouldNotBeFound("id.notEquals=" + id);

        defaultTipoEmpreendimentoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTipoEmpreendimentoShouldNotBeFound("id.greaterThan=" + id);

        defaultTipoEmpreendimentoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTipoEmpreendimentoShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllTipoEmpreendimentosByDescricaoIsEqualToSomething() throws Exception {
        // Initialize the database
        tipoEmpreendimentoRepository.saveAndFlush(tipoEmpreendimento);

        // Get all the tipoEmpreendimentoList where descricao equals to DEFAULT_DESCRICAO
        defaultTipoEmpreendimentoShouldBeFound("descricao.equals=" + DEFAULT_DESCRICAO);

        // Get all the tipoEmpreendimentoList where descricao equals to UPDATED_DESCRICAO
        defaultTipoEmpreendimentoShouldNotBeFound("descricao.equals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllTipoEmpreendimentosByDescricaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tipoEmpreendimentoRepository.saveAndFlush(tipoEmpreendimento);

        // Get all the tipoEmpreendimentoList where descricao not equals to DEFAULT_DESCRICAO
        defaultTipoEmpreendimentoShouldNotBeFound("descricao.notEquals=" + DEFAULT_DESCRICAO);

        // Get all the tipoEmpreendimentoList where descricao not equals to UPDATED_DESCRICAO
        defaultTipoEmpreendimentoShouldBeFound("descricao.notEquals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllTipoEmpreendimentosByDescricaoIsInShouldWork() throws Exception {
        // Initialize the database
        tipoEmpreendimentoRepository.saveAndFlush(tipoEmpreendimento);

        // Get all the tipoEmpreendimentoList where descricao in DEFAULT_DESCRICAO or UPDATED_DESCRICAO
        defaultTipoEmpreendimentoShouldBeFound("descricao.in=" + DEFAULT_DESCRICAO + "," + UPDATED_DESCRICAO);

        // Get all the tipoEmpreendimentoList where descricao equals to UPDATED_DESCRICAO
        defaultTipoEmpreendimentoShouldNotBeFound("descricao.in=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllTipoEmpreendimentosByDescricaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        tipoEmpreendimentoRepository.saveAndFlush(tipoEmpreendimento);

        // Get all the tipoEmpreendimentoList where descricao is not null
        defaultTipoEmpreendimentoShouldBeFound("descricao.specified=true");

        // Get all the tipoEmpreendimentoList where descricao is null
        defaultTipoEmpreendimentoShouldNotBeFound("descricao.specified=false");
    }
                @Test
    @Transactional
    public void getAllTipoEmpreendimentosByDescricaoContainsSomething() throws Exception {
        // Initialize the database
        tipoEmpreendimentoRepository.saveAndFlush(tipoEmpreendimento);

        // Get all the tipoEmpreendimentoList where descricao contains DEFAULT_DESCRICAO
        defaultTipoEmpreendimentoShouldBeFound("descricao.contains=" + DEFAULT_DESCRICAO);

        // Get all the tipoEmpreendimentoList where descricao contains UPDATED_DESCRICAO
        defaultTipoEmpreendimentoShouldNotBeFound("descricao.contains=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllTipoEmpreendimentosByDescricaoNotContainsSomething() throws Exception {
        // Initialize the database
        tipoEmpreendimentoRepository.saveAndFlush(tipoEmpreendimento);

        // Get all the tipoEmpreendimentoList where descricao does not contain DEFAULT_DESCRICAO
        defaultTipoEmpreendimentoShouldNotBeFound("descricao.doesNotContain=" + DEFAULT_DESCRICAO);

        // Get all the tipoEmpreendimentoList where descricao does not contain UPDATED_DESCRICAO
        defaultTipoEmpreendimentoShouldBeFound("descricao.doesNotContain=" + UPDATED_DESCRICAO);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTipoEmpreendimentoShouldBeFound(String filter) throws Exception {
        restTipoEmpreendimentoMockMvc.perform(get("/api/tipo-empreendimentos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoEmpreendimento.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)));

        // Check, that the count call also returns 1
        restTipoEmpreendimentoMockMvc.perform(get("/api/tipo-empreendimentos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTipoEmpreendimentoShouldNotBeFound(String filter) throws Exception {
        restTipoEmpreendimentoMockMvc.perform(get("/api/tipo-empreendimentos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTipoEmpreendimentoMockMvc.perform(get("/api/tipo-empreendimentos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingTipoEmpreendimento() throws Exception {
        // Get the tipoEmpreendimento
        restTipoEmpreendimentoMockMvc.perform(get("/api/tipo-empreendimentos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTipoEmpreendimento() throws Exception {
        // Initialize the database
        tipoEmpreendimentoService.save(tipoEmpreendimento);

        int databaseSizeBeforeUpdate = tipoEmpreendimentoRepository.findAll().size();

        // Update the tipoEmpreendimento
        TipoEmpreendimento updatedTipoEmpreendimento = tipoEmpreendimentoRepository.findById(tipoEmpreendimento.getId()).get();
        // Disconnect from session so that the updates on updatedTipoEmpreendimento are not directly saved in db
        em.detach(updatedTipoEmpreendimento);
        updatedTipoEmpreendimento
            .descricao(UPDATED_DESCRICAO);

        restTipoEmpreendimentoMockMvc.perform(put("/api/tipo-empreendimentos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTipoEmpreendimento)))
            .andExpect(status().isOk());

        // Validate the TipoEmpreendimento in the database
        List<TipoEmpreendimento> tipoEmpreendimentoList = tipoEmpreendimentoRepository.findAll();
        assertThat(tipoEmpreendimentoList).hasSize(databaseSizeBeforeUpdate);
        TipoEmpreendimento testTipoEmpreendimento = tipoEmpreendimentoList.get(tipoEmpreendimentoList.size() - 1);
        assertThat(testTipoEmpreendimento.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void updateNonExistingTipoEmpreendimento() throws Exception {
        int databaseSizeBeforeUpdate = tipoEmpreendimentoRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTipoEmpreendimentoMockMvc.perform(put("/api/tipo-empreendimentos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tipoEmpreendimento)))
            .andExpect(status().isBadRequest());

        // Validate the TipoEmpreendimento in the database
        List<TipoEmpreendimento> tipoEmpreendimentoList = tipoEmpreendimentoRepository.findAll();
        assertThat(tipoEmpreendimentoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTipoEmpreendimento() throws Exception {
        // Initialize the database
        tipoEmpreendimentoService.save(tipoEmpreendimento);

        int databaseSizeBeforeDelete = tipoEmpreendimentoRepository.findAll().size();

        // Delete the tipoEmpreendimento
        restTipoEmpreendimentoMockMvc.perform(delete("/api/tipo-empreendimentos/{id}", tipoEmpreendimento.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TipoEmpreendimento> tipoEmpreendimentoList = tipoEmpreendimentoRepository.findAll();
        assertThat(tipoEmpreendimentoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
