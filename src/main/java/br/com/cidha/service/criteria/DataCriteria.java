package br.com.cidha.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LocalDateFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link br.com.cidha.domain.Data} entity. This class is used
 * in {@link br.com.cidha.web.rest.DataResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /data?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class DataCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LocalDateFilter data;

    private LongFilter tipoDataId;

    private LongFilter processoId;

    public DataCriteria() {}

    public DataCriteria(DataCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.data = other.data == null ? null : other.data.copy();
        this.tipoDataId = other.tipoDataId == null ? null : other.tipoDataId.copy();
        this.processoId = other.processoId == null ? null : other.processoId.copy();
    }

    @Override
    public DataCriteria copy() {
        return new DataCriteria(this);
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

    public LocalDateFilter getData() {
        return data;
    }

    public LocalDateFilter data() {
        if (data == null) {
            data = new LocalDateFilter();
        }
        return data;
    }

    public void setData(LocalDateFilter data) {
        this.data = data;
    }

    public LongFilter getTipoDataId() {
        return tipoDataId;
    }

    public LongFilter tipoDataId() {
        if (tipoDataId == null) {
            tipoDataId = new LongFilter();
        }
        return tipoDataId;
    }

    public void setTipoDataId(LongFilter tipoDataId) {
        this.tipoDataId = tipoDataId;
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
        final DataCriteria that = (DataCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(data, that.data) &&
            Objects.equals(tipoDataId, that.tipoDataId) &&
            Objects.equals(processoId, that.processoId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, data, tipoDataId, processoId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DataCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (data != null ? "data=" + data + ", " : "") +
            (tipoDataId != null ? "tipoDataId=" + tipoDataId + ", " : "") +
            (processoId != null ? "processoId=" + processoId + ", " : "") +
            "}";
    }
}
