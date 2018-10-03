import React, { Component } from 'react'
import fetch from 'isomorphic-fetch'
import { Button, Card, Dimmer, Loader } from 'semantic-ui-react'
import ControlledModal from './ControlledModal'
import { Link } from 'react-router-dom'

class Uploads extends Component {
    
    state = { 
        isUploading: false,
        isSuccessfullyUploaded: false,
        modalHeader: "",
        modalMessage: "",
        importModuleVisibility: true
    }
    
    onChangeUpload = (e) => {
      console.log("on change")
      let modalHeader = e.target.getAttribute('headercontent')
      let modalMessage = e.target.getAttribute('message')
      this.setState({ isUploading: true })
      let file = e.target.files[0]
      let formData = new FormData()
      formData.append("file", file)
      
      let uploadUrl = e.target.getAttribute('uploadurl')
      fetch(uploadUrl, {
        method: 'POST',
        body: formData
      }).then(response => {
          if (response.status === 200) {
              this.setState({ isSuccessfullyUploaded: true, modalHeader: modalHeader, modalMessage: modalMessage })
          }
          this.setState({ isUploading: false })
      })
    }
    
    onClickUpload = (e) => {
        console.log("on clicked upload")
        e.target.files = null
    }
    
    closeControlledModal = () => {
        this.setState({ isSuccessfullyUploaded: false })
    }
    
    render() {
        return (
            <div>
            { this.state.isUploading &&
                <Dimmer active>
                  <Loader> Uploading... </Loader>
                </Dimmer>
            }
            { this.state.isSuccessfullyUploaded &&
                <ControlledModal 
                    header={this.state.modalHeader}
                    message={this.state.modalMessage}
                    onHandleClose={this.closeControlledModal}/>
            }  
            <Card.Group>
                <Card>
                  <Card.Content>
                    <Card.Header>Applications</Card.Header>
                    <Card.Meta>Upload csv file</Card.Meta>
                    <Card.Description>
                      A template is availble. <strong>Please use it as a reference.</strong>
                    </Card.Description>
                  </Card.Content>
                  <Card.Content extra>
                      <input type="file" id="applicationImport" 
                        style={{ display: 'none' }}
                        uploadurl="/applications/import"
                        headercontent="Application List Upload"
                        message="Successfully uploaded list of applications."
                        onClick={this.onClickUpload} 
                        onChange={this.onChangeUpload}/>
                      <label htmlFor="applicationImport" className="ui teal button">
                        Upload
                      </label>

                      <a href="/applications/template/download">
                        <Button basic color='blue'>
                          Download Template
                        </Button>
                      </a>
                  </Card.Content>
                </Card>          

                <Card>
                  <Card.Content>
                    <Card.Header>Modules</Card.Header>
                    <Card.Meta>Upload csv file</Card.Meta>
                    <Card.Description>
                      A template is availble. <strong>Please use it as a reference.</strong>
                    </Card.Description>
                  </Card.Content>
                  <Card.Content extra>
                    <Link to="uploads/importModule">
                    <Button className="ui teal button">
                      Upload
                    </Button>
                                    </Link>
                    <a href="/modules/template/download">
                      <Button basic color='blue'>
                        Download Template
                      </Button>
                    </a>
                  </Card.Content>
                </Card>

                <Card>
                  <Card.Content>
                    <Card.Header>Machines</Card.Header>
                    <Card.Meta>Upload csv file</Card.Meta>
                    <Card.Description>
                      A template is availble. <strong>Please use it as a reference.</strong>
                    </Card.Description>
                  </Card.Content>
                  <Card.Content extra>
                    <input type="file" id="machinesImport" 
                        style={{ display: 'none' }} 
                        uploadurl="/machines/import"
                        headercontent="Machine List Upload"
                        message="Successfully uploaded list of machines."
                        onClick={this.onClickUpload} 
                        onChange={this.onChangeUpload}/>
                    <label htmlFor="machinesImport" className="ui teal button">
                      Upload
                    </label>
                    <a href="/machines/template/download">
                      <Button basic color='blue'>
                        Download Template
                      </Button>
                    </a>
                  </Card.Content>
                </Card>

                <Card>
                  <Card.Content>
                    <Card.Header>Test cases</Card.Header>
                    <Card.Meta>Upload csv file</Card.Meta>
                    <Card.Description>
                      A template is availble. <strong>Please use it as a reference.</strong>
                    </Card.Description>
                  </Card.Content>
                  <Card.Content extra>
                    <Button className="ui teal button">
                      Upload
                    </Button>
                    <a href="/testcases/template/download">
                      <Button basic color='blue'>
                        Download Template
                      </Button>
                    </a>
                  </Card.Content>
                </Card>        
            </Card.Group>
            </div>
        )        
    }
}

export default Uploads