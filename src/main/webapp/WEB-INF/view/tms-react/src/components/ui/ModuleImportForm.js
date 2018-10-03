import React, { Component } from 'react'
import { Select, Button } from 'semantic-ui-react'

class ModuleImportForm extends Component {
    
    fetchApplications = () => {
        
    }
    
    componentDidMount() {
        
    }
    
    render() {
        let applicationOptions = [{valule: 3, text: "App3"}, {value: 4, text: 'App4'}]    
        return (
            <form>
                <Select placeholder="Select Application" options={applicationOptions} className="teal"/>                      
                <input type="file" id="applicationImport" 
                        style={{ display: 'none' }}/>
                <label htmlFor="applicationImport" className="ui teal button">
                    Choose File
                </label>
                <Button>Upload</Button>
            </form>
        )
    }
}

export default ModuleImportForm