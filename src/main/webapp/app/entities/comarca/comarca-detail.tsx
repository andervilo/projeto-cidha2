import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './comarca.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IComarcaDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ComarcaDetail = (props: IComarcaDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { comarcaEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="comarcaDetailsHeading">
          <Translate contentKey="cidhaApp.comarca.detail.title">Comarca</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{comarcaEntity.id}</dd>
          <dt>
            <span id="nome">
              <Translate contentKey="cidhaApp.comarca.nome">Nome</Translate>
            </span>
          </dt>
          <dd>{comarcaEntity.nome}</dd>
          <dt>
            <span id="codigoCnj">
              <Translate contentKey="cidhaApp.comarca.codigoCnj">Codigo Cnj</Translate>
            </span>
          </dt>
          <dd>{comarcaEntity.codigoCnj}</dd>
        </dl>
        <Button tag={Link} to="/comarca" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/comarca/${comarcaEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ comarca }: IRootState) => ({
  comarcaEntity: comarca.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ComarcaDetail);
