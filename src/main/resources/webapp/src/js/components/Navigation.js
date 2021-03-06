import React from 'react';
import { connect } from 'react-redux';
import NavLink from 'react-router-dom/NavLink';
import Navbar from 'react-bootstrap/Navbar';
import Nav from 'react-bootstrap/Nav';
import Logo from './blogolo.png';


class Navigation extends React.Component {

    render() {
        return (
            <div className="navbar-wrapper">

                <Navbar bg="dark" variant="dark">
                    <Navbar.Brand href="/">
                        <img
                            alt=""
                            src={Logo}
                            width="120"
                            height="30"
                            className="d-inline-block align-top"
                        />{' '}
                    </Navbar.Brand>
                    <Nav className="mr-auto">
                        {this.props.loggedIn && this.props.user?.role === "ADMIN" ? <Nav.Link><NavLink to="/add">Add Article</NavLink></Nav.Link> : null}
                        {this.props.loggedIn && this.props.user?.role === "ADMIN" ?  <Nav.Link><NavLink to="/addTopic">Add Topic</NavLink></Nav.Link> : null}
                    </Nav>
                    <Nav>
                        {!this.props.loggedIn ?  <Nav.Link><NavLink to="/login">Login</NavLink></Nav.Link> : null}
                        {!this.props.loggedIn ?  <Nav.Link><NavLink to="/register">Register</NavLink></Nav.Link> : null}
                        {this.props.loggedIn ? <Nav.Link><NavLink to="/j_spring_security_logout" onClick={this.props.logout}>Logout</NavLink></Nav.Link> : null}

                    </Nav>
                </Navbar>

            </div>
        );
    }
}

const mapStateToProps = (state, ownProps) => {
    return {
        loggedIn: state.loggedIn,
        user: state.user
    }
}

const mapDispatchToProps = (dispatch) => {
    return {
        logout: () => {
            dispatch({ type: 'LOGOUT' });
        }
    }
}
export default connect(mapStateToProps, mapDispatchToProps)(Navigation);
