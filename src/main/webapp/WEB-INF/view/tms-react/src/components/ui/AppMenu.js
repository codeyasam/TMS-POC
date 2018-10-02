import React from 'react'
import { Menu } from 'semantic-ui-react'

const AppMenu = ({ onLogout=f=>f, onToggleSidebar, isSidebarVisible }) => {
           
    const toggleSidebar = () => {
        onToggleSidebar(isSidebarVisible)
    }
    
    return (
        <div>
            <Menu>
                <Menu.Item
                  icon="list"
                  onClick={toggleSidebar} />

                <Menu.Menu position='right'>
                  <Menu.Item
                    name='logout'
                    onClick={onLogout}
                  />
                </Menu.Menu>                      
            </Menu>
        </div>              
    )        
}

export default AppMenu