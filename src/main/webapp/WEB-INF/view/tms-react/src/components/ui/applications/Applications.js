import React from 'react'
import lifecycle from 'react-pure-lifecycle'
import CustomTable from '../../container/generic/CustomTable'
import CustomTableFooter from '../generic/CustomTableFooter'
import { Modal } from 'semantic-ui-react'
import AddApplicationForm from '../../container/applications/AddApplicationForm'

const methods = {
    componentDidMount(props) {
        console.log("on component did mount applications")
        props.onFetchApplications()
    }
}

const Applications = ({ applications, isAddApplicationFormVisible, onCloseAddApplicationForm, onAddApplicationButtonClick }) => {
    
    const tableHeaders = ["ID", "Application Name"]
    const tableColumns = ["id", "name"]
    const tableRows = [
        {"id": 1, "name": "Application1"}, 
        {"id": 2, "name": "Application2"}, 
        {"id": 3, "name": "Application3"}
    ]
    
    const selectedEntriesCount = 1
    
    const tableFooter = () => {
        return (
            <CustomTableFooter addButtonText='Add Application'
                onAddButtonClick={onAddApplicationButtonClick}
                selectedEntriesCount={selectedEntriesCount} />
        )
    }
    
    return (
        <div>
            <CustomTable tableHeaders={tableHeaders}
                tableColumns={tableColumns}
                tableRows={tableRows}
                CustomTableFooter={tableFooter}
            />
        
            <Modal open={isAddApplicationFormVisible} 
              size='tiny'
              onClose={onCloseAddApplicationForm}
              closeIcon>
              <Modal.Header>Add New Application</Modal.Header>
              <Modal.Content>
                <AddApplicationForm />
              </Modal.Content>
            </Modal>        
        </div>
    )
}

export default lifecycle(methods)(Applications)