import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './quilombo.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IQuilomboDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const QuilomboDetail = (props: IQuilomboDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { quilomboEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="quilomboDetailsHeading">
          <Translate contentKey="cidhaApp.quilombo.detail.title">Quilombo</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{quilomboEntity.id}</dd>
          <dt>
            <span id="nome">
              <Translate contentKey="cidhaApp.quilombo.nome">Nome</Translate>
            </span>
          </dt>
          <dd>{quilomboEntity.nome}</dd>
          <dt>
            <span id="tipoQuilombo">
              <Translate contentKey="cidhaApp.quilombo.tipoQuilombo">Tipo Quilombo</Translate>
            </span>
          </dt>
          <dd>{quilomboEntity.tipoQuilombo}</dd>
        </dl>
        <Button tag={Link} to="/quilombo" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/quilombo/${quilomboEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ quilombo }: IRootState) => ({
  quilomboEntity: quilombo.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(QuilomboDetail);
