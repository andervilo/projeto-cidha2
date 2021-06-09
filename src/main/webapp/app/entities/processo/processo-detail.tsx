import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './processo.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IProcessoDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ProcessoDetail = (props: IProcessoDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { processoEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="processoDetailsHeading">
          <Translate contentKey="cidhaApp.processo.detail.title">Processo</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{processoEntity.id}</dd>
          <dt>
            <span id="numeroProcesso">
              <Translate contentKey="cidhaApp.processo.numeroProcesso">Numero Processo</Translate>
            </span>
          </dt>
          <dd>{processoEntity.numeroProcesso}</dd>
          <dt>
            <span id="oficio">
              <Translate contentKey="cidhaApp.processo.oficio">Oficio</Translate>
            </span>
          </dt>
          <dd>{processoEntity.oficio}</dd>
          <dt>
            <span id="assunto">
              <Translate contentKey="cidhaApp.processo.assunto">Assunto</Translate>
            </span>
          </dt>
          <dd>{processoEntity.assunto}</dd>
          <dt>
            <span id="linkUnico">
              <Translate contentKey="cidhaApp.processo.linkUnico">Link Unico</Translate>
            </span>
          </dt>
          <dd>{processoEntity.linkUnico}</dd>
          <dt>
            <span id="linkTrf">
              <Translate contentKey="cidhaApp.processo.linkTrf">Link Trf</Translate>
            </span>
          </dt>
          <dd>{processoEntity.linkTrf}</dd>
          <dt>
            <span id="turmaTrf1">
              <Translate contentKey="cidhaApp.processo.turmaTrf1">Turma Trf 1</Translate>
            </span>
          </dt>
          <dd>{processoEntity.turmaTrf1}</dd>
          <dt>
            <span id="numeroProcessoAdministrativo">
              <Translate contentKey="cidhaApp.processo.numeroProcessoAdministrativo">Numero Processo Administrativo</Translate>
            </span>
          </dt>
          <dd>{processoEntity.numeroProcessoAdministrativo}</dd>
          <dt>
            <span id="numeroProcessoJudicialPrimeiraInstancia">
              <Translate contentKey="cidhaApp.processo.numeroProcessoJudicialPrimeiraInstancia">
                Numero Processo Judicial Primeira Instancia
              </Translate>
            </span>
          </dt>
          <dd>{processoEntity.numeroProcessoJudicialPrimeiraInstancia}</dd>
          <dt>
            <span id="numeroProcessoJudicialPrimeiraInstanciaLink">
              <Translate contentKey="cidhaApp.processo.numeroProcessoJudicialPrimeiraInstanciaLink">
                Numero Processo Judicial Primeira Instancia Link
              </Translate>
            </span>
          </dt>
          <dd>{processoEntity.numeroProcessoJudicialPrimeiraInstanciaLink}</dd>
          <dt>
            <span id="numeroProcessoJudicialPrimeiraInstanciaObservacoes">
              <Translate contentKey="cidhaApp.processo.numeroProcessoJudicialPrimeiraInstanciaObservacoes">
                Numero Processo Judicial Primeira Instancia Observacoes
              </Translate>
            </span>
          </dt>
          <dd>{processoEntity.numeroProcessoJudicialPrimeiraInstanciaObservacoes}</dd>
          <dt>
            <span id="parecer">
              <Translate contentKey="cidhaApp.processo.parecer">Parecer</Translate>
            </span>
          </dt>
          <dd>{processoEntity.parecer ? 'true' : 'false'}</dd>
          <dt>
            <span id="folhasProcessoConcessaoLiminar">
              <Translate contentKey="cidhaApp.processo.folhasProcessoConcessaoLiminar">Folhas Processo Concessao Liminar</Translate>
            </span>
          </dt>
          <dd>{processoEntity.folhasProcessoConcessaoLiminar}</dd>
          <dt>
            <span id="concessaoLiminarObservacoes">
              <Translate contentKey="cidhaApp.processo.concessaoLiminarObservacoes">Concessao Liminar Observacoes</Translate>
            </span>
          </dt>
          <dd>{processoEntity.concessaoLiminarObservacoes}</dd>
          <dt>
            <span id="folhasProcessoCassacao">
              <Translate contentKey="cidhaApp.processo.folhasProcessoCassacao">Folhas Processo Cassacao</Translate>
            </span>
          </dt>
          <dd>{processoEntity.folhasProcessoCassacao}</dd>
          <dt>
            <span id="folhasParecer">
              <Translate contentKey="cidhaApp.processo.folhasParecer">Folhas Parecer</Translate>
            </span>
          </dt>
          <dd>{processoEntity.folhasParecer}</dd>
          <dt>
            <span id="folhasEmbargo">
              <Translate contentKey="cidhaApp.processo.folhasEmbargo">Folhas Embargo</Translate>
            </span>
          </dt>
          <dd>{processoEntity.folhasEmbargo}</dd>
          <dt>
            <span id="acordaoEmbargo">
              <Translate contentKey="cidhaApp.processo.acordaoEmbargo">Acordao Embargo</Translate>
            </span>
          </dt>
          <dd>{processoEntity.acordaoEmbargo}</dd>
          <dt>
            <span id="folhasCienciaJulgEmbargos">
              <Translate contentKey="cidhaApp.processo.folhasCienciaJulgEmbargos">Folhas Ciencia Julg Embargos</Translate>
            </span>
          </dt>
          <dd>{processoEntity.folhasCienciaJulgEmbargos}</dd>
          <dt>
            <span id="apelacao">
              <Translate contentKey="cidhaApp.processo.apelacao">Apelacao</Translate>
            </span>
          </dt>
          <dd>{processoEntity.apelacao}</dd>
          <dt>
            <span id="folhasApelacao">
              <Translate contentKey="cidhaApp.processo.folhasApelacao">Folhas Apelacao</Translate>
            </span>
          </dt>
          <dd>{processoEntity.folhasApelacao}</dd>
          <dt>
            <span id="acordaoApelacao">
              <Translate contentKey="cidhaApp.processo.acordaoApelacao">Acordao Apelacao</Translate>
            </span>
          </dt>
          <dd>{processoEntity.acordaoApelacao}</dd>
          <dt>
            <span id="folhasCienciaJulgApelacao">
              <Translate contentKey="cidhaApp.processo.folhasCienciaJulgApelacao">Folhas Ciencia Julg Apelacao</Translate>
            </span>
          </dt>
          <dd>{processoEntity.folhasCienciaJulgApelacao}</dd>
          <dt>
            <span id="embargoDeclaracao">
              <Translate contentKey="cidhaApp.processo.embargoDeclaracao">Embargo Declaracao</Translate>
            </span>
          </dt>
          <dd>{processoEntity.embargoDeclaracao ? 'true' : 'false'}</dd>
          <dt>
            <span id="embargoRecursoExtraordinario">
              <Translate contentKey="cidhaApp.processo.embargoRecursoExtraordinario">Embargo Recurso Extraordinario</Translate>
            </span>
          </dt>
          <dd>{processoEntity.embargoRecursoExtraordinario ? 'true' : 'false'}</dd>
          <dt>
            <span id="folhasRecursoEspecial">
              <Translate contentKey="cidhaApp.processo.folhasRecursoEspecial">Folhas Recurso Especial</Translate>
            </span>
          </dt>
          <dd>{processoEntity.folhasRecursoEspecial}</dd>
          <dt>
            <span id="acordaoRecursoEspecial">
              <Translate contentKey="cidhaApp.processo.acordaoRecursoEspecial">Acordao Recurso Especial</Translate>
            </span>
          </dt>
          <dd>{processoEntity.acordaoRecursoEspecial}</dd>
          <dt>
            <span id="folhasCienciaJulgamentoRecursoEspecial">
              <Translate contentKey="cidhaApp.processo.folhasCienciaJulgamentoRecursoEspecial">
                Folhas Ciencia Julgamento Recurso Especial
              </Translate>
            </span>
          </dt>
          <dd>{processoEntity.folhasCienciaJulgamentoRecursoEspecial}</dd>
          <dt>
            <span id="embargoRecursoEspecial">
              <Translate contentKey="cidhaApp.processo.embargoRecursoEspecial">Embargo Recurso Especial</Translate>
            </span>
          </dt>
          <dd>{processoEntity.embargoRecursoEspecial ? 'true' : 'false'}</dd>
          <dt>
            <span id="folhasCiencia">
              <Translate contentKey="cidhaApp.processo.folhasCiencia">Folhas Ciencia</Translate>
            </span>
          </dt>
          <dd>{processoEntity.folhasCiencia}</dd>
          <dt>
            <span id="agravoRespRe">
              <Translate contentKey="cidhaApp.processo.agravoRespRe">Agravo Resp Re</Translate>
            </span>
          </dt>
          <dd>{processoEntity.agravoRespRe}</dd>
          <dt>
            <span id="folhasRespRe">
              <Translate contentKey="cidhaApp.processo.folhasRespRe">Folhas Resp Re</Translate>
            </span>
          </dt>
          <dd>{processoEntity.folhasRespRe}</dd>
          <dt>
            <span id="acordaoAgravoRespRe">
              <Translate contentKey="cidhaApp.processo.acordaoAgravoRespRe">Acordao Agravo Resp Re</Translate>
            </span>
          </dt>
          <dd>{processoEntity.acordaoAgravoRespRe}</dd>
          <dt>
            <span id="folhasCienciaJulgamentoAgravoRespRe">
              <Translate contentKey="cidhaApp.processo.folhasCienciaJulgamentoAgravoRespRe">
                Folhas Ciencia Julgamento Agravo Resp Re
              </Translate>
            </span>
          </dt>
          <dd>{processoEntity.folhasCienciaJulgamentoAgravoRespRe}</dd>
          <dt>
            <span id="embargoRespRe">
              <Translate contentKey="cidhaApp.processo.embargoRespRe">Embargo Resp Re</Translate>
            </span>
          </dt>
          <dd>{processoEntity.embargoRespRe}</dd>
          <dt>
            <span id="agravoInterno">
              <Translate contentKey="cidhaApp.processo.agravoInterno">Agravo Interno</Translate>
            </span>
          </dt>
          <dd>{processoEntity.agravoInterno}</dd>
          <dt>
            <span id="folhasAgravoInterno">
              <Translate contentKey="cidhaApp.processo.folhasAgravoInterno">Folhas Agravo Interno</Translate>
            </span>
          </dt>
          <dd>{processoEntity.folhasAgravoInterno}</dd>
          <dt>
            <span id="embargoRecursoAgravo">
              <Translate contentKey="cidhaApp.processo.embargoRecursoAgravo">Embargo Recurso Agravo</Translate>
            </span>
          </dt>
          <dd>{processoEntity.embargoRecursoAgravo ? 'true' : 'false'}</dd>
          <dt>
            <span id="observacoes">
              <Translate contentKey="cidhaApp.processo.observacoes">Observacoes</Translate>
            </span>
          </dt>
          <dd>{processoEntity.observacoes}</dd>
          <dt>
            <span id="recursoSTJ">
              <Translate contentKey="cidhaApp.processo.recursoSTJ">Recurso STJ</Translate>
            </span>
          </dt>
          <dd>{processoEntity.recursoSTJ ? 'true' : 'false'}</dd>
          <dt>
            <span id="linkRecursoSTJ">
              <Translate contentKey="cidhaApp.processo.linkRecursoSTJ">Link Recurso STJ</Translate>
            </span>
          </dt>
          <dd>{processoEntity.linkRecursoSTJ}</dd>
          <dt>
            <span id="folhasRecursoSTJ">
              <Translate contentKey="cidhaApp.processo.folhasRecursoSTJ">Folhas Recurso STJ</Translate>
            </span>
          </dt>
          <dd>{processoEntity.folhasRecursoSTJ}</dd>
          <dt>
            <span id="recursoSTF">
              <Translate contentKey="cidhaApp.processo.recursoSTF">Recurso STF</Translate>
            </span>
          </dt>
          <dd>{processoEntity.recursoSTF ? 'true' : 'false'}</dd>
          <dt>
            <span id="linkRecursoSTF">
              <Translate contentKey="cidhaApp.processo.linkRecursoSTF">Link Recurso STF</Translate>
            </span>
          </dt>
          <dd>{processoEntity.linkRecursoSTF}</dd>
          <dt>
            <span id="folhasRecursoSTF">
              <Translate contentKey="cidhaApp.processo.folhasRecursoSTF">Folhas Recurso STF</Translate>
            </span>
          </dt>
          <dd>{processoEntity.folhasRecursoSTF}</dd>
          <dt>
            <span id="folhasMemorialMPF">
              <Translate contentKey="cidhaApp.processo.folhasMemorialMPF">Folhas Memorial MPF</Translate>
            </span>
          </dt>
          <dd>{processoEntity.folhasMemorialMPF}</dd>
          <dt>
            <span id="execusaoProvisoria">
              <Translate contentKey="cidhaApp.processo.execusaoProvisoria">Execusao Provisoria</Translate>
            </span>
          </dt>
          <dd>{processoEntity.execusaoProvisoria ? 'true' : 'false'}</dd>
          <dt>
            <span id="numeracaoExecusaoProvisoria">
              <Translate contentKey="cidhaApp.processo.numeracaoExecusaoProvisoria">Numeracao Execusao Provisoria</Translate>
            </span>
          </dt>
          <dd>{processoEntity.numeracaoExecusaoProvisoria}</dd>
          <dt>
            <span id="recuperacaoEfetivaCumprimentoSentenca">
              <Translate contentKey="cidhaApp.processo.recuperacaoEfetivaCumprimentoSentenca">
                Recuperacao Efetiva Cumprimento Sentenca
              </Translate>
            </span>
          </dt>
          <dd>{processoEntity.recuperacaoEfetivaCumprimentoSentenca}</dd>
          <dt>
            <span id="recuperacaoEfetivaCumprimentoSentencaObservacoes">
              <Translate contentKey="cidhaApp.processo.recuperacaoEfetivaCumprimentoSentencaObservacoes">
                Recuperacao Efetiva Cumprimento Sentenca Observacoes
              </Translate>
            </span>
          </dt>
          <dd>{processoEntity.recuperacaoEfetivaCumprimentoSentencaObservacoes}</dd>
          <dt>
            <span id="envolveEmpreendimento">
              <Translate contentKey="cidhaApp.processo.envolveEmpreendimento">Envolve Empreendimento</Translate>
            </span>
          </dt>
          <dd>{processoEntity.envolveEmpreendimento ? 'true' : 'false'}</dd>
          <dt>
            <span id="envolveExploracaoIlegal">
              <Translate contentKey="cidhaApp.processo.envolveExploracaoIlegal">Envolve Exploracao Ilegal</Translate>
            </span>
          </dt>
          <dd>{processoEntity.envolveExploracaoIlegal ? 'true' : 'false'}</dd>
          <dt>
            <span id="envolveTerraQuilombola">
              <Translate contentKey="cidhaApp.processo.envolveTerraQuilombola">Envolve Terra Quilombola</Translate>
            </span>
          </dt>
          <dd>{processoEntity.envolveTerraQuilombola ? 'true' : 'false'}</dd>
          <dt>
            <span id="envolveTerraComunidadeTradicional">
              <Translate contentKey="cidhaApp.processo.envolveTerraComunidadeTradicional">Envolve Terra Comunidade Tradicional</Translate>
            </span>
          </dt>
          <dd>{processoEntity.envolveTerraComunidadeTradicional ? 'true' : 'false'}</dd>
          <dt>
            <span id="envolveTerraIndigena">
              <Translate contentKey="cidhaApp.processo.envolveTerraIndigena">Envolve Terra Indigena</Translate>
            </span>
          </dt>
          <dd>{processoEntity.envolveTerraIndigena ? 'true' : 'false'}</dd>
          <dt>
            <span id="resumoFatos">
              <Translate contentKey="cidhaApp.processo.resumoFatos">Resumo Fatos</Translate>
            </span>
          </dt>
          <dd>{processoEntity.resumoFatos}</dd>
          <dt>
            <span id="tamanhoArea">
              <Translate contentKey="cidhaApp.processo.tamanhoArea">Tamanho Area</Translate>
            </span>
          </dt>
          <dd>{processoEntity.tamanhoArea}</dd>
          <dt>
            <span id="valorArea">
              <Translate contentKey="cidhaApp.processo.valorArea">Valor Area</Translate>
            </span>
          </dt>
          <dd>{processoEntity.valorArea}</dd>
          <dt>
            <span id="tamanhoAreaObservacao">
              <Translate contentKey="cidhaApp.processo.tamanhoAreaObservacao">Tamanho Area Observacao</Translate>
            </span>
          </dt>
          <dd>{processoEntity.tamanhoAreaObservacao}</dd>
          <dt>
            <span id="dadosGeograficosLitigioConflito">
              <Translate contentKey="cidhaApp.processo.dadosGeograficosLitigioConflito">Dados Geograficos Litigio Conflito</Translate>
            </span>
          </dt>
          <dd>{processoEntity.dadosGeograficosLitigioConflito ? 'true' : 'false'}</dd>
          <dt>
            <span id="latitude">
              <Translate contentKey="cidhaApp.processo.latitude">Latitude</Translate>
            </span>
          </dt>
          <dd>{processoEntity.latitude}</dd>
          <dt>
            <span id="longitude">
              <Translate contentKey="cidhaApp.processo.longitude">Longitude</Translate>
            </span>
          </dt>
          <dd>{processoEntity.longitude}</dd>
          <dt>
            <span id="numeroProcessoMPF">
              <Translate contentKey="cidhaApp.processo.numeroProcessoMPF">Numero Processo MPF</Translate>
            </span>
          </dt>
          <dd>{processoEntity.numeroProcessoMPF}</dd>
          <dt>
            <span id="numeroEmbargo">
              <Translate contentKey="cidhaApp.processo.numeroEmbargo">Numero Embargo</Translate>
            </span>
          </dt>
          <dd>{processoEntity.numeroEmbargo}</dd>
          <dt>
            <span id="pautaApelacao">
              <Translate contentKey="cidhaApp.processo.pautaApelacao">Pauta Apelacao</Translate>
            </span>
          </dt>
          <dd>{processoEntity.pautaApelacao}</dd>
          <dt>
            <span id="numeroRecursoEspecial">
              <Translate contentKey="cidhaApp.processo.numeroRecursoEspecial">Numero Recurso Especial</Translate>
            </span>
          </dt>
          <dd>{processoEntity.numeroRecursoEspecial}</dd>
          <dt>
            <span id="admissiblidade">
              <Translate contentKey="cidhaApp.processo.admissiblidade">Admissiblidade</Translate>
            </span>
          </dt>
          <dd>{processoEntity.admissiblidade}</dd>
          <dt>
            <span id="envolveGrandeProjeto">
              <Translate contentKey="cidhaApp.processo.envolveGrandeProjeto">Envolve Grande Projeto</Translate>
            </span>
          </dt>
          <dd>{processoEntity.envolveGrandeProjeto ? 'true' : 'false'}</dd>
          <dt>
            <span id="envolveUnidadeConservacao">
              <Translate contentKey="cidhaApp.processo.envolveUnidadeConservacao">Envolve Unidade Conservacao</Translate>
            </span>
          </dt>
          <dd>{processoEntity.envolveUnidadeConservacao ? 'true' : 'false'}</dd>
          <dt>
            <span id="linkReferencia">
              <Translate contentKey="cidhaApp.processo.linkReferencia">Link Referencia</Translate>
            </span>
          </dt>
          <dd>{processoEntity.linkReferencia}</dd>
          <dt>
            <span id="statusProcesso">
              <Translate contentKey="cidhaApp.processo.statusProcesso">Status Processo</Translate>
            </span>
          </dt>
          <dd>{processoEntity.statusProcesso}</dd>
          <dt>
            <Translate contentKey="cidhaApp.processo.tipoDecisao">Tipo Decisao</Translate>
          </dt>
          <dd>{processoEntity.tipoDecisao ? processoEntity.tipoDecisao.descricao : ''}</dd>
          <dt>
            <Translate contentKey="cidhaApp.processo.tipoEmpreendimento">Tipo Empreendimento</Translate>
          </dt>
          <dd>{processoEntity.tipoEmpreendimento ? processoEntity.tipoEmpreendimento.descricao : ''}</dd>
          <dt>
            <Translate contentKey="cidhaApp.processo.secaoJudiciaria">Secao Judiciaria</Translate>
          </dt>
          <dd>{processoEntity.secaoJudiciaria ? processoEntity.secaoJudiciaria.nome : ''}</dd>
          <dt>
            <Translate contentKey="cidhaApp.processo.comarca">Comarca</Translate>
          </dt>
          <dd>
            {processoEntity.comarcas
              ? processoEntity.comarcas.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.nome}</a>
                    {processoEntity.comarcas && i === processoEntity.comarcas.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>
            <Translate contentKey="cidhaApp.processo.municipio">Municipio</Translate>
          </dt>
          <dd>
            {processoEntity.municipios
              ? processoEntity.municipios.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.nome}</a>
                    {processoEntity.municipios && i === processoEntity.municipios.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>
            <Translate contentKey="cidhaApp.processo.territorio">Territorio</Translate>
          </dt>
          <dd>
            {processoEntity.territorios
              ? processoEntity.territorios.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.nome}</a>
                    {processoEntity.territorios && i === processoEntity.territorios.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>
            <Translate contentKey="cidhaApp.processo.atividadeExploracaoIlegal">Atividade Exploracao Ilegal</Translate>
          </dt>
          <dd>
            {processoEntity.atividadeExploracaoIlegals
              ? processoEntity.atividadeExploracaoIlegals.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.descricao}</a>
                    {processoEntity.atividadeExploracaoIlegals && i === processoEntity.atividadeExploracaoIlegals.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>
            <Translate contentKey="cidhaApp.processo.unidadeConservacao">Unidade Conservacao</Translate>
          </dt>
          <dd>
            {processoEntity.unidadeConservacaos
              ? processoEntity.unidadeConservacaos.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.descricao}</a>
                    {processoEntity.unidadeConservacaos && i === processoEntity.unidadeConservacaos.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>
            <Translate contentKey="cidhaApp.processo.envolvidosConflitoLitigio">Envolvidos Conflito Litigio</Translate>
          </dt>
          <dd>
            {processoEntity.envolvidosConflitoLitigios
              ? processoEntity.envolvidosConflitoLitigios.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {processoEntity.envolvidosConflitoLitigios && i === processoEntity.envolvidosConflitoLitigios.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>
            <Translate contentKey="cidhaApp.processo.terraIndigena">Terra Indigena</Translate>
          </dt>
          <dd>
            {processoEntity.terraIndigenas
              ? processoEntity.terraIndigenas.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.descricao}</a>
                    {processoEntity.terraIndigenas && i === processoEntity.terraIndigenas.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>
            <Translate contentKey="cidhaApp.processo.processoConflito">Processo Conflito</Translate>
          </dt>
          <dd>
            {processoEntity.processoConflitos
              ? processoEntity.processoConflitos.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.nomeCasoComuidade}</a>
                    {processoEntity.processoConflitos && i === processoEntity.processoConflitos.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>
            <Translate contentKey="cidhaApp.processo.parteInteresssada">Parte Interesssada</Translate>
          </dt>
          <dd>
            {processoEntity.parteInteresssadas
              ? processoEntity.parteInteresssadas.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.nome}</a>
                    {processoEntity.parteInteresssadas && i === processoEntity.parteInteresssadas.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>
            <Translate contentKey="cidhaApp.processo.relator">Relator</Translate>
          </dt>
          <dd>
            {processoEntity.relators
              ? processoEntity.relators.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.nome}</a>
                    {processoEntity.relators && i === processoEntity.relators.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>
            <Translate contentKey="cidhaApp.processo.quilombo">Quilombo</Translate>
          </dt>
          <dd>
            {processoEntity.quilombos
              ? processoEntity.quilombos.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.nome}</a>
                    {processoEntity.quilombos && i === processoEntity.quilombos.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/processo" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/processo/${processoEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ processo }: IRootState) => ({
  processoEntity: processo.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ProcessoDetail);
