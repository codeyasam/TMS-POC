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
                <div className='ui two buttons'>
                  <Button basic color='red'>
                    Upload
                  </Button>
                  <a href="/applications/template/download">
                    <Button basic color='blue'>
                      Download Template
                    </Button>
                  </a>
                </div>
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
                <div className='ui two buttons'>
                  <Button basic color='red'>
                    Upload
                  </Button>
                  <a href="/modules/template/download">
                    <Button basic color='blue'>
                      Download Template
                    </Button>
                  </a>
                </div>
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
                <div className='ui two buttons'>
                  <Button basic color='red'>
                    Upload
                  </Button>
                  <a href="/machines/template/download">
                    <Button basic color='blue'>
                      Download Template
                    </Button>
                  </a>
                </div>
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
                <div className='ui two buttons'>
                  <Button basic color='red'>
                    Upload
                  </Button>
                  <a href="/testcases/template/download">
                    <Button basic color='blue'>
                      Download Template
                    </Button>
                  </a>
                </div>
              </Card.Content>
            </Card>        
        </Card.Group>
    )
    
}

export default Uploads