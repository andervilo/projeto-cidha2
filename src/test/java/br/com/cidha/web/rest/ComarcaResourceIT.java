package br.com.cidha.web.rest;

import static br.com.cidha.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.cidha.IntegrationTest;
import br.com.cidha.domain.Comarca;
import br.com.cidha.domain.Processo;
import br.com.cidha.repository.ComarcaRepository;
import br.com.cidha.service.criteria.ComarcaCriteria;
import java.math.BigDecimal;
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
 * Integration tests for the {@link ComarcaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ComarcaResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_CODIGO_CNJ = new BigDecimal(1);
    private static final BigDecimal UPDATED_CODIGO_CNJ = new BigDecimal(2);
    private static final BigDecimal SMALLER_CODIGO_CNJ = new BigDecimal(1 - 1);

    private static final String ENTITY_API_URL = "/api/comarcas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ComarcaRepository comarcaRepository;

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
        Comarca comarca = new Comarca().nome(DEFAULT_NOME).codigoCnj(DEFAULT_CODIGO_CNJ);
        return comarca;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Comarca createUpdatedEntity(EntityManager em) {
        Comarca comarca = new Comarca().nome(UPDATED_NOME).codigoCnj(UPDATED_CODIGO_CNJ);
        return comarca;
    }

    @BeforeEach
    public void initTest() {
        comarca = createEntity(em);
    }

    @Test
    @Transactional
    void createComarca() throws Exception {
        int databaseSizeBeforeCreate = comarcaRepository.findAll().size();
        // Create the Comarca
        restComarcaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(comarca)))
            .andExpect(status().isCreated());

        // Validate the Comarca in the database
        List<Comarca> comarcaList = comarcaRepository.findAll();
        assertThat(comarcaList).hasSize(databaseSizeBeforeCreate + 1);
        Comarca testComarca = comarcaList.get(comarcaList.size() - 1);
        assertThat(testComarca.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testComarca.getCodigoCnj()).isEqualByComparingTo(DEFAULT_CODIGO_CNJ);
    }

    @Test
    @Transactional
    void createComarcaWithExistingId() throws Exception {
        // Create the Comarca with an existing ID
        comarca.setId(1L);

        int databaseSizeBeforeCreate = comarcaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restComarcaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(comarca)))
            .andExpect(status().isBadRequest());

        // Validate the Comarca in the database
        List<Comarca> comarcaList = comarcaRepository.findAll();
        assertThat(comarcaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllComarcas() throws Exception {
        // Initialize the database
        comarcaRepository.saveAndFlush(comarca);

        // Get all the comarcaList
        restComarcaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(comarca.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].codigoCnj").value(hasItem(sameNumber(DEFAULT_CODIGO_CNJ))));
    }

    @Test
    @Transactional
    void getComarca() throws Exception {
        // Initialize the database
        comarcaRepository.saveAndFlush(comarca);

        // Get the comarca
        restComarcaMockMvc
            .perform(get(ENTITY_API_URL_ID, comarca.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(comarca.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.codigoCnj").value(sameNumber(DEFAULT_CODIGO_CNJ)));
    }

    @Test
    @Transactional
    void getComarcasByIdFiltering() throws Exception {
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
    void getAllComarcasByNomeIsEqualToSomething() throws Exception {
        // Initialize the database
        comarcaRepository.saveAndFlush(comarca);

        // Get all the comarcaList where nome equals to DEFAULT_NOME
        defaultComarcaShouldBeFound("nome.equals=" + DEFAULT_NOME);

        // Get all the comarcaList where nome equals to UPDATED_NOME
        defaultComarcaShouldNotBeFound("nome.equals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllComarcasByNomeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        comarcaRepository.saveAndFlush(comarca);

        // Get all the comarcaList where nome not equals to DEFAULT_NOME
        defaultComarcaShouldNotBeFound("nome.notEquals=" + DEFAULT_NOME);

        // Get all the comarcaList where nome not equals to UPDATED_NOME
        defaultComarcaShouldBeFound("nome.notEquals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllComarcasByNomeIsInShouldWork() throws Exception {
        // Initialize the database
        comarcaRepository.saveAndFlush(comarca);

        // Get all the comarcaList where nome in DEFAULT_NOME or UPDATED_NOME
        defaultComarcaShouldBeFound("nome.in=" + DEFAULT_NOME + "," + UPDATED_NOME);

        // Get all the comarcaList where nome equals to UPDATED_NOME
        defaultComarcaShouldNotBeFound("nome.in=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllComarcasByNomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        comarcaRepository.saveAndFlush(comarca);

        // Get all the comarcaList where nome is not null
        defaultComarcaShouldBeFound("nome.specified=true");

        // Get all the comarcaList where nome is null
        defaultComarcaShouldNotBeFound("nome.specified=false");
    }

    @Test
    @Transactional
    void getAllComarcasByNomeContainsSomething() throws Exception {
        // Initialize the database
        comarcaRepository.saveAndFlush(comarca);

        // Get all the comarcaList where nome contains DEFAULT_NOME
        defaultComarcaShouldBeFound("nome.contains=" + DEFAULT_NOME);

        // Get all the comarcaList where nome contains UPDATED_NOME
        defaultComarcaShouldNotBeFound("nome.contains=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllComarcasByNomeNotContainsSomething() throws Exception {
        // Initialize the database
        comarcaRepository.saveAndFlush(comarca);

        // Get all the comarcaList where nome does not contain DEFAULT_NOME
        defaultComarcaShouldNotBeFound("nome.doesNotContain=" + DEFAULT_NOME);

        // Get all the comarcaList where nome does not contain UPDATED_NOME
        defaultComarcaShouldBeFound("nome.doesNotContain=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllComarcasByCodigoCnjIsEqualToSomething() throws Exception {
        // Initialize the database
        comarcaRepository.saveAndFlush(comarca);

        // Get all the comarcaList where codigoCnj equals to DEFAULT_CODIGO_CNJ
        defaultComarcaShouldBeFound("codigoCnj.equals=" + DEFAULT_CODIGO_CNJ);

        // Get all the comarcaList where codigoCnj equals to UPDATED_CODIGO_CNJ
        defaultComarcaShouldNotBeFound("codigoCnj.equals=" + UPDATED_CODIGO_CNJ);
    }

    @Test
    @Transactional
    void getAllComarcasByCodigoCnjIsNotEqualToSomething() throws Exception {
        // Initialize the database
        comarcaRepository.saveAndFlush(comarca);

        // Get all the comarcaList where codigoCnj not equals to DEFAULT_CODIGO_CNJ
        defaultComarcaShouldNotBeFound("codigoCnj.notEquals=" + DEFAULT_CODIGO_CNJ);

        // Get all the comarcaList where codigoCnj not equals to UPDATED_CODIGO_CNJ
        defaultComarcaShouldBeFound("codigoCnj.notEquals=" + UPDATED_CODIGO_CNJ);
    }

    @Test
    @Transactional
    void getAllComarcasByCodigoCnjIsInShouldWork() throws Exception {
        // Initialize the database
        comarcaRepository.saveAndFlush(comarca);

        // Get all the comarcaList where codigoCnj in DEFAULT_CODIGO_CNJ or UPDATED_CODIGO_CNJ
        defaultComarcaShouldBeFound("codigoCnj.in=" + DEFAULT_CODIGO_CNJ + "," + UPDATED_CODIGO_CNJ);

        // Get all the comarcaList where codigoCnj equals to UPDATED_CODIGO_CNJ
        defaultComarcaShouldNotBeFound("codigoCnj.in=" + UPDATED_CODIGO_CNJ);
    }

    @Test
    @Transactional
    void getAllComarcasByCodigoCnjIsNullOrNotNull() throws Exception {
        // Initialize the database
        comarcaRepository.saveAndFlush(comarca);

        // Get all the comarcaList where codigoCnj is not null
        defaultComarcaShouldBeFound("codigoCnj.specified=true");

        // Get all the comarcaList where codigoCnj is null
        defaultComarcaShouldNotBeFound("codigoCnj.specified=false");
    }

    @Test
    @Transactional
    void getAllComarcasByCodigoCnjIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        comarcaRepository.saveAndFlush(comarca);

        // Get all the comarcaList where codigoCnj is greater than or equal to DEFAULT_CODIGO_CNJ
        defaultComarcaShouldBeFound("codigoCnj.greaterThanOrEqual=" + DEFAULT_CODIGO_CNJ);

        // Get all the comarcaList where codigoCnj is greater than or equal to UPDATED_CODIGO_CNJ
        defaultComarcaShouldNotBeFound("codigoCnj.greaterThanOrEqual=" + UPDATED_CODIGO_CNJ);
    }

    @Test
    @Transactional
    void getAllComarcasByCodigoCnjIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        comarcaRepository.saveAndFlush(comarca);

        // Get all the comarcaList where codigoCnj is less than or equal to DEFAULT_CODIGO_CNJ
        defaultComarcaShouldBeFound("codigoCnj.lessThanOrEqual=" + DEFAULT_CODIGO_CNJ);

        // Get all the comarcaList where codigoCnj is less than or equal to SMALLER_CODIGO_CNJ
        defaultComarcaShouldNotBeFound("codigoCnj.lessThanOrEqual=" + SMALLER_CODIGO_CNJ);
    }

    @Test
    @Transactional
    void getAllComarcasByCodigoCnjIsLessThanSomething() throws Exception {
        // Initialize the database
        comarcaRepository.saveAndFlush(comarca);

        // Get all the comarcaList where codigoCnj is less than DEFAULT_CODIGO_CNJ
        defaultComarcaShouldNotBeFound("codigoCnj.lessThan=" + DEFAULT_CODIGO_CNJ);

        // Get all the comarcaList where codigoCnj is less than UPDATED_CODIGO_CNJ
        defaultComarcaShouldBeFound("codigoCnj.lessThan=" + UPDATED_CODIGO_CNJ);
    }

    @Test
    @Transactional
    void getAllComarcasByCodigoCnjIsGreaterThanSomething() throws Exception {
        // Initialize the database
        comarcaRepository.saveAndFlush(comarca);

        // Get all the comarcaList where codigoCnj is greater than DEFAULT_CODIGO_CNJ
        defaultComarcaShouldNotBeFound("codigoCnj.greaterThan=" + DEFAULT_CODIGO_CNJ);

        // Get all the comarcaList where codigoCnj is greater than SMALLER_CODIGO_CNJ
        defaultComarcaShouldBeFound("codigoCnj.greaterThan=" + SMALLER_CODIGO_CNJ);
    }

    @Test
    @Transactional
    void getAllComarcasByProcessoIsEqualToSomething() throws Exception {
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

        // Get all the comarcaList where processo equals to (processoId + 1)
        defaultComarcaShouldNotBeFound("processoId.equals=" + (processoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultComarcaShouldBeFound(String filter) throws Exception {
        restComarcaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(comarca.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].codigoCnj").value(hasItem(sameNumber(DEFAULT_CODIGO_CNJ))));

        // Check, that the count call also returns 1
        restComarcaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultComarcaShouldNotBeFound(String filter) throws Exception {
        restComarcaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restComarcaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingComarca() throws Exception {
        // Get the comarca
        restComarcaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewComarca() throws Exception {
        // Initialize the database
        comarcaRepository.saveAndFlush(comarca);

        int databaseSizeBeforeUpdate = comarcaRepository.findAll().size();

        // Update the comarca
        Comarca updatedComarca = comarcaRepository.findById(comarca.getId()).get();
        // Disconnect from session so that the updates on updatedComarca are not directly saved in db
        em.detach(updatedComarca);
        updatedComarca.nome(UPDATED_NOME).codigoCnj(UPDATED_CODIGO_CNJ);

        restComarcaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedComarca.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedComarca))
            )
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
    void putNonExistingComarca() throws Exception {
        int databaseSizeBeforeUpdate = comarcaRepository.findAll().size();
        comarca.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restComarcaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, comarca.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(comarca))
            )
            .andExpect(status().isBadRequest());

        // Validate the Comarca in the database
        List<Comarca> comarcaList = comarcaRepository.findAll();
        assertThat(comarcaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchComarca() throws Exception {
        int databaseSizeBeforeUpdate = comarcaRepository.findAll().size();
        comarca.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restComarcaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(comarca))
            )
            .andExpect(status().isBadRequest());

        // Validate the Comarca in the database
        List<Comarca> comarcaList = comarcaRepository.findAll();
        assertThat(comarcaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamComarca() throws Exception {
        int databaseSizeBeforeUpdate = comarcaRepository.findAll().size();
        comarca.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restComarcaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(comarca)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Comarca in the database
        List<Comarca> comarcaList = comarcaRepository.findAll();
        assertThat(comarcaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateComarcaWithPatch() throws Exception {
        // Initialize the database
        comarcaRepository.saveAndFlush(comarca);

        int databaseSizeBeforeUpdate = comarcaRepository.findAll().size();

        // Update the comarca using partial update
        Comarca partialUpdatedComarca = new Comarca();
        partialUpdatedComarca.setId(comarca.getId());

        restComarcaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedComarca.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedComarca))
            )
            .andExpect(status().isOk());

        // Validate the Comarca in the database
        List<Comarca> comarcaList = comarcaRepository.findAll();
        assertThat(comarcaList).hasSize(databaseSizeBeforeUpdate);
        Comarca testComarca = comarcaList.get(comarcaList.size() - 1);
        assertThat(testComarca.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testComarca.getCodigoCnj()).isEqualByComparingTo(DEFAULT_CODIGO_CNJ);
    }

    @Test
    @Transactional
    void fullUpdateComarcaWithPatch() throws Exception {
        // Initialize the database
        comarcaRepository.saveAndFlush(comarca);

        int databaseSizeBeforeUpdate = comarcaRepository.findAll().size();

        // Update the comarca using partial update
        Comarca partialUpdatedComarca = new Comarca();
        partialUpdatedComarca.setId(comarca.getId());

        partialUpdatedComarca.nome(UPDATED_NOME).codigoCnj(UPDATED_CODIGO_CNJ);

        restComarcaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedComarca.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedComarca))
            )
            .andExpect(status().isOk());

        // Validate the Comarca in the database
        List<Comarca> comarcaList = comarcaRepository.findAll();
        assertThat(comarcaList).hasSize(databaseSizeBeforeUpdate);
        Comarca testComarca = comarcaList.get(comarcaList.size() - 1);
        assertThat(testComarca.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testComarca.getCodigoCnj()).isEqualByComparingTo(UPDATED_CODIGO_CNJ);
    }

    @Test
    @Transactional
    void patchNonExistingComarca() throws Exception {
        int databaseSizeBeforeUpdate = comarcaRepository.findAll().size();
        comarca.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restComarcaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, comarca.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(comarca))
            )
            .andExpect(status().isBadRequest());

        // Validate the Comarca in the database
        List<Comarca> comarcaList = comarcaRepository.findAll();
        assertThat(comarcaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchComarca() throws Exception {
        int databaseSizeBeforeUpdate = comarcaRepository.findAll().size();
        comarca.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restComarcaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(comarca))
            )
            .andExpect(status().isBadRequest());

        // Validate the Comarca in the database
        List<Comarca> comarcaList = comarcaRepository.findAll();
        assertThat(comarcaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamComarca() throws Exception {
        int databaseSizeBeforeUpdate = comarcaRepository.findAll().size();
        comarca.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restComarcaMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(comarca)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Comarca in the database
        List<Comarca> comarcaList = comarcaRepository.findAll();
        assertThat(comarcaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteComarca() throws Exception {
        // Initialize the database
        comarcaRepository.saveAndFlush(comarca);

        int databaseSizeBeforeDelete = comarcaRepository.findAll().size();

        // Delete the comarca
        restComarcaMockMvc
            .perform(delete(ENTITY_API_URL_ID, comarca.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Comarca> comarcaList = comarcaRepository.findAll();
        assertThat(comarcaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
