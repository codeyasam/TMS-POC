import React, { Component } from 'react'
import { Segment } from 'semantic-ui-react'
import Uploads from './Uploads'
import ModuleImportForm from './ModuleImportForm'

class SidebarBody extends Component {
    
    render() {
        const { pathname } = this.props
        return(
            <Segment basic style={{ minHeight: 300 }} >
            { pathname === "/uploads" &&
                <Uploads />
            }
            {
              pathname === "/uploads/importModule" &&
                <ModuleImportForm />
            }
            </Segment>            
        )
    }
    
}

export default SidebarBody