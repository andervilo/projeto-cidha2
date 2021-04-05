package br.com.cidha.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Territorio.
 */
@Entity
@Table(name = "territorio")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Territorio implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "nome")
    private String nome;

    @ManyToMany(mappedBy = "territorios")
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

    public Territorio nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Set<Processo> getProcessos() {
        return processos;
    }

    public Territorio processos(Set<Processo> processos) {
        this.processos = processos;
        return this;
    }

    public Territorio addProcesso(Processo processo) {
        this.processos.add(processo);
        processo.getTerritorios().add(this);
        return this;
    }

    public Territorio removeProcesso(Processo processo) {
        this.processos.remove(processo);
        processo.getTerritorios().remove(this);
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
        if (!(o instanceof Territorio)) {
            return false;
        }
        return id != null && id.equals(((Territorio) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Territorio{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            "}";
    }
}
