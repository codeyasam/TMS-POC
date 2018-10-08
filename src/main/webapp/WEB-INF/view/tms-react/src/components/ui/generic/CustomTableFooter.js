import React from 'react'
import PropTypes from 'prop-types'
import { Button, Table } from 'semantic-ui-react'

const CustomTableFooter =({ addButtonText, onAddButtonClick=f=>f, onEditButtonClick=f=>f, selectedEntriesCount }) => {
    return (
        <Table.Footer fullWidth>
          <Table.Row>
            <Table.HeaderCell />
            <Table.HeaderCell colSpan='4'>
              <Button floated='right' primary onClick={onAddButtonClick}>
                { addButtonText }
              </Button>
              <Button disabled={selectedEntriesCount !==1} size='small' onClick={onEditButtonClick}>Edit</Button>
              <Button disabled={selectedEntriesCount ===0} size='small'>
                Delete
              </Button>
            </Table.HeaderCell>
          </Table.Row>
        </Table.Footer>             
    )
}

CustomTableFooter.propTypes = {
  addButtonText: PropTypes.string,
  onAddButtonClick: PropTypes.func,
  onEditButtonClick: PropTypes.func,
  selectedEntriesCount: PropTypes.number
}

export default CustomTableFooter