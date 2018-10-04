import React, { Component } from 'react'
import fetch from 'isomorphic-fetch'
import { Button, Divider } from 'semantic-ui-react'
import ApplicationOptions from './ApplicationOptions'

class ModuleImportForm extends Component {
    
    constructor(props) {
        super(props)
        this.submit = this.submit.bind(this)
    }
    
    state = {
        applicationOptions: []
    }
    
    fetchApplications = () => {
        fetch('/applications/', {
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
        // this.fetchApplications()
    }
    
    submit(e) {
        e.preventDefault()
        let uploadedFile = this.refs.uploadedfile.files[0] 
        let selectedApplication = this.refs.selectedApplication.value
        console.log(selectedApplication)
        console.log(uploadedFile)
    }
    
    render() {
        //const { applicationOptions } = this.state
        let applicationOptions = [{key: 1, value: 1, text: "App1"}, {key: 2, value: 2, text: "App2"}]
        console.log(applicationOptions)
        return (
            <form className="ui form" onSubmit={this.submit}>
                <div className="fields">
                    <div class="ten wide field">
                        <select placeholder="Select Application" 
                            className="ui fluid dropdown" 
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
                    <div class="four wide field">    
                        <label htmlFor="moduleImport" 
                            className="ui button floated right">
                            Choose File
                        </label>
                    </div>
                </div>
                <Divider />
                <Button className="teal" position="ui right floated">Upload</Button>
            </form>
        )
    }
}

export default ModuleImportForm