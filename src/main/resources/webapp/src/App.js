import React, {Component} from 'react';
import {BrowserRouter as Router, Link, Switch, Route} from 'react-router-dom';

import Homepage from './js/pages/Homepage.js';
import Login from './js/pages/Login.js'
import Logout from './js/components/Logout.js';
import AddArticle from './js/pages/AddArticle.js';
import Register from './js/pages/Register.js';
import Article from './js/pages/Article.js';


class App extends Component{
  render(){
    return (
        <Router>
          <Route exact path="/" component={Homepage}></Route>
          <Route exact path="/login" component={Login}></Route>
          <Route exact path="/j_spring_security_logout" component={Logout}></Route>
          <Route exact path="/add" component={AddArticle}></Route>
          <Route exact path="/register" component={Register}></Route>
          <Route path="/article/:article_id" component={Article}></Route>
        </Router>
      );
  }
}

export default App;
