import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './envolvidos-conflito-litigio.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IEnvolvidosConflitoLitigioDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const EnvolvidosConflitoLitigioDetail = (props: IEnvolvidosConflitoLitigioDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { envolvidosConflitoLitigioEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="envolvidosConflitoLitigioDetailsHeading">
          <Translate contentKey="cidhaApp.envolvidosConflitoLitigio.detail.title">EnvolvidosConflitoLitigio</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{envolvidosConflitoLitigioEntity.id}</dd>
          <dt>
            <span id="numeroIndividuos">
              <Translate contentKey="cidhaApp.envolvidosConflitoLitigio.numeroIndividuos">Numero Individuos</Translate>
            </span>
          </dt>
          <dd>{envolvidosConflitoLitigioEntity.numeroIndividuos}</dd>
          <dt>
            <span id="fonteInformacaoQtde">
              <Translate contentKey="cidhaApp.envolvidosConflitoLitigio.fonteInformacaoQtde">Fonte Informacao Qtde</Translate>
            </span>
          </dt>
          <dd>{envolvidosConflitoLitigioEntity.fonteInformacaoQtde}</dd>
          <dt>
            <span id="observacoes">
              <Translate contentKey="cidhaApp.envolvidosConflitoLitigio.observacoes">Observacoes</Translate>
            </span>
          </dt>
          <dd>{envolvidosConflitoLitigioEntity.observacoes}</dd>
        </dl>
        <Button tag={Link} to="/envolvidos-conflito-litigio" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/envolvidos-conflito-litigio/${envolvidosConflitoLitigioEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ envolvidosConflitoLitigio }: IRootState) => ({
  envolvidosConflitoLitigioEntity: envolvidosConflitoLitigio.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(EnvolvidosConflitoLitigioDetail);
