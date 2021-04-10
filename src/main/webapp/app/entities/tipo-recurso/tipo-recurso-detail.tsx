import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './tipo-recurso.reducer';
import { ITipoRecurso } from 'app/shared/model/tipo-recurso.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ITipoRecursoDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const TipoRecursoDetail = (props: ITipoRecursoDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { tipoRecursoEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="cidhaApp.tipoRecurso.detail.title">TipoRecurso</Translate> [<b>{tipoRecursoEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="descricao">
              <Translate contentKey="cidhaApp.tipoRecurso.descricao">Descricao</Translate>
            </span>
          </dt>
          <dd>{tipoRecursoEntity.descricao}</dd>
        </dl>
        <Button tag={Link} to="/tipo-recurso" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/tipo-recurso/${tipoRecursoEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ tipoRecurso }: IRootState) => ({
  tipoRecursoEntity: tipoRecurso.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(TipoRecursoDetail);
