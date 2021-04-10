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
 * Criteria class for the {@link br.com.cidha.domain.ProblemaJuridico} entity. This class is used
 * in {@link br.com.cidha.web.rest.ProblemaJuridicoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /problema-juridicos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ProblemaJuridicoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter folhasProblemaJuridico;

    private LongFilter fundamentacaoDoutrinariaId;

    private LongFilter jurisprudenciaId;

    private LongFilter fundamentacaoLegalId;

    private LongFilter instrumentoInternacionalId;

    private LongFilter processoId;

    public ProblemaJuridicoCriteria() {
    }

    public ProblemaJuridicoCriteria(ProblemaJuridicoCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.folhasProblemaJuridico = other.folhasProblemaJuridico == null ? null : other.folhasProblemaJuridico.copy();
        this.fundamentacaoDoutrinariaId = other.fundamentacaoDoutrinariaId == null ? null : other.fundamentacaoDoutrinariaId.copy();
        this.jurisprudenciaId = other.jurisprudenciaId == null ? null : other.jurisprudenciaId.copy();
        this.fundamentacaoLegalId = other.fundamentacaoLegalId == null ? null : other.fundamentacaoLegalId.copy();
        this.instrumentoInternacionalId = other.instrumentoInternacionalId == null ? null : other.instrumentoInternacionalId.copy();
        this.processoId = other.processoId == null ? null : other.processoId.copy();
    }

    @Override
    public ProblemaJuridicoCriteria copy() {
        return new ProblemaJuridicoCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getFolhasProblemaJuridico() {
        return folhasProblemaJuridico;
    }

    public void setFolhasProblemaJuridico(StringFilter folhasProblemaJuridico) {
        this.folhasProblemaJuridico = folhasProblemaJuridico;
    }

    public LongFilter getFundamentacaoDoutrinariaId() {
        return fundamentacaoDoutrinariaId;
    }

    public void setFundamentacaoDoutrinariaId(LongFilter fundamentacaoDoutrinariaId) {
        this.fundamentacaoDoutrinariaId = fundamentacaoDoutrinariaId;
    }

    public LongFilter getJurisprudenciaId() {
        return jurisprudenciaId;
    }

    public void setJurisprudenciaId(LongFilter jurisprudenciaId) {
        this.jurisprudenciaId = jurisprudenciaId;
    }

    public LongFilter getFundamentacaoLegalId() {
        return fundamentacaoLegalId;
    }

    public void setFundamentacaoLegalId(LongFilter fundamentacaoLegalId) {
        this.fundamentacaoLegalId = fundamentacaoLegalId;
    }

    public LongFilter getInstrumentoInternacionalId() {
        return instrumentoInternacionalId;
    }

    public void setInstrumentoInternacionalId(LongFilter instrumentoInternacionalId) {
        this.instrumentoInternacionalId = instrumentoInternacionalId;
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
        final ProblemaJuridicoCriteria that = (ProblemaJuridicoCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(folhasProblemaJuridico, that.folhasProblemaJuridico) &&
            Objects.equals(fundamentacaoDoutrinariaId, that.fundamentacaoDoutrinariaId) &&
            Objects.equals(jurisprudenciaId, that.jurisprudenciaId) &&
            Objects.equals(fundamentacaoLegalId, that.fundamentacaoLegalId) &&
            Objects.equals(instrumentoInternacionalId, that.instrumentoInternacionalId) &&
            Objects.equals(processoId, that.processoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        folhasProblemaJuridico,
        fundamentacaoDoutrinariaId,
        jurisprudenciaId,
        fundamentacaoLegalId,
        instrumentoInternacionalId,
        processoId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProblemaJuridicoCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (folhasProblemaJuridico != null ? "folhasProblemaJuridico=" + folhasProblemaJuridico + ", " : "") +
                (fundamentacaoDoutrinariaId != null ? "fundamentacaoDoutrinariaId=" + fundamentacaoDoutrinariaId + ", " : "") +
                (jurisprudenciaId != null ? "jurisprudenciaId=" + jurisprudenciaId + ", " : "") +
                (fundamentacaoLegalId != null ? "fundamentacaoLegalId=" + fundamentacaoLegalId + ", " : "") +
                (instrumentoInternacionalId != null ? "instrumentoInternacionalId=" + instrumentoInternacionalId + ", " : "") +
                (processoId != null ? "processoId=" + processoId + ", " : "") +
            "}";
    }

}
