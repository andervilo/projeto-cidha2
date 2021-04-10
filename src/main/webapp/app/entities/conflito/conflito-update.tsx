import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { setFileData, byteSize, Translate, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IProcessoConflito } from 'app/shared/model/processo-conflito.model';
import { getEntities as getProcessoConflitos } from 'app/entities/processo-conflito/processo-conflito.reducer';
import { getEntity, updateEntity, createEntity, setBlob, reset } from './conflito.reducer';
import { IConflito } from 'app/shared/model/conflito.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IConflitoUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ConflitoUpdate = (props: IConflitoUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { conflitoEntity, processoConflitos, loading, updating } = props;

  const { descricao } = conflitoEntity;

  const handleClose = () => {
    props.history.push('/conflito' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getProcessoConflitos();
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
        ...conflitoEntity,
        ...values,
        processoConflito: processoConflitos.find(it => it.id.toString() === values.processoConflitoId.toString()),
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
          <h2 id="cidhaApp.conflito.home.createOrEditLabel" data-cy="ConflitoCreateUpdateHeading">
            <Translate contentKey="cidhaApp.conflito.home.createOrEditLabel">Create or edit a Conflito</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : conflitoEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="conflito-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="conflito-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="descricaoLabel" for="conflito-descricao">
                  <Translate contentKey="cidhaApp.conflito.descricao">Descricao</Translate>
                </Label>
                <AvInput id="conflito-descricao" data-cy="descricao" type="textarea" name="descricao" />
              </AvGroup>
              <AvGroup>
                <Label for="conflito-processoConflito">
                  <Translate contentKey="cidhaApp.conflito.processoConflito">Processo Conflito</Translate>
                </Label>
                <AvInput
                  id="conflito-processoConflito"
                  data-cy="processoConflito"
                  type="select"
                  className="form-control"
                  name="processoConflitoId"
                >
                  <option value="" key="0" />
                  {processoConflitos
                    ? processoConflitos.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/conflito" replace color="info">
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
  processoConflitos: storeState.processoConflito.entities,
  conflitoEntity: storeState.conflito.entity,
  loading: storeState.conflito.loading,
  updating: storeState.conflito.updating,
  updateSuccess: storeState.conflito.updateSuccess,
});

const mapDispatchToProps = {
  getProcessoConflitos,
  getEntity,
  updateEntity,
  setBlob,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ConflitoUpdate);
