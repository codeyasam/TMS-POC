import React from 'react'
import PropTypes from 'prop-types'
import { Divider, Button } from 'semantic-ui-react'

const DeleteApplicationForm = ({ selectedEntries, onDeleteApplication=f=>f }) => {
    
    const onDeleteButtonClick = () => {
        const entriesToDelete = selectedEntries
    }
    
    return (
        <form className="ui form">
            <div className="fields">
                Are you sure you want to delete the selected entries? 
            </div>
            <Divider />
            <div className="ui buttons">
              <button className="ui button">Cancel</button>
              <div className="or"></div>
              <button className="ui positive button" onClick={onDeleteButtonClick}>OK</button>
            </div>                        
        </form>
    )
    
}

DeleteApplicationForm.PropTypes = {
    selectedEntries: PropTypes.object,
    onDeleteApplication: PropTypes.func
}

export default DeleteApplicationForm