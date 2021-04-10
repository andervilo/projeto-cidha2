package br.com.cidha.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A UnidadeConservacao.
 */
@Entity
@Table(name = "unidade_conservacao")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class UnidadeConservacao implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "descricao")
    private String descricao;

    @ManyToMany(mappedBy = "unidadeConservacaos")
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

    public String getDescricao() {
        return descricao;
    }

    public UnidadeConservacao descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Set<Processo> getProcessos() {
        return processos;
    }

    public UnidadeConservacao processos(Set<Processo> processos) {
        this.processos = processos;
        return this;
    }

    public UnidadeConservacao addProcesso(Processo processo) {
        this.processos.add(processo);
        processo.getUnidadeConservacaos().add(this);
        return this;
    }

    public UnidadeConservacao removeProcesso(Processo processo) {
        this.processos.remove(processo);
        processo.getUnidadeConservacaos().remove(this);
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
        if (!(o instanceof UnidadeConservacao)) {
            return false;
        }
        return id != null && id.equals(((UnidadeConservacao) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UnidadeConservacao{" +
            "id=" + getId() +
            ", descricao='" + getDescricao() + "'" +
            "}";
    }
}
