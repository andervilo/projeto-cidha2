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
    @JsonIgnoreProperties(
        value = {
            "concessaoLiminars",
            "concessaoLiminarCassadas",
            "embargoRespRes",
            "embargoDeclaracaoAgravos",
            "embargoDeclaracaos",
            "embargoRecursoEspecials",
            "tipoDecisao",
            "tipoEmpreendimento",
            "comarcas",
            "quilombos",
            "municipios",
            "territorios",
            "atividadeExploracaoIlegals",
            "unidadeConservacaos",
            "envolvidosConflitoLitigios",
            "terraIndigenas",
            "processoConflitos",
            "parteInteresssadas",
            "relators",
            "problemaJuridicos",
        },
        allowSetters = true
    )
    private Set<Processo> processos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EnvolvidosConflitoLitigio id(Long id) {
        this.id = id;
        return this;
    }

    public Integer getNumeroIndividuos() {
        return this.numeroIndividuos;
    }

    public EnvolvidosConflitoLitigio numeroIndividuos(Integer numeroIndividuos) {
        this.numeroIndividuos = numeroIndividuos;
        return this;
    }

    public void setNumeroIndividuos(Integer numeroIndividuos) {
        this.numeroIndividuos = numeroIndividuos;
    }

    public String getFonteInformacaoQtde() {
        return this.fonteInformacaoQtde;
    }

    public EnvolvidosConflitoLitigio fonteInformacaoQtde(String fonteInformacaoQtde) {
        this.fonteInformacaoQtde = fonteInformacaoQtde;
        return this;
    }

    public void setFonteInformacaoQtde(String fonteInformacaoQtde) {
        this.fonteInformacaoQtde = fonteInformacaoQtde;
    }

    public String getObservacoes() {
        return this.observacoes;
    }

    public EnvolvidosConflitoLitigio observacoes(String observacoes) {
        this.observacoes = observacoes;
        return this;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public Set<Processo> getProcessos() {
        return this.processos;
    }

    public EnvolvidosConflitoLitigio processos(Set<Processo> processos) {
        this.setProcessos(processos);
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
        if (this.processos != null) {
            this.processos.forEach(i -> i.removeEnvolvidosConflitoLitigio(this));
        }
        if (processos != null) {
            processos.forEach(i -> i.addEnvolvidosConflitoLitigio(this));
        }
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
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
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
