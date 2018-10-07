import React from 'react'
import { Table } from 'semantic-ui-react'
import CustomRow from '../../container/generic/CustomRow'

const CustomTable = ({tableHeaders, tableRows, tableColumns, CustomTableFooter}) => {
    
    return (
        <div>
          <Table celled compact definition>
            <Table.Header fullWidth>
              <Table.Row>
                <Table.HeaderCell />
                { tableHeaders.map((header, i) => {
                    return (
                        <Table.HeaderCell key={i}>{header}</Table.HeaderCell>
                    )
                }) }
              </Table.Row>
            </Table.Header>

            <Table.Body>
              { tableRows.map((row, i) => {
                return(
                    <CustomRow row={row}
                        columns={tableColumns}
                        key={i} />
                )
              }) }
            </Table.Body>
            <CustomTableFooter />
          </Table>        
        </div>
    )
}

export default CustomTable

