import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { ITerraIndigena } from 'app/shared/model/terra-indigena.model';
import { getEntities as getTerraIndigenas } from 'app/entities/terra-indigena/terra-indigena.reducer';
import { getEntity, updateEntity, createEntity, reset } from './etnia-indigena.reducer';
import { IEtniaIndigena } from 'app/shared/model/etnia-indigena.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IEtniaIndigenaUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const EtniaIndigenaUpdate = (props: IEtniaIndigenaUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { etniaIndigenaEntity, terraIndigenas, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/etnia-indigena' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getTerraIndigenas();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...etniaIndigenaEntity,
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
          <h2 id="cidhaApp.etniaIndigena.home.createOrEditLabel" data-cy="EtniaIndigenaCreateUpdateHeading">
            <Translate contentKey="cidhaApp.etniaIndigena.home.createOrEditLabel">Create or edit a EtniaIndigena</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : etniaIndigenaEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="etnia-indigena-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="etnia-indigena-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="nomeLabel" for="etnia-indigena-nome">
                  <Translate contentKey="cidhaApp.etniaIndigena.nome">Nome</Translate>
                </Label>
                <AvField id="etnia-indigena-nome" data-cy="nome" type="text" name="nome" />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/etnia-indigena" replace color="info">
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
  terraIndigenas: storeState.terraIndigena.entities,
  etniaIndigenaEntity: storeState.etniaIndigena.entity,
  loading: storeState.etniaIndigena.loading,
  updating: storeState.etniaIndigena.updating,
  updateSuccess: storeState.etniaIndigena.updateSuccess,
});

const mapDispatchToProps = {
  getTerraIndigenas,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(EtniaIndigenaUpdate);
