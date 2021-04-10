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
 * Criteria class for the {@link br.com.cidha.domain.InstrumentoInternacional} entity. This class is used
 * in {@link br.com.cidha.web.rest.InstrumentoInternacionalResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /instrumento-internacionals?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class InstrumentoInternacionalCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter folhasInstrumentoInternacional;

    private LongFilter problemaJuridicoId;

    public InstrumentoInternacionalCriteria() {
    }

    public InstrumentoInternacionalCriteria(InstrumentoInternacionalCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.folhasInstrumentoInternacional = other.folhasInstrumentoInternacional == null ? null : other.folhasInstrumentoInternacional.copy();
        this.problemaJuridicoId = other.problemaJuridicoId == null ? null : other.problemaJuridicoId.copy();
    }

    @Override
    public InstrumentoInternacionalCriteria copy() {
        return new InstrumentoInternacionalCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getFolhasInstrumentoInternacional() {
        return folhasInstrumentoInternacional;
    }

    public void setFolhasInstrumentoInternacional(StringFilter folhasInstrumentoInternacional) {
        this.folhasInstrumentoInternacional = folhasInstrumentoInternacional;
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
        final InstrumentoInternacionalCriteria that = (InstrumentoInternacionalCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(folhasInstrumentoInternacional, that.folhasInstrumentoInternacional) &&
            Objects.equals(problemaJuridicoId, that.problemaJuridicoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        folhasInstrumentoInternacional,
        problemaJuridicoId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InstrumentoInternacionalCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (folhasInstrumentoInternacional != null ? "folhasInstrumentoInternacional=" + folhasInstrumentoInternacional + ", " : "") +
                (problemaJuridicoId != null ? "problemaJuridicoId=" + problemaJuridicoId + ", " : "") +
            "}";
    }

}
