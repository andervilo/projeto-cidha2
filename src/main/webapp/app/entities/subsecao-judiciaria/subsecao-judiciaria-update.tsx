import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './subsecao-judiciaria.reducer';
import { ISubsecaoJudiciaria } from 'app/shared/model/subsecao-judiciaria.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ISubsecaoJudiciariaUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const SubsecaoJudiciariaUpdate = (props: ISubsecaoJudiciariaUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { subsecaoJudiciariaEntity, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/subsecao-judiciaria');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...subsecaoJudiciariaEntity,
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
          <h2 id="cidhaApp.subsecaoJudiciaria.home.createOrEditLabel" data-cy="SubsecaoJudiciariaCreateUpdateHeading">
            <Translate contentKey="cidhaApp.subsecaoJudiciaria.home.createOrEditLabel">Create or edit a SubsecaoJudiciaria</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : subsecaoJudiciariaEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="subsecao-judiciaria-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="subsecao-judiciaria-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="siglaLabel" for="subsecao-judiciaria-sigla">
                  <Translate contentKey="cidhaApp.subsecaoJudiciaria.sigla">Sigla</Translate>
                </Label>
                <AvField id="subsecao-judiciaria-sigla" data-cy="sigla" type="text" name="sigla" />
              </AvGroup>
              <AvGroup>
                <Label id="nomeLabel" for="subsecao-judiciaria-nome">
                  <Translate contentKey="cidhaApp.subsecaoJudiciaria.nome">Nome</Translate>
                </Label>
                <AvField id="subsecao-judiciaria-nome" data-cy="nome" type="text" name="nome" />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/subsecao-judiciaria" replace color="info">
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
  subsecaoJudiciariaEntity: storeState.subsecaoJudiciaria.entity,
  loading: storeState.subsecaoJudiciaria.loading,
  updating: storeState.subsecaoJudiciaria.updating,
  updateSuccess: storeState.subsecaoJudiciaria.updateSuccess,
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(SubsecaoJudiciariaUpdate);
