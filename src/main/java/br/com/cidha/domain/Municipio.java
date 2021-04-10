package br.com.cidha.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Municipio.
 */
@Entity
@Table(name = "municipio")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Municipio implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "amazonia_legal")
    private Boolean amazoniaLegal;

    @Column(name = "codigo_ibge")
    private Integer codigoIbge;

    @Column(name = "estado")
    private String estado;

    @Column(name = "nome")
    private String nome;

    @ManyToMany(mappedBy = "municipios")
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

    public Boolean isAmazoniaLegal() {
        return amazoniaLegal;
    }

    public Municipio amazoniaLegal(Boolean amazoniaLegal) {
        this.amazoniaLegal = amazoniaLegal;
        return this;
    }

    public void setAmazoniaLegal(Boolean amazoniaLegal) {
        this.amazoniaLegal = amazoniaLegal;
    }

    public Integer getCodigoIbge() {
        return codigoIbge;
    }

    public Municipio codigoIbge(Integer codigoIbge) {
        this.codigoIbge = codigoIbge;
        return this;
    }

    public void setCodigoIbge(Integer codigoIbge) {
        this.codigoIbge = codigoIbge;
    }

    public String getEstado() {
        return estado;
    }

    public Municipio estado(String estado) {
        this.estado = estado;
        return this;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getNome() {
        return nome;
    }

    public Municipio nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Set<Processo> getProcessos() {
        return processos;
    }

    public Municipio processos(Set<Processo> processos) {
        this.processos = processos;
        return this;
    }

    public Municipio addProcesso(Processo processo) {
        this.processos.add(processo);
        processo.getMunicipios().add(this);
        return this;
    }

    public Municipio removeProcesso(Processo processo) {
        this.processos.remove(processo);
        processo.getMunicipios().remove(this);
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
        if (!(o instanceof Municipio)) {
            return false;
        }
        return id != null && id.equals(((Municipio) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Municipio{" +
            "id=" + getId() +
            ", amazoniaLegal='" + isAmazoniaLegal() + "'" +
            ", codigoIbge=" + getCodigoIbge() +
            ", estado='" + getEstado() + "'" +
            ", nome='" + getNome() + "'" +
            "}";
    }
}
