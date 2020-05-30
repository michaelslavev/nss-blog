import React from 'react';
import { connect } from 'react-redux';
import Container from 'react-bootstrap/Container';
import Form from 'react-bootstrap/Form';
import Button from 'react-bootstrap/Button';
import Navigation from '../components/Navigation.js';
import withRouter from "react-router-dom/withRouter";




class Login extends React.Component {
    loginRequest = (e) => {
        e.preventDefault();
        let data = {
            username: document.querySelector('input#username-input').value,
            password: document.querySelector('input#password-input').value
        }

        fetch('/api/users/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        }).then((data) => {
            if (data.status === 202 && data.ok) {
                
                data.json().then(data => this.props.userLoggedIn(data));
                this.props.history.push("/");
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
                <Navigation/>
                <Container>
                <Form onSubmit={this.loginRequest}>
                    <Form.Group controlId="formUsername">
                        <Form.Label>Username</Form.Label>
                        <Form.Control type="text" placeholder="Enter your username" id="username-input"/>
                    </Form.Group>
                    <Form.Group controlId="formPassword">
                        <Form.Label>Password</Form.Label>
                        <Form.Control type="text" placeholder="Enter your password" id="password-input"/>
                    </Form.Group>

                    <Button variant="primary" type="submit">
                        Submit
                    </Button>
                </Form>
                </Container>
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

export default connect(mapStateToProps, mapDispatchToProps)(withRouter(Login));