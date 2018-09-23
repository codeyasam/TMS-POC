import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import App from './App';
import registerServiceWorker from './registerServiceWorker';
import 'semantic-ui-css/semantic.min.css';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom'
import sampleData from './intialState'
import storeFactory from './store'
import { login } from './actions'
import { Provider } from 'react-redux'

const initialState = (localStorage["redux-store"]) ? 
      JSON.parse(localStorage["redux-store"]) :
      sampleData;

const saveState = () => localStorage["redux-store"] = JSON.stringify(store.getState())

const store = storeFactory()
store.subscribe(saveState)

store.dispatch(login({"username": "codeyasam", "password": "secret"}))

ReactDOM.render(
  <Provider store={store}>
      <Router>
        <Switch>
          <Route exact={true} path="/login" component={App} />
        </Switch>
      </Router>
  </Provider>,
  document.getElementById('root'));
registerServiceWorker();
