package com.seitenbau.testing.dbunit.dao;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepo extends JpaRepository<Person, Long>
{
}
