import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './concessao-liminar.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IConcessaoLiminarDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ConcessaoLiminarDetail = (props: IConcessaoLiminarDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { concessaoLiminarEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="concessaoLiminarDetailsHeading">
          <Translate contentKey="cidhaApp.concessaoLiminar.detail.title">ConcessaoLiminar</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{concessaoLiminarEntity.id}</dd>
          <dt>
            <span id="descricao">
              <Translate contentKey="cidhaApp.concessaoLiminar.descricao">Descricao</Translate>
            </span>
          </dt>
          <dd>{concessaoLiminarEntity.descricao}</dd>
          <dt>
            <Translate contentKey="cidhaApp.concessaoLiminar.processo">Processo</Translate>
          </dt>
          <dd>{concessaoLiminarEntity.processo ? concessaoLiminarEntity.processo.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/concessao-liminar" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/concessao-liminar/${concessaoLiminarEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ concessaoLiminar }: IRootState) => ({
  concessaoLiminarEntity: concessaoLiminar.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ConcessaoLiminarDetail);
