import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './embargo-declaracao.reducer';
import { IEmbargoDeclaracao } from 'app/shared/model/embargo-declaracao.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IEmbargoDeclaracaoDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const EmbargoDeclaracaoDetail = (props: IEmbargoDeclaracaoDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { embargoDeclaracaoEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="cidhaApp.embargoDeclaracao.detail.title">EmbargoDeclaracao</Translate> [<b>{embargoDeclaracaoEntity.id}</b>
          ]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="descricao">
              <Translate contentKey="cidhaApp.embargoDeclaracao.descricao">Descricao</Translate>
            </span>
          </dt>
          <dd>{embargoDeclaracaoEntity.descricao}</dd>
          <dt>
            <Translate contentKey="cidhaApp.embargoDeclaracao.processo">Processo</Translate>
          </dt>
          <dd>{embargoDeclaracaoEntity.processo ? embargoDeclaracaoEntity.processo.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/embargo-declaracao" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/embargo-declaracao/${embargoDeclaracaoEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ embargoDeclaracao }: IRootState) => ({
  embargoDeclaracaoEntity: embargoDeclaracao.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(EmbargoDeclaracaoDetail);
