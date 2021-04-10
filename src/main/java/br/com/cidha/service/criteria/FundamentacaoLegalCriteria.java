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

    public FundamentacaoLegalCriteria() {}

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

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getFolhasFundamentacaoLegal() {
        return folhasFundamentacaoLegal;
    }

    public StringFilter folhasFundamentacaoLegal() {
        if (folhasFundamentacaoLegal == null) {
            folhasFundamentacaoLegal = new StringFilter();
        }
        return folhasFundamentacaoLegal;
    }

    public void setFolhasFundamentacaoLegal(StringFilter folhasFundamentacaoLegal) {
        this.folhasFundamentacaoLegal = folhasFundamentacaoLegal;
    }

    public LongFilter getProblemaJuridicoId() {
        return problemaJuridicoId;
    }

    public LongFilter problemaJuridicoId() {
        if (problemaJuridicoId == null) {
            problemaJuridicoId = new LongFilter();
        }
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
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(folhasFundamentacaoLegal, that.folhasFundamentacaoLegal) &&
            Objects.equals(problemaJuridicoId, that.problemaJuridicoId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, folhasFundamentacaoLegal, problemaJuridicoId);
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
