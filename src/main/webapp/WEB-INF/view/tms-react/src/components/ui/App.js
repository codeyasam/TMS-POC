import React, { Component } from 'react'
import { Menu, Segment, Sidebar, Input } from 'semantic-ui-react'

class App extends Component {
    
    state = { visible: false }
    handleButtonClick = () => this.setState({ visible: !this.state.visible })
    handleSidebarHide = () => this.setState({ visible: false })    
    
    render() {
        const { visible } = this.state        
        
        return (
            <div>
                <Menu>
                    <Menu.Item
                      icon="list"
                      onClick={this.handleButtonClick} />
                
                    <Menu.Menu position='right'>
                      <Menu.Item>
                        <Input icon='search' placeholder='Search...' />
                      </Menu.Item>
                      <Menu.Item
                        name='logout'
                        onClick={this.props.onLogout}
                      />
                    </Menu.Menu>                      
                </Menu>
                <Sidebar.Pushable as={Segment}>
                  <Sidebar
                    as={Menu}
                    animation='overlay'
                    icon='labeled'
                    inverted
                    onHide={this.handleSidebarHide}
                    vertical
                    visible={visible}
                    width='thin'
                  >
                    <Menu.Item as='a'>
                      Uploads
                    </Menu.Item>
                  </Sidebar>
        
                  <Sidebar.Pusher>
                    <Segment basic style={{ height: 300 }}>
                    </Segment>
                  </Sidebar.Pusher>
                </Sidebar.Pushable>        
            </div>
        )        
    }
}

export default App