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
 * Criteria class for the {@link br.com.cidha.domain.Jurisprudencia} entity. This class is used
 * in {@link br.com.cidha.web.rest.JurisprudenciaResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /jurisprudencias?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class JurisprudenciaCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter folhasJurisprudenciaCitada;

    private LongFilter problemaJuridicoId;

    public JurisprudenciaCriteria() {}

    public JurisprudenciaCriteria(JurisprudenciaCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.folhasJurisprudenciaCitada = other.folhasJurisprudenciaCitada == null ? null : other.folhasJurisprudenciaCitada.copy();
        this.problemaJuridicoId = other.problemaJuridicoId == null ? null : other.problemaJuridicoId.copy();
    }

    @Override
    public JurisprudenciaCriteria copy() {
        return new JurisprudenciaCriteria(this);
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

    public StringFilter getFolhasJurisprudenciaCitada() {
        return folhasJurisprudenciaCitada;
    }

    public StringFilter folhasJurisprudenciaCitada() {
        if (folhasJurisprudenciaCitada == null) {
            folhasJurisprudenciaCitada = new StringFilter();
        }
        return folhasJurisprudenciaCitada;
    }

    public void setFolhasJurisprudenciaCitada(StringFilter folhasJurisprudenciaCitada) {
        this.folhasJurisprudenciaCitada = folhasJurisprudenciaCitada;
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
        final JurisprudenciaCriteria that = (JurisprudenciaCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(folhasJurisprudenciaCitada, that.folhasJurisprudenciaCitada) &&
            Objects.equals(problemaJuridicoId, that.problemaJuridicoId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, folhasJurisprudenciaCitada, problemaJuridicoId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "JurisprudenciaCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (folhasJurisprudenciaCitada != null ? "folhasJurisprudenciaCitada=" + folhasJurisprudenciaCitada + ", " : "") +
            (problemaJuridicoId != null ? "problemaJuridicoId=" + problemaJuridicoId + ", " : "") +
            "}";
    }
}
