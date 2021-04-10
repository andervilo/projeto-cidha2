import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { setFileData, byteSize, Translate, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { ITipoRecurso } from 'app/shared/model/tipo-recurso.model';
import { getEntities as getTipoRecursos } from 'app/entities/tipo-recurso/tipo-recurso.reducer';
import { IOpcaoRecurso } from 'app/shared/model/opcao-recurso.model';
import { getEntities as getOpcaoRecursos } from 'app/entities/opcao-recurso/opcao-recurso.reducer';
import { IProcesso } from 'app/shared/model/processo.model';
import { getEntities as getProcessos } from 'app/entities/processo/processo.reducer';
import { getEntity, updateEntity, createEntity, setBlob, reset } from './recurso.reducer';
import { IRecurso } from 'app/shared/model/recurso.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IRecursoUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const RecursoUpdate = (props: IRecursoUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { recursoEntity, tipoRecursos, opcaoRecursos, processos, loading, updating } = props;

  const { observacoes } = recursoEntity;

  const handleClose = () => {
    props.history.push('/recurso' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getTipoRecursos();
    props.getOpcaoRecursos();
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
        ...recursoEntity,
        ...values,
        tipoRecurso: tipoRecursos.find(it => it.id.toString() === values.tipoRecursoId.toString()),
        opcaoRecurso: opcaoRecursos.find(it => it.id.toString() === values.opcaoRecursoId.toString()),
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
          <h2 id="cidhaApp.recurso.home.createOrEditLabel" data-cy="RecursoCreateUpdateHeading">
            <Translate contentKey="cidhaApp.recurso.home.createOrEditLabel">Create or edit a Recurso</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : recursoEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="recurso-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="recurso-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="observacoesLabel" for="recurso-observacoes">
                  <Translate contentKey="cidhaApp.recurso.observacoes">Observacoes</Translate>
                </Label>
                <AvInput id="recurso-observacoes" data-cy="observacoes" type="textarea" name="observacoes" />
              </AvGroup>
              <AvGroup>
                <Label for="recurso-tipoRecurso">
                  <Translate contentKey="cidhaApp.recurso.tipoRecurso">Tipo Recurso</Translate>
                </Label>
                <AvInput id="recurso-tipoRecurso" data-cy="tipoRecurso" type="select" className="form-control" name="tipoRecursoId">
                  <option value="" key="0" />
                  {tipoRecursos
                    ? tipoRecursos.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.descricao}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="recurso-opcaoRecurso">
                  <Translate contentKey="cidhaApp.recurso.opcaoRecurso">Opcao Recurso</Translate>
                </Label>
                <AvInput id="recurso-opcaoRecurso" data-cy="opcaoRecurso" type="select" className="form-control" name="opcaoRecursoId">
                  <option value="" key="0" />
                  {opcaoRecursos
                    ? opcaoRecursos.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.descricao}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="recurso-processo">
                  <Translate contentKey="cidhaApp.recurso.processo">Processo</Translate>
                </Label>
                <AvInput id="recurso-processo" data-cy="processo" type="select" className="form-control" name="processoId">
                  <option value="" key="0" />
                  {processos
                    ? processos.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.oficio}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/recurso" replace color="info">
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
  tipoRecursos: storeState.tipoRecurso.entities,
  opcaoRecursos: storeState.opcaoRecurso.entities,
  processos: storeState.processo.entities,
  recursoEntity: storeState.recurso.entity,
  loading: storeState.recurso.loading,
  updating: storeState.recurso.updating,
  updateSuccess: storeState.recurso.updateSuccess,
});

const mapDispatchToProps = {
  getTipoRecursos,
  getOpcaoRecursos,
  getProcessos,
  getEntity,
  updateEntity,
  setBlob,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(RecursoUpdate);
