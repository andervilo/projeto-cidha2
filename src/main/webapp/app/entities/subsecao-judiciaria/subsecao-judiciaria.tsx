import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './subsecao-judiciaria.reducer';
import { ISubsecaoJudiciaria } from 'app/shared/model/subsecao-judiciaria.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ISubsecaoJudiciariaProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const SubsecaoJudiciaria = (props: ISubsecaoJudiciariaProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const handleSyncList = () => {
    props.getEntities();
  };

  const { subsecaoJudiciariaList, match, loading } = props;
  return (
    <div>
      <h2 id="subsecao-judiciaria-heading" data-cy="SubsecaoJudiciariaHeading">
        <Translate contentKey="cidhaApp.subsecaoJudiciaria.home.title">Subsecao Judiciarias</Translate>
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="cidhaApp.subsecaoJudiciaria.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="cidhaApp.subsecaoJudiciaria.home.createLabel">Create new Subsecao Judiciaria</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {subsecaoJudiciariaList && subsecaoJudiciariaList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="cidhaApp.subsecaoJudiciaria.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="cidhaApp.subsecaoJudiciaria.sigla">Sigla</Translate>
                </th>
                <th>
                  <Translate contentKey="cidhaApp.subsecaoJudiciaria.nome">Nome</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {subsecaoJudiciariaList.map((subsecaoJudiciaria, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${subsecaoJudiciaria.id}`} color="link" size="sm">
                      {subsecaoJudiciaria.id}
                    </Button>
                  </td>
                  <td>{subsecaoJudiciaria.sigla}</td>
                  <td>{subsecaoJudiciaria.nome}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${subsecaoJudiciaria.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${subsecaoJudiciaria.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${subsecaoJudiciaria.id}/delete`}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="cidhaApp.subsecaoJudiciaria.home.notFound">No Subsecao Judiciarias found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ subsecaoJudiciaria }: IRootState) => ({
  subsecaoJudiciariaList: subsecaoJudiciaria.entities,
  loading: subsecaoJudiciaria.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(SubsecaoJudiciaria);
