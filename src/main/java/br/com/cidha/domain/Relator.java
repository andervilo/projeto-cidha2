package br.com.cidha.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Relator.
 */
@Entity
@Table(name = "relator")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Relator implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "nome")
    private String nome;

    @ManyToMany(mappedBy = "relators")
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

    public Relator nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Set<Processo> getProcessos() {
        return processos;
    }

    public Relator processos(Set<Processo> processos) {
        this.processos = processos;
        return this;
    }

    public Relator addProcesso(Processo processo) {
        this.processos.add(processo);
        processo.getRelators().add(this);
        return this;
    }

    public Relator removeProcesso(Processo processo) {
        this.processos.remove(processo);
        processo.getRelators().remove(this);
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
        if (!(o instanceof Relator)) {
            return false;
        }
        return id != null && id.equals(((Relator) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Relator{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            "}";
    }
}
