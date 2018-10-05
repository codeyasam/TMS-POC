import React, { Component } from 'react'
import fetch from 'isomorphic-fetch'
import { Button, Divider, Dimmer, Loader, Message } from 'semantic-ui-react'
import TestCaseOptions from '../generic/SelectOptions'
import ControlledModal from '../generic/ControlledModal'

class TestCaseImportForm extends Component {
    
    constructor(props) {
        super(props)
        this.state = {
            testCaseOptions: [],
            isUploading: false,
            isSuccessfullyUploaded: false,
            hasErrorOnSubmit: false
        }
        this.submit = this.submit.bind(this)
    }
    
    fetchModules = () => {
        fetch('/modules/', {
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
                    this.setState({ testCaseOptions: dataOptions })
                }        
        })
    }
    
    componentDidMount() {
        this.fetchModules()
    }
    
    submit(e) {
        e.preventDefault()
        this.setState({ isUploading: true })
        let uploadedFile = this.refs.uploadedfile.files[0]
        let moduleId = this.refs.selectedModule.value
        let formData = new FormData()
        formData.append("moduleId", moduleId)
        formData.append("file", uploadedFile)
        
        if (!uploadedFile) {
            this.setState({ hasErrorOnSubmit: true })
        }
        
        this.importTestCases(formData)
    }
    
    importTestCases(formData) {
        fetch('/testcases/importTestCases', {
            method: 'POST',
            body: formData
        }).then(response => {
            if (response.status === 200) {
                this.setState({ isSuccessfullyUploaded: true })
            }
            this.setState({ isUploading: false })
        })
    }
    
    render() {
        console.log("successfully uploaded testcase: ", this.state.isSuccessfullyUploaded)
        const { testCaseOptions } = this.state
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
                    header="Test Case List Upload"
                    message="Sucessfully uploaded list of testcase to a module." />            
            
                <form className="ui form" onSubmit={this.submit}>
                    <div className="fields">
                        <div className="ten wide field">
                            <select className="ui fluid dropdown"
                                ref="selectedModule">
                                { testCaseOptions.map((options, i) => {
                                    return (<TestCaseOptions key={i}
                                        value={options.value}
                                        text={options.text} />)
                                }) }
                            </select>
                        </div>
                        <div className="six wide field">
                            <input type="file"
                                id="testCaseImport"
                                style={{ display: 'none' }}
                                ref="uploadedfile" />                            
                            <label htmlFor="testCaseImport"
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

export default TestCaseImportForm