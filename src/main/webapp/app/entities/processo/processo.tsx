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
                <th className="hand" onClick={sort('numeroProcesso')}>
                  <Translate contentKey="cidhaApp.processo.numeroProcesso">Numero Processo</Translate> <FontAwesomeIcon icon="sort" />
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
                <th className="hand" onClick={sort('folhasProcessoConcessaoLiminar')}>
                  <Translate contentKey="cidhaApp.processo.folhasProcessoConcessaoLiminar">Folhas Processo Concessao Liminar</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('concessaoLiminarObservacoes')}>
                  <Translate contentKey="cidhaApp.processo.concessaoLiminarObservacoes">Concessao Liminar Observacoes</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('folhasProcessoCassacao')}>
                  <Translate contentKey="cidhaApp.processo.folhasProcessoCassacao">Folhas Processo Cassacao</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('folhasParecer')}>
                  <Translate contentKey="cidhaApp.processo.folhasParecer">Folhas Parecer</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('folhasEmbargo')}>
                  <Translate contentKey="cidhaApp.processo.folhasEmbargo">Folhas Embargo</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('acordaoEmbargo')}>
                  <Translate contentKey="cidhaApp.processo.acordaoEmbargo">Acordao Embargo</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('folhasCienciaJulgEmbargos')}>
                  <Translate contentKey="cidhaApp.processo.folhasCienciaJulgEmbargos">Folhas Ciencia Julg Embargos</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('apelacao')}>
                  <Translate contentKey="cidhaApp.processo.apelacao">Apelacao</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('folhasApelacao')}>
                  <Translate contentKey="cidhaApp.processo.folhasApelacao">Folhas Apelacao</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('acordaoApelacao')}>
                  <Translate contentKey="cidhaApp.processo.acordaoApelacao">Acordao Apelacao</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('folhasCienciaJulgApelacao')}>
                  <Translate contentKey="cidhaApp.processo.folhasCienciaJulgApelacao">Folhas Ciencia Julg Apelacao</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('embargoDeclaracao')}>
                  <Translate contentKey="cidhaApp.processo.embargoDeclaracao">Embargo Declaracao</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('embargoRecursoExtraordinario')}>
                  <Translate contentKey="cidhaApp.processo.embargoRecursoExtraordinario">Embargo Recurso Extraordinario</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('folhasRecursoEspecial')}>
                  <Translate contentKey="cidhaApp.processo.folhasRecursoEspecial">Folhas Recurso Especial</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('acordaoRecursoEspecial')}>
                  <Translate contentKey="cidhaApp.processo.acordaoRecursoEspecial">Acordao Recurso Especial</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('folhasCienciaJulgamentoRecursoEspecial')}>
                  <Translate contentKey="cidhaApp.processo.folhasCienciaJulgamentoRecursoEspecial">
                    Folhas Ciencia Julgamento Recurso Especial
                  </Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('embargoRecursoEspecial')}>
                  <Translate contentKey="cidhaApp.processo.embargoRecursoEspecial">Embargo Recurso Especial</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('folhasCiencia')}>
                  <Translate contentKey="cidhaApp.processo.folhasCiencia">Folhas Ciencia</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('agravoRespRe')}>
                  <Translate contentKey="cidhaApp.processo.agravoRespRe">Agravo Resp Re</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('folhasRespRe')}>
                  <Translate contentKey="cidhaApp.processo.folhasRespRe">Folhas Resp Re</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('acordaoAgravoRespRe')}>
                  <Translate contentKey="cidhaApp.processo.acordaoAgravoRespRe">Acordao Agravo Resp Re</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('folhasCienciaJulgamentoAgravoRespRe')}>
                  <Translate contentKey="cidhaApp.processo.folhasCienciaJulgamentoAgravoRespRe">
                    Folhas Ciencia Julgamento Agravo Resp Re
                  </Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('embargoRespRe')}>
                  <Translate contentKey="cidhaApp.processo.embargoRespRe">Embargo Resp Re</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('agravoInterno')}>
                  <Translate contentKey="cidhaApp.processo.agravoInterno">Agravo Interno</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('folhasAgravoInterno')}>
                  <Translate contentKey="cidhaApp.processo.folhasAgravoInterno">Folhas Agravo Interno</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('embargoRecursoAgravo')}>
                  <Translate contentKey="cidhaApp.processo.embargoRecursoAgravo">Embargo Recurso Agravo</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('observacoes')}>
                  <Translate contentKey="cidhaApp.processo.observacoes">Observacoes</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('recursoSTJ')}>
                  <Translate contentKey="cidhaApp.processo.recursoSTJ">Recurso STJ</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('linkRecursoSTJ')}>
                  <Translate contentKey="cidhaApp.processo.linkRecursoSTJ">Link Recurso STJ</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('folhasRecursoSTJ')}>
                  <Translate contentKey="cidhaApp.processo.folhasRecursoSTJ">Folhas Recurso STJ</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('recursoSTF')}>
                  <Translate contentKey="cidhaApp.processo.recursoSTF">Recurso STF</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('linkRecursoSTF')}>
                  <Translate contentKey="cidhaApp.processo.linkRecursoSTF">Link Recurso STF</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('folhasRecursoSTF')}>
                  <Translate contentKey="cidhaApp.processo.folhasRecursoSTF">Folhas Recurso STF</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('folhasMemorialMPF')}>
                  <Translate contentKey="cidhaApp.processo.folhasMemorialMPF">Folhas Memorial MPF</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('execusaoProvisoria')}>
                  <Translate contentKey="cidhaApp.processo.execusaoProvisoria">Execusao Provisoria</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('numeracaoExecusaoProvisoria')}>
                  <Translate contentKey="cidhaApp.processo.numeracaoExecusaoProvisoria">Numeracao Execusao Provisoria</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('recuperacaoEfetivaCumprimentoSentenca')}>
                  <Translate contentKey="cidhaApp.processo.recuperacaoEfetivaCumprimentoSentenca">
                    Recuperacao Efetiva Cumprimento Sentenca
                  </Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('recuperacaoEfetivaCumprimentoSentencaObservacoes')}>
                  <Translate contentKey="cidhaApp.processo.recuperacaoEfetivaCumprimentoSentencaObservacoes">
                    Recuperacao Efetiva Cumprimento Sentenca Observacoes
                  </Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('envolveEmpreendimento')}>
                  <Translate contentKey="cidhaApp.processo.envolveEmpreendimento">Envolve Empreendimento</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('envolveExploracaoIlegal')}>
                  <Translate contentKey="cidhaApp.processo.envolveExploracaoIlegal">Envolve Exploracao Ilegal</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('envolveTerraQuilombola')}>
                  <Translate contentKey="cidhaApp.processo.envolveTerraQuilombola">Envolve Terra Quilombola</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('envolveTerraComunidadeTradicional')}>
                  <Translate contentKey="cidhaApp.processo.envolveTerraComunidadeTradicional">
                    Envolve Terra Comunidade Tradicional
                  </Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('envolveTerraIndigena')}>
                  <Translate contentKey="cidhaApp.processo.envolveTerraIndigena">Envolve Terra Indigena</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('resumoFatos')}>
                  <Translate contentKey="cidhaApp.processo.resumoFatos">Resumo Fatos</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('tamanhoArea')}>
                  <Translate contentKey="cidhaApp.processo.tamanhoArea">Tamanho Area</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('valorArea')}>
                  <Translate contentKey="cidhaApp.processo.valorArea">Valor Area</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('tamanhoAreaObservacao')}>
                  <Translate contentKey="cidhaApp.processo.tamanhoAreaObservacao">Tamanho Area Observacao</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('dadosGeograficosLitigioConflito')}>
                  <Translate contentKey="cidhaApp.processo.dadosGeograficosLitigioConflito">Dados Geograficos Litigio Conflito</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('latitude')}>
                  <Translate contentKey="cidhaApp.processo.latitude">Latitude</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('longitude')}>
                  <Translate contentKey="cidhaApp.processo.longitude">Longitude</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('numeroProcessoMPF')}>
                  <Translate contentKey="cidhaApp.processo.numeroProcessoMPF">Numero Processo MPF</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('numeroEmbargo')}>
                  <Translate contentKey="cidhaApp.processo.numeroEmbargo">Numero Embargo</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('pautaApelacao')}>
                  <Translate contentKey="cidhaApp.processo.pautaApelacao">Pauta Apelacao</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('numeroRecursoEspecial')}>
                  <Translate contentKey="cidhaApp.processo.numeroRecursoEspecial">Numero Recurso Especial</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('admissiblidade')}>
                  <Translate contentKey="cidhaApp.processo.admissiblidade">Admissiblidade</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('envolveGrandeProjeto')}>
                  <Translate contentKey="cidhaApp.processo.envolveGrandeProjeto">Envolve Grande Projeto</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('envolveUnidadeConservacao')}>
                  <Translate contentKey="cidhaApp.processo.envolveUnidadeConservacao">Envolve Unidade Conservacao</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('linkReferencia')}>
                  <Translate contentKey="cidhaApp.processo.linkReferencia">Link Referencia</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('statusProcesso')}>
                  <Translate contentKey="cidhaApp.processo.statusProcesso">Status Processo</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="cidhaApp.processo.tipoDecisao">Tipo Decisao</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="cidhaApp.processo.tipoEmpreendimento">Tipo Empreendimento</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="cidhaApp.processo.secaoJudiciaria">Secao Judiciaria</Translate> <FontAwesomeIcon icon="sort" />
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
                  <td>{processo.numeroProcesso}</td>
                  <td>{processo.oficio}</td>
                  <td>{processo.assunto}</td>
                  <td>{processo.linkUnico}</td>
                  <td>{processo.linkTrf}</td>
                  <td>{processo.turmaTrf1}</td>
                  <td>{processo.numeroProcessoAdministrativo}</td>
                  <td>{processo.numeroProcessoJudicialPrimeiraInstancia}</td>
                  <td>{processo.numeroProcessoJudicialPrimeiraInstanciaLink}</td>
                  <td>{processo.numeroProcessoJudicialPrimeiraInstanciaObservacoes}</td>
                  <td>{processo.parecer ? 'true' : 'false'}</td>
                  <td>{processo.folhasProcessoConcessaoLiminar}</td>
                  <td>{processo.concessaoLiminarObservacoes}</td>
                  <td>{processo.folhasProcessoCassacao}</td>
                  <td>{processo.folhasParecer}</td>
                  <td>{processo.folhasEmbargo}</td>
                  <td>{processo.acordaoEmbargo}</td>
                  <td>{processo.folhasCienciaJulgEmbargos}</td>
                  <td>{processo.apelacao}</td>
                  <td>{processo.folhasApelacao}</td>
                  <td>{processo.acordaoApelacao}</td>
                  <td>{processo.folhasCienciaJulgApelacao}</td>
                  <td>{processo.embargoDeclaracao ? 'true' : 'false'}</td>
                  <td>{processo.embargoRecursoExtraordinario ? 'true' : 'false'}</td>
                  <td>{processo.folhasRecursoEspecial}</td>
                  <td>{processo.acordaoRecursoEspecial}</td>
                  <td>{processo.folhasCienciaJulgamentoRecursoEspecial}</td>
                  <td>{processo.embargoRecursoEspecial ? 'true' : 'false'}</td>
                  <td>{processo.folhasCiencia}</td>
                  <td>{processo.agravoRespRe}</td>
                  <td>{processo.folhasRespRe}</td>
                  <td>{processo.acordaoAgravoRespRe}</td>
                  <td>{processo.folhasCienciaJulgamentoAgravoRespRe}</td>
                  <td>{processo.embargoRespRe}</td>
                  <td>{processo.agravoInterno}</td>
                  <td>{processo.folhasAgravoInterno}</td>
                  <td>{processo.embargoRecursoAgravo ? 'true' : 'false'}</td>
                  <td>{processo.observacoes}</td>
                  <td>{processo.recursoSTJ ? 'true' : 'false'}</td>
                  <td>{processo.linkRecursoSTJ}</td>
                  <td>{processo.folhasRecursoSTJ}</td>
                  <td>{processo.recursoSTF ? 'true' : 'false'}</td>
                  <td>{processo.linkRecursoSTF}</td>
                  <td>{processo.folhasRecursoSTF}</td>
                  <td>{processo.folhasMemorialMPF}</td>
                  <td>{processo.execusaoProvisoria ? 'true' : 'false'}</td>
                  <td>{processo.numeracaoExecusaoProvisoria}</td>
                  <td>{processo.recuperacaoEfetivaCumprimentoSentenca}</td>
                  <td>{processo.recuperacaoEfetivaCumprimentoSentencaObservacoes}</td>
                  <td>{processo.envolveEmpreendimento ? 'true' : 'false'}</td>
                  <td>{processo.envolveExploracaoIlegal ? 'true' : 'false'}</td>
                  <td>{processo.envolveTerraQuilombola ? 'true' : 'false'}</td>
                  <td>{processo.envolveTerraComunidadeTradicional ? 'true' : 'false'}</td>
                  <td>{processo.envolveTerraIndigena ? 'true' : 'false'}</td>
                  <td>{processo.resumoFatos}</td>
                  <td>{processo.tamanhoArea}</td>
                  <td>{processo.valorArea}</td>
                  <td>{processo.tamanhoAreaObservacao}</td>
                  <td>{processo.dadosGeograficosLitigioConflito ? 'true' : 'false'}</td>
                  <td>{processo.latitude}</td>
                  <td>{processo.longitude}</td>
                  <td>{processo.numeroProcessoMPF}</td>
                  <td>{processo.numeroEmbargo}</td>
                  <td>{processo.pautaApelacao}</td>
                  <td>{processo.numeroRecursoEspecial}</td>
                  <td>{processo.admissiblidade}</td>
                  <td>{processo.envolveGrandeProjeto ? 'true' : 'false'}</td>
                  <td>{processo.envolveUnidadeConservacao ? 'true' : 'false'}</td>
                  <td>{processo.linkReferencia}</td>
                  <td>
                    <Translate contentKey={`cidhaApp.StatusProcesso.${processo.statusProcesso}`} />
                  </td>
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
                  <td>
                    {processo.secaoJudiciaria ? (
                      <Link to={`secao-judiciaria/${processo.secaoJudiciaria.id}`}>{processo.secaoJudiciaria.nome}</Link>
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
