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
 * A TerraIndigena.
 */
@Entity
@Table(name = "terra_indigena")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TerraIndigena implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "descricao")
    private String descricao;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(
        name = "rel_terra_indigena__etnia",
        joinColumns = @JoinColumn(name = "terra_indigena_id"),
        inverseJoinColumns = @JoinColumn(name = "etnia_id")
    )
    @JsonIgnoreProperties(value = { "terraIndigenas" }, allowSetters = true)
    private Set<EtniaIndigena> etnias = new HashSet<>();

    @ManyToMany(mappedBy = "terraIndigenas")
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

    public TerraIndigena id(Long id) {
        this.id = id;
        return this;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public TerraIndigena descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Set<EtniaIndigena> getEtnias() {
        return this.etnias;
    }

    public TerraIndigena etnias(Set<EtniaIndigena> etniaIndigenas) {
        this.setEtnias(etniaIndigenas);
        return this;
    }

    public TerraIndigena addEtnia(EtniaIndigena etniaIndigena) {
        this.etnias.add(etniaIndigena);
        etniaIndigena.getTerraIndigenas().add(this);
        return this;
    }

    public TerraIndigena removeEtnia(EtniaIndigena etniaIndigena) {
        this.etnias.remove(etniaIndigena);
        etniaIndigena.getTerraIndigenas().remove(this);
        return this;
    }

    public void setEtnias(Set<EtniaIndigena> etniaIndigenas) {
        this.etnias = etniaIndigenas;
    }

    public Set<Processo> getProcessos() {
        return this.processos;
    }

    public TerraIndigena processos(Set<Processo> processos) {
        this.setProcessos(processos);
        return this;
    }

    public TerraIndigena addProcesso(Processo processo) {
        this.processos.add(processo);
        processo.getTerraIndigenas().add(this);
        return this;
    }

    public TerraIndigena removeProcesso(Processo processo) {
        this.processos.remove(processo);
        processo.getTerraIndigenas().remove(this);
        return this;
    }

    public void setProcessos(Set<Processo> processos) {
        if (this.processos != null) {
            this.processos.forEach(i -> i.removeTerraIndigena(this));
        }
        if (processos != null) {
            processos.forEach(i -> i.addTerraIndigena(this));
        }
        this.processos = processos;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TerraIndigena)) {
            return false;
        }
        return id != null && id.equals(((TerraIndigena) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TerraIndigena{" +
            "id=" + getId() +
            ", descricao='" + getDescricao() + "'" +
            "}";
    }
}
