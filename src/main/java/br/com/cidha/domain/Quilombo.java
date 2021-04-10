package br.com.cidha.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Quilombo.
 */
@Entity
@Table(name = "quilombo")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Quilombo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "nome")
    private String nome;

    @ManyToMany(mappedBy = "quilombos")
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

    public Quilombo nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Set<Processo> getProcessos() {
        return processos;
    }

    public Quilombo processos(Set<Processo> processos) {
        this.processos = processos;
        return this;
    }

    public Quilombo addProcesso(Processo processo) {
        this.processos.add(processo);
        processo.getQuilombos().add(this);
        return this;
    }

    public Quilombo removeProcesso(Processo processo) {
        this.processos.remove(processo);
        processo.getQuilombos().remove(this);
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
        if (!(o instanceof Quilombo)) {
            return false;
        }
        return id != null && id.equals(((Quilombo) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Quilombo{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            "}";
    }
}
