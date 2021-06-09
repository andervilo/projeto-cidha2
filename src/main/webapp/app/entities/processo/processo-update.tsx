import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { setFileData, byteSize, Translate, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { ITipoDecisao } from 'app/shared/model/tipo-decisao.model';
import { getEntities as getTipoDecisaos } from 'app/entities/tipo-decisao/tipo-decisao.reducer';
import { ITipoEmpreendimento } from 'app/shared/model/tipo-empreendimento.model';
import { getEntities as getTipoEmpreendimentos } from 'app/entities/tipo-empreendimento/tipo-empreendimento.reducer';
import { ISecaoJudiciaria } from 'app/shared/model/secao-judiciaria.model';
import { getEntities as getSecaoJudiciarias } from 'app/entities/secao-judiciaria/secao-judiciaria.reducer';
import { IComarca } from 'app/shared/model/comarca.model';
import { getEntities as getComarcas } from 'app/entities/comarca/comarca.reducer';
import { IMunicipio } from 'app/shared/model/municipio.model';
import { getEntities as getMunicipios } from 'app/entities/municipio/municipio.reducer';
import { ITerritorio } from 'app/shared/model/territorio.model';
import { getEntities as getTerritorios } from 'app/entities/territorio/territorio.reducer';
import { IAtividadeExploracaoIlegal } from 'app/shared/model/atividade-exploracao-ilegal.model';
import { getEntities as getAtividadeExploracaoIlegals } from 'app/entities/atividade-exploracao-ilegal/atividade-exploracao-ilegal.reducer';
import { IUnidadeConservacao } from 'app/shared/model/unidade-conservacao.model';
import { getEntities as getUnidadeConservacaos } from 'app/entities/unidade-conservacao/unidade-conservacao.reducer';
import { IEnvolvidosConflitoLitigio } from 'app/shared/model/envolvidos-conflito-litigio.model';
import { getEntities as getEnvolvidosConflitoLitigios } from 'app/entities/envolvidos-conflito-litigio/envolvidos-conflito-litigio.reducer';
import { ITerraIndigena } from 'app/shared/model/terra-indigena.model';
import { getEntities as getTerraIndigenas } from 'app/entities/terra-indigena/terra-indigena.reducer';
import { IProcessoConflito } from 'app/shared/model/processo-conflito.model';
import { getEntities as getProcessoConflitos } from 'app/entities/processo-conflito/processo-conflito.reducer';
import { IParteInteresssada } from 'app/shared/model/parte-interesssada.model';
import { getEntities as getParteInteresssadas } from 'app/entities/parte-interesssada/parte-interesssada.reducer';
import { IRelator } from 'app/shared/model/relator.model';
import { getEntities as getRelators } from 'app/entities/relator/relator.reducer';
import { IQuilombo } from 'app/shared/model/quilombo.model';
import { getEntities as getQuilombos } from 'app/entities/quilombo/quilombo.reducer';
import { IProblemaJuridico } from 'app/shared/model/problema-juridico.model';
import { getEntities as getProblemaJuridicos } from 'app/entities/problema-juridico/problema-juridico.reducer';
import { getEntity, updateEntity, createEntity, setBlob, reset } from './processo.reducer';
import { IProcesso } from 'app/shared/model/processo.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IProcessoUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ProcessoUpdate = (props: IProcessoUpdateProps) => {
  const [idscomarca, setIdscomarca] = useState([]);
  const [idsmunicipio, setIdsmunicipio] = useState([]);
  const [idsterritorio, setIdsterritorio] = useState([]);
  const [idsatividadeExploracaoIlegal, setIdsatividadeExploracaoIlegal] = useState([]);
  const [idsunidadeConservacao, setIdsunidadeConservacao] = useState([]);
  const [idsenvolvidosConflitoLitigio, setIdsenvolvidosConflitoLitigio] = useState([]);
  const [idsterraIndigena, setIdsterraIndigena] = useState([]);
  const [idsprocessoConflito, setIdsprocessoConflito] = useState([]);
  const [idsparteInteresssada, setIdsparteInteresssada] = useState([]);
  const [idsrelator, setIdsrelator] = useState([]);
  const [idsquilombo, setIdsquilombo] = useState([]);
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const {
    processoEntity,
    tipoDecisaos,
    tipoEmpreendimentos,
    secaoJudiciarias,
    comarcas,
    municipios,
    territorios,
    atividadeExploracaoIlegals,
    unidadeConservacaos,
    envolvidosConflitoLitigios,
    terraIndigenas,
    processoConflitos,
    parteInteresssadas,
    relators,
    quilombos,
    problemaJuridicos,
    loading,
    updating,
  } = props;

  const {
    assunto,
    numeroProcessoJudicialPrimeiraInstanciaObservacoes,
    concessaoLiminarObservacoes,
    acordaoEmbargo,
    acordaoApelacao,
    acordaoRecursoEspecial,
    acordaoAgravoRespRe,
    observacoes,
    recuperacaoEfetivaCumprimentoSentenca,
    recuperacaoEfetivaCumprimentoSentencaObservacoes,
    resumoFatos,
    tamanhoAreaObservacao,
    pautaApelacao,
    admissiblidade,
    linkReferencia,
  } = processoEntity;

  const handleClose = () => {
    props.history.push('/processo' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getTipoDecisaos();
    props.getTipoEmpreendimentos();
    props.getSecaoJudiciarias();
    props.getComarcas();
    props.getMunicipios();
    props.getTerritorios();
    props.getAtividadeExploracaoIlegals();
    props.getUnidadeConservacaos();
    props.getEnvolvidosConflitoLitigios();
    props.getTerraIndigenas();
    props.getProcessoConflitos();
    props.getParteInteresssadas();
    props.getRelators();
    props.getQuilombos();
    props.getProblemaJuridicos();
  }, []);

  const onBlobChange = (isAnImage, name) => event => {
    setFileData(event, (contentType, data) => props.setBlob(name, data, contentType), isAnImage);
  };

  const clearBlob = name => () => {
    props.setBlob(name, undefined, undefined);
  };

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...processoEntity,
        ...values,
        comarcas: mapIdList(values.comarcas),
        municipios: mapIdList(values.municipios),
        territorios: mapIdList(values.territorios),
        atividadeExploracaoIlegals: mapIdList(values.atividadeExploracaoIlegals),
        unidadeConservacaos: mapIdList(values.unidadeConservacaos),
        envolvidosConflitoLitigios: mapIdList(values.envolvidosConflitoLitigios),
        terraIndigenas: mapIdList(values.terraIndigenas),
        processoConflitos: mapIdList(values.processoConflitos),
        parteInteresssadas: mapIdList(values.parteInteresssadas),
        relators: mapIdList(values.relators),
        quilombos: mapIdList(values.quilombos),
        tipoDecisao: tipoDecisaos.find(it => it.id.toString() === values.tipoDecisaoId.toString()),
        tipoEmpreendimento: tipoEmpreendimentos.find(it => it.id.toString() === values.tipoEmpreendimentoId.toString()),
        secaoJudiciaria: secaoJudiciarias.find(it => it.id.toString() === values.secaoJudiciariaId.toString()),
      };

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="cidhaApp.processo.home.createOrEditLabel" data-cy="ProcessoCreateUpdateHeading">
            <Translate contentKey="cidhaApp.processo.home.createOrEditLabel">Create or edit a Processo</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : processoEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="processo-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="processo-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="numeroProcessoLabel" for="processo-numeroProcesso">
                  <Translate contentKey="cidhaApp.processo.numeroProcesso">Numero Processo</Translate>
                </Label>
                <AvField id="processo-numeroProcesso" data-cy="numeroProcesso" type="text" name="numeroProcesso" />
              </AvGroup>
              <AvGroup>
                <Label id="oficioLabel" for="processo-oficio">
                  <Translate contentKey="cidhaApp.processo.oficio">Oficio</Translate>
                </Label>
                <AvField id="processo-oficio" data-cy="oficio" type="text" name="oficio" />
              </AvGroup>
              <AvGroup>
                <Label id="assuntoLabel" for="processo-assunto">
                  <Translate contentKey="cidhaApp.processo.assunto">Assunto</Translate>
                </Label>
                <AvInput id="processo-assunto" data-cy="assunto" type="textarea" name="assunto" />
              </AvGroup>
              <AvGroup>
                <Label id="linkUnicoLabel" for="processo-linkUnico">
                  <Translate contentKey="cidhaApp.processo.linkUnico">Link Unico</Translate>
                </Label>
                <AvField id="processo-linkUnico" data-cy="linkUnico" type="text" name="linkUnico" />
              </AvGroup>
              <AvGroup>
                <Label id="linkTrfLabel" for="processo-linkTrf">
                  <Translate contentKey="cidhaApp.processo.linkTrf">Link Trf</Translate>
                </Label>
                <AvField id="processo-linkTrf" data-cy="linkTrf" type="text" name="linkTrf" />
              </AvGroup>
              <AvGroup>
                <Label id="turmaTrf1Label" for="processo-turmaTrf1">
                  <Translate contentKey="cidhaApp.processo.turmaTrf1">Turma Trf 1</Translate>
                </Label>
                <AvField id="processo-turmaTrf1" data-cy="turmaTrf1" type="text" name="turmaTrf1" />
              </AvGroup>
              <AvGroup>
                <Label id="numeroProcessoAdministrativoLabel" for="processo-numeroProcessoAdministrativo">
                  <Translate contentKey="cidhaApp.processo.numeroProcessoAdministrativo">Numero Processo Administrativo</Translate>
                </Label>
                <AvField
                  id="processo-numeroProcessoAdministrativo"
                  data-cy="numeroProcessoAdministrativo"
                  type="text"
                  name="numeroProcessoAdministrativo"
                />
              </AvGroup>
              <AvGroup>
                <Label id="numeroProcessoJudicialPrimeiraInstanciaLabel" for="processo-numeroProcessoJudicialPrimeiraInstancia">
                  <Translate contentKey="cidhaApp.processo.numeroProcessoJudicialPrimeiraInstancia">
                    Numero Processo Judicial Primeira Instancia
                  </Translate>
                </Label>
                <AvField
                  id="processo-numeroProcessoJudicialPrimeiraInstancia"
                  data-cy="numeroProcessoJudicialPrimeiraInstancia"
                  type="text"
                  name="numeroProcessoJudicialPrimeiraInstancia"
                />
              </AvGroup>
              <AvGroup>
                <Label id="numeroProcessoJudicialPrimeiraInstanciaLinkLabel" for="processo-numeroProcessoJudicialPrimeiraInstanciaLink">
                  <Translate contentKey="cidhaApp.processo.numeroProcessoJudicialPrimeiraInstanciaLink">
                    Numero Processo Judicial Primeira Instancia Link
                  </Translate>
                </Label>
                <AvField
                  id="processo-numeroProcessoJudicialPrimeiraInstanciaLink"
                  data-cy="numeroProcessoJudicialPrimeiraInstanciaLink"
                  type="text"
                  name="numeroProcessoJudicialPrimeiraInstanciaLink"
                />
              </AvGroup>
              <AvGroup>
                <Label
                  id="numeroProcessoJudicialPrimeiraInstanciaObservacoesLabel"
                  for="processo-numeroProcessoJudicialPrimeiraInstanciaObservacoes"
                >
                  <Translate contentKey="cidhaApp.processo.numeroProcessoJudicialPrimeiraInstanciaObservacoes">
                    Numero Processo Judicial Primeira Instancia Observacoes
                  </Translate>
                </Label>
                <AvInput
                  id="processo-numeroProcessoJudicialPrimeiraInstanciaObservacoes"
                  data-cy="numeroProcessoJudicialPrimeiraInstanciaObservacoes"
                  type="textarea"
                  name="numeroProcessoJudicialPrimeiraInstanciaObservacoes"
                />
              </AvGroup>
              <AvGroup check>
                <Label id="parecerLabel">
                  <AvInput id="processo-parecer" data-cy="parecer" type="checkbox" className="form-check-input" name="parecer" />
                  <Translate contentKey="cidhaApp.processo.parecer">Parecer</Translate>
                </Label>
              </AvGroup>
              <AvGroup>
                <Label id="folhasProcessoConcessaoLiminarLabel" for="processo-folhasProcessoConcessaoLiminar">
                  <Translate contentKey="cidhaApp.processo.folhasProcessoConcessaoLiminar">Folhas Processo Concessao Liminar</Translate>
                </Label>
                <AvField
                  id="processo-folhasProcessoConcessaoLiminar"
                  data-cy="folhasProcessoConcessaoLiminar"
                  type="text"
                  name="folhasProcessoConcessaoLiminar"
                />
              </AvGroup>
              <AvGroup>
                <Label id="concessaoLiminarObservacoesLabel" for="processo-concessaoLiminarObservacoes">
                  <Translate contentKey="cidhaApp.processo.concessaoLiminarObservacoes">Concessao Liminar Observacoes</Translate>
                </Label>
                <AvInput
                  id="processo-concessaoLiminarObservacoes"
                  data-cy="concessaoLiminarObservacoes"
                  type="textarea"
                  name="concessaoLiminarObservacoes"
                />
              </AvGroup>
              <AvGroup>
                <Label id="folhasProcessoCassacaoLabel" for="processo-folhasProcessoCassacao">
                  <Translate contentKey="cidhaApp.processo.folhasProcessoCassacao">Folhas Processo Cassacao</Translate>
                </Label>
                <AvField id="processo-folhasProcessoCassacao" data-cy="folhasProcessoCassacao" type="text" name="folhasProcessoCassacao" />
              </AvGroup>
              <AvGroup>
                <Label id="folhasParecerLabel" for="processo-folhasParecer">
                  <Translate contentKey="cidhaApp.processo.folhasParecer">Folhas Parecer</Translate>
                </Label>
                <AvField id="processo-folhasParecer" data-cy="folhasParecer" type="text" name="folhasParecer" />
              </AvGroup>
              <AvGroup>
                <Label id="folhasEmbargoLabel" for="processo-folhasEmbargo">
                  <Translate contentKey="cidhaApp.processo.folhasEmbargo">Folhas Embargo</Translate>
                </Label>
                <AvField id="processo-folhasEmbargo" data-cy="folhasEmbargo" type="text" name="folhasEmbargo" />
              </AvGroup>
              <AvGroup>
                <Label id="acordaoEmbargoLabel" for="processo-acordaoEmbargo">
                  <Translate contentKey="cidhaApp.processo.acordaoEmbargo">Acordao Embargo</Translate>
                </Label>
                <AvInput id="processo-acordaoEmbargo" data-cy="acordaoEmbargo" type="textarea" name="acordaoEmbargo" />
              </AvGroup>
              <AvGroup>
                <Label id="folhasCienciaJulgEmbargosLabel" for="processo-folhasCienciaJulgEmbargos">
                  <Translate contentKey="cidhaApp.processo.folhasCienciaJulgEmbargos">Folhas Ciencia Julg Embargos</Translate>
                </Label>
                <AvField
                  id="processo-folhasCienciaJulgEmbargos"
                  data-cy="folhasCienciaJulgEmbargos"
                  type="text"
                  name="folhasCienciaJulgEmbargos"
                />
              </AvGroup>
              <AvGroup>
                <Label id="apelacaoLabel" for="processo-apelacao">
                  <Translate contentKey="cidhaApp.processo.apelacao">Apelacao</Translate>
                </Label>
                <AvField id="processo-apelacao" data-cy="apelacao" type="text" name="apelacao" />
              </AvGroup>
              <AvGroup>
                <Label id="folhasApelacaoLabel" for="processo-folhasApelacao">
                  <Translate contentKey="cidhaApp.processo.folhasApelacao">Folhas Apelacao</Translate>
                </Label>
                <AvField id="processo-folhasApelacao" data-cy="folhasApelacao" type="text" name="folhasApelacao" />
              </AvGroup>
              <AvGroup>
                <Label id="acordaoApelacaoLabel" for="processo-acordaoApelacao">
                  <Translate contentKey="cidhaApp.processo.acordaoApelacao">Acordao Apelacao</Translate>
                </Label>
                <AvInput id="processo-acordaoApelacao" data-cy="acordaoApelacao" type="textarea" name="acordaoApelacao" />
              </AvGroup>
              <AvGroup>
                <Label id="folhasCienciaJulgApelacaoLabel" for="processo-folhasCienciaJulgApelacao">
                  <Translate contentKey="cidhaApp.processo.folhasCienciaJulgApelacao">Folhas Ciencia Julg Apelacao</Translate>
                </Label>
                <AvField
                  id="processo-folhasCienciaJulgApelacao"
                  data-cy="folhasCienciaJulgApelacao"
                  type="text"
                  name="folhasCienciaJulgApelacao"
                />
              </AvGroup>
              <AvGroup check>
                <Label id="embargoDeclaracaoLabel">
                  <AvInput
                    id="processo-embargoDeclaracao"
                    data-cy="embargoDeclaracao"
                    type="checkbox"
                    className="form-check-input"
                    name="embargoDeclaracao"
                  />
                  <Translate contentKey="cidhaApp.processo.embargoDeclaracao">Embargo Declaracao</Translate>
                </Label>
              </AvGroup>
              <AvGroup check>
                <Label id="embargoRecursoExtraordinarioLabel">
                  <AvInput
                    id="processo-embargoRecursoExtraordinario"
                    data-cy="embargoRecursoExtraordinario"
                    type="checkbox"
                    className="form-check-input"
                    name="embargoRecursoExtraordinario"
                  />
                  <Translate contentKey="cidhaApp.processo.embargoRecursoExtraordinario">Embargo Recurso Extraordinario</Translate>
                </Label>
              </AvGroup>
              <AvGroup>
                <Label id="folhasRecursoEspecialLabel" for="processo-folhasRecursoEspecial">
                  <Translate contentKey="cidhaApp.processo.folhasRecursoEspecial">Folhas Recurso Especial</Translate>
                </Label>
                <AvField id="processo-folhasRecursoEspecial" data-cy="folhasRecursoEspecial" type="text" name="folhasRecursoEspecial" />
              </AvGroup>
              <AvGroup>
                <Label id="acordaoRecursoEspecialLabel" for="processo-acordaoRecursoEspecial">
                  <Translate contentKey="cidhaApp.processo.acordaoRecursoEspecial">Acordao Recurso Especial</Translate>
                </Label>
                <AvInput
                  id="processo-acordaoRecursoEspecial"
                  data-cy="acordaoRecursoEspecial"
                  type="textarea"
                  name="acordaoRecursoEspecial"
                />
              </AvGroup>
              <AvGroup>
                <Label id="folhasCienciaJulgamentoRecursoEspecialLabel" for="processo-folhasCienciaJulgamentoRecursoEspecial">
                  <Translate contentKey="cidhaApp.processo.folhasCienciaJulgamentoRecursoEspecial">
                    Folhas Ciencia Julgamento Recurso Especial
                  </Translate>
                </Label>
                <AvField
                  id="processo-folhasCienciaJulgamentoRecursoEspecial"
                  data-cy="folhasCienciaJulgamentoRecursoEspecial"
                  type="text"
                  name="folhasCienciaJulgamentoRecursoEspecial"
                />
              </AvGroup>
              <AvGroup check>
                <Label id="embargoRecursoEspecialLabel">
                  <AvInput
                    id="processo-embargoRecursoEspecial"
                    data-cy="embargoRecursoEspecial"
                    type="checkbox"
                    className="form-check-input"
                    name="embargoRecursoEspecial"
                  />
                  <Translate contentKey="cidhaApp.processo.embargoRecursoEspecial">Embargo Recurso Especial</Translate>
                </Label>
              </AvGroup>
              <AvGroup>
                <Label id="folhasCienciaLabel" for="processo-folhasCiencia">
                  <Translate contentKey="cidhaApp.processo.folhasCiencia">Folhas Ciencia</Translate>
                </Label>
                <AvField id="processo-folhasCiencia" data-cy="folhasCiencia" type="text" name="folhasCiencia" />
              </AvGroup>
              <AvGroup>
                <Label id="agravoRespReLabel" for="processo-agravoRespRe">
                  <Translate contentKey="cidhaApp.processo.agravoRespRe">Agravo Resp Re</Translate>
                </Label>
                <AvField id="processo-agravoRespRe" data-cy="agravoRespRe" type="text" name="agravoRespRe" />
              </AvGroup>
              <AvGroup>
                <Label id="folhasRespReLabel" for="processo-folhasRespRe">
                  <Translate contentKey="cidhaApp.processo.folhasRespRe">Folhas Resp Re</Translate>
                </Label>
                <AvField id="processo-folhasRespRe" data-cy="folhasRespRe" type="text" name="folhasRespRe" />
              </AvGroup>
              <AvGroup>
                <Label id="acordaoAgravoRespReLabel" for="processo-acordaoAgravoRespRe">
                  <Translate contentKey="cidhaApp.processo.acordaoAgravoRespRe">Acordao Agravo Resp Re</Translate>
                </Label>
                <AvInput id="processo-acordaoAgravoRespRe" data-cy="acordaoAgravoRespRe" type="textarea" name="acordaoAgravoRespRe" />
              </AvGroup>
              <AvGroup>
                <Label id="folhasCienciaJulgamentoAgravoRespReLabel" for="processo-folhasCienciaJulgamentoAgravoRespRe">
                  <Translate contentKey="cidhaApp.processo.folhasCienciaJulgamentoAgravoRespRe">
                    Folhas Ciencia Julgamento Agravo Resp Re
                  </Translate>
                </Label>
                <AvField
                  id="processo-folhasCienciaJulgamentoAgravoRespRe"
                  data-cy="folhasCienciaJulgamentoAgravoRespRe"
                  type="text"
                  name="folhasCienciaJulgamentoAgravoRespRe"
                />
              </AvGroup>
              <AvGroup>
                <Label id="embargoRespReLabel" for="processo-embargoRespRe">
                  <Translate contentKey="cidhaApp.processo.embargoRespRe">Embargo Resp Re</Translate>
                </Label>
                <AvField id="processo-embargoRespRe" data-cy="embargoRespRe" type="text" name="embargoRespRe" />
              </AvGroup>
              <AvGroup>
                <Label id="agravoInternoLabel" for="processo-agravoInterno">
                  <Translate contentKey="cidhaApp.processo.agravoInterno">Agravo Interno</Translate>
                </Label>
                <AvField id="processo-agravoInterno" data-cy="agravoInterno" type="text" name="agravoInterno" />
              </AvGroup>
              <AvGroup>
                <Label id="folhasAgravoInternoLabel" for="processo-folhasAgravoInterno">
                  <Translate contentKey="cidhaApp.processo.folhasAgravoInterno">Folhas Agravo Interno</Translate>
                </Label>
                <AvField id="processo-folhasAgravoInterno" data-cy="folhasAgravoInterno" type="text" name="folhasAgravoInterno" />
              </AvGroup>
              <AvGroup check>
                <Label id="embargoRecursoAgravoLabel">
                  <AvInput
                    id="processo-embargoRecursoAgravo"
                    data-cy="embargoRecursoAgravo"
                    type="checkbox"
                    className="form-check-input"
                    name="embargoRecursoAgravo"
                  />
                  <Translate contentKey="cidhaApp.processo.embargoRecursoAgravo">Embargo Recurso Agravo</Translate>
                </Label>
              </AvGroup>
              <AvGroup>
                <Label id="observacoesLabel" for="processo-observacoes">
                  <Translate contentKey="cidhaApp.processo.observacoes">Observacoes</Translate>
                </Label>
                <AvInput id="processo-observacoes" data-cy="observacoes" type="textarea" name="observacoes" />
              </AvGroup>
              <AvGroup check>
                <Label id="recursoSTJLabel">
                  <AvInput id="processo-recursoSTJ" data-cy="recursoSTJ" type="checkbox" className="form-check-input" name="recursoSTJ" />
                  <Translate contentKey="cidhaApp.processo.recursoSTJ">Recurso STJ</Translate>
                </Label>
              </AvGroup>
              <AvGroup>
                <Label id="linkRecursoSTJLabel" for="processo-linkRecursoSTJ">
                  <Translate contentKey="cidhaApp.processo.linkRecursoSTJ">Link Recurso STJ</Translate>
                </Label>
                <AvField id="processo-linkRecursoSTJ" data-cy="linkRecursoSTJ" type="text" name="linkRecursoSTJ" />
              </AvGroup>
              <AvGroup>
                <Label id="folhasRecursoSTJLabel" for="processo-folhasRecursoSTJ">
                  <Translate contentKey="cidhaApp.processo.folhasRecursoSTJ">Folhas Recurso STJ</Translate>
                </Label>
                <AvField id="processo-folhasRecursoSTJ" data-cy="folhasRecursoSTJ" type="text" name="folhasRecursoSTJ" />
              </AvGroup>
              <AvGroup check>
                <Label id="recursoSTFLabel">
                  <AvInput id="processo-recursoSTF" data-cy="recursoSTF" type="checkbox" className="form-check-input" name="recursoSTF" />
                  <Translate contentKey="cidhaApp.processo.recursoSTF">Recurso STF</Translate>
                </Label>
              </AvGroup>
              <AvGroup>
                <Label id="linkRecursoSTFLabel" for="processo-linkRecursoSTF">
                  <Translate contentKey="cidhaApp.processo.linkRecursoSTF">Link Recurso STF</Translate>
                </Label>
                <AvField id="processo-linkRecursoSTF" data-cy="linkRecursoSTF" type="text" name="linkRecursoSTF" />
              </AvGroup>
              <AvGroup>
                <Label id="folhasRecursoSTFLabel" for="processo-folhasRecursoSTF">
                  <Translate contentKey="cidhaApp.processo.folhasRecursoSTF">Folhas Recurso STF</Translate>
                </Label>
                <AvField id="processo-folhasRecursoSTF" data-cy="folhasRecursoSTF" type="text" name="folhasRecursoSTF" />
              </AvGroup>
              <AvGroup>
                <Label id="folhasMemorialMPFLabel" for="processo-folhasMemorialMPF">
                  <Translate contentKey="cidhaApp.processo.folhasMemorialMPF">Folhas Memorial MPF</Translate>
                </Label>
                <AvField id="processo-folhasMemorialMPF" data-cy="folhasMemorialMPF" type="text" name="folhasMemorialMPF" />
              </AvGroup>
              <AvGroup check>
                <Label id="execusaoProvisoriaLabel">
                  <AvInput
                    id="processo-execusaoProvisoria"
                    data-cy="execusaoProvisoria"
                    type="checkbox"
                    className="form-check-input"
                    name="execusaoProvisoria"
                  />
                  <Translate contentKey="cidhaApp.processo.execusaoProvisoria">Execusao Provisoria</Translate>
                </Label>
              </AvGroup>
              <AvGroup>
                <Label id="numeracaoExecusaoProvisoriaLabel" for="processo-numeracaoExecusaoProvisoria">
                  <Translate contentKey="cidhaApp.processo.numeracaoExecusaoProvisoria">Numeracao Execusao Provisoria</Translate>
                </Label>
                <AvField
                  id="processo-numeracaoExecusaoProvisoria"
                  data-cy="numeracaoExecusaoProvisoria"
                  type="text"
                  name="numeracaoExecusaoProvisoria"
                />
              </AvGroup>
              <AvGroup>
                <Label id="recuperacaoEfetivaCumprimentoSentencaLabel" for="processo-recuperacaoEfetivaCumprimentoSentenca">
                  <Translate contentKey="cidhaApp.processo.recuperacaoEfetivaCumprimentoSentenca">
                    Recuperacao Efetiva Cumprimento Sentenca
                  </Translate>
                </Label>
                <AvInput
                  id="processo-recuperacaoEfetivaCumprimentoSentenca"
                  data-cy="recuperacaoEfetivaCumprimentoSentenca"
                  type="textarea"
                  name="recuperacaoEfetivaCumprimentoSentenca"
                />
              </AvGroup>
              <AvGroup>
                <Label
                  id="recuperacaoEfetivaCumprimentoSentencaObservacoesLabel"
                  for="processo-recuperacaoEfetivaCumprimentoSentencaObservacoes"
                >
                  <Translate contentKey="cidhaApp.processo.recuperacaoEfetivaCumprimentoSentencaObservacoes">
                    Recuperacao Efetiva Cumprimento Sentenca Observacoes
                  </Translate>
                </Label>
                <AvInput
                  id="processo-recuperacaoEfetivaCumprimentoSentencaObservacoes"
                  data-cy="recuperacaoEfetivaCumprimentoSentencaObservacoes"
                  type="textarea"
                  name="recuperacaoEfetivaCumprimentoSentencaObservacoes"
                />
              </AvGroup>
              <AvGroup check>
                <Label id="envolveEmpreendimentoLabel">
                  <AvInput
                    id="processo-envolveEmpreendimento"
                    data-cy="envolveEmpreendimento"
                    type="checkbox"
                    className="form-check-input"
                    name="envolveEmpreendimento"
                  />
                  <Translate contentKey="cidhaApp.processo.envolveEmpreendimento">Envolve Empreendimento</Translate>
                </Label>
              </AvGroup>
              <AvGroup check>
                <Label id="envolveExploracaoIlegalLabel">
                  <AvInput
                    id="processo-envolveExploracaoIlegal"
                    data-cy="envolveExploracaoIlegal"
                    type="checkbox"
                    className="form-check-input"
                    name="envolveExploracaoIlegal"
                  />
                  <Translate contentKey="cidhaApp.processo.envolveExploracaoIlegal">Envolve Exploracao Ilegal</Translate>
                </Label>
              </AvGroup>
              <AvGroup check>
                <Label id="envolveTerraQuilombolaLabel">
                  <AvInput
                    id="processo-envolveTerraQuilombola"
                    data-cy="envolveTerraQuilombola"
                    type="checkbox"
                    className="form-check-input"
                    name="envolveTerraQuilombola"
                  />
                  <Translate contentKey="cidhaApp.processo.envolveTerraQuilombola">Envolve Terra Quilombola</Translate>
                </Label>
              </AvGroup>
              <AvGroup check>
                <Label id="envolveTerraComunidadeTradicionalLabel">
                  <AvInput
                    id="processo-envolveTerraComunidadeTradicional"
                    data-cy="envolveTerraComunidadeTradicional"
                    type="checkbox"
                    className="form-check-input"
                    name="envolveTerraComunidadeTradicional"
                  />
                  <Translate contentKey="cidhaApp.processo.envolveTerraComunidadeTradicional">
                    Envolve Terra Comunidade Tradicional
                  </Translate>
                </Label>
              </AvGroup>
              <AvGroup check>
                <Label id="envolveTerraIndigenaLabel">
                  <AvInput
                    id="processo-envolveTerraIndigena"
                    data-cy="envolveTerraIndigena"
                    type="checkbox"
                    className="form-check-input"
                    name="envolveTerraIndigena"
                  />
                  <Translate contentKey="cidhaApp.processo.envolveTerraIndigena">Envolve Terra Indigena</Translate>
                </Label>
              </AvGroup>
              <AvGroup>
                <Label id="resumoFatosLabel" for="processo-resumoFatos">
                  <Translate contentKey="cidhaApp.processo.resumoFatos">Resumo Fatos</Translate>
                </Label>
                <AvInput id="processo-resumoFatos" data-cy="resumoFatos" type="textarea" name="resumoFatos" />
              </AvGroup>
              <AvGroup>
                <Label id="tamanhoAreaLabel" for="processo-tamanhoArea">
                  <Translate contentKey="cidhaApp.processo.tamanhoArea">Tamanho Area</Translate>
                </Label>
                <AvField id="processo-tamanhoArea" data-cy="tamanhoArea" type="text" name="tamanhoArea" />
              </AvGroup>
              <AvGroup>
                <Label id="valorAreaLabel" for="processo-valorArea">
                  <Translate contentKey="cidhaApp.processo.valorArea">Valor Area</Translate>
                </Label>
                <AvField id="processo-valorArea" data-cy="valorArea" type="text" name="valorArea" />
              </AvGroup>
              <AvGroup>
                <Label id="tamanhoAreaObservacaoLabel" for="processo-tamanhoAreaObservacao">
                  <Translate contentKey="cidhaApp.processo.tamanhoAreaObservacao">Tamanho Area Observacao</Translate>
                </Label>
                <AvInput id="processo-tamanhoAreaObservacao" data-cy="tamanhoAreaObservacao" type="textarea" name="tamanhoAreaObservacao" />
              </AvGroup>
              <AvGroup check>
                <Label id="dadosGeograficosLitigioConflitoLabel">
                  <AvInput
                    id="processo-dadosGeograficosLitigioConflito"
                    data-cy="dadosGeograficosLitigioConflito"
                    type="checkbox"
                    className="form-check-input"
                    name="dadosGeograficosLitigioConflito"
                  />
                  <Translate contentKey="cidhaApp.processo.dadosGeograficosLitigioConflito">Dados Geograficos Litigio Conflito</Translate>
                </Label>
              </AvGroup>
              <AvGroup>
                <Label id="latitudeLabel" for="processo-latitude">
                  <Translate contentKey="cidhaApp.processo.latitude">Latitude</Translate>
                </Label>
                <AvField id="processo-latitude" data-cy="latitude" type="text" name="latitude" />
              </AvGroup>
              <AvGroup>
                <Label id="longitudeLabel" for="processo-longitude">
                  <Translate contentKey="cidhaApp.processo.longitude">Longitude</Translate>
                </Label>
                <AvField id="processo-longitude" data-cy="longitude" type="text" name="longitude" />
              </AvGroup>
              <AvGroup>
                <Label id="numeroProcessoMPFLabel" for="processo-numeroProcessoMPF">
                  <Translate contentKey="cidhaApp.processo.numeroProcessoMPF">Numero Processo MPF</Translate>
                </Label>
                <AvField id="processo-numeroProcessoMPF" data-cy="numeroProcessoMPF" type="text" name="numeroProcessoMPF" />
              </AvGroup>
              <AvGroup>
                <Label id="numeroEmbargoLabel" for="processo-numeroEmbargo">
                  <Translate contentKey="cidhaApp.processo.numeroEmbargo">Numero Embargo</Translate>
                </Label>
                <AvField id="processo-numeroEmbargo" data-cy="numeroEmbargo" type="text" name="numeroEmbargo" />
              </AvGroup>
              <AvGroup>
                <Label id="pautaApelacaoLabel" for="processo-pautaApelacao">
                  <Translate contentKey="cidhaApp.processo.pautaApelacao">Pauta Apelacao</Translate>
                </Label>
                <AvInput id="processo-pautaApelacao" data-cy="pautaApelacao" type="textarea" name="pautaApelacao" />
              </AvGroup>
              <AvGroup>
                <Label id="numeroRecursoEspecialLabel" for="processo-numeroRecursoEspecial">
                  <Translate contentKey="cidhaApp.processo.numeroRecursoEspecial">Numero Recurso Especial</Translate>
                </Label>
                <AvField id="processo-numeroRecursoEspecial" data-cy="numeroRecursoEspecial" type="text" name="numeroRecursoEspecial" />
              </AvGroup>
              <AvGroup>
                <Label id="admissiblidadeLabel" for="processo-admissiblidade">
                  <Translate contentKey="cidhaApp.processo.admissiblidade">Admissiblidade</Translate>
                </Label>
                <AvInput id="processo-admissiblidade" data-cy="admissiblidade" type="textarea" name="admissiblidade" />
              </AvGroup>
              <AvGroup check>
                <Label id="envolveGrandeProjetoLabel">
                  <AvInput
                    id="processo-envolveGrandeProjeto"
                    data-cy="envolveGrandeProjeto"
                    type="checkbox"
                    className="form-check-input"
                    name="envolveGrandeProjeto"
                  />
                  <Translate contentKey="cidhaApp.processo.envolveGrandeProjeto">Envolve Grande Projeto</Translate>
                </Label>
              </AvGroup>
              <AvGroup check>
                <Label id="envolveUnidadeConservacaoLabel">
                  <AvInput
                    id="processo-envolveUnidadeConservacao"
                    data-cy="envolveUnidadeConservacao"
                    type="checkbox"
                    className="form-check-input"
                    name="envolveUnidadeConservacao"
                  />
                  <Translate contentKey="cidhaApp.processo.envolveUnidadeConservacao">Envolve Unidade Conservacao</Translate>
                </Label>
              </AvGroup>
              <AvGroup>
                <Label id="linkReferenciaLabel" for="processo-linkReferencia">
                  <Translate contentKey="cidhaApp.processo.linkReferencia">Link Referencia</Translate>
                </Label>
                <AvInput id="processo-linkReferencia" data-cy="linkReferencia" type="textarea" name="linkReferencia" />
              </AvGroup>
              <AvGroup>
                <Label id="statusProcessoLabel" for="processo-statusProcesso">
                  <Translate contentKey="cidhaApp.processo.statusProcesso">Status Processo</Translate>
                </Label>
                <AvInput
                  id="processo-statusProcesso"
                  data-cy="statusProcesso"
                  type="select"
                  className="form-control"
                  name="statusProcesso"
                  value={(!isNew && processoEntity.statusProcesso) || 'EM_ANDAMENTO'}
                >
                  <option value="EM_ANDAMENTO">{translate('cidhaApp.StatusProcesso.EM_ANDAMENTO')}</option>
                  <option value="FINALIZADO">{translate('cidhaApp.StatusProcesso.FINALIZADO')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="processo-tipoDecisao">
                  <Translate contentKey="cidhaApp.processo.tipoDecisao">Tipo Decisao</Translate>
                </Label>
                <AvInput id="processo-tipoDecisao" data-cy="tipoDecisao" type="select" className="form-control" name="tipoDecisaoId">
                  <option value="" key="0" />
                  {tipoDecisaos
                    ? tipoDecisaos.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.descricao}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="processo-tipoEmpreendimento">
                  <Translate contentKey="cidhaApp.processo.tipoEmpreendimento">Tipo Empreendimento</Translate>
                </Label>
                <AvInput
                  id="processo-tipoEmpreendimento"
                  data-cy="tipoEmpreendimento"
                  type="select"
                  className="form-control"
                  name="tipoEmpreendimentoId"
                >
                  <option value="" key="0" />
                  {tipoEmpreendimentos
                    ? tipoEmpreendimentos.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.descricao}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="processo-secaoJudiciaria">
                  <Translate contentKey="cidhaApp.processo.secaoJudiciaria">Secao Judiciaria</Translate>
                </Label>
                <AvInput
                  id="processo-secaoJudiciaria"
                  data-cy="secaoJudiciaria"
                  type="select"
                  className="form-control"
                  name="secaoJudiciariaId"
                >
                  <option value="" key="0" />
                  {secaoJudiciarias
                    ? secaoJudiciarias.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.nome}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="processo-comarca">
                  <Translate contentKey="cidhaApp.processo.comarca">Comarca</Translate>
                </Label>
                <AvInput
                  id="processo-comarca"
                  data-cy="comarca"
                  type="select"
                  multiple
                  className="form-control"
                  name="comarcas"
                  value={!isNew && processoEntity.comarcas && processoEntity.comarcas.map(e => e.id)}
                >
                  <option value="" key="0" />
                  {comarcas
                    ? comarcas.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.nome}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="processo-municipio">
                  <Translate contentKey="cidhaApp.processo.municipio">Municipio</Translate>
                </Label>
                <AvInput
                  id="processo-municipio"
                  data-cy="municipio"
                  type="select"
                  multiple
                  className="form-control"
                  name="municipios"
                  value={!isNew && processoEntity.municipios && processoEntity.municipios.map(e => e.id)}
                >
                  <option value="" key="0" />
                  {municipios
                    ? municipios.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.nome}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="processo-territorio">
                  <Translate contentKey="cidhaApp.processo.territorio">Territorio</Translate>
                </Label>
                <AvInput
                  id="processo-territorio"
                  data-cy="territorio"
                  type="select"
                  multiple
                  className="form-control"
                  name="territorios"
                  value={!isNew && processoEntity.territorios && processoEntity.territorios.map(e => e.id)}
                >
                  <option value="" key="0" />
                  {territorios
                    ? territorios.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.nome}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="processo-atividadeExploracaoIlegal">
                  <Translate contentKey="cidhaApp.processo.atividadeExploracaoIlegal">Atividade Exploracao Ilegal</Translate>
                </Label>
                <AvInput
                  id="processo-atividadeExploracaoIlegal"
                  data-cy="atividadeExploracaoIlegal"
                  type="select"
                  multiple
                  className="form-control"
                  name="atividadeExploracaoIlegals"
                  value={!isNew && processoEntity.atividadeExploracaoIlegals && processoEntity.atividadeExploracaoIlegals.map(e => e.id)}
                >
                  <option value="" key="0" />
                  {atividadeExploracaoIlegals
                    ? atividadeExploracaoIlegals.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.descricao}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="processo-unidadeConservacao">
                  <Translate contentKey="cidhaApp.processo.unidadeConservacao">Unidade Conservacao</Translate>
                </Label>
                <AvInput
                  id="processo-unidadeConservacao"
                  data-cy="unidadeConservacao"
                  type="select"
                  multiple
                  className="form-control"
                  name="unidadeConservacaos"
                  value={!isNew && processoEntity.unidadeConservacaos && processoEntity.unidadeConservacaos.map(e => e.id)}
                >
                  <option value="" key="0" />
                  {unidadeConservacaos
                    ? unidadeConservacaos.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.descricao}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="processo-envolvidosConflitoLitigio">
                  <Translate contentKey="cidhaApp.processo.envolvidosConflitoLitigio">Envolvidos Conflito Litigio</Translate>
                </Label>
                <AvInput
                  id="processo-envolvidosConflitoLitigio"
                  data-cy="envolvidosConflitoLitigio"
                  type="select"
                  multiple
                  className="form-control"
                  name="envolvidosConflitoLitigios"
                  value={!isNew && processoEntity.envolvidosConflitoLitigios && processoEntity.envolvidosConflitoLitigios.map(e => e.id)}
                >
                  <option value="" key="0" />
                  {envolvidosConflitoLitigios
                    ? envolvidosConflitoLitigios.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="processo-terraIndigena">
                  <Translate contentKey="cidhaApp.processo.terraIndigena">Terra Indigena</Translate>
                </Label>
                <AvInput
                  id="processo-terraIndigena"
                  data-cy="terraIndigena"
                  type="select"
                  multiple
                  className="form-control"
                  name="terraIndigenas"
                  value={!isNew && processoEntity.terraIndigenas && processoEntity.terraIndigenas.map(e => e.id)}
                >
                  <option value="" key="0" />
                  {terraIndigenas
                    ? terraIndigenas.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.descricao}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="processo-processoConflito">
                  <Translate contentKey="cidhaApp.processo.processoConflito">Processo Conflito</Translate>
                </Label>
                <AvInput
                  id="processo-processoConflito"
                  data-cy="processoConflito"
                  type="select"
                  multiple
                  className="form-control"
                  name="processoConflitos"
                  value={!isNew && processoEntity.processoConflitos && processoEntity.processoConflitos.map(e => e.id)}
                >
                  <option value="" key="0" />
                  {processoConflitos
                    ? processoConflitos.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.nomeCasoComuidade}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="processo-parteInteresssada">
                  <Translate contentKey="cidhaApp.processo.parteInteresssada">Parte Interesssada</Translate>
                </Label>
                <AvInput
                  id="processo-parteInteresssada"
                  data-cy="parteInteresssada"
                  type="select"
                  multiple
                  className="form-control"
                  name="parteInteresssadas"
                  value={!isNew && processoEntity.parteInteresssadas && processoEntity.parteInteresssadas.map(e => e.id)}
                >
                  <option value="" key="0" />
                  {parteInteresssadas
                    ? parteInteresssadas.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.nome}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="processo-relator">
                  <Translate contentKey="cidhaApp.processo.relator">Relator</Translate>
                </Label>
                <AvInput
                  id="processo-relator"
                  data-cy="relator"
                  type="select"
                  multiple
                  className="form-control"
                  name="relators"
                  value={!isNew && processoEntity.relators && processoEntity.relators.map(e => e.id)}
                >
                  <option value="" key="0" />
                  {relators
                    ? relators.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.nome}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="processo-quilombo">
                  <Translate contentKey="cidhaApp.processo.quilombo">Quilombo</Translate>
                </Label>
                <AvInput
                  id="processo-quilombo"
                  data-cy="quilombo"
                  type="select"
                  multiple
                  className="form-control"
                  name="quilombos"
                  value={!isNew && processoEntity.quilombos && processoEntity.quilombos.map(e => e.id)}
                >
                  <option value="" key="0" />
                  {quilombos
                    ? quilombos.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.nome}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/processo" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </AvForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  tipoDecisaos: storeState.tipoDecisao.entities,
  tipoEmpreendimentos: storeState.tipoEmpreendimento.entities,
  secaoJudiciarias: storeState.secaoJudiciaria.entities,
  comarcas: storeState.comarca.entities,
  municipios: storeState.municipio.entities,
  territorios: storeState.territorio.entities,
  atividadeExploracaoIlegals: storeState.atividadeExploracaoIlegal.entities,
  unidadeConservacaos: storeState.unidadeConservacao.entities,
  envolvidosConflitoLitigios: storeState.envolvidosConflitoLitigio.entities,
  terraIndigenas: storeState.terraIndigena.entities,
  processoConflitos: storeState.processoConflito.entities,
  parteInteresssadas: storeState.parteInteresssada.entities,
  relators: storeState.relator.entities,
  quilombos: storeState.quilombo.entities,
  problemaJuridicos: storeState.problemaJuridico.entities,
  processoEntity: storeState.processo.entity,
  loading: storeState.processo.loading,
  updating: storeState.processo.updating,
  updateSuccess: storeState.processo.updateSuccess,
});

const mapDispatchToProps = {
  getTipoDecisaos,
  getTipoEmpreendimentos,
  getSecaoJudiciarias,
  getComarcas,
  getMunicipios,
  getTerritorios,
  getAtividadeExploracaoIlegals,
  getUnidadeConservacaos,
  getEnvolvidosConflitoLitigios,
  getTerraIndigenas,
  getProcessoConflitos,
  getParteInteresssadas,
  getRelators,
  getQuilombos,
  getProblemaJuridicos,
  getEntity,
  updateEntity,
  setBlob,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ProcessoUpdate);
