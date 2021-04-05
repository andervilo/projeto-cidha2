import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IProcesso } from 'app/shared/model/processo.model';
import { getEntities as getProcessos } from 'app/entities/processo/processo.reducer';
import { getEntity, updateEntity, createEntity, reset } from './concessao-liminar-cassada.reducer';
import { IConcessaoLiminarCassada } from 'app/shared/model/concessao-liminar-cassada.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IConcessaoLiminarCassadaUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ConcessaoLiminarCassadaUpdate = (props: IConcessaoLiminarCassadaUpdateProps) => {
  const [processoId, setProcessoId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { concessaoLiminarCassadaEntity, processos, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/concessao-liminar-cassada' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

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
        ...concessaoLiminarCassadaEntity,
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
          <h2 id="cidhaApp.concessaoLiminarCassada.home.createOrEditLabel">
            <Translate contentKey="cidhaApp.concessaoLiminarCassada.home.createOrEditLabel">
              Create or edit a ConcessaoLiminarCassada
            </Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : concessaoLiminarCassadaEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="concessao-liminar-cassada-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="concessao-liminar-cassada-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="descricaoLabel" for="concessao-liminar-cassada-descricao">
                  <Translate contentKey="cidhaApp.concessaoLiminarCassada.descricao">Descricao</Translate>
                </Label>
                <AvField id="concessao-liminar-cassada-descricao" type="text" name="descricao" />
              </AvGroup>
              <AvGroup>
                <Label for="concessao-liminar-cassada-processo">
                  <Translate contentKey="cidhaApp.concessaoLiminarCassada.processo">Processo</Translate>
                </Label>
                <AvInput id="concessao-liminar-cassada-processo" type="select" className="form-control" name="processo.id">
                  <option value="" key="0" />
                  {processos
                    ? processos.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/concessao-liminar-cassada" replace color="info">
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
  processos: storeState.processo.entities,
  concessaoLiminarCassadaEntity: storeState.concessaoLiminarCassada.entity,
  loading: storeState.concessaoLiminarCassada.loading,
  updating: storeState.concessaoLiminarCassada.updating,
  updateSuccess: storeState.concessaoLiminarCassada.updateSuccess,
});

const mapDispatchToProps = {
  getProcessos,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ConcessaoLiminarCassadaUpdate);
