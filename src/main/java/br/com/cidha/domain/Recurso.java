package br.com.cidha.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Recurso.
 */
@Entity
@Table(name = "recurso")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Recurso implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "observacoes")
    private String observacoes;

    @OneToOne
    @JoinColumn(unique = true)
    private TipoRecurso tipoRecurso;

    @OneToOne
    @JoinColumn(unique = true)
    private OpcaoRecurso opcaoRecurso;

    @ManyToOne
    @JsonIgnoreProperties(value = "recursos", allowSetters = true)
    private Processo processo;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public Recurso observacoes(String observacoes) {
        this.observacoes = observacoes;
        return this;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public TipoRecurso getTipoRecurso() {
        return tipoRecurso;
    }

    public Recurso tipoRecurso(TipoRecurso tipoRecurso) {
        this.tipoRecurso = tipoRecurso;
        return this;
    }

    public void setTipoRecurso(TipoRecurso tipoRecurso) {
        this.tipoRecurso = tipoRecurso;
    }

    public OpcaoRecurso getOpcaoRecurso() {
        return opcaoRecurso;
    }

    public Recurso opcaoRecurso(OpcaoRecurso opcaoRecurso) {
        this.opcaoRecurso = opcaoRecurso;
        return this;
    }

    public void setOpcaoRecurso(OpcaoRecurso opcaoRecurso) {
        this.opcaoRecurso = opcaoRecurso;
    }

    public Processo getProcesso() {
        return processo;
    }

    public Recurso processo(Processo processo) {
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
        if (!(o instanceof Recurso)) {
            return false;
        }
        return id != null && id.equals(((Recurso) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Recurso{" +
            "id=" + getId() +
            ", observacoes='" + getObservacoes() + "'" +
            "}";
    }
}
