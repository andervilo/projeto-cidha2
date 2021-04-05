import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IRepresentanteLegal } from 'app/shared/model/representante-legal.model';
import { getEntities as getRepresentanteLegals } from 'app/entities/representante-legal/representante-legal.reducer';
import { IProcesso } from 'app/shared/model/processo.model';
import { getEntities as getProcessos } from 'app/entities/processo/processo.reducer';
import { getEntity, updateEntity, createEntity, reset } from './parte-interesssada.reducer';
import { IParteInteresssada } from 'app/shared/model/parte-interesssada.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IParteInteresssadaUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ParteInteresssadaUpdate = (props: IParteInteresssadaUpdateProps) => {
  const [idsrepresentanteLegal, setIdsrepresentanteLegal] = useState([]);
  const [processoId, setProcessoId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { parteInteresssadaEntity, representanteLegals, processos, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/parte-interesssada' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getRepresentanteLegals();
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
        ...parteInteresssadaEntity,
        ...values,
        representanteLegals: mapIdList(values.representanteLegals),
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
          <h2 id="cidhaApp.parteInteresssada.home.createOrEditLabel">
            <Translate contentKey="cidhaApp.parteInteresssada.home.createOrEditLabel">Create or edit a ParteInteresssada</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : parteInteresssadaEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="parte-interesssada-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="parte-interesssada-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="nomeLabel" for="parte-interesssada-nome">
                  <Translate contentKey="cidhaApp.parteInteresssada.nome">Nome</Translate>
                </Label>
                <AvField id="parte-interesssada-nome" type="text" name="nome" />
              </AvGroup>
              <AvGroup>
                <Label id="classificacaoLabel" for="parte-interesssada-classificacao">
                  <Translate contentKey="cidhaApp.parteInteresssada.classificacao">Classificacao</Translate>
                </Label>
                <AvField id="parte-interesssada-classificacao" type="text" name="classificacao" />
              </AvGroup>
              <AvGroup>
                <Label for="parte-interesssada-representanteLegal">
                  <Translate contentKey="cidhaApp.parteInteresssada.representanteLegal">Representante Legal</Translate>
                </Label>
                <AvInput
                  id="parte-interesssada-representanteLegal"
                  type="select"
                  multiple
                  className="form-control"
                  name="representanteLegals"
                  value={parteInteresssadaEntity.representanteLegals && parteInteresssadaEntity.representanteLegals.map(e => e.id)}
                >
                  <option value="" key="0" />
                  {representanteLegals
                    ? representanteLegals.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.nome}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/parte-interesssada" replace color="info">
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
  representanteLegals: storeState.representanteLegal.entities,
  processos: storeState.processo.entities,
  parteInteresssadaEntity: storeState.parteInteresssada.entity,
  loading: storeState.parteInteresssada.loading,
  updating: storeState.parteInteresssada.updating,
  updateSuccess: storeState.parteInteresssada.updateSuccess,
});

const mapDispatchToProps = {
  getRepresentanteLegals,
  getProcessos,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ParteInteresssadaUpdate);
