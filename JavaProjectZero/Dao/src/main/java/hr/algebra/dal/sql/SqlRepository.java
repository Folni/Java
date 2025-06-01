/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.dal.sql;

import hr.algebra.dal.Repository;
import hr.algebra.model.Article;
import hr.algebra.model.Person;
import hr.algebra.model.User;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;

public class SqlRepository implements Repository {

    /*Article const*/
    private static final String ID_ARTICLE = "IDArticle";
    private static final String TITLE = "Title";
    private static final String LINK = "Link";
    private static final String DESCRIPTION = "Description";
    private static final String PUBLISHED_DATE = "PublishedDate";
    private static final String CREATOR = "Creator";
    private static final String PICTURE_PATH = "PicturePath";
    private static final String CONTENT = "Content";
    
    /*Person const*/
    private static final String ID_PERSON = "IDPerson";
    private static final String NAME = "Name";
    private static final String SURNAME = "Surname";
    private static final String EMAIL = "Email";
    
    /*User const*/
    private static final String ID_USER = "IDUser";
    private static final String USERNAME = "Username";
    private static final String PWDHASH = "PwdHash";
    private static final String PWDSALT = "PwdSalt";
    private static final String ISADMIN = "IsAdmin";
    
    /*Article crud const*/
    private static final String CREATE_ARTICLE = "{ CALL createArticle (?,?,?,?,?,?,?,?) }";
    private static final String UPDATE_ARTICLE = "{ CALL updateArticle (?,?,?,?,?,?,?,?) }";
    private static final String DELETE_ARTICLE = "{ CALL deleteArticle (?) }";
    private static final String SELECT_ARTICLE = "{ CALL selectArticle (?) }";
    private static final String SELECT_ARTICLES = "{ CALL selectArticles }";
    
    /*Person crud const*/
    private static final String CREATE_PERSON = "{ CALL createPerson (?,?,?,?) }";
    private static final String UPDATE_PERSON = "{ CALL updatePerson (?,?,?,?) }";
    private static final String DELETE_PERSON = "{ CALL deletePerson (?) }";
    private static final String SELECT_PERSON = "{ CALL selectPerson (?) }";
    private static final String SELECT_PEOPLE = "{ CALL selectPeople }";
    
    /*User crud const*/
    private static final String CREATE_USER = "{ CALL createUser (?,?,?,?,?) }";
    private static final String UPDATE_USER = "{ CALL updateUser (?,?,?,?,?) }";
    private static final String DELETE_USER = "{ CALL deleteUser (?) }";
    private static final String SELECT_USER = "{ CALL selectUser (?) }";
    private static final String SELECT_USERS = "{ CALL selectUsers }";

