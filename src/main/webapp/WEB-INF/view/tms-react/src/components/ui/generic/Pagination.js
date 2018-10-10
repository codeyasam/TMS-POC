import React from 'react'
import { Menu, Icon } from 'semantic-ui-react'

const Pagination = ({ total=20, pageSize=3, currentPage=6, navSize=5, onSetPaginationPage=f=>f, onSetPaginationNextPage, onSetPaginationPreviousPage }) => {
    
    const totalPages = total / pageSize
    
    const setupNav = () => {
        let menuArray = []
        let startIndex = currentPage / navSize
        startIndex = startIndex - Math.floor(startIndex) !== 0 ?
            Math.floor(startIndex) * navSize + 1 : 
            (Math.floor(startIndex) - 1) * navSize + 1
        
        let endIndex = startIndex + navSize
        endIndex = endIndex <= totalPages + 1 ?
            endIndex : startIndex + totalPages % navSize
        
        console.log(endIndex)
        for (let i = startIndex; i < endIndex; i++) {
            console.log("executed")
            menuArray = [...menuArray, 
                <Menu.Item as='a' 
                    key={i} 
                    onClick={() => onSetPaginationPage(i, pageSize)}
                    active={i === currentPage}>{i}</Menu.Item>]
        }
        return menuArray
    }
    
    return (
        <Menu floated='right' pagination>
            <Menu.Item as='a' icon disabled={currentPage === 1} onClick={() => onSetPaginationPage(1, pageSize)}>
                <Icon name='chevron left' />
                <Icon name='chevron left' />                
            </Menu.Item>        
            <Menu.Item as='a' icon disabled={currentPage === 1} onClick={() => onSetPaginationPreviousPage(currentPage, pageSize)}>
                <Icon name='chevron left' />
            </Menu.Item>
            {setupNav()}
            <Menu.Item as='a' icon disabled={currentPage === totalPages} onClick={() => onSetPaginationNextPage(currentPage, pageSize)}>
                <Icon name='chevron right' />
            </Menu.Item>
            <Menu.Item as='a' icon disabled={currentPage === totalPages} onClick={() => onSetPaginationPage(totalPages, pageSize)}>
                <Icon name='chevron right' />
                <Icon name='chevron right' />                
            </Menu.Item>            
        </Menu>    
    )
}

export default Pagination