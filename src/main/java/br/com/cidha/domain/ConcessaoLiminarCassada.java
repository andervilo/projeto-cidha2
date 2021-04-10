package br.com.cidha.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ConcessaoLiminarCassada.
 */
@Entity
@Table(name = "concessao_liminar_cassada")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ConcessaoLiminarCassada implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "descricao")
    private String descricao;

    @ManyToOne
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
    private Processo processo;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ConcessaoLiminarCassada id(Long id) {
        this.id = id;
        return this;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public ConcessaoLiminarCassada descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Processo getProcesso() {
        return this.processo;
    }

    public ConcessaoLiminarCassada processo(Processo processo) {
        this.setProcesso(processo);
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
        if (!(o instanceof ConcessaoLiminarCassada)) {
            return false;
        }
        return id != null && id.equals(((ConcessaoLiminarCassada) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ConcessaoLiminarCassada{" +
            "id=" + getId() +
            ", descricao='" + getDescricao() + "'" +
            "}";
    }
}
