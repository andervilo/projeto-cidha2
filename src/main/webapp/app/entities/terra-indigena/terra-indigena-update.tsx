import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { setFileData, byteSize, Translate, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IEtniaIndigena } from 'app/shared/model/etnia-indigena.model';
import { getEntities as getEtniaIndigenas } from 'app/entities/etnia-indigena/etnia-indigena.reducer';
import { IProcesso } from 'app/shared/model/processo.model';
import { getEntities as getProcessos } from 'app/entities/processo/processo.reducer';
import { getEntity, updateEntity, createEntity, setBlob, reset } from './terra-indigena.reducer';
import { ITerraIndigena } from 'app/shared/model/terra-indigena.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ITerraIndigenaUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const TerraIndigenaUpdate = (props: ITerraIndigenaUpdateProps) => {
  const [idsetnia, setIdsetnia] = useState([]);
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { terraIndigenaEntity, etniaIndigenas, processos, loading, updating } = props;

  const { descricao } = terraIndigenaEntity;

  const handleClose = () => {
    props.history.push('/terra-indigena' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getEtniaIndigenas();
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
        ...terraIndigenaEntity,
        ...values,
        etnias: mapIdList(values.etnias),
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
          <h2 id="cidhaApp.terraIndigena.home.createOrEditLabel" data-cy="TerraIndigenaCreateUpdateHeading">
            <Translate contentKey="cidhaApp.terraIndigena.home.createOrEditLabel">Create or edit a TerraIndigena</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : terraIndigenaEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="terra-indigena-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="terra-indigena-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="descricaoLabel" for="terra-indigena-descricao">
                  <Translate contentKey="cidhaApp.terraIndigena.descricao">Descricao</Translate>
                </Label>
                <AvInput id="terra-indigena-descricao" data-cy="descricao" type="textarea" name="descricao" />
              </AvGroup>
              <AvGroup>
                <Label for="terra-indigena-etnia">
                  <Translate contentKey="cidhaApp.terraIndigena.etnia">Etnia</Translate>
                </Label>
                <AvInput
                  id="terra-indigena-etnia"
                  data-cy="etnia"
                  type="select"
                  multiple
                  className="form-control"
                  name="etnias"
                  value={!isNew && terraIndigenaEntity.etnias && terraIndigenaEntity.etnias.map(e => e.id)}
                >
                  <option value="" key="0" />
                  {etniaIndigenas
                    ? etniaIndigenas.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.nome}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/terra-indigena" replace color="info">
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
  etniaIndigenas: storeState.etniaIndigena.entities,
  processos: storeState.processo.entities,
  terraIndigenaEntity: storeState.terraIndigena.entity,
  loading: storeState.terraIndigena.loading,
  updating: storeState.terraIndigena.updating,
  updateSuccess: storeState.terraIndigena.updateSuccess,
});

const mapDispatchToProps = {
  getEtniaIndigenas,
  getProcessos,
  getEntity,
  updateEntity,
  setBlob,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(TerraIndigenaUpdate);
