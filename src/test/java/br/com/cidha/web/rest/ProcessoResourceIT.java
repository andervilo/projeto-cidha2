package br.com.cidha.web.rest;

import static br.com.cidha.web.rest.TestUtil.sameNumber;
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
import br.com.cidha.domain.SecaoJudiciaria;
import br.com.cidha.domain.TerraIndigena;
import br.com.cidha.domain.Territorio;
import br.com.cidha.domain.TipoDecisao;
import br.com.cidha.domain.TipoEmpreendimento;
import br.com.cidha.domain.UnidadeConservacao;
import br.com.cidha.domain.enumeration.StatusProcesso;
import br.com.cidha.repository.ProcessoRepository;
import br.com.cidha.service.ProcessoService;
import br.com.cidha.service.criteria.ProcessoCriteria;
import java.math.BigDecimal;
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

    private static final String DEFAULT_NUMERO_PROCESSO = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_PROCESSO = "BBBBBBBBBB";

    private static final String DEFAULT_OFICIO = "AAAAAAAAAA";
    private static final String UPDATED_OFICIO = "BBBBBBBBBB";

    private static final String DEFAULT_ASSUNTO = "AAAAAAAAAA";
    private static final String UPDATED_ASSUNTO = "BBBBBBBBBB";

    private static final String DEFAULT_LINK_UNICO = "AAAAAAAAAA";
    private static final String UPDATED_LINK_UNICO = "BBBBBBBBBB";

    private static final String DEFAULT_LINK_TRF = "AAAAAAAAAA";
    private static final String UPDATED_LINK_TRF = "BBBBBBBBBB";

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

    private static final String DEFAULT_FOLHAS_PROCESSO_CONCESSAO_LIMINAR = "AAAAAAAAAA";
    private static final String UPDATED_FOLHAS_PROCESSO_CONCESSAO_LIMINAR = "BBBBBBBBBB";

    private static final String DEFAULT_CONCESSAO_LIMINAR_OBSERVACOES = "AAAAAAAAAA";
    private static final String UPDATED_CONCESSAO_LIMINAR_OBSERVACOES = "BBBBBBBBBB";

    private static final String DEFAULT_FOLHAS_PROCESSO_CASSACAO = "AAAAAAAAAA";
    private static final String UPDATED_FOLHAS_PROCESSO_CASSACAO = "BBBBBBBBBB";

    private static final String DEFAULT_FOLHAS_PARECER = "AAAAAAAAAA";
    private static final String UPDATED_FOLHAS_PARECER = "BBBBBBBBBB";

    private static final String DEFAULT_FOLHAS_EMBARGO = "AAAAAAAAAA";
    private static final String UPDATED_FOLHAS_EMBARGO = "BBBBBBBBBB";

    private static final String DEFAULT_ACORDAO_EMBARGO = "AAAAAAAAAA";
    private static final String UPDATED_ACORDAO_EMBARGO = "BBBBBBBBBB";

    private static final String DEFAULT_FOLHAS_CIENCIA_JULG_EMBARGOS = "AAAAAAAAAA";
    private static final String UPDATED_FOLHAS_CIENCIA_JULG_EMBARGOS = "BBBBBBBBBB";

    private static final String DEFAULT_APELACAO = "AAAAAAAAAA";
    private static final String UPDATED_APELACAO = "BBBBBBBBBB";

    private static final String DEFAULT_FOLHAS_APELACAO = "AAAAAAAAAA";
    private static final String UPDATED_FOLHAS_APELACAO = "BBBBBBBBBB";

    private static final String DEFAULT_ACORDAO_APELACAO = "AAAAAAAAAA";
    private static final String UPDATED_ACORDAO_APELACAO = "BBBBBBBBBB";

    private static final String DEFAULT_FOLHAS_CIENCIA_JULG_APELACAO = "AAAAAAAAAA";
    private static final String UPDATED_FOLHAS_CIENCIA_JULG_APELACAO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_EMBARGO_DECLARACAO = false;
    private static final Boolean UPDATED_EMBARGO_DECLARACAO = true;

    private static final Boolean DEFAULT_EMBARGO_RECURSO_EXTRAORDINARIO = false;
    private static final Boolean UPDATED_EMBARGO_RECURSO_EXTRAORDINARIO = true;

    private static final String DEFAULT_FOLHAS_RECURSO_ESPECIAL = "AAAAAAAAAA";
    private static final String UPDATED_FOLHAS_RECURSO_ESPECIAL = "BBBBBBBBBB";

    private static final String DEFAULT_ACORDAO_RECURSO_ESPECIAL = "AAAAAAAAAA";
    private static final String UPDATED_ACORDAO_RECURSO_ESPECIAL = "BBBBBBBBBB";

    private static final String DEFAULT_FOLHAS_CIENCIA_JULGAMENTO_RECURSO_ESPECIAL = "AAAAAAAAAA";
    private static final String UPDATED_FOLHAS_CIENCIA_JULGAMENTO_RECURSO_ESPECIAL = "BBBBBBBBBB";

    private static final Boolean DEFAULT_EMBARGO_RECURSO_ESPECIAL = false;
    private static final Boolean UPDATED_EMBARGO_RECURSO_ESPECIAL = true;

    private static final String DEFAULT_FOLHAS_CIENCIA = "AAAAAAAAAA";
    private static final String UPDATED_FOLHAS_CIENCIA = "BBBBBBBBBB";

    private static final String DEFAULT_AGRAVO_RESP_RE = "AAAAAAAAAA";
    private static final String UPDATED_AGRAVO_RESP_RE = "BBBBBBBBBB";

    private static final String DEFAULT_FOLHAS_RESP_RE = "AAAAAAAAAA";
    private static final String UPDATED_FOLHAS_RESP_RE = "BBBBBBBBBB";

    private static final String DEFAULT_ACORDAO_AGRAVO_RESP_RE = "AAAAAAAAAA";
    private static final String UPDATED_ACORDAO_AGRAVO_RESP_RE = "BBBBBBBBBB";

    private static final String DEFAULT_FOLHAS_CIENCIA_JULGAMENTO_AGRAVO_RESP_RE = "AAAAAAAAAA";
    private static final String UPDATED_FOLHAS_CIENCIA_JULGAMENTO_AGRAVO_RESP_RE = "BBBBBBBBBB";

    private static final String DEFAULT_EMBARGO_RESP_RE = "AAAAAAAAAA";
    private static final String UPDATED_EMBARGO_RESP_RE = "BBBBBBBBBB";

    private static final String DEFAULT_AGRAVO_INTERNO = "AAAAAAAAAA";
    private static final String UPDATED_AGRAVO_INTERNO = "BBBBBBBBBB";

    private static final String DEFAULT_FOLHAS_AGRAVO_INTERNO = "AAAAAAAAAA";
    private static final String UPDATED_FOLHAS_AGRAVO_INTERNO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_EMBARGO_RECURSO_AGRAVO = false;
    private static final Boolean UPDATED_EMBARGO_RECURSO_AGRAVO = true;

    private static final String DEFAULT_OBSERVACOES = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVACOES = "BBBBBBBBBB";

    private static final Boolean DEFAULT_RECURSO_STJ = false;
    private static final Boolean UPDATED_RECURSO_STJ = true;

    private static final String DEFAULT_LINK_RECURSO_STJ = "AAAAAAAAAA";
    private static final String UPDATED_LINK_RECURSO_STJ = "BBBBBBBBBB";

    private static final String DEFAULT_FOLHAS_RECURSO_STJ = "AAAAAAAAAA";
    private static final String UPDATED_FOLHAS_RECURSO_STJ = "BBBBBBBBBB";

    private static final Boolean DEFAULT_RECURSO_STF = false;
    private static final Boolean UPDATED_RECURSO_STF = true;

    private static final String DEFAULT_LINK_RECURSO_STF = "AAAAAAAAAA";
    private static final String UPDATED_LINK_RECURSO_STF = "BBBBBBBBBB";

    private static final String DEFAULT_FOLHAS_RECURSO_STF = "AAAAAAAAAA";
    private static final String UPDATED_FOLHAS_RECURSO_STF = "BBBBBBBBBB";

    private static final String DEFAULT_FOLHAS_MEMORIAL_MPF = "AAAAAAAAAA";
    private static final String UPDATED_FOLHAS_MEMORIAL_MPF = "BBBBBBBBBB";

    private static final Boolean DEFAULT_EXECUSAO_PROVISORIA = false;
    private static final Boolean UPDATED_EXECUSAO_PROVISORIA = true;

    private static final String DEFAULT_NUMERACAO_EXECUSAO_PROVISORIA = "AAAAAAAAAA";
    private static final String UPDATED_NUMERACAO_EXECUSAO_PROVISORIA = "BBBBBBBBBB";

    private static final String DEFAULT_RECUPERACAO_EFETIVA_CUMPRIMENTO_SENTENCA = "AAAAAAAAAA";
    private static final String UPDATED_RECUPERACAO_EFETIVA_CUMPRIMENTO_SENTENCA = "BBBBBBBBBB";

    private static final String DEFAULT_RECUPERACAO_EFETIVA_CUMPRIMENTO_SENTENCA_OBSERVACOES = "AAAAAAAAAA";
    private static final String UPDATED_RECUPERACAO_EFETIVA_CUMPRIMENTO_SENTENCA_OBSERVACOES = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ENVOLVE_EMPREENDIMENTO = false;
    private static final Boolean UPDATED_ENVOLVE_EMPREENDIMENTO = true;

    private static final Boolean DEFAULT_ENVOLVE_EXPLORACAO_ILEGAL = false;
    private static final Boolean UPDATED_ENVOLVE_EXPLORACAO_ILEGAL = true;

    private static final Boolean DEFAULT_ENVOLVE_TERRA_QUILOMBOLA = false;
    private static final Boolean UPDATED_ENVOLVE_TERRA_QUILOMBOLA = true;

    private static final Boolean DEFAULT_ENVOLVE_TERRA_COMUNIDADE_TRADICIONAL = false;
    private static final Boolean UPDATED_ENVOLVE_TERRA_COMUNIDADE_TRADICIONAL = true;

    private static final Boolean DEFAULT_ENVOLVE_TERRA_INDIGENA = false;
    private static final Boolean UPDATED_ENVOLVE_TERRA_INDIGENA = true;

    private static final String DEFAULT_RESUMO_FATOS = "AAAAAAAAAA";
    private static final String UPDATED_RESUMO_FATOS = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_TAMANHO_AREA = new BigDecimal(1);
    private static final BigDecimal UPDATED_TAMANHO_AREA = new BigDecimal(2);
    private static final BigDecimal SMALLER_TAMANHO_AREA = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_VALOR_AREA = new BigDecimal(1);
    private static final BigDecimal UPDATED_VALOR_AREA = new BigDecimal(2);
    private static final BigDecimal SMALLER_VALOR_AREA = new BigDecimal(1 - 1);

    private static final String DEFAULT_TAMANHO_AREA_OBSERVACAO = "AAAAAAAAAA";
    private static final String UPDATED_TAMANHO_AREA_OBSERVACAO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_DADOS_GEOGRAFICOS_LITIGIO_CONFLITO = false;
    private static final Boolean UPDATED_DADOS_GEOGRAFICOS_LITIGIO_CONFLITO = true;

    private static final String DEFAULT_LATITUDE = "AAAAAAAAAA";
    private static final String UPDATED_LATITUDE = "BBBBBBBBBB";

    private static final String DEFAULT_LONGITUDE = "AAAAAAAAAA";
    private static final String UPDATED_LONGITUDE = "BBBBBBBBBB";

    private static final String DEFAULT_NUMERO_PROCESSO_MPF = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_PROCESSO_MPF = "BBBBBBBBBB";

    private static final String DEFAULT_NUMERO_EMBARGO = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_EMBARGO = "BBBBBBBBBB";

    private static final String DEFAULT_PAUTA_APELACAO = "AAAAAAAAAA";
    private static final String UPDATED_PAUTA_APELACAO = "BBBBBBBBBB";

    private static final String DEFAULT_NUMERO_RECURSO_ESPECIAL = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_RECURSO_ESPECIAL = "BBBBBBBBBB";

    private static final String DEFAULT_ADMISSIBLIDADE = "AAAAAAAAAA";
    private static final String UPDATED_ADMISSIBLIDADE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ENVOLVE_GRANDE_PROJETO = false;
    private static final Boolean UPDATED_ENVOLVE_GRANDE_PROJETO = true;

    private static final Boolean DEFAULT_ENVOLVE_UNIDADE_CONSERVACAO = false;
    private static final Boolean UPDATED_ENVOLVE_UNIDADE_CONSERVACAO = true;

    private static final String DEFAULT_LINK_REFERENCIA = "AAAAAAAAAA";
    private static final String UPDATED_LINK_REFERENCIA = "BBBBBBBBBB";

    private static final StatusProcesso DEFAULT_STATUS_PROCESSO = StatusProcesso.EM_ANDAMENTO;
    private static final StatusProcesso UPDATED_STATUS_PROCESSO = StatusProcesso.FINALIZADO;

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
            .numeroProcesso(DEFAULT_NUMERO_PROCESSO)
            .oficio(DEFAULT_OFICIO)
            .assunto(DEFAULT_ASSUNTO)
            .linkUnico(DEFAULT_LINK_UNICO)
            .linkTrf(DEFAULT_LINK_TRF)
            .turmaTrf1(DEFAULT_TURMA_TRF_1)
            .numeroProcessoAdministrativo(DEFAULT_NUMERO_PROCESSO_ADMINISTRATIVO)
            .numeroProcessoJudicialPrimeiraInstancia(DEFAULT_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA)
            .numeroProcessoJudicialPrimeiraInstanciaLink(DEFAULT_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA_LINK)
            .numeroProcessoJudicialPrimeiraInstanciaObservacoes(DEFAULT_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA_OBSERVACOES)
            .parecer(DEFAULT_PARECER)
            .folhasProcessoConcessaoLiminar(DEFAULT_FOLHAS_PROCESSO_CONCESSAO_LIMINAR)
            .concessaoLiminarObservacoes(DEFAULT_CONCESSAO_LIMINAR_OBSERVACOES)
            .folhasProcessoCassacao(DEFAULT_FOLHAS_PROCESSO_CASSACAO)
            .folhasParecer(DEFAULT_FOLHAS_PARECER)
            .folhasEmbargo(DEFAULT_FOLHAS_EMBARGO)
            .acordaoEmbargo(DEFAULT_ACORDAO_EMBARGO)
            .folhasCienciaJulgEmbargos(DEFAULT_FOLHAS_CIENCIA_JULG_EMBARGOS)
            .apelacao(DEFAULT_APELACAO)
            .folhasApelacao(DEFAULT_FOLHAS_APELACAO)
            .acordaoApelacao(DEFAULT_ACORDAO_APELACAO)
            .folhasCienciaJulgApelacao(DEFAULT_FOLHAS_CIENCIA_JULG_APELACAO)
            .embargoDeclaracao(DEFAULT_EMBARGO_DECLARACAO)
            .embargoRecursoExtraordinario(DEFAULT_EMBARGO_RECURSO_EXTRAORDINARIO)
            .folhasRecursoEspecial(DEFAULT_FOLHAS_RECURSO_ESPECIAL)
            .acordaoRecursoEspecial(DEFAULT_ACORDAO_RECURSO_ESPECIAL)
            .folhasCienciaJulgamentoRecursoEspecial(DEFAULT_FOLHAS_CIENCIA_JULGAMENTO_RECURSO_ESPECIAL)
            .embargoRecursoEspecial(DEFAULT_EMBARGO_RECURSO_ESPECIAL)
            .folhasCiencia(DEFAULT_FOLHAS_CIENCIA)
            .agravoRespRe(DEFAULT_AGRAVO_RESP_RE)
            .folhasRespRe(DEFAULT_FOLHAS_RESP_RE)
            .acordaoAgravoRespRe(DEFAULT_ACORDAO_AGRAVO_RESP_RE)
            .folhasCienciaJulgamentoAgravoRespRe(DEFAULT_FOLHAS_CIENCIA_JULGAMENTO_AGRAVO_RESP_RE)
            .embargoRespRe(DEFAULT_EMBARGO_RESP_RE)
            .agravoInterno(DEFAULT_AGRAVO_INTERNO)
            .folhasAgravoInterno(DEFAULT_FOLHAS_AGRAVO_INTERNO)
            .embargoRecursoAgravo(DEFAULT_EMBARGO_RECURSO_AGRAVO)
            .observacoes(DEFAULT_OBSERVACOES)
            .recursoSTJ(DEFAULT_RECURSO_STJ)
            .linkRecursoSTJ(DEFAULT_LINK_RECURSO_STJ)
            .folhasRecursoSTJ(DEFAULT_FOLHAS_RECURSO_STJ)
            .recursoSTF(DEFAULT_RECURSO_STF)
            .linkRecursoSTF(DEFAULT_LINK_RECURSO_STF)
            .folhasRecursoSTF(DEFAULT_FOLHAS_RECURSO_STF)
            .folhasMemorialMPF(DEFAULT_FOLHAS_MEMORIAL_MPF)
            .execusaoProvisoria(DEFAULT_EXECUSAO_PROVISORIA)
            .numeracaoExecusaoProvisoria(DEFAULT_NUMERACAO_EXECUSAO_PROVISORIA)
            .recuperacaoEfetivaCumprimentoSentenca(DEFAULT_RECUPERACAO_EFETIVA_CUMPRIMENTO_SENTENCA)
            .recuperacaoEfetivaCumprimentoSentencaObservacoes(DEFAULT_RECUPERACAO_EFETIVA_CUMPRIMENTO_SENTENCA_OBSERVACOES)
            .envolveEmpreendimento(DEFAULT_ENVOLVE_EMPREENDIMENTO)
            .envolveExploracaoIlegal(DEFAULT_ENVOLVE_EXPLORACAO_ILEGAL)
            .envolveTerraQuilombola(DEFAULT_ENVOLVE_TERRA_QUILOMBOLA)
            .envolveTerraComunidadeTradicional(DEFAULT_ENVOLVE_TERRA_COMUNIDADE_TRADICIONAL)
            .envolveTerraIndigena(DEFAULT_ENVOLVE_TERRA_INDIGENA)
            .resumoFatos(DEFAULT_RESUMO_FATOS)
            .tamanhoArea(DEFAULT_TAMANHO_AREA)
            .valorArea(DEFAULT_VALOR_AREA)
            .tamanhoAreaObservacao(DEFAULT_TAMANHO_AREA_OBSERVACAO)
            .dadosGeograficosLitigioConflito(DEFAULT_DADOS_GEOGRAFICOS_LITIGIO_CONFLITO)
            .latitude(DEFAULT_LATITUDE)
            .longitude(DEFAULT_LONGITUDE)
            .numeroProcessoMPF(DEFAULT_NUMERO_PROCESSO_MPF)
            .numeroEmbargo(DEFAULT_NUMERO_EMBARGO)
            .pautaApelacao(DEFAULT_PAUTA_APELACAO)
            .numeroRecursoEspecial(DEFAULT_NUMERO_RECURSO_ESPECIAL)
            .admissiblidade(DEFAULT_ADMISSIBLIDADE)
            .envolveGrandeProjeto(DEFAULT_ENVOLVE_GRANDE_PROJETO)
            .envolveUnidadeConservacao(DEFAULT_ENVOLVE_UNIDADE_CONSERVACAO)
            .linkReferencia(DEFAULT_LINK_REFERENCIA)
            .statusProcesso(DEFAULT_STATUS_PROCESSO);
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
            .numeroProcesso(UPDATED_NUMERO_PROCESSO)
            .oficio(UPDATED_OFICIO)
            .assunto(UPDATED_ASSUNTO)
            .linkUnico(UPDATED_LINK_UNICO)
            .linkTrf(UPDATED_LINK_TRF)
            .turmaTrf1(UPDATED_TURMA_TRF_1)
            .numeroProcessoAdministrativo(UPDATED_NUMERO_PROCESSO_ADMINISTRATIVO)
            .numeroProcessoJudicialPrimeiraInstancia(UPDATED_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA)
            .numeroProcessoJudicialPrimeiraInstanciaLink(UPDATED_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA_LINK)
            .numeroProcessoJudicialPrimeiraInstanciaObservacoes(UPDATED_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA_OBSERVACOES)
            .parecer(UPDATED_PARECER)
            .folhasProcessoConcessaoLiminar(UPDATED_FOLHAS_PROCESSO_CONCESSAO_LIMINAR)
            .concessaoLiminarObservacoes(UPDATED_CONCESSAO_LIMINAR_OBSERVACOES)
            .folhasProcessoCassacao(UPDATED_FOLHAS_PROCESSO_CASSACAO)
            .folhasParecer(UPDATED_FOLHAS_PARECER)
            .folhasEmbargo(UPDATED_FOLHAS_EMBARGO)
            .acordaoEmbargo(UPDATED_ACORDAO_EMBARGO)
            .folhasCienciaJulgEmbargos(UPDATED_FOLHAS_CIENCIA_JULG_EMBARGOS)
            .apelacao(UPDATED_APELACAO)
            .folhasApelacao(UPDATED_FOLHAS_APELACAO)
            .acordaoApelacao(UPDATED_ACORDAO_APELACAO)
            .folhasCienciaJulgApelacao(UPDATED_FOLHAS_CIENCIA_JULG_APELACAO)
            .embargoDeclaracao(UPDATED_EMBARGO_DECLARACAO)
            .embargoRecursoExtraordinario(UPDATED_EMBARGO_RECURSO_EXTRAORDINARIO)
            .folhasRecursoEspecial(UPDATED_FOLHAS_RECURSO_ESPECIAL)
            .acordaoRecursoEspecial(UPDATED_ACORDAO_RECURSO_ESPECIAL)
            .folhasCienciaJulgamentoRecursoEspecial(UPDATED_FOLHAS_CIENCIA_JULGAMENTO_RECURSO_ESPECIAL)
            .embargoRecursoEspecial(UPDATED_EMBARGO_RECURSO_ESPECIAL)
            .folhasCiencia(UPDATED_FOLHAS_CIENCIA)
            .agravoRespRe(UPDATED_AGRAVO_RESP_RE)
            .folhasRespRe(UPDATED_FOLHAS_RESP_RE)
            .acordaoAgravoRespRe(UPDATED_ACORDAO_AGRAVO_RESP_RE)
            .folhasCienciaJulgamentoAgravoRespRe(UPDATED_FOLHAS_CIENCIA_JULGAMENTO_AGRAVO_RESP_RE)
            .embargoRespRe(UPDATED_EMBARGO_RESP_RE)
            .agravoInterno(UPDATED_AGRAVO_INTERNO)
            .folhasAgravoInterno(UPDATED_FOLHAS_AGRAVO_INTERNO)
            .embargoRecursoAgravo(UPDATED_EMBARGO_RECURSO_AGRAVO)
            .observacoes(UPDATED_OBSERVACOES)
            .recursoSTJ(UPDATED_RECURSO_STJ)
            .linkRecursoSTJ(UPDATED_LINK_RECURSO_STJ)
            .folhasRecursoSTJ(UPDATED_FOLHAS_RECURSO_STJ)
            .recursoSTF(UPDATED_RECURSO_STF)
            .linkRecursoSTF(UPDATED_LINK_RECURSO_STF)
            .folhasRecursoSTF(UPDATED_FOLHAS_RECURSO_STF)
            .folhasMemorialMPF(UPDATED_FOLHAS_MEMORIAL_MPF)
            .execusaoProvisoria(UPDATED_EXECUSAO_PROVISORIA)
            .numeracaoExecusaoProvisoria(UPDATED_NUMERACAO_EXECUSAO_PROVISORIA)
            .recuperacaoEfetivaCumprimentoSentenca(UPDATED_RECUPERACAO_EFETIVA_CUMPRIMENTO_SENTENCA)
            .recuperacaoEfetivaCumprimentoSentencaObservacoes(UPDATED_RECUPERACAO_EFETIVA_CUMPRIMENTO_SENTENCA_OBSERVACOES)
            .envolveEmpreendimento(UPDATED_ENVOLVE_EMPREENDIMENTO)
            .envolveExploracaoIlegal(UPDATED_ENVOLVE_EXPLORACAO_ILEGAL)
            .envolveTerraQuilombola(UPDATED_ENVOLVE_TERRA_QUILOMBOLA)
            .envolveTerraComunidadeTradicional(UPDATED_ENVOLVE_TERRA_COMUNIDADE_TRADICIONAL)
            .envolveTerraIndigena(UPDATED_ENVOLVE_TERRA_INDIGENA)
            .resumoFatos(UPDATED_RESUMO_FATOS)
            .tamanhoArea(UPDATED_TAMANHO_AREA)
            .valorArea(UPDATED_VALOR_AREA)
            .tamanhoAreaObservacao(UPDATED_TAMANHO_AREA_OBSERVACAO)
            .dadosGeograficosLitigioConflito(UPDATED_DADOS_GEOGRAFICOS_LITIGIO_CONFLITO)
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE)
            .numeroProcessoMPF(UPDATED_NUMERO_PROCESSO_MPF)
            .numeroEmbargo(UPDATED_NUMERO_EMBARGO)
            .pautaApelacao(UPDATED_PAUTA_APELACAO)
            .numeroRecursoEspecial(UPDATED_NUMERO_RECURSO_ESPECIAL)
            .admissiblidade(UPDATED_ADMISSIBLIDADE)
            .envolveGrandeProjeto(UPDATED_ENVOLVE_GRANDE_PROJETO)
            .envolveUnidadeConservacao(UPDATED_ENVOLVE_UNIDADE_CONSERVACAO)
            .linkReferencia(UPDATED_LINK_REFERENCIA)
            .statusProcesso(UPDATED_STATUS_PROCESSO);
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
        assertThat(testProcesso.getNumeroProcesso()).isEqualTo(DEFAULT_NUMERO_PROCESSO);
        assertThat(testProcesso.getOficio()).isEqualTo(DEFAULT_OFICIO);
        assertThat(testProcesso.getAssunto()).isEqualTo(DEFAULT_ASSUNTO);
        assertThat(testProcesso.getLinkUnico()).isEqualTo(DEFAULT_LINK_UNICO);
        assertThat(testProcesso.getLinkTrf()).isEqualTo(DEFAULT_LINK_TRF);
        assertThat(testProcesso.getTurmaTrf1()).isEqualTo(DEFAULT_TURMA_TRF_1);
        assertThat(testProcesso.getNumeroProcessoAdministrativo()).isEqualTo(DEFAULT_NUMERO_PROCESSO_ADMINISTRATIVO);
        assertThat(testProcesso.getNumeroProcessoJudicialPrimeiraInstancia())
            .isEqualTo(DEFAULT_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA);
        assertThat(testProcesso.getNumeroProcessoJudicialPrimeiraInstanciaLink())
            .isEqualTo(DEFAULT_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA_LINK);
        assertThat(testProcesso.getNumeroProcessoJudicialPrimeiraInstanciaObservacoes())
            .isEqualTo(DEFAULT_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA_OBSERVACOES);
        assertThat(testProcesso.getParecer()).isEqualTo(DEFAULT_PARECER);
        assertThat(testProcesso.getFolhasProcessoConcessaoLiminar()).isEqualTo(DEFAULT_FOLHAS_PROCESSO_CONCESSAO_LIMINAR);
        assertThat(testProcesso.getConcessaoLiminarObservacoes()).isEqualTo(DEFAULT_CONCESSAO_LIMINAR_OBSERVACOES);
        assertThat(testProcesso.getFolhasProcessoCassacao()).isEqualTo(DEFAULT_FOLHAS_PROCESSO_CASSACAO);
        assertThat(testProcesso.getFolhasParecer()).isEqualTo(DEFAULT_FOLHAS_PARECER);
        assertThat(testProcesso.getFolhasEmbargo()).isEqualTo(DEFAULT_FOLHAS_EMBARGO);
        assertThat(testProcesso.getAcordaoEmbargo()).isEqualTo(DEFAULT_ACORDAO_EMBARGO);
        assertThat(testProcesso.getFolhasCienciaJulgEmbargos()).isEqualTo(DEFAULT_FOLHAS_CIENCIA_JULG_EMBARGOS);
        assertThat(testProcesso.getApelacao()).isEqualTo(DEFAULT_APELACAO);
        assertThat(testProcesso.getFolhasApelacao()).isEqualTo(DEFAULT_FOLHAS_APELACAO);
        assertThat(testProcesso.getAcordaoApelacao()).isEqualTo(DEFAULT_ACORDAO_APELACAO);
        assertThat(testProcesso.getFolhasCienciaJulgApelacao()).isEqualTo(DEFAULT_FOLHAS_CIENCIA_JULG_APELACAO);
        assertThat(testProcesso.getEmbargoDeclaracao()).isEqualTo(DEFAULT_EMBARGO_DECLARACAO);
        assertThat(testProcesso.getEmbargoRecursoExtraordinario()).isEqualTo(DEFAULT_EMBARGO_RECURSO_EXTRAORDINARIO);
        assertThat(testProcesso.getFolhasRecursoEspecial()).isEqualTo(DEFAULT_FOLHAS_RECURSO_ESPECIAL);
        assertThat(testProcesso.getAcordaoRecursoEspecial()).isEqualTo(DEFAULT_ACORDAO_RECURSO_ESPECIAL);
        assertThat(testProcesso.getFolhasCienciaJulgamentoRecursoEspecial()).isEqualTo(DEFAULT_FOLHAS_CIENCIA_JULGAMENTO_RECURSO_ESPECIAL);
        assertThat(testProcesso.getEmbargoRecursoEspecial()).isEqualTo(DEFAULT_EMBARGO_RECURSO_ESPECIAL);
        assertThat(testProcesso.getFolhasCiencia()).isEqualTo(DEFAULT_FOLHAS_CIENCIA);
        assertThat(testProcesso.getAgravoRespRe()).isEqualTo(DEFAULT_AGRAVO_RESP_RE);
        assertThat(testProcesso.getFolhasRespRe()).isEqualTo(DEFAULT_FOLHAS_RESP_RE);
        assertThat(testProcesso.getAcordaoAgravoRespRe()).isEqualTo(DEFAULT_ACORDAO_AGRAVO_RESP_RE);
        assertThat(testProcesso.getFolhasCienciaJulgamentoAgravoRespRe()).isEqualTo(DEFAULT_FOLHAS_CIENCIA_JULGAMENTO_AGRAVO_RESP_RE);
        assertThat(testProcesso.getEmbargoRespRe()).isEqualTo(DEFAULT_EMBARGO_RESP_RE);
        assertThat(testProcesso.getAgravoInterno()).isEqualTo(DEFAULT_AGRAVO_INTERNO);
        assertThat(testProcesso.getFolhasAgravoInterno()).isEqualTo(DEFAULT_FOLHAS_AGRAVO_INTERNO);
        assertThat(testProcesso.getEmbargoRecursoAgravo()).isEqualTo(DEFAULT_EMBARGO_RECURSO_AGRAVO);
        assertThat(testProcesso.getObservacoes()).isEqualTo(DEFAULT_OBSERVACOES);
        assertThat(testProcesso.getRecursoSTJ()).isEqualTo(DEFAULT_RECURSO_STJ);
        assertThat(testProcesso.getLinkRecursoSTJ()).isEqualTo(DEFAULT_LINK_RECURSO_STJ);
        assertThat(testProcesso.getFolhasRecursoSTJ()).isEqualTo(DEFAULT_FOLHAS_RECURSO_STJ);
        assertThat(testProcesso.getRecursoSTF()).isEqualTo(DEFAULT_RECURSO_STF);
        assertThat(testProcesso.getLinkRecursoSTF()).isEqualTo(DEFAULT_LINK_RECURSO_STF);
        assertThat(testProcesso.getFolhasRecursoSTF()).isEqualTo(DEFAULT_FOLHAS_RECURSO_STF);
        assertThat(testProcesso.getFolhasMemorialMPF()).isEqualTo(DEFAULT_FOLHAS_MEMORIAL_MPF);
        assertThat(testProcesso.getExecusaoProvisoria()).isEqualTo(DEFAULT_EXECUSAO_PROVISORIA);
        assertThat(testProcesso.getNumeracaoExecusaoProvisoria()).isEqualTo(DEFAULT_NUMERACAO_EXECUSAO_PROVISORIA);
        assertThat(testProcesso.getRecuperacaoEfetivaCumprimentoSentenca()).isEqualTo(DEFAULT_RECUPERACAO_EFETIVA_CUMPRIMENTO_SENTENCA);
        assertThat(testProcesso.getRecuperacaoEfetivaCumprimentoSentencaObservacoes())
            .isEqualTo(DEFAULT_RECUPERACAO_EFETIVA_CUMPRIMENTO_SENTENCA_OBSERVACOES);
        assertThat(testProcesso.getEnvolveEmpreendimento()).isEqualTo(DEFAULT_ENVOLVE_EMPREENDIMENTO);
        assertThat(testProcesso.getEnvolveExploracaoIlegal()).isEqualTo(DEFAULT_ENVOLVE_EXPLORACAO_ILEGAL);
        assertThat(testProcesso.getEnvolveTerraQuilombola()).isEqualTo(DEFAULT_ENVOLVE_TERRA_QUILOMBOLA);
        assertThat(testProcesso.getEnvolveTerraComunidadeTradicional()).isEqualTo(DEFAULT_ENVOLVE_TERRA_COMUNIDADE_TRADICIONAL);
        assertThat(testProcesso.getEnvolveTerraIndigena()).isEqualTo(DEFAULT_ENVOLVE_TERRA_INDIGENA);
        assertThat(testProcesso.getResumoFatos()).isEqualTo(DEFAULT_RESUMO_FATOS);
        assertThat(testProcesso.getTamanhoArea()).isEqualByComparingTo(DEFAULT_TAMANHO_AREA);
        assertThat(testProcesso.getValorArea()).isEqualByComparingTo(DEFAULT_VALOR_AREA);
        assertThat(testProcesso.getTamanhoAreaObservacao()).isEqualTo(DEFAULT_TAMANHO_AREA_OBSERVACAO);
        assertThat(testProcesso.getDadosGeograficosLitigioConflito()).isEqualTo(DEFAULT_DADOS_GEOGRAFICOS_LITIGIO_CONFLITO);
        assertThat(testProcesso.getLatitude()).isEqualTo(DEFAULT_LATITUDE);
        assertThat(testProcesso.getLongitude()).isEqualTo(DEFAULT_LONGITUDE);
        assertThat(testProcesso.getNumeroProcessoMPF()).isEqualTo(DEFAULT_NUMERO_PROCESSO_MPF);
        assertThat(testProcesso.getNumeroEmbargo()).isEqualTo(DEFAULT_NUMERO_EMBARGO);
        assertThat(testProcesso.getPautaApelacao()).isEqualTo(DEFAULT_PAUTA_APELACAO);
        assertThat(testProcesso.getNumeroRecursoEspecial()).isEqualTo(DEFAULT_NUMERO_RECURSO_ESPECIAL);
        assertThat(testProcesso.getAdmissiblidade()).isEqualTo(DEFAULT_ADMISSIBLIDADE);
        assertThat(testProcesso.getEnvolveGrandeProjeto()).isEqualTo(DEFAULT_ENVOLVE_GRANDE_PROJETO);
        assertThat(testProcesso.getEnvolveUnidadeConservacao()).isEqualTo(DEFAULT_ENVOLVE_UNIDADE_CONSERVACAO);
        assertThat(testProcesso.getLinkReferencia()).isEqualTo(DEFAULT_LINK_REFERENCIA);
        assertThat(testProcesso.getStatusProcesso()).isEqualTo(DEFAULT_STATUS_PROCESSO);
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
            .andExpect(jsonPath("$.[*].numeroProcesso").value(hasItem(DEFAULT_NUMERO_PROCESSO)))
            .andExpect(jsonPath("$.[*].oficio").value(hasItem(DEFAULT_OFICIO)))
            .andExpect(jsonPath("$.[*].assunto").value(hasItem(DEFAULT_ASSUNTO.toString())))
            .andExpect(jsonPath("$.[*].linkUnico").value(hasItem(DEFAULT_LINK_UNICO)))
            .andExpect(jsonPath("$.[*].linkTrf").value(hasItem(DEFAULT_LINK_TRF)))
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
            .andExpect(jsonPath("$.[*].folhasProcessoConcessaoLiminar").value(hasItem(DEFAULT_FOLHAS_PROCESSO_CONCESSAO_LIMINAR)))
            .andExpect(jsonPath("$.[*].concessaoLiminarObservacoes").value(hasItem(DEFAULT_CONCESSAO_LIMINAR_OBSERVACOES.toString())))
            .andExpect(jsonPath("$.[*].folhasProcessoCassacao").value(hasItem(DEFAULT_FOLHAS_PROCESSO_CASSACAO)))
            .andExpect(jsonPath("$.[*].folhasParecer").value(hasItem(DEFAULT_FOLHAS_PARECER)))
            .andExpect(jsonPath("$.[*].folhasEmbargo").value(hasItem(DEFAULT_FOLHAS_EMBARGO)))
            .andExpect(jsonPath("$.[*].acordaoEmbargo").value(hasItem(DEFAULT_ACORDAO_EMBARGO.toString())))
            .andExpect(jsonPath("$.[*].folhasCienciaJulgEmbargos").value(hasItem(DEFAULT_FOLHAS_CIENCIA_JULG_EMBARGOS)))
            .andExpect(jsonPath("$.[*].apelacao").value(hasItem(DEFAULT_APELACAO)))
            .andExpect(jsonPath("$.[*].folhasApelacao").value(hasItem(DEFAULT_FOLHAS_APELACAO)))
            .andExpect(jsonPath("$.[*].acordaoApelacao").value(hasItem(DEFAULT_ACORDAO_APELACAO.toString())))
            .andExpect(jsonPath("$.[*].folhasCienciaJulgApelacao").value(hasItem(DEFAULT_FOLHAS_CIENCIA_JULG_APELACAO)))
            .andExpect(jsonPath("$.[*].embargoDeclaracao").value(hasItem(DEFAULT_EMBARGO_DECLARACAO.booleanValue())))
            .andExpect(jsonPath("$.[*].embargoRecursoExtraordinario").value(hasItem(DEFAULT_EMBARGO_RECURSO_EXTRAORDINARIO.booleanValue())))
            .andExpect(jsonPath("$.[*].folhasRecursoEspecial").value(hasItem(DEFAULT_FOLHAS_RECURSO_ESPECIAL)))
            .andExpect(jsonPath("$.[*].acordaoRecursoEspecial").value(hasItem(DEFAULT_ACORDAO_RECURSO_ESPECIAL.toString())))
            .andExpect(
                jsonPath("$.[*].folhasCienciaJulgamentoRecursoEspecial").value(hasItem(DEFAULT_FOLHAS_CIENCIA_JULGAMENTO_RECURSO_ESPECIAL))
            )
            .andExpect(jsonPath("$.[*].embargoRecursoEspecial").value(hasItem(DEFAULT_EMBARGO_RECURSO_ESPECIAL.booleanValue())))
            .andExpect(jsonPath("$.[*].folhasCiencia").value(hasItem(DEFAULT_FOLHAS_CIENCIA)))
            .andExpect(jsonPath("$.[*].agravoRespRe").value(hasItem(DEFAULT_AGRAVO_RESP_RE)))
            .andExpect(jsonPath("$.[*].folhasRespRe").value(hasItem(DEFAULT_FOLHAS_RESP_RE)))
            .andExpect(jsonPath("$.[*].acordaoAgravoRespRe").value(hasItem(DEFAULT_ACORDAO_AGRAVO_RESP_RE.toString())))
            .andExpect(
                jsonPath("$.[*].folhasCienciaJulgamentoAgravoRespRe").value(hasItem(DEFAULT_FOLHAS_CIENCIA_JULGAMENTO_AGRAVO_RESP_RE))
            )
            .andExpect(jsonPath("$.[*].embargoRespRe").value(hasItem(DEFAULT_EMBARGO_RESP_RE)))
            .andExpect(jsonPath("$.[*].agravoInterno").value(hasItem(DEFAULT_AGRAVO_INTERNO)))
            .andExpect(jsonPath("$.[*].folhasAgravoInterno").value(hasItem(DEFAULT_FOLHAS_AGRAVO_INTERNO)))
            .andExpect(jsonPath("$.[*].embargoRecursoAgravo").value(hasItem(DEFAULT_EMBARGO_RECURSO_AGRAVO.booleanValue())))
            .andExpect(jsonPath("$.[*].observacoes").value(hasItem(DEFAULT_OBSERVACOES.toString())))
            .andExpect(jsonPath("$.[*].recursoSTJ").value(hasItem(DEFAULT_RECURSO_STJ.booleanValue())))
            .andExpect(jsonPath("$.[*].linkRecursoSTJ").value(hasItem(DEFAULT_LINK_RECURSO_STJ)))
            .andExpect(jsonPath("$.[*].folhasRecursoSTJ").value(hasItem(DEFAULT_FOLHAS_RECURSO_STJ)))
            .andExpect(jsonPath("$.[*].recursoSTF").value(hasItem(DEFAULT_RECURSO_STF.booleanValue())))
            .andExpect(jsonPath("$.[*].linkRecursoSTF").value(hasItem(DEFAULT_LINK_RECURSO_STF)))
            .andExpect(jsonPath("$.[*].folhasRecursoSTF").value(hasItem(DEFAULT_FOLHAS_RECURSO_STF)))
            .andExpect(jsonPath("$.[*].folhasMemorialMPF").value(hasItem(DEFAULT_FOLHAS_MEMORIAL_MPF)))
            .andExpect(jsonPath("$.[*].execusaoProvisoria").value(hasItem(DEFAULT_EXECUSAO_PROVISORIA.booleanValue())))
            .andExpect(jsonPath("$.[*].numeracaoExecusaoProvisoria").value(hasItem(DEFAULT_NUMERACAO_EXECUSAO_PROVISORIA)))
            .andExpect(
                jsonPath("$.[*].recuperacaoEfetivaCumprimentoSentenca")
                    .value(hasItem(DEFAULT_RECUPERACAO_EFETIVA_CUMPRIMENTO_SENTENCA.toString()))
            )
            .andExpect(
                jsonPath("$.[*].recuperacaoEfetivaCumprimentoSentencaObservacoes")
                    .value(hasItem(DEFAULT_RECUPERACAO_EFETIVA_CUMPRIMENTO_SENTENCA_OBSERVACOES.toString()))
            )
            .andExpect(jsonPath("$.[*].envolveEmpreendimento").value(hasItem(DEFAULT_ENVOLVE_EMPREENDIMENTO.booleanValue())))
            .andExpect(jsonPath("$.[*].envolveExploracaoIlegal").value(hasItem(DEFAULT_ENVOLVE_EXPLORACAO_ILEGAL.booleanValue())))
            .andExpect(jsonPath("$.[*].envolveTerraQuilombola").value(hasItem(DEFAULT_ENVOLVE_TERRA_QUILOMBOLA.booleanValue())))
            .andExpect(
                jsonPath("$.[*].envolveTerraComunidadeTradicional")
                    .value(hasItem(DEFAULT_ENVOLVE_TERRA_COMUNIDADE_TRADICIONAL.booleanValue()))
            )
            .andExpect(jsonPath("$.[*].envolveTerraIndigena").value(hasItem(DEFAULT_ENVOLVE_TERRA_INDIGENA.booleanValue())))
            .andExpect(jsonPath("$.[*].resumoFatos").value(hasItem(DEFAULT_RESUMO_FATOS.toString())))
            .andExpect(jsonPath("$.[*].tamanhoArea").value(hasItem(sameNumber(DEFAULT_TAMANHO_AREA))))
            .andExpect(jsonPath("$.[*].valorArea").value(hasItem(sameNumber(DEFAULT_VALOR_AREA))))
            .andExpect(jsonPath("$.[*].tamanhoAreaObservacao").value(hasItem(DEFAULT_TAMANHO_AREA_OBSERVACAO.toString())))
            .andExpect(
                jsonPath("$.[*].dadosGeograficosLitigioConflito").value(hasItem(DEFAULT_DADOS_GEOGRAFICOS_LITIGIO_CONFLITO.booleanValue()))
            )
            .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE)))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE)))
            .andExpect(jsonPath("$.[*].numeroProcessoMPF").value(hasItem(DEFAULT_NUMERO_PROCESSO_MPF)))
            .andExpect(jsonPath("$.[*].numeroEmbargo").value(hasItem(DEFAULT_NUMERO_EMBARGO)))
            .andExpect(jsonPath("$.[*].pautaApelacao").value(hasItem(DEFAULT_PAUTA_APELACAO.toString())))
            .andExpect(jsonPath("$.[*].numeroRecursoEspecial").value(hasItem(DEFAULT_NUMERO_RECURSO_ESPECIAL)))
            .andExpect(jsonPath("$.[*].admissiblidade").value(hasItem(DEFAULT_ADMISSIBLIDADE.toString())))
            .andExpect(jsonPath("$.[*].envolveGrandeProjeto").value(hasItem(DEFAULT_ENVOLVE_GRANDE_PROJETO.booleanValue())))
            .andExpect(jsonPath("$.[*].envolveUnidadeConservacao").value(hasItem(DEFAULT_ENVOLVE_UNIDADE_CONSERVACAO.booleanValue())))
            .andExpect(jsonPath("$.[*].linkReferencia").value(hasItem(DEFAULT_LINK_REFERENCIA.toString())))
            .andExpect(jsonPath("$.[*].statusProcesso").value(hasItem(DEFAULT_STATUS_PROCESSO.toString())));
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
            .andExpect(jsonPath("$.numeroProcesso").value(DEFAULT_NUMERO_PROCESSO))
            .andExpect(jsonPath("$.oficio").value(DEFAULT_OFICIO))
            .andExpect(jsonPath("$.assunto").value(DEFAULT_ASSUNTO.toString()))
            .andExpect(jsonPath("$.linkUnico").value(DEFAULT_LINK_UNICO))
            .andExpect(jsonPath("$.linkTrf").value(DEFAULT_LINK_TRF))
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
            .andExpect(jsonPath("$.folhasProcessoConcessaoLiminar").value(DEFAULT_FOLHAS_PROCESSO_CONCESSAO_LIMINAR))
            .andExpect(jsonPath("$.concessaoLiminarObservacoes").value(DEFAULT_CONCESSAO_LIMINAR_OBSERVACOES.toString()))
            .andExpect(jsonPath("$.folhasProcessoCassacao").value(DEFAULT_FOLHAS_PROCESSO_CASSACAO))
            .andExpect(jsonPath("$.folhasParecer").value(DEFAULT_FOLHAS_PARECER))
            .andExpect(jsonPath("$.folhasEmbargo").value(DEFAULT_FOLHAS_EMBARGO))
            .andExpect(jsonPath("$.acordaoEmbargo").value(DEFAULT_ACORDAO_EMBARGO.toString()))
            .andExpect(jsonPath("$.folhasCienciaJulgEmbargos").value(DEFAULT_FOLHAS_CIENCIA_JULG_EMBARGOS))
            .andExpect(jsonPath("$.apelacao").value(DEFAULT_APELACAO))
            .andExpect(jsonPath("$.folhasApelacao").value(DEFAULT_FOLHAS_APELACAO))
            .andExpect(jsonPath("$.acordaoApelacao").value(DEFAULT_ACORDAO_APELACAO.toString()))
            .andExpect(jsonPath("$.folhasCienciaJulgApelacao").value(DEFAULT_FOLHAS_CIENCIA_JULG_APELACAO))
            .andExpect(jsonPath("$.embargoDeclaracao").value(DEFAULT_EMBARGO_DECLARACAO.booleanValue()))
            .andExpect(jsonPath("$.embargoRecursoExtraordinario").value(DEFAULT_EMBARGO_RECURSO_EXTRAORDINARIO.booleanValue()))
            .andExpect(jsonPath("$.folhasRecursoEspecial").value(DEFAULT_FOLHAS_RECURSO_ESPECIAL))
            .andExpect(jsonPath("$.acordaoRecursoEspecial").value(DEFAULT_ACORDAO_RECURSO_ESPECIAL.toString()))
            .andExpect(jsonPath("$.folhasCienciaJulgamentoRecursoEspecial").value(DEFAULT_FOLHAS_CIENCIA_JULGAMENTO_RECURSO_ESPECIAL))
            .andExpect(jsonPath("$.embargoRecursoEspecial").value(DEFAULT_EMBARGO_RECURSO_ESPECIAL.booleanValue()))
            .andExpect(jsonPath("$.folhasCiencia").value(DEFAULT_FOLHAS_CIENCIA))
            .andExpect(jsonPath("$.agravoRespRe").value(DEFAULT_AGRAVO_RESP_RE))
            .andExpect(jsonPath("$.folhasRespRe").value(DEFAULT_FOLHAS_RESP_RE))
            .andExpect(jsonPath("$.acordaoAgravoRespRe").value(DEFAULT_ACORDAO_AGRAVO_RESP_RE.toString()))
            .andExpect(jsonPath("$.folhasCienciaJulgamentoAgravoRespRe").value(DEFAULT_FOLHAS_CIENCIA_JULGAMENTO_AGRAVO_RESP_RE))
            .andExpect(jsonPath("$.embargoRespRe").value(DEFAULT_EMBARGO_RESP_RE))
            .andExpect(jsonPath("$.agravoInterno").value(DEFAULT_AGRAVO_INTERNO))
            .andExpect(jsonPath("$.folhasAgravoInterno").value(DEFAULT_FOLHAS_AGRAVO_INTERNO))
            .andExpect(jsonPath("$.embargoRecursoAgravo").value(DEFAULT_EMBARGO_RECURSO_AGRAVO.booleanValue()))
            .andExpect(jsonPath("$.observacoes").value(DEFAULT_OBSERVACOES.toString()))
            .andExpect(jsonPath("$.recursoSTJ").value(DEFAULT_RECURSO_STJ.booleanValue()))
            .andExpect(jsonPath("$.linkRecursoSTJ").value(DEFAULT_LINK_RECURSO_STJ))
            .andExpect(jsonPath("$.folhasRecursoSTJ").value(DEFAULT_FOLHAS_RECURSO_STJ))
            .andExpect(jsonPath("$.recursoSTF").value(DEFAULT_RECURSO_STF.booleanValue()))
            .andExpect(jsonPath("$.linkRecursoSTF").value(DEFAULT_LINK_RECURSO_STF))
            .andExpect(jsonPath("$.folhasRecursoSTF").value(DEFAULT_FOLHAS_RECURSO_STF))
            .andExpect(jsonPath("$.folhasMemorialMPF").value(DEFAULT_FOLHAS_MEMORIAL_MPF))
            .andExpect(jsonPath("$.execusaoProvisoria").value(DEFAULT_EXECUSAO_PROVISORIA.booleanValue()))
            .andExpect(jsonPath("$.numeracaoExecusaoProvisoria").value(DEFAULT_NUMERACAO_EXECUSAO_PROVISORIA))
            .andExpect(
                jsonPath("$.recuperacaoEfetivaCumprimentoSentenca").value(DEFAULT_RECUPERACAO_EFETIVA_CUMPRIMENTO_SENTENCA.toString())
            )
            .andExpect(
                jsonPath("$.recuperacaoEfetivaCumprimentoSentencaObservacoes")
                    .value(DEFAULT_RECUPERACAO_EFETIVA_CUMPRIMENTO_SENTENCA_OBSERVACOES.toString())
            )
            .andExpect(jsonPath("$.envolveEmpreendimento").value(DEFAULT_ENVOLVE_EMPREENDIMENTO.booleanValue()))
            .andExpect(jsonPath("$.envolveExploracaoIlegal").value(DEFAULT_ENVOLVE_EXPLORACAO_ILEGAL.booleanValue()))
            .andExpect(jsonPath("$.envolveTerraQuilombola").value(DEFAULT_ENVOLVE_TERRA_QUILOMBOLA.booleanValue()))
            .andExpect(jsonPath("$.envolveTerraComunidadeTradicional").value(DEFAULT_ENVOLVE_TERRA_COMUNIDADE_TRADICIONAL.booleanValue()))
            .andExpect(jsonPath("$.envolveTerraIndigena").value(DEFAULT_ENVOLVE_TERRA_INDIGENA.booleanValue()))
            .andExpect(jsonPath("$.resumoFatos").value(DEFAULT_RESUMO_FATOS.toString()))
            .andExpect(jsonPath("$.tamanhoArea").value(sameNumber(DEFAULT_TAMANHO_AREA)))
            .andExpect(jsonPath("$.valorArea").value(sameNumber(DEFAULT_VALOR_AREA)))
            .andExpect(jsonPath("$.tamanhoAreaObservacao").value(DEFAULT_TAMANHO_AREA_OBSERVACAO.toString()))
            .andExpect(jsonPath("$.dadosGeograficosLitigioConflito").value(DEFAULT_DADOS_GEOGRAFICOS_LITIGIO_CONFLITO.booleanValue()))
            .andExpect(jsonPath("$.latitude").value(DEFAULT_LATITUDE))
            .andExpect(jsonPath("$.longitude").value(DEFAULT_LONGITUDE))
            .andExpect(jsonPath("$.numeroProcessoMPF").value(DEFAULT_NUMERO_PROCESSO_MPF))
            .andExpect(jsonPath("$.numeroEmbargo").value(DEFAULT_NUMERO_EMBARGO))
            .andExpect(jsonPath("$.pautaApelacao").value(DEFAULT_PAUTA_APELACAO.toString()))
            .andExpect(jsonPath("$.numeroRecursoEspecial").value(DEFAULT_NUMERO_RECURSO_ESPECIAL))
            .andExpect(jsonPath("$.admissiblidade").value(DEFAULT_ADMISSIBLIDADE.toString()))
            .andExpect(jsonPath("$.envolveGrandeProjeto").value(DEFAULT_ENVOLVE_GRANDE_PROJETO.booleanValue()))
            .andExpect(jsonPath("$.envolveUnidadeConservacao").value(DEFAULT_ENVOLVE_UNIDADE_CONSERVACAO.booleanValue()))
            .andExpect(jsonPath("$.linkReferencia").value(DEFAULT_LINK_REFERENCIA.toString()))
            .andExpect(jsonPath("$.statusProcesso").value(DEFAULT_STATUS_PROCESSO.toString()));
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
    void getAllProcessosByNumeroProcessoIsEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where numeroProcesso equals to DEFAULT_NUMERO_PROCESSO
        defaultProcessoShouldBeFound("numeroProcesso.equals=" + DEFAULT_NUMERO_PROCESSO);

        // Get all the processoList where numeroProcesso equals to UPDATED_NUMERO_PROCESSO
        defaultProcessoShouldNotBeFound("numeroProcesso.equals=" + UPDATED_NUMERO_PROCESSO);
    }

    @Test
    @Transactional
    void getAllProcessosByNumeroProcessoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where numeroProcesso not equals to DEFAULT_NUMERO_PROCESSO
        defaultProcessoShouldNotBeFound("numeroProcesso.notEquals=" + DEFAULT_NUMERO_PROCESSO);

        // Get all the processoList where numeroProcesso not equals to UPDATED_NUMERO_PROCESSO
        defaultProcessoShouldBeFound("numeroProcesso.notEquals=" + UPDATED_NUMERO_PROCESSO);
    }

    @Test
    @Transactional
    void getAllProcessosByNumeroProcessoIsInShouldWork() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where numeroProcesso in DEFAULT_NUMERO_PROCESSO or UPDATED_NUMERO_PROCESSO
        defaultProcessoShouldBeFound("numeroProcesso.in=" + DEFAULT_NUMERO_PROCESSO + "," + UPDATED_NUMERO_PROCESSO);

        // Get all the processoList where numeroProcesso equals to UPDATED_NUMERO_PROCESSO
        defaultProcessoShouldNotBeFound("numeroProcesso.in=" + UPDATED_NUMERO_PROCESSO);
    }

    @Test
    @Transactional
    void getAllProcessosByNumeroProcessoIsNullOrNotNull() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where numeroProcesso is not null
        defaultProcessoShouldBeFound("numeroProcesso.specified=true");

        // Get all the processoList where numeroProcesso is null
        defaultProcessoShouldNotBeFound("numeroProcesso.specified=false");
    }

    @Test
    @Transactional
    void getAllProcessosByNumeroProcessoContainsSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where numeroProcesso contains DEFAULT_NUMERO_PROCESSO
        defaultProcessoShouldBeFound("numeroProcesso.contains=" + DEFAULT_NUMERO_PROCESSO);

        // Get all the processoList where numeroProcesso contains UPDATED_NUMERO_PROCESSO
        defaultProcessoShouldNotBeFound("numeroProcesso.contains=" + UPDATED_NUMERO_PROCESSO);
    }

    @Test
    @Transactional
    void getAllProcessosByNumeroProcessoNotContainsSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where numeroProcesso does not contain DEFAULT_NUMERO_PROCESSO
        defaultProcessoShouldNotBeFound("numeroProcesso.doesNotContain=" + DEFAULT_NUMERO_PROCESSO);

        // Get all the processoList where numeroProcesso does not contain UPDATED_NUMERO_PROCESSO
        defaultProcessoShouldBeFound("numeroProcesso.doesNotContain=" + UPDATED_NUMERO_PROCESSO);
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
    void getAllProcessosByFolhasProcessoConcessaoLiminarIsEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where folhasProcessoConcessaoLiminar equals to DEFAULT_FOLHAS_PROCESSO_CONCESSAO_LIMINAR
        defaultProcessoShouldBeFound("folhasProcessoConcessaoLiminar.equals=" + DEFAULT_FOLHAS_PROCESSO_CONCESSAO_LIMINAR);

        // Get all the processoList where folhasProcessoConcessaoLiminar equals to UPDATED_FOLHAS_PROCESSO_CONCESSAO_LIMINAR
        defaultProcessoShouldNotBeFound("folhasProcessoConcessaoLiminar.equals=" + UPDATED_FOLHAS_PROCESSO_CONCESSAO_LIMINAR);
    }

    @Test
    @Transactional
    void getAllProcessosByFolhasProcessoConcessaoLiminarIsNotEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where folhasProcessoConcessaoLiminar not equals to DEFAULT_FOLHAS_PROCESSO_CONCESSAO_LIMINAR
        defaultProcessoShouldNotBeFound("folhasProcessoConcessaoLiminar.notEquals=" + DEFAULT_FOLHAS_PROCESSO_CONCESSAO_LIMINAR);

        // Get all the processoList where folhasProcessoConcessaoLiminar not equals to UPDATED_FOLHAS_PROCESSO_CONCESSAO_LIMINAR
        defaultProcessoShouldBeFound("folhasProcessoConcessaoLiminar.notEquals=" + UPDATED_FOLHAS_PROCESSO_CONCESSAO_LIMINAR);
    }

    @Test
    @Transactional
    void getAllProcessosByFolhasProcessoConcessaoLiminarIsInShouldWork() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where folhasProcessoConcessaoLiminar in DEFAULT_FOLHAS_PROCESSO_CONCESSAO_LIMINAR or UPDATED_FOLHAS_PROCESSO_CONCESSAO_LIMINAR
        defaultProcessoShouldBeFound(
            "folhasProcessoConcessaoLiminar.in=" +
            DEFAULT_FOLHAS_PROCESSO_CONCESSAO_LIMINAR +
            "," +
            UPDATED_FOLHAS_PROCESSO_CONCESSAO_LIMINAR
        );

        // Get all the processoList where folhasProcessoConcessaoLiminar equals to UPDATED_FOLHAS_PROCESSO_CONCESSAO_LIMINAR
        defaultProcessoShouldNotBeFound("folhasProcessoConcessaoLiminar.in=" + UPDATED_FOLHAS_PROCESSO_CONCESSAO_LIMINAR);
    }

    @Test
    @Transactional
    void getAllProcessosByFolhasProcessoConcessaoLiminarIsNullOrNotNull() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where folhasProcessoConcessaoLiminar is not null
        defaultProcessoShouldBeFound("folhasProcessoConcessaoLiminar.specified=true");

        // Get all the processoList where folhasProcessoConcessaoLiminar is null
        defaultProcessoShouldNotBeFound("folhasProcessoConcessaoLiminar.specified=false");
    }

    @Test
    @Transactional
    void getAllProcessosByFolhasProcessoConcessaoLiminarContainsSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where folhasProcessoConcessaoLiminar contains DEFAULT_FOLHAS_PROCESSO_CONCESSAO_LIMINAR
        defaultProcessoShouldBeFound("folhasProcessoConcessaoLiminar.contains=" + DEFAULT_FOLHAS_PROCESSO_CONCESSAO_LIMINAR);

        // Get all the processoList where folhasProcessoConcessaoLiminar contains UPDATED_FOLHAS_PROCESSO_CONCESSAO_LIMINAR
        defaultProcessoShouldNotBeFound("folhasProcessoConcessaoLiminar.contains=" + UPDATED_FOLHAS_PROCESSO_CONCESSAO_LIMINAR);
    }

    @Test
    @Transactional
    void getAllProcessosByFolhasProcessoConcessaoLiminarNotContainsSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where folhasProcessoConcessaoLiminar does not contain DEFAULT_FOLHAS_PROCESSO_CONCESSAO_LIMINAR
        defaultProcessoShouldNotBeFound("folhasProcessoConcessaoLiminar.doesNotContain=" + DEFAULT_FOLHAS_PROCESSO_CONCESSAO_LIMINAR);

        // Get all the processoList where folhasProcessoConcessaoLiminar does not contain UPDATED_FOLHAS_PROCESSO_CONCESSAO_LIMINAR
        defaultProcessoShouldBeFound("folhasProcessoConcessaoLiminar.doesNotContain=" + UPDATED_FOLHAS_PROCESSO_CONCESSAO_LIMINAR);
    }

    @Test
    @Transactional
    void getAllProcessosByFolhasProcessoCassacaoIsEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where folhasProcessoCassacao equals to DEFAULT_FOLHAS_PROCESSO_CASSACAO
        defaultProcessoShouldBeFound("folhasProcessoCassacao.equals=" + DEFAULT_FOLHAS_PROCESSO_CASSACAO);

        // Get all the processoList where folhasProcessoCassacao equals to UPDATED_FOLHAS_PROCESSO_CASSACAO
        defaultProcessoShouldNotBeFound("folhasProcessoCassacao.equals=" + UPDATED_FOLHAS_PROCESSO_CASSACAO);
    }

    @Test
    @Transactional
    void getAllProcessosByFolhasProcessoCassacaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where folhasProcessoCassacao not equals to DEFAULT_FOLHAS_PROCESSO_CASSACAO
        defaultProcessoShouldNotBeFound("folhasProcessoCassacao.notEquals=" + DEFAULT_FOLHAS_PROCESSO_CASSACAO);

        // Get all the processoList where folhasProcessoCassacao not equals to UPDATED_FOLHAS_PROCESSO_CASSACAO
        defaultProcessoShouldBeFound("folhasProcessoCassacao.notEquals=" + UPDATED_FOLHAS_PROCESSO_CASSACAO);
    }

    @Test
    @Transactional
    void getAllProcessosByFolhasProcessoCassacaoIsInShouldWork() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where folhasProcessoCassacao in DEFAULT_FOLHAS_PROCESSO_CASSACAO or UPDATED_FOLHAS_PROCESSO_CASSACAO
        defaultProcessoShouldBeFound(
            "folhasProcessoCassacao.in=" + DEFAULT_FOLHAS_PROCESSO_CASSACAO + "," + UPDATED_FOLHAS_PROCESSO_CASSACAO
        );

        // Get all the processoList where folhasProcessoCassacao equals to UPDATED_FOLHAS_PROCESSO_CASSACAO
        defaultProcessoShouldNotBeFound("folhasProcessoCassacao.in=" + UPDATED_FOLHAS_PROCESSO_CASSACAO);
    }

    @Test
    @Transactional
    void getAllProcessosByFolhasProcessoCassacaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where folhasProcessoCassacao is not null
        defaultProcessoShouldBeFound("folhasProcessoCassacao.specified=true");

        // Get all the processoList where folhasProcessoCassacao is null
        defaultProcessoShouldNotBeFound("folhasProcessoCassacao.specified=false");
    }

    @Test
    @Transactional
    void getAllProcessosByFolhasProcessoCassacaoContainsSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where folhasProcessoCassacao contains DEFAULT_FOLHAS_PROCESSO_CASSACAO
        defaultProcessoShouldBeFound("folhasProcessoCassacao.contains=" + DEFAULT_FOLHAS_PROCESSO_CASSACAO);

        // Get all the processoList where folhasProcessoCassacao contains UPDATED_FOLHAS_PROCESSO_CASSACAO
        defaultProcessoShouldNotBeFound("folhasProcessoCassacao.contains=" + UPDATED_FOLHAS_PROCESSO_CASSACAO);
    }

    @Test
    @Transactional
    void getAllProcessosByFolhasProcessoCassacaoNotContainsSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where folhasProcessoCassacao does not contain DEFAULT_FOLHAS_PROCESSO_CASSACAO
        defaultProcessoShouldNotBeFound("folhasProcessoCassacao.doesNotContain=" + DEFAULT_FOLHAS_PROCESSO_CASSACAO);

        // Get all the processoList where folhasProcessoCassacao does not contain UPDATED_FOLHAS_PROCESSO_CASSACAO
        defaultProcessoShouldBeFound("folhasProcessoCassacao.doesNotContain=" + UPDATED_FOLHAS_PROCESSO_CASSACAO);
    }

    @Test
    @Transactional
    void getAllProcessosByFolhasParecerIsEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where folhasParecer equals to DEFAULT_FOLHAS_PARECER
        defaultProcessoShouldBeFound("folhasParecer.equals=" + DEFAULT_FOLHAS_PARECER);

        // Get all the processoList where folhasParecer equals to UPDATED_FOLHAS_PARECER
        defaultProcessoShouldNotBeFound("folhasParecer.equals=" + UPDATED_FOLHAS_PARECER);
    }

    @Test
    @Transactional
    void getAllProcessosByFolhasParecerIsNotEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where folhasParecer not equals to DEFAULT_FOLHAS_PARECER
        defaultProcessoShouldNotBeFound("folhasParecer.notEquals=" + DEFAULT_FOLHAS_PARECER);

        // Get all the processoList where folhasParecer not equals to UPDATED_FOLHAS_PARECER
        defaultProcessoShouldBeFound("folhasParecer.notEquals=" + UPDATED_FOLHAS_PARECER);
    }

    @Test
    @Transactional
    void getAllProcessosByFolhasParecerIsInShouldWork() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where folhasParecer in DEFAULT_FOLHAS_PARECER or UPDATED_FOLHAS_PARECER
        defaultProcessoShouldBeFound("folhasParecer.in=" + DEFAULT_FOLHAS_PARECER + "," + UPDATED_FOLHAS_PARECER);

        // Get all the processoList where folhasParecer equals to UPDATED_FOLHAS_PARECER
        defaultProcessoShouldNotBeFound("folhasParecer.in=" + UPDATED_FOLHAS_PARECER);
    }

    @Test
    @Transactional
    void getAllProcessosByFolhasParecerIsNullOrNotNull() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where folhasParecer is not null
        defaultProcessoShouldBeFound("folhasParecer.specified=true");

        // Get all the processoList where folhasParecer is null
        defaultProcessoShouldNotBeFound("folhasParecer.specified=false");
    }

    @Test
    @Transactional
    void getAllProcessosByFolhasParecerContainsSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where folhasParecer contains DEFAULT_FOLHAS_PARECER
        defaultProcessoShouldBeFound("folhasParecer.contains=" + DEFAULT_FOLHAS_PARECER);

        // Get all the processoList where folhasParecer contains UPDATED_FOLHAS_PARECER
        defaultProcessoShouldNotBeFound("folhasParecer.contains=" + UPDATED_FOLHAS_PARECER);
    }

    @Test
    @Transactional
    void getAllProcessosByFolhasParecerNotContainsSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where folhasParecer does not contain DEFAULT_FOLHAS_PARECER
        defaultProcessoShouldNotBeFound("folhasParecer.doesNotContain=" + DEFAULT_FOLHAS_PARECER);

        // Get all the processoList where folhasParecer does not contain UPDATED_FOLHAS_PARECER
        defaultProcessoShouldBeFound("folhasParecer.doesNotContain=" + UPDATED_FOLHAS_PARECER);
    }

    @Test
    @Transactional
    void getAllProcessosByFolhasEmbargoIsEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where folhasEmbargo equals to DEFAULT_FOLHAS_EMBARGO
        defaultProcessoShouldBeFound("folhasEmbargo.equals=" + DEFAULT_FOLHAS_EMBARGO);

        // Get all the processoList where folhasEmbargo equals to UPDATED_FOLHAS_EMBARGO
        defaultProcessoShouldNotBeFound("folhasEmbargo.equals=" + UPDATED_FOLHAS_EMBARGO);
    }

    @Test
    @Transactional
    void getAllProcessosByFolhasEmbargoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where folhasEmbargo not equals to DEFAULT_FOLHAS_EMBARGO
        defaultProcessoShouldNotBeFound("folhasEmbargo.notEquals=" + DEFAULT_FOLHAS_EMBARGO);

        // Get all the processoList where folhasEmbargo not equals to UPDATED_FOLHAS_EMBARGO
        defaultProcessoShouldBeFound("folhasEmbargo.notEquals=" + UPDATED_FOLHAS_EMBARGO);
    }

    @Test
    @Transactional
    void getAllProcessosByFolhasEmbargoIsInShouldWork() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where folhasEmbargo in DEFAULT_FOLHAS_EMBARGO or UPDATED_FOLHAS_EMBARGO
        defaultProcessoShouldBeFound("folhasEmbargo.in=" + DEFAULT_FOLHAS_EMBARGO + "," + UPDATED_FOLHAS_EMBARGO);

        // Get all the processoList where folhasEmbargo equals to UPDATED_FOLHAS_EMBARGO
        defaultProcessoShouldNotBeFound("folhasEmbargo.in=" + UPDATED_FOLHAS_EMBARGO);
    }

    @Test
    @Transactional
    void getAllProcessosByFolhasEmbargoIsNullOrNotNull() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where folhasEmbargo is not null
        defaultProcessoShouldBeFound("folhasEmbargo.specified=true");

        // Get all the processoList where folhasEmbargo is null
        defaultProcessoShouldNotBeFound("folhasEmbargo.specified=false");
    }

    @Test
    @Transactional
    void getAllProcessosByFolhasEmbargoContainsSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where folhasEmbargo contains DEFAULT_FOLHAS_EMBARGO
        defaultProcessoShouldBeFound("folhasEmbargo.contains=" + DEFAULT_FOLHAS_EMBARGO);

        // Get all the processoList where folhasEmbargo contains UPDATED_FOLHAS_EMBARGO
        defaultProcessoShouldNotBeFound("folhasEmbargo.contains=" + UPDATED_FOLHAS_EMBARGO);
    }

    @Test
    @Transactional
    void getAllProcessosByFolhasEmbargoNotContainsSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where folhasEmbargo does not contain DEFAULT_FOLHAS_EMBARGO
        defaultProcessoShouldNotBeFound("folhasEmbargo.doesNotContain=" + DEFAULT_FOLHAS_EMBARGO);

        // Get all the processoList where folhasEmbargo does not contain UPDATED_FOLHAS_EMBARGO
        defaultProcessoShouldBeFound("folhasEmbargo.doesNotContain=" + UPDATED_FOLHAS_EMBARGO);
    }

    @Test
    @Transactional
    void getAllProcessosByFolhasCienciaJulgEmbargosIsEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where folhasCienciaJulgEmbargos equals to DEFAULT_FOLHAS_CIENCIA_JULG_EMBARGOS
        defaultProcessoShouldBeFound("folhasCienciaJulgEmbargos.equals=" + DEFAULT_FOLHAS_CIENCIA_JULG_EMBARGOS);

        // Get all the processoList where folhasCienciaJulgEmbargos equals to UPDATED_FOLHAS_CIENCIA_JULG_EMBARGOS
        defaultProcessoShouldNotBeFound("folhasCienciaJulgEmbargos.equals=" + UPDATED_FOLHAS_CIENCIA_JULG_EMBARGOS);
    }

    @Test
    @Transactional
    void getAllProcessosByFolhasCienciaJulgEmbargosIsNotEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where folhasCienciaJulgEmbargos not equals to DEFAULT_FOLHAS_CIENCIA_JULG_EMBARGOS
        defaultProcessoShouldNotBeFound("folhasCienciaJulgEmbargos.notEquals=" + DEFAULT_FOLHAS_CIENCIA_JULG_EMBARGOS);

        // Get all the processoList where folhasCienciaJulgEmbargos not equals to UPDATED_FOLHAS_CIENCIA_JULG_EMBARGOS
        defaultProcessoShouldBeFound("folhasCienciaJulgEmbargos.notEquals=" + UPDATED_FOLHAS_CIENCIA_JULG_EMBARGOS);
    }

    @Test
    @Transactional
    void getAllProcessosByFolhasCienciaJulgEmbargosIsInShouldWork() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where folhasCienciaJulgEmbargos in DEFAULT_FOLHAS_CIENCIA_JULG_EMBARGOS or UPDATED_FOLHAS_CIENCIA_JULG_EMBARGOS
        defaultProcessoShouldBeFound(
            "folhasCienciaJulgEmbargos.in=" + DEFAULT_FOLHAS_CIENCIA_JULG_EMBARGOS + "," + UPDATED_FOLHAS_CIENCIA_JULG_EMBARGOS
        );

        // Get all the processoList where folhasCienciaJulgEmbargos equals to UPDATED_FOLHAS_CIENCIA_JULG_EMBARGOS
        defaultProcessoShouldNotBeFound("folhasCienciaJulgEmbargos.in=" + UPDATED_FOLHAS_CIENCIA_JULG_EMBARGOS);
    }

    @Test
    @Transactional
    void getAllProcessosByFolhasCienciaJulgEmbargosIsNullOrNotNull() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where folhasCienciaJulgEmbargos is not null
        defaultProcessoShouldBeFound("folhasCienciaJulgEmbargos.specified=true");

        // Get all the processoList where folhasCienciaJulgEmbargos is null
        defaultProcessoShouldNotBeFound("folhasCienciaJulgEmbargos.specified=false");
    }

    @Test
    @Transactional
    void getAllProcessosByFolhasCienciaJulgEmbargosContainsSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where folhasCienciaJulgEmbargos contains DEFAULT_FOLHAS_CIENCIA_JULG_EMBARGOS
        defaultProcessoShouldBeFound("folhasCienciaJulgEmbargos.contains=" + DEFAULT_FOLHAS_CIENCIA_JULG_EMBARGOS);

        // Get all the processoList where folhasCienciaJulgEmbargos contains UPDATED_FOLHAS_CIENCIA_JULG_EMBARGOS
        defaultProcessoShouldNotBeFound("folhasCienciaJulgEmbargos.contains=" + UPDATED_FOLHAS_CIENCIA_JULG_EMBARGOS);
    }

    @Test
    @Transactional
    void getAllProcessosByFolhasCienciaJulgEmbargosNotContainsSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where folhasCienciaJulgEmbargos does not contain DEFAULT_FOLHAS_CIENCIA_JULG_EMBARGOS
        defaultProcessoShouldNotBeFound("folhasCienciaJulgEmbargos.doesNotContain=" + DEFAULT_FOLHAS_CIENCIA_JULG_EMBARGOS);

        // Get all the processoList where folhasCienciaJulgEmbargos does not contain UPDATED_FOLHAS_CIENCIA_JULG_EMBARGOS
        defaultProcessoShouldBeFound("folhasCienciaJulgEmbargos.doesNotContain=" + UPDATED_FOLHAS_CIENCIA_JULG_EMBARGOS);
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
    void getAllProcessosByFolhasApelacaoIsEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where folhasApelacao equals to DEFAULT_FOLHAS_APELACAO
        defaultProcessoShouldBeFound("folhasApelacao.equals=" + DEFAULT_FOLHAS_APELACAO);

        // Get all the processoList where folhasApelacao equals to UPDATED_FOLHAS_APELACAO
        defaultProcessoShouldNotBeFound("folhasApelacao.equals=" + UPDATED_FOLHAS_APELACAO);
    }

    @Test
    @Transactional
    void getAllProcessosByFolhasApelacaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where folhasApelacao not equals to DEFAULT_FOLHAS_APELACAO
        defaultProcessoShouldNotBeFound("folhasApelacao.notEquals=" + DEFAULT_FOLHAS_APELACAO);

        // Get all the processoList where folhasApelacao not equals to UPDATED_FOLHAS_APELACAO
        defaultProcessoShouldBeFound("folhasApelacao.notEquals=" + UPDATED_FOLHAS_APELACAO);
    }

    @Test
    @Transactional
    void getAllProcessosByFolhasApelacaoIsInShouldWork() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where folhasApelacao in DEFAULT_FOLHAS_APELACAO or UPDATED_FOLHAS_APELACAO
        defaultProcessoShouldBeFound("folhasApelacao.in=" + DEFAULT_FOLHAS_APELACAO + "," + UPDATED_FOLHAS_APELACAO);

        // Get all the processoList where folhasApelacao equals to UPDATED_FOLHAS_APELACAO
        defaultProcessoShouldNotBeFound("folhasApelacao.in=" + UPDATED_FOLHAS_APELACAO);
    }

    @Test
    @Transactional
    void getAllProcessosByFolhasApelacaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where folhasApelacao is not null
        defaultProcessoShouldBeFound("folhasApelacao.specified=true");

        // Get all the processoList where folhasApelacao is null
        defaultProcessoShouldNotBeFound("folhasApelacao.specified=false");
    }

    @Test
    @Transactional
    void getAllProcessosByFolhasApelacaoContainsSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where folhasApelacao contains DEFAULT_FOLHAS_APELACAO
        defaultProcessoShouldBeFound("folhasApelacao.contains=" + DEFAULT_FOLHAS_APELACAO);

        // Get all the processoList where folhasApelacao contains UPDATED_FOLHAS_APELACAO
        defaultProcessoShouldNotBeFound("folhasApelacao.contains=" + UPDATED_FOLHAS_APELACAO);
    }

    @Test
    @Transactional
    void getAllProcessosByFolhasApelacaoNotContainsSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where folhasApelacao does not contain DEFAULT_FOLHAS_APELACAO
        defaultProcessoShouldNotBeFound("folhasApelacao.doesNotContain=" + DEFAULT_FOLHAS_APELACAO);

        // Get all the processoList where folhasApelacao does not contain UPDATED_FOLHAS_APELACAO
        defaultProcessoShouldBeFound("folhasApelacao.doesNotContain=" + UPDATED_FOLHAS_APELACAO);
    }

    @Test
    @Transactional
    void getAllProcessosByFolhasCienciaJulgApelacaoIsEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where folhasCienciaJulgApelacao equals to DEFAULT_FOLHAS_CIENCIA_JULG_APELACAO
        defaultProcessoShouldBeFound("folhasCienciaJulgApelacao.equals=" + DEFAULT_FOLHAS_CIENCIA_JULG_APELACAO);

        // Get all the processoList where folhasCienciaJulgApelacao equals to UPDATED_FOLHAS_CIENCIA_JULG_APELACAO
        defaultProcessoShouldNotBeFound("folhasCienciaJulgApelacao.equals=" + UPDATED_FOLHAS_CIENCIA_JULG_APELACAO);
    }

    @Test
    @Transactional
    void getAllProcessosByFolhasCienciaJulgApelacaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where folhasCienciaJulgApelacao not equals to DEFAULT_FOLHAS_CIENCIA_JULG_APELACAO
        defaultProcessoShouldNotBeFound("folhasCienciaJulgApelacao.notEquals=" + DEFAULT_FOLHAS_CIENCIA_JULG_APELACAO);

        // Get all the processoList where folhasCienciaJulgApelacao not equals to UPDATED_FOLHAS_CIENCIA_JULG_APELACAO
        defaultProcessoShouldBeFound("folhasCienciaJulgApelacao.notEquals=" + UPDATED_FOLHAS_CIENCIA_JULG_APELACAO);
    }

    @Test
    @Transactional
    void getAllProcessosByFolhasCienciaJulgApelacaoIsInShouldWork() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where folhasCienciaJulgApelacao in DEFAULT_FOLHAS_CIENCIA_JULG_APELACAO or UPDATED_FOLHAS_CIENCIA_JULG_APELACAO
        defaultProcessoShouldBeFound(
            "folhasCienciaJulgApelacao.in=" + DEFAULT_FOLHAS_CIENCIA_JULG_APELACAO + "," + UPDATED_FOLHAS_CIENCIA_JULG_APELACAO
        );

        // Get all the processoList where folhasCienciaJulgApelacao equals to UPDATED_FOLHAS_CIENCIA_JULG_APELACAO
        defaultProcessoShouldNotBeFound("folhasCienciaJulgApelacao.in=" + UPDATED_FOLHAS_CIENCIA_JULG_APELACAO);
    }

    @Test
    @Transactional
    void getAllProcessosByFolhasCienciaJulgApelacaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where folhasCienciaJulgApelacao is not null
        defaultProcessoShouldBeFound("folhasCienciaJulgApelacao.specified=true");

        // Get all the processoList where folhasCienciaJulgApelacao is null
        defaultProcessoShouldNotBeFound("folhasCienciaJulgApelacao.specified=false");
    }

    @Test
    @Transactional
    void getAllProcessosByFolhasCienciaJulgApelacaoContainsSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where folhasCienciaJulgApelacao contains DEFAULT_FOLHAS_CIENCIA_JULG_APELACAO
        defaultProcessoShouldBeFound("folhasCienciaJulgApelacao.contains=" + DEFAULT_FOLHAS_CIENCIA_JULG_APELACAO);

        // Get all the processoList where folhasCienciaJulgApelacao contains UPDATED_FOLHAS_CIENCIA_JULG_APELACAO
        defaultProcessoShouldNotBeFound("folhasCienciaJulgApelacao.contains=" + UPDATED_FOLHAS_CIENCIA_JULG_APELACAO);
    }

    @Test
    @Transactional
    void getAllProcessosByFolhasCienciaJulgApelacaoNotContainsSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where folhasCienciaJulgApelacao does not contain DEFAULT_FOLHAS_CIENCIA_JULG_APELACAO
        defaultProcessoShouldNotBeFound("folhasCienciaJulgApelacao.doesNotContain=" + DEFAULT_FOLHAS_CIENCIA_JULG_APELACAO);

        // Get all the processoList where folhasCienciaJulgApelacao does not contain UPDATED_FOLHAS_CIENCIA_JULG_APELACAO
        defaultProcessoShouldBeFound("folhasCienciaJulgApelacao.doesNotContain=" + UPDATED_FOLHAS_CIENCIA_JULG_APELACAO);
    }

    @Test
    @Transactional
    void getAllProcessosByEmbargoDeclaracaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where embargoDeclaracao not equals to DEFAULT_EMBARGO_DECLARACAO
        defaultProcessoShouldNotBeFound("embargoDeclaracao.notEquals=" + DEFAULT_EMBARGO_DECLARACAO);

        // Get all the processoList where embargoDeclaracao not equals to UPDATED_EMBARGO_DECLARACAO
        defaultProcessoShouldBeFound("embargoDeclaracao.notEquals=" + UPDATED_EMBARGO_DECLARACAO);
    }

    @Test
    @Transactional
    void getAllProcessosByEmbargoDeclaracaoIsInShouldWork() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where embargoDeclaracao in DEFAULT_EMBARGO_DECLARACAO or UPDATED_EMBARGO_DECLARACAO
        defaultProcessoShouldBeFound("embargoDeclaracao.in=" + DEFAULT_EMBARGO_DECLARACAO + "," + UPDATED_EMBARGO_DECLARACAO);

        // Get all the processoList where embargoDeclaracao equals to UPDATED_EMBARGO_DECLARACAO
        defaultProcessoShouldNotBeFound("embargoDeclaracao.in=" + UPDATED_EMBARGO_DECLARACAO);
    }

    @Test
    @Transactional
    void getAllProcessosByEmbargoDeclaracaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where embargoDeclaracao is not null
        defaultProcessoShouldBeFound("embargoDeclaracao.specified=true");

        // Get all the processoList where embargoDeclaracao is null
        defaultProcessoShouldNotBeFound("embargoDeclaracao.specified=false");
    }

    @Test
    @Transactional
    void getAllProcessosByEmbargoRecursoExtraordinarioIsEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where embargoRecursoExtraordinario equals to DEFAULT_EMBARGO_RECURSO_EXTRAORDINARIO
        defaultProcessoShouldBeFound("embargoRecursoExtraordinario.equals=" + DEFAULT_EMBARGO_RECURSO_EXTRAORDINARIO);

        // Get all the processoList where embargoRecursoExtraordinario equals to UPDATED_EMBARGO_RECURSO_EXTRAORDINARIO
        defaultProcessoShouldNotBeFound("embargoRecursoExtraordinario.equals=" + UPDATED_EMBARGO_RECURSO_EXTRAORDINARIO);
    }

    @Test
    @Transactional
    void getAllProcessosByEmbargoRecursoExtraordinarioIsNotEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where embargoRecursoExtraordinario not equals to DEFAULT_EMBARGO_RECURSO_EXTRAORDINARIO
        defaultProcessoShouldNotBeFound("embargoRecursoExtraordinario.notEquals=" + DEFAULT_EMBARGO_RECURSO_EXTRAORDINARIO);

        // Get all the processoList where embargoRecursoExtraordinario not equals to UPDATED_EMBARGO_RECURSO_EXTRAORDINARIO
        defaultProcessoShouldBeFound("embargoRecursoExtraordinario.notEquals=" + UPDATED_EMBARGO_RECURSO_EXTRAORDINARIO);
    }

    @Test
    @Transactional
    void getAllProcessosByEmbargoRecursoExtraordinarioIsInShouldWork() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where embargoRecursoExtraordinario in DEFAULT_EMBARGO_RECURSO_EXTRAORDINARIO or UPDATED_EMBARGO_RECURSO_EXTRAORDINARIO
        defaultProcessoShouldBeFound(
            "embargoRecursoExtraordinario.in=" + DEFAULT_EMBARGO_RECURSO_EXTRAORDINARIO + "," + UPDATED_EMBARGO_RECURSO_EXTRAORDINARIO
        );

        // Get all the processoList where embargoRecursoExtraordinario equals to UPDATED_EMBARGO_RECURSO_EXTRAORDINARIO
        defaultProcessoShouldNotBeFound("embargoRecursoExtraordinario.in=" + UPDATED_EMBARGO_RECURSO_EXTRAORDINARIO);
    }

    @Test
    @Transactional
    void getAllProcessosByEmbargoRecursoExtraordinarioIsNullOrNotNull() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where embargoRecursoExtraordinario is not null
        defaultProcessoShouldBeFound("embargoRecursoExtraordinario.specified=true");

        // Get all the processoList where embargoRecursoExtraordinario is null
        defaultProcessoShouldNotBeFound("embargoRecursoExtraordinario.specified=false");
    }

    @Test
    @Transactional
    void getAllProcessosByFolhasRecursoEspecialIsEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where folhasRecursoEspecial equals to DEFAULT_FOLHAS_RECURSO_ESPECIAL
        defaultProcessoShouldBeFound("folhasRecursoEspecial.equals=" + DEFAULT_FOLHAS_RECURSO_ESPECIAL);

        // Get all the processoList where folhasRecursoEspecial equals to UPDATED_FOLHAS_RECURSO_ESPECIAL
        defaultProcessoShouldNotBeFound("folhasRecursoEspecial.equals=" + UPDATED_FOLHAS_RECURSO_ESPECIAL);
    }

    @Test
    @Transactional
    void getAllProcessosByFolhasRecursoEspecialIsNotEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where folhasRecursoEspecial not equals to DEFAULT_FOLHAS_RECURSO_ESPECIAL
        defaultProcessoShouldNotBeFound("folhasRecursoEspecial.notEquals=" + DEFAULT_FOLHAS_RECURSO_ESPECIAL);

        // Get all the processoList where folhasRecursoEspecial not equals to UPDATED_FOLHAS_RECURSO_ESPECIAL
        defaultProcessoShouldBeFound("folhasRecursoEspecial.notEquals=" + UPDATED_FOLHAS_RECURSO_ESPECIAL);
    }

    @Test
    @Transactional
    void getAllProcessosByFolhasRecursoEspecialIsInShouldWork() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where folhasRecursoEspecial in DEFAULT_FOLHAS_RECURSO_ESPECIAL or UPDATED_FOLHAS_RECURSO_ESPECIAL
        defaultProcessoShouldBeFound("folhasRecursoEspecial.in=" + DEFAULT_FOLHAS_RECURSO_ESPECIAL + "," + UPDATED_FOLHAS_RECURSO_ESPECIAL);

        // Get all the processoList where folhasRecursoEspecial equals to UPDATED_FOLHAS_RECURSO_ESPECIAL
        defaultProcessoShouldNotBeFound("folhasRecursoEspecial.in=" + UPDATED_FOLHAS_RECURSO_ESPECIAL);
    }

    @Test
    @Transactional
    void getAllProcessosByFolhasRecursoEspecialIsNullOrNotNull() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where folhasRecursoEspecial is not null
        defaultProcessoShouldBeFound("folhasRecursoEspecial.specified=true");

        // Get all the processoList where folhasRecursoEspecial is null
        defaultProcessoShouldNotBeFound("folhasRecursoEspecial.specified=false");
    }

    @Test
    @Transactional
    void getAllProcessosByFolhasRecursoEspecialContainsSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where folhasRecursoEspecial contains DEFAULT_FOLHAS_RECURSO_ESPECIAL
        defaultProcessoShouldBeFound("folhasRecursoEspecial.contains=" + DEFAULT_FOLHAS_RECURSO_ESPECIAL);

        // Get all the processoList where folhasRecursoEspecial contains UPDATED_FOLHAS_RECURSO_ESPECIAL
        defaultProcessoShouldNotBeFound("folhasRecursoEspecial.contains=" + UPDATED_FOLHAS_RECURSO_ESPECIAL);
    }

    @Test
    @Transactional
    void getAllProcessosByFolhasRecursoEspecialNotContainsSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where folhasRecursoEspecial does not contain DEFAULT_FOLHAS_RECURSO_ESPECIAL
        defaultProcessoShouldNotBeFound("folhasRecursoEspecial.doesNotContain=" + DEFAULT_FOLHAS_RECURSO_ESPECIAL);

        // Get all the processoList where folhasRecursoEspecial does not contain UPDATED_FOLHAS_RECURSO_ESPECIAL
        defaultProcessoShouldBeFound("folhasRecursoEspecial.doesNotContain=" + UPDATED_FOLHAS_RECURSO_ESPECIAL);
    }

    @Test
    @Transactional
    void getAllProcessosByFolhasCienciaJulgamentoRecursoEspecialIsEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where folhasCienciaJulgamentoRecursoEspecial equals to DEFAULT_FOLHAS_CIENCIA_JULGAMENTO_RECURSO_ESPECIAL
        defaultProcessoShouldBeFound("folhasCienciaJulgamentoRecursoEspecial.equals=" + DEFAULT_FOLHAS_CIENCIA_JULGAMENTO_RECURSO_ESPECIAL);

        // Get all the processoList where folhasCienciaJulgamentoRecursoEspecial equals to UPDATED_FOLHAS_CIENCIA_JULGAMENTO_RECURSO_ESPECIAL
        defaultProcessoShouldNotBeFound(
            "folhasCienciaJulgamentoRecursoEspecial.equals=" + UPDATED_FOLHAS_CIENCIA_JULGAMENTO_RECURSO_ESPECIAL
        );
    }

    @Test
    @Transactional
    void getAllProcessosByFolhasCienciaJulgamentoRecursoEspecialIsNotEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where folhasCienciaJulgamentoRecursoEspecial not equals to DEFAULT_FOLHAS_CIENCIA_JULGAMENTO_RECURSO_ESPECIAL
        defaultProcessoShouldNotBeFound(
            "folhasCienciaJulgamentoRecursoEspecial.notEquals=" + DEFAULT_FOLHAS_CIENCIA_JULGAMENTO_RECURSO_ESPECIAL
        );

        // Get all the processoList where folhasCienciaJulgamentoRecursoEspecial not equals to UPDATED_FOLHAS_CIENCIA_JULGAMENTO_RECURSO_ESPECIAL
        defaultProcessoShouldBeFound(
            "folhasCienciaJulgamentoRecursoEspecial.notEquals=" + UPDATED_FOLHAS_CIENCIA_JULGAMENTO_RECURSO_ESPECIAL
        );
    }

    @Test
    @Transactional
    void getAllProcessosByFolhasCienciaJulgamentoRecursoEspecialIsInShouldWork() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where folhasCienciaJulgamentoRecursoEspecial in DEFAULT_FOLHAS_CIENCIA_JULGAMENTO_RECURSO_ESPECIAL or UPDATED_FOLHAS_CIENCIA_JULGAMENTO_RECURSO_ESPECIAL
        defaultProcessoShouldBeFound(
            "folhasCienciaJulgamentoRecursoEspecial.in=" +
            DEFAULT_FOLHAS_CIENCIA_JULGAMENTO_RECURSO_ESPECIAL +
            "," +
            UPDATED_FOLHAS_CIENCIA_JULGAMENTO_RECURSO_ESPECIAL
        );

        // Get all the processoList where folhasCienciaJulgamentoRecursoEspecial equals to UPDATED_FOLHAS_CIENCIA_JULGAMENTO_RECURSO_ESPECIAL
        defaultProcessoShouldNotBeFound("folhasCienciaJulgamentoRecursoEspecial.in=" + UPDATED_FOLHAS_CIENCIA_JULGAMENTO_RECURSO_ESPECIAL);
    }

    @Test
    @Transactional
    void getAllProcessosByFolhasCienciaJulgamentoRecursoEspecialIsNullOrNotNull() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where folhasCienciaJulgamentoRecursoEspecial is not null
        defaultProcessoShouldBeFound("folhasCienciaJulgamentoRecursoEspecial.specified=true");

        // Get all the processoList where folhasCienciaJulgamentoRecursoEspecial is null
        defaultProcessoShouldNotBeFound("folhasCienciaJulgamentoRecursoEspecial.specified=false");
    }

    @Test
    @Transactional
    void getAllProcessosByFolhasCienciaJulgamentoRecursoEspecialContainsSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where folhasCienciaJulgamentoRecursoEspecial contains DEFAULT_FOLHAS_CIENCIA_JULGAMENTO_RECURSO_ESPECIAL
        defaultProcessoShouldBeFound(
            "folhasCienciaJulgamentoRecursoEspecial.contains=" + DEFAULT_FOLHAS_CIENCIA_JULGAMENTO_RECURSO_ESPECIAL
        );

        // Get all the processoList where folhasCienciaJulgamentoRecursoEspecial contains UPDATED_FOLHAS_CIENCIA_JULGAMENTO_RECURSO_ESPECIAL
        defaultProcessoShouldNotBeFound(
            "folhasCienciaJulgamentoRecursoEspecial.contains=" + UPDATED_FOLHAS_CIENCIA_JULGAMENTO_RECURSO_ESPECIAL
        );
    }

    @Test
    @Transactional
    void getAllProcessosByFolhasCienciaJulgamentoRecursoEspecialNotContainsSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where folhasCienciaJulgamentoRecursoEspecial does not contain DEFAULT_FOLHAS_CIENCIA_JULGAMENTO_RECURSO_ESPECIAL
        defaultProcessoShouldNotBeFound(
            "folhasCienciaJulgamentoRecursoEspecial.doesNotContain=" + DEFAULT_FOLHAS_CIENCIA_JULGAMENTO_RECURSO_ESPECIAL
        );

        // Get all the processoList where folhasCienciaJulgamentoRecursoEspecial does not contain UPDATED_FOLHAS_CIENCIA_JULGAMENTO_RECURSO_ESPECIAL
        defaultProcessoShouldBeFound(
            "folhasCienciaJulgamentoRecursoEspecial.doesNotContain=" + UPDATED_FOLHAS_CIENCIA_JULGAMENTO_RECURSO_ESPECIAL
        );
    }

    @Test
    @Transactional
    void getAllProcessosByEmbargoRecursoEspecialIsNotEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where embargoRecursoEspecial not equals to DEFAULT_EMBARGO_RECURSO_ESPECIAL
        defaultProcessoShouldNotBeFound("embargoRecursoEspecial.notEquals=" + DEFAULT_EMBARGO_RECURSO_ESPECIAL);

        // Get all the processoList where embargoRecursoEspecial not equals to UPDATED_EMBARGO_RECURSO_ESPECIAL
        defaultProcessoShouldBeFound("embargoRecursoEspecial.notEquals=" + UPDATED_EMBARGO_RECURSO_ESPECIAL);
    }

    @Test
    @Transactional
    void getAllProcessosByEmbargoRecursoEspecialIsInShouldWork() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where embargoRecursoEspecial in DEFAULT_EMBARGO_RECURSO_ESPECIAL or UPDATED_EMBARGO_RECURSO_ESPECIAL
        defaultProcessoShouldBeFound(
            "embargoRecursoEspecial.in=" + DEFAULT_EMBARGO_RECURSO_ESPECIAL + "," + UPDATED_EMBARGO_RECURSO_ESPECIAL
        );

        // Get all the processoList where embargoRecursoEspecial equals to UPDATED_EMBARGO_RECURSO_ESPECIAL
        defaultProcessoShouldNotBeFound("embargoRecursoEspecial.in=" + UPDATED_EMBARGO_RECURSO_ESPECIAL);
    }

    @Test
    @Transactional
    void getAllProcessosByEmbargoRecursoEspecialIsNullOrNotNull() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where embargoRecursoEspecial is not null
        defaultProcessoShouldBeFound("embargoRecursoEspecial.specified=true");

        // Get all the processoList where embargoRecursoEspecial is null
        defaultProcessoShouldNotBeFound("embargoRecursoEspecial.specified=false");
    }

    @Test
    @Transactional
    void getAllProcessosByFolhasCienciaIsEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where folhasCiencia equals to DEFAULT_FOLHAS_CIENCIA
        defaultProcessoShouldBeFound("folhasCiencia.equals=" + DEFAULT_FOLHAS_CIENCIA);

        // Get all the processoList where folhasCiencia equals to UPDATED_FOLHAS_CIENCIA
        defaultProcessoShouldNotBeFound("folhasCiencia.equals=" + UPDATED_FOLHAS_CIENCIA);
    }

    @Test
    @Transactional
    void getAllProcessosByFolhasCienciaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where folhasCiencia not equals to DEFAULT_FOLHAS_CIENCIA
        defaultProcessoShouldNotBeFound("folhasCiencia.notEquals=" + DEFAULT_FOLHAS_CIENCIA);

        // Get all the processoList where folhasCiencia not equals to UPDATED_FOLHAS_CIENCIA
        defaultProcessoShouldBeFound("folhasCiencia.notEquals=" + UPDATED_FOLHAS_CIENCIA);
    }

    @Test
    @Transactional
    void getAllProcessosByFolhasCienciaIsInShouldWork() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where folhasCiencia in DEFAULT_FOLHAS_CIENCIA or UPDATED_FOLHAS_CIENCIA
        defaultProcessoShouldBeFound("folhasCiencia.in=" + DEFAULT_FOLHAS_CIENCIA + "," + UPDATED_FOLHAS_CIENCIA);

        // Get all the processoList where folhasCiencia equals to UPDATED_FOLHAS_CIENCIA
        defaultProcessoShouldNotBeFound("folhasCiencia.in=" + UPDATED_FOLHAS_CIENCIA);
    }

    @Test
    @Transactional
    void getAllProcessosByFolhasCienciaIsNullOrNotNull() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where folhasCiencia is not null
        defaultProcessoShouldBeFound("folhasCiencia.specified=true");

        // Get all the processoList where folhasCiencia is null
        defaultProcessoShouldNotBeFound("folhasCiencia.specified=false");
    }

    @Test
    @Transactional
    void getAllProcessosByFolhasCienciaContainsSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where folhasCiencia contains DEFAULT_FOLHAS_CIENCIA
        defaultProcessoShouldBeFound("folhasCiencia.contains=" + DEFAULT_FOLHAS_CIENCIA);

        // Get all the processoList where folhasCiencia contains UPDATED_FOLHAS_CIENCIA
        defaultProcessoShouldNotBeFound("folhasCiencia.contains=" + UPDATED_FOLHAS_CIENCIA);
    }

    @Test
    @Transactional
    void getAllProcessosByFolhasCienciaNotContainsSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where folhasCiencia does not contain DEFAULT_FOLHAS_CIENCIA
        defaultProcessoShouldNotBeFound("folhasCiencia.doesNotContain=" + DEFAULT_FOLHAS_CIENCIA);

        // Get all the processoList where folhasCiencia does not contain UPDATED_FOLHAS_CIENCIA
        defaultProcessoShouldBeFound("folhasCiencia.doesNotContain=" + UPDATED_FOLHAS_CIENCIA);
    }

    @Test
    @Transactional
    void getAllProcessosByAgravoRespReIsEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where agravoRespRe equals to DEFAULT_AGRAVO_RESP_RE
        defaultProcessoShouldBeFound("agravoRespRe.equals=" + DEFAULT_AGRAVO_RESP_RE);

        // Get all the processoList where agravoRespRe equals to UPDATED_AGRAVO_RESP_RE
        defaultProcessoShouldNotBeFound("agravoRespRe.equals=" + UPDATED_AGRAVO_RESP_RE);
    }

    @Test
    @Transactional
    void getAllProcessosByAgravoRespReIsNotEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where agravoRespRe not equals to DEFAULT_AGRAVO_RESP_RE
        defaultProcessoShouldNotBeFound("agravoRespRe.notEquals=" + DEFAULT_AGRAVO_RESP_RE);

        // Get all the processoList where agravoRespRe not equals to UPDATED_AGRAVO_RESP_RE
        defaultProcessoShouldBeFound("agravoRespRe.notEquals=" + UPDATED_AGRAVO_RESP_RE);
    }

    @Test
    @Transactional
    void getAllProcessosByAgravoRespReIsInShouldWork() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where agravoRespRe in DEFAULT_AGRAVO_RESP_RE or UPDATED_AGRAVO_RESP_RE
        defaultProcessoShouldBeFound("agravoRespRe.in=" + DEFAULT_AGRAVO_RESP_RE + "," + UPDATED_AGRAVO_RESP_RE);

        // Get all the processoList where agravoRespRe equals to UPDATED_AGRAVO_RESP_RE
        defaultProcessoShouldNotBeFound("agravoRespRe.in=" + UPDATED_AGRAVO_RESP_RE);
    }

    @Test
    @Transactional
    void getAllProcessosByAgravoRespReIsNullOrNotNull() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where agravoRespRe is not null
        defaultProcessoShouldBeFound("agravoRespRe.specified=true");

        // Get all the processoList where agravoRespRe is null
        defaultProcessoShouldNotBeFound("agravoRespRe.specified=false");
    }

    @Test
    @Transactional
    void getAllProcessosByAgravoRespReContainsSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where agravoRespRe contains DEFAULT_AGRAVO_RESP_RE
        defaultProcessoShouldBeFound("agravoRespRe.contains=" + DEFAULT_AGRAVO_RESP_RE);

        // Get all the processoList where agravoRespRe contains UPDATED_AGRAVO_RESP_RE
        defaultProcessoShouldNotBeFound("agravoRespRe.contains=" + UPDATED_AGRAVO_RESP_RE);
    }

    @Test
    @Transactional
    void getAllProcessosByAgravoRespReNotContainsSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where agravoRespRe does not contain DEFAULT_AGRAVO_RESP_RE
        defaultProcessoShouldNotBeFound("agravoRespRe.doesNotContain=" + DEFAULT_AGRAVO_RESP_RE);

        // Get all the processoList where agravoRespRe does not contain UPDATED_AGRAVO_RESP_RE
        defaultProcessoShouldBeFound("agravoRespRe.doesNotContain=" + UPDATED_AGRAVO_RESP_RE);
    }

    @Test
    @Transactional
    void getAllProcessosByFolhasRespReIsEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where folhasRespRe equals to DEFAULT_FOLHAS_RESP_RE
        defaultProcessoShouldBeFound("folhasRespRe.equals=" + DEFAULT_FOLHAS_RESP_RE);

        // Get all the processoList where folhasRespRe equals to UPDATED_FOLHAS_RESP_RE
        defaultProcessoShouldNotBeFound("folhasRespRe.equals=" + UPDATED_FOLHAS_RESP_RE);
    }

    @Test
    @Transactional
    void getAllProcessosByFolhasRespReIsNotEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where folhasRespRe not equals to DEFAULT_FOLHAS_RESP_RE
        defaultProcessoShouldNotBeFound("folhasRespRe.notEquals=" + DEFAULT_FOLHAS_RESP_RE);

        // Get all the processoList where folhasRespRe not equals to UPDATED_FOLHAS_RESP_RE
        defaultProcessoShouldBeFound("folhasRespRe.notEquals=" + UPDATED_FOLHAS_RESP_RE);
    }

    @Test
    @Transactional
    void getAllProcessosByFolhasRespReIsInShouldWork() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where folhasRespRe in DEFAULT_FOLHAS_RESP_RE or UPDATED_FOLHAS_RESP_RE
        defaultProcessoShouldBeFound("folhasRespRe.in=" + DEFAULT_FOLHAS_RESP_RE + "," + UPDATED_FOLHAS_RESP_RE);

        // Get all the processoList where folhasRespRe equals to UPDATED_FOLHAS_RESP_RE
        defaultProcessoShouldNotBeFound("folhasRespRe.in=" + UPDATED_FOLHAS_RESP_RE);
    }

    @Test
    @Transactional
    void getAllProcessosByFolhasRespReIsNullOrNotNull() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where folhasRespRe is not null
        defaultProcessoShouldBeFound("folhasRespRe.specified=true");

        // Get all the processoList where folhasRespRe is null
        defaultProcessoShouldNotBeFound("folhasRespRe.specified=false");
    }

    @Test
    @Transactional
    void getAllProcessosByFolhasRespReContainsSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where folhasRespRe contains DEFAULT_FOLHAS_RESP_RE
        defaultProcessoShouldBeFound("folhasRespRe.contains=" + DEFAULT_FOLHAS_RESP_RE);

        // Get all the processoList where folhasRespRe contains UPDATED_FOLHAS_RESP_RE
        defaultProcessoShouldNotBeFound("folhasRespRe.contains=" + UPDATED_FOLHAS_RESP_RE);
    }

    @Test
    @Transactional
    void getAllProcessosByFolhasRespReNotContainsSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where folhasRespRe does not contain DEFAULT_FOLHAS_RESP_RE
        defaultProcessoShouldNotBeFound("folhasRespRe.doesNotContain=" + DEFAULT_FOLHAS_RESP_RE);

        // Get all the processoList where folhasRespRe does not contain UPDATED_FOLHAS_RESP_RE
        defaultProcessoShouldBeFound("folhasRespRe.doesNotContain=" + UPDATED_FOLHAS_RESP_RE);
    }

    @Test
    @Transactional
    void getAllProcessosByFolhasCienciaJulgamentoAgravoRespReIsEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where folhasCienciaJulgamentoAgravoRespRe equals to DEFAULT_FOLHAS_CIENCIA_JULGAMENTO_AGRAVO_RESP_RE
        defaultProcessoShouldBeFound("folhasCienciaJulgamentoAgravoRespRe.equals=" + DEFAULT_FOLHAS_CIENCIA_JULGAMENTO_AGRAVO_RESP_RE);

        // Get all the processoList where folhasCienciaJulgamentoAgravoRespRe equals to UPDATED_FOLHAS_CIENCIA_JULGAMENTO_AGRAVO_RESP_RE
        defaultProcessoShouldNotBeFound("folhasCienciaJulgamentoAgravoRespRe.equals=" + UPDATED_FOLHAS_CIENCIA_JULGAMENTO_AGRAVO_RESP_RE);
    }

    @Test
    @Transactional
    void getAllProcessosByFolhasCienciaJulgamentoAgravoRespReIsNotEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where folhasCienciaJulgamentoAgravoRespRe not equals to DEFAULT_FOLHAS_CIENCIA_JULGAMENTO_AGRAVO_RESP_RE
        defaultProcessoShouldNotBeFound(
            "folhasCienciaJulgamentoAgravoRespRe.notEquals=" + DEFAULT_FOLHAS_CIENCIA_JULGAMENTO_AGRAVO_RESP_RE
        );

        // Get all the processoList where folhasCienciaJulgamentoAgravoRespRe not equals to UPDATED_FOLHAS_CIENCIA_JULGAMENTO_AGRAVO_RESP_RE
        defaultProcessoShouldBeFound("folhasCienciaJulgamentoAgravoRespRe.notEquals=" + UPDATED_FOLHAS_CIENCIA_JULGAMENTO_AGRAVO_RESP_RE);
    }

    @Test
    @Transactional
    void getAllProcessosByFolhasCienciaJulgamentoAgravoRespReIsInShouldWork() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where folhasCienciaJulgamentoAgravoRespRe in DEFAULT_FOLHAS_CIENCIA_JULGAMENTO_AGRAVO_RESP_RE or UPDATED_FOLHAS_CIENCIA_JULGAMENTO_AGRAVO_RESP_RE
        defaultProcessoShouldBeFound(
            "folhasCienciaJulgamentoAgravoRespRe.in=" +
            DEFAULT_FOLHAS_CIENCIA_JULGAMENTO_AGRAVO_RESP_RE +
            "," +
            UPDATED_FOLHAS_CIENCIA_JULGAMENTO_AGRAVO_RESP_RE
        );

        // Get all the processoList where folhasCienciaJulgamentoAgravoRespRe equals to UPDATED_FOLHAS_CIENCIA_JULGAMENTO_AGRAVO_RESP_RE
        defaultProcessoShouldNotBeFound("folhasCienciaJulgamentoAgravoRespRe.in=" + UPDATED_FOLHAS_CIENCIA_JULGAMENTO_AGRAVO_RESP_RE);
    }

    @Test
    @Transactional
    void getAllProcessosByFolhasCienciaJulgamentoAgravoRespReIsNullOrNotNull() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where folhasCienciaJulgamentoAgravoRespRe is not null
        defaultProcessoShouldBeFound("folhasCienciaJulgamentoAgravoRespRe.specified=true");

        // Get all the processoList where folhasCienciaJulgamentoAgravoRespRe is null
        defaultProcessoShouldNotBeFound("folhasCienciaJulgamentoAgravoRespRe.specified=false");
    }

    @Test
    @Transactional
    void getAllProcessosByFolhasCienciaJulgamentoAgravoRespReContainsSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where folhasCienciaJulgamentoAgravoRespRe contains DEFAULT_FOLHAS_CIENCIA_JULGAMENTO_AGRAVO_RESP_RE
        defaultProcessoShouldBeFound("folhasCienciaJulgamentoAgravoRespRe.contains=" + DEFAULT_FOLHAS_CIENCIA_JULGAMENTO_AGRAVO_RESP_RE);

        // Get all the processoList where folhasCienciaJulgamentoAgravoRespRe contains UPDATED_FOLHAS_CIENCIA_JULGAMENTO_AGRAVO_RESP_RE
        defaultProcessoShouldNotBeFound("folhasCienciaJulgamentoAgravoRespRe.contains=" + UPDATED_FOLHAS_CIENCIA_JULGAMENTO_AGRAVO_RESP_RE);
    }

    @Test
    @Transactional
    void getAllProcessosByFolhasCienciaJulgamentoAgravoRespReNotContainsSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where folhasCienciaJulgamentoAgravoRespRe does not contain DEFAULT_FOLHAS_CIENCIA_JULGAMENTO_AGRAVO_RESP_RE
        defaultProcessoShouldNotBeFound(
            "folhasCienciaJulgamentoAgravoRespRe.doesNotContain=" + DEFAULT_FOLHAS_CIENCIA_JULGAMENTO_AGRAVO_RESP_RE
        );

        // Get all the processoList where folhasCienciaJulgamentoAgravoRespRe does not contain UPDATED_FOLHAS_CIENCIA_JULGAMENTO_AGRAVO_RESP_RE
        defaultProcessoShouldBeFound(
            "folhasCienciaJulgamentoAgravoRespRe.doesNotContain=" + UPDATED_FOLHAS_CIENCIA_JULGAMENTO_AGRAVO_RESP_RE
        );
    }

    @Test
    @Transactional
    void getAllProcessosByEmbargoRespReIsNotEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where embargoRespRe not equals to DEFAULT_EMBARGO_RESP_RE
        defaultProcessoShouldNotBeFound("embargoRespRe.notEquals=" + DEFAULT_EMBARGO_RESP_RE);

        // Get all the processoList where embargoRespRe not equals to UPDATED_EMBARGO_RESP_RE
        defaultProcessoShouldBeFound("embargoRespRe.notEquals=" + UPDATED_EMBARGO_RESP_RE);
    }

    @Test
    @Transactional
    void getAllProcessosByEmbargoRespReIsInShouldWork() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where embargoRespRe in DEFAULT_EMBARGO_RESP_RE or UPDATED_EMBARGO_RESP_RE
        defaultProcessoShouldBeFound("embargoRespRe.in=" + DEFAULT_EMBARGO_RESP_RE + "," + UPDATED_EMBARGO_RESP_RE);

        // Get all the processoList where embargoRespRe equals to UPDATED_EMBARGO_RESP_RE
        defaultProcessoShouldNotBeFound("embargoRespRe.in=" + UPDATED_EMBARGO_RESP_RE);
    }

    @Test
    @Transactional
    void getAllProcessosByEmbargoRespReIsNullOrNotNull() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where embargoRespRe is not null
        defaultProcessoShouldBeFound("embargoRespRe.specified=true");

        // Get all the processoList where embargoRespRe is null
        defaultProcessoShouldNotBeFound("embargoRespRe.specified=false");
    }

    @Test
    @Transactional
    void getAllProcessosByEmbargoRespReContainsSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where embargoRespRe contains DEFAULT_EMBARGO_RESP_RE
        defaultProcessoShouldBeFound("embargoRespRe.contains=" + DEFAULT_EMBARGO_RESP_RE);

        // Get all the processoList where embargoRespRe contains UPDATED_EMBARGO_RESP_RE
        defaultProcessoShouldNotBeFound("embargoRespRe.contains=" + UPDATED_EMBARGO_RESP_RE);
    }

    @Test
    @Transactional
    void getAllProcessosByEmbargoRespReNotContainsSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where embargoRespRe does not contain DEFAULT_EMBARGO_RESP_RE
        defaultProcessoShouldNotBeFound("embargoRespRe.doesNotContain=" + DEFAULT_EMBARGO_RESP_RE);

        // Get all the processoList where embargoRespRe does not contain UPDATED_EMBARGO_RESP_RE
        defaultProcessoShouldBeFound("embargoRespRe.doesNotContain=" + UPDATED_EMBARGO_RESP_RE);
    }

    @Test
    @Transactional
    void getAllProcessosByAgravoInternoIsEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where agravoInterno equals to DEFAULT_AGRAVO_INTERNO
        defaultProcessoShouldBeFound("agravoInterno.equals=" + DEFAULT_AGRAVO_INTERNO);

        // Get all the processoList where agravoInterno equals to UPDATED_AGRAVO_INTERNO
        defaultProcessoShouldNotBeFound("agravoInterno.equals=" + UPDATED_AGRAVO_INTERNO);
    }

    @Test
    @Transactional
    void getAllProcessosByAgravoInternoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where agravoInterno not equals to DEFAULT_AGRAVO_INTERNO
        defaultProcessoShouldNotBeFound("agravoInterno.notEquals=" + DEFAULT_AGRAVO_INTERNO);

        // Get all the processoList where agravoInterno not equals to UPDATED_AGRAVO_INTERNO
        defaultProcessoShouldBeFound("agravoInterno.notEquals=" + UPDATED_AGRAVO_INTERNO);
    }

    @Test
    @Transactional
    void getAllProcessosByAgravoInternoIsInShouldWork() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where agravoInterno in DEFAULT_AGRAVO_INTERNO or UPDATED_AGRAVO_INTERNO
        defaultProcessoShouldBeFound("agravoInterno.in=" + DEFAULT_AGRAVO_INTERNO + "," + UPDATED_AGRAVO_INTERNO);

        // Get all the processoList where agravoInterno equals to UPDATED_AGRAVO_INTERNO
        defaultProcessoShouldNotBeFound("agravoInterno.in=" + UPDATED_AGRAVO_INTERNO);
    }

    @Test
    @Transactional
    void getAllProcessosByAgravoInternoIsNullOrNotNull() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where agravoInterno is not null
        defaultProcessoShouldBeFound("agravoInterno.specified=true");

        // Get all the processoList where agravoInterno is null
        defaultProcessoShouldNotBeFound("agravoInterno.specified=false");
    }

    @Test
    @Transactional
    void getAllProcessosByAgravoInternoContainsSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where agravoInterno contains DEFAULT_AGRAVO_INTERNO
        defaultProcessoShouldBeFound("agravoInterno.contains=" + DEFAULT_AGRAVO_INTERNO);

        // Get all the processoList where agravoInterno contains UPDATED_AGRAVO_INTERNO
        defaultProcessoShouldNotBeFound("agravoInterno.contains=" + UPDATED_AGRAVO_INTERNO);
    }

    @Test
    @Transactional
    void getAllProcessosByAgravoInternoNotContainsSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where agravoInterno does not contain DEFAULT_AGRAVO_INTERNO
        defaultProcessoShouldNotBeFound("agravoInterno.doesNotContain=" + DEFAULT_AGRAVO_INTERNO);

        // Get all the processoList where agravoInterno does not contain UPDATED_AGRAVO_INTERNO
        defaultProcessoShouldBeFound("agravoInterno.doesNotContain=" + UPDATED_AGRAVO_INTERNO);
    }

    @Test
    @Transactional
    void getAllProcessosByFolhasAgravoInternoIsEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where folhasAgravoInterno equals to DEFAULT_FOLHAS_AGRAVO_INTERNO
        defaultProcessoShouldBeFound("folhasAgravoInterno.equals=" + DEFAULT_FOLHAS_AGRAVO_INTERNO);

        // Get all the processoList where folhasAgravoInterno equals to UPDATED_FOLHAS_AGRAVO_INTERNO
        defaultProcessoShouldNotBeFound("folhasAgravoInterno.equals=" + UPDATED_FOLHAS_AGRAVO_INTERNO);
    }

    @Test
    @Transactional
    void getAllProcessosByFolhasAgravoInternoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where folhasAgravoInterno not equals to DEFAULT_FOLHAS_AGRAVO_INTERNO
        defaultProcessoShouldNotBeFound("folhasAgravoInterno.notEquals=" + DEFAULT_FOLHAS_AGRAVO_INTERNO);

        // Get all the processoList where folhasAgravoInterno not equals to UPDATED_FOLHAS_AGRAVO_INTERNO
        defaultProcessoShouldBeFound("folhasAgravoInterno.notEquals=" + UPDATED_FOLHAS_AGRAVO_INTERNO);
    }

    @Test
    @Transactional
    void getAllProcessosByFolhasAgravoInternoIsInShouldWork() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where folhasAgravoInterno in DEFAULT_FOLHAS_AGRAVO_INTERNO or UPDATED_FOLHAS_AGRAVO_INTERNO
        defaultProcessoShouldBeFound("folhasAgravoInterno.in=" + DEFAULT_FOLHAS_AGRAVO_INTERNO + "," + UPDATED_FOLHAS_AGRAVO_INTERNO);

        // Get all the processoList where folhasAgravoInterno equals to UPDATED_FOLHAS_AGRAVO_INTERNO
        defaultProcessoShouldNotBeFound("folhasAgravoInterno.in=" + UPDATED_FOLHAS_AGRAVO_INTERNO);
    }

    @Test
    @Transactional
    void getAllProcessosByFolhasAgravoInternoIsNullOrNotNull() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where folhasAgravoInterno is not null
        defaultProcessoShouldBeFound("folhasAgravoInterno.specified=true");

        // Get all the processoList where folhasAgravoInterno is null
        defaultProcessoShouldNotBeFound("folhasAgravoInterno.specified=false");
    }

    @Test
    @Transactional
    void getAllProcessosByFolhasAgravoInternoContainsSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where folhasAgravoInterno contains DEFAULT_FOLHAS_AGRAVO_INTERNO
        defaultProcessoShouldBeFound("folhasAgravoInterno.contains=" + DEFAULT_FOLHAS_AGRAVO_INTERNO);

        // Get all the processoList where folhasAgravoInterno contains UPDATED_FOLHAS_AGRAVO_INTERNO
        defaultProcessoShouldNotBeFound("folhasAgravoInterno.contains=" + UPDATED_FOLHAS_AGRAVO_INTERNO);
    }

    @Test
    @Transactional
    void getAllProcessosByFolhasAgravoInternoNotContainsSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where folhasAgravoInterno does not contain DEFAULT_FOLHAS_AGRAVO_INTERNO
        defaultProcessoShouldNotBeFound("folhasAgravoInterno.doesNotContain=" + DEFAULT_FOLHAS_AGRAVO_INTERNO);

        // Get all the processoList where folhasAgravoInterno does not contain UPDATED_FOLHAS_AGRAVO_INTERNO
        defaultProcessoShouldBeFound("folhasAgravoInterno.doesNotContain=" + UPDATED_FOLHAS_AGRAVO_INTERNO);
    }

    @Test
    @Transactional
    void getAllProcessosByEmbargoRecursoAgravoIsEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where embargoRecursoAgravo equals to DEFAULT_EMBARGO_RECURSO_AGRAVO
        defaultProcessoShouldBeFound("embargoRecursoAgravo.equals=" + DEFAULT_EMBARGO_RECURSO_AGRAVO);

        // Get all the processoList where embargoRecursoAgravo equals to UPDATED_EMBARGO_RECURSO_AGRAVO
        defaultProcessoShouldNotBeFound("embargoRecursoAgravo.equals=" + UPDATED_EMBARGO_RECURSO_AGRAVO);
    }

    @Test
    @Transactional
    void getAllProcessosByEmbargoRecursoAgravoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where embargoRecursoAgravo not equals to DEFAULT_EMBARGO_RECURSO_AGRAVO
        defaultProcessoShouldNotBeFound("embargoRecursoAgravo.notEquals=" + DEFAULT_EMBARGO_RECURSO_AGRAVO);

        // Get all the processoList where embargoRecursoAgravo not equals to UPDATED_EMBARGO_RECURSO_AGRAVO
        defaultProcessoShouldBeFound("embargoRecursoAgravo.notEquals=" + UPDATED_EMBARGO_RECURSO_AGRAVO);
    }

    @Test
    @Transactional
    void getAllProcessosByEmbargoRecursoAgravoIsInShouldWork() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where embargoRecursoAgravo in DEFAULT_EMBARGO_RECURSO_AGRAVO or UPDATED_EMBARGO_RECURSO_AGRAVO
        defaultProcessoShouldBeFound("embargoRecursoAgravo.in=" + DEFAULT_EMBARGO_RECURSO_AGRAVO + "," + UPDATED_EMBARGO_RECURSO_AGRAVO);

        // Get all the processoList where embargoRecursoAgravo equals to UPDATED_EMBARGO_RECURSO_AGRAVO
        defaultProcessoShouldNotBeFound("embargoRecursoAgravo.in=" + UPDATED_EMBARGO_RECURSO_AGRAVO);
    }

    @Test
    @Transactional
    void getAllProcessosByEmbargoRecursoAgravoIsNullOrNotNull() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where embargoRecursoAgravo is not null
        defaultProcessoShouldBeFound("embargoRecursoAgravo.specified=true");

        // Get all the processoList where embargoRecursoAgravo is null
        defaultProcessoShouldNotBeFound("embargoRecursoAgravo.specified=false");
    }

    @Test
    @Transactional
    void getAllProcessosByRecursoSTJIsEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where recursoSTJ equals to DEFAULT_RECURSO_STJ
        defaultProcessoShouldBeFound("recursoSTJ.equals=" + DEFAULT_RECURSO_STJ);

        // Get all the processoList where recursoSTJ equals to UPDATED_RECURSO_STJ
        defaultProcessoShouldNotBeFound("recursoSTJ.equals=" + UPDATED_RECURSO_STJ);
    }

    @Test
    @Transactional
    void getAllProcessosByRecursoSTJIsNotEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where recursoSTJ not equals to DEFAULT_RECURSO_STJ
        defaultProcessoShouldNotBeFound("recursoSTJ.notEquals=" + DEFAULT_RECURSO_STJ);

        // Get all the processoList where recursoSTJ not equals to UPDATED_RECURSO_STJ
        defaultProcessoShouldBeFound("recursoSTJ.notEquals=" + UPDATED_RECURSO_STJ);
    }

    @Test
    @Transactional
    void getAllProcessosByRecursoSTJIsInShouldWork() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where recursoSTJ in DEFAULT_RECURSO_STJ or UPDATED_RECURSO_STJ
        defaultProcessoShouldBeFound("recursoSTJ.in=" + DEFAULT_RECURSO_STJ + "," + UPDATED_RECURSO_STJ);

        // Get all the processoList where recursoSTJ equals to UPDATED_RECURSO_STJ
        defaultProcessoShouldNotBeFound("recursoSTJ.in=" + UPDATED_RECURSO_STJ);
    }

    @Test
    @Transactional
    void getAllProcessosByRecursoSTJIsNullOrNotNull() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where recursoSTJ is not null
        defaultProcessoShouldBeFound("recursoSTJ.specified=true");

        // Get all the processoList where recursoSTJ is null
        defaultProcessoShouldNotBeFound("recursoSTJ.specified=false");
    }

    @Test
    @Transactional
    void getAllProcessosByLinkRecursoSTJIsEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where linkRecursoSTJ equals to DEFAULT_LINK_RECURSO_STJ
        defaultProcessoShouldBeFound("linkRecursoSTJ.equals=" + DEFAULT_LINK_RECURSO_STJ);

        // Get all the processoList where linkRecursoSTJ equals to UPDATED_LINK_RECURSO_STJ
        defaultProcessoShouldNotBeFound("linkRecursoSTJ.equals=" + UPDATED_LINK_RECURSO_STJ);
    }

    @Test
    @Transactional
    void getAllProcessosByLinkRecursoSTJIsNotEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where linkRecursoSTJ not equals to DEFAULT_LINK_RECURSO_STJ
        defaultProcessoShouldNotBeFound("linkRecursoSTJ.notEquals=" + DEFAULT_LINK_RECURSO_STJ);

        // Get all the processoList where linkRecursoSTJ not equals to UPDATED_LINK_RECURSO_STJ
        defaultProcessoShouldBeFound("linkRecursoSTJ.notEquals=" + UPDATED_LINK_RECURSO_STJ);
    }

    @Test
    @Transactional
    void getAllProcessosByLinkRecursoSTJIsInShouldWork() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where linkRecursoSTJ in DEFAULT_LINK_RECURSO_STJ or UPDATED_LINK_RECURSO_STJ
        defaultProcessoShouldBeFound("linkRecursoSTJ.in=" + DEFAULT_LINK_RECURSO_STJ + "," + UPDATED_LINK_RECURSO_STJ);

        // Get all the processoList where linkRecursoSTJ equals to UPDATED_LINK_RECURSO_STJ
        defaultProcessoShouldNotBeFound("linkRecursoSTJ.in=" + UPDATED_LINK_RECURSO_STJ);
    }

    @Test
    @Transactional
    void getAllProcessosByLinkRecursoSTJIsNullOrNotNull() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where linkRecursoSTJ is not null
        defaultProcessoShouldBeFound("linkRecursoSTJ.specified=true");

        // Get all the processoList where linkRecursoSTJ is null
        defaultProcessoShouldNotBeFound("linkRecursoSTJ.specified=false");
    }

    @Test
    @Transactional
    void getAllProcessosByLinkRecursoSTJContainsSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where linkRecursoSTJ contains DEFAULT_LINK_RECURSO_STJ
        defaultProcessoShouldBeFound("linkRecursoSTJ.contains=" + DEFAULT_LINK_RECURSO_STJ);

        // Get all the processoList where linkRecursoSTJ contains UPDATED_LINK_RECURSO_STJ
        defaultProcessoShouldNotBeFound("linkRecursoSTJ.contains=" + UPDATED_LINK_RECURSO_STJ);
    }

    @Test
    @Transactional
    void getAllProcessosByLinkRecursoSTJNotContainsSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where linkRecursoSTJ does not contain DEFAULT_LINK_RECURSO_STJ
        defaultProcessoShouldNotBeFound("linkRecursoSTJ.doesNotContain=" + DEFAULT_LINK_RECURSO_STJ);

        // Get all the processoList where linkRecursoSTJ does not contain UPDATED_LINK_RECURSO_STJ
        defaultProcessoShouldBeFound("linkRecursoSTJ.doesNotContain=" + UPDATED_LINK_RECURSO_STJ);
    }

    @Test
    @Transactional
    void getAllProcessosByFolhasRecursoSTJIsEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where folhasRecursoSTJ equals to DEFAULT_FOLHAS_RECURSO_STJ
        defaultProcessoShouldBeFound("folhasRecursoSTJ.equals=" + DEFAULT_FOLHAS_RECURSO_STJ);

        // Get all the processoList where folhasRecursoSTJ equals to UPDATED_FOLHAS_RECURSO_STJ
        defaultProcessoShouldNotBeFound("folhasRecursoSTJ.equals=" + UPDATED_FOLHAS_RECURSO_STJ);
    }

    @Test
    @Transactional
    void getAllProcessosByFolhasRecursoSTJIsNotEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where folhasRecursoSTJ not equals to DEFAULT_FOLHAS_RECURSO_STJ
        defaultProcessoShouldNotBeFound("folhasRecursoSTJ.notEquals=" + DEFAULT_FOLHAS_RECURSO_STJ);

        // Get all the processoList where folhasRecursoSTJ not equals to UPDATED_FOLHAS_RECURSO_STJ
        defaultProcessoShouldBeFound("folhasRecursoSTJ.notEquals=" + UPDATED_FOLHAS_RECURSO_STJ);
    }

    @Test
    @Transactional
    void getAllProcessosByFolhasRecursoSTJIsInShouldWork() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where folhasRecursoSTJ in DEFAULT_FOLHAS_RECURSO_STJ or UPDATED_FOLHAS_RECURSO_STJ
        defaultProcessoShouldBeFound("folhasRecursoSTJ.in=" + DEFAULT_FOLHAS_RECURSO_STJ + "," + UPDATED_FOLHAS_RECURSO_STJ);

        // Get all the processoList where folhasRecursoSTJ equals to UPDATED_FOLHAS_RECURSO_STJ
        defaultProcessoShouldNotBeFound("folhasRecursoSTJ.in=" + UPDATED_FOLHAS_RECURSO_STJ);
    }

    @Test
    @Transactional
    void getAllProcessosByFolhasRecursoSTJIsNullOrNotNull() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where folhasRecursoSTJ is not null
        defaultProcessoShouldBeFound("folhasRecursoSTJ.specified=true");

        // Get all the processoList where folhasRecursoSTJ is null
        defaultProcessoShouldNotBeFound("folhasRecursoSTJ.specified=false");
    }

    @Test
    @Transactional
    void getAllProcessosByFolhasRecursoSTJContainsSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where folhasRecursoSTJ contains DEFAULT_FOLHAS_RECURSO_STJ
        defaultProcessoShouldBeFound("folhasRecursoSTJ.contains=" + DEFAULT_FOLHAS_RECURSO_STJ);

        // Get all the processoList where folhasRecursoSTJ contains UPDATED_FOLHAS_RECURSO_STJ
        defaultProcessoShouldNotBeFound("folhasRecursoSTJ.contains=" + UPDATED_FOLHAS_RECURSO_STJ);
    }

    @Test
    @Transactional
    void getAllProcessosByFolhasRecursoSTJNotContainsSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where folhasRecursoSTJ does not contain DEFAULT_FOLHAS_RECURSO_STJ
        defaultProcessoShouldNotBeFound("folhasRecursoSTJ.doesNotContain=" + DEFAULT_FOLHAS_RECURSO_STJ);

        // Get all the processoList where folhasRecursoSTJ does not contain UPDATED_FOLHAS_RECURSO_STJ
        defaultProcessoShouldBeFound("folhasRecursoSTJ.doesNotContain=" + UPDATED_FOLHAS_RECURSO_STJ);
    }

    @Test
    @Transactional
    void getAllProcessosByRecursoSTFIsEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where recursoSTF equals to DEFAULT_RECURSO_STF
        defaultProcessoShouldBeFound("recursoSTF.equals=" + DEFAULT_RECURSO_STF);

        // Get all the processoList where recursoSTF equals to UPDATED_RECURSO_STF
        defaultProcessoShouldNotBeFound("recursoSTF.equals=" + UPDATED_RECURSO_STF);
    }

    @Test
    @Transactional
    void getAllProcessosByRecursoSTFIsNotEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where recursoSTF not equals to DEFAULT_RECURSO_STF
        defaultProcessoShouldNotBeFound("recursoSTF.notEquals=" + DEFAULT_RECURSO_STF);

        // Get all the processoList where recursoSTF not equals to UPDATED_RECURSO_STF
        defaultProcessoShouldBeFound("recursoSTF.notEquals=" + UPDATED_RECURSO_STF);
    }

    @Test
    @Transactional
    void getAllProcessosByRecursoSTFIsInShouldWork() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where recursoSTF in DEFAULT_RECURSO_STF or UPDATED_RECURSO_STF
        defaultProcessoShouldBeFound("recursoSTF.in=" + DEFAULT_RECURSO_STF + "," + UPDATED_RECURSO_STF);

        // Get all the processoList where recursoSTF equals to UPDATED_RECURSO_STF
        defaultProcessoShouldNotBeFound("recursoSTF.in=" + UPDATED_RECURSO_STF);
    }

    @Test
    @Transactional
    void getAllProcessosByRecursoSTFIsNullOrNotNull() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where recursoSTF is not null
        defaultProcessoShouldBeFound("recursoSTF.specified=true");

        // Get all the processoList where recursoSTF is null
        defaultProcessoShouldNotBeFound("recursoSTF.specified=false");
    }

    @Test
    @Transactional
    void getAllProcessosByLinkRecursoSTFIsEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where linkRecursoSTF equals to DEFAULT_LINK_RECURSO_STF
        defaultProcessoShouldBeFound("linkRecursoSTF.equals=" + DEFAULT_LINK_RECURSO_STF);

        // Get all the processoList where linkRecursoSTF equals to UPDATED_LINK_RECURSO_STF
        defaultProcessoShouldNotBeFound("linkRecursoSTF.equals=" + UPDATED_LINK_RECURSO_STF);
    }

    @Test
    @Transactional
    void getAllProcessosByLinkRecursoSTFIsNotEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where linkRecursoSTF not equals to DEFAULT_LINK_RECURSO_STF
        defaultProcessoShouldNotBeFound("linkRecursoSTF.notEquals=" + DEFAULT_LINK_RECURSO_STF);

        // Get all the processoList where linkRecursoSTF not equals to UPDATED_LINK_RECURSO_STF
        defaultProcessoShouldBeFound("linkRecursoSTF.notEquals=" + UPDATED_LINK_RECURSO_STF);
    }

    @Test
    @Transactional
    void getAllProcessosByLinkRecursoSTFIsInShouldWork() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where linkRecursoSTF in DEFAULT_LINK_RECURSO_STF or UPDATED_LINK_RECURSO_STF
        defaultProcessoShouldBeFound("linkRecursoSTF.in=" + DEFAULT_LINK_RECURSO_STF + "," + UPDATED_LINK_RECURSO_STF);

        // Get all the processoList where linkRecursoSTF equals to UPDATED_LINK_RECURSO_STF
        defaultProcessoShouldNotBeFound("linkRecursoSTF.in=" + UPDATED_LINK_RECURSO_STF);
    }

    @Test
    @Transactional
    void getAllProcessosByLinkRecursoSTFIsNullOrNotNull() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where linkRecursoSTF is not null
        defaultProcessoShouldBeFound("linkRecursoSTF.specified=true");

        // Get all the processoList where linkRecursoSTF is null
        defaultProcessoShouldNotBeFound("linkRecursoSTF.specified=false");
    }

    @Test
    @Transactional
    void getAllProcessosByLinkRecursoSTFContainsSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where linkRecursoSTF contains DEFAULT_LINK_RECURSO_STF
        defaultProcessoShouldBeFound("linkRecursoSTF.contains=" + DEFAULT_LINK_RECURSO_STF);

        // Get all the processoList where linkRecursoSTF contains UPDATED_LINK_RECURSO_STF
        defaultProcessoShouldNotBeFound("linkRecursoSTF.contains=" + UPDATED_LINK_RECURSO_STF);
    }

    @Test
    @Transactional
    void getAllProcessosByLinkRecursoSTFNotContainsSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where linkRecursoSTF does not contain DEFAULT_LINK_RECURSO_STF
        defaultProcessoShouldNotBeFound("linkRecursoSTF.doesNotContain=" + DEFAULT_LINK_RECURSO_STF);

        // Get all the processoList where linkRecursoSTF does not contain UPDATED_LINK_RECURSO_STF
        defaultProcessoShouldBeFound("linkRecursoSTF.doesNotContain=" + UPDATED_LINK_RECURSO_STF);
    }

    @Test
    @Transactional
    void getAllProcessosByFolhasRecursoSTFIsEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where folhasRecursoSTF equals to DEFAULT_FOLHAS_RECURSO_STF
        defaultProcessoShouldBeFound("folhasRecursoSTF.equals=" + DEFAULT_FOLHAS_RECURSO_STF);

        // Get all the processoList where folhasRecursoSTF equals to UPDATED_FOLHAS_RECURSO_STF
        defaultProcessoShouldNotBeFound("folhasRecursoSTF.equals=" + UPDATED_FOLHAS_RECURSO_STF);
    }

    @Test
    @Transactional
    void getAllProcessosByFolhasRecursoSTFIsNotEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where folhasRecursoSTF not equals to DEFAULT_FOLHAS_RECURSO_STF
        defaultProcessoShouldNotBeFound("folhasRecursoSTF.notEquals=" + DEFAULT_FOLHAS_RECURSO_STF);

        // Get all the processoList where folhasRecursoSTF not equals to UPDATED_FOLHAS_RECURSO_STF
        defaultProcessoShouldBeFound("folhasRecursoSTF.notEquals=" + UPDATED_FOLHAS_RECURSO_STF);
    }

    @Test
    @Transactional
    void getAllProcessosByFolhasRecursoSTFIsInShouldWork() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where folhasRecursoSTF in DEFAULT_FOLHAS_RECURSO_STF or UPDATED_FOLHAS_RECURSO_STF
        defaultProcessoShouldBeFound("folhasRecursoSTF.in=" + DEFAULT_FOLHAS_RECURSO_STF + "," + UPDATED_FOLHAS_RECURSO_STF);

        // Get all the processoList where folhasRecursoSTF equals to UPDATED_FOLHAS_RECURSO_STF
        defaultProcessoShouldNotBeFound("folhasRecursoSTF.in=" + UPDATED_FOLHAS_RECURSO_STF);
    }

    @Test
    @Transactional
    void getAllProcessosByFolhasRecursoSTFIsNullOrNotNull() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where folhasRecursoSTF is not null
        defaultProcessoShouldBeFound("folhasRecursoSTF.specified=true");

        // Get all the processoList where folhasRecursoSTF is null
        defaultProcessoShouldNotBeFound("folhasRecursoSTF.specified=false");
    }

    @Test
    @Transactional
    void getAllProcessosByFolhasRecursoSTFContainsSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where folhasRecursoSTF contains DEFAULT_FOLHAS_RECURSO_STF
        defaultProcessoShouldBeFound("folhasRecursoSTF.contains=" + DEFAULT_FOLHAS_RECURSO_STF);

        // Get all the processoList where folhasRecursoSTF contains UPDATED_FOLHAS_RECURSO_STF
        defaultProcessoShouldNotBeFound("folhasRecursoSTF.contains=" + UPDATED_FOLHAS_RECURSO_STF);
    }

    @Test
    @Transactional
    void getAllProcessosByFolhasRecursoSTFNotContainsSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where folhasRecursoSTF does not contain DEFAULT_FOLHAS_RECURSO_STF
        defaultProcessoShouldNotBeFound("folhasRecursoSTF.doesNotContain=" + DEFAULT_FOLHAS_RECURSO_STF);

        // Get all the processoList where folhasRecursoSTF does not contain UPDATED_FOLHAS_RECURSO_STF
        defaultProcessoShouldBeFound("folhasRecursoSTF.doesNotContain=" + UPDATED_FOLHAS_RECURSO_STF);
    }

    @Test
    @Transactional
    void getAllProcessosByFolhasMemorialMPFIsEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where folhasMemorialMPF equals to DEFAULT_FOLHAS_MEMORIAL_MPF
        defaultProcessoShouldBeFound("folhasMemorialMPF.equals=" + DEFAULT_FOLHAS_MEMORIAL_MPF);

        // Get all the processoList where folhasMemorialMPF equals to UPDATED_FOLHAS_MEMORIAL_MPF
        defaultProcessoShouldNotBeFound("folhasMemorialMPF.equals=" + UPDATED_FOLHAS_MEMORIAL_MPF);
    }

    @Test
    @Transactional
    void getAllProcessosByFolhasMemorialMPFIsNotEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where folhasMemorialMPF not equals to DEFAULT_FOLHAS_MEMORIAL_MPF
        defaultProcessoShouldNotBeFound("folhasMemorialMPF.notEquals=" + DEFAULT_FOLHAS_MEMORIAL_MPF);

        // Get all the processoList where folhasMemorialMPF not equals to UPDATED_FOLHAS_MEMORIAL_MPF
        defaultProcessoShouldBeFound("folhasMemorialMPF.notEquals=" + UPDATED_FOLHAS_MEMORIAL_MPF);
    }

    @Test
    @Transactional
    void getAllProcessosByFolhasMemorialMPFIsInShouldWork() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where folhasMemorialMPF in DEFAULT_FOLHAS_MEMORIAL_MPF or UPDATED_FOLHAS_MEMORIAL_MPF
        defaultProcessoShouldBeFound("folhasMemorialMPF.in=" + DEFAULT_FOLHAS_MEMORIAL_MPF + "," + UPDATED_FOLHAS_MEMORIAL_MPF);

        // Get all the processoList where folhasMemorialMPF equals to UPDATED_FOLHAS_MEMORIAL_MPF
        defaultProcessoShouldNotBeFound("folhasMemorialMPF.in=" + UPDATED_FOLHAS_MEMORIAL_MPF);
    }

    @Test
    @Transactional
    void getAllProcessosByFolhasMemorialMPFIsNullOrNotNull() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where folhasMemorialMPF is not null
        defaultProcessoShouldBeFound("folhasMemorialMPF.specified=true");

        // Get all the processoList where folhasMemorialMPF is null
        defaultProcessoShouldNotBeFound("folhasMemorialMPF.specified=false");
    }

    @Test
    @Transactional
    void getAllProcessosByFolhasMemorialMPFContainsSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where folhasMemorialMPF contains DEFAULT_FOLHAS_MEMORIAL_MPF
        defaultProcessoShouldBeFound("folhasMemorialMPF.contains=" + DEFAULT_FOLHAS_MEMORIAL_MPF);

        // Get all the processoList where folhasMemorialMPF contains UPDATED_FOLHAS_MEMORIAL_MPF
        defaultProcessoShouldNotBeFound("folhasMemorialMPF.contains=" + UPDATED_FOLHAS_MEMORIAL_MPF);
    }

    @Test
    @Transactional
    void getAllProcessosByFolhasMemorialMPFNotContainsSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where folhasMemorialMPF does not contain DEFAULT_FOLHAS_MEMORIAL_MPF
        defaultProcessoShouldNotBeFound("folhasMemorialMPF.doesNotContain=" + DEFAULT_FOLHAS_MEMORIAL_MPF);

        // Get all the processoList where folhasMemorialMPF does not contain UPDATED_FOLHAS_MEMORIAL_MPF
        defaultProcessoShouldBeFound("folhasMemorialMPF.doesNotContain=" + UPDATED_FOLHAS_MEMORIAL_MPF);
    }

    @Test
    @Transactional
    void getAllProcessosByExecusaoProvisoriaIsEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where execusaoProvisoria equals to DEFAULT_EXECUSAO_PROVISORIA
        defaultProcessoShouldBeFound("execusaoProvisoria.equals=" + DEFAULT_EXECUSAO_PROVISORIA);

        // Get all the processoList where execusaoProvisoria equals to UPDATED_EXECUSAO_PROVISORIA
        defaultProcessoShouldNotBeFound("execusaoProvisoria.equals=" + UPDATED_EXECUSAO_PROVISORIA);
    }

    @Test
    @Transactional
    void getAllProcessosByExecusaoProvisoriaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where execusaoProvisoria not equals to DEFAULT_EXECUSAO_PROVISORIA
        defaultProcessoShouldNotBeFound("execusaoProvisoria.notEquals=" + DEFAULT_EXECUSAO_PROVISORIA);

        // Get all the processoList where execusaoProvisoria not equals to UPDATED_EXECUSAO_PROVISORIA
        defaultProcessoShouldBeFound("execusaoProvisoria.notEquals=" + UPDATED_EXECUSAO_PROVISORIA);
    }

    @Test
    @Transactional
    void getAllProcessosByExecusaoProvisoriaIsInShouldWork() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where execusaoProvisoria in DEFAULT_EXECUSAO_PROVISORIA or UPDATED_EXECUSAO_PROVISORIA
        defaultProcessoShouldBeFound("execusaoProvisoria.in=" + DEFAULT_EXECUSAO_PROVISORIA + "," + UPDATED_EXECUSAO_PROVISORIA);

        // Get all the processoList where execusaoProvisoria equals to UPDATED_EXECUSAO_PROVISORIA
        defaultProcessoShouldNotBeFound("execusaoProvisoria.in=" + UPDATED_EXECUSAO_PROVISORIA);
    }

    @Test
    @Transactional
    void getAllProcessosByExecusaoProvisoriaIsNullOrNotNull() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where execusaoProvisoria is not null
        defaultProcessoShouldBeFound("execusaoProvisoria.specified=true");

        // Get all the processoList where execusaoProvisoria is null
        defaultProcessoShouldNotBeFound("execusaoProvisoria.specified=false");
    }

    @Test
    @Transactional
    void getAllProcessosByNumeracaoExecusaoProvisoriaIsEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where numeracaoExecusaoProvisoria equals to DEFAULT_NUMERACAO_EXECUSAO_PROVISORIA
        defaultProcessoShouldBeFound("numeracaoExecusaoProvisoria.equals=" + DEFAULT_NUMERACAO_EXECUSAO_PROVISORIA);

        // Get all the processoList where numeracaoExecusaoProvisoria equals to UPDATED_NUMERACAO_EXECUSAO_PROVISORIA
        defaultProcessoShouldNotBeFound("numeracaoExecusaoProvisoria.equals=" + UPDATED_NUMERACAO_EXECUSAO_PROVISORIA);
    }

    @Test
    @Transactional
    void getAllProcessosByNumeracaoExecusaoProvisoriaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where numeracaoExecusaoProvisoria not equals to DEFAULT_NUMERACAO_EXECUSAO_PROVISORIA
        defaultProcessoShouldNotBeFound("numeracaoExecusaoProvisoria.notEquals=" + DEFAULT_NUMERACAO_EXECUSAO_PROVISORIA);

        // Get all the processoList where numeracaoExecusaoProvisoria not equals to UPDATED_NUMERACAO_EXECUSAO_PROVISORIA
        defaultProcessoShouldBeFound("numeracaoExecusaoProvisoria.notEquals=" + UPDATED_NUMERACAO_EXECUSAO_PROVISORIA);
    }

    @Test
    @Transactional
    void getAllProcessosByNumeracaoExecusaoProvisoriaIsInShouldWork() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where numeracaoExecusaoProvisoria in DEFAULT_NUMERACAO_EXECUSAO_PROVISORIA or UPDATED_NUMERACAO_EXECUSAO_PROVISORIA
        defaultProcessoShouldBeFound(
            "numeracaoExecusaoProvisoria.in=" + DEFAULT_NUMERACAO_EXECUSAO_PROVISORIA + "," + UPDATED_NUMERACAO_EXECUSAO_PROVISORIA
        );

        // Get all the processoList where numeracaoExecusaoProvisoria equals to UPDATED_NUMERACAO_EXECUSAO_PROVISORIA
        defaultProcessoShouldNotBeFound("numeracaoExecusaoProvisoria.in=" + UPDATED_NUMERACAO_EXECUSAO_PROVISORIA);
    }

    @Test
    @Transactional
    void getAllProcessosByNumeracaoExecusaoProvisoriaIsNullOrNotNull() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where numeracaoExecusaoProvisoria is not null
        defaultProcessoShouldBeFound("numeracaoExecusaoProvisoria.specified=true");

        // Get all the processoList where numeracaoExecusaoProvisoria is null
        defaultProcessoShouldNotBeFound("numeracaoExecusaoProvisoria.specified=false");
    }

    @Test
    @Transactional
    void getAllProcessosByNumeracaoExecusaoProvisoriaContainsSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where numeracaoExecusaoProvisoria contains DEFAULT_NUMERACAO_EXECUSAO_PROVISORIA
        defaultProcessoShouldBeFound("numeracaoExecusaoProvisoria.contains=" + DEFAULT_NUMERACAO_EXECUSAO_PROVISORIA);

        // Get all the processoList where numeracaoExecusaoProvisoria contains UPDATED_NUMERACAO_EXECUSAO_PROVISORIA
        defaultProcessoShouldNotBeFound("numeracaoExecusaoProvisoria.contains=" + UPDATED_NUMERACAO_EXECUSAO_PROVISORIA);
    }

    @Test
    @Transactional
    void getAllProcessosByNumeracaoExecusaoProvisoriaNotContainsSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where numeracaoExecusaoProvisoria does not contain DEFAULT_NUMERACAO_EXECUSAO_PROVISORIA
        defaultProcessoShouldNotBeFound("numeracaoExecusaoProvisoria.doesNotContain=" + DEFAULT_NUMERACAO_EXECUSAO_PROVISORIA);

        // Get all the processoList where numeracaoExecusaoProvisoria does not contain UPDATED_NUMERACAO_EXECUSAO_PROVISORIA
        defaultProcessoShouldBeFound("numeracaoExecusaoProvisoria.doesNotContain=" + UPDATED_NUMERACAO_EXECUSAO_PROVISORIA);
    }

    @Test
    @Transactional
    void getAllProcessosByEnvolveEmpreendimentoIsEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where envolveEmpreendimento equals to DEFAULT_ENVOLVE_EMPREENDIMENTO
        defaultProcessoShouldBeFound("envolveEmpreendimento.equals=" + DEFAULT_ENVOLVE_EMPREENDIMENTO);

        // Get all the processoList where envolveEmpreendimento equals to UPDATED_ENVOLVE_EMPREENDIMENTO
        defaultProcessoShouldNotBeFound("envolveEmpreendimento.equals=" + UPDATED_ENVOLVE_EMPREENDIMENTO);
    }

    @Test
    @Transactional
    void getAllProcessosByEnvolveEmpreendimentoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where envolveEmpreendimento not equals to DEFAULT_ENVOLVE_EMPREENDIMENTO
        defaultProcessoShouldNotBeFound("envolveEmpreendimento.notEquals=" + DEFAULT_ENVOLVE_EMPREENDIMENTO);

        // Get all the processoList where envolveEmpreendimento not equals to UPDATED_ENVOLVE_EMPREENDIMENTO
        defaultProcessoShouldBeFound("envolveEmpreendimento.notEquals=" + UPDATED_ENVOLVE_EMPREENDIMENTO);
    }

    @Test
    @Transactional
    void getAllProcessosByEnvolveEmpreendimentoIsInShouldWork() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where envolveEmpreendimento in DEFAULT_ENVOLVE_EMPREENDIMENTO or UPDATED_ENVOLVE_EMPREENDIMENTO
        defaultProcessoShouldBeFound("envolveEmpreendimento.in=" + DEFAULT_ENVOLVE_EMPREENDIMENTO + "," + UPDATED_ENVOLVE_EMPREENDIMENTO);

        // Get all the processoList where envolveEmpreendimento equals to UPDATED_ENVOLVE_EMPREENDIMENTO
        defaultProcessoShouldNotBeFound("envolveEmpreendimento.in=" + UPDATED_ENVOLVE_EMPREENDIMENTO);
    }

    @Test
    @Transactional
    void getAllProcessosByEnvolveEmpreendimentoIsNullOrNotNull() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where envolveEmpreendimento is not null
        defaultProcessoShouldBeFound("envolveEmpreendimento.specified=true");

        // Get all the processoList where envolveEmpreendimento is null
        defaultProcessoShouldNotBeFound("envolveEmpreendimento.specified=false");
    }

    @Test
    @Transactional
    void getAllProcessosByEnvolveExploracaoIlegalIsEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where envolveExploracaoIlegal equals to DEFAULT_ENVOLVE_EXPLORACAO_ILEGAL
        defaultProcessoShouldBeFound("envolveExploracaoIlegal.equals=" + DEFAULT_ENVOLVE_EXPLORACAO_ILEGAL);

        // Get all the processoList where envolveExploracaoIlegal equals to UPDATED_ENVOLVE_EXPLORACAO_ILEGAL
        defaultProcessoShouldNotBeFound("envolveExploracaoIlegal.equals=" + UPDATED_ENVOLVE_EXPLORACAO_ILEGAL);
    }

    @Test
    @Transactional
    void getAllProcessosByEnvolveExploracaoIlegalIsNotEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where envolveExploracaoIlegal not equals to DEFAULT_ENVOLVE_EXPLORACAO_ILEGAL
        defaultProcessoShouldNotBeFound("envolveExploracaoIlegal.notEquals=" + DEFAULT_ENVOLVE_EXPLORACAO_ILEGAL);

        // Get all the processoList where envolveExploracaoIlegal not equals to UPDATED_ENVOLVE_EXPLORACAO_ILEGAL
        defaultProcessoShouldBeFound("envolveExploracaoIlegal.notEquals=" + UPDATED_ENVOLVE_EXPLORACAO_ILEGAL);
    }

    @Test
    @Transactional
    void getAllProcessosByEnvolveExploracaoIlegalIsInShouldWork() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where envolveExploracaoIlegal in DEFAULT_ENVOLVE_EXPLORACAO_ILEGAL or UPDATED_ENVOLVE_EXPLORACAO_ILEGAL
        defaultProcessoShouldBeFound(
            "envolveExploracaoIlegal.in=" + DEFAULT_ENVOLVE_EXPLORACAO_ILEGAL + "," + UPDATED_ENVOLVE_EXPLORACAO_ILEGAL
        );

        // Get all the processoList where envolveExploracaoIlegal equals to UPDATED_ENVOLVE_EXPLORACAO_ILEGAL
        defaultProcessoShouldNotBeFound("envolveExploracaoIlegal.in=" + UPDATED_ENVOLVE_EXPLORACAO_ILEGAL);
    }

    @Test
    @Transactional
    void getAllProcessosByEnvolveExploracaoIlegalIsNullOrNotNull() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where envolveExploracaoIlegal is not null
        defaultProcessoShouldBeFound("envolveExploracaoIlegal.specified=true");

        // Get all the processoList where envolveExploracaoIlegal is null
        defaultProcessoShouldNotBeFound("envolveExploracaoIlegal.specified=false");
    }

    @Test
    @Transactional
    void getAllProcessosByEnvolveTerraQuilombolaIsEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where envolveTerraQuilombola equals to DEFAULT_ENVOLVE_TERRA_QUILOMBOLA
        defaultProcessoShouldBeFound("envolveTerraQuilombola.equals=" + DEFAULT_ENVOLVE_TERRA_QUILOMBOLA);

        // Get all the processoList where envolveTerraQuilombola equals to UPDATED_ENVOLVE_TERRA_QUILOMBOLA
        defaultProcessoShouldNotBeFound("envolveTerraQuilombola.equals=" + UPDATED_ENVOLVE_TERRA_QUILOMBOLA);
    }

    @Test
    @Transactional
    void getAllProcessosByEnvolveTerraQuilombolaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where envolveTerraQuilombola not equals to DEFAULT_ENVOLVE_TERRA_QUILOMBOLA
        defaultProcessoShouldNotBeFound("envolveTerraQuilombola.notEquals=" + DEFAULT_ENVOLVE_TERRA_QUILOMBOLA);

        // Get all the processoList where envolveTerraQuilombola not equals to UPDATED_ENVOLVE_TERRA_QUILOMBOLA
        defaultProcessoShouldBeFound("envolveTerraQuilombola.notEquals=" + UPDATED_ENVOLVE_TERRA_QUILOMBOLA);
    }

    @Test
    @Transactional
    void getAllProcessosByEnvolveTerraQuilombolaIsInShouldWork() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where envolveTerraQuilombola in DEFAULT_ENVOLVE_TERRA_QUILOMBOLA or UPDATED_ENVOLVE_TERRA_QUILOMBOLA
        defaultProcessoShouldBeFound(
            "envolveTerraQuilombola.in=" + DEFAULT_ENVOLVE_TERRA_QUILOMBOLA + "," + UPDATED_ENVOLVE_TERRA_QUILOMBOLA
        );

        // Get all the processoList where envolveTerraQuilombola equals to UPDATED_ENVOLVE_TERRA_QUILOMBOLA
        defaultProcessoShouldNotBeFound("envolveTerraQuilombola.in=" + UPDATED_ENVOLVE_TERRA_QUILOMBOLA);
    }

    @Test
    @Transactional
    void getAllProcessosByEnvolveTerraQuilombolaIsNullOrNotNull() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where envolveTerraQuilombola is not null
        defaultProcessoShouldBeFound("envolveTerraQuilombola.specified=true");

        // Get all the processoList where envolveTerraQuilombola is null
        defaultProcessoShouldNotBeFound("envolveTerraQuilombola.specified=false");
    }

    @Test
    @Transactional
    void getAllProcessosByEnvolveTerraComunidadeTradicionalIsEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where envolveTerraComunidadeTradicional equals to DEFAULT_ENVOLVE_TERRA_COMUNIDADE_TRADICIONAL
        defaultProcessoShouldBeFound("envolveTerraComunidadeTradicional.equals=" + DEFAULT_ENVOLVE_TERRA_COMUNIDADE_TRADICIONAL);

        // Get all the processoList where envolveTerraComunidadeTradicional equals to UPDATED_ENVOLVE_TERRA_COMUNIDADE_TRADICIONAL
        defaultProcessoShouldNotBeFound("envolveTerraComunidadeTradicional.equals=" + UPDATED_ENVOLVE_TERRA_COMUNIDADE_TRADICIONAL);
    }

    @Test
    @Transactional
    void getAllProcessosByEnvolveTerraComunidadeTradicionalIsNotEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where envolveTerraComunidadeTradicional not equals to DEFAULT_ENVOLVE_TERRA_COMUNIDADE_TRADICIONAL
        defaultProcessoShouldNotBeFound("envolveTerraComunidadeTradicional.notEquals=" + DEFAULT_ENVOLVE_TERRA_COMUNIDADE_TRADICIONAL);

        // Get all the processoList where envolveTerraComunidadeTradicional not equals to UPDATED_ENVOLVE_TERRA_COMUNIDADE_TRADICIONAL
        defaultProcessoShouldBeFound("envolveTerraComunidadeTradicional.notEquals=" + UPDATED_ENVOLVE_TERRA_COMUNIDADE_TRADICIONAL);
    }

    @Test
    @Transactional
    void getAllProcessosByEnvolveTerraComunidadeTradicionalIsInShouldWork() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where envolveTerraComunidadeTradicional in DEFAULT_ENVOLVE_TERRA_COMUNIDADE_TRADICIONAL or UPDATED_ENVOLVE_TERRA_COMUNIDADE_TRADICIONAL
        defaultProcessoShouldBeFound(
            "envolveTerraComunidadeTradicional.in=" +
            DEFAULT_ENVOLVE_TERRA_COMUNIDADE_TRADICIONAL +
            "," +
            UPDATED_ENVOLVE_TERRA_COMUNIDADE_TRADICIONAL
        );

        // Get all the processoList where envolveTerraComunidadeTradicional equals to UPDATED_ENVOLVE_TERRA_COMUNIDADE_TRADICIONAL
        defaultProcessoShouldNotBeFound("envolveTerraComunidadeTradicional.in=" + UPDATED_ENVOLVE_TERRA_COMUNIDADE_TRADICIONAL);
    }

    @Test
    @Transactional
    void getAllProcessosByEnvolveTerraComunidadeTradicionalIsNullOrNotNull() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where envolveTerraComunidadeTradicional is not null
        defaultProcessoShouldBeFound("envolveTerraComunidadeTradicional.specified=true");

        // Get all the processoList where envolveTerraComunidadeTradicional is null
        defaultProcessoShouldNotBeFound("envolveTerraComunidadeTradicional.specified=false");
    }

    @Test
    @Transactional
    void getAllProcessosByEnvolveTerraIndigenaIsEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where envolveTerraIndigena equals to DEFAULT_ENVOLVE_TERRA_INDIGENA
        defaultProcessoShouldBeFound("envolveTerraIndigena.equals=" + DEFAULT_ENVOLVE_TERRA_INDIGENA);

        // Get all the processoList where envolveTerraIndigena equals to UPDATED_ENVOLVE_TERRA_INDIGENA
        defaultProcessoShouldNotBeFound("envolveTerraIndigena.equals=" + UPDATED_ENVOLVE_TERRA_INDIGENA);
    }

    @Test
    @Transactional
    void getAllProcessosByEnvolveTerraIndigenaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where envolveTerraIndigena not equals to DEFAULT_ENVOLVE_TERRA_INDIGENA
        defaultProcessoShouldNotBeFound("envolveTerraIndigena.notEquals=" + DEFAULT_ENVOLVE_TERRA_INDIGENA);

        // Get all the processoList where envolveTerraIndigena not equals to UPDATED_ENVOLVE_TERRA_INDIGENA
        defaultProcessoShouldBeFound("envolveTerraIndigena.notEquals=" + UPDATED_ENVOLVE_TERRA_INDIGENA);
    }

    @Test
    @Transactional
    void getAllProcessosByEnvolveTerraIndigenaIsInShouldWork() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where envolveTerraIndigena in DEFAULT_ENVOLVE_TERRA_INDIGENA or UPDATED_ENVOLVE_TERRA_INDIGENA
        defaultProcessoShouldBeFound("envolveTerraIndigena.in=" + DEFAULT_ENVOLVE_TERRA_INDIGENA + "," + UPDATED_ENVOLVE_TERRA_INDIGENA);

        // Get all the processoList where envolveTerraIndigena equals to UPDATED_ENVOLVE_TERRA_INDIGENA
        defaultProcessoShouldNotBeFound("envolveTerraIndigena.in=" + UPDATED_ENVOLVE_TERRA_INDIGENA);
    }

    @Test
    @Transactional
    void getAllProcessosByEnvolveTerraIndigenaIsNullOrNotNull() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where envolveTerraIndigena is not null
        defaultProcessoShouldBeFound("envolveTerraIndigena.specified=true");

        // Get all the processoList where envolveTerraIndigena is null
        defaultProcessoShouldNotBeFound("envolveTerraIndigena.specified=false");
    }

    @Test
    @Transactional
    void getAllProcessosByTamanhoAreaIsEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where tamanhoArea equals to DEFAULT_TAMANHO_AREA
        defaultProcessoShouldBeFound("tamanhoArea.equals=" + DEFAULT_TAMANHO_AREA);

        // Get all the processoList where tamanhoArea equals to UPDATED_TAMANHO_AREA
        defaultProcessoShouldNotBeFound("tamanhoArea.equals=" + UPDATED_TAMANHO_AREA);
    }

    @Test
    @Transactional
    void getAllProcessosByTamanhoAreaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where tamanhoArea not equals to DEFAULT_TAMANHO_AREA
        defaultProcessoShouldNotBeFound("tamanhoArea.notEquals=" + DEFAULT_TAMANHO_AREA);

        // Get all the processoList where tamanhoArea not equals to UPDATED_TAMANHO_AREA
        defaultProcessoShouldBeFound("tamanhoArea.notEquals=" + UPDATED_TAMANHO_AREA);
    }

    @Test
    @Transactional
    void getAllProcessosByTamanhoAreaIsInShouldWork() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where tamanhoArea in DEFAULT_TAMANHO_AREA or UPDATED_TAMANHO_AREA
        defaultProcessoShouldBeFound("tamanhoArea.in=" + DEFAULT_TAMANHO_AREA + "," + UPDATED_TAMANHO_AREA);

        // Get all the processoList where tamanhoArea equals to UPDATED_TAMANHO_AREA
        defaultProcessoShouldNotBeFound("tamanhoArea.in=" + UPDATED_TAMANHO_AREA);
    }

    @Test
    @Transactional
    void getAllProcessosByTamanhoAreaIsNullOrNotNull() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where tamanhoArea is not null
        defaultProcessoShouldBeFound("tamanhoArea.specified=true");

        // Get all the processoList where tamanhoArea is null
        defaultProcessoShouldNotBeFound("tamanhoArea.specified=false");
    }

    @Test
    @Transactional
    void getAllProcessosByTamanhoAreaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where tamanhoArea is greater than or equal to DEFAULT_TAMANHO_AREA
        defaultProcessoShouldBeFound("tamanhoArea.greaterThanOrEqual=" + DEFAULT_TAMANHO_AREA);

        // Get all the processoList where tamanhoArea is greater than or equal to UPDATED_TAMANHO_AREA
        defaultProcessoShouldNotBeFound("tamanhoArea.greaterThanOrEqual=" + UPDATED_TAMANHO_AREA);
    }

    @Test
    @Transactional
    void getAllProcessosByTamanhoAreaIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where tamanhoArea is less than or equal to DEFAULT_TAMANHO_AREA
        defaultProcessoShouldBeFound("tamanhoArea.lessThanOrEqual=" + DEFAULT_TAMANHO_AREA);

        // Get all the processoList where tamanhoArea is less than or equal to SMALLER_TAMANHO_AREA
        defaultProcessoShouldNotBeFound("tamanhoArea.lessThanOrEqual=" + SMALLER_TAMANHO_AREA);
    }

    @Test
    @Transactional
    void getAllProcessosByTamanhoAreaIsLessThanSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where tamanhoArea is less than DEFAULT_TAMANHO_AREA
        defaultProcessoShouldNotBeFound("tamanhoArea.lessThan=" + DEFAULT_TAMANHO_AREA);

        // Get all the processoList where tamanhoArea is less than UPDATED_TAMANHO_AREA
        defaultProcessoShouldBeFound("tamanhoArea.lessThan=" + UPDATED_TAMANHO_AREA);
    }

    @Test
    @Transactional
    void getAllProcessosByTamanhoAreaIsGreaterThanSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where tamanhoArea is greater than DEFAULT_TAMANHO_AREA
        defaultProcessoShouldNotBeFound("tamanhoArea.greaterThan=" + DEFAULT_TAMANHO_AREA);

        // Get all the processoList where tamanhoArea is greater than SMALLER_TAMANHO_AREA
        defaultProcessoShouldBeFound("tamanhoArea.greaterThan=" + SMALLER_TAMANHO_AREA);
    }

    @Test
    @Transactional
    void getAllProcessosByValorAreaIsEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where valorArea equals to DEFAULT_VALOR_AREA
        defaultProcessoShouldBeFound("valorArea.equals=" + DEFAULT_VALOR_AREA);

        // Get all the processoList where valorArea equals to UPDATED_VALOR_AREA
        defaultProcessoShouldNotBeFound("valorArea.equals=" + UPDATED_VALOR_AREA);
    }

    @Test
    @Transactional
    void getAllProcessosByValorAreaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where valorArea not equals to DEFAULT_VALOR_AREA
        defaultProcessoShouldNotBeFound("valorArea.notEquals=" + DEFAULT_VALOR_AREA);

        // Get all the processoList where valorArea not equals to UPDATED_VALOR_AREA
        defaultProcessoShouldBeFound("valorArea.notEquals=" + UPDATED_VALOR_AREA);
    }

    @Test
    @Transactional
    void getAllProcessosByValorAreaIsInShouldWork() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where valorArea in DEFAULT_VALOR_AREA or UPDATED_VALOR_AREA
        defaultProcessoShouldBeFound("valorArea.in=" + DEFAULT_VALOR_AREA + "," + UPDATED_VALOR_AREA);

        // Get all the processoList where valorArea equals to UPDATED_VALOR_AREA
        defaultProcessoShouldNotBeFound("valorArea.in=" + UPDATED_VALOR_AREA);
    }

    @Test
    @Transactional
    void getAllProcessosByValorAreaIsNullOrNotNull() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where valorArea is not null
        defaultProcessoShouldBeFound("valorArea.specified=true");

        // Get all the processoList where valorArea is null
        defaultProcessoShouldNotBeFound("valorArea.specified=false");
    }

    @Test
    @Transactional
    void getAllProcessosByValorAreaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where valorArea is greater than or equal to DEFAULT_VALOR_AREA
        defaultProcessoShouldBeFound("valorArea.greaterThanOrEqual=" + DEFAULT_VALOR_AREA);

        // Get all the processoList where valorArea is greater than or equal to UPDATED_VALOR_AREA
        defaultProcessoShouldNotBeFound("valorArea.greaterThanOrEqual=" + UPDATED_VALOR_AREA);
    }

    @Test
    @Transactional
    void getAllProcessosByValorAreaIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where valorArea is less than or equal to DEFAULT_VALOR_AREA
        defaultProcessoShouldBeFound("valorArea.lessThanOrEqual=" + DEFAULT_VALOR_AREA);

        // Get all the processoList where valorArea is less than or equal to SMALLER_VALOR_AREA
        defaultProcessoShouldNotBeFound("valorArea.lessThanOrEqual=" + SMALLER_VALOR_AREA);
    }

    @Test
    @Transactional
    void getAllProcessosByValorAreaIsLessThanSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where valorArea is less than DEFAULT_VALOR_AREA
        defaultProcessoShouldNotBeFound("valorArea.lessThan=" + DEFAULT_VALOR_AREA);

        // Get all the processoList where valorArea is less than UPDATED_VALOR_AREA
        defaultProcessoShouldBeFound("valorArea.lessThan=" + UPDATED_VALOR_AREA);
    }

    @Test
    @Transactional
    void getAllProcessosByValorAreaIsGreaterThanSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where valorArea is greater than DEFAULT_VALOR_AREA
        defaultProcessoShouldNotBeFound("valorArea.greaterThan=" + DEFAULT_VALOR_AREA);

        // Get all the processoList where valorArea is greater than SMALLER_VALOR_AREA
        defaultProcessoShouldBeFound("valorArea.greaterThan=" + SMALLER_VALOR_AREA);
    }

    @Test
    @Transactional
    void getAllProcessosByDadosGeograficosLitigioConflitoIsEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where dadosGeograficosLitigioConflito equals to DEFAULT_DADOS_GEOGRAFICOS_LITIGIO_CONFLITO
        defaultProcessoShouldBeFound("dadosGeograficosLitigioConflito.equals=" + DEFAULT_DADOS_GEOGRAFICOS_LITIGIO_CONFLITO);

        // Get all the processoList where dadosGeograficosLitigioConflito equals to UPDATED_DADOS_GEOGRAFICOS_LITIGIO_CONFLITO
        defaultProcessoShouldNotBeFound("dadosGeograficosLitigioConflito.equals=" + UPDATED_DADOS_GEOGRAFICOS_LITIGIO_CONFLITO);
    }

    @Test
    @Transactional
    void getAllProcessosByDadosGeograficosLitigioConflitoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where dadosGeograficosLitigioConflito not equals to DEFAULT_DADOS_GEOGRAFICOS_LITIGIO_CONFLITO
        defaultProcessoShouldNotBeFound("dadosGeograficosLitigioConflito.notEquals=" + DEFAULT_DADOS_GEOGRAFICOS_LITIGIO_CONFLITO);

        // Get all the processoList where dadosGeograficosLitigioConflito not equals to UPDATED_DADOS_GEOGRAFICOS_LITIGIO_CONFLITO
        defaultProcessoShouldBeFound("dadosGeograficosLitigioConflito.notEquals=" + UPDATED_DADOS_GEOGRAFICOS_LITIGIO_CONFLITO);
    }

    @Test
    @Transactional
    void getAllProcessosByDadosGeograficosLitigioConflitoIsInShouldWork() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where dadosGeograficosLitigioConflito in DEFAULT_DADOS_GEOGRAFICOS_LITIGIO_CONFLITO or UPDATED_DADOS_GEOGRAFICOS_LITIGIO_CONFLITO
        defaultProcessoShouldBeFound(
            "dadosGeograficosLitigioConflito.in=" +
            DEFAULT_DADOS_GEOGRAFICOS_LITIGIO_CONFLITO +
            "," +
            UPDATED_DADOS_GEOGRAFICOS_LITIGIO_CONFLITO
        );

        // Get all the processoList where dadosGeograficosLitigioConflito equals to UPDATED_DADOS_GEOGRAFICOS_LITIGIO_CONFLITO
        defaultProcessoShouldNotBeFound("dadosGeograficosLitigioConflito.in=" + UPDATED_DADOS_GEOGRAFICOS_LITIGIO_CONFLITO);
    }

    @Test
    @Transactional
    void getAllProcessosByDadosGeograficosLitigioConflitoIsNullOrNotNull() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where dadosGeograficosLitigioConflito is not null
        defaultProcessoShouldBeFound("dadosGeograficosLitigioConflito.specified=true");

        // Get all the processoList where dadosGeograficosLitigioConflito is null
        defaultProcessoShouldNotBeFound("dadosGeograficosLitigioConflito.specified=false");
    }

    @Test
    @Transactional
    void getAllProcessosByLatitudeIsEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where latitude equals to DEFAULT_LATITUDE
        defaultProcessoShouldBeFound("latitude.equals=" + DEFAULT_LATITUDE);

        // Get all the processoList where latitude equals to UPDATED_LATITUDE
        defaultProcessoShouldNotBeFound("latitude.equals=" + UPDATED_LATITUDE);
    }

    @Test
    @Transactional
    void getAllProcessosByLatitudeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where latitude not equals to DEFAULT_LATITUDE
        defaultProcessoShouldNotBeFound("latitude.notEquals=" + DEFAULT_LATITUDE);

        // Get all the processoList where latitude not equals to UPDATED_LATITUDE
        defaultProcessoShouldBeFound("latitude.notEquals=" + UPDATED_LATITUDE);
    }

    @Test
    @Transactional
    void getAllProcessosByLatitudeIsInShouldWork() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where latitude in DEFAULT_LATITUDE or UPDATED_LATITUDE
        defaultProcessoShouldBeFound("latitude.in=" + DEFAULT_LATITUDE + "," + UPDATED_LATITUDE);

        // Get all the processoList where latitude equals to UPDATED_LATITUDE
        defaultProcessoShouldNotBeFound("latitude.in=" + UPDATED_LATITUDE);
    }

    @Test
    @Transactional
    void getAllProcessosByLatitudeIsNullOrNotNull() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where latitude is not null
        defaultProcessoShouldBeFound("latitude.specified=true");

        // Get all the processoList where latitude is null
        defaultProcessoShouldNotBeFound("latitude.specified=false");
    }

    @Test
    @Transactional
    void getAllProcessosByLatitudeContainsSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where latitude contains DEFAULT_LATITUDE
        defaultProcessoShouldBeFound("latitude.contains=" + DEFAULT_LATITUDE);

        // Get all the processoList where latitude contains UPDATED_LATITUDE
        defaultProcessoShouldNotBeFound("latitude.contains=" + UPDATED_LATITUDE);
    }

    @Test
    @Transactional
    void getAllProcessosByLatitudeNotContainsSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where latitude does not contain DEFAULT_LATITUDE
        defaultProcessoShouldNotBeFound("latitude.doesNotContain=" + DEFAULT_LATITUDE);

        // Get all the processoList where latitude does not contain UPDATED_LATITUDE
        defaultProcessoShouldBeFound("latitude.doesNotContain=" + UPDATED_LATITUDE);
    }

    @Test
    @Transactional
    void getAllProcessosByLongitudeIsEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where longitude equals to DEFAULT_LONGITUDE
        defaultProcessoShouldBeFound("longitude.equals=" + DEFAULT_LONGITUDE);

        // Get all the processoList where longitude equals to UPDATED_LONGITUDE
        defaultProcessoShouldNotBeFound("longitude.equals=" + UPDATED_LONGITUDE);
    }

    @Test
    @Transactional
    void getAllProcessosByLongitudeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where longitude not equals to DEFAULT_LONGITUDE
        defaultProcessoShouldNotBeFound("longitude.notEquals=" + DEFAULT_LONGITUDE);

        // Get all the processoList where longitude not equals to UPDATED_LONGITUDE
        defaultProcessoShouldBeFound("longitude.notEquals=" + UPDATED_LONGITUDE);
    }

    @Test
    @Transactional
    void getAllProcessosByLongitudeIsInShouldWork() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where longitude in DEFAULT_LONGITUDE or UPDATED_LONGITUDE
        defaultProcessoShouldBeFound("longitude.in=" + DEFAULT_LONGITUDE + "," + UPDATED_LONGITUDE);

        // Get all the processoList where longitude equals to UPDATED_LONGITUDE
        defaultProcessoShouldNotBeFound("longitude.in=" + UPDATED_LONGITUDE);
    }

    @Test
    @Transactional
    void getAllProcessosByLongitudeIsNullOrNotNull() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where longitude is not null
        defaultProcessoShouldBeFound("longitude.specified=true");

        // Get all the processoList where longitude is null
        defaultProcessoShouldNotBeFound("longitude.specified=false");
    }

    @Test
    @Transactional
    void getAllProcessosByLongitudeContainsSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where longitude contains DEFAULT_LONGITUDE
        defaultProcessoShouldBeFound("longitude.contains=" + DEFAULT_LONGITUDE);

        // Get all the processoList where longitude contains UPDATED_LONGITUDE
        defaultProcessoShouldNotBeFound("longitude.contains=" + UPDATED_LONGITUDE);
    }

    @Test
    @Transactional
    void getAllProcessosByLongitudeNotContainsSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where longitude does not contain DEFAULT_LONGITUDE
        defaultProcessoShouldNotBeFound("longitude.doesNotContain=" + DEFAULT_LONGITUDE);

        // Get all the processoList where longitude does not contain UPDATED_LONGITUDE
        defaultProcessoShouldBeFound("longitude.doesNotContain=" + UPDATED_LONGITUDE);
    }

    @Test
    @Transactional
    void getAllProcessosByNumeroProcessoMPFIsEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where numeroProcessoMPF equals to DEFAULT_NUMERO_PROCESSO_MPF
        defaultProcessoShouldBeFound("numeroProcessoMPF.equals=" + DEFAULT_NUMERO_PROCESSO_MPF);

        // Get all the processoList where numeroProcessoMPF equals to UPDATED_NUMERO_PROCESSO_MPF
        defaultProcessoShouldNotBeFound("numeroProcessoMPF.equals=" + UPDATED_NUMERO_PROCESSO_MPF);
    }

    @Test
    @Transactional
    void getAllProcessosByNumeroProcessoMPFIsNotEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where numeroProcessoMPF not equals to DEFAULT_NUMERO_PROCESSO_MPF
        defaultProcessoShouldNotBeFound("numeroProcessoMPF.notEquals=" + DEFAULT_NUMERO_PROCESSO_MPF);

        // Get all the processoList where numeroProcessoMPF not equals to UPDATED_NUMERO_PROCESSO_MPF
        defaultProcessoShouldBeFound("numeroProcessoMPF.notEquals=" + UPDATED_NUMERO_PROCESSO_MPF);
    }

    @Test
    @Transactional
    void getAllProcessosByNumeroProcessoMPFIsInShouldWork() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where numeroProcessoMPF in DEFAULT_NUMERO_PROCESSO_MPF or UPDATED_NUMERO_PROCESSO_MPF
        defaultProcessoShouldBeFound("numeroProcessoMPF.in=" + DEFAULT_NUMERO_PROCESSO_MPF + "," + UPDATED_NUMERO_PROCESSO_MPF);

        // Get all the processoList where numeroProcessoMPF equals to UPDATED_NUMERO_PROCESSO_MPF
        defaultProcessoShouldNotBeFound("numeroProcessoMPF.in=" + UPDATED_NUMERO_PROCESSO_MPF);
    }

    @Test
    @Transactional
    void getAllProcessosByNumeroProcessoMPFIsNullOrNotNull() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where numeroProcessoMPF is not null
        defaultProcessoShouldBeFound("numeroProcessoMPF.specified=true");

        // Get all the processoList where numeroProcessoMPF is null
        defaultProcessoShouldNotBeFound("numeroProcessoMPF.specified=false");
    }

    @Test
    @Transactional
    void getAllProcessosByNumeroProcessoMPFContainsSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where numeroProcessoMPF contains DEFAULT_NUMERO_PROCESSO_MPF
        defaultProcessoShouldBeFound("numeroProcessoMPF.contains=" + DEFAULT_NUMERO_PROCESSO_MPF);

        // Get all the processoList where numeroProcessoMPF contains UPDATED_NUMERO_PROCESSO_MPF
        defaultProcessoShouldNotBeFound("numeroProcessoMPF.contains=" + UPDATED_NUMERO_PROCESSO_MPF);
    }

    @Test
    @Transactional
    void getAllProcessosByNumeroProcessoMPFNotContainsSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where numeroProcessoMPF does not contain DEFAULT_NUMERO_PROCESSO_MPF
        defaultProcessoShouldNotBeFound("numeroProcessoMPF.doesNotContain=" + DEFAULT_NUMERO_PROCESSO_MPF);

        // Get all the processoList where numeroProcessoMPF does not contain UPDATED_NUMERO_PROCESSO_MPF
        defaultProcessoShouldBeFound("numeroProcessoMPF.doesNotContain=" + UPDATED_NUMERO_PROCESSO_MPF);
    }

    @Test
    @Transactional
    void getAllProcessosByNumeroEmbargoIsEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where numeroEmbargo equals to DEFAULT_NUMERO_EMBARGO
        defaultProcessoShouldBeFound("numeroEmbargo.equals=" + DEFAULT_NUMERO_EMBARGO);

        // Get all the processoList where numeroEmbargo equals to UPDATED_NUMERO_EMBARGO
        defaultProcessoShouldNotBeFound("numeroEmbargo.equals=" + UPDATED_NUMERO_EMBARGO);
    }

    @Test
    @Transactional
    void getAllProcessosByNumeroEmbargoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where numeroEmbargo not equals to DEFAULT_NUMERO_EMBARGO
        defaultProcessoShouldNotBeFound("numeroEmbargo.notEquals=" + DEFAULT_NUMERO_EMBARGO);

        // Get all the processoList where numeroEmbargo not equals to UPDATED_NUMERO_EMBARGO
        defaultProcessoShouldBeFound("numeroEmbargo.notEquals=" + UPDATED_NUMERO_EMBARGO);
    }

    @Test
    @Transactional
    void getAllProcessosByNumeroEmbargoIsInShouldWork() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where numeroEmbargo in DEFAULT_NUMERO_EMBARGO or UPDATED_NUMERO_EMBARGO
        defaultProcessoShouldBeFound("numeroEmbargo.in=" + DEFAULT_NUMERO_EMBARGO + "," + UPDATED_NUMERO_EMBARGO);

        // Get all the processoList where numeroEmbargo equals to UPDATED_NUMERO_EMBARGO
        defaultProcessoShouldNotBeFound("numeroEmbargo.in=" + UPDATED_NUMERO_EMBARGO);
    }

    @Test
    @Transactional
    void getAllProcessosByNumeroEmbargoIsNullOrNotNull() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where numeroEmbargo is not null
        defaultProcessoShouldBeFound("numeroEmbargo.specified=true");

        // Get all the processoList where numeroEmbargo is null
        defaultProcessoShouldNotBeFound("numeroEmbargo.specified=false");
    }

    @Test
    @Transactional
    void getAllProcessosByNumeroEmbargoContainsSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where numeroEmbargo contains DEFAULT_NUMERO_EMBARGO
        defaultProcessoShouldBeFound("numeroEmbargo.contains=" + DEFAULT_NUMERO_EMBARGO);

        // Get all the processoList where numeroEmbargo contains UPDATED_NUMERO_EMBARGO
        defaultProcessoShouldNotBeFound("numeroEmbargo.contains=" + UPDATED_NUMERO_EMBARGO);
    }

    @Test
    @Transactional
    void getAllProcessosByNumeroEmbargoNotContainsSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where numeroEmbargo does not contain DEFAULT_NUMERO_EMBARGO
        defaultProcessoShouldNotBeFound("numeroEmbargo.doesNotContain=" + DEFAULT_NUMERO_EMBARGO);

        // Get all the processoList where numeroEmbargo does not contain UPDATED_NUMERO_EMBARGO
        defaultProcessoShouldBeFound("numeroEmbargo.doesNotContain=" + UPDATED_NUMERO_EMBARGO);
    }

    @Test
    @Transactional
    void getAllProcessosByNumeroRecursoEspecialIsEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where numeroRecursoEspecial equals to DEFAULT_NUMERO_RECURSO_ESPECIAL
        defaultProcessoShouldBeFound("numeroRecursoEspecial.equals=" + DEFAULT_NUMERO_RECURSO_ESPECIAL);

        // Get all the processoList where numeroRecursoEspecial equals to UPDATED_NUMERO_RECURSO_ESPECIAL
        defaultProcessoShouldNotBeFound("numeroRecursoEspecial.equals=" + UPDATED_NUMERO_RECURSO_ESPECIAL);
    }

    @Test
    @Transactional
    void getAllProcessosByNumeroRecursoEspecialIsNotEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where numeroRecursoEspecial not equals to DEFAULT_NUMERO_RECURSO_ESPECIAL
        defaultProcessoShouldNotBeFound("numeroRecursoEspecial.notEquals=" + DEFAULT_NUMERO_RECURSO_ESPECIAL);

        // Get all the processoList where numeroRecursoEspecial not equals to UPDATED_NUMERO_RECURSO_ESPECIAL
        defaultProcessoShouldBeFound("numeroRecursoEspecial.notEquals=" + UPDATED_NUMERO_RECURSO_ESPECIAL);
    }

    @Test
    @Transactional
    void getAllProcessosByNumeroRecursoEspecialIsInShouldWork() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where numeroRecursoEspecial in DEFAULT_NUMERO_RECURSO_ESPECIAL or UPDATED_NUMERO_RECURSO_ESPECIAL
        defaultProcessoShouldBeFound("numeroRecursoEspecial.in=" + DEFAULT_NUMERO_RECURSO_ESPECIAL + "," + UPDATED_NUMERO_RECURSO_ESPECIAL);

        // Get all the processoList where numeroRecursoEspecial equals to UPDATED_NUMERO_RECURSO_ESPECIAL
        defaultProcessoShouldNotBeFound("numeroRecursoEspecial.in=" + UPDATED_NUMERO_RECURSO_ESPECIAL);
    }

    @Test
    @Transactional
    void getAllProcessosByNumeroRecursoEspecialIsNullOrNotNull() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where numeroRecursoEspecial is not null
        defaultProcessoShouldBeFound("numeroRecursoEspecial.specified=true");

        // Get all the processoList where numeroRecursoEspecial is null
        defaultProcessoShouldNotBeFound("numeroRecursoEspecial.specified=false");
    }

    @Test
    @Transactional
    void getAllProcessosByNumeroRecursoEspecialContainsSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where numeroRecursoEspecial contains DEFAULT_NUMERO_RECURSO_ESPECIAL
        defaultProcessoShouldBeFound("numeroRecursoEspecial.contains=" + DEFAULT_NUMERO_RECURSO_ESPECIAL);

        // Get all the processoList where numeroRecursoEspecial contains UPDATED_NUMERO_RECURSO_ESPECIAL
        defaultProcessoShouldNotBeFound("numeroRecursoEspecial.contains=" + UPDATED_NUMERO_RECURSO_ESPECIAL);
    }

    @Test
    @Transactional
    void getAllProcessosByNumeroRecursoEspecialNotContainsSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where numeroRecursoEspecial does not contain DEFAULT_NUMERO_RECURSO_ESPECIAL
        defaultProcessoShouldNotBeFound("numeroRecursoEspecial.doesNotContain=" + DEFAULT_NUMERO_RECURSO_ESPECIAL);

        // Get all the processoList where numeroRecursoEspecial does not contain UPDATED_NUMERO_RECURSO_ESPECIAL
        defaultProcessoShouldBeFound("numeroRecursoEspecial.doesNotContain=" + UPDATED_NUMERO_RECURSO_ESPECIAL);
    }

    @Test
    @Transactional
    void getAllProcessosByEnvolveGrandeProjetoIsEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where envolveGrandeProjeto equals to DEFAULT_ENVOLVE_GRANDE_PROJETO
        defaultProcessoShouldBeFound("envolveGrandeProjeto.equals=" + DEFAULT_ENVOLVE_GRANDE_PROJETO);

        // Get all the processoList where envolveGrandeProjeto equals to UPDATED_ENVOLVE_GRANDE_PROJETO
        defaultProcessoShouldNotBeFound("envolveGrandeProjeto.equals=" + UPDATED_ENVOLVE_GRANDE_PROJETO);
    }

    @Test
    @Transactional
    void getAllProcessosByEnvolveGrandeProjetoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where envolveGrandeProjeto not equals to DEFAULT_ENVOLVE_GRANDE_PROJETO
        defaultProcessoShouldNotBeFound("envolveGrandeProjeto.notEquals=" + DEFAULT_ENVOLVE_GRANDE_PROJETO);

        // Get all the processoList where envolveGrandeProjeto not equals to UPDATED_ENVOLVE_GRANDE_PROJETO
        defaultProcessoShouldBeFound("envolveGrandeProjeto.notEquals=" + UPDATED_ENVOLVE_GRANDE_PROJETO);
    }

    @Test
    @Transactional
    void getAllProcessosByEnvolveGrandeProjetoIsInShouldWork() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where envolveGrandeProjeto in DEFAULT_ENVOLVE_GRANDE_PROJETO or UPDATED_ENVOLVE_GRANDE_PROJETO
        defaultProcessoShouldBeFound("envolveGrandeProjeto.in=" + DEFAULT_ENVOLVE_GRANDE_PROJETO + "," + UPDATED_ENVOLVE_GRANDE_PROJETO);

        // Get all the processoList where envolveGrandeProjeto equals to UPDATED_ENVOLVE_GRANDE_PROJETO
        defaultProcessoShouldNotBeFound("envolveGrandeProjeto.in=" + UPDATED_ENVOLVE_GRANDE_PROJETO);
    }

    @Test
    @Transactional
    void getAllProcessosByEnvolveGrandeProjetoIsNullOrNotNull() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where envolveGrandeProjeto is not null
        defaultProcessoShouldBeFound("envolveGrandeProjeto.specified=true");

        // Get all the processoList where envolveGrandeProjeto is null
        defaultProcessoShouldNotBeFound("envolveGrandeProjeto.specified=false");
    }

    @Test
    @Transactional
    void getAllProcessosByEnvolveUnidadeConservacaoIsEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where envolveUnidadeConservacao equals to DEFAULT_ENVOLVE_UNIDADE_CONSERVACAO
        defaultProcessoShouldBeFound("envolveUnidadeConservacao.equals=" + DEFAULT_ENVOLVE_UNIDADE_CONSERVACAO);

        // Get all the processoList where envolveUnidadeConservacao equals to UPDATED_ENVOLVE_UNIDADE_CONSERVACAO
        defaultProcessoShouldNotBeFound("envolveUnidadeConservacao.equals=" + UPDATED_ENVOLVE_UNIDADE_CONSERVACAO);
    }

    @Test
    @Transactional
    void getAllProcessosByEnvolveUnidadeConservacaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where envolveUnidadeConservacao not equals to DEFAULT_ENVOLVE_UNIDADE_CONSERVACAO
        defaultProcessoShouldNotBeFound("envolveUnidadeConservacao.notEquals=" + DEFAULT_ENVOLVE_UNIDADE_CONSERVACAO);

        // Get all the processoList where envolveUnidadeConservacao not equals to UPDATED_ENVOLVE_UNIDADE_CONSERVACAO
        defaultProcessoShouldBeFound("envolveUnidadeConservacao.notEquals=" + UPDATED_ENVOLVE_UNIDADE_CONSERVACAO);
    }

    @Test
    @Transactional
    void getAllProcessosByEnvolveUnidadeConservacaoIsInShouldWork() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where envolveUnidadeConservacao in DEFAULT_ENVOLVE_UNIDADE_CONSERVACAO or UPDATED_ENVOLVE_UNIDADE_CONSERVACAO
        defaultProcessoShouldBeFound(
            "envolveUnidadeConservacao.in=" + DEFAULT_ENVOLVE_UNIDADE_CONSERVACAO + "," + UPDATED_ENVOLVE_UNIDADE_CONSERVACAO
        );

        // Get all the processoList where envolveUnidadeConservacao equals to UPDATED_ENVOLVE_UNIDADE_CONSERVACAO
        defaultProcessoShouldNotBeFound("envolveUnidadeConservacao.in=" + UPDATED_ENVOLVE_UNIDADE_CONSERVACAO);
    }

    @Test
    @Transactional
    void getAllProcessosByEnvolveUnidadeConservacaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where envolveUnidadeConservacao is not null
        defaultProcessoShouldBeFound("envolveUnidadeConservacao.specified=true");

        // Get all the processoList where envolveUnidadeConservacao is null
        defaultProcessoShouldNotBeFound("envolveUnidadeConservacao.specified=false");
    }

    @Test
    @Transactional
    void getAllProcessosByStatusProcessoIsEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where statusProcesso equals to DEFAULT_STATUS_PROCESSO
        defaultProcessoShouldBeFound("statusProcesso.equals=" + DEFAULT_STATUS_PROCESSO);

        // Get all the processoList where statusProcesso equals to UPDATED_STATUS_PROCESSO
        defaultProcessoShouldNotBeFound("statusProcesso.equals=" + UPDATED_STATUS_PROCESSO);
    }

    @Test
    @Transactional
    void getAllProcessosByStatusProcessoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where statusProcesso not equals to DEFAULT_STATUS_PROCESSO
        defaultProcessoShouldNotBeFound("statusProcesso.notEquals=" + DEFAULT_STATUS_PROCESSO);

        // Get all the processoList where statusProcesso not equals to UPDATED_STATUS_PROCESSO
        defaultProcessoShouldBeFound("statusProcesso.notEquals=" + UPDATED_STATUS_PROCESSO);
    }

    @Test
    @Transactional
    void getAllProcessosByStatusProcessoIsInShouldWork() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where statusProcesso in DEFAULT_STATUS_PROCESSO or UPDATED_STATUS_PROCESSO
        defaultProcessoShouldBeFound("statusProcesso.in=" + DEFAULT_STATUS_PROCESSO + "," + UPDATED_STATUS_PROCESSO);

        // Get all the processoList where statusProcesso equals to UPDATED_STATUS_PROCESSO
        defaultProcessoShouldNotBeFound("statusProcesso.in=" + UPDATED_STATUS_PROCESSO);
    }

    @Test
    @Transactional
    void getAllProcessosByStatusProcessoIsNullOrNotNull() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where statusProcesso is not null
        defaultProcessoShouldBeFound("statusProcesso.specified=true");

        // Get all the processoList where statusProcesso is null
        defaultProcessoShouldNotBeFound("statusProcesso.specified=false");
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
    void getAllProcessosBySecaoJudiciariaIsEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);
        SecaoJudiciaria secaoJudiciaria = SecaoJudiciariaResourceIT.createEntity(em);
        em.persist(secaoJudiciaria);
        em.flush();
        processo.setSecaoJudiciaria(secaoJudiciaria);
        processoRepository.saveAndFlush(processo);
        Long secaoJudiciariaId = secaoJudiciaria.getId();

        // Get all the processoList where secaoJudiciaria equals to secaoJudiciariaId
        defaultProcessoShouldBeFound("secaoJudiciariaId.equals=" + secaoJudiciariaId);

        // Get all the processoList where secaoJudiciaria equals to (secaoJudiciariaId + 1)
        defaultProcessoShouldNotBeFound("secaoJudiciariaId.equals=" + (secaoJudiciariaId + 1));
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
            .andExpect(jsonPath("$.[*].numeroProcesso").value(hasItem(DEFAULT_NUMERO_PROCESSO)))
            .andExpect(jsonPath("$.[*].oficio").value(hasItem(DEFAULT_OFICIO)))
            .andExpect(jsonPath("$.[*].assunto").value(hasItem(DEFAULT_ASSUNTO.toString())))
            .andExpect(jsonPath("$.[*].linkUnico").value(hasItem(DEFAULT_LINK_UNICO)))
            .andExpect(jsonPath("$.[*].linkTrf").value(hasItem(DEFAULT_LINK_TRF)))
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
            .andExpect(jsonPath("$.[*].folhasProcessoConcessaoLiminar").value(hasItem(DEFAULT_FOLHAS_PROCESSO_CONCESSAO_LIMINAR)))
            .andExpect(jsonPath("$.[*].concessaoLiminarObservacoes").value(hasItem(DEFAULT_CONCESSAO_LIMINAR_OBSERVACOES.toString())))
            .andExpect(jsonPath("$.[*].folhasProcessoCassacao").value(hasItem(DEFAULT_FOLHAS_PROCESSO_CASSACAO)))
            .andExpect(jsonPath("$.[*].folhasParecer").value(hasItem(DEFAULT_FOLHAS_PARECER)))
            .andExpect(jsonPath("$.[*].folhasEmbargo").value(hasItem(DEFAULT_FOLHAS_EMBARGO)))
            .andExpect(jsonPath("$.[*].acordaoEmbargo").value(hasItem(DEFAULT_ACORDAO_EMBARGO.toString())))
            .andExpect(jsonPath("$.[*].folhasCienciaJulgEmbargos").value(hasItem(DEFAULT_FOLHAS_CIENCIA_JULG_EMBARGOS)))
            .andExpect(jsonPath("$.[*].apelacao").value(hasItem(DEFAULT_APELACAO)))
            .andExpect(jsonPath("$.[*].folhasApelacao").value(hasItem(DEFAULT_FOLHAS_APELACAO)))
            .andExpect(jsonPath("$.[*].acordaoApelacao").value(hasItem(DEFAULT_ACORDAO_APELACAO.toString())))
            .andExpect(jsonPath("$.[*].folhasCienciaJulgApelacao").value(hasItem(DEFAULT_FOLHAS_CIENCIA_JULG_APELACAO)))
            .andExpect(jsonPath("$.[*].embargoDeclaracao").value(hasItem(DEFAULT_EMBARGO_DECLARACAO.booleanValue())))
            .andExpect(jsonPath("$.[*].embargoRecursoExtraordinario").value(hasItem(DEFAULT_EMBARGO_RECURSO_EXTRAORDINARIO.booleanValue())))
            .andExpect(jsonPath("$.[*].folhasRecursoEspecial").value(hasItem(DEFAULT_FOLHAS_RECURSO_ESPECIAL)))
            .andExpect(jsonPath("$.[*].acordaoRecursoEspecial").value(hasItem(DEFAULT_ACORDAO_RECURSO_ESPECIAL.toString())))
            .andExpect(
                jsonPath("$.[*].folhasCienciaJulgamentoRecursoEspecial").value(hasItem(DEFAULT_FOLHAS_CIENCIA_JULGAMENTO_RECURSO_ESPECIAL))
            )
            .andExpect(jsonPath("$.[*].embargoRecursoEspecial").value(hasItem(DEFAULT_EMBARGO_RECURSO_ESPECIAL.booleanValue())))
            .andExpect(jsonPath("$.[*].folhasCiencia").value(hasItem(DEFAULT_FOLHAS_CIENCIA)))
            .andExpect(jsonPath("$.[*].agravoRespRe").value(hasItem(DEFAULT_AGRAVO_RESP_RE)))
            .andExpect(jsonPath("$.[*].folhasRespRe").value(hasItem(DEFAULT_FOLHAS_RESP_RE)))
            .andExpect(jsonPath("$.[*].acordaoAgravoRespRe").value(hasItem(DEFAULT_ACORDAO_AGRAVO_RESP_RE.toString())))
            .andExpect(
                jsonPath("$.[*].folhasCienciaJulgamentoAgravoRespRe").value(hasItem(DEFAULT_FOLHAS_CIENCIA_JULGAMENTO_AGRAVO_RESP_RE))
            )
            .andExpect(jsonPath("$.[*].embargoRespRe").value(hasItem(DEFAULT_EMBARGO_RESP_RE)))
            .andExpect(jsonPath("$.[*].agravoInterno").value(hasItem(DEFAULT_AGRAVO_INTERNO)))
            .andExpect(jsonPath("$.[*].folhasAgravoInterno").value(hasItem(DEFAULT_FOLHAS_AGRAVO_INTERNO)))
            .andExpect(jsonPath("$.[*].embargoRecursoAgravo").value(hasItem(DEFAULT_EMBARGO_RECURSO_AGRAVO.booleanValue())))
            .andExpect(jsonPath("$.[*].observacoes").value(hasItem(DEFAULT_OBSERVACOES.toString())))
            .andExpect(jsonPath("$.[*].recursoSTJ").value(hasItem(DEFAULT_RECURSO_STJ.booleanValue())))
            .andExpect(jsonPath("$.[*].linkRecursoSTJ").value(hasItem(DEFAULT_LINK_RECURSO_STJ)))
            .andExpect(jsonPath("$.[*].folhasRecursoSTJ").value(hasItem(DEFAULT_FOLHAS_RECURSO_STJ)))
            .andExpect(jsonPath("$.[*].recursoSTF").value(hasItem(DEFAULT_RECURSO_STF.booleanValue())))
            .andExpect(jsonPath("$.[*].linkRecursoSTF").value(hasItem(DEFAULT_LINK_RECURSO_STF)))
            .andExpect(jsonPath("$.[*].folhasRecursoSTF").value(hasItem(DEFAULT_FOLHAS_RECURSO_STF)))
            .andExpect(jsonPath("$.[*].folhasMemorialMPF").value(hasItem(DEFAULT_FOLHAS_MEMORIAL_MPF)))
            .andExpect(jsonPath("$.[*].execusaoProvisoria").value(hasItem(DEFAULT_EXECUSAO_PROVISORIA.booleanValue())))
            .andExpect(jsonPath("$.[*].numeracaoExecusaoProvisoria").value(hasItem(DEFAULT_NUMERACAO_EXECUSAO_PROVISORIA)))
            .andExpect(
                jsonPath("$.[*].recuperacaoEfetivaCumprimentoSentenca")
                    .value(hasItem(DEFAULT_RECUPERACAO_EFETIVA_CUMPRIMENTO_SENTENCA.toString()))
            )
            .andExpect(
                jsonPath("$.[*].recuperacaoEfetivaCumprimentoSentencaObservacoes")
                    .value(hasItem(DEFAULT_RECUPERACAO_EFETIVA_CUMPRIMENTO_SENTENCA_OBSERVACOES.toString()))
            )
            .andExpect(jsonPath("$.[*].envolveEmpreendimento").value(hasItem(DEFAULT_ENVOLVE_EMPREENDIMENTO.booleanValue())))
            .andExpect(jsonPath("$.[*].envolveExploracaoIlegal").value(hasItem(DEFAULT_ENVOLVE_EXPLORACAO_ILEGAL.booleanValue())))
            .andExpect(jsonPath("$.[*].envolveTerraQuilombola").value(hasItem(DEFAULT_ENVOLVE_TERRA_QUILOMBOLA.booleanValue())))
            .andExpect(
                jsonPath("$.[*].envolveTerraComunidadeTradicional")
                    .value(hasItem(DEFAULT_ENVOLVE_TERRA_COMUNIDADE_TRADICIONAL.booleanValue()))
            )
            .andExpect(jsonPath("$.[*].envolveTerraIndigena").value(hasItem(DEFAULT_ENVOLVE_TERRA_INDIGENA.booleanValue())))
            .andExpect(jsonPath("$.[*].resumoFatos").value(hasItem(DEFAULT_RESUMO_FATOS.toString())))
            .andExpect(jsonPath("$.[*].tamanhoArea").value(hasItem(sameNumber(DEFAULT_TAMANHO_AREA))))
            .andExpect(jsonPath("$.[*].valorArea").value(hasItem(sameNumber(DEFAULT_VALOR_AREA))))
            .andExpect(jsonPath("$.[*].tamanhoAreaObservacao").value(hasItem(DEFAULT_TAMANHO_AREA_OBSERVACAO.toString())))
            .andExpect(
                jsonPath("$.[*].dadosGeograficosLitigioConflito").value(hasItem(DEFAULT_DADOS_GEOGRAFICOS_LITIGIO_CONFLITO.booleanValue()))
            )
            .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE)))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE)))
            .andExpect(jsonPath("$.[*].numeroProcessoMPF").value(hasItem(DEFAULT_NUMERO_PROCESSO_MPF)))
            .andExpect(jsonPath("$.[*].numeroEmbargo").value(hasItem(DEFAULT_NUMERO_EMBARGO)))
            .andExpect(jsonPath("$.[*].pautaApelacao").value(hasItem(DEFAULT_PAUTA_APELACAO.toString())))
            .andExpect(jsonPath("$.[*].numeroRecursoEspecial").value(hasItem(DEFAULT_NUMERO_RECURSO_ESPECIAL)))
            .andExpect(jsonPath("$.[*].admissiblidade").value(hasItem(DEFAULT_ADMISSIBLIDADE.toString())))
            .andExpect(jsonPath("$.[*].envolveGrandeProjeto").value(hasItem(DEFAULT_ENVOLVE_GRANDE_PROJETO.booleanValue())))
            .andExpect(jsonPath("$.[*].envolveUnidadeConservacao").value(hasItem(DEFAULT_ENVOLVE_UNIDADE_CONSERVACAO.booleanValue())))
            .andExpect(jsonPath("$.[*].linkReferencia").value(hasItem(DEFAULT_LINK_REFERENCIA.toString())))
            .andExpect(jsonPath("$.[*].statusProcesso").value(hasItem(DEFAULT_STATUS_PROCESSO.toString())));

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
            .numeroProcesso(UPDATED_NUMERO_PROCESSO)
            .oficio(UPDATED_OFICIO)
            .assunto(UPDATED_ASSUNTO)
            .linkUnico(UPDATED_LINK_UNICO)
            .linkTrf(UPDATED_LINK_TRF)
            .turmaTrf1(UPDATED_TURMA_TRF_1)
            .numeroProcessoAdministrativo(UPDATED_NUMERO_PROCESSO_ADMINISTRATIVO)
            .numeroProcessoJudicialPrimeiraInstancia(UPDATED_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA)
            .numeroProcessoJudicialPrimeiraInstanciaLink(UPDATED_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA_LINK)
            .numeroProcessoJudicialPrimeiraInstanciaObservacoes(UPDATED_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA_OBSERVACOES)
            .parecer(UPDATED_PARECER)
            .folhasProcessoConcessaoLiminar(UPDATED_FOLHAS_PROCESSO_CONCESSAO_LIMINAR)
            .concessaoLiminarObservacoes(UPDATED_CONCESSAO_LIMINAR_OBSERVACOES)
            .folhasProcessoCassacao(UPDATED_FOLHAS_PROCESSO_CASSACAO)
            .folhasParecer(UPDATED_FOLHAS_PARECER)
            .folhasEmbargo(UPDATED_FOLHAS_EMBARGO)
            .acordaoEmbargo(UPDATED_ACORDAO_EMBARGO)
            .folhasCienciaJulgEmbargos(UPDATED_FOLHAS_CIENCIA_JULG_EMBARGOS)
            .apelacao(UPDATED_APELACAO)
            .folhasApelacao(UPDATED_FOLHAS_APELACAO)
            .acordaoApelacao(UPDATED_ACORDAO_APELACAO)
            .folhasCienciaJulgApelacao(UPDATED_FOLHAS_CIENCIA_JULG_APELACAO)
            .embargoDeclaracao(UPDATED_EMBARGO_DECLARACAO)
            .embargoRecursoExtraordinario(UPDATED_EMBARGO_RECURSO_EXTRAORDINARIO)
            .folhasRecursoEspecial(UPDATED_FOLHAS_RECURSO_ESPECIAL)
            .acordaoRecursoEspecial(UPDATED_ACORDAO_RECURSO_ESPECIAL)
            .folhasCienciaJulgamentoRecursoEspecial(UPDATED_FOLHAS_CIENCIA_JULGAMENTO_RECURSO_ESPECIAL)
            .embargoRecursoEspecial(UPDATED_EMBARGO_RECURSO_ESPECIAL)
            .folhasCiencia(UPDATED_FOLHAS_CIENCIA)
            .agravoRespRe(UPDATED_AGRAVO_RESP_RE)
            .folhasRespRe(UPDATED_FOLHAS_RESP_RE)
            .acordaoAgravoRespRe(UPDATED_ACORDAO_AGRAVO_RESP_RE)
            .folhasCienciaJulgamentoAgravoRespRe(UPDATED_FOLHAS_CIENCIA_JULGAMENTO_AGRAVO_RESP_RE)
            .embargoRespRe(UPDATED_EMBARGO_RESP_RE)
            .agravoInterno(UPDATED_AGRAVO_INTERNO)
            .folhasAgravoInterno(UPDATED_FOLHAS_AGRAVO_INTERNO)
            .embargoRecursoAgravo(UPDATED_EMBARGO_RECURSO_AGRAVO)
            .observacoes(UPDATED_OBSERVACOES)
            .recursoSTJ(UPDATED_RECURSO_STJ)
            .linkRecursoSTJ(UPDATED_LINK_RECURSO_STJ)
            .folhasRecursoSTJ(UPDATED_FOLHAS_RECURSO_STJ)
            .recursoSTF(UPDATED_RECURSO_STF)
            .linkRecursoSTF(UPDATED_LINK_RECURSO_STF)
            .folhasRecursoSTF(UPDATED_FOLHAS_RECURSO_STF)
            .folhasMemorialMPF(UPDATED_FOLHAS_MEMORIAL_MPF)
            .execusaoProvisoria(UPDATED_EXECUSAO_PROVISORIA)
            .numeracaoExecusaoProvisoria(UPDATED_NUMERACAO_EXECUSAO_PROVISORIA)
            .recuperacaoEfetivaCumprimentoSentenca(UPDATED_RECUPERACAO_EFETIVA_CUMPRIMENTO_SENTENCA)
            .recuperacaoEfetivaCumprimentoSentencaObservacoes(UPDATED_RECUPERACAO_EFETIVA_CUMPRIMENTO_SENTENCA_OBSERVACOES)
            .envolveEmpreendimento(UPDATED_ENVOLVE_EMPREENDIMENTO)
            .envolveExploracaoIlegal(UPDATED_ENVOLVE_EXPLORACAO_ILEGAL)
            .envolveTerraQuilombola(UPDATED_ENVOLVE_TERRA_QUILOMBOLA)
            .envolveTerraComunidadeTradicional(UPDATED_ENVOLVE_TERRA_COMUNIDADE_TRADICIONAL)
            .envolveTerraIndigena(UPDATED_ENVOLVE_TERRA_INDIGENA)
            .resumoFatos(UPDATED_RESUMO_FATOS)
            .tamanhoArea(UPDATED_TAMANHO_AREA)
            .valorArea(UPDATED_VALOR_AREA)
            .tamanhoAreaObservacao(UPDATED_TAMANHO_AREA_OBSERVACAO)
            .dadosGeograficosLitigioConflito(UPDATED_DADOS_GEOGRAFICOS_LITIGIO_CONFLITO)
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE)
            .numeroProcessoMPF(UPDATED_NUMERO_PROCESSO_MPF)
            .numeroEmbargo(UPDATED_NUMERO_EMBARGO)
            .pautaApelacao(UPDATED_PAUTA_APELACAO)
            .numeroRecursoEspecial(UPDATED_NUMERO_RECURSO_ESPECIAL)
            .admissiblidade(UPDATED_ADMISSIBLIDADE)
            .envolveGrandeProjeto(UPDATED_ENVOLVE_GRANDE_PROJETO)
            .envolveUnidadeConservacao(UPDATED_ENVOLVE_UNIDADE_CONSERVACAO)
            .linkReferencia(UPDATED_LINK_REFERENCIA)
            .statusProcesso(UPDATED_STATUS_PROCESSO);

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
        assertThat(testProcesso.getNumeroProcesso()).isEqualTo(UPDATED_NUMERO_PROCESSO);
        assertThat(testProcesso.getOficio()).isEqualTo(UPDATED_OFICIO);
        assertThat(testProcesso.getAssunto()).isEqualTo(UPDATED_ASSUNTO);
        assertThat(testProcesso.getLinkUnico()).isEqualTo(UPDATED_LINK_UNICO);
        assertThat(testProcesso.getLinkTrf()).isEqualTo(UPDATED_LINK_TRF);
        assertThat(testProcesso.getTurmaTrf1()).isEqualTo(UPDATED_TURMA_TRF_1);
        assertThat(testProcesso.getNumeroProcessoAdministrativo()).isEqualTo(UPDATED_NUMERO_PROCESSO_ADMINISTRATIVO);
        assertThat(testProcesso.getNumeroProcessoJudicialPrimeiraInstancia())
            .isEqualTo(UPDATED_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA);
        assertThat(testProcesso.getNumeroProcessoJudicialPrimeiraInstanciaLink())
            .isEqualTo(UPDATED_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA_LINK);
        assertThat(testProcesso.getNumeroProcessoJudicialPrimeiraInstanciaObservacoes())
            .isEqualTo(UPDATED_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA_OBSERVACOES);
        assertThat(testProcesso.getParecer()).isEqualTo(UPDATED_PARECER);
        assertThat(testProcesso.getFolhasProcessoConcessaoLiminar()).isEqualTo(UPDATED_FOLHAS_PROCESSO_CONCESSAO_LIMINAR);
        assertThat(testProcesso.getConcessaoLiminarObservacoes()).isEqualTo(UPDATED_CONCESSAO_LIMINAR_OBSERVACOES);
        assertThat(testProcesso.getFolhasProcessoCassacao()).isEqualTo(UPDATED_FOLHAS_PROCESSO_CASSACAO);
        assertThat(testProcesso.getFolhasParecer()).isEqualTo(UPDATED_FOLHAS_PARECER);
        assertThat(testProcesso.getFolhasEmbargo()).isEqualTo(UPDATED_FOLHAS_EMBARGO);
        assertThat(testProcesso.getAcordaoEmbargo()).isEqualTo(UPDATED_ACORDAO_EMBARGO);
        assertThat(testProcesso.getFolhasCienciaJulgEmbargos()).isEqualTo(UPDATED_FOLHAS_CIENCIA_JULG_EMBARGOS);
        assertThat(testProcesso.getApelacao()).isEqualTo(UPDATED_APELACAO);
        assertThat(testProcesso.getFolhasApelacao()).isEqualTo(UPDATED_FOLHAS_APELACAO);
        assertThat(testProcesso.getAcordaoApelacao()).isEqualTo(UPDATED_ACORDAO_APELACAO);
        assertThat(testProcesso.getFolhasCienciaJulgApelacao()).isEqualTo(UPDATED_FOLHAS_CIENCIA_JULG_APELACAO);
        assertThat(testProcesso.getEmbargoDeclaracao()).isEqualTo(UPDATED_EMBARGO_DECLARACAO);
        assertThat(testProcesso.getEmbargoRecursoExtraordinario()).isEqualTo(UPDATED_EMBARGO_RECURSO_EXTRAORDINARIO);
        assertThat(testProcesso.getFolhasRecursoEspecial()).isEqualTo(UPDATED_FOLHAS_RECURSO_ESPECIAL);
        assertThat(testProcesso.getAcordaoRecursoEspecial()).isEqualTo(UPDATED_ACORDAO_RECURSO_ESPECIAL);
        assertThat(testProcesso.getFolhasCienciaJulgamentoRecursoEspecial()).isEqualTo(UPDATED_FOLHAS_CIENCIA_JULGAMENTO_RECURSO_ESPECIAL);
        assertThat(testProcesso.getEmbargoRecursoEspecial()).isEqualTo(UPDATED_EMBARGO_RECURSO_ESPECIAL);
        assertThat(testProcesso.getFolhasCiencia()).isEqualTo(UPDATED_FOLHAS_CIENCIA);
        assertThat(testProcesso.getAgravoRespRe()).isEqualTo(UPDATED_AGRAVO_RESP_RE);
        assertThat(testProcesso.getFolhasRespRe()).isEqualTo(UPDATED_FOLHAS_RESP_RE);
        assertThat(testProcesso.getAcordaoAgravoRespRe()).isEqualTo(UPDATED_ACORDAO_AGRAVO_RESP_RE);
        assertThat(testProcesso.getFolhasCienciaJulgamentoAgravoRespRe()).isEqualTo(UPDATED_FOLHAS_CIENCIA_JULGAMENTO_AGRAVO_RESP_RE);
        assertThat(testProcesso.getEmbargoRespRe()).isEqualTo(UPDATED_EMBARGO_RESP_RE);
        assertThat(testProcesso.getAgravoInterno()).isEqualTo(UPDATED_AGRAVO_INTERNO);
        assertThat(testProcesso.getFolhasAgravoInterno()).isEqualTo(UPDATED_FOLHAS_AGRAVO_INTERNO);
        assertThat(testProcesso.getEmbargoRecursoAgravo()).isEqualTo(UPDATED_EMBARGO_RECURSO_AGRAVO);
        assertThat(testProcesso.getObservacoes()).isEqualTo(UPDATED_OBSERVACOES);
        assertThat(testProcesso.getRecursoSTJ()).isEqualTo(UPDATED_RECURSO_STJ);
        assertThat(testProcesso.getLinkRecursoSTJ()).isEqualTo(UPDATED_LINK_RECURSO_STJ);
        assertThat(testProcesso.getFolhasRecursoSTJ()).isEqualTo(UPDATED_FOLHAS_RECURSO_STJ);
        assertThat(testProcesso.getRecursoSTF()).isEqualTo(UPDATED_RECURSO_STF);
        assertThat(testProcesso.getLinkRecursoSTF()).isEqualTo(UPDATED_LINK_RECURSO_STF);
        assertThat(testProcesso.getFolhasRecursoSTF()).isEqualTo(UPDATED_FOLHAS_RECURSO_STF);
        assertThat(testProcesso.getFolhasMemorialMPF()).isEqualTo(UPDATED_FOLHAS_MEMORIAL_MPF);
        assertThat(testProcesso.getExecusaoProvisoria()).isEqualTo(UPDATED_EXECUSAO_PROVISORIA);
        assertThat(testProcesso.getNumeracaoExecusaoProvisoria()).isEqualTo(UPDATED_NUMERACAO_EXECUSAO_PROVISORIA);
        assertThat(testProcesso.getRecuperacaoEfetivaCumprimentoSentenca()).isEqualTo(UPDATED_RECUPERACAO_EFETIVA_CUMPRIMENTO_SENTENCA);
        assertThat(testProcesso.getRecuperacaoEfetivaCumprimentoSentencaObservacoes())
            .isEqualTo(UPDATED_RECUPERACAO_EFETIVA_CUMPRIMENTO_SENTENCA_OBSERVACOES);
        assertThat(testProcesso.getEnvolveEmpreendimento()).isEqualTo(UPDATED_ENVOLVE_EMPREENDIMENTO);
        assertThat(testProcesso.getEnvolveExploracaoIlegal()).isEqualTo(UPDATED_ENVOLVE_EXPLORACAO_ILEGAL);
        assertThat(testProcesso.getEnvolveTerraQuilombola()).isEqualTo(UPDATED_ENVOLVE_TERRA_QUILOMBOLA);
        assertThat(testProcesso.getEnvolveTerraComunidadeTradicional()).isEqualTo(UPDATED_ENVOLVE_TERRA_COMUNIDADE_TRADICIONAL);
        assertThat(testProcesso.getEnvolveTerraIndigena()).isEqualTo(UPDATED_ENVOLVE_TERRA_INDIGENA);
        assertThat(testProcesso.getResumoFatos()).isEqualTo(UPDATED_RESUMO_FATOS);
        assertThat(testProcesso.getTamanhoArea()).isEqualTo(UPDATED_TAMANHO_AREA);
        assertThat(testProcesso.getValorArea()).isEqualTo(UPDATED_VALOR_AREA);
        assertThat(testProcesso.getTamanhoAreaObservacao()).isEqualTo(UPDATED_TAMANHO_AREA_OBSERVACAO);
        assertThat(testProcesso.getDadosGeograficosLitigioConflito()).isEqualTo(UPDATED_DADOS_GEOGRAFICOS_LITIGIO_CONFLITO);
        assertThat(testProcesso.getLatitude()).isEqualTo(UPDATED_LATITUDE);
        assertThat(testProcesso.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
        assertThat(testProcesso.getNumeroProcessoMPF()).isEqualTo(UPDATED_NUMERO_PROCESSO_MPF);
        assertThat(testProcesso.getNumeroEmbargo()).isEqualTo(UPDATED_NUMERO_EMBARGO);
        assertThat(testProcesso.getPautaApelacao()).isEqualTo(UPDATED_PAUTA_APELACAO);
        assertThat(testProcesso.getNumeroRecursoEspecial()).isEqualTo(UPDATED_NUMERO_RECURSO_ESPECIAL);
        assertThat(testProcesso.getAdmissiblidade()).isEqualTo(UPDATED_ADMISSIBLIDADE);
        assertThat(testProcesso.getEnvolveGrandeProjeto()).isEqualTo(UPDATED_ENVOLVE_GRANDE_PROJETO);
        assertThat(testProcesso.getEnvolveUnidadeConservacao()).isEqualTo(UPDATED_ENVOLVE_UNIDADE_CONSERVACAO);
        assertThat(testProcesso.getLinkReferencia()).isEqualTo(UPDATED_LINK_REFERENCIA);
        assertThat(testProcesso.getStatusProcesso()).isEqualTo(UPDATED_STATUS_PROCESSO);
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
            .numeroProcesso(UPDATED_NUMERO_PROCESSO)
            .assunto(UPDATED_ASSUNTO)
            .linkUnico(UPDATED_LINK_UNICO)
            .linkTrf(UPDATED_LINK_TRF)
            .turmaTrf1(UPDATED_TURMA_TRF_1)
            .numeroProcessoAdministrativo(UPDATED_NUMERO_PROCESSO_ADMINISTRATIVO)
            .numeroProcessoJudicialPrimeiraInstanciaLink(UPDATED_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA_LINK)
            .folhasProcessoConcessaoLiminar(UPDATED_FOLHAS_PROCESSO_CONCESSAO_LIMINAR)
            .concessaoLiminarObservacoes(UPDATED_CONCESSAO_LIMINAR_OBSERVACOES)
            .folhasParecer(UPDATED_FOLHAS_PARECER)
            .folhasCienciaJulgEmbargos(UPDATED_FOLHAS_CIENCIA_JULG_EMBARGOS)
            .folhasApelacao(UPDATED_FOLHAS_APELACAO)
            .folhasCienciaJulgApelacao(UPDATED_FOLHAS_CIENCIA_JULG_APELACAO)
            .embargoRecursoExtraordinario(UPDATED_EMBARGO_RECURSO_EXTRAORDINARIO)
            .folhasCienciaJulgamentoRecursoEspecial(UPDATED_FOLHAS_CIENCIA_JULGAMENTO_RECURSO_ESPECIAL)
            .embargoRecursoEspecial(UPDATED_EMBARGO_RECURSO_ESPECIAL)
            .agravoRespRe(UPDATED_AGRAVO_RESP_RE)
            .acordaoAgravoRespRe(UPDATED_ACORDAO_AGRAVO_RESP_RE)
            .folhasCienciaJulgamentoAgravoRespRe(UPDATED_FOLHAS_CIENCIA_JULGAMENTO_AGRAVO_RESP_RE)
            .embargoRespRe(UPDATED_EMBARGO_RESP_RE)
            .agravoInterno(UPDATED_AGRAVO_INTERNO)
            .embargoRecursoAgravo(UPDATED_EMBARGO_RECURSO_AGRAVO)
            .recursoSTJ(UPDATED_RECURSO_STJ)
            .linkRecursoSTJ(UPDATED_LINK_RECURSO_STJ)
            .linkRecursoSTF(UPDATED_LINK_RECURSO_STF)
            .numeracaoExecusaoProvisoria(UPDATED_NUMERACAO_EXECUSAO_PROVISORIA)
            .recuperacaoEfetivaCumprimentoSentenca(UPDATED_RECUPERACAO_EFETIVA_CUMPRIMENTO_SENTENCA)
            .recuperacaoEfetivaCumprimentoSentencaObservacoes(UPDATED_RECUPERACAO_EFETIVA_CUMPRIMENTO_SENTENCA_OBSERVACOES)
            .envolveTerraQuilombola(UPDATED_ENVOLVE_TERRA_QUILOMBOLA)
            .envolveTerraIndigena(UPDATED_ENVOLVE_TERRA_INDIGENA)
            .resumoFatos(UPDATED_RESUMO_FATOS)
            .tamanhoArea(UPDATED_TAMANHO_AREA)
            .valorArea(UPDATED_VALOR_AREA)
            .tamanhoAreaObservacao(UPDATED_TAMANHO_AREA_OBSERVACAO)
            .dadosGeograficosLitigioConflito(UPDATED_DADOS_GEOGRAFICOS_LITIGIO_CONFLITO)
            .latitude(UPDATED_LATITUDE)
            .numeroProcessoMPF(UPDATED_NUMERO_PROCESSO_MPF)
            .admissiblidade(UPDATED_ADMISSIBLIDADE)
            .envolveUnidadeConservacao(UPDATED_ENVOLVE_UNIDADE_CONSERVACAO)
            .linkReferencia(UPDATED_LINK_REFERENCIA)
            .statusProcesso(UPDATED_STATUS_PROCESSO);

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
        assertThat(testProcesso.getNumeroProcesso()).isEqualTo(UPDATED_NUMERO_PROCESSO);
        assertThat(testProcesso.getOficio()).isEqualTo(DEFAULT_OFICIO);
        assertThat(testProcesso.getAssunto()).isEqualTo(UPDATED_ASSUNTO);
        assertThat(testProcesso.getLinkUnico()).isEqualTo(UPDATED_LINK_UNICO);
        assertThat(testProcesso.getLinkTrf()).isEqualTo(UPDATED_LINK_TRF);
        assertThat(testProcesso.getTurmaTrf1()).isEqualTo(UPDATED_TURMA_TRF_1);
        assertThat(testProcesso.getNumeroProcessoAdministrativo()).isEqualTo(UPDATED_NUMERO_PROCESSO_ADMINISTRATIVO);
        assertThat(testProcesso.getNumeroProcessoJudicialPrimeiraInstancia())
            .isEqualTo(DEFAULT_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA);
        assertThat(testProcesso.getNumeroProcessoJudicialPrimeiraInstanciaLink())
            .isEqualTo(UPDATED_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA_LINK);
        assertThat(testProcesso.getNumeroProcessoJudicialPrimeiraInstanciaObservacoes())
            .isEqualTo(DEFAULT_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA_OBSERVACOES);
        assertThat(testProcesso.getParecer()).isEqualTo(DEFAULT_PARECER);
        assertThat(testProcesso.getFolhasProcessoConcessaoLiminar()).isEqualTo(UPDATED_FOLHAS_PROCESSO_CONCESSAO_LIMINAR);
        assertThat(testProcesso.getConcessaoLiminarObservacoes()).isEqualTo(UPDATED_CONCESSAO_LIMINAR_OBSERVACOES);
        assertThat(testProcesso.getFolhasProcessoCassacao()).isEqualTo(DEFAULT_FOLHAS_PROCESSO_CASSACAO);
        assertThat(testProcesso.getFolhasParecer()).isEqualTo(UPDATED_FOLHAS_PARECER);
        assertThat(testProcesso.getFolhasEmbargo()).isEqualTo(DEFAULT_FOLHAS_EMBARGO);
        assertThat(testProcesso.getAcordaoEmbargo()).isEqualTo(DEFAULT_ACORDAO_EMBARGO);
        assertThat(testProcesso.getFolhasCienciaJulgEmbargos()).isEqualTo(UPDATED_FOLHAS_CIENCIA_JULG_EMBARGOS);
        assertThat(testProcesso.getApelacao()).isEqualTo(DEFAULT_APELACAO);
        assertThat(testProcesso.getFolhasApelacao()).isEqualTo(UPDATED_FOLHAS_APELACAO);
        assertThat(testProcesso.getAcordaoApelacao()).isEqualTo(DEFAULT_ACORDAO_APELACAO);
        assertThat(testProcesso.getFolhasCienciaJulgApelacao()).isEqualTo(UPDATED_FOLHAS_CIENCIA_JULG_APELACAO);
        assertThat(testProcesso.getEmbargoDeclaracao()).isEqualTo(DEFAULT_EMBARGO_DECLARACAO);
        assertThat(testProcesso.getEmbargoRecursoExtraordinario()).isEqualTo(UPDATED_EMBARGO_RECURSO_EXTRAORDINARIO);
        assertThat(testProcesso.getFolhasRecursoEspecial()).isEqualTo(DEFAULT_FOLHAS_RECURSO_ESPECIAL);
        assertThat(testProcesso.getAcordaoRecursoEspecial()).isEqualTo(DEFAULT_ACORDAO_RECURSO_ESPECIAL);
        assertThat(testProcesso.getFolhasCienciaJulgamentoRecursoEspecial()).isEqualTo(UPDATED_FOLHAS_CIENCIA_JULGAMENTO_RECURSO_ESPECIAL);
        assertThat(testProcesso.getEmbargoRecursoEspecial()).isEqualTo(UPDATED_EMBARGO_RECURSO_ESPECIAL);
        assertThat(testProcesso.getFolhasCiencia()).isEqualTo(DEFAULT_FOLHAS_CIENCIA);
        assertThat(testProcesso.getAgravoRespRe()).isEqualTo(UPDATED_AGRAVO_RESP_RE);
        assertThat(testProcesso.getFolhasRespRe()).isEqualTo(DEFAULT_FOLHAS_RESP_RE);
        assertThat(testProcesso.getAcordaoAgravoRespRe()).isEqualTo(UPDATED_ACORDAO_AGRAVO_RESP_RE);
        assertThat(testProcesso.getFolhasCienciaJulgamentoAgravoRespRe()).isEqualTo(UPDATED_FOLHAS_CIENCIA_JULGAMENTO_AGRAVO_RESP_RE);
        assertThat(testProcesso.getEmbargoRespRe()).isEqualTo(UPDATED_EMBARGO_RESP_RE);
        assertThat(testProcesso.getAgravoInterno()).isEqualTo(UPDATED_AGRAVO_INTERNO);
        assertThat(testProcesso.getFolhasAgravoInterno()).isEqualTo(DEFAULT_FOLHAS_AGRAVO_INTERNO);
        assertThat(testProcesso.getEmbargoRecursoAgravo()).isEqualTo(UPDATED_EMBARGO_RECURSO_AGRAVO);
        assertThat(testProcesso.getObservacoes()).isEqualTo(DEFAULT_OBSERVACOES);
        assertThat(testProcesso.getRecursoSTJ()).isEqualTo(UPDATED_RECURSO_STJ);
        assertThat(testProcesso.getLinkRecursoSTJ()).isEqualTo(UPDATED_LINK_RECURSO_STJ);
        assertThat(testProcesso.getFolhasRecursoSTJ()).isEqualTo(DEFAULT_FOLHAS_RECURSO_STJ);
        assertThat(testProcesso.getRecursoSTF()).isEqualTo(DEFAULT_RECURSO_STF);
        assertThat(testProcesso.getLinkRecursoSTF()).isEqualTo(UPDATED_LINK_RECURSO_STF);
        assertThat(testProcesso.getFolhasRecursoSTF()).isEqualTo(DEFAULT_FOLHAS_RECURSO_STF);
        assertThat(testProcesso.getFolhasMemorialMPF()).isEqualTo(DEFAULT_FOLHAS_MEMORIAL_MPF);
        assertThat(testProcesso.getExecusaoProvisoria()).isEqualTo(DEFAULT_EXECUSAO_PROVISORIA);
        assertThat(testProcesso.getNumeracaoExecusaoProvisoria()).isEqualTo(UPDATED_NUMERACAO_EXECUSAO_PROVISORIA);
        assertThat(testProcesso.getRecuperacaoEfetivaCumprimentoSentenca()).isEqualTo(UPDATED_RECUPERACAO_EFETIVA_CUMPRIMENTO_SENTENCA);
        assertThat(testProcesso.getRecuperacaoEfetivaCumprimentoSentencaObservacoes())
            .isEqualTo(UPDATED_RECUPERACAO_EFETIVA_CUMPRIMENTO_SENTENCA_OBSERVACOES);
        assertThat(testProcesso.getEnvolveEmpreendimento()).isEqualTo(DEFAULT_ENVOLVE_EMPREENDIMENTO);
        assertThat(testProcesso.getEnvolveExploracaoIlegal()).isEqualTo(DEFAULT_ENVOLVE_EXPLORACAO_ILEGAL);
        assertThat(testProcesso.getEnvolveTerraQuilombola()).isEqualTo(UPDATED_ENVOLVE_TERRA_QUILOMBOLA);
        assertThat(testProcesso.getEnvolveTerraComunidadeTradicional()).isEqualTo(DEFAULT_ENVOLVE_TERRA_COMUNIDADE_TRADICIONAL);
        assertThat(testProcesso.getEnvolveTerraIndigena()).isEqualTo(UPDATED_ENVOLVE_TERRA_INDIGENA);
        assertThat(testProcesso.getResumoFatos()).isEqualTo(UPDATED_RESUMO_FATOS);
        assertThat(testProcesso.getTamanhoArea()).isEqualByComparingTo(UPDATED_TAMANHO_AREA);
        assertThat(testProcesso.getValorArea()).isEqualByComparingTo(UPDATED_VALOR_AREA);
        assertThat(testProcesso.getTamanhoAreaObservacao()).isEqualTo(UPDATED_TAMANHO_AREA_OBSERVACAO);
        assertThat(testProcesso.getDadosGeograficosLitigioConflito()).isEqualTo(UPDATED_DADOS_GEOGRAFICOS_LITIGIO_CONFLITO);
        assertThat(testProcesso.getLatitude()).isEqualTo(UPDATED_LATITUDE);
        assertThat(testProcesso.getLongitude()).isEqualTo(DEFAULT_LONGITUDE);
        assertThat(testProcesso.getNumeroProcessoMPF()).isEqualTo(UPDATED_NUMERO_PROCESSO_MPF);
        assertThat(testProcesso.getNumeroEmbargo()).isEqualTo(DEFAULT_NUMERO_EMBARGO);
        assertThat(testProcesso.getPautaApelacao()).isEqualTo(DEFAULT_PAUTA_APELACAO);
        assertThat(testProcesso.getNumeroRecursoEspecial()).isEqualTo(DEFAULT_NUMERO_RECURSO_ESPECIAL);
        assertThat(testProcesso.getAdmissiblidade()).isEqualTo(UPDATED_ADMISSIBLIDADE);
        assertThat(testProcesso.getEnvolveGrandeProjeto()).isEqualTo(DEFAULT_ENVOLVE_GRANDE_PROJETO);
        assertThat(testProcesso.getEnvolveUnidadeConservacao()).isEqualTo(UPDATED_ENVOLVE_UNIDADE_CONSERVACAO);
        assertThat(testProcesso.getLinkReferencia()).isEqualTo(UPDATED_LINK_REFERENCIA);
        assertThat(testProcesso.getStatusProcesso()).isEqualTo(UPDATED_STATUS_PROCESSO);
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
            .numeroProcesso(UPDATED_NUMERO_PROCESSO)
            .oficio(UPDATED_OFICIO)
            .assunto(UPDATED_ASSUNTO)
            .linkUnico(UPDATED_LINK_UNICO)
            .linkTrf(UPDATED_LINK_TRF)
            .turmaTrf1(UPDATED_TURMA_TRF_1)
            .numeroProcessoAdministrativo(UPDATED_NUMERO_PROCESSO_ADMINISTRATIVO)
            .numeroProcessoJudicialPrimeiraInstancia(UPDATED_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA)
            .numeroProcessoJudicialPrimeiraInstanciaLink(UPDATED_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA_LINK)
            .numeroProcessoJudicialPrimeiraInstanciaObservacoes(UPDATED_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA_OBSERVACOES)
            .parecer(UPDATED_PARECER)
            .folhasProcessoConcessaoLiminar(UPDATED_FOLHAS_PROCESSO_CONCESSAO_LIMINAR)
            .concessaoLiminarObservacoes(UPDATED_CONCESSAO_LIMINAR_OBSERVACOES)
            .folhasProcessoCassacao(UPDATED_FOLHAS_PROCESSO_CASSACAO)
            .folhasParecer(UPDATED_FOLHAS_PARECER)
            .folhasEmbargo(UPDATED_FOLHAS_EMBARGO)
            .acordaoEmbargo(UPDATED_ACORDAO_EMBARGO)
            .folhasCienciaJulgEmbargos(UPDATED_FOLHAS_CIENCIA_JULG_EMBARGOS)
            .apelacao(UPDATED_APELACAO)
            .folhasApelacao(UPDATED_FOLHAS_APELACAO)
            .acordaoApelacao(UPDATED_ACORDAO_APELACAO)
            .folhasCienciaJulgApelacao(UPDATED_FOLHAS_CIENCIA_JULG_APELACAO)
            .embargoDeclaracao(UPDATED_EMBARGO_DECLARACAO)
            .embargoRecursoExtraordinario(UPDATED_EMBARGO_RECURSO_EXTRAORDINARIO)
            .folhasRecursoEspecial(UPDATED_FOLHAS_RECURSO_ESPECIAL)
            .acordaoRecursoEspecial(UPDATED_ACORDAO_RECURSO_ESPECIAL)
            .folhasCienciaJulgamentoRecursoEspecial(UPDATED_FOLHAS_CIENCIA_JULGAMENTO_RECURSO_ESPECIAL)
            .embargoRecursoEspecial(UPDATED_EMBARGO_RECURSO_ESPECIAL)
            .folhasCiencia(UPDATED_FOLHAS_CIENCIA)
            .agravoRespRe(UPDATED_AGRAVO_RESP_RE)
            .folhasRespRe(UPDATED_FOLHAS_RESP_RE)
            .acordaoAgravoRespRe(UPDATED_ACORDAO_AGRAVO_RESP_RE)
            .folhasCienciaJulgamentoAgravoRespRe(UPDATED_FOLHAS_CIENCIA_JULGAMENTO_AGRAVO_RESP_RE)
            .embargoRespRe(UPDATED_EMBARGO_RESP_RE)
            .agravoInterno(UPDATED_AGRAVO_INTERNO)
            .folhasAgravoInterno(UPDATED_FOLHAS_AGRAVO_INTERNO)
            .embargoRecursoAgravo(UPDATED_EMBARGO_RECURSO_AGRAVO)
            .observacoes(UPDATED_OBSERVACOES)
            .recursoSTJ(UPDATED_RECURSO_STJ)
            .linkRecursoSTJ(UPDATED_LINK_RECURSO_STJ)
            .folhasRecursoSTJ(UPDATED_FOLHAS_RECURSO_STJ)
            .recursoSTF(UPDATED_RECURSO_STF)
            .linkRecursoSTF(UPDATED_LINK_RECURSO_STF)
            .folhasRecursoSTF(UPDATED_FOLHAS_RECURSO_STF)
            .folhasMemorialMPF(UPDATED_FOLHAS_MEMORIAL_MPF)
            .execusaoProvisoria(UPDATED_EXECUSAO_PROVISORIA)
            .numeracaoExecusaoProvisoria(UPDATED_NUMERACAO_EXECUSAO_PROVISORIA)
            .recuperacaoEfetivaCumprimentoSentenca(UPDATED_RECUPERACAO_EFETIVA_CUMPRIMENTO_SENTENCA)
            .recuperacaoEfetivaCumprimentoSentencaObservacoes(UPDATED_RECUPERACAO_EFETIVA_CUMPRIMENTO_SENTENCA_OBSERVACOES)
            .envolveEmpreendimento(UPDATED_ENVOLVE_EMPREENDIMENTO)
            .envolveExploracaoIlegal(UPDATED_ENVOLVE_EXPLORACAO_ILEGAL)
            .envolveTerraQuilombola(UPDATED_ENVOLVE_TERRA_QUILOMBOLA)
            .envolveTerraComunidadeTradicional(UPDATED_ENVOLVE_TERRA_COMUNIDADE_TRADICIONAL)
            .envolveTerraIndigena(UPDATED_ENVOLVE_TERRA_INDIGENA)
            .resumoFatos(UPDATED_RESUMO_FATOS)
            .tamanhoArea(UPDATED_TAMANHO_AREA)
            .valorArea(UPDATED_VALOR_AREA)
            .tamanhoAreaObservacao(UPDATED_TAMANHO_AREA_OBSERVACAO)
            .dadosGeograficosLitigioConflito(UPDATED_DADOS_GEOGRAFICOS_LITIGIO_CONFLITO)
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE)
            .numeroProcessoMPF(UPDATED_NUMERO_PROCESSO_MPF)
            .numeroEmbargo(UPDATED_NUMERO_EMBARGO)
            .pautaApelacao(UPDATED_PAUTA_APELACAO)
            .numeroRecursoEspecial(UPDATED_NUMERO_RECURSO_ESPECIAL)
            .admissiblidade(UPDATED_ADMISSIBLIDADE)
            .envolveGrandeProjeto(UPDATED_ENVOLVE_GRANDE_PROJETO)
            .envolveUnidadeConservacao(UPDATED_ENVOLVE_UNIDADE_CONSERVACAO)
            .linkReferencia(UPDATED_LINK_REFERENCIA)
            .statusProcesso(UPDATED_STATUS_PROCESSO);

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
        assertThat(testProcesso.getNumeroProcesso()).isEqualTo(UPDATED_NUMERO_PROCESSO);
        assertThat(testProcesso.getOficio()).isEqualTo(UPDATED_OFICIO);
        assertThat(testProcesso.getAssunto()).isEqualTo(UPDATED_ASSUNTO);
        assertThat(testProcesso.getLinkUnico()).isEqualTo(UPDATED_LINK_UNICO);
        assertThat(testProcesso.getLinkTrf()).isEqualTo(UPDATED_LINK_TRF);
        assertThat(testProcesso.getTurmaTrf1()).isEqualTo(UPDATED_TURMA_TRF_1);
        assertThat(testProcesso.getNumeroProcessoAdministrativo()).isEqualTo(UPDATED_NUMERO_PROCESSO_ADMINISTRATIVO);
        assertThat(testProcesso.getNumeroProcessoJudicialPrimeiraInstancia())
            .isEqualTo(UPDATED_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA);
        assertThat(testProcesso.getNumeroProcessoJudicialPrimeiraInstanciaLink())
            .isEqualTo(UPDATED_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA_LINK);
        assertThat(testProcesso.getNumeroProcessoJudicialPrimeiraInstanciaObservacoes())
            .isEqualTo(UPDATED_NUMERO_PROCESSO_JUDICIAL_PRIMEIRA_INSTANCIA_OBSERVACOES);
        assertThat(testProcesso.getParecer()).isEqualTo(UPDATED_PARECER);
        assertThat(testProcesso.getFolhasProcessoConcessaoLiminar()).isEqualTo(UPDATED_FOLHAS_PROCESSO_CONCESSAO_LIMINAR);
        assertThat(testProcesso.getConcessaoLiminarObservacoes()).isEqualTo(UPDATED_CONCESSAO_LIMINAR_OBSERVACOES);
        assertThat(testProcesso.getFolhasProcessoCassacao()).isEqualTo(UPDATED_FOLHAS_PROCESSO_CASSACAO);
        assertThat(testProcesso.getFolhasParecer()).isEqualTo(UPDATED_FOLHAS_PARECER);
        assertThat(testProcesso.getFolhasEmbargo()).isEqualTo(UPDATED_FOLHAS_EMBARGO);
        assertThat(testProcesso.getAcordaoEmbargo()).isEqualTo(UPDATED_ACORDAO_EMBARGO);
        assertThat(testProcesso.getFolhasCienciaJulgEmbargos()).isEqualTo(UPDATED_FOLHAS_CIENCIA_JULG_EMBARGOS);
        assertThat(testProcesso.getApelacao()).isEqualTo(UPDATED_APELACAO);
        assertThat(testProcesso.getFolhasApelacao()).isEqualTo(UPDATED_FOLHAS_APELACAO);
        assertThat(testProcesso.getAcordaoApelacao()).isEqualTo(UPDATED_ACORDAO_APELACAO);
        assertThat(testProcesso.getFolhasCienciaJulgApelacao()).isEqualTo(UPDATED_FOLHAS_CIENCIA_JULG_APELACAO);
        assertThat(testProcesso.getEmbargoDeclaracao()).isEqualTo(UPDATED_EMBARGO_DECLARACAO);
        assertThat(testProcesso.getEmbargoRecursoExtraordinario()).isEqualTo(UPDATED_EMBARGO_RECURSO_EXTRAORDINARIO);
        assertThat(testProcesso.getFolhasRecursoEspecial()).isEqualTo(UPDATED_FOLHAS_RECURSO_ESPECIAL);
        assertThat(testProcesso.getAcordaoRecursoEspecial()).isEqualTo(UPDATED_ACORDAO_RECURSO_ESPECIAL);
        assertThat(testProcesso.getFolhasCienciaJulgamentoRecursoEspecial()).isEqualTo(UPDATED_FOLHAS_CIENCIA_JULGAMENTO_RECURSO_ESPECIAL);
        assertThat(testProcesso.getEmbargoRecursoEspecial()).isEqualTo(UPDATED_EMBARGO_RECURSO_ESPECIAL);
        assertThat(testProcesso.getFolhasCiencia()).isEqualTo(UPDATED_FOLHAS_CIENCIA);
        assertThat(testProcesso.getAgravoRespRe()).isEqualTo(UPDATED_AGRAVO_RESP_RE);
        assertThat(testProcesso.getFolhasRespRe()).isEqualTo(UPDATED_FOLHAS_RESP_RE);
        assertThat(testProcesso.getAcordaoAgravoRespRe()).isEqualTo(UPDATED_ACORDAO_AGRAVO_RESP_RE);
        assertThat(testProcesso.getFolhasCienciaJulgamentoAgravoRespRe()).isEqualTo(UPDATED_FOLHAS_CIENCIA_JULGAMENTO_AGRAVO_RESP_RE);
        assertThat(testProcesso.getEmbargoRespRe()).isEqualTo(UPDATED_EMBARGO_RESP_RE);
        assertThat(testProcesso.getAgravoInterno()).isEqualTo(UPDATED_AGRAVO_INTERNO);
        assertThat(testProcesso.getFolhasAgravoInterno()).isEqualTo(UPDATED_FOLHAS_AGRAVO_INTERNO);
        assertThat(testProcesso.getEmbargoRecursoAgravo()).isEqualTo(UPDATED_EMBARGO_RECURSO_AGRAVO);
        assertThat(testProcesso.getObservacoes()).isEqualTo(UPDATED_OBSERVACOES);
        assertThat(testProcesso.getRecursoSTJ()).isEqualTo(UPDATED_RECURSO_STJ);
        assertThat(testProcesso.getLinkRecursoSTJ()).isEqualTo(UPDATED_LINK_RECURSO_STJ);
        assertThat(testProcesso.getFolhasRecursoSTJ()).isEqualTo(UPDATED_FOLHAS_RECURSO_STJ);
        assertThat(testProcesso.getRecursoSTF()).isEqualTo(UPDATED_RECURSO_STF);
        assertThat(testProcesso.getLinkRecursoSTF()).isEqualTo(UPDATED_LINK_RECURSO_STF);
        assertThat(testProcesso.getFolhasRecursoSTF()).isEqualTo(UPDATED_FOLHAS_RECURSO_STF);
        assertThat(testProcesso.getFolhasMemorialMPF()).isEqualTo(UPDATED_FOLHAS_MEMORIAL_MPF);
        assertThat(testProcesso.getExecusaoProvisoria()).isEqualTo(UPDATED_EXECUSAO_PROVISORIA);
        assertThat(testProcesso.getNumeracaoExecusaoProvisoria()).isEqualTo(UPDATED_NUMERACAO_EXECUSAO_PROVISORIA);
        assertThat(testProcesso.getRecuperacaoEfetivaCumprimentoSentenca()).isEqualTo(UPDATED_RECUPERACAO_EFETIVA_CUMPRIMENTO_SENTENCA);
        assertThat(testProcesso.getRecuperacaoEfetivaCumprimentoSentencaObservacoes())
            .isEqualTo(UPDATED_RECUPERACAO_EFETIVA_CUMPRIMENTO_SENTENCA_OBSERVACOES);
        assertThat(testProcesso.getEnvolveEmpreendimento()).isEqualTo(UPDATED_ENVOLVE_EMPREENDIMENTO);
        assertThat(testProcesso.getEnvolveExploracaoIlegal()).isEqualTo(UPDATED_ENVOLVE_EXPLORACAO_ILEGAL);
        assertThat(testProcesso.getEnvolveTerraQuilombola()).isEqualTo(UPDATED_ENVOLVE_TERRA_QUILOMBOLA);
        assertThat(testProcesso.getEnvolveTerraComunidadeTradicional()).isEqualTo(UPDATED_ENVOLVE_TERRA_COMUNIDADE_TRADICIONAL);
        assertThat(testProcesso.getEnvolveTerraIndigena()).isEqualTo(UPDATED_ENVOLVE_TERRA_INDIGENA);
        assertThat(testProcesso.getResumoFatos()).isEqualTo(UPDATED_RESUMO_FATOS);
        assertThat(testProcesso.getTamanhoArea()).isEqualByComparingTo(UPDATED_TAMANHO_AREA);
        assertThat(testProcesso.getValorArea()).isEqualByComparingTo(UPDATED_VALOR_AREA);
        assertThat(testProcesso.getTamanhoAreaObservacao()).isEqualTo(UPDATED_TAMANHO_AREA_OBSERVACAO);
        assertThat(testProcesso.getDadosGeograficosLitigioConflito()).isEqualTo(UPDATED_DADOS_GEOGRAFICOS_LITIGIO_CONFLITO);
        assertThat(testProcesso.getLatitude()).isEqualTo(UPDATED_LATITUDE);
        assertThat(testProcesso.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
        assertThat(testProcesso.getNumeroProcessoMPF()).isEqualTo(UPDATED_NUMERO_PROCESSO_MPF);
        assertThat(testProcesso.getNumeroEmbargo()).isEqualTo(UPDATED_NUMERO_EMBARGO);
        assertThat(testProcesso.getPautaApelacao()).isEqualTo(UPDATED_PAUTA_APELACAO);
        assertThat(testProcesso.getNumeroRecursoEspecial()).isEqualTo(UPDATED_NUMERO_RECURSO_ESPECIAL);
        assertThat(testProcesso.getAdmissiblidade()).isEqualTo(UPDATED_ADMISSIBLIDADE);
        assertThat(testProcesso.getEnvolveGrandeProjeto()).isEqualTo(UPDATED_ENVOLVE_GRANDE_PROJETO);
        assertThat(testProcesso.getEnvolveUnidadeConservacao()).isEqualTo(UPDATED_ENVOLVE_UNIDADE_CONSERVACAO);
        assertThat(testProcesso.getLinkReferencia()).isEqualTo(UPDATED_LINK_REFERENCIA);
        assertThat(testProcesso.getStatusProcesso()).isEqualTo(UPDATED_STATUS_PROCESSO);
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
