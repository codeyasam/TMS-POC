import React, { Component } from 'react'
import { Link } from 'react-router-dom'
import { Menu, Segment, Sidebar } from 'semantic-ui-react'
import SidebarBody from './SidebarBody'

class AppSidebar extends Component {
    
    render() {
        return (
            <div>
                <Sidebar.Pushable as={Segment}>
                  <Sidebar
                    as={Menu}
                    animation='overlay'
                    icon='labeled'
                    inverted
                    onHide={this.props.onSidebarHide}
                    vertical
                    visible={this.props.isSidebarVisible}
                    width='thin'
                  >
                    <Menu.Item>
                        <Link to="/uploads" onClick={this.props.onSidebarHide}>
                            Uploads
                        </Link>
                    </Menu.Item>
            
                    <Menu.Item>
                        <Link to="/applications" onClick={this.props.onSidebarHide}>
                            Applications
                        </Link>
                    </Menu.Item>        
            
                    <Menu.Item>
                        <Link to="/modules">
                            Modules
                        </Link>
                    </Menu.Item>   
            
                    <Menu.Item>
                        <Link to="/machines">
                            Machines
                        </Link>
                    </Menu.Item>           
            
                    <Menu.Item>
                        <Link to="/testcases">
                            Test cases
                        </Link>
                    </Menu.Item>            
                  </Sidebar>

                  <Sidebar.Pusher>
                    <SidebarBody pathname={this.props.pathname} />
                  </Sidebar.Pusher>
                </Sidebar.Pushable>                       
            </div>
        )        
    }

}

export default AppSidebar