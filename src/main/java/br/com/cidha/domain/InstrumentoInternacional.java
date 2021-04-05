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
 * A InstrumentoInternacional.
 */
@Entity
@Table(name = "instrumento_internacional")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class InstrumentoInternacional implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
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
    @JsonIgnore
    private Set<ProblemaJuridico> problemaJuridicos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInstrumentoInternacionalCitadoDescricao() {
        return instrumentoInternacionalCitadoDescricao;
    }

    public InstrumentoInternacional instrumentoInternacionalCitadoDescricao(String instrumentoInternacionalCitadoDescricao) {
        this.instrumentoInternacionalCitadoDescricao = instrumentoInternacionalCitadoDescricao;
        return this;
    }

    public void setInstrumentoInternacionalCitadoDescricao(String instrumentoInternacionalCitadoDescricao) {
        this.instrumentoInternacionalCitadoDescricao = instrumentoInternacionalCitadoDescricao;
    }

    public String getFolhasInstrumentoInternacional() {
        return folhasInstrumentoInternacional;
    }

    public InstrumentoInternacional folhasInstrumentoInternacional(String folhasInstrumentoInternacional) {
        this.folhasInstrumentoInternacional = folhasInstrumentoInternacional;
        return this;
    }

    public void setFolhasInstrumentoInternacional(String folhasInstrumentoInternacional) {
        this.folhasInstrumentoInternacional = folhasInstrumentoInternacional;
    }

    public String getInstrumentoInternacionalSugerido() {
        return instrumentoInternacionalSugerido;
    }

    public InstrumentoInternacional instrumentoInternacionalSugerido(String instrumentoInternacionalSugerido) {
        this.instrumentoInternacionalSugerido = instrumentoInternacionalSugerido;
        return this;
    }

    public void setInstrumentoInternacionalSugerido(String instrumentoInternacionalSugerido) {
        this.instrumentoInternacionalSugerido = instrumentoInternacionalSugerido;
    }

    public Set<ProblemaJuridico> getProblemaJuridicos() {
        return problemaJuridicos;
    }

    public InstrumentoInternacional problemaJuridicos(Set<ProblemaJuridico> problemaJuridicos) {
        this.problemaJuridicos = problemaJuridicos;
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
        return 31;
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
