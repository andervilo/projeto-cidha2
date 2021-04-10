package br.com.cidha.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Data.
 */
@Entity
@Table(name = "data")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Data implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "data")
    private LocalDate data;

    @OneToOne
    @JoinColumn(unique = true)
    private TipoData tipoData;

    @ManyToOne
    @JsonIgnoreProperties(value = "data", allowSetters = true)
    private Processo processo;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getData() {
        return data;
    }

    public Data data(LocalDate data) {
        this.data = data;
        return this;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public TipoData getTipoData() {
        return tipoData;
    }

    public Data tipoData(TipoData tipoData) {
        this.tipoData = tipoData;
        return this;
    }

    public void setTipoData(TipoData tipoData) {
        this.tipoData = tipoData;
    }

    public Processo getProcesso() {
        return processo;
    }

    public Data processo(Processo processo) {
        this.processo = processo;
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
        if (!(o instanceof Data)) {
            return false;
        }
        return id != null && id.equals(((Data) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Data{" +
            "id=" + getId() +
            ", data='" + getData() + "'" +
            "}";
    }
}
