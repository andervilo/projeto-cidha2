import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './municipio.reducer';
import { IMunicipio } from 'app/shared/model/municipio.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IMunicipioDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const MunicipioDetail = (props: IMunicipioDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { municipioEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="cidhaApp.municipio.detail.title">Municipio</Translate> [<b>{municipioEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="amazoniaLegal">
              <Translate contentKey="cidhaApp.municipio.amazoniaLegal">Amazonia Legal</Translate>
            </span>
          </dt>
          <dd>{municipioEntity.amazoniaLegal ? 'true' : 'false'}</dd>
          <dt>
            <span id="codigoIbge">
              <Translate contentKey="cidhaApp.municipio.codigoIbge">Codigo Ibge</Translate>
            </span>
          </dt>
          <dd>{municipioEntity.codigoIbge}</dd>
          <dt>
            <span id="estado">
              <Translate contentKey="cidhaApp.municipio.estado">Estado</Translate>
            </span>
          </dt>
          <dd>{municipioEntity.estado}</dd>
          <dt>
            <span id="nome">
              <Translate contentKey="cidhaApp.municipio.nome">Nome</Translate>
            </span>
          </dt>
          <dd>{municipioEntity.nome}</dd>
        </dl>
        <Button tag={Link} to="/municipio" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/municipio/${municipioEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ municipio }: IRootState) => ({
  municipioEntity: municipio.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(MunicipioDetail);
