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
 * Criteria class for the {@link br.com.cidha.domain.ParteInteresssada} entity. This class is used
 * in {@link br.com.cidha.web.rest.ParteInteresssadaResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /parte-interesssadas?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ParteInteresssadaCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nome;

    private StringFilter classificacao;

    private LongFilter representanteLegalId;

    private LongFilter processoId;

    public ParteInteresssadaCriteria() {}

    public ParteInteresssadaCriteria(ParteInteresssadaCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.nome = other.nome == null ? null : other.nome.copy();
        this.classificacao = other.classificacao == null ? null : other.classificacao.copy();
        this.representanteLegalId = other.representanteLegalId == null ? null : other.representanteLegalId.copy();
        this.processoId = other.processoId == null ? null : other.processoId.copy();
    }

    @Override
    public ParteInteresssadaCriteria copy() {
        return new ParteInteresssadaCriteria(this);
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

    public StringFilter getNome() {
        return nome;
    }

    public StringFilter nome() {
        if (nome == null) {
            nome = new StringFilter();
        }
        return nome;
    }

    public void setNome(StringFilter nome) {
        this.nome = nome;
    }

    public StringFilter getClassificacao() {
        return classificacao;
    }

    public StringFilter classificacao() {
        if (classificacao == null) {
            classificacao = new StringFilter();
        }
        return classificacao;
    }

    public void setClassificacao(StringFilter classificacao) {
        this.classificacao = classificacao;
    }

    public LongFilter getRepresentanteLegalId() {
        return representanteLegalId;
    }

    public LongFilter representanteLegalId() {
        if (representanteLegalId == null) {
            representanteLegalId = new LongFilter();
        }
        return representanteLegalId;
    }

    public void setRepresentanteLegalId(LongFilter representanteLegalId) {
        this.representanteLegalId = representanteLegalId;
    }

    public LongFilter getProcessoId() {
        return processoId;
    }

    public LongFilter processoId() {
        if (processoId == null) {
            processoId = new LongFilter();
        }
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
        final ParteInteresssadaCriteria that = (ParteInteresssadaCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(nome, that.nome) &&
            Objects.equals(classificacao, that.classificacao) &&
            Objects.equals(representanteLegalId, that.representanteLegalId) &&
            Objects.equals(processoId, that.processoId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, classificacao, representanteLegalId, processoId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ParteInteresssadaCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (nome != null ? "nome=" + nome + ", " : "") +
            (classificacao != null ? "classificacao=" + classificacao + ", " : "") +
            (representanteLegalId != null ? "representanteLegalId=" + representanteLegalId + ", " : "") +
            (processoId != null ? "processoId=" + processoId + ", " : "") +
            "}";
    }
}
