package com.example.SoyAgaci.Service;

import org.springframework.stereotype.Service;
import com.example.SoyAgaci.entities.IPerson;
import com.example.SoyAgaci.entities.Person;
import java.util.HashMap;
import java.util.Map;

@Service
public class FamilyTreeService {
    private IPerson root;
    private Map<Integer, IPerson> peopleById;

    public FamilyTreeService() {
        peopleById = new HashMap<>();
    }

    public void createRootPerson(int id, String name, String birthDate, String gender) {
        if (root != null) {
            throw new IllegalStateException("Root zaten oluşturulmuş.");
        }
        root = new Person(id, name, birthDate, gender);
        peopleById.put(id, root);
    }

    public void addSpouse(int id, int spouseId, String name, String birthDate, String gender) {
        IPerson person = peopleById.get(id);
        if (person == null) {
            throw new IllegalArgumentException("Kişi bulunamadı: ID " + id);
        }
        if (person.getSpouse() != null) {
            throw new IllegalStateException("Kişinin zaten bir eşi var: " + person.getSpouse().getName());
        }
        if (peopleById.containsKey(spouseId)) {
            throw new IllegalArgumentException("Eş ID zaten mevcut: " + spouseId);
        }

        IPerson spouse = new Person(spouseId, name, birthDate, gender);
        person.setSpouse(spouse);
        spouse.setSpouse(person);
        peopleById.put(spouseId, spouse);
    }

    public void addChild(int parentId, int childId, String name, String birthDate, String gender) {
        IPerson parent = peopleById.get(parentId);
        if (parent == null) {
            throw new IllegalArgumentException("Ebeveyn bulunamadı: ID " + parentId);
        }
        if (peopleById.containsKey(childId)) {
            throw new IllegalArgumentException("Çocuk ID zaten mevcut: " + childId);
        }

        IPerson child = new Person(childId, name, birthDate, gender);
        parent.addChild(child);
        if (parent.getSpouse() != null) {
            parent.getSpouse().addChild(child);
        }
        peopleById.put(childId, child);
    }

    public Map<Integer, IPerson> getPeopleById() {
        return peopleById;
    }

    public String printTree() {
        StringBuilder treeOutput = new StringBuilder();
        printTreeRecursive(root, 0, treeOutput);
        return treeOutput.toString();
    }

    private void printTreeRecursive(IPerson person, int level, StringBuilder output) {
        if (person == null) return;

        output.append(" ".repeat(level * 4))
                .append(person.getName())
                .append(" (").append(person.getGender()).append(")\n");
        if (person.getSpouse() != null) {
            output.append(" ".repeat(level * 4 + 2))
                    .append("Eşi: ").append(person.getSpouse().getName()).append("\n");
        }

        for (IPerson child : person.getChildren()) {
            printTreeRecursive(child, level + 1, output);
        }
    }

    public void removePerson(int personId) {
        IPerson toRemove = peopleById.get(personId);
        if (toRemove == null) {
            throw new IllegalArgumentException("Silinecek kişi bulunamadı: ID " + personId);
        }

        for (IPerson person : peopleById.values()) {
            person.getChildren().removeIf(child -> child.getId() == personId);
        }

        for (IPerson person : peopleById.values()) {
            if (person.getSpouse() != null && person.getSpouse().getId() == personId) {
                person.setSpouse(null);
            }
        }

        peopleById.remove(personId);
    }
}