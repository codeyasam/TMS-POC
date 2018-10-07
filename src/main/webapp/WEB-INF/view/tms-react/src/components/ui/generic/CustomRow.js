import React from 'react'
import { Table, Checkbox } from 'semantic-ui-react'

const CustomRow = ({ row, columns, key }) => {
    return (
        <Table.Row key={key}>
            <Table.Cell collapsing key={key}>
                <Checkbox slider />
            </Table.Cell>
            { columns.map((column, i) => {
                return (
                    <Table.Cell key={i}>{row[column]}</Table.Cell>
                )
            }) }
        </Table.Row>
    )
}

export default CustomRow