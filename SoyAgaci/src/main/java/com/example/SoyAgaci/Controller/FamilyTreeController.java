package com.example.SoyAgaci.Controller;

import com.example.SoyAgaci.Service.FamilyTreeService;
import com.example.SoyAgaci.entities.IPerson;
import com.example.SoyAgaci.entities.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/family")
@CrossOrigin(origins = "http://localhost:8000")
public class FamilyTreeController {

    @Autowired
    private FamilyTreeService familyTreeService;

    @PostMapping("/root")
    public ResponseEntity<String> createRootPerson(@RequestBody Person person) {
        try {
            familyTreeService.createRootPerson(person.getId(), person.getName(), person.getBirthDate(), person.getGender());
            return ResponseEntity.ok("Kök kişi oluşturuldu: " + person.getName());
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/{id}/spouse")
    public ResponseEntity<String> addSpouse(@PathVariable int id, @RequestBody Person spouse) {
        try {
            familyTreeService.addSpouse(id, spouse.getId(), spouse.getName(), spouse.getBirthDate(), spouse.getGender());
            return ResponseEntity.ok("Eş eklendi: " + spouse.getName());
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/{parentId}/child")
    public ResponseEntity<String> addChild(@PathVariable int parentId, @RequestBody Person child) {
        try {
            familyTreeService.addChild(parentId, child.getId(), child.getName(), child.getBirthDate(), child.getGender());
            return ResponseEntity.ok("Çocuk eklendi: " + child.getName());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{personId}")
    public ResponseEntity<String> removePerson(@PathVariable int personId) {
        try {
            familyTreeService.removePerson(personId);
            return ResponseEntity.ok("Kişi silindi (ID: " + personId + ")");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/tree")
    public Map<Integer, IPerson> getFullTree() {
        return familyTreeService.getPeopleById();
    }

    @GetMapping("/treejs")
    public List<Map<String, Object>> getTreeForFamilyJS() {
        List<Map<String, Object>> nodes = new ArrayList<>();
        Map<Integer, IPerson> people = familyTreeService.getPeopleById();

        for (IPerson person : people.values()) {
            Map<String, Object> node = new HashMap<>();
            node.put("id", person.getId());
            node.put("name", person.getName());
            node.put("gender", person.getGender().equals("Erkek") ? "male" : "female");

            if (person.getSpouse() != null) {
                node.put("pids", List.of(person.getSpouse().getId()));
            }

            List<IPerson> parents = new ArrayList<>();
            for (IPerson p : people.values()) {
                if (p.getChildren().contains(person)) {
                    parents.add(p);
                }
            }
            if (!parents.isEmpty()) {
                IPerson mother = null;
                IPerson father = null;
                for (IPerson parent : parents) {
                    if (parent.getGender().equals("Kadın") || parent.getGender().equals("female")) {
                        mother = parent;
                    } else if (parent.getGender().equals("Erkek") || parent.getGender().equals("male")) {
                        father = parent;
                    }
                }
                if (mother != null) {
                    node.put("mid", mother.getId());
                }
                if (father != null) {
                    node.put("fid", father.getId());
                }
            }

            nodes.add(node);
        }

        return nodes;
    }

    @GetMapping("/print")
    public String printTree() {
        return familyTreeService.printTree();
    }
}