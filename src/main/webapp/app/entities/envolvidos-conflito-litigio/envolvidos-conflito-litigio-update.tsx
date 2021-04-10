import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { setFileData, byteSize, Translate, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IProcesso } from 'app/shared/model/processo.model';
import { getEntities as getProcessos } from 'app/entities/processo/processo.reducer';
import { getEntity, updateEntity, createEntity, setBlob, reset } from './envolvidos-conflito-litigio.reducer';
import { IEnvolvidosConflitoLitigio } from 'app/shared/model/envolvidos-conflito-litigio.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IEnvolvidosConflitoLitigioUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const EnvolvidosConflitoLitigioUpdate = (props: IEnvolvidosConflitoLitigioUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { envolvidosConflitoLitigioEntity, processos, loading, updating } = props;

  const { fonteInformacaoQtde, observacoes } = envolvidosConflitoLitigioEntity;

  const handleClose = () => {
    props.history.push('/envolvidos-conflito-litigio' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

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
        ...envolvidosConflitoLitigioEntity,
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
          <h2 id="cidhaApp.envolvidosConflitoLitigio.home.createOrEditLabel" data-cy="EnvolvidosConflitoLitigioCreateUpdateHeading">
            <Translate contentKey="cidhaApp.envolvidosConflitoLitigio.home.createOrEditLabel">
              Create or edit a EnvolvidosConflitoLitigio
            </Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : envolvidosConflitoLitigioEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="envolvidos-conflito-litigio-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="envolvidos-conflito-litigio-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="numeroIndividuosLabel" for="envolvidos-conflito-litigio-numeroIndividuos">
                  <Translate contentKey="cidhaApp.envolvidosConflitoLitigio.numeroIndividuos">Numero Individuos</Translate>
                </Label>
                <AvField
                  id="envolvidos-conflito-litigio-numeroIndividuos"
                  data-cy="numeroIndividuos"
                  type="string"
                  className="form-control"
                  name="numeroIndividuos"
                />
              </AvGroup>
              <AvGroup>
                <Label id="fonteInformacaoQtdeLabel" for="envolvidos-conflito-litigio-fonteInformacaoQtde">
                  <Translate contentKey="cidhaApp.envolvidosConflitoLitigio.fonteInformacaoQtde">Fonte Informacao Qtde</Translate>
                </Label>
                <AvInput
                  id="envolvidos-conflito-litigio-fonteInformacaoQtde"
                  data-cy="fonteInformacaoQtde"
                  type="textarea"
                  name="fonteInformacaoQtde"
                />
              </AvGroup>
              <AvGroup>
                <Label id="observacoesLabel" for="envolvidos-conflito-litigio-observacoes">
                  <Translate contentKey="cidhaApp.envolvidosConflitoLitigio.observacoes">Observacoes</Translate>
                </Label>
                <AvInput id="envolvidos-conflito-litigio-observacoes" data-cy="observacoes" type="textarea" name="observacoes" />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/envolvidos-conflito-litigio" replace color="info">
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
  envolvidosConflitoLitigioEntity: storeState.envolvidosConflitoLitigio.entity,
  loading: storeState.envolvidosConflitoLitigio.loading,
  updating: storeState.envolvidosConflitoLitigio.updating,
  updateSuccess: storeState.envolvidosConflitoLitigio.updateSuccess,
});

const mapDispatchToProps = {
  getProcessos,
  getEntity,
  updateEntity,
  setBlob,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(EnvolvidosConflitoLitigioUpdate);
