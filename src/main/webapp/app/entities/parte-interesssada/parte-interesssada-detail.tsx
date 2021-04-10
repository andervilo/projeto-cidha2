import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './parte-interesssada.reducer';
import { IParteInteresssada } from 'app/shared/model/parte-interesssada.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IParteInteresssadaDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ParteInteresssadaDetail = (props: IParteInteresssadaDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { parteInteresssadaEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="cidhaApp.parteInteresssada.detail.title">ParteInteresssada</Translate> [<b>{parteInteresssadaEntity.id}</b>
          ]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="nome">
              <Translate contentKey="cidhaApp.parteInteresssada.nome">Nome</Translate>
            </span>
          </dt>
          <dd>{parteInteresssadaEntity.nome}</dd>
          <dt>
            <span id="classificacao">
              <Translate contentKey="cidhaApp.parteInteresssada.classificacao">Classificacao</Translate>
            </span>
          </dt>
          <dd>{parteInteresssadaEntity.classificacao}</dd>
          <dt>
            <Translate contentKey="cidhaApp.parteInteresssada.representanteLegal">Representante Legal</Translate>
          </dt>
          <dd>
            {parteInteresssadaEntity.representanteLegals
              ? parteInteresssadaEntity.representanteLegals.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.nome}</a>
                    {parteInteresssadaEntity.representanteLegals && i === parteInteresssadaEntity.representanteLegals.length - 1
                      ? ''
                      : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/parte-interesssada" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/parte-interesssada/${parteInteresssadaEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ parteInteresssada }: IRootState) => ({
  parteInteresssadaEntity: parteInteresssada.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ParteInteresssadaDetail);
