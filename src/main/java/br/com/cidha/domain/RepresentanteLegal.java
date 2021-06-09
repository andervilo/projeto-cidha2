package br.com.cidha.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A RepresentanteLegal.
 */
@Entity
@Table(name = "representante_legal")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class RepresentanteLegal implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome")
    private String nome;

    @ManyToOne
    private TipoRepresentante tipoRepresentante;

    @ManyToMany(mappedBy = "representanteLegals")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "representanteLegals", "processos" }, allowSetters = true)
    private Set<ParteInteresssada> processoConflitos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RepresentanteLegal id(Long id) {
        this.id = id;
        return this;
    }

    public String getNome() {
        return this.nome;
    }

    public RepresentanteLegal nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public TipoRepresentante getTipoRepresentante() {
        return this.tipoRepresentante;
    }

    public RepresentanteLegal tipoRepresentante(TipoRepresentante tipoRepresentante) {
        this.setTipoRepresentante(tipoRepresentante);
        return this;
    }

    public void setTipoRepresentante(TipoRepresentante tipoRepresentante) {
        this.tipoRepresentante = tipoRepresentante;
    }

    public Set<ParteInteresssada> getProcessoConflitos() {
        return this.processoConflitos;
    }

    public RepresentanteLegal processoConflitos(Set<ParteInteresssada> parteInteresssadas) {
        this.setProcessoConflitos(parteInteresssadas);
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
        if (this.processoConflitos != null) {
            this.processoConflitos.forEach(i -> i.removeRepresentanteLegal(this));
        }
        if (parteInteresssadas != null) {
            parteInteresssadas.forEach(i -> i.addRepresentanteLegal(this));
        }
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
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
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
