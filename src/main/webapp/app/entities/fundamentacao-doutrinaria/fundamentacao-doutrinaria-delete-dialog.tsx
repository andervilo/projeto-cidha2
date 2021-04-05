import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { RouteComponentProps } from 'react-router-dom';
import { Modal, ModalHeader, ModalBody, ModalFooter, Button } from 'reactstrap';
import { Translate, ICrudGetAction, ICrudDeleteAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IFundamentacaoDoutrinaria } from 'app/shared/model/fundamentacao-doutrinaria.model';
import { IRootState } from 'app/shared/reducers';
import { getEntity, deleteEntity } from './fundamentacao-doutrinaria.reducer';

export interface IFundamentacaoDoutrinariaDeleteDialogProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const FundamentacaoDoutrinariaDeleteDialog = (props: IFundamentacaoDoutrinariaDeleteDialogProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const handleClose = () => {
    props.history.push('/fundamentacao-doutrinaria' + props.location.search);
  };

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const confirmDelete = () => {
    props.deleteEntity(props.fundamentacaoDoutrinariaEntity.id);
  };

  const { fundamentacaoDoutrinariaEntity } = props;
  return (
    <Modal isOpen toggle={handleClose}>
      <ModalHeader toggle={handleClose}>
        <Translate contentKey="entity.delete.title">Confirm delete operation</Translate>
      </ModalHeader>
      <ModalBody id="cidhaApp.fundamentacaoDoutrinaria.delete.question">
        <Translate contentKey="cidhaApp.fundamentacaoDoutrinaria.delete.question" interpolate={{ id: fundamentacaoDoutrinariaEntity.id }}>
          Are you sure you want to delete this FundamentacaoDoutrinaria?
        </Translate>
      </ModalBody>
      <ModalFooter>
        <Button color="secondary" onClick={handleClose}>
          <FontAwesomeIcon icon="ban" />
          &nbsp;
          <Translate contentKey="entity.action.cancel">Cancel</Translate>
        </Button>
        <Button id="jhi-confirm-delete-fundamentacaoDoutrinaria" color="danger" onClick={confirmDelete}>
          <FontAwesomeIcon icon="trash" />
          &nbsp;
          <Translate contentKey="entity.action.delete">Delete</Translate>
        </Button>
      </ModalFooter>
    </Modal>
  );
};

const mapStateToProps = ({ fundamentacaoDoutrinaria }: IRootState) => ({
  fundamentacaoDoutrinariaEntity: fundamentacaoDoutrinaria.entity,
  updateSuccess: fundamentacaoDoutrinaria.updateSuccess,
});

const mapDispatchToProps = { getEntity, deleteEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(FundamentacaoDoutrinariaDeleteDialog);
