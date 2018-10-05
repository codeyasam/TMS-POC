import React from 'react';
import ReactDOM from 'react-dom';
import 'semantic-ui-css/semantic.min.css';
import sampleData from './intialState'
import storeFactory from './store'
import { Provider } from 'react-redux'
import AppRouter from './routes'
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
    <AppRouter />
  </Provider>,
  document.getElementById('root'));
