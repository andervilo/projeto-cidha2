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
import io.github.jhipster.service.filter.BigDecimalFilter;

/**
 * Criteria class for the {@link br.com.cidha.domain.Comarca} entity. This class is used
 * in {@link br.com.cidha.web.rest.ComarcaResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /comarcas?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ComarcaCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nome;

    private BigDecimalFilter codigoCnj;

    private LongFilter processoId;

    public ComarcaCriteria() {
    }

    public ComarcaCriteria(ComarcaCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.nome = other.nome == null ? null : other.nome.copy();
        this.codigoCnj = other.codigoCnj == null ? null : other.codigoCnj.copy();
        this.processoId = other.processoId == null ? null : other.processoId.copy();
    }

    @Override
    public ComarcaCriteria copy() {
        return new ComarcaCriteria(this);
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

    public BigDecimalFilter getCodigoCnj() {
        return codigoCnj;
    }

    public void setCodigoCnj(BigDecimalFilter codigoCnj) {
        this.codigoCnj = codigoCnj;
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
        final ComarcaCriteria that = (ComarcaCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(nome, that.nome) &&
            Objects.equals(codigoCnj, that.codigoCnj) &&
            Objects.equals(processoId, that.processoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        nome,
        codigoCnj,
        processoId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ComarcaCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (nome != null ? "nome=" + nome + ", " : "") +
                (codigoCnj != null ? "codigoCnj=" + codigoCnj + ", " : "") +
                (processoId != null ? "processoId=" + processoId + ", " : "") +
            "}";
    }

}
