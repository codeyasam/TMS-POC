import React from 'react'
import App from './components/container/App'
import LoginForm from './components/container/LoginForm'
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom'

const AppRouter = () => {
    return (
        <Router>
            <Switch>
              <Route exact={true} path="/" component={App} /> 
              <Route exact={true} path="/uploads" component={App} />
              <Route exact={true} path="/applications" component={App} />
              <Route exact={true} path="/modules" component={App} />
              <Route exact={true} path="/uploads/importModule" component={App} />

              <Route exact={true} path="/login" component={ LoginForm } />
            </Switch>     
        </Router>
    )
}

export default AppRouter
