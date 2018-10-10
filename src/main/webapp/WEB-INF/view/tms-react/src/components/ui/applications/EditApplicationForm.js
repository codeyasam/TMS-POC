import React from 'react'
import { Divider, Button, Dimmer, Loader } from 'semantic-ui-react'

const EditApplicationForm = ({ onEditApplication, selectedApplication, isEditingApplication }) => {
    
    let _applicationName
    
    const submit = (e) => {
        e.preventDefault()
        let application = selectedApplication
        application.name = _applicationName.value
        onEditApplication(application)
    }
    
    return (
        <div>
            <Dimmer active={isEditingApplication}>
              <Loader> Updating Application... </Loader>
            </Dimmer>              
            <form className="ui form" onSubmit={submit}>
                <div className="fields">
                    <div className="sixteen wide field">
                        <input type="text" defaultValue={selectedApplication.name}
                            ref={ input => _applicationName = input } />
                    </div>
                </div>
                <Divider />
                <Button className="teal" position="ui right floated">Save</Button>
            </form>
        </div>
    )
}

export default EditApplicationForm