import React, { Component } from 'react';
import Link from 'react-router-dom/Link';

import Navigation from '../components/Navigation.js';
import Card from 'react-bootstrap/Card';

class Homepage extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            articles: [],
            visible: 2,
            error: false
          };
    }

    loadMore = () => {
        this.setState((prev) => {
            return {visible: prev.visible + 4};
          });
    }

    componentDidMount() {
        fetch("/api/articles").then(
            res => res.json()
        ).then(data => {
            this.setState({
                articles: data
            });
        }).catch(error => {
            console.log(error);
            this.setState({error:true});
        });
    }

    render() {
        return (
            <div className="homepage-wrapper">
                <Navigation />
                {this.state.articles.slice(0,this.state.visible).map((article) => {
                    return (
                        <div className="row">
                            <div className="col-12">
                                <Card>
                                    <Card.Body>
                                        <Card.Title>{article.title}</Card.Title>
                                        <Card.Text>
                                            {article.content.substr(0,150)}
                                        </Card.Text>
                                        <Card.Text ><Link to={"/article/"+article.id}>Link</Link></Card.Text>
                                    </Card.Body>
                                </Card>
                            </div>
                        </div>
                        
                    );
                })}
                {this.state.visible < this.state.articles.length &&
                    <button onClick={this.loadMore} type="button" className="load-more">Load more</button>
                }
            </div>
        );
    }
}

export default Homepage