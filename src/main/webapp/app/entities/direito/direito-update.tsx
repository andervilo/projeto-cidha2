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
import { getEntity, updateEntity, createEntity, setBlob, reset } from './direito.reducer';
import { IDireito } from 'app/shared/model/direito.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IDireitoUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const DireitoUpdate = (props: IDireitoUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { direitoEntity, processoConflitos, loading, updating } = props;

  const { descricao } = direitoEntity;

  const handleClose = () => {
    props.history.push('/direito' + props.location.search);
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
        ...direitoEntity,
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
          <h2 id="cidhaApp.direito.home.createOrEditLabel" data-cy="DireitoCreateUpdateHeading">
            <Translate contentKey="cidhaApp.direito.home.createOrEditLabel">Create or edit a Direito</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : direitoEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="direito-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="direito-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="descricaoLabel" for="direito-descricao">
                  <Translate contentKey="cidhaApp.direito.descricao">Descricao</Translate>
                </Label>
                <AvInput id="direito-descricao" data-cy="descricao" type="textarea" name="descricao" />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/direito" replace color="info">
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
  direitoEntity: storeState.direito.entity,
  loading: storeState.direito.loading,
  updating: storeState.direito.updating,
  updateSuccess: storeState.direito.updateSuccess,
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

export default connect(mapStateToProps, mapDispatchToProps)(DireitoUpdate);
