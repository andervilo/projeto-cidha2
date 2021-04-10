import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './tipo-empreendimento.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ITipoEmpreendimentoDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const TipoEmpreendimentoDetail = (props: ITipoEmpreendimentoDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { tipoEmpreendimentoEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="tipoEmpreendimentoDetailsHeading">
          <Translate contentKey="cidhaApp.tipoEmpreendimento.detail.title">TipoEmpreendimento</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{tipoEmpreendimentoEntity.id}</dd>
          <dt>
            <span id="descricao">
              <Translate contentKey="cidhaApp.tipoEmpreendimento.descricao">Descricao</Translate>
            </span>
          </dt>
          <dd>{tipoEmpreendimentoEntity.descricao}</dd>
        </dl>
        <Button tag={Link} to="/tipo-empreendimento" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/tipo-empreendimento/${tipoEmpreendimentoEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ tipoEmpreendimento }: IRootState) => ({
  tipoEmpreendimentoEntity: tipoEmpreendimento.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(TipoEmpreendimentoDetail);
