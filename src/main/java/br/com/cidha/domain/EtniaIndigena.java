package br.com.cidha.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A EtniaIndigena.
 */
@Entity
@Table(name = "etnia_indigena")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class EtniaIndigena implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome")
    private String nome;

    @ManyToMany(mappedBy = "etnias")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "etnias", "processos" }, allowSetters = true)
    private Set<TerraIndigena> terraIndigenas = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EtniaIndigena id(Long id) {
        this.id = id;
        return this;
    }

    public String getNome() {
        return this.nome;
    }

    public EtniaIndigena nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Set<TerraIndigena> getTerraIndigenas() {
        return this.terraIndigenas;
    }

    public EtniaIndigena terraIndigenas(Set<TerraIndigena> terraIndigenas) {
        this.setTerraIndigenas(terraIndigenas);
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
        if (this.terraIndigenas != null) {
            this.terraIndigenas.forEach(i -> i.removeEtnia(this));
        }
        if (terraIndigenas != null) {
            terraIndigenas.forEach(i -> i.addEtnia(this));
        }
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
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
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
