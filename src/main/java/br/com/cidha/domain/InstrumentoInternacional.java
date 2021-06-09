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
 * A InstrumentoInternacional.
 */
@Entity
@Table(name = "instrumento_internacional")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class InstrumentoInternacional implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "instrumento_internacional_citado_descricao")
    private String instrumentoInternacionalCitadoDescricao;

    @Column(name = "folhas_instrumento_internacional")
    private String folhasInstrumentoInternacional;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "instrumento_internacional_sugerido")
    private String instrumentoInternacionalSugerido;

    @ManyToMany(mappedBy = "instrumentoInternacionals")
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

    public InstrumentoInternacional id(Long id) {
        this.id = id;
        return this;
    }

    public String getInstrumentoInternacionalCitadoDescricao() {
        return this.instrumentoInternacionalCitadoDescricao;
    }

    public InstrumentoInternacional instrumentoInternacionalCitadoDescricao(String instrumentoInternacionalCitadoDescricao) {
        this.instrumentoInternacionalCitadoDescricao = instrumentoInternacionalCitadoDescricao;
        return this;
    }

    public void setInstrumentoInternacionalCitadoDescricao(String instrumentoInternacionalCitadoDescricao) {
        this.instrumentoInternacionalCitadoDescricao = instrumentoInternacionalCitadoDescricao;
    }

    public String getFolhasInstrumentoInternacional() {
        return this.folhasInstrumentoInternacional;
    }

    public InstrumentoInternacional folhasInstrumentoInternacional(String folhasInstrumentoInternacional) {
        this.folhasInstrumentoInternacional = folhasInstrumentoInternacional;
        return this;
    }

    public void setFolhasInstrumentoInternacional(String folhasInstrumentoInternacional) {
        this.folhasInstrumentoInternacional = folhasInstrumentoInternacional;
    }

    public String getInstrumentoInternacionalSugerido() {
        return this.instrumentoInternacionalSugerido;
    }

    public InstrumentoInternacional instrumentoInternacionalSugerido(String instrumentoInternacionalSugerido) {
        this.instrumentoInternacionalSugerido = instrumentoInternacionalSugerido;
        return this;
    }

    public void setInstrumentoInternacionalSugerido(String instrumentoInternacionalSugerido) {
        this.instrumentoInternacionalSugerido = instrumentoInternacionalSugerido;
    }

    public Set<ProblemaJuridico> getProblemaJuridicos() {
        return this.problemaJuridicos;
    }

    public InstrumentoInternacional problemaJuridicos(Set<ProblemaJuridico> problemaJuridicos) {
        this.setProblemaJuridicos(problemaJuridicos);
        return this;
    }

    public InstrumentoInternacional addProblemaJuridico(ProblemaJuridico problemaJuridico) {
        this.problemaJuridicos.add(problemaJuridico);
        problemaJuridico.getInstrumentoInternacionals().add(this);
        return this;
    }

    public InstrumentoInternacional removeProblemaJuridico(ProblemaJuridico problemaJuridico) {
        this.problemaJuridicos.remove(problemaJuridico);
        problemaJuridico.getInstrumentoInternacionals().remove(this);
        return this;
    }

    public void setProblemaJuridicos(Set<ProblemaJuridico> problemaJuridicos) {
        if (this.problemaJuridicos != null) {
            this.problemaJuridicos.forEach(i -> i.removeInstrumentoInternacional(this));
        }
        if (problemaJuridicos != null) {
            problemaJuridicos.forEach(i -> i.addInstrumentoInternacional(this));
        }
        this.problemaJuridicos = problemaJuridicos;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof InstrumentoInternacional)) {
            return false;
        }
        return id != null && id.equals(((InstrumentoInternacional) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InstrumentoInternacional{" +
            "id=" + getId() +
            ", instrumentoInternacionalCitadoDescricao='" + getInstrumentoInternacionalCitadoDescricao() + "'" +
            ", folhasInstrumentoInternacional='" + getFolhasInstrumentoInternacional() + "'" +
            ", instrumentoInternacionalSugerido='" + getInstrumentoInternacionalSugerido() + "'" +
            "}";
    }
}
