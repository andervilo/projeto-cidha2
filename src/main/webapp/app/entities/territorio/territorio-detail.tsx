import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './territorio.reducer';
import { ITerritorio } from 'app/shared/model/territorio.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ITerritorioDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const TerritorioDetail = (props: ITerritorioDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { territorioEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="cidhaApp.territorio.detail.title">Territorio</Translate> [<b>{territorioEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="nome">
              <Translate contentKey="cidhaApp.territorio.nome">Nome</Translate>
            </span>
          </dt>
          <dd>{territorioEntity.nome}</dd>
        </dl>
        <Button tag={Link} to="/territorio" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/territorio/${territorioEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ territorio }: IRootState) => ({
  territorioEntity: territorio.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(TerritorioDetail);
