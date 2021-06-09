import React from 'react';
import MenuItem from 'app/shared/layout/menus/menu-item';
import { Translate, translate } from 'react-jhipster';
import { NavDropdown } from './menu-components';

export const EntitiesMenuThree = props => (
  <NavDropdown
    icon="th-list"
    name={translate('global.menu.entities.main_3')}
    id="entity-menu"
    data-cy="entity"
    style={{ maxHeight: '80vh', overflow: 'auto' }}
  >
    <MenuItem icon="asterisk" to="/problema-juridico">
      <Translate contentKey="global.menu.entities.problemaJuridico" />
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

    <MenuItem icon="asterisk" to="/processo-conflito">
      <Translate contentKey="global.menu.entities.processoConflito" />
    </MenuItem>

    <MenuItem icon="asterisk" to="/recurso">
      <Translate contentKey="global.menu.entities.recurso" />
    </MenuItem>

    <MenuItem icon="asterisk" to="/envolvidos-conflito-litigio">
      <Translate contentKey="global.menu.entities.envolvidosConflitoLitigio" />
    </MenuItem>

    <MenuItem icon="asterisk" to="/representante-legal">
      <Translate contentKey="global.menu.entities.representanteLegal" />
    </MenuItem>

    <MenuItem icon="asterisk" to="/parte-interesssada">
      <Translate contentKey="global.menu.entities.parteInteresssada" />
    </MenuItem>

    <MenuItem icon="asterisk" to="/embargo-resp-re">
      <Translate contentKey="global.menu.entities.embargoRespRe" />
    </MenuItem>

    <MenuItem icon="asterisk" to="/embargo-declaracao">
      <Translate contentKey="global.menu.entities.embargoDeclaracao" />
    </MenuItem>

    <MenuItem icon="asterisk" to="/embargo-declaracao-agravo">
      <Translate contentKey="global.menu.entities.embargoDeclaracaoAgravo" />
    </MenuItem>

    <MenuItem icon="asterisk" to="/embargo-recurso-especial">
      <Translate contentKey="global.menu.entities.embargoRecursoEspecial" />
    </MenuItem>

    <MenuItem icon="asterisk" to="/relator">
      <Translate contentKey="global.menu.entities.relator" />
    </MenuItem>
  </NavDropdown>
);
