import React, { Component } from 'react'
import { Segment } from 'semantic-ui-react'
import Uploads from './Uploads'

class SidebarBody extends Component {
    
    render() {
        const { pathname } = this.props
        return(
            <Segment basic style={{ minHeight: 300 }} >
            { pathname === "/uploads" &&
                <Uploads />
            }
            </Segment>            
        )
    }
    
}

export default SidebarBody