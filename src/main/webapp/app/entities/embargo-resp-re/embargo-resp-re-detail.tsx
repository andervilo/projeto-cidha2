import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './embargo-resp-re.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IEmbargoRespReDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const EmbargoRespReDetail = (props: IEmbargoRespReDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { embargoRespReEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="embargoRespReDetailsHeading">
          <Translate contentKey="cidhaApp.embargoRespRe.detail.title">EmbargoRespRe</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{embargoRespReEntity.id}</dd>
          <dt>
            <span id="descricao">
              <Translate contentKey="cidhaApp.embargoRespRe.descricao">Descricao</Translate>
            </span>
          </dt>
          <dd>{embargoRespReEntity.descricao}</dd>
          <dt>
            <Translate contentKey="cidhaApp.embargoRespRe.processo">Processo</Translate>
          </dt>
          <dd>{embargoRespReEntity.processo ? embargoRespReEntity.processo.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/embargo-resp-re" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/embargo-resp-re/${embargoRespReEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ embargoRespRe }: IRootState) => ({
  embargoRespReEntity: embargoRespRe.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(EmbargoRespReDetail);
