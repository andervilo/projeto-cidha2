import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { ITipoRepresentante } from 'app/shared/model/tipo-representante.model';
import { getEntities as getTipoRepresentantes } from 'app/entities/tipo-representante/tipo-representante.reducer';
import { IParteInteresssada } from 'app/shared/model/parte-interesssada.model';
import { getEntities as getParteInteresssadas } from 'app/entities/parte-interesssada/parte-interesssada.reducer';
import { getEntity, updateEntity, createEntity, reset } from './representante-legal.reducer';
import { IRepresentanteLegal } from 'app/shared/model/representante-legal.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IRepresentanteLegalUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const RepresentanteLegalUpdate = (props: IRepresentanteLegalUpdateProps) => {
  const [tipoRepresentanteId, setTipoRepresentanteId] = useState('0');
  const [processoConflitoId, setProcessoConflitoId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { representanteLegalEntity, tipoRepresentantes, parteInteresssadas, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/representante-legal' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getTipoRepresentantes();
    props.getParteInteresssadas();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...representanteLegalEntity,
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
          <h2 id="cidhaApp.representanteLegal.home.createOrEditLabel">
            <Translate contentKey="cidhaApp.representanteLegal.home.createOrEditLabel">Create or edit a RepresentanteLegal</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : representanteLegalEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="representante-legal-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="representante-legal-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="nomeLabel" for="representante-legal-nome">
                  <Translate contentKey="cidhaApp.representanteLegal.nome">Nome</Translate>
                </Label>
                <AvField id="representante-legal-nome" type="text" name="nome" />
              </AvGroup>
              <AvGroup>
                <Label for="representante-legal-tipoRepresentante">
                  <Translate contentKey="cidhaApp.representanteLegal.tipoRepresentante">Tipo Representante</Translate>
                </Label>
                <AvInput id="representante-legal-tipoRepresentante" type="select" className="form-control" name="tipoRepresentante.id">
                  <option value="" key="0" />
                  {tipoRepresentantes
                    ? tipoRepresentantes.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.descricao}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/representante-legal" replace color="info">
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
  tipoRepresentantes: storeState.tipoRepresentante.entities,
  parteInteresssadas: storeState.parteInteresssada.entities,
  representanteLegalEntity: storeState.representanteLegal.entity,
  loading: storeState.representanteLegal.loading,
  updating: storeState.representanteLegal.updating,
  updateSuccess: storeState.representanteLegal.updateSuccess,
});

const mapDispatchToProps = {
  getTipoRepresentantes,
  getParteInteresssadas,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(RepresentanteLegalUpdate);
