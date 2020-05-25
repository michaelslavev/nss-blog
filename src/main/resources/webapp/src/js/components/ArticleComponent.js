import React, { Component } from 'react';

import Navigation from '../components/Navigation.js';
import Jumbotron from 'react-bootstrap/Jumbotron';
import Container from 'react-bootstrap/Container';

class ArticleComponent extends React.Component {
    render() {
        return (
            <Jumbotron fluid>
                <Container>
                    <h1>{this.props.title}</h1>
                    <h3>{this.props.author?.firstName + " "+ this.props.author?.lastName} | {new Date(this.props.created).toLocaleDateString()}</h3>
                    <p>{this.props.content}</p>
                </Container>

            </Jumbotron>
        );
    }
}

export default ArticleComponent