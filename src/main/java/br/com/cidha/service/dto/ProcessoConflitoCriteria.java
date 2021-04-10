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
 * Criteria class for the {@link br.com.cidha.domain.ProcessoConflito} entity. This class is used
 * in {@link br.com.cidha.web.rest.ProcessoConflitoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /processo-conflitos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ProcessoConflitoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nomeCasoComuidade;

    private BooleanFilter consultaPrevia;

    private LongFilter conflitoId;

    private LongFilter direitoId;

    private LongFilter processoId;

    public ProcessoConflitoCriteria() {
    }

    public ProcessoConflitoCriteria(ProcessoConflitoCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.nomeCasoComuidade = other.nomeCasoComuidade == null ? null : other.nomeCasoComuidade.copy();
        this.consultaPrevia = other.consultaPrevia == null ? null : other.consultaPrevia.copy();
        this.conflitoId = other.conflitoId == null ? null : other.conflitoId.copy();
        this.direitoId = other.direitoId == null ? null : other.direitoId.copy();
        this.processoId = other.processoId == null ? null : other.processoId.copy();
    }

    @Override
    public ProcessoConflitoCriteria copy() {
        return new ProcessoConflitoCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getNomeCasoComuidade() {
        return nomeCasoComuidade;
    }

    public void setNomeCasoComuidade(StringFilter nomeCasoComuidade) {
        this.nomeCasoComuidade = nomeCasoComuidade;
    }

    public BooleanFilter getConsultaPrevia() {
        return consultaPrevia;
    }

    public void setConsultaPrevia(BooleanFilter consultaPrevia) {
        this.consultaPrevia = consultaPrevia;
    }

    public LongFilter getConflitoId() {
        return conflitoId;
    }

    public void setConflitoId(LongFilter conflitoId) {
        this.conflitoId = conflitoId;
    }

    public LongFilter getDireitoId() {
        return direitoId;
    }

    public void setDireitoId(LongFilter direitoId) {
        this.direitoId = direitoId;
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
        final ProcessoConflitoCriteria that = (ProcessoConflitoCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(nomeCasoComuidade, that.nomeCasoComuidade) &&
            Objects.equals(consultaPrevia, that.consultaPrevia) &&
            Objects.equals(conflitoId, that.conflitoId) &&
            Objects.equals(direitoId, that.direitoId) &&
            Objects.equals(processoId, that.processoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        nomeCasoComuidade,
        consultaPrevia,
        conflitoId,
        direitoId,
        processoId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProcessoConflitoCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (nomeCasoComuidade != null ? "nomeCasoComuidade=" + nomeCasoComuidade + ", " : "") +
                (consultaPrevia != null ? "consultaPrevia=" + consultaPrevia + ", " : "") +
                (conflitoId != null ? "conflitoId=" + conflitoId + ", " : "") +
                (direitoId != null ? "direitoId=" + direitoId + ", " : "") +
                (processoId != null ? "processoId=" + processoId + ", " : "") +
            "}";
    }

}
