package com.seitenbau.testing.personmanager;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<Job, Long>
{
}
