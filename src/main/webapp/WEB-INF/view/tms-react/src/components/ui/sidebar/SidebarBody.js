import React, { Component } from 'react'
import { Segment } from 'semantic-ui-react'
import Uploads from '../uploads/Uploads'
import Applications from '../../container/applications/Applications'
import Modules from '../../container/modules/Modules'

class SidebarBody extends Component {
    
    render() {
        const { pathname } = this.props
        return(
            <Segment basic style={{ minHeight: 300 }} >
            { pathname === "/uploads" &&
                <Uploads />
            }
            { pathname === "/applications" && 
                <Applications />
            }
            { pathname === "/modules" &&
                <Modules />
            }
            </Segment>            
        )
    }
    
}

export default SidebarBody