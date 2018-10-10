import React from 'react'
import PropTypes from 'prop-types'
import { Divider, Message, Dimmer, Loader } from 'semantic-ui-react'

const DeleteApplicationForm = ({ selectedEntries, onDeleteApplication=f=>f, isDeletingApplication, hasError, onCancel=f=>f }) => {
    
    const onDeleteButtonClick = (e) => {
        e.preventDefault()
        const entriesToDelete = selectedEntries
        onDeleteApplication(entriesToDelete)
    }
    
    return (
        <div>
            <Dimmer active={isDeletingApplication}>
              <Loader> Deleting Application/s... </Loader>
            </Dimmer>     
            
            <Message error hidden={!hasError}>Application/s can't be deleted.</Message>            
            <form className="ui form">
                <div className="fields">
                    Are you sure you want to delete the selected entries? 
                </div>
                <Divider />
                <div className="ui buttons">
                  <button className="ui button" onClick={onCancel}>Cancel</button>
                  <div className="or"></div>
                  <button className="ui positive button" onClick={onDeleteButtonClick}>OK</button>
                </div>                        
            </form>
        </div>
    )
    
}

DeleteApplicationForm.propTypes = {
    selectedEntries: PropTypes.array,
    onDeleteApplication: PropTypes.func
}

export default DeleteApplicationForm