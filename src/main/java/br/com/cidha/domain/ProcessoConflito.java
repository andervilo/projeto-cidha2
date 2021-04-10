package br.com.cidha.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A ProcessoConflito.
 */
@Entity
@Table(name = "processo_conflito")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ProcessoConflito implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "inicio_conflito_observacoes")
    private String inicioConflitoObservacoes;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "historico_conlito")
    private String historicoConlito;

    @Column(name = "nome_caso_comuidade")
    private String nomeCasoComuidade;

    @Column(name = "consulta_previa")
    private Boolean consultaPrevia;

    @OneToMany(mappedBy = "processoConflito")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Conflito> conflitos = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "processo_conflito_direito",
               joinColumns = @JoinColumn(name = "processo_conflito_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "direito_id", referencedColumnName = "id"))
    private Set<Direito> direitos = new HashSet<>();

    @ManyToMany(mappedBy = "processoConflitos")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnore
    private Set<Processo> processos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInicioConflitoObservacoes() {
        return inicioConflitoObservacoes;
    }

    public ProcessoConflito inicioConflitoObservacoes(String inicioConflitoObservacoes) {
        this.inicioConflitoObservacoes = inicioConflitoObservacoes;
        return this;
    }

    public void setInicioConflitoObservacoes(String inicioConflitoObservacoes) {
        this.inicioConflitoObservacoes = inicioConflitoObservacoes;
    }

    public String getHistoricoConlito() {
        return historicoConlito;
    }

    public ProcessoConflito historicoConlito(String historicoConlito) {
        this.historicoConlito = historicoConlito;
        return this;
    }

    public void setHistoricoConlito(String historicoConlito) {
        this.historicoConlito = historicoConlito;
    }

    public String getNomeCasoComuidade() {
        return nomeCasoComuidade;
    }

    public ProcessoConflito nomeCasoComuidade(String nomeCasoComuidade) {
        this.nomeCasoComuidade = nomeCasoComuidade;
        return this;
    }

    public void setNomeCasoComuidade(String nomeCasoComuidade) {
        this.nomeCasoComuidade = nomeCasoComuidade;
    }

    public Boolean isConsultaPrevia() {
        return consultaPrevia;
    }

    public ProcessoConflito consultaPrevia(Boolean consultaPrevia) {
        this.consultaPrevia = consultaPrevia;
        return this;
    }

    public void setConsultaPrevia(Boolean consultaPrevia) {
        this.consultaPrevia = consultaPrevia;
    }

    public Set<Conflito> getConflitos() {
        return conflitos;
    }

    public ProcessoConflito conflitos(Set<Conflito> conflitos) {
        this.conflitos = conflitos;
        return this;
    }

    public ProcessoConflito addConflito(Conflito conflito) {
        this.conflitos.add(conflito);
        conflito.setProcessoConflito(this);
        return this;
    }

    public ProcessoConflito removeConflito(Conflito conflito) {
        this.conflitos.remove(conflito);
        conflito.setProcessoConflito(null);
        return this;
    }

    public void setConflitos(Set<Conflito> conflitos) {
        this.conflitos = conflitos;
    }

    public Set<Direito> getDireitos() {
        return direitos;
    }

    public ProcessoConflito direitos(Set<Direito> direitos) {
        this.direitos = direitos;
        return this;
    }

    public ProcessoConflito addDireito(Direito direito) {
        this.direitos.add(direito);
        direito.getProcessoConflitos().add(this);
        return this;
    }

    public ProcessoConflito removeDireito(Direito direito) {
        this.direitos.remove(direito);
        direito.getProcessoConflitos().remove(this);
        return this;
    }

    public void setDireitos(Set<Direito> direitos) {
        this.direitos = direitos;
    }

    public Set<Processo> getProcessos() {
        return processos;
    }

    public ProcessoConflito processos(Set<Processo> processos) {
        this.processos = processos;
        return this;
    }

    public ProcessoConflito addProcesso(Processo processo) {
        this.processos.add(processo);
        processo.getProcessoConflitos().add(this);
        return this;
    }

    public ProcessoConflito removeProcesso(Processo processo) {
        this.processos.remove(processo);
        processo.getProcessoConflitos().remove(this);
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
        if (!(o instanceof ProcessoConflito)) {
            return false;
        }
        return id != null && id.equals(((ProcessoConflito) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProcessoConflito{" +
            "id=" + getId() +
            ", inicioConflitoObservacoes='" + getInicioConflitoObservacoes() + "'" +
            ", historicoConlito='" + getHistoricoConlito() + "'" +
            ", nomeCasoComuidade='" + getNomeCasoComuidade() + "'" +
            ", consultaPrevia='" + isConsultaPrevia() + "'" +
            "}";
    }
}
