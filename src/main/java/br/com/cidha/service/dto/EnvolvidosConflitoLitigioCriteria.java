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
 * Criteria class for the {@link br.com.cidha.domain.EnvolvidosConflitoLitigio} entity. This class is used
 * in {@link br.com.cidha.web.rest.EnvolvidosConflitoLitigioResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /envolvidos-conflito-litigios?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class EnvolvidosConflitoLitigioCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter numeroIndividuos;

    private LongFilter processoId;

    public EnvolvidosConflitoLitigioCriteria() {
    }

    public EnvolvidosConflitoLitigioCriteria(EnvolvidosConflitoLitigioCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.numeroIndividuos = other.numeroIndividuos == null ? null : other.numeroIndividuos.copy();
        this.processoId = other.processoId == null ? null : other.processoId.copy();
    }

    @Override
    public EnvolvidosConflitoLitigioCriteria copy() {
        return new EnvolvidosConflitoLitigioCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public IntegerFilter getNumeroIndividuos() {
        return numeroIndividuos;
    }

    public void setNumeroIndividuos(IntegerFilter numeroIndividuos) {
        this.numeroIndividuos = numeroIndividuos;
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
        final EnvolvidosConflitoLitigioCriteria that = (EnvolvidosConflitoLitigioCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(numeroIndividuos, that.numeroIndividuos) &&
            Objects.equals(processoId, that.processoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        numeroIndividuos,
        processoId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EnvolvidosConflitoLitigioCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (numeroIndividuos != null ? "numeroIndividuos=" + numeroIndividuos + ", " : "") +
                (processoId != null ? "processoId=" + processoId + ", " : "") +
            "}";
    }

}
