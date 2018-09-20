import React, { Component } from 'react';
import './App.css';
import { LoginForm } from './components/ui/LoginForm.js'

class App extends Component {
  render() {
    return (
      <div className="App">
        {(this.props.location.pathname === "/login") ?
        <LoginForm /> : null
        }
      </div>
    );
  }
}

export default App;
