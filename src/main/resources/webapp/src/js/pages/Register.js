import React from 'react';
import { connect } from 'react-redux';

import Navigation from '../components/Navigation.js';


import Form from 'react-bootstrap/Form';
import Button from 'react-bootstrap/Button';




class Register extends React.Component {
    submit = (e) => {
        e.preventDefault();
        let data = {
            username: document.querySelector('input#username-input').value,
            password: document.querySelector('input#password-input').value
        }

        fetch('/api/users/register', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        }).then((data) => {
            if (data.status === 201 && data.ok) {
                console.log("succesfully registered")
                //data.json().then(data => this.props.userLoggedIn(data));
            }
        }).catch(error => { console.log(error) });
    }

    render() {
        return (
            <div className="register-wrapper">
                <Navigation/>
                <Form onSubmit={this.submit}>
                    <Form.Group controlId="formTitle">
                        <Form.Label>Username</Form.Label>
                        <Form.Control type="text" placeholder="Enter username" id="username-input"/>
                    </Form.Group>

                    <Form.Group controlId="formContent">
                        <Form.Label>Password</Form.Label>
                        <Form.Control type="password" placeholder="Enter password" id="password-input"/>
                    </Form.Group>
                    <Button variant="primary" type="submit">
                        Submit
                    </Button>
                </Form>
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

export default connect(mapStateToProps, mapDispatchToProps)(Register);