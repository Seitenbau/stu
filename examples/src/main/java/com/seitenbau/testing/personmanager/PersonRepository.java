package com.seitenbau.testing.personmanager;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long>
{

    Person findByName(String name);

}