    @Override
    public int createArticle(Article article) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(CREATE_ARTICLE)) {

            stmt.setString(TITLE, article.getTitle());
            stmt.setString(LINK, article.getLink());
            stmt.setString(DESCRIPTION, article.getDescription());
            stmt.setString(PUBLISHED_DATE, article.getPublishedDate().format(Article.DATE_FORMATTER));
            stmt.setString(CREATOR, article.getCreator());
            stmt.setString(PICTURE_PATH, article.getPicturePath());
            stmt.setString(CONTENT, article.getContent());
            stmt.registerOutParameter(ID_ARTICLE, Types.INTEGER);

            stmt.executeUpdate();
            return stmt.getInt(ID_ARTICLE);
        }
    }

    @Override
    public void createArticles(List<Article> articles) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(CREATE_ARTICLE)) {

            for (Article article : articles) {
                stmt.setString(TITLE, article.getTitle());
                stmt.setString(LINK, article.getLink());
                stmt.setString(DESCRIPTION, article.getDescription());
                stmt.setString(PUBLISHED_DATE, article.getPublishedDate().format(Article.DATE_FORMATTER));
                stmt.setString(CREATOR, article.getCreator());
                stmt.setString(PICTURE_PATH, article.getPicturePath());
                stmt.setString(CONTENT, article.getContent());
                stmt.registerOutParameter(ID_ARTICLE, Types.INTEGER);
                
                stmt.executeUpdate();
            }
        }
    }

    @Override
    public void updateArticle(int id, Article article) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(UPDATE_ARTICLE)) {

            stmt.setString(TITLE, article.getTitle());
            stmt.setString(LINK, article.getLink());
            stmt.setString(DESCRIPTION, article.getDescription());
            stmt.setString(PUBLISHED_DATE, article.getPublishedDate().format(Article.DATE_FORMATTER));
            stmt.setString(CREATOR, article.getCreator());
            stmt.setString(PICTURE_PATH, article.getPicturePath());
            stmt.setString(CONTENT, article.getContent());
            stmt.setInt(ID_ARTICLE, id);

            stmt.executeUpdate();
            
        }
    }

    @Override
    public void deleteArticle(int id) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(DELETE_ARTICLE)) {

            stmt.setInt(ID_ARTICLE, id);

            stmt.executeUpdate();
        }
    }

    @Override
    public Optional<Article> selectArticle(int id) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(SELECT_ARTICLE)) {

            stmt.setInt(ID_ARTICLE, id);
            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {
                    return Optional.of(new Article(
                            rs.getInt(ID_ARTICLE),
                            rs.getString(TITLE),
                            rs.getString(LINK),
                            rs.getString(DESCRIPTION),
                            LocalDateTime.parse(rs.getString(PUBLISHED_DATE), Article.DATE_FORMATTER)),
                            rs.getString(CREATOR),
                            rs.getString(PICTURE_PATH),
                            rs.getString(CONTENT));
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Article> selectArticles() throws Exception {
        List<Article> articles = new ArrayList<>();
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(SELECT_ARTICLES); 
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                articles.add(new Article(
                            rs.getInt(ID_ARTICLE),
                            rs.getString(TITLE),
                            rs.getString(LINK),
                            rs.getString(DESCRIPTION),
                            LocalDateTime.parse(rs.getString(PUBLISHED_DATE), Article.DATE_FORMATTER)),
                            rs.getString(CREATOR),
                            rs.getString(PICTURE_PATH),
                            rs.getString(CONTENT));
            }
        }
        return articles;
    }

    @Override
    public int createPerson(Person person) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(CREATE_PERSON)) {

            stmt.setString(NAME, person.getName());
            stmt.setString(SURNAME, person.getSurname());
            stmt.setString(EMAIL, person.getEmail());
            
            stmt.registerOutParameter(ID_PERSON, Types.INTEGER);

            stmt.executeUpdate();
            return stmt.getInt(ID_PERSON);
        }
    }

    @Override
    public void createPeople(List<Person> people) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(CREATE_PERSON)) {

            for (Person person : people) {
                stmt.setString(NAME, person.getName());
                stmt.setString(SURNAME, person.getSurname());
                stmt.setString(EMAIL, person.getEmail());
                
                stmt.registerOutParameter(ID_PERSON, Types.INTEGER);
                
                stmt.executeUpdate();
            }
            
        }
    }

    @Override
    public void updatePerson(int id, Person data) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(UPDATE_PERSON)) {

            stmt.setString(NAME, data.getName());
            stmt.setString(SURNAME, data.getSurname());
            stmt.setString(EMAIL, data.getEmail());
            
            stmt.setInt(ID_PERSON, id);

            stmt.executeUpdate();
            
        }
    }

    @Override
    public void deletePerson(int id) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(DELETE_PERSON)) {

            stmt.setInt(ID_PERSON, id);

            stmt.executeUpdate();
        }
    }

    @Override
    public Optional<Person> selectPerson(int id) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(SELECT_PERSON)) {

            stmt.setInt(ID_PERSON, id);
            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {
                    return Optional.of(new Person(
                            rs.getInt(ID_PERSON),
                            rs.getString(NAME),
                            rs.getString(SURNAME),
                            rs.getString(EMAIL)));
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Person> selectPeople() throws Exception {
        List<Person> people = new ArrayList<>();
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(SELECT_PERSON); 
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                people.add(new Person(
                            rs.getInt(ID_PERSON),
                            rs.getString(NAME),
                            rs.getString(SURNAME),
                            rs.getString(EMAIL)));            }
        }
        return people;
    }

    @Override
    public int createUser(User user) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(CREATE_USER)) {

            stmt.setString(USERNAME, user.getUsername());
            stmt.setString(PWDHASH, user.getPwdHash());
            stmt.setString(PWDSALT, user.getPwdSalt());
            stmt.setString(ISADMIN, user.getIsAdmin());
            
            stmt.registerOutParameter(ID_USER, Types.INTEGER);

            stmt.executeUpdate();
            return stmt.getInt(ID_USER);
        }
    }

    @Override
    public void createUsers(List<User> users) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void updateUser(int id, User data) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void deleteUser(int id) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Optional<User> selectUser(int id) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<User> selectUsers() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void InsertArticleContributor(int id, Person data) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void DeleteArticleContributor(int ArticleID, int PersonID) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Person> getArticleContributors(int id) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
