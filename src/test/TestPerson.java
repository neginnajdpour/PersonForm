package test;

import model.da.PersonDa;
import model.entity.City;
import model.entity.Gender;
import model.entity.Person;


import java.sql.SQLException;
import java.time.LocalDate;

public class TestPerson {
    public static void main(String[] args) throws SQLException {
        Person person = Person
                .builder()
                .id(17)
                .name("nn")
                .family("nn")
                .birthdate(LocalDate.now())
                .gender(Gender.Female)
                .city(City.Shiraz)
                .seSkill(true)
                .eeSkill(false)
                .build();

        PersonDa personDa = new PersonDa();
        personDa.editPerson(person);
    }
}
