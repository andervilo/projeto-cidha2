import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './concessao-liminar-cassada.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IConcessaoLiminarCassadaDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ConcessaoLiminarCassadaDetail = (props: IConcessaoLiminarCassadaDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { concessaoLiminarCassadaEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="concessaoLiminarCassadaDetailsHeading">
          <Translate contentKey="cidhaApp.concessaoLiminarCassada.detail.title">ConcessaoLiminarCassada</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{concessaoLiminarCassadaEntity.id}</dd>
          <dt>
            <span id="descricao">
              <Translate contentKey="cidhaApp.concessaoLiminarCassada.descricao">Descricao</Translate>
            </span>
          </dt>
          <dd>{concessaoLiminarCassadaEntity.descricao}</dd>
          <dt>
            <Translate contentKey="cidhaApp.concessaoLiminarCassada.processo">Processo</Translate>
          </dt>
          <dd>{concessaoLiminarCassadaEntity.processo ? concessaoLiminarCassadaEntity.processo.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/concessao-liminar-cassada" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/concessao-liminar-cassada/${concessaoLiminarCassadaEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ concessaoLiminarCassada }: IRootState) => ({
  concessaoLiminarCassadaEntity: concessaoLiminarCassada.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ConcessaoLiminarCassadaDetail);
