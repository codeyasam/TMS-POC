import React from 'react'
import PropTypes from 'prop-types'
import { Divider, Button } from 'semantic-ui-react'

const AddApplicationForm = ({ onAddApplication=f=>f }) => {
    
    let _applicationName
    
    const submit = (e) => {
        e.preventDefault()
        let applicationName = _applicationName.value
        let application = { name: applicationName } 
        onAddApplication(application)
    }
    
    return (
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
    )
}

AddApplicationForm.PropTypes = {
    onAddApplication: PropTypes.func
}

export default AddApplicationForm