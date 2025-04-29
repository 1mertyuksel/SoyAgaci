package com.example.SoyAgaci.entities;

import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class Person {
    private int id;
    private String name;
    private String birthDate;
    private String gender;
    private Person spouse;
    private List<Person> children;

    public Person(int id, String name, String birthDate, String gender) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.gender = gender;
        this.children = new ArrayList<>();
    }

    // Getter ve Setterlar
    public int getId() { return id; }
    public String getName() { return name; }
    public String getBirthDate() { return birthDate; }
    public String getGender() { return gender; }
    public Person getSpouse() {
        return spouse != null ? new Person(spouse.id, spouse.name, spouse.birthDate, spouse.gender) : null;
    }

    public void setSpouse(Person spouse) {
        this.spouse = spouse;
    }

    public void addChild(Person child) {
        children.add(child);
    }

    public List<Person> getChildren() {
        return children;
    }
}

