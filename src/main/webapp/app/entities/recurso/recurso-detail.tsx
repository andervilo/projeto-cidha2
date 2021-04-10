import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './recurso.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IRecursoDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const RecursoDetail = (props: IRecursoDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { recursoEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="recursoDetailsHeading">
          <Translate contentKey="cidhaApp.recurso.detail.title">Recurso</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{recursoEntity.id}</dd>
          <dt>
            <span id="observacoes">
              <Translate contentKey="cidhaApp.recurso.observacoes">Observacoes</Translate>
            </span>
          </dt>
          <dd>{recursoEntity.observacoes}</dd>
          <dt>
            <Translate contentKey="cidhaApp.recurso.tipoRecurso">Tipo Recurso</Translate>
          </dt>
          <dd>{recursoEntity.tipoRecurso ? recursoEntity.tipoRecurso.descricao : ''}</dd>
          <dt>
            <Translate contentKey="cidhaApp.recurso.opcaoRecurso">Opcao Recurso</Translate>
          </dt>
          <dd>{recursoEntity.opcaoRecurso ? recursoEntity.opcaoRecurso.descricao : ''}</dd>
          <dt>
            <Translate contentKey="cidhaApp.recurso.processo">Processo</Translate>
          </dt>
          <dd>{recursoEntity.processo ? recursoEntity.processo.oficio : ''}</dd>
        </dl>
        <Button tag={Link} to="/recurso" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/recurso/${recursoEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ recurso }: IRootState) => ({
  recursoEntity: recurso.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(RecursoDetail);
