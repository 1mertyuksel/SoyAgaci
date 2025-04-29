package com.example.SoyAgaci.Service;

import org.springframework.stereotype.Service;
import com.example.SoyAgaci.entities.Person;
import java.util.HashMap;
import java.util.Map;

@Service
public class FamilyTreeService {
    private Person root;  // Soy ağacının kök düğümü
    private Map<Integer, Person> peopleById;

    public FamilyTreeService() {
        peopleById = new HashMap<>();
    }

    // Kök kişi oluşturma
    public void createRootPerson(int id, String name, String birthDate, String gender) {
        if (root != null) {
            System.out.println("Root zaten oluşturulmuş.");
            return;
        }
        root = new Person(id, name, birthDate, gender);
        peopleById.put(id, root);
    }

    // Eş ekleme
    public void addSpouse(int id, int spouseId, String name, String birthDate, String gender) {
        Person person = peopleById.get(id);
        if (person != null && person.getSpouse() == null) {
            Person spouse = new Person(spouseId, name, birthDate, gender);
            person.setSpouse(spouse);
            spouse.setSpouse(person);
            peopleById.put(spouseId, spouse);
        } else {
            System.out.println("Kişi bulunamadı veya zaten eşi var.");
        }
    }

    // Çocuk ekleme
    public void addChild(int parentId, int childId, String name, String birthDate, String gender) {
        Person parent = peopleById.get(parentId);
        if (parent != null) {
            Person child = new Person(childId, name, birthDate, gender);
            parent.addChild(child);
            peopleById.put(childId, child);
        }
    }


    public Map<Integer, Person> getPeopleById() {
        return peopleById;
    }


    // Soy ağacını yazdırma (pre-order traversal)
    public void printTree() {
        printTreeRecursive(root, 0);
    }

    // Ağaçta gezinme
    private void printTreeRecursive(Person person, int level) {
        if (person == null) return;

        System.out.println(" ".repeat(level * 4) + person.getName() + " (" + person.getGender() + ")");
        if (person.getSpouse() != null) {
            System.out.println(" ".repeat(level * 4 + 2) + "Eşi: " + person.getSpouse().getName());
        }

        for (Person child : person.getChildren()) {
            printTreeRecursive(child, level + 1);
        }
    }
    public void removePerson(int personId) {
        Person toRemove = peopleById.get(personId);
        if (toRemove == null) {
            System.out.println("Silinecek kişi bulunamadı.");
            return;
        }

        // Tüm kişiler içinde bu kişiyi çocuk listesinden kaldır
        for (Person person : peopleById.values()) {
            person.getChildren().removeIf(child -> child.getId() == personId);
        }

        // Spouse ilişkisini de kaldır
        for (Person person : peopleById.values()) {
            if (person.getSpouse() != null && person.getSpouse().getId() == personId) {
                person.setSpouse(null);
            }
        }

        // Son olarak haritadan sil
        peopleById.remove(personId);
        System.out.println("Kişi başarıyla silindi: " + toRemove.getName());
    }


}
