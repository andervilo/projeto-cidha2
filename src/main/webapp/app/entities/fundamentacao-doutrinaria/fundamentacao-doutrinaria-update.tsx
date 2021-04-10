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
import { getEntity, updateEntity, createEntity, setBlob, reset } from './fundamentacao-doutrinaria.reducer';
import { IFundamentacaoDoutrinaria } from 'app/shared/model/fundamentacao-doutrinaria.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IFundamentacaoDoutrinariaUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const FundamentacaoDoutrinariaUpdate = (props: IFundamentacaoDoutrinariaUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { fundamentacaoDoutrinariaEntity, problemaJuridicos, loading, updating } = props;

  const { fundamentacaoDoutrinariaCitada, fundamentacaoDoutrinariaSugerida } = fundamentacaoDoutrinariaEntity;

  const handleClose = () => {
    props.history.push('/fundamentacao-doutrinaria' + props.location.search);
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
        ...fundamentacaoDoutrinariaEntity,
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
          <h2 id="cidhaApp.fundamentacaoDoutrinaria.home.createOrEditLabel" data-cy="FundamentacaoDoutrinariaCreateUpdateHeading">
            <Translate contentKey="cidhaApp.fundamentacaoDoutrinaria.home.createOrEditLabel">
              Create or edit a FundamentacaoDoutrinaria
            </Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : fundamentacaoDoutrinariaEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="fundamentacao-doutrinaria-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="fundamentacao-doutrinaria-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="fundamentacaoDoutrinariaCitadaLabel" for="fundamentacao-doutrinaria-fundamentacaoDoutrinariaCitada">
                  <Translate contentKey="cidhaApp.fundamentacaoDoutrinaria.fundamentacaoDoutrinariaCitada">
                    Fundamentacao Doutrinaria Citada
                  </Translate>
                </Label>
                <AvInput
                  id="fundamentacao-doutrinaria-fundamentacaoDoutrinariaCitada"
                  data-cy="fundamentacaoDoutrinariaCitada"
                  type="textarea"
                  name="fundamentacaoDoutrinariaCitada"
                />
              </AvGroup>
              <AvGroup>
                <Label id="folhasFundamentacaoDoutrinariaLabel" for="fundamentacao-doutrinaria-folhasFundamentacaoDoutrinaria">
                  <Translate contentKey="cidhaApp.fundamentacaoDoutrinaria.folhasFundamentacaoDoutrinaria">
                    Folhas Fundamentacao Doutrinaria
                  </Translate>
                </Label>
                <AvField
                  id="fundamentacao-doutrinaria-folhasFundamentacaoDoutrinaria"
                  data-cy="folhasFundamentacaoDoutrinaria"
                  type="text"
                  name="folhasFundamentacaoDoutrinaria"
                />
              </AvGroup>
              <AvGroup>
                <Label id="fundamentacaoDoutrinariaSugeridaLabel" for="fundamentacao-doutrinaria-fundamentacaoDoutrinariaSugerida">
                  <Translate contentKey="cidhaApp.fundamentacaoDoutrinaria.fundamentacaoDoutrinariaSugerida">
                    Fundamentacao Doutrinaria Sugerida
                  </Translate>
                </Label>
                <AvInput
                  id="fundamentacao-doutrinaria-fundamentacaoDoutrinariaSugerida"
                  data-cy="fundamentacaoDoutrinariaSugerida"
                  type="textarea"
                  name="fundamentacaoDoutrinariaSugerida"
                />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/fundamentacao-doutrinaria" replace color="info">
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
  fundamentacaoDoutrinariaEntity: storeState.fundamentacaoDoutrinaria.entity,
  loading: storeState.fundamentacaoDoutrinaria.loading,
  updating: storeState.fundamentacaoDoutrinaria.updating,
  updateSuccess: storeState.fundamentacaoDoutrinaria.updateSuccess,
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

export default connect(mapStateToProps, mapDispatchToProps)(FundamentacaoDoutrinariaUpdate);
