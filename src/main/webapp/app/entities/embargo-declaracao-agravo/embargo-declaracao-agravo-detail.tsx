import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './embargo-declaracao-agravo.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IEmbargoDeclaracaoAgravoDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const EmbargoDeclaracaoAgravoDetail = (props: IEmbargoDeclaracaoAgravoDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { embargoDeclaracaoAgravoEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="embargoDeclaracaoAgravoDetailsHeading">
          <Translate contentKey="cidhaApp.embargoDeclaracaoAgravo.detail.title">EmbargoDeclaracaoAgravo</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{embargoDeclaracaoAgravoEntity.id}</dd>
          <dt>
            <span id="descricao">
              <Translate contentKey="cidhaApp.embargoDeclaracaoAgravo.descricao">Descricao</Translate>
            </span>
          </dt>
          <dd>{embargoDeclaracaoAgravoEntity.descricao}</dd>
          <dt>
            <Translate contentKey="cidhaApp.embargoDeclaracaoAgravo.processo">Processo</Translate>
          </dt>
          <dd>{embargoDeclaracaoAgravoEntity.processo ? embargoDeclaracaoAgravoEntity.processo.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/embargo-declaracao-agravo" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/embargo-declaracao-agravo/${embargoDeclaracaoAgravoEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ embargoDeclaracaoAgravo }: IRootState) => ({
  embargoDeclaracaoAgravoEntity: embargoDeclaracaoAgravo.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(EmbargoDeclaracaoAgravoDetail);
