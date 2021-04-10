package br.com.cidha.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

/**
 * A FundamentacaoLegal.
 */
@Entity
@Table(name = "fundamentacao_legal")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class FundamentacaoLegal implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "fundamentacao_legal")
    private String fundamentacaoLegal;

    @Column(name = "folhas_fundamentacao_legal")
    private String folhasFundamentacaoLegal;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "fundamentacao_legal_sugerida")
    private String fundamentacaoLegalSugerida;

    @ManyToMany(mappedBy = "fundamentacaoLegals")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "fundamentacaoDoutrinarias", "jurisprudencias", "fundamentacaoLegals", "instrumentoInternacionals", "processos" },
        allowSetters = true
    )
    private Set<ProblemaJuridico> problemaJuridicos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FundamentacaoLegal id(Long id) {
        this.id = id;
        return this;
    }

    public String getFundamentacaoLegal() {
        return this.fundamentacaoLegal;
    }

    public FundamentacaoLegal fundamentacaoLegal(String fundamentacaoLegal) {
        this.fundamentacaoLegal = fundamentacaoLegal;
        return this;
    }

    public void setFundamentacaoLegal(String fundamentacaoLegal) {
        this.fundamentacaoLegal = fundamentacaoLegal;
    }

    public String getFolhasFundamentacaoLegal() {
        return this.folhasFundamentacaoLegal;
    }

    public FundamentacaoLegal folhasFundamentacaoLegal(String folhasFundamentacaoLegal) {
        this.folhasFundamentacaoLegal = folhasFundamentacaoLegal;
        return this;
    }

    public void setFolhasFundamentacaoLegal(String folhasFundamentacaoLegal) {
        this.folhasFundamentacaoLegal = folhasFundamentacaoLegal;
    }

    public String getFundamentacaoLegalSugerida() {
        return this.fundamentacaoLegalSugerida;
    }

    public FundamentacaoLegal fundamentacaoLegalSugerida(String fundamentacaoLegalSugerida) {
        this.fundamentacaoLegalSugerida = fundamentacaoLegalSugerida;
        return this;
    }

    public void setFundamentacaoLegalSugerida(String fundamentacaoLegalSugerida) {
        this.fundamentacaoLegalSugerida = fundamentacaoLegalSugerida;
    }

    public Set<ProblemaJuridico> getProblemaJuridicos() {
        return this.problemaJuridicos;
    }

    public FundamentacaoLegal problemaJuridicos(Set<ProblemaJuridico> problemaJuridicos) {
        this.setProblemaJuridicos(problemaJuridicos);
        return this;
    }

    public FundamentacaoLegal addProblemaJuridico(ProblemaJuridico problemaJuridico) {
        this.problemaJuridicos.add(problemaJuridico);
        problemaJuridico.getFundamentacaoLegals().add(this);
        return this;
    }

    public FundamentacaoLegal removeProblemaJuridico(ProblemaJuridico problemaJuridico) {
        this.problemaJuridicos.remove(problemaJuridico);
        problemaJuridico.getFundamentacaoLegals().remove(this);
        return this;
    }

    public void setProblemaJuridicos(Set<ProblemaJuridico> problemaJuridicos) {
        if (this.problemaJuridicos != null) {
            this.problemaJuridicos.forEach(i -> i.removeFundamentacaoLegal(this));
        }
        if (problemaJuridicos != null) {
            problemaJuridicos.forEach(i -> i.addFundamentacaoLegal(this));
        }
        this.problemaJuridicos = problemaJuridicos;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FundamentacaoLegal)) {
            return false;
        }
        return id != null && id.equals(((FundamentacaoLegal) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FundamentacaoLegal{" +
            "id=" + getId() +
            ", fundamentacaoLegal='" + getFundamentacaoLegal() + "'" +
            ", folhasFundamentacaoLegal='" + getFolhasFundamentacaoLegal() + "'" +
            ", fundamentacaoLegalSugerida='" + getFundamentacaoLegalSugerida() + "'" +
            "}";
    }
}
