import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './atividade-exploracao-ilegal.reducer';
import { IAtividadeExploracaoIlegal } from 'app/shared/model/atividade-exploracao-ilegal.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IAtividadeExploracaoIlegalDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const AtividadeExploracaoIlegalDetail = (props: IAtividadeExploracaoIlegalDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { atividadeExploracaoIlegalEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="cidhaApp.atividadeExploracaoIlegal.detail.title">AtividadeExploracaoIlegal</Translate> [
          <b>{atividadeExploracaoIlegalEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="descricao">
              <Translate contentKey="cidhaApp.atividadeExploracaoIlegal.descricao">Descricao</Translate>
            </span>
          </dt>
          <dd>{atividadeExploracaoIlegalEntity.descricao}</dd>
        </dl>
        <Button tag={Link} to="/atividade-exploracao-ilegal" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/atividade-exploracao-ilegal/${atividadeExploracaoIlegalEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ atividadeExploracaoIlegal }: IRootState) => ({
  atividadeExploracaoIlegalEntity: atividadeExploracaoIlegal.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(AtividadeExploracaoIlegalDetail);
