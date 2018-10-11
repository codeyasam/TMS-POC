import React from 'react'
import { Menu, Icon, Segment, Label, Button, Divider } from 'semantic-ui-react'

const Pagination = ({ total=20, pageSize=3, currentPage=6, navSize=5, onSetPaginationPage=f=>f, onSetPaginationNextPage, onSetPaginationPreviousPage, selectedEntries, onClearSelectedEntries }) => {
    
    const totalPages = total / pageSize
    
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
    
    const submit = (e) => {
        e.preventDefault()
        let pageNumber = _pageNumberJumpValue.value
        onSetPaginationPage(pageNumber, pageSize)
    }
                         
    const setupSelectedEntriesInfo = () => {
        let entriesPerPageCount = mapSelectedEntriesToPages()
        return entriesPerPageCount.map((value, key) => {
            return  (
                <Label>{value} selected entries on page {key}</Label>
            )                 
        })
    }
                         
    const mapSelectedEntriesToPages = () => {
        let pages = []
        selectedEntries.forEach(entry => {
            let entriesCountByPage = pages[entry.currentPage]
            if (entriesCountByPage) {
                pages[entry.currentPage] += 1
            } else {
                pages[entry.currentPage] = 1
            }
        })
        console.log(pages)
        return pages
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
                          <Button className="two wide field" >Go</Button>    
                          <Label>{totalPages} total pages</Label>
                    </div>
                </form>
                <div>
                    <div>
                        { setupSelectedEntriesInfo() }
                    </div>
                    <Divider />
                    <Button onClick={onClearSelectedEntries}>Clear Selected Entries</Button>
                </div>
            </Segment>
            <Segment>
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
            </Segment>
        </Segment.Group>
    )
}

export default Pagination