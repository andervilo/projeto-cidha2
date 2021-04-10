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
import { getEntity, updateEntity, createEntity, setBlob, reset } from './instrumento-internacional.reducer';
import { IInstrumentoInternacional } from 'app/shared/model/instrumento-internacional.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IInstrumentoInternacionalUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const InstrumentoInternacionalUpdate = (props: IInstrumentoInternacionalUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { instrumentoInternacionalEntity, problemaJuridicos, loading, updating } = props;

  const { instrumentoInternacionalCitadoDescricao, instrumentoInternacionalSugerido } = instrumentoInternacionalEntity;

  const handleClose = () => {
    props.history.push('/instrumento-internacional' + props.location.search);
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
        ...instrumentoInternacionalEntity,
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
          <h2 id="cidhaApp.instrumentoInternacional.home.createOrEditLabel" data-cy="InstrumentoInternacionalCreateUpdateHeading">
            <Translate contentKey="cidhaApp.instrumentoInternacional.home.createOrEditLabel">
              Create or edit a InstrumentoInternacional
            </Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : instrumentoInternacionalEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="instrumento-internacional-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="instrumento-internacional-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label
                  id="instrumentoInternacionalCitadoDescricaoLabel"
                  for="instrumento-internacional-instrumentoInternacionalCitadoDescricao"
                >
                  <Translate contentKey="cidhaApp.instrumentoInternacional.instrumentoInternacionalCitadoDescricao">
                    Instrumento Internacional Citado Descricao
                  </Translate>
                </Label>
                <AvInput
                  id="instrumento-internacional-instrumentoInternacionalCitadoDescricao"
                  data-cy="instrumentoInternacionalCitadoDescricao"
                  type="textarea"
                  name="instrumentoInternacionalCitadoDescricao"
                />
              </AvGroup>
              <AvGroup>
                <Label id="folhasInstrumentoInternacionalLabel" for="instrumento-internacional-folhasInstrumentoInternacional">
                  <Translate contentKey="cidhaApp.instrumentoInternacional.folhasInstrumentoInternacional">
                    Folhas Instrumento Internacional
                  </Translate>
                </Label>
                <AvField
                  id="instrumento-internacional-folhasInstrumentoInternacional"
                  data-cy="folhasInstrumentoInternacional"
                  type="text"
                  name="folhasInstrumentoInternacional"
                />
              </AvGroup>
              <AvGroup>
                <Label id="instrumentoInternacionalSugeridoLabel" for="instrumento-internacional-instrumentoInternacionalSugerido">
                  <Translate contentKey="cidhaApp.instrumentoInternacional.instrumentoInternacionalSugerido">
                    Instrumento Internacional Sugerido
                  </Translate>
                </Label>
                <AvInput
                  id="instrumento-internacional-instrumentoInternacionalSugerido"
                  data-cy="instrumentoInternacionalSugerido"
                  type="textarea"
                  name="instrumentoInternacionalSugerido"
                />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/instrumento-internacional" replace color="info">
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
  instrumentoInternacionalEntity: storeState.instrumentoInternacional.entity,
  loading: storeState.instrumentoInternacional.loading,
  updating: storeState.instrumentoInternacional.updating,
  updateSuccess: storeState.instrumentoInternacional.updateSuccess,
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

export default connect(mapStateToProps, mapDispatchToProps)(InstrumentoInternacionalUpdate);
