package br.com.cidha.web.rest;

import br.com.cidha.CidhaApp;
import br.com.cidha.domain.Comarca;
import br.com.cidha.domain.Processo;
import br.com.cidha.repository.ComarcaRepository;
import br.com.cidha.service.ComarcaService;
import br.com.cidha.service.dto.ComarcaCriteria;
import br.com.cidha.service.ComarcaQueryService;

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
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ComarcaResource} REST controller.
 */
@SpringBootTest(classes = CidhaApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ComarcaResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_CODIGO_CNJ = new BigDecimal(1);
    private static final BigDecimal UPDATED_CODIGO_CNJ = new BigDecimal(2);
    private static final BigDecimal SMALLER_CODIGO_CNJ = new BigDecimal(1 - 1);

    @Autowired
    private ComarcaRepository comarcaRepository;

    @Autowired
    private ComarcaService comarcaService;

    @Autowired
    private ComarcaQueryService comarcaQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restComarcaMockMvc;

    private Comarca comarca;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Comarca createEntity(EntityManager em) {
        Comarca comarca = new Comarca()
            .nome(DEFAULT_NOME)
            .codigoCnj(DEFAULT_CODIGO_CNJ);
        return comarca;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Comarca createUpdatedEntity(EntityManager em) {
        Comarca comarca = new Comarca()
            .nome(UPDATED_NOME)
            .codigoCnj(UPDATED_CODIGO_CNJ);
        return comarca;
    }

    @BeforeEach
    public void initTest() {
        comarca = createEntity(em);
    }

    @Test
    @Transactional
    public void createComarca() throws Exception {
        int databaseSizeBeforeCreate = comarcaRepository.findAll().size();
        // Create the Comarca
        restComarcaMockMvc.perform(post("/api/comarcas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(comarca)))
            .andExpect(status().isCreated());

        // Validate the Comarca in the database
        List<Comarca> comarcaList = comarcaRepository.findAll();
        assertThat(comarcaList).hasSize(databaseSizeBeforeCreate + 1);
        Comarca testComarca = comarcaList.get(comarcaList.size() - 1);
        assertThat(testComarca.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testComarca.getCodigoCnj()).isEqualTo(DEFAULT_CODIGO_CNJ);
    }

    @Test
    @Transactional
    public void createComarcaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = comarcaRepository.findAll().size();

        // Create the Comarca with an existing ID
        comarca.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restComarcaMockMvc.perform(post("/api/comarcas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(comarca)))
            .andExpect(status().isBadRequest());

        // Validate the Comarca in the database
        List<Comarca> comarcaList = comarcaRepository.findAll();
        assertThat(comarcaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllComarcas() throws Exception {
        // Initialize the database
        comarcaRepository.saveAndFlush(comarca);

        // Get all the comarcaList
        restComarcaMockMvc.perform(get("/api/comarcas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(comarca.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].codigoCnj").value(hasItem(DEFAULT_CODIGO_CNJ.intValue())));
    }
    
    @Test
    @Transactional
    public void getComarca() throws Exception {
        // Initialize the database
        comarcaRepository.saveAndFlush(comarca);

        // Get the comarca
        restComarcaMockMvc.perform(get("/api/comarcas/{id}", comarca.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(comarca.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.codigoCnj").value(DEFAULT_CODIGO_CNJ.intValue()));
    }


    @Test
    @Transactional
    public void getComarcasByIdFiltering() throws Exception {
        // Initialize the database
        comarcaRepository.saveAndFlush(comarca);

        Long id = comarca.getId();

        defaultComarcaShouldBeFound("id.equals=" + id);
        defaultComarcaShouldNotBeFound("id.notEquals=" + id);

        defaultComarcaShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultComarcaShouldNotBeFound("id.greaterThan=" + id);

        defaultComarcaShouldBeFound("id.lessThanOrEqual=" + id);
        defaultComarcaShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllComarcasByNomeIsEqualToSomething() throws Exception {
        // Initialize the database
        comarcaRepository.saveAndFlush(comarca);

        // Get all the comarcaList where nome equals to DEFAULT_NOME
        defaultComarcaShouldBeFound("nome.equals=" + DEFAULT_NOME);

        // Get all the comarcaList where nome equals to UPDATED_NOME
        defaultComarcaShouldNotBeFound("nome.equals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllComarcasByNomeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        comarcaRepository.saveAndFlush(comarca);

        // Get all the comarcaList where nome not equals to DEFAULT_NOME
        defaultComarcaShouldNotBeFound("nome.notEquals=" + DEFAULT_NOME);

        // Get all the comarcaList where nome not equals to UPDATED_NOME
        defaultComarcaShouldBeFound("nome.notEquals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllComarcasByNomeIsInShouldWork() throws Exception {
        // Initialize the database
        comarcaRepository.saveAndFlush(comarca);

        // Get all the comarcaList where nome in DEFAULT_NOME or UPDATED_NOME
        defaultComarcaShouldBeFound("nome.in=" + DEFAULT_NOME + "," + UPDATED_NOME);

        // Get all the comarcaList where nome equals to UPDATED_NOME
        defaultComarcaShouldNotBeFound("nome.in=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllComarcasByNomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        comarcaRepository.saveAndFlush(comarca);

        // Get all the comarcaList where nome is not null
        defaultComarcaShouldBeFound("nome.specified=true");

        // Get all the comarcaList where nome is null
        defaultComarcaShouldNotBeFound("nome.specified=false");
    }
                @Test
    @Transactional
    public void getAllComarcasByNomeContainsSomething() throws Exception {
        // Initialize the database
        comarcaRepository.saveAndFlush(comarca);

        // Get all the comarcaList where nome contains DEFAULT_NOME
        defaultComarcaShouldBeFound("nome.contains=" + DEFAULT_NOME);

        // Get all the comarcaList where nome contains UPDATED_NOME
        defaultComarcaShouldNotBeFound("nome.contains=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllComarcasByNomeNotContainsSomething() throws Exception {
        // Initialize the database
        comarcaRepository.saveAndFlush(comarca);

        // Get all the comarcaList where nome does not contain DEFAULT_NOME
        defaultComarcaShouldNotBeFound("nome.doesNotContain=" + DEFAULT_NOME);

        // Get all the comarcaList where nome does not contain UPDATED_NOME
        defaultComarcaShouldBeFound("nome.doesNotContain=" + UPDATED_NOME);
    }


    @Test
    @Transactional
    public void getAllComarcasByCodigoCnjIsEqualToSomething() throws Exception {
        // Initialize the database
        comarcaRepository.saveAndFlush(comarca);

        // Get all the comarcaList where codigoCnj equals to DEFAULT_CODIGO_CNJ
        defaultComarcaShouldBeFound("codigoCnj.equals=" + DEFAULT_CODIGO_CNJ);

        // Get all the comarcaList where codigoCnj equals to UPDATED_CODIGO_CNJ
        defaultComarcaShouldNotBeFound("codigoCnj.equals=" + UPDATED_CODIGO_CNJ);
    }

    @Test
    @Transactional
    public void getAllComarcasByCodigoCnjIsNotEqualToSomething() throws Exception {
        // Initialize the database
        comarcaRepository.saveAndFlush(comarca);

        // Get all the comarcaList where codigoCnj not equals to DEFAULT_CODIGO_CNJ
        defaultComarcaShouldNotBeFound("codigoCnj.notEquals=" + DEFAULT_CODIGO_CNJ);

        // Get all the comarcaList where codigoCnj not equals to UPDATED_CODIGO_CNJ
        defaultComarcaShouldBeFound("codigoCnj.notEquals=" + UPDATED_CODIGO_CNJ);
    }

    @Test
    @Transactional
    public void getAllComarcasByCodigoCnjIsInShouldWork() throws Exception {
        // Initialize the database
        comarcaRepository.saveAndFlush(comarca);

        // Get all the comarcaList where codigoCnj in DEFAULT_CODIGO_CNJ or UPDATED_CODIGO_CNJ
        defaultComarcaShouldBeFound("codigoCnj.in=" + DEFAULT_CODIGO_CNJ + "," + UPDATED_CODIGO_CNJ);

        // Get all the comarcaList where codigoCnj equals to UPDATED_CODIGO_CNJ
        defaultComarcaShouldNotBeFound("codigoCnj.in=" + UPDATED_CODIGO_CNJ);
    }

    @Test
    @Transactional
    public void getAllComarcasByCodigoCnjIsNullOrNotNull() throws Exception {
        // Initialize the database
        comarcaRepository.saveAndFlush(comarca);

        // Get all the comarcaList where codigoCnj is not null
        defaultComarcaShouldBeFound("codigoCnj.specified=true");

        // Get all the comarcaList where codigoCnj is null
        defaultComarcaShouldNotBeFound("codigoCnj.specified=false");
    }

    @Test
    @Transactional
    public void getAllComarcasByCodigoCnjIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        comarcaRepository.saveAndFlush(comarca);

        // Get all the comarcaList where codigoCnj is greater than or equal to DEFAULT_CODIGO_CNJ
        defaultComarcaShouldBeFound("codigoCnj.greaterThanOrEqual=" + DEFAULT_CODIGO_CNJ);

        // Get all the comarcaList where codigoCnj is greater than or equal to UPDATED_CODIGO_CNJ
        defaultComarcaShouldNotBeFound("codigoCnj.greaterThanOrEqual=" + UPDATED_CODIGO_CNJ);
    }

    @Test
    @Transactional
    public void getAllComarcasByCodigoCnjIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        comarcaRepository.saveAndFlush(comarca);

        // Get all the comarcaList where codigoCnj is less than or equal to DEFAULT_CODIGO_CNJ
        defaultComarcaShouldBeFound("codigoCnj.lessThanOrEqual=" + DEFAULT_CODIGO_CNJ);

        // Get all the comarcaList where codigoCnj is less than or equal to SMALLER_CODIGO_CNJ
        defaultComarcaShouldNotBeFound("codigoCnj.lessThanOrEqual=" + SMALLER_CODIGO_CNJ);
    }

    @Test
    @Transactional
    public void getAllComarcasByCodigoCnjIsLessThanSomething() throws Exception {
        // Initialize the database
        comarcaRepository.saveAndFlush(comarca);

        // Get all the comarcaList where codigoCnj is less than DEFAULT_CODIGO_CNJ
        defaultComarcaShouldNotBeFound("codigoCnj.lessThan=" + DEFAULT_CODIGO_CNJ);

        // Get all the comarcaList where codigoCnj is less than UPDATED_CODIGO_CNJ
        defaultComarcaShouldBeFound("codigoCnj.lessThan=" + UPDATED_CODIGO_CNJ);
    }

    @Test
    @Transactional
    public void getAllComarcasByCodigoCnjIsGreaterThanSomething() throws Exception {
        // Initialize the database
        comarcaRepository.saveAndFlush(comarca);

        // Get all the comarcaList where codigoCnj is greater than DEFAULT_CODIGO_CNJ
        defaultComarcaShouldNotBeFound("codigoCnj.greaterThan=" + DEFAULT_CODIGO_CNJ);

        // Get all the comarcaList where codigoCnj is greater than SMALLER_CODIGO_CNJ
        defaultComarcaShouldBeFound("codigoCnj.greaterThan=" + SMALLER_CODIGO_CNJ);
    }


    @Test
    @Transactional
    public void getAllComarcasByProcessoIsEqualToSomething() throws Exception {
        // Initialize the database
        comarcaRepository.saveAndFlush(comarca);
        Processo processo = ProcessoResourceIT.createEntity(em);
        em.persist(processo);
        em.flush();
        comarca.addProcesso(processo);
        comarcaRepository.saveAndFlush(comarca);
        Long processoId = processo.getId();

        // Get all the comarcaList where processo equals to processoId
        defaultComarcaShouldBeFound("processoId.equals=" + processoId);

        // Get all the comarcaList where processo equals to processoId + 1
        defaultComarcaShouldNotBeFound("processoId.equals=" + (processoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultComarcaShouldBeFound(String filter) throws Exception {
        restComarcaMockMvc.perform(get("/api/comarcas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(comarca.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].codigoCnj").value(hasItem(DEFAULT_CODIGO_CNJ.intValue())));

        // Check, that the count call also returns 1
        restComarcaMockMvc.perform(get("/api/comarcas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultComarcaShouldNotBeFound(String filter) throws Exception {
        restComarcaMockMvc.perform(get("/api/comarcas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restComarcaMockMvc.perform(get("/api/comarcas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingComarca() throws Exception {
        // Get the comarca
        restComarcaMockMvc.perform(get("/api/comarcas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateComarca() throws Exception {
        // Initialize the database
        comarcaService.save(comarca);

        int databaseSizeBeforeUpdate = comarcaRepository.findAll().size();

        // Update the comarca
        Comarca updatedComarca = comarcaRepository.findById(comarca.getId()).get();
        // Disconnect from session so that the updates on updatedComarca are not directly saved in db
        em.detach(updatedComarca);
        updatedComarca
            .nome(UPDATED_NOME)
            .codigoCnj(UPDATED_CODIGO_CNJ);

        restComarcaMockMvc.perform(put("/api/comarcas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedComarca)))
            .andExpect(status().isOk());

        // Validate the Comarca in the database
        List<Comarca> comarcaList = comarcaRepository.findAll();
        assertThat(comarcaList).hasSize(databaseSizeBeforeUpdate);
        Comarca testComarca = comarcaList.get(comarcaList.size() - 1);
        assertThat(testComarca.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testComarca.getCodigoCnj()).isEqualTo(UPDATED_CODIGO_CNJ);
    }

    @Test
    @Transactional
    public void updateNonExistingComarca() throws Exception {
        int databaseSizeBeforeUpdate = comarcaRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restComarcaMockMvc.perform(put("/api/comarcas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(comarca)))
            .andExpect(status().isBadRequest());

        // Validate the Comarca in the database
        List<Comarca> comarcaList = comarcaRepository.findAll();
        assertThat(comarcaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteComarca() throws Exception {
        // Initialize the database
        comarcaService.save(comarca);

        int databaseSizeBeforeDelete = comarcaRepository.findAll().size();

        // Delete the comarca
        restComarcaMockMvc.perform(delete("/api/comarcas/{id}", comarca.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Comarca> comarcaList = comarcaRepository.findAll();
        assertThat(comarcaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
