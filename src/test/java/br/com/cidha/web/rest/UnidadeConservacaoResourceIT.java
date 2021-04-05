package br.com.cidha.web.rest;

import br.com.cidha.CidhaApp;
import br.com.cidha.domain.UnidadeConservacao;
import br.com.cidha.domain.Processo;
import br.com.cidha.repository.UnidadeConservacaoRepository;
import br.com.cidha.service.UnidadeConservacaoService;
import br.com.cidha.service.dto.UnidadeConservacaoCriteria;
import br.com.cidha.service.UnidadeConservacaoQueryService;

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
 * Integration tests for the {@link UnidadeConservacaoResource} REST controller.
 */
@SpringBootTest(classes = CidhaApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class UnidadeConservacaoResourceIT {

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    @Autowired
    private UnidadeConservacaoRepository unidadeConservacaoRepository;

    @Autowired
    private UnidadeConservacaoService unidadeConservacaoService;

    @Autowired
    private UnidadeConservacaoQueryService unidadeConservacaoQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUnidadeConservacaoMockMvc;

    private UnidadeConservacao unidadeConservacao;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UnidadeConservacao createEntity(EntityManager em) {
        UnidadeConservacao unidadeConservacao = new UnidadeConservacao()
            .descricao(DEFAULT_DESCRICAO);
        return unidadeConservacao;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UnidadeConservacao createUpdatedEntity(EntityManager em) {
        UnidadeConservacao unidadeConservacao = new UnidadeConservacao()
            .descricao(UPDATED_DESCRICAO);
        return unidadeConservacao;
    }

    @BeforeEach
    public void initTest() {
        unidadeConservacao = createEntity(em);
    }

    @Test
    @Transactional
    public void createUnidadeConservacao() throws Exception {
        int databaseSizeBeforeCreate = unidadeConservacaoRepository.findAll().size();
        // Create the UnidadeConservacao
        restUnidadeConservacaoMockMvc.perform(post("/api/unidade-conservacaos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(unidadeConservacao)))
            .andExpect(status().isCreated());

        // Validate the UnidadeConservacao in the database
        List<UnidadeConservacao> unidadeConservacaoList = unidadeConservacaoRepository.findAll();
        assertThat(unidadeConservacaoList).hasSize(databaseSizeBeforeCreate + 1);
        UnidadeConservacao testUnidadeConservacao = unidadeConservacaoList.get(unidadeConservacaoList.size() - 1);
        assertThat(testUnidadeConservacao.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
    }

    @Test
    @Transactional
    public void createUnidadeConservacaoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = unidadeConservacaoRepository.findAll().size();

        // Create the UnidadeConservacao with an existing ID
        unidadeConservacao.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUnidadeConservacaoMockMvc.perform(post("/api/unidade-conservacaos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(unidadeConservacao)))
            .andExpect(status().isBadRequest());

        // Validate the UnidadeConservacao in the database
        List<UnidadeConservacao> unidadeConservacaoList = unidadeConservacaoRepository.findAll();
        assertThat(unidadeConservacaoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllUnidadeConservacaos() throws Exception {
        // Initialize the database
        unidadeConservacaoRepository.saveAndFlush(unidadeConservacao);

        // Get all the unidadeConservacaoList
        restUnidadeConservacaoMockMvc.perform(get("/api/unidade-conservacaos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(unidadeConservacao.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)));
    }
    
    @Test
    @Transactional
    public void getUnidadeConservacao() throws Exception {
        // Initialize the database
        unidadeConservacaoRepository.saveAndFlush(unidadeConservacao);

        // Get the unidadeConservacao
        restUnidadeConservacaoMockMvc.perform(get("/api/unidade-conservacaos/{id}", unidadeConservacao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(unidadeConservacao.getId().intValue()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO));
    }


    @Test
    @Transactional
    public void getUnidadeConservacaosByIdFiltering() throws Exception {
        // Initialize the database
        unidadeConservacaoRepository.saveAndFlush(unidadeConservacao);

        Long id = unidadeConservacao.getId();

        defaultUnidadeConservacaoShouldBeFound("id.equals=" + id);
        defaultUnidadeConservacaoShouldNotBeFound("id.notEquals=" + id);

        defaultUnidadeConservacaoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultUnidadeConservacaoShouldNotBeFound("id.greaterThan=" + id);

        defaultUnidadeConservacaoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultUnidadeConservacaoShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllUnidadeConservacaosByDescricaoIsEqualToSomething() throws Exception {
        // Initialize the database
        unidadeConservacaoRepository.saveAndFlush(unidadeConservacao);

        // Get all the unidadeConservacaoList where descricao equals to DEFAULT_DESCRICAO
        defaultUnidadeConservacaoShouldBeFound("descricao.equals=" + DEFAULT_DESCRICAO);

        // Get all the unidadeConservacaoList where descricao equals to UPDATED_DESCRICAO
        defaultUnidadeConservacaoShouldNotBeFound("descricao.equals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllUnidadeConservacaosByDescricaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        unidadeConservacaoRepository.saveAndFlush(unidadeConservacao);

        // Get all the unidadeConservacaoList where descricao not equals to DEFAULT_DESCRICAO
        defaultUnidadeConservacaoShouldNotBeFound("descricao.notEquals=" + DEFAULT_DESCRICAO);

        // Get all the unidadeConservacaoList where descricao not equals to UPDATED_DESCRICAO
        defaultUnidadeConservacaoShouldBeFound("descricao.notEquals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllUnidadeConservacaosByDescricaoIsInShouldWork() throws Exception {
        // Initialize the database
        unidadeConservacaoRepository.saveAndFlush(unidadeConservacao);

        // Get all the unidadeConservacaoList where descricao in DEFAULT_DESCRICAO or UPDATED_DESCRICAO
        defaultUnidadeConservacaoShouldBeFound("descricao.in=" + DEFAULT_DESCRICAO + "," + UPDATED_DESCRICAO);

        // Get all the unidadeConservacaoList where descricao equals to UPDATED_DESCRICAO
        defaultUnidadeConservacaoShouldNotBeFound("descricao.in=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllUnidadeConservacaosByDescricaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        unidadeConservacaoRepository.saveAndFlush(unidadeConservacao);

        // Get all the unidadeConservacaoList where descricao is not null
        defaultUnidadeConservacaoShouldBeFound("descricao.specified=true");

        // Get all the unidadeConservacaoList where descricao is null
        defaultUnidadeConservacaoShouldNotBeFound("descricao.specified=false");
    }
                @Test
    @Transactional
    public void getAllUnidadeConservacaosByDescricaoContainsSomething() throws Exception {
        // Initialize the database
        unidadeConservacaoRepository.saveAndFlush(unidadeConservacao);

        // Get all the unidadeConservacaoList where descricao contains DEFAULT_DESCRICAO
        defaultUnidadeConservacaoShouldBeFound("descricao.contains=" + DEFAULT_DESCRICAO);

        // Get all the unidadeConservacaoList where descricao contains UPDATED_DESCRICAO
        defaultUnidadeConservacaoShouldNotBeFound("descricao.contains=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllUnidadeConservacaosByDescricaoNotContainsSomething() throws Exception {
        // Initialize the database
        unidadeConservacaoRepository.saveAndFlush(unidadeConservacao);

        // Get all the unidadeConservacaoList where descricao does not contain DEFAULT_DESCRICAO
        defaultUnidadeConservacaoShouldNotBeFound("descricao.doesNotContain=" + DEFAULT_DESCRICAO);

        // Get all the unidadeConservacaoList where descricao does not contain UPDATED_DESCRICAO
        defaultUnidadeConservacaoShouldBeFound("descricao.doesNotContain=" + UPDATED_DESCRICAO);
    }


    @Test
    @Transactional
    public void getAllUnidadeConservacaosByProcessoIsEqualToSomething() throws Exception {
        // Initialize the database
        unidadeConservacaoRepository.saveAndFlush(unidadeConservacao);
        Processo processo = ProcessoResourceIT.createEntity(em);
        em.persist(processo);
        em.flush();
        unidadeConservacao.addProcesso(processo);
        unidadeConservacaoRepository.saveAndFlush(unidadeConservacao);
        Long processoId = processo.getId();

        // Get all the unidadeConservacaoList where processo equals to processoId
        defaultUnidadeConservacaoShouldBeFound("processoId.equals=" + processoId);

        // Get all the unidadeConservacaoList where processo equals to processoId + 1
        defaultUnidadeConservacaoShouldNotBeFound("processoId.equals=" + (processoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultUnidadeConservacaoShouldBeFound(String filter) throws Exception {
        restUnidadeConservacaoMockMvc.perform(get("/api/unidade-conservacaos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(unidadeConservacao.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)));

        // Check, that the count call also returns 1
        restUnidadeConservacaoMockMvc.perform(get("/api/unidade-conservacaos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultUnidadeConservacaoShouldNotBeFound(String filter) throws Exception {
        restUnidadeConservacaoMockMvc.perform(get("/api/unidade-conservacaos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restUnidadeConservacaoMockMvc.perform(get("/api/unidade-conservacaos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingUnidadeConservacao() throws Exception {
        // Get the unidadeConservacao
        restUnidadeConservacaoMockMvc.perform(get("/api/unidade-conservacaos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUnidadeConservacao() throws Exception {
        // Initialize the database
        unidadeConservacaoService.save(unidadeConservacao);

        int databaseSizeBeforeUpdate = unidadeConservacaoRepository.findAll().size();

        // Update the unidadeConservacao
        UnidadeConservacao updatedUnidadeConservacao = unidadeConservacaoRepository.findById(unidadeConservacao.getId()).get();
        // Disconnect from session so that the updates on updatedUnidadeConservacao are not directly saved in db
        em.detach(updatedUnidadeConservacao);
        updatedUnidadeConservacao
            .descricao(UPDATED_DESCRICAO);

        restUnidadeConservacaoMockMvc.perform(put("/api/unidade-conservacaos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedUnidadeConservacao)))
            .andExpect(status().isOk());

        // Validate the UnidadeConservacao in the database
        List<UnidadeConservacao> unidadeConservacaoList = unidadeConservacaoRepository.findAll();
        assertThat(unidadeConservacaoList).hasSize(databaseSizeBeforeUpdate);
        UnidadeConservacao testUnidadeConservacao = unidadeConservacaoList.get(unidadeConservacaoList.size() - 1);
        assertThat(testUnidadeConservacao.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void updateNonExistingUnidadeConservacao() throws Exception {
        int databaseSizeBeforeUpdate = unidadeConservacaoRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUnidadeConservacaoMockMvc.perform(put("/api/unidade-conservacaos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(unidadeConservacao)))
            .andExpect(status().isBadRequest());

        // Validate the UnidadeConservacao in the database
        List<UnidadeConservacao> unidadeConservacaoList = unidadeConservacaoRepository.findAll();
        assertThat(unidadeConservacaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUnidadeConservacao() throws Exception {
        // Initialize the database
        unidadeConservacaoService.save(unidadeConservacao);

        int databaseSizeBeforeDelete = unidadeConservacaoRepository.findAll().size();

        // Delete the unidadeConservacao
        restUnidadeConservacaoMockMvc.perform(delete("/api/unidade-conservacaos/{id}", unidadeConservacao.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UnidadeConservacao> unidadeConservacaoList = unidadeConservacaoRepository.findAll();
        assertThat(unidadeConservacaoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
