import React from 'react'
import CustomTable from '../../container/generic/CustomTable'
import CustomTableFooter from '../../container/modules/CustomTableFooter'
import CustomRow from '../../container/modules/CustomRow'
import Pagination from '../../container/modules/Pagination'
import SelectedEntries from '../../container/modules/SelectedEntries'
import lifecycle from 'react-pure-lifecycle'

const methods = {
    componentDidMount(props) {
        props.onFetchModules()
    }
}

const Modules = ({ moduleList, onFetchModules }) => {
    
    const mapModuleListToRowData = rowsData => {
        if (!rowsData) return
        return rowsData.map(entry => {
            let rowData = {}
            rowData.id = entry.id
            rowData.name = entry.name
            rowData.applicationId = entry.application.id
            return rowData
        })
    }    
    
    const tableHeaders = ["ID", "Module Name", "Application ID"]
    const tableColumns = ["id", "name", "applicationId"]
    const tableRowsData = mapModuleListToRowData(moduleList)

    const tableFooter = () => {
        return (
            <CustomTableFooter addButtonText='Add Module' />
        )
    }    
    
    const tableRows = () => {
        return tableRowsData.map((row, i) => {
            return(
                <CustomRow row={row}
                    columns={tableColumns}
                    key={i}
                    currentPage={1} />
            )
        })
    }    
    
    return (
        <div>
            <CustomTable tableHeaders={tableHeaders}
                tableColumns={tableColumns}
                CustomTableFooter={tableFooter}
                CustomRows={tableRows} />
            <Pagination />
            <SelectedEntries />
        </div>
    )
}

export default lifecycle(methods)(Modules)