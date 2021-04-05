import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './fundamentacao-doutrinaria.reducer';
import { IFundamentacaoDoutrinaria } from 'app/shared/model/fundamentacao-doutrinaria.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IFundamentacaoDoutrinariaDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const FundamentacaoDoutrinariaDetail = (props: IFundamentacaoDoutrinariaDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { fundamentacaoDoutrinariaEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="cidhaApp.fundamentacaoDoutrinaria.detail.title">FundamentacaoDoutrinaria</Translate> [
          <b>{fundamentacaoDoutrinariaEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="fundamentacaoDoutrinariaCitada">
              <Translate contentKey="cidhaApp.fundamentacaoDoutrinaria.fundamentacaoDoutrinariaCitada">
                Fundamentacao Doutrinaria Citada
              </Translate>
            </span>
          </dt>
          <dd>{fundamentacaoDoutrinariaEntity.fundamentacaoDoutrinariaCitada}</dd>
          <dt>
            <span id="folhasFundamentacaoDoutrinaria">
              <Translate contentKey="cidhaApp.fundamentacaoDoutrinaria.folhasFundamentacaoDoutrinaria">
                Folhas Fundamentacao Doutrinaria
              </Translate>
            </span>
          </dt>
          <dd>{fundamentacaoDoutrinariaEntity.folhasFundamentacaoDoutrinaria}</dd>
          <dt>
            <span id="fundamentacaoDoutrinariaSugerida">
              <Translate contentKey="cidhaApp.fundamentacaoDoutrinaria.fundamentacaoDoutrinariaSugerida">
                Fundamentacao Doutrinaria Sugerida
              </Translate>
            </span>
          </dt>
          <dd>{fundamentacaoDoutrinariaEntity.fundamentacaoDoutrinariaSugerida}</dd>
        </dl>
        <Button tag={Link} to="/fundamentacao-doutrinaria" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/fundamentacao-doutrinaria/${fundamentacaoDoutrinariaEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ fundamentacaoDoutrinaria }: IRootState) => ({
  fundamentacaoDoutrinariaEntity: fundamentacaoDoutrinaria.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(FundamentacaoDoutrinariaDetail);
