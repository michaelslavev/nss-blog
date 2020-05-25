import React, {Component} from 'react';

import Navigation from '../components/Navigation.js';
import ArticleComponent from '../components/ArticleComponent.js';


class Article extends React.Component{
    constructor(props){
        super(props);
        this.state={
            article:null
        }
    }

    componentDidMount(){
        let id = this.props.match.params.article_id;
        fetch('/api/articles/'+id).then(res => {
            console.log(res);
            res.json().then(data => {
                console.log(data);
                this.setState({article:data})
            });
            }
        ).catch(error => console.log(error));
    }


    render(){
        return(
            <div className="article-wrapper">
                <Navigation/>
                <ArticleComponent title={this.state.article?.title} content={this.state.article?.content} created={this.state.article?.date} author={this.state.article?.user}/>
            </div>
        );
    }
}

export default Article