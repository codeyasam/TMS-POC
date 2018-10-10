import React from 'react'
import { Menu, Icon } from 'semantic-ui-react'

const Pagination = ({ total=13, pageSize, currentPage=13, navSize=5 }) => {
    
    const setupNav = () => {
        let menuArray = []
        let startIndex = currentPage / navSize
        startIndex = startIndex - Math.floor(startIndex) !== 0 ?
            Math.floor(startIndex) * navSize + 1 : 
            (Math.floor(startIndex) - 1) * navSize + 1
        
        let endIndex = startIndex + navSize
        endIndex = endIndex <= total + 1 ?
            endIndex : startIndex + total % navSize
        
        console.log(endIndex)
        for (let i = startIndex; i < endIndex; i++) {
            console.log("executed")
            menuArray = [...menuArray, <Menu.Item as='a' key={i}>{i}</Menu.Item>]
        }
        return menuArray
    }
    
    return (
        
        <Menu floated='right' pagination>
            <Menu.Item as='a' icon disabled={currentPage === 1}>
                <Icon name='chevron left' />
                <Icon name='chevron left' />                
            </Menu.Item>        
            <Menu.Item as='a' icon disabled={currentPage === 1}>
                <Icon name='chevron left' />
            </Menu.Item>
            {setupNav()}
            <Menu.Item as='a' icon disabled={currentPage === total}>
                <Icon name='chevron right' />
            </Menu.Item>
            <Menu.Item as='a' icon disabled={currentPage === total}>
                <Icon name='chevron right' />
                <Icon name='chevron right' />                
            </Menu.Item>            
        </Menu>    
    )
}

export default Pagination