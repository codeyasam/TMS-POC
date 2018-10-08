import React from 'react'
import PropTypes from 'prop-types'
import { Modal } from 'semantic-ui-react'

const ModalForm = ({ isVisible, formHeader, onCloseModal=f=>f, FormUI }) => {
    return (
        <Modal open={isVisible}
            size='tiny'
            onClose={onCloseModal}
            closeIcon>
            <Modal.Header>{formHeader}</Modal.Header>
            <Modal.Content>
                <FormUI />
            </Modal.Content>
        </Modal>    
    )
}

ModalForm.propTypes = {
    isVisible: PropTypes.bool,
    formHeader: PropTypes.string,
    onCloseModal: PropTypes.func,
    FormUI: PropTypes.func
}

export default ModalForm