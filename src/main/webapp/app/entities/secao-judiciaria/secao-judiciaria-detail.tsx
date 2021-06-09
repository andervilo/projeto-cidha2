import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './secao-judiciaria.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ISecaoJudiciariaDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const SecaoJudiciariaDetail = (props: ISecaoJudiciariaDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { secaoJudiciariaEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="secaoJudiciariaDetailsHeading">
          <Translate contentKey="cidhaApp.secaoJudiciaria.detail.title">SecaoJudiciaria</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{secaoJudiciariaEntity.id}</dd>
          <dt>
            <span id="sigla">
              <Translate contentKey="cidhaApp.secaoJudiciaria.sigla">Sigla</Translate>
            </span>
          </dt>
          <dd>{secaoJudiciariaEntity.sigla}</dd>
          <dt>
            <span id="nome">
              <Translate contentKey="cidhaApp.secaoJudiciaria.nome">Nome</Translate>
            </span>
          </dt>
          <dd>{secaoJudiciariaEntity.nome}</dd>
          <dt>
            <Translate contentKey="cidhaApp.secaoJudiciaria.subsecaoJudiciaria">Subsecao Judiciaria</Translate>
          </dt>
          <dd>{secaoJudiciariaEntity.subsecaoJudiciaria ? secaoJudiciariaEntity.subsecaoJudiciaria.nome : ''}</dd>
        </dl>
        <Button tag={Link} to="/secao-judiciaria" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/secao-judiciaria/${secaoJudiciariaEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ secaoJudiciaria }: IRootState) => ({
  secaoJudiciariaEntity: secaoJudiciaria.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(SecaoJudiciariaDetail);
