package br.com.cidha.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.cidha.IntegrationTest;
import br.com.cidha.domain.AtividadeExploracaoIlegal;
import br.com.cidha.domain.Comarca;
import br.com.cidha.domain.ConcessaoLiminar;
import br.com.cidha.domain.ConcessaoLiminarCassada;
import br.com.cidha.domain.EmbargoDeclaracao;
import br.com.cidha.domain.EmbargoDeclaracaoAgravo;
import br.com.cidha.domain.EmbargoRecursoEspecial;
import br.com.cidha.domain.EmbargoRespRe;
import br.com.cidha.domain.EnvolvidosConflitoLitigio;
import br.com.cidha.domain.Municipio;
import br.com.cidha.domain.ParteInteresssada;
import br.com.cidha.domain.ProblemaJuridico;
import br.com.cidha.domain.Processo;
import br.com.cidha.domain.ProcessoConflito;
import br.com.cidha.domain.Quilombo;
import br.com.cidha.domain.Relator;
import br.com.cidha.domain.TerraIndigena;
import br.com.cidha.domain.Territorio;
import br.com.cidha.domain.TipoDecisao;
import br.com.cidha.domain.TipoEmpreendimento;
import br.com.cidha.domain.UnidadeConservacao;
import br.com.cidha.repository.ProcessoRepository;
import br.com.cidha.service.ProcessoService;
import br.com.cidha.service.criteria.ProcessoCriteria;
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
 * Integration tests for the {@link ProcessoResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ProcessoResourceIT {

    private static final String DEFAULT_OFICIO = "AAAAAAAAAA";
    private static final String UPDATED_OFICIO = "BBBBBBBBBB";

    private static final String DEFAULT_ASSUNTO = "AAAAAAAAAA";
    private static final String UPDATED_ASSUNTO = "BBBBBBBBBB";

    private static final String DEFAULT_LINK_UNICO = "AAAAAAAAAA";
    private static final String UPDATED_LINK_UNICO = "BBBBBBBBBB";

    private static final String DEFAULT_LINK_TRF = "AAAAAAAAAA";
    private static final String UPDATED_LINK_TRF = "BBBBBBBBBB";

    private static final String DEFAULT_SUBSECAO_JUDICIARIA = "AAAAAAAAAA";
    private static final String UPDATED_SUBSECAO_JUDICIARIA = "BBBBBBBBBB";

    private static final String DEFAULT_TURMA_TRF_1 = "AAAAAAAAAA";
    private static final String UPDATED_TURMA_TRF_1 = "BBBBBBBBBB";

    private static final String DEFAULT_NUMERO_PROCESSO_ADMINISTRATIVO = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_PROCESSO_ADMINISTRATIVO = "BBBBBBBBBB";

    private static final String DEFAULT_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA = "BBBBBBBBBB";

    private static final String DEFAULT_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA_LINK = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA_LINK = "BBBBBBBBBB";

    private static final String DEFAULT_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA_OBSERVACOES = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA_OBSERVACOES = "BBBBBBBBBB";

    private static final Boolean DEFAULT_PARECER = false;
    private static final Boolean UPDATED_PARECER = true;

    private static final String DEFAULT_APELACAO = "AAAAAAAAAA";
    private static final String UPDATED_APELACAO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/processos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProcessoRepository processoRepository;

    @Mock
    private ProcessoRepository processoRepositoryMock;

    @Mock
    private ProcessoService processoServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProcessoMockMvc;

    private Processo processo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Processo createEntity(EntityManager em) {
        Processo processo = new Processo()
            .oficio(DEFAULT_OFICIO)
            .assunto(DEFAULT_ASSUNTO)
            .linkUnico(DEFAULT_LINK_UNICO)
            .linkTrf(DEFAULT_LINK_TRF)
            .subsecaoJudiciaria(DEFAULT_SUBSECAO_JUDICIARIA)
            .turmaTrf1(DEFAULT_TURMA_TRF_1)
            .numeroProcessoAdministrativo(DEFAULT_NUMERO_PROCESSO_ADMINISTRATIVO)
            .numeroProcessoJudicialPrimeiraInstancia(DEFAULT_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA)
            .numeroProcessoJudicialPrimeiraInstanciaLink(DEFAULT_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA_LINK)
            .numeroProcessoJudicialPrimeiraInstanciaObservacoes(DEFAULT_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA_OBSERVACOES)
            .parecer(DEFAULT_PARECER)
            .apelacao(DEFAULT_APELACAO);
        return processo;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Processo createUpdatedEntity(EntityManager em) {
        Processo processo = new Processo()
            .oficio(UPDATED_OFICIO)
            .assunto(UPDATED_ASSUNTO)
            .linkUnico(UPDATED_LINK_UNICO)
            .linkTrf(UPDATED_LINK_TRF)
            .subsecaoJudiciaria(UPDATED_SUBSECAO_JUDICIARIA)
            .turmaTrf1(UPDATED_TURMA_TRF_1)
            .numeroProcessoAdministrativo(UPDATED_NUMERO_PROCESSO_ADMINISTRATIVO)
            .numeroProcessoJudicialPrimeiraInstancia(UPDATED_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA)
            .numeroProcessoJudicialPrimeiraInstanciaLink(UPDATED_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA_LINK)
            .numeroProcessoJudicialPrimeiraInstanciaObservacoes(UPDATED_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA_OBSERVACOES)
            .parecer(UPDATED_PARECER)
            .apelacao(UPDATED_APELACAO);
        return processo;
    }

    @BeforeEach
    public void initTest() {
        processo = createEntity(em);
    }

    @Test
    @Transactional
    void createProcesso() throws Exception {
        int databaseSizeBeforeCreate = processoRepository.findAll().size();
        // Create the Processo
        restProcessoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(processo)))
            .andExpect(status().isCreated());

        // Validate the Processo in the database
        List<Processo> processoList = processoRepository.findAll();
        assertThat(processoList).hasSize(databaseSizeBeforeCreate + 1);
        Processo testProcesso = processoList.get(processoList.size() - 1);
        assertThat(testProcesso.getOficio()).isEqualTo(DEFAULT_OFICIO);
        assertThat(testProcesso.getAssunto()).isEqualTo(DEFAULT_ASSUNTO);
        assertThat(testProcesso.getLinkUnico()).isEqualTo(DEFAULT_LINK_UNICO);
        assertThat(testProcesso.getLinkTrf()).isEqualTo(DEFAULT_LINK_TRF);
        assertThat(testProcesso.getSubsecaoJudiciaria()).isEqualTo(DEFAULT_SUBSECAO_JUDICIARIA);
        assertThat(testProcesso.getTurmaTrf1()).isEqualTo(DEFAULT_TURMA_TRF_1);
        assertThat(testProcesso.getNumeroProcessoAdministrativo()).isEqualTo(DEFAULT_NUMERO_PROCESSO_ADMINISTRATIVO);
        assertThat(testProcesso.getNumeroProcessoJudicialPrimeiraInstancia())
            .isEqualTo(DEFAULT_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA);
        assertThat(testProcesso.getNumeroProcessoJudicialPrimeiraInstanciaLink())
            .isEqualTo(DEFAULT_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA_LINK);
        assertThat(testProcesso.getNumeroProcessoJudicialPrimeiraInstanciaObservacoes())
            .isEqualTo(DEFAULT_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA_OBSERVACOES);
        assertThat(testProcesso.getParecer()).isEqualTo(DEFAULT_PARECER);
        assertThat(testProcesso.getApelacao()).isEqualTo(DEFAULT_APELACAO);
    }

    @Test
    @Transactional
    void createProcessoWithExistingId() throws Exception {
        // Create the Processo with an existing ID
        processo.setId(1L);

        int databaseSizeBeforeCreate = processoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProcessoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(processo)))
            .andExpect(status().isBadRequest());

        // Validate the Processo in the database
        List<Processo> processoList = processoRepository.findAll();
        assertThat(processoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllProcessos() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList
        restProcessoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(processo.getId().intValue())))
            .andExpect(jsonPath("$.[*].oficio").value(hasItem(DEFAULT_OFICIO)))
            .andExpect(jsonPath("$.[*].assunto").value(hasItem(DEFAULT_ASSUNTO.toString())))
            .andExpect(jsonPath("$.[*].linkUnico").value(hasItem(DEFAULT_LINK_UNICO)))
            .andExpect(jsonPath("$.[*].linkTrf").value(hasItem(DEFAULT_LINK_TRF)))
            .andExpect(jsonPath("$.[*].subsecaoJudiciaria").value(hasItem(DEFAULT_SUBSECAO_JUDICIARIA)))
            .andExpect(jsonPath("$.[*].turmaTrf1").value(hasItem(DEFAULT_TURMA_TRF_1)))
            .andExpect(jsonPath("$.[*].numeroProcessoAdministrativo").value(hasItem(DEFAULT_NUMERO_PROCESSO_ADMINISTRATIVO)))
            .andExpect(
                jsonPath("$.[*].numeroProcessoJudicialPrimeiraInstancia")
                    .value(hasItem(DEFAULT_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA))
            )
            .andExpect(
                jsonPath("$.[*].numeroProcessoJudicialPrimeiraInstanciaLink")
                    .value(hasItem(DEFAULT_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA_LINK))
            )
            .andExpect(
                jsonPath("$.[*].numeroProcessoJudicialPrimeiraInstanciaObservacoes")
                    .value(hasItem(DEFAULT_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA_OBSERVACOES.toString()))
            )
            .andExpect(jsonPath("$.[*].parecer").value(hasItem(DEFAULT_PARECER.booleanValue())))
            .andExpect(jsonPath("$.[*].apelacao").value(hasItem(DEFAULT_APELACAO)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllProcessosWithEagerRelationshipsIsEnabled() throws Exception {
        when(processoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restProcessoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(processoServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllProcessosWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(processoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restProcessoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(processoServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getProcesso() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get the processo
        restProcessoMockMvc
            .perform(get(ENTITY_API_URL_ID, processo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(processo.getId().intValue()))
            .andExpect(jsonPath("$.oficio").value(DEFAULT_OFICIO))
            .andExpect(jsonPath("$.assunto").value(DEFAULT_ASSUNTO.toString()))
            .andExpect(jsonPath("$.linkUnico").value(DEFAULT_LINK_UNICO))
            .andExpect(jsonPath("$.linkTrf").value(DEFAULT_LINK_TRF))
            .andExpect(jsonPath("$.subsecaoJudiciaria").value(DEFAULT_SUBSECAO_JUDICIARIA))
            .andExpect(jsonPath("$.turmaTrf1").value(DEFAULT_TURMA_TRF_1))
            .andExpect(jsonPath("$.numeroProcessoAdministrativo").value(DEFAULT_NUMERO_PROCESSO_ADMINISTRATIVO))
            .andExpect(jsonPath("$.numeroProcessoJudicialPrimeiraInstancia").value(DEFAULT_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA))
            .andExpect(
                jsonPath("$.numeroProcessoJudicialPrimeiraInstanciaLink").value(DEFAULT_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA_LINK)
            )
            .andExpect(
                jsonPath("$.numeroProcessoJudicialPrimeiraInstanciaObservacoes")
                    .value(DEFAULT_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA_OBSERVACOES.toString())
            )
            .andExpect(jsonPath("$.parecer").value(DEFAULT_PARECER.booleanValue()))
            .andExpect(jsonPath("$.apelacao").value(DEFAULT_APELACAO));
    }

    @Test
    @Transactional
    void getProcessosByIdFiltering() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        Long id = processo.getId();

        defaultProcessoShouldBeFound("id.equals=" + id);
        defaultProcessoShouldNotBeFound("id.notEquals=" + id);

        defaultProcessoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultProcessoShouldNotBeFound("id.greaterThan=" + id);

        defaultProcessoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultProcessoShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllProcessosByOficioIsEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where oficio equals to DEFAULT_OFICIO
        defaultProcessoShouldBeFound("oficio.equals=" + DEFAULT_OFICIO);

        // Get all the processoList where oficio equals to UPDATED_OFICIO
        defaultProcessoShouldNotBeFound("oficio.equals=" + UPDATED_OFICIO);
    }

    @Test
    @Transactional
    void getAllProcessosByOficioIsNotEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where oficio not equals to DEFAULT_OFICIO
        defaultProcessoShouldNotBeFound("oficio.notEquals=" + DEFAULT_OFICIO);

        // Get all the processoList where oficio not equals to UPDATED_OFICIO
        defaultProcessoShouldBeFound("oficio.notEquals=" + UPDATED_OFICIO);
    }

    @Test
    @Transactional
    void getAllProcessosByOficioIsInShouldWork() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where oficio in DEFAULT_OFICIO or UPDATED_OFICIO
        defaultProcessoShouldBeFound("oficio.in=" + DEFAULT_OFICIO + "," + UPDATED_OFICIO);

        // Get all the processoList where oficio equals to UPDATED_OFICIO
        defaultProcessoShouldNotBeFound("oficio.in=" + UPDATED_OFICIO);
    }

    @Test
    @Transactional
    void getAllProcessosByOficioIsNullOrNotNull() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where oficio is not null
        defaultProcessoShouldBeFound("oficio.specified=true");

        // Get all the processoList where oficio is null
        defaultProcessoShouldNotBeFound("oficio.specified=false");
    }

    @Test
    @Transactional
    void getAllProcessosByOficioContainsSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where oficio contains DEFAULT_OFICIO
        defaultProcessoShouldBeFound("oficio.contains=" + DEFAULT_OFICIO);

        // Get all the processoList where oficio contains UPDATED_OFICIO
        defaultProcessoShouldNotBeFound("oficio.contains=" + UPDATED_OFICIO);
    }

    @Test
    @Transactional
    void getAllProcessosByOficioNotContainsSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where oficio does not contain DEFAULT_OFICIO
        defaultProcessoShouldNotBeFound("oficio.doesNotContain=" + DEFAULT_OFICIO);

        // Get all the processoList where oficio does not contain UPDATED_OFICIO
        defaultProcessoShouldBeFound("oficio.doesNotContain=" + UPDATED_OFICIO);
    }

    @Test
    @Transactional
    void getAllProcessosByLinkUnicoIsEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where linkUnico equals to DEFAULT_LINK_UNICO
        defaultProcessoShouldBeFound("linkUnico.equals=" + DEFAULT_LINK_UNICO);

        // Get all the processoList where linkUnico equals to UPDATED_LINK_UNICO
        defaultProcessoShouldNotBeFound("linkUnico.equals=" + UPDATED_LINK_UNICO);
    }

    @Test
    @Transactional
    void getAllProcessosByLinkUnicoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where linkUnico not equals to DEFAULT_LINK_UNICO
        defaultProcessoShouldNotBeFound("linkUnico.notEquals=" + DEFAULT_LINK_UNICO);

        // Get all the processoList where linkUnico not equals to UPDATED_LINK_UNICO
        defaultProcessoShouldBeFound("linkUnico.notEquals=" + UPDATED_LINK_UNICO);
    }

    @Test
    @Transactional
    void getAllProcessosByLinkUnicoIsInShouldWork() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where linkUnico in DEFAULT_LINK_UNICO or UPDATED_LINK_UNICO
        defaultProcessoShouldBeFound("linkUnico.in=" + DEFAULT_LINK_UNICO + "," + UPDATED_LINK_UNICO);

        // Get all the processoList where linkUnico equals to UPDATED_LINK_UNICO
        defaultProcessoShouldNotBeFound("linkUnico.in=" + UPDATED_LINK_UNICO);
    }

    @Test
    @Transactional
    void getAllProcessosByLinkUnicoIsNullOrNotNull() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where linkUnico is not null
        defaultProcessoShouldBeFound("linkUnico.specified=true");

        // Get all the processoList where linkUnico is null
        defaultProcessoShouldNotBeFound("linkUnico.specified=false");
    }

    @Test
    @Transactional
    void getAllProcessosByLinkUnicoContainsSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where linkUnico contains DEFAULT_LINK_UNICO
        defaultProcessoShouldBeFound("linkUnico.contains=" + DEFAULT_LINK_UNICO);

        // Get all the processoList where linkUnico contains UPDATED_LINK_UNICO
        defaultProcessoShouldNotBeFound("linkUnico.contains=" + UPDATED_LINK_UNICO);
    }

    @Test
    @Transactional
    void getAllProcessosByLinkUnicoNotContainsSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where linkUnico does not contain DEFAULT_LINK_UNICO
        defaultProcessoShouldNotBeFound("linkUnico.doesNotContain=" + DEFAULT_LINK_UNICO);

        // Get all the processoList where linkUnico does not contain UPDATED_LINK_UNICO
        defaultProcessoShouldBeFound("linkUnico.doesNotContain=" + UPDATED_LINK_UNICO);
    }

    @Test
    @Transactional
    void getAllProcessosByLinkTrfIsEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where linkTrf equals to DEFAULT_LINK_TRF
        defaultProcessoShouldBeFound("linkTrf.equals=" + DEFAULT_LINK_TRF);

        // Get all the processoList where linkTrf equals to UPDATED_LINK_TRF
        defaultProcessoShouldNotBeFound("linkTrf.equals=" + UPDATED_LINK_TRF);
    }

    @Test
    @Transactional
    void getAllProcessosByLinkTrfIsNotEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where linkTrf not equals to DEFAULT_LINK_TRF
        defaultProcessoShouldNotBeFound("linkTrf.notEquals=" + DEFAULT_LINK_TRF);

        // Get all the processoList where linkTrf not equals to UPDATED_LINK_TRF
        defaultProcessoShouldBeFound("linkTrf.notEquals=" + UPDATED_LINK_TRF);
    }

    @Test
    @Transactional
    void getAllProcessosByLinkTrfIsInShouldWork() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where linkTrf in DEFAULT_LINK_TRF or UPDATED_LINK_TRF
        defaultProcessoShouldBeFound("linkTrf.in=" + DEFAULT_LINK_TRF + "," + UPDATED_LINK_TRF);

        // Get all the processoList where linkTrf equals to UPDATED_LINK_TRF
        defaultProcessoShouldNotBeFound("linkTrf.in=" + UPDATED_LINK_TRF);
    }

    @Test
    @Transactional
    void getAllProcessosByLinkTrfIsNullOrNotNull() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where linkTrf is not null
        defaultProcessoShouldBeFound("linkTrf.specified=true");

        // Get all the processoList where linkTrf is null
        defaultProcessoShouldNotBeFound("linkTrf.specified=false");
    }

    @Test
    @Transactional
    void getAllProcessosByLinkTrfContainsSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where linkTrf contains DEFAULT_LINK_TRF
        defaultProcessoShouldBeFound("linkTrf.contains=" + DEFAULT_LINK_TRF);

        // Get all the processoList where linkTrf contains UPDATED_LINK_TRF
        defaultProcessoShouldNotBeFound("linkTrf.contains=" + UPDATED_LINK_TRF);
    }

    @Test
    @Transactional
    void getAllProcessosByLinkTrfNotContainsSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where linkTrf does not contain DEFAULT_LINK_TRF
        defaultProcessoShouldNotBeFound("linkTrf.doesNotContain=" + DEFAULT_LINK_TRF);

        // Get all the processoList where linkTrf does not contain UPDATED_LINK_TRF
        defaultProcessoShouldBeFound("linkTrf.doesNotContain=" + UPDATED_LINK_TRF);
    }

    @Test
    @Transactional
    void getAllProcessosBySubsecaoJudiciariaIsEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where subsecaoJudiciaria equals to DEFAULT_SUBSECAO_JUDICIARIA
        defaultProcessoShouldBeFound("subsecaoJudiciaria.equals=" + DEFAULT_SUBSECAO_JUDICIARIA);

        // Get all the processoList where subsecaoJudiciaria equals to UPDATED_SUBSECAO_JUDICIARIA
        defaultProcessoShouldNotBeFound("subsecaoJudiciaria.equals=" + UPDATED_SUBSECAO_JUDICIARIA);
    }

    @Test
    @Transactional
    void getAllProcessosBySubsecaoJudiciariaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where subsecaoJudiciaria not equals to DEFAULT_SUBSECAO_JUDICIARIA
        defaultProcessoShouldNotBeFound("subsecaoJudiciaria.notEquals=" + DEFAULT_SUBSECAO_JUDICIARIA);

        // Get all the processoList where subsecaoJudiciaria not equals to UPDATED_SUBSECAO_JUDICIARIA
        defaultProcessoShouldBeFound("subsecaoJudiciaria.notEquals=" + UPDATED_SUBSECAO_JUDICIARIA);
    }

    @Test
    @Transactional
    void getAllProcessosBySubsecaoJudiciariaIsInShouldWork() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where subsecaoJudiciaria in DEFAULT_SUBSECAO_JUDICIARIA or UPDATED_SUBSECAO_JUDICIARIA
        defaultProcessoShouldBeFound("subsecaoJudiciaria.in=" + DEFAULT_SUBSECAO_JUDICIARIA + "," + UPDATED_SUBSECAO_JUDICIARIA);

        // Get all the processoList where subsecaoJudiciaria equals to UPDATED_SUBSECAO_JUDICIARIA
        defaultProcessoShouldNotBeFound("subsecaoJudiciaria.in=" + UPDATED_SUBSECAO_JUDICIARIA);
    }

    @Test
    @Transactional
    void getAllProcessosBySubsecaoJudiciariaIsNullOrNotNull() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where subsecaoJudiciaria is not null
        defaultProcessoShouldBeFound("subsecaoJudiciaria.specified=true");

        // Get all the processoList where subsecaoJudiciaria is null
        defaultProcessoShouldNotBeFound("subsecaoJudiciaria.specified=false");
    }

    @Test
    @Transactional
    void getAllProcessosBySubsecaoJudiciariaContainsSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where subsecaoJudiciaria contains DEFAULT_SUBSECAO_JUDICIARIA
        defaultProcessoShouldBeFound("subsecaoJudiciaria.contains=" + DEFAULT_SUBSECAO_JUDICIARIA);

        // Get all the processoList where subsecaoJudiciaria contains UPDATED_SUBSECAO_JUDICIARIA
        defaultProcessoShouldNotBeFound("subsecaoJudiciaria.contains=" + UPDATED_SUBSECAO_JUDICIARIA);
    }

    @Test
    @Transactional
    void getAllProcessosBySubsecaoJudiciariaNotContainsSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where subsecaoJudiciaria does not contain DEFAULT_SUBSECAO_JUDICIARIA
        defaultProcessoShouldNotBeFound("subsecaoJudiciaria.doesNotContain=" + DEFAULT_SUBSECAO_JUDICIARIA);

        // Get all the processoList where subsecaoJudiciaria does not contain UPDATED_SUBSECAO_JUDICIARIA
        defaultProcessoShouldBeFound("subsecaoJudiciaria.doesNotContain=" + UPDATED_SUBSECAO_JUDICIARIA);
    }

    @Test
    @Transactional
    void getAllProcessosByTurmaTrf1IsEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where turmaTrf1 equals to DEFAULT_TURMA_TRF_1
        defaultProcessoShouldBeFound("turmaTrf1.equals=" + DEFAULT_TURMA_TRF_1);

        // Get all the processoList where turmaTrf1 equals to UPDATED_TURMA_TRF_1
        defaultProcessoShouldNotBeFound("turmaTrf1.equals=" + UPDATED_TURMA_TRF_1);
    }

    @Test
    @Transactional
    void getAllProcessosByTurmaTrf1IsNotEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where turmaTrf1 not equals to DEFAULT_TURMA_TRF_1
        defaultProcessoShouldNotBeFound("turmaTrf1.notEquals=" + DEFAULT_TURMA_TRF_1);

        // Get all the processoList where turmaTrf1 not equals to UPDATED_TURMA_TRF_1
        defaultProcessoShouldBeFound("turmaTrf1.notEquals=" + UPDATED_TURMA_TRF_1);
    }

    @Test
    @Transactional
    void getAllProcessosByTurmaTrf1IsInShouldWork() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where turmaTrf1 in DEFAULT_TURMA_TRF_1 or UPDATED_TURMA_TRF_1
        defaultProcessoShouldBeFound("turmaTrf1.in=" + DEFAULT_TURMA_TRF_1 + "," + UPDATED_TURMA_TRF_1);

        // Get all the processoList where turmaTrf1 equals to UPDATED_TURMA_TRF_1
        defaultProcessoShouldNotBeFound("turmaTrf1.in=" + UPDATED_TURMA_TRF_1);
    }

    @Test
    @Transactional
    void getAllProcessosByTurmaTrf1IsNullOrNotNull() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where turmaTrf1 is not null
        defaultProcessoShouldBeFound("turmaTrf1.specified=true");

        // Get all the processoList where turmaTrf1 is null
        defaultProcessoShouldNotBeFound("turmaTrf1.specified=false");
    }

    @Test
    @Transactional
    void getAllProcessosByTurmaTrf1ContainsSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where turmaTrf1 contains DEFAULT_TURMA_TRF_1
        defaultProcessoShouldBeFound("turmaTrf1.contains=" + DEFAULT_TURMA_TRF_1);

        // Get all the processoList where turmaTrf1 contains UPDATED_TURMA_TRF_1
        defaultProcessoShouldNotBeFound("turmaTrf1.contains=" + UPDATED_TURMA_TRF_1);
    }

    @Test
    @Transactional
    void getAllProcessosByTurmaTrf1NotContainsSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where turmaTrf1 does not contain DEFAULT_TURMA_TRF_1
        defaultProcessoShouldNotBeFound("turmaTrf1.doesNotContain=" + DEFAULT_TURMA_TRF_1);

        // Get all the processoList where turmaTrf1 does not contain UPDATED_TURMA_TRF_1
        defaultProcessoShouldBeFound("turmaTrf1.doesNotContain=" + UPDATED_TURMA_TRF_1);
    }

    @Test
    @Transactional
    void getAllProcessosByNumeroProcessoAdministrativoIsEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where numeroProcessoAdministrativo equals to DEFAULT_NUMERO_PROCESSO_ADMINISTRATIVO
        defaultProcessoShouldBeFound("numeroProcessoAdministrativo.equals=" + DEFAULT_NUMERO_PROCESSO_ADMINISTRATIVO);

        // Get all the processoList where numeroProcessoAdministrativo equals to UPDATED_NUMERO_PROCESSO_ADMINISTRATIVO
        defaultProcessoShouldNotBeFound("numeroProcessoAdministrativo.equals=" + UPDATED_NUMERO_PROCESSO_ADMINISTRATIVO);
    }

    @Test
    @Transactional
    void getAllProcessosByNumeroProcessoAdministrativoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where numeroProcessoAdministrativo not equals to DEFAULT_NUMERO_PROCESSO_ADMINISTRATIVO
        defaultProcessoShouldNotBeFound("numeroProcessoAdministrativo.notEquals=" + DEFAULT_NUMERO_PROCESSO_ADMINISTRATIVO);

        // Get all the processoList where numeroProcessoAdministrativo not equals to UPDATED_NUMERO_PROCESSO_ADMINISTRATIVO
        defaultProcessoShouldBeFound("numeroProcessoAdministrativo.notEquals=" + UPDATED_NUMERO_PROCESSO_ADMINISTRATIVO);
    }

    @Test
    @Transactional
    void getAllProcessosByNumeroProcessoAdministrativoIsInShouldWork() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where numeroProcessoAdministrativo in DEFAULT_NUMERO_PROCESSO_ADMINISTRATIVO or UPDATED_NUMERO_PROCESSO_ADMINISTRATIVO
        defaultProcessoShouldBeFound(
            "numeroProcessoAdministrativo.in=" + DEFAULT_NUMERO_PROCESSO_ADMINISTRATIVO + "," + UPDATED_NUMERO_PROCESSO_ADMINISTRATIVO
        );

        // Get all the processoList where numeroProcessoAdministrativo equals to UPDATED_NUMERO_PROCESSO_ADMINISTRATIVO
        defaultProcessoShouldNotBeFound("numeroProcessoAdministrativo.in=" + UPDATED_NUMERO_PROCESSO_ADMINISTRATIVO);
    }

    @Test
    @Transactional
    void getAllProcessosByNumeroProcessoAdministrativoIsNullOrNotNull() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where numeroProcessoAdministrativo is not null
        defaultProcessoShouldBeFound("numeroProcessoAdministrativo.specified=true");

        // Get all the processoList where numeroProcessoAdministrativo is null
        defaultProcessoShouldNotBeFound("numeroProcessoAdministrativo.specified=false");
    }

    @Test
    @Transactional
    void getAllProcessosByNumeroProcessoAdministrativoContainsSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where numeroProcessoAdministrativo contains DEFAULT_NUMERO_PROCESSO_ADMINISTRATIVO
        defaultProcessoShouldBeFound("numeroProcessoAdministrativo.contains=" + DEFAULT_NUMERO_PROCESSO_ADMINISTRATIVO);

        // Get all the processoList where numeroProcessoAdministrativo contains UPDATED_NUMERO_PROCESSO_ADMINISTRATIVO
        defaultProcessoShouldNotBeFound("numeroProcessoAdministrativo.contains=" + UPDATED_NUMERO_PROCESSO_ADMINISTRATIVO);
    }

    @Test
    @Transactional
    void getAllProcessosByNumeroProcessoAdministrativoNotContainsSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where numeroProcessoAdministrativo does not contain DEFAULT_NUMERO_PROCESSO_ADMINISTRATIVO
        defaultProcessoShouldNotBeFound("numeroProcessoAdministrativo.doesNotContain=" + DEFAULT_NUMERO_PROCESSO_ADMINISTRATIVO);

        // Get all the processoList where numeroProcessoAdministrativo does not contain UPDATED_NUMERO_PROCESSO_ADMINISTRATIVO
        defaultProcessoShouldBeFound("numeroProcessoAdministrativo.doesNotContain=" + UPDATED_NUMERO_PROCESSO_ADMINISTRATIVO);
    }

    @Test
    @Transactional
    void getAllProcessosByNumeroProcessoJudicialPrimeiraInstanciaIsEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where numeroProcessoJudicialPrimeiraInstancia equals to DEFAULT_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA
        defaultProcessoShouldBeFound(
            "numeroProcessoJudicialPrimeiraInstancia.equals=" + DEFAULT_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA
        );

        // Get all the processoList where numeroProcessoJudicialPrimeiraInstancia equals to UPDATED_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA
        defaultProcessoShouldNotBeFound(
            "numeroProcessoJudicialPrimeiraInstancia.equals=" + UPDATED_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA
        );
    }

    @Test
    @Transactional
    void getAllProcessosByNumeroProcessoJudicialPrimeiraInstanciaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where numeroProcessoJudicialPrimeiraInstancia not equals to DEFAULT_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA
        defaultProcessoShouldNotBeFound(
            "numeroProcessoJudicialPrimeiraInstancia.notEquals=" + DEFAULT_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA
        );

        // Get all the processoList where numeroProcessoJudicialPrimeiraInstancia not equals to UPDATED_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA
        defaultProcessoShouldBeFound(
            "numeroProcessoJudicialPrimeiraInstancia.notEquals=" + UPDATED_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA
        );
    }

    @Test
    @Transactional
    void getAllProcessosByNumeroProcessoJudicialPrimeiraInstanciaIsInShouldWork() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where numeroProcessoJudicialPrimeiraInstancia in DEFAULT_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA or UPDATED_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA
        defaultProcessoShouldBeFound(
            "numeroProcessoJudicialPrimeiraInstancia.in=" +
            DEFAULT_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA +
            "," +
            UPDATED_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA
        );

        // Get all the processoList where numeroProcessoJudicialPrimeiraInstancia equals to UPDATED_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA
        defaultProcessoShouldNotBeFound(
            "numeroProcessoJudicialPrimeiraInstancia.in=" + UPDATED_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA
        );
    }

    @Test
    @Transactional
    void getAllProcessosByNumeroProcessoJudicialPrimeiraInstanciaIsNullOrNotNull() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where numeroProcessoJudicialPrimeiraInstancia is not null
        defaultProcessoShouldBeFound("numeroProcessoJudicialPrimeiraInstancia.specified=true");

        // Get all the processoList where numeroProcessoJudicialPrimeiraInstancia is null
        defaultProcessoShouldNotBeFound("numeroProcessoJudicialPrimeiraInstancia.specified=false");
    }

    @Test
    @Transactional
    void getAllProcessosByNumeroProcessoJudicialPrimeiraInstanciaContainsSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where numeroProcessoJudicialPrimeiraInstancia contains DEFAULT_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA
        defaultProcessoShouldBeFound(
            "numeroProcessoJudicialPrimeiraInstancia.contains=" + DEFAULT_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA
        );

        // Get all the processoList where numeroProcessoJudicialPrimeiraInstancia contains UPDATED_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA
        defaultProcessoShouldNotBeFound(
            "numeroProcessoJudicialPrimeiraInstancia.contains=" + UPDATED_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA
        );
    }

    @Test
    @Transactional
    void getAllProcessosByNumeroProcessoJudicialPrimeiraInstanciaNotContainsSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where numeroProcessoJudicialPrimeiraInstancia does not contain DEFAULT_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA
        defaultProcessoShouldNotBeFound(
            "numeroProcessoJudicialPrimeiraInstancia.doesNotContain=" + DEFAULT_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA
        );

        // Get all the processoList where numeroProcessoJudicialPrimeiraInstancia does not contain UPDATED_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA
        defaultProcessoShouldBeFound(
            "numeroProcessoJudicialPrimeiraInstancia.doesNotContain=" + UPDATED_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA
        );
    }

    @Test
    @Transactional
    void getAllProcessosByNumeroProcessoJudicialPrimeiraInstanciaLinkIsEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where numeroProcessoJudicialPrimeiraInstanciaLink equals to DEFAULT_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA_LINK
        defaultProcessoShouldBeFound(
            "numeroProcessoJudicialPrimeiraInstanciaLink.equals=" + DEFAULT_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA_LINK
        );

        // Get all the processoList where numeroProcessoJudicialPrimeiraInstanciaLink equals to UPDATED_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA_LINK
        defaultProcessoShouldNotBeFound(
            "numeroProcessoJudicialPrimeiraInstanciaLink.equals=" + UPDATED_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA_LINK
        );
    }

    @Test
    @Transactional
    void getAllProcessosByNumeroProcessoJudicialPrimeiraInstanciaLinkIsNotEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where numeroProcessoJudicialPrimeiraInstanciaLink not equals to DEFAULT_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA_LINK
        defaultProcessoShouldNotBeFound(
            "numeroProcessoJudicialPrimeiraInstanciaLink.notEquals=" + DEFAULT_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA_LINK
        );

        // Get all the processoList where numeroProcessoJudicialPrimeiraInstanciaLink not equals to UPDATED_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA_LINK
        defaultProcessoShouldBeFound(
            "numeroProcessoJudicialPrimeiraInstanciaLink.notEquals=" + UPDATED_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA_LINK
        );
    }

    @Test
    @Transactional
    void getAllProcessosByNumeroProcessoJudicialPrimeiraInstanciaLinkIsInShouldWork() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where numeroProcessoJudicialPrimeiraInstanciaLink in DEFAULT_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA_LINK or UPDATED_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA_LINK
        defaultProcessoShouldBeFound(
            "numeroProcessoJudicialPrimeiraInstanciaLink.in=" +
            DEFAULT_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA_LINK +
            "," +
            UPDATED_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA_LINK
        );

        // Get all the processoList where numeroProcessoJudicialPrimeiraInstanciaLink equals to UPDATED_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA_LINK
        defaultProcessoShouldNotBeFound(
            "numeroProcessoJudicialPrimeiraInstanciaLink.in=" + UPDATED_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA_LINK
        );
    }

    @Test
    @Transactional
    void getAllProcessosByNumeroProcessoJudicialPrimeiraInstanciaLinkIsNullOrNotNull() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where numeroProcessoJudicialPrimeiraInstanciaLink is not null
        defaultProcessoShouldBeFound("numeroProcessoJudicialPrimeiraInstanciaLink.specified=true");

        // Get all the processoList where numeroProcessoJudicialPrimeiraInstanciaLink is null
        defaultProcessoShouldNotBeFound("numeroProcessoJudicialPrimeiraInstanciaLink.specified=false");
    }

    @Test
    @Transactional
    void getAllProcessosByNumeroProcessoJudicialPrimeiraInstanciaLinkContainsSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where numeroProcessoJudicialPrimeiraInstanciaLink contains DEFAULT_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA_LINK
        defaultProcessoShouldBeFound(
            "numeroProcessoJudicialPrimeiraInstanciaLink.contains=" + DEFAULT_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA_LINK
        );

        // Get all the processoList where numeroProcessoJudicialPrimeiraInstanciaLink contains UPDATED_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA_LINK
        defaultProcessoShouldNotBeFound(
            "numeroProcessoJudicialPrimeiraInstanciaLink.contains=" + UPDATED_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA_LINK
        );
    }

    @Test
    @Transactional
    void getAllProcessosByNumeroProcessoJudicialPrimeiraInstanciaLinkNotContainsSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where numeroProcessoJudicialPrimeiraInstanciaLink does not contain DEFAULT_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA_LINK
        defaultProcessoShouldNotBeFound(
            "numeroProcessoJudicialPrimeiraInstanciaLink.doesNotContain=" + DEFAULT_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA_LINK
        );

        // Get all the processoList where numeroProcessoJudicialPrimeiraInstanciaLink does not contain UPDATED_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA_LINK
        defaultProcessoShouldBeFound(
            "numeroProcessoJudicialPrimeiraInstanciaLink.doesNotContain=" + UPDATED_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA_LINK
        );
    }

    @Test
    @Transactional
    void getAllProcessosByParecerIsEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where parecer equals to DEFAULT_PARECER
        defaultProcessoShouldBeFound("parecer.equals=" + DEFAULT_PARECER);

        // Get all the processoList where parecer equals to UPDATED_PARECER
        defaultProcessoShouldNotBeFound("parecer.equals=" + UPDATED_PARECER);
    }

    @Test
    @Transactional
    void getAllProcessosByParecerIsNotEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where parecer not equals to DEFAULT_PARECER
        defaultProcessoShouldNotBeFound("parecer.notEquals=" + DEFAULT_PARECER);

        // Get all the processoList where parecer not equals to UPDATED_PARECER
        defaultProcessoShouldBeFound("parecer.notEquals=" + UPDATED_PARECER);
    }

    @Test
    @Transactional
    void getAllProcessosByParecerIsInShouldWork() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where parecer in DEFAULT_PARECER or UPDATED_PARECER
        defaultProcessoShouldBeFound("parecer.in=" + DEFAULT_PARECER + "," + UPDATED_PARECER);

        // Get all the processoList where parecer equals to UPDATED_PARECER
        defaultProcessoShouldNotBeFound("parecer.in=" + UPDATED_PARECER);
    }

    @Test
    @Transactional
    void getAllProcessosByParecerIsNullOrNotNull() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where parecer is not null
        defaultProcessoShouldBeFound("parecer.specified=true");

        // Get all the processoList where parecer is null
        defaultProcessoShouldNotBeFound("parecer.specified=false");
    }

    @Test
    @Transactional
    void getAllProcessosByApelacaoIsEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where apelacao equals to DEFAULT_APELACAO
        defaultProcessoShouldBeFound("apelacao.equals=" + DEFAULT_APELACAO);

        // Get all the processoList where apelacao equals to UPDATED_APELACAO
        defaultProcessoShouldNotBeFound("apelacao.equals=" + UPDATED_APELACAO);
    }

    @Test
    @Transactional
    void getAllProcessosByApelacaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where apelacao not equals to DEFAULT_APELACAO
        defaultProcessoShouldNotBeFound("apelacao.notEquals=" + DEFAULT_APELACAO);

        // Get all the processoList where apelacao not equals to UPDATED_APELACAO
        defaultProcessoShouldBeFound("apelacao.notEquals=" + UPDATED_APELACAO);
    }

    @Test
    @Transactional
    void getAllProcessosByApelacaoIsInShouldWork() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where apelacao in DEFAULT_APELACAO or UPDATED_APELACAO
        defaultProcessoShouldBeFound("apelacao.in=" + DEFAULT_APELACAO + "," + UPDATED_APELACAO);

        // Get all the processoList where apelacao equals to UPDATED_APELACAO
        defaultProcessoShouldNotBeFound("apelacao.in=" + UPDATED_APELACAO);
    }

    @Test
    @Transactional
    void getAllProcessosByApelacaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where apelacao is not null
        defaultProcessoShouldBeFound("apelacao.specified=true");

        // Get all the processoList where apelacao is null
        defaultProcessoShouldNotBeFound("apelacao.specified=false");
    }

    @Test
    @Transactional
    void getAllProcessosByApelacaoContainsSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where apelacao contains DEFAULT_APELACAO
        defaultProcessoShouldBeFound("apelacao.contains=" + DEFAULT_APELACAO);

        // Get all the processoList where apelacao contains UPDATED_APELACAO
        defaultProcessoShouldNotBeFound("apelacao.contains=" + UPDATED_APELACAO);
    }

    @Test
    @Transactional
    void getAllProcessosByApelacaoNotContainsSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where apelacao does not contain DEFAULT_APELACAO
        defaultProcessoShouldNotBeFound("apelacao.doesNotContain=" + DEFAULT_APELACAO);

        // Get all the processoList where apelacao does not contain UPDATED_APELACAO
        defaultProcessoShouldBeFound("apelacao.doesNotContain=" + UPDATED_APELACAO);
    }

    @Test
    @Transactional
    void getAllProcessosByConcessaoLiminarIsEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);
        ConcessaoLiminar concessaoLiminar = ConcessaoLiminarResourceIT.createEntity(em);
        em.persist(concessaoLiminar);
        em.flush();
        processo.addConcessaoLiminar(concessaoLiminar);
        processoRepository.saveAndFlush(processo);
        Long concessaoLiminarId = concessaoLiminar.getId();

        // Get all the processoList where concessaoLiminar equals to concessaoLiminarId
        defaultProcessoShouldBeFound("concessaoLiminarId.equals=" + concessaoLiminarId);

        // Get all the processoList where concessaoLiminar equals to (concessaoLiminarId + 1)
        defaultProcessoShouldNotBeFound("concessaoLiminarId.equals=" + (concessaoLiminarId + 1));
    }

    @Test
    @Transactional
    void getAllProcessosByConcessaoLiminarCassadaIsEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);
        ConcessaoLiminarCassada concessaoLiminarCassada = ConcessaoLiminarCassadaResourceIT.createEntity(em);
        em.persist(concessaoLiminarCassada);
        em.flush();
        processo.addConcessaoLiminarCassada(concessaoLiminarCassada);
        processoRepository.saveAndFlush(processo);
        Long concessaoLiminarCassadaId = concessaoLiminarCassada.getId();

        // Get all the processoList where concessaoLiminarCassada equals to concessaoLiminarCassadaId
        defaultProcessoShouldBeFound("concessaoLiminarCassadaId.equals=" + concessaoLiminarCassadaId);

        // Get all the processoList where concessaoLiminarCassada equals to (concessaoLiminarCassadaId + 1)
        defaultProcessoShouldNotBeFound("concessaoLiminarCassadaId.equals=" + (concessaoLiminarCassadaId + 1));
    }

    @Test
    @Transactional
    void getAllProcessosByEmbargoRespReIsEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);
        EmbargoRespRe embargoRespRe = EmbargoRespReResourceIT.createEntity(em);
        em.persist(embargoRespRe);
        em.flush();
        processo.addEmbargoRespRe(embargoRespRe);
        processoRepository.saveAndFlush(processo);
        Long embargoRespReId = embargoRespRe.getId();

        // Get all the processoList where embargoRespRe equals to embargoRespReId
        defaultProcessoShouldBeFound("embargoRespReId.equals=" + embargoRespReId);

        // Get all the processoList where embargoRespRe equals to (embargoRespReId + 1)
        defaultProcessoShouldNotBeFound("embargoRespReId.equals=" + (embargoRespReId + 1));
    }

    @Test
    @Transactional
    void getAllProcessosByEmbargoDeclaracaoAgravoIsEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);
        EmbargoDeclaracaoAgravo embargoDeclaracaoAgravo = EmbargoDeclaracaoAgravoResourceIT.createEntity(em);
        em.persist(embargoDeclaracaoAgravo);
        em.flush();
        processo.addEmbargoDeclaracaoAgravo(embargoDeclaracaoAgravo);
        processoRepository.saveAndFlush(processo);
        Long embargoDeclaracaoAgravoId = embargoDeclaracaoAgravo.getId();

        // Get all the processoList where embargoDeclaracaoAgravo equals to embargoDeclaracaoAgravoId
        defaultProcessoShouldBeFound("embargoDeclaracaoAgravoId.equals=" + embargoDeclaracaoAgravoId);

        // Get all the processoList where embargoDeclaracaoAgravo equals to (embargoDeclaracaoAgravoId + 1)
        defaultProcessoShouldNotBeFound("embargoDeclaracaoAgravoId.equals=" + (embargoDeclaracaoAgravoId + 1));
    }

    @Test
    @Transactional
    void getAllProcessosByEmbargoDeclaracaoIsEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);
        EmbargoDeclaracao embargoDeclaracao = EmbargoDeclaracaoResourceIT.createEntity(em);
        em.persist(embargoDeclaracao);
        em.flush();
        processo.addEmbargoDeclaracao(embargoDeclaracao);
        processoRepository.saveAndFlush(processo);
        Long embargoDeclaracaoId = embargoDeclaracao.getId();

        // Get all the processoList where embargoDeclaracao equals to embargoDeclaracaoId
        defaultProcessoShouldBeFound("embargoDeclaracaoId.equals=" + embargoDeclaracaoId);

        // Get all the processoList where embargoDeclaracao equals to (embargoDeclaracaoId + 1)
        defaultProcessoShouldNotBeFound("embargoDeclaracaoId.equals=" + (embargoDeclaracaoId + 1));
    }

    @Test
    @Transactional
    void getAllProcessosByEmbargoRecursoEspecialIsEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);
        EmbargoRecursoEspecial embargoRecursoEspecial = EmbargoRecursoEspecialResourceIT.createEntity(em);
        em.persist(embargoRecursoEspecial);
        em.flush();
        processo.addEmbargoRecursoEspecial(embargoRecursoEspecial);
        processoRepository.saveAndFlush(processo);
        Long embargoRecursoEspecialId = embargoRecursoEspecial.getId();

        // Get all the processoList where embargoRecursoEspecial equals to embargoRecursoEspecialId
        defaultProcessoShouldBeFound("embargoRecursoEspecialId.equals=" + embargoRecursoEspecialId);

        // Get all the processoList where embargoRecursoEspecial equals to (embargoRecursoEspecialId + 1)
        defaultProcessoShouldNotBeFound("embargoRecursoEspecialId.equals=" + (embargoRecursoEspecialId + 1));
    }

    @Test
    @Transactional
    void getAllProcessosByTipoDecisaoIsEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);
        TipoDecisao tipoDecisao = TipoDecisaoResourceIT.createEntity(em);
        em.persist(tipoDecisao);
        em.flush();
        processo.setTipoDecisao(tipoDecisao);
        processoRepository.saveAndFlush(processo);
        Long tipoDecisaoId = tipoDecisao.getId();

        // Get all the processoList where tipoDecisao equals to tipoDecisaoId
        defaultProcessoShouldBeFound("tipoDecisaoId.equals=" + tipoDecisaoId);

        // Get all the processoList where tipoDecisao equals to (tipoDecisaoId + 1)
        defaultProcessoShouldNotBeFound("tipoDecisaoId.equals=" + (tipoDecisaoId + 1));
    }

    @Test
    @Transactional
    void getAllProcessosByTipoEmpreendimentoIsEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);
        TipoEmpreendimento tipoEmpreendimento = TipoEmpreendimentoResourceIT.createEntity(em);
        em.persist(tipoEmpreendimento);
        em.flush();
        processo.setTipoEmpreendimento(tipoEmpreendimento);
        processoRepository.saveAndFlush(processo);
        Long tipoEmpreendimentoId = tipoEmpreendimento.getId();

        // Get all the processoList where tipoEmpreendimento equals to tipoEmpreendimentoId
        defaultProcessoShouldBeFound("tipoEmpreendimentoId.equals=" + tipoEmpreendimentoId);

        // Get all the processoList where tipoEmpreendimento equals to (tipoEmpreendimentoId + 1)
        defaultProcessoShouldNotBeFound("tipoEmpreendimentoId.equals=" + (tipoEmpreendimentoId + 1));
    }

    @Test
    @Transactional
    void getAllProcessosByComarcaIsEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);
        Comarca comarca = ComarcaResourceIT.createEntity(em);
        em.persist(comarca);
        em.flush();
        processo.addComarca(comarca);
        processoRepository.saveAndFlush(processo);
        Long comarcaId = comarca.getId();

        // Get all the processoList where comarca equals to comarcaId
        defaultProcessoShouldBeFound("comarcaId.equals=" + comarcaId);

        // Get all the processoList where comarca equals to (comarcaId + 1)
        defaultProcessoShouldNotBeFound("comarcaId.equals=" + (comarcaId + 1));
    }

    @Test
    @Transactional
    void getAllProcessosByQuilomboIsEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);
        Quilombo quilombo = QuilomboResourceIT.createEntity(em);
        em.persist(quilombo);
        em.flush();
        processo.addQuilombo(quilombo);
        processoRepository.saveAndFlush(processo);
        Long quilomboId = quilombo.getId();

        // Get all the processoList where quilombo equals to quilomboId
        defaultProcessoShouldBeFound("quilomboId.equals=" + quilomboId);

        // Get all the processoList where quilombo equals to (quilomboId + 1)
        defaultProcessoShouldNotBeFound("quilomboId.equals=" + (quilomboId + 1));
    }

    @Test
    @Transactional
    void getAllProcessosByMunicipioIsEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);
        Municipio municipio = MunicipioResourceIT.createEntity(em);
        em.persist(municipio);
        em.flush();
        processo.addMunicipio(municipio);
        processoRepository.saveAndFlush(processo);
        Long municipioId = municipio.getId();

        // Get all the processoList where municipio equals to municipioId
        defaultProcessoShouldBeFound("municipioId.equals=" + municipioId);

        // Get all the processoList where municipio equals to (municipioId + 1)
        defaultProcessoShouldNotBeFound("municipioId.equals=" + (municipioId + 1));
    }

    @Test
    @Transactional
    void getAllProcessosByTerritorioIsEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);
        Territorio territorio = TerritorioResourceIT.createEntity(em);
        em.persist(territorio);
        em.flush();
        processo.addTerritorio(territorio);
        processoRepository.saveAndFlush(processo);
        Long territorioId = territorio.getId();

        // Get all the processoList where territorio equals to territorioId
        defaultProcessoShouldBeFound("territorioId.equals=" + territorioId);

        // Get all the processoList where territorio equals to (territorioId + 1)
        defaultProcessoShouldNotBeFound("territorioId.equals=" + (territorioId + 1));
    }

    @Test
    @Transactional
    void getAllProcessosByAtividadeExploracaoIlegalIsEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);
        AtividadeExploracaoIlegal atividadeExploracaoIlegal = AtividadeExploracaoIlegalResourceIT.createEntity(em);
        em.persist(atividadeExploracaoIlegal);
        em.flush();
        processo.addAtividadeExploracaoIlegal(atividadeExploracaoIlegal);
        processoRepository.saveAndFlush(processo);
        Long atividadeExploracaoIlegalId = atividadeExploracaoIlegal.getId();

        // Get all the processoList where atividadeExploracaoIlegal equals to atividadeExploracaoIlegalId
        defaultProcessoShouldBeFound("atividadeExploracaoIlegalId.equals=" + atividadeExploracaoIlegalId);

        // Get all the processoList where atividadeExploracaoIlegal equals to (atividadeExploracaoIlegalId + 1)
        defaultProcessoShouldNotBeFound("atividadeExploracaoIlegalId.equals=" + (atividadeExploracaoIlegalId + 1));
    }

    @Test
    @Transactional
    void getAllProcessosByUnidadeConservacaoIsEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);
        UnidadeConservacao unidadeConservacao = UnidadeConservacaoResourceIT.createEntity(em);
        em.persist(unidadeConservacao);
        em.flush();
        processo.addUnidadeConservacao(unidadeConservacao);
        processoRepository.saveAndFlush(processo);
        Long unidadeConservacaoId = unidadeConservacao.getId();

        // Get all the processoList where unidadeConservacao equals to unidadeConservacaoId
        defaultProcessoShouldBeFound("unidadeConservacaoId.equals=" + unidadeConservacaoId);

        // Get all the processoList where unidadeConservacao equals to (unidadeConservacaoId + 1)
        defaultProcessoShouldNotBeFound("unidadeConservacaoId.equals=" + (unidadeConservacaoId + 1));
    }

    @Test
    @Transactional
    void getAllProcessosByEnvolvidosConflitoLitigioIsEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);
        EnvolvidosConflitoLitigio envolvidosConflitoLitigio = EnvolvidosConflitoLitigioResourceIT.createEntity(em);
        em.persist(envolvidosConflitoLitigio);
        em.flush();
        processo.addEnvolvidosConflitoLitigio(envolvidosConflitoLitigio);
        processoRepository.saveAndFlush(processo);
        Long envolvidosConflitoLitigioId = envolvidosConflitoLitigio.getId();

        // Get all the processoList where envolvidosConflitoLitigio equals to envolvidosConflitoLitigioId
        defaultProcessoShouldBeFound("envolvidosConflitoLitigioId.equals=" + envolvidosConflitoLitigioId);

        // Get all the processoList where envolvidosConflitoLitigio equals to (envolvidosConflitoLitigioId + 1)
        defaultProcessoShouldNotBeFound("envolvidosConflitoLitigioId.equals=" + (envolvidosConflitoLitigioId + 1));
    }

    @Test
    @Transactional
    void getAllProcessosByTerraIndigenaIsEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);
        TerraIndigena terraIndigena = TerraIndigenaResourceIT.createEntity(em);
        em.persist(terraIndigena);
        em.flush();
        processo.addTerraIndigena(terraIndigena);
        processoRepository.saveAndFlush(processo);
        Long terraIndigenaId = terraIndigena.getId();

        // Get all the processoList where terraIndigena equals to terraIndigenaId
        defaultProcessoShouldBeFound("terraIndigenaId.equals=" + terraIndigenaId);

        // Get all the processoList where terraIndigena equals to (terraIndigenaId + 1)
        defaultProcessoShouldNotBeFound("terraIndigenaId.equals=" + (terraIndigenaId + 1));
    }

    @Test
    @Transactional
    void getAllProcessosByProcessoConflitoIsEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);
        ProcessoConflito processoConflito = ProcessoConflitoResourceIT.createEntity(em);
        em.persist(processoConflito);
        em.flush();
        processo.addProcessoConflito(processoConflito);
        processoRepository.saveAndFlush(processo);
        Long processoConflitoId = processoConflito.getId();

        // Get all the processoList where processoConflito equals to processoConflitoId
        defaultProcessoShouldBeFound("processoConflitoId.equals=" + processoConflitoId);

        // Get all the processoList where processoConflito equals to (processoConflitoId + 1)
        defaultProcessoShouldNotBeFound("processoConflitoId.equals=" + (processoConflitoId + 1));
    }

    @Test
    @Transactional
    void getAllProcessosByParteInteresssadaIsEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);
        ParteInteresssada parteInteresssada = ParteInteresssadaResourceIT.createEntity(em);
        em.persist(parteInteresssada);
        em.flush();
        processo.addParteInteresssada(parteInteresssada);
        processoRepository.saveAndFlush(processo);
        Long parteInteresssadaId = parteInteresssada.getId();

        // Get all the processoList where parteInteresssada equals to parteInteresssadaId
        defaultProcessoShouldBeFound("parteInteresssadaId.equals=" + parteInteresssadaId);

        // Get all the processoList where parteInteresssada equals to (parteInteresssadaId + 1)
        defaultProcessoShouldNotBeFound("parteInteresssadaId.equals=" + (parteInteresssadaId + 1));
    }

    @Test
    @Transactional
    void getAllProcessosByRelatorIsEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);
        Relator relator = RelatorResourceIT.createEntity(em);
        em.persist(relator);
        em.flush();
        processo.addRelator(relator);
        processoRepository.saveAndFlush(processo);
        Long relatorId = relator.getId();

        // Get all the processoList where relator equals to relatorId
        defaultProcessoShouldBeFound("relatorId.equals=" + relatorId);

        // Get all the processoList where relator equals to (relatorId + 1)
        defaultProcessoShouldNotBeFound("relatorId.equals=" + (relatorId + 1));
    }

    @Test
    @Transactional
    void getAllProcessosByProblemaJuridicoIsEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);
        ProblemaJuridico problemaJuridico = ProblemaJuridicoResourceIT.createEntity(em);
        em.persist(problemaJuridico);
        em.flush();
        processo.addProblemaJuridico(problemaJuridico);
        processoRepository.saveAndFlush(processo);
        Long problemaJuridicoId = problemaJuridico.getId();

        // Get all the processoList where problemaJuridico equals to problemaJuridicoId
        defaultProcessoShouldBeFound("problemaJuridicoId.equals=" + problemaJuridicoId);

        // Get all the processoList where problemaJuridico equals to (problemaJuridicoId + 1)
        defaultProcessoShouldNotBeFound("problemaJuridicoId.equals=" + (problemaJuridicoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProcessoShouldBeFound(String filter) throws Exception {
        restProcessoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(processo.getId().intValue())))
            .andExpect(jsonPath("$.[*].oficio").value(hasItem(DEFAULT_OFICIO)))
            .andExpect(jsonPath("$.[*].assunto").value(hasItem(DEFAULT_ASSUNTO.toString())))
            .andExpect(jsonPath("$.[*].linkUnico").value(hasItem(DEFAULT_LINK_UNICO)))
            .andExpect(jsonPath("$.[*].linkTrf").value(hasItem(DEFAULT_LINK_TRF)))
            .andExpect(jsonPath("$.[*].subsecaoJudiciaria").value(hasItem(DEFAULT_SUBSECAO_JUDICIARIA)))
            .andExpect(jsonPath("$.[*].turmaTrf1").value(hasItem(DEFAULT_TURMA_TRF_1)))
            .andExpect(jsonPath("$.[*].numeroProcessoAdministrativo").value(hasItem(DEFAULT_NUMERO_PROCESSO_ADMINISTRATIVO)))
            .andExpect(
                jsonPath("$.[*].numeroProcessoJudicialPrimeiraInstancia")
                    .value(hasItem(DEFAULT_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA))
            )
            .andExpect(
                jsonPath("$.[*].numeroProcessoJudicialPrimeiraInstanciaLink")
                    .value(hasItem(DEFAULT_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA_LINK))
            )
            .andExpect(
                jsonPath("$.[*].numeroProcessoJudicialPrimeiraInstanciaObservacoes")
                    .value(hasItem(DEFAULT_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA_OBSERVACOES.toString()))
            )
            .andExpect(jsonPath("$.[*].parecer").value(hasItem(DEFAULT_PARECER.booleanValue())))
            .andExpect(jsonPath("$.[*].apelacao").value(hasItem(DEFAULT_APELACAO)));

        // Check, that the count call also returns 1
        restProcessoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProcessoShouldNotBeFound(String filter) throws Exception {
        restProcessoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProcessoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingProcesso() throws Exception {
        // Get the processo
        restProcessoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewProcesso() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        int databaseSizeBeforeUpdate = processoRepository.findAll().size();

        // Update the processo
        Processo updatedProcesso = processoRepository.findById(processo.getId()).get();
        // Disconnect from session so that the updates on updatedProcesso are not directly saved in db
        em.detach(updatedProcesso);
        updatedProcesso
            .oficio(UPDATED_OFICIO)
            .assunto(UPDATED_ASSUNTO)
            .linkUnico(UPDATED_LINK_UNICO)
            .linkTrf(UPDATED_LINK_TRF)
            .subsecaoJudiciaria(UPDATED_SUBSECAO_JUDICIARIA)
            .turmaTrf1(UPDATED_TURMA_TRF_1)
            .numeroProcessoAdministrativo(UPDATED_NUMERO_PROCESSO_ADMINISTRATIVO)
            .numeroProcessoJudicialPrimeiraInstancia(UPDATED_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA)
            .numeroProcessoJudicialPrimeiraInstanciaLink(UPDATED_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA_LINK)
            .numeroProcessoJudicialPrimeiraInstanciaObservacoes(UPDATED_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA_OBSERVACOES)
            .parecer(UPDATED_PARECER)
            .apelacao(UPDATED_APELACAO);

        restProcessoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedProcesso.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedProcesso))
            )
            .andExpect(status().isOk());

        // Validate the Processo in the database
        List<Processo> processoList = processoRepository.findAll();
        assertThat(processoList).hasSize(databaseSizeBeforeUpdate);
        Processo testProcesso = processoList.get(processoList.size() - 1);
        assertThat(testProcesso.getOficio()).isEqualTo(UPDATED_OFICIO);
        assertThat(testProcesso.getAssunto()).isEqualTo(UPDATED_ASSUNTO);
        assertThat(testProcesso.getLinkUnico()).isEqualTo(UPDATED_LINK_UNICO);
        assertThat(testProcesso.getLinkTrf()).isEqualTo(UPDATED_LINK_TRF);
        assertThat(testProcesso.getSubsecaoJudiciaria()).isEqualTo(UPDATED_SUBSECAO_JUDICIARIA);
        assertThat(testProcesso.getTurmaTrf1()).isEqualTo(UPDATED_TURMA_TRF_1);
        assertThat(testProcesso.getNumeroProcessoAdministrativo()).isEqualTo(UPDATED_NUMERO_PROCESSO_ADMINISTRATIVO);
        assertThat(testProcesso.getNumeroProcessoJudicialPrimeiraInstancia())
            .isEqualTo(UPDATED_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA);
        assertThat(testProcesso.getNumeroProcessoJudicialPrimeiraInstanciaLink())
            .isEqualTo(UPDATED_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA_LINK);
        assertThat(testProcesso.getNumeroProcessoJudicialPrimeiraInstanciaObservacoes())
            .isEqualTo(UPDATED_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA_OBSERVACOES);
        assertThat(testProcesso.getParecer()).isEqualTo(UPDATED_PARECER);
        assertThat(testProcesso.getApelacao()).isEqualTo(UPDATED_APELACAO);
    }

    @Test
    @Transactional
    void putNonExistingProcesso() throws Exception {
        int databaseSizeBeforeUpdate = processoRepository.findAll().size();
        processo.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProcessoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, processo.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(processo))
            )
            .andExpect(status().isBadRequest());

        // Validate the Processo in the database
        List<Processo> processoList = processoRepository.findAll();
        assertThat(processoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProcesso() throws Exception {
        int databaseSizeBeforeUpdate = processoRepository.findAll().size();
        processo.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProcessoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(processo))
            )
            .andExpect(status().isBadRequest());

        // Validate the Processo in the database
        List<Processo> processoList = processoRepository.findAll();
        assertThat(processoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProcesso() throws Exception {
        int databaseSizeBeforeUpdate = processoRepository.findAll().size();
        processo.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProcessoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(processo)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Processo in the database
        List<Processo> processoList = processoRepository.findAll();
        assertThat(processoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProcessoWithPatch() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        int databaseSizeBeforeUpdate = processoRepository.findAll().size();

        // Update the processo using partial update
        Processo partialUpdatedProcesso = new Processo();
        partialUpdatedProcesso.setId(processo.getId());

        partialUpdatedProcesso
            .oficio(UPDATED_OFICIO)
            .linkUnico(UPDATED_LINK_UNICO)
            .linkTrf(UPDATED_LINK_TRF)
            .subsecaoJudiciaria(UPDATED_SUBSECAO_JUDICIARIA)
            .turmaTrf1(UPDATED_TURMA_TRF_1)
            .numeroProcessoAdministrativo(UPDATED_NUMERO_PROCESSO_ADMINISTRATIVO)
            .numeroProcessoJudicialPrimeiraInstanciaLink(UPDATED_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA_LINK)
            .apelacao(UPDATED_APELACAO);

        restProcessoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProcesso.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProcesso))
            )
            .andExpect(status().isOk());

        // Validate the Processo in the database
        List<Processo> processoList = processoRepository.findAll();
        assertThat(processoList).hasSize(databaseSizeBeforeUpdate);
        Processo testProcesso = processoList.get(processoList.size() - 1);
        assertThat(testProcesso.getOficio()).isEqualTo(UPDATED_OFICIO);
        assertThat(testProcesso.getAssunto()).isEqualTo(DEFAULT_ASSUNTO);
        assertThat(testProcesso.getLinkUnico()).isEqualTo(UPDATED_LINK_UNICO);
        assertThat(testProcesso.getLinkTrf()).isEqualTo(UPDATED_LINK_TRF);
        assertThat(testProcesso.getSubsecaoJudiciaria()).isEqualTo(UPDATED_SUBSECAO_JUDICIARIA);
        assertThat(testProcesso.getTurmaTrf1()).isEqualTo(UPDATED_TURMA_TRF_1);
        assertThat(testProcesso.getNumeroProcessoAdministrativo()).isEqualTo(UPDATED_NUMERO_PROCESSO_ADMINISTRATIVO);
        assertThat(testProcesso.getNumeroProcessoJudicialPrimeiraInstancia())
            .isEqualTo(DEFAULT_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA);
        assertThat(testProcesso.getNumeroProcessoJudicialPrimeiraInstanciaLink())
            .isEqualTo(UPDATED_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA_LINK);
        assertThat(testProcesso.getNumeroProcessoJudicialPrimeiraInstanciaObservacoes())
            .isEqualTo(DEFAULT_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA_OBSERVACOES);
        assertThat(testProcesso.getParecer()).isEqualTo(DEFAULT_PARECER);
        assertThat(testProcesso.getApelacao()).isEqualTo(UPDATED_APELACAO);
    }

    @Test
    @Transactional
    void fullUpdateProcessoWithPatch() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        int databaseSizeBeforeUpdate = processoRepository.findAll().size();

        // Update the processo using partial update
        Processo partialUpdatedProcesso = new Processo();
        partialUpdatedProcesso.setId(processo.getId());

        partialUpdatedProcesso
            .oficio(UPDATED_OFICIO)
            .assunto(UPDATED_ASSUNTO)
            .linkUnico(UPDATED_LINK_UNICO)
            .linkTrf(UPDATED_LINK_TRF)
            .subsecaoJudiciaria(UPDATED_SUBSECAO_JUDICIARIA)
            .turmaTrf1(UPDATED_TURMA_TRF_1)
            .numeroProcessoAdministrativo(UPDATED_NUMERO_PROCESSO_ADMINISTRATIVO)
            .numeroProcessoJudicialPrimeiraInstancia(UPDATED_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA)
            .numeroProcessoJudicialPrimeiraInstanciaLink(UPDATED_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA_LINK)
            .numeroProcessoJudicialPrimeiraInstanciaObservacoes(UPDATED_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA_OBSERVACOES)
            .parecer(UPDATED_PARECER)
            .apelacao(UPDATED_APELACAO);

        restProcessoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProcesso.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProcesso))
            )
            .andExpect(status().isOk());

        // Validate the Processo in the database
        List<Processo> processoList = processoRepository.findAll();
        assertThat(processoList).hasSize(databaseSizeBeforeUpdate);
        Processo testProcesso = processoList.get(processoList.size() - 1);
        assertThat(testProcesso.getOficio()).isEqualTo(UPDATED_OFICIO);
        assertThat(testProcesso.getAssunto()).isEqualTo(UPDATED_ASSUNTO);
        assertThat(testProcesso.getLinkUnico()).isEqualTo(UPDATED_LINK_UNICO);
        assertThat(testProcesso.getLinkTrf()).isEqualTo(UPDATED_LINK_TRF);
        assertThat(testProcesso.getSubsecaoJudiciaria()).isEqualTo(UPDATED_SUBSECAO_JUDICIARIA);
        assertThat(testProcesso.getTurmaTrf1()).isEqualTo(UPDATED_TURMA_TRF_1);
        assertThat(testProcesso.getNumeroProcessoAdministrativo()).isEqualTo(UPDATED_NUMERO_PROCESSO_ADMINISTRATIVO);
        assertThat(testProcesso.getNumeroProcessoJudicialPrimeiraInstancia())
            .isEqualTo(UPDATED_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA);
        assertThat(testProcesso.getNumeroProcessoJudicialPrimeiraInstanciaLink())
            .isEqualTo(UPDATED_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA_LINK);
        assertThat(testProcesso.getNumeroProcessoJudicialPrimeiraInstanciaObservacoes())
            .isEqualTo(UPDATED_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA_OBSERVACOES);
        assertThat(testProcesso.getParecer()).isEqualTo(UPDATED_PARECER);
        assertThat(testProcesso.getApelacao()).isEqualTo(UPDATED_APELACAO);
    }

    @Test
    @Transactional
    void patchNonExistingProcesso() throws Exception {
        int databaseSizeBeforeUpdate = processoRepository.findAll().size();
        processo.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProcessoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, processo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(processo))
            )
            .andExpect(status().isBadRequest());

        // Validate the Processo in the database
        List<Processo> processoList = processoRepository.findAll();
        assertThat(processoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProcesso() throws Exception {
        int databaseSizeBeforeUpdate = processoRepository.findAll().size();
        processo.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProcessoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(processo))
            )
            .andExpect(status().isBadRequest());

        // Validate the Processo in the database
        List<Processo> processoList = processoRepository.findAll();
        assertThat(processoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProcesso() throws Exception {
        int databaseSizeBeforeUpdate = processoRepository.findAll().size();
        processo.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProcessoMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(processo)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Processo in the database
        List<Processo> processoList = processoRepository.findAll();
        assertThat(processoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProcesso() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        int databaseSizeBeforeDelete = processoRepository.findAll().size();

        // Delete the processo
        restProcessoMockMvc
            .perform(delete(ENTITY_API_URL_ID, processo.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Processo> processoList = processoRepository.findAll();
        assertThat(processoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
