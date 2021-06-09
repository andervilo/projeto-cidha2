import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './subsecao-judiciaria.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ISubsecaoJudiciariaDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const SubsecaoJudiciariaDetail = (props: ISubsecaoJudiciariaDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { subsecaoJudiciariaEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="subsecaoJudiciariaDetailsHeading">
          <Translate contentKey="cidhaApp.subsecaoJudiciaria.detail.title">SubsecaoJudiciaria</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{subsecaoJudiciariaEntity.id}</dd>
          <dt>
            <span id="sigla">
              <Translate contentKey="cidhaApp.subsecaoJudiciaria.sigla">Sigla</Translate>
            </span>
          </dt>
          <dd>{subsecaoJudiciariaEntity.sigla}</dd>
          <dt>
            <span id="nome">
              <Translate contentKey="cidhaApp.subsecaoJudiciaria.nome">Nome</Translate>
            </span>
          </dt>
          <dd>{subsecaoJudiciariaEntity.nome}</dd>
        </dl>
        <Button tag={Link} to="/subsecao-judiciaria" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/subsecao-judiciaria/${subsecaoJudiciariaEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ subsecaoJudiciaria }: IRootState) => ({
  subsecaoJudiciariaEntity: subsecaoJudiciaria.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(SubsecaoJudiciariaDetail);
