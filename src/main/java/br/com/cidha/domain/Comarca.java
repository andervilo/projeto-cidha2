package br.com.cidha.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Comarca.
 */
@Entity
@Table(name = "comarca")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Comarca implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "codigo_cnj", precision = 21, scale = 2)
    private BigDecimal codigoCnj;

    @ManyToMany(mappedBy = "comarcas")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = {
            "concessaoLiminars",
            "concessaoLiminarCassadas",
            "embargoDeclaracaos",
            "embargoDeclaracaoAgravos",
            "embargoRecursoEspecials",
            "embargoRespRes",
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

    public Comarca id(Long id) {
        this.id = id;
        return this;
    }

    public String getNome() {
        return this.nome;
    }

    public Comarca nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public BigDecimal getCodigoCnj() {
        return this.codigoCnj;
    }

    public Comarca codigoCnj(BigDecimal codigoCnj) {
        this.codigoCnj = codigoCnj;
        return this;
    }

    public void setCodigoCnj(BigDecimal codigoCnj) {
        this.codigoCnj = codigoCnj;
    }

    public Set<Processo> getProcessos() {
        return this.processos;
    }

    public Comarca processos(Set<Processo> processos) {
        this.setProcessos(processos);
        return this;
    }

    public Comarca addProcesso(Processo processo) {
        this.processos.add(processo);
        processo.getComarcas().add(this);
        return this;
    }

    public Comarca removeProcesso(Processo processo) {
        this.processos.remove(processo);
        processo.getComarcas().remove(this);
        return this;
    }

    public void setProcessos(Set<Processo> processos) {
        if (this.processos != null) {
            this.processos.forEach(i -> i.removeComarca(this));
        }
        if (processos != null) {
            processos.forEach(i -> i.addComarca(this));
        }
        this.processos = processos;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Comarca)) {
            return false;
        }
        return id != null && id.equals(((Comarca) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Comarca{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", codigoCnj=" + getCodigoCnj() +
            "}";
    }
}
