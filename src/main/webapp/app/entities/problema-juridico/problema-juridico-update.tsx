import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { setFileData, byteSize, Translate, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IFundamentacaoDoutrinaria } from 'app/shared/model/fundamentacao-doutrinaria.model';
import { getEntities as getFundamentacaoDoutrinarias } from 'app/entities/fundamentacao-doutrinaria/fundamentacao-doutrinaria.reducer';
import { IJurisprudencia } from 'app/shared/model/jurisprudencia.model';
import { getEntities as getJurisprudencias } from 'app/entities/jurisprudencia/jurisprudencia.reducer';
import { IFundamentacaoLegal } from 'app/shared/model/fundamentacao-legal.model';
import { getEntities as getFundamentacaoLegals } from 'app/entities/fundamentacao-legal/fundamentacao-legal.reducer';
import { IInstrumentoInternacional } from 'app/shared/model/instrumento-internacional.model';
import { getEntities as getInstrumentoInternacionals } from 'app/entities/instrumento-internacional/instrumento-internacional.reducer';
import { IProcesso } from 'app/shared/model/processo.model';
import { getEntities as getProcessos } from 'app/entities/processo/processo.reducer';
import { getEntity, updateEntity, createEntity, setBlob, reset } from './problema-juridico.reducer';
import { IProblemaJuridico } from 'app/shared/model/problema-juridico.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IProblemaJuridicoUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ProblemaJuridicoUpdate = (props: IProblemaJuridicoUpdateProps) => {
  const [idsfundamentacaoDoutrinaria, setIdsfundamentacaoDoutrinaria] = useState([]);
  const [idsjurisprudencia, setIdsjurisprudencia] = useState([]);
  const [idsfundamentacaoLegal, setIdsfundamentacaoLegal] = useState([]);
  const [idsinstrumentoInternacional, setIdsinstrumentoInternacional] = useState([]);
  const [idsprocesso, setIdsprocesso] = useState([]);
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const {
    problemaJuridicoEntity,
    fundamentacaoDoutrinarias,
    jurisprudencias,
    fundamentacaoLegals,
    instrumentoInternacionals,
    processos,
    loading,
    updating,
  } = props;

  const { prolemaJuridicoRespondido } = problemaJuridicoEntity;

  const handleClose = () => {
    props.history.push('/problema-juridico' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getFundamentacaoDoutrinarias();
    props.getJurisprudencias();
    props.getFundamentacaoLegals();
    props.getInstrumentoInternacionals();
    props.getProcessos();
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
        ...problemaJuridicoEntity,
        ...values,
        fundamentacaoDoutrinarias: mapIdList(values.fundamentacaoDoutrinarias),
        jurisprudencias: mapIdList(values.jurisprudencias),
        fundamentacaoLegals: mapIdList(values.fundamentacaoLegals),
        instrumentoInternacionals: mapIdList(values.instrumentoInternacionals),
        processos: mapIdList(values.processos),
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
          <h2 id="cidhaApp.problemaJuridico.home.createOrEditLabel" data-cy="ProblemaJuridicoCreateUpdateHeading">
            <Translate contentKey="cidhaApp.problemaJuridico.home.createOrEditLabel">Create or edit a ProblemaJuridico</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : problemaJuridicoEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="problema-juridico-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="problema-juridico-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="prolemaJuridicoRespondidoLabel" for="problema-juridico-prolemaJuridicoRespondido">
                  <Translate contentKey="cidhaApp.problemaJuridico.prolemaJuridicoRespondido">Prolema Juridico Respondido</Translate>
                </Label>
                <AvInput
                  id="problema-juridico-prolemaJuridicoRespondido"
                  data-cy="prolemaJuridicoRespondido"
                  type="textarea"
                  name="prolemaJuridicoRespondido"
                />
              </AvGroup>
              <AvGroup>
                <Label id="folhasProblemaJuridicoLabel" for="problema-juridico-folhasProblemaJuridico">
                  <Translate contentKey="cidhaApp.problemaJuridico.folhasProblemaJuridico">Folhas Problema Juridico</Translate>
                </Label>
                <AvField
                  id="problema-juridico-folhasProblemaJuridico"
                  data-cy="folhasProblemaJuridico"
                  type="text"
                  name="folhasProblemaJuridico"
                />
              </AvGroup>
              <AvGroup>
                <Label for="problema-juridico-fundamentacaoDoutrinaria">
                  <Translate contentKey="cidhaApp.problemaJuridico.fundamentacaoDoutrinaria">Fundamentacao Doutrinaria</Translate>
                </Label>
                <AvInput
                  id="problema-juridico-fundamentacaoDoutrinaria"
                  data-cy="fundamentacaoDoutrinaria"
                  type="select"
                  multiple
                  className="form-control"
                  name="fundamentacaoDoutrinarias"
                  value={
                    !isNew &&
                    problemaJuridicoEntity.fundamentacaoDoutrinarias &&
                    problemaJuridicoEntity.fundamentacaoDoutrinarias.map(e => e.id)
                  }
                >
                  <option value="" key="0" />
                  {fundamentacaoDoutrinarias
                    ? fundamentacaoDoutrinarias.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.fundamentacaoDoutrinariaCitada}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="problema-juridico-jurisprudencia">
                  <Translate contentKey="cidhaApp.problemaJuridico.jurisprudencia">Jurisprudencia</Translate>
                </Label>
                <AvInput
                  id="problema-juridico-jurisprudencia"
                  data-cy="jurisprudencia"
                  type="select"
                  multiple
                  className="form-control"
                  name="jurisprudencias"
                  value={!isNew && problemaJuridicoEntity.jurisprudencias && problemaJuridicoEntity.jurisprudencias.map(e => e.id)}
                >
                  <option value="" key="0" />
                  {jurisprudencias
                    ? jurisprudencias.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.jurisprudenciaCitadaDescricao}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="problema-juridico-fundamentacaoLegal">
                  <Translate contentKey="cidhaApp.problemaJuridico.fundamentacaoLegal">Fundamentacao Legal</Translate>
                </Label>
                <AvInput
                  id="problema-juridico-fundamentacaoLegal"
                  data-cy="fundamentacaoLegal"
                  type="select"
                  multiple
                  className="form-control"
                  name="fundamentacaoLegals"
                  value={!isNew && problemaJuridicoEntity.fundamentacaoLegals && problemaJuridicoEntity.fundamentacaoLegals.map(e => e.id)}
                >
                  <option value="" key="0" />
                  {fundamentacaoLegals
                    ? fundamentacaoLegals.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.fundamentacaoLegal}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="problema-juridico-instrumentoInternacional">
                  <Translate contentKey="cidhaApp.problemaJuridico.instrumentoInternacional">Instrumento Internacional</Translate>
                </Label>
                <AvInput
                  id="problema-juridico-instrumentoInternacional"
                  data-cy="instrumentoInternacional"
                  type="select"
                  multiple
                  className="form-control"
                  name="instrumentoInternacionals"
                  value={
                    !isNew &&
                    problemaJuridicoEntity.instrumentoInternacionals &&
                    problemaJuridicoEntity.instrumentoInternacionals.map(e => e.id)
                  }
                >
                  <option value="" key="0" />
                  {instrumentoInternacionals
                    ? instrumentoInternacionals.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.instrumentoInternacionalCitadoDescricao}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="problema-juridico-processo">
                  <Translate contentKey="cidhaApp.problemaJuridico.processo">Processo</Translate>
                </Label>
                <AvInput
                  id="problema-juridico-processo"
                  data-cy="processo"
                  type="select"
                  multiple
                  className="form-control"
                  name="processos"
                  value={!isNew && problemaJuridicoEntity.processos && problemaJuridicoEntity.processos.map(e => e.id)}
                >
                  <option value="" key="0" />
                  {processos
                    ? processos.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.oficio}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/problema-juridico" replace color="info">
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
  fundamentacaoDoutrinarias: storeState.fundamentacaoDoutrinaria.entities,
  jurisprudencias: storeState.jurisprudencia.entities,
  fundamentacaoLegals: storeState.fundamentacaoLegal.entities,
  instrumentoInternacionals: storeState.instrumentoInternacional.entities,
  processos: storeState.processo.entities,
  problemaJuridicoEntity: storeState.problemaJuridico.entity,
  loading: storeState.problemaJuridico.loading,
  updating: storeState.problemaJuridico.updating,
  updateSuccess: storeState.problemaJuridico.updateSuccess,
});

const mapDispatchToProps = {
  getFundamentacaoDoutrinarias,
  getJurisprudencias,
  getFundamentacaoLegals,
  getInstrumentoInternacionals,
  getProcessos,
  getEntity,
  updateEntity,
  setBlob,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ProblemaJuridicoUpdate);
