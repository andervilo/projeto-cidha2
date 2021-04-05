import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { RouteComponentProps } from 'react-router-dom';
import { Modal, ModalHeader, ModalBody, ModalFooter, Button } from 'reactstrap';
import { Translate, ICrudGetAction, ICrudDeleteAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IInstrumentoInternacional } from 'app/shared/model/instrumento-internacional.model';
import { IRootState } from 'app/shared/reducers';
import { getEntity, deleteEntity } from './instrumento-internacional.reducer';

export interface IInstrumentoInternacionalDeleteDialogProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const InstrumentoInternacionalDeleteDialog = (props: IInstrumentoInternacionalDeleteDialogProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const handleClose = () => {
    props.history.push('/instrumento-internacional' + props.location.search);
  };

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const confirmDelete = () => {
    props.deleteEntity(props.instrumentoInternacionalEntity.id);
  };

  const { instrumentoInternacionalEntity } = props;
  return (
    <Modal isOpen toggle={handleClose}>
      <ModalHeader toggle={handleClose}>
        <Translate contentKey="entity.delete.title">Confirm delete operation</Translate>
      </ModalHeader>
      <ModalBody id="cidhaApp.instrumentoInternacional.delete.question">
        <Translate contentKey="cidhaApp.instrumentoInternacional.delete.question" interpolate={{ id: instrumentoInternacionalEntity.id }}>
          Are you sure you want to delete this InstrumentoInternacional?
        </Translate>
      </ModalBody>
      <ModalFooter>
        <Button color="secondary" onClick={handleClose}>
          <FontAwesomeIcon icon="ban" />
          &nbsp;
          <Translate contentKey="entity.action.cancel">Cancel</Translate>
        </Button>
        <Button id="jhi-confirm-delete-instrumentoInternacional" color="danger" onClick={confirmDelete}>
          <FontAwesomeIcon icon="trash" />
          &nbsp;
          <Translate contentKey="entity.action.delete">Delete</Translate>
        </Button>
      </ModalFooter>
    </Modal>
  );
};

const mapStateToProps = ({ instrumentoInternacional }: IRootState) => ({
  instrumentoInternacionalEntity: instrumentoInternacional.entity,
  updateSuccess: instrumentoInternacional.updateSuccess,
});

const mapDispatchToProps = { getEntity, deleteEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(InstrumentoInternacionalDeleteDialog);
