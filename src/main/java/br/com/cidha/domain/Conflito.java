package br.com.cidha.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Conflito.
 */
@Entity
@Table(name = "conflito")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Conflito implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "descricao")
    private String descricao;

    @ManyToOne
    @JsonIgnoreProperties(value = "conflitos", allowSetters = true)
    private ProcessoConflito processoConflito;

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

    public Conflito descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public ProcessoConflito getProcessoConflito() {
        return processoConflito;
    }

    public Conflito processoConflito(ProcessoConflito processoConflito) {
        this.processoConflito = processoConflito;
        return this;
    }

    public void setProcessoConflito(ProcessoConflito processoConflito) {
        this.processoConflito = processoConflito;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Conflito)) {
            return false;
        }
        return id != null && id.equals(((Conflito) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Conflito{" +
            "id=" + getId() +
            ", descricao='" + getDescricao() + "'" +
            "}";
    }
}
