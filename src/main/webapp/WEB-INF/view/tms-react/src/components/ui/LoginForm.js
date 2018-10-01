import React from 'react'
import PropTypes from 'prop-types'
import { Grid, Header, Icon } from 'semantic-ui-react'
import lifecycle from 'react-pure-lifecycle'

const methods = {
    componentDidMount(props) { 
        props.onAuthenticationValidation(props)
    }
}

const LoginForm = ({username, isLoginFormVisible, errorMessage, 
                    onLogin=f=>f, onAuthenticationValidation=f=>f, 
                    onClearError=f=>f, router }) => {
    
    let _username, _password    

    const submit = e => {
      e.preventDefault()
      onLogin({
          username: _username.value,
          password: _password.value
      })
    }    
    
    return (
      <div className='login-form'>
        {/*
          Heads up! The styles below are necessary for the correct render of this example.
          You can do same with CSS, the main idea is that all the elements up to the `Grid`
          below must have a height of 100%.
        */}
        <style>{`
          body > div,
          body > div > div,
          body > div > div > div.login-form {
            height: 100%;
          }
        `}</style>
        { isLoginFormVisible && 
        <Grid textAlign='center' style={{ height: '100%' }} verticalAlign='middle'>
          <Grid.Column style={{ maxWidth: 450 }}>
            <Header as='h2'>
                <Icon name='settings' />
                <Header.Content>
                  Test Case Management
                  <Header.Subheader>Login to manage your test cases</Header.Subheader>
                </Header.Content>
            </Header>
            <form onSubmit={submit} className="ui large form">
              <div className="ui stacked secondary  segment">
                <div className="field">
                  <div className="ui left icon input">
                    <i className="user icon"></i>
                    <input type="text" name="email" placeholder="E-mail address" ref={input => _username = input} />
                  </div>
                </div>
                <div className="field">
                  <div className="ui left icon input">
                    <i className="lock icon"></i>
                    <input type="password" name="password" placeholder="Password" ref={input => _password = input} />
                  </div>
                </div>
                <button className="ui fluid large teal submit button">Login</button>
              </div>
            </form>
            
            { errorMessage &&
                <div className="ui warning message">
                    <i className="close icon" onClick={onClearError}></i>
                    <div className="header">
                        {errorMessage}
                    </div>
                        try again.
                </div>
            }
          </Grid.Column>
        </Grid>        
        }
        { !isLoginFormVisible &&
            <div class="ui segment" style={{ height: '100%' }} verticalAlign='middle'>
                <div class="ui active dimmer">
                    <div class="ui text loader">Loading</div>
                    </div>
                <p></p>
            </div>
        }
      </div>
)}

LoginForm.propTypes = {
    login: PropTypes.string,
    onLogin: PropTypes.func,
    router: PropTypes.object
}

export default lifecycle(methods)(LoginForm)