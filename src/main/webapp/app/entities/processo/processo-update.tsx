import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, setFileData, byteSize, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { ITipoDecisao } from 'app/shared/model/tipo-decisao.model';
import { getEntities as getTipoDecisaos } from 'app/entities/tipo-decisao/tipo-decisao.reducer';
import { ITipoEmpreendimento } from 'app/shared/model/tipo-empreendimento.model';
import { getEntities as getTipoEmpreendimentos } from 'app/entities/tipo-empreendimento/tipo-empreendimento.reducer';
import { IComarca } from 'app/shared/model/comarca.model';
import { getEntities as getComarcas } from 'app/entities/comarca/comarca.reducer';
import { IQuilombo } from 'app/shared/model/quilombo.model';
import { getEntities as getQuilombos } from 'app/entities/quilombo/quilombo.reducer';
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
import { IProblemaJuridico } from 'app/shared/model/problema-juridico.model';
import { getEntities as getProblemaJuridicos } from 'app/entities/problema-juridico/problema-juridico.reducer';
import { getEntity, updateEntity, createEntity, setBlob, reset } from './processo.reducer';
import { IProcesso } from 'app/shared/model/processo.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IProcessoUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ProcessoUpdate = (props: IProcessoUpdateProps) => {
  const [idscomarca, setIdscomarca] = useState([]);
  const [idsquilombo, setIdsquilombo] = useState([]);
  const [idsmunicipio, setIdsmunicipio] = useState([]);
  const [idsterritorio, setIdsterritorio] = useState([]);
  const [idsatividadeExploracaoIlegal, setIdsatividadeExploracaoIlegal] = useState([]);
  const [idsunidadeConservacao, setIdsunidadeConservacao] = useState([]);
  const [idsenvolvidosConflitoLitigio, setIdsenvolvidosConflitoLitigio] = useState([]);
  const [idsterraIndigena, setIdsterraIndigena] = useState([]);
  const [idsprocessoConflito, setIdsprocessoConflito] = useState([]);
  const [idsparteInteresssada, setIdsparteInteresssada] = useState([]);
  const [idsrelator, setIdsrelator] = useState([]);
  const [tipoDecisaoId, setTipoDecisaoId] = useState('0');
  const [tipoEmpreendimentoId, setTipoEmpreendimentoId] = useState('0');
  const [problemaJuridicoId, setProblemaJuridicoId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const {
    processoEntity,
    tipoDecisaos,
    tipoEmpreendimentos,
    comarcas,
    quilombos,
    municipios,
    territorios,
    atividadeExploracaoIlegals,
    unidadeConservacaos,
    envolvidosConflitoLitigios,
    terraIndigenas,
    processoConflitos,
    parteInteresssadas,
    relators,
    problemaJuridicos,
    loading,
    updating,
  } = props;

  const { assunto, numeroProcessoJudicialPrimeiraInstanciaObservacoes } = processoEntity;

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
    props.getComarcas();
    props.getQuilombos();
    props.getMunicipios();
    props.getTerritorios();
    props.getAtividadeExploracaoIlegals();
    props.getUnidadeConservacaos();
    props.getEnvolvidosConflitoLitigios();
    props.getTerraIndigenas();
    props.getProcessoConflitos();
    props.getParteInteresssadas();
    props.getRelators();
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
        quilombos: mapIdList(values.quilombos),
        municipios: mapIdList(values.municipios),
        territorios: mapIdList(values.territorios),
        atividadeExploracaoIlegals: mapIdList(values.atividadeExploracaoIlegals),
        unidadeConservacaos: mapIdList(values.unidadeConservacaos),
        envolvidosConflitoLitigios: mapIdList(values.envolvidosConflitoLitigios),
        terraIndigenas: mapIdList(values.terraIndigenas),
        processoConflitos: mapIdList(values.processoConflitos),
        parteInteresssadas: mapIdList(values.parteInteresssadas),
        relators: mapIdList(values.relators),
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
          <h2 id="cidhaApp.processo.home.createOrEditLabel">
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
                <Label id="oficioLabel" for="processo-oficio">
                  <Translate contentKey="cidhaApp.processo.oficio">Oficio</Translate>
                </Label>
                <AvField id="processo-oficio" type="text" name="oficio" />
              </AvGroup>
              <AvGroup>
                <Label id="assuntoLabel" for="processo-assunto">
                  <Translate contentKey="cidhaApp.processo.assunto">Assunto</Translate>
                </Label>
                <AvInput id="processo-assunto" type="textarea" name="assunto" />
              </AvGroup>
              <AvGroup>
                <Label id="linkUnicoLabel" for="processo-linkUnico">
                  <Translate contentKey="cidhaApp.processo.linkUnico">Link Unico</Translate>
                </Label>
                <AvField id="processo-linkUnico" type="text" name="linkUnico" />
              </AvGroup>
              <AvGroup>
                <Label id="linkTrfLabel" for="processo-linkTrf">
                  <Translate contentKey="cidhaApp.processo.linkTrf">Link Trf</Translate>
                </Label>
                <AvField id="processo-linkTrf" type="text" name="linkTrf" />
              </AvGroup>
              <AvGroup>
                <Label id="subsecaoJudiciariaLabel" for="processo-subsecaoJudiciaria">
                  <Translate contentKey="cidhaApp.processo.subsecaoJudiciaria">Subsecao Judiciaria</Translate>
                </Label>
                <AvField id="processo-subsecaoJudiciaria" type="text" name="subsecaoJudiciaria" />
              </AvGroup>
              <AvGroup>
                <Label id="turmaTrf1Label" for="processo-turmaTrf1">
                  <Translate contentKey="cidhaApp.processo.turmaTrf1">Turma Trf 1</Translate>
                </Label>
                <AvField id="processo-turmaTrf1" type="text" name="turmaTrf1" />
              </AvGroup>
              <AvGroup>
                <Label id="numeroProcessoAdministrativoLabel" for="processo-numeroProcessoAdministrativo">
                  <Translate contentKey="cidhaApp.processo.numeroProcessoAdministrativo">Numero Processo Administrativo</Translate>
                </Label>
                <AvField id="processo-numeroProcessoAdministrativo" type="text" name="numeroProcessoAdministrativo" />
              </AvGroup>
              <AvGroup>
                <Label id="numeroProcessoJudicialPrimeiraInstanciaLabel" for="processo-numeroProcessoJudicialPrimeiraInstancia">
                  <Translate contentKey="cidhaApp.processo.numeroProcessoJudicialPrimeiraInstancia">
                    Numero Processo Judicial Primeira Instancia
                  </Translate>
                </Label>
                <AvField id="processo-numeroProcessoJudicialPrimeiraInstancia" type="text" name="numeroProcessoJudicialPrimeiraInstancia" />
              </AvGroup>
              <AvGroup>
                <Label id="numeroProcessoJudicialPrimeiraInstanciaLinkLabel" for="processo-numeroProcessoJudicialPrimeiraInstanciaLink">
                  <Translate contentKey="cidhaApp.processo.numeroProcessoJudicialPrimeiraInstanciaLink">
                    Numero Processo Judicial Primeira Instancia Link
                  </Translate>
                </Label>
                <AvField
                  id="processo-numeroProcessoJudicialPrimeiraInstanciaLink"
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
                  type="textarea"
                  name="numeroProcessoJudicialPrimeiraInstanciaObservacoes"
                />
              </AvGroup>
              <AvGroup check>
                <Label id="parecerLabel">
                  <AvInput id="processo-parecer" type="checkbox" className="form-check-input" name="parecer" />
                  <Translate contentKey="cidhaApp.processo.parecer">Parecer</Translate>
                </Label>
              </AvGroup>
              <AvGroup>
                <Label id="apelacaoLabel" for="processo-apelacao">
                  <Translate contentKey="cidhaApp.processo.apelacao">Apelacao</Translate>
                </Label>
                <AvField id="processo-apelacao" type="text" name="apelacao" />
              </AvGroup>
              <AvGroup>
                <Label for="processo-tipoDecisao">
                  <Translate contentKey="cidhaApp.processo.tipoDecisao">Tipo Decisao</Translate>
                </Label>
                <AvInput id="processo-tipoDecisao" type="select" className="form-control" name="tipoDecisao.id">
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
                <AvInput id="processo-tipoEmpreendimento" type="select" className="form-control" name="tipoEmpreendimento.id">
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
                <Label for="processo-comarca">
                  <Translate contentKey="cidhaApp.processo.comarca">Comarca</Translate>
                </Label>
                <AvInput
                  id="processo-comarca"
                  type="select"
                  multiple
                  className="form-control"
                  name="comarcas"
                  value={processoEntity.comarcas && processoEntity.comarcas.map(e => e.id)}
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
                <Label for="processo-quilombo">
                  <Translate contentKey="cidhaApp.processo.quilombo">Quilombo</Translate>
                </Label>
                <AvInput
                  id="processo-quilombo"
                  type="select"
                  multiple
                  className="form-control"
                  name="quilombos"
                  value={processoEntity.quilombos && processoEntity.quilombos.map(e => e.id)}
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
              <AvGroup>
                <Label for="processo-municipio">
                  <Translate contentKey="cidhaApp.processo.municipio">Municipio</Translate>
                </Label>
                <AvInput
                  id="processo-municipio"
                  type="select"
                  multiple
                  className="form-control"
                  name="municipios"
                  value={processoEntity.municipios && processoEntity.municipios.map(e => e.id)}
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
                  type="select"
                  multiple
                  className="form-control"
                  name="territorios"
                  value={processoEntity.territorios && processoEntity.territorios.map(e => e.id)}
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
                  type="select"
                  multiple
                  className="form-control"
                  name="atividadeExploracaoIlegals"
                  value={processoEntity.atividadeExploracaoIlegals && processoEntity.atividadeExploracaoIlegals.map(e => e.id)}
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
                  type="select"
                  multiple
                  className="form-control"
                  name="unidadeConservacaos"
                  value={processoEntity.unidadeConservacaos && processoEntity.unidadeConservacaos.map(e => e.id)}
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
                  type="select"
                  multiple
                  className="form-control"
                  name="envolvidosConflitoLitigios"
                  value={processoEntity.envolvidosConflitoLitigios && processoEntity.envolvidosConflitoLitigios.map(e => e.id)}
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
                  type="select"
                  multiple
                  className="form-control"
                  name="terraIndigenas"
                  value={processoEntity.terraIndigenas && processoEntity.terraIndigenas.map(e => e.id)}
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
                  type="select"
                  multiple
                  className="form-control"
                  name="processoConflitos"
                  value={processoEntity.processoConflitos && processoEntity.processoConflitos.map(e => e.id)}
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
                  type="select"
                  multiple
                  className="form-control"
                  name="parteInteresssadas"
                  value={processoEntity.parteInteresssadas && processoEntity.parteInteresssadas.map(e => e.id)}
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
                  type="select"
                  multiple
                  className="form-control"
                  name="relators"
                  value={processoEntity.relators && processoEntity.relators.map(e => e.id)}
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
              <Button tag={Link} id="cancel-save" to="/processo" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" type="submit" disabled={updating}>
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
  comarcas: storeState.comarca.entities,
  quilombos: storeState.quilombo.entities,
  municipios: storeState.municipio.entities,
  territorios: storeState.territorio.entities,
  atividadeExploracaoIlegals: storeState.atividadeExploracaoIlegal.entities,
  unidadeConservacaos: storeState.unidadeConservacao.entities,
  envolvidosConflitoLitigios: storeState.envolvidosConflitoLitigio.entities,
  terraIndigenas: storeState.terraIndigena.entities,
  processoConflitos: storeState.processoConflito.entities,
  parteInteresssadas: storeState.parteInteresssada.entities,
  relators: storeState.relator.entities,
  problemaJuridicos: storeState.problemaJuridico.entities,
  processoEntity: storeState.processo.entity,
  loading: storeState.processo.loading,
  updating: storeState.processo.updating,
  updateSuccess: storeState.processo.updateSuccess,
});

const mapDispatchToProps = {
  getTipoDecisaos,
  getTipoEmpreendimentos,
  getComarcas,
  getQuilombos,
  getMunicipios,
  getTerritorios,
  getAtividadeExploracaoIlegals,
  getUnidadeConservacaos,
  getEnvolvidosConflitoLitigios,
  getTerraIndigenas,
  getProcessoConflitos,
  getParteInteresssadas,
  getRelators,
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
