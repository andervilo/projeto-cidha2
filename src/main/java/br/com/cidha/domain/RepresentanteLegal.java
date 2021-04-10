package br.com.cidha.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A RepresentanteLegal.
 */
@Entity
@Table(name = "representante_legal")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class RepresentanteLegal implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "nome")
    private String nome;

    @ManyToOne
    @JsonIgnoreProperties(value = "representanteLegals", allowSetters = true)
    private TipoRepresentante tipoRepresentante;

    @ManyToMany(mappedBy = "representanteLegals")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnore
    private Set<ParteInteresssada> processoConflitos = new HashSet<>();

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

    public RepresentanteLegal nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public TipoRepresentante getTipoRepresentante() {
        return tipoRepresentante;
    }

    public RepresentanteLegal tipoRepresentante(TipoRepresentante tipoRepresentante) {
        this.tipoRepresentante = tipoRepresentante;
        return this;
    }

    public void setTipoRepresentante(TipoRepresentante tipoRepresentante) {
        this.tipoRepresentante = tipoRepresentante;
    }

    public Set<ParteInteresssada> getProcessoConflitos() {
        return processoConflitos;
    }

    public RepresentanteLegal processoConflitos(Set<ParteInteresssada> parteInteresssadas) {
        this.processoConflitos = parteInteresssadas;
        return this;
    }

    public RepresentanteLegal addProcessoConflito(ParteInteresssada parteInteresssada) {
        this.processoConflitos.add(parteInteresssada);
        parteInteresssada.getRepresentanteLegals().add(this);
        return this;
    }

    public RepresentanteLegal removeProcessoConflito(ParteInteresssada parteInteresssada) {
        this.processoConflitos.remove(parteInteresssada);
        parteInteresssada.getRepresentanteLegals().remove(this);
        return this;
    }

    public void setProcessoConflitos(Set<ParteInteresssada> parteInteresssadas) {
        this.processoConflitos = parteInteresssadas;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RepresentanteLegal)) {
            return false;
        }
        return id != null && id.equals(((RepresentanteLegal) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RepresentanteLegal{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            "}";
    }
}
