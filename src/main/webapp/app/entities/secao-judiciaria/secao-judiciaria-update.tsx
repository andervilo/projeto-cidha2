import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { ISubsecaoJudiciaria } from 'app/shared/model/subsecao-judiciaria.model';
import { getEntities as getSubsecaoJudiciarias } from 'app/entities/subsecao-judiciaria/subsecao-judiciaria.reducer';
import { getEntity, updateEntity, createEntity, reset } from './secao-judiciaria.reducer';
import { ISecaoJudiciaria } from 'app/shared/model/secao-judiciaria.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ISecaoJudiciariaUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const SecaoJudiciariaUpdate = (props: ISecaoJudiciariaUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { secaoJudiciariaEntity, subsecaoJudiciarias, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/secao-judiciaria');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getSubsecaoJudiciarias();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...secaoJudiciariaEntity,
        ...values,
        subsecaoJudiciaria: subsecaoJudiciarias.find(it => it.id.toString() === values.subsecaoJudiciariaId.toString()),
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
          <h2 id="cidhaApp.secaoJudiciaria.home.createOrEditLabel" data-cy="SecaoJudiciariaCreateUpdateHeading">
            <Translate contentKey="cidhaApp.secaoJudiciaria.home.createOrEditLabel">Create or edit a SecaoJudiciaria</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : secaoJudiciariaEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="secao-judiciaria-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="secao-judiciaria-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="siglaLabel" for="secao-judiciaria-sigla">
                  <Translate contentKey="cidhaApp.secaoJudiciaria.sigla">Sigla</Translate>
                </Label>
                <AvField id="secao-judiciaria-sigla" data-cy="sigla" type="text" name="sigla" />
              </AvGroup>
              <AvGroup>
                <Label id="nomeLabel" for="secao-judiciaria-nome">
                  <Translate contentKey="cidhaApp.secaoJudiciaria.nome">Nome</Translate>
                </Label>
                <AvField id="secao-judiciaria-nome" data-cy="nome" type="text" name="nome" />
              </AvGroup>
              <AvGroup>
                <Label for="secao-judiciaria-subsecaoJudiciaria">
                  <Translate contentKey="cidhaApp.secaoJudiciaria.subsecaoJudiciaria">Subsecao Judiciaria</Translate>
                </Label>
                <AvInput
                  id="secao-judiciaria-subsecaoJudiciaria"
                  data-cy="subsecaoJudiciaria"
                  type="select"
                  className="form-control"
                  name="subsecaoJudiciariaId"
                >
                  <option value="" key="0" />
                  {subsecaoJudiciarias
                    ? subsecaoJudiciarias.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.nome}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/secao-judiciaria" replace color="info">
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
  subsecaoJudiciarias: storeState.subsecaoJudiciaria.entities,
  secaoJudiciariaEntity: storeState.secaoJudiciaria.entity,
  loading: storeState.secaoJudiciaria.loading,
  updating: storeState.secaoJudiciaria.updating,
  updateSuccess: storeState.secaoJudiciaria.updateSuccess,
});

const mapDispatchToProps = {
  getSubsecaoJudiciarias,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(SecaoJudiciariaUpdate);
