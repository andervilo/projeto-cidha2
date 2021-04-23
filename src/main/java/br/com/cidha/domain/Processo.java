package br.com.cidha.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

/**
 * A Processo.
 */
@Entity
@Table(name = "processo")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Processo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "numero_processo")
    private String numeroProcesso;

    @Column(name = "oficio")
    private String oficio;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "assunto")
    private String assunto;

    @Column(name = "link_unico")
    private String linkUnico;

    @Column(name = "link_trf")
    private String linkTrf;

    @Column(name = "secao_judiciaria")
    private String secaoJudiciaria;

    @Column(name = "subsecao_judiciaria")
    private String subsecaoJudiciaria;

    @Column(name = "turma_trf_1")
    private String turmaTrf1;

    @Column(name = "numero_processo_administrativo")
    private String numeroProcessoAdministrativo;

    @Column(name = "numero_processo_judicial_primeira_instancia")
    private String numeroProcessoJudicialPrimeiraInstancia;

    @Column(name = "numero_processo_judicial_primeira_instancia_link")
    private String numeroProcessoJudicialPrimeiraInstanciaLink;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "numero_processo_judicial_primeira_instancia_observacoes")
    private String numeroProcessoJudicialPrimeiraInstanciaObservacoes;

    @Column(name = "parecer")
    private Boolean parecer;

    @Column(name = "folhas_processo_concessao_liminar")
    private String folhasProcessoConcessaoLiminar;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "concessao_liminar_observacoes")
    private String concessaoLiminarObservacoes;

    @Column(name = "folhas_processo_cassacao")
    private String folhasProcessoCassacao;

    @Column(name = "folhas_parecer")
    private String folhasParecer;

    @Column(name = "folhas_embargo")
    private String folhasEmbargo;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "acordao_embargo")
    private String acordaoEmbargo;

    @Column(name = "folhas_ciencia_julg_embargos")
    private String folhasCienciaJulgEmbargos;

    @Column(name = "apelacao")
    private String apelacao;

    @Column(name = "folhas_apelacao")
    private String folhasApelacao;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "acordao_apelacao")
    private String acordaoApelacao;

    @Column(name = "folhas_ciencia_julg_apelacao")
    private String folhasCienciaJulgApelacao;

    @Column(name = "embargo_declaracao")
    private Boolean embargoDeclaracao;

    @Column(name = "folhas_recurso_especial")
    private String folhasRecursoEspecial;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "acordao_recurso_especial")
    private String acordaoRecursoEspecial;

    @Column(name = "folhas_ciencia_julgamento_recurso_especial")
    private String folhasCienciaJulgamentoRecursoEspecial;

    @Column(name = "embargo_recurso_especial")
    private Boolean embargoRecursoEspecial;

    @Column(name = "folhas_ciencia")
    private String folhasCiencia;

    @Column(name = "agravo_resp_re")
    private String agravoRespRe;

    @Column(name = "folhas_resp_re")
    private String folhasRespRe;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "acordao_agravo_resp_re")
    private String acordaoAgravoRespRe;

    @Column(name = "folhas_ciencia_julgamento_agravo_resp_re")
    private String folhasCienciaJulgamentoAgravoRespRe;

    @Column(name = "embargo_resp_re")
    private String embargoRespRe;

    @Column(name = "agravo_interno")
    private String agravoInterno;

    @Column(name = "folhas_agravo_interno")
    private String folhasAgravoInterno;

    @Column(name = "embargo_recurso_agravo")
    private Boolean embargoRecursoAgravo;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "observacoes")
    private String observacoes;

    @Column(name = "recurso_stj")
    private Boolean recursoSTJ;

    @Column(name = "link_recurso_stj")
    private String linkRecursoSTJ;

    @Column(name = "folhas_recurso_stj")
    private String folhasRecursoSTJ;

    @Column(name = "recurso_stf")
    private Boolean recursoSTF;

    @Column(name = "link_recurso_stf")
    private String linkRecursoSTF;

    @Column(name = "folhas_recurso_stf")
    private String folhasRecursoSTF;

    @Column(name = "folhas_memorial_mpf")
    private String folhasMemorialMPF;

    @Column(name = "execusao_provisoria")
    private Boolean execusaoProvisoria;

    @Column(name = "numeracao_execusao_provisoria")
    private String numeracaoExecusaoProvisoria;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "recuperacao_efetiva_cumprimento_sentenca")
    private String recuperacaoEfetivaCumprimentoSentenca;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "recuperacao_efetiva_cumprimento_sentenca_observacoes")
    private String recuperacaoEfetivaCumprimentoSentencaObservacoes;

    @Column(name = "envolve_empreendimento")
    private Boolean envolveEmpreendimento;

    @Column(name = "envolve_exploracao_ilegal")
    private Boolean envolveExploracaoIlegal;

    @Column(name = "envolve_terra_quilombola")
    private Boolean envolveTerraQuilombola;

    @Column(name = "envolve_terra_comunidade_tradicional")
    private Boolean envolveTerraComunidadeTradicional;

    @Column(name = "envolve_terra_indigena")
    private Boolean envolveTerraIndigena;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "resumo_fatos")
    private String resumoFatos;

    @Column(name = "tamanho_area", precision = 21, scale = 2)
    private BigDecimal tamanhoArea;

    @Column(name = "valor_area", precision = 21, scale = 2)
    private BigDecimal valorArea;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "tamanho_area_observacao")
    private String tamanhoAreaObservacao;

    @Column(name = "dados_geograficos_litigio_conflito")
    private Boolean dadosGeograficosLitigioConflito;

    @Column(name = "latitude")
    private String latitude;

    @Column(name = "longitude")
    private String longitude;

    @Column(name = "numero_processo_mpf")
    private String numeroProcessoMPF;

    @Column(name = "numero_embargo")
    private String numeroEmbargo;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "pauta_apelacao")
    private String pautaApelacao;

    @Column(name = "numero_recurso_especial")
    private String numeroRecursoEspecial;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "admissiblidade")
    private String admissiblidade;

    @Column(name = "envolve_grande_projeto")
    private Boolean envolveGrandeProjeto;

    @Column(name = "envolve_unidade_conservacao")
    private Boolean envolveUnidadeConservacao;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "link_referencia")
    private String linkReferencia;

    @OneToMany(mappedBy = "processo")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "processo" }, allowSetters = true)
    private Set<ConcessaoLiminar> concessaoLiminars = new HashSet<>();

    @OneToMany(mappedBy = "processo")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "processo" }, allowSetters = true)
    private Set<ConcessaoLiminarCassada> concessaoLiminarCassadas = new HashSet<>();

    @OneToMany(mappedBy = "processo")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "processo" }, allowSetters = true)
    private Set<EmbargoDeclaracao> embargoDeclaracaos = new HashSet<>();

    @OneToMany(mappedBy = "processo")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "processo" }, allowSetters = true)
    private Set<EmbargoDeclaracaoAgravo> embargoDeclaracaoAgravos = new HashSet<>();

    @OneToMany(mappedBy = "processo")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "processo" }, allowSetters = true)
    private Set<EmbargoRecursoEspecial> embargoRecursoEspecials = new HashSet<>();

    @OneToMany(mappedBy = "processo")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "processo" }, allowSetters = true)
    private Set<EmbargoRespRe> embargoRespRes = new HashSet<>();

    @ManyToOne
    private TipoDecisao tipoDecisao;

    @ManyToOne
    private TipoEmpreendimento tipoEmpreendimento;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(
        name = "rel_processo__comarca",
        joinColumns = @JoinColumn(name = "processo_id"),
        inverseJoinColumns = @JoinColumn(name = "comarca_id")
    )
    @JsonIgnoreProperties(value = { "processos" }, allowSetters = true)
    private Set<Comarca> comarcas = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(
        name = "rel_processo__quilombo",
        joinColumns = @JoinColumn(name = "processo_id"),
        inverseJoinColumns = @JoinColumn(name = "quilombo_id")
    )
    @JsonIgnoreProperties(value = { "processos" }, allowSetters = true)
    private Set<Quilombo> quilombos = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(
        name = "rel_processo__municipio",
        joinColumns = @JoinColumn(name = "processo_id"),
        inverseJoinColumns = @JoinColumn(name = "municipio_id")
    )
    @JsonIgnoreProperties(value = { "processos" }, allowSetters = true)
    private Set<Municipio> municipios = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(
        name = "rel_processo__territorio",
        joinColumns = @JoinColumn(name = "processo_id"),
        inverseJoinColumns = @JoinColumn(name = "territorio_id")
    )
    @JsonIgnoreProperties(value = { "processos" }, allowSetters = true)
    private Set<Territorio> territorios = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(
        name = "rel_processo__atividade_exploracao_ilegal",
        joinColumns = @JoinColumn(name = "processo_id"),
        inverseJoinColumns = @JoinColumn(name = "atividade_exploracao_ilegal_id")
    )
    @JsonIgnoreProperties(value = { "processos" }, allowSetters = true)
    private Set<AtividadeExploracaoIlegal> atividadeExploracaoIlegals = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(
        name = "rel_processo__unidade_conservacao",
        joinColumns = @JoinColumn(name = "processo_id"),
        inverseJoinColumns = @JoinColumn(name = "unidade_conservacao_id")
    )
    @JsonIgnoreProperties(value = { "processos" }, allowSetters = true)
    private Set<UnidadeConservacao> unidadeConservacaos = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(
        name = "rel_processo__envolvidos_conflito_litigio",
        joinColumns = @JoinColumn(name = "processo_id"),
        inverseJoinColumns = @JoinColumn(name = "envolvidos_conflito_litigio_id")
    )
    @JsonIgnoreProperties(value = { "processos" }, allowSetters = true)
    private Set<EnvolvidosConflitoLitigio> envolvidosConflitoLitigios = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(
        name = "rel_processo__terra_indigena",
        joinColumns = @JoinColumn(name = "processo_id"),
        inverseJoinColumns = @JoinColumn(name = "terra_indigena_id")
    )
    @JsonIgnoreProperties(value = { "etnias", "processos" }, allowSetters = true)
    private Set<TerraIndigena> terraIndigenas = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(
        name = "rel_processo__processo_conflito",
        joinColumns = @JoinColumn(name = "processo_id"),
        inverseJoinColumns = @JoinColumn(name = "processo_conflito_id")
    )
    @JsonIgnoreProperties(value = { "conflitos", "direitos", "processos" }, allowSetters = true)
    private Set<ProcessoConflito> processoConflitos = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(
        name = "rel_processo__parte_interesssada",
        joinColumns = @JoinColumn(name = "processo_id"),
        inverseJoinColumns = @JoinColumn(name = "parte_interesssada_id")
    )
    @JsonIgnoreProperties(value = { "representanteLegals", "processos" }, allowSetters = true)
    private Set<ParteInteresssada> parteInteresssadas = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(
        name = "rel_processo__relator",
        joinColumns = @JoinColumn(name = "processo_id"),
        inverseJoinColumns = @JoinColumn(name = "relator_id")
    )
    @JsonIgnoreProperties(value = { "processos" }, allowSetters = true)
    private Set<Relator> relators = new HashSet<>();

    @ManyToMany(mappedBy = "processos")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "fundamentacaoDoutrinarias", "jurisprudencias", "fundamentacaoLegals", "instrumentoInternacionals", "processos" },
        allowSetters = true
    )
    private Set<ProblemaJuridico> problemaJuridicos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Processo id(Long id) {
        this.id = id;
        return this;
    }

    public String getNumeroProcesso() {
        return this.numeroProcesso;
    }

    public Processo numeroProcesso(String numeroProcesso) {
        this.numeroProcesso = numeroProcesso;
        return this;
    }

    public void setNumeroProcesso(String numeroProcesso) {
        this.numeroProcesso = numeroProcesso;
    }

    public String getOficio() {
        return this.oficio;
    }

    public Processo oficio(String oficio) {
        this.oficio = oficio;
        return this;
    }

    public void setOficio(String oficio) {
        this.oficio = oficio;
    }

    public String getAssunto() {
        return this.assunto;
    }

    public Processo assunto(String assunto) {
        this.assunto = assunto;
        return this;
    }

    public void setAssunto(String assunto) {
        this.assunto = assunto;
    }

    public String getLinkUnico() {
        return this.linkUnico;
    }

    public Processo linkUnico(String linkUnico) {
        this.linkUnico = linkUnico;
        return this;
    }

    public void setLinkUnico(String linkUnico) {
        this.linkUnico = linkUnico;
    }

    public String getLinkTrf() {
        return this.linkTrf;
    }

    public Processo linkTrf(String linkTrf) {
        this.linkTrf = linkTrf;
        return this;
    }

    public void setLinkTrf(String linkTrf) {
        this.linkTrf = linkTrf;
    }

    public String getSecaoJudiciaria() {
        return this.secaoJudiciaria;
    }

    public Processo secaoJudiciaria(String secaoJudiciaria) {
        this.secaoJudiciaria = secaoJudiciaria;
        return this;
    }

    public void setSecaoJudiciaria(String secaoJudiciaria) {
        this.secaoJudiciaria = secaoJudiciaria;
    }

    public String getSubsecaoJudiciaria() {
        return this.subsecaoJudiciaria;
    }

    public Processo subsecaoJudiciaria(String subsecaoJudiciaria) {
        this.subsecaoJudiciaria = subsecaoJudiciaria;
        return this;
    }

    public void setSubsecaoJudiciaria(String subsecaoJudiciaria) {
        this.subsecaoJudiciaria = subsecaoJudiciaria;
    }

    public String getTurmaTrf1() {
        return this.turmaTrf1;
    }

    public Processo turmaTrf1(String turmaTrf1) {
        this.turmaTrf1 = turmaTrf1;
        return this;
    }

    public void setTurmaTrf1(String turmaTrf1) {
        this.turmaTrf1 = turmaTrf1;
    }

    public String getNumeroProcessoAdministrativo() {
        return this.numeroProcessoAdministrativo;
    }

    public Processo numeroProcessoAdministrativo(String numeroProcessoAdministrativo) {
        this.numeroProcessoAdministrativo = numeroProcessoAdministrativo;
        return this;
    }

    public void setNumeroProcessoAdministrativo(String numeroProcessoAdministrativo) {
        this.numeroProcessoAdministrativo = numeroProcessoAdministrativo;
    }

    public String getNumeroProcessoJudicialPrimeiraInstancia() {
        return this.numeroProcessoJudicialPrimeiraInstancia;
    }

    public Processo numeroProcessoJudicialPrimeiraInstancia(String numeroProcessoJudicialPrimeiraInstancia) {
        this.numeroProcessoJudicialPrimeiraInstancia = numeroProcessoJudicialPrimeiraInstancia;
        return this;
    }

    public void setNumeroProcessoJudicialPrimeiraInstancia(String numeroProcessoJudicialPrimeiraInstancia) {
        this.numeroProcessoJudicialPrimeiraInstancia = numeroProcessoJudicialPrimeiraInstancia;
    }

    public String getNumeroProcessoJudicialPrimeiraInstanciaLink() {
        return this.numeroProcessoJudicialPrimeiraInstanciaLink;
    }

    public Processo numeroProcessoJudicialPrimeiraInstanciaLink(String numeroProcessoJudicialPrimeiraInstanciaLink) {
        this.numeroProcessoJudicialPrimeiraInstanciaLink = numeroProcessoJudicialPrimeiraInstanciaLink;
        return this;
    }

    public void setNumeroProcessoJudicialPrimeiraInstanciaLink(String numeroProcessoJudicialPrimeiraInstanciaLink) {
        this.numeroProcessoJudicialPrimeiraInstanciaLink = numeroProcessoJudicialPrimeiraInstanciaLink;
    }

    public String getNumeroProcessoJudicialPrimeiraInstanciaObservacoes() {
        return this.numeroProcessoJudicialPrimeiraInstanciaObservacoes;
    }

    public Processo numeroProcessoJudicialPrimeiraInstanciaObservacoes(String numeroProcessoJudicialPrimeiraInstanciaObservacoes) {
        this.numeroProcessoJudicialPrimeiraInstanciaObservacoes = numeroProcessoJudicialPrimeiraInstanciaObservacoes;
        return this;
    }

    public void setNumeroProcessoJudicialPrimeiraInstanciaObservacoes(String numeroProcessoJudicialPrimeiraInstanciaObservacoes) {
        this.numeroProcessoJudicialPrimeiraInstanciaObservacoes = numeroProcessoJudicialPrimeiraInstanciaObservacoes;
    }

    public Boolean getParecer() {
        return this.parecer;
    }

    public Processo parecer(Boolean parecer) {
        this.parecer = parecer;
        return this;
    }

    public void setParecer(Boolean parecer) {
        this.parecer = parecer;
    }

    public String getFolhasProcessoConcessaoLiminar() {
        return this.folhasProcessoConcessaoLiminar;
    }

    public Processo folhasProcessoConcessaoLiminar(String folhasProcessoConcessaoLiminar) {
        this.folhasProcessoConcessaoLiminar = folhasProcessoConcessaoLiminar;
        return this;
    }

    public void setFolhasProcessoConcessaoLiminar(String folhasProcessoConcessaoLiminar) {
        this.folhasProcessoConcessaoLiminar = folhasProcessoConcessaoLiminar;
    }

    public String getConcessaoLiminarObservacoes() {
        return this.concessaoLiminarObservacoes;
    }

    public Processo concessaoLiminarObservacoes(String concessaoLiminarObservacoes) {
        this.concessaoLiminarObservacoes = concessaoLiminarObservacoes;
        return this;
    }

    public void setConcessaoLiminarObservacoes(String concessaoLiminarObservacoes) {
        this.concessaoLiminarObservacoes = concessaoLiminarObservacoes;
    }

    public String getFolhasProcessoCassacao() {
        return this.folhasProcessoCassacao;
    }

    public Processo folhasProcessoCassacao(String folhasProcessoCassacao) {
        this.folhasProcessoCassacao = folhasProcessoCassacao;
        return this;
    }

    public void setFolhasProcessoCassacao(String folhasProcessoCassacao) {
        this.folhasProcessoCassacao = folhasProcessoCassacao;
    }

    public String getFolhasParecer() {
        return this.folhasParecer;
    }

    public Processo folhasParecer(String folhasParecer) {
        this.folhasParecer = folhasParecer;
        return this;
    }

    public void setFolhasParecer(String folhasParecer) {
        this.folhasParecer = folhasParecer;
    }

    public String getFolhasEmbargo() {
        return this.folhasEmbargo;
    }

    public Processo folhasEmbargo(String folhasEmbargo) {
        this.folhasEmbargo = folhasEmbargo;
        return this;
    }

    public void setFolhasEmbargo(String folhasEmbargo) {
        this.folhasEmbargo = folhasEmbargo;
    }

    public String getAcordaoEmbargo() {
        return this.acordaoEmbargo;
    }

    public Processo acordaoEmbargo(String acordaoEmbargo) {
        this.acordaoEmbargo = acordaoEmbargo;
        return this;
    }

    public void setAcordaoEmbargo(String acordaoEmbargo) {
        this.acordaoEmbargo = acordaoEmbargo;
    }

    public String getFolhasCienciaJulgEmbargos() {
        return this.folhasCienciaJulgEmbargos;
    }

    public Processo folhasCienciaJulgEmbargos(String folhasCienciaJulgEmbargos) {
        this.folhasCienciaJulgEmbargos = folhasCienciaJulgEmbargos;
        return this;
    }

    public void setFolhasCienciaJulgEmbargos(String folhasCienciaJulgEmbargos) {
        this.folhasCienciaJulgEmbargos = folhasCienciaJulgEmbargos;
    }

    public String getApelacao() {
        return this.apelacao;
    }

    public Processo apelacao(String apelacao) {
        this.apelacao = apelacao;
        return this;
    }

    public void setApelacao(String apelacao) {
        this.apelacao = apelacao;
    }

    public String getFolhasApelacao() {
        return this.folhasApelacao;
    }

    public Processo folhasApelacao(String folhasApelacao) {
        this.folhasApelacao = folhasApelacao;
        return this;
    }

    public void setFolhasApelacao(String folhasApelacao) {
        this.folhasApelacao = folhasApelacao;
    }

    public String getAcordaoApelacao() {
        return this.acordaoApelacao;
    }

    public Processo acordaoApelacao(String acordaoApelacao) {
        this.acordaoApelacao = acordaoApelacao;
        return this;
    }

    public void setAcordaoApelacao(String acordaoApelacao) {
        this.acordaoApelacao = acordaoApelacao;
    }

    public String getFolhasCienciaJulgApelacao() {
        return this.folhasCienciaJulgApelacao;
    }

    public Processo folhasCienciaJulgApelacao(String folhasCienciaJulgApelacao) {
        this.folhasCienciaJulgApelacao = folhasCienciaJulgApelacao;
        return this;
    }

    public void setFolhasCienciaJulgApelacao(String folhasCienciaJulgApelacao) {
        this.folhasCienciaJulgApelacao = folhasCienciaJulgApelacao;
    }

    public Boolean getEmbargoDeclaracao() {
        return this.embargoDeclaracao;
    }

    public Processo embargoDeclaracao(Boolean embargoDeclaracao) {
        this.embargoDeclaracao = embargoDeclaracao;
        return this;
    }

    public void setEmbargoDeclaracao(Boolean embargoDeclaracao) {
        this.embargoDeclaracao = embargoDeclaracao;
    }

    public String getFolhasRecursoEspecial() {
        return this.folhasRecursoEspecial;
    }

    public Processo folhasRecursoEspecial(String folhasRecursoEspecial) {
        this.folhasRecursoEspecial = folhasRecursoEspecial;
        return this;
    }

    public void setFolhasRecursoEspecial(String folhasRecursoEspecial) {
        this.folhasRecursoEspecial = folhasRecursoEspecial;
    }

    public String getAcordaoRecursoEspecial() {
        return this.acordaoRecursoEspecial;
    }

    public Processo acordaoRecursoEspecial(String acordaoRecursoEspecial) {
        this.acordaoRecursoEspecial = acordaoRecursoEspecial;
        return this;
    }

    public void setAcordaoRecursoEspecial(String acordaoRecursoEspecial) {
        this.acordaoRecursoEspecial = acordaoRecursoEspecial;
    }

    public String getFolhasCienciaJulgamentoRecursoEspecial() {
        return this.folhasCienciaJulgamentoRecursoEspecial;
    }

    public Processo folhasCienciaJulgamentoRecursoEspecial(String folhasCienciaJulgamentoRecursoEspecial) {
        this.folhasCienciaJulgamentoRecursoEspecial = folhasCienciaJulgamentoRecursoEspecial;
        return this;
    }

    public void setFolhasCienciaJulgamentoRecursoEspecial(String folhasCienciaJulgamentoRecursoEspecial) {
        this.folhasCienciaJulgamentoRecursoEspecial = folhasCienciaJulgamentoRecursoEspecial;
    }

    public Boolean getEmbargoRecursoEspecial() {
        return this.embargoRecursoEspecial;
    }

    public Processo embargoRecursoEspecial(Boolean embargoRecursoEspecial) {
        this.embargoRecursoEspecial = embargoRecursoEspecial;
        return this;
    }

    public void setEmbargoRecursoEspecial(Boolean embargoRecursoEspecial) {
        this.embargoRecursoEspecial = embargoRecursoEspecial;
    }

    public String getFolhasCiencia() {
        return this.folhasCiencia;
    }

    public Processo folhasCiencia(String folhasCiencia) {
        this.folhasCiencia = folhasCiencia;
        return this;
    }

    public void setFolhasCiencia(String folhasCiencia) {
        this.folhasCiencia = folhasCiencia;
    }

    public String getAgravoRespRe() {
        return this.agravoRespRe;
    }

    public Processo agravoRespRe(String agravoRespRe) {
        this.agravoRespRe = agravoRespRe;
        return this;
    }

    public void setAgravoRespRe(String agravoRespRe) {
        this.agravoRespRe = agravoRespRe;
    }

    public String getFolhasRespRe() {
        return this.folhasRespRe;
    }

    public Processo folhasRespRe(String folhasRespRe) {
        this.folhasRespRe = folhasRespRe;
        return this;
    }

    public void setFolhasRespRe(String folhasRespRe) {
        this.folhasRespRe = folhasRespRe;
    }

    public String getAcordaoAgravoRespRe() {
        return this.acordaoAgravoRespRe;
    }

    public Processo acordaoAgravoRespRe(String acordaoAgravoRespRe) {
        this.acordaoAgravoRespRe = acordaoAgravoRespRe;
        return this;
    }

    public void setAcordaoAgravoRespRe(String acordaoAgravoRespRe) {
        this.acordaoAgravoRespRe = acordaoAgravoRespRe;
    }

    public String getFolhasCienciaJulgamentoAgravoRespRe() {
        return this.folhasCienciaJulgamentoAgravoRespRe;
    }

    public Processo folhasCienciaJulgamentoAgravoRespRe(String folhasCienciaJulgamentoAgravoRespRe) {
        this.folhasCienciaJulgamentoAgravoRespRe = folhasCienciaJulgamentoAgravoRespRe;
        return this;
    }

    public void setFolhasCienciaJulgamentoAgravoRespRe(String folhasCienciaJulgamentoAgravoRespRe) {
        this.folhasCienciaJulgamentoAgravoRespRe = folhasCienciaJulgamentoAgravoRespRe;
    }

    public String getEmbargoRespRe() {
        return this.embargoRespRe;
    }

    public Processo embargoRespRe(String embargoRespRe) {
        this.embargoRespRe = embargoRespRe;
        return this;
    }

    public void setEmbargoRespRe(String embargoRespRe) {
        this.embargoRespRe = embargoRespRe;
    }

    public String getAgravoInterno() {
        return this.agravoInterno;
    }

    public Processo agravoInterno(String agravoInterno) {
        this.agravoInterno = agravoInterno;
        return this;
    }

    public void setAgravoInterno(String agravoInterno) {
        this.agravoInterno = agravoInterno;
    }

    public String getFolhasAgravoInterno() {
        return this.folhasAgravoInterno;
    }

    public Processo folhasAgravoInterno(String folhasAgravoInterno) {
        this.folhasAgravoInterno = folhasAgravoInterno;
        return this;
    }

    public void setFolhasAgravoInterno(String folhasAgravoInterno) {
        this.folhasAgravoInterno = folhasAgravoInterno;
    }

    public Boolean getEmbargoRecursoAgravo() {
        return this.embargoRecursoAgravo;
    }

    public Processo embargoRecursoAgravo(Boolean embargoRecursoAgravo) {
        this.embargoRecursoAgravo = embargoRecursoAgravo;
        return this;
    }

    public void setEmbargoRecursoAgravo(Boolean embargoRecursoAgravo) {
        this.embargoRecursoAgravo = embargoRecursoAgravo;
    }

    public String getObservacoes() {
        return this.observacoes;
    }

    public Processo observacoes(String observacoes) {
        this.observacoes = observacoes;
        return this;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public Boolean getRecursoSTJ() {
        return this.recursoSTJ;
    }

    public Processo recursoSTJ(Boolean recursoSTJ) {
        this.recursoSTJ = recursoSTJ;
        return this;
    }

    public void setRecursoSTJ(Boolean recursoSTJ) {
        this.recursoSTJ = recursoSTJ;
    }

    public String getLinkRecursoSTJ() {
        return this.linkRecursoSTJ;
    }

    public Processo linkRecursoSTJ(String linkRecursoSTJ) {
        this.linkRecursoSTJ = linkRecursoSTJ;
        return this;
    }

    public void setLinkRecursoSTJ(String linkRecursoSTJ) {
        this.linkRecursoSTJ = linkRecursoSTJ;
    }

    public String getFolhasRecursoSTJ() {
        return this.folhasRecursoSTJ;
    }

    public Processo folhasRecursoSTJ(String folhasRecursoSTJ) {
        this.folhasRecursoSTJ = folhasRecursoSTJ;
        return this;
    }

    public void setFolhasRecursoSTJ(String folhasRecursoSTJ) {
        this.folhasRecursoSTJ = folhasRecursoSTJ;
    }

    public Boolean getRecursoSTF() {
        return this.recursoSTF;
    }

    public Processo recursoSTF(Boolean recursoSTF) {
        this.recursoSTF = recursoSTF;
        return this;
    }

    public void setRecursoSTF(Boolean recursoSTF) {
        this.recursoSTF = recursoSTF;
    }

    public String getLinkRecursoSTF() {
        return this.linkRecursoSTF;
    }

    public Processo linkRecursoSTF(String linkRecursoSTF) {
        this.linkRecursoSTF = linkRecursoSTF;
        return this;
    }

    public void setLinkRecursoSTF(String linkRecursoSTF) {
        this.linkRecursoSTF = linkRecursoSTF;
    }

    public String getFolhasRecursoSTF() {
        return this.folhasRecursoSTF;
    }

    public Processo folhasRecursoSTF(String folhasRecursoSTF) {
        this.folhasRecursoSTF = folhasRecursoSTF;
        return this;
    }

    public void setFolhasRecursoSTF(String folhasRecursoSTF) {
        this.folhasRecursoSTF = folhasRecursoSTF;
    }

    public String getFolhasMemorialMPF() {
        return this.folhasMemorialMPF;
    }

    public Processo folhasMemorialMPF(String folhasMemorialMPF) {
        this.folhasMemorialMPF = folhasMemorialMPF;
        return this;
    }

    public void setFolhasMemorialMPF(String folhasMemorialMPF) {
        this.folhasMemorialMPF = folhasMemorialMPF;
    }

    public Boolean getExecusaoProvisoria() {
        return this.execusaoProvisoria;
    }

    public Processo execusaoProvisoria(Boolean execusaoProvisoria) {
        this.execusaoProvisoria = execusaoProvisoria;
        return this;
    }

    public void setExecusaoProvisoria(Boolean execusaoProvisoria) {
        this.execusaoProvisoria = execusaoProvisoria;
    }

    public String getNumeracaoExecusaoProvisoria() {
        return this.numeracaoExecusaoProvisoria;
    }

    public Processo numeracaoExecusaoProvisoria(String numeracaoExecusaoProvisoria) {
        this.numeracaoExecusaoProvisoria = numeracaoExecusaoProvisoria;
        return this;
    }

    public void setNumeracaoExecusaoProvisoria(String numeracaoExecusaoProvisoria) {
        this.numeracaoExecusaoProvisoria = numeracaoExecusaoProvisoria;
    }

    public String getRecuperacaoEfetivaCumprimentoSentenca() {
        return this.recuperacaoEfetivaCumprimentoSentenca;
    }

    public Processo recuperacaoEfetivaCumprimentoSentenca(String recuperacaoEfetivaCumprimentoSentenca) {
        this.recuperacaoEfetivaCumprimentoSentenca = recuperacaoEfetivaCumprimentoSentenca;
        return this;
    }

    public void setRecuperacaoEfetivaCumprimentoSentenca(String recuperacaoEfetivaCumprimentoSentenca) {
        this.recuperacaoEfetivaCumprimentoSentenca = recuperacaoEfetivaCumprimentoSentenca;
    }

    public String getRecuperacaoEfetivaCumprimentoSentencaObservacoes() {
        return this.recuperacaoEfetivaCumprimentoSentencaObservacoes;
    }

    public Processo recuperacaoEfetivaCumprimentoSentencaObservacoes(String recuperacaoEfetivaCumprimentoSentencaObservacoes) {
        this.recuperacaoEfetivaCumprimentoSentencaObservacoes = recuperacaoEfetivaCumprimentoSentencaObservacoes;
        return this;
    }

    public void setRecuperacaoEfetivaCumprimentoSentencaObservacoes(String recuperacaoEfetivaCumprimentoSentencaObservacoes) {
        this.recuperacaoEfetivaCumprimentoSentencaObservacoes = recuperacaoEfetivaCumprimentoSentencaObservacoes;
    }

    public Boolean getEnvolveEmpreendimento() {
        return this.envolveEmpreendimento;
    }

    public Processo envolveEmpreendimento(Boolean envolveEmpreendimento) {
        this.envolveEmpreendimento = envolveEmpreendimento;
        return this;
    }

    public void setEnvolveEmpreendimento(Boolean envolveEmpreendimento) {
        this.envolveEmpreendimento = envolveEmpreendimento;
    }

    public Boolean getEnvolveExploracaoIlegal() {
        return this.envolveExploracaoIlegal;
    }

    public Processo envolveExploracaoIlegal(Boolean envolveExploracaoIlegal) {
        this.envolveExploracaoIlegal = envolveExploracaoIlegal;
        return this;
    }

    public void setEnvolveExploracaoIlegal(Boolean envolveExploracaoIlegal) {
        this.envolveExploracaoIlegal = envolveExploracaoIlegal;
    }

    public Boolean getEnvolveTerraQuilombola() {
        return this.envolveTerraQuilombola;
    }

    public Processo envolveTerraQuilombola(Boolean envolveTerraQuilombola) {
        this.envolveTerraQuilombola = envolveTerraQuilombola;
        return this;
    }

    public void setEnvolveTerraQuilombola(Boolean envolveTerraQuilombola) {
        this.envolveTerraQuilombola = envolveTerraQuilombola;
    }

    public Boolean getEnvolveTerraComunidadeTradicional() {
        return this.envolveTerraComunidadeTradicional;
    }

    public Processo envolveTerraComunidadeTradicional(Boolean envolveTerraComunidadeTradicional) {
        this.envolveTerraComunidadeTradicional = envolveTerraComunidadeTradicional;
        return this;
    }

    public void setEnvolveTerraComunidadeTradicional(Boolean envolveTerraComunidadeTradicional) {
        this.envolveTerraComunidadeTradicional = envolveTerraComunidadeTradicional;
    }

    public Boolean getEnvolveTerraIndigena() {
        return this.envolveTerraIndigena;
    }

    public Processo envolveTerraIndigena(Boolean envolveTerraIndigena) {
        this.envolveTerraIndigena = envolveTerraIndigena;
        return this;
    }

    public void setEnvolveTerraIndigena(Boolean envolveTerraIndigena) {
        this.envolveTerraIndigena = envolveTerraIndigena;
    }

    public String getResumoFatos() {
        return this.resumoFatos;
    }

    public Processo resumoFatos(String resumoFatos) {
        this.resumoFatos = resumoFatos;
        return this;
    }

    public void setResumoFatos(String resumoFatos) {
        this.resumoFatos = resumoFatos;
    }

    public BigDecimal getTamanhoArea() {
        return this.tamanhoArea;
    }

    public Processo tamanhoArea(BigDecimal tamanhoArea) {
        this.tamanhoArea = tamanhoArea;
        return this;
    }

    public void setTamanhoArea(BigDecimal tamanhoArea) {
        this.tamanhoArea = tamanhoArea;
    }

    public BigDecimal getValorArea() {
        return this.valorArea;
    }

    public Processo valorArea(BigDecimal valorArea) {
        this.valorArea = valorArea;
        return this;
    }

    public void setValorArea(BigDecimal valorArea) {
        this.valorArea = valorArea;
    }

    public String getTamanhoAreaObservacao() {
        return this.tamanhoAreaObservacao;
    }

    public Processo tamanhoAreaObservacao(String tamanhoAreaObservacao) {
        this.tamanhoAreaObservacao = tamanhoAreaObservacao;
        return this;
    }

    public void setTamanhoAreaObservacao(String tamanhoAreaObservacao) {
        this.tamanhoAreaObservacao = tamanhoAreaObservacao;
    }

    public Boolean getDadosGeograficosLitigioConflito() {
        return this.dadosGeograficosLitigioConflito;
    }

    public Processo dadosGeograficosLitigioConflito(Boolean dadosGeograficosLitigioConflito) {
        this.dadosGeograficosLitigioConflito = dadosGeograficosLitigioConflito;
        return this;
    }

    public void setDadosGeograficosLitigioConflito(Boolean dadosGeograficosLitigioConflito) {
        this.dadosGeograficosLitigioConflito = dadosGeograficosLitigioConflito;
    }

    public String getLatitude() {
        return this.latitude;
    }

    public Processo latitude(String latitude) {
        this.latitude = latitude;
        return this;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return this.longitude;
    }

    public Processo longitude(String longitude) {
        this.longitude = longitude;
        return this;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getNumeroProcessoMPF() {
        return this.numeroProcessoMPF;
    }

    public Processo numeroProcessoMPF(String numeroProcessoMPF) {
        this.numeroProcessoMPF = numeroProcessoMPF;
        return this;
    }

    public void setNumeroProcessoMPF(String numeroProcessoMPF) {
        this.numeroProcessoMPF = numeroProcessoMPF;
    }

    public String getNumeroEmbargo() {
        return this.numeroEmbargo;
    }

    public Processo numeroEmbargo(String numeroEmbargo) {
        this.numeroEmbargo = numeroEmbargo;
        return this;
    }

    public void setNumeroEmbargo(String numeroEmbargo) {
        this.numeroEmbargo = numeroEmbargo;
    }

    public String getPautaApelacao() {
        return this.pautaApelacao;
    }

    public Processo pautaApelacao(String pautaApelacao) {
        this.pautaApelacao = pautaApelacao;
        return this;
    }

    public void setPautaApelacao(String pautaApelacao) {
        this.pautaApelacao = pautaApelacao;
    }

    public String getNumeroRecursoEspecial() {
        return this.numeroRecursoEspecial;
    }

    public Processo numeroRecursoEspecial(String numeroRecursoEspecial) {
        this.numeroRecursoEspecial = numeroRecursoEspecial;
        return this;
    }

    public void setNumeroRecursoEspecial(String numeroRecursoEspecial) {
        this.numeroRecursoEspecial = numeroRecursoEspecial;
    }

    public String getAdmissiblidade() {
        return this.admissiblidade;
    }

    public Processo admissiblidade(String admissiblidade) {
        this.admissiblidade = admissiblidade;
        return this;
    }

    public void setAdmissiblidade(String admissiblidade) {
        this.admissiblidade = admissiblidade;
    }

    public Boolean getEnvolveGrandeProjeto() {
        return this.envolveGrandeProjeto;
    }

    public Processo envolveGrandeProjeto(Boolean envolveGrandeProjeto) {
        this.envolveGrandeProjeto = envolveGrandeProjeto;
        return this;
    }

    public void setEnvolveGrandeProjeto(Boolean envolveGrandeProjeto) {
        this.envolveGrandeProjeto = envolveGrandeProjeto;
    }

    public Boolean getEnvolveUnidadeConservacao() {
        return this.envolveUnidadeConservacao;
    }

    public Processo envolveUnidadeConservacao(Boolean envolveUnidadeConservacao) {
        this.envolveUnidadeConservacao = envolveUnidadeConservacao;
        return this;
    }

    public void setEnvolveUnidadeConservacao(Boolean envolveUnidadeConservacao) {
        this.envolveUnidadeConservacao = envolveUnidadeConservacao;
    }

    public String getLinkReferencia() {
        return this.linkReferencia;
    }

    public Processo linkReferencia(String linkReferencia) {
        this.linkReferencia = linkReferencia;
        return this;
    }

    public void setLinkReferencia(String linkReferencia) {
        this.linkReferencia = linkReferencia;
    }

    public Set<ConcessaoLiminar> getConcessaoLiminars() {
        return this.concessaoLiminars;
    }

    public Processo concessaoLiminars(Set<ConcessaoLiminar> concessaoLiminars) {
        this.setConcessaoLiminars(concessaoLiminars);
        return this;
    }

    public Processo addConcessaoLiminar(ConcessaoLiminar concessaoLiminar) {
        this.concessaoLiminars.add(concessaoLiminar);
        concessaoLiminar.setProcesso(this);
        return this;
    }

    public Processo removeConcessaoLiminar(ConcessaoLiminar concessaoLiminar) {
        this.concessaoLiminars.remove(concessaoLiminar);
        concessaoLiminar.setProcesso(null);
        return this;
    }

    public void setConcessaoLiminars(Set<ConcessaoLiminar> concessaoLiminars) {
        if (this.concessaoLiminars != null) {
            this.concessaoLiminars.forEach(i -> i.setProcesso(null));
        }
        if (concessaoLiminars != null) {
            concessaoLiminars.forEach(i -> i.setProcesso(this));
        }
        this.concessaoLiminars = concessaoLiminars;
    }

    public Set<ConcessaoLiminarCassada> getConcessaoLiminarCassadas() {
        return this.concessaoLiminarCassadas;
    }

    public Processo concessaoLiminarCassadas(Set<ConcessaoLiminarCassada> concessaoLiminarCassadas) {
        this.setConcessaoLiminarCassadas(concessaoLiminarCassadas);
        return this;
    }

    public Processo addConcessaoLiminarCassada(ConcessaoLiminarCassada concessaoLiminarCassada) {
        this.concessaoLiminarCassadas.add(concessaoLiminarCassada);
        concessaoLiminarCassada.setProcesso(this);
        return this;
    }

    public Processo removeConcessaoLiminarCassada(ConcessaoLiminarCassada concessaoLiminarCassada) {
        this.concessaoLiminarCassadas.remove(concessaoLiminarCassada);
        concessaoLiminarCassada.setProcesso(null);
        return this;
    }

    public void setConcessaoLiminarCassadas(Set<ConcessaoLiminarCassada> concessaoLiminarCassadas) {
        if (this.concessaoLiminarCassadas != null) {
            this.concessaoLiminarCassadas.forEach(i -> i.setProcesso(null));
        }
        if (concessaoLiminarCassadas != null) {
            concessaoLiminarCassadas.forEach(i -> i.setProcesso(this));
        }
        this.concessaoLiminarCassadas = concessaoLiminarCassadas;
    }

    public Set<EmbargoDeclaracao> getEmbargoDeclaracaos() {
        return this.embargoDeclaracaos;
    }

    public Processo embargoDeclaracaos(Set<EmbargoDeclaracao> embargoDeclaracaos) {
        this.setEmbargoDeclaracaos(embargoDeclaracaos);
        return this;
    }

    public Processo addEmbargoDeclaracao(EmbargoDeclaracao embargoDeclaracao) {
        this.embargoDeclaracaos.add(embargoDeclaracao);
        embargoDeclaracao.setProcesso(this);
        return this;
    }

    public Processo removeEmbargoDeclaracao(EmbargoDeclaracao embargoDeclaracao) {
        this.embargoDeclaracaos.remove(embargoDeclaracao);
        embargoDeclaracao.setProcesso(null);
        return this;
    }

    public void setEmbargoDeclaracaos(Set<EmbargoDeclaracao> embargoDeclaracaos) {
        if (this.embargoDeclaracaos != null) {
            this.embargoDeclaracaos.forEach(i -> i.setProcesso(null));
        }
        if (embargoDeclaracaos != null) {
            embargoDeclaracaos.forEach(i -> i.setProcesso(this));
        }
        this.embargoDeclaracaos = embargoDeclaracaos;
    }

    public Set<EmbargoDeclaracaoAgravo> getEmbargoDeclaracaoAgravos() {
        return this.embargoDeclaracaoAgravos;
    }

    public Processo embargoDeclaracaoAgravos(Set<EmbargoDeclaracaoAgravo> embargoDeclaracaoAgravos) {
        this.setEmbargoDeclaracaoAgravos(embargoDeclaracaoAgravos);
        return this;
    }

    public Processo addEmbargoDeclaracaoAgravo(EmbargoDeclaracaoAgravo embargoDeclaracaoAgravo) {
        this.embargoDeclaracaoAgravos.add(embargoDeclaracaoAgravo);
        embargoDeclaracaoAgravo.setProcesso(this);
        return this;
    }

    public Processo removeEmbargoDeclaracaoAgravo(EmbargoDeclaracaoAgravo embargoDeclaracaoAgravo) {
        this.embargoDeclaracaoAgravos.remove(embargoDeclaracaoAgravo);
        embargoDeclaracaoAgravo.setProcesso(null);
        return this;
    }

    public void setEmbargoDeclaracaoAgravos(Set<EmbargoDeclaracaoAgravo> embargoDeclaracaoAgravos) {
        if (this.embargoDeclaracaoAgravos != null) {
            this.embargoDeclaracaoAgravos.forEach(i -> i.setProcesso(null));
        }
        if (embargoDeclaracaoAgravos != null) {
            embargoDeclaracaoAgravos.forEach(i -> i.setProcesso(this));
        }
        this.embargoDeclaracaoAgravos = embargoDeclaracaoAgravos;
    }

    public Set<EmbargoRecursoEspecial> getEmbargoRecursoEspecials() {
        return this.embargoRecursoEspecials;
    }

    public Processo embargoRecursoEspecials(Set<EmbargoRecursoEspecial> embargoRecursoEspecials) {
        this.setEmbargoRecursoEspecials(embargoRecursoEspecials);
        return this;
    }

    public Processo addEmbargoRecursoEspecial(EmbargoRecursoEspecial embargoRecursoEspecial) {
        this.embargoRecursoEspecials.add(embargoRecursoEspecial);
        embargoRecursoEspecial.setProcesso(this);
        return this;
    }

    public Processo removeEmbargoRecursoEspecial(EmbargoRecursoEspecial embargoRecursoEspecial) {
        this.embargoRecursoEspecials.remove(embargoRecursoEspecial);
        embargoRecursoEspecial.setProcesso(null);
        return this;
    }

    public void setEmbargoRecursoEspecials(Set<EmbargoRecursoEspecial> embargoRecursoEspecials) {
        if (this.embargoRecursoEspecials != null) {
            this.embargoRecursoEspecials.forEach(i -> i.setProcesso(null));
        }
        if (embargoRecursoEspecials != null) {
            embargoRecursoEspecials.forEach(i -> i.setProcesso(this));
        }
        this.embargoRecursoEspecials = embargoRecursoEspecials;
    }

    public Set<EmbargoRespRe> getEmbargoRespRes() {
        return this.embargoRespRes;
    }

    public Processo embargoRespRes(Set<EmbargoRespRe> embargoRespRes) {
        this.setEmbargoRespRes(embargoRespRes);
        return this;
    }

    public Processo addEmbargoRespRe(EmbargoRespRe embargoRespRe) {
        this.embargoRespRes.add(embargoRespRe);
        embargoRespRe.setProcesso(this);
        return this;
    }

    public Processo removeEmbargoRespRe(EmbargoRespRe embargoRespRe) {
        this.embargoRespRes.remove(embargoRespRe);
        embargoRespRe.setProcesso(null);
        return this;
    }

    public void setEmbargoRespRes(Set<EmbargoRespRe> embargoRespRes) {
        if (this.embargoRespRes != null) {
            this.embargoRespRes.forEach(i -> i.setProcesso(null));
        }
        if (embargoRespRes != null) {
            embargoRespRes.forEach(i -> i.setProcesso(this));
        }
        this.embargoRespRes = embargoRespRes;
    }

    public TipoDecisao getTipoDecisao() {
        return this.tipoDecisao;
    }

    public Processo tipoDecisao(TipoDecisao tipoDecisao) {
        this.setTipoDecisao(tipoDecisao);
        return this;
    }

    public void setTipoDecisao(TipoDecisao tipoDecisao) {
        this.tipoDecisao = tipoDecisao;
    }

    public TipoEmpreendimento getTipoEmpreendimento() {
        return this.tipoEmpreendimento;
    }

    public Processo tipoEmpreendimento(TipoEmpreendimento tipoEmpreendimento) {
        this.setTipoEmpreendimento(tipoEmpreendimento);
        return this;
    }

    public void setTipoEmpreendimento(TipoEmpreendimento tipoEmpreendimento) {
        this.tipoEmpreendimento = tipoEmpreendimento;
    }

    public Set<Comarca> getComarcas() {
        return this.comarcas;
    }

    public Processo comarcas(Set<Comarca> comarcas) {
        this.setComarcas(comarcas);
        return this;
    }

    public Processo addComarca(Comarca comarca) {
        this.comarcas.add(comarca);
        comarca.getProcessos().add(this);
        return this;
    }

    public Processo removeComarca(Comarca comarca) {
        this.comarcas.remove(comarca);
        comarca.getProcessos().remove(this);
        return this;
    }

    public void setComarcas(Set<Comarca> comarcas) {
        this.comarcas = comarcas;
    }

    public Set<Quilombo> getQuilombos() {
        return this.quilombos;
    }

    public Processo quilombos(Set<Quilombo> quilombos) {
        this.setQuilombos(quilombos);
        return this;
    }

    public Processo addQuilombo(Quilombo quilombo) {
        this.quilombos.add(quilombo);
        quilombo.getProcessos().add(this);
        return this;
    }

    public Processo removeQuilombo(Quilombo quilombo) {
        this.quilombos.remove(quilombo);
        quilombo.getProcessos().remove(this);
        return this;
    }

    public void setQuilombos(Set<Quilombo> quilombos) {
        this.quilombos = quilombos;
    }

    public Set<Municipio> getMunicipios() {
        return this.municipios;
    }

    public Processo municipios(Set<Municipio> municipios) {
        this.setMunicipios(municipios);
        return this;
    }

    public Processo addMunicipio(Municipio municipio) {
        this.municipios.add(municipio);
        municipio.getProcessos().add(this);
        return this;
    }

    public Processo removeMunicipio(Municipio municipio) {
        this.municipios.remove(municipio);
        municipio.getProcessos().remove(this);
        return this;
    }

    public void setMunicipios(Set<Municipio> municipios) {
        this.municipios = municipios;
    }

    public Set<Territorio> getTerritorios() {
        return this.territorios;
    }

    public Processo territorios(Set<Territorio> territorios) {
        this.setTerritorios(territorios);
        return this;
    }

    public Processo addTerritorio(Territorio territorio) {
        this.territorios.add(territorio);
        territorio.getProcessos().add(this);
        return this;
    }

    public Processo removeTerritorio(Territorio territorio) {
        this.territorios.remove(territorio);
        territorio.getProcessos().remove(this);
        return this;
    }

    public void setTerritorios(Set<Territorio> territorios) {
        this.territorios = territorios;
    }

    public Set<AtividadeExploracaoIlegal> getAtividadeExploracaoIlegals() {
        return this.atividadeExploracaoIlegals;
    }

    public Processo atividadeExploracaoIlegals(Set<AtividadeExploracaoIlegal> atividadeExploracaoIlegals) {
        this.setAtividadeExploracaoIlegals(atividadeExploracaoIlegals);
        return this;
    }

    public Processo addAtividadeExploracaoIlegal(AtividadeExploracaoIlegal atividadeExploracaoIlegal) {
        this.atividadeExploracaoIlegals.add(atividadeExploracaoIlegal);
        atividadeExploracaoIlegal.getProcessos().add(this);
        return this;
    }

    public Processo removeAtividadeExploracaoIlegal(AtividadeExploracaoIlegal atividadeExploracaoIlegal) {
        this.atividadeExploracaoIlegals.remove(atividadeExploracaoIlegal);
        atividadeExploracaoIlegal.getProcessos().remove(this);
        return this;
    }

    public void setAtividadeExploracaoIlegals(Set<AtividadeExploracaoIlegal> atividadeExploracaoIlegals) {
        this.atividadeExploracaoIlegals = atividadeExploracaoIlegals;
    }

    public Set<UnidadeConservacao> getUnidadeConservacaos() {
        return this.unidadeConservacaos;
    }

    public Processo unidadeConservacaos(Set<UnidadeConservacao> unidadeConservacaos) {
        this.setUnidadeConservacaos(unidadeConservacaos);
        return this;
    }

    public Processo addUnidadeConservacao(UnidadeConservacao unidadeConservacao) {
        this.unidadeConservacaos.add(unidadeConservacao);
        unidadeConservacao.getProcessos().add(this);
        return this;
    }

    public Processo removeUnidadeConservacao(UnidadeConservacao unidadeConservacao) {
        this.unidadeConservacaos.remove(unidadeConservacao);
        unidadeConservacao.getProcessos().remove(this);
        return this;
    }

    public void setUnidadeConservacaos(Set<UnidadeConservacao> unidadeConservacaos) {
        this.unidadeConservacaos = unidadeConservacaos;
    }

    public Set<EnvolvidosConflitoLitigio> getEnvolvidosConflitoLitigios() {
        return this.envolvidosConflitoLitigios;
    }

    public Processo envolvidosConflitoLitigios(Set<EnvolvidosConflitoLitigio> envolvidosConflitoLitigios) {
        this.setEnvolvidosConflitoLitigios(envolvidosConflitoLitigios);
        return this;
    }

    public Processo addEnvolvidosConflitoLitigio(EnvolvidosConflitoLitigio envolvidosConflitoLitigio) {
        this.envolvidosConflitoLitigios.add(envolvidosConflitoLitigio);
        envolvidosConflitoLitigio.getProcessos().add(this);
        return this;
    }

    public Processo removeEnvolvidosConflitoLitigio(EnvolvidosConflitoLitigio envolvidosConflitoLitigio) {
        this.envolvidosConflitoLitigios.remove(envolvidosConflitoLitigio);
        envolvidosConflitoLitigio.getProcessos().remove(this);
        return this;
    }

    public void setEnvolvidosConflitoLitigios(Set<EnvolvidosConflitoLitigio> envolvidosConflitoLitigios) {
        this.envolvidosConflitoLitigios = envolvidosConflitoLitigios;
    }

    public Set<TerraIndigena> getTerraIndigenas() {
        return this.terraIndigenas;
    }

    public Processo terraIndigenas(Set<TerraIndigena> terraIndigenas) {
        this.setTerraIndigenas(terraIndigenas);
        return this;
    }

    public Processo addTerraIndigena(TerraIndigena terraIndigena) {
        this.terraIndigenas.add(terraIndigena);
        terraIndigena.getProcessos().add(this);
        return this;
    }

    public Processo removeTerraIndigena(TerraIndigena terraIndigena) {
        this.terraIndigenas.remove(terraIndigena);
        terraIndigena.getProcessos().remove(this);
        return this;
    }

    public void setTerraIndigenas(Set<TerraIndigena> terraIndigenas) {
        this.terraIndigenas = terraIndigenas;
    }

    public Set<ProcessoConflito> getProcessoConflitos() {
        return this.processoConflitos;
    }

    public Processo processoConflitos(Set<ProcessoConflito> processoConflitos) {
        this.setProcessoConflitos(processoConflitos);
        return this;
    }

    public Processo addProcessoConflito(ProcessoConflito processoConflito) {
        this.processoConflitos.add(processoConflito);
        processoConflito.getProcessos().add(this);
        return this;
    }

    public Processo removeProcessoConflito(ProcessoConflito processoConflito) {
        this.processoConflitos.remove(processoConflito);
        processoConflito.getProcessos().remove(this);
        return this;
    }

    public void setProcessoConflitos(Set<ProcessoConflito> processoConflitos) {
        this.processoConflitos = processoConflitos;
    }

    public Set<ParteInteresssada> getParteInteresssadas() {
        return this.parteInteresssadas;
    }

    public Processo parteInteresssadas(Set<ParteInteresssada> parteInteresssadas) {
        this.setParteInteresssadas(parteInteresssadas);
        return this;
    }

    public Processo addParteInteresssada(ParteInteresssada parteInteresssada) {
        this.parteInteresssadas.add(parteInteresssada);
        parteInteresssada.getProcessos().add(this);
        return this;
    }

    public Processo removeParteInteresssada(ParteInteresssada parteInteresssada) {
        this.parteInteresssadas.remove(parteInteresssada);
        parteInteresssada.getProcessos().remove(this);
        return this;
    }

    public void setParteInteresssadas(Set<ParteInteresssada> parteInteresssadas) {
        this.parteInteresssadas = parteInteresssadas;
    }

    public Set<Relator> getRelators() {
        return this.relators;
    }

    public Processo relators(Set<Relator> relators) {
        this.setRelators(relators);
        return this;
    }

    public Processo addRelator(Relator relator) {
        this.relators.add(relator);
        relator.getProcessos().add(this);
        return this;
    }

    public Processo removeRelator(Relator relator) {
        this.relators.remove(relator);
        relator.getProcessos().remove(this);
        return this;
    }

    public void setRelators(Set<Relator> relators) {
        this.relators = relators;
    }

    public Set<ProblemaJuridico> getProblemaJuridicos() {
        return this.problemaJuridicos;
    }

    public Processo problemaJuridicos(Set<ProblemaJuridico> problemaJuridicos) {
        this.setProblemaJuridicos(problemaJuridicos);
        return this;
    }

    public Processo addProblemaJuridico(ProblemaJuridico problemaJuridico) {
        this.problemaJuridicos.add(problemaJuridico);
        problemaJuridico.getProcessos().add(this);
        return this;
    }

    public Processo removeProblemaJuridico(ProblemaJuridico problemaJuridico) {
        this.problemaJuridicos.remove(problemaJuridico);
        problemaJuridico.getProcessos().remove(this);
        return this;
    }

    public void setProblemaJuridicos(Set<ProblemaJuridico> problemaJuridicos) {
        if (this.problemaJuridicos != null) {
            this.problemaJuridicos.forEach(i -> i.removeProcesso(this));
        }
        if (problemaJuridicos != null) {
            problemaJuridicos.forEach(i -> i.addProcesso(this));
        }
        this.problemaJuridicos = problemaJuridicos;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Processo)) {
            return false;
        }
        return id != null && id.equals(((Processo) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Processo{" +
            "id=" + getId() +
            ", numeroProcesso='" + getNumeroProcesso() + "'" +
            ", oficio='" + getOficio() + "'" +
            ", assunto='" + getAssunto() + "'" +
            ", linkUnico='" + getLinkUnico() + "'" +
            ", linkTrf='" + getLinkTrf() + "'" +
            ", secaoJudiciaria='" + getSecaoJudiciaria() + "'" +
            ", subsecaoJudiciaria='" + getSubsecaoJudiciaria() + "'" +
            ", turmaTrf1='" + getTurmaTrf1() + "'" +
            ", numeroProcessoAdministrativo='" + getNumeroProcessoAdministrativo() + "'" +
            ", numeroProcessoJudicialPrimeiraInstancia='" + getNumeroProcessoJudicialPrimeiraInstancia() + "'" +
            ", numeroProcessoJudicialPrimeiraInstanciaLink='" + getNumeroProcessoJudicialPrimeiraInstanciaLink() + "'" +
            ", numeroProcessoJudicialPrimeiraInstanciaObservacoes='" + getNumeroProcessoJudicialPrimeiraInstanciaObservacoes() + "'" +
            ", parecer='" + getParecer() + "'" +
            ", folhasProcessoConcessaoLiminar='" + getFolhasProcessoConcessaoLiminar() + "'" +
            ", concessaoLiminarObservacoes='" + getConcessaoLiminarObservacoes() + "'" +
            ", folhasProcessoCassacao='" + getFolhasProcessoCassacao() + "'" +
            ", folhasParecer='" + getFolhasParecer() + "'" +
            ", folhasEmbargo='" + getFolhasEmbargo() + "'" +
            ", acordaoEmbargo='" + getAcordaoEmbargo() + "'" +
            ", folhasCienciaJulgEmbargos='" + getFolhasCienciaJulgEmbargos() + "'" +
            ", apelacao='" + getApelacao() + "'" +
            ", folhasApelacao='" + getFolhasApelacao() + "'" +
            ", acordaoApelacao='" + getAcordaoApelacao() + "'" +
            ", folhasCienciaJulgApelacao='" + getFolhasCienciaJulgApelacao() + "'" +
            ", embargoDeclaracao='" + getEmbargoDeclaracao() + "'" +
            ", folhasRecursoEspecial='" + getFolhasRecursoEspecial() + "'" +
            ", acordaoRecursoEspecial='" + getAcordaoRecursoEspecial() + "'" +
            ", folhasCienciaJulgamentoRecursoEspecial='" + getFolhasCienciaJulgamentoRecursoEspecial() + "'" +
            ", embargoRecursoEspecial='" + getEmbargoRecursoEspecial() + "'" +
            ", folhasCiencia='" + getFolhasCiencia() + "'" +
            ", agravoRespRe='" + getAgravoRespRe() + "'" +
            ", folhasRespRe='" + getFolhasRespRe() + "'" +
            ", acordaoAgravoRespRe='" + getAcordaoAgravoRespRe() + "'" +
            ", folhasCienciaJulgamentoAgravoRespRe='" + getFolhasCienciaJulgamentoAgravoRespRe() + "'" +
            ", embargoRespRe='" + getEmbargoRespRe() + "'" +
            ", agravoInterno='" + getAgravoInterno() + "'" +
            ", folhasAgravoInterno='" + getFolhasAgravoInterno() + "'" +
            ", embargoRecursoAgravo='" + getEmbargoRecursoAgravo() + "'" +
            ", observacoes='" + getObservacoes() + "'" +
            ", recursoSTJ='" + getRecursoSTJ() + "'" +
            ", linkRecursoSTJ='" + getLinkRecursoSTJ() + "'" +
            ", folhasRecursoSTJ='" + getFolhasRecursoSTJ() + "'" +
            ", recursoSTF='" + getRecursoSTF() + "'" +
            ", linkRecursoSTF='" + getLinkRecursoSTF() + "'" +
            ", folhasRecursoSTF='" + getFolhasRecursoSTF() + "'" +
            ", folhasMemorialMPF='" + getFolhasMemorialMPF() + "'" +
            ", execusaoProvisoria='" + getExecusaoProvisoria() + "'" +
            ", numeracaoExecusaoProvisoria='" + getNumeracaoExecusaoProvisoria() + "'" +
            ", recuperacaoEfetivaCumprimentoSentenca='" + getRecuperacaoEfetivaCumprimentoSentenca() + "'" +
            ", recuperacaoEfetivaCumprimentoSentencaObservacoes='" + getRecuperacaoEfetivaCumprimentoSentencaObservacoes() + "'" +
            ", envolveEmpreendimento='" + getEnvolveEmpreendimento() + "'" +
            ", envolveExploracaoIlegal='" + getEnvolveExploracaoIlegal() + "'" +
            ", envolveTerraQuilombola='" + getEnvolveTerraQuilombola() + "'" +
            ", envolveTerraComunidadeTradicional='" + getEnvolveTerraComunidadeTradicional() + "'" +
            ", envolveTerraIndigena='" + getEnvolveTerraIndigena() + "'" +
            ", resumoFatos='" + getResumoFatos() + "'" +
            ", tamanhoArea=" + getTamanhoArea() +
            ", valorArea=" + getValorArea() +
            ", tamanhoAreaObservacao='" + getTamanhoAreaObservacao() + "'" +
            ", dadosGeograficosLitigioConflito='" + getDadosGeograficosLitigioConflito() + "'" +
            ", latitude='" + getLatitude() + "'" +
            ", longitude='" + getLongitude() + "'" +
            ", numeroProcessoMPF='" + getNumeroProcessoMPF() + "'" +
            ", numeroEmbargo='" + getNumeroEmbargo() + "'" +
            ", pautaApelacao='" + getPautaApelacao() + "'" +
            ", numeroRecursoEspecial='" + getNumeroRecursoEspecial() + "'" +
            ", admissiblidade='" + getAdmissiblidade() + "'" +
            ", envolveGrandeProjeto='" + getEnvolveGrandeProjeto() + "'" +
            ", envolveUnidadeConservacao='" + getEnvolveUnidadeConservacao() + "'" +
            ", linkReferencia='" + getLinkReferencia() + "'" +
            "}";
    }
}
