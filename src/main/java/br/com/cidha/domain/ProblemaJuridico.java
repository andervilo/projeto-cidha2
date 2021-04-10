package br.com.cidha.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A ProblemaJuridico.
 */
@Entity
@Table(name = "problema_juridico")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ProblemaJuridico implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "prolema_juridico_respondido")
    private String prolemaJuridicoRespondido;

    @Column(name = "folhas_problema_juridico")
    private String folhasProblemaJuridico;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "problema_juridico_fundamentacao_doutrinaria",
               joinColumns = @JoinColumn(name = "problema_juridico_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "fundamentacao_doutrinaria_id", referencedColumnName = "id"))
    private Set<FundamentacaoDoutrinaria> fundamentacaoDoutrinarias = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "problema_juridico_jurisprudencia",
               joinColumns = @JoinColumn(name = "problema_juridico_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "jurisprudencia_id", referencedColumnName = "id"))
    private Set<Jurisprudencia> jurisprudencias = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "problema_juridico_fundamentacao_legal",
               joinColumns = @JoinColumn(name = "problema_juridico_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "fundamentacao_legal_id", referencedColumnName = "id"))
    private Set<FundamentacaoLegal> fundamentacaoLegals = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "problema_juridico_instrumento_internacional",
               joinColumns = @JoinColumn(name = "problema_juridico_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "instrumento_internacional_id", referencedColumnName = "id"))
    private Set<InstrumentoInternacional> instrumentoInternacionals = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "problema_juridico_processo",
               joinColumns = @JoinColumn(name = "problema_juridico_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "processo_id", referencedColumnName = "id"))
    private Set<Processo> processos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProlemaJuridicoRespondido() {
        return prolemaJuridicoRespondido;
    }

    public ProblemaJuridico prolemaJuridicoRespondido(String prolemaJuridicoRespondido) {
        this.prolemaJuridicoRespondido = prolemaJuridicoRespondido;
        return this;
    }

    public void setProlemaJuridicoRespondido(String prolemaJuridicoRespondido) {
        this.prolemaJuridicoRespondido = prolemaJuridicoRespondido;
    }

    public String getFolhasProblemaJuridico() {
        return folhasProblemaJuridico;
    }

    public ProblemaJuridico folhasProblemaJuridico(String folhasProblemaJuridico) {
        this.folhasProblemaJuridico = folhasProblemaJuridico;
        return this;
    }

    public void setFolhasProblemaJuridico(String folhasProblemaJuridico) {
        this.folhasProblemaJuridico = folhasProblemaJuridico;
    }

    public Set<FundamentacaoDoutrinaria> getFundamentacaoDoutrinarias() {
        return fundamentacaoDoutrinarias;
    }

    public ProblemaJuridico fundamentacaoDoutrinarias(Set<FundamentacaoDoutrinaria> fundamentacaoDoutrinarias) {
        this.fundamentacaoDoutrinarias = fundamentacaoDoutrinarias;
        return this;
    }

    public ProblemaJuridico addFundamentacaoDoutrinaria(FundamentacaoDoutrinaria fundamentacaoDoutrinaria) {
        this.fundamentacaoDoutrinarias.add(fundamentacaoDoutrinaria);
        fundamentacaoDoutrinaria.getProblemaJuridicos().add(this);
        return this;
    }

    public ProblemaJuridico removeFundamentacaoDoutrinaria(FundamentacaoDoutrinaria fundamentacaoDoutrinaria) {
        this.fundamentacaoDoutrinarias.remove(fundamentacaoDoutrinaria);
        fundamentacaoDoutrinaria.getProblemaJuridicos().remove(this);
        return this;
    }

    public void setFundamentacaoDoutrinarias(Set<FundamentacaoDoutrinaria> fundamentacaoDoutrinarias) {
        this.fundamentacaoDoutrinarias = fundamentacaoDoutrinarias;
    }

    public Set<Jurisprudencia> getJurisprudencias() {
        return jurisprudencias;
    }

    public ProblemaJuridico jurisprudencias(Set<Jurisprudencia> jurisprudencias) {
        this.jurisprudencias = jurisprudencias;
        return this;
    }

    public ProblemaJuridico addJurisprudencia(Jurisprudencia jurisprudencia) {
        this.jurisprudencias.add(jurisprudencia);
        jurisprudencia.getProblemaJuridicos().add(this);
        return this;
    }

    public ProblemaJuridico removeJurisprudencia(Jurisprudencia jurisprudencia) {
        this.jurisprudencias.remove(jurisprudencia);
        jurisprudencia.getProblemaJuridicos().remove(this);
        return this;
    }

    public void setJurisprudencias(Set<Jurisprudencia> jurisprudencias) {
        this.jurisprudencias = jurisprudencias;
    }

    public Set<FundamentacaoLegal> getFundamentacaoLegals() {
        return fundamentacaoLegals;
    }

    public ProblemaJuridico fundamentacaoLegals(Set<FundamentacaoLegal> fundamentacaoLegals) {
        this.fundamentacaoLegals = fundamentacaoLegals;
        return this;
    }

    public ProblemaJuridico addFundamentacaoLegal(FundamentacaoLegal fundamentacaoLegal) {
        this.fundamentacaoLegals.add(fundamentacaoLegal);
        fundamentacaoLegal.getProblemaJuridicos().add(this);
        return this;
    }

    public ProblemaJuridico removeFundamentacaoLegal(FundamentacaoLegal fundamentacaoLegal) {
        this.fundamentacaoLegals.remove(fundamentacaoLegal);
        fundamentacaoLegal.getProblemaJuridicos().remove(this);
        return this;
    }

    public void setFundamentacaoLegals(Set<FundamentacaoLegal> fundamentacaoLegals) {
        this.fundamentacaoLegals = fundamentacaoLegals;
    }

    public Set<InstrumentoInternacional> getInstrumentoInternacionals() {
        return instrumentoInternacionals;
    }

    public ProblemaJuridico instrumentoInternacionals(Set<InstrumentoInternacional> instrumentoInternacionals) {
        this.instrumentoInternacionals = instrumentoInternacionals;
        return this;
    }

    public ProblemaJuridico addInstrumentoInternacional(InstrumentoInternacional instrumentoInternacional) {
        this.instrumentoInternacionals.add(instrumentoInternacional);
        instrumentoInternacional.getProblemaJuridicos().add(this);
        return this;
    }

    public ProblemaJuridico removeInstrumentoInternacional(InstrumentoInternacional instrumentoInternacional) {
        this.instrumentoInternacionals.remove(instrumentoInternacional);
        instrumentoInternacional.getProblemaJuridicos().remove(this);
        return this;
    }

    public void setInstrumentoInternacionals(Set<InstrumentoInternacional> instrumentoInternacionals) {
        this.instrumentoInternacionals = instrumentoInternacionals;
    }

    public Set<Processo> getProcessos() {
        return processos;
    }

    public ProblemaJuridico processos(Set<Processo> processos) {
        this.processos = processos;
        return this;
    }

    public ProblemaJuridico addProcesso(Processo processo) {
        this.processos.add(processo);
        processo.getProblemaJuridicos().add(this);
        return this;
    }

    public ProblemaJuridico removeProcesso(Processo processo) {
        this.processos.remove(processo);
        processo.getProblemaJuridicos().remove(this);
        return this;
    }

    public void setProcessos(Set<Processo> processos) {
        this.processos = processos;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProblemaJuridico)) {
            return false;
        }
        return id != null && id.equals(((ProblemaJuridico) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProblemaJuridico{" +
            "id=" + getId() +
            ", prolemaJuridicoRespondido='" + getProlemaJuridicoRespondido() + "'" +
            ", folhasProblemaJuridico='" + getFolhasProblemaJuridico() + "'" +
            "}";
    }
}
