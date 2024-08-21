package model.utils;

import org.apache.commons.dbcp2.BasicDataSource;


import java.sql.*;

// todo : add mysql jdbc driver to project
public class JdbcProvider {
    private BasicDataSource basicDataSource = new BasicDataSource();

    public Connection getConnection() throws SQLException {

        System.out.println("Creating a new connection");

        basicDataSource = new BasicDataSource();
        basicDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        basicDataSource.setUrl("jdbc:mysql://localhost:3306/mft");
        basicDataSource.setUsername("root");
        basicDataSource.setPassword("Negin@09143148516");
        basicDataSource.setMinIdle(5);
        basicDataSource.setMaxIdle(10);

        return basicDataSource.getConnection();
    }
}
