package br.com.cidha.domain;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A SecaoJudiciaria.
 */
@Entity
@Table(name = "secao_judiciaria")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SecaoJudiciaria implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sigla")
    private String sigla;

    @Column(name = "nome")
    private String nome;

    @ManyToOne
    private SubsecaoJudiciaria subsecaoJudiciaria;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SecaoJudiciaria id(Long id) {
        this.id = id;
        return this;
    }

    public String getSigla() {
        return this.sigla;
    }

    public SecaoJudiciaria sigla(String sigla) {
        this.sigla = sigla;
        return this;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public String getNome() {
        return this.nome;
    }

    public SecaoJudiciaria nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public SubsecaoJudiciaria getSubsecaoJudiciaria() {
        return this.subsecaoJudiciaria;
    }

    public SecaoJudiciaria subsecaoJudiciaria(SubsecaoJudiciaria subsecaoJudiciaria) {
        this.setSubsecaoJudiciaria(subsecaoJudiciaria);
        return this;
    }

    public void setSubsecaoJudiciaria(SubsecaoJudiciaria subsecaoJudiciaria) {
        this.subsecaoJudiciaria = subsecaoJudiciaria;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SecaoJudiciaria)) {
            return false;
        }
        return id != null && id.equals(((SecaoJudiciaria) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SecaoJudiciaria{" +
            "id=" + getId() +
            ", sigla='" + getSigla() + "'" +
            ", nome='" + getNome() + "'" +
            "}";
    }
}
