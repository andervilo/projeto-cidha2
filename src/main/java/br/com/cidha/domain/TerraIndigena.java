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
    @JoinTable(name = "terra_indigena_etnia",
               joinColumns = @JoinColumn(name = "terra_indigena_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "etnia_id", referencedColumnName = "id"))
    private Set<EtniaIndigena> etnias = new HashSet<>();

    @ManyToMany(mappedBy = "terraIndigenas")
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

    public String getDescricao() {
        return descricao;
    }

    public TerraIndigena descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Set<EtniaIndigena> getEtnias() {
        return etnias;
    }

    public TerraIndigena etnias(Set<EtniaIndigena> etniaIndigenas) {
        this.etnias = etniaIndigenas;
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
        return processos;
    }

    public TerraIndigena processos(Set<Processo> processos) {
        this.processos = processos;
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
        return 31;
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
