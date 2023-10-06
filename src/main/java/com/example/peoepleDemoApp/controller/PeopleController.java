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

    // @Autowired
    // @Qualifier("fancyWriter")
    // Writer writer;

    @Autowired
    PeopleRepository repo;

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

}
