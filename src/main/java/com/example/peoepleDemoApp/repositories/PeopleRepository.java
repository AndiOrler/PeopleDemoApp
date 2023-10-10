package com.example.peoepleDemoApp.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.peoepleDemoApp.models.Person;

import java.util.List;

@Repository
public interface PeopleRepository extends CrudRepository<Person, Long> {

    List<Person> findAll();

}
