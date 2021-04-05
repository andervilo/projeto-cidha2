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
 * A FundamentacaoDoutrinaria.
 */
@Entity
@Table(name = "fundamentacao_doutrinaria")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class FundamentacaoDoutrinaria implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "fundamentacao_doutrinaria_citada")
    private String fundamentacaoDoutrinariaCitada;

    @Column(name = "folhas_fundamentacao_doutrinaria")
    private String folhasFundamentacaoDoutrinaria;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "fundamentacao_doutrinaria_sugerida")
    private String fundamentacaoDoutrinariaSugerida;

    @ManyToMany(mappedBy = "fundamentacaoDoutrinarias")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnore
    private Set<ProblemaJuridico> problemaJuridicos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFundamentacaoDoutrinariaCitada() {
        return fundamentacaoDoutrinariaCitada;
    }

    public FundamentacaoDoutrinaria fundamentacaoDoutrinariaCitada(String fundamentacaoDoutrinariaCitada) {
        this.fundamentacaoDoutrinariaCitada = fundamentacaoDoutrinariaCitada;
        return this;
    }

    public void setFundamentacaoDoutrinariaCitada(String fundamentacaoDoutrinariaCitada) {
        this.fundamentacaoDoutrinariaCitada = fundamentacaoDoutrinariaCitada;
    }

    public String getFolhasFundamentacaoDoutrinaria() {
        return folhasFundamentacaoDoutrinaria;
    }

    public FundamentacaoDoutrinaria folhasFundamentacaoDoutrinaria(String folhasFundamentacaoDoutrinaria) {
        this.folhasFundamentacaoDoutrinaria = folhasFundamentacaoDoutrinaria;
        return this;
    }

    public void setFolhasFundamentacaoDoutrinaria(String folhasFundamentacaoDoutrinaria) {
        this.folhasFundamentacaoDoutrinaria = folhasFundamentacaoDoutrinaria;
    }

    public String getFundamentacaoDoutrinariaSugerida() {
        return fundamentacaoDoutrinariaSugerida;
    }

    public FundamentacaoDoutrinaria fundamentacaoDoutrinariaSugerida(String fundamentacaoDoutrinariaSugerida) {
        this.fundamentacaoDoutrinariaSugerida = fundamentacaoDoutrinariaSugerida;
        return this;
    }

    public void setFundamentacaoDoutrinariaSugerida(String fundamentacaoDoutrinariaSugerida) {
        this.fundamentacaoDoutrinariaSugerida = fundamentacaoDoutrinariaSugerida;
    }

    public Set<ProblemaJuridico> getProblemaJuridicos() {
        return problemaJuridicos;
    }

    public FundamentacaoDoutrinaria problemaJuridicos(Set<ProblemaJuridico> problemaJuridicos) {
        this.problemaJuridicos = problemaJuridicos;
        return this;
    }

    public FundamentacaoDoutrinaria addProblemaJuridico(ProblemaJuridico problemaJuridico) {
        this.problemaJuridicos.add(problemaJuridico);
        problemaJuridico.getFundamentacaoDoutrinarias().add(this);
        return this;
    }

    public FundamentacaoDoutrinaria removeProblemaJuridico(ProblemaJuridico problemaJuridico) {
        this.problemaJuridicos.remove(problemaJuridico);
        problemaJuridico.getFundamentacaoDoutrinarias().remove(this);
        return this;
    }

    public void setProblemaJuridicos(Set<ProblemaJuridico> problemaJuridicos) {
        this.problemaJuridicos = problemaJuridicos;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FundamentacaoDoutrinaria)) {
            return false;
        }
        return id != null && id.equals(((FundamentacaoDoutrinaria) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FundamentacaoDoutrinaria{" +
            "id=" + getId() +
            ", fundamentacaoDoutrinariaCitada='" + getFundamentacaoDoutrinariaCitada() + "'" +
            ", folhasFundamentacaoDoutrinaria='" + getFolhasFundamentacaoDoutrinaria() + "'" +
            ", fundamentacaoDoutrinariaSugerida='" + getFundamentacaoDoutrinariaSugerida() + "'" +
            "}";
    }
}
