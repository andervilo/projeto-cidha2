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
 * Criteria class for the {@link br.com.cidha.domain.FundamentacaoDoutrinaria} entity. This class is used
 * in {@link br.com.cidha.web.rest.FundamentacaoDoutrinariaResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /fundamentacao-doutrinarias?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class FundamentacaoDoutrinariaCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter folhasFundamentacaoDoutrinaria;

    private LongFilter problemaJuridicoId;

    public FundamentacaoDoutrinariaCriteria() {
    }

    public FundamentacaoDoutrinariaCriteria(FundamentacaoDoutrinariaCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.folhasFundamentacaoDoutrinaria = other.folhasFundamentacaoDoutrinaria == null ? null : other.folhasFundamentacaoDoutrinaria.copy();
        this.problemaJuridicoId = other.problemaJuridicoId == null ? null : other.problemaJuridicoId.copy();
    }

    @Override
    public FundamentacaoDoutrinariaCriteria copy() {
        return new FundamentacaoDoutrinariaCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getFolhasFundamentacaoDoutrinaria() {
        return folhasFundamentacaoDoutrinaria;
    }

    public void setFolhasFundamentacaoDoutrinaria(StringFilter folhasFundamentacaoDoutrinaria) {
        this.folhasFundamentacaoDoutrinaria = folhasFundamentacaoDoutrinaria;
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
        final FundamentacaoDoutrinariaCriteria that = (FundamentacaoDoutrinariaCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(folhasFundamentacaoDoutrinaria, that.folhasFundamentacaoDoutrinaria) &&
            Objects.equals(problemaJuridicoId, that.problemaJuridicoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        folhasFundamentacaoDoutrinaria,
        problemaJuridicoId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FundamentacaoDoutrinariaCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (folhasFundamentacaoDoutrinaria != null ? "folhasFundamentacaoDoutrinaria=" + folhasFundamentacaoDoutrinaria + ", " : "") +
                (problemaJuridicoId != null ? "problemaJuridicoId=" + problemaJuridicoId + ", " : "") +
            "}";
    }

}
