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
import { getEntity, updateEntity, createEntity, reset } from './atividade-exploracao-ilegal.reducer';
import { IAtividadeExploracaoIlegal } from 'app/shared/model/atividade-exploracao-ilegal.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IAtividadeExploracaoIlegalUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const AtividadeExploracaoIlegalUpdate = (props: IAtividadeExploracaoIlegalUpdateProps) => {
  const [processoId, setProcessoId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { atividadeExploracaoIlegalEntity, processos, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/atividade-exploracao-ilegal' + props.location.search);
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
        ...atividadeExploracaoIlegalEntity,
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
          <h2 id="cidhaApp.atividadeExploracaoIlegal.home.createOrEditLabel">
            <Translate contentKey="cidhaApp.atividadeExploracaoIlegal.home.createOrEditLabel">
              Create or edit a AtividadeExploracaoIlegal
            </Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : atividadeExploracaoIlegalEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="atividade-exploracao-ilegal-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="atividade-exploracao-ilegal-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="descricaoLabel" for="atividade-exploracao-ilegal-descricao">
                  <Translate contentKey="cidhaApp.atividadeExploracaoIlegal.descricao">Descricao</Translate>
                </Label>
                <AvField id="atividade-exploracao-ilegal-descricao" type="text" name="descricao" />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/atividade-exploracao-ilegal" replace color="info">
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
  atividadeExploracaoIlegalEntity: storeState.atividadeExploracaoIlegal.entity,
  loading: storeState.atividadeExploracaoIlegal.loading,
  updating: storeState.atividadeExploracaoIlegal.updating,
  updateSuccess: storeState.atividadeExploracaoIlegal.updateSuccess,
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

export default connect(mapStateToProps, mapDispatchToProps)(AtividadeExploracaoIlegalUpdate);
