import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { byteSize, Translate, getSortState, IPaginationBaseState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './processo.reducer';
import { IProcesso } from 'app/shared/model/processo.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';

export interface IProcessoProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const Processo = (props: IProcessoProps) => {
  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(props.location, ITEMS_PER_PAGE, 'id'), props.location.search)
  );

  const getAllEntities = () => {
    props.getEntities(paginationState.activePage - 1, paginationState.itemsPerPage, `${paginationState.sort},${paginationState.order}`);
  };

  const sortEntities = () => {
    getAllEntities();
    const endURL = `?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`;
    if (props.location.search !== endURL) {
      props.history.push(`${props.location.pathname}${endURL}`);
    }
  };

  useEffect(() => {
    sortEntities();
  }, [paginationState.activePage, paginationState.order, paginationState.sort]);

  useEffect(() => {
    const params = new URLSearchParams(props.location.search);
    const page = params.get('page');
    const sort = params.get('sort');
    if (page && sort) {
      const sortSplit = sort.split(',');
      setPaginationState({
        ...paginationState,
        activePage: +page,
        sort: sortSplit[0],
        order: sortSplit[1],
      });
    }
  }, [props.location.search]);

  const sort = p => () => {
    setPaginationState({
      ...paginationState,
      order: paginationState.order === 'asc' ? 'desc' : 'asc',
      sort: p,
    });
  };

  const handlePagination = currentPage =>
    setPaginationState({
      ...paginationState,
      activePage: currentPage,
    });

  const handleSyncList = () => {
    sortEntities();
  };

  const { processoList, match, loading, totalItems } = props;
  return (
    <div>
      <h2 id="processo-heading" data-cy="ProcessoHeading">
        <Translate contentKey="cidhaApp.processo.home.title">Processos</Translate>
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="cidhaApp.processo.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="cidhaApp.processo.home.createLabel">Create new Processo</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {processoList && processoList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="cidhaApp.processo.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('oficio')}>
                  <Translate contentKey="cidhaApp.processo.oficio">Oficio</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('assunto')}>
                  <Translate contentKey="cidhaApp.processo.assunto">Assunto</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('linkUnico')}>
                  <Translate contentKey="cidhaApp.processo.linkUnico">Link Unico</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('linkTrf')}>
                  <Translate contentKey="cidhaApp.processo.linkTrf">Link Trf</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('subsecaoJudiciaria')}>
                  <Translate contentKey="cidhaApp.processo.subsecaoJudiciaria">Subsecao Judiciaria</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('turmaTrf1')}>
                  <Translate contentKey="cidhaApp.processo.turmaTrf1">Turma Trf 1</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('numeroProcessoAdministrativo')}>
                  <Translate contentKey="cidhaApp.processo.numeroProcessoAdministrativo">Numero Processo Administrativo</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('numeroProcessoJudicialPrimeiraInstancia')}>
                  <Translate contentKey="cidhaApp.processo.numeroProcessoJudicialPrimeiraInstancia">
                    Numero Processo Judicial Primeira Instancia
                  </Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('numeroProcessoJudicialPrimeiraInstanciaLink')}>
                  <Translate contentKey="cidhaApp.processo.numeroProcessoJudicialPrimeiraInstanciaLink">
                    Numero Processo Judicial Primeira Instancia Link
                  </Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('numeroProcessoJudicialPrimeiraInstanciaObservacoes')}>
                  <Translate contentKey="cidhaApp.processo.numeroProcessoJudicialPrimeiraInstanciaObservacoes">
                    Numero Processo Judicial Primeira Instancia Observacoes
                  </Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('parecer')}>
                  <Translate contentKey="cidhaApp.processo.parecer">Parecer</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('apelacao')}>
                  <Translate contentKey="cidhaApp.processo.apelacao">Apelacao</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="cidhaApp.processo.tipoDecisao">Tipo Decisao</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="cidhaApp.processo.tipoEmpreendimento">Tipo Empreendimento</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {processoList.map((processo, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${processo.id}`} color="link" size="sm">
                      {processo.id}
                    </Button>
                  </td>
                  <td>{processo.oficio}</td>
                  <td>{processo.assunto}</td>
                  <td>{processo.linkUnico}</td>
                  <td>{processo.linkTrf}</td>
                  <td>{processo.subsecaoJudiciaria}</td>
                  <td>{processo.turmaTrf1}</td>
                  <td>{processo.numeroProcessoAdministrativo}</td>
                  <td>{processo.numeroProcessoJudicialPrimeiraInstancia}</td>
                  <td>{processo.numeroProcessoJudicialPrimeiraInstanciaLink}</td>
                  <td>{processo.numeroProcessoJudicialPrimeiraInstanciaObservacoes}</td>
                  <td>{processo.parecer ? 'true' : 'false'}</td>
                  <td>{processo.apelacao}</td>
                  <td>
                    {processo.tipoDecisao ? (
                      <Link to={`tipo-decisao/${processo.tipoDecisao.id}`}>{processo.tipoDecisao.descricao}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {processo.tipoEmpreendimento ? (
                      <Link to={`tipo-empreendimento/${processo.tipoEmpreendimento.id}`}>{processo.tipoEmpreendimento.descricao}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${processo.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${processo.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
                        to={`${match.url}/${processo.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
              <Translate contentKey="cidhaApp.processo.home.notFound">No Processos found</Translate>
            </div>
          )
        )}
      </div>
      {props.totalItems ? (
        <div className={processoList && processoList.length > 0 ? '' : 'd-none'}>
          <Row className="justify-content-center">
            <JhiItemCount page={paginationState.activePage} total={totalItems} itemsPerPage={paginationState.itemsPerPage} i18nEnabled />
          </Row>
          <Row className="justify-content-center">
            <JhiPagination
              activePage={paginationState.activePage}
              onSelect={handlePagination}
              maxButtons={5}
              itemsPerPage={paginationState.itemsPerPage}
              totalItems={props.totalItems}
            />
          </Row>
        </div>
      ) : (
        ''
      )}
    </div>
  );
};

const mapStateToProps = ({ processo }: IRootState) => ({
  processoList: processo.entities,
  loading: processo.loading,
  totalItems: processo.totalItems,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Processo);
