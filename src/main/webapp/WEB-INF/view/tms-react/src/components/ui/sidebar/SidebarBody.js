import React, { Component } from 'react'
import { Segment } from 'semantic-ui-react'
import Uploads from '../uploads/Uploads'
import Applications from '../applications/Applications'

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
            </Segment>            
        )
    }
    
}

export default SidebarBody