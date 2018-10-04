import React, { Component } from 'react'
import { Button, Header, Modal } from 'semantic-ui-react'

export default class ModalExampleControlled extends Component {
      
  constructor(props) {
      super(props)
      this.state = { isVisible: this.props.isVisible }
      this.onHandleClose = this.onHandleClose.bind(this)
      console.log("constructor executed ", this.props.isVisible)
  }
 
  componentWillReceiveProps(nextProps) {
      this.setState({ isVisible: nextProps.isVisible })
  }
    
  onHandleClose() {
      this.setState({ isVisible: false })
  }
    
  render() {
    console.log("ModalControlled", this.state.isVisible)  
    return (
      <Modal
        open={this.state.isVisible}
        basic
        size='small'
      >
        <Header icon='browser' content={this.props.header} />
        <Modal.Content>
          <h3>{this.props.message}</h3>
        </Modal.Content>
        <Modal.Actions>
          <Button className="teal" onClick={this.onHandleClose}>
                OK
          </Button>
        </Modal.Actions>
      </Modal>
    )
  }
}
