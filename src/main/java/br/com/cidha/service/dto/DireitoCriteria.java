package br.com.cidha.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

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

    public DireitoCriteria() {
    }

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

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LongFilter getProcessoConflitoId() {
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
        return
            Objects.equals(id, that.id) &&
            Objects.equals(processoConflitoId, that.processoConflitoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        processoConflitoId
        );
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
