import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IProcesso } from 'app/shared/model/processo.model';
import { getEntities as getProcessos } from 'app/entities/processo/processo.reducer';
import { getEntity, updateEntity, createEntity, reset } from './embargo-declaracao.reducer';
import { IEmbargoDeclaracao } from 'app/shared/model/embargo-declaracao.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IEmbargoDeclaracaoUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const EmbargoDeclaracaoUpdate = (props: IEmbargoDeclaracaoUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { embargoDeclaracaoEntity, processos, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/embargo-declaracao' + props.location.search);
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
        ...embargoDeclaracaoEntity,
        ...values,
        processo: processos.find(it => it.id.toString() === values.processoId.toString()),
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
          <h2 id="cidhaApp.embargoDeclaracao.home.createOrEditLabel" data-cy="EmbargoDeclaracaoCreateUpdateHeading">
            <Translate contentKey="cidhaApp.embargoDeclaracao.home.createOrEditLabel">Create or edit a EmbargoDeclaracao</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : embargoDeclaracaoEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="embargo-declaracao-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="embargo-declaracao-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="descricaoLabel" for="embargo-declaracao-descricao">
                  <Translate contentKey="cidhaApp.embargoDeclaracao.descricao">Descricao</Translate>
                </Label>
                <AvField id="embargo-declaracao-descricao" data-cy="descricao" type="text" name="descricao" />
              </AvGroup>
              <AvGroup>
                <Label for="embargo-declaracao-processo">
                  <Translate contentKey="cidhaApp.embargoDeclaracao.processo">Processo</Translate>
                </Label>
                <AvInput id="embargo-declaracao-processo" data-cy="processo" type="select" className="form-control" name="processoId">
                  <option value="" key="0" />
                  {processos
                    ? processos.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/embargo-declaracao" replace color="info">
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
  processos: storeState.processo.entities,
  embargoDeclaracaoEntity: storeState.embargoDeclaracao.entity,
  loading: storeState.embargoDeclaracao.loading,
  updating: storeState.embargoDeclaracao.updating,
  updateSuccess: storeState.embargoDeclaracao.updateSuccess,
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

export default connect(mapStateToProps, mapDispatchToProps)(EmbargoDeclaracaoUpdate);
