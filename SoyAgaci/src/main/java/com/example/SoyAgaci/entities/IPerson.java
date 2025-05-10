package com.example.SoyAgaci.entities;

import java.util.List;

public interface IPerson {
    int getId();
    String getName();
    String getBirthDate();
    String getGender();
    IPerson getSpouse();
    void setSpouse(IPerson spouse);
    void addChild(IPerson child);
    List<IPerson> getChildren();
}