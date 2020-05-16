import React from 'react';
import {connect} from 'react-redux';
import Link from 'react-router-dom/Link';

class Navbar extends React.Component {



    componentDidMount(){
    }

    render() {
        return (
            <div className="navbar-wrapper">
                <nav>
                    <ul>
                        <li><Link to={'/'}>Homepage</Link></li>
                        <li><Link to={'/login'}>Login</Link></li>
                        {this.props.loggedIn?<li><Link to={'/j_spring_security_logout'}>Logout</Link></li>:null}
                        <li><a>4</a></li>
                        <li><a>5</a></li>
                        <li><a>6</a></li>

                    </ul>
                </nav>
            </div>
        );
    }
}

const mapStateToProps = (state,ownProps) => {
    return{
        loggedIn: state.loggedIn
    }
}

export default connect(mapStateToProps)(Navbar);