import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import App from './App';
import registerServiceWorker from './registerServiceWorker';
import 'semantic-ui-css/semantic.min.css';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom'

ReactDOM.render(
  <Router>
    <Switch>
      <Route exact={true} path="/login" component={App} />
    </Switch>
  </Router>,
  document.getElementById('root'));
registerServiceWorker();
