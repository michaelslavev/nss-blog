import React, { Component } from 'react';

import Navigation from '../components/Navigation.js';
import Form from 'react-bootstrap/Form';
import Button from 'react-bootstrap/Button';
import Container from 'react-bootstrap/Container';

class AddTopic extends React.Component {

    submit = (e) =>{
        e.preventDefault();
        let name = document.querySelector('input#name-input');

        if(!(name.value.length > 0)){
            alert("Něco chybí");
            return;
        }

        let body = {
            name: name.value,
        };
        fetch('/api/topics',{
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body:JSON.stringify(body)
        }
        ).then(data => {
            console.log(data);
        }).catch(error => {
            console.log(error);
            alert("oops");
        })
    }

    render() {
        return (
            <div className="add-article-wrapper">
                <Navigation />
                <Container>
                <Form onSubmit={this.submit}>
                    <Form.Group controlId="formName">
                        <Form.Label>Name</Form.Label>
                        <Form.Control type="text" placeholder="Enter topic name" id="name-input"/>
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

export default AddTopic