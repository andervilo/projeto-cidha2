import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, setFileData, byteSize, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IDireito } from 'app/shared/model/direito.model';
import { getEntities as getDireitos } from 'app/entities/direito/direito.reducer';
import { IProcesso } from 'app/shared/model/processo.model';
import { getEntities as getProcessos } from 'app/entities/processo/processo.reducer';
import { getEntity, updateEntity, createEntity, setBlob, reset } from './processo-conflito.reducer';
import { IProcessoConflito } from 'app/shared/model/processo-conflito.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IProcessoConflitoUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ProcessoConflitoUpdate = (props: IProcessoConflitoUpdateProps) => {
  const [idsdireito, setIdsdireito] = useState([]);
  const [processoId, setProcessoId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { processoConflitoEntity, direitos, processos, loading, updating } = props;

  const { inicioConflitoObservacoes, historicoConlito } = processoConflitoEntity;

  const handleClose = () => {
    props.history.push('/processo-conflito' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getDireitos();
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
        ...processoConflitoEntity,
        ...values,
        direitos: mapIdList(values.direitos),
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
          <h2 id="cidhaApp.processoConflito.home.createOrEditLabel">
            <Translate contentKey="cidhaApp.processoConflito.home.createOrEditLabel">Create or edit a ProcessoConflito</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : processoConflitoEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="processo-conflito-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="processo-conflito-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="inicioConflitoObservacoesLabel" for="processo-conflito-inicioConflitoObservacoes">
                  <Translate contentKey="cidhaApp.processoConflito.inicioConflitoObservacoes">Inicio Conflito Observacoes</Translate>
                </Label>
                <AvInput id="processo-conflito-inicioConflitoObservacoes" type="textarea" name="inicioConflitoObservacoes" />
              </AvGroup>
              <AvGroup>
                <Label id="historicoConlitoLabel" for="processo-conflito-historicoConlito">
                  <Translate contentKey="cidhaApp.processoConflito.historicoConlito">Historico Conlito</Translate>
                </Label>
                <AvInput id="processo-conflito-historicoConlito" type="textarea" name="historicoConlito" />
              </AvGroup>
              <AvGroup>
                <Label id="nomeCasoComuidadeLabel" for="processo-conflito-nomeCasoComuidade">
                  <Translate contentKey="cidhaApp.processoConflito.nomeCasoComuidade">Nome Caso Comuidade</Translate>
                </Label>
                <AvField id="processo-conflito-nomeCasoComuidade" type="text" name="nomeCasoComuidade" />
              </AvGroup>
              <AvGroup check>
                <Label id="consultaPreviaLabel">
                  <AvInput id="processo-conflito-consultaPrevia" type="checkbox" className="form-check-input" name="consultaPrevia" />
                  <Translate contentKey="cidhaApp.processoConflito.consultaPrevia">Consulta Previa</Translate>
                </Label>
              </AvGroup>
              <AvGroup>
                <Label for="processo-conflito-direito">
                  <Translate contentKey="cidhaApp.processoConflito.direito">Direito</Translate>
                </Label>
                <AvInput
                  id="processo-conflito-direito"
                  type="select"
                  multiple
                  className="form-control"
                  name="direitos"
                  value={processoConflitoEntity.direitos && processoConflitoEntity.direitos.map(e => e.id)}
                >
                  <option value="" key="0" />
                  {direitos
                    ? direitos.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.descricao}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/processo-conflito" replace color="info">
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
  direitos: storeState.direito.entities,
  processos: storeState.processo.entities,
  processoConflitoEntity: storeState.processoConflito.entity,
  loading: storeState.processoConflito.loading,
  updating: storeState.processoConflito.updating,
  updateSuccess: storeState.processoConflito.updateSuccess,
});

const mapDispatchToProps = {
  getDireitos,
  getProcessos,
  getEntity,
  updateEntity,
  setBlob,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ProcessoConflitoUpdate);
