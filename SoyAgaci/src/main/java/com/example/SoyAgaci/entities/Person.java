package com.example.SoyAgaci.entities;

import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class Person implements IPerson {
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

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getBirthDate() {
        return birthDate;
    }

    @Override
    public String getGender() {
        return gender;
    }

    @Override
    public IPerson getSpouse() {
        return spouse != null ? new Person(spouse.id, spouse.name, spouse.birthDate, spouse.gender) : null;
    }

    @Override
    public void setSpouse(IPerson spouse) {
        this.spouse = (Person) spouse;
    }

    @Override
    public void addChild(IPerson child) {
        children.add((Person) child);
    }

    @Override
    public List<IPerson> getChildren() {
        return new ArrayList<>(children);
    }
}