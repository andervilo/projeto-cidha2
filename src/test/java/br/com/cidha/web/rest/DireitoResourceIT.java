package br.com.cidha.web.rest;

import br.com.cidha.CidhaApp;
import br.com.cidha.domain.Direito;
import br.com.cidha.domain.ProcessoConflito;
import br.com.cidha.repository.DireitoRepository;
import br.com.cidha.service.DireitoService;
import br.com.cidha.service.dto.DireitoCriteria;
import br.com.cidha.service.DireitoQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link DireitoResource} REST controller.
 */
@SpringBootTest(classes = CidhaApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class DireitoResourceIT {

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    @Autowired
    private DireitoRepository direitoRepository;

    @Autowired
    private DireitoService direitoService;

    @Autowired
    private DireitoQueryService direitoQueryService;

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
        Direito direito = new Direito()
            .descricao(DEFAULT_DESCRICAO);
        return direito;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Direito createUpdatedEntity(EntityManager em) {
        Direito direito = new Direito()
            .descricao(UPDATED_DESCRICAO);
        return direito;
    }

    @BeforeEach
    public void initTest() {
        direito = createEntity(em);
    }

    @Test
    @Transactional
    public void createDireito() throws Exception {
        int databaseSizeBeforeCreate = direitoRepository.findAll().size();
        // Create the Direito
        restDireitoMockMvc.perform(post("/api/direitos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(direito)))
            .andExpect(status().isCreated());

        // Validate the Direito in the database
        List<Direito> direitoList = direitoRepository.findAll();
        assertThat(direitoList).hasSize(databaseSizeBeforeCreate + 1);
        Direito testDireito = direitoList.get(direitoList.size() - 1);
        assertThat(testDireito.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
    }

    @Test
    @Transactional
    public void createDireitoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = direitoRepository.findAll().size();

        // Create the Direito with an existing ID
        direito.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDireitoMockMvc.perform(post("/api/direitos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(direito)))
            .andExpect(status().isBadRequest());

        // Validate the Direito in the database
        List<Direito> direitoList = direitoRepository.findAll();
        assertThat(direitoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllDireitos() throws Exception {
        // Initialize the database
        direitoRepository.saveAndFlush(direito);

        // Get all the direitoList
        restDireitoMockMvc.perform(get("/api/direitos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(direito.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())));
    }
    
    @Test
    @Transactional
    public void getDireito() throws Exception {
        // Initialize the database
        direitoRepository.saveAndFlush(direito);

        // Get the direito
        restDireitoMockMvc.perform(get("/api/direitos/{id}", direito.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(direito.getId().intValue()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()));
    }


    @Test
    @Transactional
    public void getDireitosByIdFiltering() throws Exception {
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
    public void getAllDireitosByProcessoConflitoIsEqualToSomething() throws Exception {
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

        // Get all the direitoList where processoConflito equals to processoConflitoId + 1
        defaultDireitoShouldNotBeFound("processoConflitoId.equals=" + (processoConflitoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDireitoShouldBeFound(String filter) throws Exception {
        restDireitoMockMvc.perform(get("/api/direitos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(direito.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())));

        // Check, that the count call also returns 1
        restDireitoMockMvc.perform(get("/api/direitos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDireitoShouldNotBeFound(String filter) throws Exception {
        restDireitoMockMvc.perform(get("/api/direitos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDireitoMockMvc.perform(get("/api/direitos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingDireito() throws Exception {
        // Get the direito
        restDireitoMockMvc.perform(get("/api/direitos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDireito() throws Exception {
        // Initialize the database
        direitoService.save(direito);

        int databaseSizeBeforeUpdate = direitoRepository.findAll().size();

        // Update the direito
        Direito updatedDireito = direitoRepository.findById(direito.getId()).get();
        // Disconnect from session so that the updates on updatedDireito are not directly saved in db
        em.detach(updatedDireito);
        updatedDireito
            .descricao(UPDATED_DESCRICAO);

        restDireitoMockMvc.perform(put("/api/direitos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedDireito)))
            .andExpect(status().isOk());

        // Validate the Direito in the database
        List<Direito> direitoList = direitoRepository.findAll();
        assertThat(direitoList).hasSize(databaseSizeBeforeUpdate);
        Direito testDireito = direitoList.get(direitoList.size() - 1);
        assertThat(testDireito.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void updateNonExistingDireito() throws Exception {
        int databaseSizeBeforeUpdate = direitoRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDireitoMockMvc.perform(put("/api/direitos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(direito)))
            .andExpect(status().isBadRequest());

        // Validate the Direito in the database
        List<Direito> direitoList = direitoRepository.findAll();
        assertThat(direitoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDireito() throws Exception {
        // Initialize the database
        direitoService.save(direito);

        int databaseSizeBeforeDelete = direitoRepository.findAll().size();

        // Delete the direito
        restDireitoMockMvc.perform(delete("/api/direitos/{id}", direito.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Direito> direitoList = direitoRepository.findAll();
        assertThat(direitoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
