import React, { Component } from 'react'
import AppMenu from '../container/AppMenu'
import AppSidebar from './AppSidebar'

class App extends Component {

    state = { visible: true }
    handleButtonClick = () => this.setState({ visible: !this.state.visible })
    handleSidebarHide = () => this.setState({ visible: false })        
    
    render() {
        console.log("rendered")
        const { visible } = this.state
        const { pathname } = this.props.location
        return (
            <div>
                <AppMenu onToggleSidebar={this.handleButtonClick} />
                <AppSidebar isSidebarVisible={visible} 
                        onSidebarHide={this.handleSidebarHide} 
                        pathname={pathname} />
            </div>
        )        
    }
}

export default App