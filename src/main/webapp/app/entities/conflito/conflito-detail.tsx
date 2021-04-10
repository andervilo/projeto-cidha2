import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './conflito.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IConflitoDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ConflitoDetail = (props: IConflitoDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { conflitoEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="conflitoDetailsHeading">
          <Translate contentKey="cidhaApp.conflito.detail.title">Conflito</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{conflitoEntity.id}</dd>
          <dt>
            <span id="descricao">
              <Translate contentKey="cidhaApp.conflito.descricao">Descricao</Translate>
            </span>
          </dt>
          <dd>{conflitoEntity.descricao}</dd>
          <dt>
            <Translate contentKey="cidhaApp.conflito.processoConflito">Processo Conflito</Translate>
          </dt>
          <dd>{conflitoEntity.processoConflito ? conflitoEntity.processoConflito.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/conflito" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/conflito/${conflitoEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ conflito }: IRootState) => ({
  conflitoEntity: conflito.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ConflitoDetail);
