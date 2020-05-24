import React, { Component } from 'react';

import Navigation from '../components/Navigation.js';
import { connect } from 'react-redux';
import Form from 'react-bootstrap/Form';
import Button from 'react-bootstrap/Button';

class AddArticle extends React.Component {

    submit = (e) =>{
        e.preventDefault();
        let body = {
            title: document.querySelector('input#title-input').value,
            content: document.querySelector('input#content-input').value
        };
        fetch('/api/articles',{
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body:JSON.stringify(body)
        }
        ).then(data => {
            console.log(data);
        })
    }

    render() {
        return (
            <div className="add-article-wrapper">
                <Navigation />
                <Form onSubmit={this.submit}>
                    <Form.Group controlId="formTitle">
                        <Form.Label>Title</Form.Label>
                        <Form.Control type="text" placeholder="Enter title" id="title-input"/>
                    </Form.Group>

                    <Form.Group controlId="formContent">
                        <Form.Label>Content</Form.Label>
                        <Form.Control type="text" placeholder="Enter content" id="content-input"/>
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