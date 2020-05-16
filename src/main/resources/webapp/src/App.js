import React, {Component} from 'react';
import {BrowserRouter as Router, Link, Switch, Route} from 'react-router-dom';

import Homepage from './js/pages/Homepage.js';
import Login from './js/pages/Login.js'



class App extends Component{
  render(){
    return (
        <Router>
          <Route exact path="/" ><Homepage/></Route>
          <Route exact path="/login" component={Login}/>
        </Router>
      );
  }
}

export default App;
