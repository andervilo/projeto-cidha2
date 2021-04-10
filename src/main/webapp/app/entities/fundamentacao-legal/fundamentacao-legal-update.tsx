import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { setFileData, byteSize, Translate, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IProblemaJuridico } from 'app/shared/model/problema-juridico.model';
import { getEntities as getProblemaJuridicos } from 'app/entities/problema-juridico/problema-juridico.reducer';
import { getEntity, updateEntity, createEntity, setBlob, reset } from './fundamentacao-legal.reducer';
import { IFundamentacaoLegal } from 'app/shared/model/fundamentacao-legal.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IFundamentacaoLegalUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const FundamentacaoLegalUpdate = (props: IFundamentacaoLegalUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { fundamentacaoLegalEntity, problemaJuridicos, loading, updating } = props;

  const { fundamentacaoLegal, fundamentacaoLegalSugerida } = fundamentacaoLegalEntity;

  const handleClose = () => {
    props.history.push('/fundamentacao-legal' + props.location.search);
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
        ...fundamentacaoLegalEntity,
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
          <h2 id="cidhaApp.fundamentacaoLegal.home.createOrEditLabel" data-cy="FundamentacaoLegalCreateUpdateHeading">
            <Translate contentKey="cidhaApp.fundamentacaoLegal.home.createOrEditLabel">Create or edit a FundamentacaoLegal</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : fundamentacaoLegalEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="fundamentacao-legal-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="fundamentacao-legal-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="fundamentacaoLegalLabel" for="fundamentacao-legal-fundamentacaoLegal">
                  <Translate contentKey="cidhaApp.fundamentacaoLegal.fundamentacaoLegal">Fundamentacao Legal</Translate>
                </Label>
                <AvInput
                  id="fundamentacao-legal-fundamentacaoLegal"
                  data-cy="fundamentacaoLegal"
                  type="textarea"
                  name="fundamentacaoLegal"
                />
              </AvGroup>
              <AvGroup>
                <Label id="folhasFundamentacaoLegalLabel" for="fundamentacao-legal-folhasFundamentacaoLegal">
                  <Translate contentKey="cidhaApp.fundamentacaoLegal.folhasFundamentacaoLegal">Folhas Fundamentacao Legal</Translate>
                </Label>
                <AvField
                  id="fundamentacao-legal-folhasFundamentacaoLegal"
                  data-cy="folhasFundamentacaoLegal"
                  type="text"
                  name="folhasFundamentacaoLegal"
                />
              </AvGroup>
              <AvGroup>
                <Label id="fundamentacaoLegalSugeridaLabel" for="fundamentacao-legal-fundamentacaoLegalSugerida">
                  <Translate contentKey="cidhaApp.fundamentacaoLegal.fundamentacaoLegalSugerida">Fundamentacao Legal Sugerida</Translate>
                </Label>
                <AvInput
                  id="fundamentacao-legal-fundamentacaoLegalSugerida"
                  data-cy="fundamentacaoLegalSugerida"
                  type="textarea"
                  name="fundamentacaoLegalSugerida"
                />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/fundamentacao-legal" replace color="info">
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
  problemaJuridicos: storeState.problemaJuridico.entities,
  fundamentacaoLegalEntity: storeState.fundamentacaoLegal.entity,
  loading: storeState.fundamentacaoLegal.loading,
  updating: storeState.fundamentacaoLegal.updating,
  updateSuccess: storeState.fundamentacaoLegal.updateSuccess,
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

export default connect(mapStateToProps, mapDispatchToProps)(FundamentacaoLegalUpdate);
