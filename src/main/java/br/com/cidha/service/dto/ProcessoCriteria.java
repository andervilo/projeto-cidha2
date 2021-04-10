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
 * Criteria class for the {@link br.com.cidha.domain.Processo} entity. This class is used
 * in {@link br.com.cidha.web.rest.ProcessoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /processos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ProcessoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter oficio;

    private StringFilter linkUnico;

    private StringFilter linkTrf;

    private StringFilter subsecaoJudiciaria;

    private StringFilter turmaTrf1;

    private StringFilter numeroProcessoAdministrativo;

    private StringFilter numeroProcessoJudicialPrimeiraInstancia;

    private StringFilter numeroProcessoJudicialPrimeiraInstanciaLink;

    private BooleanFilter parecer;

    private StringFilter apelacao;

    private LongFilter concessaoLiminarId;

    private LongFilter concessaoLiminarCassadaId;

    private LongFilter embargoRespReId;

    private LongFilter embargoDeclaracaoAgravoId;

    private LongFilter embargoDeclaracaoId;

    private LongFilter embargoRecursoEspecialId;

    private LongFilter tipoDecisaoId;

    private LongFilter tipoEmpreendimentoId;

    private LongFilter comarcaId;

    private LongFilter quilomboId;

    private LongFilter municipioId;

    private LongFilter territorioId;

    private LongFilter atividadeExploracaoIlegalId;

    private LongFilter unidadeConservacaoId;

    private LongFilter envolvidosConflitoLitigioId;

    private LongFilter terraIndigenaId;

    private LongFilter processoConflitoId;

    private LongFilter parteInteresssadaId;

    private LongFilter relatorId;

    private LongFilter problemaJuridicoId;

    public ProcessoCriteria() {
    }

    public ProcessoCriteria(ProcessoCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.oficio = other.oficio == null ? null : other.oficio.copy();
        this.linkUnico = other.linkUnico == null ? null : other.linkUnico.copy();
        this.linkTrf = other.linkTrf == null ? null : other.linkTrf.copy();
        this.subsecaoJudiciaria = other.subsecaoJudiciaria == null ? null : other.subsecaoJudiciaria.copy();
        this.turmaTrf1 = other.turmaTrf1 == null ? null : other.turmaTrf1.copy();
        this.numeroProcessoAdministrativo = other.numeroProcessoAdministrativo == null ? null : other.numeroProcessoAdministrativo.copy();
        this.numeroProcessoJudicialPrimeiraInstancia = other.numeroProcessoJudicialPrimeiraInstancia == null ? null : other.numeroProcessoJudicialPrimeiraInstancia.copy();
        this.numeroProcessoJudicialPrimeiraInstanciaLink = other.numeroProcessoJudicialPrimeiraInstanciaLink == null ? null : other.numeroProcessoJudicialPrimeiraInstanciaLink.copy();
        this.parecer = other.parecer == null ? null : other.parecer.copy();
        this.apelacao = other.apelacao == null ? null : other.apelacao.copy();
        this.concessaoLiminarId = other.concessaoLiminarId == null ? null : other.concessaoLiminarId.copy();
        this.concessaoLiminarCassadaId = other.concessaoLiminarCassadaId == null ? null : other.concessaoLiminarCassadaId.copy();
        this.embargoRespReId = other.embargoRespReId == null ? null : other.embargoRespReId.copy();
        this.embargoDeclaracaoAgravoId = other.embargoDeclaracaoAgravoId == null ? null : other.embargoDeclaracaoAgravoId.copy();
        this.embargoDeclaracaoId = other.embargoDeclaracaoId == null ? null : other.embargoDeclaracaoId.copy();
        this.embargoRecursoEspecialId = other.embargoRecursoEspecialId == null ? null : other.embargoRecursoEspecialId.copy();
        this.tipoDecisaoId = other.tipoDecisaoId == null ? null : other.tipoDecisaoId.copy();
        this.tipoEmpreendimentoId = other.tipoEmpreendimentoId == null ? null : other.tipoEmpreendimentoId.copy();
        this.comarcaId = other.comarcaId == null ? null : other.comarcaId.copy();
        this.quilomboId = other.quilomboId == null ? null : other.quilomboId.copy();
        this.municipioId = other.municipioId == null ? null : other.municipioId.copy();
        this.territorioId = other.territorioId == null ? null : other.territorioId.copy();
        this.atividadeExploracaoIlegalId = other.atividadeExploracaoIlegalId == null ? null : other.atividadeExploracaoIlegalId.copy();
        this.unidadeConservacaoId = other.unidadeConservacaoId == null ? null : other.unidadeConservacaoId.copy();
        this.envolvidosConflitoLitigioId = other.envolvidosConflitoLitigioId == null ? null : other.envolvidosConflitoLitigioId.copy();
        this.terraIndigenaId = other.terraIndigenaId == null ? null : other.terraIndigenaId.copy();
        this.processoConflitoId = other.processoConflitoId == null ? null : other.processoConflitoId.copy();
        this.parteInteresssadaId = other.parteInteresssadaId == null ? null : other.parteInteresssadaId.copy();
        this.relatorId = other.relatorId == null ? null : other.relatorId.copy();
        this.problemaJuridicoId = other.problemaJuridicoId == null ? null : other.problemaJuridicoId.copy();
    }

    @Override
    public ProcessoCriteria copy() {
        return new ProcessoCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getOficio() {
        return oficio;
    }

    public void setOficio(StringFilter oficio) {
        this.oficio = oficio;
    }

    public StringFilter getLinkUnico() {
        return linkUnico;
    }

    public void setLinkUnico(StringFilter linkUnico) {
        this.linkUnico = linkUnico;
    }

    public StringFilter getLinkTrf() {
        return linkTrf;
    }

    public void setLinkTrf(StringFilter linkTrf) {
        this.linkTrf = linkTrf;
    }

    public StringFilter getSubsecaoJudiciaria() {
        return subsecaoJudiciaria;
    }

    public void setSubsecaoJudiciaria(StringFilter subsecaoJudiciaria) {
        this.subsecaoJudiciaria = subsecaoJudiciaria;
    }

    public StringFilter getTurmaTrf1() {
        return turmaTrf1;
    }

    public void setTurmaTrf1(StringFilter turmaTrf1) {
        this.turmaTrf1 = turmaTrf1;
    }

    public StringFilter getNumeroProcessoAdministrativo() {
        return numeroProcessoAdministrativo;
    }

    public void setNumeroProcessoAdministrativo(StringFilter numeroProcessoAdministrativo) {
        this.numeroProcessoAdministrativo = numeroProcessoAdministrativo;
    }

    public StringFilter getNumeroProcessoJudicialPrimeiraInstancia() {
        return numeroProcessoJudicialPrimeiraInstancia;
    }

    public void setNumeroProcessoJudicialPrimeiraInstancia(StringFilter numeroProcessoJudicialPrimeiraInstancia) {
        this.numeroProcessoJudicialPrimeiraInstancia = numeroProcessoJudicialPrimeiraInstancia;
    }

    public StringFilter getNumeroProcessoJudicialPrimeiraInstanciaLink() {
        return numeroProcessoJudicialPrimeiraInstanciaLink;
    }

    public void setNumeroProcessoJudicialPrimeiraInstanciaLink(StringFilter numeroProcessoJudicialPrimeiraInstanciaLink) {
        this.numeroProcessoJudicialPrimeiraInstanciaLink = numeroProcessoJudicialPrimeiraInstanciaLink;
    }

    public BooleanFilter getParecer() {
        return parecer;
    }

    public void setParecer(BooleanFilter parecer) {
        this.parecer = parecer;
    }

    public StringFilter getApelacao() {
        return apelacao;
    }

    public void setApelacao(StringFilter apelacao) {
        this.apelacao = apelacao;
    }

    public LongFilter getConcessaoLiminarId() {
        return concessaoLiminarId;
    }

    public void setConcessaoLiminarId(LongFilter concessaoLiminarId) {
        this.concessaoLiminarId = concessaoLiminarId;
    }

    public LongFilter getConcessaoLiminarCassadaId() {
        return concessaoLiminarCassadaId;
    }

    public void setConcessaoLiminarCassadaId(LongFilter concessaoLiminarCassadaId) {
        this.concessaoLiminarCassadaId = concessaoLiminarCassadaId;
    }

    public LongFilter getEmbargoRespReId() {
        return embargoRespReId;
    }

    public void setEmbargoRespReId(LongFilter embargoRespReId) {
        this.embargoRespReId = embargoRespReId;
    }

    public LongFilter getEmbargoDeclaracaoAgravoId() {
        return embargoDeclaracaoAgravoId;
    }

    public void setEmbargoDeclaracaoAgravoId(LongFilter embargoDeclaracaoAgravoId) {
        this.embargoDeclaracaoAgravoId = embargoDeclaracaoAgravoId;
    }

    public LongFilter getEmbargoDeclaracaoId() {
        return embargoDeclaracaoId;
    }

    public void setEmbargoDeclaracaoId(LongFilter embargoDeclaracaoId) {
        this.embargoDeclaracaoId = embargoDeclaracaoId;
    }

    public LongFilter getEmbargoRecursoEspecialId() {
        return embargoRecursoEspecialId;
    }

    public void setEmbargoRecursoEspecialId(LongFilter embargoRecursoEspecialId) {
        this.embargoRecursoEspecialId = embargoRecursoEspecialId;
    }

    public LongFilter getTipoDecisaoId() {
        return tipoDecisaoId;
    }

    public void setTipoDecisaoId(LongFilter tipoDecisaoId) {
        this.tipoDecisaoId = tipoDecisaoId;
    }

    public LongFilter getTipoEmpreendimentoId() {
        return tipoEmpreendimentoId;
    }

    public void setTipoEmpreendimentoId(LongFilter tipoEmpreendimentoId) {
        this.tipoEmpreendimentoId = tipoEmpreendimentoId;
    }

    public LongFilter getComarcaId() {
        return comarcaId;
    }

    public void setComarcaId(LongFilter comarcaId) {
        this.comarcaId = comarcaId;
    }

    public LongFilter getQuilomboId() {
        return quilomboId;
    }

    public void setQuilomboId(LongFilter quilomboId) {
        this.quilomboId = quilomboId;
    }

    public LongFilter getMunicipioId() {
        return municipioId;
    }

    public void setMunicipioId(LongFilter municipioId) {
        this.municipioId = municipioId;
    }

    public LongFilter getTerritorioId() {
        return territorioId;
    }

    public void setTerritorioId(LongFilter territorioId) {
        this.territorioId = territorioId;
    }

    public LongFilter getAtividadeExploracaoIlegalId() {
        return atividadeExploracaoIlegalId;
    }

    public void setAtividadeExploracaoIlegalId(LongFilter atividadeExploracaoIlegalId) {
        this.atividadeExploracaoIlegalId = atividadeExploracaoIlegalId;
    }

    public LongFilter getUnidadeConservacaoId() {
        return unidadeConservacaoId;
    }

    public void setUnidadeConservacaoId(LongFilter unidadeConservacaoId) {
        this.unidadeConservacaoId = unidadeConservacaoId;
    }

    public LongFilter getEnvolvidosConflitoLitigioId() {
        return envolvidosConflitoLitigioId;
    }

    public void setEnvolvidosConflitoLitigioId(LongFilter envolvidosConflitoLitigioId) {
        this.envolvidosConflitoLitigioId = envolvidosConflitoLitigioId;
    }

    public LongFilter getTerraIndigenaId() {
        return terraIndigenaId;
    }

    public void setTerraIndigenaId(LongFilter terraIndigenaId) {
        this.terraIndigenaId = terraIndigenaId;
    }

    public LongFilter getProcessoConflitoId() {
        return processoConflitoId;
    }

    public void setProcessoConflitoId(LongFilter processoConflitoId) {
        this.processoConflitoId = processoConflitoId;
    }

    public LongFilter getParteInteresssadaId() {
        return parteInteresssadaId;
    }

    public void setParteInteresssadaId(LongFilter parteInteresssadaId) {
        this.parteInteresssadaId = parteInteresssadaId;
    }

    public LongFilter getRelatorId() {
        return relatorId;
    }

    public void setRelatorId(LongFilter relatorId) {
        this.relatorId = relatorId;
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
        final ProcessoCriteria that = (ProcessoCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(oficio, that.oficio) &&
            Objects.equals(linkUnico, that.linkUnico) &&
            Objects.equals(linkTrf, that.linkTrf) &&
            Objects.equals(subsecaoJudiciaria, that.subsecaoJudiciaria) &&
            Objects.equals(turmaTrf1, that.turmaTrf1) &&
            Objects.equals(numeroProcessoAdministrativo, that.numeroProcessoAdministrativo) &&
            Objects.equals(numeroProcessoJudicialPrimeiraInstancia, that.numeroProcessoJudicialPrimeiraInstancia) &&
            Objects.equals(numeroProcessoJudicialPrimeiraInstanciaLink, that.numeroProcessoJudicialPrimeiraInstanciaLink) &&
            Objects.equals(parecer, that.parecer) &&
            Objects.equals(apelacao, that.apelacao) &&
            Objects.equals(concessaoLiminarId, that.concessaoLiminarId) &&
            Objects.equals(concessaoLiminarCassadaId, that.concessaoLiminarCassadaId) &&
            Objects.equals(embargoRespReId, that.embargoRespReId) &&
            Objects.equals(embargoDeclaracaoAgravoId, that.embargoDeclaracaoAgravoId) &&
            Objects.equals(embargoDeclaracaoId, that.embargoDeclaracaoId) &&
            Objects.equals(embargoRecursoEspecialId, that.embargoRecursoEspecialId) &&
            Objects.equals(tipoDecisaoId, that.tipoDecisaoId) &&
            Objects.equals(tipoEmpreendimentoId, that.tipoEmpreendimentoId) &&
            Objects.equals(comarcaId, that.comarcaId) &&
            Objects.equals(quilomboId, that.quilomboId) &&
            Objects.equals(municipioId, that.municipioId) &&
            Objects.equals(territorioId, that.territorioId) &&
            Objects.equals(atividadeExploracaoIlegalId, that.atividadeExploracaoIlegalId) &&
            Objects.equals(unidadeConservacaoId, that.unidadeConservacaoId) &&
            Objects.equals(envolvidosConflitoLitigioId, that.envolvidosConflitoLitigioId) &&
            Objects.equals(terraIndigenaId, that.terraIndigenaId) &&
            Objects.equals(processoConflitoId, that.processoConflitoId) &&
            Objects.equals(parteInteresssadaId, that.parteInteresssadaId) &&
            Objects.equals(relatorId, that.relatorId) &&
            Objects.equals(problemaJuridicoId, that.problemaJuridicoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        oficio,
        linkUnico,
        linkTrf,
        subsecaoJudiciaria,
        turmaTrf1,
        numeroProcessoAdministrativo,
        numeroProcessoJudicialPrimeiraInstancia,
        numeroProcessoJudicialPrimeiraInstanciaLink,
        parecer,
        apelacao,
        concessaoLiminarId,
        concessaoLiminarCassadaId,
        embargoRespReId,
        embargoDeclaracaoAgravoId,
        embargoDeclaracaoId,
        embargoRecursoEspecialId,
        tipoDecisaoId,
        tipoEmpreendimentoId,
        comarcaId,
        quilomboId,
        municipioId,
        territorioId,
        atividadeExploracaoIlegalId,
        unidadeConservacaoId,
        envolvidosConflitoLitigioId,
        terraIndigenaId,
        processoConflitoId,
        parteInteresssadaId,
        relatorId,
        problemaJuridicoId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProcessoCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (oficio != null ? "oficio=" + oficio + ", " : "") +
                (linkUnico != null ? "linkUnico=" + linkUnico + ", " : "") +
                (linkTrf != null ? "linkTrf=" + linkTrf + ", " : "") +
                (subsecaoJudiciaria != null ? "subsecaoJudiciaria=" + subsecaoJudiciaria + ", " : "") +
                (turmaTrf1 != null ? "turmaTrf1=" + turmaTrf1 + ", " : "") +
                (numeroProcessoAdministrativo != null ? "numeroProcessoAdministrativo=" + numeroProcessoAdministrativo + ", " : "") +
                (numeroProcessoJudicialPrimeiraInstancia != null ? "numeroProcessoJudicialPrimeiraInstancia=" + numeroProcessoJudicialPrimeiraInstancia + ", " : "") +
                (numeroProcessoJudicialPrimeiraInstanciaLink != null ? "numeroProcessoJudicialPrimeiraInstanciaLink=" + numeroProcessoJudicialPrimeiraInstanciaLink + ", " : "") +
                (parecer != null ? "parecer=" + parecer + ", " : "") +
                (apelacao != null ? "apelacao=" + apelacao + ", " : "") +
                (concessaoLiminarId != null ? "concessaoLiminarId=" + concessaoLiminarId + ", " : "") +
                (concessaoLiminarCassadaId != null ? "concessaoLiminarCassadaId=" + concessaoLiminarCassadaId + ", " : "") +
                (embargoRespReId != null ? "embargoRespReId=" + embargoRespReId + ", " : "") +
                (embargoDeclaracaoAgravoId != null ? "embargoDeclaracaoAgravoId=" + embargoDeclaracaoAgravoId + ", " : "") +
                (embargoDeclaracaoId != null ? "embargoDeclaracaoId=" + embargoDeclaracaoId + ", " : "") +
                (embargoRecursoEspecialId != null ? "embargoRecursoEspecialId=" + embargoRecursoEspecialId + ", " : "") +
                (tipoDecisaoId != null ? "tipoDecisaoId=" + tipoDecisaoId + ", " : "") +
                (tipoEmpreendimentoId != null ? "tipoEmpreendimentoId=" + tipoEmpreendimentoId + ", " : "") +
                (comarcaId != null ? "comarcaId=" + comarcaId + ", " : "") +
                (quilomboId != null ? "quilomboId=" + quilomboId + ", " : "") +
                (municipioId != null ? "municipioId=" + municipioId + ", " : "") +
                (territorioId != null ? "territorioId=" + territorioId + ", " : "") +
                (atividadeExploracaoIlegalId != null ? "atividadeExploracaoIlegalId=" + atividadeExploracaoIlegalId + ", " : "") +
                (unidadeConservacaoId != null ? "unidadeConservacaoId=" + unidadeConservacaoId + ", " : "") +
                (envolvidosConflitoLitigioId != null ? "envolvidosConflitoLitigioId=" + envolvidosConflitoLitigioId + ", " : "") +
                (terraIndigenaId != null ? "terraIndigenaId=" + terraIndigenaId + ", " : "") +
                (processoConflitoId != null ? "processoConflitoId=" + processoConflitoId + ", " : "") +
                (parteInteresssadaId != null ? "parteInteresssadaId=" + parteInteresssadaId + ", " : "") +
                (relatorId != null ? "relatorId=" + relatorId + ", " : "") +
                (problemaJuridicoId != null ? "problemaJuridicoId=" + problemaJuridicoId + ", " : "") +
            "}";
    }

}
