import React from 'react'
import { Header, Icon, Container, Divider } from 'semantic-ui-react'

export const LoginForm = () => {
  return (
    <Container text>
    <div class="ui equal width center aligned padded grid">
      <div class="column">
      <Header as='h2'>
        <Icon name='settings' />
        <Header.Content>
          Test Case Management
          <Header.Subheader>Login to manage your test cases</Header.Subheader>
        </Header.Content>
      </Header>
      <Divider />
        <form action="" method="get" class="ui large form">
          <div class="ui stacked secondary  segment">
            <div class="field">
              <div class="ui left icon input">
                <i class="user icon"></i>
                <input type="text" name="email" placeholder="E-mail address" />
              </div>
            </div>
            <div class="field">
              <div class="ui left icon input">
                <i class="lock icon"></i>
                <input type="password" name="password" placeholder="Password" />
              </div>
            </div>
            <div class="ui fluid large teal submit button">Login</div>
          </div>

          <div class="ui error message"></div>

        </form>
      </div>
    </div>    
    </Container>
  )
}
