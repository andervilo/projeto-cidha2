import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './problema-juridico.reducer';
import { IProblemaJuridico } from 'app/shared/model/problema-juridico.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IProblemaJuridicoDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ProblemaJuridicoDetail = (props: IProblemaJuridicoDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { problemaJuridicoEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="cidhaApp.problemaJuridico.detail.title">ProblemaJuridico</Translate> [<b>{problemaJuridicoEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="prolemaJuridicoRespondido">
              <Translate contentKey="cidhaApp.problemaJuridico.prolemaJuridicoRespondido">Prolema Juridico Respondido</Translate>
            </span>
          </dt>
          <dd>{problemaJuridicoEntity.prolemaJuridicoRespondido}</dd>
          <dt>
            <span id="folhasProblemaJuridico">
              <Translate contentKey="cidhaApp.problemaJuridico.folhasProblemaJuridico">Folhas Problema Juridico</Translate>
            </span>
          </dt>
          <dd>{problemaJuridicoEntity.folhasProblemaJuridico}</dd>
          <dt>
            <Translate contentKey="cidhaApp.problemaJuridico.fundamentacaoDoutrinaria">Fundamentacao Doutrinaria</Translate>
          </dt>
          <dd>
            {problemaJuridicoEntity.fundamentacaoDoutrinarias
              ? problemaJuridicoEntity.fundamentacaoDoutrinarias.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.fundamentacaoDoutrinariaCitada}</a>
                    {problemaJuridicoEntity.fundamentacaoDoutrinarias && i === problemaJuridicoEntity.fundamentacaoDoutrinarias.length - 1
                      ? ''
                      : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>
            <Translate contentKey="cidhaApp.problemaJuridico.jurisprudencia">Jurisprudencia</Translate>
          </dt>
          <dd>
            {problemaJuridicoEntity.jurisprudencias
              ? problemaJuridicoEntity.jurisprudencias.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.jurisprudenciaCitadaDescricao}</a>
                    {problemaJuridicoEntity.jurisprudencias && i === problemaJuridicoEntity.jurisprudencias.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>
            <Translate contentKey="cidhaApp.problemaJuridico.fundamentacaoLegal">Fundamentacao Legal</Translate>
          </dt>
          <dd>
            {problemaJuridicoEntity.fundamentacaoLegals
              ? problemaJuridicoEntity.fundamentacaoLegals.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.fundamentacaoLegal}</a>
                    {problemaJuridicoEntity.fundamentacaoLegals && i === problemaJuridicoEntity.fundamentacaoLegals.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>
            <Translate contentKey="cidhaApp.problemaJuridico.instrumentoInternacional">Instrumento Internacional</Translate>
          </dt>
          <dd>
            {problemaJuridicoEntity.instrumentoInternacionals
              ? problemaJuridicoEntity.instrumentoInternacionals.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.instrumentoInternacionalCitadoDescricao}</a>
                    {problemaJuridicoEntity.instrumentoInternacionals && i === problemaJuridicoEntity.instrumentoInternacionals.length - 1
                      ? ''
                      : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>
            <Translate contentKey="cidhaApp.problemaJuridico.processo">Processo</Translate>
          </dt>
          <dd>
            {problemaJuridicoEntity.processos
              ? problemaJuridicoEntity.processos.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.oficio}</a>
                    {problemaJuridicoEntity.processos && i === problemaJuridicoEntity.processos.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/problema-juridico" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/problema-juridico/${problemaJuridicoEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ problemaJuridico }: IRootState) => ({
  problemaJuridicoEntity: problemaJuridico.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ProblemaJuridicoDetail);
