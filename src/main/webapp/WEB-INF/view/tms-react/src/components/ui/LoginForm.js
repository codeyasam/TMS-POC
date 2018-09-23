import React from 'react'
import PropTypes from 'prop-types'
import { Header, Icon, Container, Divider } from 'semantic-ui-react'

const LoginForm = ({ onLogin=f=>f }) => {
  return (
    <Container text>
    <div className="ui equal width center aligned padded grid">
      <div className="column">
      <Header as='h2'>
        <Icon name='settings' />
        <Header.Content>
          Test Case Management
          <Header.Subheader>Login to manage your test cases</Header.Subheader>
        </Header.Content>
      </Header>
      <Divider />
        <form action="" method="get" className="ui large form">
          <div className="ui stacked secondary  segment">
            <div className="field">
              <div className="ui left icon input">
                <i className="user icon"></i>
                <input type="text" name="email" placeholder="E-mail address" />
              </div>
            </div>
            <div className="field">
              <div className="ui left icon input">
                <i className="lock icon"></i>
                <input type="password" name="password" placeholder="Password" />
              </div>
            </div>
            <div className="ui fluid large teal submit button">Login</div>
          </div>

          <div className="ui error message"></div>

        </form>
      </div>
    </div>    
    </Container>
  )
}

LoginForm.propTypes = {
    onLogin: PropTypes.func
}

export default LoginForm