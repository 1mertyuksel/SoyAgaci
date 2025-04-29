package com.example.SoyAgaci.Controller;

import com.example.SoyAgaci.Service.FamilyTreeService;
import com.example.SoyAgaci.entities.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/family")
public class FamilyTreeController {

    @Autowired
    private FamilyTreeService familyTreeService;

    // Kök kişi oluşturma
    @PostMapping("/root")
    public String createRootPerson(@RequestBody Person person) {
        familyTreeService.createRootPerson(person.getId(), person.getName(), person.getBirthDate(), person.getGender());
        return "Kök kişi oluşturuldu: " + person.getName();
    }

    // Eş ekleme
    @PostMapping("/{id}/spouse")
    public String addSpouse(@PathVariable int id, @RequestBody Person spouse) {
        familyTreeService.addSpouse(id, spouse.getId(), spouse.getName(), spouse.getBirthDate(), spouse.getGender());
        return "Eş eklendi: " + spouse.getName();
    }

    // Çocuk ekleme
    @PostMapping("/{parentId}/child")
    public String addChild(@PathVariable int parentId, @RequestBody Person child) {
        familyTreeService.addChild(parentId, child.getId(), child.getName(), child.getBirthDate(), child.getGender());
        return "Çocuk eklendi: " + child.getName();
    }

    // Kişi silme
    @DeleteMapping("/{personId}")
    public String removePerson(@PathVariable int personId) {
        familyTreeService.removePerson(personId);
        return "Kişi silindi (ID: " + personId + ")";
    }

    // Tüm ağacı getirme (Basit bir JSON döngüsü)
    @GetMapping("/tree")
    public Map<Integer, Person> getFullTree() {
        return familyTreeService.getPeopleById(); // Not: Service'te getPeopleById() eklenmeli!
    }
}