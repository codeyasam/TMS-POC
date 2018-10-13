import React from 'react'
import { Segment, Divider, Button, Label, Icon } from 'semantic-ui-react'

const SelectedEntries = ({ selectedEntries=[], onClearSelectedEntries, onClearSelectedEntriesByPage, onSetPaginationPage, pageSize }) => {
    
    const setupSelectedEntriesInfo = () => {
        let entriesPerPageCount = mapSelectedEntriesToPages()
        return entriesPerPageCount.map((value, key) => {
            return  (
                <Label as='a' key={key} onClick={() => onSetPaginationPage(key, pageSize) }>
                    {value} selected entries on page {key}
                    <Icon name="delete" onClick={() => onClearSelectedEntriesByPage(key) } />
                </Label>
            )                 
        })
    }
                         
    const mapSelectedEntriesToPages = () => {
        let pages = []
        console.log(selectedEntries)
        console.log("above is selected entries")
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
        <Segment>
            { setupSelectedEntriesInfo() }
            <Divider />
            <Button onClick={onClearSelectedEntries}>Clear Selected Entries</Button>
        </Segment>
    )
}

export default SelectedEntries