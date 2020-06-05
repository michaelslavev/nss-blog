package the.nss.boys.blog.builder;

import the.nss.boys.blog.model.Article;
import the.nss.boys.blog.model.Topic;
import the.nss.boys.blog.model.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ArticleBuilder {

    private static ArticleBuilder instance = null;
    private Article article = null;

    private ArticleBuilder(){}

    public static ArticleBuilder getInstance(){
        if(instance == null){
            instance = new ArticleBuilder();
        }
        return instance;
    }

    public boolean startBuilding(){
        if(article == null){
            article = new Article();
            return true;
        }
        return false;
    }

    public Article getBuiltArticle(){
        Article toReturn = article;
        article = null;
        return toReturn;
    }

    public void addContent(String content){
        article.setContent(content);
    }

    public void addDate(){
        article.setDate(LocalDate.now());
    }

    public void addTitle(String title){
        article.setTitle(title);
    }

    public void addUser(User user){
        article.setUser(user);
    }

    public void addTopic(Topic topic){
        List<Topic> topics = new ArrayList<>();
        topics.add(topic);
        article.setTopics(topics);
    }
}
