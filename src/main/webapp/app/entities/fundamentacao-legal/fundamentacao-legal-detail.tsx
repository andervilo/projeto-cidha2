import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './fundamentacao-legal.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IFundamentacaoLegalDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const FundamentacaoLegalDetail = (props: IFundamentacaoLegalDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { fundamentacaoLegalEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="fundamentacaoLegalDetailsHeading">
          <Translate contentKey="cidhaApp.fundamentacaoLegal.detail.title">FundamentacaoLegal</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{fundamentacaoLegalEntity.id}</dd>
          <dt>
            <span id="fundamentacaoLegal">
              <Translate contentKey="cidhaApp.fundamentacaoLegal.fundamentacaoLegal">Fundamentacao Legal</Translate>
            </span>
          </dt>
          <dd>{fundamentacaoLegalEntity.fundamentacaoLegal}</dd>
          <dt>
            <span id="folhasFundamentacaoLegal">
              <Translate contentKey="cidhaApp.fundamentacaoLegal.folhasFundamentacaoLegal">Folhas Fundamentacao Legal</Translate>
            </span>
          </dt>
          <dd>{fundamentacaoLegalEntity.folhasFundamentacaoLegal}</dd>
          <dt>
            <span id="fundamentacaoLegalSugerida">
              <Translate contentKey="cidhaApp.fundamentacaoLegal.fundamentacaoLegalSugerida">Fundamentacao Legal Sugerida</Translate>
            </span>
          </dt>
          <dd>{fundamentacaoLegalEntity.fundamentacaoLegalSugerida}</dd>
        </dl>
        <Button tag={Link} to="/fundamentacao-legal" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/fundamentacao-legal/${fundamentacaoLegalEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ fundamentacaoLegal }: IRootState) => ({
  fundamentacaoLegalEntity: fundamentacaoLegal.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(FundamentacaoLegalDetail);
