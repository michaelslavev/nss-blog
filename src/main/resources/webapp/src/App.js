import React, {Component} from 'react';
import {BrowserRouter as Router, Link, Switch, Route} from 'react-router-dom';

import Homepage from './js/pages/Homepage.js';
import Login from './js/pages/Login.js'
import Logout from './js/components/Logout.js';



class App extends Component{
  render(){
    return (
        <Router>
          <Route exact path="/" component={Homepage}></Route>
          <Route exact path="/login" component={Login}></Route>
          <Route exact path="/j_spring_security_logout" component={Logout}></Route>
        </Router>
      );
  }
}

export default App;
