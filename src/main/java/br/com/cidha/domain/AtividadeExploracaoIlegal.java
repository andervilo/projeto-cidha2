package br.com.cidha.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A AtividadeExploracaoIlegal.
 */
@Entity
@Table(name = "atividade_exploracao_ilegal")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AtividadeExploracaoIlegal implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "descricao")
    private String descricao;

    @ManyToMany(mappedBy = "atividadeExploracaoIlegals")
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

    public AtividadeExploracaoIlegal descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Set<Processo> getProcessos() {
        return processos;
    }

    public AtividadeExploracaoIlegal processos(Set<Processo> processos) {
        this.processos = processos;
        return this;
    }

    public AtividadeExploracaoIlegal addProcesso(Processo processo) {
        this.processos.add(processo);
        processo.getAtividadeExploracaoIlegals().add(this);
        return this;
    }

    public AtividadeExploracaoIlegal removeProcesso(Processo processo) {
        this.processos.remove(processo);
        processo.getAtividadeExploracaoIlegals().remove(this);
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
        if (!(o instanceof AtividadeExploracaoIlegal)) {
            return false;
        }
        return id != null && id.equals(((AtividadeExploracaoIlegal) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AtividadeExploracaoIlegal{" +
            "id=" + getId() +
            ", descricao='" + getDescricao() + "'" +
            "}";
    }
}
