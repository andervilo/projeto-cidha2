package br.com.cidha.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Direito.
 */
@Entity
@Table(name = "direito")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Direito implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "descricao")
    private String descricao;

    @ManyToMany(mappedBy = "direitos")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnore
    private Set<ProcessoConflito> processoConflitos = new HashSet<>();

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

    public Direito descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Set<ProcessoConflito> getProcessoConflitos() {
        return processoConflitos;
    }

    public Direito processoConflitos(Set<ProcessoConflito> processoConflitos) {
        this.processoConflitos = processoConflitos;
        return this;
    }

    public Direito addProcessoConflito(ProcessoConflito processoConflito) {
        this.processoConflitos.add(processoConflito);
        processoConflito.getDireitos().add(this);
        return this;
    }

    public Direito removeProcessoConflito(ProcessoConflito processoConflito) {
        this.processoConflitos.remove(processoConflito);
        processoConflito.getDireitos().remove(this);
        return this;
    }

    public void setProcessoConflitos(Set<ProcessoConflito> processoConflitos) {
        this.processoConflitos = processoConflitos;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Direito)) {
            return false;
        }
        return id != null && id.equals(((Direito) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Direito{" +
            "id=" + getId() +
            ", descricao='" + getDescricao() + "'" +
            "}";
    }
}
