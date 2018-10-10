import React from 'react'
import PropTypes from 'prop-types'
import { Divider, Button, Message, Dimmer, Loader } from 'semantic-ui-react'

const AddApplicationForm = ({ onAddApplication=f=>f, hasError, isAddingApplication, currentPage, pageSize }) => {
    
    let _applicationName
    
    const submit = (e) => {
        e.preventDefault()
        let applicationName = _applicationName.value
        let application = { name: applicationName } 
        onAddApplication(application, currentPage, pageSize)
    }
    
    return (
        <div>
            <Dimmer active={isAddingApplication}>
              <Loader> Adding Application... </Loader>
            </Dimmer>         
        
            <Message error hidden={!hasError}>Application name can't be empty.</Message>
            <form className="ui form" onSubmit={submit}>
                <div className="fields">
                    <div className="sixteen wide field">
                        <input type="text"  
                            ref={input => _applicationName = input}/>
                    </div>
                </div>
                <Divider />
                <Button className="teal" position="ui right floated">Add</Button>
            </form>   
        </div>
    )
}

AddApplicationForm.propTypes = {
    onAddApplication: PropTypes.func,
    hasError: PropTypes.bool,
    isAddingApplication: PropTypes.bool
}

export default AddApplicationForm