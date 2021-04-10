import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './processo-conflito.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IProcessoConflitoDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ProcessoConflitoDetail = (props: IProcessoConflitoDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { processoConflitoEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="processoConflitoDetailsHeading">
          <Translate contentKey="cidhaApp.processoConflito.detail.title">ProcessoConflito</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{processoConflitoEntity.id}</dd>
          <dt>
            <span id="inicioConflitoObservacoes">
              <Translate contentKey="cidhaApp.processoConflito.inicioConflitoObservacoes">Inicio Conflito Observacoes</Translate>
            </span>
          </dt>
          <dd>{processoConflitoEntity.inicioConflitoObservacoes}</dd>
          <dt>
            <span id="historicoConlito">
              <Translate contentKey="cidhaApp.processoConflito.historicoConlito">Historico Conlito</Translate>
            </span>
          </dt>
          <dd>{processoConflitoEntity.historicoConlito}</dd>
          <dt>
            <span id="nomeCasoComuidade">
              <Translate contentKey="cidhaApp.processoConflito.nomeCasoComuidade">Nome Caso Comuidade</Translate>
            </span>
          </dt>
          <dd>{processoConflitoEntity.nomeCasoComuidade}</dd>
          <dt>
            <span id="consultaPrevia">
              <Translate contentKey="cidhaApp.processoConflito.consultaPrevia">Consulta Previa</Translate>
            </span>
          </dt>
          <dd>{processoConflitoEntity.consultaPrevia ? 'true' : 'false'}</dd>
          <dt>
            <Translate contentKey="cidhaApp.processoConflito.direito">Direito</Translate>
          </dt>
          <dd>
            {processoConflitoEntity.direitos
              ? processoConflitoEntity.direitos.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.descricao}</a>
                    {processoConflitoEntity.direitos && i === processoConflitoEntity.direitos.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/processo-conflito" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/processo-conflito/${processoConflitoEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ processoConflito }: IRootState) => ({
  processoConflitoEntity: processoConflito.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ProcessoConflitoDetail);
