import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './terra-indigena.reducer';
import { ITerraIndigena } from 'app/shared/model/terra-indigena.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ITerraIndigenaDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const TerraIndigenaDetail = (props: ITerraIndigenaDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { terraIndigenaEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="cidhaApp.terraIndigena.detail.title">TerraIndigena</Translate> [<b>{terraIndigenaEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="descricao">
              <Translate contentKey="cidhaApp.terraIndigena.descricao">Descricao</Translate>
            </span>
          </dt>
          <dd>{terraIndigenaEntity.descricao}</dd>
          <dt>
            <Translate contentKey="cidhaApp.terraIndigena.etnia">Etnia</Translate>
          </dt>
          <dd>
            {terraIndigenaEntity.etnias
              ? terraIndigenaEntity.etnias.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.nome}</a>
                    {terraIndigenaEntity.etnias && i === terraIndigenaEntity.etnias.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/terra-indigena" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/terra-indigena/${terraIndigenaEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ terraIndigena }: IRootState) => ({
  terraIndigenaEntity: terraIndigena.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(TerraIndigenaDetail);
