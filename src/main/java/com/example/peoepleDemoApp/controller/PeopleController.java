package com.example.peoepleDemoApp.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.peoepleDemoApp.repositories.PeopleRepository;
import com.example.peoepleDemoApp.models.Person;

@RestController
public class PeopleController {

    @Autowired
    PeopleRepository repo;

    @GetMapping("/")
    public String welcome() {
        return "Welcome to the people demo app!";
    }

    @GetMapping("/all")
    public List<Person> findAll() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> findById(@PathVariable Long id) {

        Optional<Person> p = repo.findById(id);

        if (p.isPresent()) {
            return ResponseEntity.ok(p.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/create")
    public Person create(@RequestBody Person p) {
        return repo.save(p);
    }

    @PutMapping("/update/{id}")
    public Person update(@PathVariable Long id, @RequestBody Person p) {
        Optional<Person> existingPerson = repo.findById(id);
        if (existingPerson.isPresent()) {
            Person person = existingPerson.get();
            person.setFirstName(p.getFirstName());
            person.setLastName(p.getLastName());
            person.setBirthday(p.getBirthday());
            person.setCarOwner(p.isCarOwner());
            person.setCity(p.getCity());
            person.setStreet(p.getStreet());
            System.out.println("PeopleController - updating person now");
            return repo.save(person);
        } else {
            return null;
        }
    }


}