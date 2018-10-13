import React from 'react'
import { Menu, Icon, Segment, Label, Button } from 'semantic-ui-react'

const Pagination = ({ total=20, pageSize=3, currentPage=6, navSize=5, onSetPaginationPage=f=>f, onSetPaginationNextPage, onSetPaginationPreviousPage, searchText }) => {
    
    const totalPages = Math.ceil(total / pageSize)
    
    let _pageNumberJumpValue
    
    const setupNav = () => {
        let menuArray = []
        let startIndex = currentPage / navSize
        startIndex = startIndex - Math.floor(startIndex) !== 0 ?
            Math.floor(startIndex) * navSize + 1 : 
            (Math.floor(startIndex) - 1) * navSize + 1
        
        let endIndex = startIndex + navSize
        endIndex = endIndex <= totalPages + 1 ?
            endIndex : startIndex + totalPages % navSize
        
        for (let i = startIndex; i < endIndex; i++) {
            console.log("executed")
            menuArray = [...menuArray, 
                <Menu.Item as='a' 
                    key={i} 
                    onClick={() => onSetPaginationPage(searchText, i, pageSize)}
                    active={i === currentPage}>{i}</Menu.Item>]
        }
        return menuArray
    }
    
    const submit = (e) => {
        e.preventDefault()
        let pageNumber = parseInt(_pageNumberJumpValue.value, 10)
        onSetPaginationPage(searchText, pageNumber, pageSize)
    }
                         
    return (
        <Segment.Group horizontal>
            <Segment>
                <form className="form ui" onSubmit={submit}>
                    <div>
                          <Label pointing='right'>Set page number to</Label>
                          <input className="two wide field" 
                                type='number' min='1' 
                                max={totalPages}
                                ref={input => _pageNumberJumpValue = input} />
                          <Button>Go</Button>    
                          <Label>{totalPages} total pages</Label>
                    </div>
                </form>
            </Segment>
            <Segment>
                <Menu floated='right' pagination>
                    <Menu.Item as='a' icon disabled={currentPage === 1} onClick={() => onSetPaginationPage(searchText, 1, pageSize)}>
                        <Icon name='chevron left' />
                        <Icon name='chevron left' />                
                    </Menu.Item>        
                    <Menu.Item as='a' icon disabled={currentPage === 1} onClick={() => onSetPaginationPreviousPage(searchText, currentPage, pageSize)}>
                        <Icon name='chevron left' />
                    </Menu.Item>
                    {setupNav()}
                    <Menu.Item as='a' icon disabled={currentPage === totalPages} onClick={() => onSetPaginationNextPage(searchText, currentPage, pageSize)}>
                        <Icon name='chevron right' />
                    </Menu.Item>
                    <Menu.Item as='a' icon disabled={currentPage === totalPages} onClick={() => onSetPaginationPage(searchText, totalPages, pageSize)}>
                        <Icon name='chevron right' />
                        <Icon name='chevron right' />                
                    </Menu.Item>            
                </Menu> 
            </Segment>
        </Segment.Group>
    )
}

export default Pagination