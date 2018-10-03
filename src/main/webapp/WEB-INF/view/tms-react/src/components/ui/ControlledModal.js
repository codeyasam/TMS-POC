import React, { Component } from 'react'
import { Button, Header, Icon, Modal } from 'semantic-ui-react'

export default class ModalExampleControlled extends Component {

  render() {
    return (
      <Modal
        open={true}
        basic
        size='small'
      >
        <Header icon='browser' content={this.props.header} />
        <Modal.Content>
          <h3>{this.props.message}</h3>
        </Modal.Content>
        <Modal.Actions>
          <Button className="teal" onClick={this.props.onHandleClose}>
                OK
          </Button>
        </Modal.Actions>
      </Modal>
    )
  }
}
