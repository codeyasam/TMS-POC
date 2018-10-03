import React from 'react'
import fetch from 'isomorphic-fetch'
import { Button, Card } from 'semantic-ui-react'

const Uploads = () => {
    
    const onChangeUpload = (e) => {
      console.log(e.target.files[0])
      let file = e.target.files[0]
      let formData = new FormData()
      formData.append("file", file)
      fetch('/applications/import', {
        method: 'POST',
        body: formData
      })
    }
    
    return (
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
                  <input type="file" id="applicationImport" style={{ display: 'none' }} onChange={onChangeUpload}/>
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
                <Button className="ui teal button">
                  Upload
                </Button>
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
                <input type="file" id="machinesImport" style={{ display: 'none' }} />
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
    )
    
}

export default Uploads