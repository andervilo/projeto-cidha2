import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './unidade-conservacao.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IUnidadeConservacaoDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const UnidadeConservacaoDetail = (props: IUnidadeConservacaoDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { unidadeConservacaoEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="unidadeConservacaoDetailsHeading">
          <Translate contentKey="cidhaApp.unidadeConservacao.detail.title">UnidadeConservacao</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{unidadeConservacaoEntity.id}</dd>
          <dt>
            <span id="descricao">
              <Translate contentKey="cidhaApp.unidadeConservacao.descricao">Descricao</Translate>
            </span>
          </dt>
          <dd>{unidadeConservacaoEntity.descricao}</dd>
        </dl>
        <Button tag={Link} to="/unidade-conservacao" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/unidade-conservacao/${unidadeConservacaoEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ unidadeConservacao }: IRootState) => ({
  unidadeConservacaoEntity: unidadeConservacao.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(UnidadeConservacaoDetail);
