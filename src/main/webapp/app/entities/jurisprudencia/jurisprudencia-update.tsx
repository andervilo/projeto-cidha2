import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, setFileData, byteSize, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IProblemaJuridico } from 'app/shared/model/problema-juridico.model';
import { getEntities as getProblemaJuridicos } from 'app/entities/problema-juridico/problema-juridico.reducer';
import { getEntity, updateEntity, createEntity, setBlob, reset } from './jurisprudencia.reducer';
import { IJurisprudencia } from 'app/shared/model/jurisprudencia.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IJurisprudenciaUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const JurisprudenciaUpdate = (props: IJurisprudenciaUpdateProps) => {
  const [problemaJuridicoId, setProblemaJuridicoId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { jurisprudenciaEntity, problemaJuridicos, loading, updating } = props;

  const { jurisprudenciaCitadaDescricao, jurisprudenciaSugerida } = jurisprudenciaEntity;

  const handleClose = () => {
    props.history.push('/jurisprudencia' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getProblemaJuridicos();
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
        ...jurisprudenciaEntity,
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
          <h2 id="cidhaApp.jurisprudencia.home.createOrEditLabel">
            <Translate contentKey="cidhaApp.jurisprudencia.home.createOrEditLabel">Create or edit a Jurisprudencia</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : jurisprudenciaEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="jurisprudencia-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="jurisprudencia-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="jurisprudenciaCitadaDescricaoLabel" for="jurisprudencia-jurisprudenciaCitadaDescricao">
                  <Translate contentKey="cidhaApp.jurisprudencia.jurisprudenciaCitadaDescricao">Jurisprudencia Citada Descricao</Translate>
                </Label>
                <AvInput id="jurisprudencia-jurisprudenciaCitadaDescricao" type="textarea" name="jurisprudenciaCitadaDescricao" />
              </AvGroup>
              <AvGroup>
                <Label id="folhasJurisprudenciaCitadaLabel" for="jurisprudencia-folhasJurisprudenciaCitada">
                  <Translate contentKey="cidhaApp.jurisprudencia.folhasJurisprudenciaCitada">Folhas Jurisprudencia Citada</Translate>
                </Label>
                <AvField id="jurisprudencia-folhasJurisprudenciaCitada" type="text" name="folhasJurisprudenciaCitada" />
              </AvGroup>
              <AvGroup>
                <Label id="jurisprudenciaSugeridaLabel" for="jurisprudencia-jurisprudenciaSugerida">
                  <Translate contentKey="cidhaApp.jurisprudencia.jurisprudenciaSugerida">Jurisprudencia Sugerida</Translate>
                </Label>
                <AvInput id="jurisprudencia-jurisprudenciaSugerida" type="textarea" name="jurisprudenciaSugerida" />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/jurisprudencia" replace color="info">
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
  problemaJuridicos: storeState.problemaJuridico.entities,
  jurisprudenciaEntity: storeState.jurisprudencia.entity,
  loading: storeState.jurisprudencia.loading,
  updating: storeState.jurisprudencia.updating,
  updateSuccess: storeState.jurisprudencia.updateSuccess,
});

const mapDispatchToProps = {
  getProblemaJuridicos,
  getEntity,
  updateEntity,
  setBlob,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(JurisprudenciaUpdate);
