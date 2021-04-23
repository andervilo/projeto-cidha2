package br.com.cidha.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BigDecimalFilter;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

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

    private StringFilter numeroProcesso;

    private StringFilter oficio;

    private StringFilter linkUnico;

    private StringFilter linkTrf;

    private StringFilter secaoJudiciaria;

    private StringFilter subsecaoJudiciaria;

    private StringFilter turmaTrf1;

    private StringFilter numeroProcessoAdministrativo;

    private StringFilter numeroProcessoJudicialPrimeiraInstancia;

    private StringFilter numeroProcessoJudicialPrimeiraInstanciaLink;

    private BooleanFilter parecer;

    private StringFilter folhasProcessoConcessaoLiminar;

    private StringFilter folhasProcessoCassacao;

    private StringFilter folhasParecer;

    private StringFilter folhasEmbargo;

    private StringFilter folhasCienciaJulgEmbargos;

    private StringFilter apelacao;

    private StringFilter folhasApelacao;

    private StringFilter folhasCienciaJulgApelacao;

    private BooleanFilter embargoDeclaracao;

    private StringFilter folhasRecursoEspecial;

    private StringFilter folhasCienciaJulgamentoRecursoEspecial;

    private BooleanFilter embargoRecursoEspecial;

    private StringFilter folhasCiencia;

    private StringFilter agravoRespRe;

    private StringFilter folhasRespRe;

    private StringFilter folhasCienciaJulgamentoAgravoRespRe;

    private StringFilter embargoRespRe;

    private StringFilter agravoInterno;

    private StringFilter folhasAgravoInterno;

    private BooleanFilter embargoRecursoAgravo;

    private BooleanFilter recursoSTJ;

    private StringFilter linkRecursoSTJ;

    private StringFilter folhasRecursoSTJ;

    private BooleanFilter recursoSTF;

    private StringFilter linkRecursoSTF;

    private StringFilter folhasRecursoSTF;

    private StringFilter folhasMemorialMPF;

    private BooleanFilter execusaoProvisoria;

    private StringFilter numeracaoExecusaoProvisoria;

    private BooleanFilter envolveEmpreendimento;

    private BooleanFilter envolveExploracaoIlegal;

    private BooleanFilter envolveTerraQuilombola;

    private BooleanFilter envolveTerraComunidadeTradicional;

    private BooleanFilter envolveTerraIndigena;

    private BigDecimalFilter tamanhoArea;

    private BigDecimalFilter valorArea;

    private BooleanFilter dadosGeograficosLitigioConflito;

    private StringFilter latitude;

    private StringFilter longitude;

    private StringFilter numeroProcessoMPF;

    private StringFilter numeroEmbargo;

    private StringFilter numeroRecursoEspecial;

    private BooleanFilter envolveGrandeProjeto;

    private BooleanFilter envolveUnidadeConservacao;

    private LongFilter concessaoLiminarId;

    private LongFilter concessaoLiminarCassadaId;

    private LongFilter embargoDeclaracaoId;

    private LongFilter embargoDeclaracaoAgravoId;

    private LongFilter embargoRecursoEspecialId;

    private LongFilter embargoRespReId;

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

    public ProcessoCriteria() {}

    public ProcessoCriteria(ProcessoCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.numeroProcesso = other.numeroProcesso == null ? null : other.numeroProcesso.copy();
        this.oficio = other.oficio == null ? null : other.oficio.copy();
        this.linkUnico = other.linkUnico == null ? null : other.linkUnico.copy();
        this.linkTrf = other.linkTrf == null ? null : other.linkTrf.copy();
        this.secaoJudiciaria = other.secaoJudiciaria == null ? null : other.secaoJudiciaria.copy();
        this.subsecaoJudiciaria = other.subsecaoJudiciaria == null ? null : other.subsecaoJudiciaria.copy();
        this.turmaTrf1 = other.turmaTrf1 == null ? null : other.turmaTrf1.copy();
        this.numeroProcessoAdministrativo = other.numeroProcessoAdministrativo == null ? null : other.numeroProcessoAdministrativo.copy();
        this.numeroProcessoJudicialPrimeiraInstancia =
            other.numeroProcessoJudicialPrimeiraInstancia == null ? null : other.numeroProcessoJudicialPrimeiraInstancia.copy();
        this.numeroProcessoJudicialPrimeiraInstanciaLink =
            other.numeroProcessoJudicialPrimeiraInstanciaLink == null ? null : other.numeroProcessoJudicialPrimeiraInstanciaLink.copy();
        this.parecer = other.parecer == null ? null : other.parecer.copy();
        this.folhasProcessoConcessaoLiminar =
            other.folhasProcessoConcessaoLiminar == null ? null : other.folhasProcessoConcessaoLiminar.copy();
        this.folhasProcessoCassacao = other.folhasProcessoCassacao == null ? null : other.folhasProcessoCassacao.copy();
        this.folhasParecer = other.folhasParecer == null ? null : other.folhasParecer.copy();
        this.folhasEmbargo = other.folhasEmbargo == null ? null : other.folhasEmbargo.copy();
        this.folhasCienciaJulgEmbargos = other.folhasCienciaJulgEmbargos == null ? null : other.folhasCienciaJulgEmbargos.copy();
        this.apelacao = other.apelacao == null ? null : other.apelacao.copy();
        this.folhasApelacao = other.folhasApelacao == null ? null : other.folhasApelacao.copy();
        this.folhasCienciaJulgApelacao = other.folhasCienciaJulgApelacao == null ? null : other.folhasCienciaJulgApelacao.copy();
        this.embargoDeclaracao = other.embargoDeclaracao == null ? null : other.embargoDeclaracao.copy();
        this.folhasRecursoEspecial = other.folhasRecursoEspecial == null ? null : other.folhasRecursoEspecial.copy();
        this.folhasCienciaJulgamentoRecursoEspecial =
            other.folhasCienciaJulgamentoRecursoEspecial == null ? null : other.folhasCienciaJulgamentoRecursoEspecial.copy();
        this.embargoRecursoEspecial = other.embargoRecursoEspecial == null ? null : other.embargoRecursoEspecial.copy();
        this.folhasCiencia = other.folhasCiencia == null ? null : other.folhasCiencia.copy();
        this.agravoRespRe = other.agravoRespRe == null ? null : other.agravoRespRe.copy();
        this.folhasRespRe = other.folhasRespRe == null ? null : other.folhasRespRe.copy();
        this.folhasCienciaJulgamentoAgravoRespRe =
            other.folhasCienciaJulgamentoAgravoRespRe == null ? null : other.folhasCienciaJulgamentoAgravoRespRe.copy();
        this.embargoRespRe = other.embargoRespRe == null ? null : other.embargoRespRe.copy();
        this.agravoInterno = other.agravoInterno == null ? null : other.agravoInterno.copy();
        this.folhasAgravoInterno = other.folhasAgravoInterno == null ? null : other.folhasAgravoInterno.copy();
        this.embargoRecursoAgravo = other.embargoRecursoAgravo == null ? null : other.embargoRecursoAgravo.copy();
        this.recursoSTJ = other.recursoSTJ == null ? null : other.recursoSTJ.copy();
        this.linkRecursoSTJ = other.linkRecursoSTJ == null ? null : other.linkRecursoSTJ.copy();
        this.folhasRecursoSTJ = other.folhasRecursoSTJ == null ? null : other.folhasRecursoSTJ.copy();
        this.recursoSTF = other.recursoSTF == null ? null : other.recursoSTF.copy();
        this.linkRecursoSTF = other.linkRecursoSTF == null ? null : other.linkRecursoSTF.copy();
        this.folhasRecursoSTF = other.folhasRecursoSTF == null ? null : other.folhasRecursoSTF.copy();
        this.folhasMemorialMPF = other.folhasMemorialMPF == null ? null : other.folhasMemorialMPF.copy();
        this.execusaoProvisoria = other.execusaoProvisoria == null ? null : other.execusaoProvisoria.copy();
        this.numeracaoExecusaoProvisoria = other.numeracaoExecusaoProvisoria == null ? null : other.numeracaoExecusaoProvisoria.copy();
        this.envolveEmpreendimento = other.envolveEmpreendimento == null ? null : other.envolveEmpreendimento.copy();
        this.envolveExploracaoIlegal = other.envolveExploracaoIlegal == null ? null : other.envolveExploracaoIlegal.copy();
        this.envolveTerraQuilombola = other.envolveTerraQuilombola == null ? null : other.envolveTerraQuilombola.copy();
        this.envolveTerraComunidadeTradicional =
            other.envolveTerraComunidadeTradicional == null ? null : other.envolveTerraComunidadeTradicional.copy();
        this.envolveTerraIndigena = other.envolveTerraIndigena == null ? null : other.envolveTerraIndigena.copy();
        this.tamanhoArea = other.tamanhoArea == null ? null : other.tamanhoArea.copy();
        this.valorArea = other.valorArea == null ? null : other.valorArea.copy();
        this.dadosGeograficosLitigioConflito =
            other.dadosGeograficosLitigioConflito == null ? null : other.dadosGeograficosLitigioConflito.copy();
        this.latitude = other.latitude == null ? null : other.latitude.copy();
        this.longitude = other.longitude == null ? null : other.longitude.copy();
        this.numeroProcessoMPF = other.numeroProcessoMPF == null ? null : other.numeroProcessoMPF.copy();
        this.numeroEmbargo = other.numeroEmbargo == null ? null : other.numeroEmbargo.copy();
        this.numeroRecursoEspecial = other.numeroRecursoEspecial == null ? null : other.numeroRecursoEspecial.copy();
        this.envolveGrandeProjeto = other.envolveGrandeProjeto == null ? null : other.envolveGrandeProjeto.copy();
        this.envolveUnidadeConservacao = other.envolveUnidadeConservacao == null ? null : other.envolveUnidadeConservacao.copy();
        this.concessaoLiminarId = other.concessaoLiminarId == null ? null : other.concessaoLiminarId.copy();
        this.concessaoLiminarCassadaId = other.concessaoLiminarCassadaId == null ? null : other.concessaoLiminarCassadaId.copy();
        this.embargoDeclaracaoId = other.embargoDeclaracaoId == null ? null : other.embargoDeclaracaoId.copy();
        this.embargoDeclaracaoAgravoId = other.embargoDeclaracaoAgravoId == null ? null : other.embargoDeclaracaoAgravoId.copy();
        this.embargoRecursoEspecialId = other.embargoRecursoEspecialId == null ? null : other.embargoRecursoEspecialId.copy();
        this.embargoRespReId = other.embargoRespReId == null ? null : other.embargoRespReId.copy();
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

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getNumeroProcesso() {
        return numeroProcesso;
    }

    public StringFilter numeroProcesso() {
        if (numeroProcesso == null) {
            numeroProcesso = new StringFilter();
        }
        return numeroProcesso;
    }

    public void setNumeroProcesso(StringFilter numeroProcesso) {
        this.numeroProcesso = numeroProcesso;
    }

    public StringFilter getOficio() {
        return oficio;
    }

    public StringFilter oficio() {
        if (oficio == null) {
            oficio = new StringFilter();
        }
        return oficio;
    }

    public void setOficio(StringFilter oficio) {
        this.oficio = oficio;
    }

    public StringFilter getLinkUnico() {
        return linkUnico;
    }

    public StringFilter linkUnico() {
        if (linkUnico == null) {
            linkUnico = new StringFilter();
        }
        return linkUnico;
    }

    public void setLinkUnico(StringFilter linkUnico) {
        this.linkUnico = linkUnico;
    }

    public StringFilter getLinkTrf() {
        return linkTrf;
    }

    public StringFilter linkTrf() {
        if (linkTrf == null) {
            linkTrf = new StringFilter();
        }
        return linkTrf;
    }

    public void setLinkTrf(StringFilter linkTrf) {
        this.linkTrf = linkTrf;
    }

    public StringFilter getSecaoJudiciaria() {
        return secaoJudiciaria;
    }

    public StringFilter secaoJudiciaria() {
        if (secaoJudiciaria == null) {
            secaoJudiciaria = new StringFilter();
        }
        return secaoJudiciaria;
    }

    public void setSecaoJudiciaria(StringFilter secaoJudiciaria) {
        this.secaoJudiciaria = secaoJudiciaria;
    }

    public StringFilter getSubsecaoJudiciaria() {
        return subsecaoJudiciaria;
    }

    public StringFilter subsecaoJudiciaria() {
        if (subsecaoJudiciaria == null) {
            subsecaoJudiciaria = new StringFilter();
        }
        return subsecaoJudiciaria;
    }

    public void setSubsecaoJudiciaria(StringFilter subsecaoJudiciaria) {
        this.subsecaoJudiciaria = subsecaoJudiciaria;
    }

    public StringFilter getTurmaTrf1() {
        return turmaTrf1;
    }

    public StringFilter turmaTrf1() {
        if (turmaTrf1 == null) {
            turmaTrf1 = new StringFilter();
        }
        return turmaTrf1;
    }

    public void setTurmaTrf1(StringFilter turmaTrf1) {
        this.turmaTrf1 = turmaTrf1;
    }

    public StringFilter getNumeroProcessoAdministrativo() {
        return numeroProcessoAdministrativo;
    }

    public StringFilter numeroProcessoAdministrativo() {
        if (numeroProcessoAdministrativo == null) {
            numeroProcessoAdministrativo = new StringFilter();
        }
        return numeroProcessoAdministrativo;
    }

    public void setNumeroProcessoAdministrativo(StringFilter numeroProcessoAdministrativo) {
        this.numeroProcessoAdministrativo = numeroProcessoAdministrativo;
    }

    public StringFilter getNumeroProcessoJudicialPrimeiraInstancia() {
        return numeroProcessoJudicialPrimeiraInstancia;
    }

    public StringFilter numeroProcessoJudicialPrimeiraInstancia() {
        if (numeroProcessoJudicialPrimeiraInstancia == null) {
            numeroProcessoJudicialPrimeiraInstancia = new StringFilter();
        }
        return numeroProcessoJudicialPrimeiraInstancia;
    }

    public void setNumeroProcessoJudicialPrimeiraInstancia(StringFilter numeroProcessoJudicialPrimeiraInstancia) {
        this.numeroProcessoJudicialPrimeiraInstancia = numeroProcessoJudicialPrimeiraInstancia;
    }

    public StringFilter getNumeroProcessoJudicialPrimeiraInstanciaLink() {
        return numeroProcessoJudicialPrimeiraInstanciaLink;
    }

    public StringFilter numeroProcessoJudicialPrimeiraInstanciaLink() {
        if (numeroProcessoJudicialPrimeiraInstanciaLink == null) {
            numeroProcessoJudicialPrimeiraInstanciaLink = new StringFilter();
        }
        return numeroProcessoJudicialPrimeiraInstanciaLink;
    }

    public void setNumeroProcessoJudicialPrimeiraInstanciaLink(StringFilter numeroProcessoJudicialPrimeiraInstanciaLink) {
        this.numeroProcessoJudicialPrimeiraInstanciaLink = numeroProcessoJudicialPrimeiraInstanciaLink;
    }

    public BooleanFilter getParecer() {
        return parecer;
    }

    public BooleanFilter parecer() {
        if (parecer == null) {
            parecer = new BooleanFilter();
        }
        return parecer;
    }

    public void setParecer(BooleanFilter parecer) {
        this.parecer = parecer;
    }

    public StringFilter getFolhasProcessoConcessaoLiminar() {
        return folhasProcessoConcessaoLiminar;
    }

    public StringFilter folhasProcessoConcessaoLiminar() {
        if (folhasProcessoConcessaoLiminar == null) {
            folhasProcessoConcessaoLiminar = new StringFilter();
        }
        return folhasProcessoConcessaoLiminar;
    }

    public void setFolhasProcessoConcessaoLiminar(StringFilter folhasProcessoConcessaoLiminar) {
        this.folhasProcessoConcessaoLiminar = folhasProcessoConcessaoLiminar;
    }

    public StringFilter getFolhasProcessoCassacao() {
        return folhasProcessoCassacao;
    }

    public StringFilter folhasProcessoCassacao() {
        if (folhasProcessoCassacao == null) {
            folhasProcessoCassacao = new StringFilter();
        }
        return folhasProcessoCassacao;
    }

    public void setFolhasProcessoCassacao(StringFilter folhasProcessoCassacao) {
        this.folhasProcessoCassacao = folhasProcessoCassacao;
    }

    public StringFilter getFolhasParecer() {
        return folhasParecer;
    }

    public StringFilter folhasParecer() {
        if (folhasParecer == null) {
            folhasParecer = new StringFilter();
        }
        return folhasParecer;
    }

    public void setFolhasParecer(StringFilter folhasParecer) {
        this.folhasParecer = folhasParecer;
    }

    public StringFilter getFolhasEmbargo() {
        return folhasEmbargo;
    }

    public StringFilter folhasEmbargo() {
        if (folhasEmbargo == null) {
            folhasEmbargo = new StringFilter();
        }
        return folhasEmbargo;
    }

    public void setFolhasEmbargo(StringFilter folhasEmbargo) {
        this.folhasEmbargo = folhasEmbargo;
    }

    public StringFilter getFolhasCienciaJulgEmbargos() {
        return folhasCienciaJulgEmbargos;
    }

    public StringFilter folhasCienciaJulgEmbargos() {
        if (folhasCienciaJulgEmbargos == null) {
            folhasCienciaJulgEmbargos = new StringFilter();
        }
        return folhasCienciaJulgEmbargos;
    }

    public void setFolhasCienciaJulgEmbargos(StringFilter folhasCienciaJulgEmbargos) {
        this.folhasCienciaJulgEmbargos = folhasCienciaJulgEmbargos;
    }

    public StringFilter getApelacao() {
        return apelacao;
    }

    public StringFilter apelacao() {
        if (apelacao == null) {
            apelacao = new StringFilter();
        }
        return apelacao;
    }

    public void setApelacao(StringFilter apelacao) {
        this.apelacao = apelacao;
    }

    public StringFilter getFolhasApelacao() {
        return folhasApelacao;
    }

    public StringFilter folhasApelacao() {
        if (folhasApelacao == null) {
            folhasApelacao = new StringFilter();
        }
        return folhasApelacao;
    }

    public void setFolhasApelacao(StringFilter folhasApelacao) {
        this.folhasApelacao = folhasApelacao;
    }

    public StringFilter getFolhasCienciaJulgApelacao() {
        return folhasCienciaJulgApelacao;
    }

    public StringFilter folhasCienciaJulgApelacao() {
        if (folhasCienciaJulgApelacao == null) {
            folhasCienciaJulgApelacao = new StringFilter();
        }
        return folhasCienciaJulgApelacao;
    }

    public void setFolhasCienciaJulgApelacao(StringFilter folhasCienciaJulgApelacao) {
        this.folhasCienciaJulgApelacao = folhasCienciaJulgApelacao;
    }

    public BooleanFilter getEmbargoDeclaracao() {
        return embargoDeclaracao;
    }

    public BooleanFilter embargoDeclaracao() {
        if (embargoDeclaracao == null) {
            embargoDeclaracao = new BooleanFilter();
        }
        return embargoDeclaracao;
    }

    public void setEmbargoDeclaracao(BooleanFilter embargoDeclaracao) {
        this.embargoDeclaracao = embargoDeclaracao;
    }

    public StringFilter getFolhasRecursoEspecial() {
        return folhasRecursoEspecial;
    }

    public StringFilter folhasRecursoEspecial() {
        if (folhasRecursoEspecial == null) {
            folhasRecursoEspecial = new StringFilter();
        }
        return folhasRecursoEspecial;
    }

    public void setFolhasRecursoEspecial(StringFilter folhasRecursoEspecial) {
        this.folhasRecursoEspecial = folhasRecursoEspecial;
    }

    public StringFilter getFolhasCienciaJulgamentoRecursoEspecial() {
        return folhasCienciaJulgamentoRecursoEspecial;
    }

    public StringFilter folhasCienciaJulgamentoRecursoEspecial() {
        if (folhasCienciaJulgamentoRecursoEspecial == null) {
            folhasCienciaJulgamentoRecursoEspecial = new StringFilter();
        }
        return folhasCienciaJulgamentoRecursoEspecial;
    }

    public void setFolhasCienciaJulgamentoRecursoEspecial(StringFilter folhasCienciaJulgamentoRecursoEspecial) {
        this.folhasCienciaJulgamentoRecursoEspecial = folhasCienciaJulgamentoRecursoEspecial;
    }

    public BooleanFilter getEmbargoRecursoEspecial() {
        return embargoRecursoEspecial;
    }

    public BooleanFilter embargoRecursoEspecial() {
        if (embargoRecursoEspecial == null) {
            embargoRecursoEspecial = new BooleanFilter();
        }
        return embargoRecursoEspecial;
    }

    public void setEmbargoRecursoEspecial(BooleanFilter embargoRecursoEspecial) {
        this.embargoRecursoEspecial = embargoRecursoEspecial;
    }

    public StringFilter getFolhasCiencia() {
        return folhasCiencia;
    }

    public StringFilter folhasCiencia() {
        if (folhasCiencia == null) {
            folhasCiencia = new StringFilter();
        }
        return folhasCiencia;
    }

    public void setFolhasCiencia(StringFilter folhasCiencia) {
        this.folhasCiencia = folhasCiencia;
    }

    public StringFilter getAgravoRespRe() {
        return agravoRespRe;
    }

    public StringFilter agravoRespRe() {
        if (agravoRespRe == null) {
            agravoRespRe = new StringFilter();
        }
        return agravoRespRe;
    }

    public void setAgravoRespRe(StringFilter agravoRespRe) {
        this.agravoRespRe = agravoRespRe;
    }

    public StringFilter getFolhasRespRe() {
        return folhasRespRe;
    }

    public StringFilter folhasRespRe() {
        if (folhasRespRe == null) {
            folhasRespRe = new StringFilter();
        }
        return folhasRespRe;
    }

    public void setFolhasRespRe(StringFilter folhasRespRe) {
        this.folhasRespRe = folhasRespRe;
    }

    public StringFilter getFolhasCienciaJulgamentoAgravoRespRe() {
        return folhasCienciaJulgamentoAgravoRespRe;
    }

    public StringFilter folhasCienciaJulgamentoAgravoRespRe() {
        if (folhasCienciaJulgamentoAgravoRespRe == null) {
            folhasCienciaJulgamentoAgravoRespRe = new StringFilter();
        }
        return folhasCienciaJulgamentoAgravoRespRe;
    }

    public void setFolhasCienciaJulgamentoAgravoRespRe(StringFilter folhasCienciaJulgamentoAgravoRespRe) {
        this.folhasCienciaJulgamentoAgravoRespRe = folhasCienciaJulgamentoAgravoRespRe;
    }

    public StringFilter getEmbargoRespRe() {
        return embargoRespRe;
    }

    public StringFilter embargoRespRe() {
        if (embargoRespRe == null) {
            embargoRespRe = new StringFilter();
        }
        return embargoRespRe;
    }

    public void setEmbargoRespRe(StringFilter embargoRespRe) {
        this.embargoRespRe = embargoRespRe;
    }

    public StringFilter getAgravoInterno() {
        return agravoInterno;
    }

    public StringFilter agravoInterno() {
        if (agravoInterno == null) {
            agravoInterno = new StringFilter();
        }
        return agravoInterno;
    }

    public void setAgravoInterno(StringFilter agravoInterno) {
        this.agravoInterno = agravoInterno;
    }

    public StringFilter getFolhasAgravoInterno() {
        return folhasAgravoInterno;
    }

    public StringFilter folhasAgravoInterno() {
        if (folhasAgravoInterno == null) {
            folhasAgravoInterno = new StringFilter();
        }
        return folhasAgravoInterno;
    }

    public void setFolhasAgravoInterno(StringFilter folhasAgravoInterno) {
        this.folhasAgravoInterno = folhasAgravoInterno;
    }

    public BooleanFilter getEmbargoRecursoAgravo() {
        return embargoRecursoAgravo;
    }

    public BooleanFilter embargoRecursoAgravo() {
        if (embargoRecursoAgravo == null) {
            embargoRecursoAgravo = new BooleanFilter();
        }
        return embargoRecursoAgravo;
    }

    public void setEmbargoRecursoAgravo(BooleanFilter embargoRecursoAgravo) {
        this.embargoRecursoAgravo = embargoRecursoAgravo;
    }

    public BooleanFilter getRecursoSTJ() {
        return recursoSTJ;
    }

    public BooleanFilter recursoSTJ() {
        if (recursoSTJ == null) {
            recursoSTJ = new BooleanFilter();
        }
        return recursoSTJ;
    }

    public void setRecursoSTJ(BooleanFilter recursoSTJ) {
        this.recursoSTJ = recursoSTJ;
    }

    public StringFilter getLinkRecursoSTJ() {
        return linkRecursoSTJ;
    }

    public StringFilter linkRecursoSTJ() {
        if (linkRecursoSTJ == null) {
            linkRecursoSTJ = new StringFilter();
        }
        return linkRecursoSTJ;
    }

    public void setLinkRecursoSTJ(StringFilter linkRecursoSTJ) {
        this.linkRecursoSTJ = linkRecursoSTJ;
    }

    public StringFilter getFolhasRecursoSTJ() {
        return folhasRecursoSTJ;
    }

    public StringFilter folhasRecursoSTJ() {
        if (folhasRecursoSTJ == null) {
            folhasRecursoSTJ = new StringFilter();
        }
        return folhasRecursoSTJ;
    }

    public void setFolhasRecursoSTJ(StringFilter folhasRecursoSTJ) {
        this.folhasRecursoSTJ = folhasRecursoSTJ;
    }

    public BooleanFilter getRecursoSTF() {
        return recursoSTF;
    }

    public BooleanFilter recursoSTF() {
        if (recursoSTF == null) {
            recursoSTF = new BooleanFilter();
        }
        return recursoSTF;
    }

    public void setRecursoSTF(BooleanFilter recursoSTF) {
        this.recursoSTF = recursoSTF;
    }

    public StringFilter getLinkRecursoSTF() {
        return linkRecursoSTF;
    }

    public StringFilter linkRecursoSTF() {
        if (linkRecursoSTF == null) {
            linkRecursoSTF = new StringFilter();
        }
        return linkRecursoSTF;
    }

    public void setLinkRecursoSTF(StringFilter linkRecursoSTF) {
        this.linkRecursoSTF = linkRecursoSTF;
    }

    public StringFilter getFolhasRecursoSTF() {
        return folhasRecursoSTF;
    }

    public StringFilter folhasRecursoSTF() {
        if (folhasRecursoSTF == null) {
            folhasRecursoSTF = new StringFilter();
        }
        return folhasRecursoSTF;
    }

    public void setFolhasRecursoSTF(StringFilter folhasRecursoSTF) {
        this.folhasRecursoSTF = folhasRecursoSTF;
    }

    public StringFilter getFolhasMemorialMPF() {
        return folhasMemorialMPF;
    }

    public StringFilter folhasMemorialMPF() {
        if (folhasMemorialMPF == null) {
            folhasMemorialMPF = new StringFilter();
        }
        return folhasMemorialMPF;
    }

    public void setFolhasMemorialMPF(StringFilter folhasMemorialMPF) {
        this.folhasMemorialMPF = folhasMemorialMPF;
    }

    public BooleanFilter getExecusaoProvisoria() {
        return execusaoProvisoria;
    }

    public BooleanFilter execusaoProvisoria() {
        if (execusaoProvisoria == null) {
            execusaoProvisoria = new BooleanFilter();
        }
        return execusaoProvisoria;
    }

    public void setExecusaoProvisoria(BooleanFilter execusaoProvisoria) {
        this.execusaoProvisoria = execusaoProvisoria;
    }

    public StringFilter getNumeracaoExecusaoProvisoria() {
        return numeracaoExecusaoProvisoria;
    }

    public StringFilter numeracaoExecusaoProvisoria() {
        if (numeracaoExecusaoProvisoria == null) {
            numeracaoExecusaoProvisoria = new StringFilter();
        }
        return numeracaoExecusaoProvisoria;
    }

    public void setNumeracaoExecusaoProvisoria(StringFilter numeracaoExecusaoProvisoria) {
        this.numeracaoExecusaoProvisoria = numeracaoExecusaoProvisoria;
    }

    public BooleanFilter getEnvolveEmpreendimento() {
        return envolveEmpreendimento;
    }

    public BooleanFilter envolveEmpreendimento() {
        if (envolveEmpreendimento == null) {
            envolveEmpreendimento = new BooleanFilter();
        }
        return envolveEmpreendimento;
    }

    public void setEnvolveEmpreendimento(BooleanFilter envolveEmpreendimento) {
        this.envolveEmpreendimento = envolveEmpreendimento;
    }

    public BooleanFilter getEnvolveExploracaoIlegal() {
        return envolveExploracaoIlegal;
    }

    public BooleanFilter envolveExploracaoIlegal() {
        if (envolveExploracaoIlegal == null) {
            envolveExploracaoIlegal = new BooleanFilter();
        }
        return envolveExploracaoIlegal;
    }

    public void setEnvolveExploracaoIlegal(BooleanFilter envolveExploracaoIlegal) {
        this.envolveExploracaoIlegal = envolveExploracaoIlegal;
    }

    public BooleanFilter getEnvolveTerraQuilombola() {
        return envolveTerraQuilombola;
    }

    public BooleanFilter envolveTerraQuilombola() {
        if (envolveTerraQuilombola == null) {
            envolveTerraQuilombola = new BooleanFilter();
        }
        return envolveTerraQuilombola;
    }

    public void setEnvolveTerraQuilombola(BooleanFilter envolveTerraQuilombola) {
        this.envolveTerraQuilombola = envolveTerraQuilombola;
    }

    public BooleanFilter getEnvolveTerraComunidadeTradicional() {
        return envolveTerraComunidadeTradicional;
    }

    public BooleanFilter envolveTerraComunidadeTradicional() {
        if (envolveTerraComunidadeTradicional == null) {
            envolveTerraComunidadeTradicional = new BooleanFilter();
        }
        return envolveTerraComunidadeTradicional;
    }

    public void setEnvolveTerraComunidadeTradicional(BooleanFilter envolveTerraComunidadeTradicional) {
        this.envolveTerraComunidadeTradicional = envolveTerraComunidadeTradicional;
    }

    public BooleanFilter getEnvolveTerraIndigena() {
        return envolveTerraIndigena;
    }

    public BooleanFilter envolveTerraIndigena() {
        if (envolveTerraIndigena == null) {
            envolveTerraIndigena = new BooleanFilter();
        }
        return envolveTerraIndigena;
    }

    public void setEnvolveTerraIndigena(BooleanFilter envolveTerraIndigena) {
        this.envolveTerraIndigena = envolveTerraIndigena;
    }

    public BigDecimalFilter getTamanhoArea() {
        return tamanhoArea;
    }

    public BigDecimalFilter tamanhoArea() {
        if (tamanhoArea == null) {
            tamanhoArea = new BigDecimalFilter();
        }
        return tamanhoArea;
    }

    public void setTamanhoArea(BigDecimalFilter tamanhoArea) {
        this.tamanhoArea = tamanhoArea;
    }

    public BigDecimalFilter getValorArea() {
        return valorArea;
    }

    public BigDecimalFilter valorArea() {
        if (valorArea == null) {
            valorArea = new BigDecimalFilter();
        }
        return valorArea;
    }

    public void setValorArea(BigDecimalFilter valorArea) {
        this.valorArea = valorArea;
    }

    public BooleanFilter getDadosGeograficosLitigioConflito() {
        return dadosGeograficosLitigioConflito;
    }

    public BooleanFilter dadosGeograficosLitigioConflito() {
        if (dadosGeograficosLitigioConflito == null) {
            dadosGeograficosLitigioConflito = new BooleanFilter();
        }
        return dadosGeograficosLitigioConflito;
    }

    public void setDadosGeograficosLitigioConflito(BooleanFilter dadosGeograficosLitigioConflito) {
        this.dadosGeograficosLitigioConflito = dadosGeograficosLitigioConflito;
    }

    public StringFilter getLatitude() {
        return latitude;
    }

    public StringFilter latitude() {
        if (latitude == null) {
            latitude = new StringFilter();
        }
        return latitude;
    }

    public void setLatitude(StringFilter latitude) {
        this.latitude = latitude;
    }

    public StringFilter getLongitude() {
        return longitude;
    }

    public StringFilter longitude() {
        if (longitude == null) {
            longitude = new StringFilter();
        }
        return longitude;
    }

    public void setLongitude(StringFilter longitude) {
        this.longitude = longitude;
    }

    public StringFilter getNumeroProcessoMPF() {
        return numeroProcessoMPF;
    }

    public StringFilter numeroProcessoMPF() {
        if (numeroProcessoMPF == null) {
            numeroProcessoMPF = new StringFilter();
        }
        return numeroProcessoMPF;
    }

    public void setNumeroProcessoMPF(StringFilter numeroProcessoMPF) {
        this.numeroProcessoMPF = numeroProcessoMPF;
    }

    public StringFilter getNumeroEmbargo() {
        return numeroEmbargo;
    }

    public StringFilter numeroEmbargo() {
        if (numeroEmbargo == null) {
            numeroEmbargo = new StringFilter();
        }
        return numeroEmbargo;
    }

    public void setNumeroEmbargo(StringFilter numeroEmbargo) {
        this.numeroEmbargo = numeroEmbargo;
    }

    public StringFilter getNumeroRecursoEspecial() {
        return numeroRecursoEspecial;
    }

    public StringFilter numeroRecursoEspecial() {
        if (numeroRecursoEspecial == null) {
            numeroRecursoEspecial = new StringFilter();
        }
        return numeroRecursoEspecial;
    }

    public void setNumeroRecursoEspecial(StringFilter numeroRecursoEspecial) {
        this.numeroRecursoEspecial = numeroRecursoEspecial;
    }

    public BooleanFilter getEnvolveGrandeProjeto() {
        return envolveGrandeProjeto;
    }

    public BooleanFilter envolveGrandeProjeto() {
        if (envolveGrandeProjeto == null) {
            envolveGrandeProjeto = new BooleanFilter();
        }
        return envolveGrandeProjeto;
    }

    public void setEnvolveGrandeProjeto(BooleanFilter envolveGrandeProjeto) {
        this.envolveGrandeProjeto = envolveGrandeProjeto;
    }

    public BooleanFilter getEnvolveUnidadeConservacao() {
        return envolveUnidadeConservacao;
    }

    public BooleanFilter envolveUnidadeConservacao() {
        if (envolveUnidadeConservacao == null) {
            envolveUnidadeConservacao = new BooleanFilter();
        }
        return envolveUnidadeConservacao;
    }

    public void setEnvolveUnidadeConservacao(BooleanFilter envolveUnidadeConservacao) {
        this.envolveUnidadeConservacao = envolveUnidadeConservacao;
    }

    public LongFilter getConcessaoLiminarId() {
        return concessaoLiminarId;
    }

    public LongFilter concessaoLiminarId() {
        if (concessaoLiminarId == null) {
            concessaoLiminarId = new LongFilter();
        }
        return concessaoLiminarId;
    }

    public void setConcessaoLiminarId(LongFilter concessaoLiminarId) {
        this.concessaoLiminarId = concessaoLiminarId;
    }

    public LongFilter getConcessaoLiminarCassadaId() {
        return concessaoLiminarCassadaId;
    }

    public LongFilter concessaoLiminarCassadaId() {
        if (concessaoLiminarCassadaId == null) {
            concessaoLiminarCassadaId = new LongFilter();
        }
        return concessaoLiminarCassadaId;
    }

    public void setConcessaoLiminarCassadaId(LongFilter concessaoLiminarCassadaId) {
        this.concessaoLiminarCassadaId = concessaoLiminarCassadaId;
    }

    public LongFilter getEmbargoDeclaracaoId() {
        return embargoDeclaracaoId;
    }

    public LongFilter embargoDeclaracaoId() {
        if (embargoDeclaracaoId == null) {
            embargoDeclaracaoId = new LongFilter();
        }
        return embargoDeclaracaoId;
    }

    public void setEmbargoDeclaracaoId(LongFilter embargoDeclaracaoId) {
        this.embargoDeclaracaoId = embargoDeclaracaoId;
    }

    public LongFilter getEmbargoDeclaracaoAgravoId() {
        return embargoDeclaracaoAgravoId;
    }

    public LongFilter embargoDeclaracaoAgravoId() {
        if (embargoDeclaracaoAgravoId == null) {
            embargoDeclaracaoAgravoId = new LongFilter();
        }
        return embargoDeclaracaoAgravoId;
    }

    public void setEmbargoDeclaracaoAgravoId(LongFilter embargoDeclaracaoAgravoId) {
        this.embargoDeclaracaoAgravoId = embargoDeclaracaoAgravoId;
    }

    public LongFilter getEmbargoRecursoEspecialId() {
        return embargoRecursoEspecialId;
    }

    public LongFilter embargoRecursoEspecialId() {
        if (embargoRecursoEspecialId == null) {
            embargoRecursoEspecialId = new LongFilter();
        }
        return embargoRecursoEspecialId;
    }

    public void setEmbargoRecursoEspecialId(LongFilter embargoRecursoEspecialId) {
        this.embargoRecursoEspecialId = embargoRecursoEspecialId;
    }

    public LongFilter getEmbargoRespReId() {
        return embargoRespReId;
    }

    public LongFilter embargoRespReId() {
        if (embargoRespReId == null) {
            embargoRespReId = new LongFilter();
        }
        return embargoRespReId;
    }

    public void setEmbargoRespReId(LongFilter embargoRespReId) {
        this.embargoRespReId = embargoRespReId;
    }

    public LongFilter getTipoDecisaoId() {
        return tipoDecisaoId;
    }

    public LongFilter tipoDecisaoId() {
        if (tipoDecisaoId == null) {
            tipoDecisaoId = new LongFilter();
        }
        return tipoDecisaoId;
    }

    public void setTipoDecisaoId(LongFilter tipoDecisaoId) {
        this.tipoDecisaoId = tipoDecisaoId;
    }

    public LongFilter getTipoEmpreendimentoId() {
        return tipoEmpreendimentoId;
    }

    public LongFilter tipoEmpreendimentoId() {
        if (tipoEmpreendimentoId == null) {
            tipoEmpreendimentoId = new LongFilter();
        }
        return tipoEmpreendimentoId;
    }

    public void setTipoEmpreendimentoId(LongFilter tipoEmpreendimentoId) {
        this.tipoEmpreendimentoId = tipoEmpreendimentoId;
    }

    public LongFilter getComarcaId() {
        return comarcaId;
    }

    public LongFilter comarcaId() {
        if (comarcaId == null) {
            comarcaId = new LongFilter();
        }
        return comarcaId;
    }

    public void setComarcaId(LongFilter comarcaId) {
        this.comarcaId = comarcaId;
    }

    public LongFilter getQuilomboId() {
        return quilomboId;
    }

    public LongFilter quilomboId() {
        if (quilomboId == null) {
            quilomboId = new LongFilter();
        }
        return quilomboId;
    }

    public void setQuilomboId(LongFilter quilomboId) {
        this.quilomboId = quilomboId;
    }

    public LongFilter getMunicipioId() {
        return municipioId;
    }

    public LongFilter municipioId() {
        if (municipioId == null) {
            municipioId = new LongFilter();
        }
        return municipioId;
    }

    public void setMunicipioId(LongFilter municipioId) {
        this.municipioId = municipioId;
    }

    public LongFilter getTerritorioId() {
        return territorioId;
    }

    public LongFilter territorioId() {
        if (territorioId == null) {
            territorioId = new LongFilter();
        }
        return territorioId;
    }

    public void setTerritorioId(LongFilter territorioId) {
        this.territorioId = territorioId;
    }

    public LongFilter getAtividadeExploracaoIlegalId() {
        return atividadeExploracaoIlegalId;
    }

    public LongFilter atividadeExploracaoIlegalId() {
        if (atividadeExploracaoIlegalId == null) {
            atividadeExploracaoIlegalId = new LongFilter();
        }
        return atividadeExploracaoIlegalId;
    }

    public void setAtividadeExploracaoIlegalId(LongFilter atividadeExploracaoIlegalId) {
        this.atividadeExploracaoIlegalId = atividadeExploracaoIlegalId;
    }

    public LongFilter getUnidadeConservacaoId() {
        return unidadeConservacaoId;
    }

    public LongFilter unidadeConservacaoId() {
        if (unidadeConservacaoId == null) {
            unidadeConservacaoId = new LongFilter();
        }
        return unidadeConservacaoId;
    }

    public void setUnidadeConservacaoId(LongFilter unidadeConservacaoId) {
        this.unidadeConservacaoId = unidadeConservacaoId;
    }

    public LongFilter getEnvolvidosConflitoLitigioId() {
        return envolvidosConflitoLitigioId;
    }

    public LongFilter envolvidosConflitoLitigioId() {
        if (envolvidosConflitoLitigioId == null) {
            envolvidosConflitoLitigioId = new LongFilter();
        }
        return envolvidosConflitoLitigioId;
    }

    public void setEnvolvidosConflitoLitigioId(LongFilter envolvidosConflitoLitigioId) {
        this.envolvidosConflitoLitigioId = envolvidosConflitoLitigioId;
    }

    public LongFilter getTerraIndigenaId() {
        return terraIndigenaId;
    }

    public LongFilter terraIndigenaId() {
        if (terraIndigenaId == null) {
            terraIndigenaId = new LongFilter();
        }
        return terraIndigenaId;
    }

    public void setTerraIndigenaId(LongFilter terraIndigenaId) {
        this.terraIndigenaId = terraIndigenaId;
    }

    public LongFilter getProcessoConflitoId() {
        return processoConflitoId;
    }

    public LongFilter processoConflitoId() {
        if (processoConflitoId == null) {
            processoConflitoId = new LongFilter();
        }
        return processoConflitoId;
    }

    public void setProcessoConflitoId(LongFilter processoConflitoId) {
        this.processoConflitoId = processoConflitoId;
    }

    public LongFilter getParteInteresssadaId() {
        return parteInteresssadaId;
    }

    public LongFilter parteInteresssadaId() {
        if (parteInteresssadaId == null) {
            parteInteresssadaId = new LongFilter();
        }
        return parteInteresssadaId;
    }

    public void setParteInteresssadaId(LongFilter parteInteresssadaId) {
        this.parteInteresssadaId = parteInteresssadaId;
    }

    public LongFilter getRelatorId() {
        return relatorId;
    }

    public LongFilter relatorId() {
        if (relatorId == null) {
            relatorId = new LongFilter();
        }
        return relatorId;
    }

    public void setRelatorId(LongFilter relatorId) {
        this.relatorId = relatorId;
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
        final ProcessoCriteria that = (ProcessoCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(numeroProcesso, that.numeroProcesso) &&
            Objects.equals(oficio, that.oficio) &&
            Objects.equals(linkUnico, that.linkUnico) &&
            Objects.equals(linkTrf, that.linkTrf) &&
            Objects.equals(secaoJudiciaria, that.secaoJudiciaria) &&
            Objects.equals(subsecaoJudiciaria, that.subsecaoJudiciaria) &&
            Objects.equals(turmaTrf1, that.turmaTrf1) &&
            Objects.equals(numeroProcessoAdministrativo, that.numeroProcessoAdministrativo) &&
            Objects.equals(numeroProcessoJudicialPrimeiraInstancia, that.numeroProcessoJudicialPrimeiraInstancia) &&
            Objects.equals(numeroProcessoJudicialPrimeiraInstanciaLink, that.numeroProcessoJudicialPrimeiraInstanciaLink) &&
            Objects.equals(parecer, that.parecer) &&
            Objects.equals(folhasProcessoConcessaoLiminar, that.folhasProcessoConcessaoLiminar) &&
            Objects.equals(folhasProcessoCassacao, that.folhasProcessoCassacao) &&
            Objects.equals(folhasParecer, that.folhasParecer) &&
            Objects.equals(folhasEmbargo, that.folhasEmbargo) &&
            Objects.equals(folhasCienciaJulgEmbargos, that.folhasCienciaJulgEmbargos) &&
            Objects.equals(apelacao, that.apelacao) &&
            Objects.equals(folhasApelacao, that.folhasApelacao) &&
            Objects.equals(folhasCienciaJulgApelacao, that.folhasCienciaJulgApelacao) &&
            Objects.equals(embargoDeclaracao, that.embargoDeclaracao) &&
            Objects.equals(folhasRecursoEspecial, that.folhasRecursoEspecial) &&
            Objects.equals(folhasCienciaJulgamentoRecursoEspecial, that.folhasCienciaJulgamentoRecursoEspecial) &&
            Objects.equals(embargoRecursoEspecial, that.embargoRecursoEspecial) &&
            Objects.equals(folhasCiencia, that.folhasCiencia) &&
            Objects.equals(agravoRespRe, that.agravoRespRe) &&
            Objects.equals(folhasRespRe, that.folhasRespRe) &&
            Objects.equals(folhasCienciaJulgamentoAgravoRespRe, that.folhasCienciaJulgamentoAgravoRespRe) &&
            Objects.equals(embargoRespRe, that.embargoRespRe) &&
            Objects.equals(agravoInterno, that.agravoInterno) &&
            Objects.equals(folhasAgravoInterno, that.folhasAgravoInterno) &&
            Objects.equals(embargoRecursoAgravo, that.embargoRecursoAgravo) &&
            Objects.equals(recursoSTJ, that.recursoSTJ) &&
            Objects.equals(linkRecursoSTJ, that.linkRecursoSTJ) &&
            Objects.equals(folhasRecursoSTJ, that.folhasRecursoSTJ) &&
            Objects.equals(recursoSTF, that.recursoSTF) &&
            Objects.equals(linkRecursoSTF, that.linkRecursoSTF) &&
            Objects.equals(folhasRecursoSTF, that.folhasRecursoSTF) &&
            Objects.equals(folhasMemorialMPF, that.folhasMemorialMPF) &&
            Objects.equals(execusaoProvisoria, that.execusaoProvisoria) &&
            Objects.equals(numeracaoExecusaoProvisoria, that.numeracaoExecusaoProvisoria) &&
            Objects.equals(envolveEmpreendimento, that.envolveEmpreendimento) &&
            Objects.equals(envolveExploracaoIlegal, that.envolveExploracaoIlegal) &&
            Objects.equals(envolveTerraQuilombola, that.envolveTerraQuilombola) &&
            Objects.equals(envolveTerraComunidadeTradicional, that.envolveTerraComunidadeTradicional) &&
            Objects.equals(envolveTerraIndigena, that.envolveTerraIndigena) &&
            Objects.equals(tamanhoArea, that.tamanhoArea) &&
            Objects.equals(valorArea, that.valorArea) &&
            Objects.equals(dadosGeograficosLitigioConflito, that.dadosGeograficosLitigioConflito) &&
            Objects.equals(latitude, that.latitude) &&
            Objects.equals(longitude, that.longitude) &&
            Objects.equals(numeroProcessoMPF, that.numeroProcessoMPF) &&
            Objects.equals(numeroEmbargo, that.numeroEmbargo) &&
            Objects.equals(numeroRecursoEspecial, that.numeroRecursoEspecial) &&
            Objects.equals(envolveGrandeProjeto, that.envolveGrandeProjeto) &&
            Objects.equals(envolveUnidadeConservacao, that.envolveUnidadeConservacao) &&
            Objects.equals(concessaoLiminarId, that.concessaoLiminarId) &&
            Objects.equals(concessaoLiminarCassadaId, that.concessaoLiminarCassadaId) &&
            Objects.equals(embargoDeclaracaoId, that.embargoDeclaracaoId) &&
            Objects.equals(embargoDeclaracaoAgravoId, that.embargoDeclaracaoAgravoId) &&
            Objects.equals(embargoRecursoEspecialId, that.embargoRecursoEspecialId) &&
            Objects.equals(embargoRespReId, that.embargoRespReId) &&
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
            Objects.equals(problemaJuridicoId, that.problemaJuridicoId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            numeroProcesso,
            oficio,
            linkUnico,
            linkTrf,
            secaoJudiciaria,
            subsecaoJudiciaria,
            turmaTrf1,
            numeroProcessoAdministrativo,
            numeroProcessoJudicialPrimeiraInstancia,
            numeroProcessoJudicialPrimeiraInstanciaLink,
            parecer,
            folhasProcessoConcessaoLiminar,
            folhasProcessoCassacao,
            folhasParecer,
            folhasEmbargo,
            folhasCienciaJulgEmbargos,
            apelacao,
            folhasApelacao,
            folhasCienciaJulgApelacao,
            embargoDeclaracao,
            folhasRecursoEspecial,
            folhasCienciaJulgamentoRecursoEspecial,
            embargoRecursoEspecial,
            folhasCiencia,
            agravoRespRe,
            folhasRespRe,
            folhasCienciaJulgamentoAgravoRespRe,
            embargoRespRe,
            agravoInterno,
            folhasAgravoInterno,
            embargoRecursoAgravo,
            recursoSTJ,
            linkRecursoSTJ,
            folhasRecursoSTJ,
            recursoSTF,
            linkRecursoSTF,
            folhasRecursoSTF,
            folhasMemorialMPF,
            execusaoProvisoria,
            numeracaoExecusaoProvisoria,
            envolveEmpreendimento,
            envolveExploracaoIlegal,
            envolveTerraQuilombola,
            envolveTerraComunidadeTradicional,
            envolveTerraIndigena,
            tamanhoArea,
            valorArea,
            dadosGeograficosLitigioConflito,
            latitude,
            longitude,
            numeroProcessoMPF,
            numeroEmbargo,
            numeroRecursoEspecial,
            envolveGrandeProjeto,
            envolveUnidadeConservacao,
            concessaoLiminarId,
            concessaoLiminarCassadaId,
            embargoDeclaracaoId,
            embargoDeclaracaoAgravoId,
            embargoRecursoEspecialId,
            embargoRespReId,
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
            (numeroProcesso != null ? "numeroProcesso=" + numeroProcesso + ", " : "") +
            (oficio != null ? "oficio=" + oficio + ", " : "") +
            (linkUnico != null ? "linkUnico=" + linkUnico + ", " : "") +
            (linkTrf != null ? "linkTrf=" + linkTrf + ", " : "") +
            (secaoJudiciaria != null ? "secaoJudiciaria=" + secaoJudiciaria + ", " : "") +
            (subsecaoJudiciaria != null ? "subsecaoJudiciaria=" + subsecaoJudiciaria + ", " : "") +
            (turmaTrf1 != null ? "turmaTrf1=" + turmaTrf1 + ", " : "") +
            (numeroProcessoAdministrativo != null ? "numeroProcessoAdministrativo=" + numeroProcessoAdministrativo + ", " : "") +
            (numeroProcessoJudicialPrimeiraInstancia != null ? "numeroProcessoJudicialPrimeiraInstancia=" + numeroProcessoJudicialPrimeiraInstancia + ", " : "") +
            (numeroProcessoJudicialPrimeiraInstanciaLink != null ? "numeroProcessoJudicialPrimeiraInstanciaLink=" + numeroProcessoJudicialPrimeiraInstanciaLink + ", " : "") +
            (parecer != null ? "parecer=" + parecer + ", " : "") +
            (folhasProcessoConcessaoLiminar != null ? "folhasProcessoConcessaoLiminar=" + folhasProcessoConcessaoLiminar + ", " : "") +
            (folhasProcessoCassacao != null ? "folhasProcessoCassacao=" + folhasProcessoCassacao + ", " : "") +
            (folhasParecer != null ? "folhasParecer=" + folhasParecer + ", " : "") +
            (folhasEmbargo != null ? "folhasEmbargo=" + folhasEmbargo + ", " : "") +
            (folhasCienciaJulgEmbargos != null ? "folhasCienciaJulgEmbargos=" + folhasCienciaJulgEmbargos + ", " : "") +
            (apelacao != null ? "apelacao=" + apelacao + ", " : "") +
            (folhasApelacao != null ? "folhasApelacao=" + folhasApelacao + ", " : "") +
            (folhasCienciaJulgApelacao != null ? "folhasCienciaJulgApelacao=" + folhasCienciaJulgApelacao + ", " : "") +
            (embargoDeclaracao != null ? "embargoDeclaracao=" + embargoDeclaracao + ", " : "") +
            (folhasRecursoEspecial != null ? "folhasRecursoEspecial=" + folhasRecursoEspecial + ", " : "") +
            (folhasCienciaJulgamentoRecursoEspecial != null ? "folhasCienciaJulgamentoRecursoEspecial=" + folhasCienciaJulgamentoRecursoEspecial + ", " : "") +
            (embargoRecursoEspecial != null ? "embargoRecursoEspecial=" + embargoRecursoEspecial + ", " : "") +
            (folhasCiencia != null ? "folhasCiencia=" + folhasCiencia + ", " : "") +
            (agravoRespRe != null ? "agravoRespRe=" + agravoRespRe + ", " : "") +
            (folhasRespRe != null ? "folhasRespRe=" + folhasRespRe + ", " : "") +
            (folhasCienciaJulgamentoAgravoRespRe != null ? "folhasCienciaJulgamentoAgravoRespRe=" + folhasCienciaJulgamentoAgravoRespRe + ", " : "") +
            (embargoRespRe != null ? "embargoRespRe=" + embargoRespRe + ", " : "") +
            (agravoInterno != null ? "agravoInterno=" + agravoInterno + ", " : "") +
            (folhasAgravoInterno != null ? "folhasAgravoInterno=" + folhasAgravoInterno + ", " : "") +
            (embargoRecursoAgravo != null ? "embargoRecursoAgravo=" + embargoRecursoAgravo + ", " : "") +
            (recursoSTJ != null ? "recursoSTJ=" + recursoSTJ + ", " : "") +
            (linkRecursoSTJ != null ? "linkRecursoSTJ=" + linkRecursoSTJ + ", " : "") +
            (folhasRecursoSTJ != null ? "folhasRecursoSTJ=" + folhasRecursoSTJ + ", " : "") +
            (recursoSTF != null ? "recursoSTF=" + recursoSTF + ", " : "") +
            (linkRecursoSTF != null ? "linkRecursoSTF=" + linkRecursoSTF + ", " : "") +
            (folhasRecursoSTF != null ? "folhasRecursoSTF=" + folhasRecursoSTF + ", " : "") +
            (folhasMemorialMPF != null ? "folhasMemorialMPF=" + folhasMemorialMPF + ", " : "") +
            (execusaoProvisoria != null ? "execusaoProvisoria=" + execusaoProvisoria + ", " : "") +
            (numeracaoExecusaoProvisoria != null ? "numeracaoExecusaoProvisoria=" + numeracaoExecusaoProvisoria + ", " : "") +
            (envolveEmpreendimento != null ? "envolveEmpreendimento=" + envolveEmpreendimento + ", " : "") +
            (envolveExploracaoIlegal != null ? "envolveExploracaoIlegal=" + envolveExploracaoIlegal + ", " : "") +
            (envolveTerraQuilombola != null ? "envolveTerraQuilombola=" + envolveTerraQuilombola + ", " : "") +
            (envolveTerraComunidadeTradicional != null ? "envolveTerraComunidadeTradicional=" + envolveTerraComunidadeTradicional + ", " : "") +
            (envolveTerraIndigena != null ? "envolveTerraIndigena=" + envolveTerraIndigena + ", " : "") +
            (tamanhoArea != null ? "tamanhoArea=" + tamanhoArea + ", " : "") +
            (valorArea != null ? "valorArea=" + valorArea + ", " : "") +
            (dadosGeograficosLitigioConflito != null ? "dadosGeograficosLitigioConflito=" + dadosGeograficosLitigioConflito + ", " : "") +
            (latitude != null ? "latitude=" + latitude + ", " : "") +
            (longitude != null ? "longitude=" + longitude + ", " : "") +
            (numeroProcessoMPF != null ? "numeroProcessoMPF=" + numeroProcessoMPF + ", " : "") +
            (numeroEmbargo != null ? "numeroEmbargo=" + numeroEmbargo + ", " : "") +
            (numeroRecursoEspecial != null ? "numeroRecursoEspecial=" + numeroRecursoEspecial + ", " : "") +
            (envolveGrandeProjeto != null ? "envolveGrandeProjeto=" + envolveGrandeProjeto + ", " : "") +
            (envolveUnidadeConservacao != null ? "envolveUnidadeConservacao=" + envolveUnidadeConservacao + ", " : "") +
            (concessaoLiminarId != null ? "concessaoLiminarId=" + concessaoLiminarId + ", " : "") +
            (concessaoLiminarCassadaId != null ? "concessaoLiminarCassadaId=" + concessaoLiminarCassadaId + ", " : "") +
            (embargoDeclaracaoId != null ? "embargoDeclaracaoId=" + embargoDeclaracaoId + ", " : "") +
            (embargoDeclaracaoAgravoId != null ? "embargoDeclaracaoAgravoId=" + embargoDeclaracaoAgravoId + ", " : "") +
            (embargoRecursoEspecialId != null ? "embargoRecursoEspecialId=" + embargoRecursoEspecialId + ", " : "") +
            (embargoRespReId != null ? "embargoRespReId=" + embargoRespReId + ", " : "") +
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
