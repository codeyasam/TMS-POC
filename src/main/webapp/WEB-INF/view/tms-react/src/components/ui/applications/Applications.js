import React from 'react'
import lifecycle from 'react-pure-lifecycle'
import CustomTable from '../../container/generic/CustomTable'
import CustomTableFooter from '../../container/applications/CustomTableFooter'
import CustomRow from '../../container/applications/CustomRow'
import AddApplicationForm from '../../container/applications/AddApplicationForm'
import AddModalForm from '../../container/applications/AddModalForm'
import EditApplicationForm from '../../container/applications/EditApplicationForm'
import EditModalForm from '../../container/applications/EditModalForm'
import PromptModal from '../generic/PromptModal'

const methods = {
    componentDidMount(props) {
        console.log("on component did mount applications")
        props.onFetchApplications()
    }
}

const Applications = ({ applicationList, onPromptModalClose=f=>f, isSuccessfullyAdded }) => {
    
    const tableHeaders = ["ID", "Application Name"]
    const tableColumns = ["id", "name"]
    const tableRowsData = applicationList
    /*  
    Test Data
    const tableRowsData = [
        {"id": 1, "name": "Application1"}, 
        {"id": 2, "name": "Application2"}, 
        {"id": 3, "name": "Application3"}
    ] */
    
    const tableFooter = () => {
        return (
            <CustomTableFooter addButtonText='Add Application' />
        )
    }
    
    const tableRows = () => {
        return tableRowsData.map((row, i) => {
            return(
                <CustomRow row={row}
                    columns={tableColumns}
                    key={i}  />
            )
        })
    }
    
    return (
        <div>
            <CustomTable tableHeaders={tableHeaders}
                tableColumns={tableColumns}
                CustomTableFooter={tableFooter}
                CustomRows={tableRows}
            />
            
            <AddModalForm formHeader="Add New Application" 
                FormUI={AddApplicationForm} />
                
            <EditModalForm formHeader="Edit Selected Application"
                FormUI={EditApplicationForm} />
        
            <PromptModal isVisible={isSuccessfullyAdded}
                header="Application"
                message="Successfully added application."
                onHandleClose={onPromptModalClose} />        
        </div>
    )
}

export default lifecycle(methods)(Applications)