import React from 'react';
import { connect } from 'react-redux';

import Navbar from '../components/Navbar.js';




class Login extends React.Component {
    loginRequest = (e) => {
        e.preventDefault();
        let data = {
            username: document.querySelector('input#username-input').value,
            password: document.querySelector('input#password-input').value
        }

        let response1;
        fetch('/api/users/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        }).then((data) => {
            if (data.status === 202 && data.ok) {
                
                data.json().then(data => this.props.userLoggedIn(data));
            }
        }).catch(error => { console.log(error) });
    }

    login = (data) => {
        fetch('/api/users/current', {
        }).then((resp) => {
            resp.json().then((data) =>
                this.props.userLoggedIn(data))
        });
    }

    render() {
        return (
            <div className="login-wrapper">
                <Navbar></Navbar>
                <form onSubmit={this.loginRequest} id="login-form">
                    <label htmlFor="username-input">username:</label>
                    <input id="username-input" placeholder="Username"></input>
                    <label htmlFor="password-input">password:</label>
                    <input id="password-input" type="password" placeholder="password"></input>
                    <button type="submit">submit</button>
                </form>
            </div>
        );
    }
}


const mapDispatchToProps = (dispatch) => {
    return {
        userLoggedIn: (user) => {
            dispatch({ type: 'LOGGING_IN', user: user });
        }
    }
}

const mapStateToProps = (state) => {
    return {
        user: state.user
    }
}

export default connect(mapStateToProps, mapDispatchToProps)(Login);