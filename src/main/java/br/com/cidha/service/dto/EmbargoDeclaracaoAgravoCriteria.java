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
 * Criteria class for the {@link br.com.cidha.domain.EmbargoDeclaracaoAgravo} entity. This class is used
 * in {@link br.com.cidha.web.rest.EmbargoDeclaracaoAgravoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /embargo-declaracao-agravos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class EmbargoDeclaracaoAgravoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter descricao;

    private LongFilter processoId;

    public EmbargoDeclaracaoAgravoCriteria() {
    }

    public EmbargoDeclaracaoAgravoCriteria(EmbargoDeclaracaoAgravoCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.descricao = other.descricao == null ? null : other.descricao.copy();
        this.processoId = other.processoId == null ? null : other.processoId.copy();
    }

    @Override
    public EmbargoDeclaracaoAgravoCriteria copy() {
        return new EmbargoDeclaracaoAgravoCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getDescricao() {
        return descricao;
    }

    public void setDescricao(StringFilter descricao) {
        this.descricao = descricao;
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
        final EmbargoDeclaracaoAgravoCriteria that = (EmbargoDeclaracaoAgravoCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(descricao, that.descricao) &&
            Objects.equals(processoId, that.processoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        descricao,
        processoId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmbargoDeclaracaoAgravoCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (descricao != null ? "descricao=" + descricao + ", " : "") +
                (processoId != null ? "processoId=" + processoId + ", " : "") +
            "}";
    }

}
