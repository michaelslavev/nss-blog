import React, { Component } from 'react';

import Navigation from '../components/Navigation.js';
import { connect } from 'react-redux';
import Form from 'react-bootstrap/Form';
import Button from 'react-bootstrap/Button';
import Container from 'react-bootstrap/Container';

class AddArticle extends React.Component {

    constructor(props){
        super(props);
        this.state={
            topics:null
        }
    }

    submit = (e) =>{
        e.preventDefault();
        let title = document.querySelector('input#title-input');
        let content = document.querySelector('input#content-input');
        let topicSelect = document.querySelector('select#topic-input');

        let topicBody = this.state.topics.filter((t) => t.name == topicSelect.options[topicSelect.selectedIndex].value);
        console.log(topicBody);

        if(!(title.value.length > 0 && content.value.length > 0 && topicBody.length > 0)){
            alert("Něco chybí");
            return;
        }

        let body = {
            title: title.value,
            content: content.value,
            topics: topicBody
        };

        console.log(body);
        fetch('/api/articles',{
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

    componentDidMount(){
        fetch('/api/topics').then((response) => {
            response.json().then((data) => {
                this.setState({
                    topics:data
                })
            });
        });
    }

    render() {
        return (
            <div className="add-topic-wrapper">
                <Navigation />
                <Container>
                <Form onSubmit={this.submit}>
                    <Form.Group controlId="formTitle">
                        <Form.Label>Title</Form.Label>
                        <Form.Control type="text" placeholder="Enter title" id="title-input"/>
                    </Form.Group>

                    <Form.Group controlId="formContent">
                        <Form.Label>Content</Form.Label>
                        <Form.Control type="text" placeholder="Enter content" id="content-input"/>
                    </Form.Group>

                    <Form.Group controlId="formTopic">
                        <Form.Label>Topic select</Form.Label>
                        <Form.Control as="select" id="topic-input">
                            {this.state.topics && this.state.topics.map((topic) => {
                                return <option value={topic.name}>{topic.name}</option>
                            })}
                        </Form.Control>
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

const mapStateToProps = (state, ownProps) => {
    return {
        user: state.user
    }
}

export default connect(mapStateToProps)(AddArticle)