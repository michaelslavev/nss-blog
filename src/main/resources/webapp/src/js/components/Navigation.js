import React from 'react';
import { connect } from 'react-redux';
import NavLink from 'react-router-dom/NavLink';
import Navbar from 'react-bootstrap/Navbar';
import Nav from 'react-bootstrap/Nav';
import Logo from './blogolo.png';


class Navigation extends React.Component {

    componentDidMount() {
        console.log(this.props);
    }

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
                        {this.props.loggedIn && this.props.user?.role === "ADMIN" ? <NavLink to="/add">Add Article</NavLink> : null}
                    </Nav>
                    <Nav>
                        {!this.props.loggedIn ? <NavLink to="/login">Login</NavLink> : null}
                        {!this.props.loggedIn ? <NavLink to="/register">Register</NavLink> : null}
                        {this.props.loggedIn ? <NavLink to="/j_spring_security_logout" onClick={this.props.logout}>Logout</NavLink> : null}
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
