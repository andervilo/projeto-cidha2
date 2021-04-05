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
 * A EnvolvidosConflitoLitigio.
 */
@Entity
@Table(name = "envolvidos_conflito_litigio")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class EnvolvidosConflitoLitigio implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "numero_individuos")
    private Integer numeroIndividuos;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "fonte_informacao_qtde")
    private String fonteInformacaoQtde;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "observacoes")
    private String observacoes;

    @ManyToMany(mappedBy = "envolvidosConflitoLitigios")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnore
    private Set<Processo> processos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumeroIndividuos() {
        return numeroIndividuos;
    }

    public EnvolvidosConflitoLitigio numeroIndividuos(Integer numeroIndividuos) {
        this.numeroIndividuos = numeroIndividuos;
        return this;
    }

    public void setNumeroIndividuos(Integer numeroIndividuos) {
        this.numeroIndividuos = numeroIndividuos;
    }

    public String getFonteInformacaoQtde() {
        return fonteInformacaoQtde;
    }

    public EnvolvidosConflitoLitigio fonteInformacaoQtde(String fonteInformacaoQtde) {
        this.fonteInformacaoQtde = fonteInformacaoQtde;
        return this;
    }

    public void setFonteInformacaoQtde(String fonteInformacaoQtde) {
        this.fonteInformacaoQtde = fonteInformacaoQtde;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public EnvolvidosConflitoLitigio observacoes(String observacoes) {
        this.observacoes = observacoes;
        return this;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public Set<Processo> getProcessos() {
        return processos;
    }

    public EnvolvidosConflitoLitigio processos(Set<Processo> processos) {
        this.processos = processos;
        return this;
    }

    public EnvolvidosConflitoLitigio addProcesso(Processo processo) {
        this.processos.add(processo);
        processo.getEnvolvidosConflitoLitigios().add(this);
        return this;
    }

    public EnvolvidosConflitoLitigio removeProcesso(Processo processo) {
        this.processos.remove(processo);
        processo.getEnvolvidosConflitoLitigios().remove(this);
        return this;
    }

    public void setProcessos(Set<Processo> processos) {
        this.processos = processos;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EnvolvidosConflitoLitigio)) {
            return false;
        }
        return id != null && id.equals(((EnvolvidosConflitoLitigio) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EnvolvidosConflitoLitigio{" +
            "id=" + getId() +
            ", numeroIndividuos=" + getNumeroIndividuos() +
            ", fonteInformacaoQtde='" + getFonteInformacaoQtde() + "'" +
            ", observacoes='" + getObservacoes() + "'" +
            "}";
    }
}
