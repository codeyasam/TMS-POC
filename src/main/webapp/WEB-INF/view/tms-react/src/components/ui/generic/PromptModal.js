import React from 'react'
import PropTypes from 'prop-types'
import { Button, Header, Modal } from 'semantic-ui-react'

const PromptModal = ({ isVisible, header, message, onHandleClose=f=>f }) => {
    
    return (
      <Modal open={isVisible} basic size='small'>
        <Header icon='browser' content={header} />
        <Modal.Content>
          <h3>{message}</h3>
        </Modal.Content>
        <Modal.Actions>
          <Button className="teal" onClick={onHandleClose}>
                OK
          </Button>
        </Modal.Actions>
      </Modal>        
    )
}

PromptModal.propTypes = {
    isVisible: PropTypes.bool,
    header: PropTypes.string,
    message: PropTypes.string,
    onHandleClose: PropTypes.func
}

export default PromptModal