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
 * Criteria class for the {@link br.com.cidha.domain.TerraIndigena} entity. This class is used
 * in {@link br.com.cidha.web.rest.TerraIndigenaResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /terra-indigenas?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class TerraIndigenaCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter etniaId;

    private LongFilter processoId;

    public TerraIndigenaCriteria() {}

    public TerraIndigenaCriteria(TerraIndigenaCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.etniaId = other.etniaId == null ? null : other.etniaId.copy();
        this.processoId = other.processoId == null ? null : other.processoId.copy();
    }

    @Override
    public TerraIndigenaCriteria copy() {
        return new TerraIndigenaCriteria(this);
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

    public LongFilter getEtniaId() {
        return etniaId;
    }

    public LongFilter etniaId() {
        if (etniaId == null) {
            etniaId = new LongFilter();
        }
        return etniaId;
    }

    public void setEtniaId(LongFilter etniaId) {
        this.etniaId = etniaId;
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
        final TerraIndigenaCriteria that = (TerraIndigenaCriteria) o;
        return Objects.equals(id, that.id) && Objects.equals(etniaId, that.etniaId) && Objects.equals(processoId, that.processoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, etniaId, processoId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TerraIndigenaCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (etniaId != null ? "etniaId=" + etniaId + ", " : "") +
            (processoId != null ? "processoId=" + processoId + ", " : "") +
            "}";
    }
}
