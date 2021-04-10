import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './direito.reducer';
import { IDireito } from 'app/shared/model/direito.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IDireitoDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const DireitoDetail = (props: IDireitoDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { direitoEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="cidhaApp.direito.detail.title">Direito</Translate> [<b>{direitoEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="descricao">
              <Translate contentKey="cidhaApp.direito.descricao">Descricao</Translate>
            </span>
          </dt>
          <dd>{direitoEntity.descricao}</dd>
        </dl>
        <Button tag={Link} to="/direito" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/direito/${direitoEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ direito }: IRootState) => ({
  direitoEntity: direito.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(DireitoDetail);
