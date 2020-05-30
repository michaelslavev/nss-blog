import React, { Component } from 'react';

import Navigation from '../components/Navigation.js';
import { connect } from 'react-redux';
import Form from 'react-bootstrap/Form';
import Button from 'react-bootstrap/Button';
import Container from 'react-bootstrap/Container';

class EditArticle extends React.Component {

    constructor(props){
        super(props);
        this.state={
            topics:null,
            article:null
        }
    }

    submit = (e) =>{
        e.preventDefault();
        const title = document.querySelector('input#title-input').value || this.state.article.title;
        const content = document.querySelector('input#content-input').value || this.state.article.content;
        const topicSelect = document.querySelector('select#topic-input');

        let topicBody = this.state.topics.filter((t) => t.name == topicSelect.options[topicSelect.selectedIndex].value);
        console.log(topicBody);

        const id = this.props.match.params.article_id;

        if(!(title.length > 0 && content.length > 0 && topicBody.length > 0)){
            alert("Něco chybí");
            return;
        }

        let body = this.state.article;
        body = {
            ...body,
            id: id,
            title: title,
            content: content,
            topics: topicBody
        };

        console.log(body);
        fetch('/api/articles/'+id,{
            method: 'PUT',
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
                    ...this.state,
                    topics:data
                })
            });
        });
        fetch('/api/articles/'+this.props.match.params.article_id).then((response) => {
            response.json().then((data) => {
                this.setState({
                    ...this.state,
                    article:data
                })
            });
        }).catch(error => {console.log(error)});
    }

    render() {
        return (
            <div className="add-topic-wrapper">
                <Navigation />
                <Container>
                <Form onSubmit={this.submit}>
                    <Form.Group controlId="formTitle">
                        <Form.Label>Title</Form.Label>
                        <Form.Control type="text" placeholder={this.state.article?.title} id="title-input"/>
                    </Form.Group>

                    <Form.Group controlId="formContent">
                        <Form.Label>Content</Form.Label>
                        <Form.Control type="text" placeholder={this.state.article?.content} id="content-input"/>
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

export default connect(mapStateToProps)(EditArticle)