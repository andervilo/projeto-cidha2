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
import { getEntity, updateEntity, createEntity, reset } from './municipio.reducer';
import { IMunicipio } from 'app/shared/model/municipio.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IMunicipioUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const MunicipioUpdate = (props: IMunicipioUpdateProps) => {
  const [processoId, setProcessoId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { municipioEntity, processos, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/municipio' + props.location.search);
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
        ...municipioEntity,
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
          <h2 id="cidhaApp.municipio.home.createOrEditLabel">
            <Translate contentKey="cidhaApp.municipio.home.createOrEditLabel">Create or edit a Municipio</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : municipioEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="municipio-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="municipio-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup check>
                <Label id="amazoniaLegalLabel">
                  <AvInput id="municipio-amazoniaLegal" type="checkbox" className="form-check-input" name="amazoniaLegal" />
                  <Translate contentKey="cidhaApp.municipio.amazoniaLegal">Amazonia Legal</Translate>
                </Label>
              </AvGroup>
              <AvGroup>
                <Label id="codigoIbgeLabel" for="municipio-codigoIbge">
                  <Translate contentKey="cidhaApp.municipio.codigoIbge">Codigo Ibge</Translate>
                </Label>
                <AvField id="municipio-codigoIbge" type="string" className="form-control" name="codigoIbge" />
              </AvGroup>
              <AvGroup>
                <Label id="estadoLabel" for="municipio-estado">
                  <Translate contentKey="cidhaApp.municipio.estado">Estado</Translate>
                </Label>
                <AvField id="municipio-estado" type="text" name="estado" />
              </AvGroup>
              <AvGroup>
                <Label id="nomeLabel" for="municipio-nome">
                  <Translate contentKey="cidhaApp.municipio.nome">Nome</Translate>
                </Label>
                <AvField id="municipio-nome" type="text" name="nome" />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/municipio" replace color="info">
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
  municipioEntity: storeState.municipio.entity,
  loading: storeState.municipio.loading,
  updating: storeState.municipio.updating,
  updateSuccess: storeState.municipio.updateSuccess,
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

export default connect(mapStateToProps, mapDispatchToProps)(MunicipioUpdate);
