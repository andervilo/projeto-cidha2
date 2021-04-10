import React from 'react';
import MenuItem from 'app/shared/layout/menus/menu-item';
import { Translate, translate } from 'react-jhipster';
import { NavDropdown } from './menu-components';

export const EntitiesMenu = props => (
  <NavDropdown
    icon="th-list"
    name={translate('global.menu.entities.main')}
    id="entity-menu"
    data-cy="entity"
    style={{ maxHeight: '80vh', overflow: 'auto' }}
  >
    <MenuItem icon="asterisk" to="/comarca">
      <Translate contentKey="global.menu.entities.comarca" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/quilombo">
      <Translate contentKey="global.menu.entities.quilombo" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/processo">
      <Translate contentKey="global.menu.entities.processo" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/concessao-liminar">
      <Translate contentKey="global.menu.entities.concessaoLiminar" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/tipo-empreendimento">
      <Translate contentKey="global.menu.entities.tipoEmpreendimento" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/tipo-decisao">
      <Translate contentKey="global.menu.entities.tipoDecisao" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/embargo-resp-re">
      <Translate contentKey="global.menu.entities.embargoRespRe" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/concessao-liminar-cassada">
      <Translate contentKey="global.menu.entities.concessaoLiminarCassada" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/embargo-declaracao">
      <Translate contentKey="global.menu.entities.embargoDeclaracao" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/embargo-declaracao-agravo">
      <Translate contentKey="global.menu.entities.embargoDeclaracaoAgravo" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/recurso">
      <Translate contentKey="global.menu.entities.recurso" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/tipo-recurso">
      <Translate contentKey="global.menu.entities.tipoRecurso" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/opcao-recurso">
      <Translate contentKey="global.menu.entities.opcaoRecurso" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/envolvidos-conflito-litigio">
      <Translate contentKey="global.menu.entities.envolvidosConflitoLitigio" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/tipo-data">
      <Translate contentKey="global.menu.entities.tipoData" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/data">
      <Translate contentKey="global.menu.entities.data" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/fundamentacao-doutrinaria">
      <Translate contentKey="global.menu.entities.fundamentacaoDoutrinaria" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/jurisprudencia">
      <Translate contentKey="global.menu.entities.jurisprudencia" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/fundamentacao-legal">
      <Translate contentKey="global.menu.entities.fundamentacaoLegal" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/instrumento-internacional">
      <Translate contentKey="global.menu.entities.instrumentoInternacional" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/problema-juridico">
      <Translate contentKey="global.menu.entities.problemaJuridico" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/municipio">
      <Translate contentKey="global.menu.entities.municipio" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/territorio">
      <Translate contentKey="global.menu.entities.territorio" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/embargo-recurso-especial">
      <Translate contentKey="global.menu.entities.embargoRecursoEspecial" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/atividade-exploracao-ilegal">
      <Translate contentKey="global.menu.entities.atividadeExploracaoIlegal" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/unidade-conservacao">
      <Translate contentKey="global.menu.entities.unidadeConservacao" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/terra-indigena">
      <Translate contentKey="global.menu.entities.terraIndigena" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/etnia-indigena">
      <Translate contentKey="global.menu.entities.etniaIndigena" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/direito">
      <Translate contentKey="global.menu.entities.direito" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/conflito">
      <Translate contentKey="global.menu.entities.conflito" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/processo-conflito">
      <Translate contentKey="global.menu.entities.processoConflito" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/tipo-representante">
      <Translate contentKey="global.menu.entities.tipoRepresentante" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/representante-legal">
      <Translate contentKey="global.menu.entities.representanteLegal" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/parte-interesssada">
      <Translate contentKey="global.menu.entities.parteInteresssada" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/relator">
      <Translate contentKey="global.menu.entities.relator" />
    </MenuItem>
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
  </NavDropdown>
);
