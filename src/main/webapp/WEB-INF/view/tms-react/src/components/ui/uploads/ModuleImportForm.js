import React, { Component } from 'react'
import fetch from 'isomorphic-fetch'
import { Button, Divider, Dimmer, Loader, Message } from 'semantic-ui-react'
import ApplicationOptions from '../generic/SelectOptions'
import ControlledModal from '../generic/ControlledModal'

class ModuleImportForm extends Component {
    
    constructor(props) {
        super(props)
        this.state = { 
            applicationOptions: [],
            isUploading: false,
            isSuccessfullyUploaded: false,
            hasErrorOnSubmit: false
        }
        this.submit = this.submit.bind(this)
    }
    
    fetchApplications = () => {
        fetch('/api/applications/', {
            method: 'GET',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(response => response.json())
            .then(jsonResponse => {
                if (jsonResponse.status === 200) {
                    let dataOptions = []
                    jsonResponse.data.forEach((data) => {
                        let eachData = {}
                        eachData.key = data.id
                        eachData.value = data.id
                        eachData.text = data.name
                        dataOptions = [...dataOptions, eachData]
                    })
                    console.log(dataOptions)
                    this.setState({ applicationOptions: dataOptions })
                }        
        })
    }
    
    componentDidMount() {
        console.log("module import form mounted")
        this.fetchApplications()
    }
    
    submit(e) {
        e.preventDefault()
        this.setState({ isUploading: true })
        let uploadedFile = this.refs.uploadedfile.files[0] 
        let applicationId = this.refs.selectedApplication.value
        let formData = new FormData()
        formData.append("applicationId", applicationId)
        formData.append("file", uploadedFile)
        
        if (!uploadedFile) {
            console.log("no uploaded file.")
            this.setState({ hasErrorOnSubmit: true })
        }
        
        fetch('/modules/import', {
            method: 'POST',
            body: formData
        }).then(response => {
            if (response.status === 200) {
                this.setState({ isSuccessfullyUploaded: true })
                console.log("Successfully uploaded modules")
            }
            this.setState({ isUploading: false })
        })
    }
    
    render() {
        const { applicationOptions } = this.state
        console.log(this.state.applicationOptions)
        return (
            <div>
            { this.state.isUploading &&
                <Dimmer active>
                  <Loader> Uploading... </Loader>
                </Dimmer>
            }            
            
            { this.state.hasErrorOnSubmit &&
                <Message
                  error
                  header='Upload Error'
                  content='You have to choose a file.'
                />            
            }
            
            <ControlledModal isVisible={this.state.isSuccessfullyUploaded}
                header="Module List Upload"
                message="Sucessfully uploaded list of modules." />  
                
            Note: Modules belong to an Application.
            <form className="ui form" onSubmit={this.submit}>
                <div className="fields">
                    <div className="ten wide field">
                        <select className="ui fluid dropdown" 
                            ref="selectedApplication">
                            { applicationOptions.map((options, i) => {
                                return (<ApplicationOptions key={i}
                                    value={options.value}
                                    text={options.text} />)
                            }) }
                        </select>
                        <input type="file" 
                            id="moduleImport" 
                            style={{ display: 'none' }}
                            ref="uploadedfile"/>
                    </div>
                    <div className="six wide field">    
                        <label htmlFor="moduleImport" 
                            className="ui button floated right">
                            Choose File
                        </label>
                    </div>
                </div>
                <Divider />
                <Button className="teal" position="ui right floated">Upload</Button>
            </form>
            </div>
        )
    }
}

export default ModuleImportForm