import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './jurisprudencia.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IJurisprudenciaDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const JurisprudenciaDetail = (props: IJurisprudenciaDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { jurisprudenciaEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="jurisprudenciaDetailsHeading">
          <Translate contentKey="cidhaApp.jurisprudencia.detail.title">Jurisprudencia</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{jurisprudenciaEntity.id}</dd>
          <dt>
            <span id="jurisprudenciaCitadaDescricao">
              <Translate contentKey="cidhaApp.jurisprudencia.jurisprudenciaCitadaDescricao">Jurisprudencia Citada Descricao</Translate>
            </span>
          </dt>
          <dd>{jurisprudenciaEntity.jurisprudenciaCitadaDescricao}</dd>
          <dt>
            <span id="folhasJurisprudenciaCitada">
              <Translate contentKey="cidhaApp.jurisprudencia.folhasJurisprudenciaCitada">Folhas Jurisprudencia Citada</Translate>
            </span>
          </dt>
          <dd>{jurisprudenciaEntity.folhasJurisprudenciaCitada}</dd>
          <dt>
            <span id="jurisprudenciaSugerida">
              <Translate contentKey="cidhaApp.jurisprudencia.jurisprudenciaSugerida">Jurisprudencia Sugerida</Translate>
            </span>
          </dt>
          <dd>{jurisprudenciaEntity.jurisprudenciaSugerida}</dd>
        </dl>
        <Button tag={Link} to="/jurisprudencia" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/jurisprudencia/${jurisprudenciaEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ jurisprudencia }: IRootState) => ({
  jurisprudenciaEntity: jurisprudencia.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(JurisprudenciaDetail);
