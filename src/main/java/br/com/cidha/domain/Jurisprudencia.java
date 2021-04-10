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
 * A Jurisprudencia.
 */
@Entity
@Table(name = "jurisprudencia")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Jurisprudencia implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "jurisprudencia_citada_descricao")
    private String jurisprudenciaCitadaDescricao;

    @Column(name = "folhas_jurisprudencia_citada")
    private String folhasJurisprudenciaCitada;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "jurisprudencia_sugerida")
    private String jurisprudenciaSugerida;

    @ManyToMany(mappedBy = "jurisprudencias")
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

    public Jurisprudencia id(Long id) {
        this.id = id;
        return this;
    }

    public String getJurisprudenciaCitadaDescricao() {
        return this.jurisprudenciaCitadaDescricao;
    }

    public Jurisprudencia jurisprudenciaCitadaDescricao(String jurisprudenciaCitadaDescricao) {
        this.jurisprudenciaCitadaDescricao = jurisprudenciaCitadaDescricao;
        return this;
    }

    public void setJurisprudenciaCitadaDescricao(String jurisprudenciaCitadaDescricao) {
        this.jurisprudenciaCitadaDescricao = jurisprudenciaCitadaDescricao;
    }

    public String getFolhasJurisprudenciaCitada() {
        return this.folhasJurisprudenciaCitada;
    }

    public Jurisprudencia folhasJurisprudenciaCitada(String folhasJurisprudenciaCitada) {
        this.folhasJurisprudenciaCitada = folhasJurisprudenciaCitada;
        return this;
    }

    public void setFolhasJurisprudenciaCitada(String folhasJurisprudenciaCitada) {
        this.folhasJurisprudenciaCitada = folhasJurisprudenciaCitada;
    }

    public String getJurisprudenciaSugerida() {
        return this.jurisprudenciaSugerida;
    }

    public Jurisprudencia jurisprudenciaSugerida(String jurisprudenciaSugerida) {
        this.jurisprudenciaSugerida = jurisprudenciaSugerida;
        return this;
    }

    public void setJurisprudenciaSugerida(String jurisprudenciaSugerida) {
        this.jurisprudenciaSugerida = jurisprudenciaSugerida;
    }

    public Set<ProblemaJuridico> getProblemaJuridicos() {
        return this.problemaJuridicos;
    }

    public Jurisprudencia problemaJuridicos(Set<ProblemaJuridico> problemaJuridicos) {
        this.setProblemaJuridicos(problemaJuridicos);
        return this;
    }

    public Jurisprudencia addProblemaJuridico(ProblemaJuridico problemaJuridico) {
        this.problemaJuridicos.add(problemaJuridico);
        problemaJuridico.getJurisprudencias().add(this);
        return this;
    }

    public Jurisprudencia removeProblemaJuridico(ProblemaJuridico problemaJuridico) {
        this.problemaJuridicos.remove(problemaJuridico);
        problemaJuridico.getJurisprudencias().remove(this);
        return this;
    }

    public void setProblemaJuridicos(Set<ProblemaJuridico> problemaJuridicos) {
        if (this.problemaJuridicos != null) {
            this.problemaJuridicos.forEach(i -> i.removeJurisprudencia(this));
        }
        if (problemaJuridicos != null) {
            problemaJuridicos.forEach(i -> i.addJurisprudencia(this));
        }
        this.problemaJuridicos = problemaJuridicos;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Jurisprudencia)) {
            return false;
        }
        return id != null && id.equals(((Jurisprudencia) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Jurisprudencia{" +
            "id=" + getId() +
            ", jurisprudenciaCitadaDescricao='" + getJurisprudenciaCitadaDescricao() + "'" +
            ", folhasJurisprudenciaCitada='" + getFolhasJurisprudenciaCitada() + "'" +
            ", jurisprudenciaSugerida='" + getJurisprudenciaSugerida() + "'" +
            "}";
    }
}
