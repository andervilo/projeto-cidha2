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
 * Criteria class for the {@link br.com.cidha.domain.Direito} entity. This class is used
 * in {@link br.com.cidha.web.rest.DireitoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /direitos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class DireitoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter processoConflitoId;

    public DireitoCriteria() {}

    public DireitoCriteria(DireitoCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.processoConflitoId = other.processoConflitoId == null ? null : other.processoConflitoId.copy();
    }

    @Override
    public DireitoCriteria copy() {
        return new DireitoCriteria(this);
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

    public LongFilter getProcessoConflitoId() {
        return processoConflitoId;
    }

    public LongFilter processoConflitoId() {
        if (processoConflitoId == null) {
            processoConflitoId = new LongFilter();
        }
        return processoConflitoId;
    }

    public void setProcessoConflitoId(LongFilter processoConflitoId) {
        this.processoConflitoId = processoConflitoId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final DireitoCriteria that = (DireitoCriteria) o;
        return Objects.equals(id, that.id) && Objects.equals(processoConflitoId, that.processoConflitoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, processoConflitoId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DireitoCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (processoConflitoId != null ? "processoConflitoId=" + processoConflitoId + ", " : "") +
            "}";
    }
}
