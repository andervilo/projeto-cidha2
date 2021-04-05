package br.com.cidha.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A EtniaIndigena.
 */
@Entity
@Table(name = "etnia_indigena")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class EtniaIndigena implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "nome")
    private String nome;

    @ManyToMany(mappedBy = "etnias")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnore
    private Set<TerraIndigena> terraIndigenas = new HashSet<>();

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

    public EtniaIndigena nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Set<TerraIndigena> getTerraIndigenas() {
        return terraIndigenas;
    }

    public EtniaIndigena terraIndigenas(Set<TerraIndigena> terraIndigenas) {
        this.terraIndigenas = terraIndigenas;
        return this;
    }

    public EtniaIndigena addTerraIndigena(TerraIndigena terraIndigena) {
        this.terraIndigenas.add(terraIndigena);
        terraIndigena.getEtnias().add(this);
        return this;
    }

    public EtniaIndigena removeTerraIndigena(TerraIndigena terraIndigena) {
        this.terraIndigenas.remove(terraIndigena);
        terraIndigena.getEtnias().remove(this);
        return this;
    }

    public void setTerraIndigenas(Set<TerraIndigena> terraIndigenas) {
        this.terraIndigenas = terraIndigenas;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EtniaIndigena)) {
            return false;
        }
        return id != null && id.equals(((EtniaIndigena) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EtniaIndigena{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            "}";
    }
}
