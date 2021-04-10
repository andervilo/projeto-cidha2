import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './instrumento-internacional.reducer';
import { IInstrumentoInternacional } from 'app/shared/model/instrumento-internacional.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IInstrumentoInternacionalDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const InstrumentoInternacionalDetail = (props: IInstrumentoInternacionalDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { instrumentoInternacionalEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="cidhaApp.instrumentoInternacional.detail.title">InstrumentoInternacional</Translate> [
          <b>{instrumentoInternacionalEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="instrumentoInternacionalCitadoDescricao">
              <Translate contentKey="cidhaApp.instrumentoInternacional.instrumentoInternacionalCitadoDescricao">
                Instrumento Internacional Citado Descricao
              </Translate>
            </span>
          </dt>
          <dd>{instrumentoInternacionalEntity.instrumentoInternacionalCitadoDescricao}</dd>
          <dt>
            <span id="folhasInstrumentoInternacional">
              <Translate contentKey="cidhaApp.instrumentoInternacional.folhasInstrumentoInternacional">
                Folhas Instrumento Internacional
              </Translate>
            </span>
          </dt>
          <dd>{instrumentoInternacionalEntity.folhasInstrumentoInternacional}</dd>
          <dt>
            <span id="instrumentoInternacionalSugerido">
              <Translate contentKey="cidhaApp.instrumentoInternacional.instrumentoInternacionalSugerido">
                Instrumento Internacional Sugerido
              </Translate>
            </span>
          </dt>
          <dd>{instrumentoInternacionalEntity.instrumentoInternacionalSugerido}</dd>
        </dl>
        <Button tag={Link} to="/instrumento-internacional" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/instrumento-internacional/${instrumentoInternacionalEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ instrumentoInternacional }: IRootState) => ({
  instrumentoInternacionalEntity: instrumentoInternacional.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(InstrumentoInternacionalDetail);
