package model.entity;



import com.google.gson.Gson;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@ToString

public class Person {

    private int id;
    private String name;
    private String family;
    private LocalDate birthdate;
    private Gender gender;
    private City city;
    private boolean seSkill;
    private boolean eeSkill;

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

}
