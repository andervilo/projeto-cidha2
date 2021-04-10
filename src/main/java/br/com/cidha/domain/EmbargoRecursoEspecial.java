package br.com.cidha.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A EmbargoRecursoEspecial.
 */
@Entity
@Table(name = "embargo_recurso_especial")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class EmbargoRecursoEspecial implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "descricao")
    private String descricao;

    @ManyToOne
    @JsonIgnoreProperties(value = "embargoRecursoEspecials", allowSetters = true)
    private Processo processo;

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

    public EmbargoRecursoEspecial descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Processo getProcesso() {
        return processo;
    }

    public EmbargoRecursoEspecial processo(Processo processo) {
        this.processo = processo;
        return this;
    }

    public void setProcesso(Processo processo) {
        this.processo = processo;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EmbargoRecursoEspecial)) {
            return false;
        }
        return id != null && id.equals(((EmbargoRecursoEspecial) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmbargoRecursoEspecial{" +
            "id=" + getId() +
            ", descricao='" + getDescricao() + "'" +
            "}";
    }
}
