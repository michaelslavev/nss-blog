import React, {Component} from 'react';
import {connect} from 'react-redux';
import Navigation from '../components/Navigation.js';
import ArticleComponent from '../components/ArticleComponent.js';
import Button from 'react-bootstrap/Button';


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

    deleteArticle = () => {
        let id = this.props.match.params.article_id;
        fetch('/api/articles/'+id,{
            method:'DELETE'
        }).then((response) => {
            if(response.status === 204 && response.ok){
                this.props.history.push("/");
            }else{
                alert("oops");
            }
        });
    }

    updateArticle = () => {
        let id = this.props.match.params.article_id;
        console.log(this.props.history);
        this.props.history.push("/edit/"+id);
    }


    render(){
        return(
            <div className="article-wrapper">
                <Navigation/>
                <ArticleComponent title={this.state.article?.title} content={this.state.article?.content} created={this.state.article?.date} author={this.state.article?.user}/>
                {this.props.user?.role === "ADMIN"?<Button onClick={this.updateArticle} variant="warning">Update!</Button>:null}
                {this.props.user?.role === "ADMIN"?<Button onClick={this.deleteArticle} variant="danger">Delete!</Button>:null}
                
            </div>
        );
    }
}

const mapStateToProps = (state, ownProps) => {
    return {
        user: state.user
    }
}

export default connect(mapStateToProps)(Article)