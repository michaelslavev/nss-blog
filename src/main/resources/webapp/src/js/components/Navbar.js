import React from 'react';
import { connect } from 'react-redux';
import Link from 'react-router-dom/Link';

class Navbar extends React.Component {

    componentDidMount() {
    }

    render() {
        return (
            <div className="navbar-wrapper">
                <nav>
                    <ul>
                        <li><Link to={'/'}>Homepage</Link></li>
                        {this.props.loggedIn ? <li><Link to={'/add'}>Add article</Link></li> : null}
                        {!this.props.loggedIn ? <li><Link to={'/login'}>Login</Link></li> : null}
                        {this.props.loggedIn ? <li><Link to={'/j_spring_security_logout'} onClick={this.props.logout}>Logout</Link></li> : null}

                    </ul>
                </nav>
            </div>
        );
    }
}

const mapStateToProps = (state, ownProps) => {
    return {
        loggedIn: state.loggedIn
    }
}

const mapDispatchToProps = (dispatch) => {
    return {
        logout: () => {
            dispatch({ type: 'LOGOUT'});
        }
    }
}
export default connect(mapStateToProps,mapDispatchToProps)(Navbar);