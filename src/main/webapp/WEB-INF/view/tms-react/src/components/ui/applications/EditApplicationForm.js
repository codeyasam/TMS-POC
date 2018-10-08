import React from 'react'
import { Divider, Button } from 'semantic-ui-react'

const EditApplicationForm = ({ onEditApplication, selectedApplication }) => {
    
    let _applicationName
    
    const submit = (e) => {
        e.preventDefault()
        let application = selectedApplication
        application.name = _applicationName.value
        onEditApplication(application)
    }
    
    return (
        <form className="ui form" onSubmit={submit}>
            <div className="fields">
                <div className="sixteen wide field">
                    <input type="text" value={selectedApplication.name}
                        ref={ input => _applicationName = input } />
                </div>
            </div>
            <Divider />
            <Button className="teal" position="ui right floated">Save</Button>
        </form>
    )
}

export default EditApplicationForm