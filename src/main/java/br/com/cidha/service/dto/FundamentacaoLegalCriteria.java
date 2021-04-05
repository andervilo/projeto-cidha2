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
 * Criteria class for the {@link br.com.cidha.domain.FundamentacaoLegal} entity. This class is used
 * in {@link br.com.cidha.web.rest.FundamentacaoLegalResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /fundamentacao-legals?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class FundamentacaoLegalCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter folhasFundamentacaoLegal;

    private LongFilter problemaJuridicoId;

    public FundamentacaoLegalCriteria() {
    }

    public FundamentacaoLegalCriteria(FundamentacaoLegalCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.folhasFundamentacaoLegal = other.folhasFundamentacaoLegal == null ? null : other.folhasFundamentacaoLegal.copy();
        this.problemaJuridicoId = other.problemaJuridicoId == null ? null : other.problemaJuridicoId.copy();
    }

    @Override
    public FundamentacaoLegalCriteria copy() {
        return new FundamentacaoLegalCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getFolhasFundamentacaoLegal() {
        return folhasFundamentacaoLegal;
    }

    public void setFolhasFundamentacaoLegal(StringFilter folhasFundamentacaoLegal) {
        this.folhasFundamentacaoLegal = folhasFundamentacaoLegal;
    }

    public LongFilter getProblemaJuridicoId() {
        return problemaJuridicoId;
    }

    public void setProblemaJuridicoId(LongFilter problemaJuridicoId) {
        this.problemaJuridicoId = problemaJuridicoId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final FundamentacaoLegalCriteria that = (FundamentacaoLegalCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(folhasFundamentacaoLegal, that.folhasFundamentacaoLegal) &&
            Objects.equals(problemaJuridicoId, that.problemaJuridicoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        folhasFundamentacaoLegal,
        problemaJuridicoId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FundamentacaoLegalCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (folhasFundamentacaoLegal != null ? "folhasFundamentacaoLegal=" + folhasFundamentacaoLegal + ", " : "") +
                (problemaJuridicoId != null ? "problemaJuridicoId=" + problemaJuridicoId + ", " : "") +
            "}";
    }

}
