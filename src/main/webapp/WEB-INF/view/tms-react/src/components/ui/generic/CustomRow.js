import React from 'react'
import { Table, Checkbox } from 'semantic-ui-react'

const CustomRow = ({ row, columns, key, onSelectEntry }) => {
    return (
        <Table.Row key={key}>
            <Table.Cell collapsing key={key}>
                <Checkbox slider 
                    id={row['id']} 
                    onClick={onSelectEntry}/>
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