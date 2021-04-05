package br.com.cidha.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A ParteInteresssada.
 */
@Entity
@Table(name = "parte_interesssada")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ParteInteresssada implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "classificacao")
    private String classificacao;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "parte_interesssada_representante_legal",
               joinColumns = @JoinColumn(name = "parte_interesssada_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "representante_legal_id", referencedColumnName = "id"))
    private Set<RepresentanteLegal> representanteLegals = new HashSet<>();

    @ManyToMany(mappedBy = "parteInteresssadas")
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

    public String getNome() {
        return nome;
    }

    public ParteInteresssada nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getClassificacao() {
        return classificacao;
    }

    public ParteInteresssada classificacao(String classificacao) {
        this.classificacao = classificacao;
        return this;
    }

    public void setClassificacao(String classificacao) {
        this.classificacao = classificacao;
    }

    public Set<RepresentanteLegal> getRepresentanteLegals() {
        return representanteLegals;
    }

    public ParteInteresssada representanteLegals(Set<RepresentanteLegal> representanteLegals) {
        this.representanteLegals = representanteLegals;
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
        return processos;
    }

    public ParteInteresssada processos(Set<Processo> processos) {
        this.processos = processos;
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
        return 31;
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
