package br.com.cidha.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
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

    @Column(name = "apelacao")
    private String apelacao;

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
    private Set<EmbargoRespRe> embargoRespRes = new HashSet<>();

    @OneToMany(mappedBy = "processo")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "processo" }, allowSetters = true)
    private Set<EmbargoDeclaracaoAgravo> embargoDeclaracaoAgravos = new HashSet<>();

    @OneToMany(mappedBy = "processo")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "processo" }, allowSetters = true)
    private Set<EmbargoDeclaracao> embargoDeclaracaos = new HashSet<>();

    @OneToMany(mappedBy = "processo")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "processo" }, allowSetters = true)
    private Set<EmbargoRecursoEspecial> embargoRecursoEspecials = new HashSet<>();

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
            ", oficio='" + getOficio() + "'" +
            ", assunto='" + getAssunto() + "'" +
            ", linkUnico='" + getLinkUnico() + "'" +
            ", linkTrf='" + getLinkTrf() + "'" +
            ", subsecaoJudiciaria='" + getSubsecaoJudiciaria() + "'" +
            ", turmaTrf1='" + getTurmaTrf1() + "'" +
            ", numeroProcessoAdministrativo='" + getNumeroProcessoAdministrativo() + "'" +
            ", numeroProcessoJudicialPrimeiraInstancia='" + getNumeroProcessoJudicialPrimeiraInstancia() + "'" +
            ", numeroProcessoJudicialPrimeiraInstanciaLink='" + getNumeroProcessoJudicialPrimeiraInstanciaLink() + "'" +
            ", numeroProcessoJudicialPrimeiraInstanciaObservacoes='" + getNumeroProcessoJudicialPrimeiraInstanciaObservacoes() + "'" +
            ", parecer='" + getParecer() + "'" +
            ", apelacao='" + getApelacao() + "'" +
            "}";
    }
}
