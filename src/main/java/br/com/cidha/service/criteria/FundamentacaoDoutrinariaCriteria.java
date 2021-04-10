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

    public FundamentacaoDoutrinariaCriteria() {}

    public FundamentacaoDoutrinariaCriteria(FundamentacaoDoutrinariaCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.folhasFundamentacaoDoutrinaria =
            other.folhasFundamentacaoDoutrinaria == null ? null : other.folhasFundamentacaoDoutrinaria.copy();
        this.problemaJuridicoId = other.problemaJuridicoId == null ? null : other.problemaJuridicoId.copy();
    }

    @Override
    public FundamentacaoDoutrinariaCriteria copy() {
        return new FundamentacaoDoutrinariaCriteria(this);
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

    public StringFilter getFolhasFundamentacaoDoutrinaria() {
        return folhasFundamentacaoDoutrinaria;
    }

    public StringFilter folhasFundamentacaoDoutrinaria() {
        if (folhasFundamentacaoDoutrinaria == null) {
            folhasFundamentacaoDoutrinaria = new StringFilter();
        }
        return folhasFundamentacaoDoutrinaria;
    }

    public void setFolhasFundamentacaoDoutrinaria(StringFilter folhasFundamentacaoDoutrinaria) {
        this.folhasFundamentacaoDoutrinaria = folhasFundamentacaoDoutrinaria;
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
        final FundamentacaoDoutrinariaCriteria that = (FundamentacaoDoutrinariaCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(folhasFundamentacaoDoutrinaria, that.folhasFundamentacaoDoutrinaria) &&
            Objects.equals(problemaJuridicoId, that.problemaJuridicoId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, folhasFundamentacaoDoutrinaria, problemaJuridicoId);
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
