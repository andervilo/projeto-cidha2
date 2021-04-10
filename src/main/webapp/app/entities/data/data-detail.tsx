import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './data.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IDataDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const DataDetail = (props: IDataDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { dataEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="dataDetailsHeading">
          <Translate contentKey="cidhaApp.data.detail.title">Data</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{dataEntity.id}</dd>
          <dt>
            <span id="data">
              <Translate contentKey="cidhaApp.data.data">Data</Translate>
            </span>
          </dt>
          <dd>{dataEntity.data ? <TextFormat value={dataEntity.data} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}</dd>
          <dt>
            <Translate contentKey="cidhaApp.data.tipoData">Tipo Data</Translate>
          </dt>
          <dd>{dataEntity.tipoData ? dataEntity.tipoData.descricao : ''}</dd>
          <dt>
            <Translate contentKey="cidhaApp.data.processo">Processo</Translate>
          </dt>
          <dd>{dataEntity.processo ? dataEntity.processo.oficio : ''}</dd>
        </dl>
        <Button tag={Link} to="/data" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/data/${dataEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ data }: IRootState) => ({
  dataEntity: data.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(DataDetail);
