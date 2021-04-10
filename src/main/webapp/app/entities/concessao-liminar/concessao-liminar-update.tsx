import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IProcesso } from 'app/shared/model/processo.model';
import { getEntities as getProcessos } from 'app/entities/processo/processo.reducer';
import { getEntity, updateEntity, createEntity, reset } from './concessao-liminar.reducer';
import { IConcessaoLiminar } from 'app/shared/model/concessao-liminar.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IConcessaoLiminarUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ConcessaoLiminarUpdate = (props: IConcessaoLiminarUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { concessaoLiminarEntity, processos, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/concessao-liminar' + props.location.search);
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
        ...concessaoLiminarEntity,
        ...values,
        processo: processos.find(it => it.id.toString() === values.processoId.toString()),
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
          <h2 id="cidhaApp.concessaoLiminar.home.createOrEditLabel" data-cy="ConcessaoLiminarCreateUpdateHeading">
            <Translate contentKey="cidhaApp.concessaoLiminar.home.createOrEditLabel">Create or edit a ConcessaoLiminar</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : concessaoLiminarEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="concessao-liminar-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="concessao-liminar-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="descricaoLabel" for="concessao-liminar-descricao">
                  <Translate contentKey="cidhaApp.concessaoLiminar.descricao">Descricao</Translate>
                </Label>
                <AvField id="concessao-liminar-descricao" data-cy="descricao" type="text" name="descricao" />
              </AvGroup>
              <AvGroup>
                <Label for="concessao-liminar-processo">
                  <Translate contentKey="cidhaApp.concessaoLiminar.processo">Processo</Translate>
                </Label>
                <AvInput id="concessao-liminar-processo" data-cy="processo" type="select" className="form-control" name="processoId">
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
              <Button tag={Link} id="cancel-save" to="/concessao-liminar" replace color="info">
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
  processos: storeState.processo.entities,
  concessaoLiminarEntity: storeState.concessaoLiminar.entity,
  loading: storeState.concessaoLiminar.loading,
  updating: storeState.concessaoLiminar.updating,
  updateSuccess: storeState.concessaoLiminar.updateSuccess,
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

export default connect(mapStateToProps, mapDispatchToProps)(ConcessaoLiminarUpdate);
