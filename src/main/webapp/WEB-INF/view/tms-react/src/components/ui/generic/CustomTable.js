import React from 'react'
import { Table } from 'semantic-ui-react'

const CustomTable = ({tableHeaders, CustomRows, CustomTableFooter}) => {
    
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
              <CustomRows />
            </Table.Body>
            <CustomTableFooter />
          </Table>        
        </div>
    )
}

export default CustomTable

