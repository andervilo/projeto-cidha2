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
import { getEntity, updateEntity, createEntity, reset } from './embargo-resp-re.reducer';
import { IEmbargoRespRe } from 'app/shared/model/embargo-resp-re.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IEmbargoRespReUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const EmbargoRespReUpdate = (props: IEmbargoRespReUpdateProps) => {
  const [processoId, setProcessoId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { embargoRespReEntity, processos, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/embargo-resp-re' + props.location.search);
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
        ...embargoRespReEntity,
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
          <h2 id="cidhaApp.embargoRespRe.home.createOrEditLabel">
            <Translate contentKey="cidhaApp.embargoRespRe.home.createOrEditLabel">Create or edit a EmbargoRespRe</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : embargoRespReEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="embargo-resp-re-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="embargo-resp-re-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="descricaoLabel" for="embargo-resp-re-descricao">
                  <Translate contentKey="cidhaApp.embargoRespRe.descricao">Descricao</Translate>
                </Label>
                <AvField id="embargo-resp-re-descricao" type="text" name="descricao" />
              </AvGroup>
              <AvGroup>
                <Label for="embargo-resp-re-processo">
                  <Translate contentKey="cidhaApp.embargoRespRe.processo">Processo</Translate>
                </Label>
                <AvInput id="embargo-resp-re-processo" type="select" className="form-control" name="processo.id">
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
              <Button tag={Link} id="cancel-save" to="/embargo-resp-re" replace color="info">
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
  embargoRespReEntity: storeState.embargoRespRe.entity,
  loading: storeState.embargoRespRe.loading,
  updating: storeState.embargoRespRe.updating,
  updateSuccess: storeState.embargoRespRe.updateSuccess,
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

export default connect(mapStateToProps, mapDispatchToProps)(EmbargoRespReUpdate);
