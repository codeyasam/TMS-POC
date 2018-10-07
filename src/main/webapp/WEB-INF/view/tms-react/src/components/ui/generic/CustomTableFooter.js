import React from 'react'
import { Button, Table } from 'semantic-ui-react'

const CustomTableFooter =({ addButtonText, onAddButtonClick, selectedEntriesCount }) => {
    return (
        <Table.Footer fullWidth>
          <Table.Row>
            <Table.HeaderCell />
            <Table.HeaderCell colSpan='4'>
              <Button floated='right' primary onClick={onAddButtonClick}>
                { addButtonText }
              </Button>
              <Button disabled={selectedEntriesCount !==1} size='small'>Edit</Button>
              <Button disabled={selectedEntriesCount ===0} size='small'>
                Delete
              </Button>
            </Table.HeaderCell>
          </Table.Row>
        </Table.Footer>             
    )
}

export default CustomTableFooter