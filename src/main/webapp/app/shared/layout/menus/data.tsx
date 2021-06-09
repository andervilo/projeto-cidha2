import React from 'react';
import MenuItem from 'app/shared/layout/menus/menu-item';
import { Translate, translate } from 'react-jhipster';
import { NavDropdown } from './menu-components';

export const EntitiesMenuData = props => (
  <NavDropdown
    icon="th-list"
    name={translate('global.menu.entities.main_4')}
    id="entity-menu"
    data-cy="entity"
    style={{ maxHeight: '80vh', overflow: 'auto' }}
  >
    <MenuItem icon="asterisk" to="/data">
      <Translate contentKey="global.menu.entities.data" />
    </MenuItem>

    <MenuItem icon="asterisk" to="/tipo-data">
      <Translate contentKey="global.menu.entities.tipoData" />
    </MenuItem>
  </NavDropdown>
);
