import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { ITipoData } from 'app/shared/model/tipo-data.model';
import { getEntities as getTipoData } from 'app/entities/tipo-data/tipo-data.reducer';
import { IProcesso } from 'app/shared/model/processo.model';
import { getEntities as getProcessos } from 'app/entities/processo/processo.reducer';
import { getEntity, updateEntity, createEntity, reset } from './data.reducer';
import { IData } from 'app/shared/model/data.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IDataUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const DataUpdate = (props: IDataUpdateProps) => {
  const [tipoDataId, setTipoDataId] = useState('0');
  const [processoId, setProcessoId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { dataEntity, tipoData, processos, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/data' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getTipoData();
    props.getProcessos();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...dataEntity,
        ...values,
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
          <h2 id="cidhaApp.data.home.createOrEditLabel">
            <Translate contentKey="cidhaApp.data.home.createOrEditLabel">Create or edit a Data</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : dataEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="data-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="data-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="dataLabel" for="data-data">
                  <Translate contentKey="cidhaApp.data.data">Data</Translate>
                </Label>
                <AvField id="data-data" type="date" className="form-control" name="data" />
              </AvGroup>
              <AvGroup>
                <Label for="data-tipoData">
                  <Translate contentKey="cidhaApp.data.tipoData">Tipo Data</Translate>
                </Label>
                <AvInput id="data-tipoData" type="select" className="form-control" name="tipoData.id">
                  <option value="" key="0" />
                  {tipoData
                    ? tipoData.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.descricao}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="data-processo">
                  <Translate contentKey="cidhaApp.data.processo">Processo</Translate>
                </Label>
                <AvInput id="data-processo" type="select" className="form-control" name="processo.id">
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
              <Button tag={Link} id="cancel-save" to="/data" replace color="info">
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
  tipoData: storeState.tipoData.entities,
  processos: storeState.processo.entities,
  dataEntity: storeState.data.entity,
  loading: storeState.data.loading,
  updating: storeState.data.updating,
  updateSuccess: storeState.data.updateSuccess,
});

const mapDispatchToProps = {
  getTipoData,
  getProcessos,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(DataUpdate);
