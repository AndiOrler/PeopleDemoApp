package com.example.peoepleDemoApp.seeder;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.example.peoepleDemoApp.models.Person;
import com.example.peoepleDemoApp.repositories.PeopleRepository;

@Component
public class DBSeeder {

    @Autowired
    PeopleRepository repo;

    // seed objects
    ArrayList<Person> newPeople = new ArrayList<Person>();

    @EventListener
    public void seed(ContextRefreshedEvent event) {
        seedFromFile();
        seedFromCode();
    }

    String[] firstNames = {
            "Alice",
            "Bob",
            "Catherine",
            "David",
            "Emily",
            "Frank",
            "Grace",
            "Henry",
            "Isabella",
            "Jack",
            "Katherine",
            "Liam",
            "Mia",
            "Nathan",
            "Olivia",
            "Peter",
            "Quinn",
            "Rachel",
            "Samuel",
            "Taylor"
    };

    String[] lastNames = {
            "Smith",
            "Johnson",
            "Brown",
            "Davis",
            "Wilson",
            "Anderson",
            "Martinez",
            "Lee",
            "Gonzalez",
            "Clark",
            "Hall",
            "Lewis",
            "Walker",
            "Perez",
            "Taylor",
            "White",
            "Moore",
            "Harris",
            "Martin",
            "Jackson"
    };

    String[] cities = {
            "Basel",
            "Zürich",
            "Luzern",
            "Bern",
            "Winterthur"
    };

    String[] germanStreetNames = {
            "Hauptstraße",
            "Kaiserstraße",
            "Schillerstraße",
            "Goethestraße",
            "Mozartstraße",
            "Berliner Straße",
            "Ludwigstraße",
            "Friedrichstraße",
            "Königstraße",
            "Schlossallee"
    };

    public void seedFromFile() {

        try (BufferedReader reader = new BufferedReader(new FileReader("./seeder/seed.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Split the line into fields (e.g., ID and Name)
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    int id = Integer.parseInt(parts[0]);
                    String name = parts[1].trim();
                    Person entity = new Person();
                    entity.setFirstName(randValFromArr(firstNames));
                    entity.setLastName(randValFromArr(lastNames));
                    entity.setBirthday(randBirthday());
                    entity.setCarOwner(false);
                    entity.setCity(randValFromArr(cities));
                    entity.setStreet(randValFromArr(germanStreetNames) + " " + randNo(1, 200));
                    newPeople.add(entity);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // return entityList;

    }

    public void seedFromCode() {

        List<Person> allPeople = repo.findAll();

        if (allPeople.size() > 0) {
            System.out.println("DBSeeder - already seeded");
            return;
        }

        System.out.println("DBSeeder - start seeding");

        for (int i = 0; i < 50; i++) {

            Person newPerson = new Person();
            newPerson.setFirstName(randValFromArr(firstNames));
            newPerson.setLastName(randValFromArr(lastNames));
            newPerson.setBirthday(randBirthday());
            newPerson.setCarOwner(false);
            newPerson.setCity(randValFromArr(cities));
            newPerson.setStreet(randValFromArr(germanStreetNames) + " " + randNo(1, 200));

            int count = 0;
            while (newPeople.contains(newPerson) && count <= 100) {
                newPerson.setBirthday(randBirthday());
                count++;
            }

            newPeople.add(newPerson);
        }

        repo.saveAll(newPeople);

    }

    private static String randBirthday() {
        // Create a Calendar object and set it to the current date
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());

        // Calculate a random age between 1 and 80
        Random random = new Random();
        int randomAge = random.nextInt(80) + 1;

        // Subtract the random age from the current year to get the birth year
        int birthYear = calendar.get(Calendar.YEAR) - randomAge;

        // Generate a random day of the year
        int randomDayOfYear = random.nextInt(365) + 1;

        // Set the calendar to the birth year and random day
        calendar.set(Calendar.YEAR, birthYear);
        calendar.set(Calendar.DAY_OF_YEAR, randomDayOfYear);

        // Format the date as a string (e.g., "yyyy-MM-dd")
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String birthdayString = dateFormat.format(calendar.getTime());

        return birthdayString;
    }

    private static <T> T randValFromArr(T[] array) {
        if (array == null || array.length == 0) {
            throw new IllegalArgumentException("Array is empty or null");
        }

        Random random = new Random();
        int randomIndex = random.nextInt(array.length);

        return array[randomIndex];
    }

    private static int randNo(int min, int max) {
        if (min >= max) {
            throw new IllegalArgumentException("Max must be greater than min");
        }

        Random random = new Random();
        return random.nextInt(max - min + 1) + min;
    }

}
