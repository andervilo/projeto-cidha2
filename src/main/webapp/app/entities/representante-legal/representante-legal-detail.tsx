import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './representante-legal.reducer';
import { IRepresentanteLegal } from 'app/shared/model/representante-legal.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IRepresentanteLegalDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const RepresentanteLegalDetail = (props: IRepresentanteLegalDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { representanteLegalEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="cidhaApp.representanteLegal.detail.title">RepresentanteLegal</Translate> [
          <b>{representanteLegalEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="nome">
              <Translate contentKey="cidhaApp.representanteLegal.nome">Nome</Translate>
            </span>
          </dt>
          <dd>{representanteLegalEntity.nome}</dd>
          <dt>
            <Translate contentKey="cidhaApp.representanteLegal.tipoRepresentante">Tipo Representante</Translate>
          </dt>
          <dd>{representanteLegalEntity.tipoRepresentante ? representanteLegalEntity.tipoRepresentante.descricao : ''}</dd>
        </dl>
        <Button tag={Link} to="/representante-legal" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/representante-legal/${representanteLegalEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ representanteLegal }: IRootState) => ({
  representanteLegalEntity: representanteLegal.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(RepresentanteLegalDetail);
