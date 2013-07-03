package com.seitenbau.testing.personmanager;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonJobRepository extends JpaRepository<PersonJob, PersonJobPK>
{
}
