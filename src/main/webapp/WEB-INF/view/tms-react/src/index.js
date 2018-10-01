import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import App from './App';
import 'semantic-ui-css/semantic.min.css';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom'
import sampleData from './intialState'
import storeFactory from './store'
import { Provider } from 'react-redux'
import LoginForm from './components/container/LoginForm'
import { unregister } from './registerServiceWorker';
unregister();

const initialState = (localStorage["redux-store"]) ? 
      JSON.parse(localStorage["redux-store"]) :
      sampleData;

const saveState = () => {
    console.log("save state log.")
    localStorage["redux-store"] = JSON.stringify(store.getState())
}
const store = storeFactory(initialState)
store.subscribe(saveState)

window.React = React
window.store = store

ReactDOM.render(
  <Provider store={store}>
      <Router>
        <Switch>
          <Route exact={true} path="/login" component={ LoginForm } />
        </Switch>
      </Router>
  </Provider>,
  document.getElementById('root'));
