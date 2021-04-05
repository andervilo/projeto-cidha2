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
 * Criteria class for the {@link br.com.cidha.domain.RepresentanteLegal} entity. This class is used
 * in {@link br.com.cidha.web.rest.RepresentanteLegalResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /representante-legals?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class RepresentanteLegalCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nome;

    private LongFilter tipoRepresentanteId;

    private LongFilter processoConflitoId;

    public RepresentanteLegalCriteria() {
    }

    public RepresentanteLegalCriteria(RepresentanteLegalCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.nome = other.nome == null ? null : other.nome.copy();
        this.tipoRepresentanteId = other.tipoRepresentanteId == null ? null : other.tipoRepresentanteId.copy();
        this.processoConflitoId = other.processoConflitoId == null ? null : other.processoConflitoId.copy();
    }

    @Override
    public RepresentanteLegalCriteria copy() {
        return new RepresentanteLegalCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getNome() {
        return nome;
    }

    public void setNome(StringFilter nome) {
        this.nome = nome;
    }

    public LongFilter getTipoRepresentanteId() {
        return tipoRepresentanteId;
    }

    public void setTipoRepresentanteId(LongFilter tipoRepresentanteId) {
        this.tipoRepresentanteId = tipoRepresentanteId;
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
        final RepresentanteLegalCriteria that = (RepresentanteLegalCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(nome, that.nome) &&
            Objects.equals(tipoRepresentanteId, that.tipoRepresentanteId) &&
            Objects.equals(processoConflitoId, that.processoConflitoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        nome,
        tipoRepresentanteId,
        processoConflitoId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RepresentanteLegalCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (nome != null ? "nome=" + nome + ", " : "") +
                (tipoRepresentanteId != null ? "tipoRepresentanteId=" + tipoRepresentanteId + ", " : "") +
                (processoConflitoId != null ? "processoConflitoId=" + processoConflitoId + ", " : "") +
            "}";
    }

}
