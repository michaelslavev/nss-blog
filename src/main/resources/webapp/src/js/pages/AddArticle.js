import React, { Component } from 'react';

import Navigation from '../components/Navigation.js';
import { connect } from 'react-redux';
import Form from 'react-bootstrap/Form';
import Button from 'react-bootstrap/Button';

class AddArticle extends React.Component {

    submit = (e) =>{
        e.preventDefault();
        console.log("submit");
        console.log(this.props.user.id);
    }

    render() {
        return (
            <div className="add-article-wrapper">
                <Navigation />
                <Form onSubmit={this.submit}>
                    <Form.Group controlId="formTitle">
                        <Form.Label>Title</Form.Label>
                        <Form.Control type="text" placeholder="Enter title" />
                    </Form.Group>

                    <Form.Group controlId="formContent">
                        <Form.Label>Content</Form.Label>
                        <Form.Control type="text" placeholder="Enter content" />
                    </Form.Group>
                    <Button variant="primary" type="submit">
                        Submit
                    </Button>
                </Form>
            </div>
        );
    }
}

const mapStateToProps = (state, ownProps) => {
    return {
        user: state.user
    }
}

export default connect(mapStateToProps)(AddArticle)