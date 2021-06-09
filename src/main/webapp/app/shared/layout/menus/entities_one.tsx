import React from 'react';
import MenuItem from 'app/shared/layout/menus/menu-item';
import { Translate, translate } from 'react-jhipster';
import { NavDropdown } from './menu-components';

export const EntitiesMenuOne = props => (
  <NavDropdown
    icon="th-list"
    name={translate('global.menu.entities.main_2')}
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
    <MenuItem icon="asterisk" to="/concessao-liminar">
      <Translate contentKey="global.menu.entities.concessaoLiminar" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/tipo-empreendimento">
      <Translate contentKey="global.menu.entities.tipoEmpreendimento" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/tipo-decisao">
      <Translate contentKey="global.menu.entities.tipoDecisao" />
    </MenuItem>

    <MenuItem icon="asterisk" to="/concessao-liminar-cassada">
      <Translate contentKey="global.menu.entities.concessaoLiminarCassada" />
    </MenuItem>

    <MenuItem icon="asterisk" to="/tipo-recurso">
      <Translate contentKey="global.menu.entities.tipoRecurso" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/opcao-recurso">
      <Translate contentKey="global.menu.entities.opcaoRecurso" />
    </MenuItem>

    <MenuItem icon="asterisk" to="/municipio">
      <Translate contentKey="global.menu.entities.municipio" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/territorio">
      <Translate contentKey="global.menu.entities.territorio" />
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

    <MenuItem icon="asterisk" to="/tipo-representante">
      <Translate contentKey="global.menu.entities.tipoRepresentante" />
    </MenuItem>

    <MenuItem icon="asterisk" to="/secao-judiciaria">
      <Translate contentKey="global.menu.entities.secaoJudiciaria" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/subsecao-judiciaria">
      <Translate contentKey="global.menu.entities.subsecaoJudiciaria" />
    </MenuItem>
  </NavDropdown>
);
