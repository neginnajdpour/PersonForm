package model.da;

import model.entity.City;
import model.entity.Gender;
import model.entity.Person;
import model.utils.JdbcProvider;

import javax.naming.Name;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

// todo : !!!
// save/edit/remove/findAll/findById

public class PersonDa {

    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet rs;

    public PersonDa() throws SQLException {

        System.out.println("Connecting to database...");
        JdbcProvider jdbcProvider = new JdbcProvider();
        connection = jdbcProvider.getConnection();
    }

    public void savePerson(Person person) throws SQLException {

        preparedStatement = connection.prepareStatement(
                "INSERT INTO PERSON (NAME,FAMILY,BIRTHDATE,GENDER,CITY,SESKILL, EESKILL) VALUES (?,?,?,?,?,?,?)"
        );
        preparedStatement.setString(1, person.getName());
        preparedStatement.setString(2, person.getFamily());
        preparedStatement.setDate(3, Date.valueOf(person.getBirthdate()));
        preparedStatement.setString(4, person.getGender().toString());
        preparedStatement.setString(5, person.getCity().toString());
        preparedStatement.setBoolean(6,person.isSeSkill());
        preparedStatement.setBoolean(7,person.isEeSkill());
        preparedStatement.execute();
        close();

    }

    public void editPerson(Person person) throws SQLException {

        preparedStatement = connection.prepareStatement("UPDATE PERSON SET NAME=?, FAMILY=?, BIRTHDATE=?, GENDER=?, CITY=?, SESKILL=?, EESKILL=? WHERE PERSON.ID=?");
        preparedStatement.setString(1, person.getName());
        preparedStatement.setString(2, person.getFamily());
        preparedStatement.setDate(3, Date.valueOf(person.getBirthdate()));
        preparedStatement.setString(4, person.getGender().toString());
        preparedStatement.setString(5, person.getCity().toString());
        preparedStatement.setBoolean(6, person.isSeSkill());
        preparedStatement.setBoolean(7, person.isEeSkill());
        preparedStatement.setInt(8, person.getId());
        preparedStatement.execute();
        close();

    }

    public void deletePerson(int personId) throws SQLException {

        preparedStatement = connection.prepareStatement("DELETE FROM PERSON WHERE PERSON.ID=?");
        preparedStatement.setInt(1, personId);
        preparedStatement.execute();
    }

    public List<Person> getAllPersons() throws SQLException {
        List<Person> persons = new ArrayList<>();
        preparedStatement = connection.prepareStatement("SELECT * FROM PERSON");
        rs = preparedStatement.executeQuery();
        while (rs.next()) {
            Person person = new Person();
            person.setId(rs.getInt("PERSON.ID"));
            person.setName(rs.getString("PERSON.NAME"));
            person.setFamily(rs.getString("PERSON.FAMILY"));
            person.setBirthdate(rs.getDate("PERSON.Birthdate").toLocalDate());
            person.setGender(Gender.valueOf(rs.getString("PERSON.GENDER")));
            person.setCity(City.valueOf(rs.getString("PERSON.CITY")));
            person.setSeSkill(rs.getBoolean("PERSON.SESKILL"));
            person.setEeSkill(rs.getBoolean("PERSON.EESKILL"));

            persons.add(person);
        }
        return persons;
    }


    public void close() throws SQLException {
        preparedStatement.close();
        connection.close();
    }
}
