package br.com.cidha.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link br.com.cidha.domain.Municipio} entity. This class is used
 * in {@link br.com.cidha.web.rest.MunicipioResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /municipios?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class MunicipioCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private BooleanFilter amazoniaLegal;

    private IntegerFilter codigoIbge;

    private StringFilter estado;

    private StringFilter nome;

    private LongFilter processoId;

    public MunicipioCriteria() {}

    public MunicipioCriteria(MunicipioCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.amazoniaLegal = other.amazoniaLegal == null ? null : other.amazoniaLegal.copy();
        this.codigoIbge = other.codigoIbge == null ? null : other.codigoIbge.copy();
        this.estado = other.estado == null ? null : other.estado.copy();
        this.nome = other.nome == null ? null : other.nome.copy();
        this.processoId = other.processoId == null ? null : other.processoId.copy();
    }

    @Override
    public MunicipioCriteria copy() {
        return new MunicipioCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public BooleanFilter getAmazoniaLegal() {
        return amazoniaLegal;
    }

    public BooleanFilter amazoniaLegal() {
        if (amazoniaLegal == null) {
            amazoniaLegal = new BooleanFilter();
        }
        return amazoniaLegal;
    }

    public void setAmazoniaLegal(BooleanFilter amazoniaLegal) {
        this.amazoniaLegal = amazoniaLegal;
    }

    public IntegerFilter getCodigoIbge() {
        return codigoIbge;
    }

    public IntegerFilter codigoIbge() {
        if (codigoIbge == null) {
            codigoIbge = new IntegerFilter();
        }
        return codigoIbge;
    }

    public void setCodigoIbge(IntegerFilter codigoIbge) {
        this.codigoIbge = codigoIbge;
    }

    public StringFilter getEstado() {
        return estado;
    }

    public StringFilter estado() {
        if (estado == null) {
            estado = new StringFilter();
        }
        return estado;
    }

    public void setEstado(StringFilter estado) {
        this.estado = estado;
    }

    public StringFilter getNome() {
        return nome;
    }

    public StringFilter nome() {
        if (nome == null) {
            nome = new StringFilter();
        }
        return nome;
    }

    public void setNome(StringFilter nome) {
        this.nome = nome;
    }

    public LongFilter getProcessoId() {
        return processoId;
    }

    public LongFilter processoId() {
        if (processoId == null) {
            processoId = new LongFilter();
        }
        return processoId;
    }

    public void setProcessoId(LongFilter processoId) {
        this.processoId = processoId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final MunicipioCriteria that = (MunicipioCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(amazoniaLegal, that.amazoniaLegal) &&
            Objects.equals(codigoIbge, that.codigoIbge) &&
            Objects.equals(estado, that.estado) &&
            Objects.equals(nome, that.nome) &&
            Objects.equals(processoId, that.processoId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, amazoniaLegal, codigoIbge, estado, nome, processoId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MunicipioCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (amazoniaLegal != null ? "amazoniaLegal=" + amazoniaLegal + ", " : "") +
            (codigoIbge != null ? "codigoIbge=" + codigoIbge + ", " : "") +
            (estado != null ? "estado=" + estado + ", " : "") +
            (nome != null ? "nome=" + nome + ", " : "") +
            (processoId != null ? "processoId=" + processoId + ", " : "") +
            "}";
    }
}
