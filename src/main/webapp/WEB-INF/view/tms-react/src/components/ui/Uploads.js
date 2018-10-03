import React from 'react'
import { Button, Card } from 'semantic-ui-react'

const Uploads = () => {
    
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
                  <input type="file" id="applicationImport" style={{ visibility: 'hidden' }} />
                  <label for="applicationImport">
                      <div className="ui teal button">
                        Upload
                      </div>
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
                <input type="file" id="machinesImport" style={{ visibility: 'hidden' }} />
                <label for="machinesImport">
                    <div class="ui teal button">
                      Upload
                    </div>
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