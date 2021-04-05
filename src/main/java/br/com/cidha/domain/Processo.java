package br.com.cidha.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

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

    @OneToMany(mappedBy = "processo")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<ConcessaoLiminar> concessaoLiminars = new HashSet<>();

    @OneToMany(mappedBy = "processo")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<ConcessaoLiminarCassada> concessaoLiminarCassadas = new HashSet<>();

    @OneToMany(mappedBy = "processo")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<EmbargoRespRe> embargoRespRes = new HashSet<>();

    @OneToMany(mappedBy = "processo")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<EmbargoDeclaracaoAgravo> embargoDeclaracaoAgravos = new HashSet<>();

    @OneToMany(mappedBy = "processo")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<EmbargoDeclaracao> embargoDeclaracaos = new HashSet<>();

    @OneToMany(mappedBy = "processo")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<EmbargoRecursoEspecial> embargoRecursoEspecials = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "processos", allowSetters = true)
    private TipoDecisao tipoDecisao;

    @ManyToOne
    @JsonIgnoreProperties(value = "processos", allowSetters = true)
    private TipoEmpreendimento tipoEmpreendimento;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "processo_comarca",
               joinColumns = @JoinColumn(name = "processo_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "comarca_id", referencedColumnName = "id"))
    private Set<Comarca> comarcas = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "processo_quilombo",
               joinColumns = @JoinColumn(name = "processo_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "quilombo_id", referencedColumnName = "id"))
    private Set<Quilombo> quilombos = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "processo_municipio",
               joinColumns = @JoinColumn(name = "processo_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "municipio_id", referencedColumnName = "id"))
    private Set<Municipio> municipios = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "processo_territorio",
               joinColumns = @JoinColumn(name = "processo_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "territorio_id", referencedColumnName = "id"))
    private Set<Territorio> territorios = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "processo_atividade_exploracao_ilegal",
               joinColumns = @JoinColumn(name = "processo_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "atividade_exploracao_ilegal_id", referencedColumnName = "id"))
    private Set<AtividadeExploracaoIlegal> atividadeExploracaoIlegals = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "processo_unidade_conservacao",
               joinColumns = @JoinColumn(name = "processo_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "unidade_conservacao_id", referencedColumnName = "id"))
    private Set<UnidadeConservacao> unidadeConservacaos = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "processo_envolvidos_conflito_litigio",
               joinColumns = @JoinColumn(name = "processo_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "envolvidos_conflito_litigio_id", referencedColumnName = "id"))
    private Set<EnvolvidosConflitoLitigio> envolvidosConflitoLitigios = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "processo_terra_indigena",
               joinColumns = @JoinColumn(name = "processo_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "terra_indigena_id", referencedColumnName = "id"))
    private Set<TerraIndigena> terraIndigenas = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "processo_processo_conflito",
               joinColumns = @JoinColumn(name = "processo_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "processo_conflito_id", referencedColumnName = "id"))
    private Set<ProcessoConflito> processoConflitos = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "processo_parte_interesssada",
               joinColumns = @JoinColumn(name = "processo_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "parte_interesssada_id", referencedColumnName = "id"))
    private Set<ParteInteresssada> parteInteresssadas = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "processo_relator",
               joinColumns = @JoinColumn(name = "processo_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "relator_id", referencedColumnName = "id"))
    private Set<Relator> relators = new HashSet<>();

    @ManyToMany(mappedBy = "processos")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnore
    private Set<ProblemaJuridico> problemaJuridicos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOficio() {
        return oficio;
    }

    public Processo oficio(String oficio) {
        this.oficio = oficio;
        return this;
    }

    public void setOficio(String oficio) {
        this.oficio = oficio;
    }

    public String getAssunto() {
        return assunto;
    }

    public Processo assunto(String assunto) {
        this.assunto = assunto;
        return this;
    }

    public void setAssunto(String assunto) {
        this.assunto = assunto;
    }

    public String getLinkUnico() {
        return linkUnico;
    }

    public Processo linkUnico(String linkUnico) {
        this.linkUnico = linkUnico;
        return this;
    }

    public void setLinkUnico(String linkUnico) {
        this.linkUnico = linkUnico;
    }

    public String getLinkTrf() {
        return linkTrf;
    }

    public Processo linkTrf(String linkTrf) {
        this.linkTrf = linkTrf;
        return this;
    }

    public void setLinkTrf(String linkTrf) {
        this.linkTrf = linkTrf;
    }

    public String getSubsecaoJudiciaria() {
        return subsecaoJudiciaria;
    }

    public Processo subsecaoJudiciaria(String subsecaoJudiciaria) {
        this.subsecaoJudiciaria = subsecaoJudiciaria;
        return this;
    }

    public void setSubsecaoJudiciaria(String subsecaoJudiciaria) {
        this.subsecaoJudiciaria = subsecaoJudiciaria;
    }

    public String getTurmaTrf1() {
        return turmaTrf1;
    }

    public Processo turmaTrf1(String turmaTrf1) {
        this.turmaTrf1 = turmaTrf1;
        return this;
    }

    public void setTurmaTrf1(String turmaTrf1) {
        this.turmaTrf1 = turmaTrf1;
    }

    public String getNumeroProcessoAdministrativo() {
        return numeroProcessoAdministrativo;
    }

    public Processo numeroProcessoAdministrativo(String numeroProcessoAdministrativo) {
        this.numeroProcessoAdministrativo = numeroProcessoAdministrativo;
        return this;
    }

    public void setNumeroProcessoAdministrativo(String numeroProcessoAdministrativo) {
        this.numeroProcessoAdministrativo = numeroProcessoAdministrativo;
    }

    public String getNumeroProcessoJudicialPrimeiraInstancia() {
        return numeroProcessoJudicialPrimeiraInstancia;
    }

    public Processo numeroProcessoJudicialPrimeiraInstancia(String numeroProcessoJudicialPrimeiraInstancia) {
        this.numeroProcessoJudicialPrimeiraInstancia = numeroProcessoJudicialPrimeiraInstancia;
        return this;
    }

    public void setNumeroProcessoJudicialPrimeiraInstancia(String numeroProcessoJudicialPrimeiraInstancia) {
        this.numeroProcessoJudicialPrimeiraInstancia = numeroProcessoJudicialPrimeiraInstancia;
    }

    public String getNumeroProcessoJudicialPrimeiraInstanciaLink() {
        return numeroProcessoJudicialPrimeiraInstanciaLink;
    }

    public Processo numeroProcessoJudicialPrimeiraInstanciaLink(String numeroProcessoJudicialPrimeiraInstanciaLink) {
        this.numeroProcessoJudicialPrimeiraInstanciaLink = numeroProcessoJudicialPrimeiraInstanciaLink;
        return this;
    }

    public void setNumeroProcessoJudicialPrimeiraInstanciaLink(String numeroProcessoJudicialPrimeiraInstanciaLink) {
        this.numeroProcessoJudicialPrimeiraInstanciaLink = numeroProcessoJudicialPrimeiraInstanciaLink;
    }

    public String getNumeroProcessoJudicialPrimeiraInstanciaObservacoes() {
        return numeroProcessoJudicialPrimeiraInstanciaObservacoes;
    }

    public Processo numeroProcessoJudicialPrimeiraInstanciaObservacoes(String numeroProcessoJudicialPrimeiraInstanciaObservacoes) {
        this.numeroProcessoJudicialPrimeiraInstanciaObservacoes = numeroProcessoJudicialPrimeiraInstanciaObservacoes;
        return this;
    }

    public void setNumeroProcessoJudicialPrimeiraInstanciaObservacoes(String numeroProcessoJudicialPrimeiraInstanciaObservacoes) {
        this.numeroProcessoJudicialPrimeiraInstanciaObservacoes = numeroProcessoJudicialPrimeiraInstanciaObservacoes;
    }

    public Boolean isParecer() {
        return parecer;
    }

    public Processo parecer(Boolean parecer) {
        this.parecer = parecer;
        return this;
    }

    public void setParecer(Boolean parecer) {
        this.parecer = parecer;
    }

    public Set<ConcessaoLiminar> getConcessaoLiminars() {
        return concessaoLiminars;
    }

    public Processo concessaoLiminars(Set<ConcessaoLiminar> concessaoLiminars) {
        this.concessaoLiminars = concessaoLiminars;
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
        this.concessaoLiminars = concessaoLiminars;
    }

    public Set<ConcessaoLiminarCassada> getConcessaoLiminarCassadas() {
        return concessaoLiminarCassadas;
    }

    public Processo concessaoLiminarCassadas(Set<ConcessaoLiminarCassada> concessaoLiminarCassadas) {
        this.concessaoLiminarCassadas = concessaoLiminarCassadas;
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
        this.concessaoLiminarCassadas = concessaoLiminarCassadas;
    }

    public Set<EmbargoRespRe> getEmbargoRespRes() {
        return embargoRespRes;
    }

    public Processo embargoRespRes(Set<EmbargoRespRe> embargoRespRes) {
        this.embargoRespRes = embargoRespRes;
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
        this.embargoRespRes = embargoRespRes;
    }

    public Set<EmbargoDeclaracaoAgravo> getEmbargoDeclaracaoAgravos() {
        return embargoDeclaracaoAgravos;
    }

    public Processo embargoDeclaracaoAgravos(Set<EmbargoDeclaracaoAgravo> embargoDeclaracaoAgravos) {
        this.embargoDeclaracaoAgravos = embargoDeclaracaoAgravos;
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
        this.embargoDeclaracaoAgravos = embargoDeclaracaoAgravos;
    }

    public Set<EmbargoDeclaracao> getEmbargoDeclaracaos() {
        return embargoDeclaracaos;
    }

    public Processo embargoDeclaracaos(Set<EmbargoDeclaracao> embargoDeclaracaos) {
        this.embargoDeclaracaos = embargoDeclaracaos;
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
        this.embargoDeclaracaos = embargoDeclaracaos;
    }

    public Set<EmbargoRecursoEspecial> getEmbargoRecursoEspecials() {
        return embargoRecursoEspecials;
    }

    public Processo embargoRecursoEspecials(Set<EmbargoRecursoEspecial> embargoRecursoEspecials) {
        this.embargoRecursoEspecials = embargoRecursoEspecials;
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
        this.embargoRecursoEspecials = embargoRecursoEspecials;
    }

    public TipoDecisao getTipoDecisao() {
        return tipoDecisao;
    }

    public Processo tipoDecisao(TipoDecisao tipoDecisao) {
        this.tipoDecisao = tipoDecisao;
        return this;
    }

    public void setTipoDecisao(TipoDecisao tipoDecisao) {
        this.tipoDecisao = tipoDecisao;
    }

    public TipoEmpreendimento getTipoEmpreendimento() {
        return tipoEmpreendimento;
    }

    public Processo tipoEmpreendimento(TipoEmpreendimento tipoEmpreendimento) {
        this.tipoEmpreendimento = tipoEmpreendimento;
        return this;
    }

    public void setTipoEmpreendimento(TipoEmpreendimento tipoEmpreendimento) {
        this.tipoEmpreendimento = tipoEmpreendimento;
    }

    public Set<Comarca> getComarcas() {
        return comarcas;
    }

    public Processo comarcas(Set<Comarca> comarcas) {
        this.comarcas = comarcas;
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
        return quilombos;
    }

    public Processo quilombos(Set<Quilombo> quilombos) {
        this.quilombos = quilombos;
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
        return municipios;
    }

    public Processo municipios(Set<Municipio> municipios) {
        this.municipios = municipios;
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
        return territorios;
    }

    public Processo territorios(Set<Territorio> territorios) {
        this.territorios = territorios;
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
        return atividadeExploracaoIlegals;
    }

    public Processo atividadeExploracaoIlegals(Set<AtividadeExploracaoIlegal> atividadeExploracaoIlegals) {
        this.atividadeExploracaoIlegals = atividadeExploracaoIlegals;
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
        return unidadeConservacaos;
    }

    public Processo unidadeConservacaos(Set<UnidadeConservacao> unidadeConservacaos) {
        this.unidadeConservacaos = unidadeConservacaos;
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
        return envolvidosConflitoLitigios;
    }

    public Processo envolvidosConflitoLitigios(Set<EnvolvidosConflitoLitigio> envolvidosConflitoLitigios) {
        this.envolvidosConflitoLitigios = envolvidosConflitoLitigios;
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
        return terraIndigenas;
    }

    public Processo terraIndigenas(Set<TerraIndigena> terraIndigenas) {
        this.terraIndigenas = terraIndigenas;
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
        return processoConflitos;
    }

    public Processo processoConflitos(Set<ProcessoConflito> processoConflitos) {
        this.processoConflitos = processoConflitos;
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
        return parteInteresssadas;
    }

    public Processo parteInteresssadas(Set<ParteInteresssada> parteInteresssadas) {
        this.parteInteresssadas = parteInteresssadas;
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
        return relators;
    }

    public Processo relators(Set<Relator> relators) {
        this.relators = relators;
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
        return problemaJuridicos;
    }

    public Processo problemaJuridicos(Set<ProblemaJuridico> problemaJuridicos) {
        this.problemaJuridicos = problemaJuridicos;
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
        return 31;
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
            ", parecer='" + isParecer() + "'" +
            "}";
    }
}
