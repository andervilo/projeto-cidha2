import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './embargo-recurso-especial.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IEmbargoRecursoEspecialDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const EmbargoRecursoEspecialDetail = (props: IEmbargoRecursoEspecialDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { embargoRecursoEspecialEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="embargoRecursoEspecialDetailsHeading">
          <Translate contentKey="cidhaApp.embargoRecursoEspecial.detail.title">EmbargoRecursoEspecial</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{embargoRecursoEspecialEntity.id}</dd>
          <dt>
            <span id="descricao">
              <Translate contentKey="cidhaApp.embargoRecursoEspecial.descricao">Descricao</Translate>
            </span>
          </dt>
          <dd>{embargoRecursoEspecialEntity.descricao}</dd>
          <dt>
            <Translate contentKey="cidhaApp.embargoRecursoEspecial.processo">Processo</Translate>
          </dt>
          <dd>{embargoRecursoEspecialEntity.processo ? embargoRecursoEspecialEntity.processo.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/embargo-recurso-especial" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/embargo-recurso-especial/${embargoRecursoEspecialEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ embargoRecursoEspecial }: IRootState) => ({
  embargoRecursoEspecialEntity: embargoRecursoEspecial.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(EmbargoRecursoEspecialDetail);
