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
    @JsonIgnoreProperties(value = { "processoConflito" }, allowSetters = true)
    private Set<Conflito> conflitos = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(
        name = "rel_processo_conflito__direito",
        joinColumns = @JoinColumn(name = "processo_conflito_id"),
        inverseJoinColumns = @JoinColumn(name = "direito_id")
    )
    @JsonIgnoreProperties(value = { "processoConflitos" }, allowSetters = true)
    private Set<Direito> direitos = new HashSet<>();

    @ManyToMany(mappedBy = "processoConflitos")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = {
            "concessaoLiminars",
            "concessaoLiminarCassadas",
            "embargoDeclaracaos",
            "embargoDeclaracaoAgravos",
            "embargoRecursoEspecials",
            "embargoRespRes",
            "tipoDecisao",
            "tipoEmpreendimento",
            "comarcas",
            "quilombos",
            "municipios",
            "territorios",
            "atividadeExploracaoIlegals",
            "unidadeConservacaos",
            "envolvidosConflitoLitigios",
            "terraIndigenas",
            "processoConflitos",
            "parteInteresssadas",
            "relators",
            "problemaJuridicos",
        },
        allowSetters = true
    )
    private Set<Processo> processos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProcessoConflito id(Long id) {
        this.id = id;
        return this;
    }

    public String getInicioConflitoObservacoes() {
        return this.inicioConflitoObservacoes;
    }

    public ProcessoConflito inicioConflitoObservacoes(String inicioConflitoObservacoes) {
        this.inicioConflitoObservacoes = inicioConflitoObservacoes;
        return this;
    }

    public void setInicioConflitoObservacoes(String inicioConflitoObservacoes) {
        this.inicioConflitoObservacoes = inicioConflitoObservacoes;
    }

    public String getHistoricoConlito() {
        return this.historicoConlito;
    }

    public ProcessoConflito historicoConlito(String historicoConlito) {
        this.historicoConlito = historicoConlito;
        return this;
    }

    public void setHistoricoConlito(String historicoConlito) {
        this.historicoConlito = historicoConlito;
    }

    public String getNomeCasoComuidade() {
        return this.nomeCasoComuidade;
    }

    public ProcessoConflito nomeCasoComuidade(String nomeCasoComuidade) {
        this.nomeCasoComuidade = nomeCasoComuidade;
        return this;
    }

    public void setNomeCasoComuidade(String nomeCasoComuidade) {
        this.nomeCasoComuidade = nomeCasoComuidade;
    }

    public Boolean getConsultaPrevia() {
        return this.consultaPrevia;
    }

    public ProcessoConflito consultaPrevia(Boolean consultaPrevia) {
        this.consultaPrevia = consultaPrevia;
        return this;
    }

    public void setConsultaPrevia(Boolean consultaPrevia) {
        this.consultaPrevia = consultaPrevia;
    }

    public Set<Conflito> getConflitos() {
        return this.conflitos;
    }

    public ProcessoConflito conflitos(Set<Conflito> conflitos) {
        this.setConflitos(conflitos);
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
        if (this.conflitos != null) {
            this.conflitos.forEach(i -> i.setProcessoConflito(null));
        }
        if (conflitos != null) {
            conflitos.forEach(i -> i.setProcessoConflito(this));
        }
        this.conflitos = conflitos;
    }

    public Set<Direito> getDireitos() {
        return this.direitos;
    }

    public ProcessoConflito direitos(Set<Direito> direitos) {
        this.setDireitos(direitos);
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
        return this.processos;
    }

    public ProcessoConflito processos(Set<Processo> processos) {
        this.setProcessos(processos);
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
        if (this.processos != null) {
            this.processos.forEach(i -> i.removeProcessoConflito(this));
        }
        if (processos != null) {
            processos.forEach(i -> i.addProcessoConflito(this));
        }
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
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProcessoConflito{" +
            "id=" + getId() +
            ", inicioConflitoObservacoes='" + getInicioConflitoObservacoes() + "'" +
            ", historicoConlito='" + getHistoricoConlito() + "'" +
            ", nomeCasoComuidade='" + getNomeCasoComuidade() + "'" +
            ", consultaPrevia='" + getConsultaPrevia() + "'" +
            "}";
    }
}
