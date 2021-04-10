package br.com.cidha.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A EmbargoDeclaracao.
 */
@Entity
@Table(name = "embargo_declaracao")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class EmbargoDeclaracao implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "descricao")
    private String descricao;

    @ManyToOne
    @JsonIgnoreProperties(value = "embargoDeclaracaos", allowSetters = true)
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

    public EmbargoDeclaracao descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Processo getProcesso() {
        return processo;
    }

    public EmbargoDeclaracao processo(Processo processo) {
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
        if (!(o instanceof EmbargoDeclaracao)) {
            return false;
        }
        return id != null && id.equals(((EmbargoDeclaracao) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmbargoDeclaracao{" +
            "id=" + getId() +
            ", descricao='" + getDescricao() + "'" +
            "}";
    }
}
