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
 * Criteria class for the {@link br.com.cidha.domain.Recurso} entity. This class is used
 * in {@link br.com.cidha.web.rest.RecursoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /recursos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class RecursoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter tipoRecursoId;

    private LongFilter opcaoRecursoId;

    private LongFilter processoId;

    public RecursoCriteria() {
    }

    public RecursoCriteria(RecursoCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.tipoRecursoId = other.tipoRecursoId == null ? null : other.tipoRecursoId.copy();
        this.opcaoRecursoId = other.opcaoRecursoId == null ? null : other.opcaoRecursoId.copy();
        this.processoId = other.processoId == null ? null : other.processoId.copy();
    }

    @Override
    public RecursoCriteria copy() {
        return new RecursoCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LongFilter getTipoRecursoId() {
        return tipoRecursoId;
    }

    public void setTipoRecursoId(LongFilter tipoRecursoId) {
        this.tipoRecursoId = tipoRecursoId;
    }

    public LongFilter getOpcaoRecursoId() {
        return opcaoRecursoId;
    }

    public void setOpcaoRecursoId(LongFilter opcaoRecursoId) {
        this.opcaoRecursoId = opcaoRecursoId;
    }

    public LongFilter getProcessoId() {
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
        final RecursoCriteria that = (RecursoCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(tipoRecursoId, that.tipoRecursoId) &&
            Objects.equals(opcaoRecursoId, that.opcaoRecursoId) &&
            Objects.equals(processoId, that.processoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        tipoRecursoId,
        opcaoRecursoId,
        processoId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RecursoCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (tipoRecursoId != null ? "tipoRecursoId=" + tipoRecursoId + ", " : "") +
                (opcaoRecursoId != null ? "opcaoRecursoId=" + opcaoRecursoId + ", " : "") +
                (processoId != null ? "processoId=" + processoId + ", " : "") +
            "}";
    }

}
