package br.com.cidha.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ParteInteresssada.
 */
@Entity
@Table(name = "parte_interesssada")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ParteInteresssada implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "classificacao")
    private String classificacao;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(
        name = "rel_parte_interesssada__representante_legal",
        joinColumns = @JoinColumn(name = "parte_interesssada_id"),
        inverseJoinColumns = @JoinColumn(name = "representante_legal_id")
    )
    @JsonIgnoreProperties(value = { "tipoRepresentante", "processoConflitos" }, allowSetters = true)
    private Set<RepresentanteLegal> representanteLegals = new HashSet<>();

    @ManyToMany(mappedBy = "parteInteresssadas")
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
            "secaoJudiciaria",
            "comarcas",
            "municipios",
            "territorios",
            "atividadeExploracaoIlegals",
            "unidadeConservacaos",
            "envolvidosConflitoLitigios",
            "terraIndigenas",
            "processoConflitos",
            "parteInteresssadas",
            "relators",
            "quilombos",
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

    public ParteInteresssada id(Long id) {
        this.id = id;
        return this;
    }

    public String getNome() {
        return this.nome;
    }

    public ParteInteresssada nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getClassificacao() {
        return this.classificacao;
    }

    public ParteInteresssada classificacao(String classificacao) {
        this.classificacao = classificacao;
        return this;
    }

    public void setClassificacao(String classificacao) {
        this.classificacao = classificacao;
    }

    public Set<RepresentanteLegal> getRepresentanteLegals() {
        return this.representanteLegals;
    }

    public ParteInteresssada representanteLegals(Set<RepresentanteLegal> representanteLegals) {
        this.setRepresentanteLegals(representanteLegals);
        return this;
    }

    public ParteInteresssada addRepresentanteLegal(RepresentanteLegal representanteLegal) {
        this.representanteLegals.add(representanteLegal);
        representanteLegal.getProcessoConflitos().add(this);
        return this;
    }

    public ParteInteresssada removeRepresentanteLegal(RepresentanteLegal representanteLegal) {
        this.representanteLegals.remove(representanteLegal);
        representanteLegal.getProcessoConflitos().remove(this);
        return this;
    }

    public void setRepresentanteLegals(Set<RepresentanteLegal> representanteLegals) {
        this.representanteLegals = representanteLegals;
    }

    public Set<Processo> getProcessos() {
        return this.processos;
    }

    public ParteInteresssada processos(Set<Processo> processos) {
        this.setProcessos(processos);
        return this;
    }

    public ParteInteresssada addProcesso(Processo processo) {
        this.processos.add(processo);
        processo.getParteInteresssadas().add(this);
        return this;
    }

    public ParteInteresssada removeProcesso(Processo processo) {
        this.processos.remove(processo);
        processo.getParteInteresssadas().remove(this);
        return this;
    }

    public void setProcessos(Set<Processo> processos) {
        if (this.processos != null) {
            this.processos.forEach(i -> i.removeParteInteresssada(this));
        }
        if (processos != null) {
            processos.forEach(i -> i.addParteInteresssada(this));
        }
        this.processos = processos;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ParteInteresssada)) {
            return false;
        }
        return id != null && id.equals(((ParteInteresssada) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ParteInteresssada{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", classificacao='" + getClassificacao() + "'" +
            "}";
    }
}
