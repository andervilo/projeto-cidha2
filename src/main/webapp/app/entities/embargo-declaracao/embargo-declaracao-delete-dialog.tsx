import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { RouteComponentProps } from 'react-router-dom';
import { Modal, ModalHeader, ModalBody, ModalFooter, Button } from 'reactstrap';
import { Translate, ICrudGetAction, ICrudDeleteAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IEmbargoDeclaracao } from 'app/shared/model/embargo-declaracao.model';
import { IRootState } from 'app/shared/reducers';
import { getEntity, deleteEntity } from './embargo-declaracao.reducer';

export interface IEmbargoDeclaracaoDeleteDialogProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const EmbargoDeclaracaoDeleteDialog = (props: IEmbargoDeclaracaoDeleteDialogProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const handleClose = () => {
    props.history.push('/embargo-declaracao' + props.location.search);
  };

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const confirmDelete = () => {
    props.deleteEntity(props.embargoDeclaracaoEntity.id);
  };

  const { embargoDeclaracaoEntity } = props;
  return (
    <Modal isOpen toggle={handleClose}>
      <ModalHeader toggle={handleClose}>
        <Translate contentKey="entity.delete.title">Confirm delete operation</Translate>
      </ModalHeader>
      <ModalBody id="cidhaApp.embargoDeclaracao.delete.question">
        <Translate contentKey="cidhaApp.embargoDeclaracao.delete.question" interpolate={{ id: embargoDeclaracaoEntity.id }}>
          Are you sure you want to delete this EmbargoDeclaracao?
        </Translate>
      </ModalBody>
      <ModalFooter>
        <Button color="secondary" onClick={handleClose}>
          <FontAwesomeIcon icon="ban" />
          &nbsp;
          <Translate contentKey="entity.action.cancel">Cancel</Translate>
        </Button>
        <Button id="jhi-confirm-delete-embargoDeclaracao" color="danger" onClick={confirmDelete}>
          <FontAwesomeIcon icon="trash" />
          &nbsp;
          <Translate contentKey="entity.action.delete">Delete</Translate>
        </Button>
      </ModalFooter>
    </Modal>
  );
};

const mapStateToProps = ({ embargoDeclaracao }: IRootState) => ({
  embargoDeclaracaoEntity: embargoDeclaracao.entity,
  updateSuccess: embargoDeclaracao.updateSuccess,
});

const mapDispatchToProps = { getEntity, deleteEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(EmbargoDeclaracaoDeleteDialog);
