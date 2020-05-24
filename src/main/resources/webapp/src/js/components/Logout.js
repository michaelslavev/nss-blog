import React, {Component} from 'react';
import {Redirect} from 'react-router-dom';

import Navigation from './Navigation.js';

class Logout extends React.Component{


    render(){
        return(
            <div className="logout-wrapper">
                <Redirect to="/" ></Redirect>>
            </div>
        );
    }
}

export default Logout